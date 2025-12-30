# Avito Reverse Engineering

## Setup

### 1. Install jadx (Java Decompiler)

```powershell
# Option A: Download from GitHub releases
# https://github.com/skylot/jadx/releases/latest
# Download jadx-x.x.x.zip, extract to C:\tools\jadx

# Option B: Using scoop
scoop install jadx
```

### 2. Download Avito APK

**Option A: From APKPure**
- Go to https://apkpure.com/avito/com.avito.android
- Download latest version
- Save as `reverse/avito/avito.apk`

**Option B: From device via ADB**
```bash
adb shell pm path com.avito.android
# Output: package:/data/app/.../base.apk
adb pull /data/app/.../base.apk reverse/avito/avito.apk
```

### 3. Decompile APK

```bash
# Extract to Java source
jadx -d output avito.apk

# Or use GUI
jadx-gui avito.apk
```

### 4. Search for API endpoints

```bash
# Find messenger endpoints
grep -r "messenger" output/ --include="*.java"
grep -r "/web/1/" output/ --include="*.java"
grep -r "socket.avito" output/ --include="*.java"

# Find API URLs
grep -r "api.avito.ru" output/ --include="*.java"
grep -r "m.avito.ru" output/ --include="*.java"
```

## Target Endpoints to Find

| Function | Official API | Target User API |
|----------|--------------|-----------------|
| Send message | POST /messenger/v1/.../messages | ??? |
| Upload image | POST /messenger/v1/.../uploadImages | ??? |
| Mark as read | POST /messenger/v1/.../read | ??? |
| WebSocket | webhooks | socket.avito.ru? |
| Delete message | POST .../messages/{id} | ??? |

## Files Structure

```
reverse/avito/
├── avito.apk          # Original APK
├── output/            # Decompiled Java sources
├── strings.txt        # Extracted strings
├── endpoints.md       # Found API endpoints
└── README.md          # This file
```
