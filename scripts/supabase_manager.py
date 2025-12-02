#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Supabase Database Schema Manager
Инструмент для экспорта схемы БД в Git для version control
"""

import requests
import json
import sys
import io
from pathlib import Path
from datetime import datetime

# Fix Windows encoding
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Конфигурация
SUPABASE_URL = "https://supabase.n8nsrv.ru"
SERVICE_ROLE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0"

HEADERS = {
    "apikey": SERVICE_ROLE_KEY,
    "Authorization": f"Bearer {SERVICE_ROLE_KEY}",
    "Content-Type": "application/json"
}

print(f"🗄️  Supabase Schema Manager")
print(f"🔗 Database: {SUPABASE_URL}\n")

def get_tables():
    """Получить список всех таблиц"""
    # Используем Supabase REST API для получения реальных таблиц
    print("📋 Сканирую базу данных...\n")

    # Таблицы BatteryCRM из Database_Structure_BatteryCRM_COMPLETE.md
    known_tables = [
        # Multi-tenant
        "tenants", "tenant_configs",
        # Clients
        "clients", "client_merges",
        # Appeals and messages
        "appeals", "messages_history",
        # Operators
        "operators", "operator_actions", "operator_appeal_status",
        # Channels and marketing
        "channels", "sales_channels", "lead_sources",
        # Device and service references
        "device_types", "brands", "models", "repair_types",
        "issue_types", "issues", "deal_types", "services",
        "masters", "appointments", "knowledge_base",
        "appeal_meta_config", "appeal_meta_visibility",
        # Android devices
        "operator_devices", "fcm_tokens"
    ]

    # Проверяем какие таблицы существуют
    existing_tables = []
    for table in known_tables:
        try:
            response = requests.get(
                f"{SUPABASE_URL}/rest/v1/{table}",
                headers=HEADERS,
                params={"limit": 0, "select": "count"},
                timeout=5
            )
            if response.status_code in [200, 206]:
                existing_tables.append(table)
        except:
            pass

    return existing_tables if existing_tables else known_tables

def get_table_schema(table_name):
    """Получить схему конкретной таблицы"""
    query = f"""
    SELECT
        column_name,
        data_type,
        character_maximum_length,
        column_default,
        is_nullable,
        ordinal_position
    FROM information_schema.columns
    WHERE table_schema = 'public'
      AND table_name = '{table_name}'
    ORDER BY ordinal_position;
    """

    try:
        # Пробуем получить через REST API
        response = requests.get(
            f"{SUPABASE_URL}/rest/v1/{table_name}",
            headers=HEADERS,
            params={"limit": 1}
        )

        if response.status_code == 200:
            # Получаем информацию о колонках из первой записи
            data = response.json()
            if data:
                columns = list(data[0].keys())
                return {
                    "table_name": table_name,
                    "columns": columns,
                    "row_count": len(data)
                }
            else:
                return {
                    "table_name": table_name,
                    "columns": [],
                    "row_count": 0
                }
        else:
            return {
                "table_name": table_name,
                "error": f"HTTP {response.status_code}",
                "accessible": False
            }
    except Exception as e:
        return {
            "table_name": table_name,
            "error": str(e),
            "accessible": False
        }

def export_schema(output_dir="database_schema"):
    """Экспортировать полную схему БД в файлы"""
    output_path = Path(output_dir)
    output_path.mkdir(exist_ok=True)

    print("📥 Экспортирую схему базы данных...\n")

    tables = get_tables()

    schema_info = {
        "export_date": datetime.now().isoformat(),
        "database_url": SUPABASE_URL,
        "total_tables": len(tables),
        "tables": {}
    }

    for i, table_name in enumerate(tables, 1):
        print(f"[{i}/{len(tables)}] Обрабатываю таблицу: {table_name}")

        table_schema = get_table_schema(table_name)
        schema_info["tables"][table_name] = table_schema

        # Сохраняем схему каждой таблицы в отдельный файл
        table_file = output_path / f"{table_name}.json"
        with open(table_file, 'w', encoding='utf-8') as f:
            json.dump(table_schema, f, indent=2, ensure_ascii=False)

    # Сохраняем общий файл со всей схемой
    schema_file = output_path / "schema_complete.json"
    with open(schema_file, 'w', encoding='utf-8') as f:
        json.dump(schema_info, f, indent=2, ensure_ascii=False)

    print(f"\n✅ Схема экспортирована в {output_dir}/")
    print(f"📄 Всего таблиц: {len(tables)}")
    print(f"📁 Главный файл: schema_complete.json")

    return schema_info

def list_tables():
    """Показать список всех таблиц"""
    tables = get_tables()

    print(f"📋 Всего таблиц: {len(tables)}\n")

    for i, table_name in enumerate(tables, 1):
        # Получаем базовую информацию о таблице
        info = get_table_schema(table_name)

        if "error" in info:
            status = "❌"
            details = f"(недоступна: {info['error']})"
        else:
            status = "✅"
            col_count = len(info.get("columns", []))
            details = f"({col_count} колонок)"

        print(f"{status} {i}. {table_name} {details}")

def compare_schemas(old_schema_file, new_schema_file=None):
    """Сравнить две версии схемы (для отслеживания изменений)"""
    # TODO: Implement schema comparison
    print("🔍 Функция сравнения схем - в разработке")
    print("Используй Git diff для сравнения файлов схем")

def main():
    """Главная функция"""
    if len(sys.argv) < 2:
        print("Использование:")
        print("  python supabase_manager.py list              - список таблиц")
        print("  python supabase_manager.py export            - экспорт схемы")
        print("  python supabase_manager.py export [папка]    - экспорт в указанную папку")
        print()
        return

    command = sys.argv[1].lower()

    if command == "list":
        list_tables()

    elif command == "export":
        output_dir = sys.argv[2] if len(sys.argv) > 2 else "database_schema"
        export_schema(output_dir)

    elif command == "compare":
        if len(sys.argv) < 3:
            print("❌ Укажи файл старой схемы для сравнения")
            return
        compare_schemas(sys.argv[2], sys.argv[3] if len(sys.argv) > 3 else None)

    else:
        print(f"❌ Неизвестная команда: {command}")

if __name__ == "__main__":
    main()
