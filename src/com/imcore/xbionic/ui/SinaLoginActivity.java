package com.imcore.xbionic.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.imcore.xbionic.R;

public class SinaLoginActivity extends Activity {
   private Button btn_OAUTH,btn_click,btn_zhuxiao;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sina_login);
		findView();
	}
	
	private void findView(){
		btn_OAUTH=(Button)findViewById(R.id.btn_OAUTH);
		btn_OAUTH.setOnClickListener(mOnClick);
		
		btn_click=(Button)findViewById(R.id.btn_click);
		btn_click.setOnClickListener(mOnClick);
		
		btn_zhuxiao=(Button)findViewById(R.id.btn_zhuxiao);
		btn_zhuxiao.setOnClickListener(mOnClick);
	}

	private OnClickListener mOnClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		  switch (v.getId()) {
		case R.id.btn_OAUTH:{
			Intent intent=new Intent(SinaLoginActivity.this,MainActivity.class);
			startActivity(intent);
		  }
		case R.id.btn_click:{
			  
		  }
		case R.id.btn_zhuxiao:{
			finish();
		  }
		}
	}
}; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sina_login, menu);
		return true;
	 }
	
}
