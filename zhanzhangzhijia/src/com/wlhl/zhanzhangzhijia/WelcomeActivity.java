package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.wlhl.hong.Constant;

public class WelcomeActivity extends Activity {
	private boolean isfirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();// 如果要恢复之前界面就结束新的Activity
			return;
		}
		// 友盟推送http://dev.umeng.com/push/android/integration
//		PushAgent mPushAgent = PushAgent.getInstance(this);
//		mPushAgent.enable();
//		PushAgent.getInstance(this).onAppStart();

		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 通知栏透明
		setContentView(R.layout.activity_welcome);
		SharedPreferences sp = getSharedPreferences(Constant.SP.SPNAME,
				Context.MODE_PRIVATE);
		isfirst = sp.getBoolean(Constant.SP.ISFIRST, false);
		myCheck();// 判断是否第一次使用app
	}

	private void myCheck() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isfirst) {
					Intent intent = new Intent(WelcomeActivity.this,
							HomeActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(WelcomeActivity.this,
							GuideActivity.class);
					startActivity(intent);
				}
				finish();
			}
		}, 2000);
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
