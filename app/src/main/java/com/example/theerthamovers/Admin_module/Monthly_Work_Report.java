package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.GetAllPendingWork;
import com.example.theerthamovers.Adapter.MonthlyReportAdapter;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.Allcompletedbetweentask;
import com.example.theerthamovers.Pojo.Allcompletedinc;
import com.example.theerthamovers.Pojo.ExampleMonthlyTask;
import com.example.theerthamovers.Pojo.ExampleSumByMonth;
import com.example.theerthamovers.Pojo.MonthlyReportPojo;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Monthly_Work_Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Monthly_Work_Report extends Fragment {

    ImageView iv_search;
    CustomEditText ced_date_from,ced_date_to;
    RecyclerView rcv_monthly_completed;
    Calendar myCal;
    LinearLayout ll_month;
    ProgressDialog progressBar;
    CustomTextView ctv_erru;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Monthly_Work_Report() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragTest1.
     */
    // TODO: Rename and change types and number of parameters
    public static Monthly_Work_Report newInstance(String param1, String param2) {
        Monthly_Work_Report fragment = new Monthly_Work_Report();
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
        View view = inflater.inflate(R.layout.fragment_frag_test1, container, false);

        myCal= Calendar.getInstance();
        rcv_monthly_completed=view.findViewById(R.id.rcv_monthly_completed);

        progressBar = new ProgressDialog(getContext());
        iv_search = view.findViewById(R.id.iv_search);
        ced_date_from = view.findViewById(R.id.ced_date_from);
        ced_date_to = view.findViewById(R.id.ced_date_to);
        ll_month = view.findViewById(R.id.ll_month);
        ctv_erru = view.findViewById(R.id.ctv_erru);

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



        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String from = ced_date_from.getText().toString();
                String to = ced_date_to.getText().toString();

                if (Validate(from,to))
                {
                    progressBar.setCancelable(false);
                    progressBar.setMessage("Fetching Data...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    Retrofit retrofit = RetrofitHelper.getClient();
                    Api api =retrofit.create(Api.class);

                    api.GetCompletedMonthly(from,to).enqueue(new Callback<ExampleMonthlyTask>() {
                        @Override
                        public void onResponse(Call<ExampleMonthlyTask> call, Response<ExampleMonthlyTask> response) {

                            List<Allcompletedbetweentask> list = response.body().getAllcompletedbetweentasks();

                            if (response.body().getMessage().equals("No value found"))
                            {
                                ctv_erru.setText("No Values Found");
                                ctv_erru.setVisibility(View.VISIBLE);
                                progressBar.dismiss();
                                Snackbar.make(ll_month,"No Value Found",Snackbar.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressBar.dismiss();
                                MonthlyReportAdapter adapter = new MonthlyReportAdapter(getActivity(),list);
                                rcv_monthly_completed.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rcv_monthly_completed.setAdapter(adapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<ExampleMonthlyTask> call, Throwable t) {

                        }
                    });

//                    api.GetCompletedSumMonthly(from,to).enqueue(new Callback<ExampleSumByMonth>() {
//                        @Override
//                        public void onResponse(Call<ExampleSumByMonth> call, Response<ExampleSumByMonth> response) {
//
//                            List<Allcompletedinc> list = response.body().getAllcompletedinc();
//
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<ExampleSumByMonth> call, Throwable t) {
//
//                        }
//                    });


                }

            }
        });

        return view;
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

    private boolean Validate(String from, String to) {

        if (from.isEmpty())
        {
            ced_date_from.setError("Select Valid Fromat");
        }
        else if(to.isEmpty())
        {
            ced_date_to.setError("Select Valid Fromat");
        }
        else
        {
            return true;
        }
         return false;
    }

}
