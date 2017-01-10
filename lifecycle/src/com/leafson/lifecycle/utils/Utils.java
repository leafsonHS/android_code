package com.leafson.lifecycle.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.leafson.lifecycle.HomeActivity;

public class Utils {
	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str)
	{
		if(str == null || str.length() <= 0 || str.trim() == null || str.trim().length() <= 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 */
	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}
	public static String convertStreamToString(InputStream is) {

		GZIPInputStream gzin = null;
		try {
			gzin = new GZIPInputStream(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		InputStreamReader isr = null; 
         try {
			 isr = new InputStreamReader(gzin, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
		
		
		BufferedReader reader = null;
				reader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();

		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}
		return sb.toString();

	}
	
	public static String convertStreamToStringNoz(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return sb.toString();

	}
	
	
	public static 	List<Map<String, Object>> getXpathBus(InputStream inStream) {
		HtmlCleaner cleaner = new HtmlCleaner();
//		String s1 =convertStreamToStringNoz(inStream);
//		System.out.print(s1);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
 		String[] s=null;
		try {
			TagNode node = cleaner.clean(inStream, "utf-8");
			Object[] nsxxxxcontent = null;
			Object[] nsxxxxno = null;
			Object[] nsxxxxbus = null;
			
			nsxxxxcontent= node.evaluateXPath("//div[@class='swap']");
			TagNode divNod1e=(TagNode) nsxxxxcontent[0];
			TagNode divNode=(TagNode) divNod1e.getElementListByName("div", false).get(3);
		
			TagNode divMode1=(TagNode) divNod1e.getElementListByName("div", false).get(2);
			String href=divMode1.getAttributeByName("onclick");
		
			String fanxiangid=href.substring(href.lastIndexOf("=")+1);
			if(HomeActivity.fanxiangid==null){
			HomeActivity.fanxiangid=fanxiangid.replace("'", "");
			}
			Object[]  childs=divNode.getChildTags();
			int length=childs.length;
			int index=1;
			for(int in=0;in<length;in++)
			{ 
			
				TagNode contentNode=(TagNode)childs[in];
				if(contentNode.getName().equals("br"))
				{
					index++;
				}
				if(contentNode.getName().equals("span"))
				{
		
					String info=contentNode.getText().toString();
					String[] infos=info.split("米");
					String updateTime=infos[1];
					String distance=infos[0];
					String stNo=String.valueOf(index);
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("updateTime", updateTime);
					map.put("distance", distance);
					map.put("stNo", stNo);
					data.add(map);
					index--;			
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return data;
		
	}
	
	public static 	List<Map<String, Object>> getXpathBusBeiFen(InputStream inStream) {
		HtmlCleaner cleaner = new HtmlCleaner();
//		String s1 =convertStreamToStringNoz(inStream);
//		System.out.print(s1);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
 		String[] s=null;
		try {
			TagNode node = cleaner.clean(inStream, "utf-8");
			Object[] nsxxxxcontent = null;
			Object[] nsxxxxno = null;
			Object[] nsxxxxbus = null;
			
			nsxxxxcontent= node.evaluateXPath("//div/div/div/div");
			TagNode divNod1e=(TagNode) nsxxxxcontent[0];
			String href=divNod1e.getAttributeByName("onclick");
			String fanxiangid=href.substring(href.lastIndexOf("=")+1);
			if(HomeActivity.fanxiangid==null){
			HomeActivity.fanxiangid=fanxiangid.replace("')", "");
			}
			
			Object[] nsxxxxcontent1 = null;
			nsxxxxcontent1= node.evaluateXPath("//div/div/ol");
			TagNode nsxxxxcontent1ol=(TagNode) nsxxxxcontent1[0];
			Object[]  childs=nsxxxxcontent1ol.getChildTags();
			int length=childs.length;
			int index=1;
			for(int in=0;in<length;in++)
			{ 
			
				TagNode contentNode=(TagNode)childs[in];
				if(contentNode.getName().equals("li"))
				{
					
					List<TagNode> tsglist=contentNode.getElementListByName("img", true);
					List<TagNode> atsglist=contentNode.getElementListByName("a", true);
					if(tsglist.isEmpty()&&atsglist.size()>0)
					{
						index++;	
					}else
					{
					
						List<TagNode> tsgspanlist=contentNode.getElementListByName("span", true);
						for(TagNode nd:tsgspanlist)
						{
							String info=nd.getText().toString();
							String[] infos=info.split("米");
							String updateTime="";
							String distance="";
							if(infos.length<2)
							{
								 distance=info;
								  updateTime=info.split("已到站")[1];
							}else{
								
								 updateTime=infos[1];
								 distance=infos[0];
							}
							
							String stNo=String.valueOf(index);
							Map<String, Object> map=new HashMap<String, Object>();
							map.put("updateTime", updateTime);
							map.put("distance", distance);
							map.put("stNo", stNo);
							data.add(map);
							//index--;				
							
							
						}
						
					}
					
				}
				if(contentNode.getName().equals("span"))
				{
		
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return data;
		
	}
	 public static String dateToString(Date date, String dateFormat)
	    {
	        SimpleDateFormat df = null;
	        String returnValue = "";
	        if (date != null)
	        {
	            df = new SimpleDateFormat(dateFormat);
	            returnValue = df.format(date);
	        }
	        return returnValue;
	    }
	
}
