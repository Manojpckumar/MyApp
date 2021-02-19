package com.example.theerthamovers.Network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.theerthamovers.NoInternet;

public class NetworkUtil {

    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            Intent intent = new Intent(context, NoInternet.class);
            context.startActivity(intent);
            return status;
        }
        return status;
    }
}
