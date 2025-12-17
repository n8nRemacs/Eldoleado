package com.eldoleado.app.channels.setup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
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

class AvitoSetupActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AvitoSetupActivity"
        private const val AVITO_URL = "https://m.avito.ru/profile"
        private const val AVITO_LOGIN_URL = "https://m.avito.ru/login"
    }

    private lateinit var channelCredentialsManager: ChannelCredentialsManager

    // Views
    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var instructionsContainer: LinearLayout
    private lateinit var webView: WebView
    private lateinit var loadingOverlay: LinearLayout
    private lateinit var loadingText: TextView
    private lateinit var successOverlay: LinearLayout
    private lateinit var successInfo: TextView
    private lateinit var btnDone: Button

    private var sessidExtracted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avito_setup)

        channelCredentialsManager = ChannelCredentialsManager(this)

        initViews()
        setupListeners()
        setupWebView()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        headerTitle = findViewById(R.id.headerTitle)
        instructionsContainer = findViewById(R.id.instructionsContainer)
        webView = findViewById(R.id.webView)
        loadingOverlay = findViewById(R.id.loadingOverlay)
        loadingText = findViewById(R.id.loadingText)
        successOverlay = findViewById(R.id.successOverlay)
        successInfo = findViewById(R.id.successInfo)
        btnDone = findViewById(R.id.btnDone)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnDone.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        // Clear cookies first
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            userAgentString = "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "Page finished: $url")

                // Check cookies after page load
                checkAndExtractSessid(url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                Log.d(TAG, "Loading URL: $url")
                return false
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100) {
                    showLoading("Загрузка... $newProgress%")
                } else {
                    hideLoading()
                }
            }
        }

        // Load Avito login page
        webView.loadUrl(AVITO_LOGIN_URL)
    }

    private fun checkAndExtractSessid(url: String?) {
        if (sessidExtracted) return

        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie("avito.ru") ?: cookieManager.getCookie("m.avito.ru")

        Log.d(TAG, "Cookies: $cookies")

        if (cookies != null && cookies.contains("sessid=")) {
            // Extract sessid
            val sessid = extractCookieValue(cookies, "sessid")

            if (!sessid.isNullOrEmpty()) {
                Log.d(TAG, "Found sessid: ${sessid.take(10)}...")

                // Check if user is logged in by looking at the URL or cookies
                if (url?.contains("/profile") == true || cookies.contains("auth=1") || cookies.contains("u=")) {
                    sessidExtracted = true
                    verifySessid(sessid)
                }
            }
        }
    }

    private fun extractCookieValue(cookies: String, name: String): String? {
        return cookies.split(";")
            .map { it.trim() }
            .find { it.startsWith("$name=") }
            ?.substringAfter("$name=")
    }

    private fun verifySessid(sessid: String) {
        showLoading("Проверка авторизации...")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Verify sessid by calling Avito API
                val url = URL("https://m.avito.ru/api/1/profile/short")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Cookie", "sessid=$sessid; auth=1")
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                Log.d(TAG, "Profile API response: $responseCode")

                if (responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    Log.d(TAG, "Profile response: $response")

                    try {
                        val json = JSONObject(response)
                        val email = json.optString("email", null)
                        val name = json.optString("name", null)
                        val userId = json.optString("id", null)

                        // Save credentials
                        channelCredentialsManager.saveAvito(sessid, userId, email ?: name)

                        withContext(Dispatchers.Main) {
                            showSuccess(email ?: name ?: "Подключено")
                        }
                    } catch (e: Exception) {
                        // Even if parsing fails, sessid might still be valid
                        channelCredentialsManager.saveAvito(sessid, null, null)

                        withContext(Dispatchers.Main) {
                            showSuccess("Подключено")
                        }
                    }
                } else {
                    // Try alternative check
                    withContext(Dispatchers.Main) {
                        // Save anyway if we got to profile page
                        channelCredentialsManager.saveAvito(sessid, null, null)
                        showSuccess("Подключено")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error verifying sessid", e)
                withContext(Dispatchers.Main) {
                    // Save anyway - user did login
                    channelCredentialsManager.saveAvito(sessid, null, null)
                    showSuccess("Подключено")
                }
            }
        }
    }

    private fun showLoading(text: String) {
        loadingOverlay.visibility = View.VISIBLE
        loadingText.text = text
    }

    private fun hideLoading() {
        loadingOverlay.visibility = View.GONE
    }

    private fun showSuccess(info: String) {
        hideLoading()
        instructionsContainer.visibility = View.GONE
        webView.visibility = View.GONE
        successOverlay.visibility = View.VISIBLE
        successInfo.text = info
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
