package com.example.cookapp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.cookapp.FoodCookDetailActivity;
import com.example.cookapp.FoodShowActivity;
import com.example.cookapp.R;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.bean.SearchFoodEntity;
import com.example.cookapp.imgcache.ImageManager;
import com.example.cookapp.imgcache.ImageManager.ImageCallback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class SearchFoodAdapter extends BaseAdapter{

	private  List<SearchFoodEntity> foodList;
	private Context context;
	private LayoutInflater inflater;
	private ImageManager imageManager;
    private int mFirstVisibleItem;//GridView中可见的第一张图片的下标
    private int mVisibleItemCount;//GridView中可见的图片的数量
    private boolean isFirstEnterThisActivity = true;//记录是否是第一次进入该界面
    private ListView listView;
    private ScrollListenerImpl scrollerListen;
	public SearchFoodAdapter(Context context,List<SearchFoodEntity> foodList,ListView listView)
	{
		this.context = context;
		this.foodList = foodList;
		this.listView = listView;
		inflater = LayoutInflater.from(context);
		imageManager = new ImageManager();
		scrollerListen =new ScrollListenerImpl(foodList);
		listView.setOnScrollListener(scrollerListen);
		
	}
	
	public void update(List<SearchFoodEntity> foodList)
	{
		this.foodList = foodList;
		scrollerListen.update(foodList);
		isFirstEnterThisActivity=true;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return foodList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return foodList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder vHolder;
		if(convertView == null)
		{
			vHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_food_item, null);
			vHolder.imageView = (ImageView)convertView.findViewById(R.id.searchFoodShowImg);
			vHolder.textFoodName = (TextView)convertView.findViewById(R.id.searchFoodName);
			convertView.setTag(vHolder);
		}else
		{
			vHolder = (ViewHolder)convertView.getTag();
		}
		
		SearchFoodEntity foodInfo = foodList.get(position);
		vHolder.textFoodName.setText(Html.fromHtml(foodInfo.getFoodName()));
		
		String imageUrl = foodInfo.getFoodImg();
		
		vHolder.imageView.setTag(imageUrl);
		
		Bitmap bitmap=imageManager.getBitmap(imageUrl);
		
		if(bitmap == null)
		{
			vHolder.imageView.setImageResource(R.drawable.empty_photo);
		}else
		{
			vHolder.imageView.setImageBitmap(bitmap);
		}
		
		final int id = foodInfo.getFoodId();
		
		convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,FoodCookDetailActivity.class);
				
				intent.putExtra("foodcookdetail", id);
				
				context.startActivity(intent);
			}
			
		});
		
		
		return convertView;
	}
	
	class ScrollListenerImpl implements OnScrollListener 
	{
		private List<SearchFoodEntity> info;

		public ScrollListenerImpl(List<SearchFoodEntity> info)
		{
			this.info=info;
		}
		
		public void update(List<SearchFoodEntity> info)
		{
			this.info = info;
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
	
	
	
	
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount,List<SearchFoodEntity> info) {
		 for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++)
		 {
			 	 SearchFoodEntity entity=info.get(i);
				 String imageUrl=entity.getFoodImg();
				 Bitmap bitmap=imageManager.getBitmap(imageUrl);
				 if (bitmap == null) {
					 Bitmap bmp=imageManager.loadDrawable(imageUrl, new ImageCallback(){
						@Override
						public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
							ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
							if (imageViewByTag != null && imageDrawable != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					 });
				 } else 
				 {
						ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
						if (imageViewByTag != null && bitmap!= null) {
							imageViewByTag.setImageBitmap(bitmap);
						}
				 }
				 
		 }
	}
	
	
	
	static class ViewHolder
	{
		ImageView imageView;
		TextView textFoodName;
	}

}