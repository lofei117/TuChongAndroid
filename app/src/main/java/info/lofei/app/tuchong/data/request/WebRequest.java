package info.lofei.app.tuchong.data.request;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.lofei.app.tuchong.model.result.LoginResult;

/**
 * Created by jerrysher on 16/1/3.
 */
public class WebRequest extends BaseRequest<String> {
    public WebRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String group = "";
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));

            Pattern pattern =  Pattern.compile("name=\"nonce\" value=\"[A-Za-z0-9]{16}\"");
            Matcher matcher = pattern.matcher(json);

            if(matcher.find()){
                group = matcher.group();
                if(!TextUtils.isEmpty(group)){
                    group = group.substring(20, group.length() -1);
                }
            }

            if(TextUtils.isEmpty(group)){
                Pattern pattern2 =  Pattern.compile("nonce = '[A-Za-z0-9]{16}'");
                Matcher matcher2 = pattern2.matcher(json);
                //nonce = '6cc0a3519df37770'
                if(matcher2.find()){
                    group = matcher2.group();
                    if(!TextUtils.isEmpty(group)){
                        group = group.substring(8, group.length() -1);
                    }
                }
            }

            //name="nonce" value="[A-Za-z0-9]{16}"
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } finally {
            return Response.success(group, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    @Override
    protected String parseResponse(JSONObject jsonObject) {
        return "";
    }
}
