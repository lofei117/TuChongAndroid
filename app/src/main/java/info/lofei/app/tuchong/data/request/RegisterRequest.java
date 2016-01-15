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

import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.model.result.LoginResult;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 16/1/3.
 */
public class RegisterRequest extends BaseRequest<BaseResult>{
    public RegisterRequest(Map<String, String> params, Response.Listener<BaseResult> listener, Response.ErrorListener errorListener) {
        super(Method.POST, TuChongApi.REGIESTER_USER, params, listener, errorListener);
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
    protected BaseResult parseResponse(final JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), BaseResult.class);
    }
//
//    {
//        "mail": "sent",
//            "redirect": "http:\/\/tuchong.com\/",
//            "result": "SUCCESS"
//    }
}
