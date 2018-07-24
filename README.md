## WebView-based Android mobile app for AnonAce.

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
settings.setSupportZoom(true);
settings.setBuiltInZoomControls(true);
settings.setDomStorageEnabled(true);
```

### URLs:
- Opt-in for testing: https://play.google.com/apps/testing/com.anonace.android
- Google Play URL: https://play.google.com/store/apps/details?id=com.anonace.android


