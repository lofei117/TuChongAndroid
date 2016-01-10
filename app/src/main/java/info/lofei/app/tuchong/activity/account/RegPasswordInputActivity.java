package info.lofei.app.tuchong.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import javax.xml.transform.Result;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.BaseActivity;

/**
 * Created by jerrysher on 16/1/5.
 */
public class RegPasswordInputActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_DATA_REG_TYPE = "extra_data_reg_type";
    public static final String REG_TYPE_PHONE = "phone";
    public static final String REG_TYPE_EMAIL = "email";
    public static final String EXTRA_DATA_REG_PHONE_ZONE = "extra_data_reg_phone_zone";
    public static final String EXTRA_DATA_REG_PHONE_NO = "extra_data_reg_phone_no";
    public static final String EXTRA_DATA_SMS_CODE = "extra_data_sms_code";
    public static final String EXTRA_DATA_REG_EMAIL = "extra_data_reg_email";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_password)
    AppCompatEditText mEtPassword;

    @Bind(R.id.et_conform_password)
    AppCompatEditText mEtConformPassword;

    @Bind(R.id.btn_action_next)
    Button mBtnActionNext;

    private String getRegType;

    private String getPhoneNo;
    private String getPoneZone;
    private String getSmsCode;

    private String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getIntentData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_register_pwd);
        ButterKnife.bind(this);
        setListeners();

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        getRegType = getIntent().getStringExtra(EXTRA_DATA_REG_TYPE);
        getEmail = getIntent().getStringExtra(EXTRA_DATA_REG_EMAIL);

        getPhoneNo = getIntent().getStringExtra(EXTRA_DATA_REG_PHONE_NO);
        getPoneZone = getIntent().getStringExtra(EXTRA_DATA_REG_PHONE_ZONE);
        getSmsCode = getIntent().getStringExtra(EXTRA_DATA_SMS_CODE);

    }

    private void setListeners() {
        mBtnActionNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_action_next:{
                if(checkPasswords()){
                    return;
                }
                String pwd = mEtConformPassword.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(UserNameInputActivity.EXTRA_DATA_PWD, pwd);
                setResult(RESULT_OK, intent);
                finish();
//
//                if(REG_TYPE_EMAIL.equals(getRegType)){
//                    Intent intent = new Intent();
//                    intent.putExtra(UserNameInputActivity.EXTRA_DATA_PWD, pwd);
//                    setResult(RESULT_OK, intent);
//                    //UserNameInputActivity.launch(this, getEmail, pwd);//携带密码  手机号码， 验证码 到下一步注册
//                }else{
//
//                    UserNameInputActivity.launch(this, getPoneZone,
//                            getPhoneNo, getSmsCode , pwd);//携带密码  手机号码， 验证码 到下一步注册
//                }

                break;
            }

        }
    }

    private boolean checkPasswords() {
        //判断是否为空，判断是否一致。判断是否大于6位
        String pwd = mEtPassword.getText().toString();
        if(TextUtils.isEmpty(pwd)){
            mEtPassword.setError("密码不能为空");
            return true;
        }

        if(pwd.length() < 6){
            mEtPassword.setError("密码长度至少6位");
            return true;
        }

        String rePwd = mEtConformPassword.getText().toString();

        if(TextUtils.isEmpty(rePwd)){
            mEtConformPassword.setError("请再次输入密码");
            return true;
        }

        if(rePwd.length() < 6){
            mEtConformPassword.setError("密码长度至少6位");
            return true;
        }

        if(!rePwd.equals(pwd)){
            mEtConformPassword.setError("两次密码输入不一致");
            return true;
        }

        return false;
    }


    public static void launch(Context context, String email){
        Intent intent = new Intent(context, RegPasswordInputActivity.class);
        intent.putExtra(EXTRA_DATA_REG_TYPE, REG_TYPE_EMAIL);
        intent.putExtra(EXTRA_DATA_REG_EMAIL, email);
        context.startActivity(intent);
    }
}
