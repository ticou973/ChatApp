package com.example.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://material.io/guidelines/motion/material-motion.html#");
    }



}
