package com.imcore.xbionic.ui;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.imcore.xbionic.R;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.XActivityModel;

public class XActivity extends Activity {

	private ListView lvXActivity;
	List<XActivityModel> listXactivity = new ArrayList<XActivityModel>();
	XActivityAdapter xActivityAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_x);
		lvXActivity = (ListView) findViewById(R.id.x_activity);
		Button btn_forfilter=(Button)findViewById(R.id.btn_forfilter);
		btn_forfilter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(XActivity.this, v);
				menu.getMenuInflater().inflate(R.menu.x,menu.getMenu());
				menu.show();
				
			}
		});
		
		Button btn_back_x=(Button)findViewById(R.id.btn_back_x);
		btn_back_x.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
		LoadXActivity();

	}

	public List<XActivityModel> LoadXActivity() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "search/keyword.do?type=2&offset=0",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						listXactivity = JsonUtil.toObjectList(response,
								XActivityModel.class);

						if (xActivityAdapter == null) {
							xActivityAdapter = new XActivityAdapter(
									XActivity.this, listXactivity);
							lvXActivity.setAdapter(xActivityAdapter);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(XActivity.this, error.getMessage(), 5000)
								.show();

					}
				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listXactivity;

	}

	private class XActivityAdapter extends BaseAdapter {
		LayoutInflater inflater;
		private Context mContext;

		List<XActivityModel> listXactivity;

		public XActivityAdapter(Context c, List<XActivityModel> listXactivity) {
			mContext = c;
			inflater = LayoutInflater.from(c);
			this.listXactivity = listXactivity;
		}

		@Override
		public int getCount() {

			return listXactivity.size();
		}

		@Override
		public Object getItem(int position) {

			return listXactivity.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View converView, ViewGroup parent) {
			View childView;
			childView = inflater.from(XActivity.this).inflate(
					R.layout.view_xactivity_item, null);
			ImageView ivActivity = (ImageView) childView
					.findViewById(R.id.img_xaty);
			TextView tvTime1 = (TextView) childView.findViewById(R.id.tv_time1);
			TextView tvTime2 = (TextView) childView.findViewById(R.id.tv_time2);
			TextView tvxaty = (TextView) childView.findViewById(R.id.tv_xaty);

			
			tvxaty.setText(this.listXactivity.get(position).title);
		
			
		 	SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
		 	Date bt;
			try {
				bt = formatter.parse(this.listXactivity.get(position).beginTime);
				Date et =formatter.parse(this.listXactivity.get(position).endTime);
				  Calendar cal = Calendar.getInstance();
				  cal.setTime(bt);
				  String btime = cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"
						  +cal.get(Calendar.DATE)+"日——";
				  
				  cal.setTime(et);
				  String etiume = cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"
						  +cal.get(Calendar.DATE)+"日";
				  
				  tvTime1.setText(btime);
					tvTime2.setText(etiume);
				  
			} catch (ParseException e) {
				e.printStackTrace();
			} 		
			//点击跳转到每项活动的详情
			childView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(XActivity.this,XActivityDetials.class);
					
					Bundle bundle = new Bundle();
					bundle.putInt("id", listXactivity.get(position).id );
					intent.putExtras(bundle);
					startActivity(intent);
					
				}
			});

			String url = HttpUtil.BASE_Img1
					+ listXactivity.get(position).titleImageUrl + ".jpg";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					XActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(ivActivity,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);

			return childView;
		}
	}

}
