package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import info.lofei.app.tuchong.data.request.result.FavoriteResult;

/**
 * Created by jerrysher on 15/12/4.
 */
public class FavoriteRequest extends BaseRequest<FavoriteResult>{
    Gson mGson;

    public FavoriteRequest(int method, String url, Response.Listener<FavoriteResult> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected FavoriteResult parseResponse(JSONObject jsonObject) {
        FavoriteResult  favoriteRequest = mGson.fromJson(jsonObject.toString(), FavoriteResult.class);
        return favoriteRequest;
    }
}
