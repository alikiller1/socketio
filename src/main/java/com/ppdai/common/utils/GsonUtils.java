package com.ppdai.common.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson util
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2015  
 * Company:		拍拍贷
 * Author:		denghongbing
 * Version:		1.0  
 * Create at:	2015年9月1日 下午1:18:04  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class GsonUtils {

	private static Gson gson = new Gson();

	private static GsonBuilder gsonBuilder = new GsonBuilder();

	/**
	 * 对象转json字符
	 * 
	 * @param object
	 * @return
	 */
	public static String getJson(Object object) {
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		return gsonBuilder.create().toJson(object);
	}

	/**
	 * 对象转json字符
	 * 
	 * @param object
	 * @param date
	 * @return
	 */
	public static String getJson(Object object, String dateFormat) {
		return gsonBuilder.setDateFormat(dateFormat).create().toJson(object);
	}

	/**
	 * json字符转List<T>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
		return gson.fromJson(json, new ListOfSomething<T>(clazz));
	}

	/**
	 * json转对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}
}
