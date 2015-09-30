package com.example.cookapp.bean;

public class FoodCookDetailEntity {
	
	private String FoodName;
	private String FoodImg;
	private String FoodTag;
	private String FoodFood;
	private int FoodCount;
	private int FoodId;
	private String FoodMessage;
	
	
	public FoodCookDetailEntity()
	{
		
	}
	
	public FoodCookDetailEntity(String FoodName,String FoodImg,String FoodTag,String FoodFood,int FoodCount,int FoodId,String FoodMessage)
	{
		this.FoodName=FoodName;
		//this.FoodImg=FoodImg;
		this.FoodFood=FoodFood;
		this.FoodTag = FoodTag;
		this.FoodCount=FoodCount;
		this.FoodId=FoodId;
		this.FoodMessage = FoodMessage;
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
	
	public void setFoodFood(String FoodFood)
	{
		this.FoodFood=FoodFood;
	}
	
	public String getFoodFood()
	{
		return FoodFood;
	}
	
	
	public void setFoodTag(String FoodTag)
	{
		this.FoodTag=FoodTag;
	}
	
	public String getFoodTag()
	{
		return FoodTag;
	}
	
	public void setFoodCount(int FoodCount)
	{
		this.FoodCount=FoodCount;
	}
	
	public int getFoodCount()
	{
		return FoodCount;
	}
	
	public void setFoodId(int FoodId)
	{
		this.FoodId=FoodId;
	}
	
	public int getFoodId()
	{
		return FoodId;
	}
	
	
	public void setFoodMessage(String FoodMessage)
	{
		this.FoodMessage=FoodMessage;
	}
	
	public String getFoodMessage()
	{
		return FoodMessage;
	}


}
