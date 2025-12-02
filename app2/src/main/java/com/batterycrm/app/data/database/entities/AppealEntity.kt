package com.batterycrm.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appeals")
data class AppealEntity(
    @PrimaryKey val id: String,
    val number: String? = null,
    val status: String? = null,
    val stage: String? = null,
    val channel: String? = null,
    val channelName: String? = null,
    val clientName: String? = null,
    val clientPhone: String? = null,
    val deviceBrand: String? = null,
    val deviceModel: String? = null,
    val appealType: String? = null,
    val dealTypeName: String? = null,
    val repairTypeName: String? = null,
    val issueTypeName: String? = null,
    val issueName: String? = null,
    val problemDescription: String? = null,
    val partsOwner: String? = null,
    val aiResponse: String? = null,
    val estimatedCost: String? = null,
    val estimatedTime: String? = null,
    val operationMode: String? = null,
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val devicesJson: String? = null, // JSON array of devices with repairs
    val lastAccessed: Long = System.currentTimeMillis(),
    val isCacheValid: Boolean = true,
    val hasFullDetails: Boolean = false // true если загружены детали, а не только список
)
