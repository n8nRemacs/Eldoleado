package com.eldoleado.app.callrecording.root

import android.util.Log
import java.io.File

/**
 * Проверка наличия root доступа на устройстве.
 * Поддерживает Magisk, SuperSU и другие решения.
 */
object RootChecker {

    private const val TAG = "RootChecker"

    // Кэшируем результат проверки
    private var cachedRootStatus: Boolean? = null
    private var cachedRootMethod: RootMethod? = null

    enum class RootMethod {
        NONE,
        MAGISK,
        SUPERSU,
        GENERIC_SU
    }

    data class RootStatus(
        val isRooted: Boolean,
        val method: RootMethod,
        val suPath: String? = null
    )

    /**
     * Быстрая проверка наличия root (кэшированная)
     */
    fun isRooted(): Boolean {
        cachedRootStatus?.let { return it }

        val status = checkRootStatus()
        cachedRootStatus = status.isRooted
        cachedRootMethod = status.method
        return status.isRooted
    }

    /**
     * Полная проверка с информацией о методе root
     */
    fun checkRootStatus(): RootStatus {
        // 1. Проверка Magisk
        if (checkMagisk()) {
            Log.d(TAG, "Root detected: Magisk")
            return RootStatus(true, RootMethod.MAGISK, findSuBinary())
        }

        // 2. Проверка SuperSU
        if (checkSuperSU()) {
            Log.d(TAG, "Root detected: SuperSU")
            return RootStatus(true, RootMethod.SUPERSU, findSuBinary())
        }

        // 3. Проверка наличия su binary
        val suPath = findSuBinary()
        if (suPath != null) {
            // Проверяем что su реально работает
            if (testSuExecution()) {
                Log.d(TAG, "Root detected: Generic SU at $suPath")
                return RootStatus(true, RootMethod.GENERIC_SU, suPath)
            }
        }

        Log.d(TAG, "Root not detected")
        return RootStatus(false, RootMethod.NONE)
    }

    /**
     * Сбросить кэш (например после установки Magisk)
     */
    fun clearCache() {
        cachedRootStatus = null
        cachedRootMethod = null
    }

    private fun checkMagisk(): Boolean {
        // Magisk скрывает себя, но можно проверить через su
        val magiskPaths = listOf(
            "/sbin/magisk",
            "/system/bin/magisk",
            "/system/xbin/magisk",
            "/data/adb/magisk"
        )

        for (path in magiskPaths) {
            if (File(path).exists()) {
                return true
            }
        }

        // Проверка через выполнение команды
        return try {
            val process = Runtime.getRuntime().exec("magisk -v")
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun checkSuperSU(): Boolean {
        val supersuPaths = listOf(
            "/system/app/Superuser.apk",
            "/system/app/SuperSU.apk",
            "/data/data/com.noshufou.android.su",
            "/data/data/eu.chainfire.supersu"
        )

        for (path in supersuPaths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    private fun findSuBinary(): String? {
        val paths = listOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/su/bin/su",
            "/data/adb/su"
        )

        for (path in paths) {
            if (File(path).exists()) {
                return path
            }
        }
        return null
    }

    private fun testSuExecution(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
            val result = process.inputStream.bufferedReader().readText()
            process.waitFor()
            result.contains("uid=0")
        } catch (e: Exception) {
            Log.w(TAG, "su execution test failed", e)
            false
        }
    }

    /**
     * Выполнить команду с root правами
     */
    fun executeAsRoot(command: String): RootCommandResult {
        if (!isRooted()) {
            return RootCommandResult(false, "", "Device is not rooted")
        }

        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val stdout = process.inputStream.bufferedReader().readText()
            val stderr = process.errorStream.bufferedReader().readText()
            val exitCode = process.waitFor()

            RootCommandResult(
                success = exitCode == 0,
                output = stdout,
                error = stderr,
                exitCode = exitCode
            )
        } catch (e: Exception) {
            RootCommandResult(false, "", e.message ?: "Unknown error")
        }
    }

    data class RootCommandResult(
        val success: Boolean,
        val output: String,
        val error: String,
        val exitCode: Int = -1
    )
}
