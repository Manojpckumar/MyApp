package com.example.theerthamovers.User_Module;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.theerthamovers.Admin_module.Admin_Home;
import com.example.theerthamovers.Admin_module.Admin_Login;
import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.ForgotDialogue;
import com.example.theerthamovers.Dialogue.NoInterNetDialogue;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Network.MyReceiver;
import com.example.theerthamovers.Network.NetworkCheck;
import com.example.theerthamovers.Pojo.ExamplePasswordVisibility;
import com.example.theerthamovers.Pojo.LoginGet;
import com.example.theerthamovers.Pojo.Passwordvisibility;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;
import com.example.theerthamovers.SharedPrefrence.SharedUser;
import com.example.theerthamovers.UserInterface.EnterMobileNumber;
import com.example.theerthamovers.UserInterface.Splash_Screen;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LogIn_Page extends AppCompatActivity {

    TextInputEditText ted_phone,ted_password;
    TextInputLayout tl_email,tl_password;
    CustomButton cbn_LogIn;
    CustomTextView ctv_forgot;
    String mobile,pass;
    ProgressDialog progressBar;
    RelativeLayout rl_userlogin;

    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__page);

        fn_permission();

        rl_userlogin = findViewById(R.id.rl_userlogin);
        ted_phone = findViewById(R.id.ted_phone);
        ted_password = findViewById(R.id.ted_password);
        cbn_LogIn = findViewById(R.id.cbn_LogIn);
        ctv_forgot = findViewById(R.id.ctv_forgot);

        tl_email = findViewById(R.id.tl_email);
        tl_password = findViewById(R.id.tl_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        if (SharedPrefMgr.getInstance(LogIn_Page.this).isuserLoggedIn()) {
            finish();
            startActivity(new Intent(LogIn_Page.this, User_Home_Page.class));
            return;
        }

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "font/GeometriaLight.ttf");
        tl_password.setTypeface(tf);
        tl_email.setTypeface(tf);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api = retrofit.create(Api.class);

        api.GetForgotVisibility().enqueue(new Callback<ExamplePasswordVisibility>() {
            @Override
            public void onResponse(Call<ExamplePasswordVisibility> call, Response<ExamplePasswordVisibility> response) {

               List<Passwordvisibility>  lis = response.body().getPasswordvisibility();

               if (lis.size()==0)
               {
                   Snackbar.make(rl_userlogin,"No Value",Snackbar.LENGTH_SHORT).show();
               }
               else
               {
                   for (Passwordvisibility mod : lis)
                   {
                       Log.d("PasswordTest001122",mod.getDataStatus());
                       if (mod.getDataStatus().equals("1"))
                       {
                           ctv_forgot.setVisibility(View.VISIBLE);
                       }
                   }
               }
            }

            @Override
            public void onFailure(Call<ExamplePasswordVisibility> call, Throwable t) {

            }
        });

        ctv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LogIn_Page.this, EnterMobileNumber.class));
                finish();

            }
        });

        cbn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobile = ted_phone.getText().toString();
                pass = ted_password.getText().toString();
                progressBar = new ProgressDialog(view.getContext());
                if (Validate(mobile,pass))
                {
                    NetworkCheck check = new NetworkCheck(LogIn_Page.this);

                    if (check.isNetworkAvailable())
                    {
                        progressBar.setCancelable(false);
                        progressBar.setMessage("Please Wait ...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();

                        OperatorLogIn logIn =new OperatorLogIn(mobile,pass);
                        logIn.execute();
                    }else
                    {
                        progressBar.dismiss();
                        Snackbar.make(rl_userlogin,"Check Internet Connection",Snackbar.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Snackbar.make(rl_userlogin,"Enter Credentials",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean Validate(String mobile, String pass) {

        if (mobile.isEmpty() || !Patterns.PHONE.matcher(mobile).matches())
        {
            ted_phone.setError("Enter Valid Phone Number");
        }
        else if (pass.isEmpty())
        {
            ted_password.setError("Enter Password");
        }
        else
        {
            return true;
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        Animatoo.animateZoom(this);
    }

    public void GoToSignup(View view)
    {
        startActivity(new Intent(LogIn_Page.this, Register_Page.class));
        Animatoo.animateZoom(this);
    }


    public void GoToAdminLogIn(View view)
    {

        if (SharedPrefMgr.getInstance(LogIn_Page.this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LogIn_Page.this, Admin_Home.class));
            return;
        }
        else {
            startActivity(new Intent(LogIn_Page.this, Admin_Login.class));
            finish();
        }
    }

    class OperatorLogIn extends AsyncTask<Void, Void, String> {

        String mob,pass;

        public OperatorLogIn(String mobile, String pass) {
            this.mob=mobile;
            this.pass= pass;
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

                    Snackbar.make(rl_userlogin,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    if (obj.getString("message").equals("Invalid username or password"))
                    {
                        progressBar.dismiss();
                        Snackbar.make(rl_userlogin,obj.getString("message"),Snackbar.LENGTH_SHORT).show();
                    }

                    JSONObject userJson = obj.getJSONObject("logindet");


                    Userpref ve = new Userpref(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("mobile"),
                            userJson.getString("email")
                    );

                    SharedPrefMgr.getInstance(getApplicationContext()).adminLogin(ve);

                    Log.d("displayusername",ve.getUsername());

                    progressBar.dismiss();
                  //  SaveSharedPreference.setLoggedIns(LogIn_Page.this,true);

                    Intent intent = new Intent(LogIn_Page.this,User_Home_Page.class);
                    intent.putExtra("opname",userJson.getString("username"));
                    startActivity(intent);


                } else {
                    Snackbar.make(rl_userlogin,"Invalid call",Snackbar.LENGTH_SHORT).show();
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
            params.put("mobile",mob);
            params.put("password",pass);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=login", params);
        }
    }


    private void fn_permission() {

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 112); // your request code

    }



    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

}
