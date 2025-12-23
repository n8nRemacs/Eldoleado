#!/bin/bash
# Deploy avito-listener to server
# Usage: ./deploy.sh

SERVER="155.212.221.189"
SERVICE_DIR="/opt/avito-listener"

echo "=== Deploying avito-listener to $SERVER ==="

# Create directory and copy files
ssh root@$SERVER "mkdir -p $SERVICE_DIR"
scp avito_ws_listener.py requirements.txt root@$SERVER:$SERVICE_DIR/

# Create .env file on server
ssh root@$SERVER "cat > $SERVICE_DIR/.env << 'EOF'
N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/avito/incoming
POSTGRES_URL=postgresql://supabase_admin:Eldoleado2024!@185.221.214.83:6544/postgres
PORT=8769
EOF"

# Install dependencies and create systemd service
ssh root@$SERVER << 'ENDSSH'
cd /opt/avito-listener

# Install Python venv if needed
apt-get update && apt-get install -y python3-venv python3-pip

# Create venv and install deps
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt

# Create systemd service
cat > /etc/systemd/system/avito-listener.service << 'EOF'
[Unit]
Description=Avito WebSocket Listener
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=/opt/avito-listener
Environment=PATH=/opt/avito-listener/venv/bin
ExecStart=/opt/avito-listener/venv/bin/python avito_ws_listener.py
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

# Enable and start service
systemctl daemon-reload
systemctl enable avito-listener
systemctl restart avito-listener
systemctl status avito-listener
ENDSSH

echo "=== Done! Check: curl http://$SERVER:8769/health ==="
