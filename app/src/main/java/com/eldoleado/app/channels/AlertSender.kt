package com.eldoleado.app.channels

import android.content.Context
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Sends alert notifications to Telegram bot.
 *
 * Alerts are sent for:
 * - Battery low/critical
 * - Network lost/restored
 * - Channel errors/restored
 * - Server offline/online
 */
class AlertSender(private val context: Context) {

    companion object {
        private const val TAG = "AlertSender"
        private const val TELEGRAM_API = "https://api.telegram.org/bot"

        // Alert types
        const val ALERT_BATTERY_LOW = "battery_low"
        const val ALERT_BATTERY_CRITICAL = "battery_critical"
        const val ALERT_NETWORK_LOST = "network_lost"
        const val ALERT_NETWORK_RESTORED = "network_restored"
        const val ALERT_CHANNEL_ERROR = "channel_error"
        const val ALERT_CHANNEL_RESTORED = "channel_restored"
        const val ALERT_SERVER_OFFLINE = "server_offline"
        const val ALERT_SERVER_ONLINE = "server_online"
    }

    private val credentialsManager = ChannelCredentialsManager(context)

    /**
     * Send alert message to configured Telegram chat.
     */
    suspend fun sendAlert(alertType: String, details: Map<String, String> = emptyMap()): Boolean {
        val botToken = credentialsManager.getAlertBotToken()
        val chatId = credentialsManager.getAlertChatId()

        if (botToken.isNullOrBlank() || chatId.isNullOrBlank()) {
            Log.w(TAG, "Alert not configured: botToken or chatId missing")
            return false
        }

        // Check if this alert type is enabled
        if (!isAlertEnabled(alertType)) {
            Log.d(TAG, "Alert type $alertType is disabled")
            return false
        }

        val message = buildAlertMessage(alertType, details)

        return withContext(Dispatchers.IO) {
            try {
                sendTelegramMessage(botToken, chatId, message)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to send alert: ${e.message}")
                false
            }
        }
    }

    /**
     * Send test message to verify configuration.
     */
    suspend fun sendTestMessage(): Boolean {
        val botToken = credentialsManager.getAlertBotToken()
        val chatId = credentialsManager.getAlertChatId()

        if (botToken.isNullOrBlank() || chatId.isNullOrBlank()) {
            return false
        }

        val message = buildTestMessage()

        return withContext(Dispatchers.IO) {
            try {
                sendTelegramMessage(botToken, chatId, message)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to send test message: ${e.message}")
                false
            }
        }
    }

    private fun isAlertEnabled(alertType: String): Boolean {
        return when (alertType) {
            ALERT_BATTERY_LOW, ALERT_BATTERY_CRITICAL -> credentialsManager.isAlertBatteryEnabled()
            ALERT_NETWORK_LOST, ALERT_NETWORK_RESTORED -> credentialsManager.isAlertNetworkEnabled()
            ALERT_CHANNEL_ERROR, ALERT_CHANNEL_RESTORED -> credentialsManager.isAlertChannelsEnabled()
            ALERT_SERVER_OFFLINE, ALERT_SERVER_ONLINE -> true // Always enabled
            else -> true
        }
    }

    private fun buildAlertMessage(alertType: String, details: Map<String, String>): String {
        val deviceName = Build.MODEL
        val timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date())

        val (emoji, title, description) = when (alertType) {
            ALERT_BATTERY_LOW -> {
                val level = details["level"] ?: "?"
                Triple("⚠️", "Низкий заряд батареи", "Уровень заряда: $level%")
            }
            ALERT_BATTERY_CRITICAL -> {
                val level = details["level"] ?: "?"
                Triple("🔴", "Критический заряд батареи", "Уровень заряда: $level%")
            }
            ALERT_NETWORK_LOST -> {
                Triple("📵", "Потеряно соединение", "Интернет недоступен")
            }
            ALERT_NETWORK_RESTORED -> {
                Triple("✅", "Соединение восстановлено", "Интернет снова доступен")
            }
            ALERT_CHANNEL_ERROR -> {
                val channel = details["channel"] ?: "Неизвестный"
                val error = details["error"] ?: "Сессия недействительна"
                Triple("🔴", "$channel: ошибка", error)
            }
            ALERT_CHANNEL_RESTORED -> {
                val channel = details["channel"] ?: "Неизвестный"
                Triple("✅", "$channel: восстановлено", "Подключение восстановлено")
            }
            ALERT_SERVER_OFFLINE -> {
                Triple("🔴", "Сервер не отвечает", "Tunnel сервер недоступен")
            }
            ALERT_SERVER_ONLINE -> {
                Triple("✅", "Сервер онлайн", "Tunnel сервер снова доступен")
            }
            else -> {
                Triple("ℹ️", "Уведомление", details["message"] ?: "")
            }
        }

        // Build channels status
        val channelsStatus = buildChannelsStatusLine()

        return """
$emoji ALERT: Eldoleado Server

📱 Устройство: $deviceName
🕐 Время: $timestamp

$title
$description

$channelsStatus
        """.trimIndent()
    }

    private fun buildTestMessage(): String {
        val deviceName = Build.MODEL
        val timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date())

        val channelsStatus = buildChannelsStatusLine()

        return """
✅ Тестовое сообщение

📱 Устройство: $deviceName
🕐 Время: $timestamp

Уведомления настроены корректно!

$channelsStatus
        """.trimIndent()
    }

    private fun buildChannelsStatusLine(): String {
        val statuses = mutableListOf<String>()

        val telegramStatus = credentialsManager.getChannelStatus(ChannelType.TELEGRAM)
        val whatsappStatus = credentialsManager.getChannelStatus(ChannelType.WHATSAPP)
        val avitoStatus = credentialsManager.getChannelStatus(ChannelType.AVITO)
        val maxStatus = credentialsManager.getChannelStatus(ChannelType.MAX)

        statuses.add("• Telegram: ${statusToEmoji(telegramStatus)}")
        statuses.add("• WhatsApp: ${statusToEmoji(whatsappStatus)}")
        statuses.add("• Avito: ${statusToEmoji(avitoStatus)}")
        statuses.add("• MAX: ${statusToEmoji(maxStatus)}")

        return "Статус каналов:\n${statuses.joinToString("\n")}"
    }

    private fun statusToEmoji(status: ChannelStatus): String {
        return when (status) {
            ChannelStatus.CONNECTED -> "✅"
            ChannelStatus.ERROR -> "🔴"
            ChannelStatus.CHECKING -> "🟡"
            ChannelStatus.NOT_CONFIGURED -> "⚪"
        }
    }

    private fun sendTelegramMessage(botToken: String, chatId: String, message: String): Boolean {
        val urlString = "${TELEGRAM_API}${botToken}/sendMessage"
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val params = "chat_id=${URLEncoder.encode(chatId, "UTF-8")}" +
                    "&text=${URLEncoder.encode(message, "UTF-8")}" +
                    "&parse_mode=HTML"

            connection.outputStream.bufferedWriter().use { it.write(params) }

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                Log.d(TAG, "Alert sent successfully")
                true
            } else {
                val error = connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                Log.e(TAG, "Telegram API error: $responseCode - $error")
                false
            }
        } finally {
            connection.disconnect()
        }
    }
}
