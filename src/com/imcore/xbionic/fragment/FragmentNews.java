package com.imcore.xbionic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.imcore.xbionic.R;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.News;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentNews extends Fragment {

	NewsAdapter newsadapter;
	View view;
	List<News> listNews=new ArrayList<News>();
	ListView lv_news;
	int IsFistLoad = 1;
	ProgressDialog progressDialog = null;
	
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.framgent_award_win, null);
		
		initWidgets(rootView);
		return rootView;
	  }
	 
	 private void initWidgets(View rootView) {
		    lv_news = (ListView) rootView.findViewById(R.id.lv_award);
			newsadapter = new NewsAdapter(getActivity(), listNews);
			lv_news.setAdapter(newsadapter);
			loadNewsList();
			//onHeaderRefresh(mPullToRefreshView);
		}
	 
	//加载新闻列表
		private List<News> loadNewsList() {
			if (IsFistLoad==1) {
				progressDialog = ProgressDialog.show(getActivity(),
						"请稍等...", "正在加载中...", true);
				IsFistLoad=0;
			}
			DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
					+ "news/list.do?type=2&offset="+page+"&fetchSize=10", new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					List<News> list = JsonUtil.toObjectList(response, News.class);
					if (list != null && list.size() > 0) {
						if (isFlash) {
							listNews.clear();
						}
						listNews.addAll(list);
						newsadapter.notifyDataSetChanged();
					}
					progressDialog.dismiss();
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(getActivity(), error.getMessage(), 5000).show();
				}
			});
			RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(
					request);
			return listNews;
		}

		// 新闻适配器

		private class NewsAdapter extends BaseAdapter {
			private Context mContext;
			LayoutInflater inflater;

			public NewsAdapter(Context context, List<News> listNews) {
				mContext = context;
				inflater = LayoutInflater.from(mContext);
			}

			@Override
			public int getCount() {

				return listNews.size();
			}

			@Override
			public Object getItem(int position) {

				return listNews.get(position);
			}

			@Override
			public long getItemId(int position) {

				return position;
			}

			@SuppressWarnings("static-access")
			@Override
			public View getView(int position, View contenView, ViewGroup parent) {
				View view = contenView;
				ViewHolder vh = null;
				if (view == null) {
					view = inflater.inflate(R.layout.view_item_news, null);
					vh = new ViewHolder();

					vh.ivNewsImage = (ImageView) view.findViewById(R.id.img_news);
					vh.tvNewTitle = (TextView) view.findViewById(R.id.tv_news);
					vh.tvNewTime = (TextView) view.findViewById(R.id.tv_news_time);
					view.setTag(vh);
				} else {
					vh = (ViewHolder) view.getTag();
				}

				// 图片地址
				String url = HttpUtil.BASE_Img2
						+ listNews.get(position).imageUrl + "_M.jpg";
				// 下载图片
				ImageLoader imageLoader = RequestQueueSingleton.getInstance(
						getActivity()).getImageLoader();
				// 图片地址，默认图片，错误图片
				imageLoader.get(url, imageLoader.getImageListener(vh.ivNewsImage,
						R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 200);
				vh.tvNewTitle.setText(listNews.get(position).title);
				vh.tvNewTime.setText(listNews.get(position).newsDate);
				return view;
			}
		}

		private class ViewHolder {
			ImageView ivNewsImage;
			TextView tvNewTitle;
			TextView tvNewTime;
		}

		/**
		 * 刷新加载更多
		 */
		int page = 0;
		int count = 1;
		boolean isFlash = false;


}
