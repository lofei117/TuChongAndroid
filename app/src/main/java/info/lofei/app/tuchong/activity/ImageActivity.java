package info.lofei.app.tuchong.activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.data.request.FavoriteRequest;
import info.lofei.app.tuchong.data.request.result.FavoriteResult;
import info.lofei.app.tuchong.model.TCExif;
import info.lofei.app.tuchong.model.TCImage;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.utils.NumberUtil;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * 图片详情页.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-08-04 10:45
 */
public class ImageActivity extends BaseActivity {

    public static final String BUNDLE_EXTRA_IMAGE = "bundle_extra_image";

    public static final String BUNDLE_EXTRA_POST = "bundle_extra_post";

    //region View region
    @Bind(R.id.iv_photo)
    public ImageView mImage;

    @Bind(R.id.appbar)
    public AppBarLayout mAppBarLayout;

    @Bind(R.id.ll_bottom_bar)
    public View mBottomBarLayout;

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.tv_favorite_count)
    TextView mTvFavoriteCount;

    @Bind(R.id.tv_comment_count)
    TextView mTvCommentCount;
    //endregion

    private TCImage mTCImage;

    private TCPost mTCPost;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
    }

    private void updateViews() {
        if (mTCPost != null) {
            mTvFavoriteCount.setEnabled(true);
            if (mTCPost.isFavorite()) {
                mTvFavoriteCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_active, 0, 0, 0);
            } else {
                mTvFavoriteCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_normal, 0, 0, 0);
            }
            mTvFavoriteCount.setText(String.valueOf(mTCPost.getFavoriteCount()));
            mTvCommentCount.setText(String.valueOf(mTCPost.getCommentCount()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_detail:
                showDetailDialog();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showDetailDialog() {
        TCExif tcExif = mTCImage.getExif();
        if (tcExif != null) {
            Toast.makeText(ImageActivity.this, tcExif.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            mTCImage = (TCImage) getIntent().getSerializableExtra(BUNDLE_EXTRA_IMAGE);
            mTCPost = (TCPost) getIntent().getSerializableExtra(BUNDLE_EXTRA_POST);
            final String url = String.format(TuChongApi.PHOTO_URL_LARGE, mTCPost.getAuthorId(),
                    mTCImage.getImageId());
            RequestManager.loadImage(url, RequestManager.getImageListener(mImage, null, null));
        }
        updateViews();
        mTvFavoriteCount.post(new Runnable() {
            @Override
            public void run() {
                toggleBarsVisibility();
            }
        });
    }

    @OnClick({R.id.iv_photo, R.id.tv_favorite_count, R.id.tv_comment_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                toggleBarsVisibility();
                break;
            case R.id.tv_favorite_count:
                doFavoriteAction();
                break;
            case R.id.tv_comment_count:
                doCommentAction();
                break;
            default:
                break;
        }
    }

    private void doCommentAction() {

    }

    private void doFavoriteAction() {
        final String url = String.format(TuChongApi.FAVORITE_POST_URL, mTCPost.getPostId());
        int method = mTCPost.isFavorite() ? Request.Method.DELETE : Request.Method.PUT;
        mTvFavoriteCount.setEnabled(false);
        executeRequest(new FavoriteRequest(method, url,
                new Response.Listener<FavoriteResult>() {

                    @Override
                    public void onResponse(FavoriteResult response) {
                        if (response != null) {
                            mTCPost.setFavorite(response.isFavorited());
                            mTCPost.setFavoriteCount(NumberUtil.toInt(response.getFavoriteCount()));
                            updateViews();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateViews();
                    }
                }
        ));
    }

    private void toggleBarsVisibility() {
        final float y1 = mAppBarLayout.getVisibility() == View.VISIBLE ? -mAppBarLayout.getHeight() : 0;
        mAppBarLayout.animate().translationY(y1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAppBarLayout.setVisibility(y1 < 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAppBarLayout.setVisibility(View.VISIBLE);
            }
        }).start();

        final float y2 = mBottomBarLayout.getVisibility() == View.VISIBLE ? mBottomBarLayout.getHeight() : 0;
        mBottomBarLayout.animate().translationY(y2).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBottomBarLayout.setVisibility(y2 > 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBottomBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
