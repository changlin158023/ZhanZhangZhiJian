package com.wlhl.zhanzhangzhijia;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.xinbo.utils.ExitApp;

@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ExitApp.getInstance().addActivity(this);// 将activity加入容器以便退出时finish
		initTabhost();
	}

	private void initTabhost() {
		TabHost host = getTabHost();
		TabSpec tabSpec = host.newTabSpec("1");
		tabSpec.setContent(new Intent(this, HomeActivity2.class));
		View view1 = getLayoutInflater().inflate(R.layout.tab_home, null);
		tabSpec.setIndicator(view1);
		host.addTab(tabSpec);

		TabSpec tabSpec2 = host.newTabSpec("2");
		tabSpec2.setContent(new Intent(this, FlowPurchaseActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		View view2 = getLayoutInflater().inflate(R.layout.tab_buy, null);
		tabSpec2.setIndicator(view2);
		host.addTab(tabSpec2);

		TabSpec tabSpec3 = host.newTabSpec("3");
		tabSpec3.setContent(new Intent(this, OrdersActivity.class));
		View view3 = getLayoutInflater().inflate(R.layout.tab_sum, null);
		tabSpec3.setIndicator(view3);
		host.addTab(tabSpec3);
		TabSpec tabSpec4 = host.newTabSpec("4");
		tabSpec4.setContent(new Intent(this, AccountActivity.class));
		View view4 = getLayoutInflater().inflate(R.layout.tab_account, null);
		tabSpec4.setIndicator(view4);
		host.addTab(tabSpec4);
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
