package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ClauseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clause);
		WebView webview = (WebView) findViewById(R.id.webView1);
		webview.setWebViewClient(new WebViewClient());
		webview.setWebChromeClient(new WebChromeClient());
		webview.loadUrl("file:///android_asset/clause.html");
		findViewById(R.id.tvback).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);// 友盟统计
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);// 友盟统计
//	}

}
