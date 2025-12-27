# Avito-Plus TODO

**Дата:** 2025-12-27
**Статус:** В разработке (заблокирован residential IP)

---

## Что сделано

| Задача | Статус |
|--------|--------|
| Развёрнут сервер 85.198.86.96 | Done |
| Установлен Docker, nginx, certbot | Done |
| Настроен HTTPS (msg.eldoleado.ru) | Done |
| Настроен WireGuard VPN (51820) | Done |
| Создан Docker-образ avito-plus | Done |
| Запущен контейнер (порт 8794) | Done |
| Health check работает | Done |
| Сгенерированы ключи для клиента | Done |
| Подключен домашний ПК через WireGuard | Done |
| Настроен SOCKS5 прокси (3proxy) | Done |
| Исправлен Camoufox API (async context manager) | Done |
| Исправлен WebSocket interception (route_web_socket вместо CDP) | Done |

---

## На чём остановились

**Проблема:** Домашний IP (31.57.108.168) заблокирован Avito.

При попытке зайти через residential прокси:
```
Доступ ограничен: проблема с IP
```

Это блокировка на уровне Avito, не техническая проблема с нашей стороны.

---

## Что работает

| Компонент | URL/Команда | Статус |
|-----------|-------------|--------|
| HTTPS endpoint | https://msg.eldoleado.ru/health | OK |
| Docker контейнер | `docker ps` - avito-plus running | OK |
| WireGuard VPN | `wg show` - peer connected | OK |
| SOCKS5 прокси | `curl --socks5 10.0.10.2:1080 https://api.ipify.org` → 31.57.108.168 | OK |
| Camoufox браузер | Запускается, страницы грузит | OK |
| Форма логина Avito | Открывается, поля заполняются | OK |

---

## Что НЕ работает

| Проблема | Причина | Решение |
|----------|---------|---------|
| Авторизация через datacenter IP | Avito блокирует серверные IP | Нужен residential прокси |
| Авторизация через residential IP | IP 31.57.108.168 заблокирован Avito | Ждать или другой IP |
| Отправка формы логина | Не отправлялась из-за блокировки IP | - |

---

## Технические находки

### Camoufox API
```python
# Неправильно (старый API):
browser = AsyncCamoufox(...)
context = await browser.new_context(...)  # Error!

# Правильно:
async with AsyncCamoufox(...) as browser:
    context = await browser.new_context(...)
```

### WebSocket interception
```python
# CDP не работает с Firefox/Camoufox!
# Вместо CDP используем Playwright route_web_socket:
await page.route_web_socket("**/*socket.avito.ru*", handler)
```

### Avito форма логина
- Поля: `input[name="login"]`, `input[name="password"]`
- Кнопка: `button[type="submit"]`
- Avito автоформатирует номер: `79171708077` → `+7 917 170-80-77`

---

## WireGuard конфигурация

### Сервер (85.198.86.96)
```
Public Key: 5IwR2fPVpn+PABZW/qxSOjJAcwqsLm/kxZa1aFACrjU=
Port: 51820
Subnet: 10.0.10.0/24
```

### Клиент 1 (домашний ПК)
```
IP: 10.0.10.2
Private Key: mCOiiTjSLPPHpJxhCs8s0B+QPltjhV5/lSX6w+Y1ekk=
Public Key: ECRvGw9vMgzhgcvyNU5vatwXSJdF2qZgYrwSk/xkXiw=
SOCKS5: socks5://10.0.10.2:1080
```

---

## Следующие шаги

1. **Подождать снятия блокировки IP** (часы/дни)
   - Или использовать другой residential IP

2. **Альтернативные варианты прокси:**
   - Другой клиент с чистым IP
   - Мобильный прокси (телефон + SIM)
   - Купить residential прокси (платные сервисы)

3. **После разблокировки:**
   - Завершить тест авторизации
   - Проверить SMS-верификацию
   - Протестировать WebSocket interception
   - Извлечь hash_id из WebSocket URL

---

## Файлы проекта

```
/opt/avito-plus/                    # На сервере
├── src/
│   ├── server.py                   # FastAPI сервер
│   ├── account_manager.py          # Управление аккаунтами
│   ├── avito_interceptor.py        # WebSocket interception
│   ├── fingerprint_manager.py      # Стабильные fingerprints
│   ├── proxy_pool.py               # Пул прокси
│   ├── message_router.py           # Роутинг сообщений
│   └── models.py                   # Pydantic модели
├── config/
│   └── proxies.yaml                # Конфигурация прокси
├── Dockerfile
└── docker-compose.yml

client-config/                       # Локально
├── wg0.conf                        # WireGuard конфиг для клиента
├── 3proxy.cfg                      # SOCKS5 конфиг
└── SETUP.md                        # Инструкция
```

---

## Команды для проверки

```bash
# Проверить контейнер
ssh root@85.198.86.96 "docker ps && docker logs avito-plus --tail 20"

# Проверить WireGuard
ssh root@85.198.86.96 "wg show"

# Проверить прокси
ssh root@85.198.86.96 "curl -s --socks5 10.0.10.2:1080 https://api.ipify.org"

# Проверить health
curl https://msg.eldoleado.ru/health
```

---

*Последнее обновление: 2025-12-27 18:30*
