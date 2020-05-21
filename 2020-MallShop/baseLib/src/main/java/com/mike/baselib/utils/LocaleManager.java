package com.mike.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

public class LocaleManager {

    public static final  String LANGUAGE_ENGLISH   = "en";
    public static final  String LANGUAGE_CHINESE= "zh_rCN";
    public static final  String LANGUAGE_TRADITIONAL = "zh_rHK";
    private static final String LANGUAGE_KEY       = "language_key";

    private final SharedPreferences prefs;

    public LocaleManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context setLocale(Context c) {
        return updateResources(c, getLanguage());
    }

    public Context setNewLocale(Context c, String language) {
        persistLanguage(language);
        return updateResources(c, language);
    }

    public String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_CHINESE);
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish

        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if(LANGUAGE_ENGLISH.equals(language)){
           config.locale = Locale.ENGLISH;
        }else if(LANGUAGE_CHINESE.equals(language)){
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }else if(LANGUAGE_TRADITIONAL.equals(language)){
            config.locale = Locale.TRADITIONAL_CHINESE;
        }else {
            config.locale = Locale.getDefault();
        }

//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
        if (LocaleManager.isAtLeastVersion(JELLY_BEAN_MR1)) {
            //config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
        //    config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return LocaleManager.isAtLeastVersion(N) ? config.getLocales().get(0) : config.locale;
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
