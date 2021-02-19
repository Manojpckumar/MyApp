package com.example.theerthamovers.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.theerthamovers.NoInternet;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        if(status.isEmpty()) {
            status="No Internet Connection";
//            Intent i = new Intent(context, NoInternet.class);
//            context.startActivity(i);
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

}
