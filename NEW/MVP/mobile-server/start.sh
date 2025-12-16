#!/bin/bash
# Mobile Server Startup Script
# For Termux or Linux

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "=== Mobile Server ==="
echo "Starting all services..."

# Create logs directory
mkdir -p logs

# Check if .env exists
if [ ! -f .env ]; then
    echo "WARNING: .env not found. Copy .env.example to .env and configure."
    cp .env.example .env
fi

# Load environment
source .env

# Install Python dependencies if needed
if [ ! -d "venv" ]; then
    echo "Creating Python virtual environment..."
    python3 -m venv venv
fi

source venv/bin/activate
pip install -q -r requirements.txt

# Start main server
echo "Starting main server..."
python main.py
