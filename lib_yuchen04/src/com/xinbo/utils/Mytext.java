package com.xinbo.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/*
 * textview keep focus文字滚动保持焦点
 */
public class Mytext extends TextView {
	public Mytext(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (hasWindowFocus) {
			super.onWindowFocusChanged(hasWindowFocus);
		}
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
