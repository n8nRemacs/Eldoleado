package com.eldoleado.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.eldoleado.app.api.ChatMessageDto
import com.eldoleado.app.api.ChatMessagesResponse
import com.eldoleado.app.api.NormalizeDialogRequest
import com.eldoleado.app.api.NormalizeDialogResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.api.SendChatMessageRequest
import com.eldoleado.app.api.SendChatMessageResponse
import com.eldoleado.app.adapters.ChatMessagesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ChatActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ChatActivity"
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    private lateinit var sessionManager: SessionManager
    private lateinit var messagesAdapter: ChatMessagesAdapter

    private lateinit var tvClientName: TextView
    private lateinit var tvChannel: TextView
    private lateinit var ivBack: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var inputMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var btnNormalize: ImageButton
    private lateinit var btnAudio: ImageButton
    private lateinit var btnClear: ImageButton
    private lateinit var btnReject: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recordingTimer: TextView
    private lateinit var btnCall: ImageButton
    private lateinit var channelButtonsContainer: LinearLayout
    private lateinit var btnChannelTelegram: ImageButton
    private lateinit var btnChannelWhatsApp: ImageButton
    private lateinit var btnChannelAvito: ImageButton
    private lateinit var btnChannelMax: ImageButton

    private var dialogId: String = ""
    private var channel: String = ""
    private var clientName: String = ""
    private var clientPhone: String = ""
    private var selectedResponseChannel: String = ""

    // Audio recording
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var isRecording = false
    private var recordingStartTime: Long = 0
    private val recordingHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sessionManager = SessionManager(this)

        dialogId = intent.getStringExtra("dialog_id") ?: ""
        channel = intent.getStringExtra("channel") ?: ""
        clientName = intent.getStringExtra("client_name") ?: "Клиент"
        clientPhone = intent.getStringExtra("client_phone") ?: ""
        selectedResponseChannel = channel  // Default to current dialog channel

        if (dialogId.isEmpty()) {
            Log.e(TAG, "No dialog_id provided")
            finish()
            return
        }

        initViews()
        setupAdapter()
        loadMessages()
        setupEventBusListener()
    }

    private fun setupEventBusListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                AppealEventBus.events.collect { event ->
                    when (event) {
                        is AppealUpdateEvent.NewMessage -> {
                            if (event.appealId == dialogId) {
                                Log.d(TAG, "FCM event: new message for current dialog, refreshing...")
                                loadMessages()
                            }
                        }
                        else -> { /* ignore other events */ }
                    }
                }
            }
        }
    }

    private fun initViews() {
        tvClientName = findViewById(R.id.tvClientName)
        tvChannel = findViewById(R.id.tvChannel)
        ivBack = findViewById(R.id.ivBack)
        recyclerView = findViewById(R.id.messagesRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        tvEmpty = findViewById(R.id.tvEmpty)
        inputMessage = findViewById(R.id.inputMessage)
        btnSend = findViewById(R.id.btnSend)
        btnNormalize = findViewById(R.id.btnNormalize)
        btnAudio = findViewById(R.id.btnAudio)
        btnClear = findViewById(R.id.btnClear)
        btnReject = findViewById(R.id.btnReject)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        recordingTimer = findViewById(R.id.recordingTimer)
        btnCall = findViewById(R.id.btnCall)
        channelButtonsContainer = findViewById(R.id.channelButtonsContainer)
        btnChannelTelegram = findViewById(R.id.btnChannelTelegram)
        btnChannelWhatsApp = findViewById(R.id.btnChannelWhatsApp)
        btnChannelAvito = findViewById(R.id.btnChannelAvito)
        btnChannelMax = findViewById(R.id.btnChannelMax)

        tvClientName.text = clientName
        tvChannel.text = channel.uppercase()

        ivBack.setOnClickListener { finish() }

        // Call button - dial client phone
        btnCall.setOnClickListener { dialClientPhone() }

        // Channel buttons - switch response channel
        setupChannelButtons()

        // Send button
        btnSend.setOnClickListener {
            val text = inputMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(text)
            }
        }

        // Normalize button
        btnNormalize.setOnClickListener {
            val text = inputMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                normalizeText(text)
            } else {
                Toast.makeText(this, "Введите текст для нормализации", Toast.LENGTH_SHORT).show()
            }
        }

        // Clear button
        btnClear.setOnClickListener {
            inputMessage.text.clear()
        }

        // Reject button
        btnReject.setOnClickListener {
            Toast.makeText(this, "Отклонение в разработке", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dialogs -> {
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Настройки в разработке", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Audio button - hold to record
        setupAudioButton()
    }

    @Suppress("ClickableViewAccessibility")
    private fun setupAudioButton() {
        btnAudio.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (checkAudioPermission()) {
                        startRecording()
                    }
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (isRecording) {
                        stopRecording()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun checkAudioPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Удерживайте кнопку для записи", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Разрешение на запись аудио отклонено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapter() {
        messagesAdapter = ChatMessagesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = messagesAdapter
    }

    private fun loadMessages() {
        val sessionToken = sessionManager.getSessionToken()
        if (sessionToken.isNullOrBlank()) {
            Log.e(TAG, "No session token")
            finish()
            return
        }

        progressBar.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE

        RetrofitClient.getApiService(this).getChatMessages(dialogId, sessionToken)
            .enqueue(object : Callback<ChatMessagesResponse> {
                override fun onResponse(
                    call: Call<ChatMessagesResponse>,
                    response: Response<ChatMessagesResponse>
                ) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful && response.body()?.success == true) {
                        val data = response.body()!!

                        // Update header with dialog info
                        data.dialog?.let { dialog ->
                            tvClientName.text = dialog.client_name ?: dialog.client_phone ?: clientName
                            tvChannel.text = dialog.channel?.uppercase() ?: channel.uppercase()
                            // Update client phone if available from API
                            if (!dialog.client_phone.isNullOrBlank()) {
                                clientPhone = dialog.client_phone
                            }
                            // Update selected channel if different
                            if (!dialog.channel.isNullOrBlank()) {
                                channel = dialog.channel
                                selectedResponseChannel = dialog.channel
                                updateChannelButtonsUI()
                            }
                        }

                        // Show messages
                        val messages = data.messages ?: emptyList()
                        if (messages.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                            tvEmpty.text = "Нет сообщений"
                        } else {
                            messagesAdapter.setMessages(messages)
                            recyclerView.scrollToPosition(messages.size - 1)
                        }

                        Log.i(TAG, "Loaded ${messages.size} messages")
                    } else if (response.code() == 401) {
                        Log.w(TAG, "Session expired")
                        sessionManager.clearSession()
                        finish()
                    } else {
                        Log.e(TAG, "Failed to load messages: ${response.code()}")
                        tvEmpty.visibility = View.VISIBLE
                        tvEmpty.text = "Ошибка загрузки"
                    }
                }

                override fun onFailure(call: Call<ChatMessagesResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Error loading messages: ${t.message}")
                    tvEmpty.visibility = View.VISIBLE
                    tvEmpty.text = "Ошибка сети"
                }
            })
    }

    private fun sendMessage(text: String, mediaType: String? = null, mediaData: String? = null) {
        val sessionToken = sessionManager.getSessionToken()
        if (sessionToken.isNullOrBlank()) {
            Toast.makeText(this, "Сессия истекла", Toast.LENGTH_SHORT).show()
            return
        }

        // Disable send button while sending
        btnSend.isEnabled = false

        val request = SendChatMessageRequest(
            session_token = sessionToken,
            text = text,
            media_type = mediaType,
            media_data = mediaData
        )

        RetrofitClient.getApiService(this).sendChatMessage(dialogId, request)
            .enqueue(object : Callback<SendChatMessageResponse> {
                override fun onResponse(
                    call: Call<SendChatMessageResponse>,
                    response: Response<SendChatMessageResponse>
                ) {
                    btnSend.isEnabled = true

                    if (response.isSuccessful && response.body()?.success == true) {
                        inputMessage.text.clear()

                        // Add message to list
                        response.body()?.message?.let { msg ->
                            messagesAdapter.addMessage(msg)
                            recyclerView.scrollToPosition(messagesAdapter.itemCount - 1)
                        }

                        tvEmpty.visibility = View.GONE
                        Log.i(TAG, "Message sent successfully")
                    } else {
                        val error = response.body()?.error ?: "Ошибка отправки"
                        Toast.makeText(this@ChatActivity, error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Send failed: ${response.code()} - $error")
                    }
                }

                override fun onFailure(call: Call<SendChatMessageResponse>, t: Throwable) {
                    btnSend.isEnabled = true
                    Toast.makeText(this@ChatActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Send error: ${t.message}")
                }
            })
    }

    private fun normalizeText(text: String) {
        val sessionToken = sessionManager.getSessionToken()
        if (sessionToken.isNullOrBlank()) {
            Toast.makeText(this, "Сессия истекла", Toast.LENGTH_SHORT).show()
            return
        }

        // Disable normalize button while processing
        btnNormalize.isEnabled = false
        Toast.makeText(this, "Нормализация текста...", Toast.LENGTH_SHORT).show()

        val request = NormalizeDialogRequest(
            session_token = sessionToken,
            text = text
        )

        RetrofitClient.getApiService(this).normalizeDialogText(dialogId, request)
            .enqueue(object : Callback<NormalizeDialogResponse> {
                override fun onResponse(
                    call: Call<NormalizeDialogResponse>,
                    response: Response<NormalizeDialogResponse>
                ) {
                    btnNormalize.isEnabled = true

                    if (response.isSuccessful && response.body()?.success == true) {
                        val normalizedText = response.body()?.normalized_text
                        if (!normalizedText.isNullOrBlank()) {
                            inputMessage.setText(normalizedText)
                            inputMessage.setSelection(normalizedText.length)
                            Toast.makeText(this@ChatActivity, "Текст нормализован", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val error = response.body()?.error ?: "Ошибка нормализации"
                        Toast.makeText(this@ChatActivity, error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Normalize failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NormalizeDialogResponse>, t: Throwable) {
                    btnNormalize.isEnabled = true
                    Toast.makeText(this@ChatActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Normalize error: ${t.message}")
                }
            })
    }

    private fun startRecording() {
        try {
            audioFile = File(cacheDir, "voice_${System.currentTimeMillis()}.m4a")

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(this)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
            }

            isRecording = true
            recordingStartTime = System.currentTimeMillis()
            btnAudio.setBackgroundResource(R.drawable.bg_button_recording)
            recordingTimer.visibility = View.VISIBLE
            updateRecordingTimer()
            Log.i(TAG, "Recording started")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording: ${e.message}")
            Toast.makeText(this, "Ошибка записи: ${e.message}", Toast.LENGTH_SHORT).show()
            isRecording = false
        }
    }

    private fun updateRecordingTimer() {
        if (!isRecording) return
        val elapsed = (System.currentTimeMillis() - recordingStartTime) / 1000
        val minutes = elapsed / 60
        val seconds = elapsed % 60
        recordingTimer.text = String.format("%d:%02d", minutes, seconds)
        recordingHandler.postDelayed({ updateRecordingTimer() }, 1000)
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false

            // Reset UI
            recordingHandler.removeCallbacksAndMessages(null)
            recordingTimer.visibility = View.GONE
            btnAudio.setBackgroundResource(R.drawable.bg_button_voice)

            // Send audio file
            audioFile?.let { file ->
                if (file.exists() && file.length() > 0) {
                    val audioBytes = file.readBytes()
                    val base64Audio = Base64.encodeToString(audioBytes, Base64.NO_WRAP)

                    Toast.makeText(this, "Отправка голосового сообщения...", Toast.LENGTH_SHORT).show()
                    sendMessage("[Голосовое сообщение]", "voice", base64Audio)

                    // Clean up
                    file.delete()
                }
            }

            Log.i(TAG, "Recording stopped and sent")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop recording: ${e.message}")
            Toast.makeText(this, "Ошибка остановки записи", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaRecorder = null
        audioFile?.delete()
    }

    private fun setupChannelButtons() {
        // Set initial selection based on current dialog channel
        updateChannelButtonsUI()

        btnChannelTelegram.setOnClickListener { selectChannel("telegram") }
        btnChannelWhatsApp.setOnClickListener { selectChannel("whatsapp") }
        btnChannelAvito.setOnClickListener { selectChannel("avito") }
        btnChannelMax.setOnClickListener { selectChannel("max") }
    }

    private fun selectChannel(channelName: String) {
        selectedResponseChannel = channelName
        updateChannelButtonsUI()
        Toast.makeText(this, "Ответ через: ${channelName.uppercase()}", Toast.LENGTH_SHORT).show()
    }

    private fun updateChannelButtonsUI() {
        // Reset all buttons
        btnChannelTelegram.isSelected = false
        btnChannelWhatsApp.isSelected = false
        btnChannelAvito.isSelected = false
        btnChannelMax.isSelected = false

        // Highlight selected channel
        when (selectedResponseChannel.lowercase()) {
            "telegram" -> btnChannelTelegram.isSelected = true
            "whatsapp" -> btnChannelWhatsApp.isSelected = true
            "avito" -> btnChannelAvito.isSelected = true
            "max" -> btnChannelMax.isSelected = true
        }
    }

    private fun dialClientPhone() {
        val phone = clientPhone.ifEmpty {
            // Try to extract phone from client name if it looks like a phone
            if (clientName.matches(Regex("^[+0-9\\s\\-()]+$"))) clientName else ""
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Номер телефона не найден", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to dial: ${e.message}")
            Toast.makeText(this, "Не удалось позвонить", Toast.LENGTH_SHORT).show()
        }
    }
}
