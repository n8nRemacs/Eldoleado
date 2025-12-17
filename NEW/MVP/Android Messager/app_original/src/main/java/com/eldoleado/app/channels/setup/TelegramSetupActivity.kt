package com.eldoleado.app.channels.setup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eldoleado.app.R
import com.eldoleado.app.channels.ChannelCredentialsManager
import com.eldoleado.app.channels.ChannelStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class TelegramSetupActivity : AppCompatActivity() {

    private lateinit var channelCredentialsManager: ChannelCredentialsManager

    // Views
    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView

    // Steps
    private lateinit var stepChooseType: LinearLayout
    private lateinit var stepBotToken: LinearLayout
    private lateinit var stepUserApi: LinearLayout
    private lateinit var stepEnterCode: LinearLayout
    private lateinit var stepSuccess: LinearLayout
    private lateinit var loadingState: LinearLayout

    // Step 1: Choose type
    private lateinit var optionBot: LinearLayout
    private lateinit var optionUser: LinearLayout

    // Step 2a: Bot
    private lateinit var btnOpenBotFather: Button
    private lateinit var inputBotToken: EditText
    private lateinit var btnVerifyBot: Button

    // Step 2b: User
    private lateinit var btnOpenTelegramOrg: Button
    private lateinit var inputApiId: EditText
    private lateinit var inputApiHash: EditText
    private lateinit var inputPhone: EditText
    private lateinit var btnRequestCode: Button

    // Step 3: Code
    private lateinit var codeDescription: TextView
    private lateinit var inputCode: EditText
    private lateinit var btnVerifyCode: Button
    private lateinit var btnResendCode: TextView

    // Success
    private lateinit var successInfo: TextView
    private lateinit var btnDone: Button

    // Loading
    private lateinit var loadingText: TextView

    // State
    private var currentStep = Step.CHOOSE_TYPE
    private var selectedType: String? = null

    enum class Step {
        CHOOSE_TYPE,
        BOT_TOKEN,
        USER_API,
        ENTER_CODE,
        SUCCESS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telegram_setup)

        channelCredentialsManager = ChannelCredentialsManager(this)

        initViews()
        setupListeners()
        showStep(Step.CHOOSE_TYPE)
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        headerTitle = findViewById(R.id.headerTitle)

        stepChooseType = findViewById(R.id.stepChooseType)
        stepBotToken = findViewById(R.id.stepBotToken)
        stepUserApi = findViewById(R.id.stepUserApi)
        stepEnterCode = findViewById(R.id.stepEnterCode)
        stepSuccess = findViewById(R.id.stepSuccess)
        loadingState = findViewById(R.id.loadingState)

        optionBot = findViewById(R.id.optionBot)
        optionUser = findViewById(R.id.optionUser)

        btnOpenBotFather = findViewById(R.id.btnOpenBotFather)
        inputBotToken = findViewById(R.id.inputBotToken)
        btnVerifyBot = findViewById(R.id.btnVerifyBot)

        btnOpenTelegramOrg = findViewById(R.id.btnOpenTelegramOrg)
        inputApiId = findViewById(R.id.inputApiId)
        inputApiHash = findViewById(R.id.inputApiHash)
        inputPhone = findViewById(R.id.inputPhone)
        btnRequestCode = findViewById(R.id.btnRequestCode)

        codeDescription = findViewById(R.id.codeDescription)
        inputCode = findViewById(R.id.inputCode)
        btnVerifyCode = findViewById(R.id.btnVerifyCode)
        btnResendCode = findViewById(R.id.btnResendCode)

        successInfo = findViewById(R.id.successInfo)
        btnDone = findViewById(R.id.btnDone)

        loadingText = findViewById(R.id.loadingText)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Step 1: Choose type
        optionBot.setOnClickListener {
            selectedType = ChannelCredentialsManager.TG_TYPE_BOT
            showStep(Step.BOT_TOKEN)
        }

        optionUser.setOnClickListener {
            selectedType = ChannelCredentialsManager.TG_TYPE_USER
            showStep(Step.USER_API)
        }

        // Step 2a: Bot
        btnOpenBotFather.setOnClickListener {
            openTelegramLink("https://t.me/BotFather")
        }

        btnVerifyBot.setOnClickListener {
            verifyBotToken()
        }

        // Step 2b: User
        btnOpenTelegramOrg.setOnClickListener {
            openBrowser("https://my.telegram.org")
        }

        btnRequestCode.setOnClickListener {
            requestVerificationCode()
        }

        // Step 3: Code
        btnVerifyCode.setOnClickListener {
            verifyCode()
        }

        btnResendCode.setOnClickListener {
            requestVerificationCode()
        }

        // Success
        btnDone.setOnClickListener {
            finish()
        }
    }

    private fun showStep(step: Step) {
        currentStep = step

        stepChooseType.visibility = View.GONE
        stepBotToken.visibility = View.GONE
        stepUserApi.visibility = View.GONE
        stepEnterCode.visibility = View.GONE
        stepSuccess.visibility = View.GONE
        loadingState.visibility = View.GONE

        when (step) {
            Step.CHOOSE_TYPE -> {
                stepChooseType.visibility = View.VISIBLE
                headerTitle.text = "Настройка Telegram"
            }
            Step.BOT_TOKEN -> {
                stepBotToken.visibility = View.VISIBLE
                headerTitle.text = "Telegram Bot"
            }
            Step.USER_API -> {
                stepUserApi.visibility = View.VISIBLE
                headerTitle.text = "Telegram User"
            }
            Step.ENTER_CODE -> {
                stepEnterCode.visibility = View.VISIBLE
                headerTitle.text = "Подтверждение"
            }
            Step.SUCCESS -> {
                stepSuccess.visibility = View.VISIBLE
                headerTitle.text = "Готово"
            }
        }
    }

    private fun showLoading(text: String) {
        stepChooseType.visibility = View.GONE
        stepBotToken.visibility = View.GONE
        stepUserApi.visibility = View.GONE
        stepEnterCode.visibility = View.GONE
        stepSuccess.visibility = View.GONE
        loadingState.visibility = View.VISIBLE
        loadingText.text = text
    }

    private fun verifyBotToken() {
        val token = inputBotToken.text.toString().trim()

        if (token.isEmpty()) {
            Toast.makeText(this, "Введите токен бота", Toast.LENGTH_SHORT).show()
            return
        }

        if (!token.contains(":")) {
            Toast.makeText(this, "Неверный формат токена", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading("Проверка токена...")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://api.telegram.org/bot$token/getMe")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)

                    if (json.getBoolean("ok")) {
                        val result = json.getJSONObject("result")
                        val username = result.getString("username")

                        // Save credentials
                        channelCredentialsManager.saveTelegramBot(token, username)

                        withContext(Dispatchers.Main) {
                            successInfo.text = "@$username"
                            showStep(Step.SUCCESS)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            showStep(Step.BOT_TOKEN)
                            Toast.makeText(this@TelegramSetupActivity, "Неверный токен", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showStep(Step.BOT_TOKEN)
                        Toast.makeText(this@TelegramSetupActivity, "Ошибка проверки: $responseCode", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showStep(Step.BOT_TOKEN)
                    Toast.makeText(this@TelegramSetupActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestVerificationCode() {
        val apiId = inputApiId.text.toString().trim()
        val apiHash = inputApiHash.text.toString().trim()
        val phone = inputPhone.text.toString().trim()

        if (apiId.isEmpty() || apiHash.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        // For User API, we need to send request to MCP server
        // This is a placeholder - actual implementation will connect to mcp-telegram server
        showLoading("Отправка кода...")

        CoroutineScope(Dispatchers.Main).launch {
            // TODO: Implement actual code request via MCP server
            // For now, just show the code input step
            kotlinx.coroutines.delay(1000)

            val maskedPhone = phone.replace(Regex("\\d(?=\\d{2})"), "*")
            codeDescription.text = "Код отправлен на $maskedPhone"
            showStep(Step.ENTER_CODE)
        }
    }

    private fun verifyCode() {
        val code = inputCode.text.toString().trim()

        if (code.isEmpty()) {
            Toast.makeText(this, "Введите код", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading("Проверка кода...")

        CoroutineScope(Dispatchers.Main).launch {
            // TODO: Implement actual code verification via MCP server
            kotlinx.coroutines.delay(1000)

            val apiId = inputApiId.text.toString().trim()
            val apiHash = inputApiHash.text.toString().trim()
            val phone = inputPhone.text.toString().trim()

            // Save credentials (placeholder - actual session will come from MCP)
            channelCredentialsManager.saveTelegramUser(apiId, apiHash, phone, null)

            successInfo.text = phone
            showStep(Step.SUCCESS)
        }
    }

    private fun openTelegramLink(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось открыть Telegram", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось открыть браузер", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (currentStep) {
            Step.BOT_TOKEN, Step.USER_API -> showStep(Step.CHOOSE_TYPE)
            Step.ENTER_CODE -> showStep(Step.USER_API)
            Step.SUCCESS -> finish()
            else -> super.onBackPressed()
        }
    }
}
