@echo off
echo Starting SSH tunnel to PostgreSQL (185.221.214.83:6544 -> localhost:5432)
echo Press Ctrl+C to stop
ssh -N -L 5432:localhost:6544 root@185.221.214.83
