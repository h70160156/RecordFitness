@echo off
echo ==========================================
echo    Fitness Record App - Build Script
echo ==========================================
echo.

REM Check Java
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java not found
    echo Please install JDK 17 or later
    echo Download from https://adoptium.net/
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set "JAVA_VER=%%i"
)
set "JAVA_VER=%JAVA_VER:"=%"
for /f "tokens=1,2 delims=." %%a in ("%JAVA_VER%") do (
    set "MAJOR_VER=%%a"
    set "MINOR_VER=%%b"
)
if "%MAJOR_VER%"=="1" (
    set "JAVA_VER_NUM=%MINOR_VER%"
) else (
    set "JAVA_VER_NUM=%MAJOR_VER%"
)
if %JAVA_VER_NUM% LSS 17 (
    echo [ERROR] JDK 17 or later required, current: %JAVA_VER%
    pause
    exit /b 1
)

echo [OK] Java version: %JAVA_VER%
echo.

REM Check Gradle Wrapper
if exist "gradlew.bat" (
    echo [OK] Using Gradle Wrapper
    echo.
    echo Building Debug APK...
    echo.
    call gradlew.bat assembleDebug
) else (
    echo [ERROR] Gradle Wrapper not found
    pause
    exit /b 1
)

if %errorlevel% equ 0 (
    echo.
    echo ==========================================
    echo [SUCCESS] Build completed!
    echo APK: app\build\outputs\apk\debug\app-debug.apk
    echo ==========================================
) else (
    echo.
    echo [ERROR] Build failed!
    echo Please check the error messages
)

pause
