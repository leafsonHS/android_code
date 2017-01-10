package com.leafson.lifecycle.nanjing.http;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseJsonResponse extends BaseResponse
{
  protected JSONObject jso;
  protected int page = 0;
  protected int pages = 0;
  protected JSONObject resultJo;
  protected int total = 0;

  public int getPage()
  {
    return this.page;
  }

  public int getPages()
  {
    return this.pages;
  }

  public void init(String paramString)
  {
    if (paramString != null);
    try
    {
      this.jso = new JSONObject(paramString);
      if (this.jso.has("status"))
        this.resultcode = ((String)this.jso.get("status"));
      if (this.jso.has("result"))
      {
        this.resultJo = this.jso.getJSONObject("result");
        if (this.resultJo != null)
        {
          this.page = this.resultJo.getInt("page");
          this.pages = this.resultJo.getInt("pages");
        }
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  public boolean isOK()
  {
    return "ok".equals(this.resultcode);
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.BaseJsonResponse
 * JD-Core Version:    0.5.4
 */