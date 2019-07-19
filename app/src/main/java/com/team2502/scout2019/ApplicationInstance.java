package com.team2502.scout2019;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public class ApplicationInstance extends Application {
    public static ApplicationInstance INSTANCE;

    public void onCreate() {
        super.onCreate();
        INSTANCE = ApplicationInstance.this;
        //Log.e("Serial", Build.SERIAL);
        setSp("scoutSerialNumber", Build.SERIAL);
        setSp("lastMatch", 0);
        setSp("isReplay", 0);
        setSp("isOverridden", 0);
        ImportUtils.setSerialNumberHashMapConstant();
        //Log.e("HashMap", Constants.SERIAL_TO_SCOUT.get("lastMatch"));
    }

    //Gets any value saved into shared preferences.
    public final static SharedPreferences getSp() {
        Log.e("INSTANCE REF SharePref", INSTANCE.toString());
        return INSTANCE.getSharedPreferences(Constants.SHARED_PREF_KEY, Activity.MODE_PRIVATE);
    }

    //Gets the shared preference for string values
    public static String getSp(String key, String defaultValue) {
        return ApplicationInstance.getSp().getString(key, defaultValue);
    }

    //Gets the shared preference for integer values
    public static int getSp(String key, int defaultValue) {
        return ApplicationInstance.getSp().getInt(key, defaultValue);
    }

    //Sets shared preferences for string values
    public static void setSp(String key, String value) {
        ApplicationInstance.getSp().edit().putString(key, value).apply();
    }

    //Sets shared preferences for integer values
    public static void setSp(String key, int value) {
        ApplicationInstance.getSp().edit().putInt(key, value).apply();
    }
}