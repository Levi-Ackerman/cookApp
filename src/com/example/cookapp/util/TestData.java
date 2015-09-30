package com.example.cookapp.util;

import java.util.ArrayList;
import java.util.List;

import com.example.cookapp.bean.AllFoodEntity;

public class TestData {

	public static List<AllFoodEntity> getTestDataList()
	{
		List<AllFoodEntity> list=new ArrayList<AllFoodEntity>();
		for(int i=0;i<ImagesUrl.Urls.length;i++)
		{
			String FoodName=ImagesUrl.title[i];
			String FoodImg=ImagesUrl.Urls[i];
			String FoodTag="";
			String FoodFood="";
			int FoodCount=0;
			int FoodId=1;
			list.add(new AllFoodEntity(FoodName,FoodImg,FoodTag,FoodFood,FoodCount,FoodId));
		}
		return list;
	}
}
