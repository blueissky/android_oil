package com.example.util;

import java.util.HashMap;

public class MonthMatch {
	private String but_month_select;
	private String but_month_select_end;
	private static MonthMatch mm;
	public static MonthMatch get(){
		if(mm==null){
			mm=new MonthMatch();
		}
		return mm;
	}
	public  HashMap<String,String> mothmatch(String param) {
		HashMap<String,String> map=new HashMap<String,String>();
		if(param.equals("一月")){
			but_month_select = "-01-01";
			but_month_select_end = "-01-31";
		}else if(param.equals("二月")){
			but_month_select = "-02-01";
			but_month_select_end = "-02-31";
		}else if(param.equals("三月")){
			but_month_select = "-03-01";
			but_month_select_end = "-03-31";
		}else if(param.equals("四月")){
			but_month_select = "-04-01";
			but_month_select_end = "-04-31";
		}else if(param.equals("五月")){
			but_month_select = "-05-01";
			but_month_select_end = "-05-31";
		}else if(param.equals("六月")){
			but_month_select = "-06-01";
			but_month_select_end = "-06-31";
		}else if(param.equals("七月")){
			but_month_select = "-07-01";
			but_month_select_end = "-07-31";
		}else if(param.equals("八月")){
			but_month_select = "-08-01";
			but_month_select_end = "-08-31";
		}else if(param.equals("九月")){
			but_month_select = "-09-01";
			but_month_select_end = "-09-31";
		}else if(param.equals("十月")){
			but_month_select = "-10-01";
			but_month_select_end = "-10-31";
		}else if(param.equals("十一月")){
			but_month_select = "-11-01";
			but_month_select_end = "-11-31";
		}else if(param.equals("十二月")){
			but_month_select = "-12-01";
			but_month_select_end = "-12-31";
		}
		map.put("but_month_select",but_month_select);
		map.put("but_month_select_end",but_month_select_end);
		return map;
	}
}
