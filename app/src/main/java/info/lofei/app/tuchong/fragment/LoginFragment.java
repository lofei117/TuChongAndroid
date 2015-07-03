package info.lofei.app.tuchong.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.data.LoginRequest;
import info.lofei.app.tuchong.util.RSA;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * 登录界面.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 14:36
 */
public class LoginFragment extends BaseFragment {

    private static final String USERNAME_KEY = "account";
    private static final String PASSWORD_KEY = "password";

    @InjectView(R.id.et_username)
    EditText mUserNameEditText;

    @InjectView(R.id.et_password)
    EditText mPasswordEditText;

    private MainActivity mMainActivity;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivity = null;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    void login() {
        String username = mUserNameEditText.getText().toString();
        String originalPassword = mPasswordEditText.getText().toString();

        String encodedPassword = new RSA().encrypt(originalPassword);

        HashMap params = new HashMap(8);
        params.put(USERNAME_KEY, username);
        params.put(PASSWORD_KEY, encodedPassword);
        params.put("remember", "on");

        executeRequest(new LoginRequest(Request.Method.POST, TuChongApi.LOGIN_URL, params, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(final Boolean response) {
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                if (mMainActivity != null) {
                    mMainActivity.onLoginSuccess();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }));
    }

}
