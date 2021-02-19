package com.example.theerthamovers.Admin_module;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.theerthamovers.Interface.HudProgress;
import com.example.theerthamovers.Network.NetworkCheck;
import com.example.theerthamovers.Pojo.AdminLoginPoJo;
import com.example.theerthamovers.Pojo.Adminpref;
import com.example.theerthamovers.Pojo.RegisterDriver;
import com.example.theerthamovers.Pojo.Vehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;
import com.example.theerthamovers.User_Module.LogIn_Page;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class Admin_Login extends AppCompatActivity {

    TextInputEditText ted_phone,ted_password;
    KProgressHUD hud;
    String phone,password;
    ProgressDialog progressBar;
    RelativeLayout rl_adminlogin;
    interface IAdminLogIn
    {
        void adminLoggedIn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        ted_phone=findViewById(R.id.ted_phone);
        ted_password=findViewById(R.id.ted_password);
        rl_adminlogin=findViewById(R.id.rl_adminlogin);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void GoToHomeLogIn(View view)
    {
        phone = ted_phone.getText().toString();
        password = ted_password.getText().toString();

        if (Validate(phone,password))
        {
            NetworkCheck check = new NetworkCheck(Admin_Login.this);
            progressBar = new ProgressDialog(view.getContext());

            if (check.isNetworkAvailable())
            {
                progressBar.setCancelable(false);
                progressBar.setMessage("Please Wait ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                LogInAdmin admin = new LogInAdmin();
                admin.execute();
            }else
            {
                progressBar.dismiss();
                Snackbar.make(rl_adminlogin,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
            }
        }
        else
        {
            Snackbar.make(rl_adminlogin,"Enter Valid Credentials",Snackbar.LENGTH_SHORT).show();

        }

    }

    private boolean Validate(String phone, String password) {

        if (!Patterns.PHONE.matcher(phone).matches()&& phone.isEmpty())
        {
            ted_phone.setError("Enter valid phone number");
        }
        else if(password.isEmpty())
        {
            ted_password.setError("Enter valid password");
        }
        else
        {
            return true;
        }
        return false;
    }


    class LogInAdmin extends AsyncTask<Void, Void, String> {

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

                    Snackbar.make(rl_adminlogin,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    if (obj.getString("message").equals("Invalid username or password"))
                    {
                        progressBar.dismiss();
                        Snackbar.make(rl_adminlogin,obj.getString("message"),Snackbar.LENGTH_SHORT).show();
                    }

                    JSONObject userJson = obj.getJSONObject("logindet");

                    //creating a new user object
                    AdminLoginPoJo ve = new AdminLoginPoJo(
                            userJson.getString("username"),
                            userJson.getString("mobile"),
                            userJson.getString("email")
                    );

                    Adminpref adminall = new Adminpref(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getString("mobile")
                    );

                    SharedPrefMgr.getInstance(getApplicationContext()).userLogin(adminall);

                    progressBar.dismiss();
                   // SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                    Intent intent = new Intent(Admin_Login.this,Admin_Home.class);
                    intent.putExtra("username",ve.getUsername());
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("mobile","+91"+phone);
            params.put("password",password);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=adminlogin", params);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Admin_Login.this, LogIn_Page.class));
        finish();
    }
}
