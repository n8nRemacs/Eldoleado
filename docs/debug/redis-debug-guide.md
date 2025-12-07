# Руководство по отладке Redis в n8n workflows

**Дата:** 2025-12-07

---

## Инструменты

### Redis Insight (веб-интерфейс)

**URL:** http://185.221.214.83:5540

Графический интерфейс для просмотра и редактирования данных в Redis.

### CLI команды

```bash
# Подключение к Redis (45.144.177.128)
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! <command>'
```

---

## Базовые команды

```bash
# Все ключи
KEYS "*"

# Ключи по паттерну
KEYS "queue:*"
KEYS "*telegram*"

# Тип ключа (ВАЖНО!)
TYPE "queue:batch:telegram:tg_123"
# Результаты: string, list, set, hash, zset

# Для string
GET "last_seen:telegram:tg_123"

# Для list
LLEN "queue:batch:telegram:tg_123"        # длина
LRANGE "queue:batch:telegram:tg_123" 0 -1 # все элементы

# Удаление
DEL "queue:batch:telegram:tg_123"
```

---

## Процесс отладки n8n workflow

### Методология: пошаговая верификация

1. **Я запускаю один поток (execution) в n8n**
2. **Claude проверяет состояние Redis после каждой ноды**
3. **Сначала выясняем и верифицируем проблему точно**
4. **Потом правим**

### Шаги

#### 1. Перед запуском — начальное состояние

```bash
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! KEYS "*"'
```

Запомнить какие ключи есть.

#### 2. Запустить workflow (один цикл)

Пользователь запускает вручную в n8n UI.

#### 3. После выполнения — проверить изменения

```bash
# Что изменилось?
KEYS "*"

# Если нода должна была записать в list
LLEN "queue:batch:..."
LRANGE "queue:batch:..." 0 -1

# Если нода должна была записать string
GET "last_seen:..."
```

#### 4. Сравнить ожидаемое vs реальное

| Нода | Ожидание | Реальность |
|------|----------|------------|
| Push to Queue | Добавит в list | ? |
| Set Last Seen | Запишет string | ? |
| Pop Message | Удалит из list | ? |

#### 5. Найти расхождение

Если нода показывает output = input (данные прошли насквозь) — операция не выполнилась.

#### 6. Проверить типы данных

**Частая ошибка:** GET для list или PUSH когда ожидается SET.

```bash
TYPE "queue:batch:telegram:tg_123"
# Если list — используй LRANGE/POP
# Если string — используй GET
```

---

## Типичные проблемы

### 1. GET возвращает NULL для list

**Симптом:** Нода Get Batch Queue возвращает `{ "value": null }`
**Причина:** Ключ имеет тип `list`, а GET работает только для `string`
**Решение:** Заменить GET на POP или LRANGE

### 2. PUSH не работает

**Симптом:** После PUSH ключ не появляется
**Проверка:**
```bash
TYPE "key"  # Если string — PUSH не создаст list
```
**Причина:** Возможно n8n Redis node баг или неверные параметры

### 3. Данные исчезают

**Симптом:** Ключи были, потом пропали
**Причина:** Какая-то нода делает DELETE или POP
**Решение:** Проверить все ноды с операциями delete/pop

---

## Полезные скрипты

### Очистить все очереди (осторожно!)

```bash
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! KEYS "queue:*" | xargs -I {} docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! DEL {}'
```

### Мониторинг в реальном времени

```bash
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! MONITOR'
```

Показывает все команды в реальном времени. Ctrl+C для выхода.

---

## Ключи проекта

| Паттерн | Тип | Описание |
|---------|-----|----------|
| `queue:incoming` | list | Входящие сообщения |
| `queue:batch:{channel}:{chat_id}` | list | Батч сообщений для чата |
| `queue:debounce:pending` | list | Ожидающие debounce задачи |
| `lock:batch:{channel}:{chat_id}` | string | Лок на обработку батча |
| `last_seen:{channel}:{chat_id}` | string | Время последнего сообщения |
