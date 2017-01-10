package com.leafson.fuzhou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
	private static String DBNAME = "itcast.db";
	private static int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	public static final String TABLE_ID = "setting_id";// 主键对应信息

	// 新闻
	public static final String TABLE_USER = "table_user";// 新闻表名
	public static final String TABLE_USER_NAME = "username";// 新闻主键
	public static final String TABLE_USER_PAZZWORDY = "pazzword";// 新闻主键
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_USER + " (" + //
				TABLE_ID + " integer primary key autoincrement, " + //
				TABLE_USER_NAME + " varchar(50), " + //
				TABLE_USER_PAZZWORDY + " VARCHAR(50))"//
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
