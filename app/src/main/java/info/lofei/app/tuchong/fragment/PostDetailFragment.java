package info.lofei.app.tuchong.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.adapter.DetailAdapter;
import info.lofei.app.tuchong.data.request.GetComments;
import info.lofei.app.tuchong.data.request.GetPostDetail;
import info.lofei.app.tuchong.model.TCAuthor;
import info.lofei.app.tuchong.model.TCComment;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.vendor.TuChongApi;

import static com.android.volley.Response.*;

/**
 * 详情界面.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-08 11:11
 */
public class PostDetailFragment extends BaseFragment {

    private static String BUNDLE_ARG_POST = "bundle_arg_post";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private TCPost mTCPost;

    private List<TCComment> mCommentList;

    private DetailAdapter mAdapter;

    private MainActivity mMainActivity;

    public static PostDetailFragment newInstance(TCPost post) {
        PostDetailFragment detailFragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_ARG_POST, post);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTCPost = (TCPost) savedInstanceState.getSerializable(BUNDLE_ARG_POST);
        } else if (getArguments() != null) {
            mTCPost = (TCPost) getArguments().getSerializable(BUNDLE_ARG_POST);
        }
        mCommentList = new ArrayList<>(10);
    }

    View view;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_detail, container, false);

            ButterKnife.bind(this, view);
            setupRecyclerView();
        }
        return view;
    }

    private void loadPostDetailData() {
        if(mTCPost != null){
            executeRequest(new GetPostDetail(
                    String.format(TuChongApi.POST_DETAIL_URL, mTCPost.getPost_id()),
                    new Response.Listener<TCPost>() {

                        @Override
                        public void onResponse(TCPost response) {
                            if (response != null) {
                                mTCPost = response;
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ));
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_ARG_POST, mTCPost);
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mMainActivity));
        if(mAdapter == null) {
            mAdapter = new DetailAdapter(mMainActivity, mTCPost);
            mAdapter.fillCommentsData(mCommentList);
        }
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadCommentListData();
        loadPostDetailData();
    }

    private void loadCommentListData() {
        mCommentList.clear();
        String url = String.format(TuChongApi.COMMENT_URL, mTCPost.getPost_id());
        executeRequest(new GetComments(url, new Response.Listener<List<TCComment>>() {
            @Override
            public void onResponse(final List<TCComment> response) {
                mCommentList.addAll(response);
                mAdapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

            }
        }));
    }
}