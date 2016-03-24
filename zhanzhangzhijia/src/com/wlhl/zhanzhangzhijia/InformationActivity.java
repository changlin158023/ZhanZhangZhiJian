package com.wlhl.zhanzhangzhijia;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.Countdown;
import com.wlhl.hong.MyBank;
import com.wlhl.hong.GetUntils;
import com.wlhl.hong.SelectBank;
import com.xinbo.utils.GetSetUntils;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class InformationActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private AlertDialog dialog;
	private String username;
	private String phone;
	private EditText etoldpass;
	private EditText etnewpass1;
	private EditText etnewpass2;
	private EditText etname;
	private EditText etcardnum;
	private ArrayList<SelectBank> albank = new ArrayList<SelectBank>();
	private ArrayList<MyBank> almybank = new ArrayList<MyBank>();
	private HashMap<String, Bitmap> allogo = new HashMap<String, Bitmap>();
	private Ladapter ladapter;
	private String us;
	private EditText etphonenum;
	private EditText etverification;
	private EditText etsalt;
	private ImageView imsalt;
	private String sid;
	private String bid;
	private String deleteid;
	private TextView tvverification;
	private TextView tvphone;
	private ImageView imdeletePhone;
	private ImageView imdeleteOldpassword;
	private ImageView imdeleteNewpassword1;
	private ImageView imdeleteNewpassword2;
	private ImageView imdeleteName;
	private ImageView imdeleteNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		phone = intent.getStringExtra("phone");
		us = GetUntils.getInstance().getUS(this);
		sid = GetUntils.getInstance().getSid(this);// get session id
		postBank();// 获取所有银行列表 banklist
		initUI();
	}

	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		TextView tvexit = (TextView) findViewById(R.id.tvexit);
		tvback.setOnClickListener(this);
		tvexit.setOnClickListener(this);
		final ListView lv = (ListView) findViewById(R.id.listView1);
		addhead(lv);// 加头
		addfoot(lv);// 加尾
		ladapter = new Ladapter();
		lv.setAdapter(ladapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				deleteid = almybank.get(arg2 - lv.getHeaderViewsCount()).myid;
				GetSetUntils.setAlertDialog(InformationActivity.this,
						"确定删除所选择的项", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								deleteWebsite();// 删除网站
							}
						});
			}
		});
	}

	/**
	 * 删除网站delete website
	 */
	private void deleteWebsite() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		params.put("id", deleteid);
		MyVolley.post(this, Constant.MYURL.DELETEBANK, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(InformationActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject job = new JSONObject(arg0);
							int statusCode = job.optInt("statusCode");
							String message = job.optString("message");
							if (statusCode == 200) {
								GetSetUntils.setToast(InformationActivity.this,
										true, message);
								getBank();
							} else {
								GetSetUntils.setToast(InformationActivity.this,
										false, message);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void addfoot(ListView lv) {
		View footview = getLayoutInflater().inflate(R.layout.foot_btnadd, null);
		TextView tvadd = (TextView) footview.findViewById(R.id.tvadd);
		tvadd.setOnClickListener(this);
		lv.addFooterView(footview);// 加尾
	}

	private void addhead(ListView lv) {
		View headview = getLayoutInflater().inflate(R.layout.head_information,
				null);
		TextView tvusername = (TextView) headview.findViewById(R.id.tvusername);
		TextView tvuser = (TextView) headview.findViewById(R.id.tvuser);
		tvphone = (TextView) headview.findViewById(R.id.tvphone);
		headview.findViewById(R.id.linear2).setOnClickListener(this);
		headview.findViewById(R.id.linear3).setOnClickListener(this);
		tvuser.setText(username);
		tvusername.setText(username);
		if (!"0".equals(phone) && phone != null && !"".equals(phone)) {
			tvphone.setText(phone);
		} else {
			tvphone.setText("未绑定手机");
		}
		lv.addHeaderView(headview);// 加头
	}

	/**
	 * 我的网站适配器 listview adapter
	 * 
	 */
	class Ladapter extends BaseAdapter {
		@Override
		public int getCount() {
			return almybank.size();
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
						.inflate(R.layout.listitem_bank, null);
			} else {
				view = convertView;
			}
			ImageView bankpic = (ImageView) view.findViewById(R.id.imageView1);
			TextView bankname = (TextView) view.findViewById(R.id.textView1);
			TextView cardnum = (TextView) view.findViewById(R.id.textView4);
			cardnum.setText("卡号：" + almybank.get(position).cardnum);
			String bname = almybank.get(position).bankname;
			bankname.setText(bname);
			if (allogo.containsKey(bname)) {
				bankpic.setImageBitmap(allogo.get(bname));
			}
			return view;
		}
	}

	/**
	 * 显示银行适配器 adapter
	 * 
	 */
	class Gadapter extends BaseAdapter {
		@Override
		public int getCount() {
			return albank.size();
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
				view = getLayoutInflater().inflate(R.layout.griditem_addbank,
						null);
			} else {
				view = convertView;
			}
			TextView tvbank = (TextView) view.findViewById(R.id.tvbank);
			ImageView impic = (ImageView) view.findViewById(R.id.impic);
			impic.setImageBitmap(albank.get(position).bankpic);
			tvbank.setText(albank.get(position).bankname);
			return view;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvback:
			finish();
			break;
		case R.id.imdelete1:// 清空手机号码
			etphonenum.setText("");
			break;
		case R.id.imdelete:// 清空旧密码
			etoldpass.setText("");
			break;
		case R.id.imdelete2:// 清空新密码
			etnewpass1.setText("");
			break;
		case R.id.imdelete3:// 清空确认新密码
			etnewpass2.setText("");
			break;
		case R.id.imdeletename:// 清空姓名
			etname.setText("");
			break;
		case R.id.imdeletenum:// 清空银行卡密码
			etcardnum.setText("");
			break;
		case R.id.linear3:// 换手机号码弹窗 phonenum window
			replacePhone();
			break;
		case R.id.imsalt:// 显示图片验证image salt
			GetUntils.getInstance().getImageSalt(this, imsalt, sid);
			break;
		case R.id.tvverification:// 获取手机验证码
			if (!"".equals(etphonenum.getText().toString().trim())
					&& !"".equals(etsalt.getText().toString().trim())) {
				getphoneauth();
			} else {
				GetSetUntils.setToast(this, false, "手机号或图片验证码有误");
			}
			break;
		case R.id.tvsend:// 手机修改提交submit
			if (!"".equals(etphonenum.getText().toString().trim())
					&& !"".equals(etsalt.getText().toString().trim())) {
				postVerification();
			} else {
				GetSetUntils.setToast(this, false, "手机号或手机验证码有误");
			}
			break;
		case R.id.tvexit:// 退出登录exit
			GetSetUntils.setAlertDialog(this, "确定退出登录",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							GetUntils.getInstance().removeUS(
									InformationActivity.this);
							setResult(RESULT_OK);
							finish();
						}
					});
			break;
		case R.id.linear2:// 换密码弹窗 password window
			replacePassword();
			break;
		case R.id.tvok:// 修改密码 submit
			postPassword();
			break;
		case R.id.tvsure:// 保存我的银行卡submit
			saveBank();
			break;
		case R.id.tvadd:// 银行卡弹窗bank window
			addBank();
			break;
		case R.id.tvcancel:// 取消
			dialog.dismiss();
			break;
		}
		// InputMethodManager im=(InputMethodManager)
		// getSystemService(INPUT_METHOD_SERVICE);
		// im.hideSoftInputFromWindow(v.getWindowToken(),0);
	}

	/**
	 * 发短信验证phone verification
	 */
	private void getphoneauth() {
		HashMap<String, String> postHash = new HashMap<String, String>();
		postHash.put("phone", etphonenum.getText().toString().trim());
		postHash.put("VerifyCode", etsalt.getText().toString().trim());
		postHash.put("session", sid);
		MyVolley.post(this, Constant.MYURL.MODIFYPHONEAUTH, postHash,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(InformationActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0);
							int statusCode = jsonObject.optInt("statusCode");
							String message = jsonObject.optString("message");
							if (statusCode == 200) {
								GetSetUntils.setToast(InformationActivity.this,
										true, message);
								Countdown.startCountdown(tvverification,
										"获取验证码", 60);
							} else {
								GetSetUntils.setToast(InformationActivity.this,
										false, message);
							}
						} catch (JSONException e) {
						}
					}
				});
	}

	/**
	 * 银行卡弹窗 bank window
	 */
	private void addBank() {
		Builder builder2 = new AlertDialog.Builder(this);
		dialog = builder2.show();
		Window window2 = dialog.getWindow();
		View view2 = getLayoutInflater().inflate(R.layout.dialog_addbank, null);// dialog自定义布局
		TextView tvcancel = (TextView) view2.findViewById(R.id.tvcancel);
		TextView tvsure = (TextView) view2.findViewById(R.id.tvsure);
		etname = (EditText) view2.findViewById(R.id.editText1);
		etcardnum = (EditText) view2.findViewById(R.id.editText2);
		Spinner spclass = (Spinner) view2.findViewById(R.id.spinner1);
		imdeleteName = (ImageView) view2.findViewById(R.id.imdeletename);
		imdeleteNum = (ImageView) view2.findViewById(R.id.imdeletenum);
		Gadapter gadapter = new Gadapter();
		spclass.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				bid = albank.get(arg2).id;// 网站类别id
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spclass.setAdapter(gadapter);
		spclass.setSelection(0);// 默认
		tvsure.setOnClickListener(this);
		tvcancel.setOnClickListener(this);
		imdeleteName.setOnClickListener(this);
		imdeleteNum.setOnClickListener(this);
		etname.setOnFocusChangeListener(this);
		etcardnum.setOnFocusChangeListener(this);
		etname.addTextChangedListener(nameTextChange());
		etcardnum.addTextChangedListener(numTextChange());
		window2.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
		window2.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window2.setContentView(view2);
	}

	/**
	 * 手机修改submit phone
	 */
	private void postVerification() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("phoneauth", etverification.getText().toString().trim());
		params.put("session", sid);
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.MODIFYPHONE, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(InformationActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject job = new JSONObject(arg0);
							int statusCode = job.optInt("statusCode");
							String message = job.optString("message");
							String value = job.optString("value");
							if (statusCode == 200) {
								dialog.dismiss();
								tvphone.setText(etphonenum.getText().toString()
										.trim());
								GetUntils.getInstance().setUS(
										InformationActivity.this, value);
								GetSetUntils.setToast(InformationActivity.this,
										true, message);
							} else {
								GetSetUntils.setToast(InformationActivity.this,
										false, message);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 手机号码弹窗 phone window
	 */
	private void replacePhone() {
		Builder builder = new AlertDialog.Builder(this);
		dialog = builder.show();
		Window window = dialog.getWindow();
		View view = getLayoutInflater().inflate(R.layout.dialog_replacephone,
				null);// dialog自定义布局
		etphonenum = (EditText) view.findViewById(R.id.etphonenum);
		etsalt = (EditText) view.findViewById(R.id.etsalt);
		etverification = (EditText) view.findViewById(R.id.etverification);
		tvverification = (TextView) view.findViewById(R.id.tvverification);
		TextView tvcancel = (TextView) view.findViewById(R.id.tvcancel);
		TextView tvsend = (TextView) view.findViewById(R.id.tvsend);
		imsalt = (ImageView) view.findViewById(R.id.imsalt);
		imdeletePhone = (ImageView) view.findViewById(R.id.imdelete1);
		imdeletePhone.setOnClickListener(this);
		imsalt.setOnClickListener(this);
		tvverification.setOnClickListener(this);
		tvcancel.setOnClickListener(this);
		tvsend.setOnClickListener(this);
		etphonenum.setOnFocusChangeListener(this);
		etphonenum.addTextChangedListener(phoneTextChange());
		window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setContentView(view);
		GetUntils.getInstance().getImageSalt(this, imsalt, sid);// 初始化图片验证码
	}

	/**
	 * 编辑框监听TextWatcher
	 * 
	 * @return
	 */
	private TextWatcher new1TextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeleteNewpassword1.setVisibility(View.VISIBLE);
				} else {
					imdeleteNewpassword1.setVisibility(View.INVISIBLE);
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

	private TextWatcher oldTextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeleteOldpassword.setVisibility(View.VISIBLE);
				} else {
					imdeleteOldpassword.setVisibility(View.INVISIBLE);
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

	private TextWatcher new2TextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeleteNewpassword2.setVisibility(View.VISIBLE);
				} else {
					imdeleteNewpassword2.setVisibility(View.INVISIBLE);
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

	private TextWatcher phoneTextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeletePhone.setVisibility(View.VISIBLE);
				} else {
					imdeletePhone.setVisibility(View.INVISIBLE);
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

	private TextWatcher nameTextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeleteName.setVisibility(View.VISIBLE);
				} else {
					imdeleteName.setVisibility(View.INVISIBLE);
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

	private TextWatcher numTextChange() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!"".equals(s.toString())) {
					imdeleteNum.setVisibility(View.VISIBLE);
				} else {
					imdeleteNum.setVisibility(View.INVISIBLE);
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
	 * 密码弹窗 password window
	 */
	private void replacePassword() {
		Builder builder = new AlertDialog.Builder(this);
		dialog = builder.show();
		Window window = dialog.getWindow();
		View view = getLayoutInflater().inflate(
				R.layout.dialog_replacepassword, null);// dialog自定义布局
		etoldpass = (EditText) view.findViewById(R.id.etoldpass);
		etnewpass1 = (EditText) view.findViewById(R.id.etnewpass1);
		etnewpass2 = (EditText) view.findViewById(R.id.etnewpass2);
		TextView tvcancel = (TextView) view.findViewById(R.id.tvcancel);
		TextView tvok = (TextView) view.findViewById(R.id.tvok);
		imdeleteOldpassword = (ImageView) view.findViewById(R.id.imdelete);
		imdeleteNewpassword1 = (ImageView) view.findViewById(R.id.imdelete2);
		imdeleteNewpassword2 = (ImageView) view.findViewById(R.id.imdelete3);
		imdeleteOldpassword.setOnClickListener(this);
		imdeleteNewpassword1.setOnClickListener(this);
		imdeleteNewpassword2.setOnClickListener(this);
		tvcancel.setOnClickListener(this);
		tvok.setOnClickListener(this);
		etoldpass.setOnFocusChangeListener(this);
		etnewpass1.setOnFocusChangeListener(this);
		etnewpass2.setOnFocusChangeListener(this);
		etoldpass.addTextChangedListener(oldTextChange());
		etnewpass1.addTextChangedListener(new1TextChange());
		etnewpass2.addTextChangedListener(new2TextChange());
		window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setContentView(view);
	}

	/**
	 * 所有银行列表all banklist
	 */
	private void postBank() {
		albank.clear();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.BANK, params, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				GetSetUntils.setToast(InformationActivity.this, false,
						"网络异常 稍后重试");
			}

			@Override
			public void onResponse(String arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					JSONObject datas = jsonObject.optJSONObject("datas");
					JSONArray data = datas.optJSONArray("data");
					int length = data.length();
					for (int i = 0; i < length; i++) {
						JSONObject object = data.optJSONObject(i);
						String id = object.optString("id");
						String name = object.optString("name");
						String image = object.optString("image");
						postBankpic(id, name, image);// 获取银行id名称图标
					}
					getBank();// 获取我的银行列表mybanklist
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 银行名称图标get bank name and logo
	 * 
	 * @param image
	 * @param name
	 * @param id
	 */
	private void postBankpic(final String id, final String name,
			final String image) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(Constant.MYURL.BANKPIC + image);
					InputStream inputStream = url.openStream();
					BufferedInputStream bfs = new BufferedInputStream(
							inputStream);
					Bitmap bitmap = BitmapFactory.decodeStream(bfs);
					albank.add(new SelectBank(id, name, bitmap));
					allogo.put(name, bitmap);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 提交银行submit mybank
	 */
	private void saveBank() {
		if (!"".equals(etname.getText().toString().trim())
				&& !"".equals(etcardnum.getText().toString().trim())) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("bid", bid);
			params.put("name", etname.getText().toString());
			params.put("no", etcardnum.getText().toString());
			params.put("userauth", us);
			MyVolley.post(this, Constant.MYURL.SAVEBANK, params,
					new VolleyListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {
							GetSetUntils.setToast(InformationActivity.this,
									false, "网络异常 稍后重试");
						}

						@Override
						public void onResponse(String arg0) {
							try {
								JSONObject object = new JSONObject(arg0);
								String message = object.optString("message");
								int statuscode = object.optInt("statusCode");
								if (statuscode == 200) {
									GetSetUntils.setToast(
											InformationActivity.this, true,
											message);
									dialog.dismiss();
									getBank();// 刷新refresh mybanklist
								} else {
									GetSetUntils.setToast(
											InformationActivity.this, false,
											message);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		} else {
			GetSetUntils.setToast(this, false, "姓名或卡号不能为空");
		}
	}

	/**
	 * 我的银行列表mybank list
	 */
	private void getBank() {
		almybank.clear();
		Map<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.GETBANK, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(InformationActivity.this, false,
								"网络异常 稍后重试");
					}

					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject object = new JSONObject(arg0);
							JSONObject datas = object.optJSONObject("datas");
							JSONArray data = datas.optJSONArray("data");
							if (data == null) {
								return;
							}
							int length = data.length();
							for (int i = 0; i < length; i++) {
								JSONObject jarr = data.optJSONObject(i);
								String myid = jarr.getString("id");
								String myno = jarr.getString("no");
								String mybankname = jarr.getString("bankname");
								String myname = jarr.getString("name");
								almybank.add(new MyBank(myname, mybankname,
										myid, myno));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							ladapter.notifyDataSetChanged();
						}
					}
				});
	}

	/**
	 * 修改密码submit password
	 */
	private void postPassword() {
		if (!"".equals(etoldpass.getText().toString())) {
			if (etnewpass2.getText().toString()
					.equals(etnewpass1.getText().toString())) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("password", etoldpass.getText().toString());
				params.put("password1", etnewpass1.getText().toString());
				params.put("password2", etnewpass1.getText().toString());
				params.put("userauth", us);
				MyVolley.post(this, Constant.MYURL.PASSWORD, params,
						new VolleyListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
								GetSetUntils.setToast(InformationActivity.this,
										false, "网络异常 稍后重试");
							}

							@Override
							public void onResponse(String arg0) {
								JSONObject object;
								try {
									object = new JSONObject(arg0);
									int statusCode = object
											.optInt("statusCode");
									String message = object
											.optString("message");
									if (statusCode == 200) {
										GetSetUntils.setToast(
												InformationActivity.this, true,
												message);
										dialog.dismiss();
									} else {
										GetSetUntils.setToast(
												InformationActivity.this,
												false, message);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
			} else {
				GetSetUntils.setToast(this, false, "密码不一致");
			}
		} else {
			GetSetUntils.setToast(this, false, "请输入旧密码");
		}
	}

	/**
	 * 焦点监听FocusListenner
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.etphonenum:// 要更换的新手机号码
			if (hasFocus && !"".equals(etphonenum.getText().toString())) {
				imdeletePhone.setVisibility(View.VISIBLE);
			} else {
				imdeletePhone.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.etoldpass:
			if (hasFocus && !"".equals(etoldpass.getText().toString())) {
				imdeleteOldpassword.setVisibility(View.VISIBLE);
			} else {
				imdeleteOldpassword.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.etnewpass1:
			if (hasFocus && !"".equals(etnewpass1.getText().toString())) {
				imdeleteNewpassword1.setVisibility(View.VISIBLE);
			} else {
				imdeleteNewpassword1.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.etnewpass2:
			if (hasFocus && !"".equals(etnewpass2.getText().toString())) {
				imdeleteNewpassword2.setVisibility(View.VISIBLE);
			} else {
				imdeleteNewpassword2.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.editText1:// 姓名
			if (hasFocus && !"".equals(etname.getText().toString())) {
				imdeleteName.setVisibility(View.VISIBLE);
			} else {
				imdeleteName.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.editText2:// 卡号
			if (hasFocus && !"".equals(etcardnum.getText().toString())) {
				imdeleteNum.setVisibility(View.VISIBLE);
			} else {
				imdeleteNum.setVisibility(View.INVISIBLE);
			}
			break;
		}
	}
}
