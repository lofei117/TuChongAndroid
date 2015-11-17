package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.util.SitesMapCache;

/**
 * 站点信息的请求
 *
 * @author jerrysher jerrysher@jerryzhang.net
 * @version 1.0.0
 *          created at: 2015-11-17 22:49
 */
public class GetSiteRequest extends BaseRequest<TCSite> {

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
        return tcSite;
    }
}
