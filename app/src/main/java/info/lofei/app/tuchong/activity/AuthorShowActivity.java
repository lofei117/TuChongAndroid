package info.lofei.app.tuchong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.model.TCAuthor;
import info.lofei.app.tuchong.model.TCSite;

/**
 * Created by jerrysher on 15/12/25.
 */
public class AuthorShowActivity extends BaseActivity{

    public static final String EXTRA_DATA_AUTHOR = "extra_data_author";
    public static final String EXTRA_DATA_SITE = "extra_data_site";

    @Bind(R.id.profile_image)
    CircleImageView userProfileImage;

    @Bind(R.id.author_name)
    TextView authorName;

    @Bind(R.id.follower_count)
    TextView followerCount;

    @Bind(R.id.action_follow)
    View actionFollow;

    @Bind(R.id.action_enter_homepage)
    View actionEnterHomePage;

    @Bind(R.id.author_info)
    TextView authorInfo;

    private TCSite mTcSite;

    private TCAuthor mTcAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_show);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null){
            mTcSite = (TCSite) intent.getSerializableExtra(EXTRA_DATA_SITE);
            mTcAuthor = (TCAuthor) intent.getSerializableExtra(EXTRA_DATA_AUTHOR);
        }

        if(mTcSite != null){
            authorName.setText(mTcSite.getName());
            authorInfo.setText(mTcSite.getDescription());
            followerCount.setText(getString(R.string.author_info_follow_count, mTcSite.getFollowers()));
            RequestManager.loadImage(mTcSite.getIcon(), RequestManager.getImageListener(userProfileImage, null, null));
            actionEnterHomePage.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    WebViewActivity.launch(v.getContext(), mTcSite.getUrl());
                    finish();
                }
            });
            actionFollow.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    //TODO 关注当前用户。
                    Toast.makeText(v.getContext(), "关注", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(mTcAuthor != null){
            authorName.setText(mTcAuthor.getName());
            authorInfo.setText(mTcAuthor.getDescription());
            followerCount.setText(getString(R.string.author_info_follow_count, mTcAuthor.getFollwerCount()));
            RequestManager.loadImage(mTcAuthor.getIconUrl(), RequestManager.getImageListener(userProfileImage, null, null));
            actionEnterHomePage.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    WebViewActivity.launch(v.getContext(), mTcAuthor.getUrl());
                    finish();
                }
            });
            actionFollow.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    //关注当前用户。
                    Toast.makeText(v.getContext(), "关注", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.root_view)
    void  rootViewClick(){
        finish();
    }

    @OnClick(R.id.content_view)
    void contentView(){}

    public static void laucher(Context context, TCAuthor author){
        Intent intent = new Intent(context, AuthorShowActivity.class);
        intent.putExtra(EXTRA_DATA_AUTHOR,author);
        context.startActivity(intent);

    }

    public static void laucher(Context context, TCSite site){
        Intent intent = new Intent(context, AuthorShowActivity.class);
        intent.putExtra(EXTRA_DATA_SITE,site);
        context.startActivity(intent);
    }
}
