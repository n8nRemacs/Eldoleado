# ПРАВИЛА РАБОТЫ С n8n (ВАЖНО!)

## 1. Merge ноды НЕ работают правильно!

**ПРОБЛЕМА**: В n8n 1.109 Merge ноды ждут ОБА входа, даже в режиме "Choose Branch".

**РЕШЕНИЕ**: Всегда заменять Merge на Code node:

```javascript
// Замена Merge на Code - пропускает данные из любого входа
const items = $input.all();
if (items.length === 0) return [];
return items[0].json;
```

---

## 2. SQL ноды теряют данные!

**ПРОБЛЕМА**: После UPDATE/INSERT нода возвращает пустой результат, исходные данные теряются.

**РЕШЕНИЕ**: После SQL нод использовать Code который берёт данные из предыдущей ноды:

```javascript
// После SQL ноды - восстанавливаем данные
let data = null;
try {
  data = $('Prepare Response').first().json;  // Указать нужную ноду
} catch (e) {}
return data || {};
```

---

## 3. Доступ к данным через IF ноду

**ПРОБЛЕМА**: `$('NodeName')` не работает если NodeName находится ДО IF ноды.

**РЕШЕНИЕ**: Использовать `$input.first().json` вместо `$('NodeName')`.

---

## 4. IF ноды и типы данных

**ПРОБЛЕМА**: `{{ $json.some_field }}` может быть строкой "true" вместо boolean.

**РЕШЕНИЕ**: В IF ноде включить **"Convert types where required"**.

---

## 5. Execute Workflow - передача данных

**ПРОБЛЕМА**: Sub-workflow не получает данные если не настроена передача.

**РЕШЕНИЕ**:
- Использовать режим "Pass input data"
- Или добавить Code ноду перед Execute Workflow для подготовки данных

---

## Текущий статус исправлений (2025-11-27)

### BAT Client Resolver
- Merge Found Client → Code node (использует $input.first().json)

### BAT Appeal Manager
- Appeal Exists? → включен "Convert types where required"
- Merge Existing Appeal → исправлен код (inputData.appeal.id)
- Prepare Contract1 → исправлены имена нод

### BAT AI Appeal Router
- Needs Extraction? → исправлена инвертированная логика
- Merge Results → Code node
- Merge After Update → Code node (берёт данные из Prepare Response)
- Save Response → message_type: 'agent' (было 'bot')
- Prepare Notifier Data → добавлена нода перед Call Operator Notifier

### БД
- Добавлена колонка `context_completion_percentage` в appeals
- База очищена от тестовых обращений

---

## Осталось сделать

1. Проверить передачу данных в BAT Operator Notifier
2. Тест полного цикла: 2+ сообщения → одно обращение (не дубликаты)
3. Проверить что AI экстракция работает (бренд/модель/ремонт)

---

## Главная проблема (РЕШЕНА)

**Было**: Создавались дубликаты обращений (72 вместо 1-2)

**Причина**:
- Merge узлы ждали оба входа
- appeal.id терялся в цепочке
- IF ноды неправильно сравнивали типы

**Решение**:
- Заменили Merge на Code nodes
- Исправили передачу appeal.id
- Включили "Convert types where required"
