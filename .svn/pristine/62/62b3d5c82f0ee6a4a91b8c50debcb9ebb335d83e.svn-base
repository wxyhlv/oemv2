package com.capitalbio.oemv2.myapplication.FirstPeriod.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CatDBhelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "cat.db";
	private static final int DB_VERSION = 1;

	public CatDBhelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"create table  if not exists cat(id INTEGER PRIMARY KEY AUTOINCREMENT,index_id TEXT,username text,time text,aqi text,formaldehyde text,humidity text,temperature text,battery text,mac text,year int,month int,day int,hour int,minute int)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
