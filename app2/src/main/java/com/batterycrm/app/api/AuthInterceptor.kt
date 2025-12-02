package com.batterycrm.app.api

import com.batterycrm.app.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        if (url.contains("/android/auth/login")) {
            return chain.proceed(request)
        }
        val sessionToken = sessionManager.getSessionToken()
        val builder = request.newBuilder()
        if (!sessionToken.isNullOrBlank()) {
            builder.header("Authorization", "Bearer $sessionToken")
            builder.header("X-Session-Token", sessionToken)
        }
        return chain.proceed(builder.build())
    }
}




