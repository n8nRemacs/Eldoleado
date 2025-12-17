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
 * WhatsApp setup using Baileys-style QR code authentication.
 *
 * Flow:
 * 1. Request QR code from local Baileys service
 * 2. Display QR code for user to scan with WhatsApp
 * 3. Poll for authentication status
 * 4. On success, save session credentials
 */
class WhatsAppSetupActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WhatsAppSetupActivity"
        // Local Baileys service endpoint (running on phone)
        private const val BAILEYS_BASE_URL = "http://localhost:3000"
        private const val QR_POLL_INTERVAL = 2000L // 2 seconds
        private const val QR_TIMEOUT = 60000L // 60 seconds
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
    private lateinit var successPhone: TextView
    private lateinit var successName: TextView
    private lateinit var btnDone: Button

    private var pollJob: Job? = null
    private var currentQrData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_setup)

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
        successPhone = findViewById(R.id.successPhone)
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
                // Request new QR code from Baileys service
                val url = URL("$BAILEYS_BASE_URL/qr")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    val qrData = json.getString("qr")

                    withContext(Dispatchers.Main) {
                        displayQrCode(qrData)
                        startPolling()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showQrError("Сервис WhatsApp недоступен")
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
        val demoData = "whatsapp://link?code=DEMO_${System.currentTimeMillis()}"
        displayQrCode(demoData)
        statusText.text = "Демо-режим: Baileys сервис не запущен"

        // Simulate successful connection after 5 seconds for demo
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            // In demo mode, just show how it would look
            // Real implementation would wait for actual scan
        }
    }

    private fun displayQrCode(data: String) {
        currentQrData = data

        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
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

    private fun startPolling() {
        pollJob?.cancel()
        pollJob = CoroutineScope(Dispatchers.IO).launch {
            val startTime = System.currentTimeMillis()

            while (isActive && (System.currentTimeMillis() - startTime) < QR_TIMEOUT) {
                try {
                    val url = URL("$BAILEYS_BASE_URL/status")
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
                                val phone = json.optString("phone", "")
                                val name = json.optString("name", "")
                                val session = json.optString("session", "")

                                withContext(Dispatchers.Main) {
                                    onConnectionSuccess(phone, name, session)
                                }
                                return@launch
                            }
                            "qr_updated" -> {
                                val newQr = json.optString("qr", "")
                                if (newQr.isNotEmpty() && newQr != currentQrData) {
                                    withContext(Dispatchers.Main) {
                                        displayQrCode(newQr)
                                    }
                                }
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

    private fun onConnectionSuccess(phone: String, name: String, session: String) {
        pollJob?.cancel()

        // Save credentials
        channelCredentialsManager.saveWhatsApp(session, phone, name)

        // Show success UI
        stepQrCode.visibility = View.GONE
        stepSuccess.visibility = View.VISIBLE
        successPhone.text = phone
        successName.text = name
        successName.visibility = if (name.isNotEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        pollJob?.cancel()
        super.onDestroy()
    }
}
