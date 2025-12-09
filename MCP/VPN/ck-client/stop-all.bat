@echo off
echo Stopping Cloak clients...
taskkill /f /im ck-client.exe 2>nul
echo Done.
pause
