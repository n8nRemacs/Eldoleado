"""
Test MAX HELLO response in detail - look for session token for QR
"""
import asyncio
import json
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_hello_detailed():
    print("=" * 60)
    print("MAX HELLO Detailed Test - Looking for QR session data")
    print("=" * 60)

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    try:
        print("\n1. Connecting to MAX WebSocket...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # Send HELLO with WEB deviceType
            hello = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 6,  # SESSION_INIT
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
                    "deviceId": "test-device-qr-" + str(asyncio.get_event_loop().time())
                }
            }

            print("\n2. Sending HELLO (WEB)...")
            print(f"   Request: {json.dumps(hello, indent=2)}")
            await ws.send(json.dumps(hello))

            print("\n3. Waiting for response...")
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)

            print("\n4. FULL RESPONSE:")
            print(json.dumps(data, indent=2, ensure_ascii=False))

            # Analyze response
            print("\n5. KEY FIELDS:")
            payload = data.get("payload", {})
            for key, value in payload.items():
                print(f"   {key}: {value}")

            # Look for session/token fields
            print("\n6. POSSIBLE QR/SESSION FIELDS:")
            for key in ["sessionId", "session", "token", "trackId", "qr", "authToken", "guestToken"]:
                if key in payload:
                    print(f"   [FOUND] {key}: {payload[key]}")
                elif key in data:
                    print(f"   [FOUND in root] {key}: {data[key]}")

            # Check if there's session in response headers
            print("\n7. ROOT LEVEL FIELDS:")
            for key, value in data.items():
                if key != "payload":
                    print(f"   {key}: {value}")

            # Wait for more messages
            print("\n8. Waiting for additional messages (5s)...")
            try:
                while True:
                    response2 = await asyncio.wait_for(ws.recv(), timeout=5)
                    data2 = json.loads(response2)
                    print(f"\n   Additional message (opcode={data2.get('opcode')}):")
                    print(f"   {json.dumps(data2, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   No more messages")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_hello_detailed())
