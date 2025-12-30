"""
Test web.max.ru QR login flow

web.max.ru shows QR code for login.
Flow hypothesis:
1. Connect WebSocket
2. Send HELLO
3. deviceId becomes QR token (encoded as max://login/{deviceId})
4. Mobile app scans QR and authorizes deviceId
5. WebSocket receives LOGIN notification

Let's test if deviceId-based polling works or if there's a push.
"""
import asyncio
import json
import uuid
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_web_qr_login():
    print("=" * 60)
    print("MAX Web QR Login Flow Test")
    print("=" * 60)

    # Generate unique device ID
    device_id = f"web-{uuid.uuid4().hex[:16]}"

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    print(f"\nDevice ID: {device_id}")
    print(f"\nPossible QR URLs:")
    print(f"  max://login/{device_id}")
    print(f"  max://auth/{device_id}")
    print(f"  https://max.ru/login/{device_id}")
    print()

    try:
        print("1. Connecting to MAX WebSocket...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # HELLO
            hello = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 6,
                "payload": {
                    "userAgent": {
                        "deviceType": "WEB",
                        "locale": "ru_RU",
                        "osVersion": "Windows 10",
                        "deviceName": "Chrome",
                        "appVersion": "25.12.1",
                        "screen": "1920x1080",
                        "timezone": "Europe/Moscow"
                    },
                    "deviceId": device_id
                }
            }

            print("\n2. Sending HELLO...")
            await ws.send(json.dumps(hello))
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            # Now wait for messages - if someone scans QR with mobile app
            # and authorizes this deviceId, we should receive LOGIN notification
            print("\n3. Waiting for QR scan authorization...")
            print("   (Scan QR with MAX mobile app to test)")
            print(f"\n   QR CODE VALUE: max://login/{device_id}")
            print()

            seq = 1
            try:
                for i in range(120):  # 2 minutes
                    try:
                        response = await asyncio.wait_for(ws.recv(), timeout=1)
                        data = json.loads(response)
                        opcode = data.get("opcode")
                        print(f"\n   [RECEIVED] Opcode {opcode}:")
                        print(f"   {json.dumps(data, indent=2, ensure_ascii=False)}")

                        # Check if it's a login notification
                        if opcode in [19, 23, 128]:  # LOGIN, AUTH_CONFIRM, NOTIF_MESSAGE
                            print("\n   [!!!] Possible auth notification!")

                    except asyncio.TimeoutError:
                        if i % 10 == 0:
                            print(f"   ... waiting ({120-i}s)")

                        # Send PING to keep connection alive
                        if i % 30 == 0 and i > 0:
                            seq += 1
                            ping = {
                                "ver": 11,
                                "cmd": 0,
                                "seq": seq,
                                "opcode": 1,
                                "payload": {}
                            }
                            await ws.send(json.dumps(ping))
                            print(f"   [PING sent]")

            except KeyboardInterrupt:
                print("\n   Stopped by user")

            print("\n4. Done")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_web_qr_login())
