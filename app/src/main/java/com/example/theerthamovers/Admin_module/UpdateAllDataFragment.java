package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.User_Module.User_Phase_2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAllDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAllDataFragment extends Fragment {

    CustomEditText ced_dname,ced_vnum,ced_modelno,ced_cate,ced_loca,ced_stread,ced_sttime,ced_endred,ced_endtime,
                   ced_diesal,ced_food,ced_grease,ced_noil,ced_hoil,ced_spare,ced_other,ced_wodate,ced_bata,ced_salary,ced_trans;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateAllDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateAllDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateAllDataFragment newInstance(String param1, String param2) {
        UpdateAllDataFragment fragment = new UpdateAllDataFragment();
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
        View view = inflater.inflate(R.layout.fragment_update_all_data, container, false);

        ced_dname = view.findViewById(R.id.ced_dname);
        ced_vnum = view.findViewById(R.id.ced_vnum);
        ced_modelno = view.findViewById(R.id.ced_modelno);
        ced_cate = view.findViewById(R.id.ced_cate);
        ced_loca = view.findViewById(R.id.ced_loca);
        ced_stread = view.findViewById(R.id.ced_stread);
        ced_sttime = view.findViewById(R.id.ced_sttime);
        ced_endred = view.findViewById(R.id.ced_endred);
        ced_endtime = view.findViewById(R.id.ced_endtime);
        ced_diesal = view.findViewById(R.id.ced_diesal);
        ced_food = view.findViewById(R.id.ced_food);
        ced_grease = view.findViewById(R.id.ced_grease);
        ced_noil = view.findViewById(R.id.ced_noil);
        ced_spare = view.findViewById(R.id.ced_spare);
        ced_other = view.findViewById(R.id.ced_other);
        ced_wodate = view.findViewById(R.id.ced_wodate);
        ced_hoil = view.findViewById(R.id.ced_hoil);
        ced_bata = view.findViewById(R.id.ced_bata);
        ced_salary = view.findViewById(R.id.ced_salary);
        ced_trans = view.findViewById(R.id.ced_trans);

        GetAllById byId = new GetAllById();
        byId.execute();

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

                if (Validate(ced_wodate.getText().toString()))
                {
                    UpdatePhaseSecond phaseSecond = new UpdatePhaseSecond(v);
                    phaseSecond.execute();
                    materialButton.setProgressVisibility(true);
                }


            }
        });

        return view;
    }

    private boolean Validate(String toString) {

        if (toString.isEmpty())
        {
            ced_wodate.setError("Enter Work Date");
        }
        else
        {
            return true;
        }

        return false;
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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    // JSONObject userJson = obj.getJSONObject("phase1info");

                    MaterialButtonProcess process = new MaterialButtonProcess(vi);
                    process.setProgressVisibility(false);
                    process.setText("Please wait...");

                    if (process.getText().equals("Please wait..."))
                    {
                        UpdateBillStatus status = new UpdateBillStatus(vi);
                        status .execute();

                    }


                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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

            params.put("id",mParam1);
            params.put("driver_name", ced_dname.getText().toString());
            params.put("vehicle_number", ced_vnum.getText().toString());
            params.put("model_number", ced_modelno.getText().toString());
            params.put("category_type", ced_cate.getText().toString());
            params.put("location", ced_loca.getText().toString());
            params.put("start_kms", ced_stread.getText().toString());
            params.put("start_time", ced_sttime.getText().toString());
            params.put("end_kms", ced_endred.getText().toString());
            params.put("end_time", ced_endtime.getText().toString());
            params.put("diesel",ced_diesal.getText().toString());
            params.put("food", ced_food.getText().toString());
            params.put("transportation_charge", ced_trans.getText().toString());
            params.put("grease", ced_grease.getText().toString());
            params.put("normal_oil", ced_noil.getText().toString());
            params.put("hydraulic_oil", ced_hoil.getText().toString());
            params.put("spare_parts", ced_spare.getText().toString());
            params.put("respstatus", "2");
            params.put("bata", ced_bata.getText().toString() );
            params.put("salary", ced_salary.getText().toString());
            params.put("others",  ced_other.getText().toString());
            params.put("work_date", ced_wodate.getText().toString());
            params.put("total_work_amount", "0");
            params.put("total_client_amount", "0");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatephase1", params);
        }
    }

    class UpdateBillStatus extends AsyncTask<Void, Void, String> {

        View vee;

        public UpdateBillStatus(View vi) {
            this.vee=vi;
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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    MaterialButtonProcess process = new MaterialButtonProcess(vee);
                    process.setProgressVisibility(false);
                    process.setText("Completed");

                    if (process.getText().equals("Completed"))
                    {
                        Fragment fragment = ClinetBillFragment.newInstance(mParam2,mParam1);
                        FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.frame1,fragment);
                        ft.commit();
                    }


                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("id",mParam2);
            params.put("bill_status","1");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatebillstatus", params);
        }
    }

    class GetAllById extends AsyncTask<Void, Void, String> {

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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    ced_dname.setText(userJson.getString("driver_name"));
                    ced_vnum.setText(userJson.getString("vehicle_number"));
                    ced_modelno.setText(userJson.getString("model_number"));
                    ced_cate.setText(userJson.getString("category_type"));
                    ced_loca.setText(userJson.getString("location"));
                    ced_stread.setText(userJson.getString("start_kms"));
                    ced_sttime.setText(userJson.getString("start_time"));
                    ced_endred.setText(userJson.getString("end_kms"));
                    ced_endtime.setText(userJson.getString("end_time"));
                    ced_wodate.setText(userJson.getString("work_date"));

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("id",mParam1);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }

}
