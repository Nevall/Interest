package com.interest.myapplication.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WebCacheDbHelper extends SQLiteOpenHelper {

	public WebCacheDbHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists WebCache (id INTEGER primary key autoincrement,newsId INTEGER unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    
}
