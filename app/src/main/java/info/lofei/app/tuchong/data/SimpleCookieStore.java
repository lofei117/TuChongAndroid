package info.lofei.app.tuchong.data;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import info.lofei.app.tuchong.util.Constant;
import info.lofei.app.tuchong.util.PreferenceUtil;

/**
 * Simple Cookie store, save cookie to preference.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 21:45
 */
public class SimpleCookieStore implements CookieStore {

    private CookieStore mDefaultCookieStore;

    private Gson mGson;

    public SimpleCookieStore() {
        mDefaultCookieStore = new CookieManager().getCookieStore();
        mGson = new Gson();

        applyCookie();
    }

    private void applyCookie() {
        String sessionCookieString = PreferenceUtil.getString(Constant.SESSION_KEY, "");
        if (!TextUtils.isEmpty(sessionCookieString)) {
            HttpCookie httpCookie = mGson.fromJson(sessionCookieString, HttpCookie.class);
            mDefaultCookieStore.add(URI.create(httpCookie.getDomain()), httpCookie);
        }
        String emailCookieString = PreferenceUtil.getString(Constant.EMAIL_KEY, "");
        if (!TextUtils.isEmpty(emailCookieString)) {
            HttpCookie httpCookie = mGson.fromJson(emailCookieString, HttpCookie.class);
            mDefaultCookieStore.add(URI.create(httpCookie.getDomain()), httpCookie);
        }
        String tokenCookieString = PreferenceUtil.getString(Constant.TOKEN_KEY, "");
        if (!TextUtils.isEmpty(tokenCookieString)) {
            HttpCookie httpCookie = mGson.fromJson(tokenCookieString, HttpCookie.class);
            mDefaultCookieStore.add(URI.create(httpCookie.getDomain()), httpCookie);
        }
    }

    @Override
    public void add(final URI uri, final HttpCookie cookie) {
        saveCookieIfNeed(cookie);
        mDefaultCookieStore.add(uri, cookie);
    }

    private void saveCookieIfNeed(final HttpCookie cookie) {
        if (cookie.getName().equalsIgnoreCase(Constant.SESSION_KEY) || cookie.getName().equalsIgnoreCase(Constant.TOKEN_KEY) || cookie.getName().equalsIgnoreCase(Constant.EMAIL_KEY)) {
            String json = mGson.toJson(cookie);
            PreferenceUtil.putString(cookie.getName(), json);
            remove(URI.create(cookie.getDomain()), cookie);
        }
        mDefaultCookieStore.add(URI.create(cookie.getDomain()), cookie);
    }

    @Override
    public List<HttpCookie> get(final URI uri) {
        return mDefaultCookieStore.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return mDefaultCookieStore.getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return mDefaultCookieStore.getURIs();
    }

    @Override
    public boolean remove(final URI uri, final HttpCookie cookie) {
        return mDefaultCookieStore.remove(uri, cookie);
    }

    @Override
    public boolean removeAll() {
        return mDefaultCookieStore.removeAll();
    }
}
