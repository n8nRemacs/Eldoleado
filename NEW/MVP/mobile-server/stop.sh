#!/bin/bash
# Stop Mobile Server

echo "Stopping Mobile Server..."

# Kill Python processes
pkill -f "mobile-server/main.py" 2>/dev/null || true
pkill -f "channels/.*/app.py" 2>/dev/null || true

# Kill Node processes (WhatsApp)
pkill -f "channels/whatsapp/index.js" 2>/dev/null || true

echo "Done."
