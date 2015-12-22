package info.lofei.app.tuchong.data.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.data.request.result.LogoutResult;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 15/12/18.
 */
public class ForgotFwdRequest extends BaseRequest<BaseResult>{

    public ForgotFwdRequest(Map<String, String> p, Response.Listener<BaseResult> listener, Response.ErrorListener errorListener) {
        super(Method.DELETE, TuChongApi.RESET_PWD, p, listener, errorListener);
    }

    @Override
    protected Response<BaseResult> parseNetworkResponse(NetworkResponse response) {
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
    protected BaseResult parseResponse(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), BaseResult.class);
    }
}
