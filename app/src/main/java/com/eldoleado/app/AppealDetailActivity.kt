package com.eldoleado.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.eldoleado.app.adapters.MessagesAdapter
import com.eldoleado.app.api.*
import com.eldoleado.app.data.database.entities.AppealEntity
import com.eldoleado.app.data.database.entities.MessageEntity
import com.eldoleado.app.databinding.ActivityAppealDetailBinding
import com.eldoleado.app.ui.AppealInfoBottomSheet
import com.eldoleado.app.viewmodel.AppealDetailUiState
import com.eldoleado.app.viewmodel.AppealDetailViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AppealDetailActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var binding: ActivityAppealDetailBinding

    private var appealId: String = ""
    private var currentAppeal: AppealEntity? = null

    // Voice recording
    private var speechRecognizer: SpeechRecognizer? = null
    private var isRecording = false
    private var isTapMode = false  // true = tap-to-toggle mode, false = hold mode
    private var recordingStartTime = 0L
    private var pressStartTime = 0L
    private val timerHandler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var longPressRunnable: Runnable? = null
    private var originalInputHeight = 0
    private var messageSentRecently = false
    private var userClearedInput = false  // Track if user manually cleared the input
    private var lastShownAiResponse: String? = null  // Track which aiResponse was shown
    private var partialTranscript = StringBuilder()
    private var lastMessageCount = 0

    // Режим работы: true = AUTO, false = ASSIST
    private var isAutoMode = true

    companion object {
        private const val LONG_PRESS_THRESHOLD = 3000L // 3 seconds for hold mode
        private const val REQUEST_RECORD_AUDIO = 1001
        private const val AUTO_REFRESH_INTERVAL = 10_000L // 10 seconds
    }

    // Auto-refresh для обновления сообщений
    private var autoRefreshRunnable: Runnable? = null
    private var isAutoRefreshEnabled = true

    private val viewModel: AppealDetailViewModel by viewModels {
        AppealDetailViewModel.Factory(
            EldoleadoApplication.instance.repository,
            appealId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appealId = intent.getStringExtra("appeal_id") ?: ""
        if (appealId.isEmpty()) {
            finish()
            return
        }

        sessionManager = SessionManager(this)

        messagesAdapter = MessagesAdapter()
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.messagesRecyclerView.adapter = messagesAdapter

        setupButtons()
        setupObservers()
        setupBottomNavigation()
        setupAutoRefresh()
        setupModeSwitch()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_appeals -> {
                    // Возврат к списку заявок
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    // Переход к настройкам через MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("open_settings", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupAutoRefresh() {
        autoRefreshRunnable = object : Runnable {
            override fun run() {
                if (isAutoRefreshEnabled) {
                    viewModel.forceRefresh()
                    timerHandler.postDelayed(this, AUTO_REFRESH_INTERVAL)
                }
            }
        }
    }

    private fun startAutoRefresh() {
        isAutoRefreshEnabled = true
        autoRefreshRunnable?.let {
            timerHandler.removeCallbacks(it)
            timerHandler.postDelayed(it, AUTO_REFRESH_INTERVAL)
        }
    }

    private fun stopAutoRefresh() {
        isAutoRefreshEnabled = false
        autoRefreshRunnable?.let { timerHandler.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
        startAutoRefresh()
    }

    override fun onPause() {
        super.onPause()
        stopAutoRefresh()
    }

    private fun setupModeSwitch() {
        // Switch: ON = AUTO (автоматический), OFF = ASSIST (ручной)
        binding.modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            isAutoMode = isChecked
            updateModeUI()
            saveAppealMode()
        }

        // Блокируем перехват событий родительским элементом для переключателя
        binding.modeSwitch.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false // Возвращаем false чтобы switch обработал событие сам
        }
    }

    private fun updateModeUI() {
        if (isAutoMode) {
            binding.modeLabelText.text = "АВТО"
            binding.modeLabelText.setTextColor(ContextCompat.getColor(this, R.color.primary_blue))
            binding.modeSwitch.isChecked = true
        } else {
            binding.modeLabelText.text = "РУЧН"
            binding.modeLabelText.setTextColor(ContextCompat.getColor(this, R.color.status_progress_text))
            binding.modeSwitch.isChecked = false
        }
    }

    private fun setModeFromAppeal(operationMode: String?) {
        isAutoMode = operationMode?.lowercase() == "auto"
        // Обновляем UI без триггера listener
        binding.modeSwitch.setOnCheckedChangeListener(null)
        updateModeUI()
        binding.modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            isAutoMode = isChecked
            updateModeUI()
            saveAppealMode()
        }
    }

    private fun saveAppealMode() {
        val operatorId = sessionManager.getOperatorId() ?: return
        val mode = if (isAutoMode) "auto" else "assist"

        val request = UpdateAppealModeRequest(operatorId, appealId, mode)
        RetrofitClient.getApiService(this).updateAppealMode(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (!response.isSuccessful || response.body()?.success != true) {
                    // Молча логируем ошибку, не показываем пользователю
                    android.util.Log.w("AppealDetail", "Failed to save appeal mode")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                android.util.Log.w("AppealDetail", "Network error saving appeal mode", t)
            }
        })
    }

    private fun switchToAssistMode() {
        if (isAutoMode) {
            isAutoMode = false
            updateModeUI()
            saveAppealMode()
            Toast.makeText(this, "Режим переключён на ручной", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupButtons() {
        binding.takeButton.setOnClickListener {
            clearInputFocus()
            takeAppeal()
        }

        binding.normalizeButton.setOnClickListener {
            clearInputFocus()
            normalizeText()
        }

        binding.sendButton.setOnClickListener {
            clearInputFocus()
            sendResponse()
        }

        binding.clearButton.setOnClickListener {
            clearInputFocus()
            clearInputText()
        }

        binding.rejectButton.setOnClickListener {
            clearInputFocus()
            showRejectConfirmation()
        }

        setupVoiceButton()
        setupInputFieldExpansion()

        // Клик на инфоблок открывает BottomSheet с деталями
        binding.detailHeaderCard.setOnClickListener {
            clearInputFocus()
            showAppealInfoBottomSheet()
        }

        // Tap on messages list clears input focus
        binding.messagesRecyclerView.setOnTouchListener { _, _ ->
            clearInputFocus()
            false
        }
    }

    private fun showAppealInfoBottomSheet() {
        currentAppeal?.let { appeal ->
            AppealInfoBottomSheet.newInstance(appeal)
                .show(supportFragmentManager, "appeal_info")
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Наблюдаем за состоянием
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is AppealDetailUiState.Loading -> {
                                showLoading(true)
                            }
                            is AppealDetailUiState.Success -> {
                                showLoading(false)
                                updateUIFromCache(state.appeal, state.messages)
                                state.refreshError?.let { error ->
                                    Toast.makeText(this@AppealDetailActivity, error, Toast.LENGTH_SHORT).show()
                                    viewModel.clearRefreshError()
                                }
                            }
                            is AppealDetailUiState.Error -> {
                                showLoading(false)
                                Toast.makeText(this@AppealDetailActivity, state.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                // Наблюдаем за индикатором обновления
                launch {
                    viewModel.isRefreshing.collect { isRefreshing ->
                        // Можно добавить SwipeRefreshLayout если нужен pull-to-refresh
                    }
                }

                // Подписка на FCM события для realtime обновления
                launch {
                    AppealEventBus.events.collect { event ->
                        handleAppealEvent(event)
                    }
                }
            }
        }
    }

    private fun handleAppealEvent(event: AppealUpdateEvent) {
        when (event) {
            is AppealUpdateEvent.NewMessage -> {
                if (event.appealId == appealId) {
                    // Новое сообщение в текущем диалоге - обновляем
                    viewModel.forceRefresh()
                }
            }
            is AppealUpdateEvent.DraftReady -> {
                if (event.appealId == appealId && !isAutoMode) {
                    // Черновик готов в режиме ASSIST - обновляем и вставляем в поле
                    viewModel.forceRefresh()
                    // Черновик будет вставлен в updateUIFromCache через aiResponse
                }
            }
            is AppealUpdateEvent.AppealUpdated -> {
                if (event.appealId == appealId) {
                    viewModel.forceRefresh()
                }
            }
            is AppealUpdateEvent.AllAppealsUpdated -> {
                viewModel.forceRefresh()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        // Можно добавить прогресс-индикатор если нужно
    }

    private fun updateUIFromCache(appeal: AppealEntity, messages: List<MessageEntity>) {
        currentAppeal = appeal
        bindHeaderFromEntity(appeal)

        // Устанавливаем режим из данных appeal
        setModeFromAppeal(appeal.operationMode)

        // Filter out agent messages, sort by createdAt
        val filteredMessages = messages
            .filter { it.senderType.lowercase() != "agent" }
            .sortedBy { it.createdAt }

        messagesAdapter.updateMessagesFromEntities(filteredMessages)

        // Scroll to the last message (bottom)
        if (filteredMessages.isNotEmpty()) {
            binding.messagesRecyclerView.scrollToPosition(filteredMessages.size - 1)
        }

        // Show AI response if available and input is empty
        // Don't show if: just sent, user cleared input, last message from operator, or same aiResponse already shown
        val lastMessage = filteredMessages.lastOrNull()
        val lastMessageIsFromOperator = lastMessage?.senderType?.lowercase() == "operator"
        val lastMessageIsFromClient = lastMessage?.senderType?.lowercase() == "client"
        val isNewAiResponse = appeal.aiResponse != lastShownAiResponse

        // Reset userClearedInput flag when new client message arrives
        if (lastMessageIsFromClient && isNewAiResponse) {
            userClearedInput = false
        }

        if (!messageSentRecently && !userClearedInput && !lastMessageIsFromOperator &&
            binding.messageInput.text.isNullOrBlank() && !appeal.aiResponse.isNullOrBlank() && isNewAiResponse) {
            binding.messageInput.setText(appeal.aiResponse)
            lastShownAiResponse = appeal.aiResponse
        }
        messageSentRecently = false
    }

    private fun bindHeaderFromEntity(appeal: AppealEntity) {
        binding.detailClientName.text = appeal.clientName
            ?.takeIf { it.isNotBlank() }
            ?: appeal.clientPhone
            ?: "Без имени"

        applyStatusBadge(appeal.stage, appeal.status)
        binding.detailAppealType.setTextOrGone(translateAppealType(appeal.appealType))
        binding.detailLastMessageDate.visibility = View.GONE

        binding.detailDevice.setTextOrGone(
            listOfNotNull(appeal.deviceBrand, appeal.deviceModel)
                .filter { it.isNotBlank() }
                .joinToString(" ")
                .ifBlank { null }
        )

        // Combine repair_type_name + deal_type_name
        val repairInfo = listOfNotNull(
            appeal.repairTypeName?.takeIf { it.isNotBlank() },
            appeal.dealTypeName?.takeIf { it.isNotBlank() }
        ).joinToString("\t\t").ifBlank { null }
        binding.detailRepairInfo.setTextOrGone(repairInfo)

        applyChannelBadge(appeal.channel ?: appeal.channelName)

        bindMetaRow(binding.detailStageRow, binding.detailStageValue, null)
        val partsOwnerValue = appeal.partsOwner?.let { translatePartsOwner(it) }
        bindMetaRow(binding.detailPartsOwnerRow, binding.detailPartsOwnerValue, partsOwnerValue)
        bindMetaRow(binding.detailCreatedRow, binding.detailCreatedValue, null)
        bindMetaRow(binding.detailClientRow, binding.detailClientValue, null)
    }

    private fun clearInputText() {
        binding.messageInput.setText("")
        userClearedInput = true  // Prevent auto-fill until new client message
    }

    private fun clearInputFocus() {
        binding.messageInput.clearFocus()
        // Hide keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(binding.messageInput.windowToken, 0)
    }

    private fun showRejectConfirmation() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Отказ от заявки")
            .setMessage("Вы отказываетесь от принятия заявки?\nНапример, это спам.")
            .setPositiveButton("Да, отказаться") { _, _ ->
                rejectAppeal()
            }
            .setNegativeButton("Нет", null)
            .show()
    }

    private fun setupVoiceButton() {
        binding.voiceButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!checkAudioPermission()) {
                        requestAudioPermission()
                        return@setOnTouchListener true
                    }

                    pressStartTime = System.currentTimeMillis()

                    // If already recording in tap mode, stop on tap
                    if (isRecording && isTapMode) {
                        // Will be handled in ACTION_UP as short tap
                    } else if (!isRecording) {
                        // Schedule long press detection
                        longPressRunnable = Runnable {
                            if (!isRecording) {
                                // Long press detected (>3 sec held) - start hold mode
                                isTapMode = false
                                startRecording()
                            }
                        }
                        timerHandler.postDelayed(longPressRunnable!!, LONG_PRESS_THRESHOLD)
                    }
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val pressDuration = System.currentTimeMillis() - pressStartTime

                    // Cancel long press detection
                    longPressRunnable?.let { timerHandler.removeCallbacks(it) }
                    longPressRunnable = null

                    if (isRecording && !isTapMode) {
                        // Hold mode release - stop recording
                        stopRecording()
                    } else if (pressDuration < LONG_PRESS_THRESHOLD) {
                        // Short tap
                        if (isRecording && isTapMode) {
                            // Stop recording in tap mode
                            stopRecording()
                        } else if (!isRecording) {
                            // Start recording in tap mode
                            isTapMode = true
                            startRecording()
                        }
                    }

                    pressStartTime = 0L
                    true
                }
                else -> false
            }
        }
    }

    private fun setupInputFieldExpansion() {
        // Save original height
        binding.messageInput.post {
            originalInputHeight = binding.messageInput.height
        }

        binding.messageInput.setOnFocusChangeListener { _, hasFocus ->
            val params = binding.messageInput.layoutParams
            if (hasFocus) {
                // Expand to 20% of screen height (reduced from 40% to leave room for keyboard)
                val displayMetrics = resources.displayMetrics
                val expandedHeight = (displayMetrics.heightPixels * 0.2).toInt()
                params.height = expandedHeight
            } else {
                // Restore original height
                params.height = if (originalInputHeight > 0) originalInputHeight else 80.dpToPx()
            }
            binding.messageInput.layoutParams = params
        }
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private fun checkAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestAudioPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_AUDIO
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение получено. Нажмите снова.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Разрешение на запись отклонено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecording() {
        if (isRecording) return

        // Clear input field and partial transcript
        binding.messageInput.setText("")
        partialTranscript.clear()

        isRecording = true
        recordingStartTime = System.currentTimeMillis()

        // Show timer on button
        binding.recordingTimer.visibility = View.VISIBLE
        binding.recordingTimer.text = "0"

        // Change voice button to stop button (red) and hide mic icon
        binding.voiceButton.setBackgroundResource(R.drawable.bg_button_recording)
        binding.voiceButton.setImageDrawable(null)  // Hide icon, show timer instead

        // Start timer update
        startTimerUpdate()

        // Initialize and start speech recognizer
        initializeSpeechRecognizer()
    }

    private fun stopRecording() {
        if (!isRecording) return

        isRecording = false

        // Stop timer
        stopTimerUpdate()

        // Hide timer
        binding.recordingTimer.visibility = View.GONE

        // Restore voice button appearance
        binding.voiceButton.setBackgroundResource(R.drawable.bg_button_voice)
        binding.voiceButton.setImageResource(R.drawable.ic_mic)

        // Stop speech recognizer - will trigger onResults
        speechRecognizer?.stopListening()
    }

    private fun startTimerUpdate() {
        timerRunnable = object : Runnable {
            override fun run() {
                if (isRecording) {
                    val elapsed = System.currentTimeMillis() - recordingStartTime
                    val seconds = (elapsed / 1000).toInt()
                    binding.recordingTimer.text = seconds.toString()
                    timerHandler.postDelayed(this, 500)
                }
            }
        }
        timerHandler.post(timerRunnable!!)
    }

    private fun stopTimerUpdate() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
        timerRunnable = null
    }

    private fun initializeSpeechRecognizer() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Распознавание речи недоступно", Toast.LENGTH_SHORT).show()
            stopRecording()
            return
        }

        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                // Speech ended - if still in recording mode, restart recognizer to continue listening
                if (isRecording) {
                    // Small delay before restarting
                    timerHandler.postDelayed({
                        if (isRecording) {
                            restartSpeechRecognizer()
                        }
                    }, 100)
                }
            }

            override fun onError(error: Int) {
                runOnUiThread {
                    when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> {
                            // No speech detected - restart if still recording
                            if (isRecording) {
                                restartSpeechRecognizer()
                            }
                        }
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                            // Timeout - restart if still recording
                            if (isRecording) {
                                restartSpeechRecognizer()
                            }
                        }
                        SpeechRecognizer.ERROR_CLIENT -> {
                            // Ignore client errors during restart
                        }
                        else -> {
                            val errorMessage = when (error) {
                                SpeechRecognizer.ERROR_AUDIO -> "Ошибка аудио"
                                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Нет разрешений"
                                SpeechRecognizer.ERROR_NETWORK -> "Ошибка сети"
                                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Таймаут сети"
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Распознаватель занят"
                                SpeechRecognizer.ERROR_SERVER -> "Ошибка сервера"
                                else -> "Ошибка: $error"
                            }
                            Toast.makeText(this@AppealDetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            finishRecording()
                        }
                    }
                }
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val text = matches[0]
                    if (text.isNotBlank()) {
                        // Append to accumulated transcript
                        if (partialTranscript.isNotEmpty()) {
                            partialTranscript.append(" ")
                        }
                        partialTranscript.append(text)

                        runOnUiThread {
                            binding.messageInput.setText(partialTranscript.toString())
                            binding.messageInput.setSelection(partialTranscript.length)
                        }
                    }
                }

                // Restart recognizer if still recording
                if (isRecording) {
                    restartSpeechRecognizer()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty() && matches[0].isNotBlank()) {
                    runOnUiThread {
                        // Show accumulated text + current partial
                        val currentPartial = matches[0]
                        val fullText = if (partialTranscript.isNotEmpty()) {
                            "${partialTranscript} $currentPartial"
                        } else {
                            currentPartial
                        }
                        binding.messageInput.setText(fullText)
                    }
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        startSpeechRecognizerListening()
    }

    private fun startSpeechRecognizerListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            // Don't show any UI
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            // Longer silence detection
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000L)
        }
        speechRecognizer?.startListening(intent)
    }

    private fun restartSpeechRecognizer() {
        if (!isRecording) return
        speechRecognizer?.cancel()
        timerHandler.postDelayed({
            if (isRecording) {
                startSpeechRecognizerListening()
            }
        }, 200)
    }

    private fun finishRecording() {
        if (!isRecording) return

        isRecording = false
        stopTimerUpdate()

        binding.recordingTimer.visibility = View.GONE
        binding.voiceButton.setBackgroundResource(R.drawable.bg_button_voice)
        binding.voiceButton.setImageResource(R.drawable.ic_mic)

        speechRecognizer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoRefresh()
        speechRecognizer?.destroy()
        speechRecognizer = null
        timerHandler.removeCallbacksAndMessages(null)
        longPressRunnable = null
        timerRunnable = null
        autoRefreshRunnable = null
    }

    private fun applyStatusBadge(stage: String?, status: String?) {
        val normalized = (stage ?: status)?.lowercase()?.trim()
        val (text, bgRes, textColorRes) = when {
            normalized.isNullOrBlank() || normalized == "null" ->
                Triple("Без статуса", R.drawable.bg_chip_neutral, android.R.color.black)
            normalized == "new" || normalized == "первичный контакт" ->
                Triple("Первичный контакт", R.drawable.bg_status_new, R.color.status_new_text)
            normalized == "in_progress" || normalized == "в работе" ->
                Triple("В работе", R.drawable.bg_status_in_progress, R.color.status_progress_text)
            normalized == "completed" || normalized == "закрыто" ->
                Triple("Завершено", R.drawable.bg_status_completed, R.color.status_completed_text)
            normalized.contains("информация") || normalized.contains("собран") ->
                Triple("Информация собрана", R.drawable.bg_status_info_collected, R.color.status_info_text)
            else -> Triple(stage ?: status ?: "Без статуса", R.drawable.bg_chip_neutral, android.R.color.black)
        }
        binding.detailStatus.text = text
        binding.detailStatus.setBackgroundResource(bgRes)
        binding.detailStatus.setTextColor(ContextCompat.getColor(this, textColorRes))
    }

    private fun applyChannelBadge(channelRaw: String?) {
        val (text, colorRes) = resolveChannelBadge(channelRaw)
        binding.detailChannelBadge.text = text

        val background = AppCompatResources.getDrawable(this, R.drawable.bg_channel_badge)?.mutate()
        val tintColor = ContextCompat.getColor(this, colorRes)
        if (background != null) {
            DrawableCompat.setTint(background, tintColor)
            ViewCompat.setBackground(binding.detailChannelBadge, background)
        }
    }

    private fun resolveChannelBadge(channelRaw: String?): Pair<String, Int> {
        val normalized = channelRaw?.lowercase()?.trim()
        return when {
            normalized.isNullOrBlank() -> "" to R.color.channel_generic
            normalized.contains("whatsapp") || normalized == "wa" ->
                "WA" to R.color.channel_whatsapp
            normalized.contains("telegram") || normalized == "tg" ->
                "TG" to R.color.channel_telegram
            normalized.contains("avito") ->
                "AV" to R.color.channel_avito
            else -> normalized.take(2)
                .uppercase()
                .takeIf { it.isNotBlank() }
                ?.let { it to R.color.channel_generic }
                ?: ("" to R.color.channel_generic)
        }
    }

    private fun bindMetaRow(row: View, valueView: TextView, value: String?) {
        if (value.isNullOrBlank()) {
            row.visibility = View.GONE
        } else {
            valueView.text = value
            row.visibility = View.VISIBLE
        }
    }

    private fun TextView.setTextOrGone(value: String?) {
        if (value.isNullOrBlank()) {
            visibility = View.GONE
        } else {
            text = value
            visibility = View.VISIBLE
        }
    }

    private fun formatDate(source: String?): String? {
        if (source.isNullOrBlank()) return null
        return try {
            val instant = java.time.Instant.parse(source)
            java.time.ZonedDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
                .format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } catch (ex: Exception) {
            source.take(10)
                .takeIf { it.isNotBlank() }
                ?.replace("-", ".")
        }
    }

    private fun formatDateTime(source: String?): String? {
        if (source.isNullOrBlank()) return null
        return try {
            val instant = java.time.Instant.parse(source)
            java.time.ZonedDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
                .format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
        } catch (_: Exception) {
            source
        }
    }

    private fun translateAppealType(type: String?): String? {
        val normalized = type?.lowercase()?.trim()
        return when (normalized) {
            null, "", "null" -> null
            "repair", "ремонт" -> "Ремонт"
            "consultation", "консультация" -> "Консультация"
            "greeting", "приветствие" -> "Приветствие"
            "spam", "спам" -> "Спам"
            "other", "другое" -> "Другое"
            else -> type?.replaceFirstChar { it.uppercase() }
        }
    }

    private fun translatePartsOwner(value: String?): String? {
        val normalized = value?.lowercase()?.trim()
        return when (normalized) {
            null, "", "null" -> null
            "workshop", "мастерская", "наша" -> null // Hide when workshop's parts
            "client", "клиента", "клиент" -> "Клиента"
            else -> null // Only show explicitly if client's parts
        }
    }
    
    private fun takeAppeal() {
        val operatorId = sessionManager.getOperatorId() ?: return
        binding.takeButton.isEnabled = false
        
        val request = TakeAppealRequest(operatorId)
        RetrofitClient.getApiService(this).takeAppeal(appealId, request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                binding.takeButton.isEnabled = true
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AppealDetailActivity, "Taken", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.takeButton.isEnabled = true
                Toast.makeText(this@AppealDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun normalizeText() {
        val operatorId = sessionManager.getOperatorId() ?: return
        val text = binding.messageInput.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            return
        }
        
        binding.normalizeButton.isEnabled = false
        
        val request = NormalizeRequest(operatorId, text)
        RetrofitClient.getApiService(this).normalizeText(appealId, request).enqueue(object : Callback<NormalizeResponse> {
            override fun onResponse(call: Call<NormalizeResponse>, response: Response<NormalizeResponse>) {
                binding.normalizeButton.isEnabled = true
                if (response.isSuccessful && response.body()?.success == true) {
                    binding.messageInput.setText(response.body()!!.normalized_text)
                    Toast.makeText(this@AppealDetailActivity, "Normalized", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onFailure(call: Call<NormalizeResponse>, t: Throwable) {
                binding.normalizeButton.isEnabled = true
                Toast.makeText(this@AppealDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun sendResponse() {
        val operatorId = sessionManager.getOperatorId() ?: return
        val text = binding.messageInput.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            return
        }

        binding.sendButton.isEnabled = false

        // Если был в режиме AUTO и оператор отправляет сообщение вручную — переключаем на ASSIST
        if (isAutoMode) {
            switchToAssistMode()
        }

        val request = SendMessageRequest(operatorId, text)
        RetrofitClient.getApiService(this).sendResponse(appealId, request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                binding.sendButton.isEnabled = true
                // Accept HTTP 200 as success even if body is empty or success field is missing
                val body = response.body()
                if (response.isSuccessful && (body == null || body.success != false)) {
                    Toast.makeText(this@AppealDetailActivity, "Sent", Toast.LENGTH_SHORT).show()
                    binding.messageInput.setText("")
                    messageSentRecently = true
                    viewModel.invalidateAndReload()
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.sendButton.isEnabled = true
                android.util.Log.e("AppealDetail", "Send failed", t)
                Toast.makeText(this@AppealDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun rejectAppeal() {
        val operatorId = sessionManager.getOperatorId() ?: return
        binding.rejectButton.isEnabled = false
        
        val request = RejectRequest(operatorId)
        RetrofitClient.getApiService(this).rejectAiResponse(appealId, request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                binding.rejectButton.isEnabled = true
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AppealDetailActivity, "Rejected", Toast.LENGTH_SHORT).show()
                    binding.messageInput.setText("")
                    messageSentRecently = true
                    viewModel.invalidateAndReload()
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.rejectButton.isEnabled = true
                Toast.makeText(this@AppealDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
