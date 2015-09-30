package com.example.cookapp;

import java.util.List;

import com.example.cookapp.GetDataThread.ThreadCallback;
import com.example.cookapp.adapter.FoodShowAdapter;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.util.DialogControl;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.RequestUrl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;


public class FoodShowActivity extends Activity {

	private GetDataThread<AllFoodEntity> getdataThread;
	private ListView listView;
	private FoodShowAdapter foodShowAdapter;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.food_show);
		int id = this.getIntent().getExtras().getInt("classifyid");
		this.context = this;
		DialogControl.startLoding(this,"正在请求数据请稍候...");
		listView = (ListView)findViewById(R.id.foodshowListView);
		
		getdataThread = new GetDataThread<AllFoodEntity>();
		
		String url = RequestUrl.foodListUrl + "/?id=" + Integer.toString(id);
		
		getdataThread.setCallBackListen(RequestType.ALLFOOD, url, new ThreadCallback<AllFoodEntity>(){

			@Override
			public void ThreadLoaded(List<AllFoodEntity> list) {
				// TODO Auto-generated method stub
				if(list.size() != 0)
				{
					foodShowAdapter =new FoodShowAdapter(context,list,listView);
					listView.setAdapter(foodShowAdapter);
				}
				
				DialogControl.stopLoging();
			}
			
		});
		
		
	}
}
