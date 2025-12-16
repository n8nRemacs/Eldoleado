/**
 * WhatsApp Channel - Baileys
 * Port: 3001
 */

const express = require('express');
const {
    default: makeWASocket,
    useMultiFileAuthState,
    DisconnectReason,
    fetchLatestBaileysVersion
} = require('@whiskeysockets/baileys');
const pino = require('pino');
const qrcode = require('qrcode-terminal');
const axios = require('axios');
const path = require('path');

const PORT = process.env.PORT || 3001;
const WEBHOOK_URL = process.env.WEBHOOK_URL || 'http://localhost:8000/webhook';
const AUTH_DIR = process.env.AUTH_DIR || './auth_info';

const app = express();
app.use(express.json());

let sock = null;
let qrCode = null;
let connectionState = 'disconnected';

const logger = pino({ level: 'warn' });

// === Baileys Connection ===

async function connectWhatsApp() {
    const { state, saveCreds } = await useMultiFileAuthState(AUTH_DIR);
    const { version } = await fetchLatestBaileysVersion();

    sock = makeWASocket({
        version,
        logger,
        printQRInTerminal: true,
        auth: state,
        browser: ['Eldoleado', 'Chrome', '120.0.0']
    });

    // Connection events
    sock.ev.on('connection.update', async (update) => {
        const { connection, lastDisconnect, qr } = update;

        if (qr) {
            qrCode = qr;
            qrcode.generate(qr, { small: true });
            console.log('QR Code generated. Scan with WhatsApp.');
        }

        if (connection === 'close') {
            connectionState = 'disconnected';
            const statusCode = lastDisconnect?.error?.output?.statusCode;
            const shouldReconnect = statusCode !== DisconnectReason.loggedOut;

            console.log('Connection closed. Reconnect:', shouldReconnect);

            if (shouldReconnect) {
                setTimeout(connectWhatsApp, 5000);
            }
        } else if (connection === 'open') {
            connectionState = 'connected';
            qrCode = null;
            console.log('WhatsApp connected!');
        }
    });

    // Save credentials on update
    sock.ev.on('creds.update', saveCreds);

    // Incoming messages
    sock.ev.on('messages.upsert', async ({ messages, type }) => {
        if (type !== 'notify') return;

        for (const msg of messages) {
            if (msg.key.fromMe) continue;

            const chatId = msg.key.remoteJid;
            const messageId = msg.key.id;
            const text = msg.message?.conversation ||
                        msg.message?.extendedTextMessage?.text ||
                        '';
            const pushName = msg.pushName || '';

            // Determine media type
            let mediaType = null;
            let mediaUrl = null;

            if (msg.message?.imageMessage) {
                mediaType = 'image';
            } else if (msg.message?.videoMessage) {
                mediaType = 'video';
            } else if (msg.message?.audioMessage) {
                mediaType = 'audio';
            } else if (msg.message?.documentMessage) {
                mediaType = 'document';
            }

            // Forward to webhook
            try {
                await axios.post(WEBHOOK_URL, {
                    service: 'whatsapp',
                    event: 'message',
                    data: {
                        id: messageId,
                        chat_id: chatId,
                        from_name: pushName,
                        text: text,
                        media_type: mediaType,
                        timestamp: msg.messageTimestamp
                    }
                });
            } catch (err) {
                console.error('Webhook error:', err.message);
            }
        }
    });
}

// === Express Routes ===

app.get('/health', (req, res) => {
    res.json({
        status: 'ok',
        connected: connectionState === 'connected',
        state: connectionState
    });
});

app.get('/qr', (req, res) => {
    if (qrCode) {
        res.json({ qr: qrCode });
    } else if (connectionState === 'connected') {
        res.json({ message: 'Already connected' });
    } else {
        res.json({ message: 'No QR code available' });
    }
});

app.get('/dialogs', async (req, res) => {
    if (!sock || connectionState !== 'connected') {
        return res.status(503).json({ error: 'Not connected' });
    }

    try {
        const chats = await sock.groupFetchAllParticipating();
        const dialogs = Object.entries(chats).map(([id, chat]) => ({
            id: id,
            title: chat.subject || id,
            type: id.endsWith('@g.us') ? 'group' : 'private'
        }));
        res.json(dialogs);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/send', async (req, res) => {
    if (!sock || connectionState !== 'connected') {
        return res.status(503).json({ error: 'Not connected' });
    }

    const { chat_id, text, reply_to } = req.body;

    if (!chat_id || !text) {
        return res.status(400).json({ error: 'chat_id and text required' });
    }

    try {
        const result = await sock.sendMessage(chat_id, { text }, {
            quoted: reply_to ? { key: { id: reply_to } } : undefined
        });
        res.json({
            success: true,
            message_id: result.key.id
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/send/image', async (req, res) => {
    if (!sock || connectionState !== 'connected') {
        return res.status(503).json({ error: 'Not connected' });
    }

    const { chat_id, url, caption } = req.body;

    try {
        const result = await sock.sendMessage(chat_id, {
            image: { url },
            caption: caption || ''
        });
        res.json({ success: true, message_id: result.key.id });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/send/document', async (req, res) => {
    if (!sock || connectionState !== 'connected') {
        return res.status(503).json({ error: 'Not connected' });
    }

    const { chat_id, url, filename, caption } = req.body;

    try {
        const result = await sock.sendMessage(chat_id, {
            document: { url },
            fileName: filename || 'document',
            caption: caption || ''
        });
        res.json({ success: true, message_id: result.key.id });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/send/audio', async (req, res) => {
    if (!sock || connectionState !== 'connected') {
        return res.status(503).json({ error: 'Not connected' });
    }

    const { chat_id, url, ptt } = req.body;

    try {
        const result = await sock.sendMessage(chat_id, {
            audio: { url },
            ptt: ptt || false  // voice message if true
        });
        res.json({ success: true, message_id: result.key.id });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Start server
app.listen(PORT, () => {
    console.log(`WhatsApp channel listening on port ${PORT}`);
    connectWhatsApp();
});
