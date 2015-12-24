package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.umeng.onlineconfig.OnlineConfigAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;

/**
 * Created by jerrysher on 15/12/17.
 */
public class RegLoginActivity extends BaseActivity implements View.OnClickListener {
    public static final String LOGIN_WELCOME = "LOGIN_WELCOME";
    @Bind(R.id.register)
    View regiseterButton;

    @Bind(R.id.login)
    View loginButton;

    @Bind(R.id.bg_image)
    ImageView bgImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OnlineConfigAgent.getInstance().updateOnlineConfig(this);

        String url = OnlineConfigAgent.getInstance().getConfigParams(this, LOGIN_WELCOME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglogin);

        ButterKnife.bind(this);

        if(!TextUtils.isEmpty(url)){
            RequestManager.loadImage(url, RequestManager.getImageListener(bgImageView, null, null));
        }

        regiseterButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.register:
                WebViewActivity.WebLaunchConfig config = new WebViewActivity.WebLaunchConfig("http://tuchong.com/signup/", "用户注册");
                config.setNetTitleEnable(true);
                WebViewActivity.launch(this, config);
                break;
        }
    }
}
