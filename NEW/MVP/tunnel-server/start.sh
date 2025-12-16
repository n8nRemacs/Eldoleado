#!/bin/bash
# Tunnel Server Startup Script

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "=== Tunnel Server ==="

# Check .env
if [ ! -f .env ]; then
    echo "WARNING: .env not found. Copy .env.example to .env and configure."
    cp .env.example .env
fi

# Virtual environment
if [ ! -d "venv" ]; then
    echo "Creating virtual environment..."
    python3 -m venv venv
fi

source venv/bin/activate
pip install -q -r requirements.txt

# Start server
echo "Starting Tunnel Server on port ${PORT:-8800}..."
python main.py
