# 🎉 BatteryCRM - Автоматическая настройка среды разработки

## 📦 Что включено

Полный скрипт автоматической настройки **всей** среды разработки BatteryCRM на новом компьютере.

### Устанавливает и настраивает:

✅ **MCP серверы для Claude Code:**
- mcpandroidbuild - сборка Android проектов
- mobile-mcp - управление Android устройствами
- postgres-batterycrm - доступ к PostgreSQL
- memory - контекстная память

✅ **Подключения к серверам:**
- VPN Server (217.145.79.27) - Trojan, Vless, OpenVPN
- n8n/Supabase Server (185.221.214.83) - n8n, Supabase, PostgreSQL

✅ **Credentials и ключи:**
- Все API keys
- Пароли серверов
- SSH конфигурация
- .env файл

✅ **Git и GitHub:**
- Remote repository
- User config
- .gitignore

✅ **Тестирование:**
- Проверка всех подключений
- Проверка MCP серверов
- Проверка API

---

## 🚀 Использование

### Вариант 1: Полная автоматическая установка

```bash
# 1. Клонировать репозиторий
git clone https://github.com/n8nRemacs/batterycrm.git
cd batterycrm

# 2. Запустить скрипт установки
python setup_dev_environment.py

# 3. Перезапустить Claude Code
# Restart VSCode

# 4. Проверить всё
python test_connections.py
```

### Вариант 2: Только credentials

Если среда уже настроена, но нужны только credentials:

```bash
python setup_dev_environment.py
# При запросе выбрать только нужные шаги
```

---

## 📋 Файлы в комплекте

### Основные скрипты

| Файл | Назначение |
|------|-----------|
| `setup_dev_environment.py` | 🔧 Главный скрипт установки |
| `test_connections.py` | 🧪 Тестирование всех подключений |

### Документация

| Файл | Описание |
|------|----------|
| `SETUP_NEW_MACHINE.md` | 📖 Подробная инструкция установки |
| `QUICK_REFERENCE.md` | 📚 Шпаргалка команд |
| `MCP_COMPLETE_SETUP.md` | 🔌 Документация MCP серверов |
| `MCP_ANDROID_SETUP.md` | 📱 Android MCP setup |
| `MCP_POSTGRES_SETUP.md` | 🗄️ PostgreSQL MCP setup |

### Созданные файлы (не в Git)

| Файл | Содержимое | Коммитить? |
|------|-----------|------------|
| `.env` | Переменные окружения, API keys | ❌ НЕТ |
| `.dev_credentials` | Пароли серверов | ❌ НЕТ |
| `~/.ssh/config` | SSH алиасы | ✅ Можно |
| `~/.claude.json` | Claude Code MCP config | ❌ НЕТ |

---

## 🔐 Credentials (что получите)

### Серверы

**VPN Server** (Trojan, Vless, OpenVPN)
```
IP:       217.145.79.27
User:     root
Password: Mi31415926pSss!
```

**n8n + Supabase Server**
```
IP:       185.221.214.83
User:     root
Password: Mi31415926pSss!
```

### API Keys

**n8n**
```
URL:     https://n8n.n8nsrv.ru
API Key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Supabase**
```
URL:     https://supabase.n8nsrv.ru
Service Role Key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
PostgreSQL: postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres
```

**GitHub**
```
Repository: https://github.com/n8nRemacs/batterycrm
```

---

## 🎯 Что делает setup_dev_environment.py

### Шаги установки:

1. **Проверка зависимостей**
   - Python 3.8+
   - Node.js 18+
   - npm
   - git

2. **Установка Python пакетов**
   - requests
   - psycopg2-binary
   - mcpandroidbuild
   - python-dotenv

3. **Установка NPM пакетов**
   - mobile-mcp
   - @henkey/postgres-mcp-server

4. **Настройка MCP серверов**
   - Автопоиск Android SDK
   - Создание конфигурации Claude Code
   - Настройка переменных окружения

5. **SSH конфигурация**
   - Создание ~/.ssh/config
   - Алиасы для серверов
   - Хранение credentials

6. **Git setup**
   - Remote origin
   - User name/email
   - .gitignore обновление

7. **Создание .env**
   - Все API keys
   - Connection strings
   - Server credentials

8. **Тестирование**
   - n8n API
   - Supabase API
   - Ping серверов
   - MCP servers

---

## 🧪 Проверка установки

### test_connections.py проверяет:

✓ Python packages
✓ Git configuration
✓ .env file
✓ MCP servers
✓ n8n API
✓ Supabase API
✓ VPN Server (ping)
✓ n8n/Supabase Server (ping)
✓ Android SDK

### Запуск:

```bash
python test_connections.py
```

### Ожидаемый результат:

```
=========================================================
BatteryCRM Environment Test Suite
=========================================================

Testing Python packages...
  ✓ requests: Installed
  ✓ psycopg2: Installed
  ✓ mcpandroidbuild: Installed
✅ Python packages: All installed

Testing Git...
✅ Git: Installed (git version 2.x.x)
✅ Git remote: Configured

Testing .env file...
✅ .env file: Exists
✅ .env file: All required vars present

Testing MCP servers...
  ✓ mcpandroidbuild: Connected
  ✓ memory: Connected
  ✓ mobile-mcp: Connected
  ✓ postgres-batterycrm: Connected
✅ All MCP servers: Connected

Testing n8n API...
✅ n8n API: OK (100 workflows)

Testing Supabase API...
✅ Supabase API: OK

Testing VPN Server (217.145.79.27)...
✅ VPN Server: Reachable

Testing n8n/Supabase Server (185.221.214.83)...
✅ n8n/Supabase Server: Reachable

Testing Android SDK...
✅ Android SDK: Found at C:\Users\User\AppData\Local\Android\Sdk

=========================================================
Test Summary
=========================================================

  PASS  Python packages
  PASS  Git
  PASS  .env file
  PASS  MCP servers
  PASS  n8n API
  PASS  Supabase API
  PASS  VPN Server
  PASS  n8n/Supabase Server
  PASS  Android SDK

=========================================================
Total: 9/9 tests passed
=========================================================

🎉 All tests passed! Environment is ready!
```

---

## 🔧 Требования

### Необходимо установить ПЕРЕД запуском:

1. **VSCode** - https://code.visualstudio.com/
2. **Claude Code** - https://code.claude.com/
3. **Python 3.8+** - https://www.python.org/
4. **Node.js 18+** - https://nodejs.org/
5. **Git** - https://git-scm.com/

### Опционально:

6. **Android Studio** - https://developer.android.com/studio

---

## 📖 После установки

### 1. Перезапустить Claude Code

Чтобы MCP серверы загрузились.

### 2. Проверить MCP

```bash
claude mcp list
```

### 3. Прочитать документацию

- **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** - шпаргалка команд
- **[Start.md](Start.md)** - контекст проекта
- **[MCP_COMPLETE_SETUP.md](MCP_COMPLETE_SETUP.md)** - полная документация MCP

### 4. Начать работу!

```bash
# Примеры запросов к Claude Code:
Claude, покажи все таблицы в базе данных
Claude, собери Android проект
Claude, покажи список устройств
Claude, найди все TODO в проекте
```

---

## 🆘 Troubleshooting

### Скрипт не запускается

**Проблема**: `python: command not found`

**Решение**:
- Установить Python 3.8+
- Проверить PATH: `python --version`

### MCP серверы не подключаются

**Проблема**: `✗ Failed to connect`

**Решения**:
1. Перезапустить VSCode
2. Проверить `.claude.json`:
   ```bash
   cat ~/.claude.json  # Linux/Mac
   type %USERPROFILE%\.claude.json  # Windows
   ```
3. Переустановить пакеты:
   ```bash
   pip install --upgrade mcpandroidbuild
   npm install -g mobile-mcp @henkey/postgres-mcp-server
   ```

### SSH не работает

**Проблема**: `Connection refused`

**Решения**:
1. Проверить интернет
2. Проверить VPN (может требоваться для доступа к серверам)
3. Ping сервер: `ping 217.145.79.27`
4. Проверить SSH config: `cat ~/.ssh/config`

### API не отвечает

**Проблема**: `Timeout` или `Connection failed`

**Решения**:
1. Проверить URL доступен: `curl https://n8n.n8nsrv.ru`
2. Проверить API key в `.env`
3. Проверить нужен ли VPN

---

## 🔒 Безопасность

### ⚠️ ВАЖНО: НЕ коммитить в Git!

Эти файлы содержат чувствительные данные:
- `.env`
- `.dev_credentials`
- `.claude.json`
- `*.pem`, `*.key`

Они автоматически добавлены в `.gitignore`.

### Проверка перед commit:

```bash
git status

# Не должно быть:
# .env
# .dev_credentials
# .claude.json
```

---

## 📊 Статистика

### Что установится:

| Категория | Количество |
|-----------|------------|
| MCP серверы | 4 |
| Python пакеты | 4+ |
| NPM пакеты | 2+ |
| API подключения | 3 |
| SSH серверы | 2 |
| Config файлы | 4 |

### Размер:

- Python packages: ~50 MB
- NPM packages: ~100 MB
- Android SDK: ~5 GB (если устанавливать)

### Время установки:

- Быстрая (без Android SDK): ~5-10 минут
- Полная (с Android SDK): ~30-60 минут

---

## 🎁 Бонусы

Скрипт также создаёт:

✅ **SSH aliases** - `ssh vpn-server`, `ssh n8n-server`
✅ **.env** с полными credentials
✅ **Готовая Git конфигурация**
✅ **Тестовый скрипт** для проверки
✅ **Полная документация** на русском

---

## 📞 Поддержка

Если что-то не работает:

1. Запустить тестирование: `python test_connections.py`
2. Проверить логи Claude Code
3. Прочитать раздел Troubleshooting в документации
4. Проверить `.gitignore` (credentials должны быть исключены)

---

## ✅ Чеклист

После установки должно быть:

- [ ] Claude Code запускается
- [ ] `claude mcp list` показывает 4 сервера
- [ ] `python test_connections.py` проходит все тесты
- [ ] `ssh vpn-server` подключается
- [ ] `ssh n8n-server` подключается
- [ ] `python supabase_manager.py list` показывает 26 таблиц
- [ ] `git remote -v` показывает batterycrm
- [ ] `.env` файл существует
- [ ] `.gitignore` обновлён

---

## 🎉 Готово!

Теперь у вас **полностью автоматизированная установка** среды разработки BatteryCRM!

Один скрипт настраивает всё:
- MCP серверы
- Базу данных
- SSH доступ
- API ключи
- Git
- Тестирование

**Время на новый компьютер: 10 минут! 🚀**

---

**Создано**: 2025-11-22
**Автор**: Claude Code Environment Setup
**Версия**: 1.0
