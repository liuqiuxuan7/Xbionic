package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.imcore.xbionic.R;
import com.imcore.xbionic.fragment.RightMainFrament;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.ProductColor;
import com.imcore.xbionic.model.ProductSize;
import com.imcore.xbionic.model.ProductStore;
import com.imcore.xbionic.model.PurseDetialsInfo;
import com.imcore.xbionic.model.ShoppingModel;
import com.imcore.xbionic.model.Size;
import com.imcore.xbionic.model.Technology;

public class PurseMainActivity extends FragmentActivity implements
		OnClickListener {
	Button btn_detailsearch, btnBack, btn_shopping, btn_collect;
	EditText tv_forcount;
	Fragment mFrament;
	String code;
	String id;
	Size size;
	// 类的对象
	ProductColor productColor;
	ProductSize productSize;
	ProductStore productStores;
	PurseDetialsInfo DetialsInfo;

	LinearLayout linearLayout;// 颜色布局
	LinearLayout linearSize;// 尺寸布局

	// 适配器
	ImageAdapter imageAdapter;
	SizeAdapter sizeAdapter;
	TechAdapter techAdapter;

	Gallery gallery;
	ListView listview;
	ListView listViewTechonlogy;

	private ImageView currentImage;
	private TextView currentImage1;
	private boolean flagcolor = false;
	private boolean flagsize = false;

	int currentColor = -1;
	int currentSize = -1;
    //当前颜色
	String currentcl;
	String currentSi;
	
	List<Size> productSizes;
	List<ProductColor> productColors;
	// 商品库存
	TextView tvPrice;
	TextView tv_count;
	// 产品图片列表
	List<PurseDetialsInfo> listPurseInfo = new ArrayList<PurseDetialsInfo>();
	// 产品的具体尺寸大小
	List<ProductSize> listProductSize = new ArrayList<ProductSize>();
	// 科技点
	List<Technology> listTechnology = new ArrayList<Technology>();
	// 商品颜色
	List<ImageView> listProductColor = new ArrayList<ImageView>();
	// 商品的尺寸
	List<Button> btnSize = new ArrayList<Button>();
	
	List<ShoppingModel> lshopping = new ArrayList<ShoppingModel>();
	private float price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_main);

		gallery = (Gallery) findViewById(R.id.gl_detail);

		listview = (ListView) findViewById(R.id.lv_size);
		tv_count = (TextView) findViewById(R.id.tv_count);
		listViewTechonlogy = (ListView) findViewById(R.id.lv_technology);

		linearLayout = (LinearLayout) findViewById(R.id.rg_color);
		linearSize = (LinearLayout) findViewById(R.id.rg_size);

		findView();

		btn_detailsearch.setOnClickListener(this);
		btn_shopping.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btn_collect.setOnClickListener(this);
	    
		
		
		LoadProductsInfo();
		priceInfo();
		LoadProductsSize();
		LoadTechnology();
		LoadProductColor();
		LoadSize();
		init();
	}

	// 底下四个选择项
	private void findView() {
		btn_detailsearch = (Button) findViewById(R.id.btn_detailsearch);
		
		btnBack = (Button) findViewById(R.id.btn_detailback);
		btn_shopping = (Button) findViewById(R.id.btn_shopping);
		btn_collect = (Button) findViewById(R.id.btn_collect_purse);
		tv_forcount=(EditText)findViewById(R.id.tv_forcount);
		
		
	}

	// 初始化右侧拉Fragment
	private void init() {
		mFrament = new RightMainFrament();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.right_drawer, mFrament).commit();
	}

	// 加载商品图片信息
	private List<PurseDetialsInfo> LoadProductsInfo() {
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "product/images/list.do?id=" + id + "",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						listPurseInfo = JsonUtil.toObjectList(response,
								PurseDetialsInfo.class);
						// Toast.makeText(PurseMainActivity.this,listPurseInfo.size()+"",
						// 5000).show();
						/*
						 * if(imageAdapter==null){ imageAdapter=new
						 * ImageAdapter(PurseMainActivity.this,listPurseInfo);
						 * gallery.setAdapter(imageAdapter); }
						 */

						imageAdapter = new ImageAdapter(PurseMainActivity.this,
								listPurseInfo);
						gallery.setAdapter(imageAdapter);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();

					}
				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listPurseInfo;
	}

	private class ImageAdapter extends BaseAdapter {
		LayoutInflater inflater;
		// 上下文
		private Context mContext;
		List<PurseDetialsInfo> listPurseInfo = new ArrayList<PurseDetialsInfo>();

		public ImageAdapter(Context c, List<PurseDetialsInfo> listPurseInfo) {

			mContext = c;
			inflater = LayoutInflater.from(mContext);
			this.listPurseInfo = listPurseInfo;
		}

		@Override
		public int getCount() {

			return listPurseInfo.size();
		}

		@Override
		public Object getItem(int position) {

			return listPurseInfo.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			view = LayoutInflater.from(PurseMainActivity.this).inflate(
					R.layout.gallery_imageview, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
			/*
			 * View view=convertView; ViewHolder viewHolder=null;
			 * 
			 * if(view==null){
			 * view=LayoutInflater.from(PurseMainActivity.this).inflate
			 * (R.layout.gallery_imageview, null); viewHolder=new ViewHolder();
			 * viewHolder
			 * .iv_grally=(ImageView)view.findViewById(R.id.gallery_image);
			 * view.setTag(viewHolder); }else{
			 * viewHolder=(ViewHolder)view.getTag(); }
			 */

			String url = HttpUtil.BASE_Img1 + listPurseInfo.get(position).image
					+ "_L.jpg" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					PurseMainActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(imageView,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);
			return view;
		}

		/*
		 * private class ViewHolder { ImageView iv_grally; }
		 */
	}

	// 加载商品的价格
	private PurseDetialsInfo priceInfo() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "product/get.do?id=" + id + "",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						DetialsInfo = JsonUtil.toObject(response,
								PurseDetialsInfo.class);
						// 商品的价格
						tvPrice = (TextView) findViewById(R.id.text_price);
						tvPrice.setText("￥" + DetialsInfo.price);
						price = DetialsInfo.price;
						// 商品的名称
						TextView tvname = (TextView) findViewById(R.id.tv_details);
						tvname.setText("" + DetialsInfo.name);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();

					}

				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return DetialsInfo;

	}

	// 加载产品的具体尺寸大小
	private List<ProductSize> LoadProductsSize() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "product/size/list.do?id=" + id + "",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						String json = JsonUtil.getJsonValueByKey(response,
								"sizeStandardDetailList");
						listProductSize = JsonUtil.toObjectList(json,
								ProductSize.class);
						sizeAdapter = new SizeAdapter(PurseMainActivity.this,
								listProductSize);
						listview.setAdapter(sizeAdapter);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();

					}
				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listProductSize;

	}

	// 产品具体大小的适配器
	private class SizeAdapter extends BaseAdapter {

		private Context mContext;
		LayoutInflater inflater;

		public SizeAdapter(Context context, List<ProductSize> listProductSize) {
			mContext = context;
			inflater = LayoutInflater.from(mContext);
			listProductSize = listProductSize;

		}

		@Override
		public int getCount() {

			return listProductSize.size();
		}

		@Override
		public Object getItem(int position) {

			return listProductSize.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			View view = convertView;
			ViewHolder viewHolder = null;

			if (view == null) {
				view = LayoutInflater.from(PurseMainActivity.this).inflate(
						R.layout.view_listv_size, null);
				viewHolder = new ViewHolder();
				viewHolder.tv1 = (TextView) view.findViewById(R.id.tv_size_1);
				viewHolder.tv2 = (TextView) view.findViewById(R.id.tv_size_2);
				viewHolder.tv3 = (TextView) view.findViewById(R.id.tv_size_3);
				viewHolder.tv4 = (TextView) view.findViewById(R.id.tv_size_4);
				viewHolder.tv5 = (TextView) view.findViewById(R.id.tv_size_5);
				viewHolder.tv6 = (TextView) view.findViewById(R.id.tv_size_6);
				viewHolder.tv7 = (TextView) view.findViewById(R.id.tv_size_7);
				viewHolder.tv8 = (TextView) view.findViewById(R.id.tv_size_8);
				viewHolder.tv9 = (TextView) view.findViewById(R.id.tv_size_9);
				viewHolder.tv10 = (TextView) view.findViewById(R.id.tv_size_10);
				viewHolder.tv11 = (TextView) view.findViewById(R.id.tv_size_11);
				viewHolder.tv12 = (TextView) view.findViewById(R.id.tv_size_12);
				viewHolder.tv13 = (TextView) view.findViewById(R.id.tv_size_13);
				viewHolder.tv14 = (TextView) view.findViewById(R.id.tv_size_14);

				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			// String url=HttpUtil.BASE_URL+listProductSize.get(position).id;
			viewHolder.tv1.setText("" + listProductSize.get(position).id);
			viewHolder.tv2.setText(""
					+ listProductSize.get(position).sizeStandardId);
			viewHolder.tv3.setText("" + listProductSize.get(position).size);
			viewHolder.tv4.setText("" + listProductSize.get(position).p1);
			viewHolder.tv5.setText("" + listProductSize.get(position).p2);
			viewHolder.tv6.setText("" + listProductSize.get(position).p3);
			viewHolder.tv7.setText("" + listProductSize.get(position).p4);
			viewHolder.tv8.setText("" + listProductSize.get(position).p5);
			viewHolder.tv9.setText("" + listProductSize.get(position).p6);
			viewHolder.tv10.setText("" + listProductSize.get(position).p7);
			viewHolder.tv11.setText("" + listProductSize.get(position).p8);
			viewHolder.tv12.setText("" + listProductSize.get(position).p9);
			viewHolder.tv13.setText("" + listProductSize.get(position).p10);
			viewHolder.tv14.setText("" + listProductSize.get(position).p11);

			return view;
		}

		private class ViewHolder {
			TextView tv1;
			TextView tv2;
			TextView tv3;
			TextView tv4;
			TextView tv5;
			TextView tv6;
			TextView tv7;
			TextView tv8;
			TextView tv9;
			TextView tv10;
			TextView tv11;
			TextView tv12;
			TextView tv13;
			TextView tv14;

		}

	}

	// 科技点
	private List<Technology> LoadTechnology() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "product/labs/list.do?id=" + id + "",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						listTechnology = JsonUtil.toObjectList(response,
								Technology.class);

						if (techAdapter == null) {
							techAdapter = new TechAdapter(
									PurseMainActivity.this, listTechnology);
							listViewTechonlogy.setAdapter(techAdapter);
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);

		return listTechnology;

	}

	// 科技点适配器
	private class TechAdapter extends BaseAdapter {
		private Context mContext;
		LayoutInflater inflater;

		public TechAdapter(Context context, List<Technology> listTechnology) {
			mContext = context;
			inflater = LayoutInflater.from(mContext);
			PurseMainActivity.this.listTechnology = listTechnology;
		}

		@Override
		public int getCount() {

			return listTechnology.size();
		}

		@Override
		public Object getItem(int position) {

			return listTechnology.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View converView, ViewGroup parent) {
			View view;
			view = LayoutInflater.from(PurseMainActivity.this).inflate(
					R.layout.view_list_technology, null);
			TextView textview = (TextView) view.findViewById(R.id.tv_technogy);
			textview.setText("" + listTechnology.get(position).title);

			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_technogy);
			String url = HttpUtil.BASE_Img2
					+ listTechnology.get(position).imageUrl + "_M.jpg" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					PurseMainActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(imageView,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);
			return view;

		}

	}

	// 商品颜色
	private List<ProductColor> LoadProductColor() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "product/get.do?id=" + id + "",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						PurseDetialsInfo InfoColor = JsonUtil.toObject(
								response, PurseDetialsInfo.class);
						productColors = JsonUtil.toObjectList(
								InfoColor.sysColorList, ProductColor.class);

						// 动态加载图片的颜色
						for (int i = 0; i < productColors.size(); i++) {
							LayoutParams params = new LayoutParams(50, 50);
							params.setMargins(8, 0, 8, 8);

							ImageView view = new ImageView(
									PurseMainActivity.this);
							view.setScaleType(ScaleType.CENTER_CROP);
							view.setLayoutParams(params);

							// 默认未选中时没有边框
							// view.setBackgroundResource(R.drawable.imgv_bg_n);
							final int temp = i;
							view.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// 默认给第一个ImageView加边框
									flagcolor=true;
									currentColor = temp;
									for (int i = 0; i < listProductColor.size(); i++) {
										if (i == currentColor) {
											listProductColor
													.get(i)
													.setBackgroundResource(
															R.drawable.imgv_background);
											
											currentcl=productColors.get(i).color;
											
											LoadProductStore();
										} else {
											listProductColor
													.get(i)
													.setBackgroundResource(
															R.drawable.imgv_bg_n);
										}
									}

								}
							});

							String url = HttpUtil.BASE_Img2
									+ productColors.get(i).colorImage + ".jpg";
							ImageLoader loader = RequestQueueSingleton
									.getInstance(PurseMainActivity.this)
									.getImageLoader();
							loader.get(url, ImageLoader.getImageListener(view,
									R.drawable.ic_launcher,
									R.drawable.ic_launcher), 300, 300);

							linearLayout.addView(view);
							listProductColor.add(view);

						}

						// String
						// json=JsonUtil.getJsonValueByKey(response,"sysColorList"
						// );
						// listProductColor=JsonUtil.toObjectList(json,
						// PurseDetialsInfo.class);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();

					}
				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return productColors;
	}

	// 商品的尺寸
	private List<ProductSize> LoadSize() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "product/size/list.do?id=" + id + "",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						PurseDetialsInfo InfoSize = JsonUtil.toObject(response,
								PurseDetialsInfo.class);
						productSizes = JsonUtil.toObjectList(
								InfoSize.sysSizeList, Size.class);
						/*
						 * String json=JsonUtil.getJsonValueByKey(response,
						 * "sysSizeList");
						 * listProductSize=JsonUtil.toObjectList(json,
						 * ProductSize.class);
						 */

						// 动态加载尺寸
						for (int i = 0; i < productSizes.size(); i++/*
																	 * Size
																	 * size:
																	 * listSize
																	 */) {
							// LayoutParams params=new LayoutParams(width,
							// height);
							LayoutParams params = new LayoutParams(80, 50);
							// params.setMargins(left, top, right, bottom)
							params.setMargins(0, 0, 0, 8);
							final Button viewSize = new Button(
									PurseMainActivity.this);
							// TextView viewSize=new
							// ImageView(PurseMainActivity.this);
							viewSize.setLayoutParams(params);
							// viewSize.setBackgroundResource(R.drawable.imgv_bg_n);
							viewSize.setText(productSizes.get(i).size);
							linearSize.addView(viewSize);
							btnSize.add(viewSize);
							final int temp = i;

							viewSize.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									currentSize = temp;
									flagsize=true;
									for (int i = 0; i < btnSize.size(); i++) {
										if (i == currentSize) {
											btnSize.get(i)
													.setBackgroundResource(
															R.drawable.imgv_background);
											currentSi=productSizes.get(i).size;
											LoadProductStore();
										} else {

											btnSize.get(i)
													.setBackgroundResource(
															R.drawable.imgv_bg_n);
										}
									}
								}
							});
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PurseMainActivity.this,
								error.getMessage(), Toast.LENGTH_SHORT).show();

					}
				});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listProductSize;
	}

	// 商品库存
	private ProductStore LoadProductStore() {
		if (currentColor >= 0 && currentSize >= 0) {
			DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
					+ "product/quantity/get.do?id=" + id + "&colorId="
					+ productColors.get(currentColor).id + "&sizeId="
					+ productSizes.get(currentSize).id,
					new Response.Listener<String>() {

						@Override
						public void onResponse(String response) {
							productStores = JsonUtil.toObject(response,
									ProductStore.class);
							Log.v("aaa", response);
							tv_count.setText("库存" + productStores.qty + "");
							
							
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(PurseMainActivity.this,
									error.getMessage(), 5000).show();
						}
					});
			RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		}
		return productStores;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_detailsearch:
			Intent intent = new Intent(PurseMainActivity.this,
					SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_detailback:
			finish();
			break;
		case R.id.btn_shopping:
			ShoppingModel shopping=new ShoppingModel();
			//shopping.setId(id);
			shopping.setColor(currentcl);
			shopping.setSize(currentSi);
			shopping.setName(DetialsInfo.name);
			shopping.setPrice(DetialsInfo.price);
			shopping.setImage(listPurseInfo.get(0).image);
			lshopping.add(shopping);
			Gson gson=new Gson();
			String json=gson.toJson(lshopping);
		

			SharedPreferences share=getSharedPreferences("demo",Activity.MODE_APPEND);
			SharedPreferences.Editor editor = share.edit(); 
			editor.putString("shopping", json);
			editor.commit();
			
			
			//使用SharePreference来存储数据到本地
				/*SharedPreferences sharepreferences=PurseMainActivity.this.getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor=sharepreferences.edit();
				editor.putString("shopping", json);
				editor.commit();*/
				Toast.makeText(PurseMainActivity.this,share.getString("shopping", ""), 2000).show();
			    break;
		
		case R.id.btn_collect_purse:
			if(flagcolor && flagsize){
				Intent intent2 = new Intent(PurseMainActivity.this,
						CollectiionActivity.class);
			    intent2.putExtra("COLOR", currentcl);
				intent2.putExtra("SIZE", currentSi);
				intent2.putExtra("GOODSCOLOR", listPurseInfo.get(0).image);
				intent2.putExtra("NAME", DetialsInfo.name);
				intent2.putExtra("SPRICE",DetialsInfo.price+"");
			    intent2.putExtra("TAG", 1);
				Toast.makeText(this, "添加收藏成功", 5000).show();
            	startActivity(intent2);
			}	
	
		}

	}

}
