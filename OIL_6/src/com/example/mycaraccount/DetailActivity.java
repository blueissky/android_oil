package com.example.mycaraccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Constants;
import com.example.util.MonthMatch;
import com.example.util.MyDialog;
import com.example.vo.Oil;
import com.example.vo.Other;

public class DetailActivity extends Activity {

	private EditText begintext;
	private EditText endText;
	private ListView recordlist;
	private Button[] but_year;
	private Button[] but_month;
	private int but_flag_year = 0;
	private int but_flag_month = 0;
	private String but_year_select = "";
	private String but_month_select = "";
	private String but_month_select_end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		recordlist = (ListView) findViewById(R.id.record_list);
		but_year = new Button[11];
		but_month = new Button[12];
		but_year[0] = (Button) findViewById(R.id.but_2015);
		but_year[1] = (Button) findViewById(R.id.but_2016);
		but_year[2] = (Button) findViewById(R.id.but_2017);
		but_year[3] = (Button) findViewById(R.id.but_2018);
		but_year[4] = (Button) findViewById(R.id.but_2019);
		but_year[5] = (Button) findViewById(R.id.but_2020);
		but_year[6] = (Button) findViewById(R.id.but_2021);
		but_year[7] = (Button) findViewById(R.id.but_2022);
		but_year[8] = (Button) findViewById(R.id.but_2023);
		but_year[9] = (Button) findViewById(R.id.but_2024);
		but_year[10] = (Button) findViewById(R.id.but_2025);

		but_month[0] = (Button) findViewById(R.id.but_01);
		but_month[1] = (Button) findViewById(R.id.but_02);
		but_month[2] = (Button) findViewById(R.id.but_03);
		but_month[3] = (Button) findViewById(R.id.but_04);
		but_month[4] = (Button) findViewById(R.id.but_05);
		but_month[5] = (Button) findViewById(R.id.but_06);
		but_month[6] = (Button) findViewById(R.id.but_07);
		but_month[7] = (Button) findViewById(R.id.but_08);
		but_month[8] = (Button) findViewById(R.id.but_09);
		but_month[9] = (Button) findViewById(R.id.but_10);
		but_month[10] = (Button) findViewById(R.id.but_11);
		but_month[11] = (Button) findViewById(R.id.but_12);
		this.initButton();
		//获取当前月份数据
        String y=Constants.getYear();
        String m=Constants.getMonth();
        String t_start=y+"-"+m+"-01";
        String t_end=y+"-"+m+"-31";
		this.query(t_start, t_end);
		SlideMenu.setActivity(this);
		SlideMenu.initMenu();
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
					Button but = (Button) findViewById(v.getId());
					but_month_select = but.getText().toString();
					HashMap<String, String> map = MonthMatch.get().mothmatch(but_month_select);//月份匹配
					but_month_select=map.get("but_month_select");
					but_month_select_end=map.get("but_month_select_end");
					if(but_year_select==""){//如果年份没有点击那么获取当年的
						but_year_select=Constants.getYear();
					}
					String time_start=but_year_select + but_month_select;//开始时间
					String time_end=but_year_select+but_month_select_end;//结束时间
					query(time_start,time_end);
				}
			});
		}
	}

	/**
	 * 2015-11-6 段彬彬  首次创建
	 * 查询数据库数据
	 */
	public void query(String beginTime,String endTime){
		ArrayList<Oil> oil_list = CommonDao.getDao().queryOil(this, beginTime, endTime);
		ArrayList<Other> other_list = CommonDao.getDao().queryOther(this, beginTime, endTime);
		oil_list(recordlist,oil_list,other_list);
	}
	// 列表
	public void oil_list(ListView list, ArrayList<Oil> oilList,ArrayList<Other> otherlist) {
		final ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
			HashMap<String, String> map = null;
			for (Oil oil : oilList) {
				map = new HashMap<String, String>();
				map.put("time_list", oil.getSystime());
				map.put("oil_list", oil.getLitre() + "升:" + oil.getCost() + "元:"+oil.getKilo()+"公里");
				map.put("hidden_id", Constants.oil_perfix+String.valueOf(oil.getId()));
				data.add(map);
			}	
			map = null;
			for (Other other : otherlist) {
				map = new HashMap<String, String>();
				map.put("time_list", other.getSystime());
				map.put("oil_list", other.getName()+":"+other.getCost());
				map.put("hidden_id", Constants.other_perfix+String.valueOf(other.getId()));
				data.add(map);
			}
		String[] from = new String[] { "time_list", "oil_list","hidden_id"};
		int[] to = new int[] { R.id.time_list, R.id.oil_list ,R.id.hidden_id};
		final SimpleAdapter adapter = new SimpleAdapter(DetailActivity.this, data,
				R.layout.showlist, from, to);
		list.setAdapter(adapter);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				TextView hidden_id = (TextView)view.findViewById(R.id.hidden_id);
				String text=hidden_id.getText().toString();
				int flag=0;
				if(text.startsWith(Constants.oil_perfix)){
					text=text.substring(1);
					flag=Constants.OIL;
				}else if(text.startsWith(Constants.other_perfix)){
					text=text.substring(1);
					flag=Constants.OTHER;
				}
				MyDialog.getDialog().deleteData(DetailActivity.this, data, position, adapter,flag,text);
				return true;
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.report_detail) {
			AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)  
	        .setIcon(android.R.drawable.ic_dialog_alert)  
	        .setTitle("小提示!")
	        .setMessage("\n长按数据可以把不想要的数据删掉！\n")
	        .create();  
			dialog.show();  
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//重新调用执行
	@Override
	protected void onRestart() {
		super.onRestart();
		//获取当前月份数据
        String y=Constants.getYear();
        String m=Constants.getMonth();
        String t_start=y+"-"+m+"-01";
        String t_end=y+"-"+m+"-31";
		this.query(t_start, t_end);
	}
}
