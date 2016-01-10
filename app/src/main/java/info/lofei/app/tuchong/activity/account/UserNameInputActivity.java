package info.lofei.app.tuchong.activity.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.AppManager;
import info.lofei.app.tuchong.BaseApplication;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.BaseActivity;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.data.request.CaptchaRequest;
import info.lofei.app.tuchong.data.request.CheckNameRequest;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.data.request.RegisterRequest;
import info.lofei.app.tuchong.data.request.WebRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.result.Captcha;
import info.lofei.app.tuchong.model.result.LoginResult;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.utils.RSA;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 16/1/5.
 */
public class UserNameInputActivity extends BaseActivity implements View.OnClickListener {


    public static final String EXTRA_DATA_PWD = "extra_data_pwd";
    public static final int REQUEST_RESULT_CODE_CAPCHA_ERROR = 12;
    private static final int REQUEST_RESULT_CODE_SMS_CAPCHA_ERROR = 4;
    public static final String FOR_RESULT_ERROR_CODE = "for_result_error_code";
    public static final String FOR_RESULT_ERROR_MSG = "for_result_error_msg";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_username)
    AppCompatEditText mEtUsername;

    @Bind(R.id.btn_action_register)
    Button mBtnActionRegister;

    @Bind(R.id.et_captcha)
    AppCompatEditText mEtCaptcha;

    @Bind(R.id.iv_captcha_show)
    ImageView mIvCaptchaShow;

    @Bind(R.id.iv_captcha_fetch)
    ImageView mIvCaptchaFetch;

    @Bind(R.id.region_email_captcha)
    LinearLayout mRegionEmailCaptcha;

    private String getPhoneNo;
    private String getPoneZone;
    private String getSmsCode;

    private String getEmail;

    private String getPwd;

    private Captcha mEmailCaptcha;
    private String nonceStrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getNonceString();
        getIntentData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_input);
        ButterKnife.bind(this);
        setListeners();

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mRegionEmailCaptcha.setVisibility(TextUtils.isEmpty(getEmail) ? View.GONE : View.VISIBLE);

        if(!TextUtils.isEmpty(getEmail)){
            fetchEmailCaptcha();
        }
    }

    private void fetchEmailCaptcha() {
        executeRequest(new CaptchaRequest(new Response.Listener<Captcha>() {
            @Override
            public void onResponse(Captcha response) {
                mEmailCaptcha = response;
                if (response == null || TextUtils.isEmpty(response.getBase64())) {
                    return;
                }
                //显示验证码。
                String base64 = response.getBase64().replace("data:image/png;base64,", "").replace("==", "");

                try {
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mIvCaptchaShow.setImageBitmap(decodedByte);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ));
    }

    private void setListeners() {
        mIvCaptchaFetch.setOnClickListener(this);
        mBtnActionRegister.setOnClickListener(this);
        mIvCaptchaShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_captcha_fetch:
            case R.id.iv_captcha_show:{
                fetchEmailCaptcha();
                break;
            }

            case R.id.btn_action_register: {
                if(checkInput()){
                    return;
                }
                checkUsernameAndRegister(mEtUsername.getText().toString());
                break;
            }
        }

    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(mEtUsername.getText().toString())){
            mEtUsername.setError("请输入用户名");
            return true;
        }
        if(!TextUtils.isEmpty(getEmail) && TextUtils.isEmpty(mEtCaptcha.getText().toString())){
            mEtCaptcha.setError("请输入验证码");
            return true;
        }
        return false;
    }

    private void checkUsernameAndRegister(String username) {
        executeRequest(new CheckNameRequest(username, new Response.Listener<BaseResult>() {

            @Override
            public void onResponse(BaseResult response) {
                if ("SUCCESS".equals(response.getResult())){
                    //用户名有效注册
                    actionRegister();
                } else {
                    mEtUsername.setError(response.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    private void getNonceString(){
        executeRequest(new WebRequest("http://tuchong.com/signup/", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if(!TextUtils.isEmpty(response)){
                    nonceStrng = response;
                }
            }
        },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }

    private String urlEncode(String encodeStr){
//        String resultStr = null;
//        if(TextUtils.isEmpty(encodeStr)){
//            return "";
//        }
//        try {
//            resultStr = URLEncoder.encode(encodeStr, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            resultStr = "";
//        }

        return encodeStr;
    }

    void actionRegister(){
        //TODO  注册用户账号。 Jerrysher  2016.1.2
        Map<String, String> map = new HashMap<>();
        map.put("nonce",nonceStrng);

        if(!TextUtils.isEmpty(getEmail)){
            map.put("user_email", urlEncode(getEmail));
        }
        if(!TextUtils.isEmpty(getPhoneNo)){
            map.put("mobile_number", urlEncode(getPhoneNo));
        }
        if(!TextUtils.isEmpty(getPoneZone)){
            map.put("zone",getPoneZone);
        }

        if(!TextUtils.isEmpty(getSmsCode)){
            map.put("sms_captcha", urlEncode(getSmsCode));
        }
        map.put("user_name", urlEncode(mEtUsername.getText().toString()));

        map.put("user_password",new RSA().encrypt(getPwd));
        map.put("user_password2",new RSA().encrypt(getPwd));
        if(mEmailCaptcha != null){
            map.put("captcha_id",mEmailCaptcha.getId());
            map.put("captcha_token", urlEncode(mEtCaptcha.getText().toString()));
        }

        map.put("accept","default-follow");//

        executeRequest(new RegisterRequest(map,
                new Response.Listener<BaseResult>() {

                    @Override
                    public void onResponse(BaseResult response) {
                        Log.d("reg", "response" + response);
                        if ("SUCCESS".equals(response.getResult())){
                            //注册成功。
                            login();
                        }else {
                            if(response.getCode() == REQUEST_RESULT_CODE_CAPCHA_ERROR){
                                //邮箱注册图形数字验证码错误
                                mEtCaptcha.setError(response.getMessage());
                            }else{
                                Intent intent = new Intent();
                                intent.putExtra(FOR_RESULT_ERROR_CODE, response.getCode() );
                                intent.putExtra(FOR_RESULT_ERROR_MSG, response.getMessage() );
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            /*
                            1、短信验证码输入错误。
                            2、也有可能密码有问题。
                             */
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("reg","error" +  error);
                    }
                }));
    }

    private void login() {
        String username = mEtUsername.getText().toString();
        String originalPassword = getPwd;

        String encodedPassword = new RSA().encrypt(originalPassword);

        HashMap<String, String> params = new HashMap<>(8);
        params.put(LoginRequest.USERNAME_KEY, username);
        params.put(LoginRequest.PASSWORD_KEY, encodedPassword);
        params.put("remember", "on");
//        if(mGetCaptcha != null  && !TextUtils.isEmpty(mGetCaptcha.getId())
//                && !TextUtils.isEmpty(mGetCaptcha.getBase64())){
//            params.put("captcha_token", mCaptchaEditText.getText().toString());
//            params.put("captcha_id", mGetCaptcha.getId());
//        }
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
                        AppManager.getInstance().finishAllActivitis();
                        startActivity(new Intent(UserNameInputActivity.this, MainActivity.class));
                        break;
                    case LoginResult.CODE_PWD_OR_NAME_ERROR:

                        break;
                    case LoginResult.CODE_NEED_CAPTCHA:
                    case LoginResult.CODE_VIDIFY_ERROR:
                        setResult(RESULT_OK);
                        finish();
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(UserNameInputActivity.this, "failed", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }));
    }

    public static void launch(Context context, String email, String pwd) {
        context.startActivity(new Intent(context, UserNameInputActivity.class));
    }

    public static void launch(Context context, String zone, String mobileNo, String smsCode, String pwd) {
        Intent intent = new Intent(context, UserNameInputActivity.class);
        context.startActivity(intent);
        intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_ZONE, zone);
        intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_NO, mobileNo);
        intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_SMS_CODE, smsCode);
        intent.putExtra(EXTRA_DATA_PWD, smsCode);

    }

    private void getIntentData() {
        getEmail = getIntent().getStringExtra(RegPasswordInputActivity.EXTRA_DATA_REG_EMAIL);

        getPhoneNo = getIntent().getStringExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_NO);
        getPoneZone = getIntent().getStringExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_ZONE);
        getSmsCode = getIntent().getStringExtra(RegPasswordInputActivity.EXTRA_DATA_SMS_CODE);
        getPwd = getIntent().getStringExtra(EXTRA_DATA_PWD);

    }
}
