package info.lofei.app.tuchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.BaseApplication;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.request.ForgotFwdRequest;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.data.request.result.LogoutResult;
import info.lofei.app.tuchong.model.result.LoginResult;
import info.lofei.app.tuchong.utils.PreferenceUtil;

/**
 * Created by jerrysher on 15/12/17.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.email)
    EditText  emailInput;

    @Bind(R.id.submit_forget_pwd)
    View submitForgetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        ButterKnife.bind(this);

        submitForgetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.submit_forget_pwd:
                Map<String, String> p = new HashMap<>();
                p.put("email", emailInput.getText().toString());
                executeRequest(new ForgotFwdRequest(p, new Response.Listener<BaseResult> (){

                    @Override
                    public void onResponse(BaseResult response) {
                        Log.d("resetpwd", " reset " + response);
                        Toast.makeText(BaseApplication.getBaseApplication(),
                                response.getMessage(), Toast.LENGTH_SHORT).show();
                        switch (response.getCode()) {
                            case LoginResult.CODE_SUCCESS:
                                finish();
                                break;
                            case LoginResult.CODE_NEED_PARMS:
                                break;
                            case LoginResult.CODE_USER_NOT_EXIST:

                                break;
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("resetpwd"," error " + error);
                                emailInput.setError("");
                            }
                        }));
                break;
        }
    }
}
