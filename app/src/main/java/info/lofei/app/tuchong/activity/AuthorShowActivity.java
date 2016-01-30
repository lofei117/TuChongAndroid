package info.lofei.app.tuchong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.data.request.FollowingRequest;
import info.lofei.app.tuchong.data.request.GetSiteRequest;
import info.lofei.app.tuchong.data.request.WebRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.utils.Constant;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 15/12/25.
 */
public class AuthorShowActivity extends BaseActivity{

    private static final String EXTRA_DATA_SITE_ID = "extra_data_site_id";

    @Bind(R.id.profile_image)
    CircleImageView userProfileImage;

    @Bind(R.id.author_name)
    TextView authorName;

    @Bind(R.id.follower_count)
    TextView followerCount;

    @Bind(R.id.action_follow)
    TextView actionFollow;

    @Bind(R.id.action_enter_homepage)
    View actionEnterHomePage;

    @Bind(R.id.author_info)
    TextView authorInfo;


    private String mSiteId;
    private String nonceStrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getNonceString();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_show);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null){
            mSiteId = intent.getStringExtra(EXTRA_DATA_SITE_ID);
        }

        getSiteInfo(mSiteId);

    }

    private void updateSiteInfo(final TCSite site) {
        if(site == null){
            return;
        }

        authorName.setText(site.getName());
        authorInfo.setText(site.getDescription());
        followerCount.setText(getString(R.string.author_info_follow_count, site.getFollowers()));
        RequestManager.loadImage(site.getIcon(), RequestManager.getImageListener(userProfileImage, null, null));
        actionEnterHomePage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                WebViewActivity.launch(v.getContext(), site.getUrl());
                finish();
            }
        });

        actionFollow.setText(getString(site.isFollowing() ? R.string.homepage_cancel_follow: R.string.homepage_follow));
        actionFollow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setFollowingSite(!site.isFollowing(), mSiteId);
            }
        });
    }

    private void setFollowingSite(boolean followingIt, String siteId){
        String url =  String.format(TuChongApi.FOLLOWING_SITE_URL, siteId);
        Map<String, String> map =new  HashMap<>();
        map.put("nonce",nonceStrng);

        executeRequest(new FollowingRequest(
                followingIt ? Request.Method.PUT : Request.Method.DELETE,
                url, map,
                new Response.Listener<BaseResult>() {

                    @Override
                    public void onResponse(BaseResult response) {
                        if (response != null) {
                            Toast.makeText(AuthorShowActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AuthorShowActivity.this, "请求出错" + (error != null ? error.getMessage() : ""), Toast.LENGTH_SHORT).show();
                    }
                }
        ));
    }

    private void getSiteInfo(String siteId){
        String url = String.format(TuChongApi.SITE_URL, siteId);

        executeRequest(new GetSiteRequest(url, new Response.Listener<TCSite>() {
            @Override
            public void onResponse(final TCSite site) {
                updateSiteInfo(site);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void getNonceString(){
        executeRequest(new WebRequest("http://tuchong.com/signup/", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if(!TextUtils.isEmpty(response)){
                    nonceStrng = response;
                }
            }
        },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }

    @OnClick(R.id.root_view)
    void  rootViewClick(){
        finish();
    }

    @OnClick(R.id.content_view)
    void contentView(){}

    public static void launch(Context context, String siteId){
        Intent intent = new Intent(context, AuthorShowActivity.class);
        intent.putExtra(EXTRA_DATA_SITE_ID,siteId);
        context.startActivity(intent);
    }

}
