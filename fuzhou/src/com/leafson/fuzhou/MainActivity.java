package com.leafson.fuzhou;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;

import com.ant.liao.GifView;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.baidu.mobstat.StatService;
import com.leafson.fuzhou.bean.LineObj;
import com.leafson.fuzhou.bean.LineObjectDetail;
import com.leafson.fuzhou.bean.Setting;
import com.leafson.fuzhou.bean.User;
import com.leafson.fuzhou.db.DAO;
import com.leafson.fuzhou.db.DAOImpl;

public class MainActivity extends Activity implements OnClickListener {

	private GifView gf1;
	private boolean f = true;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public TravelInfoLoader loadThread = null;
	public static boolean isForeground = false;
	private DAO settingDao = null;
	private DAO detailDao = null;
 private int isFist=0;
	
	private DAO LineObjDao = null;
	protected void onPause() {
		super.onPause();
		StatService.onPause(MainActivity.this);
	
	        JPushInterface.onPause(MainActivity.this);
	        
	};

	protected void onResume() {
		super.onResume();
		StatService.onResume(MainActivity.this);
	     JPushInterface.onResume(MainActivity.this);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
      
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		gf1 = (GifView) findViewById(R.id.loadinggif);
		gf1.setGifImage(R.drawable.loading_green);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adView111111);
		init();
		
		if(isFist<15){
			loadingTravelInfo();
			return;
		}
		SplashAdListener listener = new SplashAdListener() {
			@Override
			public void onAdDismissed() {
				Log.i("RSplashActivity", "onAdDismissed");
				loadingTravelInfo();
			}
			@Override
			public void onAdFailed(String arg0) {
				Log.i("RSplashActivity", "onAdDismissed");
				loadingTravelInfo();

			}
			@Override
			public void onAdPresent() {
				Log.i("RSplashActivity", "onAdPresent");
			}
			@Override
			public void onAdClick() {
				Log.i("RSplashActivity", "onAdDismissed");
			}

		};
		String adPlaceId = "2773931";

		if (Math.random()*10>7) {
			new SplashAd( this , adsParent, listener, adPlaceId, true );
		} else {
			loadingTravelInfo();
		}
	}
	
	private void init() {


//		detailDao = new DAOImpl<User>(getApplicationContext(),
//				LineObjectDetail.class);
//	
//		boolean isTableExist1=detailDao.tabbleIsExist("TABLE_LINEOBJECTDETAIL");
//		if(!isTableExist1)
//		{
//			detailDao.createTable(
//					"CREATE TABLE TABLE_LINEOBJECTDETAIL(" + //
//							"line_number   varchar(50), " + //
//							"ObjText text)"
//					);
//		}
		
		settingDao = new DAOImpl<User>(getApplicationContext(),
				Setting.class);
	
		boolean isTableExist=settingDao.tabbleIsExist("TABLE_SETTING");
		if(!isTableExist)
		{
			settingDao.createTable(
					"CREATE TABLE TABLE_SETTING(" + //
							"setting_id  integer primary key autoincrement, " + //
							"setting_noticeType varchar(50), " + //
							"setting_defaultBusLine varchar(50), " + //
							"setting_refreashInterval VARCHAR(50))"
					);
		}
	
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
			setting.setDefaultBusLine("1");
		}else if(setting!=null)
		{
			int lineNumber =1;
			String line=setting.getDefaultBusLine();
			if(null!=line&&!"".equals(line)){
				 lineNumber =Integer.parseInt(line)+1;
				 isFist=lineNumber;
			}
			setting.setDefaultBusLine(String.valueOf(lineNumber));
		}
		if (setting.getId()!=null) {
			settingDao.update(setting);
		}else
		{
			settingDao.insert(setting);
		}

		LineObjDao = new DAOImpl<User>(getApplicationContext(),
				LineObj.class);
	
		 isTableExist=LineObjDao.tabbleIsExist("TABLE_LINEOBJ");
		if(!isTableExist)
		{
			
			LineObjDao.createTable(
					"CREATE TABLE TABLE_LINEOBJ(" + //
							"lineobj_linename   varchar(50), " + //
							"lineobj_busLineId varchar(50), " + //
							"lineobj_upLine varchar(100), " + //
							"lineobj_downLine varchar(100), " + //
							"lineobj_upTime varchar(50), " + //
							"lineobj_downTime varchar(50), " + //
							"lineobj_linenamePinYin VARCHAR(50))"
					);
		}
	}
	
	private void ToMainPage(User user, boolean logined) {
		Intent intent = new Intent();
		try {

			intent.setClass(MainActivity.this, HomeActivity.class);
			startActivity(intent);
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onClick(View v) {
		if (f) {
			gf1.showCover();
			f = false;
		} else {
			gf1.showAnimation();
			f = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 加载数据
	private void loadingTravelInfo() {
		loadThread = new TravelInfoLoader();
		loadThread.start();

	}

	/**
	 * 下载文件线程
	 */
	private class TravelInfoLoader extends Thread {
		@Override
		public void run() {
			try {
				//
				Looper.prepare();
				UpdateManager manager = new UpdateManager(MainActivity.this);
				manager.checkUpdate();
				ToMainPage(null, true);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("selectStation", e.getMessage());
				dialog();
			}

		}
	};

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("网络连接失败，请检查移动网络是否打开.");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
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
}
