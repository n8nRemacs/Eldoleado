"""
Скрипт для поиска ID магазина GreenSpark
"""
import httpx
import json
import sys
from pathlib import Path

sys.stdout.reconfigure(encoding='utf-8', errors='replace')

BASE_URL = "https://green-spark.ru/local/api"

def collect_shops_from_products():
    """Собирает уникальные магазины из ответов API товаров"""
    client = httpx.Client(timeout=30.0, headers={
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    })

    shops = {}
    queries = ["дисплей", "акб", "шлейф", "корпус", "камера"]

    for query in queries:
        print(f"Поиск: {query}")
        for page in range(1, 5):
            try:
                response = client.get(
                    f"{BASE_URL}/catalog/fast-search/",
                    params={"q": query, "onlyAvailable": "true", "page": page}
                )
                data = response.json()

                for product in data.get("data", []):
                    for store in product.get("availableStores", []):
                        sid = store.get("id")
                        if sid and sid not in shops:
                            shops[sid] = {
                                "id": sid,
                                "name": store.get("name", ""),
                                "address": store.get("address", "")
                            }
            except Exception as e:
                print(f"  Ошибка: {e}")
                break

    return shops


def search_rostov(shops):
    """Ищет Ростов-на-Дону среди магазинов"""
    print("\n" + "=" * 60)
    print("Поиск магазина в Ростове-на-Дону:")
    print("=" * 60)

    found = []
    for sid, shop in shops.items():
        name = shop["name"].lower()
        address = shop["address"].lower()
        if "ростов" in name or "ростов" in address or "серафимович" in address:
            found.append(shop)
            print(f"  ID: {sid}")
            print(f"  Название: {shop['name']}")
            print(f"  Адрес: {shop['address']}")
            print()

    if not found:
        print("  Магазин в Ростове НЕ НАЙДЕН в выдаче API")
        print("  API может возвращать только ближайший магазин к текущему IP")

    return found


def main():
    output_dir = Path(__file__).parent / "data"
    output_dir.mkdir(exist_ok=True)

    print("=" * 60)
    print("Сбор списка магазинов GreenSpark")
    print("=" * 60)

    shops = collect_shops_from_products()

    print(f"\nНайдено уникальных магазинов: {len(shops)}")

    # Сохраняем все найденные магазины
    shops_file = output_dir / "shops_found.json"
    with open(shops_file, "w", encoding="utf-8") as f:
        json.dump(list(shops.values()), f, ensure_ascii=False, indent=2)
    print(f"Сохранено: {shops_file}")

    # Выводим все магазины
    print("\n" + "=" * 60)
    print("Все найденные магазины:")
    print("=" * 60)
    for sid, shop in sorted(shops.items()):
        print(f"  {sid}: {shop['name']} - {shop['address']}")

    # Ищем Ростов
    search_rostov(shops)


if __name__ == "__main__":
    main()
