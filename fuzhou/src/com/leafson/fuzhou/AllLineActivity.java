package com.leafson.fuzhou;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.mobads.InterstitialAd;
import com.leafson.fuzhou.bean.LineDataObject;
import com.leafson.fuzhou.bean.LineObj;
import com.leafson.fuzhou.bean.Setting;
import com.leafson.fuzhou.bean.User;
import com.leafson.fuzhou.db.DAO;
import com.leafson.fuzhou.db.DAOImpl;
import com.leafson.fuzhou.utils.Utils;

public class AllLineActivity extends Activity {

	private ListView list;
	private ImageView refreashIcon;
	private EditText busLineNameFilter;
	public InterstitialAd interAd;
	private DAO settingDao = null;
	
	private DAO LineObjDao = null;
	
	
	
	private Handler handler = null;
	public static List<Map<String, Object>> allLineDatas=new  ArrayList<Map<String, Object>>();;
	public  TravelInfoLoader loadThread = null;
	public  List<Map<String, Object>> datas;
	public  List<Map<String, Object>> upLineList = new ArrayList<Map<String, Object>>();
	public  List<Map<String, Object>> downLineList = new ArrayList<Map<String, Object>>();
	private AnimationDrawable anim = null;
	private String upLine = null;
	private String downLine = null;
	private String lineNumbber="";
	private String lineName="";
	private String currentSelectedLine="";
	private ImageView back;
	public static Map<String, LineDataObject> LineDataObjectMap = new HashMap<String, LineDataObject>();
	private String text;
	public Map<String, Map<String, Object>> staticStations;
	private Timer timmer=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_allline);
		// 创建属于主线程的handler
		handler = new Handler();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			lineName= extras.getString("currentSelectedLine");
			lineNumbber= extras.getString("lineNumbber");
			currentSelectedLine  = extras.getString("currentSelectedLine");
			String	isUpline  = extras.getString("isUpline");
			if(null!=isUpline)
			{
			
				this.upperOrDown=isUpline;
			
				selectLine(lineName, lineNumbber,lineName,upperOrDown);
				
			}
		
			
		}
		list = (ListView) findViewById(R.id.ListView03);

		back = (ImageView) findViewById(R.id.titlebarallline_back_id);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if(lineNumbber.equals(""))
				{
					ToMainPage(null,true);
					return;
					
				}
				selectLine(currentSelectedLine, lineNumbber, lineName,upperOrDown);
			}
		});
		
		busLineNameFilter=(EditText) findViewById(R.id.busLineNameFilter);
		
		busLineNameFilter.addTextChangedListener(new TextWatcher() {  
            @Override  
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
                // TODO Auto-generated method stub  
            }  
  
            @Override  
            public void beforeTextChanged(CharSequence s, int start, int count,  
                    int after) {  
                // TODO Auto-generated method stub  
                  
            }  
              
            @Override  
            public void afterTextChanged(Editable s) {  
            
            
            	if(timmer!=null){
            		timmer.cancel();
            	}
                 timmer= new java.util.Timer();
            	 timmer.schedule(new TimerTask(){
            	       public void run() {
            	//这里写延迟后要运行的代码
            	  startSearch();
            	//如果只要这个延迟一次，用cancel方法取消掉．
            	  this.cancel();
            	  timmer=null;
            	 }}, 800);
            }  
            
            
        });  
		  
		text="10";
		startSearch();
	
	}
	private void startSearch() {
		if (loadThread != null) {
			loadThread.x = 1;
		}
		// anim.stop();
		// anim.start();\loadThread
		
		Log.e("xxxxxxxxxxxxxxxxxrun",text );
		loadThread = new TravelInfoLoader();
		loadThread.start();

	}
	/**
	 * 下载文件线程
	 */
	private class TravelInfoLoader extends Thread {
		public int x = 0;

		@Override
		public void run() {
			try {
				
			
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
				HttpPost httpgets = new HttpPost(
						"http://0591.mygolbs.com/fzwap_html5/searchBusNameListByName");		
				 List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
				 nvps.add(new BasicNameValuePair("city", "福州")); 
				text=busLineNameFilter.getText().toString().toUpperCase();
				 nvps.add(new BasicNameValuePair("busName", text));  
			     httpgets.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
				httpgets.setHeader("Accept","application/json, text/javascript, */*; q=0.01"); 
				httpgets.setHeader("Accept-Encoding","gzip, deflate"); 
				httpgets.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"); 
				httpgets.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"); 
				httpgets.setHeader("Cookie","JSESSIONID=B25D58230C058310F5C9511F6F671048"); 
				httpgets.setHeader("Host","0591.mygolbs.com"); 
				httpgets.setHeader("Referer","http://0591.mygolbs.com/fzwap_html5/?platformid=1&portaltype=0&version=3&columnid=&resourceid=SV350100000177"); 
				httpgets.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"); 
				httpgets.setHeader("X-Requested-With","XMLHttpRequest"); 
				HttpResponse response = httpclient.execute(httpgets);			
				HttpEntity entity = response.getEntity();
				InputStream instreams = entity.getContent();
				String uplineString1 =Utils.convertStreamToStringNoz(instreams);
				allLineDatas=	stationStringToList(uplineString1);
				handler.post(runnableTravelData);
 
			} catch (Exception e) {
				
		    
 				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
				handler.post(runnableTravelDataDilof);
			 return;
			}

		}
		
	
	}
	// 构建Runnable对象，在runnable中更新界�?
		Runnable runnableTravelDataInfo = new Runnable() {
			@Override
			public void run() {
				try{
				    
					Map<String, LineDataObject> object = LineDataObjectMap;// 强制类型转换
					LineDataObject line = object.get(lineNumbber);
					upLineList = line.getUpLineList();
					if(upLineList==null||upLineList.isEmpty())
					{
						upLineList=line.getDownLineList();
						
					}
					HomeActivity.currentStationName = (String) upLineList.get(0)
							.get("sn");
					HomeActivity.staticStations = line.getStaticStations();
					HomeActivity.downLine = line.getDownLine();
					HomeActivity.upLine = line.getUpLine();
					HomeActivity.downLineList = line.getDownLineList();
					HomeActivity.upLineList = line.getUpLineList();
					HomeActivity.lineNumbber = line.getLineCode();
					HomeActivity.lineName=lineName;
					HomeActivity.isUpline=Integer.parseInt(upperOrDown);
					ToMainPage(null,true);

			}catch(Exception e){
				e.printStackTrace();}
			}

			
		};
	
	// 构建Runnable对象，在runnable中更新界�?
	Runnable runnableTravelData = new Runnable() {
		@Override
		public void run() {
			try{
				SimpleAdapter listItemAdapter = new SimpleAdapter(
        				AllLineActivity.this, allLineDatas,// 数据�?
        				R.layout.list_line_items,// ListItem的XML实现
        				// 动�?数组与ImageItem对应的子�?
        				new String[] { "linename" ,"upTime","downTime","upLine","downLine"},
        				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
        				new int[] { R.id.linename,R.id.lineupTime,R.id.linedownTime,R.id.linederectionup,R.id.linederectiondown});
        		// 添加并且显示
				list.setAdapter(listItemAdapter);

				list.setOnItemClickListener(new OnItemClickListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ListView listView = (ListView) parent;
						HashMap<String, String> map = (HashMap<String, String>) listView
								.getItemAtPosition(position);
						String sno = map.get("busLineId");
						String sn = map.get("linename");
						String fullname = map.get("fullname");
						String upperOrDown = map.get("upperOrDown");
						
						
						selectLine(sn, sno,fullname,upperOrDown);
					}
				});
		}catch(Exception e){
			e.printStackTrace();}
		}
	};
	// 构建Runnable对象，在runnable中更新界�?
	Runnable runnableTravelDataDilof = new Runnable() {
		@Override
		public void run() {
			try{
				 Toast.makeText(getApplicationContext(), "蛋疼，连服务器的路上有点堵车。等等再来咯", Toast.LENGTH_LONG).show();
		}catch(Exception e){
			e.printStackTrace();}
		}
	};
	private String fullName;
	private String upperOrDown;
	private void loadingTravelInfo() {
		TravelInfoLoaderInfo loadThread = new TravelInfoLoaderInfo();
		loadThread.start();
	}

	private void selectLine(String currentSelectedLine, String lineNumbber, String lineName2, String upperOrDown) {
		try {
			this.currentSelectedLine=currentSelectedLine;
			this.lineNumbber=lineNumbber;
			this.lineName=currentSelectedLine;
			this.fullName=lineName2;
			this.upperOrDown=upperOrDown;
			
			
			loadingTravelInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void ToMainPage(User user, boolean logined) {
		try {
			Intent intent = new Intent();
			intent.putExtra("lineNumbber", lineNumbber);
			intent.putExtra("currentSelectedLine", currentSelectedLine);
			this.setResult(Activity.RESULT_OK, intent);
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void saveLineObj(List<Map<String, Object>>  maps) {
		LineObjDao = new DAOImpl<User>(getApplicationContext(),
				LineObj.class);
		List<LineObj> list = LineObjDao.findAll();
		LineObjDao.exeC("delete  from TABLE_LINEOBJ where 1=1;");
		LineObj lineObj = null;
		for(Map<String, Object> m:maps){
			lineObj=new LineObj();
			lineObj.setBusLineId((String) m.get("busLineId"));
			lineObj.setDownLine((String) m.get("downLine"));
			lineObj.setDownTime((String) m.get("downTime"));
			lineObj.setLinename((String) m.get("linename"));
			lineObj.setLinenamePinYin((String) m.get("linenamePinYin"));
			lineObj.setUpLine((String) m.get("upLine"));
			lineObj.setUpTime((String) m.get("upTime"));
			LineObjDao.insert(lineObj);
		}
		
		
		
		
		
	}
	
	private void saveSetting() {
		settingDao = new DAOImpl<User>(getApplicationContext(),
				Setting.class);
		List<Setting> list = settingDao.findAll();
		Setting setting = null;
		if (!list.isEmpty()) {
			setting=list.get(0);
		}
		if(setting==null)
		{
			setting=new Setting();
			setting.setNoticeType("0");
			setting.setRefreashInterval("20");
			setting.setDefaultBusLine(lineNumbber);
		}else if(setting!=null)
		{
			setting.setDefaultBusLine(lineNumbber);
		}
		if (setting.getId()!=null) {
			settingDao.update(setting);
			
		}else
		{
			settingDao.insert(setting);
		}
		
	}
	
	private class TravelInfoLoaderInfo extends Thread {
		@Override
		public void run() {
			try {
				
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
				HttpPost httpgets = new HttpPost(
						"http://0591.mygolbs.com/fzwap_html5/searchLineDetailByBusName");		
				 List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
				 nvps.add(new BasicNameValuePair("busName", currentSelectedLine)); 
				 nvps.add(new BasicNameValuePair("city","福州"));  
				 nvps.add(new BasicNameValuePair("upperOrDown", upperOrDown));  
			     httpgets.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
				httpgets.setHeader("Accept","application/json, text/javascript, */*; q=0.01"); 
				httpgets.setHeader("Accept-Encoding","gzip, deflate"); 
				httpgets.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"); 
				httpgets.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"); 
				httpgets.setHeader("Cookie","JSESSIONID=B25D58230C058310F5C9511F6F671048"); 
				httpgets.setHeader("Host","0591.mygolbs.com"); 
				httpgets.setHeader("Referer","http://0591.mygolbs.com/fzwap_html5/?platformid=1&portaltype=0&version=3&columnid=&resourceid=SV350100000177"); 
				httpgets.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"); 
				httpgets.setHeader("X-Requested-With","XMLHttpRequest"); 
				HttpResponse response = httpclient.execute(httpgets);	 		
				HttpEntity entity = response.getEntity();
				InputStream inStream = entity.getContent();
				staticStations = null;
				staticStations = stationStringToListInfo(inStream);
				handler.post(runnableTravelDataInfo);
			} catch (Exception e) {
 				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
				dialog();
			}

		}
		
	
	}
	private Map<String, Map<String, Object>> stationStringToListInfo(
			InputStream inStream  ) {
		Map<String, Map<String, Object>> updata = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> downdata = new HashMap<String, Map<String, Object>>();
		upLineList = new ArrayList<Map<String, Object>>();
		downLineList = new ArrayList<Map<String, Object>>();
		
		String  ss=Utils
				.convertStreamToStringNoz(inStream);
		
		try {
			JSONObject obj = new JSONObject(ss);
			
			JSONArray upArray = obj.getJSONArray("stations");
			
			String upTimeS=obj.getString("startTime");
			String upTimeE=obj.getString("endTime");
			String upTimeString=upTimeS+"-"+upTimeE;
			
			
			
			parseStation(updata, upArray,this.upperOrDown);
			updata.putAll(downdata);
			if (!upLineList.isEmpty()) {
				LineDataObject lineDataObject = new LineDataObject();
				lineDataObject.setUptime(upTimeString);
				lineDataObject.setDownLine(downLine);
				lineDataObject.setDownLineList(downLineList);
				lineDataObject.setLineCode(lineNumbber);
				lineDataObject.setLineName(lineName);
				lineDataObject.setStaticStations(updata);
				lineDataObject.setUpLine(upLine);
				lineDataObject.setUpLineList(upLineList);
				LineDataObjectMap.put(lineNumbber, lineDataObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("selectStation", e.getMessage());
		}
		return updata;
	}
	private 	 List<Map<String, Object>>	 stationStringToList(
			String rsContent) {
		
		try {
			
			JSONArray jSONArray = new JSONArray(rsContent);
			int length=jSONArray.length();
			allLineDatas=new  ArrayList<Map<String, Object>>();
			 HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();  
			for(int index=0;index<length;index++ )
			{
				Map<String, Object> mp=new HashMap<String, Object>();
				JSONObject ob=jSONArray.getJSONObject(index);
				String busLineName=ob.getString("name");
				String startStation=ob.getString("frontName");
				String b=ob.getString("endTime");
				String a=ob.getString("startTime");
				String busLineId=ob.getString("buslineId");
				String endStation=ob.getString("terminalName");
				String upperOrDown=ob.getString("upperOrDown");
				
				
				mp.put("linename", busLineName);
				mp.put("upLine", "发往"+endStation );
				mp.put("upTime", a+"-"+b);
				mp.put("fullname", busLineName+"("+startStation+"-"+endStation+")");
				mp.put("busLineId", busLineId);
				mp.put("upperOrDown", upperOrDown);
				allLineDatas.add(mp);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("selectStation", e.getMessage());
		}
		return allLineDatas;
	}
	

		

		/**
		 * 解析车站数据到列表中�?
		 * 
		 * @param data
		 * @param jsonObjs
		 * @param updwon
		 */
		private void parseStation(Map<String, Map<String, Object>> data,
				JSONArray jsonObjs, String updwon) {
			String begin = "";
			String end = "";
			for (int i = 0; i < jsonObjs.length(); i++) {
				JSONObject jsonObj = (JSONObject) jsonObjs.opt(i);
				String sn = null;
				String sno = null;
				String log = null;
				String lat = null;
				String si = null;
				try {
					sn = jsonObj.getString("name");
					sno = String.valueOf(i+1);
				
					if (i == 0) {
						begin = sn;
					}

					if (i == jsonObjs.length() - 1) {
						end = sn;
					}

				} catch (Exception e) {
					e.printStackTrace();
					Log.e("selectStation", e.getMessage());

				}
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("sn", sn == null ? "暂无" : sn);
				station.put("sno", sno == null ? "暂无" : sno);
				station.put("log", log == null ? "暂无" : log);
				station.put("lat", lat == null ? "暂无" : lat);
				station.put("si", si == null ? "暂无" : si);
				station.put("derection", "联系电话:");
				data.put(sno, station);

				upLineList.add(station);
				
			}
			ComparatorStationMap cmap = new ComparatorStationMap();
			if (null != upLineList) {
				Collections.sort(upLineList, cmap);
			}
			if (null != downLineList) {
				Collections.sort(downLineList, cmap);
			}
			
				upLine = begin + "-" + end;
			

		}

		public class ComparatorStationMap implements Comparator {

			public int compare(Object arg0, Object arg1) {
				Map<String, Object> data0 = (Map<String, Object>) arg0;
				Map<String, Object> data1 = (Map<String, Object>) arg1;

				// 首先比较年龄，如果年龄相同，则比较名�?
				int data0SortNo = Integer.parseInt((String) data0.get("sno"));
				int data1SortNo = Integer.parseInt((String) data1.get("sno"));

				if (data0SortNo > data1SortNo) {
					return 1;
				} else if (data0SortNo == data1SortNo) {
					return 0;
				} else {
					return -1;
				}
			}
		}
		protected void dialog() {
			
		}
		
		

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				selectLine(currentSelectedLine, lineNumbber, lineName,upperOrDown);
				this.finish();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}

}
