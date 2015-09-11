package info.lofei.app.tuchong.activity;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;

import info.lofei.app.tuchong.data.RequestManager;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-08-04 10:26
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected void execute(Request request) {
        RequestManager.addRequest(request, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }
}
