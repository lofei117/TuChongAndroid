package info.lofei.app.tuchong.utils;

import android.graphics.Bitmap;

/**
 * ImageUtil.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-26 10:30
 */
public class ImageUtil {

    public static int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
