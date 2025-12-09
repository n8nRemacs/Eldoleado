"""
Парсер структуры каталога GreenSpark.ru
Извлекает дерево категорий из URL товаров
"""

import httpx
import time
import json
from collections import defaultdict
from typing import Dict, List, Set
from pathlib import Path

BASE_URL = "https://green-spark.ru/local/api"
SEARCH_ENDPOINT = "/catalog/fast-search/"
REQUEST_DELAY = 1.0

# Магазин Ростов-на-Дону, ул. Серафимовича, 53А
SHOP_ID = 15226

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


class CatalogParser:
    def __init__(self, shop_id: int = SHOP_ID):
        self.client = httpx.Client(timeout=30.0, headers={
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        })
        self.last_request = 0
        self.categories: Dict[str, dict] = {}
        self.url_paths: Set[str] = set()
        self.shop_id = shop_id

        # Устанавливаем магазин
        if shop_id:
            self._set_shop(shop_id)

    def _set_shop(self, shop_id: int):
        """Устанавливает магазин через API"""
        self._rate_limit()
        try:
            response = self.client.get(f"{BASE_URL}/shop/set/{shop_id}/")
            if response.status_code == 200:
                print(f"[OK] Установлен магазин ID: {shop_id}")
            else:
                print(f"[WARN] Не удалось установить магазин: {response.status_code}")
        except Exception as e:
            print(f"[WARN] Ошибка установки магазина: {e}")

    def _rate_limit(self):
        elapsed = time.time() - self.last_request
        if elapsed < REQUEST_DELAY:
            time.sleep(REQUEST_DELAY - elapsed)
        self.last_request = time.time()

    def search(self, query: str, page: int = 1) -> dict:
        self._rate_limit()
        params = {
            "q": query,
            "onlyAvailable": "false",  # Все товары для полноты каталога
            "page": page
        }
        response = self.client.get(f"{BASE_URL}{SEARCH_ENDPOINT}", params=params)
        response.raise_for_status()
        return response.json()

    def extract_category_from_url(self, url: str) -> List[str]:
        """Извлекает путь категорий из URL товара"""
        # /catalog/cat1/cat2/cat3/product.html -> ['cat1', 'cat2', 'cat3']
        if not url.startswith("/catalog/"):
            return []

        parts = url.replace("/catalog/", "").split("/")
        # Последний элемент - это файл товара (.html)
        return parts[:-1] if parts else []

    def build_category_tree(self, paths: List[List[str]]) -> dict:
        """Строит дерево категорий из списка путей"""
        tree = {}

        for path in paths:
            current = tree
            for i, segment in enumerate(path):
                if segment not in current:
                    current[segment] = {
                        "_info": {
                            "slug": segment,
                            "full_path": "/".join(path[:i+1]),
                            "depth": i + 1,
                            "children_count": 0
                        }
                    }
                current = current[segment]

        # Подсчёт дочерних элементов
        def count_children(node):
            if not isinstance(node, dict):
                return 0
            children = [k for k in node.keys() if k != "_info"]
            if "_info" in node:
                node["_info"]["children_count"] = len(children)
            for child in children:
                count_children(node[child])

        count_children(tree)
        return tree

    def parse_all_queries(self, max_pages_per_query: int = 5) -> dict:
        """Парсит все поисковые запросы и собирает URL"""
        all_paths = []
        products_found = 0

        for query in SEARCH_QUERIES:
            print(f"\n[SEARCH] Поиск: '{query}'")
            page = 1

            while page <= max_pages_per_query:
                try:
                    data = self.search(query, page=page)
                    products = data.get("data", [])

                    if not products:
                        break

                    for product in products:
                        url = product.get("url", "")
                        if url and url not in self.url_paths:
                            self.url_paths.add(url)
                            path = self.extract_category_from_url(url)
                            if path:
                                all_paths.append(path)
                                products_found += 1

                    meta = data.get("meta", {})
                    total_pages = meta.get("pageCount", 1)
                    print(f"  Страница {page}/{total_pages}, товаров в выдаче: {len(products)}")

                    if page >= total_pages:
                        break
                    page += 1

                except Exception as e:
                    print(f"  [ERROR] Ошибка: {e}")
                    break

        print(f"\n[STAT] Всего уникальных товаров: {products_found}")
        print(f"[STAT] Уникальных URL: {len(self.url_paths)}")

        return self.build_category_tree(all_paths)

    def get_flat_categories(self, tree: dict, parent_path: str = "") -> List[dict]:
        """Преобразует дерево в плоский список категорий"""
        result = []

        for key, value in tree.items():
            if key == "_info":
                continue

            info = value.get("_info", {})
            full_path = f"{parent_path}/{key}" if parent_path else key

            result.append({
                "slug": key,
                "full_path": full_path,
                "depth": info.get("depth", 1),
                "children_count": info.get("children_count", 0),
                "url": f"https://green-spark.ru/catalog/{full_path}/"
            })

            # Рекурсивно обрабатываем дочерние
            result.extend(self.get_flat_categories(value, full_path))

        return result


def main():
    import sys
    sys.stdout.reconfigure(encoding='utf-8', errors='replace')

    output_dir = Path(__file__).parent / "data"
    output_dir.mkdir(exist_ok=True)

    parser = CatalogParser()

    print("=" * 60)
    print(">>> Парсинг структуры каталога GreenSpark.ru")
    print("=" * 60)

    # Парсим с увеличенным количеством страниц для полноты
    tree = parser.parse_all_queries(max_pages_per_query=10)

    # Сохраняем дерево
    tree_file = output_dir / "catalog_tree.json"
    with open(tree_file, "w", encoding="utf-8") as f:
        json.dump(tree, f, ensure_ascii=False, indent=2)
    print(f"\n[OK] Дерево категорий сохранено: {tree_file}")

    # Сохраняем плоский список
    flat_categories = parser.get_flat_categories(tree)
    flat_file = output_dir / "catalog_flat.json"
    with open(flat_file, "w", encoding="utf-8") as f:
        json.dump(flat_categories, f, ensure_ascii=False, indent=2)
    print(f"[OK] Плоский список категорий: {flat_file}")

    # Сохраняем все URL товаров
    urls_file = output_dir / "product_urls.txt"
    with open(urls_file, "w", encoding="utf-8") as f:
        for url in sorted(parser.url_paths):
            f.write(url + "\n")
    print(f"[OK] URL товаров: {urls_file}")

    # Статистика
    print("\n" + "=" * 60)
    print("СТАТИСТИКА")
    print("=" * 60)

    # Группировка по глубине
    by_depth = defaultdict(list)
    for cat in flat_categories:
        by_depth[cat["depth"]].append(cat)

    for depth in sorted(by_depth.keys()):
        print(f"  Уровень {depth}: {len(by_depth[depth])} категорий")

    print(f"\n  Всего категорий: {len(flat_categories)}")
    print(f"  Всего товаров найдено: {len(parser.url_paths)}")

    # Показываем верхний уровень
    print("\nКатегории верхнего уровня:")
    top_level = [c for c in flat_categories if c["depth"] == 1]
    for cat in top_level:
        print(f"  - {cat['slug']} ({cat['children_count']} подкатегорий)")


if __name__ == "__main__":
    main()
