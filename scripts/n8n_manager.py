#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
N8N Workflow Manager
Инструмент для управления воркерами n8n через API
"""

import requests
import json
import sys
import io
from pathlib import Path

# Fix Windows encoding
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Конфигурация
N8N_URL = "https://n8n.n8nsrv.ru"
API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
N8N_VERSION = "1.119.2"  # КРИТИЧНО: Версия n8n на сервере

HEADERS = {
    "X-N8N-API-KEY": API_KEY,
    "Accept": "application/json",
    "Content-Type": "application/json"
}

print(f"⚙️  n8n Manager для версии {N8N_VERSION}")
print(f"🔗 Сервер: {N8N_URL}\n")

def list_workflows():
    """Получить список всех воркеров"""
    response = requests.get(f"{N8N_URL}/api/v1/workflows", headers=HEADERS)
    response.raise_for_status()
    workflows = response.json()["data"]

    print(f"\n📋 Всего воркеров: {len(workflows)}\n")

    # Сортируем по имени
    workflows.sort(key=lambda x: x["name"])

    for i, wf in enumerate(workflows, 1):
        status = "🟢 ACTIVE" if wf["active"] else "⚪ inactive"
        print(f"{i:3}. [{status}] {wf['name'][:60]:60} (ID: {wf['id']})")

    return workflows

def get_workflow(workflow_id):
    """Получить детали воркера"""
    response = requests.get(f"{N8N_URL}/api/v1/workflows/{workflow_id}", headers=HEADERS)
    response.raise_for_status()
    return response.json()

def save_workflow(workflow_id, filename=None):
    """Скачать воркер в JSON файл"""
    workflow = get_workflow(workflow_id)

    if filename is None:
        # Создаем безопасное имя файла
        safe_name = workflow["name"].replace("/", "_").replace("\\", "_")
        filename = f"workflows/{safe_name}_{workflow_id}.json"

    # Создаем папку если нет
    Path(filename).parent.mkdir(parents=True, exist_ok=True)

    with open(filename, 'w', encoding='utf-8') as f:
        json.dump(workflow, f, indent=2, ensure_ascii=False)

    print(f"✅ Воркер сохранен: {filename}")
    print(f"   Имя: {workflow['name']}")
    print(f"   Нод: {len(workflow['nodes'])}")
    print(f"   Активен: {workflow['active']}")

    return filename

def update_workflow(workflow_id, json_file):
    """Обновить воркер из JSON файла"""
    with open(json_file, 'r', encoding='utf-8') as f:
        workflow_data = json.load(f)

    # Убираем поля, которые не нужны при обновлении
    fields_to_remove = ['id', 'createdAt', 'updatedAt', 'versionId', 'triggerCount', 'shared']
    for field in fields_to_remove:
        workflow_data.pop(field, None)

    response = requests.put(
        f"{N8N_URL}/api/v1/workflows/{workflow_id}",
        headers=HEADERS,
        json=workflow_data
    )
    response.raise_for_status()

    print(f"✅ Воркер обновлен: {workflow_data['name']}")
    return response.json()

def activate_workflow(workflow_id, active=True):
    """Активировать/деактивировать воркер"""
    workflow = get_workflow(workflow_id)
    workflow['active'] = active

    # Убираем лишние поля
    fields_to_remove = ['createdAt', 'updatedAt', 'versionId', 'triggerCount', 'shared']
    for field in fields_to_remove:
        workflow.pop(field, None)

    response = requests.put(
        f"{N8N_URL}/api/v1/workflows/{workflow_id}",
        headers=HEADERS,
        json=workflow
    )
    response.raise_for_status()

    status = "активирован" if active else "деактивирован"
    print(f"✅ Воркер {status}: {workflow['name']}")
    return response.json()

def main():
    if len(sys.argv) < 2:
        print("""
🛠️  N8N Workflow Manager

Использование:
  python n8n_manager.py list                          - список всех воркеров
  python n8n_manager.py get <workflow_id>             - скачать воркер
  python n8n_manager.py update <workflow_id> <file>   - загрузить изменения
  python n8n_manager.py activate <workflow_id>        - активировать
  python n8n_manager.py deactivate <workflow_id>      - деактивировать

Примеры:
  python n8n_manager.py list
  python n8n_manager.py get pmDPBdREgE5wf1Cn
  python n8n_manager.py update pmDPBdREgE5wf1Cn workflows/BAT_IN_Telegram.json
  python n8n_manager.py activate pmDPBdREgE5wf1Cn
        """)
        sys.exit(1)

    command = sys.argv[1]

    try:
        if command == "list":
            list_workflows()

        elif command == "get":
            if len(sys.argv) < 3:
                print("❌ Укажите ID воркера")
                sys.exit(1)
            workflow_id = sys.argv[2]
            filename = sys.argv[3] if len(sys.argv) > 3 else None
            save_workflow(workflow_id, filename)

        elif command == "update":
            if len(sys.argv) < 4:
                print("❌ Укажите ID воркера и путь к JSON файлу")
                sys.exit(1)
            workflow_id = sys.argv[2]
            json_file = sys.argv[3]
            update_workflow(workflow_id, json_file)

        elif command == "activate":
            if len(sys.argv) < 3:
                print("❌ Укажите ID воркера")
                sys.exit(1)
            workflow_id = sys.argv[2]
            activate_workflow(workflow_id, True)

        elif command == "deactivate":
            if len(sys.argv) < 3:
                print("❌ Укажите ID воркера")
                sys.exit(1)
            workflow_id = sys.argv[2]
            activate_workflow(workflow_id, False)

        else:
            print(f"❌ Неизвестная команда: {command}")
            sys.exit(1)

    except requests.exceptions.HTTPError as e:
        print(f"❌ Ошибка API: {e}")
        print(f"   Response: {e.response.text}")
        sys.exit(1)
    except Exception as e:
        print(f"❌ Ошибка: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()
