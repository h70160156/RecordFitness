#!/bin/bash

echo "=========================================="
echo "   健身记录 App - 一键打包脚本"
echo "=========================================="
echo ""

# 检查 Java
if ! command -v java &> /dev/null; then
    echo "❌ 错误：未检测到 Java"
    echo "请先安装 JDK 17 或更高版本"
    echo "访问 https://adoptium.net/ 下载"
    exit 1
fi

# 检查 Java 版本
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ 错误：需要 JDK 17 或更高版本，当前版本：$JAVA_VERSION"
    exit 1
fi

echo "✓ Java 版本检查通过: JDK $JAVA_VERSION"
echo ""

# 检查 Gradle
if [ -f "./gradlew" ]; then
    echo "✓ 使用项目自带的 Gradle Wrapper"
    CHMOD grads
    ./gradlew --version
else
    echo "❌ 错误：未找到 Gradle Wrapper"
    exit 1
fi
