# 🚀 Deployment Checklist - AI Multi-Entity Extractor

## Дата: 2025-11-23
## Статус: Готов к деплою

---

## ✅ Pre-Deployment Checklist

### 1. Файлы готовы
- [x] `workflows/BAT AI Multi-Entity Extractor.json` - workflow создан
- [x] `prompts/multi_entity_extractor_prompt.md` - промпт готов
- [x] `AI_MULTI_ENTITY_EXTRACTOR_DOC.md` - документация написана
- [x] `AI_MULTI_ENTITY_EXTRACTOR_DEPLOYMENT.md` - руководство по деплою готово

### 2. База данных
- [x] Таблица `repair_categories` существует (10 категорий)
- [x] Таблица `brands` существует
- [x] Таблица `models` существует
- [x] Таблица `appeal_devices` существует
- [x] Таблица `appeal_repairs` существует

### 3. Multi-Appeal API
- [x] `PUT /api/operator/appeals/:id/devices` реализован
- [x] `GET /api/operator/appeals/:id/devices` реализован
- [x] `DELETE /api/operator/appeals/:id/devices/:device_id` реализован

---

## 📋 Deployment Steps

### Шаг 1: Получить Anthropic API Key

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

1. Перейти на https://console.anthropic.com/
2. Войти или зарегистрироваться
3. Создать новый API key
4. Скопировать ключ (формат: `sk-ant-api03-...`)
5. Положить на баланс минимум $5

**Важно:** Сохраните ключ в безопасном месте!

---

### Шаг 2: Настроить Anthropic Credential в n8n

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

1. Открыть n8n UI: https://n8n.n8nsrv.ru
2. Перейти: **Settings** → **Credentials** → **Add Credential**
3. Выбрать тип: **HTTP Header Auth**
4. Заполнить:
   - **Name**: `Anthropic API Key`
   - **Header Name**: `x-api-key`
   - **Header Value**: `sk-ant-api03-...` (ваш ключ)
5. Нажать **Create**

**Скриншот:**
```
┌─────────────────────────────────────┐
│ Credential Name: Anthropic API Key  │
│                                     │
│ Header Name:  x-api-key             │
│ Header Value: sk-ant-api03-xxxxx    │
│                                     │
│ [Create] [Cancel]                   │
└─────────────────────────────────────┘
```

---

### Шаг 3: Импортировать Workflow в n8n

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

**Вариант A: Через UI (рекомендуется)**

1. Открыть файл `workflows/BAT AI Multi-Entity Extractor.json`
2. Скопировать всё содержимое (Ctrl+A, Ctrl+C)
3. Открыть n8n UI: https://n8n.n8nsrv.ru
4. В левом меню: **Workflows** → **+** → **Import from File**
5. Вставить JSON (Ctrl+V)
6. Нажать **Import**

**Вариант B: Через файл**

1. Скопировать файл на сервер:
   ```bash
   scp "workflows/BAT AI Multi-Entity Extractor.json" user@n8nsrv.ru:/tmp/
   ```
2. В n8n UI: **Workflows** → **Import** → выбрать файл
3. Нажать **Import**

**Ожидаемый результат:**
- Workflow появится в списке workflows
- Имя: "BAT AI Multi-Entity Extractor"
- Nodes: 9 штук

---

### Шаг 4: Проверить и настроить Credentials в Workflow

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

**4.1 PostgreSQL Credentials**

Для каждого node (Load Brands, Load Models, Load Repair Categories):

1. Кликнуть на node
2. В секции **Credentials** проверить:
   - Credential: `Postgres account` (должен быть выбран)
   - ID: `n2SyhP9QhMnp1ryk`

Если credential не выбран:
1. Кликнуть на dropdown
2. Выбрать существующий `Postgres account`

**4.2 Anthropic API Credential**

Для node **Call Claude AI**:

1. Кликнуть на node
2. В секции **Credentials** выбрать созданный **Anthropic API Key**
3. Проверить параметры:
   - Method: `POST`
   - URL: `https://api.anthropic.com/v1/messages`

**4.3 Добавить header `anthropic-version`**

В node **Call Claude AI**:

1. Перейти в секцию **Headers**
2. Добавить header:
   - Name: `anthropic-version`
   - Value: `2023-06-01`

**Важно:** Этот header обязателен для работы с Anthropic API!

---

### Шаг 5: Сохранить Workflow

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

1. Нажать **Save** (Ctrl+S)
2. Убедиться, что workflow сохранён (индикатор "Saved")

---

### Шаг 6: Протестировать Workflow

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

**6.1 Подготовить тестовые данные**

Скопировать этот JSON:

```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "00000000-0000-0000-0000-000000000001",
  "current_message": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается",
  "message_history": [
    {
      "timestamp": "2025-11-23T10:00:00Z",
      "is_client": true,
      "text": "Здравствуйте, хочу отремонтировать телефоны"
    }
  ]
}
```

**6.2 Запустить тест**

1. В workflow кликнуть на node **Execute Workflow Trigger**
2. Кликнуть **Test workflow**
3. Вставить тестовый JSON
4. Нажать **Execute**

**6.3 Проверить результат**

Ожидаемый результат в последнем node (**Map To DB IDs**):

```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "00000000-0000-0000-0000-000000000001",
  "is_multi_device": true,
  "device_count": 2,
  "devices": [
    {
      "brand": "Apple",
      "brand_id": "...",
      "model": "iPhone 14",
      "model_id": "...",
      "repairs": [
        {
          "category_id": "5295580f-2229-42cf-8460-3418a3dbae9d",
          "category_name": "Аккумулятор",
          "symptoms": "батарея"
        },
        {
          "category_id": "08a21976-68f2-4474-b7f8-7f032d0da88c",
          "category_name": "Дисплей",
          "symptoms": "разбит экран"
        }
      ]
    },
    {
      "brand": "Samsung",
      "brand_id": "...",
      "model": "Galaxy S23",
      "model_id": "...",
      "repairs": [
        {
          "category_id": "d4a2546c-1041-4b8f-9deb-e3e9310c5239",
          "category_name": "Порты",
          "symptoms": "не заряжается"
        }
      ]
    }
  ]
}
```

**Если тест успешен:** ✅ Переходим к следующему шагу

**Если ошибка:**
- Проверить логи в каждом node
- Проверить credentials
- Проверить баланс Anthropic API

---

### Шаг 7: Получить Workflow ID

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

1. После успешного теста скопировать **Workflow ID** из URL:
   ```
   https://n8n.n8nsrv.ru/workflow/XXXXXXXXXXXXX
                                 ^^^^^^^^^^^^^
                                 Workflow ID
   ```

2. Сохранить ID для следующего шага

**Пример:**
```
Workflow ID: abcd1234efgh5678
```

---

### Шаг 8: Интегрировать в BAT AI Appeal Router

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

**8.1 Открыть Appeal Router**

1. Перейти: **Workflows** → **BAT AI Appeal Router**
2. Найти место для добавления Multi-Entity Extractor

**8.2 Добавить новый node**

1. Кликнуть **+** (Add Node)
2. Выбрать: **Action in n8n** → **Execute Workflow**
3. Настроить:
   - **Workflow ID**: `abcd1234efgh5678` (из Шага 7)
   - **Source**: `Execute Workflow Trigger`

4. Назвать node: `Call Multi-Entity Extractor`

**8.3 Подключить входные данные**

Создать node **Prepare Multi-Entity Input** (Code node) перед вызовом:

```javascript
return {
  tenant_id: $('Load Appeal & History').item.json.tenant_id,
  appeal_id: $('Load Appeal & History').item.json.id,
  current_message: $('Load Appeal & History').item.json.messages.slice(-1)[0].text,
  message_history: $('Load Appeal & History').item.json.messages
};
```

**8.4 Обработать результат**

Создать node **Process Multi-Entity Result** (Code node) после вызова:

```javascript
const result = $('Call Multi-Entity Extractor').item.json;

if (result.is_multi_device) {
  // Мультизаявка: вызвать Multi-Appeal API
  return {
    use_multi_appeal_api: true,
    devices: result.devices,
    appeal_id: result.appeal_id
  };
} else {
  // Простая заявка: использовать старую логику
  const device = result.devices[0];
  return {
    use_multi_appeal_api: false,
    brand: device.brand,
    brand_id: device.brand_id,
    model: device.model,
    model_id: device.model_id,
    repair: device.repairs[0]
  };
}
```

**8.5 Добавить вызов Multi-Appeal API**

Создать node **Call Multi-Appeal API** (HTTP Request):

```
Method: PUT
URL: https://n8n.n8nsrv.ru/webhook/api/operator/appeals/{{ $json.appeal_id }}/devices
Body:
{
  "devices": {{ JSON.stringify($json.devices) }}
}
```

**8.6 Сохранить Appeal Router**

Нажать **Save** (Ctrl+S)

---

### Шаг 9: E2E Тестирование

**Статус:** ⚠️ ТРЕБУЕТСЯ ДЕЙСТВИЕ

**9.1 Создать тестовую заявку**

Отправить сообщение в WhatsApp/Telegram от клиента:

```
У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается
```

**9.2 Проверить логи n8n**

1. Перейти: **Executions** (в левом меню)
2. Найти выполнение **BAT AI Appeal Router**
3. Проверить, что вызвался **BAT AI Multi-Entity Extractor**
4. Проверить результат

**9.3 Проверить БД**

```sql
-- Проверить заявку
SELECT id, is_multi_appeal FROM appeals WHERE id = 'appeal-uuid';

-- Должно быть: is_multi_appeal = true

-- Проверить устройства
SELECT * FROM appeal_devices WHERE appeal_id = 'appeal-uuid';

-- Должно быть: 2 записи (iPhone 14, Samsung S23)

-- Проверить неисправности
SELECT ar.*, rc.name as category_name
FROM appeal_repairs ar
JOIN repair_categories rc ON rc.id = ar.repair_category_id
WHERE ar.appeal_device_id IN (
  SELECT id FROM appeal_devices WHERE appeal_id = 'appeal-uuid'
);

-- Должно быть: 3 записи
-- - iPhone 14 → Аккумулятор
-- - iPhone 14 → Дисплей
-- - Samsung S23 → Порты
```

**9.4 Проверить в UI (если есть)**

1. Открыть заявку в UI
2. Убедиться, что отображаются 2 устройства
3. Убедиться, что каждое устройство имеет свои неисправности

---

## ✅ Post-Deployment Checklist

### 1. Мониторинг
- [ ] Настроить алерты на ошибки workflow
- [ ] Настроить логирование в PostgreSQL
- [ ] Создать dashboard с метриками

### 2. Документация
- [ ] Обновить Start.md с актуальным Workflow ID
- [ ] Добавить ссылку на workflow в документацию
- [ ] Обновить README с новым функционалом

### 3. Оптимизация
- [ ] Добавить кеширование справочников (brands, models, categories)
- [ ] Настроить fallback на старую логику при ошибках
- [ ] Оптимизировать промпт для снижения стоимости

---

## 🐛 Troubleshooting

### Ошибка: "Failed to connect to database"
**Решение:**
- Проверить PostgreSQL credentials в n8n
- Проверить доступ к серверу: `telnet 185.221.214.83 6544`
- Проверить что БД `postgres` существует

### Ошибка: "Anthropic API error: 401 Unauthorized"
**Решение:**
- Проверить API key в credential
- Проверить баланс на https://console.anthropic.com/
- Проверить header `x-api-key` и `anthropic-version`

### Ошибка: "Failed to parse AI response as JSON"
**Решение:**
- Посмотреть сырой ответ Claude AI в node **Call Claude AI**
- Проверить промпт в node **Build AI Prompt**
- Возможно Claude AI вернул текст вместо JSON

### Ошибка: "Category not found"
**Решение:**
- Проверить что таблица `repair_categories` заполнена
- Выполнить: `SELECT * FROM repair_categories WHERE is_active = true`
- Если пусто, выполнить миграцию из `database/migrations/001_step2_create_repair_categories.sql`

---

## 📊 Success Metrics

После деплоя отслеживать:

1. **Процент мультизаявок**: `(is_multi_device = true) / всего заявок`
   - Ожидаемо: 10-20%

2. **Точность извлечения**: `(правильно извлечённые устройства) / всего устройств`
   - Ожидаемо: >95%

3. **Время выполнения workflow**: среднее время
   - Ожидаемо: 2-4 секунды

4. **Стоимость AI вызовов**: стоимость на 1 заявку
   - Ожидаемо: ~$0.012

5. **Процент ошибок**: `(failed tasks) / всего tasks`
   - Ожидаемо: <1%

---

## 📝 Notes

**Автор:** Claude Code + User
**Дата:** 2025-11-23
**Версия:** 1.0

**Контакты:**
- n8n UI: https://n8n.n8nsrv.ru
- PostgreSQL: 185.221.214.83:6544
- Anthropic Console: https://console.anthropic.com/

**Связанные файлы:**
- `workflows/BAT AI Multi-Entity Extractor.json`
- `AI_MULTI_ENTITY_EXTRACTOR_DOC.md`
- `AI_MULTI_ENTITY_EXTRACTOR_DEPLOYMENT.md`
- `UNIVERSAL_AI_PROMPT_SYSTEM.md`
