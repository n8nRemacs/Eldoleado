"""Check what categories exist under Phone."""
import paramiko
import sys
sys.stdout.reconfigure(encoding='utf-8')

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect('45.144.177.128', username='root', password='Mi31415926pSss!', timeout=10)

# Check categories in DB
cmd = """cd /opt/ifixit-parser && ./venv/bin/python -c "
import asyncio
from src.loader.postgres import PostgresClient

async def check():
    async with PostgresClient() as pg:
        # Get Phone subcategories
        rows = await pg.pool.fetch('''
            SELECT name, level, guide_count, device_count
            FROM ifixit_kb.categories
            WHERE parent_id = (SELECT id FROM ifixit_kb.categories WHERE name = \\\"Phone\\\")
            ORDER BY name
            LIMIT 30
        ''')
        print('Phone subcategories:')
        for r in rows:
            print(f'  - {r[0]} (level={r[1]}, guides={r[2]}, devices={r[3]})')

        # Count total
        total = await pg.pool.fetchrow('SELECT COUNT(*) FROM ifixit_kb.categories')
        print(f'Total categories in DB: {total[0]}')

asyncio.run(check())
"
"""
stdin, stdout, stderr = ssh.exec_command(cmd)
print(stdout.read().decode())
err = stderr.read().decode()
if err:
    print("Errors:", err)

ssh.close()
