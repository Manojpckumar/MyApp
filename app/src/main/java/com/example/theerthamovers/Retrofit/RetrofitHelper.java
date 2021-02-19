package com.example.theerthamovers.Retrofit;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper extends Application {

    public  static  final String BASE_URL="https://sysirohub.com/theerthaearthmovers/";
    public  static  final String BASE_URL1="http://2factor.in/API/V1/";
    public  static Retrofit retrofit= null;
    public  static Retrofit retrofits= null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public  static Retrofit getClient(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().client(new OkHttpClient.Builder()
                    .build()).addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return  retrofit;
    }

    public  static Retrofit getClients(){

        if(retrofits==null){
            retrofits=new Retrofit.Builder().client(new OkHttpClient.Builder()
                    .build()).addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL1).build();
        }
        return  retrofits;
    }
}
