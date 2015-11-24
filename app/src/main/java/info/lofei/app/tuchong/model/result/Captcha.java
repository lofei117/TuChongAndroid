package info.lofei.app.tuchong.model.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerrysher on 10/8/15.
 */
public class Captcha {
    @SerializedName("captchaId")
    private String id;
    @SerializedName("captchaBase64")
    private String base64;
    @SerializedName("result")
    private String result;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
