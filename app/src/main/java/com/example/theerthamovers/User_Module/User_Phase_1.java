package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.Adminmobile;
import com.example.theerthamovers.Pojo.ExampleMobile;
import com.example.theerthamovers.Pojo.LoginGet;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link User_Phase_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Phase_1 extends Fragment {

    CustomEditText ced_opname, ced_vehcate, ced_vehnum, ced_vehmodel, ced_loc, ced_st_reading, ced_st_time;
    String st_reading, st_time;
    CustomButton btn_adddet, btn_submit;
    int randromnum,phid;
    String formattedDate;
    LinearLayout ll_userph1;
    String id,opname,vehname,vehnum,vehmodel,vehcate,work_loc,work_date,phase_id;

    MaterialButtonProcess materialButtonProcess;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    private static final String ARG_PARAM3 = "param3";
//    private static final String ARG_PARAM4 = "param4";
//    private static final String ARG_PARAM5 = "param5";
//    private static final String ARG_PARAM6 = "param6";
//    private static final String ARG_PARAM7 = "param7";
//    private static final String ARG_PARAM8 = "param8";
//    private static final String ARG_PARAM9 = "param9";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private String mParam3;
//    private String mParam4;
//    private String mParam5;
//    private String mParam6;
//    private String mParam7;
//    private String mParam8;
//    private String mParam9;

    private OnFragmentInteractionListener mListener;

    public User_Phase_1() {
        // Required empty public constructor
    }

    public User_Phase_1(String id, String opname, String vehname, String vehnum, String vehmodel, String vehcate,
                        String work_loc, String work_date, String phaseid) {

        this.id = id;
        this.opname = opname;
        this.vehname = vehname;
        this.vehnum = vehnum;
        this.vehmodel = vehmodel;
        this.vehcate = vehcate;
        this.work_loc = work_loc;
        this.work_date = work_date;
        this.phase_id = phaseid;
    }

    interface IUserPhase1 {

        void PhaseOneCompleted();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Phase_1.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Phase_1 newInstance(String param1, String param2) {

        User_Phase_1 fragment = new User_Phase_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
//        args.putString(ARG_PARAM3, param3);
//        args.putString(ARG_PARAM4, param4);
//        args.putString(ARG_PARAM5, param5);
//        args.putString(ARG_PARAM6, param6);
//        args.putString(ARG_PARAM7, param7);
//        args.putString(ARG_PARAM8, param8);
//        args.putString(ARG_PARAM8, param9);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            mParam3 = getArguments().getString(ARG_PARAM3);
//            mParam4 = getArguments().getString(ARG_PARAM4);
//            mParam5 = getArguments().getString(ARG_PARAM5);
//            mParam6 = getArguments().getString(ARG_PARAM6);
//            mParam7 = getArguments().getString(ARG_PARAM7);
//            mParam8 = getArguments().getString(ARG_PARAM8);
//            mParam9 = getArguments().getString(ARG_PARAM9);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__phase_1, container, false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        formattedDate = df.format(c);
        Log.d("currdate0",formattedDate);

        ced_opname = view.findViewById(R.id.ced_opname);
        ced_vehcate = view.findViewById(R.id.ced_vehcate);
        ced_vehnum = view.findViewById(R.id.ced_vehnum);
        ced_vehmodel = view.findViewById(R.id.ced_vehmodel);
        ced_loc = view.findViewById(R.id.ced_loc);
        ced_st_reading = view.findViewById(R.id.ced_st_reading);
        ced_st_time = view.findViewById(R.id.ced_st_time);

        ll_userph1 = view.findViewById(R.id.ll_userph1);

        ced_opname.setText(opname);
        ced_vehcate.setText(vehcate);
        ced_vehnum.setText(vehnum);
        ced_vehmodel.setText(vehmodel);
        ced_loc.setText(work_loc);

        //st_reading = ced_st_reading.getText().toString();

        ced_st_time.setOnClickListener(new View.OnClickListener() {

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
                        ced_st_time.setText(aTime);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        Random random = new Random();
        randromnum = random.nextInt(99999);
        Log.d("randvalue", String.valueOf(randromnum));


        View v = view.findViewById(R.id.material_button);
        final MaterialButtonProcess materialButton = new MaterialButtonProcess(view);
//        materialButton.setText(getString(R.string.app_payment));
//        materialButton.setBackgroundColor(getString(R.color.colorAccent));
//        materialButton.setVectorIcon(R.drawable.ic_shopping_cart);
        materialButton.setRadiusPixel(2);
//        materialButton.setProgressColor(getString(R.color.colorWhite));
//        materialButton.setIconColor(getString(R.color.colorWhite));
//        materialButton.setTextColor(getString(R.color.colorWhite));0

        materialButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(ced_st_reading.getText().toString(),ced_st_time.getText().toString()))
                {
                    AddToPhaseOne phaseOne = new AddToPhaseOne(v);
                    phaseOne.execute();
                    materialButton.setProgressVisibility(true);
                }

            }
        });


        return view;
    }

    private boolean validate(String toString, String toString1) {

        if (toString.isEmpty())
        {
            ced_st_reading.setError("Enter Started Reading");
        }
        else if(toString1.isEmpty())
        {
            ced_st_time.setError("Enter Started time");
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

    class AddToPhaseOne extends AsyncTask<Void, Void, String> {

        View vi;

        AddToPhaseOne(View vi)
        {
            this.vi = vi;
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

                    Snackbar.make(ll_userph1,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("phase1");

                    int phid = userJson.getInt("id");

                    MaterialButtonProcess process = new MaterialButtonProcess(vi);

                    process.setProgressVisibility(false);
                    process.setText("Please Wait...");

                    if (process.getText().equals("Please Wait..."))
                    {
                        UpdateStatus status = new UpdateStatus(vi,phid);
                        status.execute();
                    }

                } else {

                    Snackbar.make(ll_userph1,"Invalid attempt or already submitted",Snackbar.LENGTH_SHORT).show();
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
            params.put("driver_name", opname);
            params.put("vehicle_number", vehnum);
            params.put("model_number", vehmodel);
            params.put("category_type", vehcate);
            params.put("location", work_loc);
            params.put("start_kms", ced_st_reading.getText().toString());
            params.put("start_time", ced_st_time.getText().toString());
            params.put("end_kms", "0");
            params.put("end_time", "0");
            params.put("diesel", "0");
            params.put("food", "0");
            params.put("transportation_charge", "0");
            params.put("grease", "0");
            params.put("normal_oil", "0");
            params.put("hydraulic_oil", "0");
            params.put("spare_parts", "0");
            params.put("respstatus", "1");
            params.put("randchecker", String.valueOf(randromnum));
            params.put("bata", "0");
            params.put("salary", "0");
            params.put("others", "0");
            Log.d("todaykkkk",formattedDate);
            params.put("work_date", formattedDate);
            params.put("total_work_amount", "0");
            params.put("total_client_amount", "0");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=addtophase1", params);
        }
    }

    class UpdateStatus extends AsyncTask<Void, Void, String> {

        View vi;
        int phaseid;

        public UpdateStatus(View vi, int phid) {
            this.vi=vi;
            this.phaseid=phid;
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

                    Snackbar.make(ll_userph1,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    MaterialButtonProcess process = new MaterialButtonProcess(vi);

                    process.setProgressVisibility(false);
                    process.setText("Submitted");

                    Retrofit retrofit = RetrofitHelper.getClient();
                    Api api= retrofit.create(Api.class);

                    api.GetAdminMobile().enqueue(new Callback<ExampleMobile>() {
                        @Override
                        public void onResponse(Call<ExampleMobile> call, Response<ExampleMobile> response) {

                            List<Adminmobile> list = response.body().getAdminmobile();

                            Adminmobile mob= list.get(0);

                            String mobilenumber = mob.getMobile();

                            String text = "Your assigned work for "+ced_vehnum.getText().toString()+" at "+ced_loc.getText().toString()
                                    +" is started with starting reading value : "+ced_st_reading.getText().toString(); // Replace with your own message.

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

                        }
                        @Override
                        public void onFailure(Call<ExampleMobile> call, Throwable t) {

                        }
                    });

                   ((IUserPhase1) getActivity()).PhaseOneCompleted();

                } else {
                    Snackbar.make(ll_userph1,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("id", id);
            params.put("started_response","1");
            params.put("phaseid", String.valueOf(phaseid));

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
