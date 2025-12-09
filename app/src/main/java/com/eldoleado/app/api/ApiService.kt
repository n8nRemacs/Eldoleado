package com.eldoleado.app.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // API Gateway endpoints (2025-12-09)
    // Base URL: https://android-api.eldoleado.ru/

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/appeals")
    fun getAppealsList(
        @Query("operator_id") operatorId: String,
        @Query("status") status: String? = null,
        @Query("limit") limit: Int = 20
    ): Call<AppealsListResponse>

    @GET("api/appeals/{appeal_id}")
    fun getAppealDetail(
        @Path("appeal_id") appealId: String,
        @Query("limit") limit: Int = 50
    ): Call<AppealDetailResponse>

    @POST("api/appeals/{appeal_id}/send")
    fun sendResponse(
        @Path("appeal_id") appealId: String,
        @Body request: SendMessageRequest
    ): Call<ApiResponse>

    @POST("api/appeals/{appeal_id}/normalize")
    fun normalizeText(
        @Path("appeal_id") appealId: String,
        @Body request: NormalizeRequest
    ): Call<NormalizeResponse>

    @POST("api/appeals/{appeal_id}/take")
    fun takeAppeal(
        @Path("appeal_id") appealId: String,
        @Body request: TakeAppealRequest
    ): Call<ApiResponse>

    @POST("api/appeals/{appeal_id}/reject")
    fun rejectAiResponse(
        @Path("appeal_id") appealId: String,
        @Body request: RejectRequest
    ): Call<ApiResponse>

    @POST("api/appeals/{appeal_id}/promo")
    fun sendPromo(
        @Path("appeal_id") appealId: String,
        @Body request: PromoRequest
    ): Call<ApiResponse>

    @POST("api/auth/logout")
    fun logout(): Call<ApiResponse>

    @POST("api/fcm/register")
    fun registerFCMToken(
        @Body request: FCMTokenRegisterRequest
    ): Call<FCMTokenResponse>

    @POST("api/settings")
    fun updateSettings(
        @Body request: UpdateSettingsRequest
    ): Call<ApiResponse>

    @POST("api/appeals/{appeal_id}/mode")
    fun updateAppealMode(
        @Path("appeal_id") appealId: String,
        @Body request: UpdateAppealModeRequest
    ): Call<ApiResponse>

    // ========== DEVICE CRUD ==========

    @POST("api/appeals/{appeal_id}/devices")
    fun createDevice(
        @Path("appeal_id") appealId: String,
        @Body request: CreateDeviceRequest
    ): Call<DeviceResponse>

    @PATCH("api/devices/{device_id}")
    fun updateDevice(
        @Path("device_id") deviceId: String,
        @Body request: UpdateDeviceRequest
    ): Call<DeviceResponse>

    @DELETE("api/devices/{device_id}")
    fun deleteDevice(
        @Path("device_id") deviceId: String
    ): Call<ApiResponse>

    // ========== REPAIR CRUD ==========

    @POST("api/devices/{device_id}/repairs")
    fun createRepair(
        @Path("device_id") deviceId: String,
        @Body request: CreateRepairRequest
    ): Call<RepairResponse>

    @PATCH("api/repairs/{repair_id}")
    fun updateRepair(
        @Path("repair_id") repairId: String,
        @Body request: UpdateRepairRequest
    ): Call<RepairResponse>

    @DELETE("api/repairs/{repair_id}")
    fun deleteRepair(
        @Path("repair_id") repairId: String
    ): Call<ApiResponse>
}

data class UpdateSettingsRequest(
    val operator_id: String,
    val tenant_id: String,
    val operation_mode: String  // "auto" or "assist"
)

data class UpdateAppealModeRequest(
    val operator_id: String,
    val appeal_id: String,
    val operation_mode: String  // "auto" or "assist"
)

data class LoginRequest(
    val login: String,
    val password: String,
    val device_info: DeviceInfo? = null
)

data class LoginResponse(
    val success: Boolean,
    val operator_id: String,
    val tenant_id: String,
    val name: String?,
    val email: String,
    val location_id: String?,
    val password_valid: Boolean? = null,
    val session_token: String
)

data class AppealsListResponse(
    val success: Boolean,
    val appeals: List<Appeal>,
    val counts: AppealCounts? = null
)

data class AppealCounts(
    val new: Int? = null,
    val in_progress: Int? = null,
    val closed: Int? = null
)

data class AppealDetailResponse(
    val success: Boolean,
    val appeal: AppealDetail,
    val client: Client? = null,
    val device: Device? = null,
    val devices: List<AppealDeviceDto>? = null,
    val history: List<HistoryMessage>? = null,
    val messages: List<Message>? = null,
    val ai_response: AiResponse? = null
)

data class AppealDeviceDto(
    val id: String,
    val device_order: Int? = null,
    val brand_id: String? = null,
    val brand_name: String? = null,
    val model_id: String? = null,
    val model_name: String? = null,
    val phone_model: String? = null,
    val status: String? = null,
    val repairs: List<AppealRepairDto>? = null
)

data class AppealRepairDto(
    val id: String,
    val repair_category_id: String? = null,
    val repair_category_name: String? = null,
    val issue_type_id: String? = null,
    val issue_type_name: String? = null,
    val parts_owner: String? = null,
    val status: String? = null,
    val priority: Int? = null
)

data class Appeal(
    val id: String? = null,
    val number: String? = null,
    val status: String? = null,
    val stage: String? = null,
    val channel: String? = null,
    val channel_name: String? = null,
    val client: Client? = null,
    val device: Device? = null,
    val appeal_type: String? = null,
    val repair_type_name: String? = null,
    val issue_type_name: String? = null,
    val issue_name: String? = null,
    val problem_description: String? = null,
    val last_message: String? = null,
    val ai_response: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val parts_owner: String? = null
)

data class AppealDetail(
    val id: String? = null,
    val stage: String? = null,
    val deal_type_name: String? = null,
    val operation_mode: String? = null,
    val channel: String? = null,
    val channel_name: String? = null,
    val appeal_status: String? = null,
    val estimated_cost: Double? = null,
    val estimated_time: String? = null,
    val phone_model: String? = null,
    val brand_id: String? = null,
    val model_id: String? = null,
    val repair_type_id: String? = null,
    val repair_type_name: String? = null,
    val appeal_type: String? = null,
    val issue_type_name: String? = null,
    val issue_name: String? = null,
    val problem_description: String? = null,
    val parts_owner: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    val ai_response: String? = null,
    val meta: List<MetaField>? = null
)

data class Client(
    val name: String? = null,
    val phone: String? = null
)

data class Device(
    val brand: String? = null,
    val model: String? = null
)

data class HistoryMessage(
    val created_at: String? = null,
    val type: String? = null,
    val text: String? = null
)

data class Message(
    val id: String? = null,
    val text: String? = null,
    val media_type: String? = null,
    val media_url: String? = null,
    val sender_type: String? = null,
    val sender_name: String? = null,
    val created_at: String? = null
)

data class AiResponse(
    val id: String? = null,
    val response_text: String? = null,
    val status: String? = null,
    val created_at: String? = null
)

data class ApiResponse(
    val success: Boolean,
    val appeal_id: String? = null,
    val error: String? = null,
    val message: String? = null
)

data class NormalizeRequest(
    val operator_id: String,
    val response_text: String
)

data class NormalizeResponse(
    val success: Boolean,
    val normalized_text: String? = null
)

data class SendMessageRequest(
    val operator_id: String,
    val response_text: String,
    val media_type: String? = null,
    val media_data: String? = null
)

data class TakeAppealRequest(
    val operator_id: String
)

data class RejectRequest(
    val operator_id: String,
    val reject_reason: String? = null
)

data class PromoRequest(
    val operator_id: String
)

data class FCMTokenRegisterRequest(
    val operator_id: String,
    val session_token: String,
    val fcm_token: String,
    val device_info: DeviceInfo? = null
)

data class DeviceInfo(
    val device_id: String? = null,
    val device_model: String? = null,
    val os_version: String? = null,
    val app_version: String? = null
)

data class FCMTokenResponse(
    val success: Boolean,
    val message: String,
    val operator_id: String? = null
)

data class MetaField(
    val id: String,
    val label: String,
    val value: String?,
    val order: Double? = null
)

// ========== DEVICE CRUD DTOs ==========

data class CreateDeviceRequest(
    val appeal_id: String,
    val brand_id: String? = null,
    val brand_name: String? = null,
    val model_id: String? = null,
    val model_name: String? = null,
    val phone_model: String? = null
)

data class UpdateDeviceRequest(
    val brand_id: String? = null,
    val brand_name: String? = null,
    val model_id: String? = null,
    val model_name: String? = null,
    val phone_model: String? = null
)

data class DeviceResponse(
    val success: Boolean,
    val device: AppealDeviceDto? = null,
    val error: String? = null
)

// ========== REPAIR CRUD DTOs ==========

data class CreateRepairRequest(
    val appeal_device_id: String,
    val repair_category_id: String? = null,
    val repair_category_name: String? = null,
    val issue_type_id: String? = null,
    val issue_type_name: String? = null,
    val parts_owner: String? = null,
    val priority: Int? = null
)

data class UpdateRepairRequest(
    val repair_category_id: String? = null,
    val repair_category_name: String? = null,
    val issue_type_id: String? = null,
    val issue_type_name: String? = null,
    val parts_owner: String? = null,
    val status: String? = null,
    val priority: Int? = null
)

data class RepairResponse(
    val success: Boolean,
    val repair: AppealRepairDto? = null,
    val error: String? = null
)
