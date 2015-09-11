package info.lofei.app.tuchong.data.request;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Map;

/**
 * 登录请求.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 13:36
 */
public class LoginRequest extends BaseRequest<Boolean> {

    public LoginRequest(final int method, final String url, final Map<String, String> params, final Response.Listener<Boolean> listener, final Response.ErrorListener errorListener) {
        super(method, url, params, listener, errorListener);
    }

    @Override
    protected Boolean parseResponse(final JSONObject jsonObject) {
        return Boolean.TRUE;
    }

}
