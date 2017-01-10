package com.leafson.lifecycle.nanjing.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.leafson.lifecycle.utils.Md5Util;


public abstract class BaseReqest
{
  protected LinkedHashMap<String, String> params = new LinkedHashMap();

  private String parseParam()
  {
    Set localSet = this.params.keySet();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    Iterator localIterator = localSet.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localStringBuffer.append("&api_sig=" + getSig());
        return localStringBuffer.toString();
      }
      String str = (String)localIterator.next();
      ++i;
      localStringBuffer.append(str + "=" + (String)this.params.get(str));
      if (i >= localSet.size())
        continue;
      localStringBuffer.append("&");
    }
  }

  public void addParams(String paramString1, String paramString2)
  {
    this.params.put(paramString1, paramString2);
  }


  public String getSig()
  {
    Set localSet = this.params.keySet();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
        String str = (String)localIterator.next();
        localArrayList.add(str + (String)this.params.get(str));
    }
    return Md5Util.getSig(localArrayList);
  }

  public String getUrl()
  {
  //  this.params.put("timestamp", String.valueOf((int)(System.currentTimeMillis() / 1000L)));
    this.params.put("api_key", "fa6ea67bafae6709557430523e00802a");
    return getUrlParam() + parseParam();
  }

  public abstract String getUrlParam();
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.BaseReqest
 * JD-Core Version:    0.5.4
 */