package com.wlhl.zhanzhangzhijia;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.xinbo.utils.GetSetUntils;

public class SeoActivity extends Activity implements OnClickListener {
	private EditText etsearch;
	private String us;
	private Button btnsearch;

	private TextView tvname;
	private TextView tvworldrank;
	private TextView tvtrafficrank;
	private TextView tvdaylipv;
	private TextView tvdayliip;
	private TextView tvbaidu_weight;
	private TextView tvgoogle_weight;
	private TextView tvip;
	private TextView tvinfo;
	private TextView tvresponsetime;
	private TextView tvsame;
	private TextView tvyear;
	private TextView tvcreate_time;
	private TextView tvend_time;
	private TextView tvno;
	private TextView tvnature;
	private TextView tvcompany_name;
	private TextView tvaudit_time;
	private TextView tvkeyword;
	private TextView tvtraffic;
	private TextView tvindex_posttion;
	private TextView tvsnapshot_time;
	private TextView tvindex;
	private TextView tvtomonth_included;
	private TextView tvtoweek_included;
	private TextView tvtoday_included;
	private TextView tvbaidu_trans;
	private TextView tvbaidu_included;
	private TextView tvgoogle_included;
	private TextView tvgoogle_trans;
	private TextView tv360_included;
	private TextView tv360_trans;
	private TextView tvsougou_included;
	private TextView tvsougou_trans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seo);
		// us = GetUntils.getInstance().getUS(this);
		initView();

	}

	private void initView() {
		etsearch = (EditText) findViewById(R.id.etsearch);
		btnsearch = (Button) findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(this);
		ImageView imback = (ImageView) findViewById(R.id.imback);
		imback.setOnClickListener(this);
		// 网站基本信息控件
		tvname = (TextView) findViewById(R.id.tvname);
		tvworldrank = (TextView) findViewById(R.id.tvworldrank);
		tvtrafficrank = (TextView) findViewById(R.id.tvtrafficrank);
		tvdayliip = (TextView) findViewById(R.id.tvdayliip);
		tvdaylipv = (TextView) findViewById(R.id.tvdaylipv);
		tvbaidu_weight = (TextView) findViewById(R.id.tvbaidu_weight);
		tvgoogle_weight = (TextView) findViewById(R.id.tvgoogle_weight);
		tvip = (TextView) findViewById(R.id.tvip);
		tvinfo = (TextView) findViewById(R.id.tvinfo);
		tvsame = (TextView) findViewById(R.id.tvsame);
		tvresponsetime = (TextView) findViewById(R.id.tvresponsetime);
		tvyear = (TextView) findViewById(R.id.tvyear);
		tvcreate_time = (TextView) findViewById(R.id.tvcreate_time);
		tvend_time = (TextView) findViewById(R.id.tvend_time);
		tvno = (TextView) findViewById(R.id.tvno);
		tvnature = (TextView) findViewById(R.id.tvnature);
		tvcompany_name = (TextView) findViewById(R.id.tvcompany_name);
		tvaudit_time = (TextView) findViewById(R.id.tvaudit_time);

		// 百度相关控件
		tvtraffic = (TextView) findViewById(R.id.tvtraffic);
		tvkeyword = (TextView) findViewById(R.id.tvkeyword);
		tvsnapshot_time = (TextView) findViewById(R.id.tvsnapshot_time);
		tvindex_posttion = (TextView) findViewById(R.id.tvindex_posttion);
		tvindex = (TextView) findViewById(R.id.tvindex);
		tvtoday_included = (TextView) findViewById(R.id.tvtoday_included);
		tvtoweek_included = (TextView) findViewById(R.id.tvtoweek_included);
		tvtomonth_included = (TextView) findViewById(R.id.tvtomonth_included);

		tvbaidu_trans = (TextView) findViewById(R.id.tvbaidu_trans);
		tvbaidu_included = (TextView) findViewById(R.id.tvbaidu_included);
		tvgoogle_trans = (TextView) findViewById(R.id.tvgoogle_trans);
		tvgoogle_included = (TextView) findViewById(R.id.tvgoogle_included);
		tv360_included = (TextView) findViewById(R.id.tv360_included);
		tv360_trans = (TextView) findViewById(R.id.tv360_trans);
		tvsougou_included = (TextView) findViewById(R.id.tvsougou_included);
		tvsougou_trans = (TextView) findViewById(R.id.tvsougou_trans);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.btnsearch:
			searchSEO();
			break;

		}
	}

	/**
	 * seo综合查询
	 */
	private void searchSEO() {
		String str_search = etsearch.getText().toString().trim();
		if (TextUtils.isEmpty(str_search)) {
			// 值为空
			GetSetUntils.setToast(this, false, "请输入网址!");
		} else {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("url", str_search);
			MyVolley.post(this, Constant.MYURL.SEOSEARCH, params,
					new VolleyListener() {
						public void onErrorResponse(VolleyError arg0) {
							GetSetUntils.setToast(SeoActivity.this, false,
									"网络异常，请重试");
						}

						public void onResponse(String arg0) {
							try {
								JSONObject json = new JSONObject(arg0);
								if (json.optInt("status") == 500) {
									GetSetUntils.setToast(SeoActivity.this,
											false, json.optString("message"));
								} else {
									JSONObject datasObject = json
											.optJSONObject("datas");
									if (datasObject.length() == 11) {

										// 获取各个object
										JSONObject base_info_object = datasObject
												.optJSONObject("base_info");
										JSONObject baidu_data_object = datasObject
												.optJSONObject("baidu_data");
										JSONObject aleax_data_object = datasObject
												.optJSONObject("aleax_data");
										JSONObject google_data_object = datasObject
												.optJSONObject("google_data");
										JSONObject _360_data_object = datasObject
												.optJSONObject("360_data");
										JSONObject sougou_data = datasObject
												.optJSONObject("sougou_data");
										JSONObject ip_data_object = datasObject
												.optJSONObject("ip");
										JSONObject record_data_object = datasObject
												.optJSONObject("record");
										JSONObject info2_data_object = datasObject
												.optJSONObject("info2");
										JSONObject info_data_object = datasObject
												.optJSONObject("info");

										// 获取网站基本信息
										String name = base_info_object
												.getString("name");
										String worldrank = aleax_data_object
												.getString("worldrank");
										String trafficrank = aleax_data_object
												.getString("trafficrank");
										String dayliip = aleax_data_object
												.getString("dayliip");
										String daylipv = aleax_data_object
												.getString("daylipv");

										String baidu_weight = baidu_data_object
												.getString("weight");
										String google_weight = google_data_object
												.getString("weight");
										String ip = ip_data_object
												.getString("ip");
										String info = ip_data_object
												.getString("info");
										String same = ip_data_object
												.getString("same");
										String responsetime = ip_data_object
												.getString("responsetime");

										String year = info_data_object
												.getString("year");
										String create_time = info2_data_object
												.getString("create_time");
										String end_time = info2_data_object
												.getString("end_time");
										String no = record_data_object
												.getString("no");
										String nature = record_data_object
												.getString("nature");
										String company_name = record_data_object
												.getString("company_name");
										String audit_time = record_data_object
												.getString("audit_time");

										// 获取百度相关信息
										String traffic = baidu_data_object
												.getString("traffic");
										String keyword = baidu_data_object
												.getString("keyword");
										String snapshot_time = baidu_data_object
												.getString("snapshot_time");
										String index_posttion = baidu_data_object
												.getString("index_posttion");
										String index = baidu_data_object
												.getString("index");
										String today_included = baidu_data_object
												.getString("today_included");
										String toweek_included = baidu_data_object
												.getString("toweek_included");
										String tomonth_included = baidu_data_object
												.getString("tomonth_included");
										// 网站收录/反链
										String baidu_included = baidu_data_object
												.getString("included");
										String baidu_trans = baidu_data_object
												.getString("trans");
										String google_included = google_data_object
												.getString("included");
										String google_trans = google_data_object
												.getString("trans");
										String _360_included = _360_data_object
												.getString("included");
										String _360_trans = _360_data_object
												.getString("trans");
										String sougou_included = sougou_data
												.getString("included");
										String sougou_trans = sougou_data
												.getString("trans");

										// 网站基本信息
										tvname.setText(name);
										tvworldrank
												.setText("世界排名:" + worldrank);
										tvtrafficrank.setText("流量排名"
												+ trafficrank);
										tvdayliip.setText("日均IP≈:" + dayliip);
										tvdaylipv.setText("日均PV≈:" + daylipv);
										tvbaidu_weight.setText("百度权重:"
												+ baidu_weight);
										tvgoogle_weight.setText("Google:"
												+ google_weight);
										tvip.setText("" + ip);
										tvinfo.setText("[" + info + "]");
										tvsame.setText("[同IP网站" + same + "个]");
										tvresponsetime.setText("[相应时间:"
												+ responsetime + "毫秒]");
										tvyear.setText(year + "年");
										tvcreate_time.setText("创建于"
												+ create_time);
										tvend_time.setText("过期时间为:" + end_time);
										tvno.setText("备案号:" + no);
										tvnature.setText("性质:" + nature);
										tvcompany_name.setText("名称"
												+ company_name);
										tvaudit_time.setText("审核时间:"
												+ audit_time);

										// 百度相关信息
										tvtraffic.setText(traffic);
										tvkeyword.setText(keyword);
										tvsnapshot_time.setText(snapshot_time);
										tvindex_posttion
												.setText(index_posttion);
										tvindex.setText(index);
										tvtoday_included
												.setText(today_included);
										tvtoweek_included
												.setText(toweek_included);
										tvtomonth_included
												.setText(tomonth_included);

										// 网站收录、反链
										tvbaidu_included
												.setText(baidu_included);
										tvbaidu_trans.setText(baidu_trans);
										tvbaidu_included
												.setText(baidu_included);
										tvgoogle_included
												.setText(google_included);
										tvgoogle_trans.setText(google_trans);
										tv360_included.setText(_360_included);
										tv360_trans.setText(_360_trans);
										tvsougou_included
												.setText(sougou_included);
										tvsougou_trans.setText(sougou_trans);

									} else {
										Toast.makeText(SeoActivity.this,
												"请输入正确的网址", Toast.LENGTH_SHORT)
												.show();
									}

								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});

		}

	}
}
