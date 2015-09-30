package com.example.cookapp.util;

import com.example.cookapp.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class DialogFactory {
	
	public  static Dialog createDialogRequest(final Context context,String tip)
	{
	
		final Dialog dialog=new Dialog(context,R.style.dialog);
		dialog.setContentView(R.layout.dialog_layout);
		Window window=dialog.getWindow();
		WindowManager.LayoutParams lp=window.getAttributes();
		int width=GetScreenWidth(context);
		lp.width = (int) (0.6 * width);
	
		TextView text=(TextView)dialog.findViewById(R.id.proText);
		
		
		if(tip==null||tip.length()==0)
		{
			text.setText("正在发送请求");
		}else
		{
			text.setText(tip);
		}
		
		return dialog;
	}
	
	public  static int GetScreenWidth(Context context)
	{
		WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display=manager.getDefaultDisplay();
		return display.getWidth();
	}
}
