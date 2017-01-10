package com.leafson.lifecycle.nanjing.http.pro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leafson.lifecycle.nanjing.bean.UpdownBean;
import com.leafson.lifecycle.nanjing.http.BaseJsonResponse;

public class BusUpdownsUpdateRes extends BaseJsonResponse
{
  private ArrayList<UpdownBean> updowns = new ArrayList();

  public BusUpdownsUpdateRes()
  {
    setMsgno(32770);
  }

  private UpdownBean parseObj(JSONObject paramJSONObject)
  {
    try
    {
      UpdownBean localUpdownBean = new UpdownBean();
      localUpdownBean.updown_id = paramJSONObject.getString("updown_id");
      localUpdownBean.line_id = paramJSONObject.getString("line_id");
      localUpdownBean.line_code = paramJSONObject.getString("line_code");
      localUpdownBean.line_interval = paramJSONObject.getString("line_interval");
      localUpdownBean.updown_type = paramJSONObject.getString("updown_type");
      localUpdownBean.bus_start = paramJSONObject.getString("bus_start");
      localUpdownBean.bus_end = paramJSONObject.getString("bus_end");
      localUpdownBean.st_start_id = paramJSONObject.getString("st_start_id");
      localUpdownBean.st_end_id = paramJSONObject.getString("st_end_id");
      localUpdownBean.updown_status = paramJSONObject.getString("updown_status");
      localUpdownBean.update_time = paramJSONObject.getString("updown_updated");
      return localUpdownBean;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public ArrayList<UpdownBean> getUpdowns()
  {
    return this.updowns;
  }

  public void init(String paramString)
  {
    super.init(paramString);
    if (this.resultJo == null)
      return;
    try
    {
      if (!this.resultJo.has("updowns"))
        return;
      JSONArray localJSONArray = this.resultJo.getJSONArray("updowns");
     
      for ( int i = 0; i < localJSONArray.length();i++){
      Object localObject = localJSONArray.get(i);
      if (localObject instanceof JSONObject)
      {
        UpdownBean localUpdownBean = parseObj((JSONObject)localObject);
        if (localUpdownBean != null)
          this.updowns.add(localUpdownBean);
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
 * Qualified Name:     cn.nj.busservice.http.pro.BusUpdownsUpdateRes
 * JD-Core Version:    0.5.4
 */