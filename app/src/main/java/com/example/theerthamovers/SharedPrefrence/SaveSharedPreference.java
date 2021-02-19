package com.example.theerthamovers.SharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.theerthamovers.SharedPrefrence.PreferencesUtility.LOGGED_IN_PREF;
import static com.example.theerthamovers.SharedPrefrence.PreferencesUtility.LOGGED_UNAME;

public class SaveSharedPreference {

    public SaveSharedPreference() {
    }

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static void setLoggedIns(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_UNAME, loggedIn);
        editor.apply();
    }

//    public static boolean saveName(String name, Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor prefsEditor = prefs.edit();
//        prefsEditor.putString(LOGGED_UNAME, name);
//        prefsEditor.apply();
//        return true;
//    }
//
//    public static String getEmail(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(LOGGED_UNAME, "");
//    }


    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, true);
    }

    public static boolean getLoggedStatuss(Context context) {
        return getPreferences(context).getBoolean(LOGGED_UNAME, true);
    }

}
