package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
	public final static int SUCCESS=1;
	public final static int FALSE=0;
	public final static int OIL=1;
	public final static int OTHER=2;
	public final static String oil_perfix="a";//OIL隐藏属性前缀
	public final static String other_perfix="b";//OTHER隐藏属性前缀
	public final static String DB_NAME="OIL.db";//数据库名称
	public final static int VERSION=1;//版本民称
	public final static String road="过路费";
	public final static String repair="保养费";
	public final static String other="汽车配件杂项";
	public final static String DB_OLI=" create table oil( "
			+" id integer primary key autoincrement, "
			+" litre real not null, "
			+" cost real not null, "
			+" kilo real not null, "
			+" systime timestamp not null, "
			+" useable tinyint default 1 "
			+" ) ";
	public final static String DB_OTHER="create table other( "
			+" id integer primary key autoincrement, "
			+" name varchar(20) not null, "
			+" cost real not null, "
			+" systime timestamp not null, "
			+" useable tinyint default 1 "
			+" ) ";
	public static String gettime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date).toString();
		return time;
	}
	public static String getYear(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		Date date=new Date();
		String time = format.format(date).toString();
		return time;
	}
	public static String getMonth(){
		SimpleDateFormat format=new SimpleDateFormat("MM");
		Date date=new Date();
		String time = format.format(date).toString();
		return time;
	}
}
