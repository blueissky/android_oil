
package com.example.mycaraccount;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Constants;
import com.example.util.MonthMatch;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class MonthReportActivity extends FragmentActivity {

    private BarChart mChart;
	private TextView text_sum;
	private Button[] but_year;
	private Button[] but_month;
	private int but_flag_year = 0;
	private int but_flag_month = 0;
	private String but_year_select = "2015";
	private String but_month_select = "一月";
	private String but_month_select_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        text_sum=(TextView)findViewById(R.id.monthcostsum);
        mChart = (BarChart) findViewById(R.id.another_char);
        this.setChart(mChart);
        
        but_year = new Button[11];
		but_month = new Button[12];
		but_year[0] = (Button) findViewById(R.id.but_2015m);
		but_year[1] = (Button) findViewById(R.id.but_2016m);
		but_year[2] = (Button) findViewById(R.id.but_2017m);
		but_year[3] = (Button) findViewById(R.id.but_2018m);
		but_year[4] = (Button) findViewById(R.id.but_2019m);
		but_year[5] = (Button) findViewById(R.id.but_2020m);
		but_year[6] = (Button) findViewById(R.id.but_2021m);
		but_year[7] = (Button) findViewById(R.id.but_2022m);
		but_year[8] = (Button) findViewById(R.id.but_2023m);
		but_year[9] = (Button) findViewById(R.id.but_2024m);
		but_year[10] = (Button) findViewById(R.id.but_2025m);

		but_month[0] = (Button) findViewById(R.id.but_01m);
		but_month[1] = (Button) findViewById(R.id.but_02m);
		but_month[2] = (Button) findViewById(R.id.but_03m);
		but_month[3] = (Button) findViewById(R.id.but_04m);
		but_month[4] = (Button) findViewById(R.id.but_05m);
		but_month[5] = (Button) findViewById(R.id.but_06m);
		but_month[6] = (Button) findViewById(R.id.but_07m);
		but_month[7] = (Button) findViewById(R.id.but_08m);
		but_month[8] = (Button) findViewById(R.id.but_09m);
		but_month[9] = (Button) findViewById(R.id.but_10m);
		but_month[10] = (Button) findViewById(R.id.but_11m);
		but_month[11] = (Button) findViewById(R.id.but_12m);
        
		this.initButton();
		//获取当前月份数据
        String y=Constants.getYear();
        String m=Constants.getMonth();
        String t_start=y+"-"+m+"-01";
        String t_end=y+"-"+m+"-31";
		setData(t_start,t_end);
        SlideMenu.setActivity(this);
		SlideMenu.initMenu();
    }
    //属性设置
    public void setChart(BarChart mChart){
    	mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        // add a nice and smooth animation
        mChart.animateY(1500);
        mChart.getLegend().setEnabled(false);
    }
    /**
     * @param p1	费用总计
     * @param p2	公里数
     * @param p3	燃油
     * @param p4	当月油耗
     * 2015-11-6 段彬彬  首次创建
     */
    public void setText(TextView text_sum,String p1,String p2,String p3,String p4,String p5){
    	text_sum.setText("当前时间："+p5+"\n"
    			+"费用总计："+p1+"元"
    			+"\n"+"当月累计行驶公里数："+p2+"Km"
    			+"\n"+"加油："+p3+"L"
    			+"\n"+"当月油耗:"+p4+"L/Km");
    }
    //X值设置
    public ArrayList<String> getxVals(){
    	ArrayList<String> xVals = new ArrayList<String>();
    	xVals.add("燃油费");
    	xVals.add("过路费");
    	xVals.add("保养费");
    	xVals.add("配件杂项");
    	return xVals;
    }
    //Y值设置
    public ArrayList<BarEntry> setyVals(ArrayList<Integer> list){
    	ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
    	int i=0;
    	for (Integer integer : list) {
    		yVals.add(new BarEntry(integer,i));
    		i++;
		}
    	return yVals;
    }
    //添加到组件上
    public void setxyVals(ArrayList<String> xVals,ArrayList<BarEntry> yVals1){
    	  BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
          set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
          set1.setDrawValues(false);
          ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
          dataSets.add(set1);
          BarData data = new BarData(xVals, dataSets);
          mChart.setData(data);
          mChart.invalidate();
    }
    /**
     * 初始化监听
     * TODO
     * @Author	段彬彬  void
     * @Date	2015-11-1
     * 更新日志
     * 2015-11-1 段彬彬  首次创建
     *
     */
    	public void initButton() {
    		for (but_flag_year = 0; but_flag_year < 11; but_flag_year++) {
    			but_year[but_flag_year].setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					Button but = (Button) findViewById(v.getId());
    					but_year_select = but.getText().toString();//年费按钮监听
    				}
    			});
    		}
    		for (but_flag_month = 0; but_flag_month < 12; but_flag_month++) {
    			but_month[but_flag_month].setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {//月份按钮监听
    					HashMap<String, String> map=null;
    					Button but = (Button) findViewById(v.getId());
    					but_month_select = but.getText().toString();
    					map = MonthMatch.get().mothmatch(but_month_select);//月份匹配
    					but_month_select=map.get("but_month_select");
    					but_month_select_end=map.get("but_month_select_end");
    					String time_start=but_year_select + but_month_select;//开始时间
    					String time_end=but_year_select+but_month_select_end;//结束时间
    					mChart.refreshDrawableState();
    					setChart(mChart);
    					setData(time_start, time_end);//添加数据
    				}
    			});
    		}
    	}
    	/**
    	 * 添加数据
    	 * 2015-11-1 段彬彬  首次创建
    	 */
    	public void setData(String time_start,String time_end){
    		String year=time_start.substring(0, 4);
    		String month=time_start.substring(5, 7);
    		String current_time=year+"年"+month+"月";
    		HashMap<String, String> map = CommonDao.getDao().queryByMonth(MonthReportActivity.this, time_start,time_end);
	        String sum_litre=map.get("sum_litre");
	        String sum_cost=map.get("sum_cost");
	        String sum_kilo=map.get("sum_kilo");
	        String km_L=map.get("km_L");
	        String sum_other=map.get("sum_other");
	        String sum_road=map.get("sum_road");
	        String sum_repair=map.get("sum_repair");
	        String sum_otherCost=map.get("sum_otherCost");
	        if(sum_litre==null){
	        	sum_litre="0";
	        }
	        if(sum_cost==null){
	        	sum_cost="0";
	        }
	        if(sum_kilo==null){
	        	sum_kilo="0";
	        }
	        if(sum_other==null){
	        	sum_other="0";
	        }
	        if(sum_road==null){
	        	sum_road="0";
	        }
	        if(sum_repair==null){
	        	sum_repair="0";
	        }
	        if(sum_otherCost==null){
	        	sum_otherCost="0";
	        }
	        int sumcost = Integer.parseInt(sum_other)+Integer.parseInt(sum_cost);//费用总计
	        
	        ArrayList<Integer> listy=new ArrayList<Integer>();
			listy.add(Integer.parseInt(sum_cost));
	        listy.add(Integer.parseInt(sum_road));
	        listy.add(Integer.parseInt(sum_repair));
	        listy.add(Integer.parseInt(sum_otherCost));
	        setxyVals(getxVals(),setyVals(listy));
	        setText(text_sum,String.valueOf(sumcost),sum_kilo,sum_litre,km_L,current_time);
    	}
    	@Override
    	public boolean onCreateOptionsMenu(Menu menu) {
    		getMenuInflater().inflate(R.menu.reoprt, menu);
    		return true;
    	}

    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    		int id = item.getItemId();
    		if (id == R.id.report_book) {
    			AlertDialog dialog = new AlertDialog.Builder(MonthReportActivity.this)  
    	        .setIcon(android.R.drawable.ic_dialog_alert)  
    	        .setTitle("说明书!")
    	        .setMessage("费用总计：\n当月总消费。\n\n"+
    	        "当月累计行驶公里数：\n当月最后一次录入的数据－上月最后一次数据。\n\n"+
    	        "加油：\n当月录入的加油数据。\n\n"+
    	        "当月油耗：\n当月油耗总数－当月最后一次录入＋上月最后一次加油数据\n"+
    	        "÷累计行驶公里数x100。\n")
    	        .create();  
    			dialog.show();  
    			return true;
    		}
    		return super.onOptionsItemSelected(item);
    	}
    	@Override
    	protected void onRestart() {
    		super.onRestart();
    	}
}
