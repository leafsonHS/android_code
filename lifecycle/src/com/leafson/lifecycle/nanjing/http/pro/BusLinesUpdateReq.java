package com.leafson.lifecycle.nanjing.http.pro;

import com.leafson.lifecycle.nanjing.http.BaseReqest;
import com.loopj.android.http.RequestParams;

public class BusLinesUpdateReq extends BaseReqest
{
  public RequestParams getReqParams()
  {
    return null;
  }

  public String getUrlParam()
  {
    return "http://bus.inj100.jstv.com/getAllLines?";
  }
}