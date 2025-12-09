package com.eldoleado.app.callrecording

import android.content.Context
import android.content.SharedPreferences

class CallRecordingPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "call_recording_prefs"
        private const val KEY_RECORDING_ENABLED = "recording_enabled"
        private const val KEY_SERVER_URL = "server_url"
        private const val KEY_AUTO_UPLOAD = "auto_upload"
        private const val KEY_DELETE_AFTER_UPLOAD = "delete_after_upload"
        private const val DEFAULT_SERVER_URL = "https://android-api.eldoleado.ru/api/voice/upload"
    }

    var isRecordingEnabled: Boolean
        get() = prefs.getBoolean(KEY_RECORDING_ENABLED, false)
        set(value) = prefs.edit().putBoolean(KEY_RECORDING_ENABLED, value).apply()

    var serverUrl: String
        get() = prefs.getString(KEY_SERVER_URL, DEFAULT_SERVER_URL) ?: DEFAULT_SERVER_URL
        set(value) = prefs.edit().putString(KEY_SERVER_URL, value).apply()

    var autoUpload: Boolean
        get() = prefs.getBoolean(KEY_AUTO_UPLOAD, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_UPLOAD, value).apply()

    var deleteAfterUpload: Boolean
        get() = prefs.getBoolean(KEY_DELETE_AFTER_UPLOAD, true)
        set(value) = prefs.edit().putBoolean(KEY_DELETE_AFTER_UPLOAD, value).apply()
}
