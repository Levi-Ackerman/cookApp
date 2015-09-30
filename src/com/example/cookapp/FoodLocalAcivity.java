package com.example.cookapp;

import java.util.List;

import com.example.cookapp.adapter.FoodLocalShowAdapter;
import com.example.cookapp.bean.FoodCookDetailEntity;
import com.example.cookapp.util.FoodInfoDB;









import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class FoodLocalAcivity extends Activity{

	private FoodLocalShowAdapter foodLocalShowAdapter;
	private ListView listView;
	private MyApplication application;
	private Context context;
	private List<FoodCookDetailEntity> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.food_show);
		
		application=(MyApplication)this.getApplicationContext();
		final FoodInfoDB db = new FoodInfoDB(this);
		listView = (ListView)findViewById(R.id.foodshowListView);
		list = db.getAllFoodInfo();
		if(list == null)
		{
			Toast.makeText(this,"获取收藏食谱失败", 0).show();
			return;
		}
		context = this;
		foodLocalShowAdapter = new FoodLocalShowAdapter(context,list,listView);
		application.setLoaclAdapter(foodLocalShowAdapter);
		listView.setOnTouchListener(foodLocalShowAdapter.onTouchListener);
		listView.setAdapter(foodLocalShowAdapter);
		
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,FoodCookDetailActivity.class);
				list = db.getAllFoodInfo();
				int foodid = list.get(position).getFoodId();
				
				intent.putExtra("foodcookdetail", foodid);
				
				context.startActivity(intent);
			}
			
		});
		
		
	}
}
