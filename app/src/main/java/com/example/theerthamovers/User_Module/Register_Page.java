package com.example.theerthamovers.User_Module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.theerthamovers.Adapter.GetAllPendingWork;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Pojo.ExampleAddop;
import com.example.theerthamovers.Pojo.RegisterDriver;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register_Page extends AppCompatActivity {

    TextInputLayout tl_name,tl_email,tl_phone,tl_pass;
    Button btn_register;
    TextInputEditText ted_uname,ted_uemail,ted_upass,ted_umobile;
    RelativeLayout rl_usersignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);

        tl_name=findViewById(R.id.tl_name);
        tl_email=findViewById(R.id.tl_email);
        tl_phone=findViewById(R.id.tl_phone);
        tl_pass=findViewById(R.id.tl_pass);
        btn_register=findViewById(R.id.btn_register);
        rl_usersignup=findViewById(R.id.rl_usersignup);

        ted_uname=findViewById(R.id.ted_uname);
        ted_uemail=findViewById(R.id.ted_uemail);
        ted_umobile=findViewById(R.id.ted_umobile);
        ted_upass=findViewById(R.id.ted_upass);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "font/GeometriaLight.ttf");
        tl_name.setTypeface(tf);
        tl_email.setTypeface(tf);
        tl_phone.setTypeface(tf);
        tl_pass.setTypeface(tf);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register_Page.this, LogIn_Page.class));
        Animatoo.animateZoom(this);
    }

    public void RegisterUser(View view)
    {
        String uname,uemail,umobile,upass;

        uname=ted_uname.getText().toString();
        uemail=ted_uemail.getText().toString();
        umobile=ted_umobile.getText().toString();
        upass=ted_upass.getText().toString();

        if (uemail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(uemail).matches())
        {
            ted_uemail.setError("Enter Valid Email id");
        }
        else if (umobile.isEmpty() && !Patterns.PHONE.matcher(umobile).matches() )
        {
            ted_umobile.setError("Enter Valid Phone Number");
        }
        else if (uname.isEmpty())
        {
            ted_uname.setError("Enter User Name");
        }
        else if (upass.isEmpty())
        {
            ted_upass.setError("Enter Password");
        }
        else
        {
            Retrofit retrofit = RetrofitHelper.getClient();
            Api api =retrofit.create(Api.class);

            api.registerdriver(uname,umobile,uemail,upass).enqueue(new Callback<ExampleAddop>() {
                @Override
                public void onResponse(Call<ExampleAddop> call, Response<ExampleAddop> response) {

                    if (response.body().getMessage().equals("New operator  added successfully"))
                    {
                        UpdateUI();
                        FragmentManager manager = getSupportFragmentManager();
                        SuccessDialogue dialogue = new SuccessDialogue(Register_Page.this, "Operator Registered Successfully");
                        dialogue.show(manager, "");
                        startActivity(new Intent(Register_Page.this, LogIn_Page.class));
                        finish();
                    }
                    else
                    {
                        Snackbar.make(rl_usersignup,"Already exist",Snackbar.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ExampleAddop> call, Throwable t) {

                }
            });

        }
        }

    private void UpdateUI() {
        ted_uname.setText("");
        ted_uemail.setText("");
        ted_umobile.setText("");
        ted_upass.setText("");
    }

    public void GoToLogin(View view)
    {
        startActivity(new Intent(Register_Page.this,LogIn_Page.class));
        Animatoo.animateZoom(this);
    }
}
