"""
Главный модуль парсера GreenSpark
CLI интерфейс для управления парсером
"""

import sys
import argparse
from datetime import datetime, timedelta

sys.stdout.reconfigure(encoding="utf-8", errors="replace")

from config import DEFAULT_SHOP_ID, SEARCH_QUERIES, BRANDS
from database import (
    init_database,
    get_stats,
    search_products,
    get_product_by_id,
    get_price_history,
    get_price_changes,
)
from parser import GreenSparkParser, parse_brand


def cmd_init(args):
    """Инициализация базы данных"""
    init_database()
    print("[OK] База данных инициализирована")


def cmd_parse(args):
    """Парсинг товаров"""
    queries = None

    if args.query:
        queries = [args.query]
        print(f"Парсинг по запросу: {args.query}")
    elif args.brand:
        from config import PART_TYPES
        queries = [f"{part} {args.brand}" for part in PART_TYPES]
        print(f"Парсинг бренда: {args.brand} ({len(queries)} запросов)")
    else:
        queries = SEARCH_QUERIES
        print(f"Полный парсинг ({len(queries)} запросов)")

    parser = GreenSparkParser(shop_id=args.shop, verbose=not args.quiet)

    try:
        stats = parser.parse_all(
            queries=queries,
            only_available=args.available,
            max_pages_per_query=args.pages,
        )
        return stats
    except KeyboardInterrupt:
        print("\n[Прервано]")
        return None


def cmd_search(args):
    """Поиск товаров в локальной БД"""
    init_database()
    results = search_products(args.query, limit=args.limit)

    if not results:
        print(f"Товары по запросу '{args.query}' не найдены")
        return

    print(f"\nНайдено: {len(results)} товаров\n")
    print("-" * 80)

    for p in results:
        print(f"[{p['greenspark_id']}] {p['name'][:60]}")
        if p.get("price_retail"):
            print(f"    Розница: {p['price_retail']} руб | Грин 5: {p.get('price_green5', '-')} руб")
        if p.get("brand"):
            print(f"    Бренд: {p['brand']} | Тип: {p.get('part_type', '-')}")
        print()


def cmd_product(args):
    """Информация о товаре"""
    init_database()
    product = get_product_by_id(args.id)

    if not product:
        print(f"Товар с ID {args.id} не найден")
        return

    print("\n" + "=" * 60)
    print(f"Товар: {product['name']}")
    print("=" * 60)
    print(f"GreenSpark ID: {product['greenspark_id']}")
    print(f"URL: https://green-spark.ru{product.get('url', '')}")
    print(f"Бренд: {product.get('brand', '-')}")
    print(f"Тип: {product.get('part_type', '-')}")
    print(f"Категория: {product.get('category', '-')}")
    print()

    if product.get("price_retail"):
        print(f"Текущая цена (Розница): {product['price_retail']} руб")
    if product.get("price_green5"):
        print(f"Текущая цена (Грин 5):  {product['price_green5']} руб")
    if product.get("quantity"):
        print(f"Наличие: {product['quantity']}")
    if product.get("last_parsed"):
        print(f"Обновлено: {product['last_parsed']}")

    # История цен
    if args.history:
        history = get_price_history(args.id, limit=args.history)
        if history:
            print(f"\nИстория цен (последние {len(history)} записей):")
            print("-" * 40)
            for h in history:
                print(f"  {h['parsed_at']}: {h['price_retail']} / {h['price_green5']} руб ({h['quantity']})")


def cmd_changes(args):
    """Показать изменения цен"""
    init_database()
    changes = get_price_changes(days=args.days, min_change_pct=args.min_change)

    if not changes:
        print(f"Изменений цен за {args.days} дней не найдено")
        return

    print(f"\nИзменения цен за {args.days} дней (>= {args.min_change}%):\n")
    print("-" * 80)

    for c in changes:
        direction = "+" if c["change_percent"] > 0 else ""
        print(f"[{c['greenspark_id']}] {c['name'][:50]}")
        print(f"    {c['price_type'].upper()}: {c['old_price']} -> {c['new_price']} ({direction}{c['change_percent']:.1f}%)")
        print(f"    {c['detected_at']}")
        print()


def cmd_stats(args):
    """Статистика базы данных"""
    init_database()
    stats = get_stats()

    print("\n" + "=" * 40)
    print("СТАТИСТИКА БАЗЫ ДАННЫХ")
    print("=" * 40)
    print(f"Всего товаров:        {stats['total_products']}")
    print(f"Записей цен:          {stats['total_price_records']}")
    print(f"Изменений цен:        {stats['total_price_changes']}")
    print(f"Изменений за 24ч:     {stats['price_changes_24h']}")
    print(f"Брендов:              {stats['brands']}")
    print(f"Последний парсинг:    {stats['last_parse'] or 'никогда'}")
    print("=" * 40)


def cmd_export(args):
    """Экспорт данных"""
    import json
    import csv
    from pathlib import Path

    init_database()

    # Получаем товары
    if args.query:
        products = search_products(args.query, limit=10000)
    else:
        products = search_products("", limit=10000)

    if not products:
        print("Нет данных для экспорта")
        return

    output_file = Path(args.output)

    if args.format == "json":
        with open(output_file, "w", encoding="utf-8") as f:
            json.dump(products, f, ensure_ascii=False, indent=2, default=str)
    elif args.format == "csv":
        with open(output_file, "w", encoding="utf-8", newline="") as f:
            writer = csv.DictWriter(f, fieldnames=products[0].keys())
            writer.writeheader()
            writer.writerows(products)

    print(f"[OK] Экспортировано {len(products)} товаров в {output_file}")


def main():
    parser = argparse.ArgumentParser(
        description="Парсер цен GreenSpark.ru",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Примеры:
  python main.py init              # Создать БД
  python main.py parse             # Полный парсинг
  python main.py parse -b iphone   # Парсинг только iPhone
  python main.py parse -q "акб samsung"  # Один запрос
  python main.py search "iphone 14"      # Поиск в локальной БД
  python main.py product 247970          # Информация о товаре
  python main.py changes -d 7            # Изменения цен за 7 дней
  python main.py stats                   # Статистика
  python main.py export -o data.json     # Экспорт в JSON
        """,
    )

    subparsers = parser.add_subparsers(dest="command", help="Команда")

    # init
    p_init = subparsers.add_parser("init", help="Инициализация базы данных")

    # parse
    p_parse = subparsers.add_parser("parse", help="Парсинг товаров")
    p_parse.add_argument("-b", "--brand", help="Только один бренд")
    p_parse.add_argument("-q", "--query", help="Один поисковый запрос")
    p_parse.add_argument("-s", "--shop", type=int, default=DEFAULT_SHOP_ID, help="ID магазина")
    p_parse.add_argument("-a", "--available", action="store_true", help="Только в наличии")
    p_parse.add_argument("-p", "--pages", type=int, default=50, help="Макс. страниц на запрос")
    p_parse.add_argument("--quiet", action="store_true", help="Минимальный вывод")

    # search
    p_search = subparsers.add_parser("search", help="Поиск в локальной БД")
    p_search.add_argument("query", help="Поисковый запрос")
    p_search.add_argument("-l", "--limit", type=int, default=20, help="Лимит результатов")

    # product
    p_product = subparsers.add_parser("product", help="Информация о товаре")
    p_product.add_argument("id", type=int, help="GreenSpark ID товара")
    p_product.add_argument("--history", "-H", type=int, metavar="N", help="Показать N записей истории цен")

    # changes
    p_changes = subparsers.add_parser("changes", help="Изменения цен")
    p_changes.add_argument("-d", "--days", type=int, default=7, help="За сколько дней")
    p_changes.add_argument("-m", "--min-change", type=float, default=0, help="Minimum change percent")

    # stats
    p_stats = subparsers.add_parser("stats", help="Статистика БД")

    # export
    p_export = subparsers.add_parser("export", help="Экспорт данных")
    p_export.add_argument("-o", "--output", default="data/export.json", help="Файл для экспорта")
    p_export.add_argument("-f", "--format", choices=["json", "csv"], default="json", help="Формат")
    p_export.add_argument("-q", "--query", help="Фильтр по названию")

    args = parser.parse_args()

    if args.command is None:
        parser.print_help()
        return

    commands = {
        "init": cmd_init,
        "parse": cmd_parse,
        "search": cmd_search,
        "product": cmd_product,
        "changes": cmd_changes,
        "stats": cmd_stats,
        "export": cmd_export,
    }

    if args.command in commands:
        commands[args.command](args)
    else:
        parser.print_help()


if __name__ == "__main__":
    main()
