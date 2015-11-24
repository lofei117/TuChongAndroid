package info.lofei.app.tuchong.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import info.lofei.app.tuchong.BaseApplication;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.data.request.CaptchaRequest;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.model.result.Captcha;
import info.lofei.app.tuchong.model.result.LoginResult;
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

    @Bind(R.id.et_username)
    EditText mUserNameEditText;

    @Bind(R.id.et_password)
    EditText mPasswordEditText;

    @Bind(R.id.captcha_region)
    View mChaptchaRegionView;

    @Bind(R.id.captcha_fetch)
    ImageView mReFetchCaptchaBtn;

    @Bind(R.id.captcha_view)
    ImageView mCaptchaShowView;

    @Bind(R.id.captcha_input)
    EditText mCaptchaEditText;

    private Captcha mGetCaptcha;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    void login() {
        String username = mUserNameEditText.getText().toString();
        String originalPassword = mPasswordEditText.getText().toString();

        String encodedPassword = new RSA().encrypt(originalPassword);

        HashMap<String, String> params = new HashMap<>(8);
        params.put(USERNAME_KEY, username);
        params.put(PASSWORD_KEY, encodedPassword);
        params.put("remember", "on");
        if(mGetCaptcha != null  && !TextUtils.isEmpty(mGetCaptcha.getId())
                && !TextUtils.isEmpty(mGetCaptcha.getBase64())){
            params.put("captcha_token", mCaptchaEditText.getText().toString());
            params.put("captcha_id", mGetCaptcha.getId());
        }

        executeRequest(new LoginRequest(Request.Method.POST, TuChongApi.LOGIN_URL, params, new Response.Listener<LoginResult>() {
            @Override
            public void onResponse(LoginResult response) {
                if (response == null) {
                    return;
                }

                Toast.makeText(BaseApplication.getBaseApplication(),
                        response.getMessage(), Toast.LENGTH_SHORT).show();

                switch (response.getCode()) {
                    case LoginResult.CODE_SUCCESS:
                        if (mMainActivity != null) {
                            mMainActivity.launchMainFragment();
                        }
                        break;
                    case LoginResult.CODE_PWD_OR_NAME_ERROR:

                        break;
                    case LoginResult.CODE_NEED_CAPTCHA:
                    case LoginResult.CODE_VIDIFY_ERROR:
                        mChaptchaRegionView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "VIDIFY ERROR OR NEED CAPTCHA", Toast.LENGTH_SHORT).show();
                        fetchCaptcha();
                        break;
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

    @OnClick(R.id.captcha_fetch)
    void fetchCaptcha(){
        executeRequest(new CaptchaRequest(new Response.Listener<Captcha>() {
            @Override
            public void onResponse(Captcha response) {
                mGetCaptcha = response;
                if(response == null || TextUtils.isEmpty(response.getBase64())){
                    return;
                }
                //显示验证码。
                String base64 = response.getBase64().replace("data:image/png;base64,","").replace("==", "");

                try {
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mCaptchaShowView.setImageBitmap(decodedByte);
                }catch (java.lang.IllegalArgumentException e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ));
    }

}
