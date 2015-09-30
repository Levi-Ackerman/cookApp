package com.example.cookapp;

import java.util.List;

import com.example.cookapp.GetDataThread.ThreadCallback;
import com.example.cookapp.adapter.FoodLocalShowAdapter;
import com.example.cookapp.adapter.FoodShowAdapter;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.bean.FoodCookDetailEntity;
import com.example.cookapp.imgcache.ImageManager;
import com.example.cookapp.util.DialogControl;
import com.example.cookapp.util.FoodInfoDB;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.RequestUrl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodCookDetailActivity extends Activity{

	private GetDataThread<FoodCookDetailEntity> getdataThread;
	private Context context;
	private ImageManager imageManager;
	private MenuInflater mi;
	private FoodInfoDB foodDB;
	private FoodCookDetailEntity saveToDBInfo;
	private MyApplication application;
	private FoodLocalShowAdapter foodLocalShowAdapter;
	float x, y, ux, uy;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.food_cook_detail);
		int id = this.getIntent().getExtras().getInt("foodcookdetail");
		this.context = this;
		application=(MyApplication)this.getApplicationContext();
		DialogControl.startLoding(this,"正在请求数据请稍候...");
		ScrollView scView = (ScrollView)this.findViewById(R.id.scrollview);
		
		
		scView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
		        switch (event.getAction()) 
		        {  
		            case MotionEvent.ACTION_DOWN:  
		                x = event.getX();  
		                y = event.getY();  
		            case MotionEvent.ACTION_UP:  
		                ux = event.getX();  
		                uy = event.getY();  
		                
			            if (Math.abs(x - ux) > 50 && Math.abs(y - uy) < 30) {  
			                finish();
			            } 
		        }  
				
				return false;
			}
			
			
		});
		
		
		imageManager =new ImageManager();
		getdataThread = new GetDataThread<FoodCookDetailEntity>();
		
		String url = RequestUrl.foodDetailUrl + "/?id=" + Integer.toString(id);
		
		foodDB = new FoodInfoDB(this);
		
		mi=new MenuInflater(this);
		
		FoodCookDetailEntity info = foodDB.searchFoodInfo(id);
		
		if(info == null)
		{
			getdataThread.setCallBackListen(RequestType.FOODDETAIL, url, new ThreadCallback<FoodCookDetailEntity>(){
	
				@Override
				public void ThreadLoaded(List<FoodCookDetailEntity> list) {
					// TODO Auto-generated method stub
					if(list == null)
					{
						DialogControl.stopLoging();
						return;
					}
					FoodCookDetailEntity foodCookDetail = list.get(0);
					exec(foodCookDetail);
				}
				
			});
			
		}else
		{
			exec(info);
		}
		
	}
	
	
	public void exec(FoodCookDetailEntity foodCookDetail)
	{
		TextView textInfo = (TextView)findViewById(R.id.foodDetailInfo);
		ImageView imageView = (ImageView)findViewById(R.id.foodDetailImage);
		
		
		
		saveToDBInfo = foodCookDetail;
		
		Bitmap bitmap = imageManager.getBitmapFromNative(foodCookDetail.getFoodImg());
		
		if(bitmap != null)
		{
			imageView.setImageBitmap(bitmap);
		}else
		{
			imageView.setImageResource(R.drawable.empty_photo);
		}
		
		String foodName = foodCookDetail.getFoodName();
		String foodIntroduce = foodCookDetail.getFoodTag();
		String foodCook = foodCookDetail.getFoodMessage();
		
		String str ="<h2>菜名</h2><p>"+foodName+"</p><br/>"+"<h2>标注</h2>\n<p>"+foodIntroduce+"</p><br/>"+foodCook;
		textInfo.setText(Html.fromHtml(str));
		DialogControl.stopLoging();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		mi.inflate(R.menu.addemnu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu)
	{
		switch(menu.getItemId())
		{
		case R.id.addMenu:addToDB();break;
		default:break;
		}
		
		return true;
	}
	
	public void addToDB()
	{
		if(saveToDBInfo != null)
		{
			if(foodDB.searchFoodInfo(saveToDBInfo.getFoodId()) == null)
			{
				if(foodDB.addFoodInfo(saveToDBInfo))
				{
					Toast.makeText(context,"收藏成功", 0).show();
				}else
				{
					Toast.makeText(context,"收藏失败", 0).show();
				}
				
				List<FoodCookDetailEntity> foodList = foodDB.getAllFoodInfo();
				
				if(application.getLoaclAdapter() !=null)
				{
					application.getLoaclAdapter().update(foodList);
					application.getLoaclAdapter().setIndex(-1);
					application.getLoaclAdapter().notifyDataSetChanged();
				}
				
			}else
			{
				Toast.makeText(context,"收藏中已存在该菜谱！", 0).show();
			}
			
		}
		
		
	}

	
}
