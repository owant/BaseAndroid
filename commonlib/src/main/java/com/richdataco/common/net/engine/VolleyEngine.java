package com.richdataco.common.net.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.richdataco.common.BaseUtil;

/**
 * Created by OlaWang on 2017/5/19.
 */

public class VolleyEngine {

    private static VolleyEngine sVolleyEngine;
    private static Context sContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyEngine(Context context) {

        this.sContext = BaseUtil.getApplicationContext(context);
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {

                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public static synchronized VolleyEngine getInstance(Context context) {
        if (sVolleyEngine == null) {
            sVolleyEngine = new VolleyEngine(context);
        }
        return sVolleyEngine;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(sContext);
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
