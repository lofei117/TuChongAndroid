package info.lofei.app.tuchong.data.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.android.volley.Response;

import org.json.JSONObject;

import info.lofei.app.tuchong.model.TCExif;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-10-23 17:44
 */
public class GetExif extends BaseRequest<TCExif> {

    private Gson mGson;

    public GetExif(long imageId, long postId, Response.Listener<TCExif> listener, Response.ErrorListener errorListener) {
        super(String.format(TuChongApi.EXIF_URL, imageId, postId), listener, errorListener);
        mGson = new Gson();
    }

    @Override
    protected TCExif parseResponse(JSONObject jsonObject) {
        String json = jsonObject.optJSONObject("exif").toString();
        TCExif tcExif = mGson.fromJson(json, new TypeToken<TCExif>() {
        }.getType());
        return tcExif;
    }
}
