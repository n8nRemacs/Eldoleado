package com.callrecorder.app

import android.content.Context
import android.util.Log
import androidx.work.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * WorkManager Worker for uploading recordings to server
 */
class UploadWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val TAG = "UploadWorker"
        private const val KEY_FILE_PATH = "file_path"
        private const val WORK_NAME_PREFIX = "upload_"

        fun scheduleUpload(context: Context, filePath: String) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val inputData = workDataOf(KEY_FILE_PATH to filePath)

            val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30,
                    TimeUnit.SECONDS
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    WORK_NAME_PREFIX + File(filePath).name,
                    ExistingWorkPolicy.REPLACE,
                    uploadRequest
                )

            Log.d(TAG, "Upload scheduled for: $filePath")
        }

        fun scheduleAllPendingUploads(context: Context) {
            val recordingsDir = CallRecorderApp.instance.getRecordingsDirectory()
            if (recordingsDir.exists()) {
                recordingsDir.listFiles()?.forEach { file ->
                    if (file.isFile && file.extension == "m4a") {
                        scheduleUpload(context, file.absolutePath)
                    }
                }
            }
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    override fun doWork(): Result {
        val filePath = inputData.getString(KEY_FILE_PATH)

        if (filePath.isNullOrEmpty()) {
            Log.e(TAG, "No file path provided")
            return Result.failure()
        }

        val file = File(filePath)
        if (!file.exists()) {
            Log.e(TAG, "File does not exist: $filePath")
            return Result.failure()
        }

        val serverUrl = CallRecorderApp.instance.preferences.serverUrl

        Log.d(TAG, "Uploading file: ${file.name} to $serverUrl")

        return try {
            val success = uploadFile(file, serverUrl)
            if (success) {
                Log.d(TAG, "Upload successful: ${file.name}")

                // Delete file after successful upload if enabled
                if (CallRecorderApp.instance.preferences.deleteAfterUpload) {
                    file.delete()
                    Log.d(TAG, "File deleted: ${file.name}")
                }

                Result.success()
            } else {
                Log.w(TAG, "Upload failed, will retry: ${file.name}")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Upload error", e)
            Result.retry()
        }
    }

    private fun uploadFile(file: File, serverUrl: String): Boolean {
        // Parse file name to extract metadata
        // Format: callType_phoneNumber_timestamp.m4a
        val fileName = file.nameWithoutExtension
        val parts = fileName.split("_")

        val callType = parts.getOrNull(0) ?: "unknown"
        val phoneNumber = parts.getOrNull(1) ?: "unknown"
        val timestamp = parts.getOrNull(2) ?: ""

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("call_type", callType)
            .addFormDataPart("phone_number", phoneNumber)
            .addFormDataPart("timestamp", timestamp)
            .addFormDataPart(
                "audio_file",
                file.name,
                file.asRequestBody("audio/mp4".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url(serverUrl)
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "Server response: ${response.code}")
                    true
                } else {
                    Log.w(TAG, "Server error: ${response.code} - ${response.message}")
                    false
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error during upload", e)
            false
        }
    }
}
