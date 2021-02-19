package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.Admin_module.UpdateAllDataFragment;
import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenerateBill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerateBill extends Fragment {

    String phaseid,id;
    Fragment currenthold;
    CustomEditText ced_dnamez,ced_vnumz,ced_modelnoz,ced_catez,ced_locaz,ced_streadz,ced_sttimez,ced_endredz,ced_endtimez,
            ced_diesalz,ced_foodz,ced_greasez,ced_noilz,ced_hoilz,ced_sparez,ced_otherz,ced_wodatez,ced_bataz,ced_salaryz,ced_transz;

    LinearLayout ll_total_card;
    private File pdfFile;
    CustomButton cbn_calculatez,cbn_generateBillz;
    ImageView iv_downloadz,iv_sharez;
    CustomEditText ced_ratez;
    CustomTextView ctv_drnamez,ctv_vehnoz,ctv_locaz,ctv_vehnamez,ctv_startreadz,
            ctv_endreadz,ctv_pointz,ctv_tranchargez,ctv_bataz,ctv_tot_pntz,ctv_wamountz,ctv_trancostz,ctv_batachz,ctv_totalz;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GenerateBill() {
        // Required empty public constructor
    }

    public GenerateBill(String phaseid) {

    }

    public GenerateBill(String valueOf, String valueOf1) {
        this.phaseid =valueOf;
        this.id=valueOf1;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenerateBill.
     */
    // TODO: Rename and change types and number of parameters
    public static GenerateBill newInstance(String param1, String param2) {
        GenerateBill fragment = new GenerateBill();
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
        View view = inflater.inflate(R.layout.fragment_generate_bill, container, false);

        ced_dnamez = view.findViewById(R.id.ced_dnamez);
        ced_vnumz = view.findViewById(R.id.ced_vnumz);
        ced_modelnoz = view.findViewById(R.id.ced_modelnoz);
        ced_catez = view.findViewById(R.id.ced_catez);
        ced_locaz = view.findViewById(R.id.ced_locaz);
        ced_streadz = view.findViewById(R.id.ced_streadz);
        ced_sttimez = view.findViewById(R.id.ced_sttimez);
        ced_endredz = view.findViewById(R.id.ced_endredz);
        ced_endtimez = view.findViewById(R.id.ced_endtimez);
        ced_wodatez = view.findViewById(R.id.ced_wodatez);
        ced_bataz = view.findViewById(R.id.ced_bataz);
        ced_transz = view.findViewById(R.id.ced_transz);

        GetAllByIdz byId = new GetAllByIdz();
        byId.execute();

        final View v = view.findViewById(R.id.material_button);
        final MaterialButtonProcess materialButton = new MaterialButtonProcess(view);

        materialButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate(ced_bataz.getText().toString(),ced_transz.getText().toString()))
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
            ced_bataz.setError("Enter bata amount");
        }else if (toString1.isEmpty())
        {
            ced_transz.setError("Enter transportation charge Date");
        }
        else
        {
            return true;
        }
        return false;
    }

    class UpdatePhaseSecond extends AsyncTask<Void, Void, String> {

        View view;

        public UpdatePhaseSecond(View v) {
            this.view = v;
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

                        MaterialButtonProcess process = new MaterialButtonProcess(view);
                    process.setProgressVisibility(false);
                    process.setText("Please Wait...");

                        if (process.getText().equals("Please Wait..."))
                        {
                            UpdateBillStatus status = new UpdateBillStatus(view);
                            status.execute();
                        }

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();

            params.put("id",phaseid);
            params.put("driver_name", ced_dnamez.getText().toString());
            params.put("vehicle_number", ced_vnumz.getText().toString());
            params.put("model_number", ced_modelnoz.getText().toString());
            params.put("category_type", ced_catez.getText().toString());
            params.put("location", ced_locaz.getText().toString());
            params.put("start_kms", ced_streadz.getText().toString());
            params.put("start_time", ced_sttimez.getText().toString());
            params.put("end_kms", ced_endredz.getText().toString());
            params.put("end_time", ced_endtimez.getText().toString());
            params.put("diesel","0");
            params.put("food", "0");
            params.put("transportation_charge", ced_transz.getText().toString());
            params.put("grease", "0");
            params.put("normal_oil", "0");
            params.put("hydraulic_oil", "0");
            params.put("spare_parts", "0");
            params.put("salary", "0");
            params.put("others", "0");
            params.put("respstatus", "2");
            params.put("bata", ced_bataz.getText().toString() );
            params.put("work_date", ced_wodatez.getText().toString());
            params.put("total_work_amount", "0");
            params.put("total_client_amount", "0");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatephase1", params);
        }
    }

    class GetAllByIdz extends AsyncTask<Void, Void, String> {

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

                    ced_dnamez.setText(userJson.getString("driver_name"));
                    ced_vnumz.setText(userJson.getString("vehicle_number"));
                    ced_modelnoz.setText(userJson.getString("model_number"));
                    ced_catez.setText(userJson.getString("category_type"));
                    ced_locaz.setText(userJson.getString("location"));
                    ced_streadz.setText(userJson.getString("start_kms"));
                    ced_sttimez.setText(userJson.getString("start_time"));
                    ced_endredz.setText(userJson.getString("end_kms"));
                    ced_endtimez.setText(userJson.getString("end_time"));
                    ced_wodatez.setText(userJson.getString("work_date"));

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
            params.put("id",phaseid);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }

    class UpdateBillStatus extends AsyncTask<Void, Void, String> {

        View vie;

        public UpdateBillStatus(View view) {
            this.vie = view;
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

                    MaterialButtonProcess process = new MaterialButtonProcess(vie);
                    process.setProgressVisibility(false);

                    if (obj.getString("message").equals("bill status updated successfully"))
                    {
                        process.setText("Completed");
                        // load to generate bill two
                        LoadFragment(new GenerateBillTwo(id,phaseid),false);
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
            params.put("id",id);
            params.put("bill_status","1");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatebillstatus", params);
        }
    }


    public void LoadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currenthold = fragment;
    }

}
