package com.eldoleado.app.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eldoleado.app.EldoleadoApplication

class DataCleanupWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "DataCleanupWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting data cleanup...")

            val app = applicationContext as EldoleadoApplication
            app.repository.cleanupOldMessages()

            Log.d(TAG, "Data cleanup completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Data cleanup failed", e)
            Result.retry()
        }
    }
}
