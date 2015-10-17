package info.lofei.app.tuchong.data.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import info.lofei.app.tuchong.data.request.result.LoginResult;

/**
 * 登录请求.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 13:36
 */
public class LoginRequest extends BaseRequest<LoginResult> {

    public LoginRequest(final int method, final String url, final Map<String, String> params, final Response.Listener<LoginResult> listener, final Response.ErrorListener errorListener) {
        super(method, url, params, listener, errorListener);
    }

    @Override
    protected Response<LoginResult> parseNetworkResponse(NetworkResponse response) {
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
    protected LoginResult parseResponse(final JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), LoginResult.class);
    }

}
