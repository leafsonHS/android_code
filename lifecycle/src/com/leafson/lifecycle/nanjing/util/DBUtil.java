package com.leafson.lifecycle.nanjing.util;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.leafson.lifecycle.nanjing.bean.BusBaseBean;
import com.leafson.lifecycle.nanjing.bean.BusStationBean;
import com.leafson.lifecycle.nanjing.bean.LineBean;
import com.leafson.lifecycle.nanjing.bean.UpdownBean;

public class DBUtil
{
  private static final String TAG = DBUtil.class.getSimpleName();
  public static ArrayList<LineBean> allLines;
  public static ArrayList<String> allStations;

  public static void clearLineQueryHistory(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.clearLineQueryHistory();
    localBusDbHelper.close();
  }
  public static boolean isJiangninginited(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
   boolean is=  localBusDbHelper.isJiangninginited();
    localBusDbHelper.close();
    
    return is;
  }
  

  public static void createHistoryTable(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.createQueryHistoryTable();
    localBusDbHelper.close();
  }

  public static void doUpdateLineStations(Context paramContext, ArrayList<BusBaseBean> paramArrayList)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.updateLineStations(paramArrayList);
    localBusDbHelper.close();
  }

  public static void doUpdateLines(Context paramContext, ArrayList<LineBean> paramArrayList)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.updateLines(paramArrayList);
    localBusDbHelper.close();
  }

  public static void doUpdateStations(Context paramContext, ArrayList<BusStationBean> paramArrayList)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.updateStations(paramArrayList);
    localBusDbHelper.close();
  }

  public static void doUpdateUpdowns(Context paramContext, ArrayList<UpdownBean> paramArrayList)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    localBusDbHelper.updateUpdowns(paramArrayList);
    localBusDbHelper.close();
  }
  public static  Map<String, Map<String, Object>> getStations(Context paramContext,String lineId)
  {
	  Map<String, Map<String, Object>> DATA = null;
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    	DATA = localBusDbHelper.getStations(lineId);
        localBusDbHelper.close();
	return DATA;
  }

  public static ArrayList<LineBean> getAllLines(Context paramContext)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      if (allLines == null)
      {
        allLines = localBusDbHelper.getAllLineNames();
        localBusDbHelper.close();
        ArrayList localArrayList = allLines;
        return localArrayList;
      }
	return allLines;
  }

  public static ArrayList<String> getAllStations(Context paramContext)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      if (allStations == null)
      {
        allStations = localBusDbHelper.getStationNameByName();
        localBusDbHelper.close();
        ArrayList localArrayList = allStations;
        return localArrayList;
  }
	return allStations;}

  public static ArrayList<LineBean> getLineByLineId(Context paramContext, String paramString)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      ArrayList localArrayList = localBusDbHelper.getLineBean(paramString);
      localBusDbHelper.close();
      return localArrayList;
  }

  public static LineBean getLineByLineName(Context paramContext, String paramString)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      LineBean localLineBean = localBusDbHelper.getLineByLineName(paramString);
      localBusDbHelper.close();
      return localLineBean;
  }

  public static ArrayList<LineBean> getLineByStationName(Context paramContext, String paramString)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      ArrayList localArrayList = localBusDbHelper.getLinesByStationName(paramString);
      localBusDbHelper.close();
      return localArrayList;
   
  }

  public static String getLineIdByLineName(Context paramContext, String paramString)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      String str = localBusDbHelper.getLineIdByLineName(paramString);
      localBusDbHelper.close();
      return str;
  
  }

  public static ArrayList<String> getLineQueryHistory(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    ArrayList localArrayList = localBusDbHelper.getLineHistory();
    localBusDbHelper.close();
    return localArrayList;
  }

  public static ArrayList<BusStationBean> getLineStationsByLineId(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      ArrayList localArrayList = localBusDbHelper.getLineStationsByLineId(paramString1, paramString2, paramInt);
      localBusDbHelper.close();
      return localArrayList;
  
  }

  public static long getLineStationsUpdateTime(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    long l = localBusDbHelper.getLineStationsUpdateTime();
    localBusDbHelper.close();
    return l;
  }

  public static long getLinesUpdateTime(Context paramContext)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      long l = localBusDbHelper.getLinesUpdateTime();
      localBusDbHelper.close();
      return l;
  }

//  public static ArrayList<BusStationBean> getNearStations(Context paramContext, double paramDouble1, double paramDouble2)
//  {
//      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
//      ArrayList localArrayList = localBusDbHelper.getNearStations(paramDouble1, paramDouble2);
//      localBusDbHelper.close();
//      return localArrayList;
//  }

  public static ArrayList<String> getStationQueryHistory(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    ArrayList localArrayList = localBusDbHelper.getStationHistory();
    localBusDbHelper.close();
    return localArrayList;
  }

  public static long getStationsUpdateTime(Context paramContext)
  {
    BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
    long l = localBusDbHelper.getStationsUpdateTime();
    localBusDbHelper.close();
    return l;
  }

  public static long getUpdownsUpdateTime(Context paramContext)
  {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      long l = localBusDbHelper.getUpdownsUpdateTime();
      localBusDbHelper.close();
      return l;
  }

  public static void insertLineHistoryTable(Context paramContext, String paramString)
  {
    try
    {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      localBusDbHelper.insertLineHistoryTable(paramString);
      localBusDbHelper.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void insertStationHistoryTable(Context paramContext, String paramString)
  {
    try
    {
      BusDbHelper localBusDbHelper = new BusDbHelper(paramContext);
      localBusDbHelper.insertStationHistoryTable(paramString);
      localBusDbHelper.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}