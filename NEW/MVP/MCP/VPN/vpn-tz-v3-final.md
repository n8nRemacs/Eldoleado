# ТЗ: Неубиваемая VPN-инфраструктура v3.0 (Финал)

## Статус: Декабрь 2025

**Заблокировано РКН:**
- ❌ VLESS (детектируется по паттернам)
- ❌ Trojan (похож на VLESS)
- ❌ OpenVPN/WireGuard без обфускации
- ❌ L2TP, SOCKS5

**Работает:**
- ✅ AmneziaWG 1.5
- ✅ Shadowsocks-2022 + Cloak
- ✅ NaiveProxy
- ✅ OpenVPN + Cloak

---

## Архитектура

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              КЛИЕНТ                                         │
│  ┌────────────────────────────────────────────────────────────────────┐    │
│  │                      Приоритеты подключения                        │    │
│  │  ┌──────────┬──────────┬──────────┬──────────┬──────────┐         │    │
│  │  │    1     │    2     │    3     │    4     │    5     │         │    │
│  │  │AmneziaWG │SS+Cloak  │SS+Cloak  │SS+Cloak  │SS+Cloak  │         │    │
│  │  │(быстрый) │ vk.com   │  ya.ru   │ rutube   │kinopoisk │         │    │
│  │  │ :51821   │ :443     │ :8443    │ :2053    │ :2083    │         │    │
│  │  └────┬─────┴────┬─────┴────┬─────┴────┬─────┴────┬─────┘         │    │
│  └───────┼──────────┼──────────┼──────────┼──────────┼────────────────┘    │
└──────────┼──────────┼──────────┼──────────┼──────────┼─────────────────────┘
           │          │          │          │          │
           │   РКН видит:        │          │          │
           │   "HTTPS к VK"      │          │          │
           ▼          ▼          ▼          ▼          ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                           RU-СЕРВЕР (Россия)                                │
│                                                                             │
│  ┌─────────────┐  ┌─────────────────────────────────────────────────────┐  │
│  │ AmneziaWG   │  │              Cloak + Shadowsocks                    │  │
│  │   :51821    │  │  ┌─────────┬─────────┬─────────┬─────────┐         │  │
│  │             │  │  │ :443    │ :8443   │ :2053   │ :2083   │         │  │
│  │             │  │  │ vk.com  │ ya.ru   │ rutube  │kinopoisk│         │  │
│  └──────┬──────┘  │  └────┬────┴────┬────┴────┬────┴────┬────┘         │  │
│         │         │       └─────────┴─────────┴─────────┘              │  │
│         │         │                      │                              │  │
│         │         └──────────────────────┼──────────────────────────────┘  │
│         │                                │                                  │
│         └────────────────┬───────────────┘                                  │
│                          ▼                                                  │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │                        WireGuard (wg0)                               │  │
│  │                        IP: 10.66.66.2                                │  │
│  └──────────────────────────────────┬───────────────────────────────────┘  │
└─────────────────────────────────────┼───────────────────────────────────────┘
                                      │
                            WireGuard туннель
                            (B2B, не мониторится)
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                          FI-СЕРВЕР (Финляндия)                              │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │                        WireGuard (wg0)                               │  │
│  │                        IP: 10.66.66.1                                │  │
│  └──────────────────────────────────┬───────────────────────────────────┘  │
│                                     ▼                                       │
│                              [NAT MASQUERADE]                               │
└─────────────────────────────────────┼───────────────────────────────────────┘
                                      │
                                      ▼
                              🌍 ИНТЕРНЕТ
                         (YouTube, ChatGPT, всё)
```

---

## Серверы

| Роль | IP | SSH | Назначение |
|------|----|-----|------------|
| RU-сервер | `___________` | root@22 | Точка входа |
| FI-сервер | `___________` | root@22 | Точка выхода |

---

## Протоколы и порты

| # | Протокол | Порт | Маскировка | Когда использовать |
|---|----------|------|------------|-------------------|
| 1 | AmneziaWG 1.5 | 51821/udp | Обфускация | Основной — быстрый |
| 2 | SS+Cloak | 443/tcp | vk.com | Whitelist, видео |
| 3 | SS+Cloak | 8443/tcp | ya.ru | Whitelist, сёрфинг |
| 4 | SS+Cloak | 2053/tcp | rutube.ru | Whitelist, видео |
| 5 | SS+Cloak | 2083/tcp | kinopoisk.ru | Whitelist, фильмы |

---

## Этап 0: Аудит серверов

```bash
# Выполнить на ОБОИХ серверах
echo "=== OS ===" && cat /etc/os-release
echo "=== PORTS ===" && ss -tlnp
echo "=== SERVICES ===" && systemctl list-units --type=service | grep -E "(vpn|wireguard|openvpn|xray|cloak|shadowsocks)" || echo "none"
echo "=== DOCKER ===" && docker ps -a 2>/dev/null || echo "Docker не установлен"
echo "=== DISK ===" && df -h /
echo "=== RAM ===" && free -h
```

---

## Этап 1: WireGuard туннель (RU ↔ FI)

### 1.1 FI-сервер

```bash
# Установка
apt update && apt upgrade -y
apt install -y wireguard

# IP forwarding
echo "net.ipv4.ip_forward=1" >> /etc/sysctl.conf
sysctl -p

# Ключи
cd /etc/wireguard
wg genkey | tee fi-private.key | wg pubkey > fi-public.key
chmod 600 fi-private.key

echo "=== FI PUBLIC KEY (скопировать!) ==="
cat fi-public.key

# Определить интерфейс
INTERFACE=$(ip route | grep default | awk '{print $5}')
echo "Interface: $INTERFACE"

# Конфиг (RU_PUBLIC_KEY добавить позже!)
cat > /etc/wireguard/wg0.conf << EOF
[Interface]
Address = 10.66.66.1/24
ListenPort = 51820
PrivateKey = $(cat fi-private.key)
PostUp = iptables -A FORWARD -i wg0 -j ACCEPT; iptables -t nat -A POSTROUTING -o $INTERFACE -j MASQUERADE
PostDown = iptables -D FORWARD -i wg0 -j ACCEPT; iptables -t nat -D POSTROUTING -o $INTERFACE -j MASQUERADE

[Peer]
PublicKey = <RU_PUBLIC_KEY>
AllowedIPs = 10.66.66.2/32
EOF

# Файрвол
ufw allow 51820/udp
ufw allow 22/tcp
ufw --force enable
```

### 1.2 RU-сервер

```bash
# Установка
apt update && apt upgrade -y
apt install -y wireguard

echo "net.ipv4.ip_forward=1" >> /etc/sysctl.conf
sysctl -p

# Ключи
cd /etc/wireguard
wg genkey | tee ru-private.key | wg pubkey > ru-public.key
chmod 600 ru-private.key

echo "=== RU PUBLIC KEY (добавить в FI конфиг!) ==="
cat ru-public.key

# Конфиг
cat > /etc/wireguard/wg0.conf << EOF
[Interface]
Address = 10.66.66.2/24
PrivateKey = $(cat ru-private.key)

[Peer]
PublicKey = <FI_PUBLIC_KEY>
Endpoint = <FI_IP>:51820
AllowedIPs = 0.0.0.0/0
PersistentKeepalive = 25
EOF
```

### 1.3 Запуск туннеля

**Сначала на FI** (добавить RU_PUBLIC_KEY в конфиг!):
```bash
systemctl enable wg-quick@wg0
systemctl start wg-quick@wg0
```

**Потом на RU:**
```bash
systemctl enable wg-quick@wg0
systemctl start wg-quick@wg0

# Проверка
ping -c 4 10.66.66.1
curl --interface wg0 ifconfig.me  # Должен показать FI_IP
```

---

## Этап 2: AmneziaWG на RU-сервере

### 2.1 Установка

```bash
# Добавить репозиторий
apt install -y software-properties-common
add-apt-repository -y ppa:amnezia/ppa
apt update
apt install -y amneziawg

# Или через Docker
docker run -d \
  --name=awg \
  --cap-add=NET_ADMIN \
  --cap-add=SYS_MODULE \
  -p 51821:51820/udp \
  -v /opt/awg:/config \
  --sysctl="net.ipv4.conf.all.src_valid_mark=1" \
  --restart unless-stopped \
  ghcr.io/amnezia-vpn/amneziawg:latest
```

### 2.2 Генерация ключей

```bash
wg genkey | tee /etc/amneziawg/server-private.key | wg pubkey > /etc/amneziawg/server-public.key
wg genkey | tee /etc/amneziawg/client-private.key | wg pubkey > /etc/amneziawg/client-public.key
chmod 600 /etc/amneziawg/*.key
```

### 2.3 Конфиг сервера

```bash
cat > /etc/amneziawg/awg0.conf << EOF
[Interface]
Address = 10.77.77.1/24
ListenPort = 51821
PrivateKey = $(cat /etc/amneziawg/server-private.key)

# AmneziaWG параметры обфускации
Jc = 4
Jmin = 40
Jmax = 70
S1 = 0
S2 = 0
H1 = 1
H2 = 2
H3 = 3
H4 = 4

# Маршрутизация через FI
PostUp = ip route add default via 10.66.66.1 dev wg0 table 100; ip rule add from 10.77.77.0/24 table 100
PostDown = ip route del default via 10.66.66.1 dev wg0 table 100; ip rule del from 10.77.77.0/24 table 100

[Peer]
PublicKey = $(cat /etc/amneziawg/client-public.key)
AllowedIPs = 10.77.77.2/32
EOF
```

### 2.4 Конфиг клиента (для телефона)

```bash
cat > /etc/amneziawg/client.conf << EOF
[Interface]
Address = 10.77.77.2/24
PrivateKey = $(cat /etc/amneziawg/client-private.key)
DNS = 1.1.1.1

# AmneziaWG параметры (должны совпадать с сервером!)
Jc = 4
Jmin = 40
Jmax = 70
S1 = 0
S2 = 0
H1 = 1
H2 = 2
H3 = 3
H4 = 4

[Peer]
PublicKey = $(cat /etc/amneziawg/server-public.key)
Endpoint = <RU_IP>:51821
AllowedIPs = 0.0.0.0/0
PersistentKeepalive = 25
EOF

# QR код для телефона
apt install -y qrencode
qrencode -t ansiutf8 < /etc/amneziawg/client.conf
```

### 2.5 Запуск

```bash
systemctl enable amneziawg@awg0
systemctl start amneziawg@awg0

ufw allow 51821/udp
```

---

## Этап 3: Shadowsocks + Cloak на RU-сервере

### 3.1 Установка Shadowsocks

```bash
apt install -y shadowsocks-libev
```

### 3.2 Установка Cloak

```bash
# Скачать Cloak
wget https://github.com/cbeuw/Cloak/releases/download/v2.7.0/ck-server-linux-amd64-v2.7.0
mv ck-server-linux-amd64-v2.7.0 /usr/local/bin/ck-server
chmod +x /usr/local/bin/ck-server

# Генерация ключей
/usr/local/bin/ck-server -key
# Запишите: PublicKey и PrivateKey

# Генерация UID клиента
/usr/local/bin/ck-server -uid
# Запишите: UID
```

### 3.3 Конфиг Shadowsocks (4 инстанса)

```bash
# SS для VK (порт 10001 внутренний)
cat > /etc/shadowsocks-libev/ss-vk.json << EOF
{
    "server": "127.0.0.1",
    "server_port": 10001,
    "password": "$(openssl rand -base64 32)",
    "method": "chacha20-ietf-poly1305",
    "timeout": 300
}
EOF

# SS для Yandex (порт 10002)
cat > /etc/shadowsocks-libev/ss-yandex.json << EOF
{
    "server": "127.0.0.1",
    "server_port": 10002,
    "password": "$(openssl rand -base64 32)",
    "method": "chacha20-ietf-poly1305",
    "timeout": 300
}
EOF

# SS для Rutube (порт 10003)
cat > /etc/shadowsocks-libev/ss-rutube.json << EOF
{
    "server": "127.0.0.1",
    "server_port": 10003,
    "password": "$(openssl rand -base64 32)",
    "method": "chacha20-ietf-poly1305",
    "timeout": 300
}
EOF

# SS для Kinopoisk (порт 10004)
cat > /etc/shadowsocks-libev/ss-kinopoisk.json << EOF
{
    "server": "127.0.0.1",
    "server_port": 10004,
    "password": "$(openssl rand -base64 32)",
    "method": "chacha20-ietf-poly1305",
    "timeout": 300
}
EOF
```

### 3.4 Конфиги Cloak (4 профиля маскировки)

```bash
mkdir -p /etc/cloak

# Cloak для VK (:443 → vk.com)
cat > /etc/cloak/ck-vk.json << EOF
{
    "ProxyBook": {
        "shadowsocks": ["tcp", "127.0.0.1:10001"]
    },
    "BindAddr": [":443"],
    "BypassUID": [],
    "RedirAddr": "vk.com:443",
    "PrivateKey": "<CLOAK_PRIVATE_KEY>",
    "AdminUID": "<CLOAK_UID>",
    "DatabasePath": "/etc/cloak/userinfo-vk.db",
    "StreamTimeout": 300
}
EOF

# Cloak для Yandex (:8443 → ya.ru)
cat > /etc/cloak/ck-yandex.json << EOF
{
    "ProxyBook": {
        "shadowsocks": ["tcp", "127.0.0.1:10002"]
    },
    "BindAddr": [":8443"],
    "BypassUID": [],
    "RedirAddr": "ya.ru:443",
    "PrivateKey": "<CLOAK_PRIVATE_KEY>",
    "AdminUID": "<CLOAK_UID>",
    "DatabasePath": "/etc/cloak/userinfo-yandex.db",
    "StreamTimeout": 300
}
EOF

# Cloak для Rutube (:2053 → rutube.ru)
cat > /etc/cloak/ck-rutube.json << EOF
{
    "ProxyBook": {
        "shadowsocks": ["tcp", "127.0.0.1:10003"]
    },
    "BindAddr": [":2053"],
    "BypassUID": [],
    "RedirAddr": "rutube.ru:443",
    "PrivateKey": "<CLOAK_PRIVATE_KEY>",
    "AdminUID": "<CLOAK_UID>",
    "DatabasePath": "/etc/cloak/userinfo-rutube.db",
    "StreamTimeout": 300
}
EOF

# Cloak для Kinopoisk (:2083 → kinopoisk.ru)
cat > /etc/cloak/ck-kinopoisk.json << EOF
{
    "ProxyBook": {
        "shadowsocks": ["tcp", "127.0.0.1:10004"]
    },
    "BindAddr": [":2083"],
    "BypassUID": [],
    "RedirAddr": "kinopoisk.ru:443",
    "PrivateKey": "<CLOAK_PRIVATE_KEY>",
    "AdminUID": "<CLOAK_UID>",
    "DatabasePath": "/etc/cloak/userinfo-kinopoisk.db",
    "StreamTimeout": 300
}
EOF
```

### 3.5 Systemd сервисы

```bash
# Shadowsocks сервисы
for name in vk yandex rutube kinopoisk; do
cat > /etc/systemd/system/ss-$name.service << EOF
[Unit]
Description=Shadowsocks for $name
After=network.target

[Service]
Type=simple
ExecStart=/usr/bin/ss-server -c /etc/shadowsocks-libev/ss-$name.json
Restart=always
RestartSec=3

[Install]
WantedBy=multi-user.target
EOF
done

# Cloak сервисы
for name in vk yandex rutube kinopoisk; do
cat > /etc/systemd/system/ck-$name.service << EOF
[Unit]
Description=Cloak for $name
After=network.target

[Service]
Type=simple
ExecStart=/usr/local/bin/ck-server -c /etc/cloak/ck-$name.json
Restart=always
RestartSec=3

[Install]
WantedBy=multi-user.target
EOF
done

# Перезагрузить systemd
systemctl daemon-reload
```

### 3.6 Запуск всех сервисов

```bash
# Запуск Shadowsocks
for name in vk yandex rutube kinopoisk; do
    systemctl enable ss-$name
    systemctl start ss-$name
done

# Запуск Cloak
for name in vk yandex rutube kinopoisk; do
    systemctl enable ck-$name
    systemctl start ck-$name
done

# Проверка
systemctl status ss-vk ck-vk
```

### 3.7 Файрвол

```bash
ufw allow 443/tcp    # VK
ufw allow 8443/tcp   # Yandex
ufw allow 2053/tcp   # Rutube
ufw allow 2083/tcp   # Kinopoisk
ufw allow 22/tcp
ufw --force enable
```

---

## Этап 4: Настройка клиентов

### 4.1 AmneziaWG (Android/iOS)

1. Установить приложение **AmneziaWG** из Play Store / App Store
2. Отсканировать QR или импортировать файл `client.conf`
3. Подключиться

### 4.2 Shadowsocks + Cloak (Android)

Установить: **Clash for Android** или **ShadowsocksR**

Конфиг клиента Cloak:
```json
{
    "Transport": "direct",
    "ProxyMethod": "shadowsocks",
    "EncryptionMethod": "chacha20-ietf-poly1305",
    "UID": "<CLOAK_UID>",
    "PublicKey": "<CLOAK_PUBLIC_KEY>",
    "ServerName": "vk.com",
    "NumConn": 4,
    "BrowserSig": "chrome",
    "StreamTimeout": 300
}
```

### 4.3 Shadowsocks + Cloak (iOS)

Установить: **Shadowrocket** или **Stash**

---

## Этап 5: Проверка

```bash
# На RU-сервере
ss -tlnp | grep -E "(ss-server|ck-server)"

# Ожидаемый вывод:
# LISTEN  *:443   ck-server (VK)
# LISTEN  *:8443  ck-server (Yandex)
# LISTEN  *:2053  ck-server (Rutube)
# LISTEN  *:2083  ck-server (Kinopoisk)
# LISTEN  127.0.0.1:10001  ss-server
# LISTEN  127.0.0.1:10002  ss-server
# LISTEN  127.0.0.1:10003  ss-server
# LISTEN  127.0.0.1:10004  ss-server
```

### Чек-лист

- [ ] AmneziaWG подключается
- [ ] SS+Cloak (VK) подключается
- [ ] IP = финский (whatismyipaddress.com)
- [ ] YouTube работает
- [ ] DNS не течёт

---

## Мониторинг

```bash
cat > /root/vpn-monitor.sh << 'EOF'
#!/bin/bash
TELEGRAM_BOT_TOKEN="<TOKEN>"
TELEGRAM_CHAT_ID="<CHAT_ID>"

alert() {
    curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
        -d chat_id="${TELEGRAM_CHAT_ID}" -d text="🚨 VPN: $1"
}

# Проверка сервисов
for svc in wg-quick@wg0 amneziawg@awg0 ss-vk ck-vk; do
    systemctl is-active --quiet $svc || alert "$svc упал!"
done

# Проверка туннеля
ping -c 1 -W 3 10.66.66.1 > /dev/null 2>&1 || alert "Туннель RU-FI не работает!"
EOF

chmod +x /root/vpn-monitor.sh
(crontab -l 2>/dev/null; echo "*/5 * * * * /root/vpn-monitor.sh") | crontab -
```

---

## Переменные (сводка)

```bash
# Серверы
RU_IP=
FI_IP=

# WireGuard (RU ↔ FI)
FI_PRIVATE_KEY=
FI_PUBLIC_KEY=
RU_PRIVATE_KEY=
RU_PUBLIC_KEY=

# AmneziaWG
AWG_SERVER_PRIVATE=
AWG_SERVER_PUBLIC=
AWG_CLIENT_PRIVATE=
AWG_CLIENT_PUBLIC=

# Cloak
CLOAK_PRIVATE_KEY=
CLOAK_PUBLIC_KEY=
CLOAK_UID=

# Shadowsocks пароли
SS_VK_PASSWORD=
SS_YANDEX_PASSWORD=
SS_RUTUBE_PASSWORD=
SS_KINOPOISK_PASSWORD=

# Telegram
TELEGRAM_BOT_TOKEN=
TELEGRAM_CHAT_ID=
```

---

## Fallback-система

```
┌─────────────────────────────────────────────────────────────────┐
│                     ПРИОРИТЕТЫ ПОДКЛЮЧЕНИЯ                      │
├─────────────────────────────────────────────────────────────────┤
│ 1. [Основной]     AmneziaWG → RU → WG → FI  (быстрый)          │
│ 2. [Whitelist]    SS+Cloak(vk.com) → RU → WG → FI              │
│ 3. [Резерв 1]     SS+Cloak(ya.ru) → RU → WG → FI               │
│ 4. [Резерв 2]     SS+Cloak(rutube) → RU → WG → FI              │
│ 5. [Резерв 3]     SS+Cloak(kinopoisk) → RU → WG → FI           │
│ 6. [Emergency]    AmneziaFree (если всё сломалось)             │
└─────────────────────────────────────────────────────────────────┘
```

---

## Когда какой профиль использовать

| Ситуация | Профиль |
|----------|---------|
| Обычный день | AmneziaWG |
| Замедления/нестабильность | SS+Cloak (VK) |
| Whitelist-режим | SS+Cloak (любой из 4) |
| Тяжёлый стриминг | SS+Cloak (Kinopoisk/Rutube) |
| Всё сломалось | AmneziaFree |

---

## Статус выполнения

| Этап | Описание | Статус |
|------|----------|--------|
| 0 | Аудит серверов | ⬜ |
| 1 | WireGuard RU↔FI | ⬜ |
| 2 | AmneziaWG | ⬜ |
| 3 | SS+Cloak (4 профиля) | ⬜ |
| 4 | Клиенты | ⬜ |
| 5 | Тестирование | ⬜ |
| 6 | Мониторинг | ⬜ |

---

## Troubleshooting

### AmneziaWG не подключается
- Обновить клиент до последней версии
- Проверить что UDP 51821 открыт: `nc -zvu <RU_IP> 51821`
- Параметры Jc/Jmin/Jmax должны совпадать на клиенте и сервере

### Cloak не работает
- Проверить что порт открыт: `nc -zv <RU_IP> 443`
- Проверить логи: `journalctl -u ck-vk -f`
- Убедиться что PrivateKey/PublicKey/UID правильные

### Медленная скорость
- Переключиться на AmneziaWG (быстрее чем SS+Cloak)
- Проверить нагрузку на сервере: `htop`
- Проверить скорость туннеля: `iperf3 -c 10.66.66.1`
