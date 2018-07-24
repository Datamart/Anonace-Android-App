package com.anonace.android;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FullscreenActivity extends AppCompatActivity {
    private static final String TAG = FullscreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        WebView webView = (WebView) findViewById(R.id.webView);
        setSettings(webView);
        setWebViewClient(webView);
        webView.loadUrl(getAppURL());
    }

    @Override
    public void onBackPressed() {
        WebView webView = (WebView) findViewById(R.id.webView);

        if (!webView.canGoBack()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Are you sure you want to close application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", null).show();

        } else {
            webView.goBack();
        }
    }

    private void setWebViewClient(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                FullscreenActivity.this.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }

            @Override
            public boolean onJsAlert(
                    WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(FullscreenActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                }).setCancelable(false).create().show();

                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.splashView).setVisibility(View.GONE);
                findViewById(R.id.webView).setVisibility(View.VISIBLE);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                return shouldOverrideUrlLoading(view, req.getUrl());
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrlLoading(view, Uri.parse(url));
            }

            private boolean shouldOverrideUrlLoading(WebView view, Uri uri) {
                boolean result = false;

                if (!uri.getHost().contains(getResources().getString(R.string.site_domain))) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    result = true;
                }

                return result;
            }
        });
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        // settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setUserAgentString(
                settings.getUserAgentString() + " " +
                        getResources().getString(R.string.app_name) + "/" + getAppVersion());
    }

    private String getAppVersion() {
        try {
            return this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0.0";
        }
    }

    private String getAppURL() {
        String url = getResources().getString(R.string.initial_url);
        url += (url.contains("?") ? "&" : "?") + "v=" + getAppVersion();
        return url + "&nocache=" + String.valueOf(System.currentTimeMillis());
    }
}
