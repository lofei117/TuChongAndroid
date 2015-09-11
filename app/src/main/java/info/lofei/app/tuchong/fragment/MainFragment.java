package info.lofei.app.tuchong.fragment;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.adapter.MainAdapter;
import info.lofei.app.tuchong.data.request.GetActivities;
import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.util.Constant;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-02 22:19
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MainActivity mMainActivity;

    private MainAdapter mAdapter;

    private List<TCActivity> mTCActivitiesList;

    private String mCurrentUrl = TuChongApi.ACTIVITY_LIST_URL;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        setupFloatActionButton();
        setupRecyclerView();
        setupSwipeRefreshLayout();

        setupData();

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
        if (mTCActivitiesList == null) {
            mTCActivitiesList = new ArrayList<>(20);
            mAdapter.fillData(mTCActivitiesList);
        }
        loadData(false);
    }

    public void loadData(String url) {
        mCurrentUrl = url;
        loadData(true);
    }

    private void loadData(final boolean isRefresh) {
        int offset = isRefresh ? 0 : mTCActivitiesList.size();
        String url = String.format(mCurrentUrl, Constant.PAGE_COUNT, offset);
        executeRequest(new GetActivities(url, new Response.Listener<List<TCActivity>>() {
            @Override
            public void onResponse(final List<TCActivity> response) {
                if (!isDetached()) {
                    if (isRefresh) {
                        mTCActivitiesList.clear();
                    }
                    mTCActivitiesList.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                if (isDetached()) {
                    return;
                }
                mSwipeRefreshLayout.setRefreshing(false);
                if (error instanceof AuthFailureError) {
                    mMainActivity.loginRequired();
                }
                // TODO other error
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
