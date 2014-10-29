package com.imcore.xbionic.ui;

import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class RegisterMainActivity extends Activity {
  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_main);
		
		
		Button sina_register=(Button)findViewById(R.id.sina_register);
		sina_register.setOnClickListener(mClick);
		
		Button qq_register=(Button)findViewById(R.id.qq_register);
		qq_register.setOnClickListener(mClick);
		
		Button telephone_register=(Button)findViewById(R.id.telephone_register1);
		telephone_register.setOnClickListener(mClick);
		
		Button btnRegisterBack=(Button)findViewById(R.id.btn_register_back1);
		btnRegisterBack.setOnClickListener(mClick);
	}
	private OnClickListener mClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sina_register:
				Toast.makeText(RegisterMainActivity.this, "新浪微博验证成功", 1000).show();
				return;
			case R.id.qq_register:
				Toast.makeText(RegisterMainActivity.this, "腾讯账号验证成功", 1000).show();
				return;
			case R.id.telephone_register1:	
				Intent  intent=new Intent(RegisterMainActivity.this,PhoneRegisterActivity.class);
				startActivity(intent);
			case R.id.btn_register_back1:
				Intent intent1=new Intent(RegisterMainActivity.this,LoaginActivity.class);
				startActivity(intent1);
			}
			
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
