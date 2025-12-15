# VPN Credentials

## Серверы
- RU-сервер: 45.144.177.128
- FI-сервер: 217.145.79.27

## AmneziaWG (основной, быстрый)
- Порт: 51821/udp
- Конфиг: amneziawg-client.conf
- Приложение: AmneziaWG (Play Store / App Store)

## Shadowsocks + Cloak (резерв)

### Cloak ключи
- Public Key: `wLZdTWugDTZkwwiAf8wbj8q/ckV5Choj5grvJRmbSwA=`
- UID: `ajvaiQa2+37osaecudK9XA==`

### Профили

| # | Маскировка | Порт | SS Password |
|---|------------|------|-------------|
| 1 | vk.com | 2443 | `mOhXW0Mj+klpwrhRTXwY71Mi2nnUv/ANNmNJ9gHD/cY=` |
| 2 | ya.ru | 8444 | `k8H55Az7fkGj80wuBL6G0E+WYOPSIvu81SSnWJTMh/0=` |
| 3 | rutube.ru | 2053 | `IsMeqvWEGoTsB+HWd9i9Z4hOJWOd8R12Z5IGuZtaWu4=` |
| 4 | kinopoisk.ru | 2083 | `PsLzhchSSOpW2xMzybrudWcFbIV9B1UO8VJTQUuZhbY=` |

### Клиентский конфиг Cloak (пример для VK)
```json
{
    "Transport": "direct",
    "ProxyMethod": "shadowsocks",
    "EncryptionMethod": "chacha20-ietf-poly1305",
    "UID": "ajvaiQa2+37osaecudK9XA==",
    "PublicKey": "wLZdTWugDTZkwwiAf8wbj8q/ckV5Choj5grvJRmbSwA=",
    "ServerName": "vk.com",
    "RemoteHost": "45.144.177.128",
    "RemotePort": "2443",
    "LocalHost": "127.0.0.1",
    "LocalPort": "1080",
    "NumConn": 4,
    "BrowserSig": "chrome",
    "StreamTimeout": 300
}
```

## Приоритеты подключения
1. AmneziaWG (быстрый)
2. SS+Cloak VK (whitelist)
3. SS+Cloak Yandex
4. SS+Cloak Rutube
5. SS+Cloak Kinopoisk

## Приложения для клиентов
- **AmneziaWG**: AmneziaWG (официальное приложение)
- **SS+Cloak Android**: Clash for Android / SagerNet
- **SS+Cloak iOS**: Shadowrocket / Stash
