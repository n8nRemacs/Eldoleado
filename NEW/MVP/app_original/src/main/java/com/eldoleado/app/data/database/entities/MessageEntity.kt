package com.eldoleado.app.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = AppealEntity::class,
            parentColumns = ["id"],
            childColumns = ["appealId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["appealId"])]
)
data class MessageEntity(
    @PrimaryKey val id: String,
    val appealId: String,
    val text: String,
    val senderType: String, // client, operator, agent
    val senderName: String? = null, // Имя клиента или оператора
    val mediaType: String? = null,
    val mediaUrl: String? = null,
    val createdAt: String,
    val lastAccessed: Long = System.currentTimeMillis()
)
