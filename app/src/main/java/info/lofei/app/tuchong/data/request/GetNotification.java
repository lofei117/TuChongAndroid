package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import info.lofei.app.tuchong.model.TCNotifications;

/**
 * Created by jerrysher on 11/17/15.
 */
public class GetNotification extends BaseRequest<TCNotifications>{

    private Gson mGson;
    protected GetNotification(String url, Response.Listener<TCNotifications> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCNotifications parseResponse(JSONObject jsonObject) {
        TCNotifications tcNotification = null;
        if(jsonObject != null){
            tcNotification = mGson.fromJson(jsonObject.toString(), TCNotifications.class);
        }
        return tcNotification;
    }
}
