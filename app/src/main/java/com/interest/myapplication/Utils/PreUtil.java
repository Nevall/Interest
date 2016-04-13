package com.interest.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences������,���ڴ洢�û��������
 * @author H
 *
 */
public class PreUtil {

	/**
	 * �������SharedPreferences����ʽ�洢
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putStringToDefault(Context context, String key, String value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * ��ȡSharedPreferences�д洢�����
	 * @param context
	 * @param key
	 * @param defValue
	 */
	public static String getStringFromDefault(Context context ,String key,String defValue){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getString(key, defValue);
	}
}
