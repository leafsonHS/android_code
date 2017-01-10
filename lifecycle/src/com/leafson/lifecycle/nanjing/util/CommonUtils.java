package com.leafson.lifecycle.nanjing.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;

import com.leafson.lifecycle.R;

public class CommonUtils
{
  public static void copyDB(Context paramContext)
  {
    try
    {
     
      File localFile = paramContext.getDatabasePath("ibus_201609018.db");
      if (localFile.exists())
      {
    	  //有数据库
        return;
      }else
      {		
    	  InputStream localInputStream = paramContext.getResources().openRawResource(R.raw.ibus_201609018) ;
    	   Log.d("note", "bus db not exist,begin to copy....");
    	  String[] names= localFile.getAbsolutePath().split("ibus_201609018");
    	
    	  File cacheFileDir = new File(names[0]);
    	  if(!cacheFileDir.exists()){
      	    cacheFileDir.mkdirs();//生成文件
      	  }
    	  
    	  File cacheFile = new File(cacheFileDir,"ibus_201609018"+names[1]);
    	  if(!cacheFile.exists()){
    	    cacheFile.createNewFile();//生成文件
    	  }
    	      FileOutputStream localFileOutputStream = new FileOutputStream(localFile.getAbsolutePath());
    	      byte[] arrayOfByte = new byte[1024];
    	      while(localInputStream.read(arrayOfByte)!=-1){
    	    	   localFileOutputStream.write(arrayOfByte);  
    	      }
    	      localFileOutputStream.flush();
  	        localFileOutputStream.close();
  	        localInputStream.close();
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
   
  }

  public static void initTable(Context paramContext)
  {
    DBUtil.createHistoryTable(paramContext);
  }

  public static boolean isNumber(String paramString)
  {
    return Pattern.compile("[0-9]*").matcher(paramString).matches();
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.util.CommonUtils
 * JD-Core Version:    0.5.4
 */