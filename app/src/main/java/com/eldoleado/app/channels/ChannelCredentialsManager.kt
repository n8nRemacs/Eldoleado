package com.eldoleado.app.channels

import android.content.Context
import android.content.SharedPreferences

class ChannelCredentialsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "channel_credentials"

        // Telegram Bot
        private const val KEY_TG_BOT_TOKEN = "tg_bot_token"
        private const val KEY_TG_BOT_USERNAME = "tg_bot_username"
        private const val KEY_TG_BOT_STATUS = "tg_bot_status"

        // Telegram User
        private const val KEY_TG_USER_API_ID = "tg_user_api_id"
        private const val KEY_TG_USER_API_HASH = "tg_user_api_hash"
        private const val KEY_TG_USER_PHONE = "tg_user_phone"
        private const val KEY_TG_USER_SESSION = "tg_user_session"
        private const val KEY_TG_USER_STATUS = "tg_user_status"

        // Telegram type (bot or user)
        private const val KEY_TG_TYPE = "tg_type"
        const val TG_TYPE_BOT = "bot"
        const val TG_TYPE_USER = "user"

        // WhatsApp
        private const val KEY_WA_SESSION = "wa_session"
        private const val KEY_WA_PHONE = "wa_phone"
        private const val KEY_WA_NAME = "wa_name"
        private const val KEY_WA_STATUS = "wa_status"

        // Avito
        private const val KEY_AVITO_SESSID = "avito_sessid"
        private const val KEY_AVITO_USER_ID = "avito_user_id"
        private const val KEY_AVITO_EMAIL = "avito_email"
        private const val KEY_AVITO_STATUS = "avito_status"

        // MAX
        private const val KEY_MAX_TOKEN = "max_token"
        private const val KEY_MAX_USER_ID = "max_user_id"
        private const val KEY_MAX_NAME = "max_name"
        private const val KEY_MAX_STATUS = "max_status"

        // VK
        private const val KEY_VK_TOKEN = "vk_token"
        private const val KEY_VK_USER_ID = "vk_user_id"
        private const val KEY_VK_NAME = "vk_name"
        private const val KEY_VK_STATUS = "vk_status"

        // VK Group
        private const val KEY_VK_GROUP_TOKEN = "vk_group_token"
        private const val KEY_VK_GROUP_ID = "vk_group_id"
        private const val KEY_VK_GROUP_NAME = "vk_group_name"
        private const val KEY_VK_GROUP_STATUS = "vk_group_status"

        // Telegram Bot (separate from Telegram User)
        private const val KEY_TG_BOT_STANDALONE_TOKEN = "tg_bot_standalone_token"
        private const val KEY_TG_BOT_STANDALONE_USERNAME = "tg_bot_standalone_username"
        private const val KEY_TG_BOT_STANDALONE_STATUS = "tg_bot_standalone_status"

        // Alert notifications
        private const val KEY_ALERT_BOT_TOKEN = "alert_bot_token"
        private const val KEY_ALERT_CHAT_ID = "alert_chat_id"
        private const val KEY_ALERT_BATTERY = "alert_battery_enabled"
        private const val KEY_ALERT_NETWORK = "alert_network_enabled"
        private const val KEY_ALERT_CHANNELS = "alert_channels_enabled"
    }

    // ==================== TELEGRAM ====================

    fun getTelegramType(): String? = prefs.getString(KEY_TG_TYPE, null)

    fun setTelegramType(type: String) {
        prefs.edit().putString(KEY_TG_TYPE, type).apply()
    }

    // Telegram Bot
    fun saveTelegramBot(token: String, username: String) {
        prefs.edit().apply {
            putString(KEY_TG_BOT_TOKEN, token)
            putString(KEY_TG_BOT_USERNAME, username)
            putString(KEY_TG_TYPE, TG_TYPE_BOT)
            putString(KEY_TG_BOT_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getTelegramBotToken(): String? = prefs.getString(KEY_TG_BOT_TOKEN, null)
    fun getTelegramBotUsername(): String? = prefs.getString(KEY_TG_BOT_USERNAME, null)

    // Telegram User
    fun saveTelegramUser(apiId: String, apiHash: String, phone: String, session: String?) {
        prefs.edit().apply {
            putString(KEY_TG_USER_API_ID, apiId)
            putString(KEY_TG_USER_API_HASH, apiHash)
            putString(KEY_TG_USER_PHONE, phone)
            session?.let { putString(KEY_TG_USER_SESSION, it) }
            putString(KEY_TG_TYPE, TG_TYPE_USER)
            putString(KEY_TG_USER_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getTelegramUserApiId(): String? = prefs.getString(KEY_TG_USER_API_ID, null)
    fun getTelegramUserApiHash(): String? = prefs.getString(KEY_TG_USER_API_HASH, null)
    fun getTelegramUserPhone(): String? = prefs.getString(KEY_TG_USER_PHONE, null)

    fun getTelegramStatus(): ChannelStatus {
        val type = getTelegramType() ?: return ChannelStatus.NOT_CONFIGURED
        val statusKey = if (type == TG_TYPE_BOT) KEY_TG_BOT_STATUS else KEY_TG_USER_STATUS
        val statusStr = prefs.getString(statusKey, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setTelegramStatus(status: ChannelStatus) {
        val type = getTelegramType() ?: return
        val statusKey = if (type == TG_TYPE_BOT) KEY_TG_BOT_STATUS else KEY_TG_USER_STATUS
        prefs.edit().putString(statusKey, status.name).apply()
    }

    fun getTelegramDisplayInfo(): String? {
        val type = getTelegramType() ?: return null
        return if (type == TG_TYPE_BOT) {
            getTelegramBotUsername()?.let { "@$it" }
        } else {
            getTelegramUserPhone()
        }
    }

    fun clearTelegram() {
        prefs.edit().apply {
            remove(KEY_TG_BOT_TOKEN)
            remove(KEY_TG_BOT_USERNAME)
            remove(KEY_TG_BOT_STATUS)
            remove(KEY_TG_USER_API_ID)
            remove(KEY_TG_USER_API_HASH)
            remove(KEY_TG_USER_PHONE)
            remove(KEY_TG_USER_SESSION)
            remove(KEY_TG_USER_STATUS)
            remove(KEY_TG_TYPE)
            apply()
        }
    }

    // ==================== WHATSAPP ====================

    fun saveWhatsApp(session: String, phone: String, name: String?) {
        prefs.edit().apply {
            putString(KEY_WA_SESSION, session)
            putString(KEY_WA_PHONE, phone)
            name?.let { putString(KEY_WA_NAME, it) }
            putString(KEY_WA_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getWhatsAppPhone(): String? = prefs.getString(KEY_WA_PHONE, null)
    fun getWhatsAppName(): String? = prefs.getString(KEY_WA_NAME, null)
    fun getWhatsAppSession(): String? = prefs.getString(KEY_WA_SESSION, null)

    fun getWhatsAppStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_WA_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setWhatsAppStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_WA_STATUS, status.name).apply()
    }

    fun getWhatsAppDisplayInfo(): String? {
        return getWhatsAppName() ?: getWhatsAppPhone()
    }

    fun clearWhatsApp() {
        prefs.edit().apply {
            remove(KEY_WA_SESSION)
            remove(KEY_WA_PHONE)
            remove(KEY_WA_NAME)
            remove(KEY_WA_STATUS)
            apply()
        }
    }

    // ==================== AVITO ====================

    fun saveAvito(sessid: String, userId: String?, email: String?) {
        prefs.edit().apply {
            putString(KEY_AVITO_SESSID, sessid)
            userId?.let { putString(KEY_AVITO_USER_ID, it) }
            email?.let { putString(KEY_AVITO_EMAIL, it) }
            putString(KEY_AVITO_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getAvitoSessid(): String? = prefs.getString(KEY_AVITO_SESSID, null)
    fun getAvitoUserId(): String? = prefs.getString(KEY_AVITO_USER_ID, null)
    fun getAvitoEmail(): String? = prefs.getString(KEY_AVITO_EMAIL, null)

    fun getAvitoStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_AVITO_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setAvitoStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_AVITO_STATUS, status.name).apply()
    }

    fun getAvitoDisplayInfo(): String? {
        return getAvitoEmail() ?: getAvitoUserId()
    }

    fun clearAvito() {
        prefs.edit().apply {
            remove(KEY_AVITO_SESSID)
            remove(KEY_AVITO_USER_ID)
            remove(KEY_AVITO_EMAIL)
            remove(KEY_AVITO_STATUS)
            apply()
        }
    }

    // ==================== MAX ====================

    fun saveMax(token: String, userId: String?, name: String?) {
        prefs.edit().apply {
            putString(KEY_MAX_TOKEN, token)
            userId?.let { putString(KEY_MAX_USER_ID, it) }
            name?.let { putString(KEY_MAX_NAME, it) }
            putString(KEY_MAX_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getMaxToken(): String? = prefs.getString(KEY_MAX_TOKEN, null)
    fun getMaxUserId(): String? = prefs.getString(KEY_MAX_USER_ID, null)
    fun getMaxName(): String? = prefs.getString(KEY_MAX_NAME, null)

    fun getMaxStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_MAX_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setMaxStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_MAX_STATUS, status.name).apply()
    }

    fun getMaxDisplayInfo(): String? {
        return getMaxName() ?: getMaxUserId()
    }

    fun clearMax() {
        prefs.edit().apply {
            remove(KEY_MAX_TOKEN)
            remove(KEY_MAX_USER_ID)
            remove(KEY_MAX_NAME)
            remove(KEY_MAX_STATUS)
            apply()
        }
    }

    // ==================== ALERT NOTIFICATIONS ====================

    fun saveAlertSettings(botToken: String?, chatId: String?, battery: Boolean, network: Boolean, channels: Boolean) {
        prefs.edit().apply {
            botToken?.let { putString(KEY_ALERT_BOT_TOKEN, it) }
            chatId?.let { putString(KEY_ALERT_CHAT_ID, it) }
            putBoolean(KEY_ALERT_BATTERY, battery)
            putBoolean(KEY_ALERT_NETWORK, network)
            putBoolean(KEY_ALERT_CHANNELS, channels)
            apply()
        }
    }

    fun getAlertBotToken(): String? = prefs.getString(KEY_ALERT_BOT_TOKEN, null)
    fun getAlertChatId(): String? = prefs.getString(KEY_ALERT_CHAT_ID, null)
    fun isAlertBatteryEnabled(): Boolean = prefs.getBoolean(KEY_ALERT_BATTERY, true)
    fun isAlertNetworkEnabled(): Boolean = prefs.getBoolean(KEY_ALERT_NETWORK, true)
    fun isAlertChannelsEnabled(): Boolean = prefs.getBoolean(KEY_ALERT_CHANNELS, true)

    // ==================== VK ====================

    fun saveVk(token: String, userId: String?, name: String?) {
        prefs.edit().apply {
            putString(KEY_VK_TOKEN, token)
            userId?.let { putString(KEY_VK_USER_ID, it) }
            name?.let { putString(KEY_VK_NAME, it) }
            putString(KEY_VK_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getVkToken(): String? = prefs.getString(KEY_VK_TOKEN, null)
    fun getVkUserId(): String? = prefs.getString(KEY_VK_USER_ID, null)
    fun getVkName(): String? = prefs.getString(KEY_VK_NAME, null)

    fun getVkStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_VK_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setVkStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_VK_STATUS, status.name).apply()
    }

    fun getVkDisplayInfo(): String? {
        return getVkName() ?: getVkUserId()
    }

    fun clearVk() {
        prefs.edit().apply {
            remove(KEY_VK_TOKEN)
            remove(KEY_VK_USER_ID)
            remove(KEY_VK_NAME)
            remove(KEY_VK_STATUS)
            apply()
        }
    }

    // ==================== VK GROUP ====================

    fun saveVkGroup(token: String, groupId: String?, name: String?) {
        prefs.edit().apply {
            putString(KEY_VK_GROUP_TOKEN, token)
            groupId?.let { putString(KEY_VK_GROUP_ID, it) }
            name?.let { putString(KEY_VK_GROUP_NAME, it) }
            putString(KEY_VK_GROUP_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getVkGroupToken(): String? = prefs.getString(KEY_VK_GROUP_TOKEN, null)
    fun getVkGroupId(): String? = prefs.getString(KEY_VK_GROUP_ID, null)
    fun getVkGroupName(): String? = prefs.getString(KEY_VK_GROUP_NAME, null)

    fun getVkGroupStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_VK_GROUP_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setVkGroupStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_VK_GROUP_STATUS, status.name).apply()
    }

    fun getVkGroupDisplayInfo(): String? {
        return getVkGroupName() ?: getVkGroupId()
    }

    fun clearVkGroup() {
        prefs.edit().apply {
            remove(KEY_VK_GROUP_TOKEN)
            remove(KEY_VK_GROUP_ID)
            remove(KEY_VK_GROUP_NAME)
            remove(KEY_VK_GROUP_STATUS)
            apply()
        }
    }

    // ==================== TELEGRAM BOT (Standalone) ====================

    fun saveTelegramBotStandalone(token: String, username: String) {
        prefs.edit().apply {
            putString(KEY_TG_BOT_STANDALONE_TOKEN, token)
            putString(KEY_TG_BOT_STANDALONE_USERNAME, username)
            putString(KEY_TG_BOT_STANDALONE_STATUS, ChannelStatus.CONNECTED.name)
            apply()
        }
    }

    fun getTelegramBotStandaloneToken(): String? = prefs.getString(KEY_TG_BOT_STANDALONE_TOKEN, null)
    fun getTelegramBotStandaloneUsername(): String? = prefs.getString(KEY_TG_BOT_STANDALONE_USERNAME, null)

    fun getTelegramBotStandaloneStatus(): ChannelStatus {
        val statusStr = prefs.getString(KEY_TG_BOT_STANDALONE_STATUS, null) ?: return ChannelStatus.NOT_CONFIGURED
        return try {
            ChannelStatus.valueOf(statusStr)
        } catch (e: Exception) {
            ChannelStatus.NOT_CONFIGURED
        }
    }

    fun setTelegramBotStandaloneStatus(status: ChannelStatus) {
        prefs.edit().putString(KEY_TG_BOT_STANDALONE_STATUS, status.name).apply()
    }

    fun getTelegramBotStandaloneDisplayInfo(): String? {
        return getTelegramBotStandaloneUsername()?.let { "@$it" }
    }

    fun clearTelegramBotStandalone() {
        prefs.edit().apply {
            remove(KEY_TG_BOT_STANDALONE_TOKEN)
            remove(KEY_TG_BOT_STANDALONE_USERNAME)
            remove(KEY_TG_BOT_STANDALONE_STATUS)
            apply()
        }
    }

    // ==================== UTILITY ====================

    fun getChannelStatus(channel: ChannelType): ChannelStatus {
        return when (channel) {
            ChannelType.WHATSAPP -> getWhatsAppStatus()
            ChannelType.TELEGRAM -> getTelegramStatus()
            ChannelType.TELEGRAM_BOT -> getTelegramBotStandaloneStatus()
            ChannelType.VK -> getVkStatus()
            ChannelType.VK_GROUP -> getVkGroupStatus()
            ChannelType.AVITO -> getAvitoStatus()
            ChannelType.MAX -> getMaxStatus()
        }
    }

    fun getChannelDisplayInfo(channel: ChannelType): String? {
        return when (channel) {
            ChannelType.WHATSAPP -> getWhatsAppDisplayInfo()
            ChannelType.TELEGRAM -> getTelegramDisplayInfo()
            ChannelType.TELEGRAM_BOT -> getTelegramBotStandaloneDisplayInfo()
            ChannelType.VK -> getVkDisplayInfo()
            ChannelType.VK_GROUP -> getVkGroupDisplayInfo()
            ChannelType.AVITO -> getAvitoDisplayInfo()
            ChannelType.MAX -> getMaxDisplayInfo()
        }
    }

    fun clearChannel(channel: ChannelType) {
        when (channel) {
            ChannelType.WHATSAPP -> clearWhatsApp()
            ChannelType.TELEGRAM -> clearTelegram()
            ChannelType.TELEGRAM_BOT -> clearTelegramBotStandalone()
            ChannelType.VK -> clearVk()
            ChannelType.VK_GROUP -> clearVkGroup()
            ChannelType.AVITO -> clearAvito()
            ChannelType.MAX -> clearMax()
        }
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
