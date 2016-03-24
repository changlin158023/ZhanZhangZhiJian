package com.wlhl.hong;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.xinbo.utils.GetSetUntils;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class GetUntils {
	private String sid;
	private String name = Constant.SP.SPNAME;
	private String account = Constant.SP.ACCOUNT;
	private String us = Constant.SP.US;
	private static GetUntils obj;

	private GetUntils() {
	};

	public static GetUntils getInstance() {
		if (obj != null) {
			return obj;
		} else {
			obj = new GetUntils();
			return obj;
		}
	}

	public String getUS(Context context) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		String data = sp.getString(us, null);
		return data;
	}

	public void setUS(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(us, key);
		edit.commit();
	}

	public void removeUS(Context context) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.remove(us);
		edit.commit();
	}

	public String getAccount(Context context) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		String data = sp.getString(account, null);
		return data;
	}

	public void setAccount(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(account, key);
		edit.commit();
	}

	/**
	 * get session id
	 * 
	 * @param context
	 * @return
	 */
	public String getSid(final Context context) {
		MyVolley.get(context, Constant.MYURL.SESSIONID, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				GetSetUntils.setToast(context, false, "网络异常 稍后重试");
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
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		return sid;
	}

	/**
	 * get iamge salt
	 * 
	 * @param activity
	 * @return
	 */
	public void getImageSalt(final Activity activity,
			final ImageView imageview, final String sid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(Constant.MYURL.SALT + sid);
					InputStream inputStream = url.openStream();
					BufferedInputStream bfs = new BufferedInputStream(
							inputStream);
					final Bitmap bitmap = BitmapFactory.decodeStream(bfs);
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imageview.setImageBitmap(bitmap);
						}
					});
					inputStream.close();
					bfs.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
