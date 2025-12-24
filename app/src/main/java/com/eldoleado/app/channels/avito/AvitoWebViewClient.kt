package com.eldoleado.app.channels.avito

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import javax.net.ssl.HttpsURLConnection

/**
 * Avito WebView-based Client
 * Uses WebView to maintain WebSocket connection (same TLS fingerprint as browser)
 * Intercepts messages via JavaScript and forwards to n8n
 */
class AvitoWebViewClient(
    private val context: Context,
    private val cookies: String,
    private val tenantId: String,
    private val channelAccountId: String,
    private val webhookUrl: String = "https://n8n.n8nsrv.ru/webhook/avito/incoming"
) {
    companion object {
        private const val TAG = "AvitoWebView"
        private const val MESSENGER_URL = "https://m.avito.ru/profile/messenger"
        private const val RECONNECT_DELAY = 30000L
    }

    private var webView: WebView? = null
    private var isConnected = false
    private var shouldReconnect = true
    private var reconnectJob: Job? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Cache for channel names: channelId -> contactName (empty string means "fetched but no name")
    private val channelNameCache = ConcurrentHashMap<String, String>()

    interface Listener {
        fun onConnected()
        fun onDisconnected(reason: String)
        fun onMessage(message: AvitoMessage)
        fun onError(error: String)
    }

    var listener: Listener? = null

    /**
     * Start WebView and load messenger page
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun connect() {
        shouldReconnect = true
        mainHandler.post {
            try {
                // Set cookies before creating WebView
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)

                // Split and set each cookie
                cookies.split(";").forEach { cookie ->
                    val trimmed = cookie.trim()
                    if (trimmed.isNotEmpty()) {
                        cookieManager.setCookie("avito.ru", trimmed)
                        cookieManager.setCookie("m.avito.ru", trimmed)
                    }
                }
                cookieManager.flush()

                webView = WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

                    addJavascriptInterface(WebSocketInterceptor(), "AvitoAndroid")

                    // Chrome client for console logs
                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                            val msg = consoleMessage?.message() ?: return true
                            val level = consoleMessage.messageLevel()
                            if (msg.contains("Avito") || msg.contains("WebSocket") || msg.contains("socket")) {
                                Log.d(TAG, "JS Console [$level]: $msg")
                            }
                            return true
                        }
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            Log.d(TAG, "Page starting: $url")
                            // Inject interceptor EARLY - before page scripts run
                            if (url?.contains("avito.ru") == true) {
                                injectInterceptor(view)
                            }
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            Log.d(TAG, "Page loaded: $url")

                            if (url?.contains("/messenger") == true) {
                                // Re-inject to be safe
                                injectInterceptor(view)
                            }
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            errorCode: Int,
                            description: String?,
                            failingUrl: String?
                        ) {
                            Log.e(TAG, "WebView error: $errorCode $description")
                            listener?.onError("WebView error: $description")
                            scheduleReconnect()
                        }
                    }

                    loadUrl(MESSENGER_URL)
                }

                Log.i(TAG, "WebView created and loading messenger")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to create WebView: ${e.message}")
                listener?.onError(e.message ?: "WebView creation failed")
            }
        }
    }

    private fun injectInterceptor(view: WebView?) {
        val js = """
            (function() {
                if (window.__avitoIntercepted) return;
                window.__avitoIntercepted = true;

                console.log('AvitoWebView: Installing interceptor...');

                // Store all WebSockets for debugging
                window.__avitoWebSockets = [];

                var OriginalWebSocket = window.WebSocket;
                window.WebSocket = function(url, protocols) {
                    console.log('AvitoWebView: NEW WebSocket: ' + url);

                    var ws = protocols ? new OriginalWebSocket(url, protocols) : new OriginalWebSocket(url);
                    window.__avitoWebSockets.push({ws: ws, url: url});

                    ws.addEventListener('open', function() {
                        console.log('AvitoWebView: WebSocket OPEN: ' + url);
                        try { AvitoAndroid.onConnected(); } catch(e) { console.log('AvitoWebView: onConnected error: ' + e); }
                    });

                    ws.addEventListener('message', function(event) {
                        console.log('AvitoWebView: WS MESSAGE received, length=' + (event.data ? event.data.length : 0));
                        try {
                            AvitoAndroid.onMessage(event.data);
                        } catch(e) {
                            console.log('AvitoWebView: onMessage error: ' + e);
                        }
                    });

                    ws.addEventListener('close', function(event) {
                        console.log('AvitoWebView: WebSocket CLOSED: ' + event.code + ' ' + event.reason);
                        try { AvitoAndroid.onDisconnected(event.code + ': ' + event.reason); } catch(e) {}
                    });

                    ws.addEventListener('error', function(event) {
                        console.log('AvitoWebView: WebSocket ERROR');
                        try { AvitoAndroid.onError('WebSocket error'); } catch(e) {}
                    });

                    return ws;
                };
                window.WebSocket.prototype = OriginalWebSocket.prototype;
                window.WebSocket.CONNECTING = OriginalWebSocket.CONNECTING;
                window.WebSocket.OPEN = OriginalWebSocket.OPEN;
                window.WebSocket.CLOSING = OriginalWebSocket.CLOSING;
                window.WebSocket.CLOSED = OriginalWebSocket.CLOSED;

                console.log('AvitoWebView: Interceptor installed successfully');
                try { AvitoAndroid.onInterceptorReady(); } catch(e) {}
            })();
        """.trimIndent()

        view?.evaluateJavascript(js, null)
        Log.d(TAG, "Interceptor JS injected")
    }

    /**
     * Stop WebView
     */
    fun disconnect() {
        shouldReconnect = false
        reconnectJob?.cancel()
        mainHandler.post {
            webView?.destroy()
            webView = null
        }
        isConnected = false
    }

    private fun scheduleReconnect() {
        if (!shouldReconnect) return

        reconnectJob?.cancel()
        reconnectJob = scope.launch {
            Log.i(TAG, "Reconnecting in ${RECONNECT_DELAY}ms...")
            delay(RECONNECT_DELAY)
            withContext(Dispatchers.Main) {
                webView?.reload() ?: connect()
            }
        }
    }

    fun isConnected() = isConnected

    inner class WebSocketInterceptor {
        @JavascriptInterface
        fun onInterceptorReady() {
            Log.i(TAG, "WebSocket interceptor ready")
        }

        @JavascriptInterface
        fun onConnected() {
            Log.i(TAG, "WebSocket connected via WebView")
            isConnected = true
            listener?.onConnected()
        }

        @JavascriptInterface
        fun onDisconnected(reason: String) {
            Log.i(TAG, "WebSocket disconnected: $reason")
            // Only reconnect if we were actually connected (not a failed parallel socket)
            if (isConnected) {
                isConnected = false
                listener?.onDisconnected(reason)
                scheduleReconnect()
            } else {
                Log.d(TAG, "Ignoring disconnect from non-primary socket")
            }
        }

        @JavascriptInterface
        fun onError(error: String) {
            Log.e(TAG, "WebSocket error: $error")
            listener?.onError(error)
        }

        @JavascriptInterface
        fun onMessage(data: String) {
            Log.d(TAG, "RAW WS message (${data.length} chars): ${data.take(300)}")
            try {
                val json = JSONObject(data)
                handleMessage(json)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to parse message: ${e.message}")
            }
        }

        @JavascriptInterface
        fun onContactNameFetched(channelId: String, name: String) {
            Log.d(TAG, "onContactNameFetched: channelId=$channelId, name=$name")
            try {
                val callback = pendingContactNameCallbacks.remove(channelId)
                if (callback != null) {
                    Log.d(TAG, "Invoking callback for channel $channelId")
                    callback.invoke(name)
                    Log.d(TAG, "Callback completed for channel $channelId")
                } else {
                    Log.w(TAG, "No callback found for channel $channelId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in onContactNameFetched callback: ${e.javaClass.simpleName}: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun handleMessage(json: JSONObject) {
        val msgType = json.optString("type")
        val msgTypeV2 = json.optString("type_v2", "")

        Log.d(TAG, "handleMessage: type=$msgType, type_v2=$msgTypeV2")

        // Skip internal messages (but log them)
        if (json.has("jsonrpc")) {
            Log.d(TAG, "Skipping jsonrpc message")
            return
        }
        if (json.has("result")) {
            Log.d(TAG, "Skipping result message")
            return
        }
        if (json.has("error")) {
            Log.d(TAG, "Skipping error message")
            return
        }

        // Handle session init
        if (msgType == "session") {
            val value = json.optJSONObject("value")
            Log.i(TAG, "Session init, userId=${value?.optString("userId")}")
            return
        }

        // Handle new message - check multiple possible types
        val isMessage = msgType == "Message" ||
                        msgType == "message" ||
                        msgType == "notify" ||
                        msgTypeV2 == "messenger.Message" ||
                        msgTypeV2.contains("Message", ignoreCase = true) ||
                        json.has("value") && json.optJSONObject("value")?.has("body") == true

        if (isMessage) {
            val value = json.optJSONObject("value")
            if (value == null) {
                Log.w(TAG, "Message has no value object")
                return
            }

            val body = value.optJSONObject("body")
            val text = body?.optString("text") ?: value.optString("text")

            Log.i(TAG, "=== NEW MESSAGE ===")
            Log.i(TAG, "  id: ${value.optString("id")}")
            Log.i(TAG, "  channelId: ${value.optString("channelId")}")
            Log.i(TAG, "  fromUid: ${value.optString("fromUid")}")
            Log.i(TAG, "  text: $text")
            Log.i(TAG, "==================")

            val message = AvitoMessage(
                id = value.optString("id"),
                channelId = value.optString("channelId"),
                fromUid = value.optString("fromUid"),
                messageType = value.optString("type", "text"),
                text = text,
                created = value.optLong("created"),
                raw = json
            )

            listener?.onMessage(message)

            // Forward to n8n
            scope.launch {
                forwardToN8n(json)
            }
            return
        }

        Log.d(TAG, "Unhandled message type: $msgType / $msgTypeV2")
    }

    /**
     * Fetch contact name from Avito API via WebView JavaScript
     * Uses getChannelById which returns channel.info.name (contact name)
     * Uses fetch() from WebView context which has authenticated session
     * @param channelId - channel ID to get contact name from
     * @return contact name or null if not found
     */
    private suspend fun fetchContactName(channelId: String): String? {
        // Check cache first (empty string means "fetched but no name")
        val cached = channelNameCache[channelId]
        if (cached != null) {
            return cached.ifEmpty { null }
        }

        if (channelId.isEmpty()) {
            Log.w(TAG, "fetchContactName: channelId is empty")
            return null
        }

        Log.d(TAG, "Fetching contact name for channel: $channelId via WebView JS")

        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine { continuation ->
                // Use getChannels API and extract name from users[] array
                val js = """
                    (function() {
                        var called = false;
                        fetch('https://m.avito.ru/web/1/messenger/getChannels', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify({
                                category: 1,
                                limit: 50
                            }),
                            credentials: 'include'
                        })
                        .then(function(r) { return r.json(); })
                        .then(function(data) {
                            if (called) return;
                            called = true;
                            var name = '';
                            if (data.success && data.success.channels) {
                                for (var i = 0; i < data.success.channels.length; i++) {
                                    var ch = data.success.channels[i];
                                    if (ch.id === '$channelId') {
                                        console.log('AvitoWebView: Found channel:', JSON.stringify(ch).substring(0, 500));
                                        // Try to find other user's name in users array
                                        var myUserId = ch.userId;
                                        if (ch.users && ch.users.length > 0) {
                                            for (var j = 0; j < ch.users.length; j++) {
                                                var user = ch.users[j];
                                                if (user.id !== myUserId && user.name) {
                                                    name = user.name;
                                                    console.log('AvitoWebView: Found user name:', name);
                                                    break;
                                                }
                                            }
                                        }
                                        // Fallback to info.name
                                        if (!name && ch.info && ch.info.name) {
                                            name = ch.info.name;
                                        }
                                        break;
                                    }
                                }
                            }
                            if (!name) {
                                console.log('AvitoWebView: Channel not found in list or no name, got ' + (data.success ? data.success.channels.length : 0) + ' channels');
                            }
                            try {
                                AvitoAndroid.onContactNameFetched('$channelId', name);
                            } catch(e2) {
                                console.log('AvitoWebView: callback error:', e2);
                            }
                        })
                        .catch(function(e) {
                            if (called) return;
                            called = true;
                            console.log('AvitoWebView: getChannels error:', e);
                            try {
                                AvitoAndroid.onContactNameFetched('$channelId', '');
                            } catch(e2) {}
                        });
                    })();
                """.trimIndent()

                // Set up timeout
                val timeoutJob = scope.launch {
                    delay(5000)
                    if (continuation.isActive) {
                        Log.w(TAG, "fetchContactName timeout for $channelId")
                        channelNameCache[channelId] = ""
                        pendingContactNameCallbacks.remove(channelId)
                        continuation.resume(null) {}
                    }
                }

                // Store callback for JS interface
                pendingContactNameCallbacks[channelId] = { name ->
                    Log.d(TAG, "Callback lambda for channel $channelId, name='$name', cancelling timeout")
                    try {
                        timeoutJob.cancel()
                        val result = if (name.isNotEmpty()) name else null
                        // Cache: empty string means "fetched but no name"
                        channelNameCache[channelId] = name.ifEmpty { "" }
                        if (result != null) {
                            Log.i(TAG, "Got contact name via JS: $result for channel $channelId")
                        } else {
                            Log.d(TAG, "No contact name for channel $channelId")
                        }
                        // Resume on Main thread to avoid thread issues
                        mainHandler.post {
                            if (continuation.isActive) {
                                continuation.resume(result) {}
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error in callback lambda: ${e.javaClass.simpleName}: ${e.message}")
                        e.printStackTrace()
                    }
                }

                webView?.evaluateJavascript(js, null)
            }
        }
    }

    // Callbacks for async JS results - thread-safe
    private val pendingContactNameCallbacks = ConcurrentHashMap<String, (String) -> Unit>()

    private suspend fun forwardToN8n(messageData: JSONObject) {
        try {
            val value = messageData.optJSONObject("value") ?: JSONObject()
            val body = value.optJSONObject("body") ?: JSONObject()

            val createdNs = value.optLong("created", 0)
            val createdAt = if (createdNs > 0) {
                java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US)
                    .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                    .format(java.util.Date(createdNs / 1_000_000))
            } else null

            // Fetch sender name from Avito API via getChannelById (cached)
            val channelId = value.optString("channelId")
            val senderId = value.optString("fromUid")
            val senderName = try {
                if (channelId.isNotEmpty()) {
                    fetchContactName(channelId)
                } else null
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch contact name: ${e.message}")
                null
            }

            // n8n webhook puts POST body into $json.body automatically
            val payload = JSONObject().apply {
                put("channel_account_id", channelAccountId)
                put("tenant_id", tenantId)
                put("channel_type", "avito")
                put("external_chat_id", value.optString("channelId"))
                put("external_message_id", value.optString("id"))
                put("message_type", value.optString("type", "text"))
                put("message_text", body.optString("text"))
                put("sender_id", senderId)
                put("sender_name", senderName)  // User name from Avito API
                put("message_date", createdAt)
                put("received_at", java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US)
                    .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                    .format(java.util.Date()))
                put("source", "android_webview")
                put("raw_message", messageData)
            }

            val url = URL(webhookUrl)
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            connection.outputStream.bufferedWriter().use { it.write(payload.toString()) }

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                Log.i(TAG, "Forwarded message to n8n")
            } else {
                Log.w(TAG, "n8n returned $responseCode")
            }
            connection.disconnect()

        } catch (e: Exception) {
            Log.e(TAG, "Failed to forward to n8n: ${e.message}")
        }
    }
}
