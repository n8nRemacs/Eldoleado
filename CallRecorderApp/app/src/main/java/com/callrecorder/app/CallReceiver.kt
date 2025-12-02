package com.callrecorder.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log

/**
 * BroadcastReceiver that listens for phone call state changes
 * and triggers recording start/stop
 */
class CallReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CallReceiver"

        // Track call state across broadcasts
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var isIncoming = false
        private var savedNumber: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.action}")

        when (intent.action) {
            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                handleIncomingCall(context, intent)
            }
            Intent.ACTION_NEW_OUTGOING_CALL -> {
                handleOutgoingCall(context, intent)
            }
        }
    }

    private fun handleIncomingCall(context: Context, intent: Intent) {
        val stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        Log.d(TAG, "Phone state changed: $stateStr, number: $number")

        val state = when (stateStr) {
            TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
            TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
            TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
            else -> return
        }

        onCallStateChanged(context, state, number)
    }

    private fun handleOutgoingCall(context: Context, intent: Intent) {
        val number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        Log.d(TAG, "Outgoing call to: $number")
        savedNumber = number
        isIncoming = false
    }

    private fun onCallStateChanged(context: Context, state: Int, number: String?) {
        // Save number if provided
        if (!number.isNullOrEmpty()) {
            savedNumber = number
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                // Incoming call ringing
                isIncoming = true
                savedNumber = number
                Log.d(TAG, "Incoming call ringing from: $number")
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // Call answered (either incoming or outgoing)
                if (lastState == TelephonyManager.CALL_STATE_IDLE) {
                    // Outgoing call started
                    isIncoming = false
                }

                val callType = if (isIncoming) {
                    CallRecordingService.CALL_TYPE_INCOMING
                } else {
                    CallRecordingService.CALL_TYPE_OUTGOING
                }

                Log.d(TAG, "Call started - Type: $callType, Number: $savedNumber")
                startRecording(context, savedNumber, callType)
            }

            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended
                if (lastState == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Log.d(TAG, "Call ended")
                    stopRecording(context)
                }

                // Reset state
                isIncoming = false
                savedNumber = null
            }
        }

        lastState = state
    }

    private fun startRecording(context: Context, phoneNumber: String?, callType: String) {
        val intent = Intent(context, CallRecordingService::class.java).apply {
            action = CallRecordingService.ACTION_START_RECORDING
            putExtra(CallRecordingService.EXTRA_PHONE_NUMBER, phoneNumber)
            putExtra(CallRecordingService.EXTRA_CALL_TYPE, callType)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    private fun stopRecording(context: Context) {
        val intent = Intent(context, CallRecordingService::class.java).apply {
            action = CallRecordingService.ACTION_STOP_RECORDING
        }
        context.startService(intent)
    }
}
