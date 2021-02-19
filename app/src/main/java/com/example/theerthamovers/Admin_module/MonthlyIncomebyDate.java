package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.GetIncomByDateAdapter;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.Pojo.IncomeByDatePojo;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyIncomebyDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyIncomebyDate extends Fragment {

    Calendar myCal;
    CustomEditText ced_date_from,ced_date_to;
    ImageView iv_searchs;
    RecyclerView rcv_monthly_completeds;
    LinearLayout ll_incomebydate;
    ProgressDialog progressBar;
    CustomTextView ctv_err;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonthlyIncomebyDate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragTest2.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyIncomebyDate newInstance(String param1, String param2) {
        MonthlyIncomebyDate fragment = new MonthlyIncomebyDate();
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
        View view =inflater.inflate(R.layout.fragment_frag_test2, container, false);

        myCal= Calendar.getInstance();

        progressBar = new ProgressDialog(getContext());

        ced_date_from = view.findViewById(R.id.ced_date_froms);
        ced_date_to = view.findViewById(R.id.ced_date_tos);
        rcv_monthly_completeds = view.findViewById(R.id.rcv_monthly_completeds);
        iv_searchs = view.findViewById(R.id.iv_searchs);
        ll_incomebydate = view.findViewById(R.id.ll_incomebydate);
        ctv_err = view.findViewById(R.id.ctv_err);

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

        final DatePickerDialog.OnDateSetListener dateListeners = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, monthOfYear);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabels();
            }

        };

        ced_date_from.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), 0, dateListener, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DATE)).show();
            }
        });

        ced_date_to.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), 0, dateListeners, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DATE)).show();
            }
        });


        iv_searchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String from,to;
                from=ced_date_from.getText().toString();
                to=ced_date_to.getText().toString();

             if (Validate(from,to))
             {
                 progressBar.setCancelable(false);
                 progressBar.setMessage("Fetching Data...");
                 progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                 progressBar.setProgress(0);
                 progressBar.setMax(100);
                 progressBar.show();

                 GetIncomeByDate date = new GetIncomeByDate(from,to);
                 date.execute();
             }
             else
             {
                 Snackbar.make(ll_incomebydate,"Enter Valid Dates",Snackbar.LENGTH_SHORT).show();

             }



            }
        });


        return view;
    }

    private boolean Validate(String from, String to) {

        if (from.isEmpty())
        {
            Snackbar.make(ll_incomebydate,"Enter Valid From Date",Snackbar.LENGTH_SHORT).show();
        }
        else if (to.isEmpty())
        {
            Snackbar.make(ll_incomebydate,"Enter Valid To Date",Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }

        return false;
    }

    private void updateLabels() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ced_date_to.setText(sdf.format(myCal.getTime()));
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ced_date_from.setText(sdf.format(myCal.getTime()));
    }

    class GetIncomeByDate extends AsyncTask<Void, Void, String> {

        String frdate,tdate;

        public GetIncomeByDate(String from, String to) {
            this.frdate=from;
            this.tdate=to;
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

                if (!obj.getBoolean("error"))
                {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject mode = obj.getJSONObject("cmptasksinc");

                    List<IncomeByDatePojo> list = new ArrayList<>();

                    Log.d("testlog01051848",mode.getString("total_work_amount"));

                    if(mode.getString("total_work_amount").equals("null"))
                    {
                        progressBar.dismiss();
                        ctv_err.setText("No value Found");
                        ctv_err.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "No value Found", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        list.add(new IncomeByDatePojo(mode.getString("total_work_amount"), "Total Work Amount", String.valueOf(R.drawable.totalworkamntnew)));
                        list.add(new IncomeByDatePojo(mode.getString("diesel"), "Diesel Amount", String.valueOf(R.drawable.dieselnew)));
                        list.add(new IncomeByDatePojo(mode.getString("grease"), "Grease Amount", String.valueOf(R.drawable.greasenew)));
                        list.add(new IncomeByDatePojo(mode.getString("food"), "Food Amount", String.valueOf(R.drawable.foodnew)));
                        list.add(new IncomeByDatePojo(mode.getString("salary"), "Salary Amount", String.valueOf(R.drawable.salarynew)));
                        list.add(new IncomeByDatePojo(mode.getString("bata"), "Bata Amount", String.valueOf(R.drawable.batanew)));
                        list.add(new IncomeByDatePojo(mode.getString("transportation_charge"), "Transportation Charge", String.valueOf(R.drawable.transportationnew)));
                        list.add(new IncomeByDatePojo(mode.getString("normal_oil"), "Normal oil Amount", String.valueOf(R.drawable.normalnew)));
                        list.add(new IncomeByDatePojo(mode.getString("hydraulic_oil"), "Hydraulic Oil Amount", String.valueOf(R.drawable.hydraulicnew)));
                        list.add(new IncomeByDatePojo(mode.getString("spare_parts"), "Spare Parts Amount", String.valueOf(R.drawable.sparepartsnew)));
                        list.add(new IncomeByDatePojo(mode.getString("others"), "Other Amount", String.valueOf(R.drawable.othersnew)));
                        list.add(new IncomeByDatePojo(mode.getString("total_client_amount"), "Total Client Amount", String.valueOf(R.drawable.totalclientamountnew)));

                        progressBar.dismiss();
                        GetIncomByDateAdapter adapter = new GetIncomByDateAdapter(list, getContext());
                        rcv_monthly_completeds.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_monthly_completeds.setAdapter(adapter);

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
            params.put("fromdate",frdate);
            params.put("todate",tdate);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getincomebydate", params);
        }
    }

}
