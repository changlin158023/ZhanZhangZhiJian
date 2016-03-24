package com.wlhl.zhanzhangzhijia;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.GetSetUntils;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText etaccount;
	private EditText etpassword;
	private TextView tvlogin;
	private ImageView imdelete1;
	private ImageView imdelete2;
	private String account;
	private String us;
	private TextView tvforget;
	private TextView tvregister;

	// private String sid;//session id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 状态栏透明
		setContentView(R.layout.activity_login);
		account = GetUntils.getInstance().getAccount(this);
		us = GetUntils.getInstance().getUS(this);
		initUI();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		if (us != null) {
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
		}
		etaccount = (EditText) findViewById(R.id.editText1);
		etpassword = (EditText) findViewById(R.id.editText2);
		imdelete1 = (ImageView) findViewById(R.id.imdelete1);
		imdelete2 = (ImageView) findViewById(R.id.imdelete2);
		tvlogin = (TextView) findViewById(R.id.tvlogin);
		tvforget = (TextView) findViewById(R.id.tvforget);
		tvregister = (TextView) findViewById(R.id.tvregister);
		tvlogin.setOnClickListener(this);
		imdelete1.setOnClickListener(this);
		imdelete2.setOnClickListener(this);
		tvforget.setOnClickListener(this);
		tvregister.setOnClickListener(this);
		etaccount.setText(account);
		etaccount.addTextChangedListener(accountListener());
		etpassword.addTextChangedListener(passwordListener());
		etaccount.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete2.setVisibility(View.INVISIBLE);
				if (!"".equals(etaccount.getText().toString())) {
					imdelete1.setVisibility(View.VISIBLE);
				} else {
					imdelete1.setVisibility(View.INVISIBLE);
				}
			}
		});
		etpassword.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete1.setVisibility(View.INVISIBLE);
				if (!"".equals(etpassword.getText().toString())) {
					imdelete2.setVisibility(View.VISIBLE);
				} else {
					imdelete2.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	/**
	 * 编辑框监听TextListener
	 * 
	 * @return
	 */
	private TextWatcher passwordListener() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (!"".equals(s.toString())) {
					imdelete2.setVisibility(View.VISIBLE);
				} else {
					imdelete2.setVisibility(View.INVISIBLE);
				}
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

	private TextWatcher accountListener() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdelete1.setVisibility(View.VISIBLE);
				} else {
					imdelete1.setVisibility(View.INVISIBLE);
				}
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

	/**
	 * 点击事件onClickListener
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvlogin:
			login();// 登陆账号
			break;
		case R.id.imdelete1:
			etaccount.setText("");// 清空用户名
			break;
		case R.id.imdelete2:
			etpassword.setText("");// 清空密码
			break;
		case R.id.tvforget:
			Intent intent1 = new Intent(this, ForgetActivity.class);
			startActivity(intent1);// 忘记密码
			break;
		case R.id.tvregister: // 注册用户
			Intent intent2 = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent2, 0);
			break;
		}
	}

	/**
	 * 判断judge
	 */
	private void login() {
		if (!"".equals(etaccount.getText().toString().trim())
				&& !"".equals(etpassword.getText().toString().trim())) {
			postAccount();// 确定登陆
		} else {
			GetSetUntils.setToast(LoginActivity.this, false, "账号或密码有误");
		}
	}

	/**
	 * 登陆login
	 */
	private void postAccount() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("account", etaccount.getText().toString());
		params.put("password", etpassword.getText().toString());
		MyVolley.post(this, Constant.MYURL.LOGIN, params, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				GetSetUntils.setToast(LoginActivity.this, false, "网络异常");
			}

			@Override
			public void onResponse(String arg0) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(arg0);
					int statusCode = jsonObject.optInt("statusCode");
					String message = jsonObject.optString("message");
					String value = jsonObject.optString("value");
					if (statusCode == 200) {
						tvlogin.setText("登陆中…");
						GetUntils.getInstance().setAccount(LoginActivity.this,
								etaccount.getText().toString());
						GetUntils.getInstance()
								.setUS(LoginActivity.this, value);
						GetSetUntils
								.setToast(LoginActivity.this, true, message);
						setResult(RESULT_OK);
						finish();
					} else {
						GetSetUntils.setToast(LoginActivity.this, false,
								message);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);// 友盟统计
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);// 友盟统计
//	}

	/**
	 * 返回结果 ActivityResult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			// etaccount.setText(data.getStringExtra(Constant.REQUEST_RESULTCODE.NAME_ACCOUNT));
			// etpassword.setText(data.getStringExtra(Constant.REQUEST_RESULTCODE.NAME_PASSWORD));
			// imdelete1.setVisibility(View.INVISIBLE);
			// imdelete2.setVisibility(View.INVISIBLE);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
