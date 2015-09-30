package com.example.cookapp.bean;

public class FoodClassifyEntity {
	
	private String FoodClassitfyName;
	private int FoodClassitfyId;
	private int FoodCookClass;
	
	public FoodClassifyEntity(String FoodClassitfyName,int FoodCookClass,int FoodClassitfyId)
	{
		this.FoodClassitfyName = FoodClassitfyName;
		this.FoodClassitfyId = FoodClassitfyId;
		this.FoodCookClass = FoodCookClass;
	}
	
	public void setFoodClassitfyName(String FoodClassitfyName)
	{
		this.FoodClassitfyName=FoodClassitfyName;
	}
	
	public String  getFoodClassitfyName()
	{
		return 	FoodClassitfyName;
	}

	
	public void setFoodClassitfyId(int ClassitfyId)
	{
		this.FoodClassitfyId = FoodClassitfyId;
	}
	
	public int getFoodClassitfyId()
	{
		return FoodClassitfyId;
	}
	
	public void setFoodCookClass(int FoodCookClass)
	{
		this.FoodCookClass=FoodCookClass;
	}
	
	public int getFoodCookClass()
	{
		return FoodCookClass;
	}
	
}
