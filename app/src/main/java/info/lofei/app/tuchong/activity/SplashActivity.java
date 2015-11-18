package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jerrysher on 11/18/15.
 */
public class SplashActivity extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
