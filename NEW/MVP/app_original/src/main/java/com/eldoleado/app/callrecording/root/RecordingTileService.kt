package com.eldoleado.app.callrecording.root

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.eldoleado.app.R
import java.io.File

/**
 * Quick Settings Tile для ручного запуска записи VoIP звонков.
 * Появляется в шторке уведомлений.
 *
 * Для регистрации добавить в AndroidManifest.xml:
 *
 * <service
 *     android:name=".callrecording.root.RecordingTileService"
 *     android:icon="@drawable/ic_mic"
 *     android:label="@string/tile_record_call"
 *     android:permission="android.permission.BIND_QUICK_SETTINGS_TILE"
 *     android:exported="true">
 *     <intent-filter>
 *         <action android:name="android.service.quicksettings.action.QS_TILE" />
 *     </intent-filter>
 * </service>
 */
class RecordingTileService : TileService() {

    companion object {
        private const val TAG = "RecordingTileService"

        // Singleton recorder instance
        @Volatile
        private var recorder: StereoCallRecorder? = null

        private var isRecordingActive = false

        /**
         * Запросить обновление tile (вызывать при изменении состояния)
         */
        fun requestUpdate(context: Context) {
            try {
                requestListeningState(
                    context,
                    ComponentName(context, RecordingTileService::class.java)
                )
            } catch (e: Exception) {
                Log.w(TAG, "Failed to request tile update", e)
            }
        }

        /**
         * Проверить идёт ли запись
         */
        fun isRecording(): Boolean = isRecordingActive
    }

    private lateinit var preferences: RootRecordingPreferences

    override fun onCreate() {
        super.onCreate()
        preferences = RootRecordingPreferences(this)
    }

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onStopListening() {
        super.onStopListening()
    }

    override fun onClick() {
        super.onClick()

        if (!preferences.quickTileEnabled) {
            Log.d(TAG, "Quick tile is disabled in settings")
            return
        }

        if (isRecordingActive) {
            stopRecording()
        } else {
            startRecording()
        }

        updateTileState()
    }

    private fun startRecording() {
        // Проверяем root
        if (!RootChecker.isRooted()) {
            Log.w(TAG, "Cannot start: device not rooted")
            showToast("Требуется root доступ")
            return
        }

        // Создаём recorder если нужно
        if (recorder == null) {
            val outputDir = File(filesDir, "call_recordings")
            if (!outputDir.exists()) outputDir.mkdirs()
            recorder = StereoCallRecorder(outputDir, preferences)
        }

        // Запускаем запись
        val started = recorder?.startRecording("voip", null) ?: false

        if (started) {
            isRecordingActive = true
            vibrateIfEnabled()
            Log.d(TAG, "Recording started via tile")
        } else {
            showToast("Не удалось начать запись")
        }
    }

    private fun stopRecording() {
        val result = recorder?.stopRecording()

        isRecordingActive = false
        vibrateIfEnabled()

        if (result?.success == true) {
            Log.d(TAG, "Recording stopped: ${result.file?.name}, duration: ${result.durationMs}ms")
            showToast("Записано: ${formatDuration(result.durationMs)}")

            // Планируем загрузку если включено
            if (preferences.autoUpload && result.file != null) {
                scheduleUpload(result.file)
            }
        } else {
            Log.w(TAG, "Recording stopped with error: ${result?.error}")
        }
    }

    private fun updateTileState() {
        val tile = qsTile ?: return

        if (isRecordingActive) {
            tile.state = Tile.STATE_ACTIVE
            tile.label = "Запись..."
            tile.contentDescription = "Идёт запись звонка. Нажмите чтобы остановить."

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.subtitle = "Нажмите для остановки"
            }

            // Красная иконка
            tile.icon = Icon.createWithResource(this, R.drawable.ic_mic)
        } else {
            // Проверяем доступность
            val isAvailable = RootChecker.isRooted() && preferences.quickTileEnabled

            tile.state = if (isAvailable) Tile.STATE_INACTIVE else Tile.STATE_UNAVAILABLE
            tile.label = "Запись звонка"
            tile.contentDescription = if (isAvailable) {
                "Нажмите чтобы начать запись VoIP звонка"
            } else {
                "Запись недоступна (требуется root)"
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.subtitle = if (isAvailable) "VoIP" else "Нет root"
            }

            tile.icon = Icon.createWithResource(this, R.drawable.ic_mic)
        }

        tile.updateTile()
    }

    private fun vibrateIfEnabled() {
        if (!preferences.vibrateOnStart) return

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(100)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Vibration failed", e)
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun formatDuration(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / 1000 / 60) % 60
        val hours = ms / 1000 / 60 / 60

        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }

    private fun scheduleUpload(file: File) {
        // TODO: Интегрировать с CallUploadWorker
        Log.d(TAG, "Upload scheduled for: ${file.absolutePath}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRecordingActive) {
            recorder?.stopRecording()
            isRecordingActive = false
        }
    }
}
