package com.batterycrm.app.fcm

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.batterycrm.app.BuildConfig
import com.batterycrm.app.api.ApiService
import com.batterycrm.app.api.DeviceInfo
import com.batterycrm.app.api.FCMTokenRegisterRequest
import com.batterycrm.app.api.FCMTokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FCMRepository(
    private val apiService: ApiService,
    private val context: Context
) {
    fun registerFCMToken(operatorId: String, sessionToken: String, fcmToken: String) {
        val deviceInfo = DeviceInfo(
            device_id = getAndroidId(),
            device_model = getDeviceModel(),
            os_version = getOsVersion(),
            app_version = safeAppVersion()
        )
        val request = FCMTokenRegisterRequest(
            operator_id = operatorId,
            session_token = sessionToken,
            fcm_token = fcmToken,
            device_info = deviceInfo
        )

        apiService.registerFCMToken(request).enqueue(object : Callback<FCMTokenResponse> {
            override fun onResponse(
                call: Call<FCMTokenResponse>,
                response: Response<FCMTokenResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("FCMRepository", "FCM token registered: ${response.body()?.message}")
                } else {
                    Log.e("FCMRepository", "FCM register failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FCMTokenResponse>, t: Throwable) {
                Log.e("FCMRepository", "FCM register error", t)
            }
        })
    }

    private fun getAndroidId(): String? {
        return try {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            Log.e("FCMRepository", "Failed to get Android ID", e)
            null
        }
    }

    private fun getDeviceModel(): String? {
        return try {
            val manufacturer = Build.MANUFACTURER ?: ""
            val model = Build.MODEL ?: ""
            if (manufacturer.isNotEmpty() && model.isNotEmpty()) {
                "$manufacturer $model".trim()
            } else if (model.isNotEmpty()) {
                model
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("FCMRepository", "Failed to get device model", e)
            null
        }
    }

    private fun getOsVersion(): String? {
        return try {
            val release = Build.VERSION.RELEASE
            if (release.isNotEmpty()) {
                "Android $release"
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("FCMRepository", "Failed to get OS version", e)
            null
        }
    }

    private fun safeAppVersion(): String? {
        return try {
            BuildConfig.VERSION_NAME
        } catch (e: Exception) {
            runCatching {
                val info = context.packageManager.getPackageInfo(context.packageName, 0)
                info.versionName
            }.getOrNull()
        }
    }
}

