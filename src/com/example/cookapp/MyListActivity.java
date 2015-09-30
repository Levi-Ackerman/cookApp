package com.example.cookapp;

import java.util.ArrayList;
import java.util.List;

import com.example.cookapp.GetDataThread.ThreadCallback;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.imgcache.ImageAndTextListAdapter;
import com.example.cookapp.util.DialogControl;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.RequestUrl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListView;

public class MyListActivity extends Activity {
	/** Called when the activity is first created. */
	
	private GetDataThread<AllFoodEntity> getdataThread;
	private ListView listView;
	private MenuInflater mi;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.list_main);
		listView = (ListView) findViewById(R.id.listview);
		mi=new MenuInflater(this);
		DialogControl.startLoding(this,"���������������Ժ�...");
		String url = RequestUrl.allFoodListUrl;
		
		getdataThread=new GetDataThread<AllFoodEntity>();
		getdataThread.setCallBackListen(RequestType.ALLFOOD,url,new ThreadCallback<AllFoodEntity>(){   //�������Ϊ��һ��ʵ���˵ķ�����ȥ
			@Override
			public void ThreadLoaded(List<AllFoodEntity> list) {
				// TODO Auto-generated method stub
				if(list.size() != 0)
				{
					listView.setAdapter(new ImageAndTextListAdapter(MyListActivity.this, list, null,listView));
				}
				DialogControl.stopLoging();
			} 
	    });
	    
		//getdataThread.start();
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		mi.inflate(R.menu.menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu)
	{
		switch(menu.getItemId())
		{
		case R.id.changemenu:
			showAnother();
		break;
		
		case R.id.exit:
			finish();
		break;
		default:break;
		}
		
		return true;
	}
	
	private void showAnother()
	{
		
		Intent intent=new Intent(this,MyGridActivity.class);
		startActivity(intent);
		//finish();
	}
	
	
}

