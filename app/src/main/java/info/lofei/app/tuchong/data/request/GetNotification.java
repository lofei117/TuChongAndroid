package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import info.lofei.app.tuchong.model.TCNotification;

/**
 * Created by jerrysher on 11/17/15.
 */
public class GetNotification extends BaseRequest<TCNotification>{

    private Gson mGson;
    protected GetNotification(String url, Response.Listener<TCNotification> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCNotification parseResponse(JSONObject jsonObject) {
        TCNotification tcNotification = null;
        if(jsonObject != null){
            tcNotification = mGson.fromJson(jsonObject.toString(), TCNotification.class);
        }
        return tcNotification;
    }
}
