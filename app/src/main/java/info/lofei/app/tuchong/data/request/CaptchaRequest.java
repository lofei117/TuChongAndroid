package info.lofei.app.tuchong.data.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import info.lofei.app.tuchong.data.request.result.Captcha;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 10/8/15.
 */
public class CaptchaRequest extends BaseRequest<Captcha>{
    public CaptchaRequest(Response.Listener<Captcha> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, TuChongApi.CAPTCHA_URL, null, listener, errorListener);
    }

    @Override
    protected Captcha parseResponse(JSONObject jsonObject) {
        Captcha captcha = null;
        String string  = jsonObject.toString();
        try {
            captcha = new Gson().fromJson(string, Captcha.class);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        return captcha;
    }
}
