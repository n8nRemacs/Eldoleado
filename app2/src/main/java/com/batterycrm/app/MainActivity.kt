package com.batterycrm.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
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
import com.batterycrm.app.adapters.AppealsAdapter
import com.batterycrm.app.api.ApiResponse
import com.batterycrm.app.api.RetrofitClient
import com.batterycrm.app.api.UpdateSettingsRequest
import com.batterycrm.app.viewmodel.AppealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var settingsContainer: LinearLayout
    private lateinit var headerTitle: TextView
    private lateinit var aiModeRadioGroup: RadioGroup
    private lateinit var radioAutomatic: RadioButton
    private lateinit var radioSemiAutomatic: RadioButton
    private lateinit var saveSettingsButton: Button

    companion object {
        const val PREF_AI_MODE = "ai_mode"
        const val AI_MODE_AUTOMATIC = "automatic"
        const val AI_MODE_SEMI_AUTOMATIC = "semi_automatic"
    }

    private val viewModel: AppealsViewModel by viewModels {
        AppealsViewModel.Factory(BatteryCRMApplication.instance.repository)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            showPermissionDeniedDialog()
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
}
