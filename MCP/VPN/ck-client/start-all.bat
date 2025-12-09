@echo off
echo Starting Cloak clients...
echo.

cd /d "%~dp0"

echo Starting VK on port 11080...
start "CK-VK" /min ck-client.exe -c ck-vk.json -i 127.0.0.1 -l 11080
timeout /t 2 >nul

echo Starting Yandex on port 11081...
start "CK-Yandex" /min ck-client.exe -c ck-yandex.json -i 127.0.0.1 -l 11081
timeout /t 2 >nul

echo Starting Rutube on port 11082...
start "CK-Rutube" /min ck-client.exe -c ck-rutube.json -i 127.0.0.1 -l 11082
timeout /t 2 >nul

echo Starting Kinopoisk on port 11083...
start "CK-Kinopoisk" /min ck-client.exe -c ck-kinopoisk.json -i 127.0.0.1 -l 11083

echo.
echo ==========================================
echo Cloak clients started!
echo.
echo Mihomo/Clash connects to these as SS servers:
echo   VK:        ss://...@127.0.0.1:11080
echo   Yandex:    ss://...@127.0.0.1:11081
echo   Rutube:    ss://...@127.0.0.1:11082
echo   Kinopoisk: ss://...@127.0.0.1:11083
echo.
echo Use mihomo-with-ck.yaml config in Mihomo
echo ==========================================
pause
