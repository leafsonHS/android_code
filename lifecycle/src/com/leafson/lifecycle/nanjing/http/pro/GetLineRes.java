package com.leafson.lifecycle.nanjing.http.pro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leafson.lifecycle.nanjing.bean.BusStationBean;
import com.leafson.lifecycle.nanjing.http.BaseJsonResponse;

public class GetLineRes extends BaseJsonResponse
{
  private ArrayList<BusStationBean> stations = new ArrayList();

  public GetLineRes()
  {
    setMsgno(32773);
  }

  private BusStationBean parseObj(JSONObject paramJSONObject)
  {
    try
    {
      BusStationBean localBusStationBean = new BusStationBean();
      localBusStationBean.st_id = paramJSONObject.getString("st_id");
      localBusStationBean.distence = paramJSONObject.getString("st_dis");
      localBusStationBean.update_time = paramJSONObject.getString("updated");
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
    if (this.jso == null)
      return;
    try
    {
      if (!this.jso.has("buses"))
        return;
      JSONArray localJSONArray = this.jso.getJSONArray("buses");
      for ( int i = 0; i < localJSONArray.length();i++){
	      Object localObject = localJSONArray.get(i);
	      if (localObject instanceof JSONObject)
	      {
	        BusStationBean localBusStationBean = parseObj((JSONObject)localObject);
	        if (localBusStationBean != null){
	          this.stations.add(localBusStationBean);
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
 * Qualified Name:     cn.nj.busservice.http.pro.GetLineRes
 * JD-Core Version:    0.5.4
 */