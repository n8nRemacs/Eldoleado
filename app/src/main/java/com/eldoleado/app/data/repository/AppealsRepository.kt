package com.eldoleado.app.data.repository

import android.util.Log
import com.google.gson.Gson
import com.eldoleado.app.api.*
import com.eldoleado.app.data.database.dao.AppealDao
import com.eldoleado.app.data.database.dao.MessageDao
import com.eldoleado.app.data.database.entities.AppealEntity
import com.eldoleado.app.data.database.entities.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class AppealsRepository(
    private val appealDao: AppealDao,
    private val messageDao: MessageDao,
    private val apiService: ApiService
) {
    companion object {
        private const val TAG = "AppealsRepository"
        private const val TTL_DAYS = 7
        private const val TTL_MILLIS = TTL_DAYS * 24 * 60 * 60 * 1000L
    }

    // ========== APPEALS LIST ==========

    fun getAppealsFlow(): Flow<List<AppealEntity>> {
        return appealDao.getAllAppeals()
    }

    suspend fun getAppealsList(): List<AppealEntity> {
        return appealDao.getAllAppealsList()
    }

    suspend fun refreshAppeals(operatorId: String): Result<List<AppealEntity>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAppealsList(operatorId).awaitResponse()
                if (response.isSuccessful && response.body()?.success == true) {
                    val appeals = response.body()?.appeals ?: emptyList()
                    val entities = appeals.map { it.toEntity() }
                    appealDao.insertAppeals(entities)
                    Log.d(TAG, "Refreshed ${entities.size} appeals from server")
                    Result.success(entities)
                } else {
                    Log.e(TAG, "Failed to refresh appeals: ${response.code()}")
                    Result.failure(Exception("Failed to load appeals: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing appeals", e)
                Result.failure(e)
            }
        }
    }

    // ========== APPEAL DETAILS ==========

    suspend fun getAppealDetail(appealId: String): AppealDetailState {
        return withContext(Dispatchers.IO) {
            // Сначала проверяем кэш
            val cached = appealDao.getAppealById(appealId)
            if (cached != null && cached.isCacheValid && cached.hasFullDetails) {
                // Обновляем lastAccessed
                appealDao.updateLastAccessed(appealId, System.currentTimeMillis())
                messageDao.updateLastAccessedForAppeal(appealId, System.currentTimeMillis())

                val messages = messageDao.getMessagesForAppealList(appealId)
                Log.d(TAG, "Returning cached appeal detail: $appealId with ${messages.size} messages")
                return@withContext AppealDetailState.Success(cached, messages)
            }

            // Загружаем с сервера
            loadAppealDetailFromNetwork(appealId)
        }
    }

    fun getAppealDetailFlow(appealId: String): Flow<AppealEntity?> {
        return appealDao.getAppealByIdFlow(appealId)
    }

    fun getMessagesFlow(appealId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessagesForAppeal(appealId)
    }

    private suspend fun loadAppealDetailFromNetwork(appealId: String): AppealDetailState {
        return try {
            val response = apiService.getAppealDetail(appealId).awaitResponse()
            if (response.isSuccessful && response.body()?.success == true) {
                val body = response.body()!!
                val appealEntity = body.toAppealEntity()
                val clientName = appealEntity.clientName
                val messageEntities = body.messages?.map { it.toEntity(appealId, clientName) } ?: emptyList()

                // Сохраняем в БД
                appealDao.insertAppeal(appealEntity)
                appealDao.markAsFullyLoaded(appealId)
                messageDao.deleteMessagesForAppeal(appealId)
                messageDao.insertMessages(messageEntities)

                Log.d(TAG, "Loaded appeal detail from network: $appealId with ${messageEntities.size} messages")
                AppealDetailState.Success(appealEntity, messageEntities)
            } else {
                Log.e(TAG, "Failed to load appeal detail: ${response.code()}")
                AppealDetailState.Error("Failed to load appeal: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading appeal detail", e)
            AppealDetailState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun forceRefreshAppealDetail(appealId: String): AppealDetailState {
        return withContext(Dispatchers.IO) {
            loadAppealDetailFromNetwork(appealId)
        }
    }

    // ========== CACHE INVALIDATION ==========

    suspend fun invalidateAppealCache(appealId: String) {
        withContext(Dispatchers.IO) {
            appealDao.invalidateCache(appealId)
            Log.d(TAG, "Invalidated cache for appeal: $appealId")
        }
    }

    suspend fun invalidateAllCache() {
        withContext(Dispatchers.IO) {
            appealDao.invalidateAllCache()
            Log.d(TAG, "Invalidated all appeals cache")
        }
    }

    // ========== TTL CLEANUP ==========

    suspend fun cleanupOldMessages() {
        withContext(Dispatchers.IO) {
            val threshold = System.currentTimeMillis() - TTL_MILLIS
            messageDao.deleteOldMessages(threshold)
            Log.d(TAG, "Cleaned up messages older than $TTL_DAYS days")
        }
    }

    // ========== MAPPING FUNCTIONS ==========

    private fun Appeal.toEntity(): AppealEntity {
        return AppealEntity(
            id = this.id ?: "",
            number = this.number,
            status = this.status,
            stage = this.stage,
            channel = this.channel,
            channelName = this.channel_name,
            clientName = this.client?.name,
            clientPhone = this.client?.phone,
            deviceBrand = this.device?.brand,
            deviceModel = this.device?.model,
            appealType = this.appeal_type,
            repairTypeName = this.repair_type_name,
            issueTypeName = this.issue_type_name,
            issueName = this.issue_name,
            problemDescription = this.problem_description,
            partsOwner = this.parts_owner,
            aiResponse = this.ai_response,
            updatedAt = this.updated_at,
            createdAt = this.created_at,
            lastAccessed = System.currentTimeMillis(),
            isCacheValid = true,
            hasFullDetails = false
        )
    }

    private fun AppealDetailResponse.toAppealEntity(): AppealEntity {
        val appeal = this.appeal
        // Сериализуем devices в JSON для кэширования
        val devicesJson = if (!this.devices.isNullOrEmpty()) {
            Gson().toJson(this.devices)
        } else null

        return AppealEntity(
            id = appeal.id ?: "",
            status = appeal.appeal_status,
            stage = appeal.stage,
            channel = appeal.channel,
            channelName = appeal.channel_name,
            clientName = this.client?.name,
            clientPhone = this.client?.phone,
            deviceBrand = this.device?.brand,
            deviceModel = this.device?.model,
            appealType = appeal.appeal_type,
            dealTypeName = appeal.deal_type_name,
            repairTypeName = appeal.repair_type_name,
            issueTypeName = appeal.issue_type_name,
            issueName = appeal.issue_name,
            problemDescription = appeal.problem_description,
            partsOwner = appeal.parts_owner,
            aiResponse = this.ai_response?.response_text ?: appeal.ai_response,
            estimatedCost = appeal.estimated_cost?.toString(),
            estimatedTime = appeal.estimated_time,
            operationMode = appeal.operation_mode,
            devicesJson = devicesJson,
            updatedAt = appeal.updated_at,
            createdAt = appeal.created_at,
            lastAccessed = System.currentTimeMillis(),
            isCacheValid = true,
            hasFullDetails = true
        )
    }

    private fun Message.toEntity(appealId: String, clientName: String? = null): MessageEntity {
        // Генерируем id если его нет (на основе appealId + createdAt + text hash)
        val id = this.id ?: "${appealId}_${this.created_at}_${this.text.hashCode()}"
        // Для клиентских сообщений используем имя клиента, для операторских - имя из API
        val resolvedSenderName = when (this.sender_type?.lowercase()) {
            "client" -> this.sender_name ?: clientName
            else -> this.sender_name
        }
        return MessageEntity(
            id = id,
            appealId = appealId,
            text = this.text ?: "",
            senderType = this.sender_type ?: "unknown",
            senderName = resolvedSenderName,
            mediaType = this.media_type,
            mediaUrl = this.media_url,
            createdAt = this.created_at ?: "",
            lastAccessed = System.currentTimeMillis()
        )
    }
}

sealed class AppealDetailState {
    object Loading : AppealDetailState()
    data class Success(val appeal: AppealEntity, val messages: List<MessageEntity>) : AppealDetailState()
    data class Error(val message: String) : AppealDetailState()
}

// Extension function для получения devices из JSON
fun AppealEntity.getDevices(): List<AppealDeviceDto> {
    return if (!devicesJson.isNullOrEmpty()) {
        try {
            Gson().fromJson(devicesJson, Array<AppealDeviceDto>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    } else {
        emptyList()
    }
}
