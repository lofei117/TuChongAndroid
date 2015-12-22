package info.lofei.app.tuchong.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.PostDetailActivity;
import info.lofei.app.tuchong.adapter.DetailAdapter;
import info.lofei.app.tuchong.data.request.FavoriteRequest;
import info.lofei.app.tuchong.data.request.GetComments;
import info.lofei.app.tuchong.data.request.GetPostDetail;
import info.lofei.app.tuchong.data.request.GetExif;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.data.request.PostComment;
import info.lofei.app.tuchong.data.request.result.FavoriteResult;
import info.lofei.app.tuchong.model.TCComment;
import info.lofei.app.tuchong.model.TCExif;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.utils.NumberUtil;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.vendor.TuChongApi;

import static com.android.volley.Response.*;

/**
 * 详情界面.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-08 11:11
 */
public class PostDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = PostDetailFragment.class.getSimpleName();
    private static String BUNDLE_ARG_POST = "bundle_arg_post";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.btn_add_comment)
    Button addCommentButton;

    @Bind(R.id.et_comment_content)
    EditText etCommentContent;

    @Bind(R.id.reply_to_user)
    TextView tvReplayToUser;

    private TCPost mTCPost;

    private List<TCComment> mCommentList;

    private DetailAdapter mAdapter;

    private PostDetailActivity mPostDetailActivity;

    private InputMethodManager inputMethodManager;

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
        mPostDetailActivity = (PostDetailActivity) activity;
        inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
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
            addCommentButton.setOnClickListener(this);
            tvReplayToUser.setOnClickListener(this);
        }
        return view;
    }

    private void loadPostDetailData() {
        if(mTCPost != null){
            executeRequest(new GetPostDetail(
                    String.format(TuChongApi.POST_DETAIL_URL, mTCPost.getPost_id()),
                    new Listener<TCPost>() {

                        @Override
                        public void onResponse(TCPost response) {
                            if (response != null) {
                                mTCPost.setAuthor(response.getAuthor());
                                mTCPost.setTags(response.getTags());
                                mTCPost.setParsedContent(response.getParsedContent());
                                mTCPost.setFavorites(response.getFavorites());

                                if(!mTCPost.is_favorite() && response.getFavorites() > 0){
                                    final String url = String.format(TuChongApi.FAVORITE_POST_URL, mTCPost.getPost_id());
                                    executeRequest(new FavoriteRequest(
                                            (Request.Method.PUT), url,
                                            new Response.Listener<FavoriteResult>() {

                                                @Override
                                                public void onResponse(FavoriteResult response) {
                                                    if (response != null) {
                                                        if(NumberUtil.toInt(response.getFavoriteCount()) != mTCPost.getFavorites()){
                                                            executeRequest(new FavoriteRequest((Request.Method.DELETE), url,null, null));
                                                            mTCPost.setIs_favorite(false);
                                                        }else{
                                                            mTCPost.setIs_favorite(true);
                                                        }
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

    long replaytoUserId;

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getActivity() == null) {
                    return;
                }

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mPostDetailActivity));
        if(mAdapter == null) {
            mAdapter = new DetailAdapter(mPostDetailActivity, mTCPost);
            mAdapter.fillCommentsData(mCommentList);
            mAdapter.setOnCommentItemClick(new DetailAdapter.OnCommentItemClickListener() {
                @Override
                public boolean onItemClick(long authorUserId, String authorUserName) {
                    if (authorUserId != NumberUtil.toLong(PreferenceUtil.getString(LoginRequest.DATA_SAVE_TUCHONG_CURRENT_USER_ID, ""))) {
                        tvReplayToUser.setText(getString(R.string.reply_to_someone, authorUserName));
                        etCommentContent.setHint(getString(R.string.reply_to_someone, authorUserName));
                        replaytoUserId = authorUserId;
                        showCommentLayout(true);
                    }
                    return false;
                }
            });
            mAdapter.setOnCommentButtonClickListener(new DetailAdapter.OnCommentButtonClickListener() {
                @Override
                public boolean onCommentButtonClick() {
                    //TODO jerry 开启评论
                    replaytoUserId = 0;
                    tvReplayToUser.setText(null);
                    etCommentContent.setHint(null);
                    showCommentLayout(true);

                    return false;
                }
            });
            mAdapter.setmOnLikeButtonClickListener(new DetailAdapter.OnLikeButtonClickListener() {
                @Override
                public boolean onLikeButtonClick(final View view, boolean isLiked) {
                    //TODO like  喜欢或者取消喜欢post
                    String url = String.format(TuChongApi.FAVORITE_POST_URL, mTCPost.getPost_id());
                    executeRequest(new FavoriteRequest(
                            (isLiked ? Request.Method.DELETE : Request.Method.PUT), url,
                            new Response.Listener<FavoriteResult>() {

                                @Override
                                public void onResponse(FavoriteResult response) {
                                    if (response != null && response.isFavorited()) {
                                        ((TextView) view).setText(R.string.post_has_liked);
                                    } else {
                                        ((TextView) view).setText(R.string.post_like);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    ));
                    return false;
                }
            });
            mAdapter.setOnImageLongClickListener(new DetailAdapter.OnImageLongClickListener() {
                @Override
                public boolean onLongClick(View view, long imageId, long postId) {
                    executeRequest(new GetExif(imageId, postId, new Listener<TCExif>() {
                        @Override
                        public void onResponse(TCExif response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO
                            Toast.makeText(mPostDetailActivity, "null exif", Toast.LENGTH_SHORT).show();
                        }
                    }));
                    return true;
                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showCommentLayout(boolean isShow) {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        addCommentButton.setVisibility(isShow ? View.VISIBLE : View.GONE);
        etCommentContent.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvReplayToUser.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if(isShow){
            etCommentContent.requestFocus();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        loadCommentListData();
        loadPostDetailData();
    }

    private void loadCommentListData() {
        mCommentList.clear();
        if(mTCPost == null){
            return;
        }
        String url = String.format(TuChongApi.COMMENT_URL, mTCPost.getPost_id());
        executeRequest(new GetComments(url, new Listener<List<TCComment>>() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reply_to_user:
                replaytoUserId = 0;
                tvReplayToUser.setText(null);
                etCommentContent.setHint(null);
                break;
            case R.id.btn_add_comment:
                String context = etCommentContent.getText().toString();
                etCommentContent.setText(null);

                String url = String.format(TuChongApi.COMMENT_URL, mTCPost.getPost_id());
                HashMap<String, String> map = new HashMap<>();
                map.put("format","json");
                map.put("content",context);
                map.put("post_id", "" + mTCPost.getPost_id());
                if(replaytoUserId > 0){
                    map.put("replyto[]", "" + replaytoUserId);
                }

                executeRequest(new PostComment(url, map, new Listener<TCComment>() {

                    @Override
                    public void onResponse(TCComment response) {
                        loadCommentListData();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
                break;
        }
    }
}
