# START VOICE - Разработка голосовой функциональности

## ПЕРВЫМ ДЕЛОМ — Синхронизация

**Если читаешь этот файл ВТОРОЙ раз после git pull — ПРОПУСТИ этот блок и переходи к следующей секции!**

```bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
```

После git pull — ПЕРЕЧИТАЙ этот файл заново (StartVoice.md), начиная со следующей секции (пропустив этот блок синхронизации, чтобы не зациклиться).

---

## Дата и время последнего обновления
**5 декабря 2025, 15:30 (UTC+4)**

---

## Текущее состояние

### Android — Базовая запись (ГОТОВО):

1. **Запись GSM звонков**
   - `CallRecordingService.kt` - foreground service для записи
   - `CallReceiver.kt` - перехват входящих/исходящих звонков
   - `BootReceiver.kt` - автозапуск при загрузке устройства
   - `CallRecordingPreferences.kt` - настройки записи
   - `CallUploadWorker.kt` - фоновая загрузка на сервер

2. **UI в приложении**
   - Переключатель в настройках MainActivity
   - Статус записи (включена/выключена)
   - Автоматический запрос разрешений

3. **Формат файлов (базовый)**
   - Имя: `{callType}_{phoneNumber}_{timestamp}.m4a`
   - Моно, только микрофон или VOICE_COMMUNICATION

### Android — ROOT модуль (ГОТОВО, не интегрирован):

1. **Стерео запись с разделением каналов**
   - `root/RootChecker.kt` - проверка Magisk/SuperSU/su
   - `root/StereoCallRecorder.kt` - запись L=ты, R=собеседник
   - `root/RootRecordingPreferences.kt` - настройки модуля
   - `root/RecordingTileService.kt` - Quick Settings плитка для VoIP

2. **Возможности ROOT модуля**
   - Стерео WAV: Left = VOICE_UPLINK (ты), Right = VOICE_DOWNLINK (собеседник)
   - Ручная запись VoIP через плитку в шторке
   - Улучшенное качество GSM записи
   - Идеально для Whisper транскрибации

3. **Формат файлов (ROOT)**
   - Имя: `{callType}_{phoneNumber}_{timestamp}_stereo.wav`
   - Стерео 44100Hz 16bit

### n8n (НЕ ГОТОВО):

1. **Webhook endpoint**
   - URL: `https://n8n.n8nsrv.ru/webhook/voice-upload`
   - Метод: POST multipart/form-data
   - Поля: call_type, phone_number, timestamp, source, audio_file

2. **Транскрибация**
   - OpenAI Whisper API
   - Для стерео — раздельная транскрибация каналов

3. **Извлечение сущностей**
   - GPT-4 / Claude для анализа текста
   - Speaker 1 / Speaker 2 разметка

---

## Архитектура

```
[Android App]
     |
     |-- GSM звонок (авто) --> CallReceiver --> CallRecordingService
     |                                               |
     |-- VoIP звонок (вручную) --> RecordingTileService --> StereoCallRecorder
     |                                                           |
     |                                                      [Стерео WAV]
     |                                                      L: твой голос
     |                                                      R: собеседник
     |
     | POST multipart/form-data
     v
[n8n Webhook: voice-upload]
     |
     v
[Сохранение файла]
     |
     v
[Транскрибация (Whisper)]
     |-- Моно: обычная транскрибация
     |-- Стерео: раздельно L/R каналы --> Speaker 1 / Speaker 2
     |
     v
[Извлечение сущностей (LLM)]
     |
     v
[Сохранение в БД / Создание обращения]
```

---

## Файлы проекта

### Android — Базовый модуль:
```
app/src/main/java/com/eldoleado/app/callrecording/
├── CallRecordingPreferences.kt  # SharedPreferences
├── CallRecordingService.kt      # Foreground service (моно)
├── CallReceiver.kt              # BroadcastReceiver для GSM
├── BootReceiver.kt              # Автозапуск
└── CallUploadWorker.kt          # WorkManager загрузка
```

### Android — ROOT модуль (NEW):
```
app/src/main/java/com/eldoleado/app/callrecording/root/
├── RootChecker.kt               # Проверка root доступа
├── StereoCallRecorder.kt        # Стерео запись L/R
├── RootRecordingPreferences.kt  # Настройки ROOT модуля
├── RecordingTileService.kt      # Quick Settings плитка
└── README.md                    # Инструкция по интеграции
```

---

## Следующие шаги

### Приоритет 1 — Интеграция ROOT модуля:
- [ ] Добавить RecordingTileService в AndroidManifest.xml
- [ ] Интегрировать StereoCallRecorder в CallRecordingService
- [ ] Добавить UI настроек ROOT записи в приложение
- [ ] Тестирование на устройстве с root

### Приоритет 2 — n8n backend:
- [ ] Создать workflow для webhook voice-upload
- [ ] Настроить сохранение аудио файлов
- [ ] Подключить OpenAI Whisper API
- [ ] Для стерео: разделить каналы перед транскрибацией
- [ ] Создать промпт для извлечения сущностей
- [ ] Связать с системой обращений

---

## Разрешения Android

```xml
<!-- Базовые -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_CALL_LOG" />
<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
<uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MICROPHONE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_PHONE_CALL" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- Для Quick Settings Tile -->
<uses-permission android:name="android.permission.VIBRATE" />
```

---

## Endpoint для n8n

### POST /webhook/voice-upload

**Request:**
```
Content-Type: multipart/form-data

Fields:
- call_type: string ("incoming" | "outgoing" | "voip")
- phone_number: string ("+79001234567" или "unknown")
- timestamp: string ("20251205_143022")
- source: string ("eldoleado_android")
- is_stereo: boolean (true если стерео)
- audio_file: file (audio/wav для стерео, audio/mp4 для моно)
```

**Response:**
```json
{
  "success": true,
  "transcription_id": "uuid",
  "message": "File received and queued for processing"
}
```

---

## Транскрибация стерео

```python
# В n8n через Python node или отдельный сервис
from pydub import AudioSegment
import whisper

# Разделить каналы
audio = AudioSegment.from_wav("recording_stereo.wav")
left_channel = audio.split_to_mono()[0]   # Твой голос
right_channel = audio.split_to_mono()[1]  # Собеседник

# Транскрибация
model = whisper.load_model("base")
speaker1_text = model.transcribe(left_channel)["text"]
speaker2_text = model.transcribe(right_channel)["text"]

# Результат
result = {
    "speaker_1": speaker1_text,  # Оператор
    "speaker_2": speaker2_text,  # Клиент
    "full_text": f"Оператор: {speaker1_text}\nКлиент: {speaker2_text}"
}
```

---

## Команды

### Тестирование записи:
```bash
# Логи базовой записи
adb logcat | grep -i "CallRecording"

# Логи ROOT модуля
adb logcat | grep -i "StereoCallRecorder\|RootChecker\|RecordingTile"

# Проверка файлов
adb shell ls -la /data/data/com.eldoleado.app/files/call_recordings/
```

### Сборка:
```bash
cd "C:/Users/User/Documents/Eldoleado"
./gradlew.bat assembleDebug
```

---

## Ссылки

- **Webhook URL**: https://n8n.n8nsrv.ru/webhook/voice-upload
- **OpenAI Whisper**: https://platform.openai.com/docs/guides/speech-to-text
- **ROOT модуль README**: app/src/main/java/com/eldoleado/app/callrecording/root/README.md
