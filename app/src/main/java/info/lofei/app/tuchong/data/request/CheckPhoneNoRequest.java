package info.lofei.app.tuchong.data.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import info.lofei.app.tuchong.data.request.result.BaseResult;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by jerrysher on 15/12/31.
 */
public class CheckPhoneNoRequest extends BaseRequest<BaseResult>{
    public CheckPhoneNoRequest(String phoneNo, Response.Listener<BaseResult> listener, Response.ErrorListener errorListener) {
        super(TuChongApi.ACCOUNTS_CHECK_PHONE_NO + phoneNo, listener, errorListener);
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
