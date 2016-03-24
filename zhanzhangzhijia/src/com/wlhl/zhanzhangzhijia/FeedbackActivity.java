package com.wlhl.zhanzhangzhijia;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.GetSetUntils;

public class FeedbackActivity extends Activity implements OnClickListener {
	private EditText etfeedback;
	private TextView tvok;
	private TextView tvcount;
	private int max_length = 400;
	private String us;
	private Spinner sp_title;
	private EditText edtcontact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 状态栏透明
		setContentView(R.layout.activity_feedback);
		initUI();
		us = GetUntils.getInstance().getUS(this);
	}

	private void initUI() {
		sp_title = (Spinner) findViewById(R.id.sp_title);
		edtcontact = (EditText) findViewById(R.id.edtcontact);
		ImageView imback = (ImageView) findViewById(R.id.imback);
		etfeedback = (EditText) findViewById(R.id.editText2);
		tvok = (TextView) findViewById(R.id.tvok);
		tvcount = (TextView) findViewById(R.id.tvcount);
		tvcount.setText("" + max_length);
		imback.setOnClickListener(this);
		tvok.setOnClickListener(this);
		InputFilter[] filters = { new InputFilter.LengthFilter(max_length) };
		etfeedback.setFilters(filters);
		etfeedback.addTextChangedListener(passwordListener());
	}

	/**
	 * 检查字数
	 * 
	 * @return TextWatcher
	 */
	private TextWatcher passwordListener() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int length = s.toString().length();
				tvcount.setText(length + "/" + max_length);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imback:
			finish();
			break;
		case R.id.tvok:
			submitFeedback();// 提交
			break;
		}
	}

	/**
	 * 提交留言submit feedback
	 */
	private void submitFeedback() {
		String title = sp_title.getSelectedItem().toString();
		String touch = edtcontact.getText().toString();
		if (!"".equals(etfeedback.getText().toString().trim())) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("userauth", us);
			params.put("title", title);
			params.put("info", etfeedback.getText().toString());
			params.put("touch", touch + "");
			MyVolley.post(this, Constant.MYURL.FEEDBACK, params,
					new VolleyListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {
							GetSetUntils.setToast(FeedbackActivity.this, false,
									"网络异常 ,稍后重试");
						}

						@Override
						public void onResponse(String arg0) {
							try {
								JSONObject jsonObject = new JSONObject(arg0);
								int statusCode = jsonObject
										.optInt("statusCode");
								String message = jsonObject
										.optString("message");
								if (statusCode == 200) {
									GetSetUntils.setToast(
											FeedbackActivity.this, true,
											message);
									finish();
								} else {
									GetSetUntils.setToast(
											FeedbackActivity.this, false,
											message);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		} else {
			GetSetUntils.setToast(this, false, "请输入您的反馈内容");
		}
	}

}
