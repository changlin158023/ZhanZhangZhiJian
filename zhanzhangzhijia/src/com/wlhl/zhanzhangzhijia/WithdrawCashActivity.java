package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;

public class WithdrawCashActivity extends Activity implements OnClickListener {

	private TextView tvcash;
	private String us;
	private ArrayList<JSONObject> banklist = new ArrayList<JSONObject>();
	private ListView listview;
	private MyAdapter myAdapter;
	private AlertDialog dialog;

	/**
	 * 提现页面
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawcash);
		us = GetUntils.getInstance().getUS(this);
		getCashData();
		initView();
		getBank();

	}

	private void getBank() {
		banklist.clear();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.GETBANK, params,
				new VolleyListener() {
					public void onErrorResponse(VolleyError arg0) {
					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONObject datas = json.optJSONObject("datas");
							String num = datas.optString("num_rows");
							if (Integer.valueOf(num) == 0) {
								// 银行卡为空，不显示listview
								listview.setVisibility(View.GONE);
							} else {
								JSONArray data = datas.optJSONArray("data");
								for (int i = 0; i < data.length(); i++) {
									JSONObject real_data = new JSONObject(
											String.valueOf(data.get(i)));
									banklist.add(real_data);
								}
							}
							myAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void getCashData() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.USER, params, new VolleyListener() {
			public void onErrorResponse(VolleyError arg0) {
			}

			public void onResponse(String arg0) {
				try {
					JSONObject json = new JSONObject(arg0);
					JSONObject datas = json.optJSONObject("datas");
					tvcash.setText("当前账户余额:" + datas.getString("balance") + "元");
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

	private void initView() {
		findViewById(R.id.tvback).setOnClickListener(this);
		tvcash = (TextView) findViewById(R.id.cash);
		listview = (ListView) findViewById(R.id.lsitview);
		myAdapter = new MyAdapter();
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Builder builder = new AlertDialog.Builder(
						WithdrawCashActivity.this);
				dialog = builder.show();
				Window window = dialog.getWindow();
				View views = getLayoutInflater().inflate(
						R.layout.dialog_withdrawcash, null);// dialog自定义布局
				TextView tvclose = (TextView) views.findViewById(R.id.tvclose);
				tvclose.setOnClickListener(WithdrawCashActivity.this);
				window.setGravity(Gravity.BOTTOM);
				window.setContentView(views);
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		public int getCount() {
			return banklist.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = getLayoutInflater().inflate(
					R.layout.listitem_refund, null);
			TextView username = (TextView) inflate.findViewById(R.id.username);
			TextView banknum = (TextView) inflate.findViewById(R.id.banknum);
			TextView bankname = (TextView) inflate.findViewById(R.id.bankname);

			JSONObject data = banklist.get(position);
			// username.setText(data.getString("username"));
			try {
				banknum.setText(data.getString("no"));
				bankname.setText(data.getString("bankname"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return inflate;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvback:
			finish();
			break;
		case R.id.tvclose:
			dialog.dismiss();
			break;
		}
	}
}
