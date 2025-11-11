@echo off
chcp 65001 >nul

@echo off
setlocal enabledelayedexpansion

set "ISCC=C:\Program Files (x86)\Inno Setup 6\ISCC.exe"
set "ISS_FILE=fpl-scraper-standalone.iss"
set "OUT_DIR=D:\fpl-scraper-out"

echo 🧱 Building installer via Inno Setup...
"%ISCC%" "%ISS_FILE%"
if errorlevel 1 (
    echo ❌ Inno Setup build failed!
    pause
    exit /b 1
)

set "BAT_FILE=fpl-scraper-standalone.bat"

if exist "%BAT_FILE%" (
    copy /Y "%BAT_FILE%" "%OUT_DIR%\%BAT_FILE%" >nul
    echo ✅ Copied %BAT_FILE% to %OUT_DIR%
) else (
    echo ⚠️  File not found: %BAT_FILE%
)

echo 📂 Opening output folder...
explorer "%OUT_DIR%"

