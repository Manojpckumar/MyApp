package com.example.theerthamovers.SharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.theerthamovers.SharedPrefrence.UserPrefrence.LOGGED_IN_PREF;

public class SharedUser {

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
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

}
