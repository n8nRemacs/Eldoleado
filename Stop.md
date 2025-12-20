# Stop Session - 2025-12-21 00:10

## Что сделано сегодня

### 1. Упрощён экран логина
- Убран выбор режима (Оператор/Сервер/Оба)
- Теперь только режим "Оператор" (MODE_CLIENT)
- Файлы: `activity_login.xml`, `LoginActivity.kt`

### 2. Исправлена загрузка сообщений в диалогах
**Проблема:** 404 ошибка при открытии диалога - "Ошибка загрузки"

**Причина:** n8n не поддерживает динамические path параметры (`:dialog_id`) в production webhooks

**Решение:**
- Изменён webhook path: `android/dialogs/:dialog_id/messages` → `android/messages`
- Изменён ApiService.kt: `@Path("dialog_id")` → `@Query("dialog_id")`
- Исправлены SQL запросы в n8n: `$json.params.dialog_id` → `$json.query.dialog_id`

### 3. Создана связь Оператор ↔ Канал
**Проблема:** Диалоги не назначались оператору (assigned_operator_id = NULL)

**Решение:**
- Создана таблица `elo_t_operator_channels` (связь оператор-канал)
- Добавлен столбец `channel_account_id` в `elo_t_dialogs`
- Обновлены существующие диалоги с назначением оператора

### 4. Исправлено дублирование текста сообщений
**Проблема:** Текст сохранялся как "Привет\n\nПривет" (конкатенация батча)

**Причина:** WhatsApp Baileys батчит сообщения, Validate Input конкатенирует тексты

**Решение:**
- В Save Incoming Message изменено: `$json.text` → `$json.meta.raw.data.text`

---

## Файлы изменены

### Android App
- `app/src/main/res/layout/activity_login.xml` - убран RadioGroup выбора режима
- `app/src/main/java/com/eldoleado/app/LoginActivity.kt` - всегда MODE_CLIENT
- `app/src/main/java/com/eldoleado/app/api/ApiService.kt` - messages endpoint fix

### База данных
```sql
-- Новая таблица
CREATE TABLE elo_t_operator_channels (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operator_id UUID NOT NULL REFERENCES elo_t_operators(id),
    channel_account_id UUID NOT NULL REFERENCES elo_t_channel_accounts(id),
    is_primary BOOLEAN DEFAULT false,
    max_concurrent_dialogs INT DEFAULT 50,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT now(),
    UNIQUE(operator_id, channel_account_id)
);

-- Новый столбец
ALTER TABLE elo_t_dialogs ADD COLUMN channel_account_id UUID;
```

### n8n Workflows (изменены вручную)
- **ELO_API_Android_Messages:**
  - Webhook path: `android/messages`
  - SQL: `$json.query.dialog_id` вместо `$json.params.dialog_id`

- **ELO_Client_Resolve:**
  - Save Incoming Message: `$json.meta.raw.data.text` вместо `$json.text`

### Документация
- `NEW/N8N_SQL_FIXES.md` - документация по SQL фиксам для n8n

---

## Текущее состояние

**Работает:**
- Логин в приложении
- Загрузка списка диалогов
- Загрузка сообщений в диалоге
- Сохранение входящих сообщений (без дублирования)

**Не работает:**
- Нормализация текста (webhook с `:dialog_id`)
- Отправка сообщений (не проверялось)

---

## Сессионные данные

- **Тестовый оператор:** Test Admin (22222222-2222-2222-2222-222222222222)
- **Session token:** 85bc5364-7765-4562-be9e-02d899bb575e
- **Тестовый диалог:** cff56064-1fc3-4152-8e64-6e0266a87bf6 (Дмитрий, WhatsApp)
