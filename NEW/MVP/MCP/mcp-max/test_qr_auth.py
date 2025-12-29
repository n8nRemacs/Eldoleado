"""
Test MAX Auth - QR and SMS
Run from PowerShell: python test_qr_auth.py
"""
import asyncio
import json
import sys
from max_client import MaxClient, Opcodes

async def test_qr_auth():
    print("=" * 50)
    print("MAX QR Auth Test")
    print("=" * 50)

    client = MaxClient()

    try:
        print("\n1. Connecting to MAX WebSocket...")
        await client.connect()
        print("   [OK] Connected!")

        print("\n2. Creating QR auth track (opcode 112)...")
        response = await client.create_auth_track()
        print(f"   Response: {json.dumps(response, indent=2, ensure_ascii=False)}")

        track_id = response.get("trackId")
        if track_id:
            print(f"\n   [OK] Track ID: {track_id}")
            print(f"\n   QR Code Data: max://auth/{track_id}")
            print("\n   Scan this with MAX mobile app to authenticate!")

            print("\n3. Waiting for QR scan (60 seconds)...")
            for i in range(60):
                await asyncio.sleep(1)
                if client.authenticated:
                    print(f"\n   [OK] Authenticated!")
                    print(f"   Profile: {json.dumps(client.profile, indent=2, ensure_ascii=False)}")
                    print(f"   Login Token: {client.login_token[:20]}..." if client.login_token else "   No token")
                    break
                if i % 10 == 0:
                    print(f"   ... waiting ({60-i}s)")
            else:
                print("\n   [FAIL] Timeout - QR not scanned")
        else:
            print(f"\n   [FAIL] No track_id in response")

    except Exception as e:
        print(f"\n   [ERROR] {e}")

    finally:
        await client.close()
        print("\n4. Disconnected")


async def test_sms_auth(phone: str):
    print("=" * 50)
    print("MAX SMS Auth Test")
    print("=" * 50)

    client = MaxClient()

    try:
        print("\n1. Connecting to MAX WebSocket...")
        await client.connect()
        print("   [OK] Connected!")

        print(f"\n2. Starting SMS auth for {phone}...")
        response = await client.start_auth(phone)
        print(f"   Response: {json.dumps(response, indent=2, ensure_ascii=False)}")

        token = response.get("token")
        if token:
            print(f"\n   [OK] SMS sent! Token: {token[:20]}...")

            code = input("\n   Enter SMS code: ").strip()
            if code:
                print(f"\n3. Verifying code {code}...")
                verify_response = await client.verify_code(token, code)
                print(f"   Response: {json.dumps(verify_response, indent=2, ensure_ascii=False)}")

                if client.authenticated:
                    print(f"\n   [OK] Authenticated!")
                    print(f"   Profile: {json.dumps(client.profile, indent=2, ensure_ascii=False)}")
                    print(f"   Login Token: {client.login_token}")
                    print(f"   Device ID: {client.device_id}")
        else:
            print(f"\n   [FAIL] No token in response")

    except Exception as e:
        print(f"\n   [ERROR] {e}")

    finally:
        await client.close()
        print("\n4. Disconnected")


if __name__ == "__main__":
    if len(sys.argv) > 1:
        # SMS auth with phone number
        phone = sys.argv[1]
        asyncio.run(test_sms_auth(phone))
    else:
        # QR auth
        print("Usage:")
        print("  python test_qr_auth.py           - QR auth")
        print("  python test_qr_auth.py +7900...  - SMS auth")
        print()
        asyncio.run(test_qr_auth())
