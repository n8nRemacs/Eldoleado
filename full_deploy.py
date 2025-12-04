#!/usr/bin/env python3
"""Full deploy of Eldoleado project to server."""

import paramiko
from scp import SCPClient
import os
import sys
import tarfile
import tempfile

sys.stdout.reconfigure(encoding='utf-8')

SERVER = "45.144.177.128"
USER = "root"
PASSWORD = "Mi31415926pSss!"

# What to deploy
COMPONENTS = {
    "ifixit-parser": {
        "local_path": r"c:\Users\User\Documents\Eldoleado\ifixit-parser",
        "remote_path": "/opt/ifixit-parser",
        "excludes": [".git", "__pycache__", "*.pyc", ".venv", "venv", "*.egg-info", "data/raw", "data/images"],
        "setup_commands": [
            "cd /opt/ifixit-parser && python3 -m venv venv 2>/dev/null || true",
            "cd /opt/ifixit-parser && ./venv/bin/pip install --upgrade pip -q",
            "cd /opt/ifixit-parser && ./venv/bin/pip install -e . -q",
        ],
        "test_command": "cd /opt/ifixit-parser && ./venv/bin/python -c 'from src.parser.main import app; print(\"OK\")'",
    },
}


def create_archive(local_path, excludes):
    """Create tar.gz archive excluding specified patterns."""
    archive_path = tempfile.mktemp(suffix='.tar.gz')

    def filter_func(tarinfo):
        name = tarinfo.name
        for exclude in excludes:
            if exclude.startswith("*"):
                if name.endswith(exclude[1:]):
                    return None
            elif exclude in name:
                return None
        return tarinfo

    with tarfile.open(archive_path, "w:gz") as tar:
        tar.add(local_path, arcname=os.path.basename(local_path), filter=filter_func)

    return archive_path


def deploy_component(ssh, scp, name, config):
    """Deploy a single component."""
    print(f"\n{'='*60}")
    print(f"Deploying: {name}")
    print(f"{'='*60}")

    local_path = config["local_path"]
    remote_path = config["remote_path"]

    # Check if local path exists
    if not os.path.exists(local_path):
        print(f"  [ERROR] Local path not found: {local_path}")
        return False

    # Create archive
    print(f"  Creating archive...")
    archive_path = create_archive(local_path, config.get("excludes", []))
    archive_size = os.path.getsize(archive_path) / 1024 / 1024
    print(f"  Archive size: {archive_size:.2f} MB")

    # Upload
    print(f"  Uploading to server...")
    remote_archive = f"/tmp/{name}.tar.gz"
    scp.put(archive_path, remote_archive)
    print(f"  Uploaded!")

    # Extract
    print(f"  Extracting...")
    # Backup existing .env if present
    ssh.exec_command(f"cp {remote_path}/.env /tmp/{name}_env_backup 2>/dev/null || true")

    # Remove old and extract new
    cmd = f"rm -rf {remote_path} && mkdir -p {os.path.dirname(remote_path)} && cd {os.path.dirname(remote_path)} && tar -xzf {remote_archive}"
    stdin, stdout, stderr = ssh.exec_command(cmd)
    stdout.channel.recv_exit_status()

    # Restore .env
    ssh.exec_command(f"cp /tmp/{name}_env_backup {remote_path}/.env 2>/dev/null || true")

    # Cleanup
    os.remove(archive_path)
    ssh.exec_command(f"rm {remote_archive}")

    # Run setup commands
    for setup_cmd in config.get("setup_commands", []):
        print(f"  Running: {setup_cmd[:60]}...")
        stdin, stdout, stderr = ssh.exec_command(setup_cmd)
        exit_code = stdout.channel.recv_exit_status()
        if exit_code != 0:
            err = stderr.read().decode()
            print(f"    [WARN] Exit code {exit_code}: {err[:200]}")

    # Test
    test_cmd = config.get("test_command")
    if test_cmd:
        print(f"  Testing...")
        stdin, stdout, stderr = ssh.exec_command(test_cmd)
        out = stdout.read().decode().strip()
        err = stderr.read().decode().strip()
        if "OK" in out or not err:
            print(f"    [OK] {out}")
        else:
            print(f"    [ERROR] {err[:200]}")
            return False

    print(f"  [SUCCESS] {name} deployed!")
    return True


def check_services(ssh):
    """Check running services."""
    print(f"\n{'='*60}")
    print("Checking services...")
    print(f"{'='*60}")

    # Parser
    stdin, stdout, stderr = ssh.exec_command("ps aux | grep 'smart-parse\\|ifixit' | grep -v grep | head -3")
    procs = stdout.read().decode().strip()
    if procs:
        print(f"\niFixit Parser:")
        for line in procs.split('\n')[:3]:
            parts = line.split()
            if len(parts) > 10:
                print(f"  PID {parts[1]}: {' '.join(parts[10:])[:60]}")
    else:
        print("\niFixit Parser: Not running")

    # Screen sessions
    stdin, stdout, stderr = ssh.exec_command("screen -ls 2>/dev/null | grep -E '\\.[a-z]' | head -5")
    screens = stdout.read().decode().strip()
    if screens:
        print(f"\nScreen sessions:")
        print(f"  {screens}")


def main():
    print("="*60)
    print("Eldoleado Full Deploy")
    print("="*60)
    print(f"Server: {SERVER}")
    print(f"Components: {', '.join(COMPONENTS.keys())}")

    # Connect
    print(f"\nConnecting to {SERVER}...")
    ssh = paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(SERVER, username=USER, password=PASSWORD, timeout=30)
    print("Connected!")

    # Server info
    stdin, stdout, stderr = ssh.exec_command("python3 --version && uname -a | cut -d' ' -f1-3")
    print(f"Server: {stdout.read().decode().strip()}")

    # Create SCP client
    scp = SCPClient(ssh.get_transport())

    # Deploy each component
    results = {}
    for name, config in COMPONENTS.items():
        try:
            results[name] = deploy_component(ssh, scp, name, config)
        except Exception as e:
            print(f"  [ERROR] {e}")
            results[name] = False

    # Check services
    check_services(ssh)

    # Summary
    print(f"\n{'='*60}")
    print("DEPLOYMENT SUMMARY")
    print(f"{'='*60}")
    for name, success in results.items():
        status = "[OK]" if success else "[FAILED]"
        print(f"  {status} {name}")

    scp.close()
    ssh.close()

    print(f"\nDone!")
    return all(results.values())


if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1)
