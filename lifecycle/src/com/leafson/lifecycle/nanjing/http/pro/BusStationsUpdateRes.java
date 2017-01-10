package com.leafson.lifecycle.nanjing.http.pro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leafson.lifecycle.nanjing.bean.BusStationBean;
import com.leafson.lifecycle.nanjing.http.BaseJsonResponse;

public class BusStationsUpdateRes extends BaseJsonResponse
{
  private ArrayList<BusStationBean> stations = new ArrayList();

  public BusStationsUpdateRes()
  {
    setMsgno(32771);
  }

  private BusStationBean parseObj(JSONObject paramJSONObject)
  {
    try
    {
      BusStationBean localBusStationBean = new BusStationBean();
      localBusStationBean.st_id = paramJSONObject.getString("st_id");
      localBusStationBean.st_name = paramJSONObject.getString("st_name");
      localBusStationBean.st_real_lat = paramJSONObject.getDouble("st_real_lat");
      localBusStationBean.st_real_lon = paramJSONObject.getDouble("st_real_lon");
      localBusStationBean.st_status = paramJSONObject.getString("st_status");
      localBusStationBean.weiba_id = paramJSONObject.getString("weiba_id");
      localBusStationBean.st_side = paramJSONObject.getString("st_side");
      localBusStationBean.is_true = paramJSONObject.getInt("is_true");
      localBusStationBean.update_time = paramJSONObject.getString("st_updated");
      return localBusStationBean;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public ArrayList<BusStationBean> getStations()
  {
    return this.stations;
  }

  public void init(String paramString)
  {
    super.init(paramString);
    if (this.resultJo == null)
      return;
    try
    {
      if (!this.resultJo.has("stations"))
        return;
      JSONArray localJSONArray = this.resultJo.getJSONArray("stations");
  
      for( int i = 0; i< localJSONArray.length();i++){
    	  Object localObject = localJSONArray.get(i);
	      if (localObject instanceof JSONObject)
	      {
	        BusStationBean localBusStationBean = parseObj((JSONObject)localObject);
	        if (localBusStationBean != null)
	          this.stations.add(localBusStationBean);
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
 * Qualified Name:     cn.nj.busservice.http.pro.BusStationsUpdateRes
 * JD-Core Version:    0.5.4
 */