package com.xinbo.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public final class GsonUtils {

	public static <T> T parseJSON(String json, Class<T> clazz) {
		Gson gson = new Gson();
		T info = gson.fromJson(json, clazz);
		return info;
	}
	
	/**
	 * Type type = new 
			TypeToken&lt;ArrayList&lt;TypeInfo>>(){}.getType();
	   Type���ڵİ�java.lang.reflect
	   TypeToken���ڵİ�com.google.gson.reflect.TypeToken	
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T parseJSONArray(String jsonArr, Type type) {
		Gson gson = new Gson();
		T infos = gson.fromJson(jsonArr, type);
		return infos;
	}
	
	
	
	private GsonUtils(){}
}
