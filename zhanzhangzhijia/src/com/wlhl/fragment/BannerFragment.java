package com.wlhl.fragment;

import com.wlhl.zhanzhangzhijia.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class BannerFragment extends Fragment {
	private View inflate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (inflate == null) {
			inflate = inflater.inflate(R.layout.fragment_banner, container,
					false);
		}
		ViewGroup parent = (ViewGroup) inflate.getParent();
		if (parent != null) {
			parent.removeView(inflate);
		}
		int arg = getArguments().getInt("arg", 0);
		ImageView im = (ImageView) inflate.findViewById(R.id.imageView1);
		switch (arg) {
		case 0:
			im.setBackgroundResource(R.drawable.bannerx);
			
			break;
		case 1:
			im.setBackgroundResource(R.drawable.bannerx2);
			break;
		case 2:
			im.setBackgroundResource(R.drawable.bannerx);
			break;
//		case 3:
//			im.setBackgroundResource(R.drawable.bannerx2);
//			break;
//		case 4:
//			im.setBackgroundColor(getResources().getColor(R.color.orange));
//			break;
		}
		// Log.e("图片fragment-----------------",arg+"");
		return inflate;
	}
}
