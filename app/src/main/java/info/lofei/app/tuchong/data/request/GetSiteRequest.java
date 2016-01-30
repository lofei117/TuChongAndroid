package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import info.lofei.app.tuchong.model.TCSite;

/**
 * 站点信息的请求
 *
 * @author jerrysher jerrysher@jerryzhang.net
 * @version 1.0.0
 *          created at: 2015-11-17 22:49
 */
public class GetSiteRequest extends BaseRequest<TCSite> {

    private static final int IS_FOLLOWING = 1;

    private Gson mGson;

    public GetSiteRequest(final String url, final Response.Listener<TCSite> listener,
                          final Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCSite parseResponse(final JSONObject jsonObject) {
        String json = jsonObject.optJSONObject("site").toString();
        TCSite tcSite = mGson.fromJson(json, TCSite.class);

        JSONObject relationshipJsonObj = jsonObject.optJSONObject("relationship");
        if(relationshipJsonObj != null){
            tcSite.setIsFollowing(IS_FOLLOWING == relationshipJsonObj.optInt("is_following"));
        }

        return tcSite;
    }
}
