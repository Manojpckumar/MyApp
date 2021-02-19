package com.example.theerthamovers.Retrofit;

import com.example.theerthamovers.Pojo.AddNewVehicle;
import com.example.theerthamovers.Pojo.Example;
import com.example.theerthamovers.Pojo.ExampleAddop;
import com.example.theerthamovers.Pojo.ExampleAllOperator;
import com.example.theerthamovers.Pojo.ExampleCompletedNew;
import com.example.theerthamovers.Pojo.ExampleGetApi;
import com.example.theerthamovers.Pojo.ExampleMobile;
import com.example.theerthamovers.Pojo.ExampleMonthlyTask;
import com.example.theerthamovers.Pojo.ExampleOTP;
import com.example.theerthamovers.Pojo.ExampleOtpVerify;
import com.example.theerthamovers.Pojo.ExamplePasswordVisibility;
import com.example.theerthamovers.Pojo.ExamplePending;
import com.example.theerthamovers.Pojo.ExamplePendingNew;
import com.example.theerthamovers.Pojo.ExampleStarted;
import com.example.theerthamovers.Pojo.ExampleStartedNew;
import com.example.theerthamovers.Pojo.ExampleSumByMonth;
import com.example.theerthamovers.Pojo.ExampleVehicleNumber;
import com.example.theerthamovers.Pojo.ExampleVehicles;
import com.example.theerthamovers.Pojo.GetAllOperator;
import com.example.theerthamovers.Pojo.GetAllVehicle;
import com.example.theerthamovers.Pojo.GetAssignedTaskPoJo;
import com.example.theerthamovers.Pojo.GetVehicleNumber;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

//    @FormUrlEncoded
//    @POST("Api.php?apicall=addoperators")
//    Call<RegisterDriver> registerdriver(@Body RegisterDriver driver);
//

    @FormUrlEncoded
    @POST("Api.php?apicall=addoperators")
    Call<ExampleAddop> registerdriver(@Field("username") String uname, @Field("mobile") String mobile, @Field("email") String mail, @Field("password") String pass);

    @FormUrlEncoded
    @POST("Api.php?apicall=login")
    Call<JsonObject> operatorlogin(@Field("mobile") String mob, @Field("password") String pass);

    @FormUrlEncoded
    @POST("Api.php?apicall=adminlogin")
    Call<JsonObject> AdminLogin(@Field("mobile") String phone,@Field("password") String password);

    @FormUrlEncoded
    @POST("Api.php?apicall=addvehicles")
    Call<AddNewVehicle> Addvehicle(@Field("vehicle_name") String vname, @Field("vehicle_number") String vnum,
                                         @Field("model_no") String vmodel, @Field("category") String vcate, @Field("point_time") String point);

    @GET("phase1/v1/Api.php?apicall=fetchalloperators")
    Call<ExampleAllOperator> GetAllOperators();

    @GET("phase1/v1/Api.php?apicall=getallvehicles")
    Call<ExampleVehicles> GetAllVehicles();

    @GET("phase1/v1/Api.php?apicall=getapikey")
    Call<ExampleGetApi> GetApi();

    @GET("phase1/v1/Api.php?apicall=getadminmobile")
    Call<ExampleMobile> GetAdminMobile();

    @GET("{api_key}/SMS/{phone_number}/AUTOGEN")
    Call<ExampleOTP> SendOTP(@Path("api_key")String a,
                             @Path("phone_number")String b);


    @GET("{api_key}/SMS/VERIFY/{session_id}/{otp_input}")
    Call<ExampleOtpVerify> VerifyOTP(@Path("api_key")String a, @Path("session_id")String b , @Path("otp_input") String c);

    @FormUrlEncoded
    @POST("Api.php?apicall=getTasksbydatecompleted")
    Call<ExampleCompletedNew> GetAllCompletedd(@Field("work_date") String date);

    @GET("phase1/v1/Api.php?apicall=getstatusInfocompleted")
    Call<Example> GetAllCompleted();

    @GET("phase1/v1/Api.php?apicall=getstatusInfopending")
    Call<ExamplePending> GetAllPending();

    @GET("phase1/v1/Api.php?apicall=getpasswordvisibility")
    Call<ExamplePasswordVisibility> GetForgotVisibility();

    @FormUrlEncoded
    @POST("Api.php?apicall=getTasksbydatestarted")
    Call<ExampleStartedNew> GetAllStarted(@Field("work_date") String date);

    @FormUrlEncoded
    @POST("Api.php?apicall=getTasksbydatepending")
    Call<ExamplePendingNew> GetAllPending(@Field("work_date") String date);

    @FormUrlEncoded
    @POST("Api.php?apicall=getAllcompletedtaskbydate")
    Call<ExampleMonthlyTask> GetCompletedMonthly(@Field("fromdate") String from,
                                                 @Field("todate") String to);

    @FormUrlEncoded
    @POST("Api.php?apicall=getincomebydate")
    Call<ExampleSumByMonth> GetCompletedSumMonthly(@Field("fromdate") String from,
                                                   @Field("todate") String to);

    @GET("phase1/v1/Api.php?apicall=fetchvehiclenumber")
    Call<ExampleVehicleNumber> GetAllVehicleDetails();

    @GET("phase1/v1/Api.php?apicall=deleteoperator")
    Call<JSONObject> DeleteOperator(@Query("id") int id);


    @GET("phase1/v1/Api.php?apicall=deleteassignedtasks")
    Call<JSONObject> DeletePending(@Query("id") int id);

    @GET("phase1/v1/Api.php?apicall=deletevehicle")
    Call<JSONObject> DeleteVehicle(@Query("id") int id);


    @FormUrlEncoded
    @POST("Api.php?apicall=addtotask")
    Call<JsonObject> AssignNewTask(@Field("vehicle_number") String vname,
                                   @Field("vehicle_name") String vnum,
                                   @Field("model_number") String vmodel,
                                   @Field("category_type") String vcate,
                                   @Field("driver_name") String dname,
                                   @Field("work_location") String work_loc,
                                   @Field("work_date") String work_date,
                                   @Field("started_response") String st_response);


    @FormUrlEncoded
    @POST("Api.php?apicall=getTasksassigned")
    Call<List<GetAssignedTaskPoJo>> GetOperatorsTask(@Field("driver_name")String opName,
                                                        @Field("work_date") String date);


}
