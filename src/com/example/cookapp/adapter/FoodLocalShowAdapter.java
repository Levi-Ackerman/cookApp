package com.example.cookapp.adapter;

import java.util.List;

import com.example.cookapp.FoodCookDetailActivity;
import com.example.cookapp.FoodShowActivity;
import com.example.cookapp.R;
import com.example.cookapp.adapter.SearchFoodAdapter.ScrollListenerImpl;
import com.example.cookapp.bean.FoodCookDetailEntity;
import com.example.cookapp.bean.SearchFoodEntity;
import com.example.cookapp.imgcache.ImageManager;
import com.example.cookapp.imgcache.ImageManager.ImageCallback;
import com.example.cookapp.util.FoodInfoDB;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class FoodLocalShowAdapter extends BaseAdapter{

	private List<FoodCookDetailEntity> foodList;
	private Context context;
	private LayoutInflater inflater;
	private ImageManager imageManager;
    private int mFirstVisibleItem;//GridView中可见的第一张图片的下标
    private int mVisibleItemCount;//GridView中可见的图片的数量
    private boolean isFirstEnterThisActivity = true;//记录是否是第一次进入该界面
    private ListView listView;
    private ScrollListenerImpl scrollerListen;
    
	private int index;
	
	private TranslateAnimation mShowAction;
	private TranslateAnimation mHiddenAction;
	
	
	private FoodInfoDB foodDB;
	
	
	public FoodLocalShowAdapter(Context context,List<FoodCookDetailEntity> foodList,ListView listView)
	{
		this.context = context;
		this.foodList = foodList;
		this.listView = listView;
		inflater = LayoutInflater.from(context);
		imageManager = new ImageManager();
		scrollerListen =new ScrollListenerImpl(foodList);
		listView.setOnScrollListener(scrollerListen);
		
		foodDB = new FoodInfoDB(context);
		
		index = -1;
		
		 mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,     
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,     
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);     
        mShowAction.setDuration(500);   
        
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,     
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,     
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,     
                -1.0f);    
        mHiddenAction.setDuration(500);     
	}
	
	public void update(List<FoodCookDetailEntity> foodList)
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
	public View getView(final int position, View convertView, ViewGroup parent) {
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
			vHolder.delBtn = (Button)convertView.findViewById(R.id.delBtn);
			convertView.setTag(vHolder);
		}else
		{
			vHolder = (ViewHolder)convertView.getTag();
		}
		
		FoodCookDetailEntity foodInfo = foodList.get(position);
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
		
		
		 if(position==index){  
             if(vHolder.delBtn.getVisibility()==View.VISIBLE){  
            	 vHolder.delBtn.setVisibility(View.GONE);  
            	 vHolder.delBtn.startAnimation(mHiddenAction);
             }else{  
            	 vHolder.delBtn.setVisibility(View.VISIBLE);  
            	 vHolder.delBtn.startAnimation(mShowAction);
             }  
		 }else
		 {
			 vHolder.delBtn.setVisibility(View.GONE);  
		 }
		
		/*convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,FoodCookDetailActivity.class);
				
				intent.putExtra("foodcookdetail", id);
				
				context.startActivity(intent);
			}
			
		});*/
		
		
		vHolder.delBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					vHolder.delBtn.setVisibility(View.GONE);  //点击删除按钮后，影藏按钮 
					//v.startAnimation(mHiddenAction);
					if(foodDB.delFoodInfo(id))
					{
						foodList.remove(foodList.remove(position));
						Toast.makeText(context, "删除成功", 0).show();
						notifyDataSetChanged(); 
						index = -1;
					}else
					{
						Toast.makeText(context, "删除失败", 0).show();
					}

			}
			
			
		});
		
		return convertView;
	}
	
	
	
	
	class ScrollListenerImpl implements OnScrollListener 
	{
		private List<FoodCookDetailEntity> info;

		public ScrollListenerImpl(List<FoodCookDetailEntity> info)
		{
			this.info=info;
		}
		
		public void update(List<FoodCookDetailEntity> info)
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
	
	
	
	
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount,List<FoodCookDetailEntity> info) {
		 for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++)
		 {
			 	 FoodCookDetailEntity entity=info.get(i);
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
		Button delBtn;
	}
	
	
	public View.OnTouchListener onTouchListener = new View.OnTouchListener() {  
        float x, y, ux, uy;  
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
	                index = ((ListView)v).pointToPosition((int) x, (int) y);//item的position  
	                int p2 = ((ListView)v).pointToPosition((int) ux, (int) uy);  
	                
	                String st = "index:"+String.valueOf(index) +"  p2:" + String.valueOf(p2);
	                Log.d("testmsg", st);
	                
	                if (index == p2 && Math.abs(x - ux) > 20) {  
	                    notifyDataSetChanged();  
	                } 
            }  
  
            return false;  
        }  
  
    }; 
    
    public void setIndex(int index)
    {
    	this.index = index;
    }
    

}
