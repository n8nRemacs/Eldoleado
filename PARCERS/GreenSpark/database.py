"""
Работа с SQLite базой данных для парсера GreenSpark
"""

import sqlite3
from pathlib import Path
from datetime import datetime
from typing import List, Dict, Optional, Tuple
from config import DB_FILE, DATA_DIR


def get_connection() -> sqlite3.Connection:
    """Получить соединение с БД"""
    Path(DATA_DIR).mkdir(exist_ok=True)
    conn = sqlite3.connect(DB_FILE)
    conn.row_factory = sqlite3.Row
    return conn


def init_database():
    """Создать таблицы БД"""
    conn = get_connection()
    cursor = conn.cursor()

    # Таблица товаров
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS products (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            greenspark_id INTEGER UNIQUE NOT NULL,
            name TEXT NOT NULL,
            url TEXT,
            category TEXT,
            brand TEXT,
            part_type TEXT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    """)

    # Таблица цен (история)
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS prices (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            product_id INTEGER NOT NULL,
            price_retail REAL,
            price_green5 REAL,
            quantity TEXT,
            parsed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (product_id) REFERENCES products(id)
        )
    """)

    # Таблица наличия по магазинам
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS availability (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            product_id INTEGER NOT NULL,
            store_id INTEGER NOT NULL,
            store_name TEXT,
            store_address TEXT,
            quantity TEXT,
            parsed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (product_id) REFERENCES products(id)
        )
    """)

    # Таблица изменений цен
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS price_changes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            product_id INTEGER NOT NULL,
            price_type TEXT NOT NULL,
            old_price REAL,
            new_price REAL,
            change_percent REAL,
            detected_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (product_id) REFERENCES products(id)
        )
    """)

    # Таблица логов парсинга
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS parse_logs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            query TEXT,
            products_found INTEGER,
            products_new INTEGER,
            products_updated INTEGER,
            price_changes INTEGER,
            started_at DATETIME,
            finished_at DATETIME,
            status TEXT
        )
    """)

    # Индексы для быстрого поиска
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_products_greenspark_id ON products(greenspark_id)")
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_products_brand ON products(brand)")
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_prices_product_id ON prices(product_id)")
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_prices_parsed_at ON prices(parsed_at)")
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_availability_product_id ON availability(product_id)")
    cursor.execute("CREATE INDEX IF NOT EXISTS idx_price_changes_product_id ON price_changes(product_id)")

    conn.commit()
    conn.close()
    print("[DB] База данных инициализирована")


def upsert_product(greenspark_id: int, name: str, url: str,
                   category: str = None, brand: str = None,
                   part_type: str = None) -> int:
    """Добавить или обновить товар, вернуть ID"""
    conn = get_connection()
    cursor = conn.cursor()

    # Проверяем существование
    cursor.execute("SELECT id FROM products WHERE greenspark_id = ?", (greenspark_id,))
    row = cursor.fetchone()

    if row:
        # Обновляем
        cursor.execute("""
            UPDATE products
            SET name = ?, url = ?, category = ?, brand = ?, part_type = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE greenspark_id = ?
        """, (name, url, category, brand, part_type, greenspark_id))
        product_id = row['id']
    else:
        # Создаём
        cursor.execute("""
            INSERT INTO products (greenspark_id, name, url, category, brand, part_type)
            VALUES (?, ?, ?, ?, ?, ?)
        """, (greenspark_id, name, url, category, brand, part_type))
        product_id = cursor.lastrowid

    conn.commit()
    conn.close()
    return product_id


def add_price(product_id: int, price_retail: float, price_green5: float,
              quantity: str) -> Tuple[bool, Optional[Dict]]:
    """
    Добавить запись о цене.
    Возвращает (is_changed, change_info) - были ли изменения цены
    """
    conn = get_connection()
    cursor = conn.cursor()

    # Получаем последнюю цену
    cursor.execute("""
        SELECT price_retail, price_green5 FROM prices
        WHERE product_id = ?
        ORDER BY parsed_at DESC LIMIT 1
    """, (product_id,))
    last_price = cursor.fetchone()

    # Добавляем новую запись
    cursor.execute("""
        INSERT INTO prices (product_id, price_retail, price_green5, quantity)
        VALUES (?, ?, ?, ?)
    """, (product_id, price_retail, price_green5, quantity))

    change_info = None
    is_changed = False

    # Проверяем изменения
    if last_price:
        old_retail = last_price['price_retail']
        old_green5 = last_price['price_green5']

        if old_retail and price_retail and old_retail != price_retail:
            change_pct = ((price_retail - old_retail) / old_retail) * 100
            cursor.execute("""
                INSERT INTO price_changes (product_id, price_type, old_price, new_price, change_percent)
                VALUES (?, 'retail', ?, ?, ?)
            """, (product_id, old_retail, price_retail, change_pct))
            is_changed = True
            change_info = {
                'type': 'retail',
                'old': old_retail,
                'new': price_retail,
                'change_pct': change_pct
            }

        if old_green5 and price_green5 and old_green5 != price_green5:
            change_pct = ((price_green5 - old_green5) / old_green5) * 100
            cursor.execute("""
                INSERT INTO price_changes (product_id, price_type, old_price, new_price, change_percent)
                VALUES (?, 'green5', ?, ?, ?)
            """, (product_id, old_green5, price_green5, change_pct))
            is_changed = True
            if not change_info:
                change_info = {
                    'type': 'green5',
                    'old': old_green5,
                    'new': price_green5,
                    'change_pct': change_pct
                }

    conn.commit()
    conn.close()
    return is_changed, change_info


def add_availability(product_id: int, stores: List[Dict]):
    """Добавить информацию о наличии в магазинах"""
    conn = get_connection()
    cursor = conn.cursor()

    for store in stores:
        cursor.execute("""
            INSERT INTO availability (product_id, store_id, store_name, store_address, quantity)
            VALUES (?, ?, ?, ?, ?)
        """, (
            product_id,
            store.get('id'),
            store.get('name'),
            store.get('address'),
            store.get('quantity')
        ))

    conn.commit()
    conn.close()


def get_product_by_id(greenspark_id: int) -> Optional[Dict]:
    """Получить товар по GreenSpark ID"""
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        SELECT p.*,
               pr.price_retail, pr.price_green5, pr.quantity, pr.parsed_at as last_parsed
        FROM products p
        LEFT JOIN prices pr ON pr.product_id = p.id
        WHERE p.greenspark_id = ?
        ORDER BY pr.parsed_at DESC
        LIMIT 1
    """, (greenspark_id,))

    row = cursor.fetchone()
    conn.close()

    return dict(row) if row else None


def get_price_history(greenspark_id: int, limit: int = 30) -> List[Dict]:
    """Получить историю цен товара"""
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        SELECT pr.* FROM prices pr
        JOIN products p ON p.id = pr.product_id
        WHERE p.greenspark_id = ?
        ORDER BY pr.parsed_at DESC
        LIMIT ?
    """, (greenspark_id, limit))

    rows = cursor.fetchall()
    conn.close()

    return [dict(row) for row in rows]


def get_price_changes(days: int = 7, min_change_pct: float = 0) -> List[Dict]:
    """Получить изменения цен за последние N дней"""
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        SELECT pc.*, p.name, p.greenspark_id, p.brand, p.part_type
        FROM price_changes pc
        JOIN products p ON p.id = pc.product_id
        WHERE pc.detected_at >= datetime('now', '-' || ? || ' days')
          AND ABS(pc.change_percent) >= ?
        ORDER BY pc.detected_at DESC
    """, (days, min_change_pct))

    rows = cursor.fetchall()
    conn.close()

    return [dict(row) for row in rows]


def get_stats() -> Dict:
    """Получить статистику БД"""
    conn = get_connection()
    cursor = conn.cursor()

    stats = {}

    cursor.execute("SELECT COUNT(*) as cnt FROM products")
    stats['total_products'] = cursor.fetchone()['cnt']

    cursor.execute("SELECT COUNT(*) as cnt FROM prices")
    stats['total_price_records'] = cursor.fetchone()['cnt']

    cursor.execute("SELECT COUNT(*) as cnt FROM price_changes")
    stats['total_price_changes'] = cursor.fetchone()['cnt']

    cursor.execute("""
        SELECT COUNT(*) as cnt FROM price_changes
        WHERE detected_at >= datetime('now', '-1 day')
    """)
    stats['price_changes_24h'] = cursor.fetchone()['cnt']

    cursor.execute("SELECT COUNT(DISTINCT brand) as cnt FROM products WHERE brand IS NOT NULL")
    stats['brands'] = cursor.fetchone()['cnt']

    cursor.execute("SELECT MAX(parsed_at) as last FROM prices")
    row = cursor.fetchone()
    stats['last_parse'] = row['last'] if row else None

    conn.close()
    return stats


def search_products(query: str, limit: int = 50) -> List[Dict]:
    """Поиск товаров по названию"""
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        SELECT p.*,
               pr.price_retail, pr.price_green5, pr.quantity, pr.parsed_at as last_parsed
        FROM products p
        LEFT JOIN (
            SELECT product_id, price_retail, price_green5, quantity, parsed_at,
                   ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY parsed_at DESC) as rn
            FROM prices
        ) pr ON pr.product_id = p.id AND pr.rn = 1
        WHERE p.name LIKE ?
        ORDER BY p.name
        LIMIT ?
    """, (f'%{query}%', limit))

    rows = cursor.fetchall()
    conn.close()

    return [dict(row) for row in rows]


def log_parse_session(query: str, products_found: int, products_new: int,
                      products_updated: int, price_changes: int,
                      started_at: datetime, status: str = 'completed'):
    """Записать лог сессии парсинга"""
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        INSERT INTO parse_logs (query, products_found, products_new, products_updated,
                               price_changes, started_at, finished_at, status)
        VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
    """, (query, products_found, products_new, products_updated,
          price_changes, started_at, status))

    conn.commit()
    conn.close()


if __name__ == "__main__":
    init_database()
    print("[OK] База данных создана")
