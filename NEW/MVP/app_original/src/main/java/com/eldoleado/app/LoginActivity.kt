package com.eldoleado.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eldoleado.app.BuildConfig
import com.eldoleado.app.api.DeviceInfo
import com.eldoleado.app.api.LoginRequest
import com.eldoleado.app.api.LoginResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.fcm.FCMRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val loginScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var sessionManager: SessionManager
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return
        }

        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener { login() }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginScope.cancel()
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите почту и пароль", Toast.LENGTH_SHORT).show()
            return
        }

        btnLogin.isEnabled = false
        btnLogin.text = getString(R.string.login_progress)

        val deviceInfo = getDeviceInfo()
        val request = LoginRequest(login = email, password = password, device_info = deviceInfo)
        val apiService = RetrofitClient.getApiService(this)

        apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                btnLogin.isEnabled = true
                btnLogin.text = getString(R.string.login_action)

                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Ошибка входа: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val data = response.body()
                if (data != null && data.success) {
                    sessionManager.saveSession(
                        operatorId = data.operator_id,
                        tenantId = data.tenant_id,
                        username = data.email,
                        name = data.name,
                        sessionToken = data.session_token,
                        appMode = data.app_mode,
                        tunnelUrl = data.tunnel_url,
                        tunnelSecret = data.tunnel_secret
                    )
                    registerFCMToken(data.operator_id, data.session_token)
                    navigateToMain()
                } else {
                    Toast.makeText(this@LoginActivity, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                btnLogin.isEnabled = true
                btnLogin.text = getString(R.string.login_action)
                Toast.makeText(
                    this@LoginActivity,
                    "Сеть недоступна: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun registerFCMToken(operatorId: String, sessionToken: String) {
        loginScope.launch {
            try {
                val fcmToken = FirebaseMessaging.getInstance().token.await()
                val repository = FCMRepository(
                    apiService = RetrofitClient.getApiService(this@LoginActivity),
                    context = this@LoginActivity
                )
                repository.registerFCMToken(operatorId, sessionToken, fcmToken)
            } catch (e: Exception) {
                android.util.Log.e("LoginActivity", "FCM токен не зарегистрирован", e)
            }
        }
    }

    private fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo(
            device_id = getAndroidId(),
            device_model = getDeviceModel(),
            os_version = getOsVersion(),
            app_version = getAppVersion()
        )
    }

    private fun getAndroidId(): String? {
        return try {
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            android.util.Log.e("LoginActivity", "Failed to get Android ID", e)
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
            android.util.Log.e("LoginActivity", "Failed to get device model", e)
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
            android.util.Log.e("LoginActivity", "Failed to get OS version", e)
            null
        }
    }

    private fun getAppVersion(): String? {
        return try {
            BuildConfig.VERSION_NAME
        } catch (e: Exception) {
            runCatching {
                val info = packageManager.getPackageInfo(packageName, 0)
                info.versionName
            }.getOrNull()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
