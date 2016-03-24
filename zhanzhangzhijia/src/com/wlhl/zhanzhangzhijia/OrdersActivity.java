package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.Random;
import com.xinbo.utils.ExitApp;
import com.xinbo.utils.GetSetUntils;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrdersActivity extends Activity implements OnClickListener {
	private ArrayList<Integer> mlist = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		initUI();
		TextView tvsearch = (TextView) findViewById(R.id.tvsearch);
		tvsearch.setOnClickListener(this);
	}

	private void initUI() {
		mlist.add(1);
		mlist.add(2);
		ListView lv = (ListView) findViewById(R.id.listView1);
		Ladapter adapter = new Ladapter();
		lv.setAdapter(adapter);
	}

	class Ladapter extends BaseAdapter {
		@Override
		public int getCount() {
			return 2;
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
			convertView = getLayoutInflater().inflate(R.layout.listitem_orders,
					null);
			TextView tvnum = (TextView) convertView.findViewById(R.id.tvnum);
			TextView tvclassify = (TextView) convertView
					.findViewById(R.id.tvclassify);
			TextView tvwebsite = (TextView) convertView
					.findViewById(R.id.tvwebsite);
			TextView tvtitle = (TextView) convertView
					.findViewById(R.id.tvtitle);
			TextView tvurl = (TextView) convertView.findViewById(R.id.tvurl);
			TextView tvprice = (TextView) convertView
					.findViewById(R.id.tvprice);
			TextView tvcycle = (TextView) convertView
					.findViewById(R.id.tvcycle);
			TextView tvtotal = (TextView) convertView
					.findViewById(R.id.tvtotal);
			TextView tvstatus = (TextView) convertView
					.findViewById(R.id.tvstatus);
			View frabreakdown = convertView.findViewById(R.id.frabreakdown);
			View linearbtn = convertView.findViewById(R.id.linearbtn);
			if (mlist.get(position) == 1) {
				frabreakdown.setVisibility(View.VISIBLE);
				linearbtn.setVisibility(View.GONE);
				frabreakdown.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(OrdersActivity.this,
								BreakdownActivity.class);
						startActivity(intent);// application申请故障
					}
				});
			} else {
				linearbtn.setVisibility(View.VISIBLE);
				frabreakdown.setVisibility(View.GONE);
				btnPay(linearbtn);// pay付款
				btnCancel(linearbtn);// cancel取消订单
			}
			return convertView;
		}
	}

	/**
	 * pay付款
	 * 
	 * @param relativebtn
	 */
	private void btnPay(View linearbtn) {
		linearbtn.findViewById(R.id.frapay).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						int nextInt = new Random().nextInt(2);
						AlertDialog show = new AlertDialog.Builder(
								OrdersActivity.this).show();
						Window window = show.getWindow();
						View view = getLayoutInflater().inflate(
								R.layout.dialog_pay, null);
						TextView tvpay_recharge = (TextView) view
								.findViewById(R.id.tvpay_recharge);
						window.setGravity(Gravity.BOTTOM
								| Gravity.FILL_HORIZONTAL);
						window.setContentView(view);
						if (nextInt == 0) {
							tvpay_recharge.setText("去充值");
						} else {
							tvpay_recharge.setText("付款");
						}
					}
				});
	}

	/**
	 * cancel取消订单
	 * 
	 * @param relativebtn
	 */
	private void btnCancel(View linearbtn) {
		linearbtn.findViewById(R.id.fracancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						final AlertDialog show = new AlertDialog.Builder(
								OrdersActivity.this).show();
						Window window = show.getWindow();
						View view = getLayoutInflater().inflate(
								R.layout.dialog_cancelorder, null);
						view.findViewById(R.id.frapay_recharge)
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										show.dismiss();
									}
								});
						window.setGravity(Gravity.BOTTOM
								| Gravity.FILL_HORIZONTAL);
						window.setContentView(view);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvsearch:
			Intent intent = new Intent();
			startActivity(intent);
			break;
		}
	}

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
