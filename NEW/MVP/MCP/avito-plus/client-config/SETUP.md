# Настройка домашнего ПК как residential proxy

## Шаг 1: Установить WireGuard

1. Скачать: https://www.wireguard.com/install/
2. Установить
3. Открыть WireGuard → "Add Tunnel" → "Add empty tunnel..."
4. Вставить содержимое файла `wg0.conf`:

```ini
[Interface]
PrivateKey = mCOiiTjSLPPHpJxhCs8s0B+QPltjhV5/lSX6w+Y1ekk=
Address = 10.0.10.2/24

[Peer]
PublicKey = 5IwR2fPVpn+PABZW/qxSOjJAcwqsLm/kxZa1aFACrjU=
Endpoint = 85.198.86.96:51820
AllowedIPs = 10.0.10.0/24
PersistentKeepalive = 25
```

5. Сохранить как "avito-proxy"
6. Нажать "Activate"

## Шаг 2: Установить 3proxy (SOCKS5 сервер)

1. Скачать: https://github.com/3proxy/3proxy/releases
2. Распаковать в `C:\3proxy\`
3. Создать папку `C:\3proxy\logs\`
4. Скопировать `3proxy.cfg` в `C:\3proxy\`
5. Запустить: `C:\3proxy\bin\3proxy.exe C:\3proxy\3proxy.cfg`

## Шаг 3: Проверить

С сервера (85.198.86.96):
```bash
curl --socks5 10.0.10.2:1080 https://api.ipify.org
```

Должен показать ваш домашний IP (не серверный).

## Автозапуск 3proxy как службы

```cmd
sc create 3proxy binPath= "C:\3proxy\bin\3proxy.exe C:\3proxy\3proxy.cfg" start= auto
sc start 3proxy
```

---

## Данные для avito-plus

| Параметр | Значение |
|----------|----------|
| SOCKS5 URL | `socks5://10.0.10.2:1080` |
| WireGuard IP | 10.0.10.2 |
| Client ID | client1 |

После настройки сообщите, и я добавлю прокси в конфигурацию avito-plus.
