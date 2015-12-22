package info.lofei.app.tuchong.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;

/**
 * Created by jerrysher on 15/12/17.
 */
public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }
}
