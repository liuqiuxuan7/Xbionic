package com.imcore.xbionic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.imcore.xbionic.R;
import com.imcore.xbionic.http.DataRequest;
import com.imcore.xbionic.http.HttpUtil;
import com.imcore.xbionic.http.JsonUtil;
import com.imcore.xbionic.image.RequestQueueSingleton;
import com.imcore.xbionic.model.CommentsModel;
import com.imcore.xbionic.ui.ProductCommentsActivity;
import com.imcore.xbionic.util.DisplayUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentComment extends Fragment implements OnClickListener{
	View view;
	ListView lv_comments;
	List<CommentsModel> listComModels = new ArrayList<CommentsModel>();
	CommentAdaper cadapter;
	String id;
	int IsFistLoad = 1;
	ProgressDialog progressDialog = null;
	private Button btn_comm;
	
	  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  view = inflater.inflate(R.layout.framgent_comments, null);

		    initWidgets();
			FragmentActivity activity = getActivity();
			Intent intent = activity.getIntent();
			Bundle bundle = intent.getExtras();
			id = bundle.getString("id");
			//id = bundle.getInt("id");
			loadComments();
			return view;
	  }
	  private void initWidgets() {
			lv_comments = (ListView) view.findViewById(R.id.lv_comments);
			btn_comm=(Button) view.findViewById(R.id.btn_comments);
			btn_comm.setOnClickListener(this);
		}
	  
	  private List<CommentsModel> loadComments() {
			if (IsFistLoad == 1) {
				progressDialog = ProgressDialog.show(getActivity(), "请稍等...",
						"正在加载中...", true);
				IsFistLoad = 0;
			}
			DataRequest request = new DataRequest(Method.GET, HttpUtil.BASE_URL
					+ "product/comments/list.do?id=" + id,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							listComModels = JsonUtil.toObjectList(response,
									CommentsModel.class);
							if (cadapter == null) {
								cadapter = new CommentAdaper(getActivity(),
										listComModels);
								lv_comments.setAdapter(cadapter);
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
			return listComModels;
		}
	 

		private class CommentAdaper extends BaseAdapter {
			private Context mContext;
			LayoutInflater inflater;

			public CommentAdaper(Context context, List<CommentsModel> listComModels) {
				mContext = context;
				inflater = LayoutInflater.from(mContext);
			}

			@Override
			public int getCount() {
				return listComModels.size();
			}

			@Override
			public Object getItem(int position) {

				return listComModels.get(position);
			}

			@Override
			public long getItemId(int position) {

				return position;
			}

			@Override
			public View getView(int position, View contenView, ViewGroup parent) {
				View view = contenView;
				ViewHolder vh = null;
				if (view == null) {
					view = inflater.inflate(R.layout.view_item_comments, null);
					vh = new ViewHolder();
					vh.tv_usercomments = (TextView) view.findViewById(R.id.tv_usercomments);
					vh.tv_time = (TextView) view.findViewById(R.id.tv_time);
					vh.tv_name = (TextView) view.findViewById(R.id.tv_name);
				//	vh.rg_start = (RadioGroup) view.findViewWithTag(R.id.rg_start);
					view.setTag(vh);
				} else {
					vh = (ViewHolder) view.getTag();
				}
				vh.tv_usercomments.setText(listComModels.get(position).comment);
				vh.tv_time.setText(listComModels.get(position).commentDate);
				vh.tv_name.setText(listComModels.get(position).userName);

				int imagestar = listComModels.get(position).star;
				for (int count = 0; count < imagestar; count++) {
					ImageView imagestart = new ImageView(getActivity());
					RadioGroup.LayoutParams lps = new RadioGroup.LayoutParams(
							DisplayUtil.dip2Px(getActivity(), 24),
							DisplayUtil.dip2Px(getActivity(), 24));
					lps.setMargins(8, 0, 0, 8);
					imagestart.setLayoutParams(lps);
				//	imagestart.setImageDrawable(getResources().getDrawable(
					//		R.drawable.start));
				//	vh.rg_start.addView(imagestart);

				}

				return view;
			}
		}

		private class ViewHolder {
			TextView tv_usercomments;
			TextView tv_time;
			TextView tv_name;
		//	RadioGroup rg_start;

		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_comments:
				Intent intent=new Intent(getActivity(),ProductCommentsActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.anim_right_in,
						R.anim.anim_left_out);
				break;

			default:
				break;
			}
			
		}

		
}
