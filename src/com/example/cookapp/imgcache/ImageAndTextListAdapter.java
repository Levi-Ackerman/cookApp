package com.example.cookapp.imgcache;

import java.util.List;

import com.example.cookapp.FoodCookDetailActivity;
import com.example.cookapp.R;



import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.imgcache.ImageManager.ImageCallback;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class ImageAndTextListAdapter extends ArrayAdapter<AllFoodEntity>{

	private GridView gridView;
	private ListView listView;
	private ImageManager imageManager;
	private int LayoutId;
    private int mFirstVisibleItem;//GridView中可见的第一张图片的下标
    private int mVisibleItemCount;//GridView中可见的图片的数量
    private boolean isFirstEnterThisActivity = true;//记录是否是第一次进入该界面
    
    
	public ImageAndTextListAdapter(Activity activity,List<AllFoodEntity> imageAndTexts, GridView gridView,ListView listView) {
		super(activity, 0, imageAndTexts);
		this.gridView = gridView;
		this.listView = listView;
		if(gridView != null)
		{
			gridView.setOnScrollListener(new ScrollListenerImpl(imageAndTexts));
		}else if(listView != null)
		{
			listView.setOnScrollListener(new ScrollListenerImpl(imageAndTexts));
		}
        if(gridView!=null)
		{
			LayoutId=R.layout.griditem;
		}else if(listView!=null)
		{
			LayoutId=R.layout.listitem;
		}
		imageManager = new ImageManager();
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		View rowView = convertView;
		ViewCache viewCache;
	
		if(rowView==null)
		{
			LayoutInflater inflater=activity.getLayoutInflater();
			rowView=inflater.inflate(LayoutId, null);
			viewCache = new ViewCache(rowView);
			rowView.setTag(viewCache);
		}else
		{
			viewCache = (ViewCache) rowView.getTag();
		}
		
		AllFoodEntity imageAndText = getItem(position);
		String imageUrl = imageAndText.getFoodImg();
		ImageView imageView = viewCache.getImageView();
		TextView textView = viewCache.getTextView();
		imageView.setTag(imageUrl);

		Bitmap bitmap=imageManager.getBitmap(imageUrl);
		
		if (bitmap == null) {
			imageView.setImageResource(R.drawable.empty_photo);
		}else
		{
			imageView.setImageBitmap(bitmap);
		}
		textView.setText(imageAndText.getFoodName());
		
		final int id = imageAndText.getFoodId();
		
		rowView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getContext(),FoodCookDetailActivity.class);
				
				intent.putExtra("foodcookdetail", id);
				
				getContext().startActivity(intent);
			}
			
		});
		return rowView;
	}
	
	
	
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount,List<AllFoodEntity> info) {
				
		 for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++)
		 {
			 
			 AllFoodEntity entity=info.get(i);
			 String imageUrl=entity.getFoodImg();
		
			 Bitmap bitmap=imageManager.getBitmap(imageUrl);
				
			 if (bitmap == null) {
				
				 Bitmap bmp=imageManager.loadDrawable(imageUrl, new ImageCallback(){
		
					@Override
					public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
						// TODO Auto-generated method stub
						if(gridView!=null)
						{
							ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);
							if (imageViewByTag != null && imageDrawable != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}else if(listView!=null)
						{
							ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
							if (imageViewByTag != null  && imageDrawable != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
						
					}
				});
				 
			}else
			{
				if(gridView!=null)
				{
					ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);
					if (imageViewByTag != null && bitmap!= null) {
						imageViewByTag.setImageBitmap(bitmap);
					}
				}else if(listView!=null)
				{
					ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
					if (imageViewByTag != null && bitmap!= null) {
						imageViewByTag.setImageBitmap(bitmap);
					}
				}
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
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if(scrollState==SCROLL_STATE_IDLE)
    		{
    			loadBitmaps(mFirstVisibleItem, mVisibleItemCount,info);
    		}else
    		{
    			imageManager.waitingdownload.clear();
    		}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			mFirstVisibleItem=firstVisibleItem;
			mVisibleItemCount=visibleItemCount;
			if(isFirstEnterThisActivity && visibleItemCount>0)
			{
				loadBitmaps(firstVisibleItem, visibleItemCount,info);
				isFirstEnterThisActivity=false;
			}
		}
	}
		
}

	
