package com.xyf.flashlight;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

public class Contact extends Activity {

	ViewGroup bannerContainer;
	BannerView bv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact);

				        bannerContainer = (ViewGroup) this.findViewById(R.id.contact_bannerContainer);
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
}
