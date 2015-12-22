package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;

/**
 * Created by jerrysher on 15/12/17.
 */
public class RegLoginActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.register)
    View regiseterButton;

    @Bind(R.id.login)
    View loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglogin);

        ButterKnife.bind(this);

        regiseterButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.register:
                WebViewActivity.WebLaunchConfig  config = new WebViewActivity.WebLaunchConfig("http://tuchong.com/signup/","用户注册");
                config.setNetTitleEnable(true);
                WebViewActivity.launch(this,config);
                break;
        }
    }
}
