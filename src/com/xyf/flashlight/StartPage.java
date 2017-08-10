package com.xyf.flashlight;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class StartPage extends Activity implements SplashADListener{

	private TextView start_text,start_version;
	private boolean isExit=false;
	

	ViewGroup bannerContainer;
	BannerView bv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_page);
		start_text=(TextView)super.findViewById(R.id.start_text);

        bannerContainer = (ViewGroup) this.findViewById(R.id.startpage_bannerContainer);
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
	    
	    ViewGroup container = (ViewGroup) this.findViewById(R.id.splash);
		SplashAD splashAD = new SplashAD(this, container, "1105416268", "8070517738923265", this);
	    
		start_version=(TextView)super.findViewById(R.id.start_version);
		
		
		AnimationSet set=new AnimationSet(true);
		AlphaAnimation alpha=new AlphaAnimation(0.3f,1); //渐变透明度
		ScaleAnimation scale=new ScaleAnimation(0.3f,1.2f,0.3f,1.2f); //缩放
		TranslateAnimation translate=new TranslateAnimation(0.0f,10.0f,0.0f,30.0f); //偏移
		alpha.setDuration(1000);
		scale.setDuration(3000);
		translate.setDuration(3000);
//		alpha.setRepeatCount(3);
		set.addAnimation(alpha);
		set.addAnimation(scale);
		set.addAnimation(translate);
		start_text.startAnimation(set);
	}

	private void init(){
		try{
			PackageInfo info=getPackageManager().getPackageInfo(getPackageName(), 0);
			start_version.setText("Ver "+info.versionName);
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(5000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				if(!isExit){
					Intent it=new Intent(StartPage.this,Main.class);
					StartPage.this.startActivity(it);
					StartPage.this.finish();
				}
			}
		}).start();
	}
	
	@Override
	public void onADClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onADDismissed() {
		// TODO Auto-generated method stub
		init();
	}

	@Override
	public void onADPresent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoAD(int arg0) {
		// TODO Auto-generated method stub
		init();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
