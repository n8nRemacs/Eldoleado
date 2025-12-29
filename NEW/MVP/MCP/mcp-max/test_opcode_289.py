"""
Test opcode 289 - QR Auth for web.max.ru

Flow discovered:
1. Send HELLO (opcode 6)
2. Send opcode 289 with trackId (UUID)
3. Server responds with status.expiresAt
4. Poll opcode 289 every 5s until auth confirmed
"""
import asyncio
import json
import uuid
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_qr_auth_289():
    print("=" * 60)
    print("MAX QR Auth Test - Opcode 289")
    print("=" * 60)

    device_id = str(uuid.uuid4())
    track_id = str(uuid.uuid4())

    print(f"\nDevice ID: {device_id}")
    print(f"Track ID:  {track_id}")
    print(f"\nQR URL: https://max.ru/:auth/{track_id}")

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

            print("\n2. Sending HELLO...")
            await ws.send(json.dumps(hello))
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            # QR Auth request (opcode 289)
            print("\n3. Sending QR Auth request (opcode 289)...")
            qr_auth = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 289,
                "payload": {
                    "trackId": track_id
                }
            }
            await ws.send(json.dumps(qr_auth))

            try:
                response = await asyncio.wait_for(ws.recv(), timeout=10)
                data = json.loads(response)
                print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

                payload = data.get("payload", {})
                status = payload.get("status", {})
                expires_at = status.get("expiresAt")

                if expires_at:
                    import datetime
                    exp_time = datetime.datetime.fromtimestamp(expires_at / 1000)
                    print(f"\n   [OK] QR Auth track created!")
                    print(f"   Expires at: {exp_time}")
                    print(f"\n   QR CODE URL: https://max.ru/:auth/{track_id}")
                    print(f"\n   Scan with MAX mobile app to authenticate!")

                    # Poll for auth status
                    print("\n4. Polling for auth (scan QR now)...")
                    seq = 2
                    for i in range(60):  # 1 minute
                        await asyncio.sleep(5)
                        seq += 1
                        poll = {
                            "ver": 11,
                            "cmd": 0,
                            "seq": seq,
                            "opcode": 289,
                            "payload": {
                                "trackId": track_id
                            }
                        }
                        await ws.send(json.dumps(poll))
                        response = await asyncio.wait_for(ws.recv(), timeout=10)
                        data = json.loads(response)

                        payload = data.get("payload", {})
                        status = payload.get("status", {})

                        # Check for login token or profile
                        if "loginToken" in payload or "profile" in payload:
                            print(f"\n   [SUCCESS] Authenticated!")
                            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
                            break

                        if "error" in payload:
                            print(f"\n   [ERROR] {payload.get('error')}")
                            break

                        print(f"   ... polling ({60-i*5}s) - status: {status}")

                elif "error" in payload:
                    print(f"\n   [ERROR] {payload.get('error')}: {payload.get('message')}")
                else:
                    print(f"\n   Unknown response")

            except asyncio.TimeoutError:
                print("   [TIMEOUT] No response")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    asyncio.run(test_qr_auth_289())
