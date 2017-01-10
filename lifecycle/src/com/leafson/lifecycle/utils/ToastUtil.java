package com.leafson.lifecycle.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil
{
  public static void show(Context paramContext, int paramInt)
  {
    Toast.makeText(paramContext, paramInt, 1).show();
  }

  public static void show(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 1).show();
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.util.ToastUtil
 * JD-Core Version:    0.5.4
 */