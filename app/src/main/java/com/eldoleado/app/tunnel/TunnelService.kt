package com.eldoleado.app.tunnel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Base64
import android.util.Log
import androidx.core.app.NotificationCompat
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import com.eldoleado.app.SessionManager
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

/**
 * TunnelService - HTTP Proxy through Mobile IP.
 *
 * Receives HTTP request commands from server via WebSocket,
 * executes them locally using OkHttp (with mobile IP and Android TLS fingerprint),
 * returns responses back to server.
 *
 * This ensures:
 * - Mobile IP address (not datacenter)
 * - Android/OkHttp TLS fingerprint (not Python/bot)
 * - Real device characteristics
 */
class TunnelService : Service() {

    companion object {
        private const val TAG = "TunnelService"
        private const val NOTIFICATION_ID = 2001
        private const val CHANNEL_ID = "tunnel_service_channel"
        private const val STATUS_UPDATE_INTERVAL_MS = 60_000L // 1 minute

        const val ACTION_START = "com.eldoleado.app.tunnel.START"
        const val ACTION_STOP = "com.eldoleado.app.tunnel.STOP"

        fun start(context: Context) {
            val intent = Intent(context, TunnelService::class.java).apply {
                action = ACTION_START
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, TunnelService::class.java).apply {
                action = ACTION_STOP
            }
            context.startService(intent)
        }
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var webSocket: WebSocket? = null
    private var isConnected = false
    private var reconnectJob: Job? = null
    private var statusUpdateJob: Job? = null
    private val requestCounter = AtomicLong(0)
    private val activeRequests = AtomicLong(0)

    // TCP Tunnels: tunnelId -> TcpTunnel
    private val tcpTunnels = ConcurrentHashMap<String, TcpTunnel>()

    private data class TcpTunnel(
        val tunnelId: String,
        val socket: Socket,
        val inputStream: InputStream,
        val outputStream: OutputStream,
        var readJob: Job? = null
    )

    private lateinit var sessionManager: SessionManager
    private lateinit var httpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)

        // HTTP client optimized for reliability
        httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            // Connection pool for performance
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .build()

        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                startForeground(NOTIFICATION_ID, createNotification("Подключение..."))
                connect()
            }
            ACTION_STOP -> {
                disconnect()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        serviceScope.cancel()
        httpClient.dispatcher.executorService.shutdown()
        httpClient.connectionPool.evictAll()
    }

    private fun connect() {
        var tunnelUrl = sessionManager.getTunnelUrl()
        val tunnelSecret = sessionManager.getTunnelSecret()

        // Fallback to default tunnel server if URL not configured or invalid
        if (tunnelUrl.isNullOrBlank() || tunnelUrl.contains("eldoleado.ru")) {
            tunnelUrl = "ws://155.212.221.189:8800/ws"
            Log.i(TAG, "Using default tunnel URL: $tunnelUrl")
        }

        // Generate server_id from device info
        val serverId = "${Build.MODEL}_${Build.ID}".replace(" ", "_")

        // Build WebSocket URL with auth params (matches tunnel-server protocol)
        val wsUrl = buildString {
            append(tunnelUrl)
            append(if (tunnelUrl.contains("?")) "&" else "?")
            append("server_id=$serverId")
            append("&secret=$tunnelSecret")
        }

        val request = Request.Builder()
            .url(wsUrl)
            .header("X-Device-Id", Build.ID)
            .header("X-Device-Model", Build.MODEL)
            .build()

        webSocket = httpClient.newWebSocket(request, TunnelWebSocketListener())
        Log.i(TAG, "Connecting to tunnel server as $serverId...")
    }

    private fun disconnect() {
        reconnectJob?.cancel()
        statusUpdateJob?.cancel()

        // Close all TCP tunnels
        for (tunnelId in tcpTunnels.keys.toList()) {
            closeTcpTunnel(tunnelId, notifyServer = false)
        }

        webSocket?.close(1000, "Service stopped")
        webSocket = null
        isConnected = false
    }

    private fun scheduleReconnect(delayMs: Long = 5000) {
        reconnectJob?.cancel()
        reconnectJob = serviceScope.launch {
            delay(delayMs)
            if (!isConnected) {
                Log.i(TAG, "Reconnecting...")
                connect()
            }
        }
    }

    private inner class TunnelWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.i(TAG, "Connected to tunnel server")
            isConnected = true
            updateNotification("Подключено ✓")

            // Send hello message with proxy settings
            sendHello()

            // Start periodic status updates
            startStatusUpdates()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            serviceScope.launch {
                handleMessage(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            // Binary message - TCP tunnel data
            handleTcpData(bytes.toByteArray())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.i(TAG, "WebSocket closing: $code $reason")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.i(TAG, "WebSocket closed: $code $reason")
            isConnected = false
            updateNotification("Отключено")
            scheduleReconnect()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket error: ${t.message}")
            isConnected = false
            updateNotification("Ошибка подключения")
            // Exponential backoff
            scheduleReconnect(10000)
        }
    }

    /**
     * Send hello message with proxy settings for auto-registration.
     */
    private fun sendHello() {
        val serverId = "${Build.MODEL}_${Build.ID}".replace(" ", "_")
        val tenantId = sessionManager.getTenantId() ?: ""
        val nodeType = if (sessionManager.getAppMode() == SessionManager.MODE_CLIENT) "client" else "operator"

        val hello = JSONObject().apply {
            put("action", "hello")
            put("server_id", serverId)
            put("services", JSONArray(listOf("http_proxy", "tcp_tunnel")))  // We support http_proxy and tcp_tunnel
            put("tenant_id", tenantId)
            put("node_type", nodeType)
            put("wifi_only", true)  // Only use on WiFi by default
            put("max_requests_per_hour", 10)
            put("device_id", Build.ID)
            put("device_model", Build.MODEL)
            put("manufacturer", Build.MANUFACTURER)
            put("android_version", Build.VERSION.RELEASE)
            put("timestamp", System.currentTimeMillis())
        }
        webSocket?.send(hello.toString())
        Log.i(TAG, "Sent hello: tenant=$tenantId, type=$nodeType, server_id=$serverId")
    }

    /**
     * Start periodic status updates.
     */
    private fun startStatusUpdates() {
        statusUpdateJob?.cancel()
        statusUpdateJob = serviceScope.launch {
            while (isConnected) {
                delay(STATUS_UPDATE_INTERVAL_MS)
                if (isConnected) {
                    sendProxyStatus()
                }
            }
        }
    }

    /**
     * Send proxy_status with WiFi and battery info.
     */
    private fun sendProxyStatus() {
        val isWifi = isOnWifi()
        val batteryLevel = getBatteryLevel()

        val status = JSONObject().apply {
            put("action", "proxy_status")
            put("server_id", "${Build.MODEL}_${Build.ID}".replace(" ", "_"))
            put("is_wifi", isWifi)
            put("battery_level", batteryLevel)
            put("timestamp", System.currentTimeMillis())
        }
        webSocket?.send(status.toString())
        Log.d(TAG, "Sent proxy_status: wifi=$isWifi, battery=$batteryLevel")
    }

    /**
     * Check if device is on WiFi.
     */
    private fun isOnWifi(): Boolean {
        return try {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) == true
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.type == android.net.ConnectivityManager.TYPE_WIFI
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking WiFi: ${e.message}")
            true // Assume WiFi if can't check
        }
    }

    /**
     * Get battery level percentage.
     */
    private fun getBatteryLevel(): Int {
        return try {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
            batteryManager.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting battery: ${e.message}")
            100 // Assume full if can't check
        }
    }

    /**
     * Handle incoming message from server.
     *
     * HTTP Request format:
     * {
     *   "id": "uuid",
     *   "action": "http",
     *   "method": "GET|POST|PUT|DELETE|PATCH",
     *   "url": "https://api.avito.ru/...",
     *   "headers": {"Authorization": "Bearer ..."},
     *   "body": "string or base64",
     *   "body_base64": false,
     *   "timeout": 30
     * }
     */
    private suspend fun handleMessage(text: String) {
        try {
            val json = JSONObject(text)
            val id = json.optString("id", "")
            val action = json.optString("action", "")

            when (action) {
                "http", "http_request" -> handleHttpRequest(id, json)
                "proxy_fetch" -> handleProxyFetch(id, json)
                "tcp_connect" -> handleTcpConnect(json)
                "tcp_close" -> handleTcpClose(json)
                "ping" -> sendPong(id)
                "status" -> sendStatus(id)
                else -> {
                    if (id.isNotEmpty()) {
                        sendError(id, "Unknown action: $action", 400)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing message: ${e.message}")
        }
    }

    /**
     * Execute HTTP request through mobile network.
     * This is the core function - executes request with Android TLS fingerprint.
     */
    private suspend fun handleHttpRequest(id: String, json: JSONObject) {
        val requestNum = requestCounter.incrementAndGet()
        activeRequests.incrementAndGet()

        val method = json.optString("method", "GET").uppercase()
        val url = json.optString("url", "")
        val headersJson = json.optJSONObject("headers")
        val bodyStr = json.optString("body", "")
        val bodyBase64 = json.optBoolean("body_base64", false)
        val timeout = json.optInt("timeout", 30)

        if (url.isEmpty()) {
            sendError(id, "Missing URL", 400)
            activeRequests.decrementAndGet()
            return
        }

        Log.d(TAG, "[$requestNum] $method $url")
        updateNotification("Запросов: ${activeRequests.get()}")

        try {
            // Build request
            val requestBuilder = Request.Builder().url(url)

            // Add headers from server
            headersJson?.keys()?.forEach { key ->
                val value = headersJson.getString(key)
                // Skip headers that OkHttp sets automatically
                if (!key.equals("Content-Length", ignoreCase = true) &&
                    !key.equals("Host", ignoreCase = true)) {
                    requestBuilder.addHeader(key, value)
                }
            }

            // Prepare body
            val requestBody: RequestBody? = when {
                bodyStr.isEmpty() -> null
                bodyBase64 -> {
                    val bytes = Base64.decode(bodyStr, Base64.DEFAULT)
                    val contentType = headersJson?.optString("Content-Type", "application/octet-stream")
                        ?: "application/octet-stream"
                    bytes.toRequestBody(contentType.toMediaType())
                }
                else -> {
                    val contentType = headersJson?.optString("Content-Type", "application/json")
                        ?: "application/json"
                    bodyStr.toRequestBody(contentType.toMediaType())
                }
            }

            // Set method
            when (method) {
                "GET" -> requestBuilder.get()
                "HEAD" -> requestBuilder.head()
                "POST" -> requestBuilder.post(requestBody ?: "".toRequestBody(null))
                "PUT" -> requestBuilder.put(requestBody ?: "".toRequestBody(null))
                "DELETE" -> if (requestBody != null) requestBuilder.delete(requestBody) else requestBuilder.delete()
                "PATCH" -> requestBuilder.patch(requestBody ?: "".toRequestBody(null))
                else -> requestBuilder.method(method, requestBody)
            }

            // Create client with custom timeout if needed
            val client = if (timeout != 30) {
                httpClient.newBuilder()
                    .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .build()
            } else {
                httpClient
            }

            // Execute request
            val request = requestBuilder.build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.bytes() ?: ByteArray(0)

                    // Check if response is text or binary
                    val contentType = response.header("Content-Type", "") ?: ""
                    val isText = contentType.contains("text") ||
                                 contentType.contains("json") ||
                                 contentType.contains("xml") ||
                                 contentType.contains("javascript")

                    // Build response headers
                    val responseHeaders = JSONObject()
                    response.headers.forEach { (name, value) ->
                        responseHeaders.put(name, value)
                    }

                    // Build response
                    val responseJson = JSONObject().apply {
                        put("id", id)
                        put("status", response.code)
                        put("headers", responseHeaders)
                        if (isText && responseBody.isNotEmpty()) {
                            put("body", String(responseBody, Charsets.UTF_8))
                            put("body_base64", false)
                        } else if (responseBody.isNotEmpty()) {
                            put("body", Base64.encodeToString(responseBody, Base64.NO_WRAP))
                            put("body_base64", true)
                        } else {
                            put("body", "")
                            put("body_base64", false)
                        }
                    }

                    Log.d(TAG, "[$requestNum] Response: ${response.code}")
                    sendResponse(responseJson.toString())
                }
            }

        } catch (e: IOException) {
            Log.e(TAG, "[$requestNum] Network error: ${e.message}")
            sendError(id, "Network error: ${e.message}", 502)
        } catch (e: Exception) {
            Log.e(TAG, "[$requestNum] Error: ${e.message}")
            sendError(id, e.message ?: "Unknown error", 500)
        } finally {
            activeRequests.decrementAndGet()
            updateNotification(if (isConnected) "Подключено ✓" else "Отключено")
        }
    }

    /**
     * Handle proxy_fetch request - direct URL fetch via mobile IP.
     * Used for price scraping and external API requests.
     *
     * Format from body field:
     * {
     *   "url": "https://example.com",
     *   "method": "GET",
     *   "headers": {},
     *   "body": null,
     *   "timeout": 30
     * }
     */
    private suspend fun handleProxyFetch(id: String, json: JSONObject) {
        val requestNum = requestCounter.incrementAndGet()
        activeRequests.incrementAndGet()

        // proxy_fetch has request details in "body" field
        val bodyObj = json.optJSONObject("body") ?: json
        val url = bodyObj.optString("url", "")
        val method = bodyObj.optString("method", "GET").uppercase()
        val headersJson = bodyObj.optJSONObject("headers")
        val requestBody = bodyObj.optString("body", "")
        val timeout = bodyObj.optInt("timeout", 30)

        if (url.isEmpty()) {
            sendProxyError(id, "Missing URL")
            activeRequests.decrementAndGet()
            return
        }

        Log.i(TAG, "[$requestNum] Proxy fetch: $method $url")
        updateNotification("Proxy: ${activeRequests.get()}")

        try {
            val requestBuilder = Request.Builder().url(url)

            // Add headers
            headersJson?.keys()?.forEach { key ->
                val value = headersJson.getString(key)
                if (!key.equals("Content-Length", ignoreCase = true) &&
                    !key.equals("Host", ignoreCase = true)) {
                    requestBuilder.addHeader(key, value)
                }
            }

            // Prepare body
            val body: RequestBody? = if (requestBody.isNotEmpty()) {
                val contentType = headersJson?.optString("Content-Type", "application/json")
                    ?: "application/json"
                requestBody.toRequestBody(contentType.toMediaType())
            } else null

            // Set method
            when (method) {
                "GET" -> requestBuilder.get()
                "HEAD" -> requestBuilder.head()
                "POST" -> requestBuilder.post(body ?: "".toRequestBody(null))
                "PUT" -> requestBuilder.put(body ?: "".toRequestBody(null))
                "DELETE" -> if (body != null) requestBuilder.delete(body) else requestBuilder.delete()
                "PATCH" -> requestBuilder.patch(body ?: "".toRequestBody(null))
                else -> requestBuilder.method(method, body)
            }

            // Create client with custom timeout
            val client = httpClient.newBuilder()
                .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .build()

            val request = requestBuilder.build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""

                    // Build response headers
                    val responseHeaders = JSONObject()
                    response.headers.forEach { (name, value) ->
                        responseHeaders.put(name, value)
                    }

                    // Send proxy_response (distinct action for proxy manager)
                    val responseJson = JSONObject().apply {
                        put("id", id)
                        put("action", "proxy_response")
                        put("status", response.code)
                        put("headers", responseHeaders)
                        put("body", responseBody)
                        put("url", response.request.url.toString())
                    }

                    Log.i(TAG, "[$requestNum] Proxy response: ${response.code}")
                    sendResponse(responseJson.toString())
                }
            }

        } catch (e: IOException) {
            Log.e(TAG, "[$requestNum] Proxy network error: ${e.message}")
            sendProxyError(id, "Network error: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "[$requestNum] Proxy error: ${e.message}")
            sendProxyError(id, e.message ?: "Unknown error")
        } finally {
            activeRequests.decrementAndGet()
            updateNotification(if (isConnected) "Подключено ✓" else "Отключено")
        }
    }

    /**
     * Send proxy error response with proxy_response action.
     */
    private fun sendProxyError(id: String, error: String) {
        val response = JSONObject().apply {
            put("id", id)
            put("action", "proxy_response")
            put("status", 500)
            put("error", error)
        }
        sendResponse(response.toString())
    }

    // ============ TCP Tunnel Handlers ============

    /**
     * Handle tcp_connect request - create TCP tunnel to target host.
     *
     * Format:
     * {
     *   "action": "tcp_connect",
     *   "tunnel_id": "uuid",
     *   "host": "web.whatsapp.com",
     *   "port": 443
     * }
     */
    private fun handleTcpConnect(json: JSONObject) {
        val tunnelId = json.optString("tunnel_id", "")
        val host = json.optString("host", "")
        val port = json.optInt("port", 0)

        if (tunnelId.isEmpty() || host.isEmpty() || port <= 0) {
            Log.e(TAG, "Invalid tcp_connect params")
            sendTcpError(tunnelId, "Invalid parameters")
            return
        }

        Log.i(TAG, "TCP connect: $host:$port (tunnel: $tunnelId)")

        serviceScope.launch {
            try {
                // Create socket and connect
                val socket = Socket()
                socket.soTimeout = 0 // No read timeout for long-lived connections
                socket.connect(InetSocketAddress(host, port), 30000)

                val tunnel = TcpTunnel(
                    tunnelId = tunnelId,
                    socket = socket,
                    inputStream = socket.getInputStream(),
                    outputStream = socket.getOutputStream()
                )
                tcpTunnels[tunnelId] = tunnel

                // Notify server of successful connection
                val response = JSONObject().apply {
                    put("action", "tcp_connected")
                    put("tunnel_id", tunnelId)
                }
                sendResponse(response.toString())

                Log.i(TAG, "TCP tunnel $tunnelId connected to $host:$port")

                // Start reading from socket and forwarding to server
                tunnel.readJob = launch {
                    readFromTcpTunnel(tunnel)
                }

            } catch (e: Exception) {
                Log.e(TAG, "TCP connect error: ${e.message}")
                sendTcpError(tunnelId, e.message ?: "Connection failed")
            }
        }
    }

    /**
     * Read data from TCP socket and send to server via WebSocket.
     */
    private suspend fun readFromTcpTunnel(tunnel: TcpTunnel) {
        val buffer = ByteArray(8192)

        try {
            withContext(Dispatchers.IO) {
                while (tcpTunnels.containsKey(tunnel.tunnelId)) {
                    val bytesRead = tunnel.inputStream.read(buffer)
                    if (bytesRead <= 0) break

                    // Send binary: tunnelId (36 bytes) + data
                    val tunnelIdBytes = tunnel.tunnelId.toByteArray(Charsets.UTF_8)
                    val data = ByteArray(36 + bytesRead)
                    System.arraycopy(tunnelIdBytes, 0, data, 0, 36)
                    System.arraycopy(buffer, 0, data, 36, bytesRead)

                    webSocket?.send(ByteString.of(*data))
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "TCP tunnel ${tunnel.tunnelId} read ended: ${e.message}")
        } finally {
            closeTcpTunnel(tunnel.tunnelId, notifyServer = true)
        }
    }

    /**
     * Handle incoming binary data from server for TCP tunnel.
     */
    private fun handleTcpData(data: ByteArray) {
        if (data.size < 36) return

        val tunnelId = String(data, 0, 36, Charsets.UTF_8)
        val payload = data.copyOfRange(36, data.size)

        val tunnel = tcpTunnels[tunnelId] ?: return

        serviceScope.launch(Dispatchers.IO) {
            try {
                tunnel.outputStream.write(payload)
                tunnel.outputStream.flush()
            } catch (e: Exception) {
                Log.e(TAG, "TCP write error: ${e.message}")
                closeTcpTunnel(tunnelId, notifyServer = true)
            }
        }
    }

    /**
     * Handle tcp_close request from server.
     */
    private fun handleTcpClose(json: JSONObject) {
        val tunnelId = json.optString("tunnel_id", "")
        if (tunnelId.isNotEmpty()) {
            Log.i(TAG, "TCP close requested: $tunnelId")
            closeTcpTunnel(tunnelId, notifyServer = false)
        }
    }

    /**
     * Close TCP tunnel and clean up.
     */
    private fun closeTcpTunnel(tunnelId: String, notifyServer: Boolean) {
        val tunnel = tcpTunnels.remove(tunnelId) ?: return

        tunnel.readJob?.cancel()

        try {
            tunnel.socket.close()
        } catch (e: Exception) {
            // Ignore
        }

        if (notifyServer) {
            val response = JSONObject().apply {
                put("action", "tcp_closed")
                put("tunnel_id", tunnelId)
            }
            sendResponse(response.toString())
        }

        Log.i(TAG, "TCP tunnel $tunnelId closed")
    }

    private fun sendTcpError(tunnelId: String, error: String) {
        val response = JSONObject().apply {
            put("action", "tcp_error")
            put("tunnel_id", tunnelId)
            put("error", error)
        }
        sendResponse(response.toString())
    }

    private fun sendPong(id: String) {
        val response = JSONObject().apply {
            put("id", id)
            put("action", "pong")
            put("timestamp", System.currentTimeMillis())
        }
        sendResponse(response.toString())
    }

    private fun sendStatus(id: String) {
        val response = JSONObject().apply {
            put("id", id)
            put("action", "status")
            put("connected", isConnected)
            put("total_requests", requestCounter.get())
            put("active_requests", activeRequests.get())
            put("device_id", Build.ID)
            put("device_model", Build.MODEL)
        }
        sendResponse(response.toString())
    }

    private fun sendError(id: String, error: String, status: Int = 500) {
        val response = JSONObject().apply {
            put("id", id)
            put("status", status)
            put("error", error)
        }
        sendResponse(response.toString())
    }

    private fun sendResponse(text: String) {
        try {
            webSocket?.send(text)
        } catch (e: Exception) {
            Log.e(TAG, "Error sending response: ${e.message}")
        }
    }

    // === Notification ===

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tunnel Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "HTTP прокси через мобильный IP"
                setShowBadge(false)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(status: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Eldoleado Tunnel")
            .setContentText(status)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(status: String) {
        try {
            val notification = createNotification(status)
            val manager = getSystemService(NotificationManager::class.java)
            manager.notify(NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating notification: ${e.message}")
        }
    }
}
