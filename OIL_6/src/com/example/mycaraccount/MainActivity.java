package com.example.mycaraccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Common;
import com.example.util.Constants;
import com.example.util.DecimalArithmetic;
import com.example.vo.Oil;

@SuppressLint("NewApi")
public class MainActivity extends Activity{

	private EditText litre;
	private EditText cost;
	private EditText kilo;
	private Button butoil;
	private EditText sale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		butoil=(Button)findViewById(R.id.but_upload_oil);
		litre=(EditText)findViewById(R.id.litre);
		cost=(EditText)findViewById(R.id.cost);
		kilo=(EditText)findViewById(R.id.kilo);
		sale=(EditText)findViewById(R.id.cur_sale_oil);
		TextView curtime = (TextView)findViewById(R.id.curtime_oil);
		curtime.setText("日期："+Constants.gettime());
		SharedPreferences sp=getSharedPreferences("sale", MODE_PRIVATE);
		String val=sp.getString("num", "No Number!");
		sale.setText(val);
		this.costFocuse();
		SlideMenu.setActivity(this);
		SlideMenu.initMenu();
	}
	public void costFocuse(){
		cost.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean bool) {
				if(!bool){//根据当前油价自动算出实际加油数量
					try {
						String s=sale.getText().toString();
						String c=cost.getText().toString();
						String l =String.valueOf(DecimalArithmetic.setDivide(Double.valueOf(c),Double.valueOf(s),Common.Scale_2));
						litre.setText(l);
						SharedPreferences sp=getSharedPreferences("sale", MODE_PRIVATE);
						sp.edit().putString("num",s).commit();
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		sale.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean bool) {
				if(!bool){//根据当前油价自动算出实际加油数量
					try {
						String s=sale.getText().toString();
						String c=cost.getText().toString();
						String l =String.valueOf(DecimalArithmetic.setDivide(Double.valueOf(c),Double.valueOf(s),Common.Scale_2));
						litre.setText(l);
						SharedPreferences sp=getSharedPreferences("sale", MODE_PRIVATE);
						sp.edit().putString("num",s).commit();
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		litre.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean bool) {
				if(!bool){//根据当前油价自动算出实际加油数量
					try {
						String s=sale.getText().toString();
						String c=cost.getText().toString();
						String l =String.valueOf(DecimalArithmetic.setDivide(Double.valueOf(c),Double.valueOf(s),Common.Scale_2));
						litre.setText(l);
						SharedPreferences sp=getSharedPreferences("sale", MODE_PRIVATE);
						sp.edit().putString("num",s).commit();
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		kilo.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean bool) {
				if(!bool){//根据当前油价自动算出实际加油数量
					try {
						String s=sale.getText().toString();
						String c=cost.getText().toString();
						String l =String.valueOf(DecimalArithmetic.setDivide(Double.valueOf(c),Double.valueOf(s),Common.Scale_2));
						litre.setText(l);
						SharedPreferences sp=getSharedPreferences("sale", MODE_PRIVATE);
						sp.edit().putString("num",s).commit();
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
	}
	public void saveoil(View view){
		String c=cost.getText().toString();
		String l=litre.getText().toString();
		String k=kilo.getText().toString();
		if(l.equals("")||c.equals("")||k.equals("")){
			Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
			return;
		}
		Oil oil=new Oil();
		try {
			oil.setCost(Integer.parseInt(c));
			oil.setKilo(Integer.parseInt(k));
			oil.setLitre(Double.parseDouble(l));
			oil.setSystime(Constants.gettime());
			this.confirm(oil);
		} catch (NumberFormatException e) {
			Toast.makeText(this,"输入异常，请输入金额！",Toast.LENGTH_SHORT).show();
			return;
		}
	}
	public void confirm(final Oil oil){
		 AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)  
         .setIcon(android.R.drawable.ic_dialog_alert)  
         .setTitle("信息确认!")  
         .setMessage("升数："+oil.getLitre()+"\n"+
        		 	 "价格："+oil.getCost()+"\n"+
        		 	 "公里："+oil.getKilo()+"\n")
         .setPositiveButton("确定",new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				CommonDao.getDao().saveOil(MainActivity.this,oil);
				litre.setText(null);
				cost.setText(null);
				kilo.setText(null);
				sale.setText(null);//油价
				litre.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
				imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS); //强制隐藏键盘
				startActivity(new Intent(MainActivity.this,DetailActivity.class));
				finish();
			}
		})  
         .setNegativeButton("取消",new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		}).create();  
		dialog.show();  
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.main_report) {
			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)  
	        .setIcon(android.R.drawable.ic_dialog_alert)  
	        .setTitle("说明书!")
	        .setMessage("油价：\n当前油价。\n\n"+
	        "价格：\n加油费用。\n\n"+
	        "加油：\n当月录入的加油数据。\n\n"+
	        "公里数：\n仪表台当前公里数。\n")
	        .create();  
			dialog.show();  
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
}
