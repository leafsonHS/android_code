package com.leafson.lifecycle;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.baidu.mobstat.StatService;
import com.leafson.lifecycle.bean.LineObj;
import com.leafson.lifecycle.bean.Setting;
import com.leafson.lifecycle.bean.User;
import com.leafson.lifecycle.db.DAO;
import com.leafson.lifecycle.db.DAOImpl;
import com.leafson.lifecycle.nanjing.util.CommonUtils;

public class MainActivity extends BaseActivity implements OnClickListener {

	private boolean f = true;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "";
	public static boolean isClicked = false;
	public static final String KEY_EXTRAS = "extras";

	public upgreadInitLoader upgreadInitLoader = null;
	public static  Setting setting = null;
	
	public static int threadover =0;

	
	public static boolean isForeground = false;
	public static  DAO settingDao = null;
	private DAO detailDao = null;
	private Handler handler = null;
	
	public static  String logtext = "";
	private int isFist = 0;
	private static TextView loadText = null;
	/**start南京代码**/
	  private int current_req = 1;
	  boolean isFirst = false;
	
	/**end南京代码**/
	  public static DAO LineObjDao = null;

	
	protected void onPause() {
		super.onPause();
		StatService.onPause(MainActivity.this);
	};

	protected void onResume() {
		super.onResume();
		StatService.onResume(MainActivity.this);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		handler = new Handler();
		setLoadingText("0%");
		loadText = (TextView) this.findViewById(R.id.loadingtext);
		loadText.setText("loading...");
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adView111111);
		CommonUtils.copyDB(getApplicationContext());
		//isClicked=true;
		 setLoadingText("20%");
		init();
				SplashAdListener listener= new SplashAdListener() {
				@Override
				public void  onAdDismissed() {
					setLoadingText("RSplash Activity onAdDismissed");
				loadingTravelInfo();
				}
				@Override
				public
				void
				onAdFailed(String arg0) {
					loadingTravelInfo();
				}
				@Override
				public
				void
				onAdPresent() {
					setLoadingText("RSplashActivi tyonAdPresent");
				}
				@Override
				public
				void
				onAdClick() {
					setLoadingText("RSplashActivity onAdClick");
				MainActivity.isClicked=true;
				}
				};
				/**
				* 
				构造函数：
				* new SplashAd(Context context, ViewGroup adsParent,
				* 
				SplashAdListener listener,
				String adPlaceId, boolean 
				canClick);
				*/
				String adPlaceId = "2435586";
				
			SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			this.isFirst = localSharedPreferences.getBoolean("isFirst", true);
		
		
		
	
		if (Math.random()*10>7) {
			new SplashAd( this , adsParent, listener, adPlaceId, true );
		} else {
			loadingTravelInfo();
		}
	}

	
	
	private void init() {
	    setLoadingText("40%");
		settingDao = new DAOImpl<User>(getApplicationContext(), Setting.class);

		boolean isTableExist = settingDao.tabbleIsExist("TABLE_SETTING");
		
		if (!isTableExist) {
			 setLoadingText("45");
			settingDao.createTable("CREATE TABLE TABLE_SETTING(" + //
					"setting_id  integer primary key autoincrement, " + //
					"setting_noticeType varchar(50), " + //
					"setting_defaultBusLine varchar(50), " + //
					"setting_refreashInterval VARCHAR(50))");
		}
		
//		boolean isTableExist_ibus_line = settingDao.tabbleIsExist("ibus_line");
//		if (!isTableExist_ibus_line) {
//			 setLoadingText("新建设置表ibus_line");
//			settingDao.createTable("CREATE TABLE ibus_line(" + //
//				
//					"line_id varchar(50), " + //
//					"weiba_id varchar(50), " + //
//					"line_code varchar(50), " + //
//					"line_interval varchar(50), " + //
//					"line_name varchar(50), " + //
//					"line_status varchar(50), " + //
//					"update_time varchar(50), " + //
//					"ltd VARCHAR(50))");
//		}
//		
//		boolean isTableExist_ibus_line_updown= settingDao.tabbleIsExist("ibus_line_updown");
//		if (!isTableExist_ibus_line_updown) {
//			settingDao.createTable("CREATE TABLE ibus_line_updown(" + //
//					"updown_id varchar(50), " + //
//					"line_id varchar(50), " + //
//					"line_code varchar(50), " + //
//					"line_interval varchar(50), " + //
//					"updown_type varchar(50), " + //
//					"st_start_id varchar(50), " + //
//					"st_end_id varchar(50), " + //
//					"bus_start varchar(50), " + //
//					"bus_end varchar(50), " + //
//					"updown_status varchar(50), " + //
//					"update_time VARCHAR(50))");
//		}
//		
//		boolean isTableExist_ibus_station= settingDao.tabbleIsExist("ibus_station");
//		if (!isTableExist_ibus_station) {
//			settingDao.createTable("CREATE TABLE ibus_station(" + //
//					"st_id varchar(50), " + //
//					"weiba_id varchar(50), " + //
//					"st_name varchar(50), " + //
//					"st_real_lat varchar(50), " + //
//					"st_real_lon varchar(50), " + //
//					"st_status varchar(50), " + //
//					"update_time varchar(50), " + //
//					"is_true varchar(50), " + //
//					"st_side VARCHAR(50))");
//		}
//		boolean isTableExist_ibus_line_stations= settingDao.tabbleIsExist("ibus_line_stations");
//		if (!isTableExist_ibus_line_stations) {
//			settingDao.createTable("CREATE TABLE ibus_line_stations(" + //
//					"line_station_id varchar(50), " + //
//					"line_id varchar(50), " + //
//					"line_code varchar(50), " + //
//					"line_interval varchar(50), " + //
//					"updown_type varchar(50), " + //
//					"st_id varchar(50), " + //
//					"st_name varchar(50), " + //
//					"st_stop_level varchar(50), " + //
//					"line_st_status varchar(50), " + //
//					"is_true varchar(50), " + //
//					"update_time VARCHAR(50))");
//		}
//		
		List<Setting> list = settingDao.findAll(null);
		
		if (!list.isEmpty()) {
			setting = list.get(0);
		}
		if (setting == null) {
			setting = new Setting();
			setting.setNoticeType("0");
			setting.setRefreashInterval("20");
			setting.setDefaultBusLine("0");
		} else if (setting != null) {
			int lineNumber = 1;
			String line = setting.getDefaultBusLine();
			if (null != line && !"".equals(line)) {
				lineNumber = Integer.parseInt(line) + 1;
			}
			setting.setDefaultBusLine(String.valueOf(lineNumber));
		}
		if (setting.getId() != null) {
			settingDao.update(setting);
		} else {
			settingDao.insert(setting);
		}

		LineObjDao = new DAOImpl<User>(getApplicationContext(), LineObj.class);

//		isTableExist = LineObjDao.tabbleIsExist("TABLE_LINEOBJ");
//		if (!isTableExist) {
//			 setLoadingText("新建表TABLE_LINEOBJ");
//			LineObjDao.createTable("CREATE TABLE TABLE_LINEOBJ(" + //
//					"lineobj_linename   varchar(50), " + //
//					"lineobj_busLineId varchar(50), " + //
//					"lineobj_upLine varchar(100), " + //
//					"lineobj_downLine varchar(100), " + //
//					"lineobj_upTime varchar(50), " + //
//					"lineobj_downTime varchar(50), " + //
//					"lineobj_linenamePinYin varchar(50), " + //
//					"source VARCHAR(20))");
//		}
//		
		
	}

	private void ToMainPage(User user, boolean logined) {
		Intent intent = new Intent();
		try {
		    setLoadingText("60%");
			 setLoadingText("即将进入");
			intent.setClass(MainActivity.this, HomeActivity.class);
			startActivity(intent);
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// �������
	private void loadingTravelInfo() {
		upgreadInitLoader = new upgreadInitLoader();
		upgreadInitLoader.start();
	
	}
	private class upgreadInitLoader extends Thread {
		@Override
		public void run() {
			try {
				Looper.prepare();
				UpdateManager manager = new UpdateManager(MainActivity.this);
				manager.checkUpdate();
				setLoadingText("80%");
				ToMainPage(null, true);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
				dialog();
			}

		}
	};
	public  void setLoadingText(String text)
	{
		logtext="初始化："+text;
		handler.post(runnableTravelData);
	}
	
	
	
	// 构建Runnable对象，在runnable中更新界面
	Runnable runnableTravelData = new Runnable() {
		@Override
		public void run() {
			try{
	
				loadText.setText(logtext);
		}catch(Exception e){
			e.printStackTrace();}
		}

	};

	
	
	
	
	
	
	
	
	
	

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("��������ʧ�ܣ������ƶ������Ƿ��.");
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

		builder.create().show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


}
