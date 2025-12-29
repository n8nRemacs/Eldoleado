"""
Test MAX HELLO response
"""
import asyncio
import json
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_hello():
    print("=" * 50)
    print("MAX HELLO Test")
    print("=" * 50)

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    try:
        print("\n1. Connecting...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # Send HELLO
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
                    "deviceId": "test-device-12345"
                }
            }

            print("\n2. Sending HELLO...")
            await ws.send(json.dumps(hello))

            print("\n3. Waiting for response...")
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            # Check location and phone-auth
            payload = data.get("payload", {})
            print(f"\n   Location: {payload.get('location')}")
            print(f"   Phone Auth Enabled: {payload.get('phone-auth-enabled')}")
            print(f"   Reg Countries: {payload.get('reg-country-code')}")

    except Exception as e:
        print(f"\n   [ERROR] {e}")

if __name__ == "__main__":
    asyncio.run(test_hello())
