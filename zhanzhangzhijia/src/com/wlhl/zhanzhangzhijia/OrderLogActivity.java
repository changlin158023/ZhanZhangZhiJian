package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;

public class OrderLogActivity extends Activity implements OnClickListener {

	private ListView orderloglistview;
	private MyAdapter myAdapter;
	private String id;
	private String us;
	private ArrayList<JSONObject> datalist = new ArrayList<JSONObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_log);
		us = GetUntils.getInstance().getUS(this);
		initView();
		getData();

	}

	private void getData() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.ORDERLOG, params,
				new VolleyListener() {

					public void onErrorResponse(VolleyError arg0) {

					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONObject datas = json.optJSONObject("datas");
							JSONArray data = datas.optJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								JSONObject real_data = new JSONObject(String
										.valueOf(data.get(i)));
								datalist.add(real_data);
							}
							myAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initView() {
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		orderloglistview = (ListView) findViewById(R.id.orderloglistview);
		orderloglistview.setDividerHeight(0);
		myAdapter = new MyAdapter();
		orderloglistview.setAdapter(myAdapter);
		findViewById(R.id.imback).setOnClickListener(this);
	}

	class MyAdapter extends BaseAdapter {

		public int getCount() {
			return datalist.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = getLayoutInflater().inflate(
					R.layout.listitem_orderlog, null);
			TextView orderlogtime = (TextView) inflate
					.findViewById(R.id.orderlogtime);
			TextView orderlogcontent = (TextView) inflate
					.findViewById(R.id.orderlogcontent);
			TextView orderloguser = (TextView) inflate
					.findViewById(R.id.orderloguser);

			JSONObject data = datalist.get(position);
			try {
				orderlogtime.setText(data.getString("atime"));
				orderlogcontent.setText(data.getString("note"));
				orderloguser.setText(data.getString("nickname"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return inflate;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;

		default:
			break;
		}
	}
}
