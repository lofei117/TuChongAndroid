package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

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
 * @author jerrysher jerry@jerryzheng.net
 * Created by jerrysher on 10/11/15.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.log_out_button)
    Button mLogoutBtn;

    @Bind(R.id.setting_item_clean_cache)
    View mCleanCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mLogoutBtn.setOnClickListener(this);
        mCleanCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_out_button:
                executeRequest(new LogoutRequest(Request.Method.POST, TuChongApi.LOGOUT_URL,
                        new Response.Listener<LogoutResult>() {

                            @Override
                            public void onResponse(LogoutResult response) {
                                AppManager.getInstance().finishAllActivitis();
                                startActivity(new Intent(SettingsActivity.this,
                                        LoginActivity.class));
                                if (response != null && "SUCCESS".equalsIgnoreCase(response.getResult())) {
                                    //// leave blank

                                } else {
                                    Toast.makeText(SettingsActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, null));
                break;

            case R.id.setting_item_clean_cache:
                //todo clean cache.
                break;
        }
    }
}
