package info.lofei.app.tuchong.model.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerrysher on 10/1/15.
 */
public class LoginResult {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_PWD_OR_NAME_ERROR = 4;
    public static final int CODE_VIDIFY_ERROR = 5;
    public static final int CODE_NEED_CAPTCHA = 11;

    /*
    11
    请填写验证码


    "message": "登录成功",
    "hint": "正在刷新页面，请稍候",
    "identity": "1100130",
    "result": "SUCCESS"

    {
    "result": "ERROR",
    "code": 4,
    "message": "用户名或密码错误"
    }
     */
    private String message;

    private String hint;

    @SerializedName("identity")
    private String id;

    private String result;

    private int code;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
