package com.xyf.flashlight;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ChangeColor extends Activity {

	LinearLayout colorLayout;
	myToast toast;
	public PowerManager mPowerManager;
	private int color[]={
			R.color.orange,
			R.color.blue,
			R.color.green,
			R.color.pink,
			R.color.yellow,
			R.color.white
	};
	private int text[]={
			R.string.orange,
			R.string.blue,
			R.string.green,
			R.string.pink,
			R.string.yellow,
			R.string.white
	}; 
	
	ViewGroup bannerContainer;
	BannerView bv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.changecolor);
				        
				        bannerContainer = (ViewGroup) this.findViewById(R.id.changeColor_bannerContainer);
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
					    
					    showInterAd();
					    
		mPowerManager=(PowerManager)getSystemService(Context.POWER_SERVICE);
		WindowManager.LayoutParams param=getWindow().getAttributes();
		toast=new myToast(this);
		param.screenBrightness=1.0f;
		getWindow().setAttributes(param);
		colorLayout=(LinearLayout)super.findViewById(R.id.colorlayout);
		colorLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ChangeColor.this)
				.setTitle(getString(R.string.title))
				.setAdapter(new textAdapter(ChangeColor.this,color,text), listener)
				.setPositiveButton(getString(R.string.cancel),new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).show();
			}
		});
		toast.show(R.string.infom, 10);
	}
	
	protected void onResume(){
    	super.onResume();
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
	
	android.content.DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			colorLayout.setBackgroundResource(color[which]);
		}
	};
	
	protected void onDestroy(){
		super.onDestroy();
		toast.cancel();
	}
}
