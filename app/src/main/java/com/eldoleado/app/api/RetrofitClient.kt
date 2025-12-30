package com.eldoleado.app.api

import android.content.Context
import com.eldoleado.app.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // n8n webhook URL (production)
    private const val BASE_URL = "https://n8n.n8nsrv.ru/webhook/"

    // WebSocket URL for push notifications (replaces FCM)
    // Server: 155.212.221.189 / 217.114.14.17
    const val WS_BASE_URL = "ws://155.212.221.189:8780"  // Production (no SSL)
    // const val WS_BASE_URL = "ws://10.0.2.2:8780"      // Local emulator
    // const val WS_BASE_URL = "wss://api.eldoleado.ru"  // Production with SSL (TODO)

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getApiService(context: Context): ApiService {
        val sessionManager = SessionManager(context.applicationContext)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(ErrorInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
