package com.imcore.xbionic.ui;

import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView.FindListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	 private PopupWindow popupWindow;
	 private Button btnListButton,btnSearch,btnPurse,
	                btnStory,btnActivity,btnIntroducte;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		findView();
	
	}
	
	
	private void findView(){
		btnListButton=(Button)findViewById(R.id.btn_listButton);
		btnListButton.setOnClickListener(mListener);
		
		btnSearch=(Button)findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(mListener);
		
		btnPurse=(Button)findViewById(R.id.btn_purse);
		btnPurse.setOnClickListener(mListener);
		
        btnStory=(Button)findViewById(R.id.btn_story);
        btnStory.setOnClickListener(mListener);
        
        btnActivity=(Button)findViewById(R.id.btn_activity);
        btnActivity.setOnClickListener(mListener);
        
        btnIntroducte=(Button)findViewById(R.id.btn_introducte);
        btnIntroducte.setOnClickListener(mListener);
	}
	
	private OnClickListener mListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_purse:
				Intent intent=new Intent(MainActivity.this,ProductPurseActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_story:
				Intent intent1=new Intent(MainActivity.this,MasterStoryActivity.class);
				startActivity(intent1);
				break;
			case R.id.btn_activity:
				Intent intent2=new Intent(MainActivity.this,XActivity.class);
				startActivity(intent2);
				break;
			case R.id.btn_introducte:
				Intent intent3=new Intent(MainActivity.this,XIntroduceActivity.class);
				startActivity(intent3);
				break;
			case R.id.btn_listButton:
				createPopuWindow(v);
				break;
			case R.id.btn_search:
				Intent intent4=new Intent(MainActivity.this,SearchActivity.class);
				startActivity(intent4);
				break;
         
			
			}
			
		}
	};


	
	protected void createPopuWindow(View v) {
		final String[] data = {"您的收藏","账户设置","达人申请","部落社区","购物车","订阅信息","分享设置","注销登录"};
		View view=getLayoutInflater().inflate(R.layout.main_home_head, null);
		ListView lv=(ListView) view.findViewById(R.id.left_drawer);
		
	    lv.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View view, ViewGroup parent) {
			  View v=LayoutInflater.from(MainActivity.this).inflate(R.layout.main_list_item, null);
			  TextView tv = (TextView) v.findViewById(R.id.menutv);
				tv.setText(data[position]);
				return v;
			}
			
			@Override
			public long getItemId(int position) {
				
				return position;
			}
			
			@Override
			public Object getItem(int arg0) {
				
				return data[arg0];
			}
			
			@Override
			public int getCount() {
				
				return data.length;
			}
		});
	    
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					Intent intent=new Intent(MainActivity.this,CollectiionActivity.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1=new Intent(MainActivity.this,CountSetActivity.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2=new Intent(MainActivity.this,MasterActivity.class);
					startActivity(intent2);
					break;	
				case 3:
					Intent intent3=new Intent(MainActivity.this,MainActivity.class);
					startActivity(intent3);
					break;	
				case 4:
					Intent intent4=new Intent(MainActivity.this,ShoppingTrolleyActivity.class);
					startActivity(intent4);
					break;
				case 5:
					Intent intent5=new Intent(MainActivity.this,MessageActivity.class);
					startActivity(intent5);
					break;
				case 6:
					Intent intent6=new Intent(MainActivity.this,ShareActivity.class);
					startActivity(intent6);
					break;
				case 7:
					finish();
					break;
			
				}
				
			}
		});
	    

		popupWindow = new PopupWindow(view,500,LayoutParams.MATCH_PARENT);
		//popupWindow.setAnimationStyle(R.style.popupwindow_style);
	//  popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); //这个叼。。。解决pop退出问题
	    popupWindow.setFocusable(true);
	//	popupWindow.update();
		
		popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
	    
	
	}



}
