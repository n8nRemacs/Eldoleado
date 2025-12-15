package com.eldoleado.app

import android.app.Application
import androidx.work.*
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.data.database.AppDatabase
import com.eldoleado.app.data.repository.AppealsRepository
import com.eldoleado.app.service.DataCleanupWorker
import java.util.concurrent.TimeUnit

class EldoleadoApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: AppealsRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Инициализация базы данных
        database = AppDatabase.getDatabase(this)

        // Инициализация репозитория
        repository = AppealsRepository(
            appealDao = database.appealDao(),
            messageDao = database.messageDao(),
            apiService = RetrofitClient.getApiService(this)
        )

        // Запуск периодической очистки старых данных (раз в день)
        scheduleDataCleanup()
    }

    private fun scheduleDataCleanup() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val cleanupRequest = PeriodicWorkRequestBuilder<DataCleanupWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS) // Первый запуск через час
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "data_cleanup",
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupRequest
        )
    }

    companion object {
        lateinit var instance: EldoleadoApplication
            private set
    }
}
