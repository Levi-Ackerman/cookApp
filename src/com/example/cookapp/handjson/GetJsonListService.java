package com.example.cookapp.handjson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.bean.FoodClassifyEntity;
import com.example.cookapp.bean.FoodCookDetailEntity;
import com.example.cookapp.bean.SearchFoodEntity;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.StreamTool;

public class GetJsonListService<T>{

	public  List<T> getJsonList(String path,RequestType type) throws Exception
	{
		
		HttpURLConnection conn=(HttpURLConnection)new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		
		if(conn.getResponseCode()==200)
		{
			InputStream json=conn.getInputStream();
			
			return parseJSON(json,type);
		}
		
		return null;
	}
	
	private  List<T> parseJSON(InputStream jsonStream,RequestType type) throws Exception
	{
		List<T> list=new ArrayList<T>();
		switch(type)
		{
			case ALLFOOD:
				list=getAllFoodJson(jsonStream);
			break;
			case FOODCLASSIFY:
				list=getFoodClassifyJson(jsonStream);
			break;
			case SECONDCLASSIFY:
				list=getSecondFoodClassifyJson(jsonStream);
			break;
			case FOODDETAIL:
				list=getFoodCookDetailJson(jsonStream);
			break;
			case SEARCHFOOD:list=getSearchFoodJson(jsonStream);break;
			default:break;
		}
		
		return list;
	}
	
	private  List<T> getAllFoodJson(InputStream jsonStream) throws Exception
	{
		List<T> list=new ArrayList<T>();
		
		byte[]data=StreamTool.read(jsonStream);
		String json=new String(data);
		
		//JSONArray jsonArray=new JSONArray(json);
		JSONObject jsonObj = new JSONObject(json);
		
		
		JSONArray jsonArray=jsonObj.getJSONArray("yi18");
		
		for(int i=0;i<jsonArray.length();i++)
		{
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			String FoodName=jsonObject.getString("name");
			String FoodImg=jsonObject.getString("img");
			String FoodTag=jsonObject.getString("tag");
			String FoodFood=jsonObject.getString("food");
			int FoodCount=jsonObject.getInt("count");
			int FoodId=jsonObject.getInt("id");
			list.add((T) new AllFoodEntity(FoodName,FoodImg,FoodTag,FoodFood,FoodCount,FoodId));
		}
		return list;
	}
	
	private  List<T> getFoodClassifyJson(InputStream jsonStream)  throws Exception
	{
			List<T> list=new ArrayList<T>();
			byte[]data = StreamTool.read(jsonStream);
			String json = new String(data);
			
			JSONObject jsonObj =new JSONObject(json);
			
			JSONArray jsonArray =jsonObj.getJSONArray("yi18");
			
			for(int i = 0;i<jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String FoodClassitfyName = jsonObject.getString("name");
				int FoodCookClass = jsonObject.getInt("cookclass");
				int FoodClassitfyId = jsonObject.getInt("id");
				list.add((T)new FoodClassifyEntity(FoodClassitfyName,FoodCookClass,FoodClassitfyId));
			}
			return list;
	}
	
	private  List<T> getSecondFoodClassifyJson(InputStream jsonStream)  throws Exception
	{
		return getFoodClassifyJson(jsonStream);
	}
	
	private  List<T> getFoodCookDetailJson(InputStream jsonStream)  throws Exception
	{
		List<T> list=new ArrayList<T>();
		
		byte[]data=StreamTool.read(jsonStream);
		String json=new String(data);
		
		//JSONArray jsonArray=new JSONArray(json);
		JSONObject jsonObj = new JSONObject(json);
		
		
		JSONObject jsonObject=jsonObj.getJSONObject("yi18");
	
		String FoodName=jsonObject.getString("name");
		String FoodImg=jsonObject.getString("img");
		String FoodTag=jsonObject.getString("tag");
		String FoodFood=jsonObject.getString("food");
		int FoodCount=jsonObject.getInt("count");
		int FoodId=jsonObject.getInt("id");
		String FoodMessage=jsonObject.getString("message");
		
		list.add((T) new FoodCookDetailEntity(FoodName,FoodImg,FoodTag,FoodFood,FoodCount,FoodId,FoodMessage));
		
		return list;
	}
	
	
	private  List<T> getSearchFoodJson(InputStream jsonStream)  throws Exception
	{
			List<T> list=new ArrayList<T>();
			byte[]data = StreamTool.read(jsonStream);
			String json = new String(data);
			
			JSONObject jsonObj =new JSONObject(json);
			
			JSONArray jsonArray =jsonObj.getJSONArray("yi18");
			
			for(int i = 0;i<jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String searchName = jsonObject.getString("name");
				int searchId = jsonObject.getInt("id");
				String  searchImg = jsonObject.getString("img");
				String  searchType = jsonObject.getString("type");
				list.add((T)new SearchFoodEntity(searchName,searchImg,searchId,searchType));
			}
			return list;
	}
	
	
	
}
