#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Database Migration via Supabase REST API
Выполняет SQL миграции через Supabase REST API (PostgREST rpc)
"""

import requests
import sys
import io
from pathlib import Path

# Fix Windows encoding
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Конфигурация
SUPABASE_URL = "https://supabase.n8nsrv.ru"
SERVICE_ROLE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0"

print("=" * 60)
print("🚀 Database Migration via Supabase REST API")
print("=" * 60)
print()

# Список файлов миграции по шагам
migration_steps = [
    "database/migrations/001_step1_drop_old_issue_types.sql",
    "database/migrations/001_step2_create_repair_categories.sql",
    "database/migrations/001_step3_create_issue_types.sql",
    "database/migrations/001_step4_create_appeal_devices.sql",
    "database/migrations/001_step5_create_appeal_repairs.sql",
]

def execute_sql_via_dashboard_instructions(step_file):
    """Показывает инструкции для выполнения шага вручную"""
    print(f"📄 Шаг: {Path(step_file).name}")
    print(f"📍 Файл: {step_file}")
    print()
    print("Инструкция:")
    print("1. Открой Supabase Dashboard: https://supabase.n8nsrv.ru")
    print("2. SQL Editor → New Query")
    print(f"3. Скопируй содержимое файла: {step_file}")
    print("4. Вставь в SQL Editor и нажми Run")
    print()

    # Показываем содержимое
    try:
        with open(step_file, 'r', encoding='utf-8') as f:
            content = f.read()
        print("Содержимое SQL:")
        print("-" * 60)
        print(content)
        print("-" * 60)
        print()
    except FileNotFoundError:
        print(f"❌ Файл не найден: {step_file}")
        return False

    return True

def main():
    print("⚠️  ВНИМАНИЕ: Прямое подключение к PostgreSQL заблокировано!")
    print("Выполнение миграции вручную через Supabase Dashboard.")
    print()

    for i, step_file in enumerate(migration_steps, 1):
        print(f"\n{'='*60}")
        print(f"ШАГ {i}/{len(migration_steps)}")
        print(f"{'='*60}\n")

        if not execute_sql_via_dashboard_instructions(step_file):
            print(f"❌ Ошибка на шаге {i}")
            return False

        input("\nНажми Enter когда выполнишь этот шаг в Dashboard...")

    print("\n" + "="*60)
    print("✅ Все шаги показаны!")
    print("="*60)
    print()
    print("Если все шаги выполнены успешно, миграция завершена!")
    return True

if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1)
