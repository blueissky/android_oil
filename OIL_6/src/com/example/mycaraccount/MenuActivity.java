package com.example.mycaraccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.slidemenu.SlideMenu;

public class MenuActivity extends Activity {

	private Button mc;
	private Button mm;
	private Button moi;
	private Button mot;
	private Button md;
	private Button my;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		mc=(Button)findViewById(R.id.menu_chargeDetail_2);
		mm=(Button)findViewById(R.id.menu_monthReport_2);
		moi=(Button)findViewById(R.id.menu_oil_2);
		mot=(Button)findViewById(R.id.menu_other_2);
		md=(Button)findViewById(R.id.menu_scaleReport_2);
		my=(Button)findViewById(R.id.menu_yearReport_2);
		this.init();
//		SlideMenu.getMenu(this);
//		SlideMenu.initMenu();
	}
	public void init(){
		mc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this,DetailActivity.class));
			}
		});
		mm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this,MonthReportActivity.class));
				// TODO Auto-generated method stub
			}
		});
		moi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this,MainActivity.class));
				// TODO Auto-generated method stub
			}
		});
		mot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MenuActivity.this,OtherActivity.class));
			}
		});
		md.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MenuActivity.this,ScaleActivity.class));
			}
		});
		my.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MenuActivity.this,YearReportActivity.class));
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_report) {
			AlertDialog dialog = new AlertDialog.Builder(MenuActivity.this)  
	        .setIcon(android.R.drawable.ic_dialog_alert)  
	        .setTitle("说明书!")
	        .setMessage("\n"+
	        "power by duan.\n" +
	        "图片资料来自互联网，感谢无名作者！\n"+"" +
	        "有问题请联系我。\n"+
	        "email:blueissky@outlook.com.\n" +
	        "qq:352606550.\n") 
	        .create();  
			dialog.show();  
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
