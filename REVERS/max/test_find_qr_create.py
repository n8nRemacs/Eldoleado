"""
Find the opcode that creates QR auth track
"""
import asyncio
import json
import uuid
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_opcodes():
    print("=" * 60)
    print("Finding QR Create Opcode")
    print("=" * 60)

    device_id = str(uuid.uuid4())

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }

    # Opcodes to try (around 289 and auth-related)
    opcodes_to_try = [
        # Around 289
        285, 286, 287, 288, 290, 291, 292, 293, 294, 295,
        # High range web-only
        280, 281, 282, 283, 284, 296, 297, 298, 299, 300,
        # Possible QR/web auth
        150, 151, 152, 153, 154, 155, 160, 161, 162,
        # Very high
        310, 320, 330, 340, 350
    ]

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
                        "osVersion": "Windows",
                        "deviceName": "Chrome",
                        "appVersion": "25.12.14",
                        "screen": "1920x1080",
                        "timezone": "Europe/Moscow"
                    },
                    "deviceId": device_id
                }
            }

            await ws.send(json.dumps(hello))
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   HELLO: location={data.get('payload', {}).get('location')}")

            seq = 0
            for op in opcodes_to_try:
                seq += 1
                print(f"\n   Testing opcode {op}...", end=" ")

                test = {
                    "ver": 11,
                    "cmd": 0,
                    "seq": seq,
                    "opcode": op,
                    "payload": {}
                }

                try:
                    await ws.send(json.dumps(test))
                    response = await asyncio.wait_for(ws.recv(), timeout=2)
                    data = json.loads(response)

                    payload = data.get("payload", {})
                    error = payload.get("error", "")

                    if "trackId" in str(payload) or "track" in str(payload).lower():
                        print(f"[!!!] TRACK FOUND!")
                        print(f"       {json.dumps(data, indent=2, ensure_ascii=False)}")
                    elif "expiresAt" in str(payload):
                        print(f"[!!!] EXPIRES FOUND!")
                        print(f"       {json.dumps(data, indent=2, ensure_ascii=False)}")
                    elif error:
                        print(f"Error: {error}")
                    else:
                        print(f"OK: {str(payload)[:60]}")

                except asyncio.TimeoutError:
                    print("timeout")
                except websockets.exceptions.ConnectionClosed as e:
                    print(f"connection closed: {e}")
                    # Reconnect
                    ws = await websockets.connect(WS_URL, additional_headers=headers)
                    await ws.send(json.dumps(hello))
                    await asyncio.wait_for(ws.recv(), timeout=10)
                    print("   (reconnected)")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_opcodes())
