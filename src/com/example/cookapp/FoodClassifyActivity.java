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
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.main_food_classify);
		exListView = (ExpandableListView)findViewById(R.id.ecpandable);
		context = this;
		flgCount = 0;
		
		DialogControl.startLoding(this,"���������������Ժ�...");
		
		listSecondFoodClassify =new ArrayList<List<FoodClassifyEntity>>();
		mapSecondFoodClassify = new HashMap<Integer,List<FoodClassifyEntity>>();
		
		String url = RequestUrl.foodClassifyUrl;
		
		getdataThread = new GetDataThread<FoodClassifyEntity>();
		
		getdataThread.setCallBackListen(RequestType.FOODCLASSIFY,url,new ThreadCallback<FoodClassifyEntity>(){   //�������Ϊ��һ��ʵ���˵ķ�����ȥ
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
	

	
	public void getSecondClassifyInfo(int id,final int nowCount,final int totalCount)     // �˴�֮ǰ����bug��ʽ�˰��죬ԭ����handler����Ӧ��ɵġ�
	{
		//�˴�֮ǰ��nowCount֮����һֱΪһ��ֵ������ΪsetCallBackListen���潫new ThreadCallback�����Ķ���ֵ��getdataThread����ĳ�Ա�����ˣ�����ÿ�λص�ʱ�����õ����һ��new ThreadCallback�����Ķ���
		//����֮ǰnew ThreadCallback�����Ķ��󶼲��ܻص�����ÿһ���ص�����Ҫ��Ӧһ���µ�handler,�����лص�������Ӧһ��handler����ÿ���ص������е����ݶ�һ������final�������ڻص������в���һ��������
		
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
