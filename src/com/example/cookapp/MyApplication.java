package com.example.cookapp;

import com.example.cookapp.adapter.FoodLocalShowAdapter;


import android.app.Application;


public class MyApplication extends Application{
	
	private FoodLocalShowAdapter foodLocalShowAdapter;

	@Override
	public void onCreate()
	{		
		super.onCreate();
	}

	public void setLoaclAdapter(FoodLocalShowAdapter foodLocalShowAdapter)
	{
		this.foodLocalShowAdapter = foodLocalShowAdapter;
	}

	public FoodLocalShowAdapter getLoaclAdapter()
	{
		return foodLocalShowAdapter;
	}
	
}
