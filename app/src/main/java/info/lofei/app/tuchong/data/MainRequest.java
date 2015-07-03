package info.lofei.app.tuchong.data;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.lofei.app.tuchong.model.TCActivity;

/**
 * 首页的请求.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 17:24
 */
public class MainRequest extends BaseRequest<List<TCActivity>> {

    private Gson mGson;

    public MainRequest(final String url, final Response.Listener<List<TCActivity>> listener, final Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected List<TCActivity> parseResponse(final JSONObject jsonObject) {
        String json = jsonObject.optJSONArray("activityList").toString();
        List<TCActivity> activityList = mGson.fromJson(json, new TypeToken<ArrayList<TCActivity>>(){}.getType());


        return activityList;
    }
}
