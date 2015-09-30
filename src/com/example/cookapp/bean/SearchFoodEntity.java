package com.example.cookapp.bean;

public class SearchFoodEntity {
	
	private String FoodName;
	private String FoodImg;
	private int FoodId;
	private String FoodType;
	
	public SearchFoodEntity(String FoodName,String FoodImg,int FoodId,String FoodType)
	{
		this.FoodName = FoodName;
		this.FoodId = FoodId;
		this.FoodType = FoodType;
		
		if(FoodImg.startsWith("http://"))
 		{
 			this.FoodImg=FoodImg;
 		}else
 		{
 			this.FoodImg="http://www.yi18.net/"+FoodImg;
 		}
	}
	
	
	public void setFoodName(String FoodName)
	{
		this.FoodName=FoodName;
	}
	
	public String getFoodName()
	{
		return FoodName;
	}
	
	
	public void setFoodImg(String FoodImg)
	{
 		if(FoodImg.startsWith("http://"))
 		{
 			this.FoodImg=FoodImg;
 		}else
 		{
 			this.FoodImg="http://www.yi18.net/"+FoodImg;
 		}
	}
	
	public String getFoodImg()
	{
		return FoodImg;
	}
	
	
	public void setFoodId(int FoodId)
	{
		this.FoodId=FoodId;
	}
	
	public int getFoodId()
	{
		return FoodId;
	}
	
	
	public void setFoodType(String FoodType)
	{
		this.FoodType=FoodType;
	}
	
	public String getFoodType()
	{
		return FoodType;
	}

}
