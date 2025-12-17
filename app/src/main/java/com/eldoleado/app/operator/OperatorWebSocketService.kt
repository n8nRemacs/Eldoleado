package com.eldoleado.app.operator

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import com.eldoleado.app.SessionManager
import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * OperatorWebSocketService - WebSocket connection for operator app.
 *
 * Connects to /ws/operator endpoint to receive:
 * - new_message: incoming client messages
 * - show_draft: normalized drafts for approval
 *
 * Sends:
 * - send: new message from operator (needs normalization)
 * - approve: approved message (send to client)
 */
class OperatorWebSocketService : Service() {

    companion object {
        private const val TAG = "OperatorWS"
        private const val NOTIFICATION_ID = 3001
        private const val CHANNEL_ID = "operator_ws_channel"

        const val ACTION_START = "com.eldoleado.app.operator.START"
        const val ACTION_STOP = "com.eldoleado.app.operator.STOP"

        // Broadcast actions
        const val BROADCAST_NEW_MESSAGE = "com.eldoleado.app.NEW_MESSAGE"
        const val BROADCAST_SHOW_DRAFT = "com.eldoleado.app.SHOW_DRAFT"
        const val BROADCAST_CONNECTION_STATE = "com.eldoleado.app.CONNECTION_STATE"

        const val EXTRA_MESSAGE = "message"
        const val EXTRA_CONNECTED = "connected"

        fun start(context: Context) {
            val intent = Intent(context, OperatorWebSocketService::class.java).apply {
                action = ACTION_START
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, OperatorWebSocketService::class.java).apply {
                action = ACTION_STOP
            }
            context.startService(intent)
        }
    }

    // Binder for activity binding
    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        fun getService(): OperatorWebSocketService = this@OperatorWebSocketService
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var webSocket: WebSocket? = null
    private var isConnected = false
    private var reconnectJob: Job? = null

    private lateinit var sessionManager: SessionManager
    private lateinit var httpClient: OkHttpClient
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)
        localBroadcastManager = LocalBroadcastManager.getInstance(this)

        httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS) // No timeout for WebSocket
            .pingInterval(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
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

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        serviceScope.cancel()
    }

    fun isConnected() = isConnected

    private fun connect() {
        val tunnelUrl = sessionManager.getTunnelUrl() ?: return
        val operatorId = sessionManager.getOperatorId() ?: return
        val tenantId = sessionManager.getTenantId() ?: return
        val token = sessionManager.getAuthToken() ?: ""

        // Build WebSocket URL for operator endpoint
        val baseUrl = tunnelUrl.replace("/ws", "").trimEnd('/')
        val wsUrl = "$baseUrl/ws/operator?operator_id=$operatorId&tenant_id=$tenantId&token=$token"

        val request = Request.Builder()
            .url(wsUrl)
            .build()

        webSocket = httpClient.newWebSocket(request, OperatorWebSocketListener())
        Log.i(TAG, "Connecting to operator WebSocket: $operatorId")
    }

    private fun disconnect() {
        reconnectJob?.cancel()
        webSocket?.close(1000, "Service stopped")
        webSocket = null
        isConnected = false
        broadcastConnectionState(false)
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

    private inner class OperatorWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.i(TAG, "Connected to operator WebSocket")
            isConnected = true
            updateNotification("Подключено")
            broadcastConnectionState(true)
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
            broadcastConnectionState(false)
            scheduleReconnect()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket error: ${t.message}")
            isConnected = false
            updateNotification("Ошибка подключения")
            broadcastConnectionState(false)
            scheduleReconnect(10000)
        }
    }

    /**
     * Handle incoming message from server.
     *
     * new_message: { action: "new_message", message: {...} }
     * show_draft: { action: "show_draft", message: {...} }
     */
    private fun handleMessage(text: String) {
        try {
            val json = JSONObject(text)
            val action = json.optString("action", "")

            when (action) {
                "new_message" -> {
                    val message = json.optJSONObject("message")
                    if (message != null) {
                        broadcastNewMessage(message.toString())
                        showNotification(message)
                    }
                }
                "show_draft" -> {
                    val message = json.optJSONObject("message")
                    if (message != null) {
                        broadcastShowDraft(message.toString())
                    }
                }
                "ping" -> {
                    sendPong()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing message: ${e.message}")
        }
    }

    /**
     * Send message to operator (needs normalization).
     */
    fun sendMessage(chatId: String, channel: String, text: String, serverId: String) {
        val message = JSONObject().apply {
            put("action", "send")
            put("id", System.currentTimeMillis().toString())
            put("channel", channel)
            put("chat_id", chatId)
            put("text", text)
            put("server_id", serverId)
        }
        webSocket?.send(message.toString())
        Log.d(TAG, "Sent message: $channel/$chatId")
    }

    /**
     * Approve and send message to client.
     */
    fun approveMessage(messageId: String, chatId: String, channel: String, finalText: String, serverId: String) {
        val message = JSONObject().apply {
            put("action", "approve")
            put("id", messageId)
            put("channel", channel)
            put("chat_id", chatId)
            put("final_text", finalText)
            put("server_id", serverId)
        }
        webSocket?.send(message.toString())
        Log.d(TAG, "Approved message: $messageId")
    }

    private fun sendPong() {
        val pong = JSONObject().apply {
            put("action", "pong")
            put("timestamp", System.currentTimeMillis())
        }
        webSocket?.send(pong.toString())
    }

    // === Broadcasts ===

    private fun broadcastConnectionState(connected: Boolean) {
        val intent = Intent(BROADCAST_CONNECTION_STATE).apply {
            putExtra(EXTRA_CONNECTED, connected)
        }
        localBroadcastManager.sendBroadcast(intent)
    }

    private fun broadcastNewMessage(messageJson: String) {
        val intent = Intent(BROADCAST_NEW_MESSAGE).apply {
            putExtra(EXTRA_MESSAGE, messageJson)
        }
        localBroadcastManager.sendBroadcast(intent)
    }

    private fun broadcastShowDraft(messageJson: String) {
        val intent = Intent(BROADCAST_SHOW_DRAFT).apply {
            putExtra(EXTRA_MESSAGE, messageJson)
        }
        localBroadcastManager.sendBroadcast(intent)
    }

    // === Notifications ===

    private fun showNotification(message: JSONObject) {
        val senderName = message.optString("sender_name", "Клиент")
        val text = message.optString("text", "Новое сообщение")
        val channel = message.optString("channel", "")

        val channelIcon = when (channel) {
            "telegram" -> "[TG]"
            "whatsapp" -> "[WA]"
            "avito" -> "[AV]"
            "max" -> "[MX]"
            else -> ""
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("$channelIcon $senderName")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Operator Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Подключение к серверу сообщений"
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
            .setContentTitle("Eldoleado Operator")
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
