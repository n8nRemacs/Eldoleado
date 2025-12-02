#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
BatteryCRM Development Environment Setup Script
Автоматическая настройка полной среды разработки на новом компьютере

Устанавливает и настраивает:
- MCP серверы для Claude Code (Android, PostgreSQL, Mobile)
- Подключения к серверам (VPN, n8n, Supabase)
- Git и GitHub
- Android Studio и SDK
- SSH ключи и доступы
"""

import os
import sys
import subprocess
import json
import platform
import shutil
from pathlib import Path

# Fix Windows console encoding for UTF-8/emoji support
if platform.system() == "Windows":
    try:
        import codecs
        sys.stdout = codecs.getwriter("utf-8")(sys.stdout.detach())
        sys.stderr = codecs.getwriter("utf-8")(sys.stderr.detach())
    except:
        pass

# Цвета для терминала
class Colors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'

def print_header(text):
    print(f"\n{Colors.HEADER}{Colors.BOLD}{'='*60}{Colors.ENDC}")
    print(f"{Colors.HEADER}{Colors.BOLD}{text}{Colors.ENDC}")
    print(f"{Colors.HEADER}{Colors.BOLD}{'='*60}{Colors.ENDC}\n")

def print_success(text):
    print(f"{Colors.OKGREEN}✅ {text}{Colors.ENDC}")

def print_error(text):
    print(f"{Colors.FAIL}❌ {text}{Colors.ENDC}")

def print_warning(text):
    print(f"{Colors.WARNING}⚠️  {text}{Colors.ENDC}")

def print_info(text):
    print(f"{Colors.OKCYAN}ℹ️  {text}{Colors.ENDC}")

# Конфигурация серверов
SERVERS = {
    "vpn": {
        "name": "VPN Server (Trojan, Vless, OpenVPN)",
        "host": "217.145.79.27",
        "user": "root",
        "password": "Mi31415926pSss!",
        "services": ["Trojan", "Vless", "OpenVPN"]
    },
    "n8n_supabase": {
        "name": "n8n + Supabase Server",
        "host": "185.221.214.83",
        "user": "root",
        "password": "Mi31415926pSss!",
        "services": ["n8n", "Supabase", "PostgreSQL"]
    }
}

# Конфигурация сервисов
SERVICES = {
    "n8n": {
        "url": "https://n8n.n8nsrv.ru",
        "api_key": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
    },
    "supabase": {
        "url": "https://supabase.n8nsrv.ru",
        "service_role_key": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoyMDcxNDc3NDgzLCJpYXQiOjE3NTYxMTc0ODMsImlzcyI6InN1cGFiYXNlIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsInN1YiI6InNlcnZpY2Vfcm9sZSJ9.Qlna54wJBmioNVOeRKbSn_z5o2mCBLdsBvFu5ZkXZx0",
        "postgres_url": "postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres"
    },
    "github": {
        "repo": "https://github.com/n8nRemacs/batterycrm"
    }
}

# MCP серверы для установки
MCP_SERVERS = {
    "mcpandroidbuild": {
        "install": "pip install mcpandroidbuild",
        "config": {
            "command": "python",
            "args": ["-m", "mcpandroidbuild"]
        }
    },
    "memory": {
        "install": None,  # Устанавливается через npx автоматически
        "config": {
            "command": "npx",
            "args": ["-y", "@modelcontextprotocol/server-memory"]
        }
    },
    "mobile-mcp": {
        "install": "npm install -g mobile-mcp",
        "config": {
            "type": "stdio",
            "command": "npx",
            "args": ["mobile-mcp"],
            "env": {
                "ANDROID_SDK_ROOT": None  # Будет определено автоматически
            }
        }
    },
    "postgres-batterycrm": {
        "install": "npm install -g @henkey/postgres-mcp-server",
        "config": {
            "type": "stdio",
            "command": "npx",
            "args": ["@henkey/postgres-mcp-server"],
            "env": {
                "POSTGRES_CONNECTION_STRING": SERVICES["supabase"]["postgres_url"]
            }
        }
    }
}

def check_os():
    """Определить операционную систему"""
    os_type = platform.system()
    print_info(f"Detected OS: {os_type}")
    return os_type

def is_admin():
    """Проверка прав администратора"""
    try:
        return os.getuid() == 0
    except AttributeError:
        import ctypes
        return ctypes.windll.shell32.IsUserAnAdmin() != 0

def run_command(command, shell=True, check=True):
    """Выполнить команду с обработкой ошибок"""
    try:
        result = subprocess.run(
            command,
            shell=shell,
            check=check,
            capture_output=True,
            text=True
        )
        return result.returncode == 0, result.stdout, result.stderr
    except subprocess.CalledProcessError as e:
        return False, e.stdout, e.stderr
    except Exception as e:
        return False, "", str(e)

def check_installed(command):
    """Проверить установлен ли инструмент"""
    success, stdout, stderr = run_command(f"{command} --version", check=False)
    return success

def install_python_package(package_name, pip_command="pip"):
    """Установить Python пакет"""
    print_info(f"Installing Python package: {package_name}")
    success, stdout, stderr = run_command(f"{pip_command} install {package_name}")
    if success:
        print_success(f"{package_name} installed successfully")
    else:
        print_error(f"Failed to install {package_name}: {stderr}")
    return success

def install_npm_package(package_name, global_install=True):
    """Установить NPM пакет"""
    cmd = f"npm install {'-g' if global_install else ''} {package_name}"
    print_info(f"Installing NPM package: {package_name}")
    success, stdout, stderr = run_command(cmd)
    if success:
        print_success(f"{package_name} installed successfully")
    else:
        print_error(f"Failed to install {package_name}: {stderr}")
    return success

def find_android_sdk():
    """Найти Android SDK"""
    os_type = platform.system()

    possible_paths = []
    if os_type == "Windows":
        possible_paths = [
            os.path.expandvars(r"%LOCALAPPDATA%\Android\Sdk"),
            os.path.expandvars(r"%USERPROFILE%\AppData\Local\Android\Sdk"),
            r"C:\Android\Sdk"
        ]
    elif os_type == "Darwin":  # macOS
        possible_paths = [
            os.path.expanduser("~/Library/Android/sdk"),
            "/usr/local/android-sdk"
        ]
    else:  # Linux
        possible_paths = [
            os.path.expanduser("~/Android/Sdk"),
            "/opt/android-sdk"
        ]

    for path in possible_paths:
        if os.path.exists(path):
            print_success(f"Found Android SDK at: {path}")
            return path

    print_warning("Android SDK not found automatically")
    return None

def get_claude_config_path():
    """Получить путь к конфигурации Claude Code"""
    os_type = platform.system()

    if os_type == "Windows":
        return os.path.expandvars(r"%USERPROFILE%\.claude.json")
    else:
        return os.path.expanduser("~/.claude.json")

def load_claude_config():
    """Загрузить конфигурацию Claude Code"""
    config_path = get_claude_config_path()

    if os.path.exists(config_path):
        try:
            with open(config_path, 'r', encoding='utf-8') as f:
                return json.load(f)
        except Exception as e:
            print_warning(f"Failed to load existing config: {e}")
            return {}
    return {}

def save_claude_config(config):
    """Сохранить конфигурацию Claude Code"""
    config_path = get_claude_config_path()

    try:
        with open(config_path, 'w', encoding='utf-8') as f:
            json.dump(config, f, indent=2, ensure_ascii=False)
        print_success(f"Claude Code config saved to: {config_path}")
        return True
    except Exception as e:
        print_error(f"Failed to save config: {e}")
        return False

def setup_mcp_servers():
    """Установить и настроить MCP серверы"""
    print_header("Setting up MCP Servers")

    # Проверить зависимости
    print_info("Checking dependencies...")

    if not check_installed("python"):
        print_error("Python is not installed! Please install Python 3.8+ first")
        return False

    if not check_installed("node"):
        print_error("Node.js is not installed! Please install Node.js first")
        return False

    if not check_installed("npm"):
        print_error("NPM is not installed! Please install NPM first")
        return False

    print_success("All dependencies are available")

    # Установить MCP серверы
    for server_name, server_info in MCP_SERVERS.items():
        print_info(f"Setting up {server_name}...")

        if server_info["install"]:
            install_cmd = server_info["install"]
            if install_cmd.startswith("pip"):
                # Python package
                package = install_cmd.replace("pip install ", "")
                install_python_package(package)
            elif install_cmd.startswith("npm"):
                # NPM package
                package = install_cmd.replace("npm install -g ", "")
                install_npm_package(package, global_install=True)

    # Настроить конфигурацию Claude Code
    print_info("Configuring Claude Code MCP servers...")

    config = load_claude_config()

    # Найти проект BatteryCRM
    current_dir = os.getcwd()
    project_key = current_dir

    # Создать секцию проекта если не существует
    if "projects" not in config:
        config["projects"] = {}

    if project_key not in config["projects"]:
        config["projects"][project_key] = {
            "allowedTools": [],
            "mcpContextUris": [],
            "mcpServers": {},
            "hasTrustDialogAccepted": True
        }

    # Найти Android SDK
    android_sdk = find_android_sdk()

    # Добавить MCP серверы
    mcp_config = {}
    for server_name, server_info in MCP_SERVERS.items():
        server_config = server_info["config"].copy()

        # Подставить Android SDK путь
        if server_name == "mobile-mcp" and android_sdk:
            server_config["env"]["ANDROID_SDK_ROOT"] = android_sdk

        mcp_config[server_name] = server_config

    config["projects"][project_key]["mcpServers"] = mcp_config

    # Сохранить конфигурацию
    if save_claude_config(config):
        print_success("MCP servers configured successfully")
        return True
    else:
        return False

def setup_ssh_keys():
    """Настроить SSH ключи для серверов"""
    print_header("Setting up SSH Keys")

    ssh_dir = os.path.expanduser("~/.ssh")
    os.makedirs(ssh_dir, exist_ok=True)

    # Создать конфигурационный файл SSH
    ssh_config_path = os.path.join(ssh_dir, "config")

    ssh_config_content = """# BatteryCRM Development Servers

# VPN Server (Trojan, Vless, OpenVPN)
Host vpn-server
    HostName 217.145.79.27
    User root
    Port 22
    IdentityFile ~/.ssh/id_rsa

# n8n + Supabase Server
Host n8n-server
    HostName 185.221.214.83
    User root
    Port 22
    IdentityFile ~/.ssh/id_rsa

# Aliases
Host batterycrm-vpn
    HostName 217.145.79.27
    User root

Host batterycrm-n8n
    HostName 185.221.214.83
    User root
"""

    try:
        with open(ssh_config_path, 'w') as f:
            f.write(ssh_config_content)
        print_success(f"SSH config created at: {ssh_config_path}")
    except Exception as e:
        print_error(f"Failed to create SSH config: {e}")
        return False

    # Создать credentials файл (НЕ для Git!)
    credentials_path = os.path.join(os.getcwd(), ".dev_credentials")

    credentials = {
        "servers": SERVERS,
        "services": SERVICES,
        "note": "DO NOT COMMIT THIS FILE TO GIT!"
    }

    try:
        with open(credentials_path, 'w') as f:
            json.dump(credentials, f, indent=2)
        print_success(f"Credentials saved to: {credentials_path}")
        print_warning("IMPORTANT: Add .dev_credentials to .gitignore!")
    except Exception as e:
        print_error(f"Failed to save credentials: {e}")

    return True

def setup_git():
    """Настроить Git и клонировать репозиторий"""
    print_header("Setting up Git and GitHub")

    if not check_installed("git"):
        print_error("Git is not installed! Please install Git first")
        return False

    # Проверить существует ли уже репозиторий
    if os.path.exists(".git"):
        print_info("Git repository already exists")

        # Обновить remote
        run_command("git remote remove origin", check=False)
        run_command(f"git remote add origin {SERVICES['github']['repo']}")

        print_success("Git remote updated")
    else:
        print_info("Initializing Git repository...")
        run_command("git init")
        run_command(f"git remote add origin {SERVICES['github']['repo']}")
        print_success("Git initialized")

    # Настроить Git config
    print_info("Configuring Git...")
    auto_mode = "--auto" in sys.argv or "-y" in sys.argv

    if auto_mode:
        print_info("Auto mode: Skipping Git user configuration")
    else:
        name = input("Enter your Git name (or press Enter to skip): ").strip()
        if name:
            run_command(f'git config user.name "{name}"')

        email = input("Enter your Git email (or press Enter to skip): ").strip()
        if email:
            run_command(f'git config user.email "{email}"')

    print_success("Git configured")
    return True

def create_env_file():
    """Создать .env файл с настройками"""
    print_header("Creating .env file")

    env_content = f"""# BatteryCRM Development Environment
# DO NOT COMMIT THIS FILE TO GIT!

# n8n Configuration
N8N_URL={SERVICES['n8n']['url']}
N8N_API_KEY={SERVICES['n8n']['api_key']}

# Supabase Configuration
SUPABASE_URL={SERVICES['supabase']['url']}
SUPABASE_SERVICE_ROLE_KEY={SERVICES['supabase']['service_role_key']}
POSTGRES_CONNECTION_STRING={SERVICES['supabase']['postgres_url']}

# GitHub
GITHUB_REPO={SERVICES['github']['repo']}

# Servers
VPN_SERVER_HOST=217.145.79.27
VPN_SERVER_USER=root
VPN_SERVER_PASSWORD=Mi31415926pSss!

N8N_SERVER_HOST=185.221.214.83
N8N_SERVER_USER=root
N8N_SERVER_PASSWORD=Mi31415926pSss!
"""

    env_path = ".env"
    try:
        with open(env_path, 'w') as f:
            f.write(env_content)
        print_success(f".env file created at: {env_path}")
        print_warning("IMPORTANT: Add .env to .gitignore!")
        return True
    except Exception as e:
        print_error(f"Failed to create .env file: {e}")
        return False

def update_gitignore():
    """Обновить .gitignore"""
    print_header("Updating .gitignore")

    gitignore_entries = [
        "# Development credentials",
        ".dev_credentials",
        ".env",
        ".env.local",
        "*.pem",
        "*.key",
        "",
        "# Claude Code local config",
        ".claude.json",
        "",
        "# IDE",
        ".vscode/",
        ".idea/",
        "*.swp",
        "*.swo",
        "",
        "# OS",
        ".DS_Store",
        "Thumbs.db",
        "",
        "# Python",
        "__pycache__/",
        "*.pyc",
        "venv/",
        ".venv/",
        "",
        "# Node",
        "node_modules/",
        "npm-debug.log",
        "",
        "# Android",
        "*.apk",
        "*.ap_",
        "*.dex",
        "local.properties",
        "",
    ]

    gitignore_path = ".gitignore"

    existing_content = ""
    if os.path.exists(gitignore_path):
        with open(gitignore_path, 'r') as f:
            existing_content = f.read()

    # Добавить новые записи если их нет
    new_entries = []
    for entry in gitignore_entries:
        if entry and entry not in existing_content:
            new_entries.append(entry)

    if new_entries:
        with open(gitignore_path, 'a') as f:
            if existing_content and not existing_content.endswith('\n'):
                f.write('\n')
            f.write('\n'.join(new_entries))
            f.write('\n')
        print_success(".gitignore updated")
    else:
        print_info(".gitignore is already up to date")

    return True

def install_python_tools():
    """Установить Python инструменты проекта"""
    print_header("Installing Python Tools")

    tools = [
        "requests",
        "psycopg2-binary",
        "python-dotenv"
    ]

    for tool in tools:
        install_python_package(tool)

    print_success("Python tools installed")
    return True

def test_connections():
    """Протестировать подключения к сервисам"""
    print_header("Testing Connections")

    # Тест n8n API
    print_info("Testing n8n API connection...")
    try:
        import requests
        headers = {
            "X-N8N-API-KEY": SERVICES['n8n']['api_key'],
            "Accept": "application/json"
        }
        response = requests.get(
            f"{SERVICES['n8n']['url']}/api/v1/workflows",
            headers=headers,
            timeout=10
        )
        if response.status_code == 200:
            workflows = response.json()
            print_success(f"n8n API: OK ({len(workflows.get('data', []))} workflows)")
        else:
            print_error(f"n8n API: Failed (Status {response.status_code})")
    except Exception as e:
        print_error(f"n8n API: Failed ({str(e)})")

    # Тест Supabase API
    print_info("Testing Supabase API connection...")
    try:
        headers = {
            "apikey": SERVICES['supabase']['service_role_key'],
            "Authorization": f"Bearer {SERVICES['supabase']['service_role_key']}"
        }
        response = requests.get(
            f"{SERVICES['supabase']['url']}/rest/v1/tenants",
            headers=headers,
            params={"limit": 1},
            timeout=10
        )
        if response.status_code in [200, 206]:
            print_success("Supabase API: OK")
        else:
            print_error(f"Supabase API: Failed (Status {response.status_code})")
    except Exception as e:
        print_error(f"Supabase API: Failed ({str(e)})")

    # Тест SSH серверов (пинг)
    print_info("Testing server connectivity...")
    for server_key, server_info in SERVERS.items():
        host = server_info['host']
        if platform.system() == "Windows":
            cmd = f"ping -n 1 {host}"
        else:
            cmd = f"ping -c 1 {host}"

        success, _, _ = run_command(cmd, check=False)
        if success:
            print_success(f"{server_info['name']}: Reachable")
        else:
            print_warning(f"{server_info['name']}: Not reachable (check VPN?)")

def print_summary():
    """Вывести итоговую информацию"""
    print_header("Setup Complete! 🎉")

    print(f"""
{Colors.OKGREEN}Development environment is ready!{Colors.ENDC}

{Colors.BOLD}Installed MCP Servers:{Colors.ENDC}
  ✅ mcpandroidbuild - Android project build & testing
  ✅ memory - Context memory between sessions
  ✅ mobile-mcp - Android device management
  ✅ postgres-batterycrm - PostgreSQL database access

{Colors.BOLD}Configured Services:{Colors.ENDC}
  🔗 n8n: {SERVICES['n8n']['url']}
  🔗 Supabase: {SERVICES['supabase']['url']}
  🔗 GitHub: {SERVICES['github']['repo']}

{Colors.BOLD}Servers:{Colors.ENDC}
  🖥️  VPN Server: {SERVERS['vpn']['host']} ({', '.join(SERVERS['vpn']['services'])})
  🖥️  n8n/Supabase: {SERVERS['n8n_supabase']['host']} ({', '.join(SERVERS['n8n_supabase']['services'])})

{Colors.BOLD}Important Files Created:{Colors.ENDC}
  📄 .env - Environment variables (DO NOT COMMIT!)
  📄 .dev_credentials - Server credentials (DO NOT COMMIT!)
  📄 ~/.ssh/config - SSH configuration
  📄 ~/.claude.json - Claude Code MCP configuration

{Colors.BOLD}Next Steps:{Colors.ENDC}
  1. Restart Claude Code to load MCP servers
  2. Run: claude mcp list (to verify all servers connected)
  3. Test connection: python supabase_manager.py list
  4. Pull from GitHub: git pull origin main
  5. Read: MCP_COMPLETE_SETUP.md for full documentation

{Colors.BOLD}SSH Connection Examples:{Colors.ENDC}
  ssh vpn-server
  ssh n8n-server
  ssh root@217.145.79.27
  ssh root@185.221.214.83

{Colors.WARNING}⚠️  IMPORTANT SECURITY NOTES:{Colors.ENDC}
  • Never commit .env or .dev_credentials to Git!
  • .gitignore has been updated automatically
  • Credentials are stored locally only
  • Use SSH keys instead of passwords when possible

{Colors.BOLD}Documentation:{Colors.ENDC}
  📖 MCP_COMPLETE_SETUP.md - Full MCP setup guide
  📖 MCP_ANDROID_SETUP.md - Android development
  📖 MCP_POSTGRES_SETUP.md - Database access
  📖 Start.md - Project context and workflow

{Colors.OKGREEN}Happy Coding! 🚀{Colors.ENDC}
""")

def main():
    """Главная функция"""
    print_header("BatteryCRM Development Environment Setup")
    print(f"{Colors.OKCYAN}This script will set up your complete development environment{Colors.ENDC}\n")

    # Check for auto mode
    auto_mode = "--auto" in sys.argv or "-y" in sys.argv

    # Проверить OS
    os_type = check_os()

    # Предупреждение о правах
    if not is_admin():
        print_warning("Running without administrator privileges")
        print_info("Some operations may require admin rights")
        if auto_mode:
            print_info("Auto mode: Continuing without admin rights")
        else:
            response = input("Continue anyway? (y/n): ")
            if response.lower() != 'y':
                print("Setup cancelled")
                return

    # Шаги установки
    steps = [
        ("Install Python tools", install_python_tools),
        ("Setup MCP Servers", setup_mcp_servers),
        ("Setup SSH keys and config", setup_ssh_keys),
        ("Setup Git and GitHub", setup_git),
        ("Create .env file", create_env_file),
        ("Update .gitignore", update_gitignore),
        ("Test connections", test_connections),
    ]

    failed_steps = []

    for step_name, step_func in steps:
        print(f"\n{Colors.BOLD}Step: {step_name}{Colors.ENDC}")
        try:
            if not step_func():
                failed_steps.append(step_name)
                print_error(f"Step '{step_name}' failed!")
                if auto_mode:
                    print_info("Auto mode: Continuing with next step")
                else:
                    response = input("Continue with next step? (y/n): ")
                    if response.lower() != 'y':
                        print("Setup cancelled")
                        return
        except Exception as e:
            print_error(f"Step '{step_name}' failed with error: {e}")
            failed_steps.append(step_name)
            if auto_mode:
                print_info("Auto mode: Continuing with next step")
            else:
                response = input("Continue with next step? (y/n): ")
                if response.lower() != 'y':
                    print("Setup cancelled")
                    return

    # Вывести итоги
    print_summary()

    if failed_steps:
        print_warning(f"\n{len(failed_steps)} step(s) failed:")
        for step in failed_steps:
            print(f"  ❌ {step}")
        print("\nPlease review the errors above and fix manually")
    else:
        print_success("\n✨ All steps completed successfully!")

if __name__ == "__main__":
    main()
