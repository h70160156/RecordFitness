@echo off
chcp 65001 > nul
echo ==========================================
echo    健身记录 App - 一键打包脚本
echo ==========================================
echo.

REM 检查 Java
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 错误：未检测到 Java
    echo 请先安装 JDK 17 或更高版本
    echo 访问 https://adoptium.net/ 下载
    pause
    exit /b 1
)

REM 检查 Java 版本
for /f "tokens=2" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set "JAVA_VER=%%i"
)
set "JAVA_VER=%JAVA_VER:~1,2%"
if %JAVA_VER% LSS 17 (
    echo ❌ 错误：需要 JDK 17 或更高版本，当前版本：%JAVA_VER%
    pause
    exit /b 1
)

echo ✓ Java 版本检查通过: JDK %JAVA_VER%
echo.

REM 检查 Gradle Wrapper
if exist "gradlew.bat" (
    echo ✓ 使用项目自带的 Gradle Wrapper
    echo.
    echo 开始构建 Debug APK...
    echo.
    gradlew.bat assembleDebug
) else (
    echo ❌ 错误：未找到 Gradle Wrapper
    pause
    exit /b 1
)

if %errorlevel% equ 0 (
    echo.
    echo ==========================================
    echo ✓ 构建成功！
    echo APK 文件位置：app\build\outputs\apk\debug\app-debug.apk
    echo ==========================================
) else (
    echo.
    echo ❌ 构建失败！
    echo 请检查错误信息
)

pause
