package com.example.theerthamovers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;

public class NoInternet extends AppCompatActivity {

    CustomButton ceb_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ceb_refresh= findViewById(R.id.ceb_refresh);

//        ceb_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (ConnectivityReceiver.isConnected())
//                {
//                    onBackPressed();
//                }else
//                {
//                    Toast.makeText(NoInternet.this, "Check Your Connection", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


    }
}
