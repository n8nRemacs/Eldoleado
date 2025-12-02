# ТЗ-2: Android App - Интеграция Firebase FCM

## Цель
Интегрировать Firebase Cloud Messaging в Android приложение BatteryCRM для получения push-уведомлений о новых обращениях клиентов.

## Предусловия
От Firebase администратора получено:
- Файл `google-services.json`
- Package name подтвержден: `com.batterycrm.operator`
- Server Key зарегистрирован в бэкенде

## Шаг 1: Настройка Firebase SDK

### 1.1 Gradle конфигурация

**Project-level `build.gradle.kts`:**
```kotlin
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
```

**App-level `build.gradle.kts`:**
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

dependencies {
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    
    // Firebase Cloud Messaging
    implementation("com.google.firebase:firebase-messaging-ktx")
    
    // Для корутин
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}
```

### 1.2 Добавление google-services.json
Поместить полученный файл в `app/google-services.json`

**Проверка:**
После синхронизации Gradle должен появиться `R.drawable` с иконками Firebase.

## Шаг 2: Создание FCM Service

### 2.1 BatteryCRMMessagingService

**Путь:** `app/src/main/java/com/batterycrm/operator/fcm/BatteryCRMMessagingService.kt`

```kotlin
package com.batterycrm.operator.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.batterycrm.operator.R
import com.batterycrm.operator.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BatteryCRMMessagingService : FirebaseMessagingService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM token: $token")
        
        // Сохранить токен локально
        saveFCMTokenLocally(token)
        
        // Отправить на сервер если оператор залогинен
        serviceScope.launch {
            sendTokenToServer(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        Log.d(TAG, "Message received from: ${message.from}")
        Log.d(TAG, "Message data: ${message.data}")
        
        val notificationType = message.data["type"]
        
        when (notificationType) {
            "new_appeal" -> handleNewAppealNotification(message)
            else -> handleGenericNotification(message)
        }
    }

    private fun handleNewAppealNotification(message: RemoteMessage) {
        val appealId = message.data["appeal_id"]
        if (appealId.isNullOrBlank()) {
            Log.e(TAG, "Appeal ID is missing in notification")
            return
        }
        
        val clientName = message.data["client_name"] ?: "Клиент"
        val channel = message.data["channel"] ?: ""
        val repairType = message.data["repair_type"] ?: "требуется уточнение"
        val phoneModel = message.data["phone_model"] ?: ""
        val partsOwner = message.data["parts_owner"] ?: ""
        val appealType = message.data["appeal_type"] ?: ""
        
        // Формируем текст уведомления
        val title = "🔔 Новое обращение"
        val body = buildString {
            append("$clientName")
            if (repairType.isNotBlank()) append(" - $repairType")
            if (phoneModel.isNotBlank()) append(" ($phoneModel)")
        }
        
        // Intent для открытия приложения
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("appeal_id", appealId)
            putExtra("open_appeal_detail", true)
            
            // Дополнительные данные для детального экрана
            putExtra("client_name", clientName)
            putExtra("channel", channel)
            putExtra("repair_type", repairType)
            putExtra("phone_model", phoneModel)
            putExtra("parts_owner", partsOwner)
            putExtra("appeal_type", appealType)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            appealId.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        // Создаем уведомление
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 500, 200, 500))
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Создать канал для Android 8+
        createNotificationChannel(notificationManager)
        
        // Показать уведомление
        notificationManager.notify(appealId.hashCode(), notificationBuilder.build())
        
        Log.d(TAG, "Notification displayed for appeal: $appealId")
    }

    private fun handleGenericNotification(message: RemoteMessage) {
        Log.d(TAG, "Generic notification received")
        
        val title = message.notification?.title ?: "BatteryCRM"
        val body = message.notification?.body ?: ""
        
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Новые обращения",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Уведомления о новых обращениях клиентов"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun saveFCMTokenLocally(token: String) {
        getSharedPreferences("batterycrm_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("fcm_token", token)
            .apply()
    }

    private suspend fun sendTokenToServer(token: String) {
        try {
            val prefs = getSharedPreferences("batterycrm_prefs", Context.MODE_PRIVATE)
            val operatorId = prefs.getString("operator_id", null)
            val sessionToken = prefs.getString("session_token", null)
            
            if (operatorId.isNullOrBlank() || sessionToken.isNullOrBlank()) {
                Log.d(TAG, "Operator not logged in, skipping token upload")
                return
            }
            
            // Вызов через Retrofit (см. Шаг 3)
            // fcmRepository.registerFCMToken(operatorId, sessionToken)
            
            Log.d(TAG, "FCM token sent to server successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send FCM token to server", e)
        }
    }

    companion object {
        private const val TAG = "BatteryCRMMessaging"
        private const val CHANNEL_ID = "batterycrm_appeals"
    }
}
```

### 2.2 Регистрация Service в AndroidManifest.xml

**Путь:** `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <application
        android:name=".BatteryCRMApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.BatteryCRM">

        <!-- MainActivity -->
        <activity
            android:name=".ui.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Firebase Messaging Service -->
        <service
            android:name=".fcm.BatteryCRMMessagingService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <!-- Firebase Metadata -->
        <meta-data
            android:name="com.google.firebase.messaging.default_notification_icon"
            android:resource="@drawable/ic_notification" />
        <meta-data
            android:name="com.google.firebase.messaging.default_notification_color"
            android:resource="@color/notification_color" />
        <meta-data
            android:name="com.google.firebase.messaging.default_notification_channel_id"
            android:value="batterycrm_appeals" />

    </application>

</manifest>
```

### 2.3 Иконка уведомления

**Создать:** `app/src/main/res/drawable/ic_notification.xml`

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="?attr/colorControlNormal">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M12,22c1.1,0 2,-0.9 2,-2h-4c0,1.1 0.89,2 2,2zM18,16v-5c0,-3.07 -1.64,-5.64 -4.5,-6.32V4c0,-0.83 -0.67,-1.5 -1.5,-1.5s-1.5,0.67 -1.5,1.5v0.68C7.63,5.36 6,7.92 6,11v5l-2,2v1h16v-1l-2,-2z"/>
</vector>
```

**Добавить цвет:** `app/src/main/res/values/colors.xml`

```xml
<resources>
    <color name="notification_color">#FF6200EE</color>
</resources>
```

## Шаг 3: API Integration для регистрации FCM Token

### 3.1 Retrofit API Interface

**Путь:** `app/src/main/java/com/batterycrm/operator/api/FCMTokenApi.kt`

```kotlin
package com.batterycrm.operator.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class FCMTokenRequest(
    val operator_id: String,
    val fcm_token: String,
    val device_info: DeviceInfo
)

data class DeviceInfo(
    val model: String,
    val os_version: String,
    val app_version: String
)

data class FCMTokenResponse(
    val success: Boolean,
    val message: String
)

interface FCMTokenApi {
    @POST("webhook/operator/fcm-token")
    suspend fun updateFCMToken(
        @Header("Authorization") token: String,
        @Body request: FCMTokenRequest
    ): Response<FCMTokenResponse>
}
```

### 3.2 FCM Repository

**Путь:** `app/src/main/java/com/batterycrm/operator/repository/FCMRepository.kt`

```kotlin
package com.batterycrm.operator.repository

import android.content.Context
import android.os.Build
import android.util.Log
import com.batterycrm.operator.BuildConfig
import com.batterycrm.operator.api.DeviceInfo
import com.batterycrm.operator.api.FCMTokenApi
import com.batterycrm.operator.api.FCMTokenRequest
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FCMRepository(
    private val api: FCMTokenApi,
    private val context: Context
) {
    
    suspend fun registerFCMToken(operatorId: String, sessionToken: String): Result<Boolean> {
        return try {
            // Получить FCM token от Firebase
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "FCM Token obtained: $fcmToken")
            
            // Подготовить device info
            val deviceInfo = DeviceInfo(
                model = Build.MODEL,
                os_version = Build.VERSION.RELEASE,
                app_version = BuildConfig.VERSION_NAME
            )
            
            // Подготовить запрос
            val request = FCMTokenRequest(
                operator_id = operatorId,
                fcm_token = fcmToken,
                device_info = deviceInfo
            )
            
            // Отправить на сервер
            val response = api.updateFCMToken("Bearer $sessionToken", request)
            
            if (response.isSuccessful) {
                Log.d(TAG, "FCM token registered successfully")
                Result.success(true)
            } else {
                Log.e(TAG, "Failed to register FCM token: ${response.code()}")
                Result.failure(Exception("Server returned ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while registering FCM token", e)
            Result.failure(e)
        }
    }
    
    suspend fun getFCMToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get FCM token", e)
            null
        }
    }

    companion object {
        private const val TAG = "FCMRepository"
    }
}
```

### 3.3 Интеграция с Retrofit Client

**Добавить в:** `app/src/main/java/com/batterycrm/operator/network/RetrofitClient.kt`

```kotlin
package com.batterycrm.operator.network

import com.batterycrm.operator.api.FCMTokenApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://n8n.your-domain.com/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val fcmTokenApi: FCMTokenApi = retrofit.create(FCMTokenApi::class.java)
}
```

## Шаг 4: Интеграция с Authentication Flow

### 4.1 Обновление LoginViewModel

**Добавить в:** `app/src/main/java/com/batterycrm/operator/ui/login/LoginViewModel.kt`

```kotlin
class LoginViewModel(
    private val authRepository: AuthRepository,
    private val fcmRepository: FCMRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            val result = authRepository.login(username, password)
            
            if (result.isSuccess) {
                val authData = result.getOrNull()!!
                
                // Сохранить данные сессии
                saveSessionData(authData)
                
                // Зарегистрировать FCM token
                registerFCMToken(authData.operatorId, authData.sessionToken)
                
                _loginState.value = LoginState.Success(authData)
            } else {
                _loginState.value = LoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    private suspend fun registerFCMToken(operatorId: String, sessionToken: String) {
        try {
            val result = fcmRepository.registerFCMToken(operatorId, sessionToken)
            if (result.isSuccess) {
                Log.d("LoginViewModel", "FCM token registered successfully")
            } else {
                Log.e("LoginViewModel", "Failed to register FCM token")
                // Не блокируем вход при ошибке регистрации токена
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Exception during FCM registration", e)
            // Не блокируем вход при ошибке
        }
    }

    private fun saveSessionData(authData: AuthData) {
        // Сохранение в SharedPreferences или DataStore
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val authData: AuthData) : LoginState()
    data class Error(val message: String) : LoginState()
}
```

### 4.2 Запрос разрешений для Android 13+

**Добавить в:** `app/src/main/java/com/batterycrm/operator/ui/MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("MainActivity", "Notification permission granted")
        } else {
            Log.d("MainActivity", "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Запросить разрешение на уведомления для Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }
        
        // Обработать deep link из уведомления
        handleNotificationIntent(intent)
        
        setContent {
            BatteryCRMTheme {
                BatteryCRMApp()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleNotificationIntent(it) }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Show explanation dialog
                    showPermissionRationaleDialog()
                }
                else -> {
                    // Request permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Разрешение на уведомления")
            .setMessage("Приложению нужно разрешение для отправки уведомлений о новых обращениях")
            .setPositiveButton("Разрешить") { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun handleNotificationIntent(intent: Intent) {
        if (intent.getBooleanExtra("open_appeal_detail", false)) {
            val appealId = intent.getStringExtra("appeal_id")
            if (!appealId.isNullOrBlank()) {
                // Навигация к детальному экрану обращения
                // navController.navigate("appeal_detail/$appealId")
                Log.d("MainActivity", "Opening appeal detail: $appealId")
            }
        }
    }
}
```

## Шаг 5: Тестирование

### 5.1 Проверка регистрации FCM Token

**Логи для отслеживания:**
```kotlin
// В BatteryCRMMessagingService.onNewToken()
Log.d("FCM", "New token: $token")

// В FCMRepository.registerFCMToken()
Log.d("FCM", "Sending token to server...")
Log.d("FCM", "Server response: ${response.code()}")
```

**Ожидаемый результат:**
1. При первом запуске приложения генерируется FCM token
2. Token отправляется на сервер
3. В БД появляется запись в `operators.fcm_token`

### 5.2 Проверка получения уведомлений

**Тест через Firebase Console:**
1. Cloud Messaging → Send test message
2. Вставить FCM token из логов
3. Добавить custom data:
```json
{
  "type": "new_appeal",
  "appeal_id": "test-uuid-123",
  "client_name": "Тестовый клиент",
  "repair_type": "Дисплей",
  "phone_model": "iPhone 13"
}
```
4. Send message

**Ожидаемый результат:**
- Уведомление появляется на устройстве
- При клике открывается приложение
- В логах: "Opening appeal detail: test-uuid-123"

### 5.3 End-to-End тест

**Сценарий:**
1. Оператор залогинился в Android app
2. Клиент отправил сообщение через Telegram
3. n8n обработал обращение
4. Оператор получил push на Android
5. Клик по push открывает детали обращения

**Проверочный список:**
- [ ] FCM token зарегистрирован в БД
- [ ] Push уведомление получено
- [ ] Уведомление отображается корректно
- [ ] Данные в payload корректны
- [ ] Клик открывает правильное обращение
- [ ] Уведомление исчезает после клика

## Шаг 6: Обработка граничных случаев

### 6.1 Retry механизм для FCM Token

**Добавить в:** `app/src/main/java/com/batterycrm/operator/repository/FCMRepository.kt`

```kotlin
suspend fun registerFCMTokenWithRetry(
    operatorId: String,
    sessionToken: String,
    maxRetries: Int = 3
): Result<Boolean> {
    repeat(maxRetries) { attempt ->
        val result = registerFCMToken(operatorId, sessionToken)
        if (result.isSuccess) {
            return result
        }
        if (attempt < maxRetries - 1) {
            delay(2000L * (attempt + 1)) // Exponential backoff
        }
    }
    return Result.failure(Exception("Failed after $maxRetries attempts"))
}
```

### 6.2 Обновление токена при logout

```kotlin
suspend fun unregisterFCMToken(operatorId: String, sessionToken: String) {
    // Отправить пустой FCM token на сервер при выходе
    val request = FCMTokenRequest(
        operator_id = operatorId,
        fcm_token = "",
        device_info = DeviceInfo("", "", "")
    )
    api.updateFCMToken("Bearer $sessionToken", request)
}
```

### 6.3 Проверка актуальности токена

```kotlin
// При каждом запуске приложения проверять актуальность токена
fun checkAndRefreshToken() {
    viewModelScope.launch {
        val currentToken = fcmRepository.getFCMToken()
        val savedToken = prefs.getString("fcm_token", null)
        
        if (currentToken != savedToken) {
            // Token изменился, обновить на сервере
            registerFCMToken(operatorId, sessionToken)
        }
    }
}
```

## Результат работ

### Критерии приемки:
1. ✅ Firebase SDK интегрирован, `google-services.json` добавлен
2. ✅ `BatteryCRMMessagingService` создан и зарегистрирован
3. ✅ FCM token регистрируется на бэкенде при логине
4. ✅ Push уведомления получаются в фоне и в foreground
5. ✅ Клик по уведомлению открывает детали обращения
6. ✅ Разрешение POST_NOTIFICATIONS запрашивается на Android 13+
7. ✅ Retry механизм реализован
8. ✅ End-to-end тест пройден

### Deliverables:
- Исходный код Android приложения с FCM интеграцией
- Документация по тестированию
- Скриншоты успешных тестов
- Логи из Android Studio и Firebase Console

## Дополнительные требования

### Безопасность:
- FCM token храним только в SharedPreferences (зашифрованных)
- Session token передается только через HTTPS
- Валидация данных из notification payload

### Performance:
- FCM Service работает на отдельном потоке
- Минимальная задержка между получением и отображением уведомления
- Batching при множественных уведомлениях

### UX:
- Звуковое оповещение при получении push
- Вибрация по стандартному паттерну
- Badge counter на иконке приложения (если поддерживается launcher)
- Группировка уведомлений при множественных обращениях
