package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import com.xinbo.utils.ExitApp;
import com.xinbo.utils.GetSetUntils;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class BuyActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private ArrayList<String> alweb = new ArrayList<String>();
	private ArrayList<String> alclassify = new ArrayList<String>();
	private ArrayList<Boolean> alcheck = new ArrayList<Boolean>();
	private Ladapter adapter;
	private CheckBox checkall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy);
		alweb.add("阿里巴巴");
		alweb.add("网易");
		alweb.add("去哪儿");
		alweb.add("苏宁易购");
		alweb.add("腾讯");
		alclassify.add("电子商务");
		alclassify.add("新闻资讯");
		alclassify.add("旅游啊");
		alclassify.add("电商");
		alclassify.add("新闻资讯");
		initUI();
	}

	/**
	 * 找控件findviewbyid
	 */
	private void initUI() {
		for (int i = 0; i < alweb.size(); i++) {
			alcheck.add(false);
		}
		ListView lv = (ListView) findViewById(R.id.listView1);
		adapter = new Ladapter();
		lv.setAdapter(adapter);

		checkall = (CheckBox) findViewById(R.id.checkBox1);
		checkall.setOnCheckedChangeListener(this);

		TextView tvspread = (TextView) findViewById(R.id.tvspread);
		tvspread.setOnClickListener(this);
	}

	/**
	 * 适配器listview Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class Ladapter extends BaseAdapter {
		@Override
		public int getCount() {
			return alweb.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.listitem_buy, null);
			}
			CheckBox checkbox = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			checkbox.setOnCheckedChangeListener(null);
			checkbox.setChecked(alcheck.get(position));
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					checkall.setOnCheckedChangeListener(null);
					if (isChecked) {
						alcheck.remove(position);
						alcheck.add(position, true);
					} else {
						alcheck.remove(position);
						alcheck.add(position, false);
					}
					if (alcheck.contains(false)) {
						Log.e("------false------", alcheck + "、");
						checkall.setChecked(false);
					} else {
						Log.e("------true------", alcheck + "、");
						checkall.setChecked(true);
					}
					checkall.setOnCheckedChangeListener(BuyActivity.this);
				}
			});
			TextView tvweb = (TextView) convertView.findViewById(R.id.tvweb);
			TextView tvclassify = (TextView) convertView
					.findViewById(R.id.tvclassify);
			tvweb.setText(alweb.get(position));
			tvclassify.setText(alclassify.get(position));
			return convertView;
		}
	}

	/**
	 * 点击事件onclick
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvspread:
			Intent intent = new Intent(this, SpreadActivity.class);
			startActivity(intent);
			break;
		}
	}

	/**
	 * 检查框事件onCheckedChangedListener
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox1:
			alcheck.clear();
			for (int i = 0; i < alweb.size(); i++) {
				if (isChecked) {
					alcheck.add(true);
				} else {
					alcheck.add(false);
				}
			}
			adapter.notifyDataSetChanged();
			break;
		}
	}

	/**
	 * 返回退出onkeyDown
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GetSetUntils.setAlertDialog(this, "是否确定退出",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ExitApp.getInstance().exit();
						}
					});
		}
		return super.onKeyDown(keyCode, event);
	}
}
