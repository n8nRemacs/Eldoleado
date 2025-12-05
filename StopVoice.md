# STOP VOICE - Чеклист завершения сессии

> **ВАЖНО:** При обновлении этого файла ВСЕГДА указывай дату И время в формате: `DD месяц YYYY, HH:MM (UTC+4)`

---

## ОБЯЗАТЕЛЬНО перед закрытием сессии:

### 1. Обновить StartVoice.md

**ВАЖНО:** В начало StartVoice.md ВСЕГДА добавляй блок синхронизации:

```markdown
## ПЕРВЫМ ДЕЛОМ — Синхронизация

**Если читаешь этот файл ВТОРОЙ раз после git pull — ПРОПУСТИ этот блок и переходи к следующей секции!**

\`\`\`bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
\`\`\`

После git pull — ПЕРЕЧИТАЙ этот файл заново (StartVoice.md), начиная со следующей секции (пропустив этот блок синхронизации, чтобы не зациклиться).

---
```

Затем обнови секцию "Текущее состояние" — добавь всё что сделано.

### 2. Git sync
```bash
git add -A && git commit -m "Voice session: краткое описание" && git push
```

---

## Последняя сессия: 5 декабря 2025, 15:30 (UTC+4)

## Что сделано в этой сессии:

### ROOT модуль записи звонков (NEW):

1. **RootChecker.kt** — проверка root доступа
   - Поддержка Magisk, SuperSU, generic su
   - Кэширование результата
   - Выполнение команд с root

2. **StereoCallRecorder.kt** — стерео запись
   - Left канал = VOICE_UPLINK (твой голос)
   - Right канал = VOICE_DOWNLINK (голос собеседника)
   - Формат: WAV 44100Hz 16bit stereo
   - Fallback на MIC + REMOTE_SUBMIX

3. **RootRecordingPreferences.kt** — настройки модуля
   - Режимы записи (stereo split, stereo mixed, mono)
   - Качество (sample rate, bit depth, format)
   - Поведение (вибрация, уведомления)
   - Хранение (лимит, автоудаление, автозагрузка)

4. **RecordingTileService.kt** — Quick Settings плитка
   - Кнопка в шторке уведомлений
   - Ручной старт/стоп VoIP записи
   - Вибрация при начале/конце

5. **README.md** — инструкция по интеграции
   - Изменения в AndroidManifest
   - Примеры кода интеграции
   - Документация API

### Файлы созданы:
```
app/src/main/java/com/eldoleado/app/callrecording/root/
├── RootChecker.kt
├── StereoCallRecorder.kt
├── RootRecordingPreferences.kt
├── RecordingTileService.kt
└── README.md
```

---

## Что НЕ сделано:

### Android:

1. [ ] Интеграция ROOT модуля в основное приложение
2. [ ] Добавление RecordingTileService в AndroidManifest.xml
3. [ ] UI настроек ROOT записи
4. [ ] Тестирование на устройстве с root

### n8n backend:

1. [ ] Webhook `/webhook/voice-upload` — не создан
2. [ ] Сохранение аудио файлов на сервере
3. [ ] Интеграция с OpenAI Whisper для транскрибации
4. [ ] Разделение стерео каналов для Speaker 1/2
5. [ ] Промпт для извлечения сущностей
6. [ ] Связь с системой обращений (appeals)

---

## Следующая сессия — план:

### Вариант A: Интеграция ROOT модуля

1. Добавить в AndroidManifest.xml:
```xml
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

2. Интегрировать StereoCallRecorder в CallRecordingService

3. Добавить UI настроек:
   - Переключатель "Улучшенная запись (Root)"
   - Выбор режима стерео
   - Показывать только если есть root

4. Собрать и протестировать на устройстве с root

### Вариант B: n8n backend

1. Создать workflow voice-upload:
```
Webhook --> Save File --> Whisper API --> Extract Entities --> Save to DB
```

2. Для стерео файлов — разделить каналы:
```python
audio.split_to_mono()[0]  # Speaker 1
audio.split_to_mono()[1]  # Speaker 2
```

3. Промпт для извлечения:
```
Оператор: {speaker_1_text}
Клиент: {speaker_2_text}

Извлеки: тип обращения, бренд, модель, проблема, срочность
```

---

## Важные файлы:

### Android — Базовый модуль:
```
app/src/main/java/com/eldoleado/app/callrecording/
├── CallRecordingPreferences.kt
├── CallRecordingService.kt
├── CallReceiver.kt
├── BootReceiver.kt
└── CallUploadWorker.kt
```

### Android — ROOT модуль (NEW):
```
app/src/main/java/com/eldoleado/app/callrecording/root/
├── RootChecker.kt
├── StereoCallRecorder.kt
├── RootRecordingPreferences.kt
├── RecordingTileService.kt
└── README.md
```

### n8n (создать):
```
workflows_to_import/new/
└── BAT_Voice_Upload_Handler.json
```

---

## Сравнение качества записи

| Параметр | Без Root | С Root |
|----------|----------|--------|
| GSM качество | Только микрофон | Стерео L/R |
| VoIP запись | ❌ | ✅ (ручная) |
| Формат | M4A моно | WAV стерео |
| Разделение голосов | ❌ | ✅ |
| Транскрибация | Средняя | Отличная |
| Speaker diarization | Невозможна | Автоматическая |

---

## Синхронизация

**Перед завершением:**
```bash
git add -A && git commit -m "Voice: ROOT stereo recording module" && git push
```

---

## Контакты и ссылки:

- **Webhook URL**: https://n8n.n8nsrv.ru/webhook/voice-upload
- **n8n Dashboard**: https://n8n.n8nsrv.ru
- **OpenAI API**: https://platform.openai.com
- **ROOT модуль**: app/src/main/java/com/eldoleado/app/callrecording/root/README.md
