package com.imcore.xbionic.image;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ImageWorker {
	private Context mContext;

	public ImageWorker(Context context) {
		mContext = context;
	}

	/**
	 * �첽���ر���ͼƬ����ʾ��ָ����ImageView
	 * 
	 * @param path
	 * 
	 * @param path ͼƬ����·��
	 * @param imageView Ҫ��ʾͼƬ��ImageView,��ImageView�趨��ȷ�����ʱʹ��
	 */
	public void fetch(String path, ImageView imageView) {
		if (cancelPotentialWork(path, imageView)) {
			ImageWorkerTask task = new ImageWorkerTask(path, imageView);
			AsyncDrawable ad = new AsyncDrawable(task);
			imageView.setImageDrawable(ad);
			task.execute();
		}
	}

	/**
	 * �첽���ر���ͼƬ����ʾ��ָ����ImageView
	 * 
	 * @param path ͼƬ����·��
	 * @param imageView Ҫ��ʾͼƬ��ImageView
	 * @param reqWidth ѹ��֮��Ŀ�
	 * @param reqHeight ѹ��֮��ĸ�
	 */
	public void fetch(String path, ImageView imageView, int reqWidth,
			int reqHeight) {
		if (cancelPotentialWork(path, imageView)) {
			ImageWorkerTask task = new ImageWorkerTask(path, imageView,
					reqWidth, reqHeight);
			AsyncDrawable ad = new AsyncDrawable(task);
			imageView.setImageDrawable(ad);
			task.execute();
		}
	}

	private boolean cancelPotentialWork(String path, ImageView imageView) {
		ImageWorkerTask task = getImageWorkerTask(imageView);
		if (task != null) {
			if (path != null && !"".equals(path)
					&& !path.equals(task.mImagePath)) {
				task.cancel(true);
			} else {
				// imageView.setImageBitmap(null);
				return false;
			}
		}
		return true;
	}

	private class ImageWorkerTask extends AsyncTask<Void, Void, Bitmap> {
		private WeakReference<ImageView> mWeakImageView;
		private int mReqWidth;
		private int mReqHeight;

		private String mImagePath;

		private ImageWorkerTask(String path, ImageView imageView) {
			mImagePath = path;
			mWeakImageView = new WeakReference<ImageView>(imageView);

			mReqWidth = imageView.getLayoutParams().width;
			mReqHeight = imageView.getLayoutParams().height;
		}

		private ImageWorkerTask(String path, ImageView imageView, int reqWidth,
				int reqHeight) {
			mImagePath = path;
			mWeakImageView = new WeakReference<ImageView>(imageView);
			mReqWidth = DensityUtil.dip2px(mContext, reqWidth);
			mReqHeight = DensityUtil.dip2px(mContext, reqHeight);
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			String path = mImagePath;
			Bitmap bm = ImageCache.getInstance().get(path);
			// cache missed
			if (bm == null) {
				bm = getBitmapFromLocal(path, mReqWidth, mReqHeight);
				ImageCache.getInstance().put(path, bm);
			}
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (mWeakImageView != null) {
				ImageView view = mWeakImageView.get();
				if (view != null && result != null) {
					if (getImageWorkerTask(view) == this) {
						view.setImageBitmap(result);
						Animation animation = AnimationUtils.loadAnimation(
								mContext, android.R.anim.fade_in);
						view.startAnimation(animation);
					}
				}
			}
		}
	}

	private class AsyncDrawable extends BitmapDrawable {
		private WeakReference<ImageWorkerTask> mWeakTask;

		@SuppressWarnings("deprecation")
		private AsyncDrawable(ImageWorkerTask task) {
			mWeakTask = new WeakReference<ImageWorker.ImageWorkerTask>(task);
		}

		private ImageWorkerTask getImageWorkerTask() {
			return mWeakTask.get();
		}
	}

	private ImageWorkerTask getImageWorkerTask(ImageView imageView) {
		if (imageView.getDrawable() instanceof AsyncDrawable) {
			AsyncDrawable ad = (AsyncDrawable) imageView.getDrawable();
			ImageWorkerTask task = ad.getImageWorkerTask();
			return task;
		}
		return null;
	}

	// �Ӵ洢����ȡָ��·����ͼƬ����Ϊһ��Bitmap���󷵻�
	private Bitmap getBitmapFromLocal(String path, int reqWidth, int reqHeight) {
		// ��ֵ��ȡͼƬ�Ŀ�ߵȱ߽���Ϣ
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		// ���ͼƬʵ�ʵı߽���Ϣ��������ѹ����
		int inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);

		// ���ѹ���ȼ������ͼƬ
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = inSampleSize;
		Bitmap bm = BitmapFactory.decodeFile(path, opts);

		return bm;
	}

	// ����ѹ����
	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			final float totalPixels = width * height;
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;
			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}
}
