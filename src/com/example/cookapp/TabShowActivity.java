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
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.tabshow);
	    final TabHost tabHost = this.getTabHost();
	    tabHost.setup();

	    Intent intent1 = new Intent(this,FoodClassifyActivity.class);
	    Intent intent2 = new Intent(this,MyGridActivity.class);
	    Intent intent3 = new Intent(this,SearchFoodActivity.class);
	    Intent intent4 = new Intent(this,FoodLocalAcivity.class);
	    
	    tabHost.addTab(tabHost.newTabSpec("����").setIndicator("����",null) .setContent(intent1)); 
	    tabHost.addTab(tabHost.newTabSpec("ȫ��").setIndicator("ȫ��",null) .setContent(intent2)); 
	    tabHost.addTab(tabHost.newTabSpec("����").setIndicator("����",null) .setContent(intent3)); 
	    tabHost.addTab(tabHost.newTabSpec("�ղ�").setIndicator("�ղ�",null) .setContent(intent4)); 	
	    
	}
}
