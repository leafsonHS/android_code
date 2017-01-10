package com.leafson.lifecycle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.InterstitialAd;
import com.leafson.lifecycle.bean.LineDataObject;
import com.leafson.lifecycle.bean.LineObj;
import com.leafson.lifecycle.bean.Setting;
import com.leafson.lifecycle.bean.User;
import com.leafson.lifecycle.db.DAO;
import com.leafson.lifecycle.db.DAOImpl;
import com.leafson.lifecycle.nanjing.util.DBUtil;
import com.leafson.lifecycle.utils.SpecialAdapter;
import com.leafson.lifecycle.utils.Utils;

public class AllLineActivity extends Activity {

	private ListView list;
	private ImageView refreashIcon;
	private EditText busLineNameFilter;
	public InterstitialAd interAd;
	private DAO settingDao = null;
	
	private DAO LineObjDao = null;
	
	public static String source="1";
	
	private Handler handler = null;
	public static List<Map<String, Object>> allLineDatas=new  ArrayList<Map<String, Object>>();;
	public  List<Map<String, Object>> datas;
	public static List<Map<String, Object>> upLineList = new ArrayList<Map<String, Object>>();
	public  static List<Map<String, Object>> downLineList = new ArrayList<Map<String, Object>>();
	private AnimationDrawable anim = null;
	private String upLine = null;
	private String downLine = null;
	private String downTimeString = null;
	private String upTimeString = null;
	public static List<LineObj> linelist =new ArrayList<LineObj>();
	public static List<Setting> setl =new ArrayList<Setting>();
	
	private String lineNumbberForGetStaion="";
	private String lineNumbber="";
	private String lineName="";
	public  AdView adView ;
	private String currentSelectedLine="";
	private ImageView back;
	public static Map<String, LineDataObject> LineDataObjectMap = new HashMap<String, LineDataObject>();
	private String text;
	public Map<String, Map<String, Object>> staticStations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_allline);
		// 创建属于主线程的handler
		handler = new Handler();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			lineName= extras.getString("lineName");
			lineNumbber= extras.getString("lineNumbber");
			currentSelectedLine  = extras.getString("currentSelectedLine");
		}
		list = (ListView) findViewById(R.id.ListView03);

		back = (ImageView) findViewById(R.id.titlebarallline_back_id);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ToMainPage(null,true);
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
            
            	String text=busLineNameFilter.getText().toString();
            	
            	ArrayList<Map<String, Object>> filterLineData=new ArrayList<Map<String, Object>> ();
            	
        		for(int i=0; i<allLineDatas.size(); i++) { 
        		
        			Map<String, Object> line =allLineDatas.get(i);
        			String lineName=(String)line.get("linename")+(String)line.get("linenamePinYin")+(String)line.get("linenamePinYinShort");
        			text=text.toLowerCase();
        			lineName=lineName.toLowerCase();
        			if(lineName.contains(text))
        			{
        				filterLineData.add(line);
        			}
        		}
        		SpecialAdapter listItemAdapter = new SpecialAdapter(
        				AllLineActivity.this, filterLineData,// 数据源
        				R.layout.list_line_items,// ListItem的XML实现
        				// 动态数组与ImageItem对应的子项
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
						String sr= map.get("source");
						downTimeString= map.get("downTime");
						upTimeString= map.get("upTime");
						downLine= map.get("downLine");
						upLine= map.get("upLine");
						
						selectLine(sn, sno,sr);
					}
				});
            }  
            
            
        });  
		  
		text="1";
		settingDao = new DAOImpl<User>(getApplicationContext(),
				Setting.class);
		LineObjDao = new DAOImpl<User>(getApplicationContext(),
				LineObj.class);
			if(linelist.isEmpty()){
				linelist = LineObjDao.findAll("source asc");
			allLineDatas=new  ArrayList<Map<String, Object>>();
			 HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();  
			 outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			 outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			 outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			for(LineObj m:linelist)
			{
				Map<String, Object> mp=new HashMap<String, Object>();
				mp.put("linename", m.getLinename());
				mp.put("upLine", m.getUpLine());
				mp.put("downLine", m.getDownLine());
				mp.put("downTime",m.getDownTime());
				mp.put("upTime", m.getUpTime());
				mp.put("busLineId",m.getBusLineId());
				mp.put("linenamePinYin", PinyinHelper.toHanyuPinyinString(m.getLinenamePinYin(), outputFormat,  ""));
				mp.put("source",m.getSource());
				allLineDatas.add(mp);
			}	
			}
			handler.post(runnableTravelData);
				
			
		
		
		if(!MainActivity.isClicked){
		
		RelativeLayout your_original_layout	 = (RelativeLayout) this
				.findViewById(R.id.AllLineActivity);;
		//人群属性
		AdSettings.setKey(new String[]{"学生","上班","南京","公交","交通"}
		);
		// 创建广告 view
		String adPlaceId =  "2435583" ;
		// 重要：请填上你的
		adView = new AdView(this,adPlaceId);
		// 设置监听器
		adView.setListener(new AdViewListener(){

			@Override
			public void onAdClick(JSONObject arg0) {
				// TODO Auto-generated method stub
				MainActivity.isClicked=true;
			}

			@Override
			public void onAdFailed(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAdReady(AdView arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAdShow(JSONObject arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAdSwitch() {
				// TODO Auto-generated method stub
				
			}});
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		your_original_layout.addView(adView,rllp);
		}
	
	}


	
	// 构建Runnable对象，在runnable中更新界面
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
					ToMainPage(null,true);

			}catch(Exception e){
				e.printStackTrace();}
			}

			
		};
	
	// 构建Runnable对象，在runnable中更新界面
	Runnable runnableTravelData = new Runnable() {
		@Override
		public void run() {
			try{
				SimpleAdapter listItemAdapter = new SimpleAdapter(
        				AllLineActivity.this, allLineDatas,// 数据源
        				R.layout.list_line_items,// ListItem的XML实现
        				// 动态数组与ImageItem对应的子项
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
						String sr= map.get("source");
						downTimeString= map.get("downTime");
						upTimeString= map.get("upTime");
						downLine= map.get("downLine");
						upLine= map.get("upLine");
						selectLine(sn, sno,sr);
					}
				});
		}catch(Exception e){
			e.printStackTrace();}
		}
	};
	// 构建Runnable对象，在runnable中更新界面
	Runnable runnableTravelDataDilof = new Runnable() {
		@Override
		public void run() {
			try{
				 Toast.makeText(getApplicationContext(), "蛋疼，服务器貌似被搞挂了。叔也在等待修复！", Toast.LENGTH_LONG).show();
		}catch(Exception e){
			e.printStackTrace();}
		}
	};

	private void loadingTravelInfo() {
		TravelInfoLoaderInfo loadThread = new TravelInfoLoaderInfo();
		loadThread.start();
	}

	private void selectLine(String currentSelectedLine, String lineNumbber, String source) {
		try {
			this.currentSelectedLine=currentSelectedLine;
			this.lineNumbber=lineNumbber;
			this.lineName=currentSelectedLine;
			this.source=source;
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
	
	
	private class TravelInfoLoaderInfo extends Thread {
		@Override
		public void run() {
			try {
				
				if(source.equals("1")){
//					String path = "http://223.112.2.69:5902/pubserverT/bus/busQuery.do?random=0.545071161352098&command=ln&busLineId="
//						+ lineNumbber;
//					URL url;
//					url = new URL(path);
//					HttpURLConnection conn = (HttpURLConnection) url
//							.openConnection();
//					conn.setReadTimeout(30 * 1000);
//					conn.setRequestMethod("GET");
//					InputStream inStream = conn.getInputStream();
//					staticStations = null;
//					String  ss=Utils
//							.convertStreamToStringNoz(inStream);
//					staticStations = stationStringToListInfo(ss);
//					
//					
					lineNumbberForGetStaion="10095"+lineNumbber;
					upLineList = new ArrayList<Map<String, Object>>();
					downLineList = new ArrayList<Map<String, Object>>();
					staticStations = stationStringToListInfoNj();

				}else
				{
					lineNumbberForGetStaion=lineNumbber;
					upLineList = new ArrayList<Map<String, Object>>();
					downLineList = new ArrayList<Map<String, Object>>();
					staticStations = stationStringToListInfoNj();
					
				}
				handler.post(runnableTravelDataInfo);
			} catch (Exception e) {
 				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
				dialog();
			}

		}

		private Map<String, Map<String, Object>> stationStringToListInfoNj() {
			Map<String, Map<String, Object>> updata= DBUtil.getStations(getApplicationContext(), lineNumbberForGetStaion);
			ComparatorStationMap cmap = new ComparatorStationMap();
			if (null != upLineList) {
				Collections.sort(upLineList, cmap);
			}
			if (null != downLineList) {
				Collections.sort(downLineList, cmap);
			}
			if (!upLineList.isEmpty()) {
				LineDataObject lineDataObject = new LineDataObject();
				lineDataObject.setDowntime(downTimeString);
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
			return updata;
			
		}
		
	
	}
	private Map<String, Map<String, Object>> stationStringToListInfo(
			String rsContent ) {
		Map<String, Map<String, Object>> updata = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> downdata = new HashMap<String, Map<String, Object>>();
		upLineList = new ArrayList<Map<String, Object>>();
		downLineList = new ArrayList<Map<String, Object>>();

		try {
			JSONObject obj = new JSONObject(rsContent);
			JSONArray jSONArray = obj.getJSONArray("data");
			JSONObject datas = (JSONObject) jSONArray.get(0);
			JSONArray downArray = datas.getJSONArray("downlist");
			
			JSONObject downtimeObject = datas.getJSONObject("downtime");
			JSONObject uptimeObject = datas.getJSONObject("uptime");
			String downTimeS=downtimeObject.getString("stime");
			String downTimeE=downtimeObject.getString("etime");
			String downTimeString=downTimeS+"——"+downTimeE;
			
			
			String upTimeS=uptimeObject.getString("stime");
			String upTimeE=uptimeObject.getString("etime");
			String upTimeString=upTimeS+"——"+upTimeE;
			
			parseStation(updata, downArray, 2);
			JSONArray upObjs = datas.getJSONArray("uplist");
			parseStation(downdata, upObjs, 1);
			updata.putAll(downdata);
			
			
			
			if (!upLineList.isEmpty()) {

				LineDataObject lineDataObject = new LineDataObject();
				lineDataObject.setDowntime(downTimeString);
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
	

		
		// 字符串转换成车站信息列表，
		private Map<String, Map<String, Object>> c(
				String rsContent) {
			Map<String, Map<String, Object>> updata = new HashMap<String, Map<String, Object>>();
			Map<String, Map<String, Object>> downdata = new HashMap<String, Map<String, Object>>();

			try {
				JSONObject obj = new JSONObject(rsContent);
				JSONArray jSONArray = obj.getJSONArray("data");
				JSONObject datas = (JSONObject) jSONArray.get(0);
				JSONArray downArray = datas.getJSONArray("downlist");
				parseStation(updata, downArray, 2);
				JSONArray upObjs = datas.getJSONArray("uplist");
				parseStation(downdata, upObjs, 1);
				updata.putAll(downdata);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
			}
			return updata;
		}

		/**
		 * 解析车站数据到列表中。
		 * 
		 * @param data
		 * @param jsonObjs
		 * @param updwon
		 */
		private void parseStation(Map<String, Map<String, Object>> data,
				JSONArray jsonObjs, int updwon) {
			String begin = "";
			String end = "";
			for (int i = 0; i < jsonObjs.length(); i++) {
				JSONObject jsonObj = (JSONObject) jsonObjs.opt(i);
				String sn = null;
				String sno = null;
				String log = null;
				String lat = null;
				String si = null;
				String derection = null;
				try {
					sn = jsonObj.getString("sn");
					sno = jsonObj.getString("sno");
					log = jsonObj.getString("log");
					lat = jsonObj.getString("lat");
					si = jsonObj.getString("si");
					derection = String.valueOf(updwon);
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
				station.put("derection", "联系电话:"
						+ (derection == null ? "暂无" : derection));
				data.put(sno + "#" + derection, station);

				if (updwon == 1) {
					upLineList.add(station);
				} else {
					downLineList.add(station);
				}
			}
			ComparatorStationMap cmap = new ComparatorStationMap();
			if (null != upLineList) {
				Collections.sort(upLineList, cmap);
			}
			if (null != downLineList) {
				Collections.sort(downLineList, cmap);
			}
			if (updwon == 1) {
				upLine = begin + " → " + end;
				
			} else {
				downLine = begin + " → " + end;
			}

		}

		public class ComparatorStationMap implements Comparator {

			public int compare(Object arg0, Object arg1) {
				Map<String, Object> data0 = (Map<String, Object>) arg0;
				Map<String, Object> data1 = (Map<String, Object>) arg1;

				// 首先比较年龄，如果年龄相同，则比较名字
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
				selectLine(currentSelectedLine, lineNumbber, lineName);
				this.finish();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}

}
