package info.lofei.app.tuchong.data.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Map;

import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.data.request.result.FavoriteResult;
import info.lofei.app.tuchong.utils.Constant;
import info.lofei.app.tuchong.utils.PreferenceUtil;

/**
 * Created by jerrysher on 16/1/11.
 */
public class FollowingRequest extends BaseRequest<BaseResult>{

    Gson mGson;

    public FollowingRequest(int method, String url, Map<String,String> map, Response.Listener<BaseResult> listener, Response.ErrorListener errorListener) {
        super(method, url, map, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected BaseResult parseResponse(JSONObject jsonObject) {
        BaseResult  favoriteRequest = mGson.fromJson(jsonObject.toString(), BaseResult.class);
        return favoriteRequest;
    }
}
