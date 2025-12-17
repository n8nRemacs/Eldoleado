package com.eldoleado.app.operator.models

import org.json.JSONObject

/**
 * Channel types for omnichannel messaging.
 */
enum class Channel(val id: String, val displayName: String, val icon: String) {
    TELEGRAM("telegram", "Telegram", "TG"),
    WHATSAPP("whatsapp", "WhatsApp", "WA"),
    AVITO("avito", "Avito", "AV"),
    MAX("max", "MAX", "MX"),
    UNKNOWN("unknown", "Unknown", "?");

    companion object {
        fun fromId(id: String): Channel {
            return values().find { it.id == id } ?: UNKNOWN
        }
    }
}

/**
 * Chat/Dialog with a client.
 */
data class Chat(
    val chatId: String,
    val channel: Channel,
    val clientName: String,
    val clientPhone: String?,
    val lastMessage: String,
    val lastMessageTime: Long,
    val unreadCount: Int = 0,
    val serverId: String
) {
    companion object {
        fun fromJson(json: JSONObject): Chat {
            return Chat(
                chatId = json.optString("chat_id", ""),
                channel = Channel.fromId(json.optString("channel", "")),
                clientName = json.optString("sender_name", "Клиент"),
                clientPhone = json.optString("sender_phone"),
                lastMessage = json.optString("text", ""),
                lastMessageTime = System.currentTimeMillis(),
                unreadCount = 0,
                serverId = json.optString("server_id", "")
            )
        }
    }
}

/**
 * Message in a chat.
 */
data class ChatMessage(
    val id: String,
    val chatId: String,
    val channel: Channel,
    val text: String,
    val senderId: String?,
    val senderName: String?,
    val senderPhone: String?,
    val isIncoming: Boolean,
    val timestamp: Long,
    val hasAudio: Boolean = false,
    val transcription: String? = null,
    val hasMedia: Boolean = false,
    val mediaType: String? = null,
    val mediaUrl: String? = null,
    val mediaLocalPath: String? = null,
    val serverId: String
) {
    companion object {
        fun fromJson(json: JSONObject): ChatMessage {
            return ChatMessage(
                id = json.optString("id", System.currentTimeMillis().toString()),
                chatId = json.optString("chat_id", ""),
                channel = Channel.fromId(json.optString("channel", "")),
                text = json.optString("text", ""),
                senderId = json.optString("sender_id"),
                senderName = json.optString("sender_name"),
                senderPhone = json.optString("sender_phone"),
                isIncoming = true, // Messages from server are always incoming
                timestamp = System.currentTimeMillis(),
                hasAudio = json.optBoolean("has_audio", false),
                transcription = json.optString("transcription"),
                hasMedia = json.optBoolean("has_media", false),
                mediaType = json.optString("media_type"),
                mediaUrl = json.optString("media_url"),
                mediaLocalPath = json.optString("media_local_path"),
                serverId = json.optString("server_id", "")
            )
        }
    }
}

/**
 * Draft message for approval.
 */
data class DraftMessage(
    val id: String,
    val chatId: String,
    val channel: Channel,
    val originalText: String,
    val normalizedText: String,
    val timestamp: Long,
    val serverId: String
) {
    companion object {
        fun fromJson(json: JSONObject): DraftMessage {
            return DraftMessage(
                id = json.optString("id", System.currentTimeMillis().toString()),
                chatId = json.optString("chat_id", ""),
                channel = Channel.fromId(json.optString("channel", "")),
                originalText = json.optString("original_text", ""),
                normalizedText = json.optString("normalized_text", ""),
                timestamp = System.currentTimeMillis(),
                serverId = json.optString("server_id", "")
            )
        }
    }
}
