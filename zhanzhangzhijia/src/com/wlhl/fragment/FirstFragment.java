package com.wlhl.fragment;

import com.wlhl.hong.Constant;
import com.wlhl.zhanzhangzhijia.HomeActivity;
import com.wlhl.zhanzhangzhijia.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FirstFragment extends Fragment implements OnClickListener {

	private View inflate;
	private TextView tvstart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflate = inflater.inflate(R.layout.fragment_first, container, false);
		tvstart = (TextView) inflate.findViewById(R.id.tvstart);
		tvstart.setOnClickListener(this);
		change();
		return inflate;
	}

	private void change() {
		View bg = inflate.findViewById(R.id.RelativeLayout1);
		Bundle intent = getArguments();
		int arg = intent.getInt("arg", 0);// 获取当前fragment位置参数
		if (false) {
			bg.setBackgroundResource(R.drawable.welcome);
			tvstart.setVisibility(View.INVISIBLE);
		} else {
			bg.setBackgroundResource(R.drawable.first);
			tvstart.setVisibility(View.VISIBLE);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Animation animation = new TranslateAnimation(0, 0, tvstart
							.getWidth(), 0);
					animation.setDuration(2000);
					tvstart.startAnimation(animation);
				}
			}, 0);
		}
	}

	@Override
	public void onClick(View v) {
		SharedPreferences sp = getActivity().getSharedPreferences(
				Constant.SP.SPNAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean(Constant.SP.ISFIRST, true);
		edit.commit();
		Intent intent = new Intent(getActivity(), HomeActivity.class);
		startActivity(intent);
		getActivity().finish();
	}
}