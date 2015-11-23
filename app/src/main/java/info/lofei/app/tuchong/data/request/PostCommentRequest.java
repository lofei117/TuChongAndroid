package info.lofei.app.tuchong.data.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import info.lofei.app.tuchong.data.request.result.CommentResult;
import info.lofei.app.tuchong.data.request.result.LoginResult;

/**
 * Created by JunyiZhou on 2015/11/23.
 */
public class PostCommentRequest extends BaseRequest<CommentResult>{
    public PostCommentRequest(int method, String url, Map<String, String> params, Response.Listener<CommentResult> listener, Response.ErrorListener errorListener) {
        super(method, url, params, listener, errorListener);
    }

    @Override
    protected Response<CommentResult> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(parseResponse(new JSONObject(json)), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected CommentResult parseResponse(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), CommentResult.class);
    }
}
