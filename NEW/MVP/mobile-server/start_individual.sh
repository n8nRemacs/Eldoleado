#!/bin/bash
# Start individual channel services manually
# Useful for debugging or running specific channels

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

source .env 2>/dev/null || true

start_channel() {
    local name=$1
    local port=$2
    local type=$3

    echo "Starting $name on port $port..."

    if [ "$type" = "node" ]; then
        cd "$SCRIPT_DIR/channels/$name"
        PORT=$port node index.js &
    else
        cd "$SCRIPT_DIR/channels/$name"
        PORT=$port python -m uvicorn app:app --host 0.0.0.0 --port $port &
    fi
}

# Usage: ./start_individual.sh [channel_name]
# Or start all individually

case "${1:-all}" in
    whatsapp)
        start_channel whatsapp 3001 node
        ;;
    telegram)
        start_channel telegram 3002 python
        ;;
    avito)
        start_channel avito 3003 python
        ;;
    max)
        start_channel max 3004 python
        ;;
    vk)
        start_channel vk 3005 python
        ;;
    http_proxy)
        start_channel http_proxy 3010 python
        ;;
    tunnel)
        cd "$SCRIPT_DIR/tunnel_proxy"
        python proxy.py &
        ;;
    all)
        echo "Starting all channels..."
        start_channel whatsapp 3001 node
        sleep 2
        start_channel telegram 3002 python
        start_channel avito 3003 python
        start_channel max 3004 python
        start_channel vk 3005 python
        start_channel http_proxy 3010 python
        sleep 2
        cd "$SCRIPT_DIR/tunnel_proxy"
        python proxy.py
        ;;
    *)
        echo "Usage: $0 [whatsapp|telegram|avito|max|vk|http_proxy|tunnel|all]"
        exit 1
        ;;
esac

echo "Started. Use stop.sh to stop all services."
