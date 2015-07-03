package info.lofei.app.tuchong.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.adapter.MainAdapter;
import info.lofei.app.tuchong.data.MainRequest;
import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-02 22:19
 */
public class MainFragment extends BaseFragment {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @InjectView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private MainActivity mMainActivity;

    private MainAdapter mAdapter;

    private List<TCActivity> mTCActivitiesList;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.inject(this, view);

        setupFloatActionButton();
        setupRecyclerView();

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
        mAdapter = new MainAdapter(mMainActivity);
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


    private void setupData() {
        mTCActivitiesList = new ArrayList<>(20);
        String url = String.format(TuChongApi.ACTIVITY_LIST_URL, 20, 0);
        executeRequest(new MainRequest(url, new Response.Listener<List<TCActivity>>() {
            @Override
            public void onResponse(final List<TCActivity> response) {
                if(!isDetached()) {
                    mTCActivitiesList.addAll(response);
                    mAdapter.fillData(mTCActivitiesList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                if(isDetached()) {
                    return;
                }
                if (error instanceof AuthFailureError) {
                    mMainActivity.loginRequired();
                }
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        setupData();
    }
}
