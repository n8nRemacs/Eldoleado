# Root Call Recording Module

Модуль записи звонков с ROOT доступом для улучшенного качества и поддержки VoIP.

## Возможности

| Функция | Без Root | С Root |
|---------|----------|--------|
| Запись GSM звонков | ✅ (только микрофон) | ✅ Стерео (оба канала) |
| Запись VoIP (WhatsApp, Telegram) | ❌ | ✅ Ручной запуск |
| Разделение каналов | ❌ | ✅ L=ты, R=собеседник |
| Качество транскрибации | Среднее | Отличное |

## Структура модуля

```
root/
├── RootChecker.kt           # Проверка root (Magisk, SuperSU, generic)
├── StereoCallRecorder.kt    # Запись в стерео WAV
├── RootRecordingPreferences.kt  # Настройки модуля
├── RecordingTileService.kt  # Quick Settings плитка
└── README.md               # Эта документация
```

## Интеграция

### 1. AndroidManifest.xml

Добавить в `<application>`:

```xml
<!-- Quick Settings Tile для записи VoIP -->
<service
    android:name=".callrecording.root.RecordingTileService"
    android:icon="@drawable/ic_mic"
    android:label="Запись звонка"
    android:permission="android.permission.BIND_QUICK_SETTINGS_TILE"
    android:exported="true">
    <intent-filter>
        <action android:name="android.service.quicksettings.action.QS_TILE" />
    </intent-filter>
</service>
```

### 2. strings.xml

```xml
<string name="tile_record_call">Запись звонка</string>
<string name="tile_recording">Запись...</string>
```

### 3. Иконка

Убедиться что есть `res/drawable/ic_mic.xml` (уже должен быть в проекте).

### 4. Интеграция в CallRecordingService

Для автоматического использования root при GSM звонках:

```kotlin
// В CallRecordingService.kt

private var stereoRecorder: StereoCallRecorder? = null
private val rootPrefs by lazy { RootRecordingPreferences(this) }

private fun startRecording() {
    // Проверяем можно ли использовать root
    if (rootPrefs.isRootRecordingEnabled && RootChecker.isRooted()) {
        startStereoRecording()
    } else {
        startStandardRecording() // текущая логика
    }
}

private fun startStereoRecording() {
    if (stereoRecorder == null) {
        stereoRecorder = StereoCallRecorder(getRecordingsDirectory(), rootPrefs)
    }
    stereoRecorder?.startRecording(callType, currentPhoneNumber)
}
```

### 5. UI настроек

Добавить в настройки приложения:

```kotlin
// Пример для Compose
@Composable
fun RootRecordingSettings() {
    val context = LocalContext.current
    val prefs = remember { RootRecordingPreferences(context) }
    val isRooted = remember { RootChecker.isRooted() }

    if (isRooted) {
        SwitchPreference(
            title = "Улучшенная запись (Root)",
            subtitle = "Стерео запись с разделением каналов",
            checked = prefs.isRootRecordingEnabled,
            onCheckedChange = { prefs.isRootRecordingEnabled = it }
        )

        DropdownPreference(
            title = "Режим записи",
            options = RootRecordingPreferences.StereoMode.values().map { it.getDisplayName() },
            selected = prefs.stereoMode.getDisplayName(),
            onSelected = { /* ... */ }
        )
    }
}
```

## Использование

### Программно

```kotlin
val prefs = RootRecordingPreferences(context)
val outputDir = File(context.filesDir, "call_recordings")
val recorder = StereoCallRecorder(outputDir, prefs)

// Проверка доступности
if (recorder.isAvailable()) {
    // Начать запись
    recorder.startRecording("voip", "+79001234567")

    // Слушатель состояния
    recorder.onRecordingStateChanged = { state ->
        when (state) {
            RecordingState.RECORDING -> showNotification()
            RecordingState.IDLE -> hideNotification()
            RecordingState.ERROR -> showError()
        }
    }

    // Остановить запись
    val result = recorder.stopRecording()
    if (result.success) {
        uploadFile(result.file!!)
    }
}
```

### Через Quick Settings

1. Потянуть шторку уведомлений вниз
2. Нажать "Редактировать" (карандаш)
3. Найти плитку "Запись звонка" и добавить
4. Когда начинается VoIP звонок — нажать плитку
5. По окончании — нажать снова

## Формат выходных файлов

**Стерео WAV:**
```
voip_unknown_20251205_143022_stereo.wav

Каналы:
├── Left (L)  → Твой голос (VOICE_UPLINK)
└── Right (R) → Голос собеседника (VOICE_DOWNLINK)
```

## Настройки

| Параметр | Описание | По умолчанию |
|----------|----------|--------------|
| `isRootRecordingEnabled` | Использовать root запись | false |
| `stereoMode` | Режим стерео | STEREO_SPLIT |
| `autoRecordGsm` | Авто-запись GSM | true |
| `autoRecordVoip` | Авто-запись VoIP | false |
| `sampleRate` | Частота (Hz) | 44100 |
| `outputFormat` | Формат файла | WAV |
| `quickTileEnabled` | Плитка в шторке | true |
| `vibrateOnStart` | Вибрация | true |
| `autoUpload` | Авто-загрузка | true |

## Транскрибация

Стерео файлы идеально подходят для Whisper:

```python
# Разделить каналы и транскрибировать отдельно
import whisper
from pydub import AudioSegment

audio = AudioSegment.from_wav("recording_stereo.wav")
left = audio.split_to_mono()[0]   # Твой голос
right = audio.split_to_mono()[1]  # Собеседник

# Транскрибация
model = whisper.load_model("base")
speaker1 = model.transcribe(left.export(format="wav"))
speaker2 = model.transcribe(right.export(format="wav"))
```

## Требования

- Android 7.0+ (API 24)
- ROOT доступ (Magisk или SuperSU)
- Разрешения: RECORD_AUDIO, FOREGROUND_SERVICE

## Известные ограничения

1. **VOICE_UPLINK/DOWNLINK** может не работать на некоторых устройствах даже с root
2. Fallback на MIC + REMOTE_SUBMIX если UPLINK/DOWNLINK недоступны
3. VoIP запись только ручная (автодетекция требует Accessibility Service)
