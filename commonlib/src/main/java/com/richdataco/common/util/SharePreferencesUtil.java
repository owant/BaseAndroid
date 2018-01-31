package com.richdataco.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by owant on 2016/9/1.
 */
public class SharePreferencesUtil {

    private static final String BASE_PREFERENCE_NAME = "base_pre";
    private SharedPreferences mSharedPrefs;

    private SharePreferencesUtil() {
    }

    private static class InstanceHolder {
        public static SharePreferencesUtil sInstance = new SharePreferencesUtil();
    }

    public static SharePreferencesUtil getInstance() {
        return InstanceHolder.sInstance;
    }

    public void init(Context context) {
        mSharedPrefs = context.getSharedPreferences(BASE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        if (mSharedPrefs == null) return null;
        return mSharedPrefs.getString(key, "");
    }

    public void putString(String key, String value) {
        if (mSharedPrefs == null) return;
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(key, value);
        if (Build.VERSION.SDK_INT >= 15)
            editor.apply();
        else
            editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.remove(key);

        if (Build.VERSION.SDK_INT >=15) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
