package com.leafson.lifecycle.nanjing.http;

import org.apache.http.Header;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil
{
  private static final boolean DEBUG = true;
  private static final String PROPERTY_COOKIE = "Cookie";
  private static String SESSIONID;
  private static AsyncHttpClient client = new AsyncHttpClient();

  static
  {
    client.setTimeout(11000);
  }

  public static void get(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    client.get(paramString, paramAsyncHttpResponseHandler);
  }

  public static void get(String paramString, BinaryHttpResponseHandler paramBinaryHttpResponseHandler)
  {
    client.get(paramString, paramBinaryHttpResponseHandler);
  }

  public static void get(String paramString, JsonHttpResponseHandler paramJsonHttpResponseHandler)
  {
    client.get(paramString, paramJsonHttpResponseHandler);
  }

  public static void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    client.get(paramString, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public static void get(String paramString, RequestParams paramRequestParams, JsonHttpResponseHandler paramJsonHttpResponseHandler)
  {
    client.get(paramString, paramRequestParams, paramJsonHttpResponseHandler);
  }

  public static AsyncHttpClient getClient()
  {
    return client;
  }

  public static String getSessionId()
  {
    return SESSIONID;
  }

  public static void sendHttp(BaseReqest paramBaseReqest, BaseResponse paramBaseResponse, Handler paramHandler)
  {
    String str = paramBaseReqest.getUrl();
    Log.d("==========req url==========", str);
    get(str, new MyJsonHandler(paramBaseResponse, paramHandler));
  }

  private static class MyJsonHandler extends AsyncHttpResponseHandler
  {
    private Handler handler;
    private BaseResponse res;

    public MyJsonHandler(BaseResponse paramBaseResponse, Handler paramHandler)
    {
      this.handler = paramHandler;
      this.res = paramBaseResponse;
    }

    private void parseRes(String paramString)
    {
      try
      {
        this.res.init(paramString);
        Message localMessage3;
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Message localMessage2;
        return;
      }
      finally
      {
        Message localMessage1 = new Message();
        localMessage1.what = this.res.getMsgno();
        localMessage1.obj = this.res;
        this.handler.sendMessage(localMessage1);
      }
    }

//    private void parseSession(Header[] paramArrayOfHeader)
//    {
//      int i = 0;
//      if (i >= paramArrayOfHeader.length)
//      {
//    	  return;
//      }
//      
//      Header localHeader = paramArrayOfHeader[i];
//      String[] arrayOfString;
//      int j;
//      if ("Set-Cookie".equals(localHeader.getName()))
//      {
//        String str1 = localHeader.getValue();
//        if (str1.contains(";"))
//        {
//          arrayOfString = str1.split(";");
//          j = arrayOfString.length;
//        }
//      }
//      for (int k = 0; ; ++k)
//      {
//        if (k >= j);
//        while (true)
//        {
//          ++i;
//          break label2:
//          String str2 = arrayOfString[k];
//          if (!str2.contains("PHPSESSID"))
//            break;
//          HttpUtil.SESSIONID = str2;
//        }
//      }
//    }

    public void onFailure(int paramInt, Header[] paramArrayOfHeader, byte[] paramString, Throwable arg3)
    {
      Log.e("http error", "statusCode:" + paramInt + ",response:" + new String(paramString) + ",e=" + arg3.getMessage());
      parseRes(new String(paramString));
    }

    public void onSuccess(int paramInt, Header[] paramArrayOfHeader, byte[]  paramString)
    {
      Log.d("==========response==========", new String(paramString));
      parseRes(new String(paramString));
    }

	

  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.HttpUtil
 * JD-Core Version:    0.5.4
 */