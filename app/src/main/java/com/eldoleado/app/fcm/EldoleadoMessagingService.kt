package com.eldoleado.app.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.eldoleado.app.AppealEventBus
import com.eldoleado.app.AppealUpdateEvent
import com.eldoleado.app.EldoleadoApplication
import com.eldoleado.app.ChatActivity
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import com.eldoleado.app.SessionManager
import com.eldoleado.app.api.RetrofitClient
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EldoleadoMessagingService : FirebaseMessagingService() {

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
        // Support both dialog_id (new) and appeal_id (legacy)
        val dialogId = data["dialog_id"] ?: data["appeal_id"]
        val draftText = data["draft_text"] ?: data["ai_response"]

        Log.d(TAG, "FCM received: type=$type, dialogId=$dialogId, title=$title")

        // Инвалидация кэша и публикация события для realtime обновления UI
        handleCacheInvalidation(type, dialogId, draftText)

        when (type) {
            "new_appeal", "new_dialog" -> showNotification(title, body, dialogId, openDetail = true)
            "new_message" -> showNotification(title, body, dialogId, openDetail = false, openChat = true)
            "appeal_update" -> showNotification(title, body, dialogId, openDetail = true)
            "draft_ready", "agent_message" -> showNotification(title, body, dialogId, openDetail = true)
            else -> showNotification(title, body, dialogId, openDetail = false)
        }
    }

    private fun handleCacheInvalidation(type: String, appealId: String?, draftText: String? = null) {
        serviceScope.launch {
            try {
                val repository = EldoleadoApplication.instance.repository
                when (type) {
                    "new_appeal" -> {
                        // Новое обращение - инвалидируем весь список
                        repository.invalidateAllCache()
                        AppealEventBus.post(AppealUpdateEvent.AllAppealsUpdated)
                        Log.d(TAG, "Cache invalidated: new appeal")
                    }
                    "new_message" -> {
                        // Новое сообщение - обновить UI если открыт этот диалог
                        if (!appealId.isNullOrBlank()) {
                            repository.invalidateAppealCache(appealId)
                            Log.d(TAG, "Posting NewMessage event to EventBus for dialogId: $appealId")
                            AppealEventBus.post(AppealUpdateEvent.NewMessage(appealId))
                            Log.d(TAG, "NewMessage event posted successfully")
                        } else {
                            Log.w(TAG, "new_message type but appealId is null/blank!")
                        }
                    }
                    "draft_ready", "agent_message" -> {
                        // AI сгенерировал черновик - обновить UI + вставить в поле ввода
                        if (!appealId.isNullOrBlank()) {
                            repository.invalidateAppealCache(appealId)
                            AppealEventBus.post(AppealUpdateEvent.DraftReady(appealId, draftText))
                            Log.d(TAG, "Draft ready event for appeal: $appealId")
                        }
                    }
                    "appeal_update" -> {
                        // Общее обновление заявки
                        if (!appealId.isNullOrBlank()) {
                            repository.invalidateAppealCache(appealId)
                            AppealEventBus.post(AppealUpdateEvent.AppealUpdated(appealId))
                            Log.d(TAG, "Appeal updated: $appealId")
                        }
                    }
                    "data_sync" -> {
                        // Полная синхронизация
                        repository.invalidateAllCache()
                        AppealEventBus.post(AppealUpdateEvent.AllAppealsUpdated)
                        Log.d(TAG, "Full cache invalidation requested")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to invalidate cache", e)
            }
        }
    }

    companion object {
        private const val TAG = "EldoleadoFCM"
    }

    private fun showNotification(title: String, body: String, dialogId: String?, openDetail: Boolean, openChat: Boolean = false) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "eldoleado_default"
        createNotificationChannelIfNeeded(notificationManager, channelId)

        val intent = if (openChat && !dialogId.isNullOrBlank()) {
            // Open ChatActivity directly for new messages
            Intent(this, ChatActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("dialog_id", dialogId)
            }
        } else {
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                if (openDetail && !dialogId.isNullOrBlank()) {
                    putExtra("open_appeal_detail", true)
                    putExtra("appeal_id", dialogId)
                }
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
            color = ContextCompat.getColor(this@EldoleadoMessagingService, R.color.notification_color)
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
                "Eldoleado",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Системные уведомления Eldoleado"
                enableLights(true)
                lightColor = Color.WHITE
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }
    }

    private fun saveTokenLocally(token: String) {
        val prefs = getSharedPreferences("Eldoleado_FCM", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }

    private fun sendTokenToServer(token: String) {
        serviceScope.launch {
            val sessionManager = SessionManager(this@EldoleadoMessagingService)
            val operatorId = sessionManager.getOperatorId()
            val sessionToken = sessionManager.getSessionToken()
            if (!operatorId.isNullOrBlank() && !sessionToken.isNullOrBlank()) {
                val repository = FCMRepository(
                    apiService = RetrofitClient.getApiService(this@EldoleadoMessagingService),
                    context = this@EldoleadoMessagingService
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




