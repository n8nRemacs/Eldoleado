"""
Test QR auth flow - try different opcodes after HELLO
"""
import asyncio
import json
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_qr_flow():
    print("=" * 60)
    print("MAX QR Flow Test - Trying AUTH opcodes after HELLO")
    print("=" * 60)

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    device_id = f"web-qr-test-{int(asyncio.get_event_loop().time())}"

    try:
        print(f"\n1. Connecting with deviceId: {device_id}")
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
            print(f"   HELLO response: location={data.get('payload', {}).get('location')}")

            # Try opcode 112 (AUTH_CREATE_TRACK)
            print("\n3. Trying AUTH_CREATE_TRACK (opcode 112)...")
            create_track = {
                "ver": 11,
                "cmd": 0,
                "seq": 2,
                "opcode": 112,
                "payload": {}
            }
            await ws.send(json.dumps(create_track))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT] No response")

            # Try opcode 23 (AUTH_CONFIRM) - maybe for QR confirmation
            print("\n4. Trying AUTH_CONFIRM (opcode 23)...")
            auth_confirm = {
                "ver": 11,
                "cmd": 0,
                "seq": 3,
                "opcode": 23,
                "payload": {}
            }
            await ws.send(json.dumps(auth_confirm))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT] No response")

            # Try with deviceId as trackId
            print("\n5. Trying AUTH_CONFIRM with deviceId as trackId...")
            auth_confirm2 = {
                "ver": 11,
                "cmd": 0,
                "seq": 4,
                "opcode": 23,
                "payload": {
                    "trackId": device_id
                }
            }
            await ws.send(json.dumps(auth_confirm2))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT] No response")

            # Try opcode 19 (LOGIN) with empty payload
            print("\n6. Trying LOGIN (opcode 19) with empty payload...")
            login = {
                "ver": 11,
                "cmd": 0,
                "seq": 5,
                "opcode": 19,
                "payload": {}
            }
            await ws.send(json.dumps(login))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT] No response")

            print("\n7. Waiting for any additional messages...")
            for _ in range(3):
                try:
                    response = await asyncio.wait_for(ws.recv(), timeout=2)
                    data = json.loads(response)
                    print(f"   Message (opcode={data.get('opcode')}): {json.dumps(data, ensure_ascii=False)}")
                except asyncio.TimeoutError:
                    break

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_qr_flow())
