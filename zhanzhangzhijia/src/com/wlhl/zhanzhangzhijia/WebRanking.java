package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.XListView;
import com.wlhl.hong.XListView.IXListViewListener;

public class WebRanking extends FragmentActivity implements OnClickListener,
		IXListViewListener {
	List<Fragment> allFragment = new ArrayList<Fragment>();
	private TextView mtitle;
	private ArrayList<JSONObject> id_list = new ArrayList<JSONObject>();// cids容器
	private ArrayList<JSONObject> mdataList = new ArrayList<JSONObject>();// 排行数据容器
	private XListView xListView;
	private MyListViewAdapter myListViewAdapter;

	private String cids = "0";// cids分类id字符，比如“0,310,21”表示获取分类为全部，id为310和id为21的网站
	private int px = 1;// px 页码，默认是1
	private String pz = "20";
	private String order_by = "ranking";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webranking);
		getListData();
		getData();
		initview();

	}

	private void getListData() {
		// 获取分类列表数据
		MyVolley.get(this, Constant.MYURL.WEB_GET + "&id=0",
				new VolleyListener() {
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(WebRanking.this, "网络异常，请重试",
								Toast.LENGTH_SHORT).show();
					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONObject datas = json.optJSONObject("datas");
							JSONArray data = datas.optJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								JSONObject real_data = new JSONObject(String
										.valueOf(data.get(i)));
								id_list.add(real_data);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	private void getData() {

		// 获取分类排行(根据行业分类cids)
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cids", cids);// 行业分类id
		params.put("pz", pz);// 每个分类的前几条数据
		params.put("px", px + "");// px 页码，默认是1
		params.put("order_by", order_by);// 可选值：ranking表示排名，speed_ranking表示趋势排名，bdweight百度权重，rp谷歌权重，alexa
		// params.put("order_method", "asc");// asc:升序;desc:降序,默认是asc
		MyVolley.post(this, Constant.MYURL.WEBRANKING, params,
				new VolleyListener() {
					public void onErrorResponse(VolleyError arg0) {

					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONArray datas = json.optJSONArray("datas");
							JSONObject object = (JSONObject) datas.get(0);
							JSONArray data = object.optJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								JSONObject real_data = new JSONObject(String
										.valueOf(data.get(i)));
								mdataList.add(real_data);
							}
							myListViewAdapter.notifyDataSetChanged();
							xListView.stopLoadMore();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initview() {
		findViewById(R.id.imback).setOnClickListener(this);
		mtitle = (TextView) findViewById(R.id.title);
		mtitle.setOnClickListener(this);
		xListView = (XListView) findViewById(R.id.xListView);
		myListViewAdapter = new MyListViewAdapter();
		xListView.setPullLoadEnable(true);
		xListView.setPullRefreshEnable(false);
		xListView.setXListViewListener(this);
		xListView.setAdapter(myListViewAdapter);
		findViewById(R.id.tvranking).setOnClickListener(this);// 按排名
		findViewById(R.id.tvalexa).setOnClickListener(this);// 按ALEXA排名
		findViewById(R.id.tvbdweight).setOnClickListener(this);// 按权重排名
		findViewById(R.id.tvpr).setOnClickListener(this);// 按PR排名
	}

	class MyListViewAdapter extends BaseAdapter {

		public int getCount() {
			return mdataList.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = getLayoutInflater().inflate(
					R.layout.listitem_ranking, null);
			TextView tvranking = (TextView) inflate
					.findViewById(R.id.tvranking);
			TextView tvweb = (TextView) inflate.findViewById(R.id.tvweb);
			TextView tvalexa = (TextView) inflate.findViewById(R.id.tvalexa);
			TextView tvbdweight = (TextView) inflate
					.findViewById(R.id.tvbdweight);
			TextView tvpr = (TextView) inflate.findViewById(R.id.tvpr);
			TextView tvspeed_ranking = (TextView) inflate
					.findViewById(R.id.tvspeed_ranking);
			JSONObject jsonObject = mdataList.get(position);
			try {
				tvranking.setText(jsonObject.getString("ranking"));
				tvweb.setText(jsonObject.getString("wname"));
				tvalexa.setText(jsonObject.getString("alexa"));
				tvbdweight.setText(jsonObject.getString("bdweight"));
				tvpr.setText(jsonObject.getString("pr"));
				tvspeed_ranking.setText(jsonObject.getString("speed_ranking"));
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return inflate;
		}
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

		@Override
		public int getCount() {
			return list.size();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.tvranking:
			mdataList.clear();
			order_by = "ranking";
			px = 1;
			getData();
			break;
		case R.id.tvalexa:
			mdataList.clear();
			order_by = "alexa";
			px = 1;
			getData();
			break;
		case R.id.tvbdweight:
			mdataList.clear();
			order_by = "bdweight";
			px = 1;
			getData();
			break;
		case R.id.tvpr:
			mdataList.clear();
			order_by = "pr";
			px = 1;
			getData();
			break;
		case R.id.title:
			if (id_list.size() != 0) {
				ShowSelectWind();
			}
			break;

		}
	}

	private void ShowSelectWind() {
		final AlertDialog show = new AlertDialog.Builder(this).show();
		Window window = show.getWindow();
		View inflate = getLayoutInflater().inflate(R.layout.activity_gridview,
				null);
		GridView mGridView = (GridView) inflate.findViewById(R.id.gridView1);
		mGridView.setAdapter(new MyGridViewAdapter());
		window.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL);
		window.setContentView(inflate);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				try {
					mtitle.setText("" + id_list.get(position).getString("name"));
					show.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}

	class MyGridViewAdapter extends BaseAdapter {

		public int getCount() {
			return id_list.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = getLayoutInflater().inflate(
					R.layout.activity_gridview_item, null);
			TextView tv_item = (TextView) inflate.findViewById(R.id.tv_item);
			try {
				tv_item.setText(id_list.get(position).getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return inflate;
		}

	}

	public void onRefresh() {

	}

	public void onLoadMore() {
		px++;
		getData();
	}
}
