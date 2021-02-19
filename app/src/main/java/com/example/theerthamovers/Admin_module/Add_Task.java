package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.GetAllOperatorsAdapter;
import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.AllVehicleNumber;
import com.example.theerthamovers.Pojo.Alloperator;
import com.example.theerthamovers.Pojo.ExampleAllOperator;
import com.example.theerthamovers.Pojo.ExampleVehicleNumber;
import com.example.theerthamovers.Pojo.GetAllOperator;
import com.example.theerthamovers.Pojo.GetVehicleNumber;
import com.example.theerthamovers.Pojo.Vehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Add_Task#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Task extends Fragment {

    CustomEditText ced_wdate,ced_vname,ced_vmodel,ced_cate,ced_work_loc;
    java.util.Calendar myCal;
    Spinner spn_vhno , spn_opname ,spn_response;
    String vnumber;
    CustomButton cbn_assign_task;
    String num,name,model,cate,opname,work_loc,work_date;
    LinearLayout ll_addtask;
    CustomTextView ctv_spnveh,ctv_spnop;
    List list = new ArrayList();
    List listn = new ArrayList();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Add_Task() {
        // Required empty public constructor
    }

    interface IAddNewTask
    {
        void AdddTask();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Task.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Task newInstance(String param1, String param2) {
        Add_Task fragment = new Add_Task();
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
        View view = inflater.inflate(R.layout.fragment_add__task, container, false);

        myCal= Calendar.getInstance();
        ced_wdate = view.findViewById(R.id.ced_wdate);
        ced_vname = view.findViewById(R.id.ced_vname);
        ced_vmodel = view.findViewById(R.id.ced_vmodel);
        ced_cate = view.findViewById(R.id.ced_vcateo);
        ced_work_loc = view.findViewById(R.id.ced_work_loc);
        ll_addtask = view.findViewById(R.id.ll_addtask);

        cbn_assign_task = view.findViewById(R.id.cbn_assign_task);

        spn_vhno = view.findViewById(R.id.spn_vhno);
        spn_opname = view.findViewById(R.id.spn_opname);

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, monthOfYear);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ced_wdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),0, dateListener,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DATE)).show();
            }
        });

        //fetching vehicle num for spinner
        FetchVehicleNumber();

        // add vehicle num to spinner
        spn_vhno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                vnumber =  spn_vhno.getSelectedItem().toString();

                if (vnumber!="Choose One")
                {
                    Fetchvehicleinfo vehicleinfo = new Fetchvehicleinfo();
                    vehicleinfo.execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Snackbar.make(ll_addtask,"Select One",Snackbar.LENGTH_SHORT).show();
            }
        });

        // fetch all operator
        FetchOperators();

        cbn_assign_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                num=spn_vhno.getSelectedItem().toString();
                name=ced_vname.getText().toString();
                model=ced_vmodel.getText().toString();
                cate=ced_cate.getText().toString();
                opname=spn_opname.getSelectedItem().toString();
                work_loc=ced_work_loc.getText().toString();
                work_date=ced_wdate.getText().toString();

                if (num.isEmpty())
                {
                    Snackbar.make(ll_addtask,"No Vehicles Found Please add a Vehicle to continue",Snackbar.LENGTH_SHORT).show();
                }
                else if (num.equals("Choose One"))
                {
                    Snackbar.make(ll_addtask,"Select a Vehicle to continue",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    Snackbar.make(ll_addtask,"Vehicle Selected",Snackbar.LENGTH_SHORT).show();
                }

                if (opname.isEmpty())
                {
                    Snackbar.make(ll_addtask,"No Operator Found",Snackbar.LENGTH_SHORT).show();
                }
                else if (opname.equals("Choose One"))
                {
                    Snackbar.make(ll_addtask,"Select a Operator to continue",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    Snackbar.make(ll_addtask,"Operator Selected",Snackbar.LENGTH_SHORT).show();
                }

                if (name.isEmpty())
                {
                    ced_vname.setError("Name is empty please select a vehicle number");
                }

                else if(model.isEmpty())
                {
                    ced_vmodel.setError("Model is empty");
                }
                else if(cate.isEmpty())
                {
                    ced_cate.setError("Category is empty");
                }
              //  else if( ctv_spnop.getVisibility()==View.VISIBLE || opname.equals("Choose One"))
//                {
//                    Snackbar.make(ll_addtask,"Select Operator name",Snackbar.LENGTH_SHORT).show();
//                }
                else if(work_loc.isEmpty())
                {
                    Snackbar.make(ll_addtask,"Work location is empty",Snackbar.LENGTH_SHORT).show();
                }
                else if(work_date.isEmpty())
                {
                    ced_wdate.setError("Work date is empty");
                }
                else
                {
                    AssignTaskToOperator operator = new AssignTaskToOperator();
                    operator.execute();
                }

            }
        });

        return view;
    }



    private void UpdateUI() {

        ced_vname.setText("");
        ced_wdate.setText("");
        ced_work_loc.setText("");
        ced_cate.setText("");
        ced_vmodel.setText("");

    }

    private void FetchOperators() {

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api = retrofit.create(Api.class);

        api.GetAllOperators().enqueue(new Callback<ExampleAllOperator>() {
            @Override
            public void onResponse(Call<ExampleAllOperator> call, Response<ExampleAllOperator> response) {

                List<Alloperator> lis = response.body().getAlloperators();

                for (Alloperator alloperator : lis)
                {
                    listn.add(alloperator.getUsername());
                }

                if (response.body().getAlloperators().isEmpty())
                {
                    Snackbar.make(ll_addtask,"No recodrs found in list",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                  //  ctv_spnop.setVisibility(View.GONE);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,listn);
                    spn_opname.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onFailure(Call<ExampleAllOperator> call, Throwable t) {

            }
        });

    }

    public void FetchVehicleNumber() {

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api = retrofit.create(Api.class);

        api.GetAllVehicleDetails().enqueue(new Callback<ExampleVehicleNumber>() {
            @Override
            public void onResponse(Call<ExampleVehicleNumber> call, Response<ExampleVehicleNumber> response) {

                List<AllVehicleNumber> numbers = response.body().getAllvehiclenumber();

                for (AllVehicleNumber number : numbers)
                {
                    list.add(number.getVehicleNumber());
                }

                if (response.body().getAllvehiclenumber().isEmpty())
                {
                    Snackbar.make(ll_addtask,"No Values In List",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, list);
                    spn_vhno.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onFailure(Call<ExampleVehicleNumber> call, Throwable t) {

            }
        });



    }

    private void updateLabel() {
        String myFormat = "YYYY-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ced_wdate.setText(sdf.format(myCal.getTime()));

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

                    Snackbar.make(ll_addtask,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    //creating a new user object
                    Vehicle ve = new Vehicle(
                            userJson.getString("vehicle_name"),
                            userJson.getString("model_no"),
                            userJson.getString("category"),
                            userJson.getString("point_time")
                    );

                    ced_vname.setText(ve.getVehicle_number());
                    ced_vmodel.setText(ve.getModel_no());
                    ced_cate.setText(ve.getCategory());

                } else {
                    Snackbar.make(ll_addtask,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();
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
            params.put("vehicle_number",vnumber);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=vehicleinfo", params);
        }
    }


    class AssignTaskToOperator extends AsyncTask<Void, Void, String> {

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
                   Snackbar.make(ll_addtask,obj.getString("error"),Snackbar.LENGTH_SHORT).show();

                    UpdateUI();
                    ((IAddNewTask)(getActivity())).AdddTask();

                } else {
                    Snackbar.make(ll_addtask,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();
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
            params.put("driver_name",opname);
            params.put("vehicle_name",name);
            params.put("vehicle_number",num);
            params.put("model_no",model);
            params.put("category",cate);
            params.put("work_location",work_loc);
            params.put("work_date",work_date);
            params.put("started_response","0");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=addtotask", params);
        }
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
