package info.lofei.app.tuchong.fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.AppManager;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.LoginActivity;
import info.lofei.app.tuchong.adapter.CategoryAdapter;
import info.lofei.app.tuchong.data.RequestManager;
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

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //#endregion

    private Activity mMainActivity;

    private CategoryAdapter mAdapter;

    private List<TCPost> mTCPostList;

    private String mCategory;
    private boolean isLoadFinished;

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

            setupRecyclerView();
            setupSwipeRefreshLayout();

            setupData();
        }
        return view;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mMainActivity = activity;
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutmanger = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutmanger.getItemCount() - layoutmanger.findLastCompletelyVisibleItemPosition() <= Constant.PAGE_COUNT / 2 && !isLoadFinished) {
                    loadData(false);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
            isLoadFinished = false;
            mSwipeRefreshLayout.setRefreshing(true);
            mTCPostList.clear();
        }

        String categoryEncode = null;
        try {
            categoryEncode = URLEncoder.encode(mCategory, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(TuChongApi.CATEGORY_URL, categoryEncode , isRefresh ? "last" : "next", Constant.PAGE_COUNT);

        executeRequest(new GetCategoryPosts(url, new Response.Listener<List<TCPost>>() {
            @Override
            public void onResponse(final List<TCPost> response) {
                if (!isDetached()) {
                    mTCPostList.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if(response == null || response.isEmpty()){
                    isLoadFinished = true;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                if (!isDetached()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (error instanceof AuthFailureError) {
                        RequestManager.cancelAll(this);
                        AppManager.getInstance().finishAllActivities();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }
            }
        }));
    }

}
