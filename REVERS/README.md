# Reverse Engineering Setup

## Tools Installed

| Tool | Location | Purpose |
|------|----------|---------|
| JADX | `C:\tools\jadx\bin\jadx.bat` | APK decompilation to Java |
| JADX-GUI | `C:\tools\jadx\bin\jadx-gui.bat` | GUI for browsing decompiled code |
| apktool | `C:\tools\apktool.bat` | Resource extraction, smali |
| Frida | `pip install frida frida-tools` | Dynamic analysis, hooking |

## Java Path

```bash
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
```

## Directory Structure

```
REVERS/
├── avito/
│   ├── apk/              # Original APK files
│   ├── decompiled/       # JADX output
│   ├── frida-scripts/    # Frida hooks
│   ├── logs/             # Captured traffic/logs
│   └── output/           # Already decompiled (existing)
│
├── max/
│   ├── apk/              # Original APK files
│   ├── decompiled/       # JADX output
│   ├── frida-scripts/    # Frida hooks
│   └── logs/             # Captured traffic/logs
│
└── README.md
```

## Quick Commands

### Decompile APK with JADX
```bash
# CLI
"/c/tools/jadx/bin/jadx.bat" -d output_dir app.apk

# GUI (recommended for browsing)
"/c/tools/jadx/bin/jadx-gui.bat" app.apk
```

### Extract resources with apktool
```bash
# Decode APK
java -jar /c/tools/apktool.jar d app.apk -o output_dir

# Rebuild APK
java -jar /c/tools/apktool.jar b output_dir -o rebuilt.apk
```

### Frida Commands
```bash
# List devices
frida-ls-devices

# List running apps on Android
frida-ps -U

# Attach to app
frida -U -n "app.package.name"

# Run script
frida -U -n "app.package.name" -l script.js
```

## Download APKs

### Avito
- APKPure: https://apkpure.com/avito/com.avito.android
- APKMirror: https://www.apkmirror.com/apk/avito/

### MAX (VK Teams)
- APKPure: https://apkpure.com/max-vk-teams/ru.mail.myteam
- Play Store package: `ru.mail.myteam`

## Frida Server Setup (Android)

1. Download frida-server for your architecture:
   - https://github.com/frida/frida/releases
   - Choose `frida-server-X.X.X-android-arm64.xz`

2. Push to device:
```bash
adb push frida-server /data/local/tmp/
adb shell "chmod 755 /data/local/tmp/frida-server"
adb shell "su -c /data/local/tmp/frida-server &"
```

## Common Frida Hooks

### SSL Pinning Bypass
```javascript
Java.perform(function() {
    var TrustManager = Java.use('javax.net.ssl.X509TrustManager');
    // ... bypass code
});
```

### HTTP Traffic Logging
```javascript
Java.perform(function() {
    var OkHttpClient = Java.use('okhttp3.OkHttpClient');
    // ... logging code
});
```

## Existing Analysis

### Avito
- `output/` - Full JADX decompilation
- `avito_user_client.py` - Reverse-engineered client
- `humanized_client.py` - Humanized request wrapper

### MAX
- `max_client.py` - WebSocket client
- Analysis in progress...
