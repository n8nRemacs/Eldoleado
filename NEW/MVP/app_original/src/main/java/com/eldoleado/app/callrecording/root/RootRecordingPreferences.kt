package com.eldoleado.app.callrecording.root

import android.content.Context
import android.content.SharedPreferences

/**
 * Настройки модуля записи с root доступом.
 * Готов для интеграции в основное приложение.
 */
class RootRecordingPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "root_recording_prefs"

        // Основные настройки
        private const val KEY_ROOT_RECORDING_ENABLED = "root_recording_enabled"
        private const val KEY_STEREO_MODE = "stereo_mode"
        private const val KEY_AUTO_RECORD_GSM = "auto_record_gsm"
        private const val KEY_AUTO_RECORD_VOIP = "auto_record_voip"

        // Качество записи
        private const val KEY_SAMPLE_RATE = "sample_rate"
        private const val KEY_BIT_DEPTH = "bit_depth"
        private const val KEY_OUTPUT_FORMAT = "output_format"

        // Поведение
        private const val KEY_SHOW_FLOATING_BUTTON = "show_floating_button"
        private const val KEY_QUICK_TILE_ENABLED = "quick_tile_enabled"
        private const val KEY_VIBRATE_ON_START = "vibrate_on_start"
        private const val KEY_NOTIFICATION_PRIORITY = "notification_priority"

        // Хранение
        private const val KEY_MAX_STORAGE_MB = "max_storage_mb"
        private const val KEY_AUTO_DELETE_DAYS = "auto_delete_days"
        private const val KEY_AUTO_UPLOAD = "auto_upload"

        // Значения по умолчанию
        private const val DEFAULT_SAMPLE_RATE = 44100
        private const val DEFAULT_BIT_DEPTH = 16
        private const val DEFAULT_MAX_STORAGE_MB = 500
        private const val DEFAULT_AUTO_DELETE_DAYS = 30
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // ==================== Основные настройки ====================

    /**
     * Включена ли запись с root (улучшенное качество)
     */
    var isRootRecordingEnabled: Boolean
        get() = prefs.getBoolean(KEY_ROOT_RECORDING_ENABLED, false)
        set(value) = prefs.edit().putBoolean(KEY_ROOT_RECORDING_ENABLED, value).apply()

    /**
     * Режим стерео записи
     */
    var stereoMode: StereoMode
        get() = StereoMode.fromString(prefs.getString(KEY_STEREO_MODE, StereoMode.STEREO_SPLIT.name))
        set(value) = prefs.edit().putString(KEY_STEREO_MODE, value.name).apply()

    /**
     * Автоматическая запись GSM звонков
     */
    var autoRecordGsm: Boolean
        get() = prefs.getBoolean(KEY_AUTO_RECORD_GSM, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_RECORD_GSM, value).apply()

    /**
     * Автоматическая запись VoIP (требует Accessibility Service)
     */
    var autoRecordVoip: Boolean
        get() = prefs.getBoolean(KEY_AUTO_RECORD_VOIP, false)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_RECORD_VOIP, value).apply()

    // ==================== Качество записи ====================

    /**
     * Частота дискретизации (Hz)
     */
    var sampleRate: Int
        get() = prefs.getInt(KEY_SAMPLE_RATE, DEFAULT_SAMPLE_RATE)
        set(value) = prefs.edit().putInt(KEY_SAMPLE_RATE, value).apply()

    /**
     * Битность (8, 16, 24)
     */
    var bitDepth: Int
        get() = prefs.getInt(KEY_BIT_DEPTH, DEFAULT_BIT_DEPTH)
        set(value) = prefs.edit().putInt(KEY_BIT_DEPTH, value).apply()

    /**
     * Формат выходного файла
     */
    var outputFormat: OutputFormat
        get() = OutputFormat.fromString(prefs.getString(KEY_OUTPUT_FORMAT, OutputFormat.WAV.name))
        set(value) = prefs.edit().putString(KEY_OUTPUT_FORMAT, value.name).apply()

    // ==================== Поведение ====================

    /**
     * Показывать плавающую кнопку записи
     */
    var showFloatingButton: Boolean
        get() = prefs.getBoolean(KEY_SHOW_FLOATING_BUTTON, false)
        set(value) = prefs.edit().putBoolean(KEY_SHOW_FLOATING_BUTTON, value).apply()

    /**
     * Quick Settings tile в шторке
     */
    var quickTileEnabled: Boolean
        get() = prefs.getBoolean(KEY_QUICK_TILE_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_QUICK_TILE_ENABLED, value).apply()

    /**
     * Вибрация при начале записи
     */
    var vibrateOnStart: Boolean
        get() = prefs.getBoolean(KEY_VIBRATE_ON_START, true)
        set(value) = prefs.edit().putBoolean(KEY_VIBRATE_ON_START, value).apply()

    /**
     * Приоритет уведомления (low, default, high)
     */
    var notificationPriority: String
        get() = prefs.getString(KEY_NOTIFICATION_PRIORITY, "low") ?: "low"
        set(value) = prefs.edit().putString(KEY_NOTIFICATION_PRIORITY, value).apply()

    // ==================== Хранение ====================

    /**
     * Максимальный размер хранилища (MB)
     */
    var maxStorageMb: Int
        get() = prefs.getInt(KEY_MAX_STORAGE_MB, DEFAULT_MAX_STORAGE_MB)
        set(value) = prefs.edit().putInt(KEY_MAX_STORAGE_MB, value).apply()

    /**
     * Автоудаление записей старше N дней (0 = отключено)
     */
    var autoDeleteDays: Int
        get() = prefs.getInt(KEY_AUTO_DELETE_DAYS, DEFAULT_AUTO_DELETE_DAYS)
        set(value) = prefs.edit().putInt(KEY_AUTO_DELETE_DAYS, value).apply()

    /**
     * Автоматическая загрузка на сервер
     */
    var autoUpload: Boolean
        get() = prefs.getBoolean(KEY_AUTO_UPLOAD, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_UPLOAD, value).apply()

    // ==================== Enums ====================

    enum class StereoMode {
        STEREO_SPLIT,     // Левый = ты, Правый = собеседник
        STEREO_MIXED,     // Оба канала одинаковые (микс)
        MONO_UPLINK,      // Только твой голос
        MONO_DOWNLINK,    // Только голос собеседника
        MONO_MIXED;       // Моно микс

        companion object {
            fun fromString(value: String?): StereoMode {
                return try {
                    valueOf(value ?: STEREO_SPLIT.name)
                } catch (e: Exception) {
                    STEREO_SPLIT
                }
            }
        }

        fun getDisplayName(): String = when (this) {
            STEREO_SPLIT -> "Стерео (раздельно)"
            STEREO_MIXED -> "Стерео (микс)"
            MONO_UPLINK -> "Моно (только я)"
            MONO_DOWNLINK -> "Моно (только собеседник)"
            MONO_MIXED -> "Моно (микс)"
        }
    }

    enum class OutputFormat {
        WAV,    // Без сжатия, лучшее качество, большой размер
        M4A,    // AAC сжатие, хорошее качество, средний размер
        OGG;    // Vorbis/Opus, хорошее качество, маленький размер

        companion object {
            fun fromString(value: String?): OutputFormat {
                return try {
                    valueOf(value ?: WAV.name)
                } catch (e: Exception) {
                    WAV
                }
            }
        }

        fun getExtension(): String = name.lowercase()

        fun getDisplayName(): String = when (this) {
            WAV -> "WAV (без сжатия)"
            M4A -> "M4A (AAC)"
            OGG -> "OGG (Opus)"
        }
    }

    // ==================== Утилиты ====================

    /**
     * Получить все настройки как Map (для отладки/логирования)
     */
    fun getAllSettings(): Map<String, Any?> = mapOf(
        "rootRecordingEnabled" to isRootRecordingEnabled,
        "stereoMode" to stereoMode.name,
        "autoRecordGsm" to autoRecordGsm,
        "autoRecordVoip" to autoRecordVoip,
        "sampleRate" to sampleRate,
        "bitDepth" to bitDepth,
        "outputFormat" to outputFormat.name,
        "showFloatingButton" to showFloatingButton,
        "quickTileEnabled" to quickTileEnabled,
        "vibrateOnStart" to vibrateOnStart,
        "maxStorageMb" to maxStorageMb,
        "autoDeleteDays" to autoDeleteDays,
        "autoUpload" to autoUpload
    )

    /**
     * Сбросить все настройки
     */
    fun resetToDefaults() {
        prefs.edit().clear().apply()
    }
}
