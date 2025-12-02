# Скрипт применения изменений

## Шаг 1: Проверить что рабочая версия на месте
```bash
# Проверить размер AppealDetailActivity.kt - должен быть ~40KB
wc -c app/src/main/java/com/batterycrm/app/AppealDetailActivity.kt
```

## Шаг 2: Собрать и проверить что работает
```bash
./gradlew.bat assembleDebug
```

## Шаг 3: Если сборка падает на отсутствующих ресурсах

### 3.1 Проверить colors.xml
Открыть `app/src/main/res/values/colors.xml` и добавить недостающие цвета из `colors_to_add.xml`

### 3.2 Проверить drawables
Если отсутствуют файлы, скопировать из этой папки:
- `bg_chip_neutral.xml` -> `app/src/main/res/drawable/`
- `bg_chip_accent.xml` -> `app/src/main/res/drawable/`
- `bg_channel_badge.xml` -> `app/src/main/res/drawable/`

### 3.3 Проверить bg_meta_item.xml
Если отсутствует, создать `app/src/main/res/drawable/bg_meta_item.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#FFFFFF" />
    <corners android:radius="8dp" />
    <stroke android:width="1dp" android:color="#E0E0E0" />
</shape>
```

## Шаг 4: Пересобрать и установить
```bash
./gradlew.bat assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.batterycrm.app/.LoginActivity
```

## Шаг 5: Проверить функциональность
1. Открыть список обращений
2. Открыть детали обращения
3. Проверить отображение всех полей
4. Проверить кнопки действий

---

## Возможные ошибки и решения

### Ошибка: "Unresolved reference: bg_chip_neutral"
**Решение**: Скопировать `bg_chip_neutral.xml` в drawable

### Ошибка: "Cannot find symbol color/text_secondary"
**Решение**: Добавить цвет в colors.xml

### Ошибка: Room database version mismatch
**Решение**:
1. Увеличить версию в AppDatabase.kt
2. Или очистить данные приложения: `adb shell pm clear com.batterycrm.app`
