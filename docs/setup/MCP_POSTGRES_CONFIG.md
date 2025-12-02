# Настройка MCP PostgreSQL Server

## Описание
MCP PostgreSQL Server - это Model Context Protocol сервер для взаимодействия Claude Code с базами данных PostgreSQL. Позволяет выполнять SQL-запросы, управлять схемой, анализировать производительность и многое другое.

## Правильная настройка

### 1. Установка через Claude CLI

```bash
# Установка MCP сервера
claude mcp add postgres-batterycrm npx -y @batterycrm/mcp-postgres

# Проверка установки
claude mcp list
```

### 2. Конфигурация в settings.local.json

Файл: `.claude/settings.local.json`

```json
{
  "mcpServers": {
    "postgres-batterycrm": {
      "command": "npx",
      "args": ["-y", "@batterycrm/mcp-postgres"],
      "env": {
        "POSTGRES_CONNECTION_STRING": "postgresql://user:password@host:port/database"
      }
    }
  }
}
```

### 3. Формат строки подключения

#### Базовый формат:
```
postgresql://username:password@host:port/database
```

#### Примеры:

**Localhost:**
```
postgresql://postgres:mypassword@localhost:5432/mydatabase
```

**Supabase:**
```
postgresql://postgres.xxxxxxxxxxxxx:password@aws-0-region.pooler.supabase.com:6543/postgres
```

**AWS RDS:**
```
postgresql://username:password@myinstance.abc123.us-east-1.rds.amazonaws.com:5432/postgres
```

**С SSL:**
```
postgresql://user:password@host:5432/db?sslmode=require
```

### 4. Параметры подключения

Дополнительные параметры в строке подключения:

| Параметр | Значения | Описание |
|----------|----------|----------|
| `sslmode` | `disable`, `require`, `verify-ca`, `verify-full` | Режим SSL |
| `connect_timeout` | число (секунды) | Таймаут подключения |
| `application_name` | строка | Имя приложения в логах |
| `schema` | строка | Схема по умолчанию |

Пример:
```
postgresql://user:pass@host:5432/db?sslmode=require&connect_timeout=10&application_name=claude-mcp
```

## Возможные проблемы и решения

### Проблема 1: MCP сервер не запускается

**Симптомы:**
- Claude Code не видит инструменты postgres
- Ошибка при вызове `mcp__postgres-*` функций

**Причины и решения:**

1. **npx не установлен**
   ```bash
   # Проверка
   npx --version

   # Установка Node.js (включает npx)
   # Скачать с https://nodejs.org/
   ```

2. **Неверная конфигурация в settings.local.json**
   ```bash
   # Проверить синтаксис JSON
   # Убедиться что нет лишних запятых
   # Проверить кавычки (должны быть двойные)
   ```

3. **Перезапуск Claude Code**
   ```bash
   # После изменения конфигурации нужен рестарт
   # Закрыть все окна Claude Code
   # Открыть заново
   ```

### Проблема 2: Ошибка подключения к БД

**Симптомы:**
- "Connection refused"
- "Authentication failed"
- "Database does not exist"

**Решения:**

1. **Проверка строки подключения**
   ```bash
   # Тест подключения через psql
   psql "postgresql://user:password@host:port/database"
   ```

2. **Проблемы с паролем**
   - Специальные символы должны быть закодированы (URL encoding):
     - `@` → `%40`
     - `:` → `%3A`
     - `/` → `%2F`
     - `?` → `%3F`

   Пример:
   ```
   # Пароль: my@pass:word
   # Правильно: my%40pass%3Aword
   postgresql://user:my%40pass%3Aword@host:5432/db
   ```

3. **Firewall/Network**
   ```bash
   # Проверка доступности хоста
   ping host

   # Проверка порта
   telnet host 5432
   # или
   Test-NetConnection -ComputerName host -Port 5432
   ```

4. **SSL требуется, но не указан**
   ```
   # Добавить sslmode=require
   postgresql://user:pass@host:5432/db?sslmode=require
   ```

### Проблема 3: Недостаточно прав доступа

**Симптомы:**
- "Permission denied"
- "Must be owner of table"
- "Must have CREATEDB privilege"

**Решения:**

1. **Проверка прав пользователя**
   ```sql
   -- Проверить текущего пользователя и его права
   SELECT current_user, current_database();

   -- Проверить роли
   SELECT rolname, rolsuper, rolcreatedb, rolcreaterole
   FROM pg_roles
   WHERE rolname = current_user;
   ```

2. **Выдача необходимых прав**
   ```sql
   -- Права на схему
   GRANT ALL ON SCHEMA public TO username;

   -- Права на все таблицы
   GRANT ALL ON ALL TABLES IN SCHEMA public TO username;

   -- Права на последовательности
   GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO username;

   -- Права на создание БД (для суперпользователя)
   ALTER USER username CREATEDB;
   ```

3. **Использование суперпользователя** (только для dev окружения)
   ```
   postgresql://postgres:password@localhost:5432/database
   ```

### Проблема 4: Таймауты при выполнении запросов

**Симптомы:**
- "Query timeout"
- "Statement timeout"
- Долгое выполнение запросов

**Решения:**

1. **Увеличить таймаут в строке подключения**
   ```
   postgresql://user:pass@host:5432/db?connect_timeout=30&statement_timeout=60000
   ```

2. **Использовать LIMIT для больших выборок**
   ```sql
   -- Вместо
   SELECT * FROM big_table;

   -- Использовать
   SELECT * FROM big_table LIMIT 1000;
   ```

3. **Создать индексы для частых запросов**
   ```sql
   CREATE INDEX idx_appeals_status ON appeals(status);
   CREATE INDEX idx_messages_created ON messages(created_at);
   ```

### Проблема 5: Версия PostgreSQL не поддерживается

**Симптомы:**
- "Feature not supported"
- "Syntax error" при использовании новых функций

**Решения:**

1. **Проверка версии**
   ```sql
   SELECT version();
   ```

2. **Минимальная версия**: PostgreSQL 12+
   Если версия ниже - обновить БД

3. **Использовать совместимый синтаксис**
   - Избегать JSON операторов из PG 14+ если у вас PG 12
   - Проверять документацию для вашей версии

### Проблема 6: Конфликт с другими MCP серверами

**Симптомы:**
- MCP сервер не загружается
- Конфликты имен инструментов

**Решения:**

1. **Уникальные имена серверов**
   ```json
   {
     "mcpServers": {
       "postgres-batterycrm": { ... },
       "postgres-other": { ... }
     }
   }
   ```

2. **Проверка логов Claude Code**
   - Смотреть на ошибки при старте
   - Проверить что все MCP серверы запустились

### Проблема 7: Переменные окружения не читаются

**Симптомы:**
- "Connection string not provided"
- Пустая строка подключения

**Решения:**

1. **Указать напрямую в args**
   ```json
   {
     "mcpServers": {
       "postgres-batterycrm": {
         "command": "npx",
         "args": [
           "-y",
           "@batterycrm/mcp-postgres",
           "--connection-string",
           "postgresql://user:pass@host:5432/db"
         ]
       }
     }
   }
   ```

2. **Использовать env правильно**
   ```json
   {
     "mcpServers": {
       "postgres-batterycrm": {
         "command": "npx",
         "args": ["-y", "@batterycrm/mcp-postgres"],
         "env": {
           "POSTGRES_CONNECTION_STRING": "postgresql://..."
         }
       }
     }
   }
   ```

## Проверка работоспособности

### Тест 1: Простой SELECT
```sql
SELECT current_database(), current_user, version();
```

### Тест 2: Список таблиц
```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public';
```

### Тест 3: Выполнение через MCP инструменты

В Claude Code попросить:
```
Выполни запрос: SELECT count(*) FROM appeals;
```

Если все работает - конфигурация верна!

## Рекомендации по безопасности

### 1. Для продакшена
- ❌ НЕ использовать суперпользователя
- ✅ Создать отдельного пользователя с минимальными правами
- ✅ Использовать SSL (`sslmode=require`)
- ✅ Ограничить IP адреса в pg_hba.conf
- ✅ Использовать сложные пароли

### 2. Для разработки
- ✅ Можно использовать localhost без SSL
- ✅ Хранить credentials в .env файле (не в git!)
- ✅ Использовать .gitignore для .claude/settings.local.json

### 3. Защита credentials

**Не хранить пароли в коде!**

Вариант 1: Использовать переменные системы
```json
{
  "mcpServers": {
    "postgres-batterycrm": {
      "command": "npx",
      "args": ["-y", "@batterycrm/mcp-postgres"],
      "env": {
        "POSTGRES_CONNECTION_STRING": "${POSTGRES_URL}"
      }
    }
  }
}
```

Вариант 2: Файл .env
```bash
# .env
POSTGRES_URL=postgresql://user:pass@host:5432/db
```

## Дополнительные возможности

### 1. Множественные подключения
```json
{
  "mcpServers": {
    "postgres-production": {
      "command": "npx",
      "args": ["-y", "@batterycrm/mcp-postgres"],
      "env": {
        "POSTGRES_CONNECTION_STRING": "postgresql://prod..."
      }
    },
    "postgres-staging": {
      "command": "npx",
      "args": ["-y", "@batterycrm/mcp-postgres"],
      "env": {
        "POSTGRES_CONNECTION_STRING": "postgresql://stage..."
      }
    }
  }
}
```

### 2. Connection pooling
Для production рекомендуется использовать pgBouncer или аналог:
```
postgresql://user:pass@pgbouncer-host:6432/db?pool_mode=transaction
```

## Полезные команды

```bash
# Переустановка MCP сервера
claude mcp remove postgres-batterycrm
claude mcp add postgres-batterycrm npx -y @batterycrm/mcp-postgres

# Список всех MCP серверов
claude mcp list

# Проверка конфигурации
cat .claude/settings.local.json

# Логи (если есть проблемы)
# Смотреть в консоль Claude Code при запуске
```

## Поддержка

Если проблема не решена:
1. Проверить логи Claude Code
2. Проверить логи PostgreSQL (pg_log)
3. Создать issue: https://github.com/anthropics/claude-code/issues
4. Проверить документацию MCP: https://modelcontextprotocol.io/

## Changelog

- 2025-01-22: Первая версия документа
