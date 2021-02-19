package com.example.theerthamovers.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.User_Module.LogIn_Page;
import com.example.theerthamovers.User_Module.User_Phase_1;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdatePasswordScreen extends AppCompatActivity {

    CustomEditText ced_uppass,ced_upconpass;
    CustomButton btn_uppass;
    LinearLayout ll_up;
    String idd;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        dialog = new ProgressDialog(UpdatePasswordScreen.this);

        idd = getIntent().getExtras().getString("id");

        ced_uppass = findViewById(R.id.ced_uppass);
        ced_upconpass = findViewById(R.id.ced_cpass);
        btn_uppass = findViewById(R.id.btn_uppass);
        ll_up = findViewById(R.id.ll_up);


        btn_uppass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass,conpass;
                pass=ced_uppass.getText().toString();
                conpass=ced_upconpass.getText().toString();

                if (ValidatePassword(pass,conpass))
                {
                    UpdatePassword password = new UpdatePassword(idd,pass);
                    password.execute();
                }

            }
        });

    }

    private boolean ValidatePassword(String pass, String conpass) {

        if (!(pass.length()==5) && pass.isEmpty())
        {
            ced_uppass.setError("Enter Password with minimum length of five");
        }
        else if (!(conpass.length()==5) && pass.isEmpty())
        {
            ced_upconpass.setError("Enter Password with minimum length of five");
        }
        else if (!(pass.equals(conpass)))
        {
            Snackbar.make(ll_up,"Password dont match enter same password",Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }

        return false;
    }



    class UpdatePassword extends AsyncTask<Void, Void, String> {

        String id,passw;

        public UpdatePassword(String idd,String pass) {
            this.id=idd;
            this.passw=pass;
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

                    Snackbar.make(ll_up,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    if (obj.getString("message").equals("operator details updated successfully"))
                    {
                        dialog.setCancelable(false);
                        dialog.setMessage("Waiting your password is updating");
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setProgress(0);
                        dialog.setMax(100);
                        dialog.show();

                        UpdatePasswordStatus status = new UpdatePasswordStatus();
                        status.execute();

                    }




                } else {
                    Snackbar.make(ll_up,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("id",id);
            params.put("password",passw);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updateoperatorpwd", params);
        }
    }

    class UpdatePasswordStatus extends AsyncTask<Void, Void, String> {

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

                    dialog.dismiss();
                    Snackbar.make(ll_up,obj.getString("message"),Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdatePasswordScreen.this, LogIn_Page.class));
                    finish();

                } else {
                    Snackbar.make(ll_up,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("id","1");
            params.put("data_status","0");
            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatepasswordvisibility", params);
        }
    }


}
