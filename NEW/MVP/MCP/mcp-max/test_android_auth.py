"""
Test MAX Auth with deviceType: ANDROID
Maybe MAX allows SMS auth for mobile devices even from datacenters.

Run: python test_android_auth.py [+79001234567]
"""
import asyncio
import json
import sys
import websockets

WS_URL = "wss://ws-api.oneme.ru/websocket"

async def test_android_hello():
    print("=" * 50)
    print("MAX ANDROID Auth Test")
    print("=" * 50)

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36"
    }

    try:
        print("\n1. Connecting to MAX WebSocket...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # Send HELLO with ANDROID deviceType
            hello = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 6,  # SESSION_INIT
                "payload": {
                    "userAgent": {
                        "deviceType": "ANDROID",
                        "locale": "ru_RU",
                        "osVersion": "14",
                        "deviceName": "Pixel 8",
                        "appVersion": "25.12.1",
                        "screen": "1080x2400",
                        "timezone": "Europe/Moscow"
                    },
                    "deviceId": "android-test-device-12345"
                }
            }

            print("\n2. Sending HELLO with deviceType=ANDROID...")
            await ws.send(json.dumps(hello))

            print("\n3. Waiting for response...")
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            payload = data.get("payload", {})
            location = payload.get("location")
            phone_auth = payload.get("phone-auth-enabled")

            print(f"\n   Location: {location}")
            print(f"   Phone Auth Enabled: {phone_auth}")

            if phone_auth:
                print("\n   [OK] SMS auth is ALLOWED!")
            else:
                print(f"\n   [FAIL] SMS auth blocked (location={location})")

            return location, phone_auth

    except Exception as e:
        print(f"\n   [ERROR] {e}")
        return None, False


async def test_android_sms_auth(phone: str):
    print("=" * 50)
    print(f"MAX ANDROID SMS Auth Test: {phone}")
    print("=" * 50)

    headers = {
        "Origin": "https://web.max.ru",
        "User-Agent": "Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36"
    }

    try:
        print("\n1. Connecting...")
        async with websockets.connect(WS_URL, additional_headers=headers) as ws:
            print("   [OK] Connected!")

            # Hello with ANDROID
            hello = {
                "ver": 11,
                "cmd": 0,
                "seq": 1,
                "opcode": 6,
                "payload": {
                    "userAgent": {
                        "deviceType": "ANDROID",
                        "locale": "ru_RU",
                        "osVersion": "14",
                        "deviceName": "Pixel 8",
                        "appVersion": "25.12.1",
                        "screen": "1080x2400",
                        "timezone": "Europe/Moscow"
                    },
                    "deviceId": "android-test-device-12345"
                }
            }

            print("\n2. Sending HELLO (ANDROID)...")
            await ws.send(json.dumps(hello))

            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Hello response: location={data.get('payload', {}).get('location')}, phone-auth={data.get('payload', {}).get('phone-auth-enabled')}")

            if not data.get("payload", {}).get("phone-auth-enabled"):
                print("\n   [FAIL] Phone auth disabled by server")
                return

            # AUTH_REQUEST (opcode 17)
            auth_req = {
                "ver": 11,
                "cmd": 0,
                "seq": 2,
                "opcode": 17,  # AUTH_REQUEST
                "payload": {
                    "phone": phone,
                    "language": "ru"
                }
            }

            print(f"\n3. Sending AUTH_REQUEST for {phone}...")
            await ws.send(json.dumps(auth_req))

            print("\n4. Waiting for response...")
            response = await asyncio.wait_for(ws.recv(), timeout=10)
            data = json.loads(response)
            print(f"   Response: {json.dumps(data, indent=2, ensure_ascii=False)}")

            # Check for error
            payload = data.get("payload", {})
            if "error" in data or payload.get("error"):
                error = data.get("error") or payload.get("error")
                print(f"\n   [FAIL] Auth error: {error}")
            elif "token" in payload:
                print(f"\n   [OK] SMS sent! Token: {payload['token'][:20]}...")
            else:
                print(f"\n   Unknown response")

    except Exception as e:
        print(f"\n   [ERROR] {e}")


if __name__ == "__main__":
    if len(sys.argv) > 1:
        phone = sys.argv[1]
        asyncio.run(test_android_sms_auth(phone))
    else:
        print("Testing HELLO with ANDROID deviceType...")
        print("Usage: python test_android_auth.py +79001234567")
        print()
        asyncio.run(test_android_hello())
