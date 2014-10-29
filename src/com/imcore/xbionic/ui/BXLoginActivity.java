package com.imcore.xbionic.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.imcore.xbionic.R;

public class BXLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bxlogin);
		
		Button btnBack=(Button)findViewById(R.id.but_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(BXLoginActivity.this,LoaginActivity.class);
				startActivity(intent);
				
			}
		});
		
		Button btnForget=(Button)findViewById(R.id.btn_forgetpw);
		btnForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			Intent intent1=new Intent(BXLoginActivity.this,ForgetPasswordActivity.class);
			startActivity(intent1);
			}
		});
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bxlogin, menu);
		return true;
	}

}
