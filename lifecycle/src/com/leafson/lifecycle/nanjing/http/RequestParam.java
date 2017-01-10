package com.leafson.lifecycle.nanjing.http;

import java.util.Arrays;

import com.leafson.lifecycle.nanjing.util.Md5;

public class RequestParam
{
  private final String parseLineReq(String[] paramArrayOfString, String paramString)
  {
    String[] arrayOfString = new String[1 + paramArrayOfString.length];
    arrayOfString[0] = "api_keyfa6ea67bafae6709557430523e00802a";
    if (paramArrayOfString.length != 0){
      System.arraycopy(paramArrayOfString, 0, arrayOfString, 1, paramArrayOfString.length);
	    Arrays.sort(arrayOfString);
	    StringBuffer localStringBuffer = new StringBuffer();
	    int i = 0;
	    for(i=0;i<arrayOfString.length;i++)
	    {
	    	   localStringBuffer.append(arrayOfString[i]);	
	    	
	    } 
	    String str = Md5.encode("ae6709a" + localStringBuffer.toString());	
    	
    // str = DigestUtils.md5Hex("ae6709a" + localStringBuffer.toString());
	    return "http://testbk.jstv.com/rest2/" + paramString + "&api_key=fa6ea67bafae6709557430523e00802a&api_sig=" + str;
    }
	return null;
   
  }

  public String getLines(String paramString1, String paramString2, String paramString3, long paramLong)
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = ("line_code" + paramString1);
    arrayOfString[1] = ("line_id" + paramString2);
    arrayOfString[2] = ("updown_type" + paramString3);
    arrayOfString[3] = ("timestamp" + paramLong);
    return parseLineReq(arrayOfString, "/Bus/getBuses/?line_id=" + paramString1 + "&line_id=" + paramString2 + "&updown_type=" + paramString3 + "&timestamp=" + paramLong);
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.requestParam
 * JD-Core Version:    0.5.4
 */