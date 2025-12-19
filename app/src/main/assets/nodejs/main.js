/**
 * Eldoleado WhatsApp Bridge - Embedded Node.js
 * Runs Baileys inside the Android app
 */

// File-based logging for Android
const fs = require('fs');
const path = require('path');

const args = {};
process.argv.slice(2).forEach(arg => {
    const [key, value] = arg.replace('--', '').split('=');
    args[key] = value;
});
const DATA_DIR = args['data-dir'] || __dirname;
const LOG_FILE = path.join(DATA_DIR, 'node.log');

function log(msg) {
    const line = `[${new Date().toISOString()}] ${msg}\n`;
    try {
        fs.appendFileSync(LOG_FILE, line);
    } catch (e) {
        // ignore
    }
}

// Clear old log on start
try { fs.writeFileSync(LOG_FILE, ''); } catch (e) {}

log('[STARTUP] main.js starting...');
log('[STARTUP] Node version: ' + process.version);
log('[STARTUP] DATA_DIR: ' + DATA_DIR);

// Polyfill globalThis.crypto for Baileys (nodejs-mobile doesn't have webcrypto in globalThis)
try {
    if (!globalThis.crypto || !globalThis.crypto.subtle) {
        log('[STARTUP] Setting up crypto polyfill...');
        const nodeCrypto = require('crypto');
        if (nodeCrypto.webcrypto) {
            globalThis.crypto = nodeCrypto.webcrypto;
            log('[STARTUP] crypto polyfill set from crypto.webcrypto');
        } else {
            log('[WARN] crypto.webcrypto not available');
        }
    } else {
        log('[STARTUP] globalThis.crypto.subtle already available');
    }
} catch (e) {
    log('[ERROR] Failed to setup crypto polyfill: ' + e.message);
}

const http = require('http');
log('[STARTUP] http module loaded');

const { EventEmitter } = require('events');
log('[STARTUP] events module loaded');

const PORT = parseInt(args.port) || 3000;
const SESSIONS_DIR = path.join(DATA_DIR, 'sessions');

log('[STARTUP] PORT=' + PORT);
log('[STARTUP] SESSIONS_DIR=' + SESSIONS_DIR);

// Ensure sessions directory exists
try {
    if (!fs.existsSync(SESSIONS_DIR)) {
        fs.mkdirSync(SESSIONS_DIR, { recursive: true });
    }
    log('[STARTUP] Sessions dir ready');
} catch (e) {
    log('[ERROR] Failed to create sessions dir: ' + e.message);
}

// State
let socket = null;
let qrCode = '';
let pairingCode = '';
let status = 'disconnected';
let phoneNumber = '';
let pushName = '';
let pairingPhoneNumber = '';

// Event emitter for status updates
const events = new EventEmitter();

// Lazy load Baileys (it's a large module)
let makeWASocket, useMultiFileAuthState, DisconnectReason;

async function loadBaileys() {
    if (makeWASocket) return;

    log('[BAILEYS] Loading Baileys module (ESM dynamic import)...');
    try {
        // Baileys is now an ES Module, need dynamic import()
        const baileys = await import('@whiskeysockets/baileys');
        log('[BAILEYS] import() successful');
        makeWASocket = baileys.default;
        useMultiFileAuthState = baileys.useMultiFileAuthState;
        DisconnectReason = baileys.DisconnectReason;
        log('[INFO] Baileys loaded successfully');
        log('[INFO] makeWASocket: ' + (typeof makeWASocket));
        log('[INFO] useMultiFileAuthState: ' + (typeof useMultiFileAuthState));
    } catch (err) {
        log('[ERROR] Failed to load Baileys: ' + err.message);
        log('[ERROR] Stack: ' + err.stack);
        throw err;
    }
}

async function connectWhatsApp() {
    await loadBaileys();

    log('[INFO] Connecting to WhatsApp...');
    status = 'connecting';
    events.emit('status', { status });

    const { state, saveCreds } = await useMultiFileAuthState(SESSIONS_DIR);

    log('[INFO] Creating WhatsApp socket...');

    // Create pino-compatible logger
    const createLogger = (name) => ({
        level: 'warn',
        child: (opts) => createLogger(name + '/' + (opts.class || 'child')),
        trace: (msg) => {},
        debug: (msg) => {},
        info: (msg) => log('[WS-INFO:' + name + '] ' + (typeof msg === 'object' ? JSON.stringify(msg) : msg)),
        warn: (msg) => log('[WS-WARN:' + name + '] ' + (typeof msg === 'object' ? JSON.stringify(msg) : msg)),
        error: (msg) => log('[WS-ERROR:' + name + '] ' + (typeof msg === 'object' ? JSON.stringify(msg) : msg)),
        fatal: (msg) => log('[WS-FATAL:' + name + '] ' + (typeof msg === 'object' ? JSON.stringify(msg) : msg)),
    });

    socket = makeWASocket({
        auth: state,
        printQRInTerminal: false,
        // Use WhatsApp Web browser fingerprint
        browser: ['Chrome (Linux)', 'Chrome', '120.0.0'],
        // Increase connection timeout
        connectTimeoutMs: 60000,
        defaultQueryTimeoutMs: 60000,
        // pino-compatible logger
        logger: createLogger('baileys'),
    });
    log('[INFO] Socket created');

    // Log all socket events for debugging
    socket.ev.process(async (events) => {
        log('[EVENTS] Received events: ' + Object.keys(events).join(', '));
    });

    socket.ev.on('creds.update', () => {
        log('[INFO] Credentials updated');
        saveCreds();
    });

    socket.ev.on('connection.update', async (update) => {
        log('[CONN] connection.update: ' + JSON.stringify(update));
        const { connection, lastDisconnect, qr } = update;

        if (qr) {
            status = 'qr';
            qrCode = qr;
            log('[INFO] QR code generated');
            events.emit('qr', qr);
            events.emit('status', { status, qr });
        }

        if (connection === 'close') {
            const code = lastDisconnect?.error?.output?.statusCode;
            log('[INFO] Disconnected: ' + code);
            status = 'disconnected';
            events.emit('status', { status });

            if (code !== DisconnectReason?.loggedOut) {
                log('[INFO] Reconnecting in 3 seconds...');
                setTimeout(connectWhatsApp, 3000);
            } else {
                // Clear session
                fs.rmSync(SESSIONS_DIR, { recursive: true, force: true });
                fs.mkdirSync(SESSIONS_DIR, { recursive: true });
            }
        }

        if (connection === 'open') {
            status = 'connected';
            qrCode = '';

            const user = socket.user;
            if (user) {
                phoneNumber = user.id.split('@')[0].split(':')[0];
                pushName = user.name || '';
            }

            log('[INFO] Connected as ' + pushName + ' (' + phoneNumber + ')');
            events.emit('connected', { phoneNumber, pushName });
            events.emit('status', { status, phoneNumber, pushName });
        }
    });

    // Handle incoming messages
    socket.ev.on('messages.upsert', async ({ messages, type }) => {
        if (type !== 'notify') return;

        for (const msg of messages) {
            if (msg.key.fromMe) continue;

            const from = msg.key.remoteJid;
            const text = msg.message?.conversation ||
                         msg.message?.extendedTextMessage?.text ||
                         '';

            log('[MSG] ' + from + ': ' + text.substring(0, 50) + '...');
            events.emit('message', {
                from: from.split('@')[0],
                fromName: msg.pushName,
                text,
                messageId: msg.key.id,
                timestamp: Date.now()
            });
        }
    });
}

// HTTP Server for API and status
const server = http.createServer((req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin', '*');

    const url = new URL(req.url, `http://localhost:${PORT}`);

    if (url.pathname === '/status') {
        res.end(JSON.stringify({
            status,
            phone: phoneNumber,
            name: pushName,
            hasQr: !!qrCode
        }));
        return;
    }

    if (url.pathname === '/qr') {
        res.end(JSON.stringify({
            success: !!qrCode,
            qr: qrCode
        }));
        return;
    }

    if (url.pathname === '/connect' && req.method === 'POST') {
        if (status === 'disconnected') {
            connectWhatsApp().catch(err => {
                log('[ERROR] Connection failed: ' + err.message);
            });
        }
        res.end(JSON.stringify({ success: true, message: 'Connecting...' }));
        return;
    }

    // Pairing code endpoint - connect by phone number instead of QR
    if (url.pathname === '/pair' && req.method === 'POST') {
        let body = '';
        req.on('data', chunk => body += chunk);
        req.on('end', async () => {
            try {
                const { phone } = JSON.parse(body);
                if (!phone) {
                    res.statusCode = 400;
                    res.end(JSON.stringify({ error: 'Phone number required' }));
                    return;
                }

                log('[PAIR] Requesting pairing code for: ' + phone);

                if (!socket) {
                    await connectWhatsApp();
                }

                // Request pairing code
                const code = await socket.requestPairingCode(phone.replace(/\D/g, ''));
                pairingCode = code;
                pairingPhoneNumber = phone;
                log('[PAIR] Pairing code received: ' + code);

                res.end(JSON.stringify({
                    success: true,
                    code: code,
                    message: 'Enter this code in WhatsApp -> Linked Devices -> Link with phone number'
                }));
            } catch (err) {
                log('[PAIR] Error: ' + err.message);
                res.statusCode = 500;
                res.end(JSON.stringify({ error: err.message }));
            }
        });
        return;
    }

    // Get current pairing code
    if (url.pathname === '/pairing-code') {
        res.end(JSON.stringify({
            success: !!pairingCode,
            code: pairingCode,
            phone: pairingPhoneNumber
        }));
        return;
    }

    if (url.pathname === '/logout' && req.method === 'POST') {
        if (socket) {
            socket.logout().catch(() => {});
            socket = null;
        }
        status = 'disconnected';
        res.end(JSON.stringify({ success: true }));
        return;
    }

    if (url.pathname === '/send' && req.method === 'POST') {
        let body = '';
        req.on('data', chunk => body += chunk);
        req.on('end', async () => {
            try {
                const { to, text } = JSON.parse(body);
                if (!socket || status !== 'connected') {
                    res.statusCode = 503;
                    res.end(JSON.stringify({ error: 'Not connected' }));
                    return;
                }

                const jid = to.includes('@') ? to : `${to.replace(/\\D/g, '')}@s.whatsapp.net`;
                const result = await socket.sendMessage(jid, { text });
                res.end(JSON.stringify({ success: true, messageId: result.key.id }));
            } catch (err) {
                res.statusCode = 500;
                res.end(JSON.stringify({ error: err.message }));
            }
        });
        return;
    }

    // Default: status page
    res.end(JSON.stringify({
        service: 'Eldoleado WhatsApp Bridge',
        status,
        endpoints: ['/status', '/qr', '/connect', '/logout', '/send']
    }));
});

log('[STARTUP] Creating HTTP server...');

// Test DNS resolution
const dns = require('dns');
log('[DNS] Testing DNS resolution...');
dns.lookup('web.whatsapp.com', (err, address) => {
    if (err) {
        log('[DNS] FAILED: ' + err.message);
    } else {
        log('[DNS] web.whatsapp.com = ' + address);
    }
});
dns.lookup('google.com', (err, address) => {
    if (err) {
        log('[DNS] google.com FAILED: ' + err.message);
    } else {
        log('[DNS] google.com = ' + address);
    }
});

// Test WebSocket connectivity
const https = require('https');
log('[TEST] Testing HTTPS connection to WhatsApp...');
const testReq = https.get('https://web.whatsapp.com', { timeout: 10000 }, (res) => {
    log('[TEST] HTTPS response: ' + res.statusCode);
});
testReq.on('error', (e) => {
    log('[TEST] HTTPS error: ' + e.message);
});
testReq.on('timeout', () => {
    log('[TEST] HTTPS timeout');
    testReq.destroy();
});

server.listen(PORT, '127.0.0.1', () => {
    log('[INFO] WhatsApp Bridge running on http://127.0.0.1:' + PORT);
    log('[INFO] Data directory: ' + DATA_DIR);

    // Auto-connect on start
    connectWhatsApp().catch(err => {
        log('[ERROR] Initial connection failed: ' + err.message);
        log('[ERROR] Stack: ' + err.stack);
    });
});

server.on('error', (err) => {
    log('[ERROR] Server error: ' + err.message);
});

// Handle errors
process.on('uncaughtException', (err) => {
    log('[FATAL] Uncaught exception: ' + err.message);
    log('[FATAL] Stack: ' + err.stack);
});

process.on('unhandledRejection', (err) => {
    log('[FATAL] Unhandled rejection: ' + (err && err.message ? err.message : err));
});
