package com.wlhl.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.volley_examples.app.MyVolley;
import com.github.volley_examples.app.VolleyListener;
import com.wlhl.hong.Constant;
import com.wlhl.hong.GetUntils;
import com.wlhl.hong.SystemMessage;
import com.wlhl.zhanzhangzhijia.IntroductionActivity;
import com.wlhl.zhanzhangzhijia.R;
import com.xinbo.utils.GetSetUntils;

public class AnnounceFragment extends Fragment {
	private ListView listView;
	private ArrayList<SystemMessage> almessage = new ArrayList<SystemMessage>();
	private String us;
	private adapter adapter;

	/**
	 * AnnounceFragment系统公告
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_messagecentt, null);
		getdata();
		listView = (ListView) inflate.findViewById(R.id.listView1);
		adapter = new adapter();
		listView.setAdapter(adapter);
		return inflate;
	}

	private void getdata() {
		us = GetUntils.getInstance().getUS(getActivity());
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userauth", us);
		MyVolley.post(getActivity(), Constant.MYURL.SYSTEMANNOUN, params,
				new VolleyListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						GetSetUntils.setToast(getActivity(), false, "还未登录");
					}

					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject jsonObject = new JSONObject(arg0);
							JSONObject datas = jsonObject
									.optJSONObject("datas");
							JSONArray data = datas.optJSONArray("data");
							int length = data.length();
							for (int i = 0; i < length; i++) {
								JSONObject job = data.optJSONObject(i);
								String id = job.optString("title");
								String info = job.optString("info");
								String date = job.optString("adate");
								almessage
										.add(new SystemMessage(id, info, date));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							adapter.notifyDataSetChanged();
						}
					}
				});
	}

	class adapter extends BaseAdapter {

		public int getCount() {
			return almessage.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = getActivity().getLayoutInflater().inflate(
					R.layout.listitem_message, null);

			RelativeLayout item = (RelativeLayout) inflate
					.findViewById(R.id.item);
			final int index = position;
			item.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String info = almessage.get(index).getInfo();
					Intent intent = new Intent(getActivity(),
							IntroductionActivity.class);
					intent.putExtra("info", "" + info);
					startActivity(intent);
				}
			});

			TextView tvinfo = (TextView) inflate.findViewById(R.id.textView1);
			TextView tv = (TextView) inflate.findViewById(R.id.textView3);
			TextView tvdate = (TextView) inflate.findViewById(R.id.textView4);
			tvinfo.setText(almessage.get(position).info);
			tvdate.setText(almessage.get(position).date);
			tv.setText(almessage.get(position).id);
			return inflate;
		}
	}

}
