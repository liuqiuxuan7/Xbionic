package com.imcore.xbionic.fragment;

import com.imcore.xbionic.R;
import com.imcore.xbionic.ui.CollectiionActivity;
import com.imcore.xbionic.ui.PurseJieSuanActivity;
import com.imcore.xbionic.ui.ShareActivity;
import com.imcore.xbionic.ui.ShoppingTrolleyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;

public class RightMainFrament extends Fragment implements OnClickListener{

	private ImageView ivComment,ivNews,ivAward;
	private Button btnRightShare,btnRightShop,btnRightPurse,btnRightCollection;
	View view;
	Fragment mFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.right_main_frament, null);
		init();
		FirstFramentLoad();
		return view;
	}
	
	private void init(){
		ivComment=(ImageView)view.findViewById(R.id.iv_right_comment);
		ivNews=(ImageView)view.findViewById(R.id.iv_right_news);
		ivAward=(ImageView)view.findViewById(R.id.iv_right_award);
		
		ivComment.setOnClickListener(this);
		ivNews.setOnClickListener(this);
		ivAward.setOnClickListener(this);
		
		btnRightShare=(Button)view.findViewById(R.id.right_frament_share);
		btnRightShop=(Button)view.findViewById(R.id.right_frament_shopping);
		btnRightPurse=(Button)view.findViewById(R.id.right_frament_purse);
		btnRightCollection=(Button)view.findViewById(R.id.right_frament_collection);
		
		btnRightShare.setOnClickListener(this);
		btnRightShop.setOnClickListener(this);
		btnRightPurse.setOnClickListener(this);
		btnRightCollection.setOnClickListener(this);
	}
	
	private void FirstFramentLoad(){
		mFragment=new FragmentComment();
		getFragmentManager().beginTransaction()
		.replace(R.id.right_drawer_main, mFragment).commit();
		ivComment.setBackgroundResource(R.drawable.usercomment_select);
		ivNews.setBackgroundResource(R.drawable.productnews_nomal);
		ivAward.setBackgroundResource(R.drawable.awardwinning_nomal);
	}
	
	@Override
	public void onClick(View view) {
	  
		switch (view.getId()) {
		case R.id.iv_right_comment:
			mFragment=new FragmentComment();
			getFragmentManager().beginTransaction()
			.replace(R.id.right_drawer_main, mFragment).commit();
			ivComment.setBackgroundResource(R.drawable.usercomment_select);
			ivNews.setBackgroundResource(R.drawable.productnews_nomal);
			ivAward.setBackgroundResource(R.drawable.awardwinning_nomal);
			break;
		case R.id.iv_right_news:
			mFragment=new FragmentNews();
			getFragmentManager().beginTransaction()
			.replace(R.id.right_drawer_main, mFragment).commit();
			ivComment.setBackgroundResource(R.drawable.usercomment_nomal);
			ivNews.setBackgroundResource(R.drawable.productnews_select);
			ivAward.setBackgroundResource(R.drawable.awardwinning_nomal);
			break;

		case R.id.iv_right_award:
			mFragment=new FragmentAward();
			getFragmentManager().beginTransaction()
			.replace(R.id.right_drawer_main, mFragment).commit();
			ivComment.setBackgroundResource(R.drawable.usercomment_nomal);
			ivNews.setBackgroundResource(R.drawable.productnews_nomal);
			ivAward.setBackgroundResource(R.drawable.awardwinning_select);
			break;
			
		case R.id.right_frament_share:
			Intent intent=new Intent(RightMainFrament.this.getActivity(),ShareActivity.class);
			startActivity(intent);
			break;
		case R.id.right_frament_shopping:
			Intent intent1=new Intent(RightMainFrament.this.getActivity(),ShoppingTrolleyActivity.class);
			startActivity(intent1);
			break;
		case R.id.right_frament_purse:
			Intent intent2=new Intent(RightMainFrament.this.getActivity(),PurseJieSuanActivity.class);
			startActivity(intent2);
			break;
		case R.id.right_frament_collection:
			Intent intent3=new Intent(RightMainFrament.this.getActivity(),CollectiionActivity.class);
			startActivity(intent3);
			break;
		}
		
	}

}
