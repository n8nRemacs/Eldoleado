# Backend Implementation Checklist - Multi-Device Session Management

**Date:** 19.11.2025  
**Priority:** HIGH  
**Status:** 🔄 In Progress

---

## ❓ Ответы на вопросы бэкендера

### Вопрос 1: Добавить парсинг device_info в API_Android_Auth?

**Ответ:** ✅ **ДА, нужно добавить**

**Причина:** Фронтендер отправляет `device_info` в `LoginRequest`, но сейчас воркфлоу его не парсит. Нужно добавить парсинг, чтобы сохранять информацию об устройстве при логине.

**Действие:** Добавить в ноду парсинга body (или создать отдельную ноду):
```javascript
const body = $input.item.json.body || {};
return {
  login: body.login?.trim() || '',
  password: body.password?.trim() || '',
  device_info: body.device_info || null  // Добавить парсинг
};
```

---

### Вопрос 2: Реальный путь API_Operator_Appeals_List?

**Ответ:** 
- **Полный путь:** `/webhook/dbc87d6b-d3a8-4613-a2d8-b591d5533210/api/operator/appeals/list`
- **Webhook ID:** `dbc87d6b-d3a8-4613-a2d8-b591d5533210`
- **Тип:** GET с query параметрами (`operator_id`, `status`, `limit`)
- **Это существующий воркфлоу**, не новый эндпоинт

**Действие:** Обновить существующий воркфлоу `API_Operator_Appeals_List.json`

---

### Вопрос 3: Api_Voice_Uploader требует обновления?

**Ответ:** ❌ **НЕТ, не требует обновления**

**Причина:** 
- API принимает `tenant_id` и `operator_id` в body
- **НЕ проверяет** `session_token` для аутентификации
- Не используется в Android приложении (не найден в коде)

**Действие:** Оставить как есть, или добавить проверку session_token опционально (если нужна аутентификация в будущем)

---

### Исправление ошибок:

#### Ошибка 1: SQL синтаксис для n8n ✅ ИСПРАВЛЕНО
- ❌ Неправильно: `WHERE od.session_token = :session_token`
- ✅ Правильно: `WHERE od.session_token = '{{ $json.headers["x-session-token"] }}'`
- Или: `WHERE od.session_token = '{{ $json.session_token }}'` (если уже извлечен)

#### Ошибка 2: Constraint с tenant_id ✅ ИСПРАВЛЕНО
- ✅ **ДА, лучше добавить tenant_id** для безопасности
- Правильно: `CONSTRAINT unique_operator_device_type UNIQUE(operator_id, device_type, tenant_id)`
- Это предотвращает конфликты между разными tenant'ами

---

## 📋 Общий обзор

Реализация системы управления сессиями для нескольких устройств:
- 1 оператор = максимум 2 устройства одновременно (1 mobile + 1 desktop)
- При новом логине на мобильном устройстве → старая мобильная сессия автоматически удаляется
- Все API должны проверять сессию в новой таблице `operator_devices`

---

## ✅ Задача 1: Создать таблицу `operator_devices`

### SQL Script:
```sql
CREATE TABLE operator_devices (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  operator_id uuid NOT NULL,
  tenant_id uuid NOT NULL,
  device_type TEXT NOT NULL,              -- 'mobile' or 'desktop'
  fcm_token TEXT,
  session_token TEXT UNIQUE NOT NULL,     -- Auth token
  device_id TEXT,                         -- Optional: Android device ID
  device_info JSONB,                      -- Optional: model, OS, app version
  last_active_at TIMESTAMP DEFAULT NOW(),
  created_at TIMESTAMP DEFAULT NOW(),
  CONSTRAINT unique_operator_device_type UNIQUE(operator_id, device_type, tenant_id)
);

-- Индексы для быстрого поиска
CREATE INDEX idx_operator_devices_session_token ON operator_devices(session_token);
CREATE INDEX idx_operator_devices_operator_id ON operator_devices(operator_id);
CREATE INDEX idx_operator_devices_device_type ON operator_devices(device_type);
```

### ✅ Checklist:
- [ ] Таблица создана
- [ ] UNIQUE constraint на (operator_id, device_type) работает
- [ ] Индексы созданы
- [ ] Протестирована вставка/удаление записей

---

## ✅ Задача 2: Обновить `API_Android_Auth` (POST /webhook/android/auth/login)

### Текущее поведение:
- Принимает: `{ "login": "...", "password": "..." }`
- **ВАЖНО:** Сейчас `device_info` НЕ парсится в воркфлоу
- Может принимать: `{ "login": "...", "password": "...", "device_info": {...} }` (опционально, если добавить парсинг)
- Возвращает: `{ "success": true, "operator_id": "...", "tenant_id": "...", "name": "...", "email": "...", "session_token": "..." }`

### ⚠️ Вопрос: Добавить парсинг device_info?

**Ответ:** ДА, нужно добавить парсинг `device_info` в воркфлоу, чтобы сохранять информацию об устройстве при логине.

**Действие:** Добавить в ноду "Parse Body" или создать отдельную ноду для парсинга:
```javascript
const body = $input.item.json.body || {};
return {
  login: body.login?.trim() || '',
  password: body.password?.trim() || '',
  device_info: body.device_info || null  // Добавить парсинг
};
```

### Новая логика (ПОШАГОВО):

1. **Валидация credentials** ✅
   - Проверить login (email или username) и password
   - Найти оператора в таблице `operators`

2. **Проверка существующей мобильной сессии** ⚠️ **КРИТИЧНО**
   ```sql
   SELECT id FROM operator_devices 
   WHERE operator_id = '{{ $json.operator_id }}'
   AND device_type = 'mobile'
   AND tenant_id = '{{ $json.tenant_id }}';
   ```
   - Если найдена → **УДАЛИТЬ** старую запись:
     ```sql
     DELETE FROM operator_devices 
     WHERE operator_id = '{{ $json.operator_id }}'
     AND device_type = 'mobile'
     AND tenant_id = '{{ $json.tenant_id }}';
     ```
   - Это автоматически разлогинит старое устройство (оно получит 401 при следующем запросе)

3. **Генерация нового session_token**
   - `session_token = gen_random_uuid()::text`

4. **Вставка в operator_devices**
   ```sql
   INSERT INTO operator_devices (
     operator_id,
     tenant_id,
     device_type,
     session_token,
     fcm_token,
     device_id,
     device_info,
     created_at,
     last_active_at
   ) VALUES (
     '{{ $json.operator_id }}'::uuid,
     '{{ $json.tenant_id }}'::uuid,
     'mobile',  -- ⚠️ ВАЖНО: всегда 'mobile' для Android
     '{{ $json.session_token }}',
     NULL,  -- Будет установлен позже через FCM API
     {{ $json.device_id ? "'" + $json.device_id + "'" : "NULL" }},  -- Из device_info.device_id (если есть)
     {{ $json.device_info ? "'" + JSON.stringify($json.device_info) + "'::jsonb" : "NULL" }},  -- Весь объект device_info как JSONB (если есть)
     NOW(),
     NOW()
   );
   ```
   
   **Или через JavaScript ноду перед INSERT:**
   ```javascript
   const deviceInfo = $json.device_info || null;
   const deviceId = deviceInfo?.device_id || null;
   const deviceInfoJson = deviceInfo ? JSON.stringify(deviceInfo) : null;
   
   return {
     ...$json,
     device_id: deviceId,
     device_info_json: deviceInfoJson
   };
   ```
   
   Тогда SQL будет:
   ```sql
   INSERT INTO operator_devices (...)
   VALUES (
     '{{ $json.operator_id }}'::uuid,
     '{{ $json.tenant_id }}'::uuid,
     'mobile',
     '{{ $json.session_token }}',
     NULL,
     {{ $json.device_id ? "'" + $json.device_id + "'" : "NULL" }},
     {{ $json.device_info_json ? "'" + $json.device_info_json + "'::jsonb" : "NULL" }},
     NOW(),
     NOW()
   );
   ```

5. **Возврат ответа**
   - Вернуть существующий формат ответа (не меняется)

### ✅ Checklist:
- [ ] Удаление старой мобильной сессии перед созданием новой
- [ ] Вставка в `operator_devices` с `device_type = 'mobile'`
- [ ] Сохранение `device_info` (если приходит) в JSONB поле
- [ ] Сохранение `device_id` (если есть в device_info)
- [ ] `fcm_token = NULL` при создании (устанавливается позже)
- [ ] Протестировано: логин на Device A → логин на Device B → Device A получает 401

---

## ✅ Задача 3: Обновить `API_Android_Register_FCM` (POST /webhook/android-register-fcm)

### Текущее поведение:
- Принимает: `{ "operator_id": "...", "session_token": "...", "fcm_token": "...", "device_info": {...} }`
- Обновляет `fcm_token` в таблице `operators`

### Новая логика:

1. **Найти устройство по session_token в operator_devices**
   ```sql
   SELECT id, operator_id, device_type 
   FROM operator_devices 
   WHERE session_token = '{{ $json.session_token }}';
   ```

2. **Проверка**
   - Если не найдено → вернуть 401
   - Если `device_type != 'mobile'` → вернуть 401 (для безопасности)

3. **Обновить fcm_token и device_info**
   
   **Сначала через JavaScript ноду подготовить данные:**
   ```javascript
   const deviceInfo = $json.device_info || null;
   const deviceId = deviceInfo?.device_id || null;
   const deviceInfoJson = deviceInfo ? JSON.stringify(deviceInfo) : null;
   
   return {
     ...$json,
     device_id: deviceId,
     device_info_json: deviceInfoJson
   };
   ```
   
   **Затем SQL:**
   ```sql
   UPDATE operator_devices
   SET 
     fcm_token = '{{ $json.fcm_token }}',
     device_info = {{ $json.device_info_json ? "'" + $json.device_info_json + "'::jsonb" : "device_info" }},  -- Обновить, если приходит
     device_id = {{ $json.device_id ? "'" + $json.device_id + "'" : "device_id" }},  -- Обновить, если есть в device_info
     last_active_at = NOW()
   WHERE session_token = '{{ $json.session_token }}'
   AND device_type = 'mobile';
   ```

4. **Возврат ответа**
   - Существующий формат: `{ "success": true, "message": "...", "operator_id": "..." }`

### ✅ Checklist:
- [ ] Поиск в `operator_devices` вместо `operators`
- [ ] Проверка `device_type = 'mobile'`
- [ ] Обновление `fcm_token`
- [ ] Обновление `device_info` (если приходит)
- [ ] Обновление `last_active_at`
- [ ] Возврат 401, если session_token не найден

---

## ✅ Задача 4: Обновить `API_Android_Logout` (POST /webhook/android/logout)

### Текущее поведение:
- Принимает токен в headers: `Authorization: Bearer <token>` или `X-Session-Token: <token>`
- Обновляет `operators`: устанавливает `session_token = NULL`, `fcm_token = NULL`

### Новая логика:

1. **Извлечь session_token из headers**
   - Проверить `Authorization: Bearer <token>`
   - Или `X-Session-Token: <token>`

2. **Удалить запись из operator_devices**
   ```sql
   DELETE FROM operator_devices 
   WHERE session_token = '{{ $json.session_token }}';
   ```

3. **Возврат ответа**
   - Существующий формат: `{ "success": true, "message": "..." }`
   - Если токен не найден → `{ "success": false, "error": "..." }` (но не 401, чтобы не триггерить ErrorInterceptor)

### ✅ Checklist:
- [ ] Удаление из `operator_devices` вместо обновления `operators`
- [ ] Корректная обработка отсутствующего токена
- [ ] Возврат правильного формата ответа

---

## ✅ Задача 5: Обновить ВСЕ Operator API для проверки session в operator_devices

### Список API, которые нужно обновить:

1. **API_Operator_Appeals_List** 
   - **Реальный путь:** `/webhook/dbc87d6b-d3a8-4613-a2d8-b591d5533210/api/operator/appeals/list`
   - **Webhook ID:** `dbc87d6b-d3a8-4613-a2d8-b591d5533210`
   - **Тип:** GET с query параметрами
   - ✅ **Требует обновления** - использует session_token из headers

2. **API_Operator_Appeal_Detail** (GET /webhook/dbc87d6b-d3a8-4613-a2d8-b591d5533210/api/operator/appeals/{id})
   - ✅ **Требует обновления** - использует session_token из headers

3. **API_Operator_Take_Appeal** (POST /webhook/android-take-appeal/android/appeals/{id}/take)
   - ✅ **Требует обновления** - использует session_token из headers

4. **API_Operator_Send_Response** (POST /webhook/unique-send-response/api/operator/appeals/{id}/send)
   - ✅ **Требует обновления** - использует session_token из headers

5. **API_Operator_Normalize** (POST /webhook/unique-normalize/api/operator/appeals/{id}/normalize)
   - ✅ **Требует обновления** - использует session_token из headers

6. **API_Operator_Reject** (POST /webhook/android-reject/android/appeals/{id}/reject)
   - ⚠️ **Проверить** - нужно проверить, использует ли session_token

7. **API_Operator_Send_Promo** (POST /webhook/android-send-promo/android/appeals/{id}/promo)
   - ⚠️ **Проверить** - нужно проверить, использует ли session_token

8. **Api_Voice_Uploader** (POST /webhook/unique-upload-voice/api/voice/upload)
   - ❌ **НЕ требует обновления** - принимает `tenant_id` и `operator_id` в body, но НЕ проверяет session_token
   - **Примечание:** Если нужна аутентификация, можно добавить проверку session_token, но это опционально

### Новая логика аутентификации (для ВСЕХ API):

**ЗАМЕНИТЬ:**
```sql
-- СТАРЫЙ КОД (удалить)
SELECT id as operator_id, tenant_id 
FROM operators 
WHERE session_token = '{{ $json.session_token }}'
AND is_active = true
LIMIT 1;
```

**НА:**
```sql
-- НОВЫЙ КОД (правильный синтаксис для n8n)
SELECT 
  od.operator_id,
  od.tenant_id,
  od.device_type
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.headers["x-session-token"] }}'  -- Или из Authorization header
  AND od.device_type = 'mobile'  -- ⚠️ ВАЖНО: только mobile для Android
  AND o.is_active = true
LIMIT 1;
```

**Или если session_token уже извлечен в предыдущей ноде:**
```sql
SELECT 
  od.operator_id,
  od.tenant_id,
  od.device_type
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### Дополнительные действия:

1. **Обновить last_active_at** (опционально, но рекомендуется):
   ```sql
   UPDATE operator_devices 
   SET last_active_at = NOW() 
   WHERE session_token = '{{ $json.session_token }}';
   ```

2. **Проверка результата:**
   - Если запись не найдена → вернуть 401 Unauthorized
   - Если найдена → продолжить выполнение API

### ✅ Checklist для каждого API:

- [ ] Заменен SQL запрос на проверку в `operator_devices`
- [ ] Добавлена проверка `device_type = 'mobile'`
- [ ] Обновление `last_active_at` (опционально)
- [ ] Возврат 401, если session_token не найден
- [ ] Протестировано: работает с валидным токеном
- [ ] Протестировано: возвращает 401 для невалидного токена

---

## 🧪 Тестовые сценарии

### Сценарий 1: Логин на двух устройствах
1. Device A: логин → получает session_token_A
2. Device B: логин (те же credentials) → получает session_token_B
3. Device A: делает API запрос → должен получить 401
4. Device B: делает API запрос → должен работать ✅

### Сценарий 2: FCM регистрация
1. Device A: логин → получает session_token
2. Device A: регистрация FCM токена → должен обновиться fcm_token в operator_devices ✅
3. Device A: повторная регистрация FCM → должен обновиться существующий fcm_token ✅

### Сценарий 3: Logout
1. Device A: логин → получает session_token
2. Device A: logout → запись удаляется из operator_devices ✅
3. Device A: делает API запрос → должен получить 401 ✅

### Сценарий 4: Все Operator API
1. Device A: логин → получает session_token
2. Device A: вызывает каждый Operator API → все должны работать ✅
3. Device A: logout
4. Device A: вызывает Operator API → все должны вернуть 401 ✅

---

## ⚠️ Важные замечания

1. **Удаление старой сессии при логине** - КРИТИЧНО! Без этого система не будет работать правильно.

2. **device_type = 'mobile'** - всегда устанавливать для Android API, никогда не использовать 'desktop'.

3. **UNIQUE constraint** - автоматически предотвращает создание двух мобильных сессий для одного оператора.
   - Constraint включает `tenant_id` для безопасности: `UNIQUE(operator_id, device_type, tenant_id)`

4. **Обратная совместимость** - после миграции старые session_token из таблицы `operators` перестанут работать. Убедитесь, что все клиенты обновлены.

5. **Миграция данных** (опционально):
   - Если нужно мигрировать существующие активные сессии из `operators` в `operator_devices`
   - Но лучше просто дождаться, пока все пользователи перелогинятся

---

## 📝 Финальный чеклист перед релизом

- [ ] Таблица `operator_devices` создана и протестирована
- [ ] `API_Android_Auth` обновлен и протестирован
- [ ] `API_Android_Register_FCM` обновлен и протестирован
- [ ] `API_Android_Logout` обновлен и протестирован
- [ ] Все Operator API обновлены и протестированы
- [ ] Тестовые сценарии пройдены
- [ ] Проверено: старые session_token из `operators` больше не работают
- [ ] Проверено: новые session_token из `operator_devices` работают
- [ ] Уведомление фронтендера: "✅ Ready for testing"

---

## 🚀 Когда готово к тестированию

**Отправить фронтендеру:**
```
✅ operator_devices table created
✅ API_Android_Auth updated
✅ API_Android_Register_FCM updated
✅ API_Android_Logout updated
✅ API_Operator_Appeals_List updated
✅ API_Operator_Appeal_Detail updated
✅ API_Operator_Take_Appeal updated
✅ API_Operator_Send_Response updated
✅ API_Operator_Normalize updated
✅ API_Operator_Reject updated
✅ API_Operator_Send_Promo updated
✅ Ready for testing
```

---

**End of Checklist**

