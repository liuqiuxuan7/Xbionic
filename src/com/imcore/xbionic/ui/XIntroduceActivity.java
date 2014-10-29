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
import android.widget.ImageView;

import com.imcore.xbionic.R;

public class XIntroduceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_xintroduce);
		
		ImageView IvAware=(ImageView)findViewById(R.id.Iv_awareimg);
		IvAware.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(XIntroduceActivity.this,AwareActivity.class);
				startActivity(intent);
			}
		});
		
		ImageView IvBionicProto=(ImageView)findViewById(R.id.Iv_bionicprototype);
		IvBionicProto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(XIntroduceActivity.this,BionicProtoTypeActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnBack =(Button)findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent intent=new Intent(XIntroduceActivity.this,MainActivity.class);
				startActivity(intent);*/
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xintroduce, menu);
		return true;
	}

}
