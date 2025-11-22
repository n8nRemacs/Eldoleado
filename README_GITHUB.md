# 🔋 BatteryCRM

AI-powered service center management system with intelligent workflow automation.

## 📋 О проекте

BatteryCRM - это современная CRM система для сервисных центров по ремонту техники с интеграцией искусственного интеллекта.

### ✨ Основные возможности:

- 🤖 **AI-ассистент** - автоматическая обработка обращений клиентов через GPT-4
- 📱 **Multi-channel** - Telegram, VK, WhatsApp, Avito, веб-формы
- 📲 **Android приложение** - для операторов с push-уведомлениями (FCM)
- 🔄 **n8n workflows** - гибкая автоматизация бизнес-процессов
- 💾 **PostgreSQL + Supabase** - надежное хранение данных
- 🎯 **Multi-tenant** - поддержка нескольких компаний в одной системе

## 🏗️ Архитектура

```
┌─────────────────┐
│   Клиенты       │
│ Telegram/VK/WA  │
└────────┬────────┘
         │
    ┌────▼────────────────┐
    │  n8n Workflows      │
    │  - BAT IN Telegram  │
    │  - AI Router        │
    │  - FCM Sender       │
    └────────┬────────────┘
             │
    ┌────────▼─────────┐
    │   PostgreSQL     │
    │   (Supabase)     │
    └────────┬─────────┘
             │
    ┌────────▼──────────┐
    │  Android App      │
    │  (Операторы)      │
    └───────────────────┘
```

## 🚀 Быстрый старт

### Требования

- Python 3.8+
- n8n v1.119.2
- PostgreSQL 14+
- Android Studio (для мобильного приложения)

### Установка

1. Клонируй репозиторий:
```bash
git clone https://github.com/YOUR_USERNAME/BatteryCRM.git
cd BatteryCRM
```

2. Установи зависимости:
```bash
pip install requests
```

3. Настрой n8n_manager.py с твоими credentials

4. Импортируй воркеры в n8n из папки `Backend JSON/`

## 🛠️ Инструменты

### n8n Manager

Управление воркерами через API:

```bash
# Список всех воркеров
python n8n_manager.py list

# Скачать воркер
python n8n_manager.py get <WORKFLOW_ID>

# Обновить воркер
python n8n_manager.py update <WORKFLOW_ID> workflows/file.json

# Активировать воркер
python n8n_manager.py activate <WORKFLOW_ID>
```

Подробнее: [README_N8N_MANAGER.md](README_N8N_MANAGER.md)

## 📚 Документация

- [Start.md](Start.md) - контекст проекта и план работ
- [Eldoleado full.md](Eldoleado%20full.md) - полная архитектура системы
- [Database_Structure_BatteryCRM_COMPLETE.md](Database_Structure_BatteryCRM_COMPLETE.md) - структура БД
- [N8N_VERSION_INFO.md](N8N_VERSION_INFO.md) - информация о версии n8n

## 🔧 Технологии

### Backend
- **n8n** v1.119.2 - workflow automation
- **PostgreSQL** 14 - основная БД
- **Supabase** - БД хостинг + Auth
- **OpenAI GPT-4** - AI обработка

### Mobile
- **Android** - Kotlin
- **Firebase FCM** - Push уведомления
- **Retrofit** - API клиент
- **Material Design 3** - UI

### AI & ML
- **LangChain** - AI orchestration
- **OpenAI Embeddings** - векторный поиск
- **Postgres Chat Memory** - история диалогов

## 📂 Структура проекта

```
BatteryCRM/
├── app/                    # Android приложение
├── Backend JSON/           # n8n workflows
│   ├── API Backend/       # API воркеры
│   ├── IN Workers/        # Входящие каналы
│   ├── Out Workers/       # Исходящие каналы
│   └── Tool/              # AI Tools
├── workflows/             # Скачанные воркеры для редактирования
├── n8n_manager.py         # Менеджер n8n workflows
└── docs/                  # Документация
```

## 🔐 Безопасность

- Multi-tenant изоляция через `tenant_id`
- Session-based авторизация для Android
- API keys для n8n
- Encrypted FCM tokens
- SQL injection protection

## 📊 База данных

37 таблиц включая:
- `clients` - клиенты
- `appeals` - обращения
- `messages_history` - история сообщений
- `operators` - операторы
- `operator_devices` - устройства операторов
- `tenants` - арендаторы (multi-tenant)

Полная схема: [Database_Structure_BatteryCRM_COMPLETE.md](Database_Structure_BatteryCRM_COMPLETE.md)

## 🤝 Разработка

### Version Control для n8n workflows

```bash
# 1. Скачай воркер
python n8n_manager.py get pmDPBdREgE5wf1Cn

# 2. Редактируй workflows/файл.json

# 3. Commit в Git
git add workflows/
git commit -m "Update workflow: описание"
git push

# 4. Загрузи в n8n
python n8n_manager.py update pmDPBdREgE5wf1Cn workflows/файл.json
```

### Ветки

- `main` - production
- `develop` - разработка
- `feature/*` - новые фичи

## 📝 Лицензия

Proprietary - все права защищены

## 👤 Автор

BatteryCRM Development Team

---

⚡ Powered by AI, built with ❤️
