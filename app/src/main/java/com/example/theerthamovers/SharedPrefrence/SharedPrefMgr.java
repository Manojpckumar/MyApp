package com.example.theerthamovers.SharedPrefrence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.theerthamovers.Admin_module.Admin_Login;
import com.example.theerthamovers.Pojo.Adminpref;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.User_Module.LogIn_Page;

public class SharedPrefMgr {
    //the constants
    private static final String SHARED_PREF_NAME = "adminp";
    private static final String SHARED_PREF_NAMES = "userp";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_USERUSERNAME = "keyusername";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_USERPHONE = "keyphone";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_USEREMAIL = "keyemail";
    private static final String KEY_ID = "keyid";
    private static final String KEY_USERID = "keyid";

    private static SharedPrefMgr mInstance;
    private static Context mCtx;

    private SharedPrefMgr(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefMgr getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefMgr(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Adminpref adminall) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, adminall.getId());
        editor.putString(KEY_USERNAME, adminall.getUsername());
        editor.putString(KEY_EMAIL, adminall.getEmail());
        editor.putString(KEY_PHONE, adminall.getPhone());
        editor.apply();
    }

    public void adminLogin(Userpref upref) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USERID, upref.getId());
        editor.putString(KEY_USERUSERNAME, upref.getUsername());
        editor.putString(KEY_USEREMAIL, upref.getEmail());
        editor.putString(KEY_USERPHONE, upref.getPhone());
        editor.apply();
    }


    public Userpref uprefs() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMES, Context.MODE_PRIVATE);
        return new Userpref(
                sharedPreferences.getInt(KEY_USERID, -1),
                sharedPreferences.getString(KEY_USERUSERNAME, null),
                sharedPreferences.getString(KEY_USEREMAIL, null),
                sharedPreferences.getString(KEY_USERPHONE, null)
        );
    }

    public Adminpref adpref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Adminpref(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null)
        );
    }



    //this method will checker whether admin is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }


    //this method will checker whether user is already logged in or not
    public boolean isuserLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERUSERNAME, null) != null;
    }



    //this method will logout the admin
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Admin_Login.class));
    }

    //this method will logout the admin
    public void userlogout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LogIn_Page.class));
    }

}
