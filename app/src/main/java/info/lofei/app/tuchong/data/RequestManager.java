package info.lofei.app.tuchong.data;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import info.lofei.app.tuchong.BaseApplication;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-25 23:05
 */
public class RequestManager {

    private static RequestQueue sRequestQueue = newRequestQueue();

    // 取运行内存阈值的1/3作为图片缓存
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) BaseApplication.getBaseApplication()
            .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;

    private static ImageLoader sImageLoader = new ImageLoader(sRequestQueue, new BitmapLruCache(
            MEM_CACHE_SIZE));

    private static DiskBasedCache sDiskCache = (DiskBasedCache) sRequestQueue.getCache();

    private static RequestQueue newRequestQueue() {
        VolleyLog.DEBUG = true;
        RequestQueue requestQueue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
        CookieManager manager = new CookieManager(new SimpleCookieStore(), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(manager);
        return requestQueue;
    }

    private static Cache openCache() {
        return new DiskBasedCache(BaseApplication.getBaseApplication().getExternalCacheDir(),
                10 * 1024 * 1024);
    }

    public static void addRequest(Request request, Object tag) {
        if (request != null) {
            request.setTag(tag);
        }
        sRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        sRequestQueue.cancelAll(tag);
    }

    public static ImageLoader.ImageContainer loadImage(String url, ImageLoader.ImageListener listener) {
        return loadImage(url, listener, 0, 0);
    }

    public static ImageLoader.ImageContainer loadImage(String url, ImageLoader.ImageListener listener, int maxWidth, int maxHeight) {
        return sImageLoader.get(url, listener, maxWidth, maxHeight);
    }

    public static ImageLoader.ImageListener getImageListener(final ImageView view,
                                                             final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageDrawable != null) {
                    view.setImageDrawable(errorImageDrawable);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    if (!isImmediate && defaultImageDrawable != null) {
                        TransitionDrawable transitionDrawable = new TransitionDrawable(
                                new Drawable[]{
                                        defaultImageDrawable,
                                        new BitmapDrawable(BaseApplication.getBaseApplication().getResources(),
                                                response.getBitmap())
                                });
                        transitionDrawable.setCrossFadeEnabled(true);
                        view.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(100);
                    } else {
                        view.setImageBitmap(response.getBitmap());
                    }
                } else if (defaultImageDrawable != null) {
                    view.setImageDrawable(defaultImageDrawable);
                }
            }
        };
    }
}