package com.interest.myapplication.Utils;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.interest.myapplication.MyApplication;

/**
 * 数据库工具类
 * @author H
 *
 */
public class DBUtil {

	/**
	 * 将最新新闻消息的JSON数据存储到数据库中
	 * @param json
	 */
	public static void replaceFirstData(String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE, Integer.MAX_VALUE);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * 读取数据可中存储的最新新闻消息JSON数据
	 * @return
	 */
	public static String getFirstData(){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = {""+Integer.MAX_VALUE};
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//遍历游标
		if (cursor.moveToFirst()) {
			//获取数据库中存储的JSON数据,并返回
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else {
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * 将过往新闻消息的JSON数据存储到数据库中
	 * @param json
	 */
	public static void replaceBeforeData(String date,String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE,date);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * 读取数据可中存储的最新新闻消息JSON数据
	 * @return
	 */
	public static String getBeforeData(String date){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = { date };
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//遍历游标
		if (cursor.moveToFirst()) {
			//获取数据库中存储的JSON数据
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//没有更多数据
			String whereClause = Constant.CACHE_COLUMN_DATE + " < ?";
			String[] whereArgs = { date };
			db.delete(Constant.CACHE_TABLE_NAME, whereClause, whereArgs);
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * 将主题日报新闻消息的JSON数据存储到数据库中
	 * @param json
	 */
	public static void replaceThemeNewsData(String urlId,String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE,Constant.BASE_COLUMN+urlId);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * 读取数据可中存储的主题日报新闻消息JSON数据
	 * @return
	 */
	public static String getThemeNewsData(String urlId){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = { Constant.BASE_COLUMN + urlId};
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//遍历游标
		if (cursor.moveToFirst()) {
			//获取数据库中存储的JSON数据
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//没有更多数据
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * 将新闻消息的具体内容JSON数据存储到数据库中
	 * @param newsId
	 * @param json
	 */
	public static void replaceNewsContent(String newsId,String json){
		SQLiteDatabase db = MyApplication.getWebCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.WEB_CACHE_COLUMN_NEWSID,newsId);
		contentValues.put(Constant.WEB_CACHE_COLUMN_JSON, json);
		db.replace(Constant.WEB_CACHE_TABLE_NAME, Constant.WEB_CACHE_COLUMN_NEWSID, contentValues);
		db.close();
	}

	/**
	 * 读取数据可中存储的主题日报新闻消息JSON数据
	 * @return
	 */
	public static String getNewsContent(String newsId){
		SQLiteDatabase db = MyApplication.getWebCacheDBHelper().getWritableDatabase();
		String selection = Constant.WEB_CACHE_COLUMN_NEWSID+" = ?";
		String[] selectionArgs = { newsId};
		Cursor cursor = db.query(Constant.WEB_CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//遍历游标
		if (cursor.moveToFirst()) {
			//获取数据库中存储的JSON数据
			String json = cursor.getString(cursor.getColumnIndex(Constant.WEB_CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//没有更多数据
			cursor.close();
			db.close();
			return null;
		}
	}
}
