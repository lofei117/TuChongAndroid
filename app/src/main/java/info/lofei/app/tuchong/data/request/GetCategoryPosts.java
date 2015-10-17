package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.lofei.app.tuchong.model.TCPost;

/**
 * 首页的请求.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 17:24
 */
public class GetCategoryPosts extends BaseRequest<List<TCPost>> {

    private Gson mGson;

    public GetCategoryPosts(final String url, final Response.Listener<List<TCPost>> listener, final Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected List<TCPost> parseResponse(final JSONObject jsonObject) {
        String json = jsonObject.optJSONArray("posts").toString();
        List<TCPost> postList = mGson.fromJson(json, new TypeToken<ArrayList<TCPost>>() {
        }.getType());

        return postList;
    }
}
