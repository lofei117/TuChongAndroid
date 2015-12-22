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
import info.lofei.app.tuchong.adapter.MainAdapter;
import info.lofei.app.tuchong.data.request.GetActivities;
import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.utils.Constant;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * 主界面，活动列表.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-02 22:19
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    //region View region
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //#endregion

    private boolean isLoadFinished;

    private MainActivity mMainActivity;

    private MainAdapter mAdapter;

    private List<TCActivity> mTCActivitiesList;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
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
            mAdapter = new MainAdapter(mMainActivity);
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(linearLayoutManager.getItemCount() - linearLayoutManager.findLastCompletelyVisibleItemPosition() < Constant.PAGE_COUNT / 2
                        && !isLoadFinished){
                    loadData(false);
                }

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
        if (mTCActivitiesList == null) {
            mTCActivitiesList = new ArrayList<>(20);
            mAdapter.fillData(mTCActivitiesList);
        }
        loadData(true);
    }

    private void loadData(final boolean isRefresh) {
        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(true);
            mTCActivitiesList.clear();
        }
        int offset = isRefresh ? 0 : mTCActivitiesList.size();
        String url = String.format(TuChongApi.ACTIVITY_LIST_URL, Constant.PAGE_COUNT, offset);
        executeRequest(new GetActivities(url, new Response.Listener<List<TCActivity>>() {
            @Override
            public void onResponse(final List<TCActivity> response) {
                if (!isDetached()) {
                    mTCActivitiesList.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if(response == null && response.isEmpty()){
                    isLoadFinished = true;
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
                    // TODO other error
                }
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
