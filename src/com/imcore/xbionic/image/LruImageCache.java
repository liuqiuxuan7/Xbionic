package com.imcore.xbionic.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * �ṩ��Volley����е�ImageLoaderʹ�õ�ͼƬ�ڴ滺��
 */
public class LruImageCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	
	public LruImageCache (Context ctx) {
		int maxSize = getCacheSize(ctx);
		mCache = new LruCache<String, Bitmap>(maxSize);
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}
	
	private int getCacheSize(Context ctx) {
		final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		final int screenWidth = dm.widthPixels;
		final int screenHeight = dm.heightPixels;
		final int screenBytes = screenWidth * screenHeight * 4;
		
		return screenBytes * 3;
	}
}
