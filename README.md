# 健身记录 App - 快速开始

## 🚀 5分钟快速打包

### 第一步：安装 JDK 17
- **Windows**: https://adoptium.net/ → 下载并安装 JDK 17+
- **Mac**: `brew install openjdk@17`
- **Linux**: `sudo apt install openjdk-17-jdk`

### 第二步：安装 Android Studio（可选但推荐）
- 下载地址：https://developer.android.com/studio
- 安装后会自动配置 Android SDK

### 第三步：构建 APK

**方式 A - 一键脚本（推荐）**
```bash
# Windows
双击运行 build.bat

# Mac/Linux
chmod +x build.sh
./build.sh
```

**方式 B - Android Studio**
1. 打开 Android Studio
2. 选择 "Open an existing project"
3. 选择这个项目文件夹
4. 等待依赖下载完成
5. 在右侧 Gradle 面板，双击 app → Tasks → build → assembleDebug

**方式 C - 命令行**
```bash
# Windows
gradlew.bat assembleDebug

# Mac/Linux
./gradlew assembleDebug
```

### 第四步：找到 APK
```
app/build/outputs/apk/debug/app-debug.apk
```

### 第五步：安装到手机
- 方法1：直接把 APK 文件发送到手机，点击安装
- 方法2：连接电脑后执行 `adb install app/build/outputs/apk/debug/app-debug.apk`

## 📱 功能预览

- 💪 五大肌群训练记录（胸、背、腿、手臂、肩）
- 🎨 iOS 风格液态玻璃效果
- 📊 训练统计和进度图表
- 🏆 PR 个人最佳记录检测
- 🌙 深色/浅色主题切换

## ❓ 遇到问题？

### 问题1：Gradle 下载失败
```
解决方案：手动下载 Gradle 8.2
https://services.gradle.org/distributions/gradle-8.2-bin.zip
```

### 问题2：SDK 版本不对
```bash
# 在 Android Studio 中打开 SDK Manager
# 安装 Android 14 (API 34)
```

### 问题3：内存不足
```bash
# 编辑 gradle.properties
org.gradle.jvmargs=-Xmx4096m
```

### 问题4：Kotlin 插件版本不匹配
```bash
# 确保 Kotlin 版本是 1.9.22
# Compose Compiler 版本是 1.5.8
```

## 📂 项目结构

```
RecordFitness/
├── app/
│   └── src/main/
│       ├── java/com/record/fitness/
│       │   ├── data/          # 数据层（Room数据库）
│       │   ├── ui/            # 界面层（Compose UI）
│       │   └── MainActivity.kt
│       └── res/               # 资源文件
├── build.gradle.kts           # 项目配置
├── settings.gradle.kts         # 设置
└── gradle/wrapper/            # Gradle Wrapper
```

## 🎉 完成！

现在你可以将 APK 安装到手机上测试了。祝你健身愉快！

## 📞 获取帮助

如果遇到问题，请：
1. 查看详细文档：[打包指南.md](./打包指南.md)
2. 检查错误信息
3. 搜索解决方案
