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
    private val requestCounter = AtomicLong(0)
    private val activeRequests = AtomicLong(0)

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
        val tunnelUrl = sessionManager.getTunnelUrl()
        val tunnelSecret = sessionManager.getTunnelSecret()
        val operatorId = sessionManager.getOperatorId()

        if (tunnelUrl.isNullOrBlank()) {
            Log.e(TAG, "Tunnel URL not configured")
            updateNotification("Ошибка: URL не настроен")
            return
        }

        // Build WebSocket URL with auth params
        val wsUrl = buildString {
            append(tunnelUrl)
            append(if (tunnelUrl.contains("?")) "&" else "?")
            append("operator_id=$operatorId")
            append("&device_id=${Build.ID}")
            append("&device_model=${Build.MODEL}")
            append("&android_version=${Build.VERSION.RELEASE}")
        }

        val request = Request.Builder()
            .url(wsUrl)
            .header("Authorization", "Bearer $tunnelSecret")
            .header("X-Device-Id", Build.ID)
            .header("X-Device-Model", Build.MODEL)
            .build()

        webSocket = httpClient.newWebSocket(request, TunnelWebSocketListener())
        Log.i(TAG, "Connecting to tunnel server...")
    }

    private fun disconnect() {
        reconnectJob?.cancel()
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

            // Send device info
            sendDeviceInfo()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            serviceScope.launch {
                handleMessage(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            onMessage(webSocket, bytes.utf8())
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

    private fun sendDeviceInfo() {
        val info = JSONObject().apply {
            put("action", "device_info")
            put("device_id", Build.ID)
            put("device_model", Build.MODEL)
            put("manufacturer", Build.MANUFACTURER)
            put("android_version", Build.VERSION.RELEASE)
            put("sdk_version", Build.VERSION.SDK_INT)
        }
        webSocket?.send(info.toString())
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
                "http" -> handleHttpRequest(id, json)
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
                    val contentType = response.header("Content-Type", "")
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
