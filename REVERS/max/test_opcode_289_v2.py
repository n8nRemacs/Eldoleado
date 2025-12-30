"""
Test opcode 289 - try without trackId to create new track
"""
import asyncio
import json
import uuid
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_qr_auth_create():
    print("=" * 60)
    print("MAX QR Auth - Create Track Test")
    print("=" * 60)

    device_id = str(uuid.uuid4())

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    try:
        print("\n1. Connecting...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # HELLO
            hello = {
                "ver": 11,
                "cmd": 0,
                "seq": 0,
                "opcode": 6,
                "payload": {
                    "userAgent": {
                        "deviceType": "WEB",
                        "locale": "ru",
                        "deviceLocale": "ru",
                        "osVersion": "Windows",
                        "deviceName": "Chrome",
                        "appVersion": "25.12.14",
                        "screen": "1920x1080",
                        "timezone": "Europe/Moscow"
                    },
                    "deviceId": device_id
                }
            }

            print(f"\n2. Sending HELLO (deviceId={device_id[:8]}...)...")
            await ws.send(json.dumps(hello))
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   location={data.get('payload', {}).get('location')}")

            # Try opcode 289 WITHOUT trackId
            print("\n3. Trying opcode 289 WITHOUT trackId...")
            qr_create = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 289,
                "payload": {}
            }
            await ws.send(json.dumps(qr_create))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT]")

            # Try opcode 289 with empty trackId
            print("\n4. Trying opcode 289 with trackId=null...")
            qr_create2 = {
                "ver": 11,
                "cmd": 0,
                "seq": 2,
                "opcode": 289,
                "payload": {
                    "trackId": None
                }
            }
            await ws.send(json.dumps(qr_create2))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT]")

            # Try opcode 289 with action="create"
            print("\n5. Trying opcode 289 with action=create...")
            qr_create3 = {
                "ver": 11,
                "cmd": 0,
                "seq": 3,
                "opcode": 289,
                "payload": {
                    "action": "create"
                }
            }
            await ws.send(json.dumps(qr_create3))
            try:
                response = await asyncio.wait_for(ws.recv(), timeout=5)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except asyncio.TimeoutError:
                print("   [TIMEOUT]")

            # Try different opcodes around 289
            for op in [288, 290, 291, 287]:
                print(f"\n6. Trying opcode {op}...")
                test = {
                    "ver": 11,
                    "cmd": 0,
                    "seq": 4 + (op - 287),
                    "opcode": op,
                    "payload": {}
                }
                await ws.send(json.dumps(test))
                try:
                    response = await asyncio.wait_for(ws.recv(), timeout=3)
                    data = json.loads(response)
                    print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
                except asyncio.TimeoutError:
                    print("   [TIMEOUT]")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_qr_auth_create())
