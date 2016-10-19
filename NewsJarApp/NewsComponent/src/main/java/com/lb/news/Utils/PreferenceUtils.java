package com.lb.news.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    public static final String KEY_TOKEN = "token_";
    public static final String KEY_DEVICEID = "deviceId";
    public static String readString(Context context,String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static void writeString(Context context,String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static boolean readBoolean(Context context,String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public static void writeBoolean(Context context,String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static int readInt(Context context,String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static int readInt(Context context,String key, int value) {
        return getSharedPreferences(context).getInt(key, value);
    }

    public static void writeInt(Context context,String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static long readLong(Context context,String key) {
        return getSharedPreferences(context).getLong(key, 0);
    }

    public static void writeLong(Context context,String key, long value) {
        getSharedPreferences(context).edit().putLong(key, value).apply();
    }

    public static void remove(Context context,String key) {
        getSharedPreferences(context).edit().remove(key).apply();
    }

    public static void removeAll(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("news_plugin", Context.MODE_PRIVATE);
    }
}
