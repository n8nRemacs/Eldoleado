# 🚀 Настройка среды разработки BatteryCRM на новом компьютере

## 📋 Пререквизиты

### Что нужно установить ПЕРЕД запуском скрипта:

1. **VSCode** - https://code.visualstudio.com/
2. **Claude Code** (расширение для VSCode) - https://code.claude.com/
3. **Python 3.8+** - https://www.python.org/downloads/
4. **Node.js 18+** - https://nodejs.org/
5. **Git** - https://git-scm.com/downloads/
6. **(Опционально) Android Studio** - https://developer.android.com/studio

### Windows дополнительно:
- **Git for Windows** (с Git Bash) - https://gitforwindows.org/

### macOS дополнительно:
- **Homebrew** - `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`

---

## 🎯 Быстрый старт

### Шаг 1: Клонировать репозиторий

```bash
git clone https://github.com/n8nRemacs/batterycrm.git
cd batterycrm
```

### Шаг 2: Запустить скрипт установки

```bash
python setup_dev_environment.py
```

Скрипт автоматически:
- ✅ Установит все MCP серверы
- ✅ Настроит подключения к PostgreSQL
- ✅ Создаст SSH конфигурацию
- ✅ Настроит Git
- ✅ Создаст .env файл с credentials
- ✅ Протестирует все подключения

### Шаг 3: Перезапустить Claude Code

После установки перезапустите VSCode/Claude Code чтобы MCP серверы загрузились.

### Шаг 4: Проверить установку

```bash
claude mcp list
```

Должно показать:
```
✓ mcpandroidbuild: Connected
✓ memory: Connected
✓ mobile-mcp: Connected
✓ postgres-batterycrm: Connected
```

---

## 🔧 Ручная установка (если скрипт не сработал)

### 1. Установить Python пакеты

```bash
pip install mcpandroidbuild requests psycopg2-binary python-dotenv
```

### 2. Установить NPM пакеты

```bash
npm install -g mobile-mcp @henkey/postgres-mcp-server
```

### 3. Настроить Claude Code MCP

Отредактировать `~/.claude.json` (Windows: `C:\Users\<User>\.claude.json`):

```json
{
  "projects": {
    "путь_к_проекту": {
      "mcpServers": {
        "mcpandroidbuild": {
          "command": "python",
          "args": ["-m", "mcpandroidbuild"]
        },
        "memory": {
          "command": "npx",
          "args": ["-y", "@modelcontextprotocol/server-memory"]
        },
        "mobile-mcp": {
          "type": "stdio",
          "command": "npx",
          "args": ["mobile-mcp"],
          "env": {
            "ANDROID_SDK_ROOT": "путь_к_android_sdk"
          }
        },
        "postgres-batterycrm": {
          "type": "stdio",
          "command": "npx",
          "args": ["@henkey/postgres-mcp-server"],
          "env": {
            "POSTGRES_CONNECTION_STRING": "postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres"
          }
        }
      }
    }
  }
}
```

### 4. Создать .env файл

Создать `.env` в корне проекта:

```env
# n8n Configuration
N8N_URL=https://n8n.n8nsrv.ru
N8N_API_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M

# Supabase Configuration
SUPABASE_URL=https://supabase.n8nsrv.ru
SUPABASE_SERVICE_ROLE_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0
POSTGRES_CONNECTION_STRING=postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres

# Servers
VPN_SERVER_HOST=217.145.79.27
VPN_SERVER_USER=root
VPN_SERVER_PASSWORD=Mi31415926pSss!

N8N_SERVER_HOST=185.221.214.83
N8N_SERVER_USER=root
N8N_SERVER_PASSWORD=Mi31415926pSss!
```

**⚠️ ВАЖНО: Не коммитить .env в Git!**

### 5. Настроить SSH

Создать `~/.ssh/config`:

```
# VPN Server (Trojan, Vless, OpenVPN)
Host vpn-server
    HostName 217.145.79.27
    User root
    Port 22

# n8n + Supabase Server
Host n8n-server
    HostName 185.221.214.83
    User root
    Port 22
```

Теперь можно подключаться:
```bash
ssh vpn-server
# или
ssh n8n-server
```

---

## 🖥️ Информация о серверах

### Сервер VPN (Trojan, Vless, OpenVPN)
- **IP**: 217.145.79.27
- **User**: root
- **Password**: Mi31415926pSss!
- **Сервисы**: Trojan, Vless, OpenVPN

### Сервер n8n + Supabase
- **IP**: 185.221.214.83
- **User**: root
- **Password**: Mi31415926pSss!
- **Сервисы**:
  - n8n: https://n8n.n8nsrv.ru
  - Supabase: https://supabase.n8nsrv.ru
  - PostgreSQL: db.cfaelqdjknmopodvxmyw.supabase.co:5432

---

## 🔐 Учетные данные сервисов

### n8n API
- **URL**: https://n8n.n8nsrv.ru
- **API Key**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M`

Тест подключения:
```bash
curl -X GET "https://n8n.n8nsrv.ru/api/v1/workflows" \
  -H "X-N8N-API-KEY: YOUR_API_KEY" \
  -H "Accept: application/json"
```

### Supabase
- **URL**: https://supabase.n8nsrv.ru
- **Service Role Key**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0`
- **PostgreSQL**: `postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres`

Тест подключения:
```bash
python supabase_manager.py list
```

### GitHub
- **Repository**: https://github.com/n8nRemacs/batterycrm

---

## 🧪 Проверка установки

### 1. Проверить MCP серверы

```bash
claude mcp list
```

### 2. Проверить доступ к n8n

```bash
curl -s -X GET "https://n8n.n8nsrv.ru/api/v1/workflows" \
  -H "X-N8N-API-KEY: YOUR_KEY" | python -m json.tool
```

Или:
```bash
python n8n_manager.py list
```

### 3. Проверить доступ к Supabase

```bash
python supabase_manager.py list
```

Должно показать 26 таблиц.

### 4. Проверить SSH доступ

```bash
ssh vpn-server "echo 'VPN Server OK'"
ssh n8n-server "echo 'n8n Server OK'"
```

### 5. Проверить Android SDK (если установлено)

Windows:
```cmd
dir "%LOCALAPPDATA%\Android\Sdk"
```

macOS/Linux:
```bash
ls ~/Library/Android/sdk  # macOS
ls ~/Android/Sdk          # Linux
```

---

## 📱 Android Studio Setup

### Установка Android Studio

1. Скачать: https://developer.android.com/studio
2. Установить
3. Запустить Android Studio
4. SDK Manager → Install:
   - Android SDK Platform-Tools
   - Android SDK Build-Tools
   - Android SDK Platform (API 33+)
   - Android Emulator

### Путь к SDK

После установки обновить `.claude.json` с правильным путём:

**Windows**: `C:\Users\<User>\AppData\Local\Android\Sdk`
**macOS**: `~/Library/Android/sdk`
**Linux**: `~/Android/Sdk`

---

## 🛠️ Полезные команды

### Python инструменты проекта

```bash
# Список таблиц Supabase
python supabase_manager.py list

# Экспорт схемы БД
python supabase_manager.py export

# Список n8n workflows
python n8n_manager.py list

# Скачать workflow
python n8n_manager.py get <workflow_id>

# Обновить workflow
python n8n_manager.py update <workflow_id> <file.json>
```

### Git workflow

```bash
# Синхронизация с GitHub
git pull origin main
git add .
git commit -m "Your message"
git push origin main

# Проверить статус
git status

# История
git log --oneline
```

### SSH подключения

```bash
# Через alias
ssh vpn-server
ssh n8n-server

# Прямое подключение
ssh root@217.145.79.27
ssh root@185.221.214.83

# С командой
ssh vpn-server "systemctl status trojan"
ssh n8n-server "docker ps"
```

---

## 🔍 Troubleshooting

### MCP сервер не подключается

**Проблема**: `✗ Failed to connect`

**Решения**:
1. Проверить установку: `pip list | grep mcpandroidbuild`
2. Проверить установку: `npm list -g | grep mobile-mcp`
3. Перезапустить VSCode/Claude Code
4. Проверить `.claude.json` на ошибки JSON

### SSH не работает

**Проблема**: Connection refused или timeout

**Решения**:
1. Проверить интернет подключение
2. Проверить нужен ли VPN для доступа
3. Проверить firewall
4. Ping сервер: `ping 217.145.79.27`

### PostgreSQL connection error

**Проблема**: Connection timeout

**Решение**:
- Использовать REST API вместо прямого подключения
- Проверить работает ли: `python supabase_manager.py list`

### Android SDK не найден

**Проблема**: `ANDROID_SDK_ROOT not found`

**Решения**:
1. Установить Android Studio
2. Обновить путь в `.claude.json`
3. Установить переменную окружения:
   - Windows: `setx ANDROID_SDK_ROOT "C:\Users\User\AppData\Local\Android\Sdk"`
   - macOS/Linux: `export ANDROID_SDK_ROOT=~/Library/Android/sdk`

---

## 📚 Документация

После настройки прочитайте:

1. **[Start.md](Start.md)** - Контекст проекта
2. **[MCP_COMPLETE_SETUP.md](MCP_COMPLETE_SETUP.md)** - Полная документация MCP
3. **[Database_Structure_BatteryCRM_COMPLETE.md](Database_Structure_BatteryCRM_COMPLETE.md)** - Структура БД
4. **[Eldoleado full.md](Eldoleado%20full.md)** - Полная архитектура проекта

---

## ✅ Чеклист готовности

После установки проверьте:

- [ ] VSCode установлен
- [ ] Claude Code расширение установлено
- [ ] Python 3.8+ установлен
- [ ] Node.js 18+ установлен
- [ ] Git установлен
- [ ] Репозиторий клонирован
- [ ] `setup_dev_environment.py` выполнен успешно
- [ ] `claude mcp list` показывает 4 подключенных сервера
- [ ] `python supabase_manager.py list` работает
- [ ] SSH подключение к серверам работает
- [ ] .env файл создан (и добавлен в .gitignore!)
- [ ] Android SDK настроен (если нужна Android разработка)

---

## 🎉 Готово!

Теперь у вас полностью настроенная среда разработки BatteryCRM:

✅ Доступ к базе данных через MCP
✅ Управление Android проектом
✅ Доступ к n8n workflows
✅ SSH подключения к серверам
✅ Git синхронизация с GitHub
✅ Все credentials настроены

**Happy Coding! 🚀**

---

## 📞 Помощь

Если что-то не работает:
1. Проверьте раздел Troubleshooting выше
2. Проверьте логи: `claude mcp list --verbose`
3. Прочитайте документацию в `MCP_COMPLETE_SETUP.md`
