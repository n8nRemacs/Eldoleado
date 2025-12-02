# STOP VOICE - Статус голосовой разработки

## Дата и время: 2 декабря 2025, 23:45 (UTC+4)

> **ВАЖНО:** При обновлении этого файла ВСЕГДА указывай дату И время в формате: `DD месяц YYYY, HH:MM (UTC+4)`

---

## Что сделано в этой сессии:

### Android приложение:

1. **Создана система записи звонков**
   - `CallRecordingService.kt` - foreground service для записи через MediaRecorder
   - `CallReceiver.kt` - BroadcastReceiver для перехвата звонков
   - `BootReceiver.kt` - автозапуск сервиса при загрузке устройства
   - `CallRecordingPreferences.kt` - хранение настроек
   - `CallUploadWorker.kt` - фоновая загрузка через WorkManager

2. **UI интеграция**
   - Добавлен переключатель записи в настройках MainActivity
   - Статус записи отображается текстом
   - Автоматический запрос разрешений при включении

3. **Конфигурация**
   - Обновлён AndroidManifest.xml с разрешениями
   - Добавлен цвет green_status в colors.xml

4. **Тестирование**
   - Приложение собрано и установлено
   - UI работает, переключатель функционирует

---

## Что НЕ сделано:

### n8n backend:

1. [ ] Webhook `/webhook/voice-upload` - не создан
2. [ ] Сохранение аудио файлов на сервере
3. [ ] Интеграция с OpenAI Whisper для транскрибации
4. [ ] Промпт для извлечения сущностей (бренд, модель, проблема)
5. [ ] Связь с системой обращений (appeals)

---

## Следующая сессия - план:

### 1. Создать n8n workflow "Voice Upload Handler"
```
Webhook (voice-upload)
    |
    v
Сохранить файл на диск/S3
    |
    v
OpenAI Whisper API -> текст
    |
    v
GPT-4/Claude -> извлечь сущности
    |
    v
Записать в БД / создать обращение
```

### 2. Настроить промпты для извлечения:
- Номер телефона клиента
- Тип обращения (ремонт, консультация, покупка)
- Бренд устройства
- Модель устройства
- Описание проблемы
- Срочность

### 3. Тестирование полного цикла:
- Записать тестовый звонок
- Проверить загрузку на сервер
- Проверить транскрибацию
- Проверить извлечение сущностей

---

## Важные файлы:

### Android (готово):
```
app/src/main/java/com/batterycrm/app/callrecording/
├── CallRecordingPreferences.kt
├── CallRecordingService.kt
├── CallReceiver.kt
├── BootReceiver.kt
└── CallUploadWorker.kt
```

### n8n (создать):
```
n8n_workflows/
└── Voice/
    └── BAT_Voice_Upload_Handler.json
```

---

## Конфигурация для n8n workflow:

### Webhook параметры:
- **URL**: `/webhook/voice-upload`
- **Method**: POST
- **Binary Property**: audio_file
- **Response**: JSON

### OpenAI Whisper:
- **Model**: whisper-1
- **Response format**: json
- **Language**: ru (Russian)

### Промпт для извлечения сущностей:
```
Проанализируй транскрипцию телефонного разговора и извлеки:
1. Имя клиента (если упоминается)
2. Тип обращения: ремонт / консультация / покупка / другое
3. Бренд устройства (iPhone, Samsung, Xiaomi и т.д.)
4. Модель устройства
5. Описание проблемы
6. Срочность: высокая / средняя / низкая

Верни JSON:
{
  "client_name": "",
  "appeal_type": "",
  "device_brand": "",
  "device_model": "",
  "problem_description": "",
  "urgency": ""
}
```

---

## Синхронизация

**Перед завершением:**
```bash
scripts\git-sync.bat
```

---

## Контакты и ссылки:

- **Webhook URL**: https://n8n.n8nsrv.ru/webhook/voice-upload
- **n8n Dashboard**: https://n8n.n8nsrv.ru
- **OpenAI API**: https://platform.openai.com
- **GitHub**: https://github.com/n8nRemacs/batterycrm
