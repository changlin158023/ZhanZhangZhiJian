package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.xinbo.utils.ConnectionUtils;
import com.xinbo.utils.GetSetUntils;

public class AboutActivity extends Activity implements OnClickListener {

	/**
	 * 关于页面
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initUI();

	}

	private void initUI() {
		ImageView imback = (ImageView) findViewById(R.id.imback);
		View linearInformation = findViewById(R.id.linear1);
		View linearWeb = findViewById(R.id.linear2);
		imback.setOnClickListener(this);
		linearInformation.setOnClickListener(this);
		linearWeb.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.linear1:// 检查更新upgrade
			if (ConnectionUtils.isWifiConnected(this)) {
				GetSetUntils.setAlertDialog(this, "链接不到数据", null);
			} else {
				GetSetUntils.setToast(this, false, "网络异常");
			}
			break;
		case R.id.linear2:// 网站web
			Intent intent2 = new Intent(Intent.ACTION_VIEW);
			Uri data = Uri.parse("http://www.66607.com");
			intent2.setDataAndType(data, "text/html");
			startActivity(intent2);
			break;
		}
	}
}
