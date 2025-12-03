# Настройка Firebase для Eldoleado

## Зачем нужен google-services.json

Файл `google-services.json` содержит конфигурацию Firebase и необходим для:
- **Push-уведомления (FCM)** — получение уведомлений о новых обращениях
- **Firebase Analytics** — сбор статистики использования приложения
- **Crashlytics** — отчёты о сбоях (опционально)

> **Важно:** Без правильного `google-services.json` push-уведомления работать не будут!

---

## Шаг 1: Создание проекта Firebase

1. Перейди на [Firebase Console](https://console.firebase.google.com/)
2. Нажми **"Создать проект"** (или "Add project")
3. Введи название: `Eldoleado`
4. Google Analytics — можно включить или отключить (опционально)
5. Нажми **"Создать проект"**

---

## Шаг 2: Добавление Android-приложения

1. В Firebase Console открой созданный проект
2. На главной странице нажми **"Добавить приложение"** → выбери **Android**
3. Заполни форму:
   - **Android package name:** `com.eldoleado.app`
   - **App nickname:** `Eldoleado` (опционально)
   - **Debug signing certificate SHA-1:** пока можно пропустить
4. Нажми **"Зарегистрировать приложение"**

---

## Шаг 3: Скачивание google-services.json

1. После регистрации приложения появится кнопка **"Скачать google-services.json"**
2. Скачай файл
3. Замени файл в проекте:
   ```
   Eldoleado/app/google-services.json
   ```
4. Нажми **"Далее"** → **"Далее"** → **"Перейти в консоль"**

---

## Шаг 4: Получение Service Account для сервера (n8n)

> **Важно:** Legacy API отключён. Для отправки push-уведомлений с сервера используй HTTP v1 API с Service Account.

### Создание Service Account ключа:

1. В Firebase Console → **Project Settings** (шестерёнка)
2. Вкладка **"Service accounts"**
3. Нажми **"Generate new private key"**
4. Подтверди и скачай JSON-файл
5. Сохрани файл в безопасное место (например: `eldoleado-firebase-admin.json`)

### Структура Service Account JSON:

```json
{
  "type": "service_account",
  "project_id": "eldoleado",
  "private_key_id": "...",
  "private_key": "-----BEGIN PRIVATE KEY-----\n...\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-xxxxx@eldoleado.iam.gserviceaccount.com",
  "client_id": "...",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token"
}
```

### Отправка push через HTTP v1 API:

**Endpoint:**
```
POST https://fcm.googleapis.com/v1/projects/eldoleado/messages:send
```

**Headers:**
```
Authorization: Bearer {ACCESS_TOKEN}
Content-Type: application/json
```

**Body:**
```json
{
  "message": {
    "token": "{FCM_DEVICE_TOKEN}",
    "notification": {
      "title": "Новое обращение",
      "body": "Клиент: +7 999 123-45-67"
    },
    "data": {
      "type": "new_appeal",
      "appeal_id": "12345"
    }
  }
}
```

### Получение Access Token:

Для HTTP v1 API нужен OAuth2 access token. В n8n используй:
- **Google Service Account** credentials
- Scope: `https://www.googleapis.com/auth/firebase.messaging`

---

## Шаг 5: Настройка бэкенда (n8n)

Для отправки push-уведомлений с сервера нужно обновить workflow:

1. Открой n8n workflow `BAT_FCM_Sender`
2. Обнови Firebase credentials:
   - Project ID: `eldoleado` (из Firebase Console)
   - Service Account JSON: загрузи скачанный ключ

---

## Проверка

После настройки:
1. Собери и установи приложение на устройство
2. Войди в аккаунт
3. Отправь тестовое уведомление из Firebase Console:
   - **Engage** → **Messaging** → **New campaign**
   - Выбери **"Notification"**
   - Отправь тест на своё устройство

---

## Структура google-services.json

```json
{
  "project_info": {
    "project_number": "123456789012",        // Твой номер проекта
    "project_id": "eldoleado",               // ID проекта
    "storage_bucket": "eldoleado.appspot.com"
  },
  "client": [{
    "client_info": {
      "mobilesdk_app_id": "1:123456789012:android:abc123",
      "android_client_info": {
        "package_name": "com.eldoleado.app"  // Должен совпадать с applicationId
      }
    },
    "api_key": [{
      "current_key": "AIza..."               // API ключ
    }]
  }]
}
```

---

## Возможные проблемы

| Проблема | Решение |
|----------|---------|
| `package_name` не совпадает | Убедись что в Firebase указан `com.eldoleado.app` |
| Уведомления не приходят | Проверь разрешения в настройках Android |
| Ошибка при сборке | Проверь что `google-services.json` в папке `app/` |
| FCM token не регистрируется | Проверь интернет-соединение и логи |

---

## Полезные ссылки

- [Firebase Console](https://console.firebase.google.com/)
- [FCM Documentation](https://firebase.google.com/docs/cloud-messaging)
- [Setup Android App](https://firebase.google.com/docs/android/setup)
