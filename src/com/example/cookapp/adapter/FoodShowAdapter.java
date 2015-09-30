package com.example.cookapp.adapter;

import java.util.List;

import com.example.cookapp.FoodCookDetailActivity;
import com.example.cookapp.FoodShowActivity;
import com.example.cookapp.R;
import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.imgcache.ImageManager;
import com.example.cookapp.imgcache.ImageManager.ImageCallback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class FoodShowAdapter extends BaseAdapter{

	private List<AllFoodEntity> foodList;
	private Context context;
	private LayoutInflater inflater;
	private ImageManager imageManager;
    private int mFirstVisibleItem;//GridView�пɼ��ĵ�һ��ͼƬ���±�
    private int mVisibleItemCount;//GridView�пɼ���ͼƬ������
    private boolean isFirstEnterThisActivity = true;//��¼�Ƿ��ǵ�һ�ν���ý���
    private ListView listView;
    
	public FoodShowAdapter(Context context,List<AllFoodEntity> foodList,ListView listView)
	{
		this.context = context;
		this.foodList = foodList;
		this.listView = listView;
		inflater = LayoutInflater.from(context);
		imageManager = new ImageManager();
		listView.setOnScrollListener(new ScrollListenerImpl(foodList));
		
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
			convertView = inflater.inflate(R.layout.food_show_item, null);
			vHolder.imageView = (ImageView)convertView.findViewById(R.id.foodShowImg);
			vHolder.textFoodName = (TextView)convertView.findViewById(R.id.foodShowName);
			vHolder.textViewContent = (TextView)convertView.findViewById(R.id.foodContent);
			vHolder.textViewCount = (TextView)convertView.findViewById(R.id.foodCount);
			vHolder.textViewIntroduce = (TextView)convertView.findViewById(R.id.foodIntroduce);
			convertView.setTag(vHolder);
		}else
		{
			vHolder = (ViewHolder)convertView.getTag();
		}
		
		AllFoodEntity foodInfo = foodList.get(position);
		vHolder.textFoodName.setText(foodInfo.getFoodName());
		vHolder.textViewContent.setText(foodInfo.getFoodFood());
		vHolder.textViewCount.setText(Integer.toString(foodInfo.getFoodCount()));
		vHolder.textViewIntroduce.setText(foodInfo.getFoodTag());
		
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
		TextView textViewContent;
		TextView textViewCount;
		TextView textViewIntroduce;
	}

}
