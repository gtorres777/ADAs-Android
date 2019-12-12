package com.example.lagartoproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView aea = (WebView) findViewById(R.id.web_1);
        aea.getSettings().setJavaScriptEnabled(true);
        aea.getSettings().setBuiltInZoomControls(true);
        aea.loadUrl("http://tux777.pythonanywhere.com/accounts/login/");

                aea.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverriceUrlLoading(WebView view, String url){
                        return false;
                    }
                });
    }
}
