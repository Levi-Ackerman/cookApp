package com.example.cookapp;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;

public class WlecomeActivity extends Activity {

	public Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// »•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.wlecome);
		RelativeLayout re = (RelativeLayout)findViewById(R.id.welcome);
		
		if(isNetworkAvaliable())
		{
			re.setBackgroundResource(R.drawable.start1);
		}else
		{
			re.setBackgroundResource(R.drawable.start2);
		}
		
		mHandler=new Handler();
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				goLoginActivity();
				finish();
			}
			
		},2000);
	}
	
	private void goLoginActivity()
	{
		Intent intent = new Intent(this,TabShowActivity.class);
		startActivity(intent);
	}
	
	public boolean isNetworkAvaliable()
	{
		try
		{
			ConnectivityManager  connect=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	
			if(connect !=null)
			{
				NetworkInfo info=connect.getActiveNetworkInfo();
				if(info!=null && info.isConnected())
				{
					if(info.getState()==NetworkInfo.State.CONNECTED)
					{
						return true;
					}else
					{
						return false;
					}
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
