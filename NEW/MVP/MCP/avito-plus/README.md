# Avito-Plus

> Полноценный headless-клиент для Avito с CDP interception, mobile proxy и multi-tenant архитектурой.

**Создан:** 2025-12-27
**Статус:** Развёрнут на сервере

## Сервер

| Параметр | Значение |
|----------|----------|
| **URL** | https://msg.eldoleado.ru |
| **IP** | 85.198.86.96 |
| **Port** | 8794 (внутренний), 443 (HTTPS) |
| **WireGuard** | 51820 |
| **WireGuard Public Key** | `5IwR2fPVpn+PABZW/qxSOjJAcwqsLm/kxZa1aFACrjU=` |
| **WireGuard Subnet** | 10.0.10.0/24 |

---

## Проблема

Текущий mcp-avito-camoufox имеет ограничения:

| Проблема | Описание |
|----------|----------|
| **WebSocket закрывается** | Avito проверяет что-то, чего мы не видим |
| **hash_id не извлекается** | Нужен для подключения к `socket.avito.ru` |
| **Нет real-time push** | DOM parsing не даёт входящие сообщения |
| **IP блокировки** | Datacenter IP в чёрных списках Avito |
| **Детект автоматизации** | Avito может детектить Playwright/Puppeteer |

---

## Решение: Avito-Plus

**Ключевая идея:** Не пытаемся подключиться к WebSocket сами — перехватываем тот, который создаёт Avito в браузере через CDP (Chrome DevTools Protocol).

```
┌─────────────────────────────────────────────────────────────┐
│                    Текущий подход                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Camoufox ──→ Avito ──→ WebSocket                          │
│      │                      │                               │
│      └── DOM parsing        └── Мы НЕ видим что внутри     │
│          (только HTML)          (закрыто)                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    Avito-Plus: CDP Interception             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Camoufox ←──CDP──→ Network.* events                       │
│      │                   │                                  │
│      │         ┌─────────┴─────────┐                       │
│      │         │  МЫ ВИДИМ ВСЁ:    │                       │
│      │         │  • Все HTTP       │                       │
│      │         │  • Все headers    │                       │
│      │         │  • Все cookies    │                       │
│      │         │  • WebSocket init │                       │
│      │         │  • WS frames      │                       │
│      │         │  • hash_id !      │                       │
│      │         └───────────────────┘                       │
│      │                                                      │
│      └──→ Avito (думает что обычный браузер)               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Архитектура

### Общая схема

```
┌─────────────────────────────────────────────────────────────┐
│                      avito-plus                             │
│                      Port 8794                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                    ProxyPool                         │   │
│  │  ┌───────────┐ ┌───────────┐ ┌───────────┐          │   │
│  │  │ Mobile 1  │ │ Mobile 2  │ │ Residential│          │   │
│  │  │ MTS       │ │ Beeline   │ │ (backup)   │          │   │
│  │  │ WireGuard │ │ WireGuard │ │ SOCKS5     │          │   │
│  │  └─────┬─────┘ └─────┬─────┘ └─────┬─────┘          │   │
│  │        └─────────────┼─────────────┘                 │   │
│  │                      │                               │   │
│  │              ProxyHealthChecker                      │   │
│  └──────────────────────┼───────────────────────────────┘   │
│                         │                                   │
│  ┌──────────────────────┼───────────────────────────────┐   │
│  │              AccountManager                           │   │
│  │  ┌─────────────────────────────────────────────────┐ │   │
│  │  │ Account 1                                        │ │   │
│  │  │ ├── fingerprint.json (stable forever)           │ │   │
│  │  │ ├── proxy: mobile_1                             │ │   │
│  │  │ ├── Camoufox Browser                            │ │   │
│  │  │ │   └── CDP Session                             │ │   │
│  │  │ │       ├── Network.* (all traffic)             │ │   │
│  │  │ │       ├── hash_id (extracted)                 │ │   │
│  │  │ │       └── WebSocket frames → messages         │ │   │
│  │  │ └── HumanBehavior (delays, curves)              │ │   │
│  │  └─────────────────────────────────────────────────┘ │   │
│  │  ┌─────────────────────────────────────────────────┐ │   │
│  │  │ Account 2 ...                                    │ │   │
│  │  └─────────────────────────────────────────────────┘ │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                             │
│  REST API + WebSocket                                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                     n8n / Android                            │
└─────────────────────────────────────────────────────────────┘
```

### Компоненты

| Компонент | Файл | Описание |
|-----------|------|----------|
| **Server** | `server.py` | FastAPI, REST API, WebSocket endpoints |
| **AccountManager** | `account_manager.py` | Управление аккаунтами, lifecycle |
| **AvitoInterceptor** | `avito_interceptor.py` | CDP session, перехват трафика |
| **FingerprintManager** | `fingerprint_manager.py` | Стабильные fingerprints |
| **ProxyPool** | `proxy_pool.py` | Управление прокси (mobile, residential) |
| **HumanBehavior** | `human_behavior.py` | Имитация человека |
| **MessageRouter** | `message_router.py` | Роутинг сообщений в n8n/Android |

---

## CDP Interception

### Что перехватываем

| CDP Event | Что видим | Зачем |
|-----------|-----------|-------|
| `Network.requestWillBeSent` | Исходящие запросы + headers | Debug |
| `Network.responseReceived` | Ответы + headers | Debug |
| `Network.webSocketCreated` | URL WebSocket | **Извлечение hash_id!** |
| `Network.webSocketFrameSent` | Исходящие WS сообщения | Debug |
| `Network.webSocketFrameReceived` | **Входящие сообщения!** | Real-time push |
| `Runtime.consoleAPICalled` | console.log Avito | Debug |
| `Storage.getCookies` | Все cookies | Сессия |

### Извлечение hash_id

```python
@cdp.on("Network.webSocketCreated")
def on_ws_created(event):
    url = event["url"]
    # wss://socket.avito.ru/?...&my_hash_id=ABC123...
    if "socket.avito.ru" in url:
        match = re.search(r"my_hash_id=([^&]+)", url)
        if match:
            hash_id = match.group(1)
            save_hash_id(account_id, hash_id)
```

### Перехват сообщений

```python
@cdp.on("Network.webSocketFrameReceived")
def on_ws_message(event):
    data = event["response"]["payloadData"]
    message = parse_avito_message(data)
    if message.type == "new_message":
        route_to_webhook(account_id, message)
```

---

## Proxy

### Варианты

| Тип | Стоимость | Доверие Avito | Стабильность | ASN Check |
|-----|:---------:|:-------------:|:------------:|:---------:|
| **Datacenter** | $0.5-2/GB | Низкое | Высокая | ❌ Детектится |
| **Residential** | $10-15/GB | Среднее | Средняя | ✅ OK |
| **Mobile (свой)** | Бесплатно* | **Высокое** | Средняя | ✅ OK |

*Нужны SIM-карты и старые телефоны

### Proxy архитектура

```
┌─────────────────────────────────────────────────────────────┐
│                         SERVER                              │
│                     (85.198.86.96)                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  avito-plus                                                 │
│  └── ProxyPool                                              │
│      │                                                      │
│      ├── Type: CLIENT_PC (домашний ПК клиента) ← MVP       │
│      │   └── client_remaks ──→ WireGuard ──→ Home PC       │
│      │                                                      │
│      ├── Type: CLIENT_ROUTER (GL.iNet у клиента) ← Phase 2 │
│      │   └── router_tehno ──→ WireGuard ──→ GL.iNet        │
│      │                                                      │
│      ├── Type: MOBILE (телефон с SIM)                      │
│      │   └── mobile_mts ──→ WireGuard ──→ Phone            │
│      │                                                      │
│      └── Type: DATACENTER (backup)                         │
│          └── dc_backup ──→ SOCKS5 ──→ Provider             │
│                                                             │
└───────────────────────────┬─────────────────────────────────┘
                            │ WireGuard tunnels
                            │
┌───────────────────────────┴─────────────────────────────────┐
│                    PROXY ENDPOINTS                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─ MVP: Домашний ПК клиента ──────────────────────────┐   │
│  │                                                      │   │
│  │  Windows/Linux PC                                    │   │
│  │  ┌─────────────────────────────────────────────┐    │   │
│  │  │ WireGuard client                             │    │   │
│  │  │ microsocks/3proxy (SOCKS5 server)            │    │   │
│  │  │ IP: домашний провайдер клиента               │    │   │
│  │  └─────────────────────────────────────────────┘    │   │
│  │                                                      │   │
│  │  + Уже есть у клиента                               │   │
│  │  + Высокое доверие Avito (residential IP)           │   │
│  │  + Бесплатно                                        │   │
│  │  + Быстрый bandwidth (50-500 Mbps)                  │   │
│  │  - ПК должен быть включён                           │   │
│  │  - Нужна настройка у клиента                        │   │
│  │                                                      │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─ Phase 2: GL.iNet роутер ───────────────────────────┐   │
│  │                                                      │   │
│  │  GL.iNet GL-MT300N-V2 "Mango" ($25)                 │   │
│  │  ┌─────────────────────────────────────────────┐    │   │
│  │  │ OpenWrt + WireGuard (из коробки)             │    │   │
│  │  │ microsocks (устанавливается)                 │    │   │
│  │  │ Подключается к WiFi клиента                  │    │   │
│  │  └─────────────────────────────────────────────┘    │   │
│  │                                                      │   │
│  │  + Plug & play (настроили — отдали)                 │   │
│  │  + Работает 24/7                                    │   │
│  │  + Не зависит от ПК клиента                         │   │
│  │  - Нужно купить устройство                          │   │
│  │                                                      │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─ Mobile: Телефон с SIM ─────────────────────────────┐   │
│  │                                                      │   │
│  │  Старый Android                                      │   │
│  │  ┌─────────────────────────────────────────────┐    │   │
│  │  │ SIM: MTS/Beeline/Megafon                     │    │   │
│  │  │ WireGuard app + Every Proxy                  │    │   │
│  │  │ Мобильный IP (CGNAT)                         │    │   │
│  │  └─────────────────────────────────────────────┘    │   │
│  │                                                      │   │
│  │  + Мобильный IP = высокое доверие                   │   │
│  │  + Бесплатно (старый телефон + SIM)                 │   │
│  │  - Нестабильное соединение                          │   │
│  │  - Батарея                                          │   │
│  │                                                      │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Настройка домашнего ПК (MVP)

#### Windows

```powershell
# 1. Установить WireGuard
# https://www.wireguard.com/install/

# 2. Конфигурация (создаёт администратор)
# C:\Program Files\WireGuard\wg0.conf

[Interface]
PrivateKey = <GENERATED_PRIVATE_KEY>
Address = 10.0.10.X/24

[Peer]
PublicKey = 5IwR2fPVpn+PABZW/qxSOjJAcwqsLm/kxZa1aFACrjU=
Endpoint = 85.198.86.96:51820
AllowedIPs = 10.0.10.0/24
PersistentKeepalive = 25

# 3. Установить 3proxy (SOCKS5 сервер)
# https://github.com/3proxy/3proxy/releases

# 3proxy.cfg
nserver 8.8.8.8
nscache 65536
log 3proxy.log D
auth none
socks -i10.0.10.X -p1080
```

#### Linux

```bash
# 1. WireGuard
sudo apt install wireguard

# /etc/wireguard/wg0.conf
[Interface]
PrivateKey = <GENERATED_PRIVATE_KEY>
Address = 10.0.10.X/24

[Peer]
PublicKey = 5IwR2fPVpn+PABZW/qxSOjJAcwqsLm/kxZa1aFACrjU=
Endpoint = 85.198.86.96:51820
AllowedIPs = 10.0.10.0/24
PersistentKeepalive = 25

# Запуск
sudo systemctl enable wg-quick@wg0
sudo systemctl start wg-quick@wg0

# 2. microsocks (легковесный SOCKS5)
sudo apt install build-essential git
git clone https://github.com/rofl0r/microsocks
cd microsocks && make && sudo make install

# Запуск на WireGuard интерфейсе
microsocks -i 10.0.10.X -p 1080 &
```

### Проверка работы прокси

```bash
# На сервере — проверка что прокси доступен
curl --socks5 10.0.10.X:1080 https://api.ipify.org
# Должен показать IP клиента (не сервера!)
```

### Proxy конфигурация

```yaml
# config/proxies.yaml

proxies:
  # === CLIENT PC (MVP) ===
  - id: client_remaks_pc
    type: client_pc
    transport: wireguard
    wireguard_ip: 10.0.10.1
    socks_port: 1080
    client_name: РемАкс
    location: Ростов-на-Дону
    isp: Ростелеком
    priority: 1

  # === CLIENT ROUTER (Phase 2) ===
  - id: client_tehno_router
    type: client_router
    transport: wireguard
    wireguard_ip: 10.0.10.2
    socks_port: 1080
    client_name: Техносервис
    location: Москва
    isp: МГТС
    device: GL.iNet Mango
    priority: 1

  # === MOBILE ===
  - id: mobile_mts
    type: mobile
    transport: wireguard
    wireguard_ip: 10.0.20.1
    socks_port: 1080
    carrier: MTS
    priority: 2

  # === DATACENTER (backup) ===
  - id: dc_backup
    type: datacenter
    protocol: socks5
    host: proxy.provider.com
    port: 1080
    username: user
    password: pass
    priority: 3  # Последний fallback
```

---

## Fingerprint Management

### Принцип

**Один аккаунт = один fingerprint НАВСЕГДА.**

Avito видит одно и то же "устройство" при каждом входе.

### Структура

```
/data/avito-plus/{account_id}/
├── fingerprint.json    # Уникальный отпечаток (никогда не меняется)
├── profile/            # Firefox профиль (cookies, localStorage)
├── state.json          # Состояние сессии
└── traffic.log         # Лог трафика (debug, ротация)
```

### fingerprint.json

```json
{
  "created_at": "2025-12-27T10:00:00Z",
  "screen": {
    "width": 1920,
    "height": 1080,
    "colorDepth": 24
  },
  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:120.0) Gecko/20100101 Firefox/120.0",
  "platform": "Win32",
  "language": "ru-RU",
  "languages": ["ru-RU", "ru", "en-US", "en"],
  "timezone": "Europe/Moscow",
  "timezoneOffset": -180,
  "webgl": {
    "vendor": "Google Inc. (NVIDIA)",
    "renderer": "ANGLE (NVIDIA, NVIDIA GeForce GTX 1060 6GB Direct3D11 vs_5_0 ps_5_0)"
  },
  "canvas_seed": 847291,
  "audio_seed": 0.00015,
  "fonts": [
    "Arial",
    "Times New Roman",
    "Courier New",
    "Georgia",
    "Verdana",
    "Tahoma"
  ],
  "hardware": {
    "cpuCores": 8,
    "memory": 8,
    "maxTouchPoints": 0
  }
}
```

---

## Human Behavior

### Что имитируем

| Поведение | Реализация |
|-----------|------------|
| **Задержки** | Random 100-500ms между действиями |
| **Печать** | 50-150ms между символами |
| **Скролл** | Случайная амплитуда, не ровно |
| **Движение мыши** | Кривые Безье, не прямые линии |
| **Клики** | Небольшое смещение от центра |
| **Паузы** | Иногда "задумываемся" на 1-3 сек |

### Пример

```python
class HumanBehavior:
    async def human_type(self, element, text: str):
        """Печать с человеческой скоростью"""
        for char in text:
            await element.type(char)
            await asyncio.sleep(random.uniform(0.05, 0.15))

    async def human_move_and_click(self, page, element):
        """Движение мыши по кривой Безье"""
        box = await element.bounding_box()

        # Случайное смещение от центра
        target_x = box["x"] + box["width"] / 2 + random.uniform(-5, 5)
        target_y = box["y"] + box["height"] / 2 + random.uniform(-5, 5)

        # Bezier curve movement
        await self._bezier_move(page, target_x, target_y)
        await element.click()
```

---

## API

### Account Management

```
POST   /account/{id}/create          Создать аккаунт (генерирует fingerprint)
POST   /account/{id}/start           Запустить браузер
POST   /account/{id}/login           Логин (phone, password)
POST   /account/{id}/sms             Ввести SMS код
POST   /account/{id}/stop            Остановить браузер
DELETE /account/{id}                 Удалить аккаунт
GET    /account/{id}/status          Статус аккаунта
GET    /accounts                     Список всех аккаунтов
```

### Messaging

```
GET    /account/{id}/chats           Список чатов
GET    /account/{id}/messages/{chat} Сообщения чата
POST   /account/{id}/send            Отправить сообщение
POST   /account/{id}/read/{chat}     Прочитать чат
WS     /account/{id}/stream          WebSocket стрим сообщений
```

### Interception

```
POST   /account/{id}/intercept/start   Начать перехват трафика
POST   /account/{id}/intercept/stop    Остановить перехват
GET    /account/{id}/hash-id           Получить извлечённый hash_id
GET    /account/{id}/traffic           Лог трафика (debug)
GET    /account/{id}/cookies           Текущие cookies
```

### Proxy

```
GET    /proxies                      Список прокси
GET    /proxy/{id}/status            Статус прокси
POST   /proxy/{id}/rotate            Сменить IP (для mobile)
POST   /account/{id}/proxy/{proxy}   Привязать прокси к аккаунту
```

### Health

```
GET    /health                       Здоровье сервиса
GET    /metrics                      Метрики (latency, success rate)
```

---

## Риски и митигация

| Риск | Вероятность | Митигация |
|------|:-----------:|-----------|
| **TLS Fingerprint** | Средняя | Camoufox рандомизирует JA3/JA4 |
| **CDP детект** | Низкая | CDP не меняет navigator.webdriver |
| **IP reputation** | Высокая | Mobile proxy с реальными SIM |
| **Behavioral analysis** | Средняя | HumanBehavior module |
| **Canvas fingerprint** | Средняя | Стабильный seed в fingerprint.json |
| **Timezone mismatch** | Низкая | Синхронизация с IP геолокацией |
| **Rate limiting** | Средняя | Распределение по аккаунтам/IP |
| **Captcha** | Средняя | Webhook в Android для ручного решения |

---

## Сравнение с mcp-avito-camoufox

| Возможность | mcp-avito-camoufox | avito-plus |
|-------------|:------------------:|:----------:|
| DOM parsing | ✅ | ✅ |
| hash_id extraction | ❌ | ✅ |
| WebSocket messages | ❌ | ✅ |
| Real-time push | ❌ | ✅ |
| Full traffic visibility | ❌ | ✅ |
| Mobile proxy | ❌ | ✅ |
| Stable fingerprint | ⚠️ Частично | ✅ |
| Human behavior | ❌ | ✅ |

---

## Roadmap

### Phase 1: MVP — CDP Interception + Home PC Proxy

**Цель:** Доказать что CDP interception работает, получаем real-time сообщения.

**Инфраструктура:**
- [ ] WireGuard сервер на 155.212.221.189
- [ ] WireGuard + 3proxy/microsocks на домашнем ПК

**Сервис avito-plus:**
- [ ] Базовый сервер FastAPI
- [ ] CDP session management
- [ ] Network interception (все HTTP/WS)
- [ ] hash_id extraction из WebSocket URL
- [ ] WebSocket message capture (входящие сообщения!)
- [ ] Proxy через домашний ПК (SOCKS5 over WireGuard)
- [ ] Webhook в n8n при новом сообщении

**Тестирование:**
- [ ] Авторизация через CDP
- [ ] Перехват hash_id
- [ ] Получение push-сообщения в реальном времени
- [ ] Проверка что Avito видит IP домашнего ПК

### Phase 2: GL.iNet + Multi-tenant

**Цель:** Масштабирование на несколько клиентов, plug & play устройства.

- [ ] GL.iNet настройка и документация
- [ ] ProxyPool manager с health checking
- [ ] Привязка аккаунт → прокси
- [ ] Auto-switch при падении прокси
- [ ] Geo-matching (IP timezone = browser timezone)

### Phase 3: Anti-Detect

**Цель:** Минимизация риска блокировки.

- [ ] Stable FingerprintManager
- [ ] HumanBehavior module (delays, curves)
- [ ] Traffic logging и анализ паттернов Avito
- [ ] Captcha detection → Android webhook

### Phase 4: Production

**Цель:** Надёжная работа 24/7.

- [ ] Metrics и мониторинг (Prometheus/Grafana)
- [ ] Auto-recovery при падении
- [ ] Multi-server deployment
- [ ] Android app integration (AvitoPlusSetupActivity)

---

## Структура проекта

```
avito-plus/
├── README.md                 # Этот файл
├── requirements.txt          # Python dependencies
├── Dockerfile                # Container build
├── docker-compose.yml        # Deployment
│
├── config/
│   ├── settings.yaml         # Основные настройки
│   └── proxies.yaml          # Конфигурация прокси
│
├── src/
│   ├── __init__.py
│   ├── server.py             # FastAPI server
│   ├── account_manager.py    # Account lifecycle
│   ├── avito_interceptor.py  # CDP interception
│   ├── fingerprint_manager.py # Fingerprint persistence
│   ├── proxy_pool.py         # Proxy management
│   ├── human_behavior.py     # Human simulation
│   ├── message_router.py     # Message routing
│   └── models.py             # Pydantic models
│
├── tests/
│   ├── test_interceptor.py
│   ├── test_fingerprint.py
│   └── test_proxy.py
│
└── data/                     # Runtime data (не в git)
    └── {account_id}/
        ├── fingerprint.json
        ├── profile/
        └── state.json
```

---

## Зависимости

```
# requirements.txt

fastapi>=0.104.0
uvicorn>=0.24.0
camoufox>=0.4.0
playwright>=1.40.0
pydantic>=2.5.0
pyyaml>=6.0
aiohttp>=3.9.0
websockets>=12.0
python-multipart>=0.0.6
```

---

## Запуск

```bash
# Development
cd avito-plus
pip install -r requirements.txt
camoufox fetch
python -m src.server

# Docker
docker-compose up -d
```

---

## Ссылки

- [mcp-avito-camoufox](../mcp-avito-camoufox/) — предыдущая версия
- [AVITO_RESEARCH.md](../mcp-avito-camoufox/AVITO_RESEARCH.md) — исследование API
- [Chrome DevTools Protocol](https://chromedevtools.github.io/devtools-protocol/)
- [Camoufox](https://github.com/nickheal/camoufox)

---

*Документ создан: 2025-12-27*
