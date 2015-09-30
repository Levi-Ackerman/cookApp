package com.example.cookapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.cookapp.GetDataThread.ThreadCallback;
import com.example.cookapp.adapter.FoodShowAdapter;
import com.example.cookapp.adapter.SearchFoodAdapter;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.bean.SearchFoodEntity;
import com.example.cookapp.util.DialogControl;
import com.example.cookapp.util.RequestType;
import com.example.cookapp.util.RequestUrl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFoodActivity extends Activity {

	private GetDataThread<SearchFoodEntity> getdataThread;
	private ListView listView;
	private Context context;
	private SearchFoodAdapter searchFoodAdapter;
	private int flg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.search_food);
		
		this.context = this;
		flg=0;

		listView = (ListView)findViewById(R.id.search_list);
		getdataThread = new GetDataThread<SearchFoodEntity>();
		final EditText text = (EditText)findViewById(R.id.search_text);
		Button btn = (Button)findViewById(R.id.search_button);
		final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String sText = text.getText().toString();
				imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
				
				DialogControl.startLoding(context,"正在请求数据请稍候...");
				
				String query = null;
				try {
					query = URLEncoder.encode(sText, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String url = RequestUrl.searchFoodUrl + "/?keyword=" + query;
				
				getdataThread.setCallBackListen(RequestType.SEARCHFOOD, url, new ThreadCallback<SearchFoodEntity>(){

					@Override
					public void ThreadLoaded(List<SearchFoodEntity> list) {
						// TODO Auto-generated method stub
						if(flg == 0)
						{
							if(list.size() !=0)
							{
								searchFoodAdapter = new SearchFoodAdapter(context,list,listView);
								listView.setAdapter(searchFoodAdapter);
								flg = 1;
							}
						
						}else
						{
							searchFoodAdapter.update(list);
							searchFoodAdapter.notifyDataSetChanged();
						}
						DialogControl.stopLoging();
					}
					
				});
			}
	
		});
		

		
	}
}
