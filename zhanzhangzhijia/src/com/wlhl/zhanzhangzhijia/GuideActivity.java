package com.wlhl.zhanzhangzhijia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wlhl.fragment.FirstFragment;

public class GuideActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		ViewPager vp = (ViewPager) findViewById(R.id.vp);
		VPadapter ada = new VPadapter(getSupportFragmentManager());
		vp.setAdapter(ada);
	}

	class VPadapter extends FragmentPagerAdapter {

		public VPadapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			FirstFragment frag = new FirstFragment();
			Bundle args = new Bundle();
			args.putInt("arg", arg0);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public int getCount() {
			return 1;
		}
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
