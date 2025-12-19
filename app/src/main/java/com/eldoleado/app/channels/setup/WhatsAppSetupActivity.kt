package com.eldoleado.app.channels.setup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eldoleado.app.R
import com.eldoleado.app.SessionManager
import com.eldoleado.app.channels.ChannelCredentialsManager
import com.eldoleado.app.nodejs.NodeJSBridge
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * WhatsApp setup using Baileys service on Finnish server.
 *
 * Flow:
 * 1. Create session via POST /sessions
 * 2. Poll GET /sessions/:id/qr for QR code
 * 3. Display QR code for user to scan with WhatsApp
 * 4. Poll for connection status
 * 5. On success, save session credentials
 */
class WhatsAppSetupActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WhatsAppSetupActivity"
        // Local Node.js server (embedded Baileys)
        private const val LOCAL_BAILEYS_URL = "http://127.0.0.1:3000"
        // Fallback to Finnish server if local doesn't work
        private const val REMOTE_BAILEYS_URL = "http://217.145.79.27:8766"
        private const val QR_POLL_INTERVAL = 3000L // 3 seconds
        private const val QR_TIMEOUT = 120000L // 120 seconds
    }

    private lateinit var channelCredentialsManager: ChannelCredentialsManager
    private lateinit var sessionManager: SessionManager
    private lateinit var nodeJSBridge: NodeJSBridge
    private var useLocalBaileys = true

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
    private var currentSessionId: String? = null
    private var currentQrData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_setup)

        channelCredentialsManager = ChannelCredentialsManager(this)
        sessionManager = SessionManager(this)
        nodeJSBridge = NodeJSBridge(this)

        initViews()
        setupListeners()
        startLocalBaileysAndConnect()
    }

    private fun startLocalBaileysAndConnect() {
        showQrLoading()
        statusText.text = "Запуск WhatsApp сервиса..."

        // Start embedded Node.js with Baileys
        if (!nodeJSBridge.isRunning()) {
            nodeJSBridge.start()
        }

        // Wait for Node.js to start and then request QR
        CoroutineScope(Dispatchers.IO).launch {
            // Give Node.js time to start
            delay(5000)

            // Check if local server is available
            val isLocalAvailable = checkLocalServer()

            withContext(Dispatchers.Main) {
                if (isLocalAvailable) {
                    useLocalBaileys = true
                    Log.i(TAG, "Using local Baileys server")
                    requestQrFromLocal()
                } else {
                    useLocalBaileys = false
                    Log.i(TAG, "Local server not available, using remote")
                    createSessionAndRequestQr()
                }
            }
        }
    }

    private fun checkLocalServer(): Boolean {
        return try {
            val url = URL("$LOCAL_BAILEYS_URL/status")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            val responseCode = connection.responseCode
            responseCode == 200
        } catch (e: Exception) {
            Log.w(TAG, "Local server check failed: ${e.message}")
            false
        }
    }

    private fun requestQrFromLocal() {
        showQrLoading()
        pollJob?.cancel()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Trigger connection
                val connectUrl = URL("$LOCAL_BAILEYS_URL/connect")
                val connectConn = connectUrl.openConnection() as HttpURLConnection
                connectConn.requestMethod = "POST"
                connectConn.connectTimeout = 5000
                connectConn.doOutput = true
                connectConn.outputStream.close()
                connectConn.responseCode // trigger

                // Wait for QR to be generated
                delay(3000)

                // Get QR code
                val qrUrl = URL("$LOCAL_BAILEYS_URL/qr")
                val qrConn = qrUrl.openConnection() as HttpURLConnection
                qrConn.requestMethod = "GET"
                qrConn.connectTimeout = 5000

                if (qrConn.responseCode == 200) {
                    val response = qrConn.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)

                    if (json.optBoolean("success", false)) {
                        val qr = json.optString("qr", "")
                        if (qr.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                displayQrCode(qr)
                                startLocalPolling()
                            }
                            return@launch
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    showQrError("QR-код не готов")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Local QR request failed: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    showQrError("Ошибка: ${e.message}")
                }
            }
        }
    }

    private fun startLocalPolling() {
        pollJob?.cancel()
        pollJob = CoroutineScope(Dispatchers.IO).launch {
            val startTime = System.currentTimeMillis()

            while (isActive && (System.currentTimeMillis() - startTime) < QR_TIMEOUT) {
                try {
                    val url = URL("$LOCAL_BAILEYS_URL/status")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000

                    if (connection.responseCode == 200) {
                        val response = connection.inputStream.bufferedReader().readText()
                        val json = JSONObject(response)
                        val status = json.optString("status", "")

                        Log.i(TAG, "Local poll status: $status")

                        when (status) {
                            "connected" -> {
                                val phone = json.optString("phone", "")
                                val name = json.optString("name", "")

                                withContext(Dispatchers.Main) {
                                    onConnectionSuccess(phone, name, "local_baileys")
                                }
                                return@launch
                            }
                            "qr" -> {
                                // QR might have changed, refresh it
                                val qrUrl = URL("$LOCAL_BAILEYS_URL/qr")
                                val qrConn = qrUrl.openConnection() as HttpURLConnection
                                if (qrConn.responseCode == 200) {
                                    val qrResponse = qrConn.inputStream.bufferedReader().readText()
                                    val qrJson = JSONObject(qrResponse)
                                    val qr = qrJson.optString("qr", "")
                                    if (qr.isNotEmpty() && qr != currentQrData) {
                                        withContext(Dispatchers.Main) {
                                            displayQrCode(qr)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Local poll error: ${e.message}")
                }

                delay(QR_POLL_INTERVAL)
            }

            withContext(Dispatchers.Main) {
                statusText.text = "Время ожидания истекло"
            }
        }
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
            if (useLocalBaileys) {
                requestQrFromLocal()
            } else {
                createSessionAndRequestQr()
            }
        }

        btnDone.setOnClickListener {
            finish()
        }
    }

    private fun createSessionAndRequestQr() {
        showQrLoading()
        pollJob?.cancel()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Step 1: Create session
                val sessionId = createSession()
                if (sessionId == null) {
                    withContext(Dispatchers.Main) {
                        showQrError("Не удалось создать сессию")
                    }
                    return@launch
                }

                currentSessionId = sessionId
                Log.i(TAG, "Session created: $sessionId")

                // Step 2: Wait a moment for QR to be generated
                delay(2000)

                // Step 3: Get QR code
                val qrResult = getQrCode(sessionId)

                withContext(Dispatchers.Main) {
                    if (qrResult != null) {
                        if (qrResult.first != null) {
                            // Got base64 image
                            displayQrImage(qrResult.first!!)
                        } else if (qrResult.second != null) {
                            // Got QR string data
                            displayQrCode(qrResult.second!!)
                        }
                        startPolling(sessionId)
                    } else {
                        showQrError("QR-код не готов, попробуйте снова")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    showQrError("Ошибка подключения: ${e.message}")
                }
            }
        }
    }

    private fun createSession(): String? {
        try {
            val url = URL("$REMOTE_BAILEYS_URL/sessions")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            // Generate unique session ID based on operator
            val operatorId = sessionManager.getOperatorId() ?: "default"
            val sessionId = "wa_${operatorId}_${System.currentTimeMillis()}"

            val requestBody = JSONObject().apply {
                put("sessionId", sessionId)
                put("webhookUrl", "https://n8n.n8nsrv.ru/webhook/whatsapp-incoming")
            }

            OutputStreamWriter(connection.outputStream).use {
                it.write(requestBody.toString())
            }

            val responseCode = connection.responseCode
            Log.i(TAG, "Create session response: $responseCode")

            if (responseCode == 200 || responseCode == 201) {
                val response = connection.inputStream.bufferedReader().readText()
                Log.i(TAG, "Create session response body: $response")
                val json = JSONObject(response)
                if (json.optBoolean("success", false)) {
                    return sessionId
                }
            } else {
                val errorResponse = connection.errorStream?.bufferedReader()?.readText()
                Log.e(TAG, "Create session error: $errorResponse")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Create session exception: ${e.message}", e)
        }
        return null
    }

    private fun getQrCode(sessionId: String): Pair<String?, String?>? {
        try {
            val url = URL("$REMOTE_BAILEYS_URL/sessions/$sessionId/qr")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val responseCode = connection.responseCode
            Log.i(TAG, "Get QR response: $responseCode")

            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                Log.i(TAG, "QR response: ${response.take(200)}...")
                val json = JSONObject(response)

                if (json.optBoolean("success", false)) {
                    val data = json.optJSONObject("data")
                    val status = data?.optString("status", "")

                    if (status == "connected") {
                        // Already connected!
                        return null
                    }

                    // Try to get QR image (base64 data URL)
                    val qrImage = data?.optString("qrImage", "")
                    if (!qrImage.isNullOrEmpty() && qrImage.startsWith("data:image")) {
                        val base64Data = qrImage.substringAfter("base64,")
                        return Pair(base64Data, null)
                    }

                    // Try to get QR string
                    val qr = data?.optString("qr", "")
                    if (!qr.isNullOrEmpty()) {
                        return Pair(null, qr)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Get QR exception: ${e.message}", e)
        }
        return null
    }

    private fun displayQrImage(base64Data: String) {
        try {
            val decodedBytes = Base64.decode(base64Data, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            qrCodeImage.setImageBitmap(bitmap)
            qrCodeImage.visibility = View.VISIBLE
            qrLoading.visibility = View.GONE
            qrError.visibility = View.GONE
            statusText.text = "Отсканируйте QR-код в WhatsApp"
        } catch (e: Exception) {
            Log.e(TAG, "Display QR image error: ${e.message}")
            showQrError("Ошибка отображения QR-кода")
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
            statusText.text = "Отсканируйте QR-код в WhatsApp"
        } catch (e: Exception) {
            showQrError("Ошибка генерации QR-кода")
        }
    }

    private fun showQrLoading() {
        qrCodeImage.visibility = View.GONE
        qrLoading.visibility = View.VISIBLE
        qrError.visibility = View.GONE
        statusText.text = "Подключение к серверу..."
    }

    private fun showQrError(message: String) {
        qrCodeImage.visibility = View.GONE
        qrLoading.visibility = View.GONE
        qrError.visibility = View.VISIBLE
        qrError.text = message
        statusText.text = "Нажмите «Обновить» для повторной попытки"
    }

    private fun startPolling(sessionId: String) {
        pollJob?.cancel()
        pollJob = CoroutineScope(Dispatchers.IO).launch {
            val startTime = System.currentTimeMillis()

            while (isActive && (System.currentTimeMillis() - startTime) < QR_TIMEOUT) {
                try {
                    val url = URL("$REMOTE_BAILEYS_URL/sessions/$sessionId/status")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000

                    val responseCode = connection.responseCode
                    if (responseCode == 200) {
                        val response = connection.inputStream.bufferedReader().readText()
                        val json = JSONObject(response)

                        if (json.optBoolean("success", false)) {
                            val data = json.optJSONObject("data")
                            val status = data?.optString("status", "")

                            Log.i(TAG, "Poll status: $status")

                            when (status) {
                                "connected" -> {
                                    val info = data?.optJSONObject("info")
                                    val phone = info?.optString("phone", "") ?: ""
                                    val name = info?.optString("name", "") ?: ""

                                    withContext(Dispatchers.Main) {
                                        onConnectionSuccess(phone, name, sessionId)
                                    }
                                    return@launch
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Poll error: ${e.message}")
                }

                delay(QR_POLL_INTERVAL)
            }

            // Timeout
            withContext(Dispatchers.Main) {
                statusText.text = "Время ожидания истекло"
            }
        }
    }

    private fun onConnectionSuccess(phone: String, name: String, sessionId: String) {
        pollJob?.cancel()

        // Save credentials
        channelCredentialsManager.saveWhatsApp(sessionId, phone, name)

        Toast.makeText(this, "WhatsApp подключен!", Toast.LENGTH_SHORT).show()

        // Show success UI
        stepQrCode.visibility = View.GONE
        stepSuccess.visibility = View.VISIBLE
        successPhone.text = if (phone.isNotEmpty()) phone else "Подключено"
        successName.text = name
        successName.visibility = if (name.isNotEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        pollJob?.cancel()
        nodeJSBridge.stop()
        super.onDestroy()
    }
}
