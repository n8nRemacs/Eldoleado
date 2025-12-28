# GL.iNet Setup Guide for Avito Camoufox

Complete guide for setting up GL.iNet routers with WireGuard tunnels to route Avito traffic through real user IPs.

---

## Why GL.iNet?

**Problem:**
- Avito detects datacenter IPs and blocks suspicious activity
- Multiple accounts from same IP = high risk of blocking
- CAPTCHA frequency increases with datacenter IPs

**Solution:**
- GL.iNet router at each client location (service center)
- Each Avito account routes through real home/office IP
- Avito sees: "User from Moscow, Novosibirsk, Kazan" (different ISPs, different cities)

**Benefits:**
- ✅ Real user IP (not datacenter)
- ✅ Geographic distribution (different cities/ISPs)
- ✅ Lower CAPTCHA frequency
- ✅ Longer session lifetime (60 days vs 30)
- ✅ One-time cost (~4000₽) vs 6000₽/month Avito subscription

---

## Hardware Recommendations

| Model | Price | RAM | CPU | Use Case |
|-------|-------|-----|-----|----------|
| **GL-MT300N-V2** (Mango) | ~$20 | 128MB | 580MHz | Basic (1-2 accounts) |
| **GL-AR750S** (Slate) ⭐ | ~$60 | 128MB | 775MHz | **RECOMMENDED** (3-5 accounts) |
| **GL-AXT1800** (Slate AX) | ~$90 | 512MB | 1.2GHz | Large (10+ accounts) |
| **GL-MT3000** (Beryl AX) | ~$80 | 512MB | 1.3GHz | Alternative (5-10 accounts) |

**Recommended:** GL-AR750S — best price/quality ratio, reliable OpenWRT, dual-band WiFi.

---

## Network Architecture

```
┌─────────────────────────────────────────────────────┐
│  Service Center (Moscow)                            │
│  ├── Internet (ISP: Rostelecom)                    │
│  │   └── Public IP: 95.123.45.67                   │
│  ├── GL.iNet Router (GL-AR750S)                    │
│  │   ├── LAN: 192.168.8.1/24                       │
│  │   ├── WireGuard Server: wg0 (10.8.0.1/24)      │
│  │   └── Port: 51820 (UDP)                         │
│  └── DDNS: moskva.glddns.com → 95.123.45.67       │
└─────────────────────────────────────────────────────┘
                    ↑ WireGuard tunnel
┌─────────────────────────────────────────────────────┐
│  MCP Server (155.212.221.189)                       │
│  ├── WireGuard Client: wg-moskva                   │
│  │   └── Tunnel IP: 10.8.0.2/24                    │
│  ├── Camoufox Session (account_moskva_1)           │
│  │   └── Proxy: socks5://10.8.0.1:1080            │
│  └── Avito sees: IP 95.123.45.67 (Moscow)          │
└─────────────────────────────────────────────────────┘
```

---

## Part 1: GL.iNet Router Setup

### 1.1 Initial Setup

```bash
# Connect GL.iNet to power and client's internet (WiFi or Ethernet)
# Router creates WiFi: GL-AR750S-XXX

# Connect to router WiFi from laptop
# Open browser: http://192.168.8.1

# First time setup:
# - Set admin password (save to password manager!)
# - Select internet connection:
#   - WiFi (scan and connect to client's WiFi)
#   - OR Ethernet (plug cable to WAN port)
# - Wait for internet connection (LED turns white)
```

### 1.2 Enable WireGuard Server (GUI Method)

```
1. Login to GL.iNet admin panel: http://192.168.8.1
2. Applications → WireGuard Server
3. Click "Enable WireGuard Server"
4. Configuration:
   - Listen Port: 51820
   - Local IP: 10.8.0.0/24
   - Start IP Pool: 10.8.0.2
   - End IP Pool: 10.8.0.254
5. Click "Apply"

6. Add Client:
   - Click "Add Client"
   - Name: "mcp-server"
   - Click "Add"
   - Click "Download" → save config as "wg-moskva.conf"
```

**Result:** You now have `wg-moskva.conf` file with WireGuard config.

### 1.3 Setup Dynamic DNS (DDNS)

**Why?** Client's IP may change (dynamic IP from ISP).

```
Method 1: GL.iNet DDNS (easiest)
1. Applications → Dynamic DNS
2. Enable DDNS
3. Choose subdomain: moskva.glddns.com
4. Click "Apply"

Method 2: Cloudflare DDNS (more reliable)
1. SSH to router:
   ssh root@192.168.8.1
2. Install ddns-scripts:
   opkg update
   opkg install ddns-scripts ddns-scripts-cloudflare
3. Configure /etc/config/ddns:
   config service 'cloudflare'
       option enabled '1'
       option service_name 'cloudflare.com-v4'
       option domain 'moskva.example.com'
       option username 'your-cloudflare-email'
       option password 'cloudflare-api-token'
       option lookup_host 'moskva.example.com'
       option use_https '1'
4. Restart:
   /etc/init.d/ddns restart
```

### 1.4 Port Forwarding (if needed)

**Check if needed:**
```bash
# From external network (e.g., your phone via mobile data)
nc -zvu ROUTER_PUBLIC_IP 51820

# If fails → port forwarding needed
```

**Enable port forwarding:**
```
Router ISP Settings:
1. Login to ISP router (e.g., 192.168.1.1)
2. Find "Port Forwarding" or "Virtual Server"
3. Add rule:
   - External Port: 51820 (UDP)
   - Internal IP: 192.168.8.1 (GL.iNet LAN IP)
   - Internal Port: 51820 (UDP)
   - Protocol: UDP
4. Save and reboot ISP router
```

**Alternative (if ISP blocks incoming ports):**
Use reverse tunnel (see Part 4).

### 1.5 Install SOCKS5 Proxy (Optional, Advanced)

**Why?** Allows Camoufox to use GL.iNet as HTTP/SOCKS5 proxy instead of routing all traffic.

```bash
# SSH to GL.iNet
ssh root@192.168.8.1

# Install Dante SOCKS5 server
opkg update
opkg install dante-server

# Create config: /etc/sockd.conf
cat > /etc/sockd.conf <<'EOF'
logoutput: syslog
internal: br-lan port = 1080
external: wg0

socksmethod: username none
clientmethod: none

client pass {
    from: 10.8.0.0/24 to: 0.0.0.0/0
}

socks pass {
    from: 10.8.0.0/24 to: 0.0.0.0/0
    protocol: tcp udp
}
EOF

# Enable and start
/etc/init.d/sockd enable
/etc/init.d/sockd start

# Test
sockd -V  # Should show version
```

**Result:** SOCKS5 proxy running on `10.8.0.1:1080` (accessible via WireGuard tunnel).

---

## Part 2: MCP Server Setup

### 2.1 Install WireGuard

```bash
# SSH to MCP Server
ssh root@155.212.221.189

# Install WireGuard
apt update
apt install -y wireguard wireguard-tools

# Verify
wg version
```

### 2.2 Configure WireGuard Client

```bash
# Copy config from GL.iNet
# Upload wg-moskva.conf to server (via scp, or paste content)

# Place config
mkdir -p /etc/wireguard
nano /etc/wireguard/wg-moskva.conf

# Paste content from GL.iNet (downloaded config):
# [Interface]
# PrivateKey = xxx
# Address = 10.8.0.2/24
# DNS = 1.1.1.1
#
# [Peer]
# PublicKey = yyy
# Endpoint = moskva.glddns.com:51820
# AllowedIPs = 0.0.0.0/0
# PersistentKeepalive = 25

# IMPORTANT: Edit AllowedIPs
# Change: AllowedIPs = 0.0.0.0/0
# To:     AllowedIPs = 10.8.0.0/24
# (Only route 10.8.x.x through tunnel, not all traffic)

# Start tunnel
wg-quick up wg-moskva

# Test connectivity
ping 10.8.0.1  # Should respond (GL.iNet)

# Enable auto-start on boot
systemctl enable wg-quick@wg-moskva
```

### 2.3 Multiple Tunnels (for multiple cities)

```bash
# Repeat for each GL.iNet router

# Novosibirsk
nano /etc/wireguard/wg-novosibirsk.conf
wg-quick up wg-novosibirsk
systemctl enable wg-quick@wg-novosibirsk

# Kazan
nano /etc/wireguard/wg-kazan.conf
wg-quick up wg-kazan
systemctl enable wg-quick@wg-kazan

# Check all tunnels
wg show
ip addr show | grep wg
```

---

## Part 3: Camoufox Integration

### 3.1 Database Schema

```sql
-- Add proxy_url to elo_t_channel_accounts
ALTER TABLE elo_t_channel_accounts
ADD COLUMN proxy_url TEXT;

-- Example data
UPDATE elo_t_channel_accounts
SET proxy_url = 'socks5://10.8.0.1:1080'
WHERE id = 'account_moskva_1';

UPDATE elo_t_channel_accounts
SET proxy_url = 'socks5://10.9.0.1:1080'
WHERE id = 'account_novosibirsk_1';
```

### 3.2 Update server.py

```python
# NEW/MVP/MCP/mcp-avito-camoufox/server.py

import asyncpg
from pathlib import Path

# Database connection
async def get_proxy_url(account_id: str) -> str:
    """Get proxy URL from database"""
    conn = await asyncpg.connect(
        host="185.221.214.83",
        port=6544,
        user="supabase_admin",
        password="...",
        database="postgres"
    )

    result = await conn.fetchrow("""
        SELECT proxy_url
        FROM elo_t_channel_accounts
        WHERE id = $1
    """, account_id)

    await conn.close()

    return result['proxy_url'] if result else None

# Modify create_session
@app.post("/account/{account_id}/create")
async def create_account(account_id: str):
    data_dir = DATA_DIR / account_id
    data_dir.mkdir(parents=True, exist_ok=True)

    # Get proxy from database
    proxy_url = await get_proxy_url(account_id)

    # Create session with proxy
    session = AvitoChannel(
        account_id=account_id,
        data_dir=data_dir,
        proxy_url=proxy_url  # ← Pass proxy
    )

    SESSIONS[account_id] = session
    return {"status": "created", "proxy": proxy_url}
```

### 3.3 Update avito_channel.py

```python
# NEW/MVP/MCP/mcp-avito-camoufox/avito_channel.py

from camoufox.sync_api import Camoufox

class AvitoChannel:
    def __init__(self, account_id: str, data_dir: Path, proxy_url: str = None):
        self.account_id = account_id
        self.data_dir = data_dir
        self.proxy_url = proxy_url

        # Load fingerprint
        fingerprint_file = data_dir / "fingerprint.json"
        if fingerprint_file.exists():
            with open(fingerprint_file) as f:
                self.fingerprint = json.load(f)
        else:
            self.fingerprint = None  # Will generate new

        # Camoufox options
        options = {
            "profile_path": str(data_dir / "profile"),
            "fingerprint": self.fingerprint,
            "headless": True
        }

        # Add proxy if available
        if self.proxy_url:
            options["proxy"] = self.proxy_url
            print(f"[{self.account_id}] Using proxy: {self.proxy_url}")

        self.browser = Camoufox(**options)
        self.page = self.browser.new_page()

        # Save fingerprint after first launch
        if not fingerprint_file.exists():
            with open(fingerprint_file, 'w') as f:
                json.dump(self.browser.fingerprint, f, indent=2)
```

---

## Part 4: Reverse Tunnel (Alternative for blocked ports)

**Use case:** ISP blocks incoming UDP 51820.

**Solution:** MCP Server acts as WireGuard server, GL.iNet connects to it.

### 4.1 MCP Server as WireGuard Server

```bash
# Generate keys
wg genkey | tee /etc/wireguard/server_private.key | wg pubkey > /etc/wireguard/server_public.key

# Create server config
nano /etc/wireguard/wg-server.conf

# Content:
[Interface]
PrivateKey = <server_private.key>
Address = 10.100.0.1/24
ListenPort = 51820

# Peer: GL.iNet Moskva
[Peer]
PublicKey = <glinet_moskva_public_key>
AllowedIPs = 10.100.0.10/32
PersistentKeepalive = 25

# Start
wg-quick up wg-server
systemctl enable wg-quick@wg-server
```

### 4.2 GL.iNet as WireGuard Client

```bash
# SSH to GL.iNet
ssh root@192.168.8.1

# Create client config
nano /etc/wireguard/wg-reverse.conf

# Content:
[Interface]
PrivateKey = <glinet_private_key>
Address = 10.100.0.10/24

[Peer]
PublicKey = <mcp_server_public_key>
Endpoint = 155.212.221.189:51820
AllowedIPs = 10.100.0.0/24
PersistentKeepalive = 25

# Start
wg-quick up wg-reverse
/etc/init.d/wireguard enable
```

### 4.3 Update Camoufox Proxy

```sql
-- Update proxy to reverse tunnel IP
UPDATE elo_t_channel_accounts
SET proxy_url = 'socks5://10.100.0.10:1080'
WHERE id = 'account_moskva_1';
```

---

## Part 5: Monitoring & Maintenance

### 5.1 Health Check Script

```python
# NEW/MVP/MCP/mcp-avito-camoufox/health_check.py

import asyncio
import subprocess

async def check_tunnel(tunnel_name: str, tunnel_ip: str):
    """Check if WireGuard tunnel is up"""
    try:
        # Ping tunnel endpoint
        proc = await asyncio.create_subprocess_exec(
            "ping", "-c", "1", "-W", "2", tunnel_ip,
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL
        )
        await proc.wait()

        if proc.returncode == 0:
            print(f"✅ {tunnel_name}: UP ({tunnel_ip})")
            return True
        else:
            print(f"❌ {tunnel_name}: DOWN ({tunnel_ip})")
            return False
    except Exception as e:
        print(f"❌ {tunnel_name}: ERROR - {e}")
        return False

async def main():
    tunnels = {
        "moskva": "10.8.0.1",
        "novosibirsk": "10.9.0.1",
        "kazan": "10.10.0.1"
    }

    results = await asyncio.gather(*[
        check_tunnel(name, ip)
        for name, ip in tunnels.items()
    ])

    if not all(results):
        # Send alert (webhook to n8n)
        print("⚠️ Some tunnels are down!")

if __name__ == "__main__":
    asyncio.run(main())
```

### 5.2 Cron Job

```bash
# Add to crontab
crontab -e

# Check tunnels every 5 minutes
*/5 * * * * /usr/bin/python3 /root/mcp-avito-camoufox/health_check.py >> /var/log/tunnel-health.log 2>&1
```

### 5.3 GL.iNet Monitoring (from client side)

```bash
# SSH to GL.iNet
ssh root@192.168.8.1

# Check WireGuard status
wg show

# Check connected clients
wg show wg0 latest-handshakes

# Check SOCKS5 proxy
netstat -tulnp | grep 1080

# Check internet connectivity
ping -c 3 8.8.8.8
```

---

## Part 6: Troubleshooting

### 6.1 Tunnel doesn't connect

**Symptoms:** `ping 10.8.0.1` times out.

**Check:**
```bash
# On MCP Server
wg show wg-moskva
# Look for "latest handshake" - should be recent (<1 min)

# If handshake is old:
# 1. Check GL.iNet is online (ping DDNS)
ping moskva.glddns.com

# 2. Check port 51820 is open
nc -zvu moskva.glddns.com 51820

# 3. Restart tunnel
wg-quick down wg-moskva
wg-quick up wg-moskva
```

### 6.2 Proxy connection fails

**Symptoms:** Camoufox fails with "ERR_PROXY_CONNECTION_FAILED".

**Check:**
```bash
# Test SOCKS5 from MCP Server
curl -x socks5://10.8.0.1:1080 https://ifconfig.me
# Should return GL.iNet public IP (95.123.45.67)

# If fails:
# 1. Check dante-server on GL.iNet
ssh root@192.168.8.1 "/etc/init.d/sockd status"

# 2. Check firewall
ssh root@192.168.8.1 "iptables -L -n | grep 1080"
```

### 6.3 Slow connection

**Symptoms:** Pages load slowly through tunnel.

**Check:**
```bash
# Test latency
ping -c 10 10.8.0.1
# Should be <50ms for same country

# Test bandwidth
iperf3 -s  # On GL.iNet
iperf3 -c 10.8.0.1  # On MCP Server
# Should get at least 5 Mbps (enough for Avito)

# If slow:
# 1. Check client's internet speed (ask them to run speedtest.net)
# 2. Check GL.iNet CPU usage (might be overloaded)
ssh root@192.168.8.1 "top -n 1"
```

### 6.4 GL.iNet reboots

**Symptoms:** Tunnel drops, then reconnects after 2-3 minutes.

**Cause:** Client rebooted router, or power outage.

**Solution:**
```bash
# Auto-reconnect is built-in to WireGuard (PersistentKeepalive)
# No action needed - tunnel will restore automatically

# To monitor reboots, add uptime logging:
# On GL.iNet, add to /etc/rc.local:
logger "GL.iNet booted at $(date)"
```

---

## Part 7: Deployment Checklist

### For each new GL.iNet router:

**Hardware:**
- [ ] Purchase GL-AR750S router (~4000₽)
- [ ] Receive router and verify it powers on

**Client Side:**
- [ ] Connect router to client's internet (WiFi or Ethernet)
- [ ] Access admin panel (192.168.8.1)
- [ ] Set admin password
- [ ] Enable WireGuard Server
- [ ] Download client config (wg-{city}.conf)
- [ ] Setup DDNS (e.g., {city}.glddns.com)
- [ ] Test port 51820 is accessible from outside
- [ ] (Optional) Install SOCKS5 proxy (dante-server)

**MCP Server Side:**
- [ ] Upload WireGuard config to /etc/wireguard/
- [ ] Edit AllowedIPs to 10.x.0.0/24
- [ ] Start tunnel: `wg-quick up wg-{city}`
- [ ] Test connectivity: `ping 10.x.0.1`
- [ ] Enable auto-start: `systemctl enable wg-quick@wg-{city}`
- [ ] Add account to database with proxy_url

**Testing:**
- [ ] Create Avito account in Camoufox
- [ ] Verify IP via: `curl -x socks5://10.x.0.1:1080 https://ifconfig.me`
- [ ] Login to Avito
- [ ] Check "My Location" in Avito profile (should show client's city)
- [ ] Send test message
- [ ] Monitor session for 24 hours (check health_check logs)

---

## Part 8: Cost Analysis

### Setup Costs (per location)

| Item | Cost (one-time) |
|------|-----------------|
| GL-AR750S Router | 4000₽ |
| Shipping | 500₽ |
| **Total** | **4500₽** |

### Operational Costs

| Item | Cost (monthly) |
|------|----------------|
| Client's internet | 0₽ (client already has) |
| DDNS | 0₽ (free with GL.iNet or Cloudflare) |
| WireGuard | 0₽ (open source) |
| **Total** | **0₽** |

### ROI Comparison

| Solution | Setup | Monthly | Year 1 Total |
|----------|-------|---------|--------------|
| **GL.iNet** | 4500₽ | 0₽ | **4500₽** |
| Avito Premium | 0₽ | 6000₽ | **72000₽** |
| VPN Service | 0₽ | 500₽/IP | **6000₽** (risky, shared IP) |

**Savings:** 67500₽ in Year 1 vs Avito Premium.

---

## Summary

**GL.iNet Benefits:**
- ✅ Real user IP (home/office)
- ✅ Geographic distribution (different cities)
- ✅ Lower CAPTCHA rate (home IP trusted by Avito)
- ✅ Longer session lifetime (60 days vs 30)
- ✅ One-time cost (4500₽ vs 72000₽/year)
- ✅ No monthly fees

**Best For:**
- Small service centers (<20 Avito dialogs/day)
- Multi-location businesses (franchise, chain)
- Anyone wanting to save 6000₽/month on Avito subscription

**Next Steps:**
1. Order 1 GL-AR750S for pilot
2. Install at first client location
3. Test with 2-3 Avito accounts
4. Collect uptime/reliability stats for 1 week
5. If successful → scale to 5-10 locations

---

*Last updated: 2025-12-28*
