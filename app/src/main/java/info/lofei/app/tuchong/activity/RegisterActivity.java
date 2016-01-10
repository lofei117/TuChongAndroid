package info.lofei.app.tuchong.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.request.CaptchaRequest;
import info.lofei.app.tuchong.data.request.CheckEmailRequest;
import info.lofei.app.tuchong.data.request.CheckNameRequest;
import info.lofei.app.tuchong.data.request.RegisterRequest;
import info.lofei.app.tuchong.data.request.SmsCaptchaRequest;
import info.lofei.app.tuchong.data.request.WebRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.result.Captcha;
import info.lofei.app.tuchong.model.result.LoginResult;
import info.lofei.app.tuchong.utils.RSA;

/**
 * Created by jerrysher on 15/12/17.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.root_view)
    View mRootView;

    @Bind(R.id.region_phone_layout)
    View mPhoneNoRegion;

    @Bind(R.id.region_email_layout)
    View mEmailRegion;

    @Bind(R.id.region_email_captcha)
    View mEmailCaptchaRegion;


    @Bind(R.id.et_user_email)
    EditText mEtUserEmail;

    @Bind(R.id.et_phone_no)
    EditText mEtPhoneNo;

    @Bind(R.id.tv_got_phone_msg_captcha)
    View mEtGotPhoneMsgCaptcha;

    @Bind(R.id.et_phone_msg_captcha)
    EditText mEtPhoneMsgCaptcha;

    @Bind(R.id.et_username)
    EditText mEtUsername;

    @Bind(R.id.et_password)
    EditText mEtPassord;

    @Bind(R.id.et_conform_pwd)
    EditText mEtConformPwd;

    @Bind(R.id.et_email_captcha)
    EditText mEtEmailCaptcha;

    @Bind(R.id.iv_email_captcha_show)
    ImageView mIvEmailCaptcha;

    @Bind(R.id.iv_captcha_fetch)
    View mFetchEmailCaptcha;

    @Bind(R.id.action_goto_email_reg)
    TextView mBtnSwitchRegisterMode;

    @Bind(R.id.checkbox_agree_protorl)
    CheckBox mChkBokAgreeProtorl;

    @Bind(R.id.btn_action_register)
    Button mBtnActionRegister;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initViewData();

        setListeners();

        fetchEmailCaptcha();
    }

    private void setListeners() {
        mFetchEmailCaptcha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchEmailCaptcha();
            }
        });
        mIvEmailCaptcha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchEmailCaptcha();
            }
        });
        mBtnSwitchRegisterMode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switchRegisterMode();
            }
        });
        mEtPhoneNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //检查手机号码是否有效。
                    String phone = mEtPhoneNo.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        clearAllError();
                        mEtPhoneNo.setError("手机号码不能为空");
                        mEtPhoneNo.requestFocus();
                    }

                }
            }
        });

        mEtUserEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //检查邮箱是否有效。
                    String email = mEtUserEmail.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        clearAllError();
                        new AlertDialog.Builder(v.getContext()).setTitle("提醒")
                                .setMessage("邮箱不能为空").setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEtUserEmail.setError("邮箱不能为空");
                                mEtUserEmail.requestFocus();
                            }
                        }).show();

                        return;
                    }

                    executeRequest(new CheckEmailRequest(email, new Response.Listener<BaseResult>() {

                        @Override
                        public void onResponse(BaseResult response) {
                            if(response.getCode() != LoginResult.CODE_SUCCESS){
                                clearAllError();
                                mEtUsername.setError(response.getMessage());
                                mEtUsername.requestFocus();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }));

                }
            }
        });


        mEtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //检查用户名是否有效。
                    String username = mEtUsername.getText().toString();
                    if (TextUtils.isEmpty(username)) {
                        clearAllError();
                        //mEtUsername.setError("用户名不能为空");
                        //mEtUsername.requestFocus();
                        return;
                    }

                    executeRequest(new CheckNameRequest(username, new Response.Listener<BaseResult>() {

                        @Override
                        public void onResponse(BaseResult response) {
                            if(response.getCode() != LoginResult.CODE_SUCCESS){
                                clearAllError();
                                mEtUserEmail.setError(response.getMessage());
                                mEtUserEmail.requestFocus();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }));

                }
            }
        });
    }

    private void clearAllError(){
        mRootView.clearFocus();
//        mEtPhoneNo.setError(null);
//        mEtUserEmail.setError(null);
//        mEtPassord.setError(null);
//        mEtConformPwd.setError(null);
//        mEtUsername.setError(null);
    }

    private void initViewData() {
        mChkBokAgreeProtorl.setText(buildSubText());

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSwitchRegisterMode.setText(R.string.register_mode_phone);
        mPhoneNoRegion.setVisibility(View.GONE);
        mEmailCaptchaRegion.setVisibility(View.VISIBLE);
        mEmailRegion.setVisibility(View.VISIBLE);
    }

    private Captcha mEmailCaptcha;

    private Captcha mForGetSmsCaptcha;

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
                    mIvEmailCaptcha.setImageBitmap(decodedByte);
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

    @OnClick(R.id.tv_got_phone_msg_captcha)
    void actionGotPhoneMsgCaptcha(){
        if(TextUtils.isEmpty(mEtPhoneNo.getText().toString())){
            clearAllError();
            mEtPhoneMsgCaptcha.setError("请输入手机号码");
            mEtPhoneMsgCaptcha.requestFocus();
            return;
        }

        executeRequest(new CaptchaRequest(new Response.Listener<Captcha>() {
            @Override
            public void onResponse(Captcha response) {
                mForGetSmsCaptcha = response;
                if (response == null || TextUtils.isEmpty(response.getBase64())) {
                    return;
                }

                View layout = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.dialog_layout_captcha_input, null);
                ImageView imageView = (ImageView) layout.findViewById(R.id.iv_for_sms_captcha);
                final EditText valueCaptcha = (EditText) layout.findViewById(R.id.et_for_sms_captcha_value);

                //显示验证码。
                String base64 = response.getBase64().replace("data:image/png;base64,", "").replace("==", "");

                try {
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                imageView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //TODO 重新加载图形验证码
                    }
                });

                AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("请输入下图中的文字或字母")
                        .setView(layout)
                        .setNegativeButton("", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 发送手机验证码
                                Map<String, String> map = new HashMap();

                                map.put("mobile_number",mEtPhoneNo.getText().toString());
                                map.put("zone", "0086");
                                map.put("captcha_id", mForGetSmsCaptcha.getId());
                                map.put("captcha_token",valueCaptcha.getText().toString());
                                executeRequest(new SmsCaptchaRequest(map,
                                        new Response.Listener<BaseResult>() {
                                            @Override
                                            public void onResponse(BaseResult response) {
                                                if ("SUCCESS".equals(response.getResult())) {
                                                    //验证码发送成功。
                                                    //120秒的后重新发送短信验证码
                                                    Toast.makeText(RegisterActivity.this,"请注意查收验证码",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }));
                            }
                        }).show();
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ));
    }

    private void switchRegisterMode(){
        String regModeBtnStr = mBtnSwitchRegisterMode.getText().toString();
        if(getString(R.string.register_mode_phone).equals(regModeBtnStr)) {
            mBtnSwitchRegisterMode.setText(R.string.register_mode_email);
            mPhoneNoRegion.setVisibility(View.VISIBLE);
            mEmailCaptchaRegion.setVisibility(View.GONE);
            mEmailRegion.setVisibility(View.GONE);
        }else{
            mBtnSwitchRegisterMode.setText(R.string.register_mode_phone);
            mPhoneNoRegion.setVisibility(View.GONE);
            mEmailCaptchaRegion.setVisibility(View.VISIBLE);
            mEmailRegion.setVisibility(View.VISIBLE);
        }
    }

    private CharSequence buildSubText() {
        String textStr = "我已经认真阅读并同意接受 用户协议";
        String flag = "用户协议";
        int start = textStr.indexOf(flag);
        int end = start + flag.length();
        SpannableString spannableString  = new SpannableString(Html.fromHtml(textStr));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebViewActivity.launch(widget.getContext(),
                        new WebViewActivity.WebLaunchConfig("http://tuchong.com/info/terms/",
                                "图虫网用户协议"));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
                ds.clearShadowLayer();
                //ds.setColor(getResources().getColor(R.color.linkColorTint));
                //ds.linkColor = (getResources().getColor(R.color.linkColorTint));
                ds.setLinearText(true);
            }
        };

        spannableString.setSpan(clickableSpan, start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    private String nonceStrng = "";

    @OnClick(R.id.btn_action_register)
    void actionRegister(){
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
        //TODO  注册用户账号。 Jerrysher  2016.1.2
        Map<String, String> map = new HashMap<>();
        map.put("nonce",nonceStrng);
        map.put("user_email", mEtUserEmail.getText().toString());
        map.put("zone","0086");
        map.put("sms_captcha",mEtPhoneMsgCaptcha.getText().toString());
        map.put("user_name",mEtUsername.getText().toString());//
        map.put("user_password",new RSA().encrypt(mEtPassord.getText().toString()));//
        map.put("user_password2",new RSA().encrypt(mEtConformPwd.getText().toString()));
        map.put("captcha_id",mEmailCaptcha.getId());//
        map.put("captcha_token",mEtEmailCaptcha.getText().toString());//
        map.put("accept","default-follow");//

        executeRequest(new RegisterRequest(map,
                new Response.Listener<BaseResult>() {

                    @Override
                    public void onResponse(BaseResult response) {
                        Log.d("reg","response" +  response);
                        if ("SUCCESS".equals(response.getResult())){
                            //注册成功。
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

}
