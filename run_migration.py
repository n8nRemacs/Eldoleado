#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Database Migration Runner
Выполняет SQL миграции в Supabase через REST API
"""

import requests
import json
import sys
import io
from pathlib import Path
from datetime import datetime

# Fix Windows encoding
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Конфигурация (из supabase_manager.py)
SUPABASE_URL = "https://supabase.n8nsrv.ru"
SERVICE_ROLE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0"

# PostgreSQL REST API endpoint
POSTGRES_API_URL = f"{SUPABASE_URL}/rest/v1/rpc"

HEADERS = {
    "apikey": SERVICE_ROLE_KEY,
    "Authorization": f"Bearer {SERVICE_ROLE_KEY}",
    "Content-Type": "application/json",
    "Prefer": "return=representation"
}

print("=" * 60)
print("🚀 Database Migration Runner")
print("=" * 60)
print(f"🔗 Database: {SUPABASE_URL}")
print(f"📅 Started: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
print("=" * 60)
print()

def execute_sql(sql_content):
    """
    Выполняет SQL через psycopg2 с прямым подключением (не через pooler)
    """
    try:
        import psycopg2
    except ImportError:
        print("❌ Модуль psycopg2 не установлен!")
        print("📦 Установка: pip install psycopg2-binary")
        return False

    # PostgreSQL connection string для Supabase (прямое подключение, не pooler)
    # Формат: postgresql://postgres:[password]@[host]:5432/postgres
    # Используем прямой порт 5432 вместо pooler 6543
    conn_string = "postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres"

    conn = None
    try:
        print("🔌 Подключение к базе данных (прямое соединение с SSL)...")
        conn = psycopg2.connect(conn_string, sslmode='require')
        conn.autocommit = False  # Транзакция для безопасности

        cursor = conn.cursor()

        print("⚙️  Выполнение миграции...")
        print()

        # Выполняем SQL
        cursor.execute(sql_content)

        # Получаем NOTICE сообщения (если есть)
        if conn.notices:
            for notice in conn.notices:
                # Фильтруем только наши RAISE NOTICE
                if 'NOTICE' in notice or '✅' in notice or '⚠️' in notice:
                    print(notice.strip())

        # Commit транзакции
        conn.commit()

        print()
        print("✅ Миграция выполнена успешно!")

        cursor.close()
        conn.close()

        return True

    except psycopg2.Error as e:
        print(f"\n❌ Ошибка выполнения SQL:")
        print(f"   {e}")
        if conn:
            conn.rollback()
            conn.close()
        return False
    except Exception as e:
        print(f"\n❌ Неожиданная ошибка:")
        print(f"   {e}")
        if conn:
            conn.rollback()
            conn.close()
        return False

def run_migration(migration_file):
    """Запускает миграцию из файла"""
    migration_path = Path(migration_file)

    if not migration_path.exists():
        print(f"❌ Файл не найден: {migration_file}")
        return False

    print(f"📄 Файл миграции: {migration_path.name}")
    print()

    # Читаем SQL
    with open(migration_path, 'r', encoding='utf-8') as f:
        sql_content = f.read()

    print(f"📊 Размер SQL: {len(sql_content)} символов")
    print()

    # Выполняем
    success = execute_sql(sql_content)

    return success

if __name__ == "__main__":
    # Путь к миграции
    migration_file = "database/migrations/001_multi_device_architecture.sql"

    success = run_migration(migration_file)

    print()
    print("=" * 60)
    if success:
        print("✅ ГОТОВО! Миграция успешно применена")
    else:
        print("❌ ОШИБКА! Миграция не выполнена")
    print("=" * 60)

    sys.exit(0 if success else 1)
