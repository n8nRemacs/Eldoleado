# SS+Cloak VPN - Статус на 08.12.2025

## Проблема
SS+Cloak VPN профили показывают timeout при подключении через Mihomo/Clash-Verge.

## Что было сделано

### 1. Исправлен Cloak PublicKey
**Проблема:** Старый PublicKey не соответствовал PrivateKey на сервере.

**Решение:** Сгенерирована новая пара ключей на сервере:
```
PUBLIC:  DBcQNa+fgS+POuKZlKfXdyXY9P/z+FXfW7A9bieEOTo=
PRIVATE: CH4gnqNZiuMrFCWLIGplNpnJ0qOGYkC0reyVvcN8/X8=
```

Обновлены файлы:
- Сервер: `/etc/cloak/ck-*.json` (все 4 конфига с новым PrivateKey)
- Клиент: `ck-client/ck-*.json` (все 4 конфига с новым PublicKey)

### 2. Исправлен WireGuard AllowedIPs на FI
**Проблема:** На FI сервере AllowedIPs был `10.0.0.2/32`, что блокировало пакеты с source 10.0.0.2 идущие к внешним адресам.

**Решение:** Изменено на `10.0.0.0/24` в `/etc/wireguard/wg0.conf` на FI (217.145.79.27).

### 3. Настроен Policy-Based Routing на RU
**Цель:** Трафик от SS серверов (пользователь ssproxy, UID 998) должен идти через WireGuard.

**Конфигурация:**
```bash
# Маркировка пакетов от ssproxy
iptables -t mangle -A OUTPUT -m owner --uid-owner 998 -j MARK --set-mark 0x1

# Правило роутинга для маркированных пакетов
ip rule add fwmark 0x1 table vpn

# Таблица vpn направляет через WG
ip route add default via 10.0.0.1 dev wg0 table vpn

# SNAT для правильного source IP
iptables -t nat -A POSTROUTING -o wg0 -j SNAT --to-source 10.0.0.2
```

### 4. SS серверы переведены на пользователя ssproxy
Все SS сервисы (`ss-vk`, `ss-yandex`, `ss-rutube`, `ss-kinopoisk`) теперь запускаются от пользователя `ssproxy` (UID 998).

Файлы systemd сервисов обновлены с `User=ssproxy`.

## Текущее состояние

### Работает:
- ✅ ck-client подключается к серверу (4 ESTABLISHED соединения к 45.144.177.128:2443)
- ✅ Cloak серверы работают на портах 2443, 8444, 2053, 2083
- ✅ SS серверы работают от пользователя ssproxy
- ✅ WireGuard туннель RU↔FI активен (handshake успешен)
- ✅ TCP трафик от ssproxy виден на FI wg0 интерфейсе (пакеты к Google, Facebook с source 10.0.0.2)
- ✅ Ответы возвращаются (видны в tcpdump)

### НЕ работает:
- ❌ ICMP ping от ssproxy (100% packet loss)
- ❌ curl от ssproxy (DNS timeout, потом connection timeout)
- ❌ SS через Cloak из Mihomo/Clash (timeout)

## Диагностика

### tcpdump на RU (wg0) при ping от ssproxy:
```
wg0 Out IP 10.0.0.2 > 8.8.8.8: ICMP echo request
```
Пакеты уходят через wg0.

### tcpdump на FI (wg0):
```
IP 10.0.0.2.56118 > 31.13.72.40.443: Flags [.], ack ...
IP 31.13.72.40.443 > 10.0.0.2.56118: Flags [.], ack ...
```
TCP пакеты видны и ответы приходят, но ICMP не появляется!

### Ключевое наблюдение:
TCP трафик проходит через WG и FI, но ICMP нет. Это странно, потому что:
- Пакеты ICMP видны на wg0 на RU с правильным source (10.0.0.2)
- Но они не появляются на wg0 на FI

## Возможные причины (требуют исследования)

1. **MTU проблема** - ICMP пакеты могут быть слишком большими после WG инкапсуляции
2. **Фрагментация** - WG пакеты могут фрагментироваться и теряться
3. **Firewall на промежуточных узлах** - между RU и FI может быть блокировка
4. **conntrack issues** - established TCP соединения работают, но новые ICMP нет

## Конфигурация серверов

### RU Server (45.144.177.128)
- WG IP: 10.0.0.2/24
- WG config: `/etc/wireguard/wg0.conf`
- Cloak configs: `/etc/cloak/ck-*.json`
- SS configs: `/etc/shadowsocks-libev/ss-*.json`
- Cloak ports: 2443 (VK), 8444 (Yandex), 2053 (Rutube), 2083 (Kinopoisk)

### FI Server (217.145.79.27)
- WG IP: 10.0.0.1/24
- WG port: 51820
- WG config: `/etc/wireguard/wg0.conf`
- VLESS port: 10443 (работает как резерв)

### Клиент (Windows)
- ck-client: `MCP/VPN/ck-client/` (порты 11080-11083)
- Mihomo config: `MCP/VPN/configs/mihomo-with-ck.yaml`
- Запуск: `start-all.bat`

## Следующие шаги для диагностики

1. Проверить MTU на всех интерфейсах:
   ```bash
   ip link show wg0  # на обоих серверах
   ```

2. Попробовать уменьшить MTU на WG:
   ```bash
   ip link set wg0 mtu 1280
   ```

3. Проверить tcpdump на eth0 на RU - уходят ли WG UDP пакеты к FI:
   ```bash
   tcpdump -i eth0 -n "udp port 51820 and host 217.145.79.27"
   ```

4. Проверить rp_filter на FI:
   ```bash
   sysctl net.ipv4.conf.wg0.rp_filter
   sysctl net.ipv4.conf.all.rp_filter
   ```

5. Временно отключить UFW на FI для теста:
   ```bash
   ufw disable
   ```

## Работающий резерв

VLESS на FI сервере работает! Можно использовать "FI VLESS" прокси в Mihomo пока SS+Cloak не починен.
