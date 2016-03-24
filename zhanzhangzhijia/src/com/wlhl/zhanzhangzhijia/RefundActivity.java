package com.wlhl.zhanzhangzhijia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wlhl.hong.GetUntils;

public class RefundActivity extends Activity implements OnClickListener {
	private AlertDialog dialog;
	private String us;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refund);
		us = GetUntils.getInstance().getUS(this);
		initUI();
	}

	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		ListView lv = (ListView) findViewById(R.id.listView1);
		Ladapter adapter = new Ladapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Builder builder = new AlertDialog.Builder(RefundActivity.this);
				dialog = builder.show();
				Window window = dialog.getWindow();
				View views = getLayoutInflater().inflate(
						R.layout.dialog_refund, null);// dialog自定义布局
				TextView tvclose = (TextView) views.findViewById(R.id.tvclose);
				tvclose.setOnClickListener(RefundActivity.this);
				window.setGravity(Gravity.BOTTOM);
				window.setContentView(views);
			}
		});
		tvback.setOnClickListener(this);
	}

	class Ladapter extends BaseAdapter {
		TextView tv = null;

		@Override
		public int getCount() {
			return 30;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate;
			if (convertView == null) {
				inflate = getLayoutInflater().inflate(R.layout.listitem_refund,
						null);
				tv = (TextView) inflate.findViewById(R.id.textView5);
			} else {
				inflate = convertView;
			}
			return inflate;
		}

		@Override
		public int getItemViewType(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			return 30;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tvback:
			finish();
			break;
		case R.id.tvclose:
			dialog.dismiss();
			break;
		case R.id.tvsetting:

			break;
		}
	}
}
