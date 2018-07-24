## WebView-based Android mobile app for AnonAce.

Versions [build.gradle](app/build.gradle)

```java
defaultConfig {
    applicationId "com.anonace.android"
    versionCode 2
    versionName "0.0.2"
}
```

Main config file: [strings.xml](app/src/main/res/values/strings.xml)

```xml
<resources>
    <string name="app_name">AnonAce</string>
    <string name="site_domain">anonace.com</string>
    <string name="initial_url">https://anonace.com/</string>
</resources>
```

WebView settings: [FullscreenActivity.java](app/src/main/java/com/anonace/android/FullscreenActivity.java#L248)

```java
settings.setJavaScriptEnabled(true);
settings.setAppCacheEnabled(true);
settings.setDatabaseEnabled(true);
settings.setDomStorageEnabled(true);
```

### URLs:
- Google Play URL: https://play.google.com/store/apps/details?id=com.anonace.android


