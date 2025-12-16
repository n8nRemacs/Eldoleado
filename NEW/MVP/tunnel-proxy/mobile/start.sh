#!/bin/bash
# Start Mobile Tunnel Proxy (run on phone/Termux)

cd "$(dirname "$0")"

# Install dependencies if needed
if [ ! -d "venv" ]; then
    echo "Creating virtual environment..."
    python3 -m venv venv
    source venv/bin/activate
    pip install -r requirements.txt
else
    source venv/bin/activate
fi

# Load env if exists
[ -f .env ] && export $(grep -v '^#' .env | xargs)

# Default values
export WS_PORT=${WS_PORT:-8765}
export TUNNEL_SECRET=${TUNNEL_SECRET:-change_me_in_production}

echo "Starting Mobile Tunnel Proxy on port $WS_PORT"
python tunnel.py
