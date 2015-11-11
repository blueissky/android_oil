package com.example.slidemenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mycaraccount.DetailActivity;
import com.example.mycaraccount.MainActivity;
import com.example.mycaraccount.MenuActivity;
import com.example.mycaraccount.MonthReportActivity;
import com.example.mycaraccount.OtherActivity;
import com.example.mycaraccount.R;
import com.example.mycaraccount.ScaleActivity;
import com.example.mycaraccount.YearReportActivity;
import com.slidingmenu.lib.SlidingMenu;

/**
 * 使用第三方侧边滑动菜单,傻瓜包
 */
public class SlideMenu {
	private static SlidingMenu menu;
	private static Activity activity;

	public static void setActivity(Context context) {
		activity = (Activity) context;
	}
	public static SlidingMenu getMenu(Context context){
		if(menu==null){
			menu = new SlidingMenu(context);
		}
		return menu;
	}
	@SuppressLint("NewApi")
	public static void initMenu() {
		menu = new SlidingMenu(activity);
		menu.setMode(SlidingMenu.LEFT);// 设置右滑菜单
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
//		 menu.setShadowDrawable(R.drawable.caidan);//设置阴影图片
//		 menu.setShadowWidthRes(R.dimen.activity_horizontal_margin);//设置阴影图片的宽度
		menu.setBehindWidth(300);// 设置SlidingMenu菜单的宽度
		menu.setFadeDegree(0.85f);// SlidingMenu滑动时的渐变程度
		menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);// 使SlidingMenu附加在Activity上
		menu.setMenu(R.layout.caidan);// 设置menu的布局文件
//		menu.setBackgroundColor(Color.rgb(0x1F, 0x1F, 0x1F));//设置背景颜色
		// menu.toggle();// 动态判断自动关闭或开启SlidingMenu
//		 menu.showMenu();// 显示SlidingMenu
		// menu.showContent();// 显示内容
		// 初始化菜单上的内容组件
		final Button menuOil = (Button) activity.findViewById(R.id.menu_oil);
		Button menuOther = (Button) activity.findViewById(R.id.menu_other);
		Button menuYearReport = (Button) activity.findViewById(R.id.menu_yearReport);
		Button meunMonthReport = (Button) activity.findViewById(R.id.menu_monthReport);
		Button menuScaleReport = (Button) activity.findViewById(R.id.menu_scaleReport);
		Button menuChargeDeatil = (Button) activity.findViewById(R.id.menu_chargeDetail);
		ImageView image_userlogo = (ImageView)activity.findViewById(R.id.image_userlogo);
		menuOil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// menu.toggle(true);
//				GradientDrawable drawable = new GradientDrawable();  
//				drawable.setShape(GradientDrawable.RECTANGLE); // 画框  
//				drawable.setStroke(1, Color.BLUE); // 边框粗细及颜色  
//				drawable.setColor(0x22FFFF00); // 边框内部颜色  
//				Drawable dr = (Drawable)drawable;
//				v.findViewById(R.id.menu_oil).setBackground(dr);
				activity.startActivity(new Intent(activity,MainActivity.class));
//				activity.finish();
			}
		});
		image_userlogo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.startActivity(new Intent(activity,MenuActivity.class));
				activity.overridePendingTransition(R.anim.youjin,R.anim.zuochu);
			}
		});
		menuOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.startActivity(new Intent(activity,OtherActivity.class));
//				activity.finish();
			}
		});
		menuYearReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.startActivity(new Intent(activity,YearReportActivity.class));
//				activity.finish();
			}
		});
		meunMonthReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.startActivity(new Intent(activity,MonthReportActivity.class));
//				activity.finish();
			}
		});
		menuChargeDeatil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.startActivity(new Intent(activity,DetailActivity.class));
//				activity.finish();
			}
		});
		menuScaleReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				SaveSqllite save=new SaveSqllite();
//				save.copyDBToSDcrad();
				activity.startActivity(new Intent(activity,ScaleActivity.class));
//				activity.finish();
			}
		});
	}
}
