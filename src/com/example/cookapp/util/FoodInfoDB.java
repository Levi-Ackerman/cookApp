package com.example.cookapp.util;

import java.util.ArrayList;
import java.util.List;

import com.example.cookapp.bean.FoodCookDetailEntity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class FoodInfoDB {
	private DBHelper helper;
	
	public FoodInfoDB(Context context)
	{
		helper = new DBHelper(context);
	}
	
	
	public FoodCookDetailEntity searchFoodInfo(int id)
	{
		int FoodId = 0;
		String FoodName = "";
		String FoodImg = "";
		String FoodTag = "";
		String FoodFood = "";
		String FoodMessage = "";
		int FoodCount = 0;
		
		FoodCookDetailEntity info = new FoodCookDetailEntity();

		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor c = db.rawQuery("select * from foodinfo where id=?",new String[] { id + "" });
		
		if(c.moveToFirst())
		{
	
			FoodId = c.getInt(c.getColumnIndex("id"));
			FoodName = c.getString(c.getColumnIndex("name"));
			FoodImg = c.getString(c.getColumnIndex("img"));;
			FoodTag = c.getString(c.getColumnIndex("tag"));;
			FoodFood = c.getString(c.getColumnIndex("food"));;
			FoodMessage = c.getString(c.getColumnIndex("message"));;
			FoodCount = c.getInt(c.getColumnIndex("count"));;
			
			info.setFoodId(FoodId);
			info.setFoodName(FoodName);
			info.setFoodImg(FoodImg);
			info.setFoodTag(FoodTag);
			info.setFoodFood(FoodFood);
			info.setFoodMessage(FoodMessage);
			info.setFoodCount(FoodCount);

		}else
		{
			c.close();
			db.close();
			return null;
		}
		c.close();
		db.close();
		
		return info;
	}
	
	
	public boolean delFoodInfo(int id)
	{
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "delete from foodinfo where id=?";
		try
		{
			db.execSQL(sql,new Object[]{id});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}
	
	
	
	public List<FoodCookDetailEntity> getAllFoodInfo()
	{
		int FoodId = 0;
		String FoodName = "";
		String FoodImg = "";
		String FoodTag = "";
		String FoodFood = "";
		String FoodMessage = "";
		int FoodCount = 0;
		Cursor c=null;
	
		List<FoodCookDetailEntity> list = new ArrayList<FoodCookDetailEntity>();
		
		SQLiteDatabase db = helper.getReadableDatabase();
		
		try
		{
			c = db.rawQuery("select * from foodinfo",null);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			db.close();
			return null;
		}
		
		while(c.moveToNext())
		{
			FoodCookDetailEntity info = new FoodCookDetailEntity();
			
			FoodId = c.getInt(c.getColumnIndex("id"));
			FoodName = c.getString(c.getColumnIndex("name"));
			FoodImg = c.getString(c.getColumnIndex("img"));;
			FoodTag = c.getString(c.getColumnIndex("tag"));;
			FoodFood = c.getString(c.getColumnIndex("food"));;
			FoodMessage = c.getString(c.getColumnIndex("message"));;
			FoodCount = c.getInt(c.getColumnIndex("count"));;
			
			info.setFoodId(FoodId);
			info.setFoodName(FoodName);
			info.setFoodImg(FoodImg);
			info.setFoodTag(FoodTag);
			info.setFoodFood(FoodFood);
			info.setFoodMessage(FoodMessage);
			info.setFoodCount(FoodCount);
			
			list.add(info);
		}
		c.close();
		db.close();
		return list;
	}
	
	public boolean addFoodInfo(FoodCookDetailEntity info)
	{

		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "insert into foodinfo (id,name,img,tag,food,message,count) values (?,?,?,?,?,?,?)";
		try
		{
			db.execSQL(sql, new Object[] {info.getFoodId(),info.getFoodName(),info.getFoodImg(),info.getFoodTag(),info.getFoodFood(),info.getFoodMessage(),info.getFoodCount()});
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}
	
	public void modifyFoodInfo()
	{
		
	}

	
	
}
