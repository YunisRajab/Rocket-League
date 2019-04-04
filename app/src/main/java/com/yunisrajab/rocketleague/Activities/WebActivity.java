package com.yunisrajab.rocketleague.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunisrajab.rocketleague.R;

public class WebActivity extends AppCompatActivity {

    Toolbar mToolbar;
    WebView mWebView;
    boolean isRedirected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mToolbar = findViewById(R.id.toolbar);
        mWebView = findViewById(R.id.webView);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mToolbar.setTitle(mBundle.getString("Title"));
            mWebView.loadUrl(mBundle.getString("Link"));
        }

        mWebView.setWebViewClient(new WebViewClient()   {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                isRedirected = true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }

            public void onLoadResource (WebView view, String url) {
                if (!isRedirected) {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(WebActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    isRedirected=true;

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
                mToolbar.setTitle(view.getTitle());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
