package info.lofei.app.tuchong.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import info.lofei.app.tuchong.BaseApplication;

/**
 * PreferenceUtil.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:51
 */
public class PreferenceUtil {

    public static String getPrefString(final String key, final String defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        settings.edit().putString(key, value).apply();
    }

    public static boolean getPrefBoolean(final String key, final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(final String key) {
        return PreferenceManager.getDefaultSharedPreferences(BaseApplication.getBaseApplication()).contains(key);
    }

    public static void setPrefBoolean(final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        settings.edit().putBoolean(key, value).apply();
    }

    public static void setPrefInt(final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        settings.edit().putInt(key, value).apply();
    }

    public static void increasePrefInt(final String key) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        increasePrefInt(settings, key);
    }

    public static void increasePrefInt(final SharedPreferences sp, final String key) {
        final int v = sp.getInt(key, 0) + 1;
        sp.edit().putInt(key, v).apply();
    }

    public static void increasePrefInt(final SharedPreferences sp, final String key,
                                       final int increment) {
        final int v = sp.getInt(key, 0) + increment;
        sp.edit().putInt(key, v).apply();
    }

    public static int getPrefInt(final String key, final int defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(final String key, final float value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        settings.edit().putFloat(key, value).apply();
    }

    public static float getPrefFloat(final String key, final float defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(final String key, final long value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        settings.edit().putLong(key, value).apply();
    }

    public static long getPrefLong(final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        return settings.getLong(key, defaultValue);
    }

    public static void increasePrefLong(final SharedPreferences sp, final String key,
                                        final long increment) {
        final long v = sp.getLong(key, 0) + increment;
        sp.edit().putLong(key, v).apply();
    }

    public static void removePreference(final String key) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BaseApplication
                .getBaseApplication());
        prefs.edit().remove(key).apply();
    }

    public static void clearPreference(final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }
}
