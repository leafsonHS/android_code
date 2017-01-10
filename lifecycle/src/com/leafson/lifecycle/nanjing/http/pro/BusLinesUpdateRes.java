package com.leafson.lifecycle.nanjing.http.pro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leafson.lifecycle.nanjing.bean.LineBean;
import com.leafson.lifecycle.nanjing.http.BaseJsonResponse;

public class BusLinesUpdateRes extends BaseJsonResponse
{
  private ArrayList<LineBean> lines = new ArrayList();

  public BusLinesUpdateRes()
  {
    setMsgno(32769);
  }

  private LineBean parseObj(JSONObject paramJSONObject)
  {
      LineBean localLineBean = new LineBean();
    try
    {
 
      localLineBean.line_id = paramJSONObject.getString("line_id");
      localLineBean.weiba_id = paramJSONObject.getString("weiba_id");
      localLineBean.line_code = paramJSONObject.getString("line_code");
      localLineBean.line_interval = paramJSONObject.getString("line_interval");
      localLineBean.line_name = paramJSONObject.getString("line_name");
      localLineBean.ltd = paramJSONObject.getString("ltd");
      localLineBean.line_status = paramJSONObject.getString("line_status");
      localLineBean.update_time = paramJSONObject.getString("line_updated");
      return localLineBean;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localLineBean;
  }

  public ArrayList<LineBean> getLines()
  {
    return this.lines;
  }

  public void init(String paramString)
  {
    super.init(paramString);
    if (this.resultJo == null)
      return;
    try
    {
      if (!this.resultJo.has("lines"))
      {
        return;
      }
        JSONArray localJSONArray = this.resultJo.getJSONArray("lines");
      for(int i=0;i< localJSONArray.length();i++)
      {
    	    Object localObject = localJSONArray.get(i);  
    	    if (localObject instanceof JSONObject)
    	      {
    	        LineBean localLineBean = parseObj((JSONObject)localObject);
    	        if (localLineBean != null){
    	          this.lines.add(localLineBean);
    	        }
    	      }
      }
  
    
   
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.pro.BusLinesUpdateRes
 * JD-Core Version:    0.5.4
 */