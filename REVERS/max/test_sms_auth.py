"""
Test MAX SMS Auth
Run: python test_sms_auth.py +79001234567
"""
import asyncio
import json
import sys
import uuid
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_sms_auth(phone: str):
    print("=" * 60)
    print(f"MAX SMS Auth Test: {phone}")
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
            payload = data.get("payload", {})
            location = payload.get("location")
            phone_auth = payload.get("phone-auth-enabled")

            print(f"   Location: {location}")
            print(f"   Phone Auth Enabled: {phone_auth}")

            if not phone_auth:
                print(f"\n   [FAIL] SMS auth disabled for location={location}")
                print("   Try from residential IP in Russia")
                return

            # AUTH_REQUEST (opcode 17)
            print(f"\n3. Sending AUTH_REQUEST for {phone}...")
            auth_req = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 17,
                "payload": {
                    "phone": phone,
                    "language": "ru"
                }
            }
            await ws.send(json.dumps(auth_req))

            response = await asyncio.wait_for(ws.recv(), timeout=15)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            payload = data.get("payload", {})
            token = payload.get("token")

            if token:
                print(f"\n   [OK] SMS sent!")
                print(f"   Token: {token[:30]}...")

                # Wait for SMS code
                code = input("\n   Enter SMS code: ").strip()

                if code:
                    # AUTH (opcode 18) - verify code
                    print(f"\n4. Verifying code {code}...")
                    auth = {
                        "ver": 11,
                        "cmd": 0,
                        "seq": 2,
                        "opcode": 18,
                        "payload": {
                            "token": token,
                            "code": code
                        }
                    }
                    await ws.send(json.dumps(auth))

                    response = await asyncio.wait_for(ws.recv(), timeout=15)
                    data = json.loads(response)
                    print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

                    payload = data.get("payload", {})
                    if "loginToken" in payload:
                        print(f"\n   [SUCCESS] Authenticated!")
                        print(f"   Login Token: {payload['loginToken'][:50]}...")
                        if "profile" in payload:
                            print(f"   Profile: {json.dumps(payload['profile'], indent=2, ensure_ascii=False)}")
                    elif "error" in payload:
                        print(f"\n   [FAIL] {payload.get('error')}: {payload.get('message')}")

            elif "error" in payload:
                print(f"\n   [FAIL] {payload.get('error')}: {payload.get('message')}")
            else:
                print(f"\n   Unknown response")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python test_sms_auth.py +79001234567")
        sys.exit(1)

    phone = sys.argv[1]
    asyncio.run(test_sms_auth(phone))
