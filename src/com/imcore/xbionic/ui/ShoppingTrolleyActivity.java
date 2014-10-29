package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.android.volley.toolbox.ImageLoader;
import com.imcore.xbionic.R;
import com.imcore.xbionic.R.layout;
import com.imcore.xbionic.R.menu;

import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.ShoppingModel;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingTrolleyActivity extends Activity {

	public static final String SHOPPING = "shopping";
	private ListView lv_shop;
	private List<ShoppingModel> lsshoppings=new ArrayList<ShoppingModel>();
	private ShoppingAdapter madapter;
	
	private TextView info;
	private TextView totalmoney;
	public ImageView iv_shop_cart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		initView();
		initData();
		
        Button  btnback = (Button)findViewById(R.id.btn_shop_back);
		
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
	
	private void initView() {
		lv_shop = (ListView) findViewById(R.id.lv_shop);
		if(madapter==null){
			madapter=new ShoppingAdapter();
		lv_shop.setAdapter(madapter);
		}
	}
	
	private void initData() {
		SharedPreferences share=getSharedPreferences("demo",Activity.MODE_PRIVATE);
		String json=share.getString("shopping", "");
		lsshoppings=JsonUtil.toObjectList(json, ShoppingModel.class);
		
	}
	class ShoppingAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return lsshoppings.size();
		}

		@Override
		public Object getItem(int position) {
			
			return lsshoppings.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder vh = null;
					
			if(convertView==null)
			{ 
				view = LayoutInflater.from(ShoppingTrolleyActivity.this).inflate(
						R.layout.view_shopcar, null);
				vh= new ViewHolder();	
				vh.tv_shop_cart_color=(TextView)view.findViewById(R.id.tv_shop_cart_color);
				vh.tv_shop_cart_size=(TextView)view.findViewById(R.id.tv_shop_cart_size);
				vh.tv_shop_cart_name=(TextView)view.findViewById(R.id.tv_shop_cart_name);
				vh.tv_shop_cart_price_value=(TextView)view.findViewById(R.id.tv_shop_cart_price_value);
				vh.tv_shop_cart_count=(TextView)view.findViewById(R.id.tv_shop_cart_count_name);
				vh.iv_shop_cart=(ImageView)view.findViewById(R.id.iv_shop_cart);
				
				view.setTag(vh);
				
			}else{
				vh=(ViewHolder)view.getTag();
			}
			
			vh.tv_shop_cart_color.setText(lsshoppings.get(position).Color);
			vh.tv_shop_cart_name.setText(lsshoppings.get(position).name);
			vh.tv_shop_cart_size.setText(lsshoppings.get(position).Size);
			vh.tv_shop_cart_price_value.setText(lsshoppings.get(position).price+"");
			
			/*String url = HttpUtil.BASE_Img1 + lsshoppings.get(position).image
					+ "_L.jpg" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					ShoppingTrolleyActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(iv_shop_cart ,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);
			*/
			return view;
		}
		class ViewHolder{
			ImageView iv_shop_cart;
			TextView tv_shop_cart_name,tv_shop_cart_color,tv_shop_cart_size;
			TextView tv_shop_cart_price_value,tv_shop_cart_count;
			
		}
		
		
	}
	
}
