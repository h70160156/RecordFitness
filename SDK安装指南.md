## 请先检查你的 Android SDK 安装位置

打开 Android Studio → Settings → Appearance & Behavior → System Settings → Android SDK

查看 "Android SDK Location" 路径，告诉我路径在哪里。

## 同时，请确保已安装：

1. **Android SDK Platform 34** (Android 14)
   - 在 SDK Manager 中勾选 "Android 14 (API 34)"

2. **Build Tools 34.0.0**
   - 在 SDK Manager 中勾选 "Android SDK Build-Tools 34.0.0"

## 如果安装后还是报错，运行这个命令看详细错误：

```bash
gradlew.bat assembleDebug --stacktrace
```

把错误信息发给我。
