"""
Конфигурация парсера GreenSpark
"""

# API
BASE_URL = "https://green-spark.ru/local/api"
SEARCH_ENDPOINT = "/catalog/fast-search/"

# Rate limiting
REQUEST_DELAY = 2.0  # секунд между запросами (увеличено чтобы избежать блокировки)
MAX_PAGES = 100      # максимум страниц на запрос

# Целевой магазин (Ростов-на-Дону, ул. Серафимовича, 53А)
DEFAULT_SHOP_ID = 15226

# Бренды для парсинга
BRANDS = [
    "iphone",
    "samsung",
    "huawei",
    "xiaomi",
    "realme",
    "oneplus",
    "infinix",
    "tecno",
]

# Типы запчастей
PART_TYPES = [
    "дисплей",
    "тачскрин",
    "акб",
    "шлейф",
    "корпус",
    "части корпуса",
    "задняя крышка",
    "камера",
    "плата",
]

# Генерируем все комбинации бренд + тип запчасти
SEARCH_QUERIES = [f"{part} {brand}" for brand in BRANDS for part in PART_TYPES]

# Уведомления (опционально)
TELEGRAM_BOT_TOKEN = ""
TELEGRAM_CHAT_ID = ""
PRICE_CHANGE_THRESHOLD = 5  # процентов для уведомления

# Пути к данным
DATA_DIR = "data"
DB_FILE = "data/greenspark.db"
