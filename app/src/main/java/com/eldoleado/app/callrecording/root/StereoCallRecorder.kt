package com.eldoleado.app.callrecording.root

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Запись звонков в стерео с разделением каналов:
 * - Left channel (L): VOICE_UPLINK — твой голос (исходящий)
 * - Right channel (R): VOICE_DOWNLINK — голос собеседника (входящий)
 *
 * Требует ROOT доступ для использования скрытых AudioSource.
 */
class StereoCallRecorder(
    private val outputDir: File,
    private val preferences: RootRecordingPreferences
) {

    companion object {
        private const val TAG = "StereoCallRecorder"

        // Скрытые константы Android MediaRecorder.AudioSource
        const val AUDIO_SOURCE_VOICE_UPLINK = 2    // Исходящий голос (ты)
        const val AUDIO_SOURCE_VOICE_DOWNLINK = 3  // Входящий голос (собеседник)
        const val AUDIO_SOURCE_VOICE_CALL = 4      // Оба канала (моно микс)

        // Параметры записи
        private const val SAMPLE_RATE = 44100
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

        // Размер буфера
        private val BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT
        ) * 2
    }

    private var uplinkRecorder: AudioRecord? = null
    private var downlinkRecorder: AudioRecord? = null

    private val isRecording = AtomicBoolean(false)
    private var recordingJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var currentOutputFile: File? = null
    private var recordingStartTime: Long = 0

    // Буферы для данных
    private val uplinkBuffer = ShortArray(BUFFER_SIZE / 2)
    private val downlinkBuffer = ShortArray(BUFFER_SIZE / 2)

    // Listener для состояния записи
    var onRecordingStateChanged: ((RecordingState) -> Unit)? = null

    enum class RecordingState {
        IDLE,
        STARTING,
        RECORDING,
        STOPPING,
        ERROR
    }

    data class RecordingResult(
        val success: Boolean,
        val file: File? = null,
        val durationMs: Long = 0,
        val error: String? = null
    )

    /**
     * Проверить доступность root-записи
     */
    fun isAvailable(): Boolean {
        if (!RootChecker.isRooted()) {
            Log.w(TAG, "Device is not rooted")
            return false
        }

        // Пробуем создать AudioRecord с VOICE_DOWNLINK
        return try {
            val testRecorder = AudioRecord(
                AUDIO_SOURCE_VOICE_DOWNLINK,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                BUFFER_SIZE
            )
            val available = testRecorder.state == AudioRecord.STATE_INITIALIZED
            testRecorder.release()
            Log.d(TAG, "Stereo recording available: $available")
            available
        } catch (e: Exception) {
            Log.w(TAG, "Stereo recording not available", e)
            false
        }
    }

    /**
     * Начать запись
     * @param callType "incoming", "outgoing", или "voip"
     * @param phoneNumber номер телефона (опционально для VoIP)
     */
    fun startRecording(callType: String, phoneNumber: String? = null): Boolean {
        if (isRecording.get()) {
            Log.w(TAG, "Already recording")
            return false
        }

        if (!RootChecker.isRooted()) {
            Log.e(TAG, "Cannot start: device not rooted")
            onRecordingStateChanged?.invoke(RecordingState.ERROR)
            return false
        }

        onRecordingStateChanged?.invoke(RecordingState.STARTING)

        return try {
            // Создаём файл
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val phoneNumberSafe = phoneNumber?.replace(Regex("[^0-9+]"), "") ?: "unknown"
            val fileName = "${callType}_${phoneNumberSafe}_${timestamp}_stereo.wav"
            currentOutputFile = File(outputDir, fileName)

            // Инициализируем рекордеры
            initRecorders()

            // Запускаем запись
            isRecording.set(true)
            recordingStartTime = System.currentTimeMillis()

            recordingJob = scope.launch {
                recordStereo()
            }

            onRecordingStateChanged?.invoke(RecordingState.RECORDING)
            Log.d(TAG, "Recording started: ${currentOutputFile?.absolutePath}")
            true

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording", e)
            cleanup()
            onRecordingStateChanged?.invoke(RecordingState.ERROR)
            false
        }
    }

    /**
     * Остановить запись
     */
    fun stopRecording(): RecordingResult {
        if (!isRecording.get()) {
            return RecordingResult(false, error = "Not recording")
        }

        onRecordingStateChanged?.invoke(RecordingState.STOPPING)

        isRecording.set(false)

        // Ждём завершения записи
        runBlocking {
            recordingJob?.cancelAndJoin()
        }

        val duration = System.currentTimeMillis() - recordingStartTime
        val file = currentOutputFile

        cleanup()

        onRecordingStateChanged?.invoke(RecordingState.IDLE)

        return if (file != null && file.exists() && file.length() > 44) {
            Log.d(TAG, "Recording stopped: ${file.absolutePath}, duration: ${duration}ms")
            RecordingResult(true, file, duration)
        } else {
            Log.w(TAG, "Recording file is empty or missing")
            file?.delete()
            RecordingResult(false, error = "Recording file is empty")
        }
    }

    /**
     * Проверить идёт ли запись
     */
    fun isRecording(): Boolean = isRecording.get()

    private fun initRecorders() {
        // Uplink — твой голос
        uplinkRecorder = AudioRecord(
            AUDIO_SOURCE_VOICE_UPLINK,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            BUFFER_SIZE
        )

        if (uplinkRecorder?.state != AudioRecord.STATE_INITIALIZED) {
            // Fallback на микрофон
            Log.w(TAG, "VOICE_UPLINK not available, falling back to MIC")
            uplinkRecorder?.release()
            uplinkRecorder = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                BUFFER_SIZE
            )
        }

        // Downlink — голос собеседника
        downlinkRecorder = AudioRecord(
            AUDIO_SOURCE_VOICE_DOWNLINK,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            BUFFER_SIZE
        )

        if (downlinkRecorder?.state != AudioRecord.STATE_INITIALIZED) {
            // Пробуем REMOTE_SUBMIX
            Log.w(TAG, "VOICE_DOWNLINK not available, trying REMOTE_SUBMIX")
            downlinkRecorder?.release()
            downlinkRecorder = AudioRecord(
                MediaRecorder.AudioSource.REMOTE_SUBMIX,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                BUFFER_SIZE
            )
        }

        if (uplinkRecorder?.state != AudioRecord.STATE_INITIALIZED ||
            downlinkRecorder?.state != AudioRecord.STATE_INITIALIZED) {
            throw IllegalStateException("Failed to initialize audio recorders")
        }

        uplinkRecorder?.startRecording()
        downlinkRecorder?.startRecording()
    }

    private suspend fun recordStereo() = withContext(Dispatchers.IO) {
        val outputFile = currentOutputFile ?: return@withContext

        // Создаём WAV файл
        val fos = FileOutputStream(outputFile)

        // Пишем заголовок WAV (обновим размер в конце)
        writeWavHeader(fos, 0)

        var totalBytesWritten = 0L

        try {
            while (isRecording.get()) {
                // Читаем данные из обоих источников
                val uplinkRead = uplinkRecorder?.read(uplinkBuffer, 0, uplinkBuffer.size) ?: 0
                val downlinkRead = downlinkRecorder?.read(downlinkBuffer, 0, downlinkBuffer.size) ?: 0

                if (uplinkRead > 0 || downlinkRead > 0) {
                    val samplesToWrite = maxOf(uplinkRead, downlinkRead)

                    // Интерливим каналы: L R L R L R...
                    val stereoBuffer = ByteBuffer.allocate(samplesToWrite * 4)
                        .order(ByteOrder.LITTLE_ENDIAN)

                    for (i in 0 until samplesToWrite) {
                        // Left channel (uplink / твой голос)
                        val leftSample = if (i < uplinkRead) uplinkBuffer[i] else 0
                        stereoBuffer.putShort(leftSample)

                        // Right channel (downlink / собеседник)
                        val rightSample = if (i < downlinkRead) downlinkBuffer[i] else 0
                        stereoBuffer.putShort(rightSample)
                    }

                    fos.write(stereoBuffer.array())
                    totalBytesWritten += stereoBuffer.capacity()
                }

                // Небольшая пауза чтобы не грузить CPU
                delay(10)
            }
        } finally {
            fos.close()

            // Обновляем заголовок WAV с правильным размером
            updateWavHeader(outputFile, totalBytesWritten)
        }
    }

    private fun writeWavHeader(fos: FileOutputStream, dataSize: Long) {
        val totalSize = dataSize + 36
        val byteRate = SAMPLE_RATE * 2 * 16 / 8  // 2 channels, 16 bit
        val blockAlign = 2 * 16 / 8

        val header = ByteBuffer.allocate(44).order(ByteOrder.LITTLE_ENDIAN)

        // RIFF header
        header.put("RIFF".toByteArray())
        header.putInt(totalSize.toInt())
        header.put("WAVE".toByteArray())

        // fmt subchunk
        header.put("fmt ".toByteArray())
        header.putInt(16)                    // Subchunk1Size (16 for PCM)
        header.putShort(1)                   // AudioFormat (1 = PCM)
        header.putShort(2)                   // NumChannels (2 = stereo)
        header.putInt(SAMPLE_RATE)           // SampleRate
        header.putInt(byteRate)              // ByteRate
        header.putShort(blockAlign.toShort()) // BlockAlign
        header.putShort(16)                  // BitsPerSample

        // data subchunk
        header.put("data".toByteArray())
        header.putInt(dataSize.toInt())

        fos.write(header.array())
    }

    private fun updateWavHeader(file: File, dataSize: Long) {
        try {
            RandomAccessFile(file, "rw").use { raf ->
                val totalSize = dataSize + 36

                // Update RIFF chunk size (offset 4)
                raf.seek(4)
                raf.write(intToByteArrayLE(totalSize.toInt()))

                // Update data chunk size (offset 40)
                raf.seek(40)
                raf.write(intToByteArrayLE(dataSize.toInt()))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update WAV header", e)
        }
    }

    private fun intToByteArrayLE(value: Int): ByteArray {
        return byteArrayOf(
            (value and 0xFF).toByte(),
            ((value shr 8) and 0xFF).toByte(),
            ((value shr 16) and 0xFF).toByte(),
            ((value shr 24) and 0xFF).toByte()
        )
    }

    private fun cleanup() {
        try {
            uplinkRecorder?.stop()
            uplinkRecorder?.release()
            uplinkRecorder = null

            downlinkRecorder?.stop()
            downlinkRecorder?.release()
            downlinkRecorder = null

            currentOutputFile = null
            recordingStartTime = 0
        } catch (e: Exception) {
            Log.e(TAG, "Cleanup error", e)
        }
    }

    fun release() {
        if (isRecording.get()) {
            stopRecording()
        }
        scope.cancel()
    }
}
