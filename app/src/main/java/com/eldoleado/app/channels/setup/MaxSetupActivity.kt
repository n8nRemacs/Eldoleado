package com.eldoleado.app.channels.setup

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eldoleado.app.R
import com.eldoleado.app.channels.ChannelCredentialsManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * MAX (VK Teams) setup using QR code authentication.
 *
 * Flow:
 * 1. Request QR login token from local vkmax service
 * 2. Display QR code (max://login?token=...) for user to scan
 * 3. Poll for authentication status
 * 4. On success, save session credentials
 */
class MaxSetupActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MaxSetupActivity"
        // Local vkmax service endpoint (running on phone)
        private const val VKMAX_BASE_URL = "http://localhost:3001"
        private const val QR_POLL_INTERVAL = 2000L // 2 seconds
        private const val QR_TIMEOUT = 120000L // 120 seconds (MAX QR lives longer)
    }

    private lateinit var channelCredentialsManager: ChannelCredentialsManager

    // Views
    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var stepQrCode: LinearLayout
    private lateinit var stepSuccess: LinearLayout
    private lateinit var qrCodeImage: ImageView
    private lateinit var qrLoading: ProgressBar
    private lateinit var qrError: TextView
    private lateinit var statusText: TextView
    private lateinit var btnRefreshQr: Button
    private lateinit var successUserId: TextView
    private lateinit var successName: TextView
    private lateinit var btnDone: Button

    private var pollJob: Job? = null
    private var currentQrToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_setup)

        channelCredentialsManager = ChannelCredentialsManager(this)

        initViews()
        setupListeners()
        requestQrCode()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        headerTitle = findViewById(R.id.headerTitle)
        stepQrCode = findViewById(R.id.stepQrCode)
        stepSuccess = findViewById(R.id.stepSuccess)
        qrCodeImage = findViewById(R.id.qrCodeImage)
        qrLoading = findViewById(R.id.qrLoading)
        qrError = findViewById(R.id.qrError)
        statusText = findViewById(R.id.statusText)
        btnRefreshQr = findViewById(R.id.btnRefreshQr)
        successUserId = findViewById(R.id.successUserId)
        successName = findViewById(R.id.successName)
        btnDone = findViewById(R.id.btnDone)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnRefreshQr.setOnClickListener {
            requestQrCode()
        }

        btnDone.setOnClickListener {
            finish()
        }
    }

    private fun requestQrCode() {
        showQrLoading()
        pollJob?.cancel()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Request new QR login token from vkmax service
                val url = URL("$VKMAX_BASE_URL/qr")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    val token = json.getString("token")
                    val qrUrl = json.optString("qr_url", "max://login?token=$token")

                    withContext(Dispatchers.Main) {
                        currentQrToken = token
                        displayQrCode(qrUrl)
                        startPolling(token)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showQrError("Сервис MAX недоступен")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // For demo/development: show a placeholder QR
                    showDemoMode()
                }
            }
        }
    }

    private fun showDemoMode() {
        // Generate a demo QR code for testing UI
        val demoToken = "demo_${System.currentTimeMillis()}"
        val demoQrUrl = "max://login?token=$demoToken"
        currentQrToken = demoToken
        displayQrCode(demoQrUrl)
        statusText.text = "Демо-режим: vkmax сервис не запущен"

        // Simulate successful connection after 5 seconds for demo
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            // In demo mode, just show how it would look
            // Real implementation would wait for actual scan
        }
    }

    private fun displayQrCode(qrUrl: String) {
        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(qrUrl, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            qrCodeImage.setImageBitmap(bitmap)
            qrCodeImage.visibility = View.VISIBLE
            qrLoading.visibility = View.GONE
            qrError.visibility = View.GONE
            statusText.text = "Ожидание сканирования..."
        } catch (e: Exception) {
            showQrError("Ошибка генерации QR-кода")
        }
    }

    private fun showQrLoading() {
        qrCodeImage.visibility = View.GONE
        qrLoading.visibility = View.VISIBLE
        qrError.visibility = View.GONE
        statusText.text = "Загрузка QR-кода..."
    }

    private fun showQrError(message: String) {
        qrCodeImage.visibility = View.GONE
        qrLoading.visibility = View.GONE
        qrError.visibility = View.VISIBLE
        qrError.text = message
        statusText.text = "Нажмите «Обновить» для повторной попытки"
    }

    private fun startPolling(token: String) {
        pollJob?.cancel()
        pollJob = CoroutineScope(Dispatchers.IO).launch {
            val startTime = System.currentTimeMillis()

            while (isActive && (System.currentTimeMillis() - startTime) < QR_TIMEOUT) {
                try {
                    val url = URL("$VKMAX_BASE_URL/status?token=$token")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000

                    val responseCode = connection.responseCode
                    if (responseCode == 200) {
                        val response = connection.inputStream.bufferedReader().readText()
                        val json = JSONObject(response)

                        when (json.optString("status")) {
                            "connected" -> {
                                val accessToken = json.optString("token", "")
                                val userId = json.optString("user_id", "")
                                val name = json.optString("name", "")

                                withContext(Dispatchers.Main) {
                                    onConnectionSuccess(accessToken, userId, name)
                                }
                                return@launch
                            }
                            "expired" -> {
                                withContext(Dispatchers.Main) {
                                    statusText.text = "QR-код истёк"
                                }
                                return@launch
                            }
                        }
                    }
                } catch (e: Exception) {
                    // Ignore polling errors, just continue
                }

                delay(QR_POLL_INTERVAL)
            }

            // Timeout
            withContext(Dispatchers.Main) {
                statusText.text = "Время ожидания истекло"
            }
        }
    }

    private fun onConnectionSuccess(token: String, userId: String, name: String) {
        pollJob?.cancel()

        // Save credentials
        channelCredentialsManager.saveMax(token, userId, name)

        // Show success UI
        stepQrCode.visibility = View.GONE
        stepSuccess.visibility = View.VISIBLE
        successUserId.text = userId
        successName.text = name
        successName.visibility = if (name.isNotEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        pollJob?.cancel()
        super.onDestroy()
    }
}
