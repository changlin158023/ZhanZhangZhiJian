package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BuySettingActivity extends Activity implements OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buysetting);
		ImageView imback = (ImageView) findViewById(R.id.imback);
		imback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		}
	}
}
