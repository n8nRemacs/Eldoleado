# BatteryCRM - Android App: Техническая спецификация

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация
**Минимальная версия Android:** 10 (API 29)
**Целевая версия:** Android 14 (API 34)

---

## Содержание

1. [Обзор приложения](#обзор-приложения)
2. [Архитектура](#архитектура)
3. [Технологический стек](#технологический-стек)
4. [Экраны и навигация](#экраны-и-навигация)
5. [Функциональность](#функциональность)
6. [Голосовые ответы оператора](#голосовые-ответы-оператора)
7. [Запись телефонных звонков](#запись-телефонных-звонков)
8. [QR-сканирование](#qr-сканирование)
9. [Push-уведомления](#push-уведомления)
10. [Multi-device sessions](#multi-device-sessions)
11. [Оффлайн режим](#оффлайн-режим)
12. [Безопасность](#безопасность)

---

## Обзор приложения

### Назначение

**BatteryCRM Android App** — мобильное приложение для операторов сервисных центров, обеспечивающее:
- Обработку входящих обращений клиентов из всех каналов
- Управление заявками и воронкой продаж
- Голосовые ответы с автоматическим AI оформлением
- Запись и транскрибацию телефонных звонков
- Сканирование QR-кодов для приёма устройств
- Push-уведомления о новых сообщениях
- Работу в режиме 1 mobile + 1 desktop одновременно

### Целевая аудитория

- **Операторы контакт-центров** — обработка обращений клиентов
- **Мастера** — приём устройств, QR-сканирование
- **Менеджеры** — мониторинг заявок в режиме мобильности

### Ключевые особенности

1. **Mobile-First UI** — оптимизирован для работы одной рукой
2. **Голосовой ввод** — быстрые ответы через Android SpeechRecognizer
3. **Автоматическая запись звонков** — InCallService API без участия провайдера
4. **QR-сканирование** — мгновенное открытие заявки через CameraX
5. **Оффлайн режим** — чтение заявок без интернета
6. **Multi-device** — синхронизация с Desktop приложением

---

## Архитектура

### Общая архитектура

```
┌────────────────────────────────────────────────┐
│                UI LAYER                        │
│  Jetpack Compose (Material 3 Design)           │
│  - Screens, Components, Navigation             │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│              PRESENTATION LAYER                │
│  ViewModels (AAC ViewModel)                    │
│  - State Management (StateFlow, SharedFlow)    │
│  - Business Logic                              │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│               DOMAIN LAYER                     │
│  Use Cases (Interactors)                       │
│  - GetAppealsUseCase                           │
│  - SendMessageUseCase                          │
│  - FormatVoiceResponseUseCase                  │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│                DATA LAYER                      │
│  Repositories (Single Source of Truth)         │
│  - AppealsRepository                           │
│  - MessagesRepository                          │
│  - VoiceRepository                             │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│             DATA SOURCES                       │
│  - Remote: Retrofit (REST API)                 │
│  - Local: Room Database (SQLite)               │
│  - Cache: DataStore (Preferences)              │
└────────────────────────────────────────────────┘
```

### Принципы архитектуры

1. **Clean Architecture** — разделение на слои (UI, Domain, Data)
2. **MVVM Pattern** — ViewModel для управления состоянием
3. **Single Source of Truth** — Repository как единый источник данных
4. **Unidirectional Data Flow** — данные текут от Repository → ViewModel → UI
5. **Dependency Injection** — Hilt для внедрения зависимостей

---

## Технологический стек

### Core

| Технология | Версия | Назначение |
|:-----------|:-------|:-----------|
| Kotlin | 1.9+ | Основной язык разработки |
| Jetpack Compose | 1.5+ | Declarative UI |
| Material 3 | 1.1+ | Design System |
| Coroutines | 1.7+ | Асинхронность |
| Flow | 1.7+ | Reactive Streams |

### Android Jetpack

| Компонент | Назначение |
|:----------|:-----------|
| ViewModel | Управление состоянием UI |
| Navigation | Навигация между экранами |
| Room | Локальная БД (оффлайн) |
| DataStore | Хранение настроек |
| WorkManager | Фоновые задачи |
| Hilt | Dependency Injection |

### Networking

| Библиотека | Назначение |
|:-----------|:-----------|
| Retrofit 2 | HTTP клиент |
| OkHttp | Низкоуровневый HTTP |
| Gson | JSON сериализация |
| Coil | Загрузка изображений |

### Специализированные

| Библиотека | Назначение |
|:-----------|:-----------|
| CameraX | QR-сканирование, фото/видео |
| ML Kit Barcode | Распознавание QR-кодов |
| Firebase Cloud Messaging | Push-уведомления |
| ExoPlayer | Воспроизведение аудио/видео |
| Android SpeechRecognizer | Голосовой ввод (встроенный) |
| InCallService | Запись телефонных звонков |

### Testing

| Библиотека | Назначение |
|:-----------|:-----------|
| JUnit 5 | Unit тесты |
| Mockk | Mocking |
| Turbine | Flow тестирование |
| Espresso | UI тесты |
| Compose Test | Compose UI тесты |

---

## Экраны и навигация

### Навигационная структура

```
┌─────────────────────────────────────────────┐
│            AUTH FLOW                        │
│  - LoginScreen                              │
│  - SplashScreen                             │
└─────────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────────┐
│            MAIN FLOW                        │
│  BottomNavigationBar:                       │
│    - Appeals (Заявки)                       │
│    - QR Scanner (Сканер)                    │
│    - Profile (Профиль)                      │
└─────────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────────┐
│         APPEAL DETAIL FLOW                  │
│  - AppealDetailScreen                       │
│  - MessagesScreen                           │
│  - EditDevicesScreen                        │
│  - PricingScreen                            │
└─────────────────────────────────────────────┘
```

### Экраны приложения

#### 1. SplashScreen (Экран загрузки)

**Назначение:** Проверка авторизации и переход на нужный экран

**Логика:**
1. Проверка наличия JWT токена в DataStore
2. Если токен валиден → переход на AppealsListScreen
3. Если токен отсутствует/невалиден → переход на LoginScreen

**Длительность:** максимум 2 секунды

---

#### 2. LoginScreen (Экран авторизации)

**Элементы UI:**
- Поле ввода email
- Поле ввода пароля (с показом/скрытием)
- Кнопка "Войти"
- Loader во время авторизации
- Обработка ошибок (неверный email/пароль)

**Логика:**
```kotlin
fun login(email: String, password: String) {
    viewModelScope.launch {
        _state.value = LoginState.Loading

        val result = authRepository.login(
            email = email,
            password = password,
            deviceType = "mobile",
            fcmToken = getFCMToken(),
            deviceInfo = getDeviceInfo()
        )

        when (result) {
            is Success -> {
                saveToken(result.token)
                _state.value = LoginState.Success
                navigateToAppeals()
            }
            is Error -> {
                _state.value = LoginState.Error(result.message)
            }
        }
    }
}
```

**API:** `POST /auth/login`

---

#### 3. AppealsListScreen (Список заявок)

**Элементы UI:**
- TopAppBar с фильтрами и поиском
- Список заявок (LazyColumn)
- Карточка заявки:
  - Имя клиента
  - Канал (иконка)
  - Устройство (бренд + модель)
  - Неисправность
  - Этап воронки (цветной бадж)
  - Счётчик непрочитанных сообщений
  - Время последнего сообщения
- Pull-to-refresh
- Fab кнопка для быстрого QR-сканирования

**Фильтры:**
- По статусу (новые, в работе, завершённые)
- По этапу воронки
- Только мои заявки
- Поиск по имени/телефону

**Логика:**
```kotlin
@Composable
fun AppealsListScreen(
    viewModel: AppealsViewModel = hiltViewModel()
) {
    val appeals by viewModel.appeals.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = { AppealsTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToQRScanner() }) {
                Icon(Icons.Default.QrCodeScanner, "Scan QR")
            }
        }
    ) {
        LazyColumn {
            items(appeals) { appeal ->
                AppealCard(
                    appeal = appeal,
                    onClick = { navigateToAppealDetail(appeal.id) }
                )
            }
        }
    }
}
```

**API:** `GET /api/operator/appeals`

---

#### 4. AppealDetailScreen (Детали заявки)

**Элементы UI:**
- TopAppBar с названием клиента и кнопкой "Редактировать"
- Табы:
  - **Сообщения** — история переписки
  - **Детали** — информация о заявке
  - **Устройства** — список устройств и неисправностей (мультизаявка)
- Floating панель с кнопками:
  - Текстовый ответ
  - Голосовой ответ (микрофон)
  - Прикрепить медиа (фото/видео)
  - Сменить этап воронки

**Tab 1: Сообщения**

```kotlin
@Composable
fun MessagesTab(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onVoiceRecord: () -> Unit
) {
    LazyColumn(reverseLayout = true) {
        items(messages) { message ->
            when (message.type) {
                MessageType.CLIENT -> ClientMessageBubble(message)
                MessageType.OPERATOR -> OperatorMessageBubble(message)
                MessageType.SYSTEM -> SystemMessageBubble(message)
            }
        }
    }

    MessageInputBar(
        onSendText = onSendMessage,
        onVoiceRecord = onVoiceRecord,
        onAttachMedia = { /* ... */ }
    )
}
```

**Tab 2: Детали**

Информация о заявке:
- Канал коммуникации
- Тип обращения (ремонт/консультация/продажа)
- Этап воронки (с кнопкой смены этапа)
- UTM метки (если есть)
- Дата создания, дата обновления
- Назначенный оператор
- Комментарии оператора (редактируемое поле)

**Tab 3: Устройства**

Список устройств в мультизаявке:
```
┌─────────────────────────────────┐
│ Устройство 1: Apple iPhone 14   │
│   - Замена батареи (3500₽)      │
│   - Замена дисплея (7500₽)      │
│ [Редактировать]                  │
└─────────────────────────────────┘
┌─────────────────────────────────┐
│ Устройство 2: Samsung Galaxy S23│
│   - Замена порта зарядки (2500₽)│
│ [Редактировать]                  │
└─────────────────────────────────┘

[+ Добавить устройство]
```

**API:** `GET /api/operator/appeals/:appeal_id`

---

#### 5. EditDevicesScreen (Редактирование устройств)

**Элементы UI:**
- Выбор бренда (автокомплит)
- Выбор модели (автокомплит)
- Цвет устройства
- Состояние устройства
- Список неисправностей:
  - Выбор типа ремонта (автокомплит)
  - Описание проблемы
  - Симптомы (множественный выбор)
  - Предварительная цена
- Кнопка "Добавить неисправность"
- Кнопка "Сохранить"

**API:** `PUT /api/operator/appeals/:appeal_id/devices`

---

#### 6. PricingScreen (Расчёт стоимости)

**Элементы UI:**
- Информация об устройстве и неисправности
- Список предложений из прайсов:
  ```
  ┌──────────────────────────────────┐
  │ Поставщик А                      │
  │ Аккумулятор iPhone 14 (оригинал) │
  │ Цена детали: 2500₽               │
  │ Рекомендуемая цена: 3500₽        │
  │ Наценка: 40%                     │
  │ В наличии: Да                    │
  │ [Выбрать]                        │
  └──────────────────────────────────┘
  ```
- Кнопка "Вручную указать цену"
- Кнопка "Отправить цену клиенту"

**Логика:**
1. Получение предложений из API
2. Оператор выбирает вариант
3. Возможность редактирования финальной цены
4. Отправка цены клиенту автоматически формирует сообщение

**API:** `GET /api/operator/pricing/suggestions`

---

#### 7. QRScannerScreen (Сканер QR-кодов)

**Элементы UI:**
- Fullscreen CameraX Preview
- Overlay с рамкой для QR-кода
- Подсказка: "Наведите камеру на QR-код"
- Кнопка "Закрыть"
- Вспышка (toggle)

**Логика:**
```kotlin
@Composable
fun QRScannerScreen(
    viewModel: QRScannerViewModel = hiltViewModel()
) {
    val scanResult by viewModel.scanResult.collectAsState()

    when (scanResult) {
        is ScanResult.Success -> {
            // Автоматический переход на AppealDetailScreen
            navigateToAppealDetail(scanResult.appealId)
        }
        is ScanResult.Error -> {
            // Показать ошибку (QR истёк, не найден и т.д.)
            ErrorDialog(scanResult.message)
        }
        is ScanResult.Scanning -> {
            CameraPreview(
                onQRDetected = { qrCode ->
                    viewModel.processQR(qrCode)
                }
            )
        }
    }
}
```

**API:** `POST /api/operator/qr/scan`

---

#### 8. ProfileScreen (Профиль)

**Элементы UI:**
- Информация об операторе:
  - Имя
  - Email
  - Роль
  - Тенант (сервисный центр)
- Активные сессии:
  - Mobile (текущая)
  - Desktop (если подключён)
- Настройки:
  - Уведомления (вкл/выкл)
  - Звук уведомлений
  - Вибрация
  - Автоматическая запись звонков
  - Голосовые ответы (стиль оформления)
- Кнопка "Выйти"

**Логика выхода:**
```kotlin
fun logout() {
    viewModelScope.launch {
        authRepository.logout()
        clearCache()
        clearToken()
        navigateToLogin()
    }
}
```

**API:** `POST /auth/logout`

---

## Функциональность

### 1. Отправка текстового сообщения

**Flow:**
1. Оператор вводит текст в поле ввода
2. Нажимает кнопку "Отправить"
3. Сообщение добавляется в UI (optimistic update)
4. API запрос отправляется в фоне
5. При успехе — обновление статуса сообщения
6. При ошибке — показ ошибки и возможность повтора

**Код:**
```kotlin
fun sendMessage(appealId: String, text: String) {
    viewModelScope.launch {
        // Optimistic update
        val tempMessage = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            type = MessageType.OPERATOR,
            status = MessageStatus.SENDING,
            timestamp = Clock.System.now()
        )
        _messages.value = _messages.value + tempMessage

        // API request
        when (val result = messagesRepository.sendMessage(appealId, text)) {
            is Success -> {
                updateMessageStatus(tempMessage.id, MessageStatus.SENT)
            }
            is Error -> {
                updateMessageStatus(tempMessage.id, MessageStatus.FAILED)
                _error.value = result.message
            }
        }
    }
}
```

---

### 2. Прикрепление медиа (фото/видео)

**Flow:**
1. Оператор нажимает кнопку "Прикрепить"
2. Выбор источника:
   - Камера (сделать фото/видео)
   - Галерея (выбрать из галереи)
3. Загрузка файла на сервер
4. Отправка сообщения с ссылкой на медиа

**Код:**
```kotlin
fun attachMedia(appealId: String, uri: Uri, type: MediaType) {
    viewModelScope.launch {
        _uploadProgress.value = 0f

        val result = mediaRepository.uploadMedia(
            appealId = appealId,
            uri = uri,
            type = type,
            onProgress = { progress ->
                _uploadProgress.value = progress
            }
        )

        when (result) {
            is Success -> {
                sendMessage(appealId, result.mediaUrl)
            }
            is Error -> {
                _error.value = result.message
            }
        }
    }
}
```

**API:** `POST /api/operator/media/upload`

---

### 3. Изменение этапа воронки

**UI:**
- BottomSheet с выбором этапа
- Опциональное поле комментария
- Кнопка "Сохранить"

**Этапы:**
1. New Empty
2. Сбор информации
3. Информация собрана
4. Цена отправлена
5. Клиент подтвердил
6. QR-код отправлен
7. Клиент пришёл
8. Ремонт выполнен
9. Отказ / невыкуп / спам

**Код:**
```kotlin
fun changeStage(appealId: String, newStage: AppealStage, comment: String?) {
    viewModelScope.launch {
        when (val result = appealsRepository.changeStage(appealId, newStage, comment)) {
            is Success -> {
                _appeal.value = _appeal.value?.copy(stage = newStage)
                _success.value = "Этап изменён"
            }
            is Error -> {
                _error.value = result.message
            }
        }
    }
}
```

**API:** `POST /api/operator/appeals/:appeal_id/change-stage`

---

## Голосовые ответы оператора

### Архитектура

```
Оператор нажимает микрофон
         ↓
Android SpeechRecognizer (встроенный)
         ↓
Распознавание речи → текст
         ↓
API: /api/operator/voice/format-response
         ↓
AI оформление текста (Claude)
         ↓
Отображение отформатированного текста
         ↓
Оператор проверяет/редактирует
         ↓
Отправка клиенту
```

### Реализация

**1. Запрос разрешений**

```kotlin
// AndroidManifest.xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />

// Runtime permission
if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO)
    != PackageManager.PERMISSION_GRANTED) {
    requestPermissions(arrayOf(RECORD_AUDIO), REQUEST_CODE)
}
```

**2. Инициализация SpeechRecognizer**

```kotlin
class VoiceRecognizer(private val context: Context) {

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    fun startListening(onResult: (String) -> Unit, onError: (String) -> Unit) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(
                    SpeechRecognizer.RESULTS_RECOGNITION
                )
                matches?.firstOrNull()?.let { text ->
                    onResult(text)
                }
            }

            override fun onError(error: Int) {
                onError(getErrorMessage(error))
            }

            // Другие методы...
        })

        speechRecognizer.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer.stopListening()
    }
}
```

**3. ViewModel для голосового ввода**

```kotlin
@HiltViewModel
class VoiceResponseViewModel @Inject constructor(
    private val voiceRecognizer: VoiceRecognizer,
    private val voiceRepository: VoiceRepository
) : ViewModel() {

    private val _state = MutableStateFlow<VoiceResponseState>(VoiceResponseState.Idle)
    val state: StateFlow<VoiceResponseState> = _state.asStateFlow()

    fun startRecording(appealId: String, context: VoiceContext) {
        _state.value = VoiceResponseState.Recording

        voiceRecognizer.startListening(
            onResult = { rawText ->
                formatVoiceResponse(appealId, rawText, context)
            },
            onError = { error ->
                _state.value = VoiceResponseState.Error(error)
            }
        )
    }

    private fun formatVoiceResponse(
        appealId: String,
        rawText: String,
        context: VoiceContext
    ) {
        viewModelScope.launch {
            _state.value = VoiceResponseState.Formatting

            when (val result = voiceRepository.formatVoiceResponse(
                appealId = appealId,
                rawText = rawText,
                context = context
            )) {
                is Success -> {
                    _state.value = VoiceResponseState.Formatted(
                        originalText = rawText,
                        formattedText = result.formattedText
                    )
                }
                is Error -> {
                    _state.value = VoiceResponseState.Error(result.message)
                }
            }
        }
    }

    fun stopRecording() {
        voiceRecognizer.stopListening()
    }
}

sealed class VoiceResponseState {
    object Idle : VoiceResponseState()
    object Recording : VoiceResponseState()
    object Formatting : VoiceResponseState()
    data class Formatted(
        val originalText: String,
        val formattedText: String
    ) : VoiceResponseState()
    data class Error(val message: String) : VoiceResponseState()
}
```

**4. UI компонент**

```kotlin
@Composable
fun VoiceRecordButton(
    appealId: String,
    context: VoiceContext,
    onFormattedText: (String) -> Unit,
    viewModel: VoiceResponseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is VoiceResponseState.Idle -> {
            IconButton(onClick = {
                viewModel.startRecording(appealId, context)
            }) {
                Icon(Icons.Default.Mic, "Record voice")
            }
        }

        is VoiceResponseState.Recording -> {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clickable { viewModel.stopRecording() }
            ) {
                CircularProgressIndicator()
                Icon(Icons.Default.Stop, "Stop recording")
            }
        }

        is VoiceResponseState.Formatting -> {
            CircularProgressIndicator()
        }

        is VoiceResponseState.Formatted -> {
            // Показать диалог с отформатированным текстом
            FormattedTextDialog(
                originalText = currentState.originalText,
                formattedText = currentState.formattedText,
                onConfirm = { editedText ->
                    onFormattedText(editedText)
                },
                onCancel = { /* Закрыть диалог */ }
            )
        }

        is VoiceResponseState.Error -> {
            // Показать ошибку
            ErrorSnackbar(currentState.message)
        }
    }
}
```

**API:** `POST /api/operator/voice/format-response`

---

## Запись телефонных звонков

### Архитектура

```
Входящий/исходящий звонок
         ↓
InCallService (Android 10+)
         ↓
Автоматическая запись (если разрешено)
         ↓
Сохранение файла локально
         ↓
После завершения звонка:
  - Загрузка на сервер
  - Транскрибация через Whisper
  - Извлечение сущностей через AI
  - Обновление заявки
```

### Реализация

**1. Разрешения**

```kotlin
// AndroidManifest.xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_CALL_LOG" />

<service
    android:name=".services.CallRecordingService"
    android:permission="android.permission.BIND_INCALL_SERVICE">
    <intent-filter>
        <action android:name="android.telecom.InCallService"/>
    </intent-filter>
</service>
```

**2. InCallService реализация**

```kotlin
class CallRecordingService : InCallService() {

    private var mediaRecorder: MediaRecorder? = null
    private var currentCall: Call? = null
    private var recordingFile: File? = null

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        currentCall = call

        call.registerCallback(object : Call.Callback() {
            override fun onStateChanged(call: Call, state: Int) {
                when (state) {
                    Call.STATE_ACTIVE -> startRecording(call)
                    Call.STATE_DISCONNECTED -> stopRecording(call)
                }
            }
        })
    }

    private fun startRecording(call: Call) {
        // Проверка настроек (включена ли автозапись)
        if (!isAutoRecordingEnabled()) return

        try {
            val outputFile = File(
                cacheDir,
                "call_${System.currentTimeMillis()}.m4a"
            )
            recordingFile = outputFile

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFile.absolutePath)
                prepare()
                start()
            }

            Log.d(TAG, "Recording started: ${outputFile.name}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording", e)
        }
    }

    private fun stopRecording(call: Call) {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null

            recordingFile?.let { file ->
                // Получение информации о звонке
                val phoneNumber = call.details.handle.schemeSpecificPart
                val direction = if (call.details.callDirection == Call.Details.DIRECTION_INCOMING) {
                    "incoming"
                } else {
                    "outgoing"
                }

                // Отправка на обработку
                uploadRecording(file, phoneNumber, direction)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop recording", e)
        }
    }

    private fun uploadRecording(file: File, phoneNumber: String, direction: String) {
        // Поиск заявки по номеру телефона
        val appealId = findAppealByPhoneNumber(phoneNumber)

        if (appealId != null) {
            // Запуск WorkManager для загрузки в фоне
            val uploadWork = OneTimeWorkRequestBuilder<UploadRecordingWorker>()
                .setInputData(workDataOf(
                    "file_path" to file.absolutePath,
                    "appeal_id" to appealId,
                    "phone_number" to phoneNumber,
                    "direction" to direction
                ))
                .build()

            WorkManager.getInstance(this).enqueue(uploadWork)
        } else {
            Log.w(TAG, "No appeal found for phone number: $phoneNumber")
        }
    }
}
```

**3. WorkManager для загрузки**

```kotlin
class UploadRecordingWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val filePath = inputData.getString("file_path") ?: return Result.failure()
        val appealId = inputData.getString("appeal_id") ?: return Result.failure()
        val phoneNumber = inputData.getString("phone_number") ?: return Result.failure()
        val direction = inputData.getString("direction") ?: return Result.failure()

        val file = File(filePath)
        if (!file.exists()) return Result.failure()

        return try {
            // Загрузка на сервер
            val response = apiService.uploadCallRecording(
                appealId = appealId,
                phoneNumber = phoneNumber,
                direction = direction,
                file = file.asRequestBody("audio/m4a".toMediaTypeOrNull())
            )

            // Удаление локального файла
            file.delete()

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Upload failed", e)
            Result.retry()
        }
    }
}
```

**API:** `POST /api/operator/calls/upload-recording`

---

## QR-сканирование

### Реализация

**1. Зависимости**

```gradle
dependencies {
    implementation "androidx.camera:camera-camera2:1.3.0"
    implementation "androidx.camera:camera-lifecycle:1.3.0"
    implementation "androidx.camera:camera-view:1.3.0"
    implementation "com.google.mlkit:barcode-scanning:17.2.0"
}
```

**2. CameraX + ML Kit**

```kotlin
@Composable
fun QRScannerPreview(
    onQRDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // Preview
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                // Image Analysis для ML Kit
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            QRCodeAnalyzer { qrCode ->
                                onQRDetected(qrCode)
                            }
                        )
                    }

                // Привязка к lifecycle
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalysis
                )

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

class QRCodeAnalyzer(
    private val onQRDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { qrCode ->
                        onQRDetected(qrCode)
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
```

**3. Обработка QR-кода**

```kotlin
@HiltViewModel
class QRScannerViewModel @Inject constructor(
    private val qrRepository: QRRepository
) : ViewModel() {

    private val _scanResult = MutableStateFlow<QRScanResult>(QRScanResult.Scanning)
    val scanResult: StateFlow<QRScanResult> = _scanResult.asStateFlow()

    fun processQR(qrCode: String) {
        viewModelScope.launch {
            when (val result = qrRepository.scanQR(qrCode)) {
                is Success -> {
                    _scanResult.value = QRScanResult.Success(
                        appealId = result.appealId,
                        clientName = result.clientName
                    )
                }
                is Error -> {
                    _scanResult.value = QRScanResult.Error(result.message)
                }
            }
        }
    }
}
```

**API:** `POST /api/operator/qr/scan`

---

## Push-уведомления

### Firebase Cloud Messaging

**1. Конфигурация**

```gradle
dependencies {
    implementation platform('com.google.firebase:firebase-bom:32.5.0')
    implementation 'com.google.firebase:firebase-messaging-ktx'
}
```

**2. FCM Service**

```kotlin
class BatteryCRMFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Отправка токена на сервер
        CoroutineScope(Dispatchers.IO).launch {
            try {
                apiService.updateFCMToken(token)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update FCM token", e)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationType = message.data["type"]

        when (notificationType) {
            "new_message" -> {
                val appealId = message.data["appeal_id"]
                val clientName = message.data["client_name"]
                val messageText = message.data["message_text"]

                showNotification(
                    title = "Новое сообщение от $clientName",
                    text = messageText,
                    appealId = appealId
                )
            }

            "stage_changed" -> {
                val appealId = message.data["appeal_id"]
                val newStage = message.data["new_stage"]

                showNotification(
                    title = "Изменён этап заявки",
                    text = "Заявка перешла в этап: $newStage",
                    appealId = appealId
                )
            }
        }
    }

    private fun showNotification(title: String, text: String?, appealId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("appeal_id", appealId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(appealId.hashCode(), notification)
    }
}
```

---

## Multi-device sessions

### Логика

**Правило:** Один оператор может иметь **1 mobile + 1 desktop** сессию одновременно.

**При логине:**
1. Если оператор логинится с mobile устройства → удаляется старая mobile сессия
2. Если оператор логинится с desktop → удаляется старая desktop сессия
3. Обе сессии могут существовать одновременно

**Синхронизация:**
- Все изменения (отправка сообщений, смена этапов) синхронизируются через БД
- Push-уведомления приходят на все активные устройства
- При отправке сообщения с одного устройства, на другом автоматически обновляется UI

---

## Оффлайн режим

### Room Database

**Локальное кэширование:**
- Список заявок (последние 50)
- Сообщения заявок (последние 100 на заявку)
- Информация об операторе

**Схема БД:**

```kotlin
@Database(
    entities = [
        AppealEntity::class,
        MessageEntity::class,
        OperatorEntity::class
    ],
    version = 1
)
abstract class BatteryCRMDatabase : RoomDatabase() {
    abstract fun appealsDao(): AppealsDao
    abstract fun messagesDao(): MessagesDao
    abstract fun operatorDao(): OperatorDao
}

@Entity(tableName = "appeals")
data class AppealEntity(
    @PrimaryKey val id: String,
    val clientId: String,
    val clientName: String,
    val channel: String,
    val appealType: String,
    val stage: String,
    val status: String,
    val brand: String?,
    val model: String?,
    val issue: String?,
    val unreadCount: Int,
    val lastMessageText: String?,
    val lastMessageTimestamp: Long,
    val createdAt: Long,
    val updatedAt: Long
)
```

**Стратегия синхронизации:**
1. При подключении к интернету — загрузка свежих данных с сервера
2. При отключении — работа с локальным кэшем (только чтение)
3. При попытке записи в оффлайне — сохранение в очередь и отправка при подключении

---

## Безопасность

### 1. Хранение токенов

**DataStore (зашифрованный):**
```kotlin
class SecureTokenStorage(private val context: Context) {

    private val dataStore = context.createDataStore(
        name = "secure_tokens",
        serializer = TokenSerializer
    )

    suspend fun saveToken(token: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setJwtToken(encrypt(token))
                .build()
        }
    }

    private fun encrypt(text: String): String {
        // Android Keystore для шифрования
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        return Base64.encodeToString(
            cipher.doFinal(text.toByteArray()),
            Base64.DEFAULT
        )
    }
}
```

### 2. SSL Pinning

```kotlin
val okHttpClient = OkHttpClient.Builder()
    .certificatePinner(
        CertificatePinner.Builder()
            .add("n8n.n8nsrv.ru", "sha256/...")
            .build()
    )
    .build()
```

### 3. ProGuard/R8

```proguard
-keep class com.batterycrm.data.models.** { *; }
-keep class com.batterycrm.api.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
```

---

**Конец спецификации Android-приложения.**
