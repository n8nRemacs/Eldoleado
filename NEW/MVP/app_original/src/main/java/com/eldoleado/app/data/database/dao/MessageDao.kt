package com.eldoleado.app.data.database.dao

import androidx.room.*
import com.eldoleado.app.data.database.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE appealId = :appealId ORDER BY createdAt ASC")
    fun getMessagesForAppeal(appealId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE appealId = :appealId ORDER BY createdAt ASC")
    suspend fun getMessagesForAppealList(appealId: String): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: String): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("DELETE FROM messages WHERE appealId = :appealId")
    suspend fun deleteMessagesForAppeal(appealId: String)

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteMessageById(id: String)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()

    // TTL: удалить сообщения для обращений старше threshold
    @Query("""
        DELETE FROM messages
        WHERE appealId IN (
            SELECT id FROM appeals WHERE lastAccessed < :threshold
        )
    """)
    suspend fun deleteOldMessages(threshold: Long)

    // Обновить lastAccessed для всех сообщений обращения
    @Query("UPDATE messages SET lastAccessed = :timestamp WHERE appealId = :appealId")
    suspend fun updateLastAccessedForAppeal(appealId: String, timestamp: Long)

    @Query("SELECT COUNT(*) FROM messages WHERE appealId = :appealId")
    suspend fun getMessagesCountForAppeal(appealId: String): Int

    @Query("SELECT COUNT(*) FROM messages")
    suspend fun getTotalMessagesCount(): Int
}
