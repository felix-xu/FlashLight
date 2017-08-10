package com.xyf.flashlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class textAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private int[] color;
	private int[] text;
	
	public textAdapter(Context context,int[] mcolor,int[] mtext){
		mInflater=LayoutInflater.from(context);
		color=mcolor;
		text=mtext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return text[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.changecolor_text, null);
			holder=new ViewHolder();
			holder.mText=(TextView)convertView.findViewById(R.id.colorText);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.mText.setText(text[position]);
		holder.mText.setBackgroundResource(color[position]);
		return convertView;
	}

	private class ViewHolder{
		TextView mText;
	}
}
