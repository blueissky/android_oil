package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Environment;
import android.util.Log;

public class SaveSqllite {
	public void copyDBToSDcrad()
	{
	    String DATABASE_NAME = Constants.DB_NAME;
	    String oldPath = "/data/data/com.example.mycaraccount/databases/" + DATABASE_NAME;
	    String newPath =Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+DATABASE_NAME;
	     Log.e("保存",newPath);
	    copyFile(oldPath, newPath);
	}
	 
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径
	 * @param newPath
	 *            String 复制后路径
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath)
	{
	    try
	    {
	        File oldfile = new File(oldPath);
	        File newfile = new File(newPath);
            InputStream in = new FileInputStream(oldPath); // 读入原文件
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1)
            {
                fs.write(buffer, 0, byteread);
            }
            fs.flush();
            fs.close();
            in.close();
	    }
	    catch (Exception e)
	    {
	    	Log.e("数据库", "出错了");
	        e.printStackTrace();
	    }
	 
	}
	public static void copyFile(){
		String path=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"xx.txt";
		File file = new File(path);
		FileOutputStream write;
		try {
			write = new FileOutputStream(file);
			write.write(12);
			write.close();
		} catch (Exception e) {
		}
		
	}
}
