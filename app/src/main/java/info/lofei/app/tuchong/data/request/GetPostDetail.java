package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.lofei.app.tuchong.model.TCComment;
import info.lofei.app.tuchong.model.TCImage;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.utils.SitesMapCache;

/**
 * Created by jerrysher
 *  jerrysher@jerryzhang.net on 15/11/23.
 */
public class GetPostDetail extends BaseRequest<TCPost> {

    private Gson mGson;

    public GetPostDetail(String url, Response.Listener<TCPost> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCPost parseResponse(JSONObject jsonObject) {
        String json = jsonObject.optJSONObject("post").toString();
        TCPost post = mGson.fromJson(json, TCPost.class);

        String siteJson = jsonObject.optJSONArray("images").toString();
        List<TCImage> postList = mGson.fromJson(siteJson, new TypeToken<ArrayList<TCImage>>() {
        }.getType());

        if(post != null){
            post.setImages(postList);
        }

        return post;
    }
}
