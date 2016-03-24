package com.wlhl.hong;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private boolean noScroll;

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setNoScroll(boolean noScroll) {
		this.noScroll = noScroll;
	}

	public boolean onTouchEvent(MotionEvent arg0) {
		return false;

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;

	}
}
