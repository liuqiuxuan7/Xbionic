package com.imcore.xbionic.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class LoadingActivity extends Activity {
    private ImageView Iv;
    private String str;
    private Timer timer;
    LayoutParams layoutParams;
	
    private Handler handler=new Handler(){
    	
    	public void handleMessage(android.os.Message msg){
    		if(msg.what==0){
    			layoutParams.height+=50;
    			Iv.setLayoutParams(layoutParams);
   			if(layoutParams.height>=750){
    				
    				Intent intent=new Intent(LoadingActivity.this,LoaginActivity.class);
					startActivity(intent);
					timer.cancel();
				    LoadingActivity.this.finish();
    			}
    		}
    	}
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);	 
		progress();
		str=this.getSharedPreferences("MyLogin", Context.MODE_PRIVATE).getString(str, "");
	}
    
	/*private void progress(){
		 mProgress=(ProgressBar)findViewById(R.id.progress_bar);
		  new Thread(new Runnable() {
			
			@Override
			public void run() {
				do {
					if(mProgress.getProgress()>=100){
						//if (LoadingActivity.this.str!=null&&LoadingActivity.this.str!="") {
							Intent intent=new Intent(LoadingActivity.this,LoaginActivity.class);
							startActivity(intent);
						    LoadingActivity.this.finish();
							return;
					}					
					try {						
						mProgress.incrementProgressBy(25);	
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (true);
			
			}
		}).start();
	}*/
	
	private void progress(){
		Iv=(ImageView)findViewById(R.id.Iv);
		layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,300);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		Iv.setLayoutParams(layoutParams);
	
		timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				handler.sendEmptyMessage(0);
			}
		}, 0, 500);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
		return true;
	}

}
