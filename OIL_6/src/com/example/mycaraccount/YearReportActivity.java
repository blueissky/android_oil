package com.example.mycaraccount;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Constants;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

public class YearReportActivity extends Activity{
	private Button[] but_year;
	private int but_flag_year = 0;
	private String but_year_select = "2015";
    protected HorizontalBarChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalbarchart);
        mChart = (HorizontalBarChart) findViewById(R.id.year_report_char);
        this.setChart(mChart);
        but_year = new Button[11];
		but_year[0] = (Button) findViewById(R.id.but_2015y);
		but_year[1] = (Button) findViewById(R.id.but_2016y);
		but_year[2] = (Button) findViewById(R.id.but_2017y);
		but_year[3] = (Button) findViewById(R.id.but_2018y);
		but_year[4] = (Button) findViewById(R.id.but_2019y);
		but_year[5] = (Button) findViewById(R.id.but_2020y);
		but_year[6] = (Button) findViewById(R.id.but_2021y);
		but_year[7] = (Button) findViewById(R.id.but_2022y);
		but_year[8] = (Button) findViewById(R.id.but_2023y);
		but_year[9] = (Button) findViewById(R.id.but_2024y);
		but_year[10] = (Button) findViewById(R.id.but_2025y);
        this.initButton();
        this.setData(mChart,Constants.getYear());
        SlideMenu.setActivity(this);
		SlideMenu.initMenu();
    }
    /**
     * 设置条显示格式
     * 2015-11-6 段彬彬  首次创建
	 */
	public void setChart(HorizontalBarChart mChart){
		mChart.setDrawGridBackground(false);//北京
	    mChart.setDescription("");
	    mChart.animateY(2500);
	    XAxis xl = mChart.getXAxis();
	    xl.setDrawGridLines(false);
	    xl.setPosition(XAxisPosition.BOTTOM);//月份在左侧显示
	    xl.setXOffset(-0.5f);
	}
	/**
	 * 设置Y数据
	 * 2015-10-21 段彬彬  首次创建
	 */
    private void setData(HorizontalBarChart mChart,String year) {
        ArrayList<String> xVals = new ArrayList<String>();//月份
        xVals.add("一月");xVals.add("二月");xVals.add("三月");xVals.add("四月");
        xVals.add("五月");xVals.add("六月");xVals.add("七月");xVals.add("八月");
        xVals.add("九月");xVals.add("十月");xVals.add("十一月");xVals.add("十二月");
        BarDataSet set1 =this.queryall(year);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);
    }
    /**
     * 2015-11-6 段彬彬  首次创建
     *查询数据
     */
    public BarDataSet queryall(String year){
    	String beginTime="";
    	String endTime="";
    	int cost=0;
    	int sum=0;
    	ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();//数据
    	for(int i=1;i<13;i++){
    		beginTime=year+"-0"+i+"-01";
    		endTime=year+"-0"+i+"-31";
    		if(i==10){
    			beginTime=year+"-"+i+"-01";
        		endTime=year+"-"+i+"-31";
    		}
    		if(i==11){
    			beginTime=year+"-"+i+"-01";
        		endTime=year+"-"+i+"-31";
    		}
    		if(i==12){
    			beginTime=year+"-"+i+"-01";
        		endTime=year+"-"+i+"-31";
    		}
    		cost=CommonDao.getDao().querySumOil(this, beginTime, endTime);
    		cost=CommonDao.getDao().querySumOther(this, beginTime, endTime)+cost;
    		float fs=Float.parseFloat(String.valueOf(cost));
    		yVals1.add(new BarEntry(fs,i-1));
    		sum+=cost;
    		cost=0;
    	}
    	BarDataSet set1 = new BarDataSet(yVals1, year+"年费用合计：("+sum+"元)");
    	return set1;
    }
    /**
     * 2015-11-6 段彬彬  首次创建
     * 初始化按钮
     */
    public void initButton() {
		for (but_flag_year = 0; but_flag_year < 11; but_flag_year++) {
			but_year[but_flag_year].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Button but = (Button) findViewById(v.getId());
					but_year_select = but.getText().toString();//年费按钮监听
					mChart.refreshDrawableState();
					setChart(mChart);
					setData(mChart,but_year_select);
				}
			});
		}
    }
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    }
}
