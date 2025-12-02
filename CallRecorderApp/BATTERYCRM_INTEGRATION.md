# Интеграция записи звонков в BatteryCRM

## Файлы для добавления

### 1. Создать пакет callrecording

Создать папку: `app/src/main/java/com/batterycrm/app/callrecording/`

### 2. CallRecordingPreferences.kt

```kotlin
package com.batterycrm.app.callrecording

import android.content.Context
import android.content.SharedPreferences

class CallRecordingPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "call_recording_prefs"
        private const val KEY_RECORDING_ENABLED = "recording_enabled"
        private const val KEY_SERVER_URL = "server_url"
        private const val KEY_AUTO_UPLOAD = "auto_upload"
        private const val KEY_DELETE_AFTER_UPLOAD = "delete_after_upload"
        private const val DEFAULT_SERVER_URL = "https://n8n.n8nsrv.ru/webhook/voice-upload"
    }

    var isRecordingEnabled: Boolean
        get() = prefs.getBoolean(KEY_RECORDING_ENABLED, false)
        set(value) = prefs.edit().putBoolean(KEY_RECORDING_ENABLED, value).apply()

    var serverUrl: String
        get() = prefs.getString(KEY_SERVER_URL, DEFAULT_SERVER_URL) ?: DEFAULT_SERVER_URL
        set(value) = prefs.edit().putString(KEY_SERVER_URL, value).apply()

    var autoUpload: Boolean
        get() = prefs.getBoolean(KEY_AUTO_UPLOAD, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_UPLOAD, value).apply()

    var deleteAfterUpload: Boolean
        get() = prefs.getBoolean(KEY_DELETE_AFTER_UPLOAD, true)
        set(value) = prefs.edit().putBoolean(KEY_DELETE_AFTER_UPLOAD, value).apply()
}
```

### 3. CallRecordingService.kt

```kotlin
package com.batterycrm.app.callrecording

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.batterycrm.app.MainActivity
import com.batterycrm.app.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CallRecordingService : Service() {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var currentRecordingFile: File? = null
    private var currentPhoneNumber: String? = null
    private var callType: String = "unknown"

    companion object {
        private const val TAG = "CallRecordingService"
        private const val NOTIFICATION_ID = 2001
        const val CHANNEL_RECORDING = "call_recording_channel"

        const val ACTION_START_SERVICE = "com.batterycrm.START_RECORDING_SERVICE"
        const val ACTION_STOP_SERVICE = "com.batterycrm.STOP_RECORDING_SERVICE"
        const val ACTION_START_RECORDING = "com.batterycrm.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.batterycrm.STOP_RECORDING"

        const val EXTRA_PHONE_NUMBER = "phone_number"
        const val EXTRA_CALL_TYPE = "call_type"

        const val CALL_TYPE_INCOMING = "incoming"
        const val CALL_TYPE_OUTGOING = "outgoing"

        private const val RECORDINGS_FOLDER = "call_recordings"

        @Volatile
        private var instance: CallRecordingService? = null

        fun isRunning(): Boolean = instance != null
    }

    private lateinit var preferences: CallRecordingPreferences

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = CallRecordingPreferences(this)
        createNotificationChannel()
        createRecordingsDirectory()
        Log.d(TAG, "Service created")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ${intent?.action}")

        when (intent?.action) {
            ACTION_START_SERVICE -> {
                startForegroundService()
            }
            ACTION_STOP_SERVICE -> {
                stopRecordingIfActive()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
            ACTION_START_RECORDING -> {
                currentPhoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER)
                callType = intent.getStringExtra(EXTRA_CALL_TYPE) ?: "unknown"
                startRecording()
            }
            ACTION_STOP_RECORDING -> {
                stopRecordingIfActive()
            }
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_RECORDING,
                "Запись звонков",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Показывает когда активна запись звонков"
                setShowBadge(false)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createRecordingsDirectory() {
        val recordingsDir = getRecordingsDirectory()
        if (!recordingsDir.exists()) {
            recordingsDir.mkdirs()
        }
    }

    fun getRecordingsDirectory(): File {
        return File(filesDir, RECORDINGS_FOLDER)
    }

    private fun startForegroundService() {
        val notification = createNotification("Запись звонков активна")
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "Foreground service started")
    }

    private fun createNotification(contentText: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_settings", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_RECORDING)
            .setContentTitle("BatteryCRM")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_mic)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun startRecording() {
        if (!preferences.isRecordingEnabled) {
            Log.d(TAG, "Recording is disabled in settings")
            return
        }

        if (isRecording) {
            Log.d(TAG, "Already recording, skipping")
            return
        }

        try {
            val recordingsDir = getRecordingsDirectory()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val phoneNumberSafe = currentPhoneNumber?.replace(Regex("[^0-9+]"), "") ?: "unknown"
            val fileName = "${callType}_${phoneNumberSafe}_${timestamp}.m4a"
            currentRecordingFile = File(recordingsDir, fileName)

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(this)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(currentRecordingFile?.absolutePath)

                prepare()
                start()
            }

            isRecording = true
            updateNotification("Запись: $callType")
            Log.d(TAG, "Recording started: ${currentRecordingFile?.absolutePath}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording with VOICE_COMMUNICATION", e)
            tryFallbackRecording()
        }
    }

    private fun tryFallbackRecording() {
        try {
            val recordingsDir = getRecordingsDirectory()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val phoneNumberSafe = currentPhoneNumber?.replace(Regex("[^0-9+]"), "") ?: "unknown"
            val fileName = "${callType}_${phoneNumberSafe}_${timestamp}.m4a"
            currentRecordingFile = File(recordingsDir, fileName)

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(this)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(currentRecordingFile?.absolutePath)

                prepare()
                start()
            }

            isRecording = true
            updateNotification("Запись: $callType (микрофон)")
            Log.d(TAG, "Fallback recording started: ${currentRecordingFile?.absolutePath}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start fallback recording", e)
            cleanupRecorder()
        }
    }

    private fun stopRecordingIfActive() {
        if (!isRecording) {
            Log.d(TAG, "Not recording, nothing to stop")
            return
        }

        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            Log.d(TAG, "Recording stopped: ${currentRecordingFile?.absolutePath}")

            currentRecordingFile?.let { file ->
                if (file.exists() && file.length() > 0) {
                    scheduleUpload(file)
                } else {
                    Log.w(TAG, "Recording file is empty or doesn't exist")
                    file.delete()
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recording", e)
        } finally {
            cleanupRecorder()
            updateNotification("Запись звонков активна")
        }
    }

    private fun cleanupRecorder() {
        mediaRecorder?.release()
        mediaRecorder = null
        isRecording = false
        currentRecordingFile = null
        currentPhoneNumber = null
        callType = "unknown"
    }

    private fun scheduleUpload(file: File) {
        if (preferences.autoUpload) {
            CallUploadWorker.scheduleUpload(this, file.absolutePath)
            Log.d(TAG, "Upload scheduled for: ${file.absolutePath}")
        }
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        stopRecordingIfActive()
        Log.d(TAG, "Service destroyed")
    }
}
```

### 4. CallReceiver.kt

```kotlin
package com.batterycrm.app.callrecording

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CallReceiver"
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var isIncoming = false
        private var savedNumber: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prefs = CallRecordingPreferences(context)
        if (!prefs.isRecordingEnabled) {
            return
        }

        Log.d(TAG, "Received intent: ${intent.action}")

        when (intent.action) {
            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                handleIncomingCall(context, intent)
            }
            @Suppress("DEPRECATION")
            Intent.ACTION_NEW_OUTGOING_CALL -> {
                handleOutgoingCall(context, intent)
            }
        }
    }

    private fun handleIncomingCall(context: Context, intent: Intent) {
        val stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        @Suppress("DEPRECATION")
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        Log.d(TAG, "Phone state changed: $stateStr, number: $number")

        val state = when (stateStr) {
            TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
            TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
            TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
            else -> return
        }

        onCallStateChanged(context, state, number)
    }

    private fun handleOutgoingCall(context: Context, intent: Intent) {
        val number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        Log.d(TAG, "Outgoing call to: $number")
        savedNumber = number
        isIncoming = false
    }

    private fun onCallStateChanged(context: Context, state: Int, number: String?) {
        if (!number.isNullOrEmpty()) {
            savedNumber = number
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                savedNumber = number
                Log.d(TAG, "Incoming call ringing from: $number")
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                if (lastState == TelephonyManager.CALL_STATE_IDLE) {
                    isIncoming = false
                }

                val callType = if (isIncoming) {
                    CallRecordingService.CALL_TYPE_INCOMING
                } else {
                    CallRecordingService.CALL_TYPE_OUTGOING
                }

                Log.d(TAG, "Call started - Type: $callType, Number: $savedNumber")
                startRecording(context, savedNumber, callType)
            }

            TelephonyManager.CALL_STATE_IDLE -> {
                if (lastState == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Log.d(TAG, "Call ended")
                    stopRecording(context)
                }
                isIncoming = false
                savedNumber = null
            }
        }

        lastState = state
    }

    private fun startRecording(context: Context, phoneNumber: String?, callType: String) {
        val intent = Intent(context, CallRecordingService::class.java).apply {
            action = CallRecordingService.ACTION_START_RECORDING
            putExtra(CallRecordingService.EXTRA_PHONE_NUMBER, phoneNumber)
            putExtra(CallRecordingService.EXTRA_CALL_TYPE, callType)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    private fun stopRecording(context: Context) {
        val intent = Intent(context, CallRecordingService::class.java).apply {
            action = CallRecordingService.ACTION_STOP_RECORDING
        }
        context.startService(intent)
    }
}
```

### 5. BootReceiver.kt

```kotlin
package com.batterycrm.app.callrecording

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON" ||
            intent.action == "com.htc.intent.action.QUICKBOOT_POWERON") {

            val prefs = CallRecordingPreferences(context)
            if (!prefs.isRecordingEnabled) {
                Log.d(TAG, "Recording is disabled, not starting service")
                return
            }

            Log.d(TAG, "Device booted, starting CallRecordingService")

            val serviceIntent = Intent(context, CallRecordingService::class.java).apply {
                action = CallRecordingService.ACTION_START_SERVICE
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
                Log.d(TAG, "CallRecordingService started successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to start CallRecordingService", e)
            }
        }
    }
}
```

### 6. CallUploadWorker.kt

```kotlin
package com.batterycrm.app.callrecording

import android.content.Context
import android.util.Log
import androidx.work.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class CallUploadWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val TAG = "CallUploadWorker"
        private const val KEY_FILE_PATH = "file_path"
        private const val WORK_NAME_PREFIX = "call_upload_"

        fun scheduleUpload(context: Context, filePath: String) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val inputData = workDataOf(KEY_FILE_PATH to filePath)

            val uploadRequest = OneTimeWorkRequestBuilder<CallUploadWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30,
                    TimeUnit.SECONDS
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    WORK_NAME_PREFIX + File(filePath).name,
                    ExistingWorkPolicy.REPLACE,
                    uploadRequest
                )

            Log.d(TAG, "Upload scheduled for: $filePath")
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    override fun doWork(): Result {
        val filePath = inputData.getString(KEY_FILE_PATH)

        if (filePath.isNullOrEmpty()) {
            Log.e(TAG, "No file path provided")
            return Result.failure()
        }

        val file = File(filePath)
        if (!file.exists()) {
            Log.e(TAG, "File does not exist: $filePath")
            return Result.failure()
        }

        val prefs = CallRecordingPreferences(applicationContext)
        val serverUrl = prefs.serverUrl

        Log.d(TAG, "Uploading file: ${file.name} to $serverUrl")

        return try {
            val success = uploadFile(file, serverUrl)
            if (success) {
                Log.d(TAG, "Upload successful: ${file.name}")

                if (prefs.deleteAfterUpload) {
                    file.delete()
                    Log.d(TAG, "File deleted: ${file.name}")
                }

                Result.success()
            } else {
                Log.w(TAG, "Upload failed, will retry: ${file.name}")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Upload error", e)
            Result.retry()
        }
    }

    private fun uploadFile(file: File, serverUrl: String): Boolean {
        val fileName = file.nameWithoutExtension
        val parts = fileName.split("_")

        val callType = parts.getOrNull(0) ?: "unknown"
        val phoneNumber = parts.getOrNull(1) ?: "unknown"
        val timestamp = parts.getOrNull(2) ?: ""

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("call_type", callType)
            .addFormDataPart("phone_number", phoneNumber)
            .addFormDataPart("timestamp", timestamp)
            .addFormDataPart("source", "batterycrm_android")
            .addFormDataPart(
                "audio_file",
                file.name,
                file.asRequestBody("audio/mp4".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url(serverUrl)
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "Server response: ${response.code}")
                    true
                } else {
                    Log.w(TAG, "Server error: ${response.code} - ${response.message}")
                    false
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error during upload", e)
            false
        }
    }
}
```

---

## Изменения в activity_main.xml

Заменить settingsContainer на ScrollView и добавить переключатель записи:

```xml
<ScrollView
    android:id="@+id/settingsContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:visibility="gone">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- Существующий контент настроек AI Mode -->

        <!-- Добавить после кнопки saveSettingsButton: -->

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:layout_marginTop="24dp"
            android:layout_marginBottom="16dp"
            android:background="@color/gray_light" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Запись звонков"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@android:color/black" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="Записывать телефонные разговоры для транскрибации"
            android:textSize="14sp"
            android:textColor="@color/text_secondary" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">

                <TextView
                    android:id="@+id/callRecordingStatusText"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Запись выключена"
                    android:textSize="16sp"
                    android:textColor="@android:color/black" />

                <TextView
                    android:id="@+id/callRecordingDescription"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Нажмите для включения записи"
                    android:textSize="13sp"
                    android:textColor="@color/text_secondary" />

            </LinearLayout>

            <com.google.android.material.switchmaterial.SwitchMaterial
                android:id="@+id/switchCallRecording"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content" />

        </LinearLayout>

        <!-- Далее кнопка выхода -->

    </LinearLayout>
</ScrollView>
```

---

## Изменения в MainActivity.kt

### Добавить импорты:

```kotlin
import android.widget.ScrollView
import com.batterycrm.app.callrecording.CallRecordingPreferences
import com.batterycrm.app.callrecording.CallRecordingService
import com.google.android.material.switchmaterial.SwitchMaterial
```

### Добавить поля:

```kotlin
private lateinit var settingsContainer: ScrollView  // изменить тип с LinearLayout
private lateinit var switchCallRecording: SwitchMaterial
private lateinit var callRecordingStatusText: TextView
private lateinit var callRecordingDescription: TextView
private lateinit var callRecordingPreferences: CallRecordingPreferences
```

### Добавить launcher для разрешений:

```kotlin
private val callRecordingPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    val allGranted = permissions.all { it.value }
    if (allGranted) {
        enableCallRecording()
    } else {
        switchCallRecording.isChecked = false
        Toast.makeText(this, "Для записи звонков необходимы разрешения", Toast.LENGTH_LONG).show()
    }
}
```

### В onCreate добавить:

```kotlin
callRecordingPreferences = CallRecordingPreferences(this)

// Call recording UI
switchCallRecording = findViewById(R.id.switchCallRecording)
callRecordingStatusText = findViewById(R.id.callRecordingStatusText)
callRecordingDescription = findViewById(R.id.callRecordingDescription)

setupCallRecordingSettings()
```

### Добавить методы:

```kotlin
private fun setupCallRecordingSettings() {
    switchCallRecording.isChecked = callRecordingPreferences.isRecordingEnabled
    updateCallRecordingUI()

    switchCallRecording.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            requestCallRecordingPermissions()
        } else {
            disableCallRecording()
        }
    }
}

private fun updateCallRecordingUI() {
    val isEnabled = callRecordingPreferences.isRecordingEnabled
    switchCallRecording.isChecked = isEnabled

    if (isEnabled) {
        callRecordingStatusText.text = "Запись включена"
        callRecordingDescription.text = "Звонки записываются и отправляются на сервер"
        callRecordingStatusText.setTextColor(ContextCompat.getColor(this, R.color.green_status))
    } else {
        callRecordingStatusText.text = "Запись выключена"
        callRecordingDescription.text = "Нажмите для включения записи"
        callRecordingStatusText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
    }
}

private fun requestCallRecordingPermissions() {
    val permissions = mutableListOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        permissions.add(Manifest.permission.READ_PHONE_NUMBERS)
    }

    val permissionsToRequest = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }

    if (permissionsToRequest.isEmpty()) {
        enableCallRecording()
    } else {
        callRecordingPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    }
}

private fun enableCallRecording() {
    callRecordingPreferences.isRecordingEnabled = true
    updateCallRecordingUI()

    val intent = Intent(this, CallRecordingService::class.java).apply {
        action = CallRecordingService.ACTION_START_SERVICE
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }

    Toast.makeText(this, "Запись звонков включена", Toast.LENGTH_SHORT).show()
}

private fun disableCallRecording() {
    callRecordingPreferences.isRecordingEnabled = false
    updateCallRecordingUI()

    val intent = Intent(this, CallRecordingService::class.java).apply {
        action = CallRecordingService.ACTION_STOP_SERVICE
    }
    startService(intent)

    Toast.makeText(this, "Запись звонков выключена", Toast.LENGTH_SHORT).show()
}
```

### В onResume добавить:

```kotlin
updateCallRecordingUI()
```

### В showSettings добавить:

```kotlin
updateCallRecordingUI()
```

---

## Изменения в AndroidManifest.xml

### Добавить разрешения:

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_CALL_LOG" />
<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
<uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MICROPHONE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_PHONE_CALL" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

### Добавить сервис и receivers внутри <application>:

```xml
<service
    android:name=".callrecording.CallRecordingService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="microphone|phoneCall" />

<receiver
    android:name=".callrecording.CallReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="android.intent.action.PHONE_STATE" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="android.intent.action.NEW_OUTGOING_CALL" />
    </intent-filter>
</receiver>

<receiver
    android:name=".callrecording.BootReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="android.intent.action.BOOT_COMPLETED" />
        <action android:name="android.intent.action.QUICKBOOT_POWERON" />
        <action android:name="com.htc.intent.action.QUICKBOOT_POWERON" />
    </intent-filter>
</receiver>
```

---

## Добавить цвет в colors.xml

```xml
<color name="green_status">#4CAF50</color>
```
