package com.example.mycaraccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.CommonDao;
import com.example.slidemenu.SlideMenu;
import com.example.util.Constants;
import com.example.vo.Other;

public class OtherActivity extends Activity {


private RadioGroup radioGroup;
private EditText cost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		radioGroup=(RadioGroup)findViewById(R.id.radio_group);
		cost=(EditText)findViewById(R.id.editText_otherscost);
		TextView curtime = (TextView)findViewById(R.id.curtime_other);
		curtime.setText("日期："+Constants.gettime());
		SlideMenu.setActivity(this);
		SlideMenu.initMenu();
	}

	public void saveother(View v){
		String othercost=cost.getText().toString();
		if(othercost.equals("")){
			Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
			return;
		}
		int id=this.radioGroup.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton)findViewById(id);
		String othername=radioButton.getText().toString();//费用名称
		int costvalue=0;//获取费用
		try{
			costvalue=Integer.parseInt(othercost);
		}catch(Exception e){
			Toast.makeText(this,"输入异常，请输入金额！",Toast.LENGTH_SHORT).show();
			return;
		}
		Other other=new Other();
		other.setName(othername);
		other.setCost(costvalue);
		other.setSystime(Constants.gettime());
		this.confirm(other);
	}
	
	public void confirm(final Other other){
		 AlertDialog dialog = new AlertDialog.Builder(OtherActivity.this)  
        .setIcon(android.R.drawable.ic_dialog_alert)  
        .setTitle("信息确认!")  
        .setMessage("名称："+other.getName()+"\n"+
        		    "费用："+other.getCost()+"\n")
        .setPositiveButton("确定",new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				CommonDao.getDao().saveOther(OtherActivity.this, other);
				cost.setText(null);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
				imm.hideSoftInputFromWindow(OtherActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS); //强制隐藏键盘  
				startActivity(new Intent(OtherActivity.this,DetailActivity.class));
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
		protected void onRestart() {
		super.onRestart();
		}
}
