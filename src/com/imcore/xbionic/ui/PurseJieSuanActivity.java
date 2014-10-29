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
import android.widget.Button;

public class PurseJieSuanActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_jie_suan);
		
		Button btn_buy_add=(Button)findViewById(R.id.btn_buy_add);
		btn_buy_add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_buy_add:
			Intent intent=new Intent(PurseJieSuanActivity.this,BuyModifyAdressActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
