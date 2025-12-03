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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.adapters.AppealsAdapter
import com.eldoleado.app.api.ApiResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.api.UpdateSettingsRequest
import com.eldoleado.app.callrecording.CallRecordingPreferences
import com.eldoleado.app.callrecording.CallRecordingService
import com.eldoleado.app.viewmodel.AppealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appealsAdapter: AppealsAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var logoutButton: Button
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var appealsRecyclerView: RecyclerView
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

    private val viewModel: AppealsViewModel by viewModels {
        AppealsViewModel.Factory(EldoleadoApplication.instance.repository)
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

        appealsRecyclerView = findViewById(R.id.appealsRecyclerView)
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

        appealsAdapter = AppealsAdapter { appeal ->
            openAppealDetail(appeal.id)
        }

        appealsRecyclerView.layoutManager = LinearLayoutManager(this)
        appealsRecyclerView.adapter = appealsAdapter

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { handleLogout() }
        saveSettingsButton.setOnClickListener { saveSettings() }

        setupBottomNavigation()
        setupAiModeSettings()
        setupCallRecordingSettings()
        setupObservers()
        requestNotificationPermissionIfNeeded()
        handleNotificationIntent(intent)

        // Начальная загрузка данных с сервера
        refreshAppeals()

        // Проверяем, нужно ли открыть настройки
        if (intent.getBooleanExtra("open_settings", false)) {
            bottomNavigation.selectedItemId = R.id.nav_settings
            showSettings()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_appeals -> {
                    showAppeals()
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

    private fun showAppeals() {
        headerTitle.text = "Обращения"
        appealsRecyclerView.visibility = View.VISIBLE
        settingsContainer.visibility = View.GONE
    }

    private fun showSettings() {
        headerTitle.text = "Настройки"
        appealsRecyclerView.visibility = View.GONE
        settingsContainer.visibility = View.VISIBLE
        updateCallRecordingUI()
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Наблюдаем за списком обращений из кэша
                launch {
                    viewModel.appeals.collect { appeals ->
                        appealsAdapter.updateAppealsFromEntities(appeals)
                    }
                }

                // Наблюдаем за ошибками
                launch {
                    viewModel.error.collect { error ->
                        error?.let {
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        // При возврате на экран обновляем данные с сервера
        refreshAppeals()
    }

    private fun refreshAppeals() {
        val operatorId = sessionManager.getOperatorId()
        if (operatorId.isNullOrBlank()) {
            Toast.makeText(this, "Сессия недействительна", Toast.LENGTH_SHORT).show()
            navigateToLogin()
            return
        }
        viewModel.refresh(operatorId)
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
        if (intent.getBooleanExtra("open_appeal_detail", false)) {
            val appealId = intent.getStringExtra("appeal_id")
            if (!appealId.isNullOrBlank()) {
                openAppealDetail(appealId)
                intent.removeExtra("open_appeal_detail")
                intent.removeExtra("appeal_id")
            }
        }
    }

    private fun openAppealDetail(appealId: String) {
        val detailIntent = Intent(this, AppealDetailActivity::class.java)
        detailIntent.putExtra("appeal_id", appealId)
        startActivity(detailIntent)
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
}
