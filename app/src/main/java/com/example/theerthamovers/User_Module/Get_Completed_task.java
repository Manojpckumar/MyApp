package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.FetchCompletedTask;
import com.example.theerthamovers.Adapter.TodayTaskFetch;
import com.example.theerthamovers.Pojo.GetAssignedTaskPoJo;
import com.example.theerthamovers.Pojo.GetCompletedTaskOperator;
import com.example.theerthamovers.Pojo.LoginGet;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Get_Completed_task#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Get_Completed_task extends Fragment {

    RecyclerView rcv_getall;
    LinearLayout ll_usercomtask;
    String OperatorName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Get_Completed_task() {
        // Required empty public constructor
    }

    public Get_Completed_task(String name) {
        this.OperatorName = name;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Get_Completed_task.
     */
    // TODO: Rename and change types and number of parameters
    public static Get_Completed_task newInstance(String param1, String param2) {
        Get_Completed_task fragment = new Get_Completed_task();
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
        View view= inflater.inflate(R.layout.fragment_get__completed_task, container, false);

        rcv_getall = view.findViewById(R.id.rcv_getall);
        ll_usercomtask = view.findViewById(R.id.ll_usercomtask);

        GetCompletedTask task = new GetCompletedTask();
        task.execute();

        return view;
    }

    class GetCompletedTask extends AsyncTask<Void, Void, String> {

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

                    Snackbar.make(ll_usercomtask,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    JSONArray userJson = obj.getJSONArray("Allcompletedtasks");

                    List<GetCompletedTaskOperator> list = new ArrayList<>();

                    for (int i=0;i<=userJson.length();i++)
                    {

                        JSONObject object = userJson.getJSONObject(i);

                        GetCompletedTaskOperator operator = new GetCompletedTaskOperator(
                          object.getInt("id"),
                          object.getString("driver_name"),
                                object.getString("vehicle_name"),
                                object.getString("vehicle_number"),
                                object.getString("model_no"),
                                object.getString("category"),
                                object.getString("work_location"),
                                object.getString("work_date"),
                                object.getString("started_response"),
                                object.getString("phaseid")
                        );

                        list.add(operator);

                        FetchCompletedTask fetch = new FetchCompletedTask(getContext(),list);
                        rcv_getall.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_getall.setAdapter(fetch);
                    }

                } else {
                    Snackbar.make(ll_usercomtask,"Invalid Call",Snackbar.LENGTH_SHORT).show();
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
            params.put("driver_name",OperatorName);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getcompletedoperatortasks", params);
        }
    }

}
