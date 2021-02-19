package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.Adminmobile;
import com.example.theerthamovers.Pojo.ExampleMobile;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link User_Phase_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Phase_2 extends Fragment {

//    LinearLayout ll_diesel,ll_food,ll_grease,ll_oil_cat,ll_oiled,ll_spare;
//    CheckBox cb_diesel,cb_food,cb_grease,cb_oil,cb_oilhy,cb_oilno,cb_spare,checkBox;
    CustomEditText ced_opname,ced_vcate,ced_vnum,ced_vmodel,ced_loc,ced_streading,ced_sttime,ced_endtime,ced_endreading;
    String formattedDate;
    ProgressDialog progressBar;
    LinearLayout ll_userph2;
    String phaseid,id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public User_Phase_2() {
        // Required empty public constructor
    }

    public User_Phase_2(String phaseid, String id) {

        this.phaseid = phaseid;
        this.id = id;

    }

    interface IUserPhase2
    {
        void AddedToPhase2();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Phase_2.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Phase_2 newInstance(String param1,String param2) {

        User_Phase_2 fragment = new User_Phase_2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user__phase_2, container, false);

//        cb_diesel = view.findViewById(R.id.cb_diesel);
//        cb_food = view.findViewById(R.id.cb_food);
//        cb_grease = view.findViewById(R.id.cb_grease);
//        cb_oil = view.findViewById(R.id.cb_oil);
//        cb_oilhy = view.findViewById(R.id.cb_oilhy);
//        cb_oilno = view.findViewById(R.id.cb_oilno);
//        cb_spare = view.findViewById(R.id.cb_spare);

        ced_opname = view.findViewById(R.id.ced_dname);
        ced_vcate = view.findViewById(R.id.ced_catetype);
        ced_vnum = view.findViewById(R.id.ced_vhno);
        ced_vmodel = view.findViewById(R.id.ced_vmno);
        ced_loc = view.findViewById(R.id.ced_wloc);
        ced_streading = view.findViewById(R.id.ced_stread);
        ced_sttime = view.findViewById(R.id.ced_sttim);
        ced_endreading = view.findViewById(R.id.ced_endread);
        ced_endtime = view.findViewById(R.id.ced_endtim);
        ll_userph2 = view.findViewById(R.id.ll_userph2);

        Log.d("testparamvalues","phase id is "+mParam1+"id is "+mParam2);

        ced_endtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //st_time = selectedHour + ":" + selectedMinute;

                        int hour,minutes;

                        hour = selectedHour;
                        minutes = selectedMinute;
                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12){
                            timeSet = "PM";
                        }else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes ;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min ).append(" ").append(timeSet).toString();
                        ced_endtime.setText(aTime);

//                        if (selectedHour>12)
//
//                        ced_endtime.setText(selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        formattedDate = df.format(c);

        Log.d("todaydate",formattedDate);

        GetPhaseValueById getPhaseValueById = new GetPhaseValueById();
        getPhaseValueById.execute();


        final View v = view.findViewById(R.id.material_button);
        final MaterialButtonProcess materialButton = new MaterialButtonProcess(view);
//        materialButton.setText(getString(R.string.app_payment));
//        materialButton.setBackgroundColor(getString(R.color.colorAccent));
//        materialButton.setVectorIcon(R.drawable.ic_shopping_cart);
        materialButton.setRadiusPixel(2);
//        materialButton.setProgressColor(getString(R.color.colorWhite));
//        materialButton.setIconColor(getString(R.color.colorWhite));
//        materialButton.setTextColor(getString(R.color.colorWhite));

        materialButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate(ced_endreading.getText().toString(),ced_endtime.getText().toString()))
                {
                    UpdatePhaseSecond phaseSecond = new UpdatePhaseSecond(v);
                    phaseSecond.execute();
                    materialButton.setProgressVisibility(true);
                }
            }
        });

        return view;
    }

    private boolean Validate(String toString, String toString1) {

        if (toString.isEmpty())
        {
            ced_endreading.setError("Enter End reading");
        }
        else if (Double.parseDouble(ced_endreading.getText().toString()) < Double.parseDouble(ced_streading.getText().toString()))
        {
            ced_endreading.setError("Enter greater reading than started reading");
        }
        else if (toString1.isEmpty())
        {
            ced_endreading.setError("Enter End Time");
        }
        else
        {
            return true;
        }
        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    class GetPhaseValueById extends AsyncTask<Void, Void, String> {

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

                    Snackbar.make(ll_userph2,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    Log.d("testphase20",userJson.getString("driver_name"));

                    ced_opname.setText(userJson.getString("driver_name"));
                    ced_vcate.setText(userJson.getString("category_type"));
                    ced_vnum.setText(userJson.getString("vehicle_number"));
                    ced_vmodel.setText(userJson.getString("model_number"));
                    ced_loc.setText(userJson.getString("location"));
                    ced_streading.setText(userJson.getString("start_kms"));
                    ced_sttime.setText(userJson.getString("start_time"));

                   // progressBar.dismiss();


                } else {

                    Snackbar.make(ll_userph2,"Inavlid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("id",phaseid);
            //Log.d("idforpost0022",mParam1);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }

    class UpdatePhaseSecond extends AsyncTask<Void, Void, String> {

        View vi;

        public UpdatePhaseSecond(View vee) {
            this.vi = vee;
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
                    Snackbar.make(ll_userph2,obj.getString("message"),Snackbar.LENGTH_SHORT).show();


                    MaterialButtonProcess process = new MaterialButtonProcess(vi);

                    process.setProgressVisibility(false);
                    process.setText("Please Wait...");

                    if (process.getText().equals("Please Wait..."))
                    {
                       UpdateStatus status = new UpdateStatus(vi);
                        status.execute();
                    }


                } else {
                    Snackbar.make(ll_userph2,"Invalid Call",Snackbar.LENGTH_SHORT).show();
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

            params.put("id",phaseid);
            params.put("driver_name", ced_opname.getText().toString());
            params.put("vehicle_number", ced_vnum.getText().toString());
            params.put("model_number", ced_vmodel.getText().toString());
            params.put("category_type", ced_vcate.getText().toString());
            params.put("location", ced_loc.getText().toString());
            params.put("start_kms", ced_streading.getText().toString());
            params.put("start_time", ced_sttime.getText().toString());
            params.put("end_kms", ced_endreading.getText().toString());
            params.put("end_time", ced_endtime.getText().toString());
            params.put("diesel","0");
            params.put("food","0");
            params.put("transportation_charge","0");
            params.put("grease","0");
            params.put("normal_oil","0");
            params.put("hydraulic_oil","0");
            params.put("spare_parts","0");
            params.put("respstatus","2");
            params.put("bata", "0");
            params.put("salary", "0");
            params.put("others", "0");
            Log.d("todaydatepost",formattedDate);
            params.put("work_date", formattedDate);
            params.put("total_work_amount", "0");
            params.put("total_client_amount", "0");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatephase1", params);
        }
    }

    class UpdateStatus extends AsyncTask<Void, Void, String> {

        View vi;

        public UpdateStatus(View vi) {
            this.vi=vi;
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

                    Snackbar.make(ll_userph2,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    MaterialButtonProcess process = new MaterialButtonProcess(vi);
                    process.setProgressVisibility(false);
                    process.setText("Completed");



                    Retrofit retrofit = RetrofitHelper.getClient();
                    Api api= retrofit.create(Api.class);

                    api.GetAdminMobile().enqueue(new Callback<ExampleMobile>() {
                        @Override
                        public void onResponse(Call<ExampleMobile> call, Response<ExampleMobile> response) {

                            List<Adminmobile> list = response.body().getAdminmobile();

                            Adminmobile mob= list.get(0);

                            String mobilenumber = mob.getMobile();

                            String text = "Your assigned work for "+ced_vnum.getText().toString()+" at "+ced_loc.getText().toString()
                                    +" is completed with end reading value : "+ced_endreading.getText().toString(); // Replace with your own message.

                            PackageManager packageManager = getContext().getPackageManager();
                            Intent i = new Intent(Intent.ACTION_VIEW);

                            try {
                                // i.putExtra(Intent.EXTRA_TEXT, text);
                                String url = "https://api.whatsapp.com/send?phone="+ mobilenumber +"&text=" + URLEncoder.encode(text, "UTF-8");
                                i.setPackage("com.whatsapp");
                                i.setData(Uri.parse(url));
                                if (i.resolveActivity(packageManager) != null) {
                                    getContext().startActivity(i);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            ((User_Phase_1.IUserPhase1) getActivity()).PhaseOneCompleted();
                        }
                        @Override
                        public void onFailure(Call<ExampleMobile> call, Throwable t) {
                        }
                    });


                  //  ((IUserPhase2)getActivity()).AddedToPhase2();

                } else {
                    Snackbar.make(ll_userph2,"Invalid Call",Snackbar.LENGTH_SHORT).show();
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
            params.put("started_response","2");
            params.put("phaseid",phaseid);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updataddtaskstatus", params);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
