package com.wlhl.zhanzhangzhijia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BreakdownOkActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breakdown_ok);
		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		initUI(id);
	}

	private void initUI(String id) {
		findViewById(R.id.bdoback).setOnClickListener(this);
		TextView bdotext = (TextView) findViewById(R.id.bdotext);
		bdotext.setText("订单号:" + id + "故障已申请");
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bdoback:
			finish();
			break;

		default:
			break;
		}
	}

}
