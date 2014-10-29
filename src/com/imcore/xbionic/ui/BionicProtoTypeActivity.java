package com.imcore.xbionic.ui;

import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BionicProtoTypeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bionic_proto_type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bionic_proto_type, menu);
		return true;
	}

}
