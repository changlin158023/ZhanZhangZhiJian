package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroductionActivity extends Activity implements OnClickListener {

	private TextView tvinfo;
	private String info;
	private ImageView tvback;

	/**
	 * 消息详情
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);
		tvinfo = (TextView) findViewById(R.id.tvinfo);
		tvback = (ImageView) findViewById(R.id.tvback);
		tvback.setOnClickListener(this);
		getdata();
		tvinfo.setText(info);
	}

	private void getdata() {
		Intent intent = getIntent();
		info = intent.getStringExtra("info");

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tvback:// 返回 back
			finish();
			break;
		}

	}
}
