package info.lofei.app.tuchong.activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.data.request.FavoriteRequest;
import info.lofei.app.tuchong.data.request.result.FavoriteResult;
import info.lofei.app.tuchong.fragment.BaseFragment;
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

    public static final String BUNDLE_EXTRA_IMAGE_INDEX = "bundle_extra_image_index";

    public static final String BUNDLE_EXTRA_POST = "bundle_extra_post";

    //region View region
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

    @Bind(R.id.vp_image)
    ViewPager mViewPager;
    //endregion

    private int mCurrentImageIndex = 0;

    private TCPost mTCPost;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setupToolbar();
        setupViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupViews() {
        mAdapter = new MyAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
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
        TCImage tcImage = mTCPost.getImages().get(mViewPager.getCurrentItem());
        TCExif tcExif = tcImage == null ? null : tcImage.getExif();
        if (tcExif != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(tcExif.getCamera().getName()).append("\n");
            sb.append(tcExif.getExposure()).append("\n");
            sb.append(tcExif.getLens().getName()).append("\n");
            sb.append(tcExif.getTakenTime()).append("\n");
            Toast.makeText(ImageActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            mCurrentImageIndex = getIntent().getIntExtra(BUNDLE_EXTRA_IMAGE_INDEX, 0);
            mTCPost = (TCPost) getIntent().getSerializableExtra(BUNDLE_EXTRA_POST);
            mCurrentImageIndex = mCurrentImageIndex % mTCPost.getImageCount();
            mAdapter.setData(mTCPost);
            mViewPager.setCurrentItem(mCurrentImageIndex);
        }
        updateViews();
        mTvFavoriteCount.post(new Runnable() {
            @Override
            public void run() {
                toggleBarsVisibility();
            }
        });
    }

    @OnClick({R.id.tv_favorite_count, R.id.tv_comment_count})
    public void onClick(View view) {
        switch (view.getId()) {
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

    public static class MyAdapter extends FragmentStatePagerAdapter {

        private TCPost mTCPost;

        private ImageActivity mActivity;

        public MyAdapter(FragmentManager fm, ImageActivity activity) {
            super(fm);
            mActivity = activity;
        }

        @Override
        public int getCount() {
            return mTCPost == null ? 0 : mTCPost.getImageCount();
        }

        private String getImageUrl(int position) {
            List<TCImage> images = mTCPost.getImages();
            final String url = String.format(TuChongApi.PHOTO_URL_LARGE, mTCPost.getAuthorId(),
                    images.get(position).getImageId());
            return url;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(getImageUrl(position), mActivity);
        }

        public void setData(TCPost tcPost) {
            mTCPost = tcPost;
            notifyDataSetChanged();
        }
    }

    public static class ImageFragment extends BaseFragment {

        private static final String BUNDLE_ARG_IMAGE_URL = "bundle_arg_image_url";

        private WeakReference<ImageActivity> mActivityWeakReference;

        @Bind(R.id.iv_photo)
        ImageView mIvPhoto;

        public static ImageFragment newInstance(String imageUrl, ImageActivity activity) {
            ImageFragment fragment = new ImageFragment();
            fragment.setActivity(activity);
            Bundle args = new Bundle();
            args.putString(BUNDLE_ARG_IMAGE_URL, imageUrl);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            if (getArguments() != null) {
                String url = getArguments().getString(BUNDLE_ARG_IMAGE_URL);
                RequestManager.loadImage(url, RequestManager.getImageListener(mIvPhoto, null, null));
            }
        }

        @OnClick(R.id.iv_photo)
        public void onClick() {
            if (mActivityWeakReference != null) {
                ImageActivity activity = mActivityWeakReference.get();
                if (activity != null) {
                    activity.toggleBarsVisibility();
                }
            }
        }

        public void setActivity(ImageActivity activity) {
            mActivityWeakReference = new WeakReference<ImageActivity>(activity);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.unbind(this);
        }
    }
}
