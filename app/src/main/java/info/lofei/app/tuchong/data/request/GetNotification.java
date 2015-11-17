package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import info.lofei.app.tuchong.model.TcNotification;

/**
 * Created by jerrysher on 11/17/15.
 */
public class GetNotification extends BaseRequest<TcNotification>{

    private Gson mGson;
    protected GetNotification(String url, Response.Listener<TcNotification> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TcNotification parseResponse(JSONObject jsonObject) {
        TcNotification tcNotification = null;
        if(jsonObject != null){
            tcNotification = mGson.fromJson(jsonObject.toString(), TcNotification.class);
        }
        return tcNotification;
    }
}
