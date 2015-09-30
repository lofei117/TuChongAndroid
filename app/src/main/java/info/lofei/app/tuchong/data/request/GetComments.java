package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.lofei.app.tuchong.model.TCComment;

/**
 * Get comments of a post.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-17 14:29
 */
public class GetComments extends BaseRequest<List<TCComment>> {

    private Gson mGson;

    public GetComments(final String url, final Response.Listener<List<TCComment>> listener, final Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected List<TCComment> parseResponse(final JSONObject jsonObject) {
        String json = jsonObject.optJSONArray("commentlist").toString();
        List<TCComment> commentList = mGson.fromJson(json, new TypeToken<ArrayList<TCComment>>() {
        }.getType());
        return commentList;
    }
}
