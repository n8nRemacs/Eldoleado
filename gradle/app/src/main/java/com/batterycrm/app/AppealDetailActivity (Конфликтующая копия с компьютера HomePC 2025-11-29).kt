package com.batterycrm.app

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.batterycrm.app.adapters.MessagesAdapter
import com.batterycrm.app.api.*
import com.batterycrm.app.databinding.ActivityAppealDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

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
        renderMetaSection(data)

        val messages = data.messages ?: emptyList()
        messagesAdapter = MessagesAdapter(messages)
        binding.messagesRecyclerView.adapter = messagesAdapter
        
        val aiText = data.ai_response?.response_text ?: data.appeal.ai_response
        if (!aiText.isNullOrBlank()) {
            binding.messageInput.setText(aiText)
        }
    }

    private fun renderMetaSection(data: AppealDetailResponse) {
        val items = buildMetaItems(data)
        val container = binding.metaContainer
        container.removeAllViews()

        if (items.isEmpty()) {
            binding.infoTextView.text = getString(R.string.appeal_meta_empty)
            return
        }

        binding.infoTextView.text = getString(R.string.appeal_meta_title)
        items.forEach { item ->
            container.addView(createMetaRow(item.label, item.value))
        }
    }

    private fun buildMetaItems(data: AppealDetailResponse): List<DisplayMetaItem> {
        val result = mutableListOf<DisplayMetaItem>()

        data.appeal.meta
            ?.filter { !it.value.isNullOrBlank() }
            ?.forEach { field ->
                val safeValue = field.value!!.trim()
                val safeLabel = field.label.ifBlank { field.id }
                result.add(
                    DisplayMetaItem(
                        id = field.id,
                        label = safeLabel,
                        value = safeValue,
                        order = field.order ?: Double.MAX_VALUE
                    )
                )
            }

        data.client?.let { client ->
            val valueParts = listOfNotNull(
                client.name?.takeIf { it.isNotBlank() },
                client.phone?.takeIf { it.isNotBlank() }
            )
            if (valueParts.isNotEmpty()) {
                result.add(
                    DisplayMetaItem(
                        id = "client_summary",
                        label = getString(R.string.appeal_client_label),
                        value = valueParts.joinToString("\n"),
                        order = 900.0
                    )
                )
            }
        }

        data.device?.let { device ->
            val deviceText = listOfNotNull(device.brand, device.model)
                .filter { it.isNotBlank() }
                .joinToString(" ")

            if (deviceText.isNotBlank()) {
                result.add(
                    DisplayMetaItem(
                        id = "device_summary",
                        label = getString(R.string.appeal_device_label),
                        value = deviceText,
                        order = 910.0
                    )
                )
            }
        }

        return result.sortedBy { it.order }
    }

    private fun createMetaRow(label: String, value: String): LinearLayout {
        val context = this
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            background = ContextCompat.getDrawable(context, R.drawable.bg_meta_item)
        }

        val labelView = TextView(context).apply {
            text = label
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        val valueView = TextView(context).apply {
            text = value
            setPadding(0, 4.dpToPx(), 0, 0)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        row.addView(labelView)
        row.addView(valueView)

        return row
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).roundToInt()

    private data class DisplayMetaItem(
        val id: String,
        val label: String,
        val value: String,
        val order: Double
    )
    
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
