package com.xinbo.utils;
import com.yuchen.lib.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class GetSetUntils {
	private static String dates;
    private static Toast toast;
	private static ImageView imToast;
	private static TextView tvToast;
	
    /**
     * makeToast 面包土司
     * @param context 环境
     * @param isSuccess true成功 false失败
     * @param text 内容
     */
	public static void setToast(final Context context,final boolean isSuccess,final String text){
		if(toast==null){
			View view=LayoutInflater.from(context).inflate(R.layout.toast_item, null);
			imToast = (ImageView) view.findViewById(R.id.im);
			tvToast = (TextView) view.findViewById(R.id.textView1);
			toast=new Toast(context);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setView(view);
		}
		if(isSuccess==true){
			imToast.setImageResource(R.drawable.success); 
		}else if(isSuccess==false){
			imToast.setImageResource(R.drawable.error);	
		}
		tvToast.setText(text);
		toast.show();
	}
//	private void setParams() {
//		  LayoutParams mParams = new WindowManager.LayoutParams();
//		  mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  
//		  mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;  
//		  mParams.format = PixelFormat.TRANSLUCENT;  
//		  mParams.windowAnimations = R.style.anim_view;//设置进入退出动画效果
//		  mParams.type = WindowManager.LayoutParams.TYPE_TOAST;  
//		  mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  
//		      | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
//		      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//		  mParams.gravity = Gravity.CENTER_HORIZONTAL;
//		  mParams.y = 250;
//		}
	/**
	 * setProgressDialog显示进度条
	 * @param context
	 */
	public static void setProgressDialog(final Activity context){
		new Thread(){
			public void run() {
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Builder builder = new AlertDialog.Builder(context);
						View view=LayoutInflater.from(context).inflate(R.layout.progress_item, null);
						ImageView im = (ImageView)view.findViewById(R.id.im);
						AnimationSet animation=new AnimationSet(true);
						Animation rota=new RotateAnimation(0f, 360f,Animation.RELATIVE_TO_SELF,0.5f
										,Animation.RELATIVE_TO_SELF,0.5f);
						rota.setRepeatCount(0);
						Interpolator i=new LinearInterpolator();
						animation.setInterpolator(i);
						animation.setDuration(800);
						animation.addAnimation(rota);
						im.startAnimation(animation); 
						final AlertDialog dialog = builder.show();
						dialog.getWindow().setContentView(view);
						new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
								dialog.dismiss();
								}
						},800); 
					}
				});
			};
		}.start();
	}
	/**
	 * new AlertDialog
	 * @param context 主环境
	 * @param text 需要显示的内容
	 * @param DialogInterface.OnClickListener 确定监听事件
	 */
	public static void setAlertDialog(Context context,String text
					,DialogInterface.OnClickListener onClickListener){
		Builder ad = new AlertDialog.Builder(context)
		.setMessage(text)
		.setNegativeButton("取消", null)
		.setPositiveButton("确定", onClickListener);
		ad.show();
	}
	/**
	 * new DatePickerDialog 时间选择框
	 * @param context  
	 * @param year
	 * @param month
	 * @param day
	 * @param tv
	 */
	public static void setDateDialog(Context context,int year,int month,int day,final TextView tv){
	    new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
		dates=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
		tv.setText(dates);
		}
	  }, year, month, day).show();
	}
	/**
	 * getDateDialogText year month day获取选择的日期时间
	 * @return
	 */
	public static String getDateDialogText(){
		return dates;
	}
/**
 * SharedPreferences 内部存储
 * @param context
 * @param name
 * @param key
 * @return
 */
	public  String getUS(Context context,String name,String key){
    	SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    	String data = sp.getString(key, null);
    	return data;
    }
    public  void setUS(Context context,String name,String key,String value){
    	SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    	Editor edit = sp.edit();
    	edit.putString(key, value);
    	edit.commit();
    } 
}
