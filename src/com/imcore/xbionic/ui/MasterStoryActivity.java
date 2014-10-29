package com.imcore.xbionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.imcore.xbionic.model.Story;

public class MasterStoryActivity extends Activity {
	private ListView lvStory;
	List<Story> listStory = new ArrayList<Story>();
	StoryAdapter storyAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_story);
		lvStory = (ListView) findViewById(R.id.lv_story);
		LoadStory();
		// 返回键
		Button btnStoryBack = (Button) findViewById(R.id.btn_story_back);
		btnStoryBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		/*
		 * //搜索按钮 Button
		 * btnStorySearch=(Button)findViewById(R.id.btn_story_search);
		 * btnStorySearch.setOnClickListener(new On)
		 */

	}

	public List<Story> LoadStory() {
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "testteam/list.do?offset=0", new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

				listStory = JsonUtil.toObjectList(response, Story.class);

				if (storyAdapter == null) {
					storyAdapter = new StoryAdapter(MasterStoryActivity.this,
							listStory);
					lvStory.setAdapter(storyAdapter);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(MasterStoryActivity.this, error.getMessage(),
						5000).show();

			}
		});
		RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
		return listStory;

	}

	private class StoryAdapter extends BaseAdapter {
		LayoutInflater inflater;
		private Context mContext;

		List<Story> listStory;

		public StoryAdapter(Context c, List<Story> listStory) {
			mContext = c;
			inflater = LayoutInflater.from(c);
			this.listStory = listStory;
		}

		@Override
		public int getCount() {

			return listStory.size();
		}

		@Override
		public Object getItem(int position) {

			return listStory.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View converView, ViewGroup parent) {
			View childView;
			childView = inflater.from(MasterStoryActivity.this).inflate(
					R.layout.view_item_story, null);
			ImageView ivStory = (ImageView) childView
					.findViewById(R.id.img_story);
			TextView tvStoryTime = (TextView) childView
					.findViewById(R.id.tv_story_time);
			TextView tvStoryTitle = (TextView) childView
					.findViewById(R.id.tv_story_title);

			tvStoryTime.setText(this.listStory.get(position).updateDate);
			tvStoryTitle.setText(this.listStory.get(position).title);

			String url = HttpUtil.BASE_Img2 + listStory.get(position).phoneUrl
					+ "_N.jpg" + "";

			ImageLoader loader = RequestQueueSingleton.getInstance(
					MasterStoryActivity.this).getImageLoader();
			loader.get(url, ImageLoader.getImageListener(ivStory,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 300);

			return childView;
		}
	}
}
