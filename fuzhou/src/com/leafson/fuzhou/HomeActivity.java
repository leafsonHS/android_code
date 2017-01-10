package com.leafson.fuzhou;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobads.InterstitialAd;
import com.leafson.fuzhou.bean.Setting;
import com.leafson.fuzhou.bean.User;
import com.leafson.fuzhou.db.DAO;
import com.leafson.fuzhou.db.DAOImpl;
import com.leafson.fuzhou.utils.SpecialAdapter;
import com.leafson.fuzhou.utils.Utils;

public class HomeActivity extends Activity {
	public static TravelInfoLoader loadThread = null;
    private ProgressDialog progressDialog = null;
	private TextToSpeech mSpeech;
	public static int isUpline = 1;
	private ListView list;
	private boolean isArrived = false;
	private ImageView refreashIcon;
	private TextView waitingStationText = null;
	private TextView derectionText = null;
	private TextView noDataText = null;
	private LinearLayout derectionChangeLinearLayout;
	private LinearLayout waitingStationLinearLayout;
	private LinearLayout lineSelectorLinearLayout;
	private TextView lineSelectorText = null;
	private ImageView back;
	private Handler handler = null;
	public static List<Map<String, Object>> datas;
	public static Map<String, Map<String, Object>> staticStations;
	public static List<Map<String, Object>> upLineList = new ArrayList<Map<String, Object>>();
	public static List<Map<String, Object>> downLineList = new ArrayList<Map<String, Object>>();
	public static String upLine = "��ѡ����·";
	public static String downLine = null;
	private int currentStation = 0;
	public static String currentStationName = "��ѡ��";
	public static String currentSelectedLine="��ѡ����·";
	public static String lineNumbber = "";
	private DAO settingDao = null;
	
	private DAO LineObjDao = null;
	public static  Setting setting = new Setting();
	public static int intervel=25;
	protected static String lineName="";
	public InterstitialAd interAd;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		// �����������̵߳�handler
		handler = new Handler();
	
		settingDao = new DAOImpl<User>(getApplicationContext(),
				Setting.class);
		List<Setting> listSetting = settingDao.findAll();
		if (!listSetting.isEmpty()) {
			setting = listSetting.get(0);
			if(null!=setting.getRefreashInterval())
			{
				if(SettingActivity.REFREASHFAST.equals(setting.getRefreashInterval()))
				{
					intervel=15;	
				}else
				{
					intervel=25;
				}
			}
		}
		list = (ListView) findViewById(R.id.ListView01);
		 
	
		back = (ImageView) findViewById(R.id.titlebar_back_id);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog();
			}
		});

		refreashIcon = (ImageView) findViewById(R.id.icon_refreash);
		refreashIcon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toSetting();
			}
		});
		// �ȵ�վ������
		waitingStationLinearLayout = (LinearLayout) this
				.findViewById(R.id.waitingStation);
		waitingStationLinearLayout
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						selectStation();
					}
				});
		lineSelectorLinearLayout = (LinearLayout) this
				.findViewById(R.id.lineSelector);
		lineSelectorLinearLayout
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						selectLine();
					}
				});
		
		// �������ý���
		derectionChangeLinearLayout = (LinearLayout) this
				.findViewById(R.id.derectionChange);
		derectionChangeLinearLayout
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						datas = new ArrayList<Map<String, Object>>();
						handler.post(runnableTravelData);
						if (isUpline == 1) {
							isUpline = 2;
							selectLineForSwotch();
						} else {
							isUpline = 1;
							selectLineForSwotch();
						}
						loadingTravelInfo();
					}
				});

		waitingStationText = (TextView) this
				.findViewById(R.id.waitingStationText);
		waitingStationText.setText(currentStationName);

		derectionText = (TextView) this.findViewById(R.id.derectionText);
		derectionText.setText(upLine);
		
		lineSelectorText = (TextView) this.findViewById(R.id.lineSelectorText);
		lineSelectorText.setText(currentSelectedLine);
		
		noDataText = (TextView) this.findViewById(R.id.noDataText);
		read();
		
		 if (isNetworkAvailable(HomeActivity.this))
	        {
	        //    Toast.makeText(getApplicationContext(), "��ǰ�п������磡", Toast.LENGTH_LONG).show();
	        }
	        else
	        {
	            Toast.makeText(getApplicationContext(), "��ǰû�п������磡", Toast.LENGTH_LONG).show();
	            return;
	        }
		 
			if("��ѡ����·".equals(currentSelectedLine))
			{
				selectLine();
			}
		loadingTravelInfo();
	
	}
	 public boolean isNetworkAvailable(Activity activity)
	    {
	        Context context = activity.getApplicationContext();
	        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        
	        if (connectivityManager == null)
	        {
	            return false;
	        }
	        else
	        {
	            // ��ȡNetworkInfo����
	            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
	            
	            if (networkInfo != null && networkInfo.length > 0)
	            {
	                for (int i = 0; i < networkInfo.length; i++)
	                {
	                    System.out.println(i + "===״̬===" + networkInfo[i].getState());
	                    System.out.println(i + "===����===" + networkInfo[i].getTypeName());
	                    // �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
	                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
	                    {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }
	
	private void selectStation() {
		try {
			Intent intent = new Intent();
			intent.putExtra("sno", currentStation);
			intent.putExtra("sn", currentStationName);
			intent.putExtra("updown", isUpline);
			intent.setClass(HomeActivity.this, SelectStationActivity.class);
			startActivityForResult(intent, 100001);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("selectStation", e.getMessage());
		}

	}
	private void selectLine() {
		try {	
			Intent intent = new Intent();
			intent.putExtra("currentSelectedLine", currentSelectedLine);
			intent.putExtra("lineNumbber", lineNumbber);
			intent.setClass(HomeActivity.this, AllLineActivity.class);
			startActivityForResult(intent, 100002);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("selectLine", e.getMessage());
		}

	}
	private void selectLineForSwotch() {
		try {	
			Intent intent = new Intent();
			intent.putExtra("currentSelectedLine", currentSelectedLine);
			intent.putExtra("lineNumbber", lineNumbber);
			intent.putExtra("isUpline",String.valueOf( isUpline));
			intent.setClass(HomeActivity.this, AllLineActivity.class);
			startActivityForResult(intent, 100002);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("selectLine", e.getMessage());
		}

	}
	private void toSetting() {
		try {	
			Intent intent = new Intent();
			//intent.putExtra("currentSelectedLine", currentSelectedLine);
			//intent.putExtra("lineNumbber", lineNumbber);
			intent.setClass(HomeActivity.this, SettingActivity.class);
			startActivityForResult(intent, 100003);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("selectLine", e.getMessage());
		}

	}

	private void changemageResource(ImageView savedInstanceState, int resourceId) {
		refreashIcon.setImageResource(resourceId);
	}

	// ��������
	private void loadingTravelInfo() {
		if (loadThread != null) {
			loadThread.x = 1;
		}
		if("".equals(lineNumbber)){return;}
		handler.post(runnableUi);
		loadThread = new TravelInfoLoader();
		loadThread.start();

	}

	/**
	 * �����ļ��߳�
	 */
	private class TravelInfoLoader extends Thread {
		public int x = 0;
		java.util.Timer timer;
		@Override
		public void run() {
			try {
				timer = new java.util.Timer(true);
				timer.schedule(new java.util.TimerTask() {
					public void run() {
						if (x != 0) {
							timer.cancel();
						} else {
							try {
								HttpClient httpclient = new DefaultHttpClient();
								httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
								HttpPost httpgets = new HttpPost(
										"http://0591.mygolbs.com/fzwap_html5/getBusPosition");		
								 List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
								 nvps.add(new BasicNameValuePair("buslineId", currentSelectedLine)); 
								 nvps.add(new BasicNameValuePair("city","����"));  
								 nvps.add(new BasicNameValuePair("upperOrDown", String.valueOf(isUpline)));  
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
								datas = busLineToList(Utils
										.convertStreamToStringNoz(inStream));
								
								handler.post(runnableTravelData);
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.e("selectStation", e.getMessage());
							}
							if((Math.random() * 8)<1){
								interAd.showAd(HomeActivity.this);
							}
						}
					}
				}, 0, intervel * 1000);

				//while (x == 0) {

				//}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
			}

		}

	};

	// ����Runnable������runnable�и��½���
	Runnable runnableTravelData = new Runnable() {
		@Override
		public void run() {
			try{
			// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
			SpecialAdapter listItemAdapter = new SpecialAdapter(
					HomeActivity.this, datas,// ����Դ
					R.layout.list_items,// ListItem��XMLʵ��
					// ��̬������ImageItem��Ӧ������

					new String[] { "carZbh", "speed", "desc", 
							"STime", "remainStation" },
					// ImageItem��XML�ļ������һ��ImageView,����TextView ID
					new int[] { R.id.Item1, R.id.Item2, R.id.Item3, 
							R.id.Item5, R.id.Item6 });
			// ��Ӳ�����ʾ
			list.setAdapter(listItemAdapter);
			progressDialog.dismiss();
			if(datas.isEmpty())
			{
				noDataText.setText("���ڸ���·����û���������еİ೵");
			}else
			{
				noDataText.setText("");
			}	
		}catch(Exception e){
			e.printStackTrace();}
		}

		
	};


	/***
	 * json�ַ���תjava List
	 * 
	 * @param rsContent
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> busLineToList(String rsContent)
			throws Exception {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {
			JSONArray jsonObjs = new JSONArray(rsContent);
			MediaPlayer mediaPlayer01;
			mediaPlayer01 = MediaPlayer.create(this, R.raw.fuzhou);
			// ��ȡ�����վ��
			// String location = ((Location) getApplication()).mTv;
			// Map<String, Object> nearStation = getNearStation(location);
			isArrived = false;
			int iner=1;
			for (int i = 0; i < jsonObjs.length(); i++) {
				JSONObject jsonObj = (JSONObject) jsonObjs.opt(i);
				String STime = null;
				String speed = null;
				String no = null;
				String log = null;
				String lat = null;
				String inOrOut = null;
				String groupId = null;
				String carNo = null;
				String inDown = String.valueOf(isUpline);
				String carZbh = null;
				String devIdStr = null;
				String lineId = null;
				String desc = null;
				String derection = null;
				String remainStation = null;
			
				int sortNo = 0;
				try {
					JSONObject stationObj=jsonObj.getJSONObject("station");
					
					String stionsname=stationObj.getString("name");
					
					for (Map<String, Object> station : upLineList) {
						if (station.get("sn")
								.equals(stionsname)) {
							no = (String) station
											.get("sno");
							break;
						}
					}
					STime = Utils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
					speed = "...";
					desc = "�޷���ȡ";

					int busStationNo = Integer.parseInt(no);
				
					//���� ����0 ��ʼ
					int minusNo=busStationNo - currentStation;
					//���ǵ�ǰѡ�е���ʻ����Ĺ��˵�
					
				
					//û��վ��յ�վ����ʾ ʣ��ս���͵�վʣ��ʱ��
					if (minusNo < 0) {
						remainStation = "����" + (currentStation - busStationNo)
								+ "վ   ��Լ" + 2.5
								* (currentStation - busStationNo) + "����";
						sortNo = currentStation - busStationNo;
					}
					if (minusNo > 0) {
						remainStation = "�ѹ�վ";
						sortNo = currentStation + busStationNo;
						}
					
					if(busStationNo==0&&currentStation==0&&"1".equals(inOrOut))
					{
						remainStation = "�ȴ����� ";
						sortNo = 1;
					}
					//�г�����һվ�͵��ˡ�
					if ((busStationNo - currentStation >= -2&&busStationNo - currentStation <0&&!"1".equals(inOrOut))) {
						isArrived = true;
					}
					if (no != null) {
						// ��ȡ��վ
						
						
						@SuppressWarnings("unchecked")
								Map<String, String> station = (Map) (staticStations
								.get(String.valueOf(busStationNo)));
						if (null!=station){
								desc = "�ִ�[" + station.get("sn") + "]վ����";
						}else
						{
							desc = "������";	
							if(currentStation==0)
							{
							
								sortNo =  -1000;
							}else
							{
								sortNo = 100000;
								
							}
						
						}
						
						if (busStationNo!=0&&currentStation!=0&&minusNo == 0) {
								remainStation = "�ִ�[" + station.get("sn") + "]վ����";
							
							sortNo = 0;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					Log.e("selectStation", e.getMessage());
				}
				Map<String, Object> bus = new HashMap<String, Object>();
				bus.put("STime", "����ʱ�� " +STime);
				bus.put("speed", "����" +speed);
				bus.put("no", no);
				bus.put("lat", lat);
				bus.put("log",  log);
				bus.put("inOrOut",inOrOut);
				bus.put("groupId",  groupId);
				bus.put("carNo",  "����"+iner);
				bus.put("inDown",  inDown);
				bus.put("lineId",  lineId);
				bus.put("devIdStr",  devIdStr);
				bus.put("carZbh",  "������");
				bus.put("desc", desc);
				bus.put("derection", derection);
				bus.put("remainStation", remainStation == null ? "����"
						: remainStation);
				bus.put("sortNo",sortNo);
				data.add(bus);
				iner++;
			}
			if (isArrived) {
				if(null!=setting.getNoticeType()&&SettingActivity.NOTICE2MIN.equals(setting.getNoticeType())){
					if(	!mediaPlayer01.isPlaying()){
						mediaPlayer01.start();
				}}
				
			}
		
		ComparatorMap cmap = new ComparatorMap();
		Collections.sort(data, cmap);
		} catch (Exception e) {
			e.printStackTrace();Log.e("selectStation", e.getMessage());
		} 
		return data;
	}

	public class ComparatorMap implements Comparator<Object> {

		public int compare(Object arg0, Object arg1) {
			@SuppressWarnings("unchecked")
			Map<String, Object> data0 = (Map<String, Object>) arg0;
			@SuppressWarnings("unchecked")
			Map<String, Object> data1 = (Map<String, Object>) arg1;
			try{
			// ���ȱȽ����䣬���������ͬ����Ƚ�����
			int data0SortNo = (Integer) data0.get("sortNo");
			int data1SortNo = (Integer) data1.get("sortNo");
			if (data0SortNo > data1SortNo) {
				return 1;
			} else if (data0SortNo == data1SortNo) {
				return 0;
			} else {
				return -1;
			}
			}catch(Exception e)
			{
				return 1;
			}
		}
	}

	// �ַ���ת���ɳ�վ��Ϣ�б�
	private Map<String, Map<String, Object>> stationStringToList(
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
	 * ������վ���ݵ��б��С�
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
			station.put("sn", sn == null ? "����" : sn);
			station.put("sno", sno == null ? "����" : sno);
			station.put("log", log == null ? "����" : log);
			station.put("lat", lat == null ? "����" : lat);
			station.put("si", si == null ? "����" : si);
			station.put("derection", "��ϵ�绰:"
					+ (derection == null ? "����" : derection));
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
			upLine = begin + " �� " + end;
		} else {
			downLine = begin + " �� " + end;
		}

	}

	public class ComparatorStationMap implements Comparator {

		public int compare(Object arg0, Object arg1) {
			Map<String, Object> data0 = (Map<String, Object>) arg0;
			Map<String, Object> data1 = (Map<String, Object>) arg1;

			// ���ȱȽ����䣬���������ͬ����Ƚ�����
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

	// ����Runnable������runnable�и��½���
	Runnable runnableUi = new Runnable() {
		@Override
		public void run() {
			progressDialog = ProgressDialog.show(HomeActivity.this, "", "��ȡ������...", true);
		}

	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100001) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle ext = data.getExtras();
				int sno = ext.getInt("sno");
				String sn = ext.getString("sn");
				if (null != sn) {
					currentStation = sno;
					currentStationName = sn;
					waitingStationText.setText(sn);
					datas = new ArrayList<Map<String, Object>>();
					handler.post(runnableTravelData);
					loadingTravelInfo();
				}
			}

		}else if(requestCode == 100002) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle ext = data.getExtras();
				String lineNumbber = ext.getString("lineNumbber");
				String currentSelectedLine = ext.getString("currentSelectedLine");
				if(!"-xx".equals(lineNumbber))
				{
					this.lineNumbber=lineNumbber;
					this.currentSelectedLine=currentSelectedLine;
					currentStation = upLineList.size();
					
					Map<String, Object> station=upLineList.get(upLineList.size()-1);
					currentStationName=(String)station.get("sn");
					waitingStationText.setText(currentStationName);
					derectionText.setText(upLine);
					lineSelectorText.setText(currentSelectedLine);
					datas = new ArrayList<Map<String, Object>>();
					handler.post(runnableTravelData);
					loadingTravelInfo();
				}
			}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return true;
		}
		return true;
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("ȷ��Ҫ�˳����߹�����?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	//�����ʶ�
	
	public void read()
	{
		mSpeech=new TextToSpeech(this,new OnInitListener()
		{
			
			public void onInit(int status)
			{
				//TODOAuto-generatedmethodstub
				if(status==TextToSpeech.SUCCESS)
				{
					int result=mSpeech.setLanguage(Locale.ENGLISH);
					if(result==TextToSpeech.LANG_MISSING_DATA ||result==TextToSpeech.LANG_NOT_SUPPORTED)
					{
						Log.e("lanageTag","notuse");
					}
					else
					{
						//mSpeech.speak("i love you",TextToSpeech.QUEUE_FLUSH,null);
					}
				}
			}
		});
	
	}
	
	
	
}
