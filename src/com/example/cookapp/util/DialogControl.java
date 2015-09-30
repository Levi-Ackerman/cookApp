package com.example.cookapp.util;

import android.app.Dialog;
import android.content.Context;

public class DialogControl {
	
	private static Dialog mDialog = null;

	public static void startLoding(Context context,String showMsg)
	{

		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.createDialogRequest(context, showMsg);
		mDialog.show();
		
	}
	
	public static void stopLoging()
	{
		if(mDialog.isShowing())
		{
			mDialog.dismiss();
		}
	}

}
