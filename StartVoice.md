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
**2 декабря 2025, 23:45 (UTC+4)**

---

## Текущее состояние

### Android (ГОТОВО):

1. **Запись звонков**
   - CallRecordingService.kt - foreground service для записи
   - CallReceiver.kt - перехват входящих/исходящих звонков
   - BootReceiver.kt - автозапуск при загрузке устройства
   - CallRecordingPreferences.kt - настройки записи
   - CallUploadWorker.kt - фоновая загрузка на сервер

2. **UI в приложении**
   - Переключатель в настройках MainActivity
   - Статус записи (включена/выключена)
   - Автоматический запрос разрешений

3. **Формат файлов**
   - Имя: `{callType}_{phoneNumber}_{timestamp}.m4a`
   - callType: "incoming" или "outgoing"
   - Пример: `incoming_+79001234567_20251202_143022.m4a`

### n8n (НЕ ГОТОВО):

1. **Webhook endpoint**
   - URL: `https://n8n.n8nsrv.ru/webhook/voice-upload`
   - Метод: POST multipart/form-data
   - Поля: call_type, phone_number, timestamp, source, audio_file

2. **Транскрибация**
   - OpenAI Whisper API
   - Или локальный Whisper

3. **Извлечение сущностей**
   - GPT-4 / Claude для анализа текста
   - Настраиваемые промпты для разных типов обращений

---

## Архитектура

```
[Android App]
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
     |
     v
[Извлечение сущностей (LLM)]
     |
     v
[Сохранение в БД / Создание обращения]
```

---

## Файлы проекта

### Android (app/src/main/java/com/batterycrm/app/callrecording/):
- `CallRecordingPreferences.kt` - SharedPreferences для настроек
- `CallRecordingService.kt` - Foreground service с MediaRecorder
- `CallReceiver.kt` - BroadcastReceiver для PHONE_STATE
- `BootReceiver.kt` - Автозапуск после перезагрузки
- `CallUploadWorker.kt` - WorkManager для загрузки файлов

### Конфигурация:
- `AndroidManifest.xml` - разрешения и регистрация компонентов

---

## Разрешения Android

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_CALL_LOG" />
<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
<uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MICROPHONE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_PHONE_CALL" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

---

## Endpoint для n8n

### POST /webhook/voice-upload

**Request:**
```
Content-Type: multipart/form-data

Fields:
- call_type: string ("incoming" | "outgoing")
- phone_number: string ("+79001234567")
- timestamp: string ("20251202_143022")
- source: string ("batterycrm_android")
- audio_file: file (audio/mp4, .m4a)
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

## Следующие шаги

1. [ ] Создать n8n workflow для webhook voice-upload
2. [ ] Настроить сохранение аудио файлов
3. [ ] Подключить OpenAI Whisper API
4. [ ] Создать промпт для извлечения сущностей
5. [ ] Связать с системой обращений

---

## Ссылки

- **Webhook URL**: https://n8n.n8nsrv.ru/webhook/voice-upload
- **OpenAI Whisper**: https://platform.openai.com/docs/guides/speech-to-text
- **GitHub**: https://github.com/n8nRemacs/batterycrm

---

## Команды

### Тестирование записи:
1. Включить переключатель в настройках приложения
2. Совершить тестовый звонок
3. Проверить логи: `adb logcat | grep -i "CallRecording"`

### Проверка файлов:
```bash
adb shell ls /data/data/com.batterycrm.app/files/recordings/
```
