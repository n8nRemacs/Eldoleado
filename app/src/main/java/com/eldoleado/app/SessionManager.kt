package com.eldoleado.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "Eldoleado_Session"
        private const val KEY_OPERATOR_ID = "operator_id"
        private const val KEY_TENANT_ID = "tenant_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_NAME = "name"
        private const val KEY_SESSION_TOKEN = "session_token"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_AI_MODE = "ai_mode"
        private const val KEY_APP_MODE = "app_mode"
        private const val KEY_TUNNEL_URL = "tunnel_url"
        private const val KEY_TUNNEL_SECRET = "tunnel_secret"

        // App modes
        const val MODE_CLIENT = "client"       // Only messenger UI
        const val MODE_SERVER = "server"       // Only tunnel proxy
        const val MODE_BOTH = "both"           // Messenger + tunnel
    }

    fun saveSession(
        operatorId: String,
        tenantId: String,
        username: String,
        name: String?,
        sessionToken: String,
        appMode: String? = null,
        tunnelUrl: String? = null,
        tunnelSecret: String? = null
    ) {
        prefs.edit().apply {
            putString(KEY_OPERATOR_ID, operatorId)
            putString(KEY_TENANT_ID, tenantId)
            putString(KEY_USERNAME, username)
            putString(KEY_NAME, name)
            putString(KEY_SESSION_TOKEN, sessionToken)
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_APP_MODE, appMode ?: MODE_CLIENT)
            tunnelUrl?.let { putString(KEY_TUNNEL_URL, it) }
            tunnelSecret?.let { putString(KEY_TUNNEL_SECRET, it) }
            apply()
        }
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getOperatorId(): String? = prefs.getString(KEY_OPERATOR_ID, null)

    fun getTenantId(): String? = prefs.getString(KEY_TENANT_ID, null)

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun getName(): String? = prefs.getString(KEY_NAME, null)

    fun getSessionToken(): String? = prefs.getString(KEY_SESSION_TOKEN, null)

    fun saveAiMode(mode: String) {
        prefs.edit().putString(KEY_AI_MODE, mode).apply()
    }

    fun getAiMode(): String = prefs.getString(KEY_AI_MODE, "semi_automatic") ?: "semi_automatic"

    fun saveAppMode(mode: String) {
        prefs.edit().putString(KEY_APP_MODE, mode).apply()
    }

    fun getAppMode(): String = prefs.getString(KEY_APP_MODE, MODE_CLIENT) ?: MODE_CLIENT

    fun isClientEnabled(): Boolean {
        val mode = getAppMode()
        return mode == MODE_CLIENT || mode == MODE_BOTH
    }

    fun isServerEnabled(): Boolean {
        val mode = getAppMode()
        return mode == MODE_SERVER || mode == MODE_BOTH
    }

    fun getTunnelUrl(): String? = prefs.getString(KEY_TUNNEL_URL, null)

    fun getTunnelSecret(): String? = prefs.getString(KEY_TUNNEL_SECRET, null)
}
