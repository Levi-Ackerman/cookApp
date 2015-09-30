package com.example.cookapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.cookapp.GetDataThread.ThreadCallback;
import com.example.cookapp.adapter.FoodClassifyAdapter;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.bean.FoodClassifyEntity;
import com.example.cookapp.handjson.GetJsonListService;
import com.example.cookapp.imgcache.ImageAndTextListAdapter;
import com.example.cookapp.util.DialogControl;
import com.example.cookapp.util.DialogFactory;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.RequestUrl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class FoodClassifyActivity extends Activity {

	private GetDataThread<FoodClassifyEntity> getdataThread;
	private ExpandableListView exListView;
	private Context context;
	private List<FoodClassifyEntity> listFoodClassifyList;
	private List<List<FoodClassifyEntity>> listSecondFoodClassify;
	private HashMap<Integer,List<FoodClassifyEntity>> mapSecondFoodClassify;
	private FoodClassifyAdapter foodClassifyAdapter;
	private int flgCount;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.main_food_classify);
		exListView = (ExpandableListView)findViewById(R.id.ecpandable);
		context = this;
		flgCount = 0;
		
		DialogControl.startLoding(this,"正在请求数据请稍候...");
		
		listSecondFoodClassify =new ArrayList<List<FoodClassifyEntity>>();
		mapSecondFoodClassify = new HashMap<Integer,List<FoodClassifyEntity>>();
		
		String url = RequestUrl.foodClassifyUrl;
		
		getdataThread = new GetDataThread<FoodClassifyEntity>();
		
		getdataThread.setCallBackListen(RequestType.FOODCLASSIFY,url,new ThreadCallback<FoodClassifyEntity>(){   //可以理解为传一个实现了的方法过去
				@Override
				public void ThreadLoaded(List<FoodClassifyEntity> list) {
				
					listFoodClassifyList = list;
					for(int i = 0;i < list.size();i++)
					{
						FoodClassifyEntity foodClassify = list.get(i);
						getSecondClassifyInfo(foodClassify.getFoodClassitfyId(),i,list.size());
					}
					
				} 
		    });

	}
	

	
	public void getSecondClassifyInfo(int id,final int nowCount,final int totalCount)     // 此处之前产生bug调式了半天，原因是handler不对应造成的。
	{
		//此处之前的nowCount之所以一直为一个值，是因为setCallBackListen里面将new ThreadCallback产生的对象赋值给getdataThread对象的成员变量了，所以每次回调时都是用的最后一次new ThreadCallback产生的对象
		//所以之前new ThreadCallback产生的对象都不能回调到。每一个回调函数要对应一个新的handler,若所有回调函数对应一个handler，则每个回调函数中的数据都一样。。final变量会在回调函数中产生一个副本。
		
		String url = RequestUrl.foodClassifyUrl + "/?id=" + Integer.toString(id);
		getdataThread.setCallBackListen(RequestType.SECONDCLASSIFY,url,new ThreadCallback<FoodClassifyEntity>(){
			@Override
			public void ThreadLoaded(List<FoodClassifyEntity> list) {
				//listSecondFoodClassify.add(list);
	
				mapSecondFoodClassify.put(nowCount, list);
				flgCount++;
				int m = nowCount;
				if(flgCount == totalCount)
				{
					foodClassifyAdapter = new FoodClassifyAdapter(context,listFoodClassifyList,mapSecondFoodClassify);
					exListView.setAdapter(foodClassifyAdapter);
					DialogControl.stopLoging();
			
				}
				
			}
		});
	}
	

	
}
