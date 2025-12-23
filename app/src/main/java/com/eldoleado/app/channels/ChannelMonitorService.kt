package com.eldoleado.app.channels

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Foreground service that monitors:
 * - Channel connection status
 * - Battery level
 * - Network connectivity
 *
 * Sends alerts via Telegram bot when issues are detected.
 */
class ChannelMonitorService : Service() {

    companion object {
        private const val TAG = "ChannelMonitorService"
        private const val NOTIFICATION_CHANNEL_ID = "channel_monitor"
        private const val NOTIFICATION_ID = 3001
        private const val DEFAULT_CHECK_INTERVAL = 5 * 60 * 1000L // 5 minutes

        const val ACTION_START = "com.eldoleado.app.channels.START_MONITOR"
        const val ACTION_STOP = "com.eldoleado.app.channels.STOP_MONITOR"

        fun start(context: Context) {
            val intent = Intent(context, ChannelMonitorService::class.java).apply {
                action = ACTION_START
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, ChannelMonitorService::class.java).apply {
                action = ACTION_STOP
            }
            context.startService(intent)
        }
    }

    private lateinit var credentialsManager: ChannelCredentialsManager
    private lateinit var alertSender: AlertSender
    private var monitorJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // State tracking
    private var lastBatteryLevel: Int = 100
    private var lastNetworkState: Boolean = true
    private var lastBatteryAlertSent: Long = 0
    private var lastChannelStates = mutableMapOf<ChannelType, ChannelStatus>()

    // Battery receiver
    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPct = if (level >= 0 && scale > 0) (level * 100 / scale) else 100
                handleBatteryChange(batteryPct)
            }
        }
    }

    // Network callback
    private lateinit var connectivityManager: ConnectivityManager
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            handleNetworkChange(true)
        }

        override fun onLost(network: Network) {
            handleNetworkChange(false)
        }
    }

    override fun onCreate() {
        super.onCreate()
        credentialsManager = ChannelCredentialsManager(this)
        alertSender = AlertSender(this)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        createNotificationChannel()
        initializeChannelStates()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                startForeground(NOTIFICATION_ID, createNotification())
                startMonitoring()
            }
            ACTION_STOP -> {
                stopMonitoring()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopMonitoring()
        scope.cancel()
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Мониторинг каналов",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Мониторинг состояния мессенджеров"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("open_settings", true)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Eldoleado")
            .setContentText("Мониторинг каналов активен")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun initializeChannelStates() {
        ChannelType.values().forEach { channel ->
            lastChannelStates[channel] = credentialsManager.getChannelStatus(channel)
        }
    }

    private fun startMonitoring() {
        Log.i(TAG, "Starting channel monitoring")

        // Register battery receiver
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        // Register network callback
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Check current network state
        lastNetworkState = isNetworkAvailable()

        // Start periodic channel checks
        monitorJob?.cancel()
        monitorJob = scope.launch {
            while (isActive) {
                checkChannels()
                delay(DEFAULT_CHECK_INTERVAL)
            }
        }
    }

    private fun stopMonitoring() {
        Log.i(TAG, "Stopping channel monitoring")
        monitorJob?.cancel()

        try {
            unregisterReceiver(batteryReceiver)
        } catch (e: Exception) {
            // Already unregistered
        }

        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            // Already unregistered
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun handleBatteryChange(level: Int) {
        val now = System.currentTimeMillis()
        val throttleInterval = 6 * 60 * 60 * 1000L // 6 hours between same alerts

        // Only alert once per threshold per throttle interval
        if (level <= 5 && lastBatteryLevel > 5) {
            if (now - lastBatteryAlertSent > throttleInterval) {
                scope.launch {
                    alertSender.sendAlert(
                        AlertSender.ALERT_BATTERY_CRITICAL,
                        mapOf("level" to level.toString())
                    )
                }
                lastBatteryAlertSent = now
            }
        } else if (level <= 15 && lastBatteryLevel > 15) {
            if (now - lastBatteryAlertSent > throttleInterval) {
                scope.launch {
                    alertSender.sendAlert(
                        AlertSender.ALERT_BATTERY_LOW,
                        mapOf("level" to level.toString())
                    )
                }
                lastBatteryAlertSent = now
            }
        }

        lastBatteryLevel = level
    }

    private fun handleNetworkChange(available: Boolean) {
        if (available != lastNetworkState) {
            scope.launch {
                // Small delay to avoid flapping
                delay(1000)

                val currentState = isNetworkAvailable()
                if (currentState != lastNetworkState) {
                    if (currentState) {
                        alertSender.sendAlert(AlertSender.ALERT_NETWORK_RESTORED)
                    } else {
                        alertSender.sendAlert(AlertSender.ALERT_NETWORK_LOST)
                    }
                    lastNetworkState = currentState
                }
            }
        }
    }

    private suspend fun checkChannels() {
        Log.d(TAG, "Checking channel status...")

        ChannelType.values().forEach { channel ->
            val currentStatus = checkChannelHealth(channel)
            val lastStatus = lastChannelStates[channel]

            // Update stored status
            when (channel) {
                ChannelType.WHATSAPP -> credentialsManager.setWhatsAppStatus(currentStatus)
                ChannelType.TELEGRAM -> credentialsManager.setTelegramStatus(currentStatus)
                ChannelType.TELEGRAM_BOT -> credentialsManager.setTelegramBotStandaloneStatus(currentStatus)
                ChannelType.VK -> credentialsManager.setVkStatus(currentStatus)
                ChannelType.VK_GROUP -> credentialsManager.setVkGroupStatus(currentStatus)
                ChannelType.AVITO -> credentialsManager.setAvitoStatus(currentStatus)
                ChannelType.MAX -> credentialsManager.setMaxStatus(currentStatus)
            }

            // Send alerts for status changes
            if (lastStatus != null && lastStatus != currentStatus) {
                when {
                    currentStatus == ChannelStatus.ERROR && lastStatus == ChannelStatus.CONNECTED -> {
                        alertSender.sendAlert(
                            AlertSender.ALERT_CHANNEL_ERROR,
                            mapOf("channel" to channel.displayName)
                        )
                    }
                    currentStatus == ChannelStatus.CONNECTED && lastStatus == ChannelStatus.ERROR -> {
                        alertSender.sendAlert(
                            AlertSender.ALERT_CHANNEL_RESTORED,
                            mapOf("channel" to channel.displayName)
                        )
                    }
                }
            }

            lastChannelStates[channel] = currentStatus
        }
    }

    private suspend fun checkChannelHealth(channel: ChannelType): ChannelStatus {
        val currentStatus = credentialsManager.getChannelStatus(channel)

        // If not configured, no need to check
        if (currentStatus == ChannelStatus.NOT_CONFIGURED) {
            return ChannelStatus.NOT_CONFIGURED
        }

        return withContext(Dispatchers.IO) {
            try {
                when (channel) {
                    ChannelType.WHATSAPP -> checkWhatsAppHealth()
                    ChannelType.TELEGRAM -> checkTelegramHealth()
                    ChannelType.TELEGRAM_BOT -> ChannelStatus.NOT_CONFIGURED // TODO: implement
                    ChannelType.VK -> ChannelStatus.NOT_CONFIGURED // TODO: implement
                    ChannelType.VK_GROUP -> ChannelStatus.NOT_CONFIGURED // TODO: implement
                    ChannelType.AVITO -> checkAvitoHealth()
                    ChannelType.MAX -> checkMaxHealth()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking ${channel.displayName}: ${e.message}")
                ChannelStatus.ERROR
            }
        }
    }

    private fun checkTelegramHealth(): ChannelStatus {
        val type = credentialsManager.getTelegramType()
        if (type == ChannelCredentialsManager.TG_TYPE_BOT) {
            val token = credentialsManager.getTelegramBotToken() ?: return ChannelStatus.NOT_CONFIGURED

            // Verify bot token
            val url = URL("https://api.telegram.org/bot$token/getMe")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            return try {
                val responseCode = connection.responseCode
                if (responseCode == 200) ChannelStatus.CONNECTED else ChannelStatus.ERROR
            } finally {
                connection.disconnect()
            }
        }

        // For user API, we can't easily verify without MCP server
        return credentialsManager.getTelegramStatus()
    }

    private fun checkWhatsAppHealth(): ChannelStatus {
        val session = credentialsManager.getWhatsAppSession() ?: return ChannelStatus.NOT_CONFIGURED

        // Check local Baileys service
        return try {
            val url = URL("http://localhost:3000/status")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                when (json.optString("status")) {
                    "connected" -> ChannelStatus.CONNECTED
                    else -> ChannelStatus.ERROR
                }
            } else {
                ChannelStatus.ERROR
            }
        } catch (e: Exception) {
            // Service not running - can't verify, assume last known state
            credentialsManager.getWhatsAppStatus()
        }
    }

    private fun checkAvitoHealth(): ChannelStatus {
        val sessid = credentialsManager.getAvitoSessid() ?: return ChannelStatus.NOT_CONFIGURED

        // Verify sessid with Avito API
        return try {
            val url = URL("https://m.avito.ru/web/1/messenger/getChannels")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.setRequestProperty("Cookie", "sessid=$sessid; auth=1")

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                if (response.contains("\"status\":\"ok\"") || response.contains("channels")) {
                    ChannelStatus.CONNECTED
                } else {
                    ChannelStatus.ERROR
                }
            } else {
                ChannelStatus.ERROR
            }
        } catch (e: Exception) {
            Log.e(TAG, "Avito health check failed: ${e.message}")
            ChannelStatus.ERROR
        }
    }

    private fun checkMaxHealth(): ChannelStatus {
        val token = credentialsManager.getMaxToken() ?: return ChannelStatus.NOT_CONFIGURED

        // Check local vkmax service
        return try {
            val url = URL("http://localhost:3001/status?token=$token")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                when (json.optString("status")) {
                    "connected" -> ChannelStatus.CONNECTED
                    else -> ChannelStatus.ERROR
                }
            } else {
                ChannelStatus.ERROR
            }
        } catch (e: Exception) {
            // Service not running - can't verify, assume last known state
            credentialsManager.getMaxStatus()
        }
    }
}
