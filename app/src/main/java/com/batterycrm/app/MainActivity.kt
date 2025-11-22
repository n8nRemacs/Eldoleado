package com.batterycrm.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batterycrm.app.adapters.AppealsAdapter
import com.batterycrm.app.api.ApiResponse
import com.batterycrm.app.api.AppealsListResponse
import com.batterycrm.app.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appealsAdapter: AppealsAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var logoutButton: Button

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

        val appealsRecyclerView: RecyclerView = findViewById(R.id.appealsRecyclerView)
        appealsAdapter = AppealsAdapter(emptyList()) { appeal ->
            appeal.id?.let { openAppealDetail(it) }
        }

        appealsRecyclerView.layoutManager = LinearLayoutManager(this)
        appealsRecyclerView.adapter = appealsAdapter

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { handleLogout() }

        requestNotificationPermissionIfNeeded()
        handleNotificationIntent(intent)
        loadAppealsList()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        loadAppealsList()
    }

    private fun loadAppealsList() {
        val operatorId = sessionManager.getOperatorId()
        if (operatorId.isNullOrBlank()) {
            Toast.makeText(this, "Сессия недействительна", Toast.LENGTH_SHORT).show()
            navigateToLogin()
            return
        }

        RetrofitClient.getApiService(this).getAppealsList(operatorId)
            .enqueue(object : Callback<AppealsListResponse> {
                override fun onResponse(
                    call: Call<AppealsListResponse>,
                    response: Response<AppealsListResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val appeals = response.body()?.appeals.orEmpty()
                        appealsAdapter.updateAppeals(appeals)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка загрузки: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AppealsListResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Сеть недоступна: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
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
