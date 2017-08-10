package com.xyf.flashlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Main extends Activity {

	private Button flash;
	private ImageView menuList;
	private TextView popup_list_data;
	private ListView popupListView;
	private PopupWindow myPopupWindow;
	private List<Map<String,String>> moreList;
	private SimpleAdapter simpleAdapter;
	boolean isOn=false;
	Camera camera;
	private int NUM=3;
	long exitTime;
	myToast toast;
	

	ViewGroup bannerContainer;
	BannerView bv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        super.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        flash=(Button)super.findViewById(R.id.flash);
        popup_list_data=(TextView)super.findViewById(R.id.popup_list_data);
        menuList=(ImageView)super.findViewById(R.id.menu_list);

        bannerContainer = (ViewGroup) this.findViewById(R.id.main_bannerContainer);
        				this.bv = new BannerView(this, ADSize.BANNER, "1105416268", "3020817293234722");
        			    bv.setRefresh(30);
        			    bv.setADListener(new AbstractBannerADListener() {

        			      @Override
        			      public void onNoAD(int arg0) {
        			      }

        			      @Override
        			      public void onADReceiv() {
        			      }
        			    });
        			    bannerContainer.addView(bv);
        			    bv.loadAD();
        			    
        toast=new myToast(this);
        flash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flashOn();
			}
		});
        initMenuData();
        initPopupWindow();
        menuList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(myPopupWindow.isShowing()){
					myPopupWindow.dismiss();
				}else{
					myPopupWindow.showAsDropDown(menuList);
				}
			}
		});
        showInterAd();
    }

    private void showInterAd(){
    	final InterstitialAD iad = new InterstitialAD(this, "1105416268", "6060512263233733");
	    iad.setADListener(new AbstractInterstitialADListener() {
	        @Override
	        public void onNoAD(int arg0) {
	        }

	        @Override
	        public void onADReceive() {
	          iad.show();
	        }
	      });
	      iad.loadAD();
    }
    
    public void initMenuData(){
    	moreList=new ArrayList<Map<String,String>>();
    	Map<String,String> map;
    	map=new HashMap<String,String>();
    	map.put("menu", "屏幕手电");
    	this.moreList.add(map);
    	map=new HashMap<String,String>();
    	map.put("menu", "帮助");
    	this.moreList.add(map);
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			String ver=info.versionName;
	    	map=new HashMap<String,String>();
	    	map.put("menu", "版本"+ver);
	    	this.moreList.add(map);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	map=new HashMap<String, String>();
//    	map.put("menu", "关闭广告");
//    	this.moreList.add(map);
    }
    
    public void initPopupWindow(){
    	LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View view=inflater.inflate(R.layout.popup_list, null);
    	popupListView=(ListView)view.findViewById(R.id.popup_list);
    	myPopupWindow=new PopupWindow(view);
    	myPopupWindow.setFocusable(true);
    	simpleAdapter=new SimpleAdapter(Main.this, moreList, R.layout.popup_data, new String[]{"menu"}, new int[]{R.id.popup_list_data});
    	popupListView.setAdapter(simpleAdapter);
    	popupListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				if(moreList.get(position).get("menu")=="屏幕手电"){
					Intent it=new Intent(Main.this,ChangeColor.class);
					startActivity(it);
					myPopupWindow.dismiss();
				}else if(moreList.get(position).get("menu")=="帮助"){
					Intent intent=new Intent(Main.this,Contact.class);
					startActivity(intent);
					myPopupWindow.dismiss();
				}
//				else if(moreList.get(position).get("menu")=="关闭广告"){
//					floatbanner.closeAds();
//					toast.show("程序将关闭广告6s", 6);
//					Handler handler=new Handler();
//					handler.postDelayed(new Runnable(){
//						public void run(){
//							floatbanner=Qhad.showFloatbannerAd(Main.this, adSpaceid, false, Qhad.FLOAT_BANNER_SIZE.SIZE_DEFAULT, Qhad.FLOAT_LOCATION.BOTTOM);
//						}
//					}, 6000);
//			}
//				simpleAdapter.notifyDataSetChanged();
		}
    });
    	
    	popupListView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    	myPopupWindow.setWidth(popupListView.getMeasuredWidth());
    	myPopupWindow.setHeight((popupListView.getMeasuredHeight()+20)*NUM);
    	
    	myPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.popupwindow_bg));
    	myPopupWindow.setOutsideTouchable(true);
    	
 }
    
    public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		if((System.currentTimeMillis()-exitTime)>2000){
    			if(myPopupWindow.isShowing()){
    				myPopupWindow.dismiss();
    			}
    			toast.show("按两次退出程序", 10);
    			exitTime=System.currentTimeMillis();
    		}else{
    			Main.this.finish();
    		}
    		}else if(keyCode==KeyEvent.KEYCODE_MENU){
    			if(myPopupWindow.isShowing()){
    				myPopupWindow.dismiss();
    			}else{
    				myPopupWindow.showAsDropDown(menuList);
    			}
    	}
    	return false;
    }
    
    public void flashOn(){
		if(!isOn){
			PackageManager manager=Main.this.getPackageManager();
			FeatureInfo[] features=manager.getSystemAvailableFeatures();
			for(FeatureInfo f:features){
				if(PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)){
					if(camera==null){
						camera=Camera.open();
					}
					Camera.Parameters parameters=camera.getParameters();
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
					camera.setParameters(parameters);
					camera.startPreview();
				}
			}
			isOn=true;
			flash.setBackgroundResource(R.drawable.flashon);
		}else{
			if(camera!=null){
				camera.stopPreview();
				camera.release();
				camera=null;
			}
			isOn=false;
			flash.setBackgroundResource(R.drawable.flashoff);
		}
	}
	protected void onPause(){
		super.onPause();
		if(camera!=null){
			camera.stopPreview();
			camera.release();
			camera=null;
		}
		flash.setBackgroundResource(R.drawable.flashoff);
	}
	protected void onResume(){
		super.onResume();
		showInterAd();
		if(camera!=null){
			camera.stopPreview();
			camera.release();
			camera=null;
		}
		flash.setBackgroundResource(R.drawable.flashoff);
	}
	protected void onDestroy(){
		super.onDestroy();
		if(camera!=null){
			camera.stopPreview();
			camera.release();
			camera=null;
		}
		flash.setBackgroundResource(R.drawable.flashoff);
		toast.cancel();
	}
}
