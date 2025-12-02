package com.batterycrm.app.api

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.batterycrm.app.LoginActivity
import com.batterycrm.app.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor(private val context: Context) : Interceptor {
    private val sessionManager = SessionManager(context.applicationContext)
    private val handler = Handler(Looper.getMainLooper())
    @Volatile
    private var isHandlingUnauthorized = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Обработка 401 Unauthorized
        if (response.code == 401) {
            val url = request.url.toString()
            // Не обрабатываем 401 для логина и logout, чтобы не было рекурсии
            if (!url.contains("/android/auth/login") && !url.contains("/android/logout")) {
                // Защита от множественных вызовов
                if (!isHandlingUnauthorized) {
                    isHandlingUnauthorized = true
                    handleUnauthorized()
                }
            }
        }

        return response
    }

    private fun handleUnauthorized() {
        // Очищаем сессию
        sessionManager.clearSession()

        // Показываем сообщение в главном потоке
        handler.post {
            Toast.makeText(
                context.applicationContext,
                "Вы вошли в систему с другого устройства",
                Toast.LENGTH_LONG
            ).show()
        }

        // Редиректим на логин
        handler.post {
            try {
                val intent = Intent(context.applicationContext, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.applicationContext.startActivity(intent)
            } catch (e: Exception) {
                android.util.Log.e("ErrorInterceptor", "Failed to navigate to login", e)
            } finally {
                // Сбрасываем флаг через небольшую задержку
                handler.postDelayed({
                    isHandlingUnauthorized = false
                }, 2000)
            }
        }
    }
}

