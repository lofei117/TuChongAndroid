package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.math.BigInteger;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.lofei.app.tuchong.AppManager;
import info.lofei.app.tuchong.BaseApplication;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.request.CaptchaRequest;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.model.result.Captcha;
import info.lofei.app.tuchong.model.result.LoginResult;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.utils.RSA;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 11/18/15.
 */
public class LoginActivity extends BaseActivity{

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

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    private Captcha mGetCaptcha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.info)
    void onClickInfo(){
        WebViewActivity.launch(this, "file:///android_asset/html/about_image_info.html");
    }

    @OnClick(R.id.forgot_pwd_button)
    void forgotPwd(){
        WebViewActivity.WebLaunchConfig  config = new WebViewActivity.WebLaunchConfig("http://tuchong.com/account/forget/", "忘记密码");
        config.setNetTitleEnable(true);
        WebViewActivity.launch(this,config);
    }

    @OnClick(R.id.btn_login)
    void login() {
        String username = mUserNameEditText.getText().toString();
        String originalPassword = mPasswordEditText.getText().toString();

        String encodedPassword = new RSA().encrypt(originalPassword);

        HashMap<String, String> params = new HashMap<>(8);
        params.put(LoginRequest.USERNAME_KEY, username);
        params.put(LoginRequest.PASSWORD_KEY, encodedPassword);
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
                        //保存用户id
                        PreferenceUtil.putString(LoginRequest.DATA_SAVE_TUCHONG_CURRENT_USER_ID, response.getId());
                        AppManager.getInstance().finishAllActivities();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        break;
                    case LoginResult.CODE_PWD_OR_NAME_ERROR:

                        break;
                    case LoginResult.CODE_NEED_CAPTCHA:
                    case LoginResult.CODE_VIDIFY_ERROR:
                        mChaptchaRegionView.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "VIDIFY ERROR OR NEED CAPTCHA", Toast.LENGTH_SHORT).show();
                        fetchCaptcha();
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
