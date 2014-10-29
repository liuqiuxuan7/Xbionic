package com.imcore.xbionic.ui;

import com.imcore.xbionic.R;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HelpActivity extends Activity implements OnPageChangeListener{
   private ViewPager viewPager;
   //装点点的ImageView数组
   private ImageView[] tips;
	//装ImageView数组
   private ImageView[] mImageViews;
   //图片资源id
   private int[] imgIdArry; 
   	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ViewGroup group=(ViewGroup)findViewById(R.id.viewGroup);
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		//加载图片资源ＩＤ
		imgIdArry=new int[]{R.drawable.welcompage1,R.drawable.welcompage2,R.drawable.welcompage3};
		
		//讲点点加入到ViewGroup中
		tips=new ImageView[imgIdArry.length];
		for(int i=0;i<tips.length;i++){
			tips[i]=new ImageView(this);
			if(i==0){
				tips[i].setBackgroundResource(R.drawable.whitepoint);
			}else {
				tips[i].setBackgroundResource(R.drawable.blackpoint);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,    
				                   LayoutParams.WRAP_CONTENT));  
            layoutParams.leftMargin=5;
            layoutParams.rightMargin=5;
            group.addView(tips[i], layoutParams);
		}
		//将图片装载到数组中
		mImageViews=new ImageView[imgIdArry.length];
		for(int i=0;i<mImageViews.length;i++){
			ImageView imageView=new ImageView(this);
			mImageViews[i]=imageView;
			imageView.setBackgroundResource(imgIdArry[i]);
		}
		//设置Adapter
		viewPager.setAdapter(new MyAdapter());
		//设置监听，主要设置点点的背景
		viewPager.setOnPageChangeListener(this);
		//设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		 viewPager.setCurrentItem((mImageViews.length) * 100);  
	}
   	
   	public class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0==arg1;
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mImageViews[position%mImageViews.length]);
		}
		
		// 载入图片进去，用当前的position 除以 图片数组长度取余数是关键 
		@Override
		public Object instantiateItem(View container, int position) {
			try{
			((ViewPager)container).addView(mImageViews
					[position%mImageViews.length], 0);		
		} catch(Exception e){ 
			
		}
			return mImageViews[position%mImageViews.length];
   	}
   	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		 setImageBackground(arg0 % mImageViews.length);  
	}
	
	//设置选中的tip的背景  @param selectItems 

	private void setImageBackground(int selectItems) {
		for(int i=0;i<tips.length;i++){
			if(i==selectItems){
				tips[i].setBackgroundResource(R.drawable.whitepoint);
				
			}else {
				tips[i].setBackgroundResource(R.drawable.blackpoint);
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}


}
