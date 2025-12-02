package com.callrecorder.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import java.io.File

class CallRecorderApp : Application() {

    lateinit var preferences: RecordingPreferences
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = RecordingPreferences(this)
        createNotificationChannels()
        createRecordingsDirectory()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Recording service channel
            val recordingChannel = NotificationChannel(
                CHANNEL_RECORDING,
                "Call Recording",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows when call recording service is running"
                setShowBadge(false)
            }

            // Upload service channel
            val uploadChannel = NotificationChannel(
                CHANNEL_UPLOAD,
                "Upload Status",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows upload progress and status"
            }

            notificationManager.createNotificationChannel(recordingChannel)
            notificationManager.createNotificationChannel(uploadChannel)
        }
    }

    private fun createRecordingsDirectory() {
        val recordingsDir = getRecordingsDirectory()
        if (!recordingsDir.exists()) {
            recordingsDir.mkdirs()
        }
    }

    fun getRecordingsDirectory(): File {
        return File(filesDir, RECORDINGS_FOLDER)
    }

    companion object {
        const val CHANNEL_RECORDING = "recording_channel"
        const val CHANNEL_UPLOAD = "upload_channel"
        const val RECORDINGS_FOLDER = "recordings"

        lateinit var instance: CallRecorderApp
            private set
    }
}
