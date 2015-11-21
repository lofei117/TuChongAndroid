package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.utils.PreferenceUtil;

/**
 * Created by jerrysher on 11/18/15.
 */
public class SplashActivity extends BaseActivity implements Animation.AnimationListener{
    private AlphaAnimation alphaAnimation;

    @Bind(R.id.root_view)
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        alphaAnimation = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(this);
        alphaAnimation.startNow();

        rootView.setAnimation(alphaAnimation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        finish();
        //判断是否存在user id。
        if(TextUtils.isEmpty(PreferenceUtil.getString(
                LoginRequest.DATA_SAVE_TUCHONG_CURRENT_USER_ID, ""))){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
