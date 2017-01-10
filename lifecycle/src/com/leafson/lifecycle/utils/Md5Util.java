package com.leafson.lifecycle.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util
{
  public static String getSig(ArrayList<String> paramArrayList)
  {
	
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      String[] arrayOfString = (String[])paramArrayList.toArray(new String[paramArrayList.size()]);
      Arrays.sort(arrayOfString);
      StringBuffer localStringBuffer = new StringBuffer();
      int i = arrayOfString.length;
      for (int j = 0; ; ++j)
      {
        if (j >= i)
        {
          System.out.println(localStringBuffer.toString());
          return DigestUtils.md5Hex("ae6709a" + localStringBuffer.toString());
        }
        localStringBuffer.append(arrayOfString[j]);
      }
    }
    return null;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.util.Md5Util
 * JD-Core Version:    0.5.4
 */