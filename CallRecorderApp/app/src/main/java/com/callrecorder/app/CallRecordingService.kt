package com.callrecorder.app

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Foreground service that handles call recording
 */
class CallRecordingService : Service() {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var currentRecordingFile: File? = null
    private var currentPhoneNumber: String? = null
    private var callType: String = "unknown"

    companion object {
        private const val TAG = "CallRecordingService"
        private const val NOTIFICATION_ID = 1001

        const val ACTION_START_SERVICE = "com.callrecorder.START_SERVICE"
        const val ACTION_STOP_SERVICE = "com.callrecorder.STOP_SERVICE"
        const val ACTION_START_RECORDING = "com.callrecorder.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.callrecorder.STOP_RECORDING"

        const val EXTRA_PHONE_NUMBER = "phone_number"
        const val EXTRA_CALL_TYPE = "call_type"

        const val CALL_TYPE_INCOMING = "incoming"
        const val CALL_TYPE_OUTGOING = "outgoing"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
    }

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

    private fun startForegroundService() {
        val notification = createNotification("Call Recorder is running")
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "Foreground service started")
    }

    private fun createNotification(contentText: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CallRecorderApp.CHANNEL_RECORDING)
            .setContentTitle("Call Recorder")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun startRecording() {
        if (!CallRecorderApp.instance.preferences.isRecordingEnabled) {
            Log.d(TAG, "Recording is disabled in settings")
            return
        }

        if (isRecording) {
            Log.d(TAG, "Already recording, skipping")
            return
        }

        try {
            val recordingsDir = CallRecorderApp.instance.getRecordingsDirectory()
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
                // Use VOICE_CALL for recording phone calls
                // Note: This may not work on all devices due to manufacturer restrictions
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
            updateNotification("Recording: $callType call")
            Log.d(TAG, "Recording started: ${currentRecordingFile?.absolutePath}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording", e)
            // Try with MIC source as fallback
            tryFallbackRecording()
        }
    }

    private fun tryFallbackRecording() {
        try {
            val recordingsDir = CallRecorderApp.instance.getRecordingsDirectory()
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
                // Fallback to MIC source
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
            updateNotification("Recording: $callType call (MIC)")
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

            // Schedule upload if auto-upload is enabled
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
            updateNotification("Call Recorder is running")
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
        if (CallRecorderApp.instance.preferences.autoUpload) {
            UploadWorker.scheduleUpload(this, file.absolutePath)
            Log.d(TAG, "Upload scheduled for: ${file.absolutePath}")
        }
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecordingIfActive()
        Log.d(TAG, "Service destroyed")
    }
}
