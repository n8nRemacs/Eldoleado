package com.eldoleado.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Dialog (chat with client) - main entity for messenger
 */
@Entity(tableName = "dialogs")
data class DialogEntity(
    @PrimaryKey val id: String,

    // Client info
    val clientName: String? = null,
    val clientPhone: String? = null,
    val clientAvatar: String? = null,

    // Channel info
    val channel: String,  // telegram, whatsapp, avito, etc.
    val chatId: String,   // External chat ID from channel

    // Last message preview
    val lastMessageText: String? = null,
    val lastMessageTime: Long = 0,
    val lastMessageIsVoice: Boolean = false,

    // Unread count
    val unreadCount: Int = 0,

    // Metadata
    val tenantId: String? = null,
    val operatorId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
