package info.lofei.app.tuchong.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;

/**
 * 图片详情页.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-08-04 10:45
 */
public class ImageActivity extends BaseActivity {

    public static final String BUNDLE_EXTRA_IMAGE_URL = "bundle_extra_image_url";

    @Bind(R.id.iv_photo)
    public ImageView mImage;

    private String mImageUrl;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            mImageUrl = getIntent().getStringExtra(BUNDLE_EXTRA_IMAGE_URL);
            RequestManager.loadImage(mImageUrl, RequestManager.getImageListener(mImage, null, null));
        }
    }

    @OnClick(R.id.iv_photo)
    public void onClick() {
        finish();
    }
}
