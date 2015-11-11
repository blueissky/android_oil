package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

	public class DBHelper extends SQLiteOpenHelper {
		private static DBHelper helper;
		public static DBHelper getDBHelper(Context context){
			if(helper==null){
				helper=new DBHelper(context);
			}
			return helper;
		}
		public DBHelper(Context context) {
			super(context,Constants.DB_NAME,null,Constants.VERSION);
		}
		/**
		 * 回调函数，第一次创建数据库时使用。
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(Constants.DB_OLI);
			db.execSQL(Constants.DB_OTHER);
//			10-12 09:35:19.279: E/初始化(2217): /data/data/com.example.mycaraccount/databases/OIL.db

		}
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			
		}
}
