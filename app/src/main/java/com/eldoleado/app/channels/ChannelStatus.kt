package com.eldoleado.app.channels

enum class ChannelStatus {
    NOT_CONFIGURED,  // Gray - not set up
    CHECKING,        // Yellow - verifying status
    CONNECTED,       // Green - working
    ERROR            // Red - session expired or error
}

enum class ChannelType(val displayName: String, val key: String) {
    WHATSAPP("WhatsApp", "whatsapp"),
    TELEGRAM("Telegram", "telegram"),
    TELEGRAM_BOT("Telegram Bot", "telegram_bot"),
    VK("VK", "vk"),
    VK_GROUP("VK Группа", "vk_group"),
    AVITO("Avito", "avito"),
    MAX("MAX", "max")
}
