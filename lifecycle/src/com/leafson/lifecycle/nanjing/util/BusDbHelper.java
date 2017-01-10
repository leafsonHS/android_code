package com.leafson.lifecycle.nanjing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.leafson.lifecycle.AllLineActivity;
import com.leafson.lifecycle.nanjing.bean.BusBaseBean;
import com.leafson.lifecycle.nanjing.bean.BusLinesBean;
import com.leafson.lifecycle.nanjing.bean.BusStationBean;
import com.leafson.lifecycle.nanjing.bean.LineBean;
import com.leafson.lifecycle.nanjing.bean.UpdownBean;

public class BusDbHelper extends SQLiteOpenHelper {
	private static final String LINEQUERY_HISTORY_TABLE = "linequery_history";
	private static final String STATIONQUERY_HISTORY_TABLE = "stationquery_history";
	private SQLiteDatabase sqlDB = getWritableDatabase();

	public BusDbHelper(Context paramContext) {
		this(paramContext, "ibus_201609018.db", null, 1);
	}

	public BusDbHelper(Context paramContext, String paramString,
			SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
		super(paramContext, "ibus_201609018.db", null, 1);
	}

	private static String a(Cursor paramCursor, String paramString) {
		return paramCursor.getString(paramCursor
				.getColumnIndexOrThrow(paramString));
	}

	private final ArrayList<LineBean> g(String paramString) {
		Cursor localCursor  = this.sqlDB
				.rawQuery(
						"select ibus_line_updown.updown_id,ibus_line_updown.line_id,ibus_line.weiba_id,ibus_line_updown.line_code,ibus_line.line_name,ibus_line.ltd,updown_type,trim(a.st_name) as start_station,trim(b.st_name) as end_station from ibus_line_updown,ibus_line,ibus_station a,ibus_station b where a.st_id = ibus_line_updown.st_start_id and b.st_id=ibus_line_updown.st_end_id and ibus_line.line_id=ibus_line_updown.line_id and ibus_line.line_status=0 and "
								+ paramString, null);
		ArrayList localArrayList = null;
		if (localCursor != null)
		localArrayList = new ArrayList();
			try {
				if (!localCursor.isClosed()) {
					return localArrayList;
				}
				while (localCursor.moveToNext()){
					LineBean localLineBean = new LineBean();
					localLineBean.updown_id = a(localCursor, "updown_id");
					localLineBean.weiba_id = a(localCursor, "weiba_id");
					localLineBean.line_id = a(localCursor, "line_id");
					localLineBean.line_code = a(localCursor, "line_code");
					localLineBean.line_name = a(localCursor, "line_name");
					localLineBean.updown_type = a(localCursor, "updown_type");
					localLineBean.start_station = a(localCursor, "start_station");
					localLineBean.end_station = a(localCursor, "end_station");
					localLineBean.ltd = a(localCursor, "ltd");
					localArrayList.add(localLineBean);
			}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		return localArrayList;
	}

	private ArrayList<BusStationBean> getLineStations(String paramString) {
		String str1 = "SELECT ibus_line_stations.st_id,ibus_line_stations.st_name,ibus_line_stations.st_stop_level,ibus_line_stations.line_code,ibus_station.st_real_lat,ibus_station.st_real_lon, ibus_station.weiba_id from ibus_line_stations ,ibus_station where "
				+ paramString;
		Cursor localCursor = this.sqlDB.rawQuery(str1, null);
		ArrayList localArrayList = new ArrayList();
		if (localCursor != null)
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				BusStationBean localBusStationBean = new BusStationBean();
				localBusStationBean.st_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name"));
				localBusStationBean.st_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_id"));
				String str2 = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_stop_level"));
				if (CommonUtils.isNumber(str2))
					localBusStationBean.st_stop_level = Integer.parseInt(str2);
				localBusStationBean.weiba_id = localCursor
						.getString(localCursor
								.getColumnIndexOrThrow("weiba_id"));
				localBusStationBean.st_real_lat = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lat"));
				localBusStationBean.st_real_lon = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lon"));
				localArrayList.add(localBusStationBean);
				}
	} catch (Exception localException) {
		localException.printStackTrace();
	}
		return localArrayList;
	}

//	public final int a(String paramString1, String paramString2, String paramString3)
//  {
//    Cursor localCursor = this.sqlDB.rawQuery("SELECT st_stop_level from ibus_line_stations where line_id='" + paramString2 + "' and updown_type='" + paramString3 + "' and st_name='" + paramString1 + "' and is_true=1", null);
//    while (true)
//    {
//      if ((localCursor == null) || (!localCursor.moveToNext()));
//      try
//      {
//        if (!localCursor.isClosed())
//          localCursor.close();
//        label81: return -1;
//        String str = localCursor.getString(localCursor.getColumnIndexOrThrow("st_stop_level"));
//        if (str != null);
//        try
//        {
//          int i = Integer.parseInt(str);
//          return i;
//        }
//        catch (NumberFormatException localNumberFormatException)
//        {
//          localNumberFormatException.printStackTrace();
//        }
//      }
//      catch (Exception localException)
//      {
//        localException.printStackTrace();
//        break label81:
//      }
//    }
//  }

	public final LineBean a(String paramString1, String paramString2) {
		String str = "select trim(a.st_name) as start_station,trim(b.st_name) as end_station from ibus_line_updown,ibus_station a,ibus_station b where a.is_true=1 and b.is_true=1 and a.st_id = ibus_line_updown.st_start_id and b.st_id=ibus_line_updown.st_end_id and ibus_line_updown.line_id = '"
				+ paramString1 + "' and updown_type='" + paramString2 + "'";
		Cursor localCursor = this.sqlDB.rawQuery(str, null);
		LineBean localLineBean = null;
		if (localCursor != null) {
			localLineBean = new LineBean();
			if (localCursor.moveToNext()) {
				localLineBean.start_station = localCursor.getString(localCursor
						.getColumnIndexOrThrow("start_station"));
				localLineBean.end_station = localCursor.getString(localCursor
						.getColumnIndexOrThrow("end_station"));
			}
		}
		try {
			if (!localCursor.isClosed())
				localCursor.close();
			return localLineBean;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localLineBean;
	}

	public final String b(String paramString1, String paramString2,
			String paramString3) {
		Cursor localCursor = this.sqlDB.rawQuery(
				"SELECT st_id from ibus_line_stations where line_id='"
						+ paramString2 + "' and updown_type='" + paramString3
						+ "' and st_name='" + paramString1 + "' and is_true=1",
				null);
		String str = null;
		if (localCursor != null){
			try {
				if (!localCursor.isClosed()){
				return str;
				}
				if (localCursor.moveToNext()){
				str = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_id"));
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return str;
	}

	public final String c(String paramString1, String paramString2,
			String paramString3) {
		String str1 = "select st_id from ibus_line_stations where line_id='"
				+ paramString1 + "' and updown_type='" + paramString2
				+ "' and st_name='" + paramString3 + "'";
		Cursor localCursor = this.sqlDB.rawQuery(str1, null);
		String str2 = null;
		if (localCursor != null){
				try {
					if (!localCursor.isClosed()) {
						return str2;
					}
					if (localCursor.moveToNext()){
				str2 = a(localCursor, "st_id");
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return str2;
	}

	public void clearLineQueryHistory() {
		this.sqlDB.execSQL("delete from linequery_history");
	}

	public void clearStationQueryHistory() {
		this.sqlDB.execSQL("delete from stationquery_history");
	}

	public void close() {
		super.close();
		return;
	}

	public void createQueryHistoryTable() {
		this.sqlDB
				.execSQL("create table if not exists linequery_history(line_name TEXT(100) primary key)");
		this.sqlDB
				.execSQL("create table if not exists stationquery_history(station_name TEXT(100) primary key)");
	}

	public final HashMap<String, ArrayList<LineBean>> e(String paramString) {
		HashMap localHashMap = new HashMap();
		Cursor localCursor = this.sqlDB
				.rawQuery(
						"select line_id,weiba_id,line_name,line_code,ltd from ibus_line where line_id in (select DISTINCT(line_id) from ibus_line_stations where trim(st_name)='"
								+ paramString + "')", null);
		if (localCursor != null){
				try {
					if (!localCursor.isClosed()) {
						return localHashMap;
					}
					while (localCursor.moveToNext()){
				LineBean localLineBean = new LineBean();
				localLineBean.line_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_id"));
				localLineBean.line_code = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_code"));
				localLineBean.ltd = localCursor.getString(localCursor
						.getColumnIndexOrThrow("ltd"));
				localLineBean.weiba_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("weiba_id"));
				String str = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_name"));
				localLineBean.line_name = str;
				ArrayList localArrayList = (ArrayList) localHashMap.get(str);
				if (localArrayList == null) {
					localArrayList = new ArrayList();
					localHashMap.put(str, localArrayList);
				}
				localArrayList.add(localLineBean);
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localHashMap;
	}

	public ArrayList<LineBean> getAllLineNames() {
		Cursor localCursor = this.sqlDB.rawQuery("select * from ibus_line",
				null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
				
				while (localCursor.moveToNext()){
				LineBean localLineBean = new LineBean();
				localLineBean.line_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_name"));
				localLineBean.line_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_id"));
				localLineBean.line_code = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_code"));
				localArrayList.add(localLineBean);
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}
	public 	Map<String, Map<String, Object>>  getStations(String lineId) {
		Map<String, Map<String, Object>> downdata = new HashMap<String, Map<String, Object>>();
		Cursor localCursor = this.sqlDB.rawQuery("select s.st_name,s.st_id,st.updown_type, st.st_stop_level from  ibus_station s left join ibus_line_stations st "+
"on s.st_id=st.st_id where st.line_id="+lineId+" and st_status=0 and line_st_status=0  and s.is_true=1 and st.[is_true]=1 order by st.updown_type desc ,CAST( st.st_stop_level AS int)  asc;",
				null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
				
				while (localCursor.moveToNext()){
				
				String sid=localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_id"));
				String derection=localCursor.getString(localCursor
						.getColumnIndexOrThrow("updown_type"));
				String st_id=localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_id"));
				String st_stop_level=localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_stop_level"));
				
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("sn",  localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name")));
				station.put("sno",  st_stop_level);
				station.put("code",  st_id);
				station.put("derection",  localCursor.getString(localCursor
						.getColumnIndexOrThrow("updown_type")));
				station.put("st_stop_level",  localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_stop_level")));
				station.put("st_name",  localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name")));
				String no= localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_stop_level"));
				if(lineId.startsWith("10095"))
				{
					downdata.put(no + "#" + derection, station);
				}else{
				downdata.put(sid + "#" + derection, station);
				}
				
				if (derection .equals("1")) {
					AllLineActivity.upLineList.add(station);
				} else {
					AllLineActivity.downLineList.add(station);
				}
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return downdata;
	}
//	public final String getAllLineNamesByStId(String paramString)
//  {
//    Cursor localCursor = this.sqlDB.rawQuery("select line_name from ibus_line where line_id in (select DISTINCT(line_id) from ibus_line_stations where st_id='" + paramString + "')", null);
//    StringBuffer localStringBuffer = new StringBuffer();
//    while (true)
//    {
//      if ((localCursor == null) || (!localCursor.moveToNext()));
//      try
//      {
//        if (!localCursor.isClosed())
//          localCursor.close();
//        label66: return localStringBuffer.substring(0, -1 + localStringBuffer.length());
//        localStringBuffer.append(localCursor.getString(localCursor.getColumnIndexOrThrow("line_name"))).append('/');
//      }
//      catch (Exception localException)
//      {
//        localException.printStackTrace();
//        break label66:
//      }
//    }
//  }

	public final BusStationBean getBusStationByStId(String paramString) {
		String str = "select st_name,st_real_lat,st_real_lon ,weiba_id from ibus_station where is_true=1 and st_id = '"
				+ paramString + "'";
		Cursor localCursor = this.sqlDB.rawQuery(str, null);
		BusStationBean localBusStationBean = null;
		if (localCursor != null) {
			boolean bool = localCursor.moveToNext();
			localBusStationBean = null;
			if (bool) {
				localBusStationBean = new BusStationBean();
				localBusStationBean.st_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name"));
				localBusStationBean.weiba_id = localCursor
						.getString(localCursor
								.getColumnIndexOrThrow("weiba_id"));
				localBusStationBean.st_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name"));
				localBusStationBean.st_real_lat = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lat"));
				localBusStationBean.st_real_lon = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lon"));
				localBusStationBean.st_real_lat = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lat"));
				localBusStationBean.st_real_lon = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lon"));
			}
		}
		try {
			if (!localCursor.isClosed())
				localCursor.close();
			return localBusStationBean;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localBusStationBean;
	}

	public final ArrayList<LineBean> getLineBean(String paramString) {
		return g("ibus_line.line_id='" + paramString + "'");
	}

	public final LineBean getLineByLineName(String paramString) {
		String str = "select line_Name,line_id from ibus_line where trim(line_Name) like '%"
				+ paramString.toUpperCase() + "%'";
		Cursor localCursor = this.sqlDB.rawQuery(str, null);
		LineBean localLineBean = null;
		if (localCursor != null) {
			localLineBean = new LineBean();
			if (localCursor.moveToNext()) {
				localLineBean.line_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_id"));
				localLineBean.line_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_name"));
			}
		}
		try {
			if (!localCursor.isClosed())
				localCursor.close();
			return localLineBean;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localLineBean;
	}

	public final String getLineCodeByLineId(BusLinesBean paramBusLinesBean) {
		String str1 = "select line_code,weiba_id from ibus_line where line_id='"
				+ paramBusLinesBean.line_id + "'";
		Cursor localCursor = this.sqlDB.rawQuery(str1, null);
		String str2 = null;
		if (localCursor != null){
			try {
				if (!localCursor.isClosed()) {
					return str2;
				}
				if (localCursor.moveToNext()){
				str2 = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_code"));
				paramBusLinesBean.line_code = str2;
				paramBusLinesBean.weiba_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("weiba_id"));
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return str2;
	}

	public ArrayList<String> getLineHistory() {
		Cursor localCursor = this.sqlDB.rawQuery(
				"select line_name from linequery_history", null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				localArrayList.add(localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_name")));}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	public String getLineIdByLineName(String paramString) {
		Cursor localCursor = this.sqlDB.rawQuery(
				"select line_id from ibus_line  where trim(line_name)='"
						+ paramString + "'", null);
		if (localCursor != null)
			if (localCursor.moveToNext())
				return localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_id"));
		try {
			if (!localCursor.isClosed()){
				localCursor.close();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public final ArrayList<BusStationBean> getLineStationsByLineId(
			String paramString1, String paramString2, int paramInt) {
		StringBuilder localStringBuilder = new StringBuilder(
				"ibus_line_stations.line_id=").append(paramString1);
		if (1 == paramInt)
			localStringBuilder.append(" and ibus_line_stations.is_true="
					+ paramInt);
		localStringBuilder
				.append(" and ibus_line_stations.updown_type="
						+ paramString2
						+ " and ibus_line_stations.st_id=ibus_station.st_id group by ibus_line_stations.st_stop_level");
		return getLineStations(localStringBuilder.toString());
	}

	public final long getLineStationsUpdateTime() {
		long l1 = 0L;
		try {
			Cursor localCursor = this.sqlDB
					.rawQuery(
							"select max(update_time) as update_time from ibus_line_stations",
							null);
			if (localCursor != null)
				if (localCursor.moveToNext()) {
					String x=localCursor.getString(localCursor
							.getColumnIndexOrThrow("update_time"));
					if(null!=x){
						long l2 = Long.parseLong(x);
						l1 = l2;
					}
				}
			try {
				if (!localCursor.isClosed())
					localCursor.close();
				return l1;
			} catch (Exception localException2) {
				localException2.printStackTrace();
				return l1;
			}
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		return l1;
	}

	public final ArrayList<LineBean> getLinesByStationName(String paramString) {
		Cursor localCursor = this.sqlDB
				.rawQuery(
						"select line_name,line_id from ibus_line where line_id in (select DISTINCT(line_id) from ibus_line_stations where trim(st_name)='"
								+ paramString + "')", null);
		ArrayList localArrayList = new ArrayList();
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				LineBean localLineBean = new LineBean();
				String str1 = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_name"));
				String str2 = localCursor.getString(localCursor
						.getColumnIndexOrThrow("line_id"));
				localLineBean.line_name = str1;
				localLineBean.line_id = str2;
				localArrayList.add(localLineBean);}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	
	public final boolean isJiangninginited() {
		boolean ret=false;
		Cursor localCursor = this.sqlDB
				.rawQuery(
						"select count(1) as sums from TABLE_LINEOBJ where source=1", null);
		if (localCursor != null){
				try {
					if (localCursor.isClosed()) {
						return ret;
					}
					while (localCursor.moveToNext()){
						int l2 =localCursor.getInt(localCursor
								.getColumnIndexOrThrow("sums"));
						if(l2>0){
						ret=true;
						}
			}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return ret;
	}

	public long getLinesUpdateTime() {
		long l1 = 0L;
		try {
			Cursor localCursor = this.sqlDB.rawQuery(
					"select max(update_time) as update_time from ibus_line",
					null);
			if (localCursor != null)
				if (localCursor.moveToNext()) {
					String x=localCursor.getString(localCursor
							.getColumnIndexOrThrow("update_time"));
					if(x!=null){
					long l2 = Long.parseLong(x);
					l1 = l2;
					}
				}
			try {
				if (!localCursor.isClosed())
					localCursor.close();
				return l1;
			} catch (Exception localException2) {
				localException2.printStackTrace();
				return l1;
			}
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		return l1;
	}
//
//	public final ArrayList<BusStationBean> getNearStations(double paramDouble1, double paramDouble2)
//  {
//    ArrayList localArrayList1 = new ArrayList();
//    String str = "select st_name,st_real_lat,st_real_lon ,st_id,weiba_id from ibus_station where st_real_lat-" + paramDouble1 + "<0.004 and " + paramDouble1 + "-st_real_lat<0.01 " + "and st_real_lon-" + paramDouble2 + "<0.004 and " + paramDouble2 + "-st_real_lon<0.01 " + "and st_status=0 and is_true=1 GROUP BY st_name";
//    Cursor localCursor = this.sqlDB.rawQuery(str, null);
//    ArrayList localArrayList2;
//    if (localCursor != null)
//    {
//      localArrayList2 = new ArrayList();
//      label102: if (localCursor.moveToNext())
//        break label163;
//      Collections.sort(localArrayList2, new Comparator()
//      {
//        public int compare(Object paramBusStationBean1, Object paramBusStationBean2)
//        {
//        	
//        	BusStationBean paramBusStationBean3 =(BusStationBean)paramBusStationBean1;
//        	BusStationBean paramBusStationBean4 =(BusStationBean)paramBusStationBean2;
//        	
//        	
//          return paramBusStationBean3.line_distance - paramBusStationBean4.line_distance;
//        }
//
//
//	
//      });
//      if (localArrayList2.size() > 10)
//        break label345;
//      localArrayList1.addAll(localArrayList2);
//    }
//    while (true)
//      try
//      {
//        if (!localCursor.isClosed())
//          localCursor.close();
//        return localArrayList1;
//        label163: BusStationBean localBusStationBean = new BusStationBean();
//        localBusStationBean.st_name = localCursor.getString(localCursor.getColumnIndexOrThrow("st_name"));
//        localBusStationBean.st_id = localCursor.getString(localCursor.getColumnIndexOrThrow("st_id"));
//        localBusStationBean.st_real_lat = localCursor.getDouble(localCursor.getColumnIndexOrThrow("st_real_lat"));
//        localBusStationBean.st_real_lon = localCursor.getDouble(localCursor.getColumnIndexOrThrow("st_real_lon"));
//        localBusStationBean.weiba_id = localCursor.getString(localCursor.getColumnIndexOrThrow("weiba_id"));
//        localBusStationBean.line_distance = (int)AMapUtils.calculateLineDistance(new LatLng(localCursor.getDouble(localCursor.getColumnIndexOrThrow("st_real_lat")), localCursor.getDouble(localCursor.getColumnIndexOrThrow("st_real_lon"))), new LatLng(paramDouble1, paramDouble2));
//        localArrayList2.add(localBusStationBean);
//        break label102:
//        label345: localArrayList1.addAll(localArrayList2.subList(0, 10));
//      }
//      catch (Exception localException)
//      {
//        localException.printStackTrace();
//      }
//    return localArrayList1;
//  }

	public final ArrayList<BusStationBean> getStationByName(String paramString) {
		String str = "select st_name,st_real_lat,st_real_lon ,st_id,weiba_id from ibus_station where is_true=1 and st_name like '%"
				+ paramString + "%' group by st_name";
		Cursor localCursor = this.sqlDB.rawQuery(str, null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				BusStationBean localBusStationBean = new BusStationBean();
				localBusStationBean.st_name = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name"));
				localBusStationBean.weiba_id = localCursor
						.getString(localCursor
								.getColumnIndexOrThrow("weiba_id"));
				localBusStationBean.st_id = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_id"));
				localBusStationBean.st_real_lat = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lat"));
				localBusStationBean.st_real_lon = localCursor
						.getDouble(localCursor
								.getColumnIndexOrThrow("st_real_lon"));
				localArrayList.add(localBusStationBean);
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	public ArrayList<String> getStationHistory() {
		Cursor localCursor = this.sqlDB.rawQuery(
				"select station_name from stationquery_history", null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				localArrayList.add(localCursor.getString(localCursor
						.getColumnIndexOrThrow("station_name")));
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	public final String getStationNameById(String paramString) {
		String str1 = "select st_name from ibus_station where is_true=1 and st_id='"
				+ paramString + "'";
		Cursor localCursor = this.sqlDB.rawQuery(str1, null);
		String str2 = null;
		if (localCursor != null) {
			boolean bool = localCursor.moveToNext();
			str2 = null;
			if (bool)
				str2 = localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name"));
		}
		try {
			if (!localCursor.isClosed())
				localCursor.close();
			return str2;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return str2;
	}

	public final ArrayList<String> getStationNameByName() {
		Cursor localCursor = this.sqlDB
				.rawQuery(
						"select st_name from ibus_station where is_true=1 group by st_name",
						null);
		ArrayList localArrayList = new ArrayList();
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					
					while (localCursor.moveToNext()){
						localArrayList.add(localCursor.getString(localCursor
						.getColumnIndexOrThrow("st_name")));
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	public final long getStationsUpdateTime() {
		long l1 = 0L;
		try {
			Cursor localCursor = this.sqlDB.rawQuery(
					"select max(update_time) as update_time from ibus_station",
					null);
			if (localCursor != null)
				if (localCursor.moveToNext()) {
					
					String x=localCursor.getString(localCursor
							.getColumnIndexOrThrow("update_time"));
					if(x!=null){
					long l2 = Long.parseLong(x);
					l1 = l2;
					}
					
					
					
				}
			try {
				if (!localCursor.isClosed())
					localCursor.close();
				return l1;
			} catch (Exception localException2) {
				localException2.printStackTrace();
				return l1;
			}
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		return l1;
	}

	public ArrayList<String> getTableDetail() {
		Cursor localCursor = this.sqlDB.rawQuery(
				"select * from sqlite_master where type='table'", null);
		ArrayList localArrayList = null;
		if (localCursor != null){
			localArrayList = new ArrayList();
				try {
					if (!localCursor.isClosed()) {
						return localArrayList;
					}
					while (localCursor.moveToNext()){
				localArrayList.add(localCursor.getString(localCursor
						.getColumnIndexOrThrow("sql")));
					}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return localArrayList;
	}

	public final long getUpdownsUpdateTime() {
		long l1 = 0L;
		Cursor localCursor = null;
		String str;
		try {
			localCursor = this.sqlDB
					.rawQuery(
							"select max(update_time) as update_time from ibus_line_updown",
							null);
			if (localCursor != null) {
				boolean bool = localCursor.moveToNext();
				str = null;
				if (bool) {
					

					String x=localCursor.getString(localCursor
							.getColumnIndexOrThrow("update_time"));
					if(x!=null){
					long l2 = Long.parseLong(x);
					l1 = l2;
					}
				
				}
			}
		} catch (Exception localException1) {
			try {
				if (!localCursor.isClosed())
					localCursor.close();
				return l1;
			} catch (Exception localException2) {
			}
		}
		return l1;
	}

	public void insertLineHistoryTable(String paramString) {
		this.sqlDB.execSQL("insert into linequery_history(line_name) values('"
				+ paramString + "')");
	}

	public void insertStationHistoryTable(String paramString) {
		this.sqlDB
				.execSQL("insert into stationquery_history(station_name) values('"
						+ paramString + "')");
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
	}

	public final void updateLineStations(ArrayList<BusBaseBean> paramArrayList) {
		if (paramArrayList == null)
			return;
		Iterator localIterator = paramArrayList.iterator();
		int i=0;
		this.sqlDB.beginTransaction();
		while (localIterator.hasNext()) {
			BusBaseBean localBusBaseBean = (BusBaseBean) localIterator.next();
			if (localBusBaseBean.line_st_status==null||localBusBaseBean.line_st_status.equals("0")) {
				String str2 = "delete from ibus_line_stations where line_station_id="
						+ localBusBaseBean.line_station_id;
				this.sqlDB.execSQL(str2);
				try {
					String str3 = "insert into ibus_line_stations(line_station_id,line_id,line_code,line_interval,updown_type,st_id,st_name,st_stop_level,line_st_status,is_true,update_time) values('"
							+ localBusBaseBean.line_station_id
							+ "','"
							+ localBusBaseBean.line_id
							+ "','"
							+ localBusBaseBean.line_code
							+ "','"
							+ localBusBaseBean.line_interval
							+ "','"
							+ localBusBaseBean.updown_type
							+ "','"
							+ localBusBaseBean.st_id
							+ "','"
							+ localBusBaseBean.st_name
							+ "',"
							+ localBusBaseBean.st_stop_level
							+ ",'"
							+ localBusBaseBean.line_st_status
							+ "','"
							+ localBusBaseBean.is_true
							+ "','"
							+ localBusBaseBean.update_time + "')";
					this.sqlDB.execSQL(str3);
				} catch (Exception localException) {
					localException.printStackTrace();
				}
			}
			else if (localBusBaseBean.line_st_status.equals("1")){
			
				String str1 = "delete from ibus_line_stations where line_station_id="
						+ localBusBaseBean.line_station_id;
				this.sqlDB.execSQL(str1);
			}
			if(i!=0&&i%100==0){
				this.sqlDB.setTransactionSuccessful();
				this.sqlDB.endTransaction();
				this.sqlDB.beginTransaction();
			}
			i++;
		}
		this.sqlDB.setTransactionSuccessful();
		this.sqlDB.endTransaction();
	
	}

	public final void updateLines(ArrayList<LineBean> paramArrayList) {
	
		this.sqlDB.beginTransaction();
		for (int i = 0;i<paramArrayList.size(); i++) {
			try {
				LineBean localLineBean = (LineBean) paramArrayList.get(i);
				if (localLineBean.line_status.equals("0")) {
					String str2 = "delete from ibus_line where line_id="
							+ localLineBean.line_id;
					this.sqlDB.execSQL(str2);
					String str3 = "insert into ibus_line(line_id,weiba_id,line_code,line_interval,line_name,line_status,update_time,ltd) values('"
							+ localLineBean.line_id
							+ "','"
							+ localLineBean.weiba_id
							+ "','"
							+ localLineBean.line_code
							+ "','"
							+ localLineBean.line_interval
							+ "','"
							+ localLineBean.line_name
							+ "','"
							+ localLineBean.line_status
							+ "','"
							+ localLineBean.update_time
							+ "','"
							+ localLineBean.ltd + "')";
					this.sqlDB.execSQL(str3);
				
					} else if (localLineBean.line_status.equals("1")) {
					String str1 = "delete from ibus_line where line_id="
							+ localLineBean.line_id;
					this.sqlDB.execSQL(str1);
				}
				if(i!=0&&i%1000==0){
					this.sqlDB.setTransactionSuccessful();
					this.sqlDB.endTransaction();
					this.sqlDB.beginTransaction();
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		this.sqlDB.setTransactionSuccessful();
		this.sqlDB.endTransaction();
	
	}

	public final void updateStations(ArrayList<BusStationBean> paramArrayList) {
		if (paramArrayList == null)
			return;
		Iterator localIterator = paramArrayList.iterator();
		int i=0;
		this.sqlDB.beginTransaction();
		while (localIterator.hasNext()) {
		
			BusStationBean localBusStationBean = (BusStationBean) localIterator
					.next();
			if (localBusStationBean.st_status.equals("0")) {
				String str2 = "delete from ibus_station where st_id="
						+ localBusStationBean.st_id;
				this.sqlDB.execSQL(str2);
				String str3 = "insert into ibus_station(st_id,weiba_id,st_name,st_real_lat,st_real_lon,st_status,update_time,is_true,st_side) values('"
						+ localBusStationBean.st_id
						+ "','"
						+ localBusStationBean.weiba_id
						+ "','"
						+ localBusStationBean.st_name
						+ "','"
						+ localBusStationBean.st_real_lat
						+ "','"
						+ localBusStationBean.st_real_lon
						+ "','"
						+ localBusStationBean.st_status
						+ "','"
						+ localBusStationBean.update_time
						+ "',"
						+ localBusStationBean.is_true
						+ ",'"
						+ localBusStationBean.st_side + "')";
				this.sqlDB.execSQL(str3);
			}
			else if (!localBusStationBean.st_status.equals("1")){
			String str1 = "delete from ibus_station where st_id="
					+ localBusStationBean.st_id;
			this.sqlDB.execSQL(str1);
			}
			if(i!=0&&i%100==0){
				this.sqlDB.setTransactionSuccessful();
				this.sqlDB.endTransaction();
				this.sqlDB.beginTransaction();
			}
			i++;
		}
		this.sqlDB.setTransactionSuccessful();
		this.sqlDB.endTransaction();
	}

	public void updateUpdowns(ArrayList<UpdownBean> paramArrayList) {
		if (paramArrayList == null)
			return;
		Iterator localIterator = paramArrayList.iterator();
		int i=0;
		this.sqlDB.beginTransaction();
		while (localIterator.hasNext()) {
		
			UpdownBean localUpdownBean = (UpdownBean) localIterator.next();
			if (localUpdownBean.updown_status.equals("0")) {
				String str2 = "delete from ibus_line_updown where updown_id="
						+ localUpdownBean.updown_id;
				this.sqlDB.execSQL(str2);
				String str3 = "insert into ibus_line_updown(updown_id,line_id,line_code,line_interval,updown_type,st_start_id,st_end_id,bus_start,bus_end,updown_status,update_time) values('"
						+ localUpdownBean.updown_id
						+ "','"
						+ localUpdownBean.line_id
						+ "','"
						+ localUpdownBean.line_code
						+ "','"
						+ localUpdownBean.line_interval
						+ "','"
						+ localUpdownBean.updown_type
						+ "','"
						+ localUpdownBean.st_start_id
						+ "','"
						+ localUpdownBean.st_end_id
						+ "','"
						+ localUpdownBean.bus_start
						+ "','"
						+ localUpdownBean.bus_end
						+ "','"
						+ localUpdownBean.updown_status
						+ "','"
						+ localUpdownBean.update_time + "')";
				this.sqlDB.execSQL(str3);
			}
			else if (!localUpdownBean.updown_status.equals("1")){
			String str1 = "delete from ibus_line_updown where updown_id="
					+ localUpdownBean.updown_id;
			this.sqlDB.execSQL(str1);}
			if(i!=0&&i%1000==0){
				this.sqlDB.setTransactionSuccessful();
				this.sqlDB.endTransaction();
				this.sqlDB.beginTransaction();
			}
			i++;
		}
		this.sqlDB.setTransactionSuccessful();
		this.sqlDB.endTransaction();
	}
}

/*
 * Location: C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name: cn.nj.busservice.db.BusDbHelper JD-Core Version: 0.5.4
 */