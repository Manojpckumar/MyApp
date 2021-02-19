package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.Vehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminBillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminBillFragment extends Fragment {
    java.util.Date c;
    LinearLayout ll_total_card,ll_tot_al,ll_genButtonss,ll_adminbillfrag;
    private File pdfFile;
    CustomButton cbn_calculate,cbn_generateBill;
    ImageView iv_download,iv_share;
    CustomEditText ced_rate;
    CustomTextView ctv_drname,ctv_vehno,ctv_loca,ctv_vehname,ctv_startread,
            ctv_endread,ctv_point,ctv_trancharge,ctv_bata,ctv_tot_pnt,ctv_wamount,ctv_trancost,ctv_batach,ctv_client_amt,
            ctv_total,ctv_model,ctv_salary,ctv_food,ctv_grease,ctv_normaloil,ctv_hydraulicoil,ctv_spare,ctv_others,ctv_work_date;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String phaid;
    private String verid;

    public AdminBillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AdminBillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminBillFragment newInstance(String phaseid, String verumid) {
        AdminBillFragment fragment = new AdminBillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, phaseid);
        args.putString(ARG_PARAM2, verumid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phaid = getArguments().getString(ARG_PARAM1);
            verid = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_bill, container, false);

        ctv_drname = view.findViewById(R.id.ctv_drnames);
        ctv_endread = view.findViewById(R.id.ctv_endreads);
        ctv_vehno = view.findViewById(R.id.ctv_vehnos);
        ctv_model = view.findViewById(R.id.ctv_modelnos);
        ctv_loca = view.findViewById(R.id.ctv_locas);
        ctv_vehname = view.findViewById(R.id.ctv_vehnames);
        ctv_startread = view.findViewById(R.id.ctv_startreads);
        ctv_point = view.findViewById(R.id.ctv_pointsss);
        ctv_work_date = view.findViewById(R.id.ctv_workdates);
        ll_adminbillfrag = view.findViewById(R.id.ll_adminbillfrag);

        ctv_salary = view.findViewById(R.id.ctv_salarys);
        ctv_food = view.findViewById(R.id.ctv_foods);
        ctv_grease = view.findViewById(R.id.ctv_greases);
        ctv_normaloil = view.findViewById(R.id.ctv_normaloils);
        ctv_hydraulicoil = view.findViewById(R.id.ctv_hydraulicoils);
        ctv_spare = view.findViewById(R.id.ctv_spares);
        ctv_others = view.findViewById(R.id.ctv_otherss);
        ctv_client_amt = view.findViewById(R.id.ctv_clients);

        iv_download = view.findViewById(R.id.iv_downloads);
        iv_share = view.findViewById(R.id.iv_shares);

        ctv_total = view.findViewById(R.id.ctv_totals);

        ced_rate = view.findViewById(R.id.ced_rates);
       // ll_total_card = view.findViewById(R.id.ll_tot_al);
        ll_tot_al = view.findViewById(R.id.ll_tot_al);
        ll_genButtonss = view.findViewById(R.id.ll_genButtonss);

        cbn_calculate = view.findViewById(R.id.cbn_calculates);
        cbn_generateBill = view.findViewById(R.id.cbn_generateBillss);

        GetAllById byId = new GetAllById();
        byId.execute();

        cbn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double salary,food,grease,oil,hoil,spare,othres,client_amt,total_to_display;

                salary = Double.parseDouble(ctv_salary.getText().toString());
                food = Double.parseDouble(ctv_food.getText().toString());
                grease = Double.parseDouble(ctv_grease.getText().toString());
                oil = Double.parseDouble(ctv_normaloil.getText().toString());
                hoil = Double.parseDouble(ctv_hydraulicoil.getText().toString());
                spare = Double.parseDouble(ctv_spare.getText().toString());
                othres = Double.parseDouble(ctv_others.getText().toString());
                client_amt = Double.parseDouble(ctv_client_amt.getText().toString());

                total_to_display = salary+food+grease+oil+hoil+spare+othres+client_amt;

                ll_tot_al.setVisibility(View.VISIBLE);
                ctv_total.setText(String.valueOf(total_to_display));
                ctv_total.requestFocus();

                ll_genButtonss.setVisibility(View.VISIBLE);

            }
        });

        cbn_generateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   iv_download.setVisibility(View.VISIBLE);

                UpdateTotalAmount amount = new UpdateTotalAmount();
                amount.execute();
            }
        });

        return view;
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

                    Snackbar.make(ll_adminbillfrag,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    ctv_drname.setText(userJson.getString("driver_name"));
                    ctv_vehno.setText(userJson.getString("vehicle_number"));
                    ctv_model.setText(userJson.getString("model_number"));
                    ctv_loca.setText(userJson.getString("location"));
                    ctv_startread.setText(userJson.getString("start_kms"));
                    ctv_endread.setText(userJson.getString("end_kms"));

                    ctv_salary.setText(userJson.getString("salary"));
                    ctv_food.setText(userJson.getString("food"));
                    ctv_grease.setText(userJson.getString("grease"));
                    ctv_normaloil.setText(userJson.getString("normal_oil"));
                    ctv_hydraulicoil.setText(userJson.getString("hydraulic_oil"));
                    ctv_spare.setText(userJson.getString("spare_parts"));
                    ctv_others.setText(userJson.getString("others"));
                    ctv_work_date.setText(userJson.getString("work_date"));
                    ctv_client_amt.setText(userJson.getString("total_client_amount"));

                    Fetchvehicleinfo fetchvehicleinfo = new Fetchvehicleinfo();
                    fetchvehicleinfo.execute();

                } else {

                    Snackbar.make(ll_adminbillfrag,"Invalid call",Snackbar.LENGTH_SHORT).show();

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
            params.put("id",phaid);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }

    class Fetchvehicleinfo extends AsyncTask<Void, Void, String> {

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

                    //creating a new user object
                    Vehicle ve = new Vehicle(
                            userJson.getString("vehicle_name"),
                            userJson.getString("model_no"),
                            userJson.getString("category"),
                            userJson.getString("point_time")
                    );

                    ctv_vehname.setText(ve.getVehicle_number());
                    ctv_point.setText(ve.getPoint_Time());

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
            params.put("vehicle_number",ctv_vehno.getText().toString());

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=vehicleinfo", params);
        }
    }

    class UpdateTotalAmount extends AsyncTask<Void, Void, String> {

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

                    UpdateBillStatus status = new UpdateBillStatus();
                    status.execute();

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
            params.put("id",phaid);
            Log.d("total_wok_amount",ctv_total.getText().toString());
            params.put("total_work_amount",ctv_total.getText().toString());

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updateworkamount", params);
        }
    }

    class UpdateBillStatus extends AsyncTask<Void, Void, String> {

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
            params.put("id",verid);
            params.put("bill_status","3");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatebillstatus", params);
        }
    }

}
