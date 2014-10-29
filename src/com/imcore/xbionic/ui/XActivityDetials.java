package com.imcore.xbionic.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.HandlerBase;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;

import com.imcore.xbionic.model.XActivityDetialsInfo;
import com.imcore.xbionic.model.XActivityModel;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class XActivityDetials extends Activity {
	int id;
	ImageView imgxaty2;
	TextView tvStartTime,tvEndTime,tvtitle,tvaddress,
	tv_faqiren,tv_orginzer,tv_signUpDeadLine,tv_activityType,tv_content,tv_participants;
	XActivityDetialsInfo XADetials;
	List<XActivityDetialsInfo> listXactivityDetialsInfo = new ArrayList<XActivityDetialsInfo>();
	
	//XActivityDetialsAdapter xActivityDetialsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xactivity_detials);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
	     LoadActivityInfo();
	        findView();
	}
    //初始化控件
	private void findView(){
		 imgxaty2=(ImageView)findViewById(R.id.img_xaty2);
		 tvtitle=(TextView)findViewById(R.id.tv_title);
		 tvStartTime=(TextView)findViewById(R.id.tv_times1);
		 tvEndTime=(TextView)findViewById(R.id.tv_times2);
		 tvaddress=(TextView)findViewById(R.id.tv_adress);
		 tv_faqiren=(TextView)findViewById(R.id.tv_faqiren);
		 tv_orginzer=(TextView)findViewById(R.id.tv_orginzer);
		 tv_signUpDeadLine=(TextView)findViewById(R.id.tv_signUpDeadLine);
		 tv_activityType=(TextView)findViewById(R.id.tv_activityType);
		 tv_participants=(TextView)findViewById(R.id.tv_participants);
		 tv_content=(TextView)findViewById(R.id.tv_content);
	}
	//加载活动详情
	private List<XActivityDetialsInfo>  LoadActivityInfo(){
		
		DataRequest request=new DataRequest(Method.GET, HttpUtil.BASE_URL
				+"search/keyword.do?type=2&offset=0id="+id, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
					listXactivityDetialsInfo=JsonUtil.toObjectList(response, XActivityDetialsInfo.class);
					for(int i=0;i<listXactivityDetialsInfo.size();i++){
						if(listXactivityDetialsInfo.get(i).id==id){
							XADetials=listXactivityDetialsInfo.get(i);
						}
						
					}
											
					tvStartTime.setText(""+XADetials.beginTime);
					tvEndTime.setText(""+XADetials.endTime);						
					tvtitle.setText(""+XADetials.title);
					tv_activityType.setText("类型:"+XADetials.activityType);
					tv_content.setText(""+XADetials.content);
					//tv_orginzer.setText("组织者："+XADetials.organizer);
					tvaddress.setText("地点:"+XADetials.address);
					tv_signUpDeadLine.setText("报名结束时间："+XADetials.signUpDeadLine);
					tv_faqiren.setText("发起人:"+XADetials.organizer);	
					tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
					tv_content.setText(Html.fromHtml(XADetials.content));
				   

				 	SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
				 	
					try {
						Date bt = formatter.parse(""+XADetials.beginTime);
						Date et =formatter.parse(""+XADetials.endTime);
						Date st=formatter.parse(""+XADetials.signUpDeadLine);
						
						  Calendar cal = Calendar.getInstance();
						  
						  cal.setTime(bt);
						  String btime = cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"
								  +cal.get(Calendar.DATE)+"日——";
						  
						  cal.setTime(et);
						  String etime = cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"
								  +cal.get(Calendar.DATE)+"日";
						  
						  cal.setTime(st);
						  String stime = cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"
								  +cal.get(Calendar.DATE)+"日";
						  
						  tvStartTime.setText(btime);
						  tvEndTime.setText(etime);
						  tv_signUpDeadLine.setText(stime);
						  
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
				/*	Date date=new Date();
				 	SimpleDateFormat formatter=new SimpleDateFormat("yyy年MM月dd日");
				    String dateString=formatter.format(XADetials.beginTime);
				   */
					imgxaty2=(ImageView)findViewById(R.id.img_xaty2);
						String url = HttpUtil.BASE_Img1
								+ XADetials.titleImageUrl + ".jpg";

						ImageLoader loader = RequestQueueSingleton.getInstance(
								XActivityDetials.this).getImageLoader();
						loader.get(url, ImageLoader.getImageListener(imgxaty2,
								R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					
							Toast.makeText(XActivityDetials.this, error.getMessage(), 5000)
									.show();
						
					}
				});
		   RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		   return listXactivityDetialsInfo;
	
		
	}
	
	/*
	private class XActivityDetialsAdapter extends BaseAdapter {
		LayoutInflater inflater;
		private Context mContext;

		List<XActivityDetialsInfo> listXactivityDetialsInfo;

		public XActivityDetialsAdapter(Context c, List<XActivityDetialsInfo> listXactivityDetialsInfo) {
			mContext = c;
			inflater = LayoutInflater.from(c);
			this.listXactivityDetialsInfo = listXactivityDetialsInfo;
		}

		@Override
		public int getCount() {

			return listXactivityDetialsInfo.size();
		}

		@Override
		public Object getItem(int position) {

			return listXactivityDetialsInfo.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}
      
		@Override
		public View getView(final int position, View converView, ViewGroup parent) {
			View childView;
			childView = inflater.from(XActivityDetials.this).inflate(
					R.layout.view_xactivity_item, null);
			ImageView ivActivity = (ImageView) childView
					.findViewById(R.id.img_xaty);
			TextView tvTime1 = (TextView) childView.findViewById(R.id.tv_time1);
			TextView tvTime2 = (TextView) childView.findViewById(R.id.tv_time2);
			TextView tvxaty = (TextView) childView.findViewById(R.id.tv_xaty);

			tvTime1.setText(this.listXactivityDetialsInfo.get(position).beginTime);
			tvTime2.setText(this.listXactivityDetialsInfo.get(position).endTime);
			tvxaty.setText(this.listXactivityDetialsInfo.get(position).title);
			

			String url = HttpUtil.BASE_Img1
					+ listXactivityDetialsInfo.get(position).titleImageUrl + ".jpg";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					XActivityDetials.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(ivActivity,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);

			return childView;
		}
	}*/
}
