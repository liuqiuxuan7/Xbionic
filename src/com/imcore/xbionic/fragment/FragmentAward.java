package com.imcore.xbionic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.imcore.xbionic.R;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.Honor;
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

public class FragmentAward extends Fragment {
	View view;
	List<Honor> listHonors = new ArrayList<Honor>();
	HonorAdapter honoradapter;
	ListView lv_honor;
	
	int IsFistLoad = 1;
	ProgressDialog progressDialog = null;

  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	  view = inflater.inflate(R.layout.framgent_award_win, null);
		init();
		loadHonorsList();
		return view;
  }
/*  private void initWidgets(View rootView) {
	    lv_news = (ListView) rootView.findViewById(R.id.lv_award);
		newsadapter = new NewsAdapter(getActivity(), listNews);
		lv_news.setAdapter(newsadapter);
		loadNewsList();
		//onHeaderRefresh(mPullToRefreshView);
	}
  */
	// 加载奖项列表
	private List<Honor> loadHonorsList() {
		if (IsFistLoad == 1) {
			progressDialog = ProgressDialog.show(getActivity(), "请稍等...",
					"正在加载中...", true);
			IsFistLoad = 0;
		}
		DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
				+ "honor/list.do?type=2&offset=" + page + "&fetchSize=10",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						List<Honor> list = JsonUtil.toObjectList(response,
								Honor.class);
						if (list != null && list.size() > 0) {
							if (isFlash) {
								listHonors.clear();
							}
							listHonors.addAll(list);
							honoradapter.notifyDataSetChanged();
						}
						progressDialog.dismiss();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), error.getMessage(), 5000)
								.show();
					}
				});
		RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(
				request);
		return listHonors;
	}

	private void init() {
		lv_honor = (ListView) view.findViewById(R.id.lv_award);
	
		honoradapter = new HonorAdapter(getActivity(), listHonors);
		lv_honor.setAdapter(honoradapter);
		// onHeaderRefresh(mPullToRefreshView);

	}

	private class HonorAdapter extends BaseAdapter {
		private Context mContext;
		LayoutInflater inflater;

		public HonorAdapter(Context context, List<Honor> listHonors) {
			mContext = context;
			inflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {

			return listHonors.size();
		}

		@Override
		public Object getItem(int position) {

			return listHonors.get(position);
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
				view = inflater.inflate(R.layout.view_item_award, null);
				vh = new ViewHolder();

				vh.ivHonorImage = (ImageView) view.findViewById(R.id.img_award);
				vh.tvHonorTitle = (TextView) view.findViewById(R.id.tv_award);
				vh.tvHonorTime = (TextView) view
						.findViewById(R.id.tv_award_time);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			// 图片地址
			String url = HttpUtil.BASE_Img2
					+ listHonors.get(position).imageUrl + "_S.jpg";
			// 下载图片
			ImageLoader imageLoader = RequestQueueSingleton.getInstance(
					getActivity()).getImageLoader();
			// 图片地址，默认图片，错误图片
			imageLoader.get(url, imageLoader.getImageListener(vh.ivHonorImage,
					R.drawable.ic_launcher, R.drawable.ic_launcher), 300, 200);
			vh.tvHonorTitle.setText(listHonors.get(position).title);
			vh.tvHonorTime.setText(listHonors.get(position).createDate);
			return view;
		}
	}

	private class ViewHolder {
		ImageView ivHonorImage;
		TextView tvHonorTitle;
		TextView tvHonorTime;
	}

	/**
	 * 刷新加载更多
	 */
	int page = 0;
	int count = 1;
	boolean isFlash = false;


	
}


