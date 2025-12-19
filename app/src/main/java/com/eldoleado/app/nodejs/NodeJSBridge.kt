package com.eldoleado.app.nodejs

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class NodeJSBridge(private val context: Context) {

    companion object {
        private const val TAG = "NodeJSBridge"

        init {
            System.loadLibrary("native-lib")
            System.loadLibrary("node")
        }
    }

    private external fun startNodeWithArguments(arguments: Array<String>): Int

    private var nodeThread: Thread? = null
    private var isRunning = false

    interface StatusCallback {
        fun onQrCode(qrCode: String)
        fun onConnected(phoneNumber: String, name: String)
        fun onDisconnected()
        fun onMessage(from: String, text: String)
        fun onError(error: String)
    }

    var statusCallback: StatusCallback? = null

    fun start() {
        if (isRunning) {
            Log.w(TAG, "Node.js already running")
            return
        }

        // Copy Node.js scripts from assets to internal storage
        copyNodeScripts()

        val nodeDir = File(context.filesDir, "nodejs")
        val mainScript = File(nodeDir, "main.js")

        nodeThread = Thread {
            isRunning = true
            Log.i(TAG, "Starting Node.js with script: ${mainScript.absolutePath}")

            val result = startNodeWithArguments(arrayOf(
                "node",
                mainScript.absolutePath,
                "--port=3000",
                "--data-dir=${nodeDir.absolutePath}"
            ))

            Log.i(TAG, "Node.js exited with code: $result")
            isRunning = false
        }
        nodeThread?.start()
    }

    fun stop() {
        // Node.js doesn't have a clean stop mechanism from outside
        // The process will be killed when the app is destroyed
        isRunning = false
    }

    private fun copyNodeScripts() {
        val nodeDir = File(context.filesDir, "nodejs")

        // Skip if already copied (check for node_modules existence)
        val nodeModulesDir = File(nodeDir, "node_modules")
        if (nodeModulesDir.exists() && nodeModulesDir.isDirectory) {
            Log.d(TAG, "Node scripts already copied, skipping")
            return
        }

        if (!nodeDir.exists()) {
            nodeDir.mkdirs()
        }

        // Recursively copy all files from assets/nodejs to internal storage
        val assetManager = context.assets
        try {
            copyAssetFolder("nodejs", nodeDir.absolutePath)
            Log.i(TAG, "Node scripts copied successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to copy node scripts", e)
        }
    }

    private fun copyAssetFolder(assetPath: String, destPath: String) {
        val assetManager = context.assets
        val files = assetManager.list(assetPath) ?: return

        val destDir = File(destPath)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }

        for (filename in files) {
            val assetFilePath = "$assetPath/$filename"
            val destFilePath = "$destPath/$filename"

            // Check if it's a directory by trying to list its contents
            val subFiles = assetManager.list(assetFilePath)
            if (subFiles != null && subFiles.isNotEmpty()) {
                // It's a directory, recurse
                copyAssetFolder(assetFilePath, destFilePath)
            } else {
                // It's a file, copy it
                try {
                    val inputStream = assetManager.open(assetFilePath)
                    val outFile = File(destFilePath)
                    val outputStream = FileOutputStream(outFile)

                    inputStream.copyTo(outputStream)

                    inputStream.close()
                    outputStream.close()
                } catch (e: Exception) {
                    // Some entries might be empty directories
                    Log.w(TAG, "Could not copy: $assetFilePath - ${e.message}")
                }
            }
        }
    }

    fun isRunning(): Boolean = isRunning
}
