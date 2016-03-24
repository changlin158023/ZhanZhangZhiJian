package com.xinbo.utils;
import java.util.LinkedList;
import android.app.Activity;
import android.app.Application;
/*
 * exit app自定义退出程序
 */
public class ExitApp extends Application{
	/**
     * Activity列表getInstance()
     */
    private static LinkedList<Activity> activityList = new LinkedList<Activity>();
    /**
     * 全局唯一实例
     */
    private static ExitApp instance;
    /**
     * 该类采用单例模式，不能实例化
     */
    private ExitApp(){
    }
    /**
     * 获取类实例对象
     * @return    ExitApp
     */
    public static ExitApp getInstance() {
        if (null == instance) {
            instance = new ExitApp();            
        }
        return instance;
    }
    /**
     * 保存Activity到现有列表中
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
//    /**
//     * onDestory()时移除 
//     */
//    public void removeActivity(Activity activity){
//    	activityList.remove(activity);
//    }
    /**
     * 关闭所有之前保存的界面finish Activity
     */
    public void exit() {
    	try {
    		Activity activity;
    		for (int i=0; i<activityList.size(); i++) {
    			activity = activityList.get(i);
    			if(null!=activity){
    				activity.finish();
    			}
    		}
		} catch (Exception e) {
		}finally{
			System.exit(0);
		}
    }
}
