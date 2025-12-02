# AI Multi-Entity Extractor - Документация

## Обзор

**BAT AI Multi-Entity Extractor** — это workflow для извлечения информации о **нескольких устройствах** и **нескольких неисправностях** из одного сообщения клиента.

## Зачем нужен

До этого система могла извлекать только **одно устройство** с **одной неисправностью**. Теперь клиент может написать:

```
У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается
```

И система автоматически создаст:
- **2 устройства** (iPhone 14, Samsung S23)
- **3 неисправности** (батарея, разбит экран, не заряжается)

## Архитектура

```
┌─────────────────────────────┐
│  BAT AI Appeal Router       │
│  (основной workflow)        │
└──────────┬──────────────────┘
           │
           │ Вызывает через Execute Workflow
           ▼
┌─────────────────────────────┐
│ BAT AI Multi-Entity         │
│ Extractor                   │
│                             │
│ 1. Load Brands              │
│ 2. Load Models              │
│ 3. Load Repair Categories   │
│ 4. Merge Reference Data     │
│ 5. Build AI Prompt          │
│ 6. Call Claude AI           │
│ 7. Parse AI Response        │
│ 8. Map To DB IDs            │
└──────────┬──────────────────┘
           │
           │ Возвращает devices[]
           ▼
┌─────────────────────────────┐
│  BAT AI Appeal Router       │
│  Сохраняет через            │
│  Multi-Appeal API           │
│  PUT /api/operator/         │
│      appeals/:id/devices    │
└─────────────────────────────┘
```

## Input (вход)

Workflow вызывается из Appeal Router с параметрами:

```json
{
  "tenant_id": "uuid",
  "appeal_id": "uuid",
  "current_message": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается",
  "message_history": [
    {
      "timestamp": "2025-11-23T10:00:00Z",
      "is_client": true,
      "text": "Здравствуйте, хочу отремонтировать телефоны"
    },
    {
      "timestamp": "2025-11-23T10:01:00Z",
      "is_client": false,
      "text": "Здравствуйте! Какие у вас телефоны?"
    },
    {
      "timestamp": "2025-11-23T10:02:00Z",
      "is_client": true,
      "text": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается"
    }
  ]
}
```

## Output (выход)

Workflow возвращает структурированные данные:

```json
{
  "tenant_id": "uuid",
  "appeal_id": "uuid",
  "is_multi_device": true,
  "device_count": 2,
  "devices": [
    {
      "brand": "Apple",
      "brand_id": "apple-brand-uuid",
      "model": "iPhone 14",
      "model_id": "iphone-14-model-uuid",
      "device_type": "smartphone",
      "serial_number": null,
      "color": null,
      "condition": "разбитое",
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
      "brand_id": "samsung-brand-uuid",
      "model": "Galaxy S23",
      "model_id": "galaxy-s23-model-uuid",
      "device_type": "smartphone",
      "serial_number": null,
      "color": null,
      "condition": null,
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

## Этапы обработки

### 1. Load Reference Data
- **Load Brands**: загрузка всех активных брендов из БД
- **Load Models**: загрузка всех активных моделей из БД
- **Load Repair Categories**: загрузка всех активных категорий ремонта

### 2. Build AI Prompt
Формирование промпта для Claude AI с:
- Списком брендов
- Списком моделей
- Списком категорий ремонта
- Историей сообщений
- Текущим сообщением

### 3. Call Claude AI
Вызов API Anthropic Claude 3.5 Sonnet с промптом.

**Model:** `claude-3-5-sonnet-20241022`
**Max tokens:** 4096

### 4. Parse AI Response
Извлечение JSON из ответа Claude AI:
- Поиск JSON в markdown блоке (\`\`\`json ... \`\`\`)
- Парсинг JSON
- Валидация структуры

### 5. Map To DB IDs
Маппинг текстовых названий на UUID из БД:
- Поиск brand_id по названию бренда
- Поиск model_id по названию модели и бренду
- Поиск category_id по названию категории ремонта

## Категории ремонта (repair_categories)

| ID | Название | Описание |
|:---|:---------|:---------|
| `08a21976-68f2-4474-b7f8-7f032d0da88c` | Дисплей | Замена экрана, тачскрина, стекла |
| `4b0b734f-122c-4c34-8306-6688a59403b1` | Корпус | Ремонт корпуса, замена задней крышки |
| `5295580f-2229-42cf-8460-3418a3dbae9d` | Аккумулятор | Замена батареи |
| `5091dc9c-9091-4aff-b1cb-597f27c18b20` | Звук | Динамик, микрофон, разъём наушников |
| `d6194620-e893-40f4-aab9-bed4ab7084e4` | Связь | Wi-Fi, Bluetooth, сотовая связь, GPS |
| `ba2cf8c9-980d-44b5-bf74-6185904a81ac` | Камера | Основная камера, фронтальная камера |
| `d4a2546c-1041-4b8f-9deb-e3e9310c5239` | Порты | Разъём зарядки, USB, Lightning |
| `2662d542-7776-4b8f-acc9-f4a99898021f` | Материнская плата | Ремонт платы, микросхем |
| `a09935aa-4a39-45b5-9661-c199d64900bd` | ПО (софт) | Прошивка, разблокировка, настройка |
| `e719da68-2b6a-4caa-bf32-6f5318c5d603` | Прочее | Другие неисправности |

## Примеры использования

### Пример 1: Одно устройство, одна неисправность

**Input:**
```json
{
  "current_message": "Привет, у меня iPhone 13, разбит экран"
}
```

**Output:**
```json
{
  "is_multi_device": false,
  "device_count": 1,
  "devices": [
    {
      "brand": "Apple",
      "model": "iPhone 13",
      "repairs": [
        {
          "category_name": "Дисплей",
          "symptoms": "разбит экран"
        }
      ]
    }
  ]
}
```

### Пример 2: Одно устройство, несколько неисправностей

**Input:**
```json
{
  "current_message": "Айфон 14 про - батарея быстро садится, экран разбит и камера не фокусируется"
}
```

**Output:**
```json
{
  "is_multi_device": false,
  "device_count": 1,
  "devices": [
    {
      "brand": "Apple",
      "model": "iPhone 14 Pro",
      "repairs": [
        {
          "category_name": "Аккумулятор",
          "symptoms": "батарея быстро садится"
        },
        {
          "category_name": "Дисплей",
          "symptoms": "экран разбит"
        },
        {
          "category_name": "Камера",
          "symptoms": "камера не фокусируется"
        }
      ]
    }
  ]
}
```

### Пример 3: Несколько устройств

**Input:**
```json
{
  "current_message": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается"
}
```

**Output:**
```json
{
  "is_multi_device": true,
  "device_count": 2,
  "devices": [
    {
      "brand": "Apple",
      "model": "iPhone 14",
      "repairs": [
        {
          "category_name": "Аккумулятор",
          "symptoms": "батарея"
        },
        {
          "category_name": "Дисплей",
          "symptoms": "разбит экран"
        }
      ]
    },
    {
      "brand": "Samsung",
      "model": "Galaxy S23",
      "repairs": [
        {
          "category_name": "Порты",
          "symptoms": "не заряжается"
        }
      ]
    }
  ]
}
```

## Интеграция с Appeal Router

После получения данных от Multi-Entity Extractor, Appeal Router должен:

1. **Обновить флаг is_multi_appeal**:
```sql
UPDATE appeals
SET is_multi_appeal = true
WHERE id = :appeal_id;
```

2. **Вызвать Multi-Appeal API**:
```http
PUT https://n8n.n8nsrv.ru/webhook/api/operator/appeals/:appeal_id/devices
Content-Type: application/json

{
  "devices": [ /* данные из Multi-Entity Extractor */ ]
}
```

3. **Multi-Appeal API автоматически создаст**:
   - Записи в `appeal_devices` (одна на каждое устройство)
   - Записи в `appeal_repairs` (одна на каждую неисправность каждого устройства)

## Обработка ошибок

### Ошибка парсинга JSON
Если Claude AI вернул невалидный JSON, выбрасывается ошибка:
```
Failed to parse AI response as JSON: {error message}
```

### Отсутствие devices
Если в ответе нет массива `devices`, выбрасывается ошибка:
```
AI response does not contain devices array
```

### Бренд не найден
Если бренд не найден в БД, `brand_id` будет `null`, но `brand` (текстовое название) сохранится.

### Категория не найдена
Если категория не найдена, используется категория "Прочее":
```
category_id = "e719da68-2b6a-4caa-bf32-6f5318c5d603"
```

## Деплой

### 1. Импорт workflow в n8n

```bash
# Скопировать файл на сервер
scp workflows/BAT\ AI\ Multi-Entity\ Extractor.json user@server:/tmp/

# На сервере импортировать через n8n UI
# Settings -> Import Workflow -> Select File
```

### 2. Настройка Credentials

Убедитесь, что настроен **Anthropic API Key**:
- ID: `anthropic-api-key`
- Type: `Header Auth`
- Header Name: `x-api-key`
- Header Value: `sk-ant-...` (ваш ключ от Anthropic)

Также нужен **Anthropic-Version** header:
- Header Name: `anthropic-version`
- Header Value: `2023-06-01`

### 3. Получение Workflow ID

После импорта скопируйте Workflow ID из URL в n8n:
```
https://n8n.n8nsrv.ru/workflow/XXXXX
                              ^^^^^
                              Workflow ID
```

### 4. Интеграция в Appeal Router

В workflow **BAT AI Appeal Router** добавьте вызов Multi-Entity Extractor:

```json
{
  "parameters": {
    "workflowId": "XXXXX",  // ID из шага 3
    "options": {}
  },
  "type": "n8n-nodes-base.executeWorkflow",
  "name": "Call Multi-Entity Extractor"
}
```

## Тестирование

### Тест 1: Простая заявка
**Input:**
```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "test-appeal-uuid",
  "current_message": "iPhone 13, разбит экран",
  "message_history": []
}
```

**Expected Output:**
- 1 устройство
- 1 неисправность
- `is_multi_device = false`

### Тест 2: Мультизаявка
**Input:**
```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "test-appeal-uuid",
  "current_message": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается",
  "message_history": []
}
```

**Expected Output:**
- 2 устройства
- 3 неисправности
- `is_multi_device = true`

## Мониторинг

### Метрики для отслеживания
- Количество вызовов Multi-Entity Extractor
- Процент мультизаявок (is_multi_device = true)
- Среднее количество устройств на заявку
- Среднее количество неисправностей на устройство
- Время выполнения workflow
- Частота ошибок парсинга

### Логирование

Все этапы workflow логируются в n8n:
- Загрузка справочников (brands, models, categories)
- Построение промпта
- Вызов Claude AI
- Парсинг ответа
- Маппинг на DB IDs

## Стоимость

**Claude 3.5 Sonnet:**
- Input: $3 за 1M токенов
- Output: $15 за 1M токенов

**Средний запрос:**
- Input: ~1500 токенов (промпт + контекст)
- Output: ~500 токенов (JSON ответ)
- **Стоимость:** ~$0.012 за запрос

**Месячная стоимость** (при 1000 заявок/месяц):
- ~$12/месяц

## Будущие улучшения

1. **Кеширование справочников** — не загружать brands/models/categories на каждый запрос
2. **Поддержка изображений** — извлечение информации из фото устройства
3. **Confidence Score** — оценка уверенности AI в извлеченных данных
4. **Автоматическая коррекция** — исправление опечаток в названиях моделей
5. **Поддержка других языков** — английский, испанский и т.д.

## FAQ

**Q: Что если клиент написал неправильное название модели?**
A: Claude AI попытается нормализовать название. Если не найдет в БД, вернет текстовое название (model_id = null).

**Q: Что если клиент не указал бренд?**
A: brand и brand_id будут null. Оператор заполнит вручную.

**Q: Работает ли с несколькими языками?**
A: Сейчас оптимизировано для русского, но Claude AI понимает и другие языки.

**Q: Сколько времени занимает обработка?**
A: ~2-4 секунды (зависит от нагрузки на Claude AI API).

**Q: Можно ли использовать другую AI модель?**
A: Да, промпт универсальный. Можно использовать GPT-4, Gemini и др.

## Контакты

**Автор:** Claude Code + User
**Дата создания:** 2025-11-23
**Версия:** 1.0

**Endpoints:**
- n8n UI: https://n8n.n8nsrv.ru
- PostgreSQL: 185.221.214.83:6544

**Связанные файлы:**
- `workflows/BAT AI Multi-Entity Extractor.json`
- `prompts/multi_entity_extractor_prompt.md`
- `MULTI_APPEAL_API_DOCUMENTATION.md`
