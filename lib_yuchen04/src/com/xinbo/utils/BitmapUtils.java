package com.xinbo.utils;
import java.io.ByteArrayOutputStream;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BitmapUtils {
	/**
	 *Bitmap to Drawable
	 * @param bmp
	 * @return Drawable
	 */
	public static Drawable bitmap2Drawable(Bitmap bmp) {
		BitmapDrawable bd = new BitmapDrawable(bmp);
		return bd;
	}
	/**
	 * Drawable to Bitmap
	 * @param drawable
	 * @return Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}
	/**资源图片Resources to Bitmap
	 * @param activity
	 * @param resId
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromResources(Activity activity, int resId) {
		Resources res = activity.getResources();
		return BitmapFactory.decodeResource(res, resId);
	}
	/**控件转图片截屏View to Bitmap
	 * @param view
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromImageView(View view){
		view.setDrawingCacheEnabled(true);
		Bitmap bmp = view.getDrawingCache();
		return bmp;
	}
	/**数组转图片byte[] to Bitmap
	 * @param bytee
	 * @return Bitmap
	 */
	public static Bitmap convertBytes2Bimap(byte[] bytee) {
		if (bytee.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(bytee, 0,bytee.length);
	}
	/**图片转数组Bitmap to byte[]
	 * @param bm
	 * @return byte[]
	 */
	public static byte[] convertBitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	/**放大图片
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap bigBitmap(Bitmap bitmap) {
	  Matrix matrix = new Matrix(); 
	  matrix.postScale(1.5f,1.5f); //长和宽放大缩小的比例
	  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
	  return resizeBmp;
	 }
	/**缩小图片
	* @param bitmap
	* @return Bitmap
	*/
	 public static Bitmap smallBitmap(Bitmap bitmap) {
	  Matrix matrix = new Matrix(); 
	  matrix.postScale(0.8f,0.8f); //长和宽放大缩小的比例
	  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
	  return resizeBmp;
	 }
	/**压缩图片解决OOM
	 * @param filepath SD卡路径
	 * @param widthpx 指定宽
	 * @param heightpx 指定高
	 * @return Bitmap
	 */
	public static Bitmap oomBitmap(String filepath,int widthpx,int heightpx){
		Options options =new BitmapFactory.Options();
		options.inJustDecodeBounds=true;//不在内存中读取图片
		options.inPreferredConfig = Bitmap.Config.RGB_565;//代表RGB位图
		BitmapFactory.decodeFile(filepath, options);//压缩前图片
		int width= options.outWidth;//原始图片宽
		int height=options.outHeight;//原始图片高
		if(height>heightpx||width>widthpx){
			int inheight=Math.round((float)height/(float)heightpx);
			int inwidth=Math.round((float)width/(float)widthpx);
			int inSize=inheight>inwidth?inheight:inwidth;
			options.inSampleSize=inSize;//缩放值:1不缩放 2为缩放1/2
		}
		options.inJustDecodeBounds=false;//在内存中创建图片
		Bitmap bm = BitmapFactory.decodeFile(filepath, options);//压缩后的图片
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG,100, baos);//压缩图片
		return bm;
	}
}
