package com.xinbo.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.yuchen.lib.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.RemoteViews;
import android.widget.Toast;
/**
 * APP upGrade软件升级
 */
public class UpGrade {
	private RemoteViews views;
	private NotificationManager nm;
	private Notification notification;
	private Activity context;
	private String savePath;
	private boolean isLoading;
	private Toast toast;
	private UpGrade(){};
	private static UpGrade upGrade;
	/**
	 * 实例化对象
	 * @return UpGrade
	 */
	public static UpGrade getInstance(){
		if(upGrade==null){
		return upGrade = new UpGrade();
		}else{
		return upGrade;
		}
	}
	public void checkVersions(final Activity context,String version,String info,final String downUrl){
		this.context=context;
		if(isLoading){
			if(toast!=null){
			toast.cancel();
			}
			toast=Toast.makeText(context, "下载中", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		try {
			if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
				savePath= Environment.getExternalStorageDirectory().getPath()
						+"/Download/"+downUrl.substring(downUrl.lastIndexOf("/"));
			}else{
				if(toast!=null){
				toast.cancel();
				}
				toast=Toast.makeText(context, "SDcard不可用", Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			if(context.isFinishing()){
				return;
			}
			PackageManager pm =context. getPackageManager();
			PackageInfo pi= pm.getPackageInfo(context.getPackageName(), 0);
			if(!pi.versionName.equals(version)){
				Builder builder = new AlertDialog.Builder(context)
				 .setMessage(info)
				 .setNegativeButton("取消", null)
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						nm = (NotificationManager)context.getSystemService
								(Context.NOTIFICATION_SERVICE);
						views = new RemoteViews(context.getPackageName(), R.layout.custom_download);
						notification = new Notification();
						notification.icon =R.drawable.iconxiazai;
//						notification.tickerText = "loading";
						notification.contentView = views;//通知显示的布局
						notification.flags=Notification.FLAG_AUTO_CANCEL;//点击;
						notification.contentIntent =PendingIntent.getActivity(context,0,new Intent(),0);//点击意图
						nm.notify(1, notification);//确定按钮下载升级包
						new MyAsynctask().execute(downUrl);
					}
				});
				builder.show();//需要升级就弹出升级框
			}else{
				if(toast!=null){
				toast.cancel();
				}
				toast=Toast.makeText(context, "当前已经是最新版本", Toast.LENGTH_SHORT);
				toast.show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
class MyAsynctask extends AsyncTask<String, Integer, String>{
		
		private InputStream is;
		private FileOutputStream fos;
		@Override
		protected void onProgressUpdate(Integer... values) {
			Integer pro = values[0];
			views.setTextViewText(R.id.textView1,pro+ "%");
			views.setProgressBar(R.id.progressBar1, 100, pro, false);
			nm.notify(1, notification);
			super.onProgressUpdate(values);
		}
		@Override
		protected String doInBackground(String... params) {
			String st=params[0];
			ConnectivityManager connectivitymanager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			try {
				URL url = new URL(st);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				int fileLength =conn.getContentLength();//网络文件大小
//				conn.setConnectTimeout(3000);
//				conn.setReadTimeout(3000);
				NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
				if(null==networkinfo||null==conn){
					return "连接异常,请重新下载";
				}
				File file = new File(savePath);
				if(!file.exists()){
					file.getParentFile().mkdirs();
				}
				is = conn.getInputStream();
				fos = new FileOutputStream(file);
				int len = 0;
				int load=0;
				int times=0;
				byte[] buffer=new byte[1024];
				while(-1!=(len=is.read(buffer))){
					isLoading=true;
					fos.write(buffer, 0, len);
					load+=len;
					int i=(int) (load*100/fileLength);
					if(i>=(5*times)){
						times++;
						publishProgress(i);
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
			if(null==networkinfo){
				return "连接异常,请重新下载";
			}
			Uri data=Uri.fromFile(new File(savePath));
			String type="application/vnd.android.package-archive";
			Intent intentok;
			if(Build.VERSION.SDK_INT>=14){
				intentok = new Intent(Intent.ACTION_INSTALL_PACKAGE);
			}else{
				intentok = new Intent(Intent.ACTION_VIEW);
			}
			intentok.setDataAndType(data, type);
			context.startActivity(intentok);//打开安装程序安装软件
			PendingIntent intent = PendingIntent.getActivity(context, 0, intentok, 0);
			notification.contentIntent = intent;//下载完成后点击意图跳转安装界面
			nm.notify(1, notification);
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,data));//扫描文件
			return "下载完成!";
		}
		@Override
		protected void onPostExecute(String result) {
			views.setTextViewText(R.id.textView1,result);
			nm.notify(1, notification);
			isLoading=false;
		super.onPostExecute(result);
		}
	}

}
