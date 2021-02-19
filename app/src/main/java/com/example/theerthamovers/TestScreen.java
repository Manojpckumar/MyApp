package com.example.theerthamovers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.User_Module.LogIn_Page;
import com.example.theerthamovers.User_Module.User_Phase_1;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class TestScreen extends AppCompatActivity {

    String reason;
    LinearLayout ll_datapost;
    CustomTextView ctv_reason,ctv_pnf;
    ImageView iv_pnf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ll_datapost=findViewById(R.id.ll_datapost);
        ctv_reason=findViewById(R.id.ctv_reason);
        iv_pnf=findViewById(R.id.iv_pnf);
        ctv_pnf=findViewById(R.id.ctv_pnf);

        String str =   getIntent().getExtras().getString("erroris");

        ctv_reason.setText(str);


    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        moveTaskToBack(true);
    }
}
