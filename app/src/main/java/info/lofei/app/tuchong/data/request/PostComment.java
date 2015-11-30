package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import info.lofei.app.tuchong.model.TCComment;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 15/11/29.
 */
public class PostComment extends BaseRequest<TCComment> {
    private Gson mGson;
    public PostComment(String url ,Map<String, String> params, Response.Listener<TCComment> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, params, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCComment parseResponse(JSONObject jsonObject) {
        String commentStr = jsonObject.optJSONObject("comment").toString();
        TCComment  comment = mGson.fromJson(commentStr, TCComment.class);
        return comment;
    }
}
