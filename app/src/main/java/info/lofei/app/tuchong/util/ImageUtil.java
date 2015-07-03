package info.lofei.app.tuchong.util;

import android.graphics.Bitmap;
import android.os.Build;

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
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    }
}
