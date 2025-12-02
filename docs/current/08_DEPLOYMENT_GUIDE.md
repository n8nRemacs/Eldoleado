# BatteryCRM - Deployment Guide: Руководство по развёртыванию

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация

---

## Содержание

1. [Требования к серверу](#требования-к-серверу)
2. [Установка PostgreSQL](#установка-postgresql)
3. [Установка n8n](#установка-n8n)
4. [Настройка workflows](#настройка-workflows)
5. [Настройка интеграций](#настройка-интеграций)
6. [Деплой Android APK](#деплой-android-apk)
7. [Деплой Desktop приложения](#деплой-desktop-приложения)
8. [Мониторинг и логирование](#мониторинг-и-логирование)
9. [Резервное копирование](#резервное-копирование)
10. [Обновления](#обновления)

---

## Требования к серверу

### Минимальные требования

**Для dev/test окружения:**
- CPU: 2 cores
- RAM: 4 GB
- Disk: 50 GB SSD
- OS: Ubuntu 20.04+ / Debian 11+

**Для production окружения:**
- CPU: 4+ cores
- RAM: 8+ GB
- Disk: 100+ GB SSD (с учётом роста БД)
- OS: Ubuntu 22.04 LTS / Debian 12
- Network: 100 Mbps+

### Рекомендуемая архитектура

**Вариант 1: Single Server (для малых проектов)**
```
┌──────────────────────────────────────┐
│      Single Server                   │
│  - PostgreSQL (порт 5432)            │
│  - n8n (порт 5678)                   │
│  - Nginx (порт 80/443)               │
│  - Supabase (опционально)            │
└──────────────────────────────────────┘
```

**Вариант 2: Distributed (для production)**
```
┌──────────────────┐    ┌──────────────────┐
│  DB Server       │    │  App Server      │
│  - PostgreSQL    │◄───┤  - n8n           │
│  - Supabase      │    │  - Nginx         │
└──────────────────┘    └──────────────────┘
```

**Вариант 3: Cloud (AWS/GCP/Azure)**
```
┌────────────────────┐
│  RDS PostgreSQL    │
└────────────────────┘
         ▲
         │
┌────────────────────┐
│  EC2 / Compute     │
│  - n8n (Docker)    │
│  - Nginx           │
└────────────────────┘
         ▲
         │
┌────────────────────┐
│  S3 / Cloud Storage│
│  - Media files     │
└────────────────────┘
```

---

## Установка PostgreSQL

### Метод 1: Нативная установка

**Ubuntu/Debian:**
```bash
# Обновление пакетов
sudo apt update

# Установка PostgreSQL 15
sudo apt install postgresql-15 postgresql-contrib-15 -y

# Проверка статуса
sudo systemctl status postgresql
```

**Создание базы данных и пользователя:**
```bash
# Вход в psql
sudo -u postgres psql

# Создание БД
CREATE DATABASE batterycrm;

# Создание пользователя
CREATE USER batterycrm_user WITH ENCRYPTED PASSWORD 'strong_password_here';

# Выдача прав
GRANT ALL PRIVILEGES ON DATABASE batterycrm TO batterycrm_user;

# Выход
\q
```

**Настройка удалённого доступа:**
```bash
# Редактирование postgresql.conf
sudo nano /etc/postgresql/15/main/postgresql.conf

# Изменить:
listen_addresses = '*'

# Редактирование pg_hba.conf
sudo nano /etc/postgresql/15/main/pg_hba.conf

# Добавить:
host    all             all             0.0.0.0/0               md5

# Перезапуск
sudo systemctl restart postgresql
```

**Настройка firewall:**
```bash
# UFW
sudo ufw allow 5432/tcp

# Или iptables
sudo iptables -A INPUT -p tcp --dport 5432 -j ACCEPT
```

---

### Метод 2: Docker

```bash
# Создание docker-compose.yml
cat > docker-compose.yml <<EOF
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: batterycrm-postgres
    restart: always
    environment:
      POSTGRES_DB: batterycrm
      POSTGRES_USER: batterycrm_user
      POSTGRES_PASSWORD: strong_password_here
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U batterycrm_user"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  postgres_data:
EOF

# Запуск
docker-compose up -d
```

---

### Создание схемы БД

**Миграции:**
```bash
# Скачать миграции
git clone https://github.com/your-repo/batterycrm-migrations.git
cd batterycrm-migrations

# Применить миграции
psql -h localhost -U batterycrm_user -d batterycrm -f migrations/001_initial_schema.sql
psql -h localhost -U batterycrm_user -d batterycrm -f migrations/002_extended_schema.sql
# ... остальные миграции
```

**Или использовать migration tool:**
```bash
# Flyway
flyway -url=jdbc:postgresql://localhost:5432/batterycrm \
       -user=batterycrm_user \
       -password=strong_password_here \
       migrate

# Liquibase
liquibase --url=jdbc:postgresql://localhost:5432/batterycrm \
          --username=batterycrm_user \
          --password=strong_password_here \
          update
```

---

## Установка n8n

### Метод 1: Docker (рекомендуется)

```bash
# Создание docker-compose.yml
cat > docker-compose.yml <<EOF
version: '3.8'

services:
  n8n:
    image: n8nio/n8n:latest
    container_name: batterycrm-n8n
    restart: always
    environment:
      - N8N_BASIC_AUTH_ACTIVE=true
      - N8N_BASIC_AUTH_USER=admin
      - N8N_BASIC_AUTH_PASSWORD=admin_password
      - N8N_HOST=n8n.n8nsrv.ru
      - N8N_PORT=5678
      - N8N_PROTOCOL=https
      - WEBHOOK_URL=https://n8n.n8nsrv.ru
      - GENERIC_TIMEZONE=Europe/Moscow
      - DB_TYPE=postgresdb
      - DB_POSTGRESDB_HOST=postgres
      - DB_POSTGRESDB_PORT=5432
      - DB_POSTGRESDB_DATABASE=n8n
      - DB_POSTGRESDB_USER=n8n_user
      - DB_POSTGRESDB_PASSWORD=n8n_password
    ports:
      - "5678:5678"
    volumes:
      - n8n_data:/home/node/.n8n
    depends_on:
      - postgres

volumes:
  n8n_data:
EOF

# Запуск
docker-compose up -d

# Проверка логов
docker logs -f batterycrm-n8n
```

---

### Метод 2: npm (для dev)

```bash
# Установка Node.js 18+
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install nodejs -y

# Установка n8n глобально
npm install -g n8n

# Создание конфигурации
mkdir -p ~/.n8n
cat > ~/.n8n/config <<EOF
{
  "database": {
    "type": "postgresdb",
    "postgresdb": {
      "host": "localhost",
      "port": 5432,
      "database": "n8n",
      "user": "n8n_user",
      "password": "n8n_password"
    }
  }
}
EOF

# Запуск
n8n start
```

---

### Настройка Nginx для n8n

```bash
# Установка Nginx
sudo apt install nginx -y

# Создание конфигурации
sudo nano /etc/nginx/sites-available/n8n.n8nsrv.ru

# Содержимое:
server {
    listen 80;
    server_name n8n.n8nsrv.ru;

    location / {
        return 301 https://$host$request_uri;
    }
}

server {
    listen 443 ssl http2;
    server_name n8n.n8nsrv.ru;

    ssl_certificate /etc/letsencrypt/live/n8n.n8nsrv.ru/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/n8n.n8nsrv.ru/privkey.pem;

    location / {
        proxy_pass http://localhost:5678;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}

# Включение конфигурации
sudo ln -s /etc/nginx/sites-available/n8n.n8nsrv.ru /etc/nginx/sites-enabled/

# Проверка конфигурации
sudo nginx -t

# Перезапуск
sudo systemctl restart nginx
```

**SSL сертификат (Let's Encrypt):**
```bash
# Установка certbot
sudo apt install certbot python3-certbot-nginx -y

# Получение сертификата
sudo certbot --nginx -d n8n.n8nsrv.ru

# Автоматическое обновление
sudo certbot renew --dry-run
```

---

## Настройка workflows

### Импорт workflows

**Метод 1: Через UI n8n**
1. Открыть https://n8n.n8nsrv.ru
2. Войти (admin / admin_password)
3. Workflows → Import from File
4. Выбрать JSON файлы workflows

**Метод 2: Через API**
```bash
# Скрипт для массового импорта
for file in workflows/*.json; do
  curl -X POST https://n8n.n8nsrv.ru/api/v1/workflows \
    -u admin:admin_password \
    -H "Content-Type: application/json" \
    -d @"$file"
done
```

### Список workflows для импорта

**Основные (обязательные):**
1. `BAT_Message_Router.json`
2. `BAT_Appeal_Router.json`
3. `BAT_Universal_Batcher.json`

**Входящие каналы:**
4. `BAT_IN_WhatsApp.json`
5. `BAT_IN_Telegram.json`
6. `BAT_IN_VK.json`
7. `BAT_IN_Avito.json`
8. `BAT_IN_Instagram.json` (NEW)

**Исходящие каналы:**
9. `BAT_OUT_WhatsApp.json`
10. `BAT_OUT_Telegram.json`
11. `BAT_OUT_VK.json`
12. `BAT_OUT_Avito.json`
13. `BAT_OUT_Instagram.json` (NEW)

**Вспомогательные (NEW):**
14. `BAT_Promo_Sender.json`
15. `BAT_Price_Calculator.json`
16. `BAT_CRM_Sync.json`
17. `BAT_QR_Code_Manager.json`
18. `BAT_Voice_Processor.json`
19. `BAT_Remarketing_Exporter.json`
20. `BAT_Data_Archiver.json`

**Утилиты:**
21. `BAT_FCM_Sender.json`
22. `BAT_Error_Handler.json`
23. `BAT_Health_Check.json`

---

### Настройка credentials в n8n

**Для каждого workflow настроить credentials:**

#### PostgreSQL
```
Host: 185.221.214.83
Port: 6544
Database: postgres
User: supabase_admin
Password: Mi31415926pS
SSL: Require
```

#### WhatsApp Business API
```
Phone Number ID: [ваш phone_number_id]
Access Token: [ваш access_token]
Webhook Verify Token: [ваш verify_token]
```

#### Telegram Bot
```
Bot Token: [ваш bot_token из @BotFather]
```

#### VK API
```
Group ID: [ID вашей группы]
Access Token: [токен группы]
API Version: 5.131
```

#### Avito API
```
Client ID: [ваш client_id]
Client Secret: [ваш client_secret]
```

#### Claude AI (Anthropic)
```
API Key: [ваш API ключ]
Model: claude-3-5-sonnet-20241022
```

#### Firebase FCM
```
Server Key: [ваш server_key]
```

---

## Настройка интеграций

### Webhooks для каналов

**WhatsApp:**
```bash
# Установка webhook в Meta Business Suite
curl -X POST "https://graph.facebook.com/v18.0/{phone_number_id}/webhooks" \
  -H "Authorization: Bearer {access_token}" \
  -d "url=https://n8n.n8nsrv.ru/webhook/whatsapp/incoming" \
  -d "verify_token=my_secure_token_123"
```

**Telegram:**
```bash
# Установка webhook
curl -X POST "https://api.telegram.org/bot{bot_token}/setWebhook" \
  -d "url=https://n8n.n8nsrv.ru/webhook/telegram/incoming"
```

**Instagram:**
```bash
# Подписка на события
curl -X POST "https://graph.facebook.com/v18.0/{page_id}/subscribed_apps" \
  -d "subscribed_fields=messages,messaging_postbacks" \
  -d "access_token={access_token}"
```

---

## Деплой Android APK

### Build приложения

**Локальный build:**
```bash
cd android-app/

# Настройка gradle.properties
cat > gradle.properties <<EOF
API_BASE_URL=https://n8n.n8nsrv.ru/webhook
FIREBASE_PROJECT_ID=batterycrm-prod
VERSION_CODE=1
VERSION_NAME=1.0.0
EOF

# Build release APK
./gradlew assembleRelease

# Подписание APK
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore my-release-key.keystore \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  alias_name

# Оптимизация (zipalign)
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/app-release.apk
```

**CI/CD (GitHub Actions):**
```yaml
# .github/workflows/android-build.yml
name: Android Build

on:
  push:
    branches: [ main ]
    tags: [ 'v*' ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build Release APK
        run: |
          cd android-app/
          ./gradlew assembleRelease

      - name: Sign APK
        run: |
          # Подписание через secrets
          echo "${{ secrets.KEYSTORE_BASE64 }}" | base64 -d > keystore.jks
          jarsigner -keystore keystore.jks \
            -storepass "${{ secrets.KEYSTORE_PASSWORD }}" \
            app/build/outputs/apk/release/app-release-unsigned.apk \
            ${{ secrets.KEY_ALIAS }}

      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-release.apk
          path: app/build/outputs/apk/release/app-release.apk
```

---

### Распространение APK

**Метод 1: Внутреннее распространение**
```bash
# Загрузка на сервер
scp app/build/outputs/apk/release/app-release.apk \
  user@server:/var/www/downloads/batterycrm.apk

# Nginx конфигурация для скачивания
location /downloads/batterycrm.apk {
    alias /var/www/downloads/batterycrm.apk;
    add_header Content-Type application/vnd.android.package-archive;
    add_header Content-Disposition "attachment; filename=batterycrm.apk";
}
```

**Метод 2: Google Play Store (Internal Testing)**
1. Создать Developer Account
2. Создать приложение в Google Play Console
3. Загрузить APK в Internal Testing track
4. Добавить тестеров по email

**Метод 3: Firebase App Distribution**
```bash
# Установка Firebase CLI
npm install -g firebase-tools

# Логин
firebase login

# Загрузка APK
firebase appdistribution:distribute app/build/outputs/apk/release/app-release.apk \
  --app {firebase_app_id} \
  --release-notes "Version 1.0.0 - Initial release" \
  --groups "testers"
```

---

## Деплой Desktop приложения

### Build приложения

**Windows:**
```bash
cd desktop-app/

# Установка зависимостей
npm install

# Build для Windows
npm run build:win

# Результат: dist/BatteryCRM-Setup-1.0.0.exe
```

**macOS:**
```bash
# Build для macOS
npm run build:mac

# Результат: dist/BatteryCRM-1.0.0.dmg
```

**Linux:**
```bash
# Build для Linux
npm run build:linux

# Результат: dist/BatteryCRM-1.0.0.AppImage
```

**package.json scripts:**
```json
{
  "scripts": {
    "dev": "vite",
    "build": "vite build && electron-builder",
    "build:win": "vite build && electron-builder --win",
    "build:mac": "vite build && electron-builder --mac",
    "build:linux": "vite build && electron-builder --linux"
  }
}
```

---

### Auto-updater настройка

**electron-builder.yml:**
```yaml
appId: com.batterycrm.app
productName: BatteryCRM
directories:
  output: dist
files:
  - "**/*"
  - "!**/*.ts"
  - "!*.map"
publish:
  - provider: github
    owner: your-org
    repo: batterycrm-desktop
    releaseType: release
win:
  target:
    - nsis
mac:
  target:
    - dmg
linux:
  target:
    - AppImage
```

**Релиз на GitHub:**
```bash
# Создание релиза
gh release create v1.0.0 \
  dist/BatteryCRM-Setup-1.0.0.exe \
  dist/BatteryCRM-1.0.0.dmg \
  dist/BatteryCRM-1.0.0.AppImage \
  --title "v1.0.0" \
  --notes "Initial release"
```

---

## Мониторинг и логирование

### n8n логи

**Docker logs:**
```bash
# Просмотр логов
docker logs -f batterycrm-n8n

# Логи за последний час
docker logs --since 1h batterycrm-n8n

# Логи с grep фильтром
docker logs batterycrm-n8n 2>&1 | grep ERROR
```

**File logs (если не Docker):**
```bash
# Логи n8n
tail -f ~/.n8n/logs/n8n.log
```

---

### PostgreSQL логи

```bash
# Логи PostgreSQL
sudo tail -f /var/log/postgresql/postgresql-15-main.log

# Включение query logging (pg_stat_statements)
sudo -u postgres psql -d batterycrm

CREATE EXTENSION IF NOT EXISTS pg_stat_statements;

# Просмотр медленных запросов
SELECT
  query,
  calls,
  mean_exec_time,
  total_exec_time
FROM pg_stat_statements
ORDER BY mean_exec_time DESC
LIMIT 10;
```

---

### Мониторинг через Prometheus + Grafana

**docker-compose.yml (добавить):**
```yaml
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus_data:/prometheus
    ports:
      - "9090:9090"

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana_data:/var/lib/grafana

  postgres_exporter:
    image: prometheuscommunity/postgres-exporter:latest
    container_name: postgres_exporter
    environment:
      DATA_SOURCE_NAME: "postgresql://batterycrm_user:strong_password_here@postgres:5432/batterycrm?sslmode=disable"
    ports:
      - "9187:9187"

volumes:
  prometheus_data:
  grafana_data:
```

**prometheus.yml:**
```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'postgres'
    static_configs:
      - targets: ['postgres_exporter:9187']
```

**Grafana dashboards:**
- PostgreSQL Database Dashboard (ID: 9628)
- n8n Monitoring Dashboard (custom)

---

## Резервное копирование

### PostgreSQL backup

**Автоматический backup (cron):**
```bash
# Скрипт backup.sh
#!/bin/bash
BACKUP_DIR="/backups/postgres"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/batterycrm_$DATE.sql.gz"

mkdir -p $BACKUP_DIR

pg_dump -h localhost -U batterycrm_user batterycrm | gzip > $BACKUP_FILE

# Удаление старых бэкапов (старше 7 дней)
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "Backup completed: $BACKUP_FILE"

# Сохранение на chmod +x
chmod +x backup.sh

# Добавление в crontab (каждый день в 2:00)
crontab -e
0 2 * * * /path/to/backup.sh >> /var/log/postgres_backup.log 2>&1
```

**Копирование на удалённый сервер:**
```bash
# rsync на другой сервер
rsync -avz /backups/postgres/ user@backup-server:/backups/batterycrm/
```

---

### Восстановление из backup

```bash
# Распаковка
gunzip batterycrm_20251123_020000.sql.gz

# Восстановление
psql -h localhost -U batterycrm_user -d batterycrm < batterycrm_20251123_020000.sql
```

---

## Обновления

### Обновление n8n

**Docker:**
```bash
# Pull новой версии
docker pull n8nio/n8n:latest

# Остановка текущего контейнера
docker-compose down

# Запуск с новой версией
docker-compose up -d

# Проверка версии
docker exec batterycrm-n8n n8n --version
```

**npm:**
```bash
# Обновление
npm update -g n8n

# Проверка версии
n8n --version
```

---

### Обновление workflows

**Через UI:**
1. Экспорт существующих workflows (backup)
2. Import новых версий workflows
3. Проверка credentials
4. Тестирование

**Через API:**
```bash
# Обновление workflow
curl -X PATCH https://n8n.n8nsrv.ru/api/v1/workflows/{workflow_id} \
  -u admin:admin_password \
  -H "Content-Type: application/json" \
  -d @updated_workflow.json
```

---

### Миграции БД

```bash
# Применение новых миграций
psql -h localhost -U batterycrm_user -d batterycrm -f migrations/003_new_features.sql

# Проверка версии схемы
psql -h localhost -U batterycrm_user -d batterycrm -c "SELECT version FROM schema_migrations ORDER BY version DESC LIMIT 1;"
```

---

## Troubleshooting

### Проблемы с n8n

**n8n не запускается:**
```bash
# Проверка логов
docker logs batterycrm-n8n

# Проверка подключения к БД
docker exec batterycrm-n8n n8n diagnose
```

**Webhooks не работают:**
```bash
# Проверка доступности
curl https://n8n.n8nsrv.ru/webhook/test

# Проверка SSL сертификата
openssl s_client -connect n8n.n8nsrv.ru:443
```

---

### Проблемы с PostgreSQL

**Подключение отклоняется:**
```bash
# Проверка статуса
sudo systemctl status postgresql

# Проверка прослушивания порта
sudo netstat -tlnp | grep 5432

# Проверка конфигурации
sudo cat /etc/postgresql/15/main/pg_hba.conf
```

**Медленные запросы:**
```sql
-- Включение логирования медленных запросов
ALTER SYSTEM SET log_min_duration_statement = 1000; -- 1 секунда
SELECT pg_reload_conf();

-- Просмотр медленных запросов
SELECT * FROM pg_stat_statements ORDER BY total_exec_time DESC LIMIT 10;
```

---

## Health Check endpoints

**n8n:**
```bash
# Health check
curl https://n8n.n8nsrv.ru/healthz

# Expected response:
{"status": "ok"}
```

**PostgreSQL:**
```bash
# Проверка подключения
psql -h localhost -U batterycrm_user -d batterycrm -c "SELECT 1;"
```

**API:**
```bash
# Проверка API
curl https://n8n.n8nsrv.ru/webhook/health

# Expected response:
{"status": "healthy", "timestamp": "2025-11-23T12:00:00Z"}
```

---

**Конец руководства по развёртыванию.**

---

## Checklist для деплоя

### Перед запуском в production

- [ ] PostgreSQL установлен и настроен
- [ ] База данных создана, миграции применены
- [ ] n8n установлен и запущен
- [ ] Все workflows импортированы
- [ ] Credentials настроены для всех интеграций
- [ ] Webhooks настроены для всех каналов
- [ ] SSL сертификаты установлены
- [ ] Nginx настроен и работает
- [ ] Firewall настроен (открыты нужные порты)
- [ ] Backup настроен (cron job)
- [ ] Мониторинг настроен (Grafana/Prometheus)
- [ ] Логирование работает
- [ ] Android APK собран и распространён
- [ ] Desktop приложение собрано для всех платформ
- [ ] Документация обновлена
- [ ] Проведено тестирование всех каналов
- [ ] Health check endpoints проверены

### После запуска

- [ ] Мониторинг метрик в течение 24 часов
- [ ] Проверка логов на ошибки
- [ ] Тестирование всех каналов коммуникации
- [ ] Проверка Push-уведомлений
- [ ] Проверка AI обработки сообщений
- [ ] Проверка CRM синхронизации
- [ ] Первый backup выполнен успешно
