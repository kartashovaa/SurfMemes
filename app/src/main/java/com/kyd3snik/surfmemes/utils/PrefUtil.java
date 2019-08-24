package com.kyd3snik.surfmemes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtil {
    private static final String DEFAULT_STRING_VALUE = "";
    private static SharedPreferences sharedPreferences;
    private static PrefUtil instance;

    private PrefUtil(Context context) {
        context = context.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static PrefUtil with(Context context) {
        if (instance == null)
            instance = new PrefUtil(context);
        return instance;
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, DEFAULT_STRING_VALUE);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public static class Keys {
        public static String ACCESS_TOKEN = "accessToken";
        public static String USERNAME = "username";
        public static String FIRSTNAME = "firstName";
        public static String LASTNAME = "lastName";
        public static String USER_DESCRIPTION = "userDescription";


    }
}
