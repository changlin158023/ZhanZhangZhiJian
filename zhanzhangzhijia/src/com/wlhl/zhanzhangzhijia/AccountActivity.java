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
import com.xinbo.utils.ExitApp;
import com.xinbo.utils.GetSetUntils;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends Activity implements OnClickListener {
	private AlertDialog dialog;
	private String username;
	private String phone;
	private String balance;
	private TextView tvusername;
	private TextView tvbalance;
	private ImageView imphoto;
	private String us;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		us = GetUntils.getInstance().getUS(this);
		initUI();
		initGson();
	}

	/**
	 * 修改信息后返回刷新if update go to refresh
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			us = GetUntils.getInstance().getUS(this);
			initGson();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 获取个人信息get account information
	 */
	private void initGson() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(this, Constant.MYURL.USER, params, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				tvusername.setText("未登录");
				tvbalance.setText("");
				imphoto.setImageResource(R.drawable.bg_photo);
				GetSetUntils.setToast(AccountActivity.this, false, "还未登录");
			}

			@Override
			public void onResponse(String arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0);
					JSONObject datas = object.optJSONObject("datas");
					username = datas.optString("username");
					phone = datas.optString("phone");
					balance = datas.optString("balance");
					tvusername.setText(username);
					tvbalance.setText("余额：" + balance + "元");
					RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory
							.create(getResources(), BitmapFactory
									.decodeResource(getResources(),
											R.drawable.logo1));
					drawable.setCornerRadius(28 * getResources()
							.getDisplayMetrics().density);
					drawable.setAntiAlias(true);
					imphoto.setImageDrawable(drawable);
				} catch (JSONException e) {
				}
			}
		});
	}

	/**
	 * 找控件findviewbyid
	 */
	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		imphoto = (ImageView) findViewById(R.id.imphoto);
		tvusername = (TextView) findViewById(R.id.tvusername);
		tvbalance = (TextView) findViewById(R.id.tvbalance);
		View linearInformation = findViewById(R.id.linear0);
		View linearmessage = findViewById(R.id.linear1);
		View linearRecharge = findViewById(R.id.linear3);
		View linearRechargehistory = findViewById(R.id.linear4);
		View linearRefund = findViewById(R.id.linear5);
		View linearRefundhistory = findViewById(R.id.linear6);
		View linearFeedback = findViewById(R.id.linear7);
		View linearAbout = findViewById(R.id.linear8);
		tvback.setOnClickListener(this);
		imphoto.setOnClickListener(this);
		linearInformation.setOnClickListener(this);
		linearmessage.setOnClickListener(this);
		linearRecharge.setOnClickListener(this);
		linearRechargehistory.setOnClickListener(this);
		linearRefund.setOnClickListener(this);
		linearRefundhistory.setOnClickListener(this);
		linearFeedback.setOnClickListener(this);
		linearAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvback:
			finish();
			break;
		case R.id.tvcancel:
			dialog.dismiss();
			break;
		case R.id.tvclose:
			dialog.dismiss();
			// initGson();//充值后刷新refresh after recharge
			break;
		case R.id.imphoto:
			Intent intent = new Intent(this, InformationActivity.class);
			if (us == null || "".equals(us)) {
				intent = new Intent(this, LoginActivity.class);
			}
			intent.putExtra("username", username);
			intent.putExtra("phone", phone);
			startActivityForResult(intent, 0);
			break;
		case R.id.tvok:
			dialog.dismiss();
			Builder builder = new AlertDialog.Builder(this);
			dialog = builder.show();
			Window window = dialog.getWindow();
			View view = getLayoutInflater().inflate(R.layout.dialog_rechargeok,
					null);// dialog自定义布局
			TextView tvcancel = (TextView) view.findViewById(R.id.tvcancel);
			TextView tvclose = (TextView) view.findViewById(R.id.tvclose);
			tvclose.setOnClickListener(this);
			tvcancel.setOnClickListener(this);
			window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
			window.setContentView(view);
			break;
		case R.id.linear0:// 个人information
			Intent intent0 = new Intent(this, InformationActivity.class);
			if (us == null || "".equals(us)) {
				intent0 = new Intent(this, LoginActivity.class);
			}
			intent0.putExtra("username", username);
			intent0.putExtra("phone", phone);
			startActivityForResult(intent0, 0);
			break;
		case R.id.linear1:// 短信msm
			Intent intent1 = new Intent(this, SettingActivity.class);
			if (us == null || "".equals(us)) {
				intent1 = new Intent(this, LoginActivity.class);
			}
			startActivityForResult(intent1, 0);
			break;
		case R.id.linear3:// 充值recharge
			if (us == null || "".equals(us)) {
				Intent intent3 = new Intent(AccountActivity.this,
						LoginActivity.class);
				startActivityForResult(intent3, 0);
				return;
			}
			recharge();
			break;
		case R.id.linear4:// 充值历史rechargehistory
			Intent intent4 = new Intent(this, RechargehistoryActivity.class);
			if (us == null || "".equals(us)) {
				intent4 = new Intent(this, LoginActivity.class);
			}
			startActivityForResult(intent4, 0);
			break;
		case R.id.linear5:// 提现refund
			Intent intent5 = new Intent(this, WithdrawCashActivity.class);
			if (us == null || "".equals(us)) {
				intent5 = new Intent(this, LoginActivity.class);
			}
			startActivityForResult(intent5, 0);
			break;
		case R.id.linear6:// 退款记录refundhistory
			Intent intent6 = new Intent(this, RefundhistoryActivity.class);
			if (us == null || "".equals(us)) {
				intent6 = new Intent(this, LoginActivity.class);
			}
			startActivityForResult(intent6, 0);
			break;
		case R.id.linear7:// 反馈feedback
			Intent intent7 = new Intent(this, FeedbackActivity.class);
			startActivity(intent7);
			break;
		case R.id.linear8:// 关于about
			Intent intent8 = new Intent(this, AboutActivity.class);
			startActivity(intent8);
			break;
		}
	}

	/**
	 * 充值recharge
	 */
	private void recharge() {
		Builder builder1 = new AlertDialog.Builder(this);
		dialog = builder1.show();
		Window window1 = dialog.getWindow();
		View view1 = getLayoutInflater()
				.inflate(R.layout.dialog_recharge, null);// dialog自定义布局
		TextView tvcancel = (TextView) view1.findViewById(R.id.tvcancel);
		TextView tvok = (TextView) view1.findViewById(R.id.tvok);
		TextView tvuser = (TextView) view1.findViewById(R.id.tvuser);
		TextView tvbalance = (TextView) view1.findViewById(R.id.tvbalance);
		tvuser.setText("用户名：" + username);
		tvbalance.setText("余额：" + balance + "元");
		tvok.setOnClickListener(this);
		tvcancel.setOnClickListener(this);
		window1.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
		window1.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window1.setContentView(view1);
	}

	/**
	 * 返回退出exit
	 */
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
