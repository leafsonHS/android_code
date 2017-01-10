package com.leafson.lifecycle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class SelectStationActivity extends Activity {
	private ListView list;
	private ImageView back;
	
	private String stn;
	private int stno;
	private int updown;

	public  AdView adView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_selectstation);
			list = (ListView) findViewById(R.id.ListView01);
			
			back = (ImageView) findViewById(R.id.titlebarss_back_id);
			back.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					selectStation(stn,stno);
					
				}
			});
			
			
			
			Bundle extras = getIntent().getExtras();
			if(extras!=null) {
				stn= extras.getString("sn");
				stno  = extras.getInt("sno");
				updown  = extras.getInt("updown");
			}
			List<Map<String, Object>> datas = updown==1?HomeActivity.upLineList:HomeActivity.downLineList;
			SimpleAdapter listItemAdapter = new SimpleAdapter(
					SelectStationActivity.this, datas,// ���Դ
					R.layout.list_station_items,// ListItem��XMLʵ��
					// ��̬������ImageItem��Ӧ������

					new String[] { "sn", "sno" },
					// ImageItem��XML�ļ������һ��ImageView,����TextView ID
					new int[] { R.id.Itemname, R.id.Itemno });
			// ��Ӳ�����ʾ
			list.setAdapter(listItemAdapter);

			list.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ListView listView = (ListView) parent;
					HashMap<String, String> map = (HashMap<String, String>) listView
							.getItemAtPosition(position);
					String sno = map.get("sno");
					String sn = map.get("sn");
					selectStation(sn, Integer.parseInt(sno));
				}

				
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(!MainActivity.isClicked){
		
		RelativeLayout your_original_layout	 = (RelativeLayout) this
				.findViewById(R.id.SelectStationActivity);;
		//人群属性
		AdSettings.setKey(new String[]{"学生","上班","南京","公交","交通"}
		);
		// 创建广告 view
		String adPlaceId =  "2435584" ;
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
	
	private void selectStation(String sn, int sno) {
		try {
//			Intent intent = new Intent();
//			if(sno!=null){
//				intent.putExtra("sno", sno);
//				intent.putExtra("sn", sn);
//			}
//			intent.setClass(SelectStationActivity.this,
//					HomeActivity.class);
//			startActivity(intent);
			
			Intent intent = new Intent();
			intent.putExtra("sno", sno);
			intent.putExtra("sn", sn);
			this.setResult(Activity.RESULT_OK, intent);
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			selectStation(stn,stno);
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
