package com.wlhl.zhanzhangzhijia;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.wlhl.hong.Countdown;
import com.xinbo.utils.GetSetUntils;

public class ForgetActivity extends Activity implements OnClickListener {
	private EditText ettelephone;
	private EditText etpassword;
	private EditText etverification;
	private EditText etsalt;
	private TextView tvok;
	private ImageView imdelete2;
	private ImageView imdelete3;
	private ImageView tvback;
	private ImageView imsalt;
	private TextView tvverification;
	private String sid;// session id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 通知栏透明
		setContentView(R.layout.activity_forgot);
		initUI();
		getSalt();// 图片验证码
	}

	/**
	 * 图片验证码 image salt
	 */
	private void getSalt() {
		MyVolley.get(this, Constant.MYURL.SESSIONID, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
			}

			@Override
			public void onResponse(final String arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject object = new JSONObject(arg0);
							JSONObject datas = object.optJSONObject("datas");
							sid = datas.optString("S_sid");
							URL url = new URL(Constant.MYURL.SALT + sid);
							InputStream inputStream = url.openStream();
							BufferedInputStream bfs = new BufferedInputStream(
									inputStream);
							final Bitmap bitmap = BitmapFactory
									.decodeStream(bfs);
							ForgetActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									imsalt.setImageBitmap(bitmap);// 显示图片验证码
								}
							});
							inputStream.close();
							bfs.close();
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}

	private void initUI() {
		etpassword = (EditText) findViewById(R.id.editText2);
		ettelephone = (EditText) findViewById(R.id.editText3);
		etverification = (EditText) findViewById(R.id.editText4);
		etsalt = (EditText) findViewById(R.id.editText5);
		imdelete2 = (ImageView) findViewById(R.id.imdelete2);
		imdelete3 = (ImageView) findViewById(R.id.imdelete3);
		imsalt = (ImageView) findViewById(R.id.imsalt);
		tvback = (ImageView) findViewById(R.id.tvback);
		tvverification = (TextView) findViewById(R.id.tvverification);
		tvok = (TextView) findViewById(R.id.tvok);
		tvverification.setOnClickListener(this);
		tvback.setOnClickListener(this);
		imsalt.setOnClickListener(this);
		tvok.setOnClickListener(this);
		imdelete2.setOnClickListener(this);
		imdelete3.setOnClickListener(this);
		etpassword.addTextChangedListener(passwordListener());
		ettelephone.addTextChangedListener(telephoneListener());
		etpassword.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete3.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(etpassword.getText().toString())) {
					imdelete2.setVisibility(View.VISIBLE);
				} else {
					imdelete2.setVisibility(View.INVISIBLE);
				}
			}
		});
		ettelephone.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete2.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(ettelephone.getText().toString())) {
					imdelete3.setVisibility(View.VISIBLE);
				} else {
					imdelete3.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

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

	private TextWatcher telephoneListener() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdelete3.setVisibility(View.VISIBLE);
				} else {
					imdelete3.setVisibility(View.INVISIBLE);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvback:
			finish();// 返回
			break;
		case R.id.imdelete2:
			etpassword.setText("");// 密码
			break;
		case R.id.imdelete3:
			ettelephone.setText("");// 手机号
			break;
		case R.id.imsalt:
			getSalt();// 图片码
			break;
		case R.id.tvverification:// 手机验证码
			getVeri();
			break;
		case R.id.tvok:
			if (!"".equals(etpassword.getText().toString().trim())) {
				forget();// 找回密码
			} else {
				GetSetUntils.setToast(this, false, "请输入最少6位数密码");
			}
			break;

		}
	}

	/**
	 * 判断 judge
	 */
	private void getVeri() {
		if (!"".equals(etpassword.getText().toString().trim())) {
			if (!"".equals(etsalt.getText().toString().trim())) {
				postURL();// 获取
			} else {
				GetSetUntils.setToast(this, false, "图片验证码有误");
			}
		} else {
			GetSetUntils.setToast(this, false, "请输入最少6位数密码");
		}
	}

	/**
	 * 获取验证码post to get verifycode of telephone
	 */
	private void postURL() {
		HashMap<String, String> postHash = new HashMap<String, String>();
		postHash.put("userphone", ettelephone.getText().toString().trim());
		postHash.put("VerifyCode", etsalt.getText().toString().trim());
		postHash.put("session", sid);
		MyVolley.post(this, Constant.MYURL.GETAUTH, postHash,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(ForgetActivity.this, false,
								"网络异常");
					}

					@Override
					public void onResponse(String arg0) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							int statusCode = jsonObject.optInt("statusCode");
							String message = jsonObject.optString("message");
							if (statusCode == 200) {
								GetSetUntils.setToast(ForgetActivity.this,
										true, message);
								Countdown.startCountdown(tvverification,
										"获取验证码", 60);
							} else {
								GetSetUntils.setToast(ForgetActivity.this,
										false, message);
							}
						} catch (JSONException e) {
						}
					}
				});
	}

	/**
	 * 提交submit
	 */
	private void forget() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("password", etpassword.getText().toString());
		params.put("password1", etpassword.getText().toString());
		params.put("phoneauth", etverification.getText().toString());
		params.put("salt", etsalt.getText().toString());
		params.put("session", sid);
		MyVolley.post(this, Constant.MYURL.FORGET, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(ForgetActivity.this, false,
								"网络异常");
					}

					@Override
					public void onResponse(String arg0) {
						tvok.setText("提交中…");
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							String statusCode = jsonObject
									.optString("statusCode");
							String message = jsonObject.optString("message");
							if ("200".equals(statusCode)) {
								GetSetUntils.setToast(ForgetActivity.this,
										true, message);
								finish();// 成功后关闭页面
							} else {
								GetSetUntils.setToast(ForgetActivity.this,
										false, message);
								tvok.setText("确定");
							}
						} catch (JSONException e) {
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
}
