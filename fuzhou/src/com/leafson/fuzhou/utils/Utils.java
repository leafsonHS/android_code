package com.leafson.fuzhou.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class Utils {

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
