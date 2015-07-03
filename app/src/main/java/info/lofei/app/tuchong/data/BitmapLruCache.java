package info.lofei.app.tuchong.data;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import info.lofei.app.tuchong.util.ImageUtil;

/**
 * BitmapLruCache.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-25 23:29
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BitmapLruCache(final int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(final String key, final Bitmap value) {
        return ImageUtil.getBitmapSize(value);
    }

    @Override
    public Bitmap getBitmap(final String url) {
        return get(url);
    }

    @Override
    public void putBitmap(final String url, final Bitmap bitmap) {
        put(url, bitmap);
    }
}
