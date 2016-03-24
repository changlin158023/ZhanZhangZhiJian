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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.Countdown;
import com.xinbo.utils.GetSetUntils;

public class RegisterActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private ImageView tvback;
	private TextView tvagree;
	private EditText etaccount;
	private EditText etpassword;
	private EditText etconfirm;
	private ImageView imdelete1;
	private ImageView imdelete2;
	private ImageView imdelete3;
	private ImageView imdelete4;
	private TextView tvregister;
	private EditText etsalt;
	private ImageView imsalt;
	private String sid;// session id
	private boolean ischeck = true;
	private TextView tvverification;
	private EditText etphone;
	private EditText etverification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regitst);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 通知栏透明
		initView();
		getSalt();// 图片验证码
	}

	private void initView() {
		tvback = (ImageView) findViewById(R.id.tvback);
		tvback.setOnClickListener(this);
		tvagree = (TextView) findViewById(R.id.tvagree);
		tvagree.setOnClickListener(this);
		CheckBox cbagree = (CheckBox) findViewById(R.id.checkBox);
		cbagree.setOnCheckedChangeListener(this);

		etaccount = (EditText) findViewById(R.id.editText1);
		etphone = (EditText) findViewById(R.id.editText2);
		etpassword = (EditText) findViewById(R.id.editText3);
		etconfirm = (EditText) findViewById(R.id.editText4);
		etsalt = (EditText) findViewById(R.id.editText5);
		etverification = (EditText) findViewById(R.id.editText6);
		imdelete1 = (ImageView) findViewById(R.id.imdelete1);
		imdelete2 = (ImageView) findViewById(R.id.imdelete2);
		imdelete3 = (ImageView) findViewById(R.id.imdelete3);
		imdelete4 = (ImageView) findViewById(R.id.imdelete4);
		tvregister = (TextView) findViewById(R.id.tvregister);
		imsalt = (ImageView) findViewById(R.id.imsalt);
		imsalt.setOnClickListener(this);
		tvregister.setOnClickListener(this);
		imdelete1.setOnClickListener(this);
		imdelete2.setOnClickListener(this);
		imdelete3.setOnClickListener(this);
		imdelete4.setOnClickListener(this);
		etaccount.addTextChangedListener(accountListener());
		etphone.addTextChangedListener(phoneListener());
		etpassword.addTextChangedListener(passwordListener());
		etconfirm.addTextChangedListener(confirmListener());
		etaccount.setOnFocusChangeListener(accountFocus());
		etphone.setOnFocusChangeListener(phoneFocus());
		etpassword.setOnFocusChangeListener(passwordFocus());
		etconfirm.setOnFocusChangeListener(confirmFocus());
		tvverification = (TextView) findViewById(R.id.tvverification);
		tvverification.setOnClickListener(this);
	}

	private OnFocusChangeListener phoneFocus() {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete1.setVisibility(View.INVISIBLE);
				imdelete3.setVisibility(View.INVISIBLE);
				imdelete4.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(etphone.getText().toString())) {
					imdelete2.setVisibility(View.VISIBLE);
				} else {
					imdelete2.setVisibility(View.INVISIBLE);
				}
			}
		};
	}

	private TextWatcher phoneListener() {
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

	private OnFocusChangeListener accountFocus() {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete2.setVisibility(View.INVISIBLE);
				imdelete3.setVisibility(View.INVISIBLE);
				imdelete4.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(etaccount.getText().toString())) {
					imdelete1.setVisibility(View.VISIBLE);
				} else {
					imdelete1.setVisibility(View.INVISIBLE);
				}
			}
		};
	}

	private OnFocusChangeListener confirmFocus() {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete1.setVisibility(View.INVISIBLE);
				imdelete2.setVisibility(View.INVISIBLE);
				imdelete3.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(etconfirm.getText().toString())) {
					imdelete4.setVisibility(View.VISIBLE);
				} else {
					imdelete4.setVisibility(View.INVISIBLE);
				}
			}
		};
	}

	private OnFocusChangeListener passwordFocus() {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				imdelete1.setVisibility(View.INVISIBLE);
				imdelete2.setVisibility(View.INVISIBLE);
				imdelete4.setVisibility(View.INVISIBLE);
				if (hasFocus && !"".equals(etpassword.getText().toString())) {
					imdelete3.setVisibility(View.VISIBLE);
				} else {
					imdelete3.setVisibility(View.INVISIBLE);
				}
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

	private TextWatcher passwordListener() {
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

	private TextWatcher confirmListener() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdelete4.setVisibility(View.VISIBLE);
				} else {
					imdelete4.setVisibility(View.INVISIBLE);
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
		case R.id.tvback:// 返回
			finish();
			break;
		case R.id.imdelete1:
			etaccount.setText("");// 清空用户名
			break;
		case R.id.imdelete2:
			etphone.setText("");// 清空手机号码
			break;
		case R.id.imdelete3:
			etpassword.setText("");// 清空密码
			break;
		case R.id.imdelete4:
			etconfirm.setText("");// 清空确认密码
			break;
		case R.id.imsalt:// 图片验证码
			getSalt();
			break;

		case R.id.tvagree:// 同意条款
			Intent intent1 = new Intent(this, ClauseActivity.class);
			startActivity(intent1);
			break;
		case R.id.tvregister:// 注册
			if (ischeck) {
				register();
			} else {
				GetSetUntils.setToast(RegisterActivity.this, false,
						"请在同意条款前打勾");
			}
			break;
		case R.id.tvverification:// 获取手机验证码
			getVeri();
			break;

		}

	}

	private void getVeri() {
		if (!"".equals(etphone.getText().toString().trim())
				&& !"".equals(etsalt.getText().toString().trim())) {
			postURL();// 获取
		} else {
			GetSetUntils.setToast(this, false, "手机号或图片验证码有误");
		}

	}

	private void register() {
		if (!"".equals(etaccount.getText().toString().trim())
				&& !"".equals(etpassword.getText().toString().trim())) {
			if (etpassword.getText().toString().trim()
					.equals(etconfirm.getText().toString().trim())) {
				postRegister();// 普通注册
			} else {
				GetSetUntils.setToast(this, false, "密码不一致");
			}
		} else {
			GetSetUntils.setToast(this, false, "请输入最少6位数用户名和密码");
		}

	}

	/**
	 * 提交注册post to register
	 */
	private void postRegister() {
		HashMap<String, String> postHash = new HashMap<String, String>();
		postHash.put("username", etaccount.getText().toString().trim());
		postHash.put("password", etpassword.getText().toString().trim());
		postHash.put("password_confirm", etconfirm.getText().toString().trim());
		postHash.put("userphone", etphone.getText().toString().trim());
		postHash.put("phoneauth", etverification.getText().toString().trim());
		postHash.put("salt", etsalt.getText().toString().trim());
		postHash.put("session", sid);
		postHash.put("agree", "1");
		MyVolley.post(this, Constant.MYURL.REGISTER, postHash,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(RegisterActivity.this, false,
								"网络异常");
					}

					@Override
					public void onResponse(String arg0) {
						tvregister.setText("提交中…");
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							int statusCode = jsonObject.optInt("statusCode");
							String message = jsonObject.optString("message");
							if (statusCode == 200) {
								GetSetUntils.setToast(RegisterActivity.this,
										true, message);
								Intent intent = new Intent();
								intent.putExtra(
										Constant.REQUEST_RESULTCODE.NAME_ACCOUNT,
										etaccount.getText().toString());
								intent.putExtra(
										Constant.REQUEST_RESULTCODE.NAME_PASSWORD,
										etpassword.getText().toString());
								setResult(
										Constant.REQUEST_RESULTCODE.RESULTCODE_MYACCOUNT,
										intent);
								finish();// 注册成功后关闭页面
							} else {
								GetSetUntils.setToast(RegisterActivity.this,
										false, message);
								tvregister.setText("注册");
							}
						} catch (JSONException e) {
						}
					}
				});
	}

	/**
	 * 获取手机验证码post to get verifycode of telephone
	 */
	private void postURL() {
		HashMap<String, String> postHash = new HashMap<String, String>();
		postHash.put("userphone", etphone.getText().toString().trim());
		postHash.put("VerifyCode", etsalt.getText().toString().trim());
		postHash.put("session", sid);
		MyVolley.post(this, Constant.MYURL.PHONEAUTH, postHash,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(RegisterActivity.this, false,
								"网络异常");
					}

					@Override
					public void onResponse(String arg0) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							int statusCode = jsonObject.optInt("statusCode");
							String message = jsonObject.optString("message");
							sid = jsonObject.optString("sid");
							if (statusCode == 200) {
								GetSetUntils.setToast(RegisterActivity.this,
										true, message);
								Countdown.startCountdown(tvverification,
										"获取授权码", 60);
							} else {
								GetSetUntils.setToast(RegisterActivity.this,
										false, message);
							}
						} catch (JSONException e) {
						}
					}
				});
	}

	/**
	 * 获取图片验证码get image salt
	 * 
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
							RegisterActivity.this
									.runOnUiThread(new Runnable() {
										public void run() {
											imsalt.setImageBitmap(bitmap);// 显示用户图片验证码
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

//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);// 友盟统计
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);// 友盟统计
//	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.checkBox:
				ischeck = true;
				break;
			}
		} else {
			if (buttonView.getId() == R.id.checkBox) {
				ischeck = false;
			}
		}
	}
}
