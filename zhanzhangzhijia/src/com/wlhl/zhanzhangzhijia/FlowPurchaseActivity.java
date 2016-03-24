package com.wlhl.zhanzhangzhijia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.wlhl.hong.XListView;
import com.wlhl.hong.XListView.IXListViewListener;
import com.xinbo.utils.GetSetUntils;

public class FlowPurchaseActivity extends Activity implements
		IXListViewListener, OnClickListener {
	private XListView mListView;
	private MyAdapter myAdapter;
	public String us;
	public ArrayList<JSONObject> orderlist = new ArrayList<JSONObject>();
	private TextView xinzenggoumai;
	private AlertDialog dialog;
	private String tatal;
	private String balance;
	private String username;
	public int px = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flowpurchase);
		us = GetUntils.getInstance().getUS(this);
		initView();
		getData();
		initGson();
	}

	private void initGson() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.USER, params, new VolleyListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				GetSetUntils.setToast(FlowPurchaseActivity.this, false, "还未登录");
				mListView.setPullLoadEnable(false);
			}

			@Override
			public void onResponse(String arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0);
					JSONObject datas = object.optJSONObject("datas");
					balance = datas.optString("balance");
					username = datas.optString("username");
				} catch (JSONException e) {
				}
			}
		});
	}

	private void getData() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		params.put("px", "" + px);
		params.put("pz", "6");
		MyVolley.post(this, Constant.MYURL.MYORDERLIST, params,
				new VolleyListener() {
					public void onErrorResponse(VolleyError arg0) {

					}

					public void onResponse(String arg0) {
						try {
							JSONObject json = new JSONObject(arg0);
							JSONObject datas = json.optJSONObject("datas");
							Integer num_rows = Integer.valueOf(datas
									.optString("num_rows"));
							if (num_rows == 0) {
								mListView.setVisibility(View.GONE);
							} else {
								mListView.setVisibility(View.VISIBLE);
								JSONArray data = datas.optJSONArray("data");
								for (int i = 0; i < data.length(); i++) {
									JSONObject real_data = new JSONObject(
											String.valueOf(data.get(i)));
									orderlist.add(real_data);
								}
							}
							myAdapter.notifyDataSetChanged();
							mListView.stopLoadMore();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initView() {
		xinzenggoumai = (TextView) findViewById(R.id.xinzenggoumai);
		xinzenggoumai.setOnClickListener(this);
		mListView = (XListView) findViewById(R.id.listView1);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		myAdapter = new MyAdapter();
		mListView.setAdapter(myAdapter);
	}

	class MyAdapter extends BaseAdapter {

		private JSONObject data;
		private String reurl_title;
		private String reurl;
		private String no;
		private String website_name;
		private String status;
		private BigDecimal bd;

		public int getCount() {
			return orderlist.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			MyViewHolder myViewHolder = null;
			final int index = position;
			if (convertView == null) {
				myViewHolder = new MyViewHolder();
				convertView = getLayoutInflater().inflate(
						R.layout.listitem_flowpurchase, null);

				myViewHolder.tvno = (TextView) convertView
						.findViewById(R.id.tvno);
				myViewHolder.tvgoods_type = (TextView) convertView
						.findViewById(R.id.tvgoods_type);
				myViewHolder.tvcategory_name = (TextView) convertView
						.findViewById(R.id.tvcategory_name);
				myViewHolder.tvreurl_title = (TextView) convertView
						.findViewById(R.id.tvreurl_title);
				myViewHolder.tvreurl = (TextView) convertView
						.findViewById(R.id.tvreurl);
				myViewHolder.tvgoods_paytype = (TextView) convertView
						.findViewById(R.id.tvgoods_paytype);
				myViewHolder.tvr_price = (TextView) convertView
						.findViewById(R.id.tvr_price);
				myViewHolder.tvprice = (TextView) convertView
						.findViewById(R.id.tvprice);
				myViewHolder.tvstatus = (TextView) convertView
						.findViewById(R.id.tvstatus);
				myViewHolder.tvwebsite_name = (TextView) convertView
						.findViewById(R.id.tvwebsite_name);

				myViewHolder.linear1 = (LinearLayout) convertView
						.findViewById(R.id.linear1);
				myViewHolder.linear0 = (LinearLayout) convertView
						.findViewById(R.id.linear0);
				myViewHolder.linear2 = (LinearLayout) convertView
						.findViewById(R.id.linear2);

				myViewHolder.zanting = (TextView) convertView
						.findViewById(R.id.zanting);
				convertView.setTag(myViewHolder);
			} else {
				myViewHolder = (MyViewHolder) convertView.getTag();
			}
			try {
				data = orderlist.get(position);
				status = data.getString("status");
				tatal = data.getString("price");
				myViewHolder.tvgoods_type.setText(data.getString("goods_type"));
				myViewHolder.tvcategory_name.setText(data
						.getString("category_name"));
				no = data.getString("no");
				website_name = data.getString("website_name");
				reurl_title = data.getString("reurl_title");
				reurl = data.getString("reurl");
				myViewHolder.tvwebsite_name.setText(website_name);
				myViewHolder.tvno.setText(no);
				myViewHolder.tvreurl_title.setText(reurl_title);
				myViewHolder.tvreurl.setText(reurl);
				myViewHolder.tvgoods_paytype.setText(data
						.getString("goods_paytype"));
				myViewHolder.tvr_price.setText(data.getString("r_price"));
				myViewHolder.tvprice.setText(data.getString("price"));
				myViewHolder.tvstatus.setText(status);

				if (status.equals("订单生成")) {
					myViewHolder.linear1.setVisibility(View.VISIBLE);
					myViewHolder.linear0.setVisibility(View.GONE);
					myViewHolder.linear2.setVisibility(View.GONE);
				} else if (status.equals("终止服务")) {
					myViewHolder.linear0.setVisibility(View.VISIBLE);
					myViewHolder.linear1.setVisibility(View.GONE);
					myViewHolder.linear2.setVisibility(View.GONE);
				} else if (status.equals("服务中")) {
					myViewHolder.linear0.setVisibility(View.VISIBLE);
					myViewHolder.linear1.setVisibility(View.GONE);
					myViewHolder.linear2.setVisibility(View.GONE);
				} else if (status.equals("交易中")) {
					myViewHolder.linear2.setVisibility(View.VISIBLE);
					myViewHolder.linear1.setVisibility(View.GONE);
					myViewHolder.linear0.setVisibility(View.GONE);
				} else if (status.equals("暂停服务")) {
					myViewHolder.zanting.setText("开启");
					myViewHolder.linear2.setVisibility(View.VISIBLE);
					myViewHolder.linear1.setVisibility(View.GONE);
					myViewHolder.linear0.setVisibility(View.GONE);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			convertView.findViewById(R.id.shenqingguzhang).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							JSONObject data = orderlist.get(index);
							try {
								String id = data.getString("id");
								String no = data.getString("no");
								String reurl_title = data
										.getString("reurl_title");
								String reurl = data.getString("reurl");
								String website_name = data
										.getString("website_name");
								Intent intent = new Intent();
								intent.setClass(FlowPurchaseActivity.this,
										BreakdownActivity.class);
								intent.putExtra("id", id);
								intent.putExtra("no", no);
								intent.putExtra("reurl", reurl);
								intent.putExtra("reurl_title", reurl_title);
								intent.putExtra("website_name", website_name);
								startActivity(intent);
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});

			convertView.findViewById(R.id.dingdanrizhi1).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							JSONObject data = orderlist.get(index);
							try {
								String id = data.getString("id");
								Intent intent = new Intent();
								intent.setClass(FlowPurchaseActivity.this,
										OrderLogActivity.class);
								intent.putExtra("id", id);
								startActivity(intent);
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});
			convertView.findViewById(R.id.dingdanrizhi2).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							JSONObject data = orderlist.get(index);
							try {
								String id = data.getString("id");
								Intent intent = new Intent();
								intent.setClass(FlowPurchaseActivity.this,
										OrderLogActivity.class);
								intent.putExtra("id", id);
								startActivity(intent);
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});

			convertView.findViewById(R.id.fukuan).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							dialog();
						}
					});
			return convertView;
		}

		protected void dialog() {

			Builder builder = new AlertDialog.Builder(FlowPurchaseActivity.this);
			dialog = builder.show();
			Window window1 = dialog.getWindow();
			View view = getLayoutInflater().inflate(R.layout.dialog_payment,
					null);
			TextView tv_tatal = (TextView) view.findViewById(R.id.tv_tatal);
			TextView tv_balance = (TextView) view.findViewById(R.id.tv_balance);
			TextView tv_top1 = (TextView) view.findViewById(R.id.tv_top1);
			Button bt_pay = (Button) view.findViewById(R.id.bt_pay);
			tv_tatal.setText("总计：￥" + tatal);
			tv_balance.setText("余额：￥" + balance);
			Double tatal_1 = Double.valueOf(tatal);
			Double tv_balance_1 = Double.valueOf(balance);
			Double result = tv_balance_1 - tatal_1;
			double abs = Math.abs(result);
			bd = new BigDecimal(abs);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			if ((Double.valueOf(balance) >= Double.valueOf(tatal))) {
				bt_pay.setText("付款");
				// tv_top1.setText("￥ 0");
			} else {

			}
			bt_pay.setText("充值");
			tv_top1.setText("￥" + bd);
			bt_pay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					TopDialog();
				}
			});
			window1.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
			window1.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			window1.setContentView(view);
		}

		protected void TopDialog() {
			dialog.dismiss();
			Builder builder = new AlertDialog.Builder(FlowPurchaseActivity.this);
			dialog = builder.show();
			Window window2 = dialog.getWindow();
			View view = getLayoutInflater().inflate(R.layout.dialog_top, null);
			TextView topname = (TextView) view.findViewById(R.id.topname);
			TextView topbalance = (TextView) view.findViewById(R.id.topbalance);
			TextView topmoney = (TextView) view.findViewById(R.id.topmoney);
			TextView top_tvcancel = (TextView) view
					.findViewById(R.id.top_tvcancel);
			TextView top_tvok = (TextView) view.findViewById(R.id.top_tvok);
			topname.setText(username);
			topbalance.setText(balance);
			topmoney.setText("" + bd);
			top_tvcancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
				}
			});
			top_tvok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					Builder builder = new AlertDialog.Builder(
							FlowPurchaseActivity.this);
					dialog = builder.show();
					Window window3 = dialog.getWindow();
					View view = getLayoutInflater().inflate(
							R.layout.dialog_top_ok, null);
					TextView topokname = (TextView) view
							.findViewById(R.id.topokname);
					TextView topokbalance = (TextView) view
							.findViewById(R.id.topokbalance);
					TextView topok_pay = (TextView) view
							.findViewById(R.id.topok_pay);
					topokname.setText(username);
					topokbalance.setText("" + bd);
					topok_pay.setOnClickListener(FlowPurchaseActivity.this);
					window3.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
					window3.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
					window3.setContentView(view);

				}
			});

			window2.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
			window2.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			window2.setContentView(view);
		}

		class MyViewHolder {
			TextView tvno;
			TextView tvgoods_type;
			TextView tvcategory_name;
			TextView tvreurl_title;
			TextView tvreurl;
			TextView tvgoods_paytype;
			TextView tvr_price;
			TextView tvstatus;
			TextView tvwebsite_name;
			TextView tvprice;

			LinearLayout linear0;
			LinearLayout linear1;
			LinearLayout linear2;

			TextView zanting;
		}

	}

	public void onRefresh() {

	}

	public void onLoadMore() {
		px++;
		getData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xinzenggoumai:
			Intent intent = new Intent(FlowPurchaseActivity.this,
					NewPurchaseActivity.class);
			startActivity(intent);
			break;
		case R.id.topok_pay:
			dialog.dismiss();
			Intent intent1 = new Intent(FlowPurchaseActivity.this,
					PayOkActivity.class);
			startActivity(intent1);// 成功后跳转至成功界面
		}
	}
}
