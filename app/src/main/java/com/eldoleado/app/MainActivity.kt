package com.eldoleado.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.adapters.DialogsAdapter
import com.eldoleado.app.api.ApiResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.api.UpdateSettingsRequest
import com.eldoleado.app.callrecording.CallRecordingPreferences
import com.eldoleado.app.callrecording.CallRecordingService
import com.eldoleado.app.data.database.entities.DialogEntity
import com.eldoleado.app.tunnel.TunnelService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var dialogsAdapter: DialogsAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var logoutButton: Button
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var dialogsRecyclerView: RecyclerView
    private lateinit var settingsContainer: ScrollView
    private lateinit var headerTitle: TextView
    private lateinit var aiModeRadioGroup: RadioGroup
    private lateinit var radioAutomatic: RadioButton
    private lateinit var radioSemiAutomatic: RadioButton
    private lateinit var saveSettingsButton: Button

    // Call recording
    private lateinit var switchCallRecording: SwitchMaterial
    private lateinit var callRecordingStatusText: TextView
    private lateinit var callRecordingDescription: TextView
    private lateinit var callRecordingPreferences: CallRecordingPreferences

    companion object {
        const val PREF_AI_MODE = "ai_mode"
        const val AI_MODE_AUTOMATIC = "automatic"
        const val AI_MODE_SEMI_AUTOMATIC = "semi_automatic"
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            showPermissionDeniedDialog()
        }
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        dialogsRecyclerView = findViewById(R.id.appealsRecyclerView)
        settingsContainer = findViewById(R.id.settingsContainer)
        headerTitle = findViewById(R.id.headerTitle)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        aiModeRadioGroup = findViewById(R.id.aiModeRadioGroup)
        radioAutomatic = findViewById(R.id.radioAutomatic)
        radioSemiAutomatic = findViewById(R.id.radioSemiAutomatic)
        saveSettingsButton = findViewById(R.id.saveSettingsButton)

        // Call recording UI
        callRecordingPreferences = CallRecordingPreferences(this)
        switchCallRecording = findViewById(R.id.switchCallRecording)
        callRecordingStatusText = findViewById(R.id.callRecordingStatusText)
        callRecordingDescription = findViewById(R.id.callRecordingDescription)

        dialogsAdapter = DialogsAdapter { dialog ->
            openChat(dialog)
        }

        dialogsRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogsRecyclerView.adapter = dialogsAdapter

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { handleLogout() }
        saveSettingsButton.setOnClickListener { saveSettings() }

        setupBottomNavigation()
        setupAiModeSettings()
        setupCallRecordingSettings()
        setupObservers()
        requestNotificationPermissionIfNeeded()
        handleNotificationIntent(intent)

        // Setup based on app mode
        setupAppMode()

        // Load dialogs (mock for now)
        loadDialogs()

        // Проверяем, нужно ли открыть настройки
        if (intent.getBooleanExtra("open_settings", false)) {
            bottomNavigation.selectedItemId = R.id.nav_settings
            showSettings()
        }
    }

    private fun setupAppMode() {
        val appMode = sessionManager.getAppMode()
        Log.i("MainActivity", "App mode: $appMode")

        // Start TunnelService if server mode enabled
        if (sessionManager.isServerEnabled()) {
            TunnelService.start(this)
        }

        // Adjust UI based on mode
        when (appMode) {
            SessionManager.MODE_SERVER -> {
                // Server only - hide messenger UI, show tunnel status
                dialogsRecyclerView.visibility = View.GONE
                bottomNavigation.visibility = View.GONE
                headerTitle.text = "Tunnel Server"
                showTunnelStatus()
            }
            SessionManager.MODE_BOTH -> {
                // Both - show everything
                dialogsRecyclerView.visibility = View.VISIBLE
                bottomNavigation.visibility = View.VISIBLE
            }
            else -> {
                // Client only - normal UI
                dialogsRecyclerView.visibility = View.VISIBLE
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun showTunnelStatus() {
        // For server-only mode, show tunnel connection status
        settingsContainer.visibility = View.VISIBLE
        // TODO: Add tunnel status UI
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dialogs -> {
                    showDialogs()
                    true
                }
                R.id.nav_settings -> {
                    showSettings()
                    true
                }
                else -> false
            }
        }
    }

    private fun showDialogs() {
        headerTitle.text = "Диалоги"
        dialogsRecyclerView.visibility = View.VISIBLE
        settingsContainer.visibility = View.GONE
    }

    private fun showSettings() {
        headerTitle.text = "Настройки"
        dialogsRecyclerView.visibility = View.GONE
        settingsContainer.visibility = View.VISIBLE
        updateCallRecordingUI()
    }

    private fun loadDialogs() {
        // TODO: Load from server via API
        // For now, show empty list or mock data
        val mockDialogs = listOf(
            DialogEntity(
                id = "1",
                clientName = "Иван Петров",
                clientPhone = "+7 999 123-45-67",
                channel = "telegram",
                chatId = "123456",
                lastMessageText = "Здравствуйте, у меня не работает экран",
                lastMessageTime = System.currentTimeMillis() - 3600000,
                unreadCount = 2
            ),
            DialogEntity(
                id = "2",
                clientName = "Мария Сидорова",
                clientPhone = "+7 999 765-43-21",
                channel = "whatsapp",
                chatId = "789012",
                lastMessageText = "Спасибо за помощь!",
                lastMessageTime = System.currentTimeMillis() - 86400000,
                unreadCount = 0
            ),
            DialogEntity(
                id = "3",
                clientName = null,
                clientPhone = "+7 999 111-22-33",
                channel = "avito",
                chatId = "345678",
                lastMessageText = "",
                lastMessageTime = System.currentTimeMillis() - 172800000,
                lastMessageIsVoice = true,
                unreadCount = 1
            )
        )
        dialogsAdapter.updateDialogs(mockDialogs)
    }

    private fun openChat(dialog: DialogEntity) {
        val intent = Intent(this, AppealDetailActivity::class.java)
        intent.putExtra("dialog_id", dialog.id)
        intent.putExtra("chat_id", dialog.chatId)
        intent.putExtra("channel", dialog.channel)
        intent.putExtra("client_name", dialog.clientName ?: dialog.clientPhone ?: "Клиент")
        startActivity(intent)
    }

    private fun setupAiModeSettings() {
        // Загружаем сохранённую настройку
        val savedMode = sessionManager.getAiMode()
        when (savedMode) {
            AI_MODE_AUTOMATIC -> radioAutomatic.isChecked = true
            else -> radioSemiAutomatic.isChecked = true
        }
    }

    private fun saveSettings() {
        val operatorId = sessionManager.getOperatorId()
        val tenantId = sessionManager.getTenantId()

        if (operatorId.isNullOrBlank() || tenantId.isNullOrBlank()) {
            Toast.makeText(this, "Сессия недействительна", Toast.LENGTH_SHORT).show()
            return
        }

        val mode = when (aiModeRadioGroup.checkedRadioButtonId) {
            R.id.radioAutomatic -> AI_MODE_AUTOMATIC
            else -> AI_MODE_SEMI_AUTOMATIC
        }

        // Сохраняем локально
        sessionManager.saveAiMode(mode)

        // Преобразуем в формат для сервера
        val operationMode = if (mode == AI_MODE_AUTOMATIC) "auto" else "assist"

        saveSettingsButton.isEnabled = false
        saveSettingsButton.text = "Сохранение..."

        val request = UpdateSettingsRequest(
            operator_id = operatorId,
            tenant_id = tenantId,
            operation_mode = operationMode
        )

        RetrofitClient.getApiService(this).updateSettings(request)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    saveSettingsButton.isEnabled = true
                    saveSettingsButton.text = "Сохранить"

                    if (response.isSuccessful && response.body()?.success == true) {
                        val modeText = if (mode == AI_MODE_AUTOMATIC) "Автомат" else "Полуавтомат"
                        Toast.makeText(this@MainActivity, "Настройки сохранены: $modeText", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Ошибка сохранения: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    saveSettingsButton.isEnabled = true
                    saveSettingsButton.text = "Сохранить"
                    Toast.makeText(this@MainActivity, "Ошибка: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setupObservers() {
        // TODO: Add observers for dialogs from server
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        // Refresh dialogs when returning to screen
        loadDialogs()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> Unit

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                AlertDialog.Builder(this)
                    .setTitle("Уведомления")
                    .setMessage("Разрешите отправку push, чтобы не пропустить обращения")
                    .setPositiveButton("Разрешить") { _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    .setNegativeButton("Отмена", null)
                    .show()
            }

            else -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun handleNotificationIntent(intent: Intent?) {
        if (intent == null) return
        if (intent.getBooleanExtra("open_dialog", false)) {
            val dialogId = intent.getStringExtra("dialog_id")
            val chatId = intent.getStringExtra("chat_id")
            val channel = intent.getStringExtra("channel")
            val clientName = intent.getStringExtra("client_name")
            if (!dialogId.isNullOrBlank()) {
                val chatIntent = Intent(this, AppealDetailActivity::class.java)
                chatIntent.putExtra("dialog_id", dialogId)
                chatIntent.putExtra("chat_id", chatId)
                chatIntent.putExtra("channel", channel)
                chatIntent.putExtra("client_name", clientName ?: "Клиент")
                startActivity(chatIntent)
                intent.removeExtra("open_dialog")
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Уведомления отключены")
            .setMessage("Вы всегда можете включить уведомления в настройках приложения")
            .setPositiveButton("Понятно", null)
            .show()
    }

    private fun handleLogout() {
        logoutButton.isEnabled = false
        RetrofitClient.getApiService(this).logout()
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    logoutButton.isEnabled = true
                    if (response.isSuccessful && response.body()?.success == true) {
                        TunnelService.stop(this@MainActivity)
                        sessionManager.clearSession()
                        Toast.makeText(
                            this@MainActivity,
                            "Сеанс завершён",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToLogin()
                    } else if (response.code() == 401) {
                        TunnelService.stop(this@MainActivity)
                        sessionManager.clearSession()
                        Toast.makeText(
                            this@MainActivity,
                            "Сессия истекла",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToLogin()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка выхода: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    logoutButton.isEnabled = true
                    Toast.makeText(
                        this@MainActivity,
                        "Не удалось выйти: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    // Call Recording Methods
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
}
