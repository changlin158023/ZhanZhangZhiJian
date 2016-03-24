package com.wlhl.hong;
import android.widget.TextView;
 
public class Countdown {
	private static int times;
	private Countdown(){};
	public static void startCountdown(final TextView view,final String text,final int time){
			times=time;
			view.setEnabled(false);
			view.postDelayed(new Runnable() {
				@Override
				public void run() {
					if(times<0){
					view.setEnabled(true);
					view.setText(text);
					return;
					}
					view.setText(times+"s");
					view.postDelayed(this, 1000);
					times--;
					}
			}, 0);
	}
}
