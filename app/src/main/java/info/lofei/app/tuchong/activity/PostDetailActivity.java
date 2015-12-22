package info.lofei.app.tuchong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.fragment.PostDetailFragment;
import info.lofei.app.tuchong.model.TCPost;

/**
 * Created by jerrysher on 15/12/7.
 */
public class PostDetailActivity extends BaseActivity{
    public static final String EXTRA_TCPOST_SENT_BY_INTENT = "extra_tcpost_sent_by_intent";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        ButterKnife.bind(this);

        TCPost  post = (TCPost) getIntent().getSerializableExtra(EXTRA_TCPOST_SENT_BY_INTENT);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, PostDetailFragment.newInstance(post)).commit();

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(post != null){
            mToolbar.setTitle(post.getTitle());
        }
    }

    public static void launch(Context context, final TCPost tcPost){
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(EXTRA_TCPOST_SENT_BY_INTENT, tcPost);
        context.startActivity(intent);

    }
}
