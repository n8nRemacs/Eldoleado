"""
API клиент для работы с GreenSpark
"""

import httpx
import time
import sys
from typing import Optional, List, Dict, Generator
from config import BASE_URL, SEARCH_ENDPOINT, REQUEST_DELAY, MAX_PAGES


class GreenSparkAPI:
    """Клиент для API green-spark.ru"""

    def __init__(self, shop_id: Optional[int] = None, delay: float = REQUEST_DELAY):
        # Cookies для выбора магазина
        cookies = {}
        if shop_id:
            cookies = {
                "magazine": str(shop_id),
                "global_magazine": str(shop_id),
            }

        self.client = httpx.Client(
            timeout=30.0,
            headers={
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
                "Accept": "application/json, text/plain, */*",
                "Accept-Language": "ru-RU,ru;q=0.9",
            },
            cookies=cookies,
            follow_redirects=True,
        )
        self.last_request = 0
        self.delay = delay
        self.shop_id = shop_id
        self.request_count = 0
        self.error_count = 0

    def _rate_limit(self):
        """Ограничение частоты запросов"""
        elapsed = time.time() - self.last_request
        if elapsed < self.delay:
            time.sleep(self.delay - elapsed)
        self.last_request = time.time()

    def set_shop(self, shop_id: int) -> bool:
        """Установить текущий магазин через cookies"""
        self.shop_id = shop_id
        self.client.cookies.set("magazine", str(shop_id))
        self.client.cookies.set("global_magazine", str(shop_id))
        return True

    def search(self, query: str, page: int = 1, only_available: bool = False) -> dict:
        """
        Поиск товаров

        Args:
            query: Поисковый запрос
            page: Номер страницы (начиная с 1)
            only_available: Только товары в наличии

        Returns:
            dict с ключами 'data' (список товаров) и 'meta' (пагинация)
        """
        self._rate_limit()
        self.request_count += 1

        params = {
            "q": query,
            "onlyAvailable": str(only_available).lower(),
            "page": page,
        }

        try:
            response = self.client.get(f"{BASE_URL}{SEARCH_ENDPOINT}", params=params)
            response.raise_for_status()
            return response.json()
        except httpx.HTTPStatusError as e:
            self.error_count += 1
            if e.response.status_code == 403:
                print(f"[ERROR] Доступ запрещён (403). Возможно IP заблокирован.")
                raise RateLimitError("API вернул 403 - возможно временная блокировка")
            raise
        except Exception as e:
            self.error_count += 1
            raise

    def search_all_pages(
        self, query: str, only_available: bool = False, max_pages: int = MAX_PAGES
    ) -> Generator[Dict, None, None]:
        """
        Генератор для получения всех товаров по запросу

        Yields:
            Товары по одному
        """
        page = 1

        while page <= max_pages:
            try:
                data = self.search(query, page=page, only_available=only_available)
                products = data.get("data", [])

                if not products:
                    break

                for product in products:
                    yield product

                meta = data.get("meta", {})
                total_pages = meta.get("pageCount", 1)

                if page >= total_pages:
                    break

                page += 1

            except RateLimitError:
                raise
            except Exception as e:
                print(f"[ERROR] Ошибка на странице {page}: {e}")
                break

    def get_all_products(
        self, query: str, only_available: bool = False, max_pages: int = MAX_PAGES
    ) -> List[Dict]:
        """
        Получить список всех товаров по запросу

        Returns:
            Список всех найденных товаров
        """
        return list(self.search_all_pages(query, only_available, max_pages))

    def get_stats(self) -> Dict:
        """Получить статистику запросов"""
        return {
            "request_count": self.request_count,
            "error_count": self.error_count,
            "shop_id": self.shop_id,
        }

    def close(self):
        """Закрыть соединение"""
        self.client.close()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.close()


class RateLimitError(Exception):
    """Ошибка превышения лимита запросов"""
    pass


def extract_product_info(product: dict) -> dict:
    """
    Извлечь информацию о товаре из ответа API

    Args:
        product: Словарь с данными товара из API

    Returns:
        Нормализованный словарь с информацией о товаре
    """
    prices = product.get("prices", [])
    price_retail = None
    price_green5 = None

    for p in prices:
        name = p.get("name", "").lower()
        price = p.get("price")
        if "розница" in name:
            price_retail = price
        elif "грин" in name or "green" in name:
            price_green5 = price

    return {
        "greenspark_id": product.get("id"),
        "name": product.get("name", ""),
        "url": product.get("url", ""),
        "quantity": product.get("quantity", "none"),
        "price_retail": price_retail,
        "price_green5": price_green5,
        "available_stores": product.get("availableStores", []),
        "is_new": product.get("isNew", False),
        "is_hit": product.get("isHit", False),
        "picture": product.get("picture", {}).get("original"),
        "article": product.get("article"),
    }


def detect_brand(name: str) -> Optional[str]:
    """Определить бренд по названию товара"""
    name_lower = name.lower()
    brands = {
        "iphone": "Apple",
        "apple": "Apple",
        "samsung": "Samsung",
        "galaxy": "Samsung",
        "xiaomi": "Xiaomi",
        "redmi": "Xiaomi",
        "poco": "Xiaomi",
        "huawei": "Huawei",
        "honor": "Huawei",
        "realme": "Realme",
        "oneplus": "OnePlus",
        "infinix": "Infinix",
        "tecno": "Tecno",
        "oppo": "OPPO",
        "vivo": "Vivo",
        "nokia": "Nokia",
        "sony": "Sony",
        "lg": "LG",
        "asus": "ASUS",
        "meizu": "Meizu",
        "zte": "ZTE",
        "lenovo": "Lenovo",
        "motorola": "Motorola",
    }

    for keyword, brand in brands.items():
        if keyword in name_lower:
            return brand

    return None


def detect_part_type(name: str) -> Optional[str]:
    """Определить тип запчасти по названию"""
    name_lower = name.lower()
    part_types = {
        "дисплей": "Дисплей",
        "lcd": "Дисплей",
        "экран": "Дисплей",
        "тачскрин": "Тачскрин",
        "сенсор": "Тачскрин",
        "touch": "Тачскрин",
        "аккумулятор": "АКБ",
        "акб": "АКБ",
        "батарея": "АКБ",
        "battery": "АКБ",
        "шлейф": "Шлейф",
        "flex": "Шлейф",
        "корпус": "Корпус",
        "рамка": "Корпус",
        "frame": "Корпус",
        "крышка": "Задняя крышка",
        "back cover": "Задняя крышка",
        "камера": "Камера",
        "camera": "Камера",
        "плата": "Плата",
        "board": "Плата",
        "разъем": "Разъем",
        "connector": "Разъем",
        "динамик": "Динамик",
        "speaker": "Динамик",
        "микрофон": "Микрофон",
        "mic": "Микрофон",
        "вибро": "Вибромотор",
        "кнопка": "Кнопка",
        "button": "Кнопка",
        "стекло": "Стекло",
        "glass": "Стекло",
        "sim": "SIM-лоток",
        "антенна": "Антенна",
    }

    for keyword, part_type in part_types.items():
        if keyword in name_lower:
            return part_type

    return None


# Тестирование
if __name__ == "__main__":
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")

    print("=" * 60)
    print("Тест API клиента GreenSpark")
    print("=" * 60)

    with GreenSparkAPI() as api:
        try:
            # Простой поиск
            result = api.search("акб iphone", page=1)
            meta = result.get("meta", {})
            products = result.get("data", [])

            print(f"\nНайдено: {meta.get('total')} товаров")
            print(f"Страница: {meta.get('pageNumber')}/{meta.get('pageCount')}")

            if products:
                print("\nПервые 3 товара:")
                for p in products[:3]:
                    info = extract_product_info(p)
                    print(f"\n  {info['name'][:60]}")
                    print(f"    Розница: {info['price_retail']} руб")
                    print(f"    Грин 5:  {info['price_green5']} руб")
                    print(f"    Бренд:   {detect_brand(info['name'])}")
                    print(f"    Тип:     {detect_part_type(info['name'])}")

            print(f"\n\nСтатистика: {api.get_stats()}")

        except RateLimitError as e:
            print(f"\n[ERROR] {e}")
            print("Подождите 10-15 минут и попробуйте снова")
        except Exception as e:
            print(f"\n[ERROR] {e}")
