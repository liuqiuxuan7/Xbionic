package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.imcore.xbionic.model.Products;

public class ProductDetialsActivity extends Activity {
	String code;
	long id;
	static String CODE_FLAG_Detial;
	ImageAdapter imageAdapter;
	List<Products> listProducts = new ArrayList<Products>();

	boolean flag = false;
	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detials);

		gridView = (GridView) findViewById(R.id.gd_second);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		code = bundle.getString(ProductPurseActivity.CODE_FLAG);
		id = bundle.getInt("id");
		
		LoadProducts();

		Button btnBack = (Button) findViewById(R.id.btn_stores_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProductDetialsActivity.this,
						ProductPurseActivity.class);
				
				
				startActivity(intent);

			}
		});
	}

	/**
	 * 加载父类别
	 */
	public List<Products> LoadProducts() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "category/products.do?navId=" + code + "&subNavId=" + id
				+ "&offset=0", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				listProducts = JsonUtil.toObjectList(response, Products.class);

				if (imageAdapter == null) {
					imageAdapter = new ImageAdapter(
							ProductDetialsActivity.this, listProducts);
					gridView.setAdapter(imageAdapter);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ProductDetialsActivity.this, error.getMessage(),
						5000).show();
			}
		});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listProducts;
	}

	private class ImageAdapter extends BaseAdapter {
		LayoutInflater inflater;
		// 上下文
		private Context mContext;
		List<Products> listProducts = new ArrayList<Products>();

		public ImageAdapter(Context c, List<Products> listProducts) {

			mContext = c;
			inflater = LayoutInflater.from(mContext);

			this.listProducts = listProducts;
		}

		@Override
		public int getCount() {

			return listProducts.size();
		}

		@Override
		public Object getItem(int position) {

			return listProducts.get(position);
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View childView;

			childView = inflater.from(ProductDetialsActivity.this).inflate(
					R.layout.view_second_item, null);
			ImageView image = (ImageView) childView
					.findViewById(R.id.img_show_second);
			TextView textOne = (TextView) childView
					.findViewById(R.id.text_show_one);
			TextView textTwo = (TextView) childView
					.findViewById(R.id.text_show_two);
			textOne.setText(listProducts.get(position).name);
			textTwo.setText("￥" + listProducts.get(position).price + "");

			// 点击跳转到商品信息详情的界面
			childView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(ProductDetialsActivity.this,
							PurseMainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("id", listProducts.get(position).id + "");
					intent.putExtras(bundle);
					startActivity(intent);

				}
			});
			String url = HttpUtil.BASE_Img1
					+ listProducts.get(position).imageUrl + "_L.jpg" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					ProductDetialsActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(image,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);

			return childView;
		}

	}

}
