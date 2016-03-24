package com.xinbo.utils;
import java.io.IOException;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
public final class ConnectionUtils {
	private static final String UNKNWON = "unkwon";
	private static final String NOT_AVAILABLE = "not_avaible";
	private static final String WIFI = "wifi";
	private static final String G3NET = "3gnet";
	private static final String G3WAP = "3gwap";
	private static final String UNINET = "uninet";
	private static final String UNIWAP = "uniwap";
	private static final String CMNET = "cmnet";
	private static final String CMWAP = "cmwap";
	private static final String CTNET = "ctnet";
	private static final String CTWAP = "ctwap";
	private static final String MOBILE = "mobile";
	private static Toast toast;
	public static void checkConnected(final Activity activity) {
		String type = getNetApn(activity);
		if(NOT_AVAILABLE.equals(type)||UNKNWON.equals(type)){
			if(toast==null){
			toast = Toast.makeText(activity, "网络异常，请检查网络", Toast.LENGTH_SHORT);
			}
			toast.show();
		}
	}
	public static boolean isWifiConnected(Activity activity) {
		String type = getNetApn(activity);
		return WIFI.equals(type);
	}
	public static boolean isMobileConnected(Activity activity) {
		String type = getNetApn(activity);
		return !WIFI.equals(type);
	}
	private static String getNetApn(final Activity activity) {
		if (activity.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") 
				== PackageManager.PERMISSION_DENIED) {//是否添加网络检查权限
			return UNKNWON;
		}
		ConnectivityManager connectivitymanager = (ConnectivityManager) activity
				.getSystemService(activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
		if (networkinfo == null||!networkinfo.isAvailable()) {
			return NOT_AVAILABLE;
		}
		if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
			new Thread(){
				public void run() {
					try {
					  Process p = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 1 " 
								+ "www.baidu.com");//ping判断网络数据是否连通
					  int status = p.waitFor();
					  if (status != 0) {
					  activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
						if(toast==null){
						toast = Toast.makeText(activity, "网络异常，请检查网络", Toast.LENGTH_SHORT);
						}
						toast.show();	
						}
					  });
					  }
					} catch (IOException e) {
					} catch (InterruptedException e) {
					}
				}; 
			}.start();
			return WIFI;
		} 
		String netInfo = networkinfo.getExtraInfo();
		if (netInfo == null) {
			return UNKNWON;
		}
		netInfo = netInfo.toLowerCase();
		if (netInfo.equals("cmnet")) {
			return CMNET;
		} else if (netInfo.equals("cmwap")) {
			return CMWAP;
		} else if (netInfo.equals("3gnet")) {
			return G3NET;
		} else if (netInfo.equals("3gwap")) {
			return G3WAP;
		} else if (netInfo.equals("uninet")) {
			return UNINET;
		} else if (netInfo.equals("uniwap")) {
			return UNIWAP;
		} else if (netInfo.equals("ctnet")) {
			return CTNET;
		} else if (netInfo.equals("ctwap")) {
			return CTWAP;
		}else {
			return MOBILE;
		}
	}
}
