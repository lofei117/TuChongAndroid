package info.lofei.app.tuchong.fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.adapter.CategoryAdapter;
import info.lofei.app.tuchong.data.request.GetCategoryPosts;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.utils.Constant;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * 各分类列表.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-10-17 10:00
 */
public class CategoryFragment extends BaseFragment {

    private static final String BUNDLE_ARG_CATEGORY = "bundle_arg_category";

    private static final String TAG = CategoryFragment.class.getSimpleName();

    //region View region
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //#endregion

    private MainActivity mMainActivity;

    private CategoryAdapter mAdapter;

    private List<TCPost> mTCPostList;

    private String mCategory;

    public static CategoryFragment newInstance(String category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);

            setupFloatActionButton();
            setupRecyclerView();
            setupSwipeRefreshLayout();

            setupData();
        }
        return view;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivity = null;
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mMainActivity));
        if (mAdapter == null) {
            mAdapter = new CategoryAdapter(mMainActivity);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupFloatActionButton() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadData(true);
            }
        });
    }

    private void setupData() {
        if (getArguments() != null) {
            mCategory = getArguments().getString(BUNDLE_ARG_CATEGORY);
        }
        if (mTCPostList == null) {
            mTCPostList = new ArrayList<>(20);
            mAdapter.fillData(mTCPostList);
        }
        loadData(true);
    }

    public void setCategory(String category) {
        mCategory = category;
        loadData(true);
    }

    private void loadData(final boolean isRefresh) {
        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(true);
            mTCPostList.clear();
        }
        String url = String.format(TuChongApi.CATEGORY_URL, mCategory, isRefresh ? "last" : "next", Constant.PAGE_COUNT);
        executeRequest(new GetCategoryPosts(url, new Response.Listener<List<TCPost>>() {
            @Override
            public void onResponse(final List<TCPost> response) {
                if (!isDetached()) {
                    mTCPostList.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                if (!isDetached()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (error instanceof AuthFailureError) {
                        mMainActivity.loginRequired();
                    }
                }
            }
        }));
    }

}
