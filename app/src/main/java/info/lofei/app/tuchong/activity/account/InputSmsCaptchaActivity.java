package info.lofei.app.tuchong.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.BaseActivity;

/**
 * Created by jerrysher on 16/1/5.
 */
public class InputSmsCaptchaActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_DATA_PHONE_NO = "Extra_data_phone_no";
    public static final int REQUEST_CODE_PHONE_NO_GET_PWD = 1;
    public static final int REQUEST_CODE_PHONE_REG_GET_USERNAME = 2;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_for_sms_captcha)
    AppCompatEditText mEtForSmsCaptcha;

    @Bind(R.id.btn_action_register)
    Button mBtnActionRegister;

    @Bind(R.id.tv_time_down_show)
    TextView mTvTimeDownShow;

    @Bind(R.id.tv_phone_msm_text_hint)
    TextView mTvPhoneMsmTextHint;

    private CountDownTimer countDownTimer = new CountDownTimer(120 * 1000 , 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvTimeDownShow.setText(getString(R.string.phone_register_resent_sms_code, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            mTvTimeDownShow.setText("重新发送验证码");
            mTvTimeDownShow.setTextColor(Color.BLUE);
            mTvTimeDownShow.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //重新回到上一页，重新刷新图形验证码，重新输入验证码。
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });
        }
    };

    private String mGetPhoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_captcha_input);
        ButterKnife.bind(this);
        setListeners();
        mGetPhoneNo = getIntent().getStringExtra(EXTRA_DATA_PHONE_NO);
        mTvPhoneMsmTextHint.setText(getString(R.string.sms_captcha_hint_text, mGetPhoneNo));

        countDownTimer.start();

        mTvTimeDownShow.setText(getString(R.string.phone_register_resent_sms_code, 120));
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setListeners() {
        mBtnActionRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action_register: {
                Intent intent = new Intent(this, RegPasswordInputActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PHONE_NO_GET_PWD);
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PHONE_NO_GET_PWD){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    String pwd = data.getStringExtra(UserNameInputActivity.EXTRA_DATA_PWD);
                    Intent intent = new Intent(this, UserNameInputActivity.class);
                    intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_ZONE, "0086");
                    intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_REG_PHONE_NO, mGetPhoneNo);
                    intent.putExtra(RegPasswordInputActivity.EXTRA_DATA_SMS_CODE, mEtForSmsCaptcha.getText().toString());
                    intent.putExtra(UserNameInputActivity.EXTRA_DATA_PWD, pwd);
                    startActivityForResult(intent, REQUEST_CODE_PHONE_REG_GET_USERNAME);
                }
            }
        }

        if(REQUEST_CODE_PHONE_REG_GET_USERNAME == requestCode){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    Toast.makeText(this, data.getStringExtra(UserNameInputActivity.FOR_RESULT_ERROR_MSG), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
