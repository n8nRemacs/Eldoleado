package com.eldoleado.app.tunnel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import com.eldoleado.app.SessionManager
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * TunnelService - Foreground Service for WebSocket tunnel to server.
 *
 * Runs in background, maintains WebSocket connection, proxies HTTP requests
 * from server to local services (Baileys, Pyrogram, etc).
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

    private lateinit var sessionManager: SessionManager
    private lateinit var httpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)
        httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
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

        // Build WebSocket URL with auth
        val wsUrl = "$tunnelUrl?operator_id=$operatorId&secret=$tunnelSecret"

        val request = Request.Builder()
            .url(wsUrl)
            .build()

        webSocket = httpClient.newWebSocket(request, TunnelWebSocketListener())
        Log.i(TAG, "Connecting to: $tunnelUrl")
    }

    private fun disconnect() {
        reconnectJob?.cancel()
        webSocket?.close(1000, "Service stopped")
        webSocket = null
        isConnected = false
    }

    private fun scheduleReconnect() {
        reconnectJob?.cancel()
        reconnectJob = serviceScope.launch {
            delay(5000) // 5 seconds
            if (!isConnected) {
                Log.i(TAG, "Reconnecting...")
                connect()
            }
        }
    }

    private inner class TunnelWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.i(TAG, "WebSocket connected")
            isConnected = true
            updateNotification("Подключено")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG, "Received: $text")
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
            scheduleReconnect()
        }
    }

    /**
     * Handle incoming message from server.
     * Expected format:
     * {
     *   "id": "uuid",
     *   "action": "http_request",
     *   "target": "localhost:3002",
     *   "method": "POST",
     *   "path": "/send",
     *   "headers": {},
     *   "body": {}
     * }
     */
    private suspend fun handleMessage(text: String) {
        try {
            val json = JSONObject(text)
            val id = json.getString("id")
            val action = json.getString("action")

            when (action) {
                "http_request" -> handleHttpRequest(id, json)
                "ping" -> sendPong(id)
                else -> sendError(id, "Unknown action: $action")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling message: ${e.message}")
        }
    }

    private suspend fun handleHttpRequest(id: String, json: JSONObject) {
        val target = json.getString("target") // e.g., "localhost:3002"
        val method = json.optString("method", "GET")
        val path = json.optString("path", "/")
        val headers = json.optJSONObject("headers")
        val body = json.optJSONObject("body")

        val url = "http://$target$path"

        try {
            val requestBuilder = Request.Builder().url(url)

            // Add headers
            headers?.keys()?.forEach { key ->
                requestBuilder.addHeader(key, headers.getString(key))
            }

            // Set method and body
            val requestBody = if (body != null) {
                RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    body.toString()
                )
            } else null

            when (method.uppercase()) {
                "GET" -> requestBuilder.get()
                "POST" -> requestBuilder.post(requestBody ?: RequestBody.create(null, ""))
                "PUT" -> requestBuilder.put(requestBody ?: RequestBody.create(null, ""))
                "DELETE" -> requestBuilder.delete(requestBody)
                "PATCH" -> requestBuilder.patch(requestBody ?: RequestBody.create(null, ""))
            }

            val request = requestBuilder.build()
            val response = httpClient.newCall(request).execute()

            val responseJson = JSONObject().apply {
                put("id", id)
                put("status", response.code)
                put("body", response.body?.string() ?: "")
            }

            sendResponse(responseJson.toString())

        } catch (e: Exception) {
            Log.e(TAG, "HTTP request error: ${e.message}")
            sendError(id, e.message ?: "Unknown error")
        }
    }

    private fun sendPong(id: String) {
        val response = JSONObject().apply {
            put("id", id)
            put("action", "pong")
        }
        sendResponse(response.toString())
    }

    private fun sendError(id: String, error: String) {
        val response = JSONObject().apply {
            put("id", id)
            put("status", 500)
            put("error", error)
        }
        sendResponse(response.toString())
    }

    private fun sendResponse(text: String) {
        webSocket?.send(text)
    }

    // === Notification ===

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tunnel Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Канал для работы туннеля"
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
        val notification = createNotification(status)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }
}
