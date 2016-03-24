package com.wlhl.zhanzhangzhijia;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.xinbo.utils.GetSetUntils;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

public class SettingActivity extends Activity implements OnClickListener {
	private String us;
	private String yue;
	private boolean is_yuetixing;
	private boolean is_chongzhitixing;
	private boolean is_tixiantixing;
	private boolean is_shourutixing;
	private boolean is_zhichutixing;
	private boolean is_quanzhongtixing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		us = GetUntils.getInstance().getUS(this);
		initUI();
	}

	/**
	 * getmysetting获取我的设置
	 * 
	 * @param tvyue
	 * @param cbquanzhong
	 * @param cbzhichu
	 * @param cbshouru
	 * @param cbtixian
	 * @param cbchongzhi
	 * @param cbyue
	 */
	private void getSetting(final EditText etyue, final CheckBox cbyue,
			final CheckBox cbchongzhi, final CheckBox cbtixian,
			final CheckBox cbshouru, final CheckBox cbzhichu,
			final CheckBox cbquanzhong) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.GETSETTING, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(SettingActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							JSONObject datas = jsonObject
									.optJSONObject("datas");
							JSONObject yuetixing = datas
									.optJSONObject("yuetixing");
							yue = yuetixing.optString("yue");
							is_yuetixing = yuetixing.optBoolean("checked");
							is_chongzhitixing = datas
									.optBoolean("chongzhitixing");
							is_tixiantixing = datas.optBoolean("tixiantixing");
							is_shourutixing = datas.optBoolean("shourutixing");
							is_zhichutixing = datas.optBoolean("zhichutixing");
							is_quanzhongtixing = datas
									.optBoolean("quanzhongtixing");
							cbyue.setChecked(is_yuetixing);
							cbchongzhi.setChecked(is_chongzhitixing);
							cbtixian.setChecked(is_tixiantixing);
							cbshouru.setChecked(is_shourutixing);
							cbzhichu.setChecked(is_zhichutixing);
							cbquanzhong.setChecked(is_quanzhongtixing);
							etyue.setText(yue);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		tvback.setOnClickListener(this);
		final EditText etyue = (EditText) findViewById(R.id.etyue);
		CheckBox cbyue = (CheckBox) findViewById(R.id.cb1);
		CheckBox cbchongzhi = (CheckBox) findViewById(R.id.cb2);
		CheckBox cbtixian = (CheckBox) findViewById(R.id.cb3);
		CheckBox cbshouru = (CheckBox) findViewById(R.id.cb4);
		CheckBox cbzhichu = (CheckBox) findViewById(R.id.cb5);
		CheckBox cbquanzhong = (CheckBox) findViewById(R.id.cb6);
		cbyue.setOnCheckedChangeListener(cb_yue());
		cbchongzhi.setOnCheckedChangeListener(cb_cbchongzhi());
		cbtixian.setOnCheckedChangeListener(cb_cbtixian());
		cbshouru.setOnCheckedChangeListener(cb_shouru());
		cbzhichu.setOnCheckedChangeListener(cb_zhichu());
		cbquanzhong.setOnCheckedChangeListener(cb_quanzhong());
		etyue.addTextChangedListener(et_update(etyue));
		getSetting(etyue, cbyue, cbchongzhi, cbtixian, cbshouru, cbzhichu,
				cbquanzhong);
	}

	/**
	 * edittext listener编辑余额
	 * 
	 * @param etyue
	 * @return new TextWatcher()
	 */
	private TextWatcher et_update(final EditText etyue) {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString().trim())) {
					yue = s.toString().trim();
					updateSetting();// 更改设置
				} else {
					etyue.setError("不能为空");
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
	 * 初始化init checkbox
	 * 
	 * @return
	 */
	private OnCheckedChangeListener cb_yue() {// 余额
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_yuetixing = true;
				} else {
					is_yuetixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	private OnCheckedChangeListener cb_cbchongzhi() {// 充值
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_chongzhitixing = true;
				} else {
					is_chongzhitixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	private OnCheckedChangeListener cb_cbtixian() {// 提现
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_tixiantixing = true;
				} else {
					is_tixiantixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	private OnCheckedChangeListener cb_shouru() {// 收入
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_shourutixing = true;
				} else {
					is_shourutixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	private OnCheckedChangeListener cb_zhichu() {// 支出
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_zhichutixing = true;
				} else {
					is_zhichutixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	private OnCheckedChangeListener cb_quanzhong() {// 权重
		return new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					is_quanzhongtixing = true;
				} else {
					is_quanzhongtixing = false;
				}
				updateSetting();// 更改设置
			}
		};
	}

	/**
	 * 更改设置
	 */
	private void updateSetting() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		params.put("yue", yue);
		params.put("yuetixing", "" + is_yuetixing);
		params.put("chongzhitixing", "" + is_chongzhitixing);
		params.put("tixiantixing", "" + is_tixiantixing);
		params.put("shourutixing", "" + is_shourutixing);
		params.put("zhichutixing", "" + is_zhichutixing);
		params.put("quanzhongtixing", "" + is_quanzhongtixing);
		MyVolley.post(this, Constant.MYURL.SETTING, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(SettingActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvback:
			finish();
			break;
		}
	}
}
