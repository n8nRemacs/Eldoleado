package com.batterycrm.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "BatteryCRM_Session"
        private const val KEY_OPERATOR_ID = "operator_id"
        private const val KEY_TENANT_ID = "tenant_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_NAME = "name"
        private const val KEY_SESSION_TOKEN = "session_token"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_AI_MODE = "ai_mode"
    }

    fun saveSession(operatorId: String, tenantId: String, username: String, name: String?, sessionToken: String) {
        prefs.edit().apply {
            putString(KEY_OPERATOR_ID, operatorId)
            putString(KEY_TENANT_ID, tenantId)
            putString(KEY_USERNAME, username)
            putString(KEY_NAME, name)
            putString(KEY_SESSION_TOKEN, sessionToken)
            putBoolean(KEY_IS_LOGGED_IN, true)
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
}
