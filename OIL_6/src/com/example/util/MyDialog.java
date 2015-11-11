package com.example.util;

import java.util.ArrayList;
import java.util.Map;

import com.example.dao.CommonDao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.SimpleAdapter;

public class MyDialog {
	private static MyDialog dialog;
	public static MyDialog getDialog(){
		if(dialog==null){
			dialog=new MyDialog();
		}
		return dialog;
	}
	public void deleteData(final Context context,final ArrayList<Map<String, String>> data,final int position,final SimpleAdapter adapter,final int flag,final String text) {
		AlertDialog dialog = new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle("提示!")
				.setMessage("确定删除所选内容?")
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if(flag==Constants.OIL){
							CommonDao.getDao().deleteOil(context, text);
						}else if(flag==Constants.OTHER){
							CommonDao.getDao().deleteOther(context, text);
						}
						data.remove(position);
						adapter.notifyDataSetChanged();
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).create();
		dialog.show();
	}
}
