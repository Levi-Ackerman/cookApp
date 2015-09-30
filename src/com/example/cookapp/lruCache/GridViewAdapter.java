package com.example.cookapp.lruCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookapp.R;
import com.example.cookapp.bean.AllFoodEntity;

@SuppressLint("NewApi")
public class GridViewAdapter extends ArrayAdapter<AllFoodEntity>{
	
	private GridView mGridView;
	private LruCache<String,Bitmap> mLruCache;  //ͼƬ������
	private HashSet<DownloadBitmapAsyncTask> mDownloadBitmapAsyncTaskHashSet;//��¼�����������ػ�ȴ����ص�����
    private int mFirstVisibleItem;//GridView�пɼ��ĵ�һ��ͼƬ���±�
    private int mVisibleItemCount;//GridView�пɼ���ͼƬ������
    private boolean isFirstEnterThisActivity = true;//��¼�Ƿ��ǵ�һ�ν���ý���
	private LayoutInflater inflater;
	
	public GridViewAdapter(Context context, int textViewResourceId,List<AllFoodEntity> objects, GridView gridView) {
        super(context, textViewResourceId, objects);
        this.mGridView=gridView;
        mGridView.setOnScrollListener(new ScrollListenerImpl(objects));
        mDownloadBitmapAsyncTaskHashSet = new HashSet<DownloadBitmapAsyncTask>();
        int maxMemory=(int)Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/6;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
            	 if (bitmap != null)  
                     return bitmap.getByteCount();  
                 else  
                     return 0;  
            }
        };
        inflater = LayoutInflater.from(context);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		AllFoodEntity info=getItem(position); 
		ViewHolder holder;
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.griditem, null);
			holder=new ViewHolder();
			holder.mImage=(ImageView)convertView.findViewById(R.id.image);
			holder.mText=(TextView)convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}else
		{
			holder=(ViewHolder)convertView.getTag();
		}
	
		setImageForImageView(info,holder);
		return convertView;
	}
	
	
	
	 private void setImageForImageView(AllFoodEntity info, ViewHolder holder) {
		 
		 Bitmap bitmap = getBitmapFromLruCache(info.getFoodImg());
		 
		 if(bitmap==null)
		 {
			 holder.mImage.setImageResource(R.drawable.empty_photo);
		 }else
		 {
			 holder.mImage.setImageBitmap(bitmap);
		 }
		 holder.mText.setText(info.getFoodName());
		 
		 holder.mImage.setTag(info.getFoodImg());
		 holder.mText.setTag(info.getFoodImg());
	 }
	
	 
	   /**
	     * ��ͼƬ�洢��LruCache
	     */
	    public void addBitmapToLruCache(String key, Bitmap bitmap) {
	        if (getBitmapFromLruCache(key) == null) {
	            mLruCache.put(key, bitmap);
	        }
	    }
	 
	    /**
	     * ��LruCache�����ȡͼƬ
	     */
	    public Bitmap getBitmapFromLruCache(String key) {
	        return mLruCache.get(key);
	    }
	 
	 
	 
	 private void loadBitmaps(int firstVisibleItem, int visibleItemCount,List<AllFoodEntity> info) {
	      
		 for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++)
		 {
			 AllFoodEntity entity=info.get(i);
			 String imageUrl=entity.getFoodImg();
			 Bitmap bitmap=getBitmapFromLruCache(imageUrl);
			 if(bitmap==null)
			 {
				 DownloadBitmapAsyncTask downloadBitmapAsyncTask = new DownloadBitmapAsyncTask();
				 mDownloadBitmapAsyncTaskHashSet.add(downloadBitmapAsyncTask);
				 downloadBitmapAsyncTask.execute(imageUrl);
			 }else
			 {
				 //����Tag�ҵ���Ӧ��ImageView��ʾͼƬ
                 ImageView imageView = (ImageView) mGridView.findViewWithTag(imageUrl);
                 if (imageView != null && bitmap != null) {
                     imageView.setImageBitmap(bitmap);
                 }
			 }
			 
		 }
	 
	 }
	 
	 /**
	     * ȡ�������������ػ�ȴ����ص�����
	     */
    public void cancelAllTasks() {
        if (mDownloadBitmapAsyncTaskHashSet != null) {
            for (DownloadBitmapAsyncTask task : mDownloadBitmapAsyncTaskHashSet) {
                task.cancel(false);
            }
        }
    }
	 

	class ScrollListenerImpl implements OnScrollListener 
	{
		private List<AllFoodEntity> info;

		public ScrollListenerImpl(List<AllFoodEntity> info)
		{
			this.info=info;
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			mFirstVisibleItem=firstVisibleItem;
			mVisibleItemCount=visibleItemCount;
			if(isFirstEnterThisActivity && visibleItemCount>0)
			{
				loadBitmaps(firstVisibleItem, visibleItemCount,info);
				isFirstEnterThisActivity=false;
			}
		}
		
		/**
         *  GridViewֹͣ����ʱ����ͼƬ
         *  ���������ȡ�������������ػ��ߵȴ����ص�����
         */
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
    		if(scrollState==SCROLL_STATE_IDLE)
    		{
    			loadBitmaps(mFirstVisibleItem, mVisibleItemCount,info);
    		}else
    		{
    			cancelAllTasks();
    		}
        }
		
	}
	
	static class ViewHolder
	{
		public ImageView mImage;
		public TextView mText;
	}
	
	
	
    class DownloadBitmapAsyncTask extends AsyncTask<String,Void,Bitmap> {
    	
    		private String imageUrl;
			@Override
			protected Bitmap doInBackground(String... params) {
				imageUrl=params[0];
				Bitmap bitmap=downloadBitmap(imageUrl);
				if(bitmap!=null)
				{
					addBitmapToLruCache(String.valueOf(params[0]), bitmap);
				}
				
				return bitmap;
			}
			
			  @Override
		      protected void onPostExecute(Bitmap bitmap) {
				  super.onPostExecute(bitmap);
				  ImageView imageView = (ImageView) mGridView.findViewWithTag(imageUrl);
				  if (imageView != null && bitmap != null) {
		                imageView.setImageBitmap(bitmap);
		          }
				  mDownloadBitmapAsyncTaskHashSet.remove(this);
			  }
		          
			
			 // ��ȡBitmap
		    private Bitmap downloadBitmap(String imageUrl) {
		    	Bitmap bitmap = null;
		        HttpURLConnection httpURLConnection = null;
		        try {
		            URL url = new URL(imageUrl);
		            httpURLConnection = (HttpURLConnection) url.openConnection();
		            httpURLConnection.setConnectTimeout(5 * 1000);
		            httpURLConnection.setReadTimeout(10 * 1000);
		            httpURLConnection.setRequestMethod("GET");
		            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
		        } catch (Exception e) {
		            e.printStackTrace(); 
		        } finally {
		            if (httpURLConnection != null) {
		                httpURLConnection.disconnect();
		            }
		        }
		        return bitmap;
		    }
		    
    }


}
