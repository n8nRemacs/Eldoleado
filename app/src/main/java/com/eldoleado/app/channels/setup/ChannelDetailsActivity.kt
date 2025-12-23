package com.eldoleado.app.channels.setup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.eldoleado.app.R
import com.eldoleado.app.channels.ChannelCredentialsManager
import com.eldoleado.app.channels.ChannelStatus
import com.eldoleado.app.channels.ChannelType

/**
 * Shows details of a configured channel.
 * Allows reconnecting or disconnecting the channel.
 */
class ChannelDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CHANNEL_TYPE = "channel_type"

        fun start(context: Context, channelType: ChannelType) {
            val intent = Intent(context, ChannelDetailsActivity::class.java).apply {
                putExtra(EXTRA_CHANNEL_TYPE, channelType.name)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var channelCredentialsManager: ChannelCredentialsManager
    private lateinit var channelType: ChannelType

    // Views
    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var channelIcon: ImageView
    private lateinit var channelName: TextView
    private lateinit var statusIndicator: View
    private lateinit var statusText: TextView
    private lateinit var accountInfoSection: LinearLayout
    private lateinit var accountPrimaryInfo: TextView
    private lateinit var accountSecondaryInfo: TextView
    private lateinit var lastCheckTime: TextView
    private lateinit var btnReconnect: Button
    private lateinit var btnDisconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_details)

        channelCredentialsManager = ChannelCredentialsManager(this)

        // Get channel type from intent
        val channelTypeName = intent.getStringExtra(EXTRA_CHANNEL_TYPE)
        channelType = try {
            ChannelType.valueOf(channelTypeName ?: "")
        } catch (e: Exception) {
            finish()
            return
        }

        initViews()
        setupListeners()
        updateUI()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        headerTitle = findViewById(R.id.headerTitle)
        channelIcon = findViewById(R.id.channelIcon)
        channelName = findViewById(R.id.channelName)
        statusIndicator = findViewById(R.id.statusIndicator)
        statusText = findViewById(R.id.statusText)
        accountInfoSection = findViewById(R.id.accountInfoSection)
        accountPrimaryInfo = findViewById(R.id.accountPrimaryInfo)
        accountSecondaryInfo = findViewById(R.id.accountSecondaryInfo)
        lastCheckTime = findViewById(R.id.lastCheckTime)
        btnReconnect = findViewById(R.id.btnReconnect)
        btnDisconnect = findViewById(R.id.btnDisconnect)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnReconnect.setOnClickListener {
            reconnectChannel()
        }

        btnDisconnect.setOnClickListener {
            showDisconnectConfirmation()
        }
    }

    private fun updateUI() {
        // Set channel info
        headerTitle.text = channelType.displayName
        channelName.text = channelType.displayName

        // Set channel icon
        val iconRes = when (channelType) {
            ChannelType.WHATSAPP -> R.drawable.ic_whatsapp
            ChannelType.TELEGRAM -> R.drawable.ic_telegram
            ChannelType.TELEGRAM_BOT -> R.drawable.ic_telegram
            ChannelType.VK -> R.drawable.ic_vk
            ChannelType.VK_GROUP -> R.drawable.ic_vk
            ChannelType.AVITO -> R.drawable.ic_avito
            ChannelType.MAX -> R.drawable.ic_max
        }
        channelIcon.setImageResource(iconRes)

        // Set status
        val status = channelCredentialsManager.getChannelStatus(channelType)
        updateStatusUI(status)

        // Set account info
        updateAccountInfoUI()

        // Last check time
        lastCheckTime.text = "Только что"
    }

    private fun updateStatusUI(status: ChannelStatus) {
        when (status) {
            ChannelStatus.CONNECTED -> {
                statusIndicator.setBackgroundResource(R.drawable.status_connected)
                statusText.text = "Подключено"
                statusText.setTextColor(getColor(R.color.green_status))
            }
            ChannelStatus.ERROR -> {
                statusIndicator.setBackgroundResource(R.drawable.status_error)
                statusText.text = "Ошибка подключения"
                statusText.setTextColor(getColor(R.color.status_error))
            }
            ChannelStatus.CHECKING -> {
                statusIndicator.setBackgroundResource(R.drawable.status_checking)
                statusText.text = "Проверка..."
                statusText.setTextColor(getColor(R.color.status_checking))
            }
            ChannelStatus.NOT_CONFIGURED -> {
                statusIndicator.setBackgroundResource(R.drawable.status_not_configured)
                statusText.text = "Не настроено"
                statusText.setTextColor(getColor(R.color.text_secondary))
            }
        }
    }

    private fun updateAccountInfoUI() {
        val displayInfo = channelCredentialsManager.getChannelDisplayInfo(channelType)

        if (displayInfo.isNullOrBlank()) {
            accountInfoSection.visibility = View.GONE
        } else {
            accountInfoSection.visibility = View.VISIBLE
            accountPrimaryInfo.text = displayInfo

            // Set secondary info based on channel type
            when (channelType) {
                ChannelType.WHATSAPP -> {
                    val name = channelCredentialsManager.getWhatsAppName()
                    if (!name.isNullOrBlank()) {
                        accountSecondaryInfo.text = name
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
                ChannelType.TELEGRAM -> {
                    val type = channelCredentialsManager.getTelegramType()
                    if (type == ChannelCredentialsManager.TG_TYPE_BOT) {
                        accountSecondaryInfo.text = "Бот"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    } else if (type == ChannelCredentialsManager.TG_TYPE_USER) {
                        accountSecondaryInfo.text = "User API"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
                ChannelType.TELEGRAM_BOT -> {
                    accountSecondaryInfo.text = "Бот"
                    accountSecondaryInfo.visibility = View.VISIBLE
                }
                ChannelType.VK -> {
                    val userId = channelCredentialsManager.getVkUserId()
                    if (!userId.isNullOrBlank()) {
                        accountSecondaryInfo.text = "ID: $userId"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
                ChannelType.VK_GROUP -> {
                    val groupId = channelCredentialsManager.getVkGroupId()
                    if (!groupId.isNullOrBlank()) {
                        accountSecondaryInfo.text = "ID: $groupId"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
                ChannelType.AVITO -> {
                    val userId = channelCredentialsManager.getAvitoUserId()
                    if (!userId.isNullOrBlank()) {
                        accountSecondaryInfo.text = "ID: $userId"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
                ChannelType.MAX -> {
                    val userId = channelCredentialsManager.getMaxUserId()
                    if (!userId.isNullOrBlank()) {
                        accountSecondaryInfo.text = "ID: $userId"
                        accountSecondaryInfo.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun reconnectChannel() {
        // Open the appropriate setup activity
        when (channelType) {
            ChannelType.WHATSAPP -> startActivity(Intent(this, WhatsAppSetupActivity::class.java))
            ChannelType.TELEGRAM -> startActivity(Intent(this, TelegramSetupActivity::class.java))
            ChannelType.AVITO -> startActivity(Intent(this, AvitoSetupActivity::class.java))
            ChannelType.MAX -> startActivity(Intent(this, MaxSetupActivity::class.java))
            // TODO: Add setup activities for these
            ChannelType.TELEGRAM_BOT, ChannelType.VK, ChannelType.VK_GROUP -> {
                Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
                return
            }
        }
        finish()
    }

    private fun showDisconnectConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Отключить ${channelType.displayName}?")
            .setMessage("Все данные авторизации будут удалены. Для повторного подключения потребуется заново пройти настройку.")
            .setPositiveButton("Отключить") { _, _ ->
                disconnectChannel()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun disconnectChannel() {
        channelCredentialsManager.clearChannel(channelType)
        Toast.makeText(this, "${channelType.displayName} отключен", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onResume() {
        super.onResume()
        // Refresh status when returning from setup
        updateUI()
    }
}
