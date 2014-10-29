package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.imcore.xbionic.R;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.Category;
import com.imcore.xbionic.model.ChildCategory;

public class ProductPurseActivity extends Activity implements OnClickListener {
	static List<Category> listCategorys = new ArrayList<Category>();
	private PopupWindow popupWindow;
	private Button btn_drawer_product, btnSearch;
	private ImageView imgView;
	private View view;
	List<ChildCategory> liChildCategories = new ArrayList<ChildCategory>();
	static String CODE_FLAG;
	ScrollView scrollView;
	ImageAdapter imageAdapter;
	LinearLayout linearLayout;
	boolean visibility_Flag = true, flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_purse);
		scrollView = (ScrollView) findViewById(R.id.scrollist);

		// 搜索的点击事件
		btnSearch = (Button) findViewById(R.id.btn_productsreach);
		btnSearch.setOnClickListener(this);

		btn_drawer_product = (Button) findViewById(R.id.btn_drawer_product);
		btn_drawer_product.setOnClickListener(this);

		loadCategory();
		// 设置组视图的图片
		dyLoadView();

	}

	// 动态加载视图控件
	private void dyLoadView() {
		int[] logos = new int[] { R.drawable.upbackground,
				R.drawable.downbackground };
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHight = dm.heightPixels;
		int width = screenWidth;
		int height = screenHight / 2;
		linearLayout = new LinearLayout(ProductPurseActivity.this);
		linearLayout.setOrientation(1);
		LayoutParams lp = new LayoutParams(width, height-80 );

		for (int i = 0; i < logos.length; i++) {
			imgView = new ImageView(ProductPurseActivity.this);
			//设置图片满足X轴Y轴的大小
			//imgView.setScaleType(ScaleType.FIT_XY);
			imgView.setLayoutParams(lp);
			imgView.setImageResource(logos[i]);
			imgView.setPadding(0, 0, 0, 0);
			view = LayoutInflater.from(ProductPurseActivity.this).inflate(
					R.layout.first_category, null);
			final GridLayout gridLayout = new GridLayout(
					ProductPurseActivity.this);
			gridLayout.setLayoutParams(lp);
			final GridView gridView = new GridView(ProductPurseActivity.this);
			gridView.setNumColumns(4);
			// 动画效果

			/*
			 * AnimationSet set = new AnimationSet(false); Animation animation =
			 * new AlphaAnimation(0, 1); animation.setDuration(500);
			 * set.addAnimation(animation); animation = new
			 * TranslateAnimation(1, 13, 10, 50); animation.setDuration(300);
			 * set.addAnimation(animation); animation = new RotateAnimation(30,
			 * 10); animation.setDuration(300); set.addAnimation(animation);
			 * animation = new ScaleAnimation(5, 0, 2, 0);
			 * animation.setDuration(300); set.addAnimation(animation);
			 * LayoutAnimationController controller = new
			 * LayoutAnimationController( set, 1);
			 * gridView.setLayoutAnimation(controller);
			 */
			if (i == 0) {
				/*
				 * loadChildCategory("100002"); imageAdapter = new
				 * ImageAdapter(ProductPurseActivity.this, liChildCategories);
				 * gridView.setAdapter(imageAdapter);
				 */
				imgView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						loadChildCategory("100001", gridView);
						/*
						 * imageAdapter = new
						 * ImageAdapter(ProductPurseActivity.this,
						 * liChildCategories);
						 * gridView.setAdapter(imageAdapter);
						 */
						if (visibility_Flag) {

							gridLayout.setVisibility(View.VISIBLE);
							visibility_Flag = false;
						} else {
							gridLayout.setVisibility(View.GONE);
							visibility_Flag = true;
						}
					}
				});
			}

			if (i == 1) {
				/*
				 * loadChildCategory("100001"); imageAdapter = new
				 * ImageAdapter(ProductPurseActivity.this, liChildCategories);
				 * gridView.setAdapter(imageAdapter);
				 */
				imgView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						loadChildCategory("100002", gridView);
						/*
						 * imageAdapter = new
						 * ImageAdapter(ProductPurseActivity.this,
						 * liChildCategories);
						 * gridView.setAdapter(imageAdapter);
						 */
						if (flag) {

							gridLayout.setVisibility(View.VISIBLE);
							flag = false;
						} else {
							gridLayout.setVisibility(View.GONE);
							flag = true;
						}
					}
				});
			}
			gridLayout.addView(gridView);
			gridLayout.setVisibility(View.GONE);
			linearLayout.addView(imgView);
			linearLayout.addView(gridLayout);

		}
		scrollView.addView(linearLayout);

	}

	/**
	 * 加载父类别
	 */

	public void loadCategory() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "category/list.do", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				listCategorys = JsonUtil.toObjectList(response, Category.class);
				// Toast.makeText(ProductActivity.this, listcategorys.size() +
				// "",
				// 1000).show();

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ProductPurseActivity.this, error.getMessage(),
						5000).show();
			}
		});

		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
	}

	/**
	 * 加载子类别
	 * 
	 * @param code
	 */
	public void loadChildCategory(String code, final GridView gridView) {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL1
				+ "category/list.do?navId=" + code + "",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						liChildCategories = JsonUtil.toObjectList(response,
								ChildCategory.class);

						imageAdapter = new ImageAdapter(
								ProductPurseActivity.this, liChildCategories);
						// 设置适配器
						gridView.setAdapter(imageAdapter);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(ProductPurseActivity.this,
								error.getMessage(), 5000).show();
					}

				});

		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
	}

	private class ImageAdapter extends BaseAdapter {
		LayoutInflater inflater;
		List<ChildCategory> liChildCategories = new ArrayList<ChildCategory>();
		// 上下文
		private Context mContext;

		public ImageAdapter(Context c, List<ChildCategory> list) {
			mContext = c;
			inflater = LayoutInflater.from(mContext);

			this.liChildCategories = list;

		}

		@Override
		public int getCount() {

			return liChildCategories.size();
		}

		@Override
		public Object getItem(int position) {

			return liChildCategories.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View childView;
			childView = LayoutInflater.from(ProductPurseActivity.this).inflate(
					R.layout.view_expandview_item, null);
			ImageView image = (ImageView) childView
					.findViewById(R.id.iv_expandview_item);

			childView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(ProductPurseActivity.this,
							ProductDetialsActivity.class);

					Bundle bundle = new Bundle();
					if (liChildCategories.size() == 8) {
						bundle.putString(ProductPurseActivity.CODE_FLAG,
								"100001");
					}
					if (liChildCategories.size() == 7) {

						bundle.putString(ProductPurseActivity.CODE_FLAG,
								"100002");
					}
					bundle.putInt("id", liChildCategories.get(position).id);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

			String url = HttpUtil.BASE_Img
					+ liChildCategories.get(position).imageUrl + "_L.png" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					ProductPurseActivity.this).getImageLoader();

			loader.get(url, ImageLoader.getImageListener(image,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);

			TextView tv = (TextView) childView
					.findViewById(R.id.tv_expandview_item);
			tv.setText(liChildCategories.get(position).name);
			return childView;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_drawer_product:
			createPopuWindow(v);
			break;
		case R.id.btn_productsreach:
			Intent intent = new Intent(ProductPurseActivity.this,
					SearchActivity.class);
			startActivity(intent);
			break;

		}

	}

	//点击出现下拉菜单
	protected void createPopuWindow(View v) {
		final String[] data = { "您的收藏", "账户设置", "达人申请", "部落社区", "购物车", "订阅信息",
				"分享设置" };
		View view = getLayoutInflater().inflate(R.layout.main_home_head, null);
		ListView lv = (ListView) view.findViewById(R.id.left_drawer);

		lv.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View view, ViewGroup parent) {
				View v = LayoutInflater.from(ProductPurseActivity.this)
						.inflate(R.layout.main_list_item, null);
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
					Intent intent = new Intent(ProductPurseActivity.this,
							CollectiionActivity.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1 = new Intent(ProductPurseActivity.this,
							CountSetActivity.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(ProductPurseActivity.this,
							MasterActivity.class);
					startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent(ProductPurseActivity.this,
							MainActivity.class);
					startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent(ProductPurseActivity.this,
							ShoppingTrolleyActivity.class);
					startActivity(intent4);
					break;
				case 5:
					Intent intent5 = new Intent(ProductPurseActivity.this,
							MessageActivity.class);
					startActivity(intent5);
					break;
				case 6:
					Intent intent6 = new Intent(ProductPurseActivity.this,
							ShareActivity.class);
					startActivity(intent6);
					break;

				}

			}
		});
		
        //  popupWindow=new PopupWindow(contentView, width, height, focusable);
		popupWindow = new PopupWindow(view, 500, LayoutParams.MATCH_PARENT);
		// popupWindow.setAnimationStyle(R.style.popupwindow_style);
		// popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个叼。。。解决pop退出问题
		popupWindow.setFocusable(true);
		// popupWindow.update();

		popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);

	}

}