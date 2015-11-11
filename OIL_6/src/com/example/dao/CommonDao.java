package com.example.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.util.Common;
import com.example.util.Constants;
import com.example.util.DBHelper;
import com.example.util.DecimalArithmetic;
import com.example.vo.Oil;
import com.example.vo.Other;

public class CommonDao {
	private static CommonDao dao;

	public static CommonDao getDao() {
		if (dao == null) {
			dao = new CommonDao();
		}
		return dao;
	}

	private SQLiteDatabase dbw;
	private SQLiteDatabase dbr;
	/**
	 * 2015-11-6 段彬彬  首次创建
	 * 保存油耗
	 */
	public int saveOil(Context context, Oil oil) {
		dbw = DBHelper.getDBHelper(context).getWritableDatabase();
		dbw.execSQL("insert into oil(litre,cost,kilo,systime) values(?,?,?,?)",
				new Object[] { oil.getLitre(), oil.getCost(), oil.getKilo(),oil.getSystime()});
		dbw.close();
		return Constants.SUCCESS;
	}
	/**
	 * 2015-11-6 段彬彬  首次创建
		保存其他费用	
	 */
	public int saveOther(Context context, Other other) {
		dbw = DBHelper.getDBHelper(context).getWritableDatabase();
		dbw.execSQL("insert into other(name,cost,systime) values(?,?,?)", new Object[] {
				other.getName(), other.getCost() ,other.getSystime()});
		dbw.close();
		return Constants.SUCCESS;
	}
/**
 * 2015-11-6 段彬彬  首次创建
 *查询其他费用年度报告
 */
	public ArrayList<Other> queryOther(Context context, String beginTime,
			String endTime) {
		ArrayList<Other> list = new ArrayList<Other>();
		Other other = null;
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = dbr
				.rawQuery(
						"select name,cost,systime,id from other where systime between ? and ? ORDER BY id DESC",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			other = new Other();
			String name = cursor.getString(0);
			int cost = cursor.getInt(1);
			String systime = cursor.getString(2);
			int id=cursor.getInt(3);
			other.setName(name);
			other.setCost(cost);
			other.setSystime(systime);
			other.setId(id);
			list.add(other);
		}
		cursor.close();
		dbr.close();
		return list;
	}
	/**
	 * 2015-11-6 段彬彬  首次创建
	 *查询燃油费年度报告
	 */
	public int querySumOil(Context context,String beginTime,
			String endTime){
		int cost=0;
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = dbr
				.rawQuery(
						"select sum(cost) from oil where systime between ? and ?",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			cost=cursor.getInt(0);
		}
		cursor.close();
		dbr.close();
		return cost;
	}
	/**
	 * 2015-11-6 段彬彬  首次创建
	 *查询其他年度报告
	 */
	public int querySumOther(Context context,String beginTime,
			String endTime){
		int cost=0;
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = dbr
				.rawQuery(
						"select sum(cost) from other where systime between ? and ?",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			cost=cursor.getInt(0);
		}
		cursor.close();
		dbr.close();
		return cost;
	}
	/**
	 * 2015-11-6 段彬彬  首次创建
	 *查询其他明细
	 */
	public int queryOtherCost(Context context,String beginTime,
			String endTime,String name){
		int cost=0;
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = dbr
				.rawQuery(
						"select sum(cost) from other where name ="+name+" and systime between ? and ?",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			cost=cursor.getInt(0);
		}
		cursor.close();
		dbr.close();
		return cost;
	}
	/**
	 * 2015-11-6 段彬彬  首次创建
	 *查询油耗明细
	 */
	public ArrayList<Oil> queryOil(Context context, String beginTime,
			String endTime) {
		ArrayList<Oil> list = new ArrayList<Oil>();
		Oil oil = null;
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = dbr
				.rawQuery(
						"select litre,cost,kilo,systime,id from oil where systime between ? and ? ORDER BY id DESC",
				new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			oil = new Oil();
			double litre = cursor.getDouble(0);
			int cost = cursor.getInt(1);
			int kilo = cursor.getInt(2);
			String systime = cursor.getString(3);
			int id = cursor.getInt(4);
			oil.setLitre(litre);
			oil.setKilo(kilo);
			oil.setCost(cost);
			oil.setSystime(systime);
			oil.setId(id);
			list.add(oil);
		}
		cursor.close();
		dbr.close();
		return list;
	}

	/**
	 * 月份查询
	 */
	public HashMap<String,String> queryByMonth(Context context,String beginTime,String endTime){
		HashMap<String,String> map=new HashMap<String,String>();
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = null;
		//查询当月燃油总数，费用
		cursor = dbr
				.rawQuery(
						"select sum(litre) s1,sum(cost) s2 from oil where systime BETWEEN ? and ?",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			map.put("sum_litre", cursor.getString(0));
			map.put("sum_cost", cursor.getString(1));
		}
		cursor.close();
		//查询当月燃油使用总数
		String sum_litre_old=this.getlitreMonth(context, beginTime, endTime);
		map.put("sum_litre_used",sum_litre_old);
		//查询当月行驶公里数
		String sum_kilo_old=this.getKiloMonth(context, beginTime, endTime);
		map.put("sum_kilo",sum_kilo_old);
		//查询//百公里油耗///////////////////////////////////////////////////
		String sum_litre_useds="";
		String sum_kilos="";
		if(map.get("sum_litre_used")==null){
			sum_litre_useds="0";
		}else{
			sum_litre_useds=map.get("sum_litre_used");
		}
		if(map.get("sum_kilo")==null){
			sum_kilos="0";
		}else{
			sum_kilos=map.get("sum_kilo");
		}
		double sum_litre = Double.parseDouble(sum_litre_useds);
		double sum_kilo = Double.parseDouble(sum_kilos);
//		DecimalFormat format=new DecimalFormat("0.0");
		String km_L="0";
		try {
			km_L = String.valueOf(DecimalArithmetic.setDivide(100*sum_litre,sum_kilo,Common.Scale_1));
		} catch (Exception e) {
			e.printStackTrace();
			km_L="0";
		}
		map.put("km_L",km_L);
		///////////////////////////////////////////////////
		//查询其他费用总和
		cursor = dbr
				.rawQuery(
						"SELECT sum(cost) from other where systime BETWEEN ? and ? ",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			map.put("sum_other", cursor.getString(0));
		}
		cursor.close();
		//过路费
		cursor = dbr
				.rawQuery(
						"SELECT sum(cost) from other where systime BETWEEN ? and ? and name = ? ",
						new String[] { beginTime, endTime,Constants.road });
		while (cursor.moveToNext()) {
			map.put("sum_road", cursor.getString(0));
		}
		cursor.close();
		//保养费
		cursor = dbr
				.rawQuery(
						"SELECT sum(cost) from other where systime BETWEEN ? and ? and name = ? ",
						new String[] { beginTime, endTime ,Constants.repair});
		while (cursor.moveToNext()) {
			map.put("sum_repair", cursor.getString(0));
		}
		cursor.close();
		//配件杂项费用
		cursor = dbr
				.rawQuery(
						"SELECT sum(cost) from other where systime BETWEEN ? and ? and name = ? ",
						new String[] { beginTime, endTime ,Constants.other});
		while (cursor.moveToNext()) {
			map.put("sum_otherCost", cursor.getString(0));
		}
		cursor.close();
		
		dbr.close();
		return map;
	}
	//查询当月行驶公里数
	public String getKiloMonth(Context context,String time_start,String time_end){
		String year2=time_start.substring(0, 4);
		String month2=time_start.substring(5, 7);
		String year1=year2;
		if(month2.equals("01")){
			year1=String.valueOf(Integer.parseInt(year2)-1);
		}
		String month1=String.valueOf(Integer.parseInt(month2)-1);
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		int s2=0;int s1=0;
		Cursor cursor=null; 
		cursor = dbr.rawQuery("select max(kilo) from oil WHERE systime BETWEEN ? and ?",
						new String[] { year1+"-"+month1+"-"+"01", year1+"-"+month1+"-"+"31"});
		while (cursor.moveToNext()) {
			s1=cursor.getInt(0);
		}
		cursor.close();
		cursor = dbr.rawQuery("select max(kilo) from oil WHERE systime BETWEEN ? and ?",
				new String[] { time_start,time_end});
		while (cursor.moveToNext()) {
			s2=cursor.getInt(0);
		}
		cursor.close();
		s1=s2-s1;
		return String.valueOf(s1);
	}
	//获取当月累计油耗
	public String getlitreMonth(Context context,String time_start,String time_end){
		String year2=time_start.substring(0, 4);
		String month2=time_start.substring(5, 7);
		String year1=year2;
		if(month2.equals("01")){
			year1=String.valueOf(Integer.parseInt(year2)-1);
		}
		String month1=String.valueOf(Integer.parseInt(month2)-1);
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		double s2=0;double s1=0;
		Cursor cursor=null; 
		cursor = dbr.rawQuery("select litre from oil WHERE systime BETWEEN ? and ? ORDER BY ID DESC LIMIT 1",
						new String[] { year1+"-"+month1+"-"+"01", year1+"-"+month1+"-"+"31"});
		while (cursor.moveToNext()) {
			s1=cursor.getDouble(0);//上个月最后一次油耗
		}
		cursor.close();
		cursor = dbr.rawQuery("select sum(litre)-(select litre from oil where systime BETWEEN ? and ? ORDER BY id desc LIMIT 1)from oil where systime BETWEEN ? and ? ",
				new String[] { time_start,time_end,time_start,time_end});
		while (cursor.moveToNext()) {
			s2=cursor.getDouble(0);
		}
		cursor.close();
		s1=s2+s1;
		return String.valueOf(s1);
	}
	/**
	 * 2015-11-2 段彬彬  首次创建
	 * 比例费用报告获取当年的
	 */
	public HashMap<String,String> queryScaleReport(Context context,String year){
		String beginTime=year+"-"+"01-01";
		String endTime=year+"-"+"12-31";
		double datas[]=new double[4];
		HashMap<String,String> map=new HashMap<String,String>();
		dbr = DBHelper.getDBHelper(context).getReadableDatabase();
		Cursor cursor = null;
		//查询当月燃油总数，费用
		cursor = dbr
				.rawQuery(
						"select sum(cost) from oil where systime BETWEEN ? and ? ",
						new String[] { beginTime, endTime });
		while (cursor.moveToNext()) {
			datas[0]=cursor.getDouble(0);
			map.put("sum_scale_oil",String.valueOf(Math.round(datas[0])));
		}
		cursor.close();
		
		cursor = dbr
				.rawQuery(
						"select sum(cost) from other where name= ? and systime BETWEEN ? and ?",
						new String[] { Constants.road,beginTime, endTime });
		while (cursor.moveToNext()) {
			datas[1]=cursor.getDouble(0);
			map.put("sum_scale_road", String.valueOf(Math.round(datas[1])));
		}
		cursor.close();
		
		cursor = dbr
				.rawQuery(
						"select sum(cost) from other where name= ? and systime BETWEEN ? and ?",
						new String[] { Constants.repair,beginTime, endTime });
		while (cursor.moveToNext()) {
			datas[2]=cursor.getDouble(0);
			map.put("sum_scale_repair",String.valueOf(Math.round(datas[2])));
		}
		cursor.close();
		
		cursor = dbr
				.rawQuery(
						"select sum(cost) from other where name= ? and systime BETWEEN ? and ?",
						new String[] { Constants.other,beginTime, endTime });
		while (cursor.moveToNext()) {
			datas[3]=cursor.getDouble(0);
			map.put("sum_scale_other",String.valueOf(Math.round(datas[3])));
		}
		cursor.close();
		dbr.close();
		double sum=0;
		for (double i : datas) {//计算总和
			sum+=i;
		}
		DecimalFormat format=new DecimalFormat("0.0");
		for(int i=0;i<4;i++){//计算百分比
			if(datas[i]!=0){
				datas[i]=(datas[i]/sum)*100;
			}
			datas[i]=Double.valueOf(format.format(datas[i]));
			map.put("percent_"+i,String.valueOf(datas[i]));
		}
		
		return map;
	}
	/**
	 * 2015-11-2 段彬彬  首次创建
	 *删除OIL数据
	 */
	public void deleteOil(Context context,String id){
		dbw = DBHelper.getDBHelper(context).getWritableDatabase();
		dbw.execSQL("delete from oil where id= ?", new Object[] {
				id});
		dbw.close();
	}
	/**
	 * 2015-11-2 段彬彬  首次创建
	 *删除OTHER数据
	 */
	public void deleteOther(Context context,String id){
		dbw = DBHelper.getDBHelper(context).getWritableDatabase();
		dbw.execSQL("delete from other where id= ?", new Object[] {
				id});
		dbw.close();
	}
}
