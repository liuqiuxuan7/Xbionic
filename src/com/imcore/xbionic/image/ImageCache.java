package com.imcore.xbionic.image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * ͼƬ�ڴ滺��
 * 
 * @author user
 */
public final class ImageCache {
	private LruCache<String, Bitmap> mCache;
	
	private static ImageCache mInstance;
	
	public static ImageCache getInstance() {
		if (mInstance == null) {
			mInstance = new ImageCache();
		}
		return mInstance;
	}

	private ImageCache() {
		int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		mCache = new LruCache<String, Bitmap>(maxSize);
	}

	public void put(String key, Bitmap value) {
		if (key == null || value == null) {
			throw new NullPointerException("key����value����Ϊ��");
		}
		mCache.put(key, value);
	}

	public Bitmap get(String key) {
		if (key == null) {
			throw new NullPointerException("key����Ϊ��");
		}
		return mCache.get(key);
	}

	public void remove(String key) {
		if (key == null) {
			throw new NullPointerException("key����Ϊ��");
		}
		mCache.remove(key);
	}
}
