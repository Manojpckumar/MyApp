package com.example.theerthamovers.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.Pojo.ExampleOtpVerify;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerifyOtpScreen extends AppCompatActivity {

    String sessionid,ids,apikey;
    CustomButton cbn_verify;
    CustomEditText ced_1,ced_2,ced_3,ced_4,ced_5,ced_6;
    String finalotp;
    String otp1,otp2,otp3,otp4,otp5,otp6;
    LinearLayout ll_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        sessionid =  getIntent().getExtras().getString("sessionid");
        ids = getIntent().getExtras().getString("ids");
        apikey = getIntent().getExtras().getString("apikey");

        ced_1=findViewById(R.id.ced_1);
        ced_2=findViewById(R.id.ced_2);
        ced_3=findViewById(R.id.ced_3);
        ced_4=findViewById(R.id.ced_4);
        ced_5=findViewById(R.id.ced_5);
        ced_6=findViewById(R.id.ced_6);

        cbn_verify=findViewById(R.id.cbn_verify);
        ll_verify=findViewById(R.id.ll_verify);


        ced_1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 // TODO Auto-generated method stub

                if(ced_1.length()==1)
                {
                   // sb.append(s);
                    ced_1.clearFocus();
                    ced_2.requestFocus();
                    ced_2.setCursorVisible(true);
                }

                if(ced_1.length()==0)
                {
                    ced_1.requestFocus();
                    ced_1.setCursorVisible(true);

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        ced_2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(ced_2.length()==1)
                {
                    ced_2.clearFocus();
                    ced_3.requestFocus();
                    ced_3.setCursorVisible(true);
                }

                if(ced_2.length()==0)
                {
                    ced_2.clearFocus();
                    ced_1.requestFocus();
                    ced_1.setCursorVisible(true);

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        ced_3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(ced_3.length()==1)
                {
                    ced_3.clearFocus();
                    ced_4.requestFocus();
                    ced_4.setCursorVisible(true);

                }

                if(ced_3.length()==0)
                {
                    ced_3.clearFocus();
                    ced_2.requestFocus();
                    ced_2.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
            }
        });

        ced_4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(ced_4.length()==1)
                {
                    ced_4.clearFocus();
                    ced_5.requestFocus();
                    ced_5.setCursorVisible(true);

                }

                if(ced_4.length()==0)
                {
                    ced_4.clearFocus();
                    ced_3.requestFocus();
                    ced_3.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        ced_5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(ced_5.length()==1)
                {
                    ced_5.clearFocus();
                    ced_6.requestFocus();
                    ced_6.setCursorVisible(true);

                }

                if(ced_5.length()==0)
                {
                    ced_5.clearFocus();
                    ced_4.requestFocus();
                    ced_4.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        ced_6.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

//                if(ced_6.length()==1)
//                {
//                    ced_6.clearFocus();
//                   // cbn_verify.requestFocus();
//
//                }

                if(ced_6.length()==0)
                {
                    ced_6.clearFocus();
                    ced_5.requestFocus();
                    ced_5.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


        cbn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                otp1 =  ced_1.getText().toString();
                otp2 =  ced_2.getText().toString();
                otp3 =  ced_3.getText().toString();
                otp4 =  ced_4.getText().toString();
                otp5 =  ced_5.getText().toString();
                otp6 =  ced_6.getText().toString();

                String otpfinal = otp1+otp2+otp3+otp4+otp5+otp6;

                if (! otpfinal.isEmpty() || otpfinal.length()==6)
                {

                    Retrofit retrofit = RetrofitHelper.getClients();
                    Api api = retrofit.create(Api.class);

                    api.VerifyOTP(apikey,sessionid,otpfinal).enqueue(new Callback<ExampleOtpVerify>() {
                        @Override
                        public void onResponse(Call<ExampleOtpVerify> call, Response<ExampleOtpVerify> response) {

                            if (response.body().getStatus().equals("Success"))
                            {

                                Snackbar.make(ll_verify,"OTP Matched Successfully",Snackbar.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyOtpScreen.this,UpdatePasswordScreen.class);
                                intent.putExtra("id",ids);
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void onFailure(Call<ExampleOtpVerify> call, Throwable t) {

                        }
                    });

                }

            }
        });

    }

    private boolean Validate(String otp1, String otp2, String otp3, String otp4, String otp5, String otp6) {

        if (otp1.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
       else if (otp2.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
       else if (otp3.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
      else   if (otp4.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
       else if (otp5.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
      else   if (otp6.isEmpty())
        {
            Snackbar.make(ll_verify,"Enter OTP",Snackbar.LENGTH_SHORT).show();
        }
      else
        {
            return true;
        }

      return false;
    }
}
