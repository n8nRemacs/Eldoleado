"""
Парсер товаров GreenSpark с сохранением в БД
"""

import sys
from datetime import datetime
from typing import List, Dict, Optional, Tuple
from collections import defaultdict

from config import SEARCH_QUERIES, DEFAULT_SHOP_ID, PRICE_CHANGE_THRESHOLD
from api_client import (
    GreenSparkAPI,
    RateLimitError,
    extract_product_info,
    detect_brand,
    detect_part_type,
)
from database import (
    init_database,
    upsert_product,
    add_price,
    add_availability,
    log_parse_session,
    get_stats,
)


class GreenSparkParser:
    """Парсер товаров с сохранением в БД"""

    def __init__(self, shop_id: int = DEFAULT_SHOP_ID, verbose: bool = True):
        self.shop_id = shop_id
        self.verbose = verbose
        self.stats = {
            "products_found": 0,
            "products_new": 0,
            "products_updated": 0,
            "price_changes": 0,
            "errors": 0,
        }

    def log(self, message: str):
        """Вывести сообщение если verbose=True"""
        if self.verbose:
            print(message)

    def parse_query(
        self,
        api: GreenSparkAPI,
        query: str,
        only_available: bool = False,
        max_pages: int = 100,
    ) -> Tuple[int, int, int]:
        """
        Парсить товары по одному запросу

        Returns:
            (products_found, price_changes, errors)
        """
        products_found = 0
        price_changes = 0
        errors = 0
        seen_ids = set()

        self.log(f"\n[SEARCH] '{query}'")

        try:
            for product in api.search_all_pages(query, only_available, max_pages):
                try:
                    info = extract_product_info(product)
                    gs_id = info["greenspark_id"]

                    # Пропускаем дубликаты в рамках одного запроса
                    if gs_id in seen_ids:
                        continue
                    seen_ids.add(gs_id)

                    # Определяем бренд и тип
                    brand = detect_brand(info["name"])
                    part_type = detect_part_type(info["name"])

                    # Сохраняем товар
                    product_id = upsert_product(
                        greenspark_id=gs_id,
                        name=info["name"],
                        url=info["url"],
                        category=info.get("category"),
                        brand=brand,
                        part_type=part_type,
                    )

                    # Сохраняем цену
                    is_changed, change_info = add_price(
                        product_id=product_id,
                        price_retail=info["price_retail"],
                        price_green5=info["price_green5"],
                        quantity=info["quantity"],
                    )

                    if is_changed:
                        price_changes += 1
                        if change_info:
                            pct = abs(change_info.get("change_pct", 0))
                            if pct >= PRICE_CHANGE_THRESHOLD:
                                self.log(
                                    f"  [PRICE] {info['name'][:40]}: "
                                    f"{change_info['old']} -> {change_info['new']} "
                                    f"({change_info['change_pct']:+.1f}%)"
                                )

                    # Сохраняем наличие по магазинам
                    stores = info.get("available_stores", [])
                    if stores:
                        add_availability(product_id, stores)

                    products_found += 1

                except Exception as e:
                    errors += 1
                    self.log(f"  [ERROR] Ошибка обработки товара: {e}")

        except RateLimitError:
            self.log(f"  [WARN] Rate limit - запрос прерван")
            raise

        self.log(f"  Найдено: {products_found}, изменений цен: {price_changes}")
        return products_found, price_changes, errors

    def parse_all(
        self,
        queries: List[str] = None,
        only_available: bool = False,
        max_pages_per_query: int = 50,
    ) -> Dict:
        """
        Парсить все запросы

        Args:
            queries: Список поисковых запросов (по умолчанию из config)
            only_available: Только товары в наличии
            max_pages_per_query: Максимум страниц на запрос

        Returns:
            Статистика парсинга
        """
        if queries is None:
            queries = SEARCH_QUERIES

        # Инициализируем БД
        init_database()

        started_at = datetime.now()
        total_products = 0
        total_changes = 0
        total_errors = 0
        completed_queries = 0

        self.log("=" * 60)
        self.log(f">>> Парсинг GreenSpark ({len(queries)} запросов)")
        self.log(f">>> Магазин: {self.shop_id}")
        self.log("=" * 60)

        with GreenSparkAPI(shop_id=self.shop_id) as api:
            for i, query in enumerate(queries, 1):
                try:
                    found, changes, errors = self.parse_query(
                        api, query, only_available, max_pages_per_query
                    )
                    total_products += found
                    total_changes += changes
                    total_errors += errors
                    completed_queries += 1

                    # Логируем прогресс
                    if i % 10 == 0:
                        self.log(f"\n[PROGRESS] {i}/{len(queries)} запросов")

                except RateLimitError:
                    self.log(f"\n[STOP] Rate limit на запросе {i}/{len(queries)}")
                    break
                except KeyboardInterrupt:
                    self.log(f"\n[STOP] Прервано пользователем")
                    break

        # Логируем сессию
        log_parse_session(
            query=f"{completed_queries}/{len(queries)} queries",
            products_found=total_products,
            products_new=0,  # TODO: подсчитать новые
            products_updated=total_products,
            price_changes=total_changes,
            started_at=started_at,
            status="completed" if completed_queries == len(queries) else "partial",
        )

        # Итоговая статистика
        elapsed = (datetime.now() - started_at).total_seconds()
        stats = {
            "queries_total": len(queries),
            "queries_completed": completed_queries,
            "products_found": total_products,
            "price_changes": total_changes,
            "errors": total_errors,
            "elapsed_seconds": elapsed,
            "api_stats": api.get_stats(),
        }

        self.log("\n" + "=" * 60)
        self.log("ИТОГО:")
        self.log(f"  Запросов: {completed_queries}/{len(queries)}")
        self.log(f"  Товаров: {total_products}")
        self.log(f"  Изменений цен: {total_changes}")
        self.log(f"  Ошибок: {total_errors}")
        self.log(f"  Время: {elapsed:.1f} сек")
        self.log("=" * 60)

        return stats


def quick_parse(queries: List[str] = None, shop_id: int = DEFAULT_SHOP_ID) -> Dict:
    """
    Быстрый парсинг с настройками по умолчанию

    Args:
        queries: Список запросов (или None для всех)
        shop_id: ID магазина
    """
    parser = GreenSparkParser(shop_id=shop_id)
    return parser.parse_all(queries=queries, max_pages_per_query=20)


def parse_brand(brand: str, shop_id: int = DEFAULT_SHOP_ID) -> Dict:
    """
    Парсинг товаров одного бренда

    Args:
        brand: Название бренда (iphone, samsung, etc.)
    """
    from config import PART_TYPES

    queries = [f"{part} {brand}" for part in PART_TYPES]
    parser = GreenSparkParser(shop_id=shop_id)
    return parser.parse_all(queries=queries)


# CLI
if __name__ == "__main__":
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")

    import argparse

    argparser = argparse.ArgumentParser(description="Парсер GreenSpark")
    argparser.add_argument(
        "--brand", "-b", help="Парсить только один бренд (iphone, samsung, etc.)"
    )
    argparser.add_argument(
        "--query", "-q", help="Парсить один поисковый запрос"
    )
    argparser.add_argument(
        "--shop", "-s", type=int, default=DEFAULT_SHOP_ID, help="ID магазина"
    )
    argparser.add_argument(
        "--available", "-a", action="store_true", help="Только товары в наличии"
    )
    argparser.add_argument(
        "--pages", "-p", type=int, default=50, help="Максимум страниц на запрос"
    )
    argparser.add_argument(
        "--quiet", action="store_true", help="Минимальный вывод"
    )

    args = argparser.parse_args()

    parser = GreenSparkParser(shop_id=args.shop, verbose=not args.quiet)

    if args.query:
        # Один запрос
        queries = [args.query]
    elif args.brand:
        # Один бренд
        from config import PART_TYPES
        queries = [f"{part} {args.brand}" for part in PART_TYPES]
    else:
        # Все запросы
        queries = SEARCH_QUERIES

    try:
        stats = parser.parse_all(
            queries=queries,
            only_available=args.available,
            max_pages_per_query=args.pages,
        )
    except KeyboardInterrupt:
        print("\n[Прервано]")
