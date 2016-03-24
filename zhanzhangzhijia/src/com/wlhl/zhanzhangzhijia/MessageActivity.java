package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.wlhl.fragment.AnnounceFragment;
import com.wlhl.fragment.MessageFragment;
import com.wlhl.hong.GetUntils;
import com.wlhl.hong.MyViewPager;

/**
 * MessageFragment消息中心 AnnounceFragment系统公告
 */
public class MessageActivity extends FragmentActivity implements
		OnClickListener {
	private MyViewPager mViewPage;
	List<Fragment> allFragment = new ArrayList<Fragment>();
	private String us;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initUI();
	}

	private void initUI() {
		us = GetUntils.getInstance().getUS(this);
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		RadioButton rbmessage = (RadioButton) findViewById(R.id.radio0);
		tvback.setOnClickListener(this);
		rbmessage.setOnCheckedChangeListener(messageCheck());
		mViewPage = (MyViewPager) findViewById(R.id.vp);
		MessageFragment messageFragment = new MessageFragment();
		AnnounceFragment announceFragment = new AnnounceFragment();
		allFragment.add(messageFragment);
		allFragment.add(announceFragment);
		// mViewPage.setOnTouchListener(new OnTouchListener() {
		// public boolean onTouch(View v, MotionEvent event) {
		// return true;
		// }
		// });
		mViewPage.setAdapter(new MyAdapter(getSupportFragmentManager(),
				allFragment));
	}

	class MyAdapter extends FragmentPagerAdapter {

		private List<Fragment> list;

		public MyAdapter(FragmentManager fm, List<Fragment> allFragment) {
			super(fm);
			this.list = allFragment;
		}

		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		public int getCount() {
			return list.size();
		}
	}

	/**
	 * 消息/公告切换
	 */
	private OnCheckedChangeListener messageCheck() {
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					mViewPage.setCurrentItem(0, false);// 获取系统消息
				} else {
					mViewPage.setCurrentItem(1, false);// 获取公告消息
				}
			}
		};
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
