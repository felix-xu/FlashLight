package com.xyf.flashlight;

import android.content.Context;
import android.widget.Toast;

public class myToast {
	Context mContext;
	Toast mToast;
	
	public myToast(Context context){
		mContext=context;
		mToast=Toast.makeText(context, "", Toast.LENGTH_SHORT);
		mToast.setGravity(16, 0, 320);
	}
	
	public void show(int id,int duration){
		show(mContext.getText(id),duration);
	}
	
	public void show(CharSequence s,int duration){
		mToast.setDuration(duration);
		mToast.setText(s);
		mToast.show();
	}
	
	public void cancel(){
		mToast.cancel();
	}
}
