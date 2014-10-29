package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.toolbox.ImageLoader;
import com.imcore.xbionic.R;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.CollectionModel;

public class CollectiionActivity extends Activity {
	
	private static String mColor;
	private static String mSize;
	private static String mGoodsColor;
	private static String mName;
	private static String mPrice;
	private int count = 0;
	private int Tag = 0;
	private static List<CollectionModel> clloect = new ArrayList<CollectionModel>();
	private ListView cllectview;
	private CollectAdapter madapter = null;
	private TextView info;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectiion);
		
		
		Button btnCollectBack=(Button)findViewById(R.id.btn_listButton);
		btnCollectBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			finish();
				
			}
		});	

		Intent intent = this.getIntent();
		int Tag;     //获取已有的intent对象   		
		Tag = intent.getIntExtra("TAG",-1);
		if(Tag == 1) { 
			mColor = intent.getStringExtra("COLOR");
			mSize = intent.getStringExtra("SIZE");
			mGoodsColor = intent.getStringExtra("GOODSCOLOR");
			mName = intent.getStringExtra("NAME");
			mPrice = intent.getStringExtra("SPRICE");
			setData();
		}
		cllectview = (ListView)findViewById(R.id.lv_collection);
		info = (TextView)findViewById(R.id.tv_info);
		if(clloect.size() < 1) {
		   info.setText("~亲~收藏还是空的~");	
		}
		
		if(madapter == null) {
			for(int i=0;i<clloect.size();i++) {
				info.setText("");	
				CollectionModel check = clloect.get(i);				
				if (check.Price == mPrice && check.Name.equals(mName) && check.Size.equals(mSize) && check.Color.equals(mColor)) {
					count++;					
				}
				if(count >1) {
					clloect.remove(i);
				}
			}
			madapter = new CollectAdapter();
			cllectview.setAdapter(madapter);
		}
	
	}

	private void setData() {
		CollectionModel c = new CollectionModel();
		c.Color = mColor;
		c.Size = mSize;
		c.GoodsColor = mGoodsColor;
		c.Name = mName;
		c.Price = mPrice;
		clloect.add(c);
			
	}
	
	class CollectAdapter extends BaseAdapter{

		@Override
		public int getCount() {

			return clloect.size();
		}

		@Override
		public Object getItem(int position) {
		
			return clloect.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			View view = CollectiionActivity.this.getLayoutInflater().inflate(R.layout.view_collection_item, null);

			ImageView imagecolor = (ImageView) view.findViewById(R.id.img_collect);
			TextView  name = (TextView) view.findViewById(R.id.tv_collect_title);
			TextView  price= (TextView) view.findViewById(R.id.tv_collectaccount);
			TextView  size = (TextView) view.findViewById(R.id.tv_data);
			TextView  color= (TextView) view.findViewById(R.id.tv_collect_color);
			Button  delete= (Button) view.findViewById(R.id.btn_delete);
			

			CollectionModel p = clloect.get(position);
			name.setText(p.Name);
			price.setText("￥"+p.Price);	
			size.setText(p.Size);
			color.setText(p.Color);
			getGoodsImage(imagecolor, p.GoodsColor);
			
			delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					clloect.remove(position);
					cllectview.setAdapter(null);
					cllectview.setAdapter(madapter);
					if(clloect.size() < 1) {
						   info.setText("收藏还是空的");	
						}
				}
			});
				
            return view;
		}
		
	}
	
	  private void getGoodsImage(ImageView view,String image) {	 
			String url = "http://bulo2bulo.com"+image+"_L.jpg";			
			ImageLoader loader = RequestQueueSingleton.getInstance(CollectiionActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(view, R.drawable.ic_launcher,  R.drawable.ic_launcher),100,100);
		}

}
