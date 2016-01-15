package info.lofei.app.tuchong.data.request.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerrysher on 15/12/19.
 */
public class BaseResult {
    private String message;

    private String result;

    private int code;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
