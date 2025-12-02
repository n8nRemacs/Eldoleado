# 📚 Quick Reference - Шпаргалка команд BatteryCRM

## 🚀 Быстрый старт на новом компьютере

```bash
# 1. Клонировать репозиторий
git clone https://github.com/n8nRemacs/batterycrm.git
cd batterycrm

# 2. Запустить установку среды
python setup_dev_environment.py

# 3. Протестировать всё
python test_connections.py

# 4. Перезапустить Claude Code
# Restart VSCode/Claude Code

# 5. Проверить MCP
claude mcp list
```

---

## 🔑 Credentials (пароли и ключи)

### Серверы
```
VPN Server:       217.145.79.27   root   Mi31415926pSss!
n8n/Supabase:     185.221.214.83  root   Mi31415926pSss!
```

### Сервисы
```
n8n URL:          https://n8n.n8nsrv.ru
n8n API Key:      eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M

Supabase URL:     https://supabase.n8nsrv.ru
Supabase Key:     eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0

PostgreSQL:       postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres

GitHub:           https://github.com/n8nRemacs/batterycrm
```

---

## 📦 MCP Servers

### Проверка статуса
```bash
claude mcp list
```

### 4 MCP сервера:
- **mcpandroidbuild** - сборка Android проектов
- **memory** - контекстная память
- **mobile-mcp** - управление Android устройствами
- **postgres-batterycrm** - доступ к PostgreSQL

---

## 🗄️ Supabase/PostgreSQL

### Python скрипт (рекомендуется)
```bash
# Список таблиц
python supabase_manager.py list

# Экспорт схемы
python supabase_manager.py export

# Экспорт в другую папку
python supabase_manager.py export my_backup
```

### Через Claude Code
```
Claude, покажи все таблицы из базы данных
Claude, сколько всего клиентов в БД?
Claude, покажи последние 10 обращений
```

---

## 🔄 n8n Workflows

### Python скрипт
```bash
# Список воркеров
python n8n_manager.py list

# Скачать workflow
python n8n_manager.py get <workflow_id>

# Обновить workflow
python n8n_manager.py update <workflow_id> workflow.json

# Активировать
python n8n_manager.py activate <workflow_id>
```

### API напрямую
```bash
# Список workflows
curl -X GET "https://n8n.n8nsrv.ru/api/v1/workflows" \
  -H "X-N8N-API-KEY: YOUR_KEY" \
  -H "Accept: application/json"
```

---

## 🔐 SSH

### Через aliases
```bash
ssh vpn-server       # 217.145.79.27
ssh n8n-server       # 185.221.214.83
```

### Прямое подключение
```bash
ssh root@217.145.79.27
ssh root@185.221.214.83
```

### С командой
```bash
ssh vpn-server "systemctl status trojan"
ssh n8n-server "docker ps"
ssh n8n-server "systemctl status n8n"
```

### Копирование файлов
```bash
# На сервер
scp local_file.txt vpn-server:/root/

# С сервера
scp n8n-server:/root/file.txt ./
```

---

## 📱 Android

### Сборка проекта
```bash
# Через Claude
Claude, собери Android проект

# Через Gradle
cd app
./gradlew assembleDebug
```

### ADB команды
```bash
# Список устройств
adb devices

# Установить APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Логи
adb logcat | grep BattCRM

# Очистить данные приложения
adb shell pm clear com.batterycrm.app
```

### Эмулятор
```bash
# Список эмуляторов
emulator -list-avds

# Запустить
emulator -avd <avd_name>
```

---

## 🔧 Git

### Обычный workflow
```bash
git pull origin main
git add .
git commit -m "Your message"
git push origin main
```

### Полезные команды
```bash
# Статус
git status

# История (красиво)
git log --oneline --graph --all

# Отменить последний commit (но сохранить изменения)
git reset --soft HEAD~1

# Посмотреть изменения
git diff

# Посмотреть все ветки
git branch -a
```

---

## 🧪 Тестирование

### Проверка всех подключений
```bash
python test_connections.py
```

### Отдельные проверки
```bash
# MCP
claude mcp list

# n8n API
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows" \
  -H "X-N8N-API-KEY: YOUR_KEY" | python -m json.tool

# Supabase
python supabase_manager.py list

# Серверы
ping 217.145.79.27
ping 185.221.214.83

# SSH
ssh vpn-server "echo OK"
ssh n8n-server "echo OK"
```

---

## 📂 Структура проекта

```
batterycrm/
├── app/                          # Android приложение
├── Backend JSON/                 # n8n workflows
│   ├── API Backend/              # API endpoints
│   ├── IN Workers/               # Входящие (Telegram, VK...)
│   └── OUT Workers/              # Исходящие
├── database/                     # Миграции БД
├── database_schema/              # Схема БД (version control)
├── .env                          # Credentials (НЕ коммитить!)
├── .dev_credentials              # Server credentials (НЕ коммитить!)
├── setup_dev_environment.py      # Установка среды
├── test_connections.py           # Тестирование
├── supabase_manager.py           # Управление БД
├── n8n_manager.py                # Управление workflows
└── README.md                     # Документация
```

---

## 📖 Документация

### Порядок чтения
1. **[SETUP_NEW_MACHINE.md](SETUP_NEW_MACHINE.md)** - Установка на новом компьютере
2. **[Start.md](Start.md)** - Контекст проекта
3. **[MCP_COMPLETE_SETUP.md](MCP_COMPLETE_SETUP.md)** - MCP серверы
4. **[Database_Structure_BatteryCRM_COMPLETE.md](Database_Structure_BatteryCRM_COMPLETE.md)** - БД
5. **[Eldoleado full.md](Eldoleado%20full.md)** - Полная архитектура

### По темам
- **MCP Setup**: `MCP_ANDROID_SETUP.md`, `MCP_POSTGRES_SETUP.md`
- **n8n**: `README_N8N_MANAGER.md`
- **Supabase**: `README_SUPABASE_MANAGER.md`
- **Git**: `README_GITHUB.md`

---

## 🆘 Troubleshooting

### MCP не работает
```bash
# Переустановить
pip install --upgrade mcpandroidbuild
npm install -g mobile-mcp @henkey/postgres-mcp-server

# Проверить конфигурацию
cat ~/.claude.json  # Linux/Mac
type %USERPROFILE%\.claude.json  # Windows

# Перезапустить Claude Code
```

### SSH не работает
```bash
# Проверить SSH config
cat ~/.ssh/config

# Тестовое подключение
ssh -v root@217.145.79.27

# Добавить в known_hosts
ssh-keyscan -H 217.145.79.27 >> ~/.ssh/known_hosts
```

### PostgreSQL connection failed
```bash
# Использовать REST API
python supabase_manager.py list

# Тест прямого подключения (может не работать)
psql "postgresql://postgres:PASSWORD@HOST:5432/postgres"
```

---

## 💡 Полезные советы

### Claude Code запросы
```
Claude, покажи структуру таблицы appeals
Claude, найди все TODO в проекте
Claude, объясни как работает BAT AI Appeal Router
Claude, собери проект и запусти тесты
Claude, создай миграцию для добавления поля X
```

### Бэкапы
```bash
# Экспорт схемы БД
python supabase_manager.py export backup_$(date +%Y%m%d)

# Скачать все workflows
python n8n_manager.py list
for id in $(seq 1 100); do
  python n8n_manager.py get $id
done

# Git коммит
git add .
git commit -m "Backup $(date +%Y-%m-%d)"
git push
```

### Мониторинг серверов
```bash
# Статус сервисов
ssh n8n-server "systemctl status n8n"
ssh n8n-server "systemctl status supabase"
ssh vpn-server "systemctl status trojan"

# Логи
ssh n8n-server "journalctl -u n8n -f"

# Место на диске
ssh n8n-server "df -h"

# Память
ssh n8n-server "free -h"

# Docker контейнеры
ssh n8n-server "docker ps"
```

---

## 🎯 Частые задачи

### Добавить новый workflow в n8n
```bash
# 1. Создать workflow в n8n UI
# 2. Скачать
python n8n_manager.py get <new_id>
# 3. Сохранить в Git
git add "Backend JSON/new_workflow.json"
git commit -m "Add new workflow: name"
git push
```

### Создать миграцию БД
```bash
# 1. Создать SQL файл
# database/migrations/XXX_description.sql

# 2. Применить через Supabase UI или:
python run_migration.py

# 3. Экспортировать новую схему
python supabase_manager.py export

# 4. Закоммитить
git add database/ database_schema/
git commit -m "Migration: add field X"
git push
```

### Обновить Android приложение
```bash
# 1. Внести изменения в код
# 2. Собрать
./gradlew assembleDebug
# 3. Установить на устройство
adb install -r app/build/outputs/apk/debug/app-debug.apk
# 4. Тестировать
# 5. Закоммитить
git add app/
git commit -m "Feature: description"
git push
```

---

## 🔗 Быстрые ссылки

- **n8n Dashboard**: https://n8n.n8nsrv.ru
- **Supabase Dashboard**: https://supabase.n8nsrv.ru
- **GitHub**: https://github.com/n8nRemacs/batterycrm
- **MCP Docs**: https://modelcontextprotocol.io/
- **Claude Code Docs**: https://docs.claude.com/

---

**Last updated**: 2025-11-22
