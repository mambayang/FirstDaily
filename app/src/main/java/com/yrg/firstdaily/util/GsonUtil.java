package com.yrg.firstdaily.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	public GsonUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 解析成实体
	 * 
	 * @param gString
	 * @param cls
	 * @return
	 */
	public static <T> T GsonToEntity(String gString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			t = gson.fromJson(gString, cls);
		}
		return t;
	}

	/**
	 * 解析成list
	 * 
	 * @param gString
	 * @return
	 */
	public static <T> ArrayList<T> GsonToList(String gString, Class<T> cls) {
		ArrayList<T> list = null;
		if (gson != null) {
			list = gson.fromJson(gString, new TypeToken<ArrayList<T>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 解析成list(正确版)
	 * @param gString
	 * @param cls
	 * @return
	 */
	public static <T> ArrayList<T> fromJsonList(String gString, Class<T> cls) {
		ArrayList<T> list = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(gString).getAsJsonArray();
		for (final JsonElement elem : array) {
			list.add(gson.fromJson(elem, cls));
		}
		return list;
	}

	/**
	 * 解析成list中有map
	 * 
	 * @param gString
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String gString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gString,
					new TypeToken<List<Map<String, T>>>() {
					}.getType());
		}
		return list;
	}

	/**
	 * 对象转成json
	 * 
	 * @param object
	 * @return
	 */
	public static String GsonString(Object object) {
		String gString = null;
		if (gson != null) {
			gString = gson.toJson(object);
		}
		return gString;
	}
}
