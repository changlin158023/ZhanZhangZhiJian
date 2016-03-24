package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wlhl.fragment.BannerFragment;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.ExitApp;
import com.xinbo.utils.GetSetUntils;

public class HomeActivity2 extends FragmentActivity implements OnClickListener {
	private Handler handler;
	private Runnable thread;
	private int currentItem = 30000;
	private int totalItem = 60000;
	private int bannerNum = 3;
	private ViewPager vp;
	private boolean isDragging;
	private ArrayList<TextView> mtvList = new ArrayList<TextView>();
	private ArrayList<JSONObject> mdataList1 = new ArrayList<JSONObject>();
	private ArrayList<JSONObject> mdataList2 = new ArrayList<JSONObject>();
	private ArrayList<JSONObject> mdataList3 = new ArrayList<JSONObject>();
	private String us;
	private Ladapter adapter;
	private TextView tvmessage;
	private RadioGroup rg;
	private int ischeckbutton = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_activity2);
		us = GetUntils.getInstance().getUS(this);
		initUI();
		getJPTJ();
	}

	/**
	 * 获取精品推荐数据
	 */
	private void getJPTJ() {
		if (mdataList1.size() != 0) {

		} else {
			mdataList1.clear();
			MyVolley.get(this, Constant.MYURL.JPTJ, new VolleyListener() {

				public void onErrorResponse(VolleyError arg0) {

				}

				public void onResponse(String arg0) {
					try {
						JSONObject JSON = new JSONObject(arg0);
						JSONObject datasObject = JSON.optJSONObject("datas");
						JSONArray dataArray = datasObject.optJSONArray("data");
						for (int i = 0; i < dataArray.length(); i++) {
							JSONObject aa = new JSONObject(String
									.valueOf(dataArray.get(i)));
							mdataList1.add(aa);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			});
		}

	}

	private void getCSPH() {
		if (mdataList2.size() != 0) {

		} else {
			MyVolley.get(this, Constant.MYURL.CSPH, new VolleyListener() {

				public void onErrorResponse(VolleyError arg0) {

				}

				public void onResponse(String arg0) {
					try {
						JSONObject JSON = new JSONObject(arg0);
						JSONObject datasObject = JSON.optJSONObject("datas");
						JSONArray dataArray = datasObject.optJSONArray("data");
						for (int i = 0; i < dataArray.length(); i++) {
							JSONObject aa = new JSONObject(String
									.valueOf(dataArray.get(i)));
							mdataList2.add(aa);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	private void getZXWZ() {
		if (mdataList3.size() != 0) {

		} else {

			MyVolley.get(this, Constant.MYURL.ZXWZ, new VolleyListener() {

				public void onErrorResponse(VolleyError arg0) {

				}

				public void onResponse(String arg0) {
					try {
						JSONObject JSON = new JSONObject(arg0);
						JSONObject datasObject = JSON.optJSONObject("datas");
						JSONArray dataArray = datasObject.optJSONArray("data");
						for (int i = 0; i < dataArray.length(); i++) {
							JSONObject aa = new JSONObject(String
									.valueOf(dataArray.get(i)));
							mdataList3.add(aa);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * 初始化控件 init UI
	 */
	private void initUI() {
		tvmessage = (TextView) findViewById(R.id.tvmessage);
		final View viewTitle = findViewById(R.id.LinearLayout2);
		final PullToRefreshListView ptr = (PullToRefreshListView) findViewById(R.id.ptrl);
		ptr.setMode(Mode.PULL_FROM_END);
		ptr.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
		setmyRefreshListener(ptr);
		ListView lv = ptr.getRefreshableView();
		addhead(lv);// addheadview
		adapter = new Ladapter();
		lv.setAdapter(adapter);
		tvmessage.setOnClickListener(this);
		ptr.setOnScrollListener(new OnScrollListener() {// 标题滚动悬浮
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem > 2) {
					viewTitle.setVisibility(View.VISIBLE);
				} else {
					viewTitle.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	/**
	 * 上拉刷新和下拉刷新 onrefreshListener
	 * 
	 * @param ptr
	 */
	@SuppressWarnings("unchecked")
	private void setmyRefreshListener(PullToRefreshListView ptr) {
		ptr.setOnRefreshListener(new OnRefreshListener() {
			public void onRefresh(final PullToRefreshBase refreshView) {
				tvmessage.postDelayed(new Runnable() {
					public void run() {
						refreshView.onRefreshComplete();
					}
				}, 2000);
			}
		});
	}

	/**
	 * 加头 addHeadView
	 * 
	 * @param lv
	 */
	private void addhead(ListView lv) {
		View banner = LayoutInflater.from(this).inflate(R.layout.head_banner,
				null);
		LinearLayout indicator = (LinearLayout) banner
				.findViewById(R.id.linear);
		for (int i = 0; i < bannerNum; i++) {
			TextView tv0 = new TextView(this);
			tv0.setBackgroundResource(R.drawable.ovalwhite);
			LayoutParams params = new LinearLayout.LayoutParams(30, 30);
			indicator.addView(tv0, params);// 添加指示器
			mtvList.add(tv0);
		}
		vp = (ViewPager) banner.findViewById(R.id.vp);
		vp.setAdapter(new VPadapter(getSupportFragmentManager()));
		autoBanner(vp);
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentItem = arg0;
				for (int i = 0; i < bannerNum; i++) {
					if (i == currentItem % bannerNum) {
						mtvList.get(i).setBackgroundResource(
								R.drawable.ovalblue);
					} else {
						mtvList.get(i).setBackgroundResource(
								R.drawable.ovalwhite);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (ViewPager.SCROLL_STATE_DRAGGING == arg0) {
					isDragging = true;// 按住时为true
				} else {
					isDragging = false;
				}
			}
		});
		lv.addHeaderView(banner);// 加头 广告banner

		View classify = LayoutInflater.from(this).inflate(
				R.layout.head_classify, null);
		// 消费信息*
		// TextView tvconsumed1 = (TextView) classify
		// .findViewById(R.id.tvconsumed1);
		// TextView tvconsumed2 = (TextView) classify
		// .findViewById(R.id.tvconsumed2);
		// TextView tvleave = (TextView) classify.findViewById(R.id.tvleave);
		// tvconsumed1.setText("100.00");
		// tvconsumed2.setText("1000.81");
		// tvleave.setText("2.01");

		TextView tvserach = (TextView) classify.findViewById(R.id.tvsearch);
		rg = (RadioGroup) classify.findViewById(R.id.radioGroup1);
		tvserach.setOnClickListener(this);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiorecommend:
					ischeckbutton = 0;
					getJPTJ();
					break;
				case R.id.radioranking:
					ischeckbutton = 1;
					getCSPH();
					break;
				case R.id.radioweb:
					ischeckbutton = 2;
					getZXWZ();
					break;
				}
				adapter.notifyDataSetChanged();
			}
		});

		lv.addHeaderView(classify);// 加头 分类信息
		View title = LayoutInflater.from(this).inflate(R.layout.head_title,
				null);
		lv.addHeaderView(title);// 详情标题
	}

	/**
	 * 自动广告条auto banner
	 * 
	 * @param vp
	 */
	private void autoBanner(final ViewPager vp) {
		handler = new Handler();
		handler.post(thread = new Runnable() {
			@Override
			public void run() {
				if (!isDragging) {
					vp.setCurrentItem(currentItem % totalItem, true);
					currentItem++;
				}
				handler.postDelayed(this, 3000);
			}
		});
	}

	/**
	 * 适配器listview adapter
	 */
	class Ladapter extends BaseAdapter {
		private int size;

		@Override
		public int getCount() {
			if (ischeckbutton == 0) {// 选项1
				size = mdataList1.size();
			} else if (ischeckbutton == 1) {// 选项2
				size = mdataList2.size();
			} else if (ischeckbutton == 2) {// 选项3
				size = mdataList3.size();
			}
			return size;
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
			View view;
			if (convertView == null) {
				view = getLayoutInflater()
						.inflate(R.layout.listitem_home, null);
			} else {
				view = convertView;
			}
			TextView tvproduct = (TextView) view.findViewById(R.id.tvproduct);
			TextView tvweb = (TextView) view.findViewById(R.id.tvweb);
			TextView tvweight = (TextView) view.findViewById(R.id.tvweight);
			TextView tvpr = (TextView) view.findViewById(R.id.tvpr);
			TextView tvprice = (TextView) view.findViewById(R.id.tvprice);
			TextView tvgo = (TextView) view.findViewById(R.id.tvgo);
			try {
				if (ischeckbutton == 0) {
					JSONObject jsonObject = mdataList1.get(position);
					tvproduct.setText(jsonObject.getString("type"));
					tvweb.setText(jsonObject.getString("name"));
					tvweight.setText(jsonObject.getString("bdweight"));
					tvpr.setText(jsonObject.getString("googleweight"));
					tvprice.setText(jsonObject.getString("price"));
				} else if (ischeckbutton == 1) {
					tvproduct.setText(mdataList2.get(position)
							.getString("type"));
					tvweb.setText(mdataList2.get(position).getString("name"));
					tvweight.setText(mdataList2.get(position).getString(
							"bdweight"));
					tvpr.setText(mdataList2.get(position).getString(
							"googleweight"));
					tvprice.setText(mdataList2.get(position).getString("price"));
				} else if (ischeckbutton == 2) {
					tvproduct.setText(mdataList3.get(position)
							.getString("type"));
					tvweb.setText(mdataList3.get(position).getString("name"));
					tvweight.setText(mdataList3.get(position).getString(
							"bdweight"));
					tvpr.setText(mdataList3.get(position).getString(
							"googleweight"));
					tvprice.setText(mdataList3.get(position).getString("price"));
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			final int index = position;
			tvgo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity2.this,
							BuySettingActivity.class);
					startActivity(intent);
				}
			});
			tvweb.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					try {
						if (ischeckbutton == 0) {
							Intent intent = new Intent(HomeActivity2.this,
									WebInfoActivity.class);
							String id = mdataList1.get(index).getString("id");
							intent.putExtra("id", "" + id);
							startActivity(intent);
						} else if (ischeckbutton == 1) {
							Intent intent = new Intent(HomeActivity2.this,
									WebInfoActivity.class);
							String id = mdataList2.get(index).getString("id");
							intent.putExtra("id", "" + id);
							startActivity(intent);
						} else if (ischeckbutton == 2) {
							Intent intent = new Intent(HomeActivity2.this,
									WebInfoActivity.class);
							String id = mdataList3.get(index).getString("id");
							intent.putExtra("id", "" + id);
							startActivity(intent);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			});
			return view;
		}
	}

	/**
	 * 广告条banner fragment
	 * 
	 */
	class VPadapter extends FragmentPagerAdapter {
		public VPadapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment frag = new BannerFragment();
			Bundle args = new Bundle();
			args.putInt("arg", currentItem % bannerNum);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public int getCount() {
			return totalItem;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvmessage:// 消息message
			Intent intentmessage = new Intent(this, MessageActivity.class);
			startActivity(intentmessage);
			break;
		case R.id.tvsearch:
			Intent intent = new Intent(this, SeoActivity.class);
			startActivity(intent);
			this.getParent().overridePendingTransition(0, 0);
			break;
		}
	}

	@Override
	protected void onRestart() {
		autoBanner(vp);
		super.onRestart();
	}

	@Override
	protected void onStop() {
		handler.removeCallbacks(thread);
		super.onStop();
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
