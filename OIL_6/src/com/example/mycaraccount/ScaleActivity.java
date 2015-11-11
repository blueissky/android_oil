package com.example.mycaraccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Constants;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;

public class ScaleActivity extends Activity{

    private PieChart mChart;
	private Button[] but_year;
	private int but_flag_year = 0;
	private String but_year_select = "2015";
	private TextView stext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        mChart = (PieChart) findViewById(R.id.scale_chart);
        stext=(TextView)findViewById(R.id.scale_text);
        but_year = new Button[11];
		but_year[0] = (Button) findViewById(R.id.but_2015s);
		but_year[1] = (Button) findViewById(R.id.but_2016s);
		but_year[2] = (Button) findViewById(R.id.but_2017s);
		but_year[3] = (Button) findViewById(R.id.but_2018s);
		but_year[4] = (Button) findViewById(R.id.but_2019s);
		but_year[5] = (Button) findViewById(R.id.but_2020s);
		but_year[6] = (Button) findViewById(R.id.but_2021s);
		but_year[7] = (Button) findViewById(R.id.but_2022s);
		but_year[8] = (Button) findViewById(R.id.but_2023s);
		but_year[9] = (Button) findViewById(R.id.but_2024s);
		but_year[10] = (Button) findViewById(R.id.but_2025s);
		this.initButton();
        this.setChart(mChart);
        this.setData(mChart,Constants.getYear());
        SlideMenu.setActivity(this);
		SlideMenu.initMenu();
    }
/**
 * 2015-11-2 段彬彬  首次创建
 *设置圆显示属性
 */
public void setChart(PieChart mChart){
	 mChart.setUsePercentValues(true);
     mChart.setDescription(null);
     mChart.setExtraOffsets(5, 10, 5, 5);
     mChart.setDragDecelerationFrictionCoef(0.95f);
     mChart.setCenterText(generateCenterSpannableText());
     mChart.setDrawHoleEnabled(true);
     mChart.setHoleColorTransparent(true);
//     mChart.setTransparentCircleColor(Color.rgb(0x08,0x08,0x08));
     mChart.setTransparentCircleAlpha(150);
     mChart.setHoleRadius(42f);
     mChart.setTransparentCircleRadius(31f);
     mChart.setDrawCenterText(true);
     mChart.setRotationAngle(0);
     mChart.setRotationEnabled(true);//设置是否可以转动
     mChart.setHighlightPerTapEnabled(true);
     // add a selection listener
     mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
     Legend l = mChart.getLegend();
     l.setPosition(LegendPosition.RIGHT_OF_CHART);
     l.setXEntrySpace(7f);
     l.setYEntrySpace(2f);
     l.setYOffset(2f);
}
/**
 * 放数据
 * TODO
 * @Author	段彬彬  void
 * @Date	2015-11-2
 * 更新日志
 * 2015-11-2 段彬彬  首次创建
 *
 */
    private void setData(PieChart mChart,String year) {
    	HashMap<String, String> map = CommonDao.getDao().queryScaleReport(this,year);
    	float p0=Float.valueOf(map.get("percent_0"));
    	float p1=Float.valueOf(map.get("percent_1"));
    	float p2=Float.valueOf(map.get("percent_2"));
    	float p3=Float.valueOf(map.get("percent_3"));
    	String sum_scale_oil=map.get("sum_scale_oil");
    	String sum_scale_road=map.get("sum_scale_road");
    	String sum_scale_repair=map.get("sum_scale_repair");
    	String sum_scale_other=map.get("sum_scale_other");
    	int sum_scale=Integer.parseInt(sum_scale_oil)
    			+Integer.parseInt(sum_scale_road)
    			+Integer.parseInt(sum_scale_repair)
    			+Integer.parseInt(sum_scale_other);
    	stext.setText("油费："+sum_scale_oil+"元"
    			+"\n过路费："+sum_scale_road+"元"
    			+"\n保养费："+sum_scale_repair+"元"
    			+"\n配件杂项："+sum_scale_other+"元"
    			+"\n合计："+sum_scale+"元");
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(p0, 0));
        yVals1.add(new Entry(p1, 1));
        yVals1.add(new Entry(p2, 2));
        yVals1.add(new Entry(p3, 3));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("油费");//油费
        xVals.add("过路费");//过路费
        xVals.add("保养费");//保养费
        xVals.add("配件杂项");//杂项
        xVals.add("");xVals.add("");xVals.add("");xVals.add("");
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(1f);//设置块之间的距离
        dataSet.setSelectionShift(8f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();//设置颜色
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(12f);//设置字体大小颜色
        data.setValueTextColor(Color.GRAY);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
        
    }
/**
 * 2015-11-1 段彬彬  首次创建
 *设置字体
 */
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("");//设置圈中间的字
        s.setSpan(new RelativeSizeSpan(2.7f), 0, s.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
        return s;
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
					mChart.refreshDrawableState();//刷新
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