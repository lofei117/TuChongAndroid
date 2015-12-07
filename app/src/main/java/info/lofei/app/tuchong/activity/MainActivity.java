package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.lofei.app.tuchong.AppManager;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.data.request.GetSiteRequest;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.fragment.CategoryFragment;
import info.lofei.app.tuchong.fragment.PostDetailFragment;
import info.lofei.app.tuchong.fragment.MainFragment;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.utils.NumberUtil;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.vendor.TuChongApi;


public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.profile_image)
    CircleImageView profileImageView;

    @Bind(R.id.username)
    TextView mUserName;

    @Bind(R.id.following)
    TextView mFollowing;

    @Bind(R.id.follower)
    TextView mFollower;

    @Bind(R.id.description)
    TextView mDescription;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private MainFragment mMainFragment;

    private CategoryFragment mCategoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupDrawerView();
        setupToolbar();
        setupDrawerLayout();

        getUserInfo();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMainFragment = MainFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, mMainFragment).commit();
    }

    private void getUserInfo(){
        String currentUserId = PreferenceUtil.getString(LoginRequest.DATA_SAVE_TUCHONG_CURRENT_USER_ID, "");
        if(TextUtils.isEmpty(currentUserId)){
            loginRequired();
        }

        String url = String.format(TuChongApi.SITE_URL, currentUserId);

        executeRequest(new GetSiteRequest(url, new Response.Listener<TCSite>() {
            @Override
            public void onResponse(TCSite site) {
                if (site != null) {
                    RequestManager.loadImage(site.getIcon(),
                            RequestManager.getImageListener(profileImageView, null, null));
                    mUserName.setText(site.getName());
                    mFollower.setText(getString(R.string.str_drawer_follower, site.getFollowers()));
                    mFollowing.setText(getString(R.string.str_drawer_following, NumberUtil.toInt(site.getFollowing())));
                    mDescription.setText(site.getDescription());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    loginRequired();
                }
            }
        }));

    }


    private void setupDrawerView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_activity:
                                if (mMainFragment == null) {
                                    mMainFragment = MainFragment.newInstance();
                                }
                                launchMainFragment();
                                break;
                            default:
                                showCategoryFragment(menuItem.getTitle().toString());

                                break;
                        }
                        return true;
                    }
                });
    }

    public void showCategoryFragment(String category) {
        if (mCategoryFragment == null || !mCategoryFragment.isVisible()) {
            mCategoryFragment = CategoryFragment.newInstance(category);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, mCategoryFragment).commitAllowingStateLoss();
        } else {
            mCategoryFragment.setCategory(category);
        }
    }

    public void launchMainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mMainFragment).commitAllowingStateLoss();
    }

    public void loginRequired() {
        RequestManager.cancelAll(this);
        AppManager.getInstance().finishAllActivitis();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void launchDetailFragment(final TCPost tcPost, final View view) {
        PostDetailActivity.launch(this, tcPost);
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //PostDetailFragment fragment = PostDetailFragment.newInstance(tcPost);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Slide slide = new Slide(Gravity.BOTTOM);
//            slide.setDuration(200);
//
//            ChangeBounds changeBounds = new ChangeBounds();
//            changeBounds.setDuration(200);
//
//            fragment.setEnterTransition(slide);
//            fragment.setAllowEnterTransitionOverlap(true);
//            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBounds);
//            String transitionName = getString(R.string.transition_image);
//            Log.d("test", transitionName);
////            view.setTransitionName(transitionName);
////            ViewCompat.setTransitionName(view, getString(R.string.transition_image));
////            fragmentTransaction.addSharedElement(view, transitionName);
////            supportPostponeEnterTransition();
//        }

        //fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(PostDetailFragment.class.getSimpleName()).commitAllowingStateLoss();
//        mActionBarDrawerToggle.onDrawerSlide(null, 0.5f);
//        getSupportFragmentManager().executePendingTransactions();
    }

}
