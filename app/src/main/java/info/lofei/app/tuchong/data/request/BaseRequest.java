package info.lofei.app.tuchong.data.request;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import info.lofei.app.tuchong.util.Constant;

/**
 * BaseRequest, 统一处理cookie和登录相关事宜.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-30 11:57
 */
public abstract class BaseRequest<T> extends Request<T> {

    private static final String TAG = BaseRequest.class.getSimpleName();

    private Map<String, String> mParams;

    private Response.Listener<T> mListener;

    public BaseRequest(final int method, final String url, final Map<String, String> params, final Response.Listener<T> listener, final Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mParams = params;
        mListener = listener;
    }

    public BaseRequest(final String url, final Response.Listener<T> listener, final Response.ErrorListener errorListener) {
        this(Method.GET, url, null, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>(8);
//        addCookie(headers);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(final NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));

            Log.d(TAG, json);

            JSONObject jsonObject = new JSONObject(json);

            String result = jsonObject.optString(Constant.RESULT_KEY, Constant.RESULT_FAILED);
            if (result.equalsIgnoreCase(Constant.RESULT_SUCCESS)) {
                return Response.success(parseResponse(jsonObject), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new AuthFailureError());
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(final T response) {
        mListener.onResponse(response);
    }

    protected abstract T parseResponse(final JSONObject jsonObject);


}
