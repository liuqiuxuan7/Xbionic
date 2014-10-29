package com.imcore.xbionic.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.imcore.xbionic.R;

public class LoaginActivity extends Activity  {
	
private Button btnSina,btnQQ,btnBuLuo,btnRegister;
private ImageButton IbHelp,IbServer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loagin);
		
		findView();
	  
	}
	

	private void findView(){
		btnSina=(Button)findViewById(R.id.btn_sina);
		btnSina.setOnClickListener(mClickListener);
		
		btnQQ=(Button)findViewById(R.id.btn_QQ);
		btnQQ.setOnClickListener(mClickListener);
		
		btnBuLuo=(Button)findViewById(R.id.btn_buluo);
		btnBuLuo.setOnClickListener(mClickListener);
		
		btnRegister=(Button)findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(mClickListener);
		
		IbHelp=(ImageButton)findViewById(R.id.Ib_help1);
		IbHelp.setOnClickListener(mClickListener);
		
		IbServer=(ImageButton)findViewById(R.id.Ib_server1);	
		IbServer.setOnClickListener(mClickListener);
	}
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_sina:
				Intent intent=new Intent(LoaginActivity.this,SinaLoginActivity.class);
				startActivity(intent);
				return ;
	      case R.id.btn_QQ:
	    	    Intent intent1=new Intent(LoaginActivity.this,QQLoginActivity.class);
				startActivity(intent1);
				return ;
	      case R.id.btn_buluo:
	  	    Intent intent2=new Intent(LoaginActivity.this,BXLoginActivity.class);
				startActivity(intent2);
				return ;
	      case R.id.btn_register:
	  	    Intent intent3=new Intent(LoaginActivity.this,RegisterMainActivity.class);
				startActivity(intent3);
				return ;
	      case R.id.Ib_help1:
	  	    Intent intent4=new Intent(LoaginActivity.this,HelpActivity.class);
				startActivity(intent4);
				return ;
	      case R.id.Ib_server1:
	  	    Intent intent5=new Intent(LoaginActivity.this,QQLoginActivity.class);
				startActivity(intent5);
				return ;
			
		
			}
			
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
