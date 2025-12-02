#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Test all connections and services
Быстрая проверка всех подключений после установки
"""

import requests
import subprocess
import platform
import os
import sys
from pathlib import Path

# Fix Windows console encoding for UTF-8/emoji support
if platform.system() == "Windows":
    try:
        import codecs
        sys.stdout = codecs.getwriter("utf-8")(sys.stdout.detach())
        sys.stderr = codecs.getwriter("utf-8")(sys.stderr.detach())
    except:
        pass

# Цвета
class C:
    G = '\033[92m'  # Green
    R = '\033[91m'  # Red
    Y = '\033[93m'  # Yellow
    B = '\033[94m'  # Blue
    E = '\033[0m'   # End

def test_n8n_api():
    """Тест n8n API"""
    print(f"\n{C.B}Testing n8n API...{C.E}")
    try:
        headers = {
            "X-N8N-API-KEY": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M",
            "Accept": "application/json"
        }
        response = requests.get(
            "https://n8n.n8nsrv.ru/api/v1/workflows",
            headers=headers,
            timeout=10
        )
        if response.status_code == 200:
            data = response.json()
            count = len(data.get('data', []))
            print(f"{C.G}✅ n8n API: OK ({count} workflows){C.E}")
            return True
        else:
            print(f"{C.R}❌ n8n API: Failed (Status {response.status_code}){C.E}")
            return False
    except Exception as e:
        print(f"{C.R}❌ n8n API: Failed ({str(e)}){C.E}")
        return False

def test_supabase_api():
    """Тест Supabase API"""
    print(f"\n{C.B}Testing Supabase API...{C.E}")
    try:
        headers = {
            "apikey": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0",
            "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0"
        }
        response = requests.get(
            "https://supabase.n8nsrv.ru/rest/v1/tenants",
            headers=headers,
            params={"limit": 1},
            timeout=10
        )
        if response.status_code in [200, 206]:
            print(f"{C.G}✅ Supabase API: OK{C.E}")
            return True
        else:
            print(f"{C.R}❌ Supabase API: Failed (Status {response.status_code}){C.E}")
            return False
    except Exception as e:
        print(f"{C.R}❌ Supabase API: Failed ({str(e)}){C.E}")
        return False

def test_server_ping(host, name):
    """Тест ping сервера"""
    print(f"\n{C.B}Testing {name} ({host})...{C.E}")
    try:
        if platform.system() == "Windows":
            cmd = f"ping -n 1 -w 1000 {host}"
        else:
            cmd = f"ping -c 1 -W 1 {host}"

        result = subprocess.run(
            cmd,
            shell=True,
            capture_output=True,
            timeout=5
        )

        if result.returncode == 0:
            print(f"{C.G}✅ {name}: Reachable{C.E}")
            return True
        else:
            print(f"{C.Y}⚠️  {name}: Not reachable (check VPN?){C.E}")
            return False
    except Exception as e:
        print(f"{C.R}❌ {name}: Failed ({str(e)}){C.E}")
        return False

def test_mcp_servers():
    """Тест MCP серверов"""
    print(f"\n{C.B}Testing MCP servers...{C.E}")
    try:
        result = subprocess.run(
            "claude mcp list",
            shell=True,
            capture_output=True,
            text=True,
            timeout=10
        )

        if result.returncode == 0:
            output = result.stdout
            servers = {
                'mcpandroidbuild': '✗',
                'memory': '✗',
                'mobile-mcp': '✗',
                'postgres-batterycrm': '✗'
            }

            for line in output.split('\n'):
                for server in servers.keys():
                    if server in line:
                        if 'Connected' in line or '✓' in line:
                            servers[server] = '✓'

            all_connected = all(status == '✓' for status in servers.values())

            for server, status in servers.items():
                if status == '✓':
                    print(f"  {C.G}✓ {server}: Connected{C.E}")
                else:
                    print(f"  {C.R}✗ {server}: Not connected{C.E}")

            if all_connected:
                print(f"\n{C.G}✅ All MCP servers: Connected{C.E}")
                return True
            else:
                print(f"\n{C.Y}⚠️  Some MCP servers not connected{C.E}")
                return False
        else:
            print(f"{C.R}❌ MCP servers: Failed to check (claude command not found?){C.E}")
            return False
    except Exception as e:
        print(f"{C.R}❌ MCP servers: Failed ({str(e)}){C.E}")
        return False

def test_git():
    """Тест Git"""
    print(f"\n{C.B}Testing Git...{C.E}")
    try:
        # Проверить git установлен
        result = subprocess.run(
            "git --version",
            shell=True,
            capture_output=True,
            text=True,
            timeout=5
        )

        if result.returncode == 0:
            print(f"{C.G}✅ Git: Installed ({result.stdout.strip()}){C.E}")

            # Проверить remote
            result = subprocess.run(
                "git remote -v",
                shell=True,
                capture_output=True,
                text=True,
                timeout=5
            )

            if 'batterycrm' in result.stdout:
                print(f"{C.G}✅ Git remote: Configured{C.E}")
                return True
            else:
                print(f"{C.Y}⚠️  Git remote: Not configured{C.E}")
                return False
        else:
            print(f"{C.R}❌ Git: Not installed{C.E}")
            return False
    except Exception as e:
        print(f"{C.R}❌ Git: Failed ({str(e)}){C.E}")
        return False

def test_python_packages():
    """Тест Python пакетов"""
    print(f"\n{C.B}Testing Python packages...{C.E}")

    packages = [
        'requests',
        'psycopg2',
        'mcpandroidbuild'
    ]

    all_ok = True
    for package in packages:
        try:
            __import__(package.replace('-', '_'))
            print(f"  {C.G}✓ {package}: Installed{C.E}")
        except ImportError:
            print(f"  {C.R}✗ {package}: Not installed{C.E}")
            all_ok = False

    if all_ok:
        print(f"\n{C.G}✅ Python packages: All installed{C.E}")
    else:
        print(f"\n{C.Y}⚠️  Python packages: Some missing{C.E}")

    return all_ok

def test_android_sdk():
    """Тест Android SDK"""
    print(f"\n{C.B}Testing Android SDK...{C.E}")

    os_type = platform.system()
    possible_paths = []

    if os_type == "Windows":
        possible_paths = [
            os.path.expandvars(r"%LOCALAPPDATA%\Android\Sdk"),
            os.path.expandvars(r"%USERPROFILE%\AppData\Local\Android\Sdk"),
        ]
    elif os_type == "Darwin":
        possible_paths = [
            os.path.expanduser("~/Library/Android/sdk"),
        ]
    else:
        possible_paths = [
            os.path.expanduser("~/Android/Sdk"),
        ]

    for path in possible_paths:
        if os.path.exists(path):
            print(f"{C.G}✅ Android SDK: Found at {path}{C.E}")
            return True

    print(f"{C.Y}⚠️  Android SDK: Not found (skip if not needed){C.E}")
    return False

def test_env_file():
    """Проверить .env файл"""
    print(f"\n{C.B}Testing .env file...{C.E}")

    if os.path.exists('.env'):
        print(f"{C.G}✅ .env file: Exists{C.E}")

        # Проверить содержимое
        with open('.env', 'r') as f:
            content = f.read()
            required = ['N8N_URL', 'SUPABASE_URL', 'N8N_API_KEY']
            missing = [key for key in required if key not in content]

            if not missing:
                print(f"{C.G}✅ .env file: All required vars present{C.E}")
                return True
            else:
                print(f"{C.Y}⚠️  .env file: Missing vars: {', '.join(missing)}{C.E}")
                return False
    else:
        print(f"{C.R}❌ .env file: Not found{C.E}")
        return False

def main():
    print(f"\n{C.B}{'='*60}{C.E}")
    print(f"{C.B}BatteryCRM Environment Test Suite{C.E}")
    print(f"{C.B}{'='*60}{C.E}")

    results = {
        "Python packages": test_python_packages(),
        "Git": test_git(),
        ".env file": test_env_file(),
        "MCP servers": test_mcp_servers(),
        "n8n API": test_n8n_api(),
        "Supabase API": test_supabase_api(),
        "VPN Server": test_server_ping("217.145.79.27", "VPN Server"),
        "n8n/Supabase Server": test_server_ping("185.221.214.83", "n8n/Supabase Server"),
        "Android SDK": test_android_sdk(),
    }

    # Итоги
    print(f"\n{C.B}{'='*60}{C.E}")
    print(f"{C.B}Test Summary{C.E}")
    print(f"{C.B}{'='*60}{C.E}\n")

    passed = sum(1 for result in results.values() if result)
    total = len(results)

    for test_name, result in results.items():
        status = f"{C.G}PASS{C.E}" if result else f"{C.R}FAIL{C.E}"
        print(f"  {status}  {test_name}")

    print(f"\n{C.B}{'='*60}{C.E}")
    print(f"{C.B}Total: {passed}/{total} tests passed{C.E}")
    print(f"{C.B}{'='*60}{C.E}\n")

    if passed == total:
        print(f"{C.G}🎉 All tests passed! Environment is ready!{C.E}\n")
        return 0
    elif passed >= total * 0.7:
        print(f"{C.Y}⚠️  Most tests passed. Review failures above.{C.E}\n")
        return 0
    else:
        print(f"{C.R}❌ Many tests failed. Please fix issues above.{C.E}\n")
        return 1

if __name__ == "__main__":
    exit(main())
