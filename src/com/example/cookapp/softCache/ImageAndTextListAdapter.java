package com.example.cookapp.softCache;

import java.util.List;

import com.example.cookapp.R;


import com.example.cookapp.bean.AllFoodEntity;
import com.example.cookapp.softCache.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAndTextListAdapter extends ArrayAdapter<AllFoodEntity>{

	private GridView gridView;
	private AsyncImageLoader asyncImageLoader;
	
	public ImageAndTextListAdapter(Activity activity,List<AllFoodEntity> imageAndTexts, GridView gridView) {
		super(activity, 0, imageAndTexts);
		this.gridView = gridView;
		asyncImageLoader = new AsyncImageLoader();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		View rowView = convertView;
		ViewCache viewCache;
	
		if(rowView==null)
		{
			LayoutInflater inflater=activity.getLayoutInflater();
			rowView=inflater.inflate(R.layout.griditem, null);
			viewCache = new ViewCache(rowView);
			rowView.setTag(viewCache);
		}else
		{
			viewCache = (ViewCache) rowView.getTag();
		}
		
		AllFoodEntity imageAndText = getItem(position);
		String imageUrl = imageAndText.getFoodImg();
		ImageView imageView = viewCache.getImageView();
		imageView.setTag(imageUrl);
		
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback(){

			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);
				if (imageViewByTag != null) {
					imageViewByTag.setImageDrawable(imageDrawable);
				}
			}
			
		});
		
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.empty_photo);
		} else {
			imageView.setImageDrawable(cachedImage);
		}
		
		TextView textView = viewCache.getTextView();
		textView.setText(imageAndText.getFoodName());
		
		return rowView;
	}

	
}
