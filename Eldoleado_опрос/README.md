# Eldoleado Survey

Веб-форма опроса для владельцев сервисных мастерских.

## Структура проекта

```
eldoleado_survey/
├── index.html          # Главная страница с формой опроса
├── styles.css          # Стили оформления
├── script.js           # Логика формы и отправка данных
├── database/
│   └── create_table.sql  # SQL-скрипт создания таблицы
├── n8n/
│   └── workflow_survey_handler.json  # Workflow для n8n
└── README.md           # Этот файл
```

## Быстрый старт

### 1. Создание таблицы в базе данных

Выполните SQL-скрипт из `database/create_table.sql` в вашей PostgreSQL базе.

### 2. Настройка n8n workflow

1. Импортируйте `n8n/workflow_survey_handler.json` в n8n
2. Замените `YOUR_POSTGRES_CREDENTIAL_ID` на ID ваших credentials PostgreSQL
3. Замените `YOUR_TELEGRAM_CREDENTIAL_ID` и `YOUR_TELEGRAM_CHAT_ID` для уведомлений
4. Активируйте workflow

### 3. Настройка веб-страницы

В файле `script.js` укажите URL вашего webhook:

```javascript
const CONFIG = {
    WEBHOOK_URL: 'https://n8n.n8nsrv.ru/webhook/eldoleado-survey',
    DEV_MODE: false
};
```

### 4. Размещение страницы

Разместите файлы `index.html`, `styles.css`, `script.js` на любом веб-сервере или хостинге:
- GitHub Pages
- Netlify
- Vercel
- Любой другой статический хостинг

## Режим разработки

Для тестирования без сервера установите `DEV_MODE: true` в `script.js`.
Данные будут сохраняться в localStorage браузера.

Для просмотра сохранённых данных в консоли:
```javascript
surveyDebug.getStoredResponses()
```

## Экспорт данных для AI

SQL-запрос для экспорта всех ответов в JSON:

```sql
SELECT jsonb_agg(to_jsonb(r))
FROM eldoleado_survey_responses r;
```

## Полезные SQL-запросы

### Общая статистика
```sql
SELECT
    COUNT(*) as total_responses,
    COUNT(DISTINCT q5_city) as unique_cities,
    COUNT(*) FILTER (WHERE q47_want_beta = 'yes') as want_beta
FROM eldoleado_survey_responses;
```

### Топ проблемы
```sql
SELECT
    COUNT(*) FILTER (WHERE q37_rank_messengers = 1) as messengers_top,
    COUNT(*) FILTER (WHERE q37_rank_night_messages = 1) as night_top,
    COUNT(*) FILTER (WHERE q37_rank_suppliers = 1) as suppliers_top,
    COUNT(*) FILTER (WHERE q37_rank_phone_agreements = 1) as phone_top,
    COUNT(*) FILTER (WHERE q37_rank_advertising = 1) as advertising_top,
    COUNT(*) FILTER (WHERE q37_rank_retention = 1) as retention_top
FROM eldoleado_survey_responses;
```

### Готовность платить
```sql
SELECT q41_willingness_to_pay, COUNT(*) as count
FROM eldoleado_survey_responses
GROUP BY q41_willingness_to_pay
ORDER BY count DESC;
```

## Вопросы анкеты

Анкета содержит 49 вопросов, разбитых на 10 блоков:

1. **О мастерской** (5 вопросов) - возраст, размер, клиенты, чек, город
2. **Каналы связи** (4 вопроса) - мессенджеры, путаница
3. **Нерабочее время** (4 вопроса) - вечерние/ночные сообщения
4. **Звонки** (5 вопросов) - фиксация, запись
5. **Поставщики** (5 вопросов) - прайсы, поиск запчастей
6. **Маркетинг** (8 вопросов) - реклама, конверсия, CAC
7. **Удержание** (5 вопросов) - повторные клиенты, база
8. **Главные боли** (3 вопроса) - ранжирование проблем
9. **Готовность платить** (5 вопросов) - ценовые ожидания
10. **Завершение** (5 вопросов) - контакты, бета-тест
