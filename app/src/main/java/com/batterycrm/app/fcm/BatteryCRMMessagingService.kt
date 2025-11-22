package com.batterycrm.app.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.batterycrm.app.MainActivity
import com.batterycrm.app.R
import com.batterycrm.app.SessionManager
import com.batterycrm.app.api.RetrofitClient
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BatteryCRMMessagingService : FirebaseMessagingService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveTokenLocally(token)
        sendTokenToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val type = data["type"] ?: "generic"
        val title = data["title"] ?: message.notification?.title ?: getString(R.string.app_name)
        val body = data["body"] ?: message.notification?.body ?: ""
        val appealId = data["appeal_id"]
        when (type) {
            "new_appeal" -> showNotification(title, body, appealId, true)
            else -> showNotification(title, body, appealId, false)
        }
    }

    private fun showNotification(title: String, body: String, appealId: String?, openDetail: Boolean) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "batterycrm_default"
        createNotificationChannelIfNeeded(notificationManager, channelId)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (openDetail && !appealId.isNullOrBlank()) {
                putExtra("open_appeal_detail", true)
                putExtra("appeal_id", appealId)
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_notification)
            color = ContextCompat.getColor(this@BatteryCRMMessagingService, R.color.notification_color)
            setContentTitle(title)
            setContentText(body)
            setAutoCancel(true)
            setStyle(NotificationCompat.BigTextStyle().bigText(body))
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        notificationManager.notify((System.currentTimeMillis() % Int.MAX_VALUE).toInt(), builder.build())
    }

    private fun createNotificationChannelIfNeeded(manager: NotificationManager, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "BatteryCRM",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Системные уведомления BatteryCRM"
                enableLights(true)
                lightColor = Color.WHITE
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }
    }

    private fun saveTokenLocally(token: String) {
        val prefs = getSharedPreferences("BatteryCRM_FCM", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }

    private fun sendTokenToServer(token: String) {
        serviceScope.launch {
            val sessionManager = SessionManager(this@BatteryCRMMessagingService)
            val operatorId = sessionManager.getOperatorId()
            val sessionToken = sessionManager.getSessionToken()
            if (!operatorId.isNullOrBlank() && !sessionToken.isNullOrBlank()) {
                val repository = FCMRepository(
                    apiService = RetrofitClient.getApiService(this@BatteryCRMMessagingService),
                    context = this@BatteryCRMMessagingService
                )
                repository.registerFCMToken(operatorId, sessionToken, token)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}




