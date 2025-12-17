package com.eldoleado.app.channels

enum class ChannelStatus {
    NOT_CONFIGURED,  // Gray - not set up
    CHECKING,        // Yellow - verifying status
    CONNECTED,       // Green - working
    ERROR            // Red - session expired or error
}

enum class ChannelType(val displayName: String, val key: String) {
    TELEGRAM("Telegram", "telegram"),
    WHATSAPP("WhatsApp", "whatsapp"),
    AVITO("Avito", "avito"),
    MAX("MAX", "max")
}
