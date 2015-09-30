package com.example.cookapp.imgcache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.cookapp.softCache.AsyncImageLoader.ImageCallback;
import com.example.cookapp.util.StreamTool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ImageManager {
	
	private ImageMemoryCache imageMemoryCache;
	private ImageFileCache imageFileCache;
	
	private static HashMap<String,Handler> ongoingdownload= new HashMap<String,Handler>();
	public static HashMap<String,Handler> waitingdownload= new HashMap<String,Handler>();	
	
	final static int MAX_DOWNLOAD_IMAGE_THREAD = 4;
	
	public ImageManager()
	{
		imageMemoryCache = new ImageMemoryCache();
		imageFileCache = new ImageFileCache();
	}
	
	
	private final Handler downloadStatusHandler= new Handler()
	{
		public void handleMessage(Message msg)
		{
			AddNewDownloadItemToQueue();
		}
	};
	
	
	public Bitmap loadDrawable(final String imageUrl,final ImageCallback imageCallback)
	{
		final  Handler handler=new Handler()
		{
			public void handleMessage(Message message)
			{
				imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
			}
		};
		
		return loadBitmap(imageUrl,handler);
	}
	
	
	public Bitmap loadBitmap(String url,Handler handler)
	{
		Bitmap bitmap = getBitmapFromNative(url);
		
		if(bitmap != null)
		{
			Message msg = new Message().obtain();
			Bundle bundle = new Bundle();
			bundle.putString("url", url);
			msg.obj=bitmap;
			msg.setData(bundle);
			handler.sendMessage(msg);
			return bitmap;
		}else
		{
			startDownloadOnNewThread(url,handler);
		}
		
		return null;
	}
	
	
	
	private void startDownloadOnNewThread(final String url,final Handler handler)
	{
		
		if(ongoingdownload.size()<=MAX_DOWNLOAD_IMAGE_THREAD)
		{
			synchronized(ongoingdownload)
			{
				ongoingdownload.put(url, handler);
				
			}
			
			new Thread()
			{
				public void run()
				{
					Bitmap bitmap = null;
					
					try {
						bitmap = getBitmapFromHttp(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					synchronized(ongoingdownload)
					{
						ongoingdownload.remove(url);
					}
					
					
					Message msg = Message.obtain();
					msg.obj=bitmap;
					Bundle bundle=new Bundle();
					bundle.putString("url",url);
					msg.setData(bundle);
					
					if(downloadStatusHandler  != null)
					{
						downloadStatusHandler.sendEmptyMessage(0);
					}
					
					if(handler != null)
					{
						handler.sendMessage(msg);
					}
					
				}
				
			}.start();
			
		}else
		{
			
			synchronized(waitingdownload)
			{
				waitingdownload.put(url, handler);
				
			}
			
		}
		
	}
	
	private void AddNewDownloadItemToQueue()
	{
		 synchronized(waitingdownload) 
		 {
			 Iterator iter = waitingdownload.entrySet().iterator();
			 while(iter.hasNext())
			 {
				 Map.Entry entry = (Map.Entry) iter.next();
				 
				 if(entry != null)
				 {
					 waitingdownload.remove(entry.getKey());
					 startDownloadOnNewThread((String)entry.getKey(),(Handler)entry.getValue());
				 }
				 break;
			 }
		 }
	}
	
	
	public Bitmap getBitmap(String url)
	{
		Bitmap bitmap = imageMemoryCache.getBitmapFromMemory(url);
		
		if(bitmap == null)
		{
			bitmap = imageFileCache.getImageFromFile(url);
			
			if (bitmap != null)   
            {                 
                imageMemoryCache.addBitmapToMemory(url, bitmap);  
            }
		}
		return bitmap;
	}
	
	
	
	public Bitmap getBitmapFromHttp(String url) throws Exception
	{
		Bitmap bitmap = null;
		HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		
		if(conn.getResponseCode()==200)
		{
			InputStream stream=conn.getInputStream();
			byte[]data=StreamTool.read(stream);
			if(data != null)
			{
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				data=null;
			}
			
			if(bitmap != null)
			{
				imageFileCache.saveBitmapToFile(bitmap, url);
				imageMemoryCache.addBitmapToMemory(url, bitmap);
			}
			
		}
		return bitmap;
	}
	
	
	
    public Bitmap getBitmapFromNative(String url)  
	{
    	Bitmap bitmap = null;
    	bitmap = imageMemoryCache.getBitmapFromMemory(url);
    	
    	if(bitmap == null)
    	{
    		bitmap = imageFileCache.getImageFromFile(url);
    		
    		if(bitmap != null)
    		{
    			imageMemoryCache.addBitmapToMemory(url, bitmap);
    		}
    	}
	    return bitmap;
    }
	
    
    
	public interface ImageCallback
	{
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}

}
