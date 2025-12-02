package com.callrecorder.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.callrecorder.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: RecordingPreferences

    private val requiredPermissions = mutableListOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            add(Manifest.permission.READ_PHONE_NUMBERS)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            onPermissionsGranted()
        } else {
            Toast.makeText(
                this,
                "Permissions are required for call recording to work",
                Toast.LENGTH_LONG
            ).show()
        }
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = CallRecorderApp.instance.preferences

        setupUI()
        checkPermissions()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun setupUI() {
        // Recording toggle
        binding.switchRecording.setOnCheckedChangeListener { _, isChecked ->
            preferences.isRecordingEnabled = isChecked
            updateStatusText()
        }

        // Auto-upload toggle
        binding.switchAutoUpload.setOnCheckedChangeListener { _, isChecked ->
            preferences.autoUpload = isChecked
        }

        // Delete after upload toggle
        binding.switchDeleteAfterUpload.setOnCheckedChangeListener { _, isChecked ->
            preferences.deleteAfterUpload = isChecked
        }

        // Server URL input
        binding.btnSaveServerUrl.setOnClickListener {
            val url = binding.editServerUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                preferences.serverUrl = url
                Toast.makeText(this, "Server URL saved", Toast.LENGTH_SHORT).show()
            }
        }

        // Upload pending files button
        binding.btnUploadPending.setOnClickListener {
            UploadWorker.scheduleAllPendingUploads(this)
            Toast.makeText(this, "Pending uploads scheduled", Toast.LENGTH_SHORT).show()
        }

        // Grant permissions button
        binding.btnGrantPermissions.setOnClickListener {
            requestPermissions()
        }

        // Battery optimization button
        binding.btnBatteryOptimization.setOnClickListener {
            requestBatteryOptimization()
        }
    }

    private fun updateUI() {
        // Update switches
        binding.switchRecording.isChecked = preferences.isRecordingEnabled
        binding.switchAutoUpload.isChecked = preferences.autoUpload
        binding.switchDeleteAfterUpload.isChecked = preferences.deleteAfterUpload

        // Update server URL
        binding.editServerUrl.setText(preferences.serverUrl)

        // Update pending files count
        val pendingCount = getPendingFilesCount()
        binding.tvPendingCount.text = "Pending files: $pendingCount"

        // Update permissions status
        updatePermissionsStatus()
        updateStatusText()
    }

    private fun updateStatusText() {
        val status = if (preferences.isRecordingEnabled) {
            "Recording is ENABLED"
        } else {
            "Recording is DISABLED"
        }
        binding.tvStatus.text = status
    }

    private fun updatePermissionsStatus() {
        val allGranted = hasAllPermissions()
        binding.tvPermissionsStatus.text = if (allGranted) {
            "All permissions granted"
        } else {
            "Some permissions missing"
        }
        binding.btnGrantPermissions.isEnabled = !allGranted
    }

    private fun getPendingFilesCount(): Int {
        val recordingsDir = CallRecorderApp.instance.getRecordingsDirectory()
        return if (recordingsDir.exists()) {
            recordingsDir.listFiles()?.count { it.isFile && it.extension == "m4a" } ?: 0
        } else {
            0
        }
    }

    private fun hasAllPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun checkPermissions() {
        if (!hasAllPermissions()) {
            showPermissionExplanationDialog()
        } else {
            onPermissionsGranted()
        }
    }

    private fun showPermissionExplanationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage(
                "This app needs the following permissions to record calls:\n\n" +
                        "• Microphone - to record audio\n" +
                        "• Phone State - to detect incoming/outgoing calls\n" +
                        "• Call Log - to get phone numbers\n" +
                        "• Notifications - to show recording status\n\n" +
                        "Please grant all permissions for the app to work correctly."
            )
            .setPositiveButton("Grant Permissions") { _, _ ->
                requestPermissions()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun requestPermissions() {
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun onPermissionsGranted() {
        // Start the foreground service
        startRecordingService()

        // Request battery optimization exemption
        if (!isIgnoringBatteryOptimizations()) {
            showBatteryOptimizationDialog()
        }
    }

    private fun startRecordingService() {
        val intent = Intent(this, CallRecordingService::class.java).apply {
            action = CallRecordingService.ACTION_START_SERVICE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun isIgnoringBatteryOptimizations(): Boolean {
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(packageName)
    }

    private fun showBatteryOptimizationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Battery Optimization")
            .setMessage(
                "To ensure the app works reliably in the background, " +
                        "please disable battery optimization for this app."
            )
            .setPositiveButton("Open Settings") { _, _ ->
                requestBatteryOptimization()
            }
            .setNegativeButton("Later", null)
            .show()
    }

    private fun requestBatteryOptimization() {
        try {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to battery optimization settings
            try {
                val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Please disable battery optimization manually in Settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
