package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.wlhl.hong.XListView;
import com.wlhl.hong.XListView.IXListViewListener;

public class NewPurchaseActivity extends Activity implements OnClickListener,
		IXListViewListener {

	private ArrayList<String> liuliangchanpin = new ArrayList<String>();
	private ArrayList<String> xuanzhehngye = new ArrayList<String>();
	private ArrayList<String> quanzhong = new ArrayList<String>();
	private ArrayList<String> jiesuanfangshi = new ArrayList<String>();
	private Spinner np_spinner4;
	private ArrayAdapter<String> arrayAdapter4;
	private XListView xlistview;
	private MyAdapter myAdapter;
	public int px = 1;
	public int pz = 4;
	private String us;
	public ArrayList<JSONObject> sublist = new ArrayList<JSONObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_purchase);
		us = GetUntils.getInstance().getUS(this);
		initList();
		initUI();
		getData();
	}

	private void getData() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pz", "" + pz);
		params.put("px", "" + px);
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.GOUMAILIIULIANG, params,
				new VolleyListener() {

					private JSONObject realdata;

					public void onErrorResponse(VolleyError arg0) {

					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONObject datas = json.optJSONObject("datas");
							JSONArray data = datas.optJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								realdata = new JSONObject(String.valueOf(data
										.get(i)));
							}
							JSONArray sub = realdata.optJSONArray("sub");
							for (int i = 0; i < sub.length(); i++) {
								JSONObject jsonObject = new JSONObject(String
										.valueOf(sub.get(i)));
								sublist.add(jsonObject);
							}
							xlistview.stopLoadMore();
							myAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initList() {
		liuliangchanpin.add("图文链");
		liuliangchanpin.add("友情链");
		liuliangchanpin.add("文字链");
		xuanzhehngye.add("所有行业");
		xuanzhehngye.add("教育培训");
		xuanzhehngye.add("小说文学");
		xuanzhehngye.add("新闻媒体");
		xuanzhehngye.add("门户网站");
		xuanzhehngye.add("电子商务");
		quanzhong.add("0");
		quanzhong.add("1");
		quanzhong.add("2");
		quanzhong.add("3");
		quanzhong.add("4");
		quanzhong.add("5");
		quanzhong.add("6");
		quanzhong.add("7");
		quanzhong.add("8");
		quanzhong.add("9");
		quanzhong.add("10");
		jiesuanfangshi.add("按点击");
		jiesuanfangshi.add("按展示");
	}

	private void initUI() {
		findViewById(R.id.npimback).setOnClickListener(this);
		Spinner np_spinner1 = (Spinner) findViewById(R.id.np_spinner1);
		Spinner np_spinner2 = (Spinner) findViewById(R.id.np_spinner2);
		Spinner np_spinner3 = (Spinner) findViewById(R.id.np_spinner3);
		np_spinner4 = (Spinner) findViewById(R.id.np_spinner4);
		ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, liuliangchanpin);
		ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, xuanzhehngye);
		ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, quanzhong);
		arrayAdapter4 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, jiesuanfangshi);
		// arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		arrayAdapter1.setDropDownViewResource(R.layout.drop_down_item);
		arrayAdapter2.setDropDownViewResource(R.layout.drop_down_item);
		arrayAdapter3.setDropDownViewResource(R.layout.drop_down_item);
		arrayAdapter4.setDropDownViewResource(R.layout.drop_down_item);
		np_spinner1.setAdapter(arrayAdapter1);
		np_spinner2.setAdapter(arrayAdapter2);
		np_spinner3.setAdapter(arrayAdapter3);
		np_spinner4.setAdapter(arrayAdapter4);
		np_spinner1.setSelection(0, true);
		np_spinner2.setSelection(1, true);
		np_spinner3.setSelection(1, true);
		np_spinner4.setSelection(0, true);

		np_spinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						jiesuanfangshi.clear();
						if (1 == pos) {
							jiesuanfangshi.add("包月付费");
						} else {
							jiesuanfangshi.add("按点击");
							jiesuanfangshi.add("按展示");
							np_spinner4.setSelection(0, true);
						}
						arrayAdapter4.notifyDataSetChanged();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		xlistview = (XListView) findViewById(R.id.nplistView);
		xlistview.setPullRefreshEnable(false);
		xlistview.setPullLoadEnable(true);
		xlistview.setXListViewListener(this);
		myAdapter = new MyAdapter();
		xlistview.setAdapter(myAdapter);
	}

	class MyAdapter extends BaseAdapter {

		public int getCount() {
			return sublist.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(
						R.layout.listitem_buy, null);
				viewHolder.tvtype = (TextView) convertView
						.findViewById(R.id.tvtype);
				viewHolder.tvtitle = (TextView) convertView
						.findViewById(R.id.tvtitle);
				viewHolder.tvcategoryname = (TextView) convertView
						.findViewById(R.id.tvcategoryname);
				viewHolder.tvpaytype = (TextView) convertView
						.findViewById(R.id.tvpaytype);
				viewHolder.tvprice = (TextView) convertView
						.findViewById(R.id.tvprice);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			JSONObject jsonObject = sublist.get(position);
			try {
				if (jsonObject.getString("paytype").equals("包月付费")) {
					viewHolder.tvprice.setText(jsonObject.getString("price")
							+ "/月");
				} else if (jsonObject.getString("paytype").equals("按点击")) {
					viewHolder.tvprice.setText(jsonObject.getString("price")
							+ "/次");
				}
				viewHolder.tvtype.setText(jsonObject.getString("type"));
				viewHolder.tvtitle.setText(jsonObject.getString("title"));
				viewHolder.tvcategoryname.setText(jsonObject
						.getString("categoryname"));
				viewHolder.tvpaytype.setText(jsonObject.getString("paytype"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertView;
		}

		class ViewHolder {
			TextView tvtype;
			TextView tvtitle;
			TextView tvcategoryname;
			TextView tvpaytype;
			TextView tvprice;

		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.npimback:
			finish();
			break;
		}
	}

	public void onRefresh() {

	}

	public void onLoadMore() {
		px++;
		getData();
	}

}
