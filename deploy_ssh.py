#!/usr/bin/env python3
"""Deploy ifixit-parser to server."""

import paramiko
from scp import SCPClient
import os

SERVER = "45.144.177.128"
USER = "root"
PASSWORD = "Mi31415926pSss!"
ARCHIVE = r"c:\Users\User\Documents\Eldoleado\ifixit-parser.tar.gz"

def main():
    print(f"Connecting to {SERVER}...")
    ssh = paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(SERVER, username=USER, password=PASSWORD)
    print("Connected!")

    # Check environment
    print("\n=== Server Info ===")
    stdin, stdout, stderr = ssh.exec_command("python3 --version && which python3 && df -h / | tail -1")
    print(stdout.read().decode())
    err = stderr.read().decode()
    if err:
        print(f"Errors: {err}")

    # Upload archive
    print("\n=== Uploading archive ===")
    with SCPClient(ssh.get_transport()) as scp:
        scp.put(ARCHIVE, "/opt/ifixit-parser.tar.gz")
    print("Archive uploaded!")

    # Extract and setup
    print("\n=== Setting up ===")
    commands = [
        "cd /opt && rm -rf ifixit-parser && tar -xzvf ifixit-parser.tar.gz",
        "apt update && apt install -y python3.10-venv python3-pip",
        "cd /opt/ifixit-parser && python3 -m venv venv",
        "cd /opt/ifixit-parser && . venv/bin/activate && pip install --upgrade pip && pip install -e .",
    ]

    for cmd in commands:
        print(f"Running: {cmd[:50]}...")
        stdin, stdout, stderr = ssh.exec_command(cmd)
        out = stdout.read().decode()
        err = stderr.read().decode()
        if out:
            print(out[-500:] if len(out) > 500 else out)
        if err and "warning" not in err.lower():
            print(f"Errors: {err[-300:]}")

    print("\n=== Done! ===")
    ssh.close()

if __name__ == "__main__":
    main()
