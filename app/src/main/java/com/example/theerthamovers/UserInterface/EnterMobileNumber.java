package com.example.theerthamovers.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Pojo.ExampleGetApi;
import com.example.theerthamovers.Pojo.ExampleOTP;
import com.example.theerthamovers.Pojo.Getapikey;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class EnterMobileNumber extends AppCompatActivity {

    CustomEditText ced_mobilenum;
    CustomButton cbn_contiunue;
    LinearLayout ll_enterphone;
    String apiis,BASE_1,idd;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        progressBar = new ProgressDialog(EnterMobileNumber.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        cbn_contiunue=findViewById(R.id.cbn_continue);
        ced_mobilenum=findViewById(R.id.ced_mobilenum);
        ll_enterphone=findViewById(R.id.ll_enterphone);

//        Retrofit retrofit = RetrofitHelper.getClient();
//        final Api api = retrofit.create(Api.class);
//
//        api.GetApi().enqueue(new Callback<ExampleGetApi>() {
//            @Override
//            public void onResponse(Call<ExampleGetApi> call, Response<ExampleGetApi> response) {
//
//                List<Getapikey>  list = response.body().getGetapikey();
//                Getapikey a = list.get(0);
//                apiis = a.getGetapikey();
//                Log.d("9562719658",apiis);
//            }
//
//            @Override
//            public void onFailure(Call<ExampleGetApi> call, Throwable t) {
//
//            }
//        });

        cbn_contiunue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num = ced_mobilenum.getText().toString();
                if (Patterns.PHONE.matcher(num).matches() && !num.isEmpty())
                {
                    Retrofit retrofit = RetrofitHelper.getClient();
                    final Api api = retrofit.create(Api.class);

                    progressBar.setCancelable(false);
                    progressBar.setMessage("Verifying Your Mobile Number");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    api.GetApi().enqueue(new Callback<ExampleGetApi>() {
                        @Override
                        public void onResponse(Call<ExampleGetApi> call, Response<ExampleGetApi> response) {

                            String numb = ced_mobilenum.getText().toString();

                            List<Getapikey>  list = response.body().getGetapikey();
                            Getapikey a = list.get(0);
                            apiis = a.getGetapikey();
                            Log.d("9562719658",apiis);

                            GetandValidateNumber number = new GetandValidateNumber(numb,apiis);
                            number.execute();
                        }

                        @Override
                        public void onFailure(Call<ExampleGetApi> call, Throwable t) {

                        }
                    });
                }

            }
        });

    }

    class GetandValidateNumber extends AsyncTask<Void, Void, String> {

        String phone,apiz;

        public GetandValidateNumber(String numb) {
            this.phone =numb;
        }

        public GetandValidateNumber(String numb, String apiis) {
            this.phone=numb;
            this.apiz=apiis;
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

                    JSONObject object = obj.getJSONObject("logincredentials");

                    progressBar.dismiss();
                    Snackbar.make(ll_enterphone,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    idd = object.getString("id");

                   // Toast.makeText(EnterMobileNumber.this, idd, Toast.LENGTH_SHORT).show();

                       Retrofit retrofit = RetrofitHelper.getClients();
                       Api api = retrofit.create(Api.class);

                       api.SendOTP(apiz,phone).enqueue(new Callback<ExampleOTP>() {
                           @Override
                           public void onResponse(Call<ExampleOTP> call, Response<ExampleOTP> response) {

                               if (!response.body().getStatus().equals("Error"))
                               {
                                   SuccessDialogue dialogue = new SuccessDialogue(EnterMobileNumber.this,"OTP Successfully send to your registered mobile number");
                                   FragmentManager fm = getSupportFragmentManager();
                                   dialogue.show(fm,"");

                                   Intent intent = new Intent(EnterMobileNumber.this,VerifyOtpScreen.class);
                                   intent.putExtra("sessionid",response.body().getDetails());
                                   intent.putExtra("ids",idd);
                                   intent.putExtra("apikey",apiis);
                                   startActivity(intent);
                               }

                           }

                           @Override
                           public void onFailure(Call<ExampleOTP> call, Throwable t) {

                           }
                       });





                } else {

                    Snackbar.make(ll_enterphone,"Inavlid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("mobile",phone);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=checknumberexist", params);
        }
    }
}
