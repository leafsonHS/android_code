package com.leafson.lifecycle.nanjing.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
  private static final String DATABASE_NAME = "ibus_201609018.db";
  private static final int DATABASE_VERSION = 1;

  public MyDatabaseHelper(Context paramContext)
  {
    super(paramContext, "ibus_201609018.db", null, 1);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.db.MyDatabaseHelper
 * JD-Core Version:    0.5.4
 */