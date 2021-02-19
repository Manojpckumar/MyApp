package com.example.theerthamovers.UserInterface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.theerthamovers.Admin_module.Admin_Home;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.example.theerthamovers.SharedPrefrence.SharedUser;
import com.example.theerthamovers.TestScreen;
import com.example.theerthamovers.User_Module.LogIn_Page;
import com.example.theerthamovers.User_Module.User_Home_Page;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Splash_Screen extends AppCompatActivity {

    TextView texlogo;
    RelativeLayout rl_back;
   // ProgressDialog pb_splash;
    ProgressBar pb_splash;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        texlogo=findViewById(R.id.texlogo);
        pb_splash=findViewById(R.id.pb_splash);
       // pb_splash = new ProgressDialog(Splash_Screen.this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "font/GeometriaLight.ttf");
        texlogo.setTypeface(tf,Typeface.BOLD);

        View view = findViewById(R.id.rl_back);

        pb_splash.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pb_splash.setMin(10);
        }
        pb_splash.setMax(100);
        pb_splash.setProgress(10,true);


//        pb_splash.setCancelable(false);
//        pb_splash.setMessage("Please Wait ...");
//        pb_splash.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pb_splash.setProgress(0);
//        pb_splash.setMax(100);
//        pb_splash.show();

       view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
           @Override
           public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
               int cx = view.getWidth() / 2;
               int cy = view.getHeight() / 2;

               float finalRadius = (float) Math.hypot(cx, cy);

               Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
               view.setVisibility(View.VISIBLE);
               anim.start();
               view.removeOnLayoutChangeListener(this);
           }
       });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                PostData data = new PostData();
                data.execute();

            }
        }, 3000);

    }

    class PostData extends AsyncTask<Void, Void, String> {

        public PostData() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                   // Snackbar.make(rl_back,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    if (obj.getString("message").equals("STATUS") && !obj.getString("data_status").isEmpty())
                    {

                        pb_splash.setVisibility(View.GONE);
                        Intent intent = new Intent(Splash_Screen.this, TestScreen.class);
                        intent.putExtra("erroris",obj.getString("data_status"));
                        startActivity(intent);

                    }
                    else
                    {
                        pb_splash.setVisibility(View.GONE);
                        startActivity(new Intent(Splash_Screen.this, LogIn_Page.class));
                        finish();
                    }

                } else {
                    Snackbar.make(rl_back,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("data_post", "1");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=configallpage", params);
        }
    }

}
