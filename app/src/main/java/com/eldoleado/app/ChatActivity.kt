package com.eldoleado.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.eldoleado.app.api.ChatMessageDto
import com.eldoleado.app.api.ChatMessagesResponse
import com.eldoleado.app.api.RetrofitClient
import com.eldoleado.app.api.SendChatMessageRequest
import com.eldoleado.app.api.SendChatMessageResponse
import com.eldoleado.app.adapters.ChatMessagesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ChatActivity"
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
    private lateinit var normalizeButton: ImageButton
    private lateinit var voiceButton: ImageButton
    private lateinit var clearButton: ImageButton
    private lateinit var rejectButton: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView

    private var dialogId: String = ""
    private var channel: String = ""
    private var clientName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sessionManager = SessionManager(this)

        dialogId = intent.getStringExtra("dialog_id") ?: ""
        channel = intent.getStringExtra("channel") ?: ""
        clientName = intent.getStringExtra("client_name") ?: "Клиент"

        if (dialogId.isEmpty()) {
            Log.e(TAG, "No dialog_id provided")
            finish()
            return
        }

        initViews()
        setupAdapter()
        loadMessages()
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
        normalizeButton = findViewById(R.id.normalizeButton)
        voiceButton = findViewById(R.id.voiceButton)
        clearButton = findViewById(R.id.clearButton)
        rejectButton = findViewById(R.id.rejectButton)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        tvClientName.text = clientName
        tvChannel.text = channel.uppercase()

        ivBack.setOnClickListener { finish() }

        btnSend.setOnClickListener {
            val text = inputMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(text)
            }
        }

        // Normalize button - пока заглушка
        normalizeButton.setOnClickListener {
            Toast.makeText(this, "Нормализация в разработке", Toast.LENGTH_SHORT).show()
        }

        // Voice button - пока заглушка
        voiceButton.setOnClickListener {
            Toast.makeText(this, "Голосовые сообщения в разработке", Toast.LENGTH_SHORT).show()
        }

        // Clear button - очистить поле ввода
        clearButton.setOnClickListener {
            inputMessage.text.clear()
        }

        // Reject button - пока заглушка
        rejectButton.setOnClickListener {
            Toast.makeText(this, "Отклонение в разработке", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dialogs -> {
                    // Вернуться к списку диалогов
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    // Открыть настройки
                    Toast.makeText(this, "Настройки в разработке", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
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

    private fun sendMessage(text: String) {
        val sessionToken = sessionManager.getSessionToken()
        if (sessionToken.isNullOrBlank()) {
            Toast.makeText(this, "Сессия истекла", Toast.LENGTH_SHORT).show()
            return
        }

        // Disable send button while sending
        btnSend.isEnabled = false
        inputMessage.isEnabled = false

        val request = SendChatMessageRequest(text = text)

        RetrofitClient.getApiService(this).sendChatMessage(dialogId, sessionToken, request)
            .enqueue(object : Callback<SendChatMessageResponse> {
                override fun onResponse(
                    call: Call<SendChatMessageResponse>,
                    response: Response<SendChatMessageResponse>
                ) {
                    btnSend.isEnabled = true
                    inputMessage.isEnabled = true

                    if (response.isSuccessful && response.body()?.success == true) {
                        inputMessage.text.clear()

                        // Add message to adapter
                        response.body()?.message?.let { msg ->
                            messagesAdapter.addMessage(msg)
                            recyclerView.scrollToPosition(messagesAdapter.itemCount - 1)
                        }

                        Log.i(TAG, "Message sent successfully")
                    } else if (response.code() == 401) {
                        Toast.makeText(this@ChatActivity, "Сессия истекла", Toast.LENGTH_SHORT).show()
                        sessionManager.clearSession()
                        finish()
                    } else {
                        val error = response.body()?.error ?: "Ошибка отправки"
                        Toast.makeText(this@ChatActivity, error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Send failed: ${response.code()} - $error")
                    }
                }

                override fun onFailure(call: Call<SendChatMessageResponse>, t: Throwable) {
                    btnSend.isEnabled = true
                    inputMessage.isEnabled = true
                    Toast.makeText(this@ChatActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Send error: ${t.message}")
                }
            })
    }
}
