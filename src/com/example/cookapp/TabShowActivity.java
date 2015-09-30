package com.example.cookapp;



import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class TabShowActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.tabshow);
	    final TabHost tabHost = this.getTabHost();
	    tabHost.setup();

	    Intent intent1 = new Intent(this,FoodClassifyActivity.class);
	    Intent intent2 = new Intent(this,MyGridActivity.class);
	    Intent intent3 = new Intent(this,SearchFoodActivity.class);
	    Intent intent4 = new Intent(this,FoodLocalAcivity.class);
	    
	    tabHost.addTab(tabHost.newTabSpec("分类").setIndicator("分类",null) .setContent(intent1)); 
	    tabHost.addTab(tabHost.newTabSpec("全部").setIndicator("全部",null) .setContent(intent2)); 
	    tabHost.addTab(tabHost.newTabSpec("搜索").setIndicator("搜索",null) .setContent(intent3)); 
	    tabHost.addTab(tabHost.newTabSpec("收藏").setIndicator("收藏",null) .setContent(intent4)); 	
	    
	}
}
