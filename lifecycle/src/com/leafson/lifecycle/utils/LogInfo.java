package com.leafson.lifecycle.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogInfo {
	
	
	private static Map<String,String> infos=new HashMap<String,String>();
	
	
	
	public static void init()
	{
		
		infos.put("453","49266468");
		infos.put("513","55791828");
		infos.put("474","51550344");
		infos.put("475","51659100");
		infos.put("476","51767856");
		infos.put("477","51876612");
		infos.put("484","52637904");
		infos.put("485","52746660");
		infos.put("478","51985368");
		infos.put("531","57749436");
		infos.put("486","52855416");
		infos.put("479","52094124");
		infos.put("487","52964172");
		infos.put("488","53072928");
		infos.put("489","53181684");
		infos.put("491","53399196");
		infos.put("480","52202880");
		infos.put("539","58619484");
		infos.put("492","53507952");
		infos.put("498","54160488");
		infos.put("404","43937424");
		infos.put("522","56770632");
		infos.put("388","42197328");
		infos.put("405","44046180");
		infos.put("494","53725464");
		infos.put("389","42306084");
		infos.put("505","54921780");
		infos.put("500","54378000");
		infos.put("473","51441588");
		infos.put("495","53834220");
		infos.put("496","53942976");
		infos.put("391","42523596");
		infos.put("501","54486756");
		infos.put("471","51224076");
		infos.put("540","58728240");
		infos.put("544","59163264");
		infos.put("536","58293216");
		infos.put("528","57423168");
		infos.put("406","44154936");
		infos.put("545","59272020");
		infos.put("543","59054508");
		infos.put("411","44698716");
		infos.put("393","42741108");
		infos.put("397","43176132");
		infos.put("439","47743884");
		infos.put("502","54595512");
		infos.put("458","49810248");
		infos.put("503","54704268");
		infos.put("504","54813024");
		infos.put("481","52311636");
		infos.put("472","51332832");
		infos.put("394","42849864");
		infos.put("533","57966948");
		infos.put("519","56444364");
		infos.put("395","42958620");
		infos.put("526","57205656");
		infos.put("525","57096900");
		infos.put("506","55030536");
		infos.put("461","50136516");
		infos.put("462","50245272");
		infos.put("468","50897808");
		infos.put("469","51006564");
		infos.put("396","43067376");
		infos.put("335","36433260");
		infos.put("374","40674744");
		infos.put("398","43284888");
		infos.put("467","50789052");
		infos.put("399","43393644");
		infos.put("400","43502400");
		infos.put("401","43611156");
		infos.put("402","43719912");
		infos.put("457","49701492");
		infos.put("457","49701492");
		infos.put("446","48505176");
		infos.put("421","45786276");
		infos.put("524","56988144");
		infos.put("463","50354028");
		infos.put("454","49375224");
		infos.put("465","50571540");
		infos.put("497","54051732");
		infos.put("451","49048956");
		infos.put("403","43828668");
		infos.put("482","52420392");
		infos.put("383","41653548");
		infos.put("386","41979816");
		infos.put("384","41762304");
		infos.put("385","41871060");

		
	}
	
	public  static String getLocCode(String lineNumbber)
	{
		
		Calendar calendar = Calendar.getInstance();
		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		int cnt=Integer.parseInt(lineNumbber)*month*year*date;
		return String.valueOf(cnt);
		
	}
	
}
