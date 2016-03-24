package com.wlhl.zhanzhangzhijia;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.GetSetUntils;

public class WebInfoActivity extends Activity implements OnClickListener {
	private String id;
	private String us;
	private LinearLayout linearLayout1;
	private RelativeLayout relativeLayout;
	private int State_WebInfoActivity = 1;
	private ArrayList<JSONObject> mdataList = new ArrayList<JSONObject>();
	private TextView tvname;
	private TextView tvwebname;
	private TextView tvurl;
	private TextView tvcompany;
	private TextView tvdistrict;
	private TextView tvcname;
	private TextView tvpoint;
	private TextView tvpointch;
	private TextView tvfounder;// 创始人字段
	private TextView tvabstract;
	private TextView tvworldrank;
	private TextView tvbdweight;
	private TextView tvtraffic;
	private TextView tvbdincluded;
	private TextView tvtrafficrank;
	private TextView tvgoogleweight;
	private TextView tvgoogleincluded;
	private TextView tvgoogletrans;
	private TextView tvtomonth_included;
	private TextView tvindex;
	private TextView tvbdtrans;
	private TextView tvkeyword;
	private TextView tv360included;
	private TextView tv360trans;
	private TextView tvsougouincluded;
	private TextView tvsougoutrans;
	private TextView tvcompany_name;
	private TextView tvnature;
	private TextView tvno;
	private TextView tvwebsitename;
	private TextView tvwebsiteindex;
	private TextView tvip;
	private TextView tvservertype;
	private TextView tvrsp;
	private TextView tvdns;
	private TextView tvend_time_fmt;
	private TextView tvcontact_name;
	private TextView tvcontact_sex;
	private TextView tvcontact_phone;
	private TextView tvcontact_email;
	private TextView tvweibo;
	private TextView tvweixin;
	private TextView tvqq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webinfo);
		us = GetUntils.getInstance().getUS(getApplicationContext());
		initview();// 初始化布局;
		ifview();// 通过是否登陆，判断登陆按钮是否显示.
		getintent();// 获取点击的ID;
		getdata();
	}

	private void getdata() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		MyVolley.post(getApplicationContext(), Constant.MYURL.WEBINFO, params,
				new VolleyListener() {

					public void onErrorResponse(VolleyError arg0) {
					}

					public void onResponse(String arg0) {
						try {
							JSONObject jsonObject = new JSONObject(arg0);
							if (jsonObject.optInt("statusCode") == 500) {
								GetSetUntils.setToast(WebInfoActivity.this,
										false, jsonObject.optString("message"));
							} else {
								JSONObject datas = jsonObject
										.optJSONObject("datas");
								if (datas.length() == 1) {
								} else {
									JSONArray data = datas.optJSONArray("data");
									if (data.length() != 0) {
										for (int i = 0; i < data.length(); i++) {
											JSONObject aa = new JSONObject(
													String.valueOf(data.get(i)));
											mdataList.add(aa);
										}
										JSONObject json = mdataList.get(0);
										tvname.setText(json.optString("name"));
										tvabstract.setText("简介:"
												+ json.optString("abstract"));
										tvwebname.setText(json
												.optString("name"));
										tvurl.setText(json.optString("url"));
										tvcompany.setText(json
												.optString("company"));
										tvdistrict.setText(json
												.optString("district"));
										tvcname.setText(json.optString("cname"));
										tvpoint.setText(json.optString("point"));
										tvpointch.setText(json
												.optString("pointch"));

										tvworldrank.setText(json
												.optString("worldrank"));
										tvbdweight.setText(json
												.optString("bdweight"));
										tvtraffic.setText(json
												.optString("traffic"));
										tvbdincluded.setText(json
												.optString("bdincluded"));
										tvtrafficrank.setText(json
												.optString("trafficrank"));
										tvgoogleweight.setText(json
												.optString("googleweight"));
										tvgoogleincluded.setText(json
												.optString("googleincluded"));
										tvgoogletrans.setText(json
												.optString("googletrans"));
										tvtomonth_included.setText(json
												.optString("tomonth_included"));
										tvindex.setText(json.optString("index"));
										tvbdtrans.setText(json
												.optString("bdtrans"));
										tvkeyword.setText(json
												.optString("keyword"));
										tv360included.setText(json
												.optString("360included"));
										tv360trans.setText(json
												.optString("360trans"));
										tvsougouincluded.setText(json
												.optString("sougouincluded"));
										tvsougoutrans.setText(json
												.optString("sougoutrans"));

										tvcompany_name.setText(json
												.optString("company_name"));
										tvnature.setText(json
												.optString("nature"));
										tvno.setText(json.optString("no"));
										tvwebsitename.setText(json
												.optString("websitename"));
										tvwebsiteindex.setText(json
												.optString("websiteindex"));

										tvip.setText(json.optString("ip"));
										tvservertype.setText(json
												.optString("servertype"));
										tvrsp.setText(json.optString("rsp"));
										tvdns.setText(json.optString("dns"));
										tvend_time_fmt.setText(json
												.optString("end_time_fmt"));

										tvcontact_name.setText(json
												.optString("contact_name"));
										tvcontact_sex.setText(json
												.optString("contact_sex"));
										tvcontact_phone.setText(json
												.optString("contact_phone"));
										tvcontact_email.setText(json
												.optString("contact_email"));
										tvweibo.setText(json.optString("weibo"));
										tvweixin.setText(json
												.optString("weixin"));
										tvqq.setText(json.optString("qq"));
									}
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void ifview() {
		us = GetUntils.getInstance().getUS(getApplicationContext());
		if (us == null || "".equals(us)) {
			linearLayout1.setVisibility(View.GONE);
			relativeLayout.setVisibility(View.VISIBLE);
		} else {
			linearLayout1.setVisibility(View.VISIBLE);
			relativeLayout.setVisibility(View.GONE);
		}
	}

	private void getintent() {
		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
	}

	private void initview() {
		findViewById(R.id.btnwebtrade).setOnClickListener(this);
		findViewById(R.id.tvranking).setOnClickListener(this);
		linearLayout1 = (LinearLayout) findViewById(R.id.linerlayout1);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
		ImageView back = (ImageView) findViewById(R.id.imback);
		back.setOnClickListener(this);
		findViewById(R.id.btnlogin).setOnClickListener(this);
		tvname = (TextView) findViewById(R.id.tvname);
		tvwebname = (TextView) findViewById(R.id.tvwebname);
		tvurl = (TextView) findViewById(R.id.tvurl);
		tvcompany = (TextView) findViewById(R.id.tvcompany);
		tvdistrict = (TextView) findViewById(R.id.tvdistrict);
		tvcname = (TextView) findViewById(R.id.tvcname);
		tvpoint = (TextView) findViewById(R.id.tvpoint);
		tvpointch = (TextView) findViewById(R.id.tvpointch);
		tvfounder = (TextView) findViewById(R.id.tvfounder);
		tvabstract = (TextView) findViewById(R.id.tvabstract);

		tvworldrank = (TextView) findViewById(R.id.tvworldrank);
		tvtraffic = (TextView) findViewById(R.id.tvtraffic);
		tvbdweight = (TextView) findViewById(R.id.tvbdweight);
		tvbdincluded = (TextView) findViewById(R.id.tvbdincluded);
		tvtrafficrank = (TextView) findViewById(R.id.tvtrafficrank);
		tvgoogleweight = (TextView) findViewById(R.id.tvgoogleweight);
		tvgoogleincluded = (TextView) findViewById(R.id.tvgoogleincluded);
		tvgoogletrans = (TextView) findViewById(R.id.tvgoogletrans);
		tvtomonth_included = (TextView) findViewById(R.id.tvtomonth_included);
		tvindex = (TextView) findViewById(R.id.tvindex);
		tvbdtrans = (TextView) findViewById(R.id.tvbdtrans);
		tvkeyword = (TextView) findViewById(R.id.tvkeyword);
		tv360included = (TextView) findViewById(R.id.tv360included);
		tv360trans = (TextView) findViewById(R.id.tv360trans);
		tvsougouincluded = (TextView) findViewById(R.id.tvsougouincluded);
		tvsougoutrans = (TextView) findViewById(R.id.tvsougoutrans);

		tvcompany_name = (TextView) findViewById(R.id.tvcompany_name);
		tvnature = (TextView) findViewById(R.id.tvnature);
		tvno = (TextView) findViewById(R.id.tvno);
		tvwebsitename = (TextView) findViewById(R.id.tvwebsitename);
		tvwebsiteindex = (TextView) findViewById(R.id.tvwebsiteindex);

		tvip = (TextView) findViewById(R.id.tvip);
		tvservertype = (TextView) findViewById(R.id.tvservertype);
		tvrsp = (TextView) findViewById(R.id.tvrsp);
		tvdns = (TextView) findViewById(R.id.tvdns);
		tvend_time_fmt = (TextView) findViewById(R.id.tvend_time_fmt);

		tvcontact_name = (TextView) findViewById(R.id.tvcontact_name);
		tvcontact_sex = (TextView) findViewById(R.id.tvcontact_sex);
		tvcontact_phone = (TextView) findViewById(R.id.tvcontact_phone);
		tvcontact_email = (TextView) findViewById(R.id.tvcontact_email);
		tvweibo = (TextView) findViewById(R.id.tvweibo);
		tvweixin = (TextView) findViewById(R.id.tvweixin);
		tvqq = (TextView) findViewById(R.id.tvqq);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.btnlogin:
			Login();
			break;
		case R.id.tvranking:
			toRanking();
			break;
		case R.id.btnwebtrade:
			toRanking();
			break;

		}

	}

	private void toRanking() {
		startActivity(new Intent().setClass(WebInfoActivity.this,
				WebRanking.class));
	}

	private void Login() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), LoginActivity.class);
		startActivityForResult(intent, State_WebInfoActivity);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			ifview();
			break;

		default:
			break;
		}
	}
}