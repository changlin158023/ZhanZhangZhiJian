package com.wlhl.zhanzhangzhijia;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.GetSetUntils;

public class BreakdownActivity extends Activity implements OnClickListener {

	private EditText etbecause;
	private String id;
	public String us;
	private String no;
	private String reurl;
	private String reurl_title;
	private String website_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breakdown);
		getdata();
		initUI();
	}

	private void getdata() {
		us = GetUntils.getInstance().getUS(this);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		no = intent.getStringExtra("no");
		reurl = intent.getStringExtra("reurl");
		reurl_title = intent.getStringExtra("reurl_title");
		website_name = intent.getStringExtra("website_name");
	}

	private void initUI() {
		ImageView imback = (ImageView) findViewById(R.id.imback);
		View fracommit = findViewById(R.id.fracommit);
		etbecause = (EditText) findViewById(R.id.editText2);
		fracommit.setOnClickListener(this);
		imback.setOnClickListener(this);
		TextView tvnum = (TextView) findViewById(R.id.tvnum);
		TextView tvwebsite = (TextView) findViewById(R.id.tvwebsite);
		TextView tvtitle = (TextView) findViewById(R.id.tvtitle);
		TextView tvurl = (TextView) findViewById(R.id.tvurl);
		tvnum.setText(no);
		tvwebsite.setText(website_name);
		tvtitle.setText(reurl_title);
		tvurl.setText(reurl);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.fracommit:
			String because = etbecause.getText().toString().trim();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", id);
			params.put("userauth", us);
			params.put("because", because);
			MyVolley.post(this, Constant.MYURL.UPDATAODER, params,
					new VolleyListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
							GetSetUntils.setToast(BreakdownActivity.this,
									false, "网络异常");
						}

						@Override
						public void onResponse(String arg0) {

							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(arg0);
								int statusCode = jsonObject
										.optInt("statusCode");
								String message = jsonObject
										.optString("message");
								if (statusCode == 200) {
									Intent intent = new Intent(
											BreakdownActivity.this,
											BreakdownOkActivity.class);
									intent.putExtra("id", no);
									startActivity(intent);
									finish();
								} else {
									GetSetUntils.setToast(
											BreakdownActivity.this, false,
											message);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});
			break;
		}
	}

}
