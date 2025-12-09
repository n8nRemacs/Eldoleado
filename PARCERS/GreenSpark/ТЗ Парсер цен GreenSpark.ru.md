# ТЗ: Парсер цен GreenSpark.ru

## Контекст проекта

Разработка системы мониторинга цен и наличия товаров с сайта green-spark.ru (B2B/B2C дистрибьютор запчастей для мобильных устройств) для бизнеса по ремонту телефонов.

---

## Разведанное API

### Базовый URL
```
https://green-spark.ru/local/api/
```

### Рабочие endpoints

#### 1. Поиск товаров (основной, без авторизации)
```
GET /local/api/catalog/fast-search/
```

**Параметры:**
- `q` — поисковый запрос (обязательный)
- `onlyAvailable` — только в наличии (true/false)
- `sectionId` — ID раздела каталога (0 = все)
- `page` — номер страницы (начинается с 1)

**Пример:**
```
https://green-spark.ru/local/api/catalog/fast-search/?q=акб+iphone&onlyAvailable=true&page=2
```

**Ответ содержит:**
```json
{
  "data": [
    {
      "id": 77576,
      "name": "Аккумулятор для iPhone 5S/5C (Vixion) усиленная (1800 mAh)",
      "url": "/catalog/.../akkumulyator_dlya_iphone_5s_5c_vixion.html",
      "quantity": "few|sufficient|none",
      "prices": [
        {
          "id": "530421",
          "name": "Розница",
          "price": 630,
          "priceFormatted": "630 ₽"
        },
        {
          "id": "530430", 
          "name": "Тариф Грин 5",
          "price": 523,
          "priceFormatted": "523 ₽"
        }
      ],
      "availableStores": [
        {
          "id": 16344,
          "name": "GreenSpark Астрахань",
          "address": "ул. Савушкина, 46",
          "quantity": "sufficient"
        }
      ]
    }
  ],
  "meta": {
    "pageNumber": 1,
    "nextPageNumber": 2,
    "pageSize": 10,
    "pageCount": 15,
    "total": 145
  }
}
```

#### 2. Список магазинов (требует cookies)
```
GET /local/api/shop/list/
```
Данные уже получены — 67 физических магазинов, файл `greenspark_physical_shops.csv`

#### 3. Установка магазина
```
GET /local/api/shop/set/{shop_id}/
```

---

## Задачи

### Этап 1: Сбор структуры каталога

**Цель:** Получить дерево категорий с ID разделов

**Подход:** Парсить URL товаров из поиска, извлекать структуру разделов:
```
/catalog/aksessuary_dlya_mobilnykh_ustroystv/
        /akkumulyatornye_batarei_elementy_pitaniya/
        /akb_dlya_mobilnykh_ustroystv/
        /akb_dlya_sotovykh_telefonov/
        /akb_dlya_iphone/
```

**Или** найти endpoint `/local/api/catalog/menu/` или `/local/api/catalog/sections/` через DevTools.

**Результат:** JSON-файл с деревом категорий и их sectionId

---

### Этап 2: Парсер товаров по категориям

**Целевые категории (уточнить у заказчика):**
- АКБ для iPhone
- АКБ для Samsung
- АКБ для Xiaomi
- Дисплеи (опционально)

**Логика:**
1. Загрузить список поисковых запросов или sectionId
2. Для каждой категории пройти все страницы пагинации
3. Сохранить в БД/JSON: id, name, prices, quantity, availableStores
4. Rate limiting: 1-2 запроса в секунду

**Результат:** SQLite база или JSON с товарами

---

### Этап 3: Система мониторинга

**Функции:**
1. **Ежедневный перепарсинг** — cron/scheduler раз в сутки
2. **Отслеживание изменений цен** — сравнение с предыдущим значением
3. **Уведомления** — Telegram бот при изменении цены > X%
4. **Проверка по запросу** — API endpoint или команда для проверки конкретного товара

**Хранение:**
```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY,
    greenspark_id INTEGER UNIQUE,
    name TEXT,
    url TEXT,
    category TEXT
);

CREATE TABLE prices (
    id INTEGER PRIMARY KEY,
    product_id INTEGER,
    price_retail REAL,
    price_green5 REAL,
    quantity TEXT,
    store_id INTEGER,
    parsed_at DATETIME,
    FOREIGN KEY (product_id) REFERENCES products(greenspark_id)
);

CREATE TABLE price_changes (
    id INTEGER PRIMARY KEY,
    product_id INTEGER,
    old_price REAL,
    new_price REAL,
    change_percent REAL,
    detected_at DATETIME
);
```

---

## Технические требования

### Стек
- **Python 3.11+**
- **httpx** или **requests** — HTTP клиент
- **SQLite** — хранение данных
- **APScheduler** или **schedule** — планировщик
- **python-telegram-bot** — уведомления (опционально)

### Структура проекта
```
greenspark-parser/
├── config.py           # Настройки, API URLs, интервалы
├── api_client.py       # Класс для работы с API GreenSpark
├── parser.py           # Логика парсинга категорий
├── database.py         # Работа с SQLite
├── scheduler.py        # Планировщик задач
├── notifier.py         # Telegram уведомления
├── main.py             # Точка входа
├── data/
│   ├── shops.json      # Список магазинов
│   ├── categories.json # Структура каталога
│   └── greenspark.db   # SQLite база
└── requirements.txt
```

### Конфигурация
```python
# config.py
BASE_URL = "https://green-spark.ru/local/api"
SEARCH_ENDPOINT = "/catalog/fast-search/"

# Целевые поисковые запросы
SEARCH_QUERIES = [
    "акб iphone",
    "акб samsung", 
    "акб xiaomi",
    "акб huawei",
]

# Или целевые sectionId (если найдём)
TARGET_SECTIONS = {
    "akb_iphone": 12345,
    "akb_samsung": 12346,
}

# Rate limiting
REQUEST_DELAY = 1.0  # секунд между запросами
MAX_PAGES = 100      # максимум страниц на запрос

# Уведомления
TELEGRAM_BOT_TOKEN = "xxx"
TELEGRAM_CHAT_ID = "xxx"
PRICE_CHANGE_THRESHOLD = 5  # процентов
```

---

## API Client (базовая реализация)

```python
# api_client.py
import httpx
import time
from config import BASE_URL, SEARCH_ENDPOINT, REQUEST_DELAY

class GreenSparkAPI:
    def __init__(self):
        self.client = httpx.Client(timeout=30.0)
        self.last_request = 0
    
    def _rate_limit(self):
        elapsed = time.time() - self.last_request
        if elapsed < REQUEST_DELAY:
            time.sleep(REQUEST_DELAY - elapsed)
        self.last_request = time.time()
    
    def search(self, query: str, page: int = 1, only_available: bool = True) -> dict:
        self._rate_limit()
        params = {
            "q": query,
            "onlyAvailable": str(only_available).lower(),
            "page": page
        }
        response = self.client.get(f"{BASE_URL}{SEARCH_ENDPOINT}", params=params)
        response.raise_for_status()
        return response.json()
    
    def search_all_pages(self, query: str) -> list:
        """Получить все товары по запросу со всех страниц"""
        all_products = []
        page = 1
        
        while True:
            data = self.search(query, page=page)
            products = data.get("data", [])
            all_products.extend(products)
            
            meta = data.get("meta", {})
            if page >= meta.get("pageCount", 1):
                break
            page += 1
        
        return all_products
```

---

## Приоритеты разработки

1. **MVP (1 день):**
   - api_client.py — работает
   - Парсинг одной категории в JSON
   - Вывод в консоль

2. **v1.0 (2-3 дня):**
   - SQLite хранение
   - Парсинг всех целевых категорий
   - CLI команды: `parse`, `check <product_id>`, `report`

3. **v2.0 (опционально):**
   - Telegram бот
   - Автоматический мониторинг
   - Web-интерфейс для просмотра цен

---

## Файлы для старта

В директории проекта уже есть:
- `greenspark_physical_shops.csv` — 67 магазинов с адресами
- `greenspark_all_shops_flat.json` — JSON всех точек
- `shop_ids.txt` — список ID магазинов

---

## Вопросы для уточнения

1. Какие конкретно категории товаров парсить? (АКБ iPhone/Samsung/Xiaomi/все?)
2. Какой магазин отслеживать по умолчанию? (Москва/все/конкретный ID?)
3. Нужен ли Telegram бот или достаточно CLI?
4. Формат отчётов — Excel/CSV/JSON?
5. Где будет запускаться — локально Windows / VPS Linux?

---

## Команда для запуска Claude Code

```bash
claude
# Затем:
# > Прочитай файл GreenSpark_Parser_TZ.md и начни с создания api_client.py
```