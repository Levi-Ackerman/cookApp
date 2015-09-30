package com.example.cookapp.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.cookapp.FoodShowActivity;
import com.example.cookapp.R;
import com.example.cookapp.bean.FoodClassifyEntity;








import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class FoodClassifyAdapter extends BaseExpandableListAdapter{

	private List<FoodClassifyEntity> grouplist;
	private HashMap<Integer,List<FoodClassifyEntity>> childlist;
	private LayoutInflater inflater;
	private Context context;

	public FoodClassifyAdapter(Context context,List<FoodClassifyEntity> grouplist,HashMap<Integer,List<FoodClassifyEntity>> childlist)
	{
		this.grouplist = grouplist;
		this.childlist = childlist;
		this.context = context;
		
		inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return grouplist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub

		return childlist.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return grouplist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childlist.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 TextView title;

         if(convertView == null){
             convertView = inflater.inflate(R.layout.food_classify_item, null);
         }
         title = (TextView) convertView.findViewById(R.id.content_001);
         title.setText(grouplist.get(groupPosition).getFoodClassitfyName());
         return convertView;
	}

	/////////////////convertview ´ý¸Ä
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView title;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.second_food_classify_item, null);
        }
		String name = childlist.get(groupPosition).get(childPosition).getFoodClassitfyName();
        
        title = (TextView) convertView.findViewById(R.id.classifyText);
        title.setText(childlist.get(groupPosition).get(childPosition).getFoodClassitfyName());
     
        final int id = childlist.get(groupPosition).get(childPosition).getFoodClassitfyId();
        		
        convertView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,FoodShowActivity.class);
				
				intent.putExtra("classifyid", id);
				
				context.startActivity(intent);
			}
        	
        	
        });
        
        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
