package info.lofei.app.tuchong.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;

import info.lofei.app.tuchong.data.RequestManager;

/**
 * The base fragment.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-25 22:39
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
