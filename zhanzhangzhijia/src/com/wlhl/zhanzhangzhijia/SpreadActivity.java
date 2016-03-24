package com.wlhl.zhanzhangzhijia;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SpreadActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spread);
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
