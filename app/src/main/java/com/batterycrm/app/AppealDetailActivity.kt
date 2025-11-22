package com.batterycrm.app

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.batterycrm.app.adapters.MessagesAdapter
import com.batterycrm.app.api.*
import com.batterycrm.app.databinding.ActivityAppealDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppealDetailActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var binding: ActivityAppealDetailBinding
    
    private var appealId: String = ""
    
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
        
        messagesAdapter = MessagesAdapter(emptyList())
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.messagesRecyclerView.adapter = messagesAdapter
        
        setupButtons()
        loadAppealDetail()
    }
    
    private fun setupButtons() {
        binding.takeButton.setOnClickListener {
            takeAppeal()
        }
        
        binding.normalizeButton.setOnClickListener {
            normalizeText()
        }
        
        binding.sendButton.setOnClickListener {
            sendResponse()
        }
        
        binding.rejectButton.setOnClickListener {
            rejectAppeal()
        }
    }
    
    private fun loadAppealDetail() {
        val apiService = RetrofitClient.getApiService(this)
        apiService.getAppealDetail(appealId).enqueue(object : Callback<AppealDetailResponse> {
            override fun onResponse(
                call: Call<AppealDetailResponse>,
                response: Response<AppealDetailResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()!!
                    updateUI(data)
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed to load", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onFailure(call: Call<AppealDetailResponse>, t: Throwable) {
                Toast.makeText(this@AppealDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun updateUI(data: AppealDetailResponse) {
        bindHeader(data)

        val messages = data.messages ?: emptyList()
        messagesAdapter = MessagesAdapter(messages)
        binding.messagesRecyclerView.adapter = messagesAdapter
        
        val aiText = data.ai_response?.response_text ?: data.appeal.ai_response
        if (!aiText.isNullOrBlank()) {
            binding.messageInput.setText(aiText)
        }
    }

    private fun bindHeader(data: AppealDetailResponse) {
        val appeal = data.appeal
        val client = data.client

        binding.detailClientName.text = client?.name
            ?.takeIf { it.isNotBlank() }
            ?: client?.phone
            ?: "Без имени"

        binding.detailStatus.text = formatStatus(appeal.stage, appeal.appeal_status)
        binding.detailAppealType.setTextOrGone(
            appeal.appeal_type?.takeIf { it.isNotBlank() }
                ?: appeal.repair_type_name
        )
        binding.detailLastMessageDate.text = formatDateTime(data.messages?.lastOrNull()?.created_at)
        binding.detailDevice.setTextOrGone(
            listOfNotNull(data.device?.brand, data.device?.model)
                .filter { !it.isNullOrBlank() }
                .joinToString(" ")
                .ifBlank { null }
        )
        val issueType = appeal.issue_type_name?.takeIf { it.isNotBlank() }
            ?: appeal.repair_type_name
        binding.detailIssueTypeLabel.visibility = if (issueType.isNullOrBlank()) View.GONE else View.VISIBLE
        binding.detailIssueTypeValue.setTextOrGone(
            appeal.issue_type_name?.takeIf { it.isNotBlank() }
                ?: appeal.repair_type_name
        )
        binding.detailIssueDescriptionValue.setTextOrGone(
            appeal.issue_name?.takeIf { it.isNotBlank() }
                ?: appeal.problem_description
        )

        applyChannelBadge(appeal.channel ?: appeal.channel_name)

        bindMetaRow(binding.detailStageRow, binding.detailStageValue, null)
        val partsOwnerValue = appeal.parts_owner
            ?.takeIf { it.equals("клиента", ignoreCase = true) || it.equals("client", ignoreCase = true) }
        bindMetaRow(binding.detailPartsOwnerRow, binding.detailPartsOwnerValue, partsOwnerValue)
        bindMetaRow(binding.detailCreatedRow, binding.detailCreatedValue, formatDate(appeal.created_at ?: appeal.updated_at))
        bindMetaRow(binding.detailClientRow, binding.detailClientValue, null)
    }

    private fun formatStatus(stage: String?, status: String?): String {
        val normalized = (stage ?: status)?.lowercase()?.trim()
        return when (normalized) {
            null, "", "null" -> "Без статуса"
            "new", "первичный контакт" -> "Первичный контакт"
            "in_progress", "в работе" -> "В работе"
            "completed", "закрыто" -> "Завершено"
            else -> stage ?: status ?: "Без статуса"
        }
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
        
        val request = SendMessageRequest(operatorId, text)
        RetrofitClient.getApiService(this).sendResponse(appealId, request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                binding.sendButton.isEnabled = true
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AppealDetailActivity, "Sent", Toast.LENGTH_SHORT).show()
                    binding.messageInput.setText("")
                    loadAppealDetail()
                } else {
                    Toast.makeText(this@AppealDetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.sendButton.isEnabled = true
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
                    loadAppealDetail()
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
