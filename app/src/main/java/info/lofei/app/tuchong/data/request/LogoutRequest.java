package info.lofei.app.tuchong.data.request;

import com.google.gson.Gson;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import info.lofei.app.tuchong.data.request.result.LogoutResult;

/**
 * 登出请求.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 13:36
 *          TODO logout interface need impl.
 */
public class LogoutRequest extends BaseRequest<LogoutResult> {

    public LogoutRequest(final int method, final String url,  final Response.Listener<LogoutResult> listener, final Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    protected Response<LogoutResult> parseNetworkResponse(NetworkResponse response) {
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
    protected LogoutResult parseResponse(final JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), LogoutResult.class);
    }

}
