package com.eldoleado.app.callrecording

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.eldoleado.app.MainActivity
import com.eldoleado.app.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CallRecordingService : Service() {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var currentRecordingFile: File? = null
    private var currentPhoneNumber: String? = null
    private var callType: String = "unknown"

    companion object {
        private const val TAG = "CallRecordingService"
        private const val NOTIFICATION_ID = 2001
        const val CHANNEL_RECORDING = "call_recording_channel"

        const val ACTION_START_SERVICE = "com.eldoleado.START_RECORDING_SERVICE"
        const val ACTION_STOP_SERVICE = "com.eldoleado.STOP_RECORDING_SERVICE"
        const val ACTION_START_RECORDING = "com.eldoleado.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.eldoleado.STOP_RECORDING"

        const val EXTRA_PHONE_NUMBER = "phone_number"
        const val EXTRA_CALL_TYPE = "call_type"

        const val CALL_TYPE_INCOMING = "incoming"
        const val CALL_TYPE_OUTGOING = "outgoing"

        private const val RECORDINGS_FOLDER = "call_recordings"

        @Volatile
        private var instance: CallRecordingService? = null

        fun isRunning(): Boolean = instance != null
    }

    private lateinit var preferences: CallRecordingPreferences

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = CallRecordingPreferences(this)
        createNotificationChannel()
        createRecordingsDirectory()
        Log.d(TAG, "Service created")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ${intent?.action}")

        when (intent?.action) {
            ACTION_START_SERVICE -> {
                startForegroundService()
            }
            ACTION_STOP_SERVICE -> {
                stopRecordingIfActive()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
            ACTION_START_RECORDING -> {
                currentPhoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER)
                callType = intent.getStringExtra(EXTRA_CALL_TYPE) ?: "unknown"
                startRecording()
            }
            ACTION_STOP_RECORDING -> {
                stopRecordingIfActive()
            }
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_RECORDING,
                "Запись звонков",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Показывает когда активна запись звонков"
                setShowBadge(false)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
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

    private fun startForegroundService() {
        val notification = createNotification("Запись звонков активна")
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "Foreground service started")
    }

    private fun createNotification(contentText: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_settings", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_RECORDING)
            .setContentTitle("Eldoleado")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_mic)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun startRecording() {
        if (!preferences.isRecordingEnabled) {
            Log.d(TAG, "Recording is disabled in settings")
            return
        }

        if (isRecording) {
            Log.d(TAG, "Already recording, skipping")
            return
        }

        try {
            val recordingsDir = getRecordingsDirectory()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val phoneNumberSafe = currentPhoneNumber?.replace(Regex("[^0-9+]"), "") ?: "unknown"
            val fileName = "${callType}_${phoneNumberSafe}_${timestamp}.m4a"
            currentRecordingFile = File(recordingsDir, fileName)

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(this)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(currentRecordingFile?.absolutePath)

                prepare()
                start()
            }

            isRecording = true
            updateNotification("Запись: $callType")
            Log.d(TAG, "Recording started: ${currentRecordingFile?.absolutePath}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording with VOICE_COMMUNICATION", e)
            tryFallbackRecording()
        }
    }

    private fun tryFallbackRecording() {
        try {
            val recordingsDir = getRecordingsDirectory()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val phoneNumberSafe = currentPhoneNumber?.replace(Regex("[^0-9+]"), "") ?: "unknown"
            val fileName = "${callType}_${phoneNumberSafe}_${timestamp}.m4a"
            currentRecordingFile = File(recordingsDir, fileName)

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(this)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(currentRecordingFile?.absolutePath)

                prepare()
                start()
            }

            isRecording = true
            updateNotification("Запись: $callType (микрофон)")
            Log.d(TAG, "Fallback recording started: ${currentRecordingFile?.absolutePath}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start fallback recording", e)
            cleanupRecorder()
        }
    }

    private fun stopRecordingIfActive() {
        if (!isRecording) {
            Log.d(TAG, "Not recording, nothing to stop")
            return
        }

        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            Log.d(TAG, "Recording stopped: ${currentRecordingFile?.absolutePath}")

            currentRecordingFile?.let { file ->
                if (file.exists() && file.length() > 0) {
                    scheduleUpload(file)
                } else {
                    Log.w(TAG, "Recording file is empty or doesn't exist")
                    file.delete()
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recording", e)
        } finally {
            cleanupRecorder()
            updateNotification("Запись звонков активна")
        }
    }

    private fun cleanupRecorder() {
        mediaRecorder?.release()
        mediaRecorder = null
        isRecording = false
        currentRecordingFile = null
        currentPhoneNumber = null
        callType = "unknown"
    }

    private fun scheduleUpload(file: File) {
        if (preferences.autoUpload) {
            CallUploadWorker.scheduleUpload(this, file.absolutePath)
            Log.d(TAG, "Upload scheduled for: ${file.absolutePath}")
        }
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        stopRecordingIfActive()
        Log.d(TAG, "Service destroyed")
    }
}
