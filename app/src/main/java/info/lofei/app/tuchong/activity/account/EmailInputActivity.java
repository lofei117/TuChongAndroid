package info.lofei.app.tuchong.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import javax.xml.transform.Result;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.BaseActivity;
import info.lofei.app.tuchong.activity.WebViewActivity;
import info.lofei.app.tuchong.data.request.CheckEmailRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.result.LoginResult;

/**
 * Created by jerrysher on 16/1/5.
 */
public class EmailInputActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_EMAIL_REG_GET_PWD = 3;
    private static final int REQUEST_CODE_EMAIL_REG_GET_USERNAME = 4;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_email)
    AppCompatEditText mEtEmail;

    @Bind(R.id.view_to_view_show_reg_protorl)
    TextView mViewToViewShowRegProtorl;

    @Bind(R.id.btn_action_register)
    Button mBtnActionRegister;

    @Bind(R.id.action_goto_phone_reg)
    TextView mActionGotoPhoneReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_input);
        ButterKnife.bind(this);
        setListeners();

        mViewToViewShowRegProtorl.setText(buildSubText());
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setListeners() {
        mActionGotoPhoneReg.setOnClickListener(this);
        mBtnActionRegister.setOnClickListener(this);

        mViewToViewShowRegProtorl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_action_register:{
                if(TextUtils.isEmpty(mEtEmail.getText().toString())){
                    mEtEmail.setError("请输入邮箱");
                    return;
                }
                checkEmail(mEtEmail.getText().toString());
                break;
            }
            case R.id.action_goto_phone_reg:{
                finish();
                PhoneNoInputActivity.launch(this);
                break;
            }
            case R.id.view_to_view_show_reg_protorl:{
                WebViewActivity.launch(this, new WebViewActivity.WebLaunchConfig("http://tuchong.com/info/terms/",
                        "图虫网用户协议"));
                break;
            }
        }

    }

    private void checkEmail(final String email) {
        executeRequest(new CheckEmailRequest(email, new Response.Listener<BaseResult>() {

            @Override
            public void onResponse(BaseResult response) {
                if (response.getCode() == LoginResult.CODE_SUCCESS) {
                    Intent intent = new Intent(EmailInputActivity.this, RegPasswordInputActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_EMAIL_REG_GET_PWD);
                } else {
                    mEtEmail.setError(response.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE_EMAIL_REG_GET_PWD == requestCode){
            if(resultCode == RESULT_OK){
                if(data != null){
                    String pwd = data.getStringExtra(UserNameInputActivity.EXTRA_DATA_PWD);
                    Intent intent = new Intent(this, UserNameInputActivity.class);
                    intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_REG_EMAIL, mEtEmail.getText().toString());
                    intent.putExtra(UserNameInputActivity.EXTRA_DATA_PWD, pwd);
                    startActivityForResult(intent, REQUEST_CODE_EMAIL_REG_GET_USERNAME);
                }
            }
        }

        if(REQUEST_CODE_EMAIL_REG_GET_USERNAME == requestCode){
            if(resultCode == RESULT_OK){
                if(data != null){
                    Toast.makeText(this, data.getStringExtra(UserNameInputActivity.FOR_RESULT_ERROR_MSG), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EmailInputActivity.class));
    }

    private CharSequence buildSubText() {
        String textStr = getString(R.string.register_user_protorl);
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

}
