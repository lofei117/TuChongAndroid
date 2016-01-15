package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.umeng.fb.FeedbackAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.AppManager;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.request.LogoutRequest;
import info.lofei.app.tuchong.data.request.result.LogoutResult;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * 设置页面
 *
 * @author jerrysher jerry@jerryzhang.net
 * Created by jerrysher on 10/11/15.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.log_out_button)
    Button mLogoutBtn;

    @Bind(R.id.setting_item_clean_cache)
    View mCleanCache;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLogoutBtn.setOnClickListener(this);
        mCleanCache.setOnClickListener(this);
        findViewById(R.id.setting_item_feedback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_out_button:
                executeRequest(new LogoutRequest(Request.Method.POST, TuChongApi.LOGOUT_URL,
                        new Response.Listener<LogoutResult>() {

                            @Override
                            public void onResponse(LogoutResult response) {
                                AppManager.getInstance().finishAllActivities();
                                startActivity(new Intent(SettingsActivity.this,
                                        RegLoginActivity.class));
                                if (response != null && "SUCCESS".equalsIgnoreCase(response.getResult())) {
                                    //// leave blank

                                } else {
                                    Toast.makeText(SettingsActivity.this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, null));
                break;

            case R.id.setting_item_clean_cache:
                //todo clean cache.
                break;
            case R.id.setting_item_feedback:
                //USER FEEDBACK
                FeedbackAgent agent = new FeedbackAgent(this);
                agent.startFeedbackActivity();
                break;
        }
    }
}