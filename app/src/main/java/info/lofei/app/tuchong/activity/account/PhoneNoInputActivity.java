package info.lofei.app.tuchong.activity.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.BaseActivity;
import info.lofei.app.tuchong.activity.WebViewActivity;
import info.lofei.app.tuchong.data.request.CaptchaRequest;
import info.lofei.app.tuchong.data.request.CheckPhoneNoRequest;
import info.lofei.app.tuchong.data.request.SmsCaptchaRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.result.Captcha;

/**
 * Created by jerrysher on 16/1/5.
 */
public class PhoneNoInputActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_SMS_CODE = 100;
    @Bind(R.id.et_phone_no)
    EditText mEtPhoneNo;

    @Bind(R.id.et_captcha)
    EditText mEtCaptcha;

    @Bind(R.id.iv_captcha_show)
    ImageView mIvShowCaptcha;

    @Bind(R.id.iv_captcha_fetch)
    View mAcitonFetchCaptcha;

    @Bind(R.id.view_to_view_show_reg_protorl)
    TextView mViewToShowRegPro;

    @Bind(R.id.btn_action_register)
    Button mBtnNextRegister;

    @Bind(R.id.action_goto_email_reg)
    View mViewToMailReg;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.region_email_captcha)
    LinearLayout mRegionEmailCaptcha;

    private Captcha mPhoneImgCaptcha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_no_register);
        ButterKnife.bind(this);

        setListeners();

        mViewToShowRegPro.setText(buildSubText());
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetechPhoneImgCaptcha();
    }

    private void fetechPhoneImgCaptcha() {
        executeRequest(new CaptchaRequest(new Response.Listener<Captcha>() {
            @Override
            public void onResponse(Captcha response) {
                mPhoneImgCaptcha = response;
                if (response == null || TextUtils.isEmpty(response.getBase64())) {
                    return;
                }
                //显示验证码。
                String base64 = response.getBase64().replace("data:image/png;base64,", "").replace("==", "");

                try {
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mIvShowCaptcha.setImageBitmap(decodedByte);
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
        mBtnNextRegister.setOnClickListener(this);

        mIvShowCaptcha.setOnClickListener(this);
        mAcitonFetchCaptcha.setOnClickListener(this);

        mViewToShowRegPro.setOnClickListener(this);
        mViewToMailReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_captcha_show:
            case R.id.iv_captcha_fetch: {
                fetechPhoneImgCaptcha();
                break;
            }
            case R.id.view_to_view_show_reg_protorl: {
                WebViewActivity.launch(this, new WebViewActivity.WebLaunchConfig("http://tuchong.com/info/terms/",
                        "图虫网用户协议"));
                break;
            }
            case R.id.btn_action_register: {
                if(mRegionEmailCaptcha.getVisibility() == View.VISIBLE){
                    //判断验证码是否有空， 不为空直接请求发短信验证码的请求。
                    if(TextUtils.isEmpty(mEtCaptcha.getText().toString())){
                        //mEtCaptcha
                        mEtCaptcha.setError("验证码不能为空");
                    }else{
                        Map<String, String> map = new HashMap();

                        map.put("mobile_number",mEtPhoneNo.getText().toString());
                        map.put("zone", "0086");
                        map.put("captcha_id", mPhoneImgCaptcha.getId());
                        map.put("captcha_token", mEtCaptcha.getText().toString());
                        executeRequest(new SmsCaptchaRequest(map,
                                new Response.Listener<BaseResult>() {
                                    @Override
                                    public void onResponse(BaseResult response) {
                                        if(response == null){
                                            Toast.makeText(PhoneNoInputActivity.this, "网络请求出错", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        if ("SUCCESS".equals(response.getResult())) {
                                            //验证码发送成功。
                                            //120秒的后重新发送短信验证码
                                            Toast.makeText(PhoneNoInputActivity.this, "请注意查收短信验证码", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(PhoneNoInputActivity.this, InputSmsCaptchaActivity.class);
                                            intent.putExtra(InputSmsCaptchaActivity.EXTRA_DATA_PHONE_NO, mEtPhoneNo.getText().toString());
                                            startActivityForResult(intent, REQUEST_CODE_SMS_CODE);
                                        }else{
                                            new AlertDialog.Builder(PhoneNoInputActivity.this)
                                                    .setTitle("提醒")
                                                    .setMessage(response.getMessage())
                                                    .setNegativeButton("确定", null).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }));

                    }

                }else{
                    // 判断手机号码是否有效，并且重复。 显示验证码
                    if(TextUtils.isEmpty(mEtPhoneNo.getText().toString())){
                        mEtPhoneNo.setError("手机号码不能为空");
                        return;
                    }

                    executeRequest(new CheckPhoneNoRequest(mEtPhoneNo.getText().toString(), new Response.Listener< BaseResult>(){

                        @Override
                        public void onResponse(BaseResult response) {
                            if(response != null && "SUCCESS".equals(response.getResult())){
                                //手机号码可以用
                                mEtPhoneNo.setError(null);
                                mRegionEmailCaptcha.setVisibility(View.VISIBLE);
                            }else{
                                mEtPhoneNo.setError(response != null ? response.getMessage() : "网络请求错误");
                            }
                        }
                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mEtPhoneNo.setError("网络请求错误");
                        }
                    }));
                }
                break;
            }
            case R.id.action_goto_email_reg: {
                finish();
                EmailInputActivity.launch(this);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_SMS_CODE:{
                    fetechPhoneImgCaptcha();
                    break;
                }
            }
        }
    }

    private CharSequence buildSubText() {
        String textStr = getString(R.string.register_user_protorl);
        String flag = "用户协议";
        int start = textStr.indexOf(flag);
        int end = start + flag.length();
        SpannableString spannableString = new SpannableString(Html.fromHtml(textStr));

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

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PhoneNoInputActivity.class));
    }
}
