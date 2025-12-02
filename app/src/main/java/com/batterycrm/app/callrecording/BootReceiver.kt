package com.batterycrm.app.callrecording

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON" ||
            intent.action == "com.htc.intent.action.QUICKBOOT_POWERON") {

            val prefs = CallRecordingPreferences(context)
            if (!prefs.isRecordingEnabled) {
                Log.d(TAG, "Recording is disabled, not starting service")
                return
            }

            Log.d(TAG, "Device booted, starting CallRecordingService")

            val serviceIntent = Intent(context, CallRecordingService::class.java).apply {
                action = CallRecordingService.ACTION_START_SERVICE
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
                Log.d(TAG, "CallRecordingService started successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to start CallRecordingService", e)
            }
        }
    }
}
