package com.eldoleado.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.adapters.DialogsAdapter
import com.eldoleado.app.data.database.entities.DialogEntity
import com.eldoleado.app.api.ApiResponse
import com.eldoleado.app.api.DialogsResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.callrecording.CallRecordingPreferences
import com.eldoleado.app.callrecording.CallRecordingService
import com.eldoleado.app.channels.ChannelCredentialsManager
import com.eldoleado.app.channels.ChannelStatus
import com.eldoleado.app.channels.ChannelType
import com.eldoleado.app.channels.setup.TelegramSetupActivity
import com.eldoleado.app.channels.setup.AvitoSetupActivity
import com.eldoleado.app.channels.setup.WhatsAppSetupActivity
import com.eldoleado.app.channels.setup.MaxSetupActivity
import com.eldoleado.app.channels.setup.ChannelDetailsActivity
import com.eldoleado.app.channels.AlertSender
import com.eldoleado.app.channels.ChannelMonitorService
// TunnelService removed - all processing in cloud now
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    // Call recording
    private lateinit var switchCallRecording: SwitchMaterial
    private lateinit var callRecordingStatusText: TextView
    private lateinit var callRecordingDescription: TextView
    private lateinit var callRecordingPreferences: CallRecordingPreferences

    // Channels
    private lateinit var channelCredentialsManager: ChannelCredentialsManager
    private lateinit var channelsSection: LinearLayout

    // Notifications
    private lateinit var notificationsSection: LinearLayout
    private lateinit var inputAlertBotToken: TextInputEditText
    private lateinit var inputAlertChatId: TextInputEditText
    private lateinit var btnTestNotification: Button
    private lateinit var switchAlertBattery: SwitchMaterial
    private lateinit var switchAlertNetwork: SwitchMaterial
    private lateinit var switchAlertChannels: SwitchMaterial
    private lateinit var btnSaveNotifications: Button


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

        // Call recording UI
        callRecordingPreferences = CallRecordingPreferences(this)
        switchCallRecording = findViewById(R.id.switchCallRecording)
        callRecordingStatusText = findViewById(R.id.callRecordingStatusText)
        callRecordingDescription = findViewById(R.id.callRecordingDescription)

        // Channels UI
        channelCredentialsManager = ChannelCredentialsManager(this)
        channelsSection = findViewById(R.id.channelsSection)

        dialogsAdapter = DialogsAdapter { dialog ->
            openChat(dialog)
        }

        dialogsRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogsRecyclerView.adapter = dialogsAdapter

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { handleLogout() }

        setupBottomNavigation()
        setupCallRecordingSettings()
        setupChannelsSection()
        setupNotificationsSection()
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

        // TunnelService removed - all processing in cloud now

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
                settingsContainer.visibility = View.GONE
                bottomNavigation.visibility = View.VISIBLE
            }
            else -> {
                // Client only - normal UI
                dialogsRecyclerView.visibility = View.VISIBLE
                settingsContainer.visibility = View.GONE
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
        updateChannelsUI()
        updateNotificationsUI()
    }

    private fun loadDialogs() {
        val sessionToken = sessionManager.getSessionToken()
        if (sessionToken.isNullOrBlank()) {
            Log.w("MainActivity", "No session token, cannot load dialogs")
            return
        }

        RetrofitClient.getApiService(this).getDialogs(sessionToken)
            .enqueue(object : Callback<DialogsResponse> {
                override fun onResponse(call: Call<DialogsResponse>, response: Response<DialogsResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val dialogs = response.body()?.dialogs?.map { dto ->
                            DialogEntity(
                                id = dto.id,
                                clientName = dto.client_name,
                                clientPhone = dto.client_phone,
                                channel = dto.channel,
                                chatId = dto.chat_id ?: "",
                                lastMessageText = dto.last_message_text ?: "",
                                lastMessageTime = dto.last_message_time ?: 0L,
                                lastMessageIsVoice = dto.last_message_is_voice ?: false,
                                unreadCount = dto.unread_count ?: 0
                            )
                        } ?: emptyList()

                        dialogsAdapter.updateDialogs(dialogs)
                        Log.i("MainActivity", "Loaded ${dialogs.size} dialogs")
                    } else if (response.code() == 401) {
                        Log.w("MainActivity", "Session expired")
                        sessionManager.clearSession()
                        navigateToLogin()
                    } else {
                        Log.e("MainActivity", "Failed to load dialogs: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DialogsResponse>, t: Throwable) {
                    Log.e("MainActivity", "Error loading dialogs: ${t.message}")
                }
            })
    }

    private fun openChat(dialog: DialogEntity) {
        Log.i("MainActivity", "Opening dialog: ${dialog.id}, channel: ${dialog.channel}, client: ${dialog.clientName}")
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("dialog_id", dialog.id)
        intent.putExtra("chat_id", dialog.chatId)
        intent.putExtra("channel", dialog.channel)
        intent.putExtra("client_name", dialog.clientName ?: dialog.clientPhone ?: "Клиент")
        intent.putExtra("client_phone", dialog.clientPhone ?: "")
        startActivity(intent)
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
        // Refresh channels status
        updateChannelsUI()
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
                        sessionManager.clearSession()
                        Toast.makeText(
                            this@MainActivity,
                            "Сеанс завершён",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToLogin()
                    } else if (response.code() == 401) {
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

    // ==================== CHANNELS SECTION ====================

    private fun setupChannelsSection() {
        // Show channels section only for Server or Both mode
        val appMode = sessionManager.getAppMode()
        val showChannels = appMode == SessionManager.MODE_SERVER || appMode == SessionManager.MODE_BOTH

        channelsSection.visibility = if (showChannels) View.VISIBLE else View.GONE

        if (!showChannels) return

        // Setup click listeners for each channel
        setupChannelItem(
            channelView = findViewById(R.id.channelTelegram),
            channelType = ChannelType.TELEGRAM,
            iconRes = R.drawable.ic_telegram
        )

        setupChannelItem(
            channelView = findViewById(R.id.channelWhatsApp),
            channelType = ChannelType.WHATSAPP,
            iconRes = R.drawable.ic_whatsapp
        )

        setupChannelItem(
            channelView = findViewById(R.id.channelAvito),
            channelType = ChannelType.AVITO,
            iconRes = R.drawable.ic_avito
        )

        setupChannelItem(
            channelView = findViewById(R.id.channelMax),
            channelType = ChannelType.MAX,
            iconRes = R.drawable.ic_max
        )

        updateChannelsUI()
    }

    private fun setupChannelItem(channelView: View, channelType: ChannelType, iconRes: Int) {
        val icon = channelView.findViewById<ImageView>(R.id.channelIcon)
        val name = channelView.findViewById<TextView>(R.id.channelName)

        icon.setImageResource(iconRes)
        name.text = channelType.displayName

        channelView.setOnClickListener {
            openChannelSetup(channelType)
        }
    }

    private fun updateChannelsUI() {
        if (!::channelsSection.isInitialized) return

        val appMode = sessionManager.getAppMode()
        val showChannels = appMode == SessionManager.MODE_SERVER || appMode == SessionManager.MODE_BOTH
        channelsSection.visibility = if (showChannels) View.VISIBLE else View.GONE

        if (!showChannels) return

        updateChannelItemUI(findViewById(R.id.channelTelegram), ChannelType.TELEGRAM)
        updateChannelItemUI(findViewById(R.id.channelWhatsApp), ChannelType.WHATSAPP)
        updateChannelItemUI(findViewById(R.id.channelAvito), ChannelType.AVITO)
        updateChannelItemUI(findViewById(R.id.channelMax), ChannelType.MAX)
    }

    private fun updateChannelItemUI(channelView: View, channelType: ChannelType) {
        val statusText = channelView.findViewById<TextView>(R.id.channelStatus)
        val statusIndicator = channelView.findViewById<View>(R.id.statusIndicator)

        val status = channelCredentialsManager.getChannelStatus(channelType)
        val displayInfo = channelCredentialsManager.getChannelDisplayInfo(channelType)

        when (status) {
            ChannelStatus.CONNECTED -> {
                statusText.text = displayInfo ?: "Подключено"
                statusIndicator.setBackgroundResource(R.drawable.status_connected)
            }
            ChannelStatus.ERROR -> {
                statusText.text = "Ошибка подключения"
                statusIndicator.setBackgroundResource(R.drawable.status_error)
            }
            ChannelStatus.CHECKING -> {
                statusText.text = "Проверка..."
                statusIndicator.setBackgroundResource(R.drawable.status_checking)
            }
            ChannelStatus.NOT_CONFIGURED -> {
                statusText.text = "Не настроено"
                statusIndicator.setBackgroundResource(R.drawable.status_not_configured)
            }
        }
    }

    private fun openChannelSetup(channelType: ChannelType) {
        val status = channelCredentialsManager.getChannelStatus(channelType)

        // If channel is configured, show details; otherwise show setup wizard
        if (status == ChannelStatus.CONNECTED || status == ChannelStatus.ERROR) {
            ChannelDetailsActivity.start(this, channelType)
        } else {
            val intent = when (channelType) {
                ChannelType.TELEGRAM -> Intent(this, TelegramSetupActivity::class.java)
                ChannelType.AVITO -> Intent(this, AvitoSetupActivity::class.java)
                ChannelType.WHATSAPP -> Intent(this, WhatsAppSetupActivity::class.java)
                ChannelType.MAX -> Intent(this, MaxSetupActivity::class.java)
            }
            startActivity(intent)
        }
    }

    // ==================== NOTIFICATIONS SECTION ====================

    private fun setupNotificationsSection() {
        // Show notifications section only for Server or Both mode
        val appMode = sessionManager.getAppMode()
        val showNotifications = appMode == SessionManager.MODE_SERVER || appMode == SessionManager.MODE_BOTH

        notificationsSection = findViewById(R.id.notificationsSection)
        notificationsSection.visibility = if (showNotifications) View.VISIBLE else View.GONE

        if (!showNotifications) return

        // Init views
        inputAlertBotToken = findViewById(R.id.inputAlertBotToken)
        inputAlertChatId = findViewById(R.id.inputAlertChatId)
        btnTestNotification = findViewById(R.id.btnTestNotification)
        switchAlertBattery = findViewById(R.id.switchAlertBattery)
        switchAlertNetwork = findViewById(R.id.switchAlertNetwork)
        switchAlertChannels = findViewById(R.id.switchAlertChannels)
        btnSaveNotifications = findViewById(R.id.btnSaveNotifications)

        // Load saved values
        inputAlertBotToken.setText(channelCredentialsManager.getAlertBotToken() ?: "")
        inputAlertChatId.setText(channelCredentialsManager.getAlertChatId() ?: "")
        switchAlertBattery.isChecked = channelCredentialsManager.isAlertBatteryEnabled()
        switchAlertNetwork.isChecked = channelCredentialsManager.isAlertNetworkEnabled()
        switchAlertChannels.isChecked = channelCredentialsManager.isAlertChannelsEnabled()

        // Test notification button
        btnTestNotification.setOnClickListener {
            sendTestNotification()
        }

        // Save button
        btnSaveNotifications.setOnClickListener {
            saveNotificationSettings()
        }

        // Start monitor service if configured
        if (!channelCredentialsManager.getAlertBotToken().isNullOrBlank() &&
            !channelCredentialsManager.getAlertChatId().isNullOrBlank()) {
            ChannelMonitorService.start(this)
        }
    }

    private fun updateNotificationsUI() {
        if (!::notificationsSection.isInitialized) return

        val appMode = sessionManager.getAppMode()
        val showNotifications = appMode == SessionManager.MODE_SERVER || appMode == SessionManager.MODE_BOTH
        notificationsSection.visibility = if (showNotifications) View.VISIBLE else View.GONE
    }

    private fun sendTestNotification() {
        val botToken = inputAlertBotToken.text?.toString()?.trim()
        val chatId = inputAlertChatId.text?.toString()?.trim()

        if (botToken.isNullOrBlank() || chatId.isNullOrBlank()) {
            Toast.makeText(this, "Заполните Bot Token и Chat ID", Toast.LENGTH_SHORT).show()
            return
        }

        // Save before testing
        channelCredentialsManager.saveAlertSettings(
            botToken,
            chatId,
            switchAlertBattery.isChecked,
            switchAlertNetwork.isChecked,
            switchAlertChannels.isChecked
        )

        btnTestNotification.isEnabled = false
        btnTestNotification.text = "Отправка..."

        val alertSender = AlertSender(this)
        CoroutineScope(Dispatchers.Main).launch {
            val success = alertSender.sendTestMessage()
            btnTestNotification.isEnabled = true
            btnTestNotification.text = "Отправить тестовое сообщение"

            if (success) {
                Toast.makeText(this@MainActivity, "Тестовое сообщение отправлено!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Ошибка отправки. Проверьте данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNotificationSettings() {
        val botToken = inputAlertBotToken.text?.toString()?.trim()
        val chatId = inputAlertChatId.text?.toString()?.trim()

        channelCredentialsManager.saveAlertSettings(
            botToken,
            chatId,
            switchAlertBattery.isChecked,
            switchAlertNetwork.isChecked,
            switchAlertChannels.isChecked
        )

        Toast.makeText(this, "Настройки уведомлений сохранены", Toast.LENGTH_SHORT).show()

        // Start or restart monitor service if configured
        if (!botToken.isNullOrBlank() && !chatId.isNullOrBlank()) {
            ChannelMonitorService.start(this)
        } else {
            ChannelMonitorService.stop(this)
        }
    }
}
