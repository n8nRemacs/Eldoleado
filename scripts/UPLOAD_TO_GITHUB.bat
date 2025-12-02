@echo off
chcp 65001 > nul
echo.
echo ╔══════════════════════════════════════════════════════════╗
echo ║     🚀 BatteryCRM - Upload to GitHub                    ║
echo ╚══════════════════════════════════════════════════════════╝
echo.
echo 📦 Git Status:
git log --oneline -5
echo.
echo ✅ Ready to upload!
echo.
echo 👉 Next steps:
echo.
echo 1. Create repository on GitHub:
echo    https://github.com/new
echo.
echo 2. Repository name: BatteryCRM
echo    Visibility: Private
echo    DO NOT create README/gitignore
echo.
echo 3. Run these commands:
echo.
set /p username="Enter your GitHub username: "
echo.
echo git remote add origin https://github.com/%username%/BatteryCRM.git
echo git branch -M main
echo git push -u origin main
echo.
pause
