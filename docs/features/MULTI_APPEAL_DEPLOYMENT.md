# Multi-Appeal API Deployment Guide

**Дата:** 2025-11-23
**Версия:** 1.0

---

## 📋 Что было создано

### 3 n8n Workflows

1. **API_Multi_Appeal_Update_Devices.json**
   - Endpoint: `PUT /api/operator/appeals/:id/devices`
   - Создание/обновление устройств и ремонтов

2. **API_Multi_Appeal_Get_Devices.json**
   - Endpoint: `GET /api/operator/appeals/:id/devices`
   - Получение всех устройств и ремонтов заявки

3. **API_Multi_Appeal_Delete_Device.json**
   - Endpoint: `DELETE /api/operator/appeals/:id/devices/:device_id`
   - Удаление устройства из мультизаявки

### 1 Документация

- **MULTI_APPEAL_API_DOCUMENTATION.md** — полная документация API

---

## 🚀 Как задеплоить

### Вариант 1: Через n8n UI (рекомендуется)

1. Открыть n8n: `http://localhost:5678` или `https://your-domain.com`

2. Импортировать workflows:
   ```
   - Workflows → Import from File
   - Выбрать: workflows/API_Multi_Appeal_Update_Devices.json
   - Activate workflow

   - Повторить для API_Multi_Appeal_Get_Devices.json
   - Повторить для API_Multi_Appeal_Delete_Device.json
   ```

3. Проверить credentials:
   - Все workflows используют PostgreSQL credential `Postgres account` (ID: `n2SyhP9QhMnp1ryk`)
   - Убедиться, что credential настроен корректно

4. Активировать workflows:
   - Нажать "Activate" для каждого workflow
   - Убедиться, что статус "Active" (зелёная галочка)

### Вариант 2: Через API

```bash
# Получить access token n8n
export N8N_API_KEY="your-api-key"

# Импортировать workflows
for file in workflows/API_Multi_Appeal_*.json; do
  curl -X POST http://localhost:5678/api/v1/workflows \
    -H "X-N8N-API-KEY: $N8N_API_KEY" \
    -H "Content-Type: application/json" \
    -d @"$file"
done
```

### Вариант 3: Автоматический скрипт

Используйте существующий скрипт:

```bash
python update_workflow_auto.py
```

---

## ✅ Проверка работоспособности

### 1. Проверить, что workflows активны

```bash
# Через n8n UI
Workflows → All workflows → Проверить статус (зелёная галочка)
```

### 2. Тестовый запрос

```bash
# Получить session_token из БД
psql -U postgres -d batterycrm -c "SELECT session_token FROM operator_devices WHERE is_active = true LIMIT 1;"

# Тестовый GET запрос
curl -X GET "http://localhost:5678/webhook/api/operator/appeals/YOUR_APPEAL_ID/devices" \
  -H "X-Session-Token: YOUR_SESSION_TOKEN"

# Ожидаемый результат: 200 OK с данными или 404 если заявка не найдена
```

### 3. Проверить webhook URLs

В n8n должны быть доступны следующие endpoints:

```
PUT    /webhook/api/operator/appeals/:appeal_id/devices
GET    /webhook/api/operator/appeals/:appeal_id/devices
DELETE /webhook/api/operator/appeals/:appeal_id/devices/:device_id
```

Проверить:
```bash
# Список всех webhook endpoints
curl http://localhost:5678/api/v1/workflows \
  -H "X-N8N-API-KEY: your-api-key" | jq '.data[].nodes[] | select(.type == "n8n-nodes-base.webhook") | .parameters.path'
```

---

## 🔧 Troubleshooting

### Проблема: "Workflow not found"

**Решение:**
1. Проверить, что workflows импортированы
2. Проверить, что workflows активированы
3. Перезапустить n8n: `docker restart n8n` или `pm2 restart n8n`

### Проблема: "401 Unauthorized"

**Решение:**
1. Проверить session_token в запросе
2. Проверить, что оператор активен:
   ```sql
   SELECT * FROM operator_devices WHERE session_token = 'your-token';
   SELECT * FROM operators WHERE id = 'operator-id' AND is_active = true;
   ```

### Проблема: "Database connection error"

**Решение:**
1. Проверить PostgreSQL credentials в n8n:
   ```
   Settings → Credentials → Postgres account
   ```
2. Проверить подключение к БД:
   ```bash
   psql -h localhost -U postgres -d batterycrm -c "SELECT 1;"
   ```

### Проблема: "Invalid appeal_id"

**Решение:**
- Appeal ID должен быть в формате UUID: `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`
- Проверить существование заявки:
  ```sql
  SELECT id FROM appeals WHERE id = 'your-appeal-id';
  ```

---

## 📊 Мониторинг

### Логи workflows

```bash
# n8n логи
docker logs n8n -f

# Или через pm2
pm2 logs n8n
```

### Проверить executions

```
n8n UI → Executions → Фильтр по workflow name
```

### Метрики

```sql
-- Количество мультизаявок
SELECT COUNT(*) FROM appeals WHERE is_multi_appeal = true;

-- Среднее количество устройств
SELECT AVG(device_count) FROM (
  SELECT appeal_id, COUNT(*) as device_count
  FROM appeal_devices
  GROUP BY appeal_id
) sub;

-- Среднее количество ремонтов на устройство
SELECT AVG(repair_count) FROM (
  SELECT appeal_device_id, COUNT(*) as repair_count
  FROM appeal_repairs
  GROUP BY appeal_device_id
) sub;
```

---

## 🔄 Обновление workflows

### Если нужно изменить workflow:

1. Экспортировать текущий workflow из n8n
2. Отредактировать JSON файл
3. Удалить старый workflow через UI
4. Импортировать обновлённый workflow
5. Активировать

**Или через API:**

```bash
# Получить ID workflow
WORKFLOW_ID=$(curl http://localhost:5678/api/v1/workflows \
  -H "X-N8N-API-KEY: $N8N_API_KEY" \
  | jq -r '.data[] | select(.name == "API Multi-Appeal Update Devices") | .id')

# Обновить workflow
curl -X PUT "http://localhost:5678/api/v1/workflows/$WORKFLOW_ID" \
  -H "X-N8N-API-KEY: $N8N_API_KEY" \
  -H "Content-Type: application/json" \
  -d @workflows/API_Multi_Appeal_Update_Devices.json
```

---

## 📈 Производительность

### Ожидаемая производительность:

- **PUT (создание/обновление):** ~500-800ms для 2-3 устройств
- **GET (получение):** ~100-200ms
- **DELETE (удаление):** ~150-300ms

### Оптимизация:

1. Индексы уже созданы на всех FK
2. Используются транзакции для атомарности
3. CASCADE удаление для автоматической очистки

---

## 🎯 Следующие шаги

После деплоя Multi-Appeal API:

1. ⏭️ Реализовать Promo Materials API
2. ⏭️ Реализовать Pricing Engine API
3. ⏭️ Обновить Android app для поддержки мультизаявок
4. ⏭️ Обновить Desktop app (аналитика по мультизаявкам)

---

## 📞 Поддержка

При возникновении проблем:

1. Проверить логи n8n
2. Проверить executions в n8n UI
3. Проверить PostgreSQL логи
4. Обратиться к документации: `MULTI_APPEAL_API_DOCUMENTATION.md`

---

**Дата создания:** 2025-11-23
**Статус:** Готово к деплою
