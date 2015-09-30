package com.example.cookapp.bean;


//{"name":"青花椒炖童子鸡","img":"img/cook/000068141.jpg","tag":"鸡肉,鸡肉类,家常小炒","food":"童子鸡,青花椒,小土豆,酱油,料酒,红糖,水淀粉","count":278,"id":68141}
public class AllFoodEntity {
	
	private String FoodName;
	private String FoodImg;
	private String FoodTag;
	private String FoodFood;
	private int FoodCount;
	private int FoodId;
	
	
	public AllFoodEntity()
	{
		
	}
	
	public AllFoodEntity(String FoodName,String FoodImg,String FoodTag,String FoodFood,int FoodCount,int FoodId)
	{
		this.FoodName=FoodName;
		//this.FoodImg=FoodImg;
		this.FoodFood=FoodFood;
		this.FoodTag = FoodTag;
		this.FoodCount=FoodCount;
		this.FoodId=FoodId;
		
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


}
