package madan.com.test.project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by 山东娃 on 2016/3/4.
 */
public class VolleyUtil {
    private static VolleyUtil volleyUtil;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Context context;


    private VolleyUtil(Context context){
        this.context = context.getApplicationContext();
        requestQueue = getRequestQueue(this.context);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String,Bitmap> cache = new LruCache<>(100);
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

    public static VolleyUtil getInstance(Context context){
        if(volleyUtil == null){
            volleyUtil = new VolleyUtil(context);
        }
        return volleyUtil;
    }


    public RequestQueue getRequestQueue(Context context) {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public <T>void  addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
