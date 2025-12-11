# Android App Update - Complete Package

## Files (14 total)

### Java/Kotlin (9 files)
- app/src/main/java/com/batterycrm/app/
  - SessionManager.kt [NEW]
  - MainActivity.kt [REPLACE]
  - api/ApiService.kt [REPLACE]
  - ui/LoginActivity.kt [NEW]
  - ui/AppealDetailActivity.kt [REPLACE]
  - models/Appeal.kt [NEW]
  - models/Message.kt [NEW]
  - adapters/AppealsAdapter.kt [NEW]
  - adapters/MessagesAdapter.kt [NEW]

### XML Layouts (5 files)
- app/src/main/res/layout/
  - activity_login.xml [NEW]
  - item_appeal.xml [NEW]
  - item_message_client.xml [NEW]
  - item_message_operator.xml [NEW]
- app/src/main/res/menu/
  - main_menu.xml [NEW]

## Installation

1. Extract archive
2. Copy `app` folder to your project root (merge/replace)
3. Update AndroidManifest.xml - see below
4. Sync Gradle

## AndroidManifest.xml Changes

Replace MainActivity declaration with:

```xml
<activity
    android:name=".ui.LoginActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>

<activity
    android:name=".MainActivity"
    android:exported="false" />

<activity
    android:name=".ui.AppealDetailActivity"
    android:exported="false" />
```

## Test Login

- Username: `test@batterycrm.tu`
- Password: `test123`
