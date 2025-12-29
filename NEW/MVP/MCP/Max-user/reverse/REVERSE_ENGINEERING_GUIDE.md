# MAX APK Reverse Engineering Guide

> Инструкция по быстрому извлечению протокола из новых версий MAX APK

## TL;DR — Быстрый путь

```bash
# 1. Декомпилировать APK
jadx -d output MAX.apk --show-bad-code

# 2. Найти opcodes (главный файл)
grep -r "SESSION_INIT\|AUTH_REQUEST\|MSG_SEND" output/sources/defpackage/ | head -1
# Результат: defpackage/xhb.java — здесь ВСЕ opcodes

# 3. Найти protobuf структуры
cat output/sources/ru/ok/tamtam/nano/Tasks.java | head -100
# Здесь ВСЕ protobuf-структуры запросов

# 4. Найти сервер подключения
grep -r "api.oneme\|ws-api\|platform-api" output/sources/
# Результат: defpackage/xo4.java, defpackage/nr3.java
```

---

## Структура декомпилированного APK

```
output/sources/
├── defpackage/           # Обфусцированные классы (ГЛАВНОЕ)
│   ├── xhb.java          # ⭐ OPCODES — все команды протокола
│   ├── xo4.java          # ⭐ СЕРВЕР — api.oneme.ru:443
│   ├── nr3.java          # Конфигурация сервера (host, port, tls)
│   ├── usb.java          # Типы задач (TYPE_MSG_SEND, etc.)
│   └── ...
│
├── ru/ok/tamtam/nano/    # Protobuf структуры
│   ├── Tasks.java        # ⭐ ЗАДАЧИ — MsgSend, MsgDelete, ChatCreate...
│   ├── Protos.java       # ⭐ ДАННЫЕ — Attaches, Photo, Video, Audio...
│   └── a.java            # Вспомогательные классы
│
├── ru/ok/tamtam/android/prefs/
│   └── PmsKey.java       # Ключи настроек (max-msg-length, etc.)
│
└── one/me/login/         # UI авторизации
    ├── inputphone/       # Ввод номера телефона
    └── confirm/          # Подтверждение SMS
```

---

## Ключевые файлы

### 1. xhb.java — Все Opcodes

**Путь:** `defpackage/xhb.java`

**Как найти:**
```bash
grep -l "PING\|SESSION_INIT\|MSG_SEND" output/sources/defpackage/*.java
```

**Формат данных:**
```java
// Каждый opcode определяется так:
new xhb("MSG_SEND", 60, (short) 64, null);
//       ^name     ^idx   ^opcode    ^handler

// Извлечение:
grep "new xhb(" xhb.java | sed 's/.*new xhb("\([^"]*\)".*short) \([0-9]*\).*/\2: \1/'
```

**Пример вывода:**
```
1: PING
6: SESSION_INIT
17: AUTH_REQUEST
18: AUTH
19: LOGIN
64: MSG_SEND
66: MSG_DELETE
128: NOTIF_MESSAGE
```

### 2. Tasks.java — Protobuf структуры

**Путь:** `ru/ok/tamtam/nano/Tasks.java`

**Как найти:**
```bash
grep -l "class MsgSend\|class MsgDelete" output/sources/ru/ok/tamtam/nano/*.java
```

**Формат данных:**
```java
public static final class MsgSend extends fl9 {
    public long chatId;           // Поле protobuf
    public long messageId;
    public boolean notify;
    // ...
}
```

**Извлечение полей:**
```bash
# Найти все поля структуры MsgSend
sed -n '/class MsgSend/,/^    public static final class /p' Tasks.java | grep "public.*;"
```

### 3. xo4.java — Конфигурация сервера

**Путь:** `defpackage/xo4.java`

**Как найти:**
```bash
grep -l "api.oneme\|443" output/sources/defpackage/*.java
```

**Что искать:**
```java
this.j = new nr3("api.oneme.ru", "443", true);
//             ^host            ^port  ^tls
```

### 4. Protos.java — Типы вложений

**Путь:** `ru/ok/tamtam/nano/Protos.java`

**Типы Attach:**
```java
public static final int PHOTO = 2;
public static final int VIDEO = 3;
public static final int AUDIO = 4;
public static final int STICKER = 5;
public static final int FILE = 10;
public static final int CONTACT = 11;
public static final int LOCATION = 14;
```

---

## Скрипт автоматического извлечения

Сохранить как `extract_protocol.sh`:

```bash
#!/bin/bash
# extract_protocol.sh — Извлечение протокола из MAX APK

APK_PATH=$1
OUTPUT_DIR=${2:-"./output"}

if [ -z "$APK_PATH" ]; then
    echo "Usage: $0 <path-to-max.apk> [output-dir]"
    exit 1
fi

echo "=== Декомпиляция APK ==="
jadx -d "$OUTPUT_DIR" "$APK_PATH" --show-bad-code

echo ""
echo "=== OPCODES (xhb.java) ==="
OPCODES_FILE=$(find "$OUTPUT_DIR" -name "*.java" -exec grep -l "SESSION_INIT.*short.*6" {} \; | head -1)
echo "Файл: $OPCODES_FILE"
grep "new xhb(" "$OPCODES_FILE" | \
    sed 's/.*new xhb("\([^"]*\)".*short) \([0-9]*\).*/| \2 | \1 |/' | \
    sort -t'|' -k2 -n

echo ""
echo "=== СЕРВЕР ==="
grep -rh "api.oneme\|ws-api\|platform-api" "$OUTPUT_DIR/sources/" | head -5

echo ""
echo "=== PROTOBUF СТРУКТУРЫ ==="
TASKS_FILE="$OUTPUT_DIR/sources/ru/ok/tamtam/nano/Tasks.java"
if [ -f "$TASKS_FILE" ]; then
    grep "public static final class.*extends fl9" "$TASKS_FILE" | \
        sed 's/.*class \([^ ]*\).*/\1/' | sort
else
    echo "Tasks.java не найден!"
fi

echo ""
echo "=== ТИПЫ ВЛОЖЕНИЙ ==="
PROTOS_FILE="$OUTPUT_DIR/sources/ru/ok/tamtam/nano/Protos.java"
if [ -f "$PROTOS_FILE" ]; then
    grep "public static final int.*=" "$PROTOS_FILE" | head -20
fi
```

---

## Сравнение версий

### Скрипт сравнения opcodes

```bash
#!/bin/bash
# compare_opcodes.sh — Сравнение opcodes между версиями

OLD_XHB=$1
NEW_XHB=$2

extract_opcodes() {
    grep "new xhb(" "$1" | \
        sed 's/.*new xhb("\([^"]*\)".*short) \([0-9]*\).*/\2:\1/' | \
        sort -t':' -k1 -n
}

echo "=== Новые opcodes ==="
diff <(extract_opcodes "$OLD_XHB") <(extract_opcodes "$NEW_XHB") | grep "^>" | sed 's/^> //'

echo ""
echo "=== Удалённые opcodes ==="
diff <(extract_opcodes "$OLD_XHB") <(extract_opcodes "$NEW_XHB") | grep "^<" | sed 's/^< //'

echo ""
echo "=== Изменённые opcodes ==="
diff <(extract_opcodes "$OLD_XHB") <(extract_opcodes "$NEW_XHB") | grep "^[0-9]"
```

### Сравнение protobuf структур

```bash
#!/bin/bash
# compare_tasks.sh — Сравнение структур запросов

OLD_TASKS=$1
NEW_TASKS=$2

extract_classes() {
    grep "public static final class.*extends fl9" "$1" | \
        sed 's/.*class \([^ ]*\).*/\1/' | sort
}

echo "=== Новые структуры ==="
diff <(extract_classes "$OLD_TASKS") <(extract_classes "$NEW_TASKS") | grep "^>" | sed 's/^> //'

echo ""
echo "=== Удалённые структуры ==="
diff <(extract_classes "$OLD_TASKS") <(extract_classes "$NEW_TASKS") | grep "^<" | sed 's/^< //'
```

---

## Что обновлять при изменениях

### 1. Новый opcode

**Найти:**
```bash
diff old/xhb.java new/xhb.java | grep "new xhb"
```

**Обновить:**
- `MAX_USER_API.md` — таблица opcodes
- `max_user_client.py` — класс `Opcodes`

### 2. Изменение структуры сообщения

**Найти:**
```bash
diff old/Tasks.java new/Tasks.java | grep -A10 "class MsgSend"
```

**Обновить:**
- `MAX_USER_API.md` — раздел Protobuf
- `max_user_client.py` — метод `send_message()`

### 3. Новый сервер

**Найти:**
```bash
grep -r "oneme\|max.ru" new/sources/defpackage/*.java
```

**Обновить:**
- `max_user_client.py` — `WS_URL`
- `MAX_USER_API.md` — раздел "Подключение"

---

## Инструменты

### Установка jadx

```bash
# macOS
brew install jadx

# Linux
wget https://github.com/skylot/jadx/releases/download/v1.5.0/jadx-1.5.0.zip
unzip jadx-1.5.0.zip -d /opt/jadx
export PATH=$PATH:/opt/jadx/bin

# Windows
# Скачать с https://github.com/skylot/jadx/releases
# Разархивировать в C:\tools\jadx
```

### Полезные команды

```bash
# Найти все Java-файлы с определённым текстом
grep -r "искомый_текст" output/sources/ --include="*.java"

# Найти класс по имени метода
grep -r "methodName" output/sources/ -l

# Извлечь все строковые константы
grep -rh "\"[a-zA-Z0-9._-]*\"" output/sources/ | sort -u

# Найти все URL
grep -roh "https\?://[^\"']*" output/sources/ | sort -u
```

---

## FAQ

### Q: Почему имена классов обфусцированы (xhb, nr3, etc.)?
A: ProGuard/R8 обфускация. Главные файлы в `defpackage/` — это обфусцированные классы из основного кода.

### Q: Как найти конкретную функцию?
A: Ищите по характерным строкам:
```bash
grep -r "auth forbidden" output/  # Ошибка авторизации
grep -r "timeout" output/          # Таймаут
```

### Q: Где WebSocket endpoint?
A: Android использует TCP + Protobuf (`api.oneme.ru:443`), а не WebSocket. WebSocket (`ws-api.oneme.ru`) — только для веба.

### Q: Как понять формат пакета?
A: Смотрите класс, который реализует `cb3` интерфейс (connect/close методы).

---

## Контрольный список обновления

При получении нового APK:

- [ ] Декомпилировать: `jadx -d output NEW_MAX.apk`
- [ ] Сравнить xhb.java: новые/удалённые opcodes
- [ ] Сравнить Tasks.java: изменения в структурах
- [ ] Проверить xo4.java: изменение сервера
- [ ] Проверить Protos.java: новые типы вложений
- [ ] Обновить MAX_USER_API.md
- [ ] Обновить max_user_client.py
- [ ] Протестировать подключение
