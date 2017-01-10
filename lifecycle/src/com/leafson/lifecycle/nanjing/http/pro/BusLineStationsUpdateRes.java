package com.leafson.lifecycle.nanjing.http.pro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leafson.lifecycle.nanjing.bean.BusBaseBean;
import com.leafson.lifecycle.nanjing.http.BaseJsonResponse;

public class BusLineStationsUpdateRes extends BaseJsonResponse
{
  private ArrayList<BusBaseBean> lineStations = new ArrayList();

  public BusLineStationsUpdateRes()
  {
    setMsgno(32772);
  }

  private BusBaseBean parseObj(JSONObject paramJSONObject)
  {
    try
    {
      BusBaseBean localBusBaseBean = new BusBaseBean();
      localBusBaseBean.line_station_id = paramJSONObject.getString("line_station_id");
      localBusBaseBean.line_id = paramJSONObject.getString("line_id");
      localBusBaseBean.line_code = paramJSONObject.getString("line_code");
      localBusBaseBean.line_interval = paramJSONObject.getString("line_interval");
      localBusBaseBean.updown_type = paramJSONObject.getString("updown_type");
      localBusBaseBean.st_id = paramJSONObject.getString("st_id");
      localBusBaseBean.st_name = paramJSONObject.getString("st_name");
      localBusBaseBean.st_stop_level = paramJSONObject.getInt("st_stop_level");
      localBusBaseBean.line_st_status = paramJSONObject.getString("line_st_status");
      localBusBaseBean.is_true = paramJSONObject.getInt("is_true");
      localBusBaseBean.update_time = paramJSONObject.getString("line_st_updated");
      return localBusBaseBean;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public ArrayList<BusBaseBean> getLineStations()
  {
    return this.lineStations;
  }

  public void init(String paramString)
  {
    super.init(paramString);
    if (this.resultJo == null)
      return;
    try
    {
      if (!this.resultJo.has("line_sts"))
        return;
      JSONArray localJSONArray = this.resultJo.getJSONArray("line_sts");
     
      for( int i = 0;i< localJSONArray.length();i++){
     
      Object localObject = localJSONArray.get(i);
      if (localObject instanceof JSONObject)
      {
        BusBaseBean localBusBaseBean = parseObj((JSONObject)localObject);
        if (localBusBaseBean != null)
          this.lineStations.add(localBusBaseBean);
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
 * Qualified Name:     cn.nj.busservice.http.pro.BusLineStationsUpdateRes
 * JD-Core Version:    0.5.4
 */