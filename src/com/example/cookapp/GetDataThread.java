package com.example.cookapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.cookapp.handjson.GetJsonListService;
import com.example.cookapp.util.RequestUrl;
import com.example.cookapp.util.RequestType;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;



 public class GetDataThread<T>
 {
	private List<T> foodList;
	public ExecutorService mThreadPool = null;

	
	public interface ThreadCallback<T>
	{
		public void ThreadLoaded(List<T> list);
	}
	

	public void setCallBackListen(RequestType type,String path,final ThreadCallback<T> threadCallback)
	{
		   final  Handler handler=new Handler()
			{
				public void handleMessage(Message message)
				{
			
					List<T> gridInfoList=(List<T>) message.obj;
					threadCallback.ThreadLoaded(gridInfoList);
				}
			};
			
			start(handler,type,path);
	}
	

	public void getAllFoodDataInfo(Handler handler,final RequestType type,final String path)
	 {
		foodList=new ArrayList<T>();
		try {
			GetJsonListService<T> json =new GetJsonListService<T>();
			switch(type)
			{
			case ALLFOOD:foodList=json.getJsonList(path,RequestType.ALLFOOD);break;
			case FOODCLASSIFY:foodList=json.getJsonList(path,RequestType.FOODCLASSIFY);break;
			case SECONDCLASSIFY:foodList=json.getJsonList(path,RequestType.SECONDCLASSIFY);break;
			case FOODDETAIL:foodList=json.getJsonList(path,RequestType.FOODDETAIL);break;
			case SEARCHFOOD:foodList=json.getJsonList(path,RequestType.SEARCHFOOD);break;
			default:break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(foodList != null)
		{
			Message message = handler.obtainMessage(0, foodList);
			message.what = type.ordinal();
			handler.sendMessage(message);
		}

	 }
	

	public ExecutorService getThreadPool(){
		if(mThreadPool == null){
			synchronized(ExecutorService.class){
				if(mThreadPool == null){
					//为了下载图片更加的流畅，我们用了2个线程来下载图片
					mThreadPool = Executors.newFixedThreadPool(4);
				}
			}
		}
		
		return mThreadPool;
	}
	
	
	
	
	public void start(final Handler handler,final RequestType type,final String path)
	{
		getThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				getAllFoodDataInfo(handler,type,path);
			}
		});
	}
	
	public synchronized void cancelTask() {
		if(mThreadPool != null){
			mThreadPool.shutdownNow();
			mThreadPool = null;
		}
	}

	
		

}
	 
	 

