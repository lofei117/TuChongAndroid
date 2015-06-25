package info.lofei.app.tuchong.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import info.lofei.app.tuchong.R;


/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-18 11:09
 */
public class FixedAspectRatioView extends FrameLayout {

    private int mAspectRatioWidth = 0;

    private int mAspectRatioHeight = 0;


    public FixedAspectRatioView(Context context) {
        super(context);
    }

    public FixedAspectRatioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixedAspectRatioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioView);

        mAspectRatioWidth = a.getInt(R.styleable.FixedAspectRatioView_aspectRatioWidth, 0);
        mAspectRatioHeight = a.getInt(R.styleable.FixedAspectRatioView_aspectRatioHeight, 0);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mAspectRatioHeight == 0 || mAspectRatioWidth == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int originalWidth = MeasureSpec.getSize(widthMeasureSpec);

            int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

            int calculatedHeight = originalWidth * mAspectRatioHeight / mAspectRatioWidth;

            int finalWidth, finalHeight;

            if (calculatedHeight > originalHeight) {
                finalWidth = originalHeight * mAspectRatioWidth / mAspectRatioHeight;
                finalHeight = originalHeight;
            } else {
                finalWidth = originalWidth;
                finalHeight = calculatedHeight;
            }

            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
        }
    }
}
