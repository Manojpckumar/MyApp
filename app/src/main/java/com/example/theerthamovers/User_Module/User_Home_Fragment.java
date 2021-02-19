package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.TodayTaskFetch;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.GetAssignedTaskPoJo;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Use the {@link User_Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Home_Fragment extends Fragment {

    LinearLayout ll_notask,ll_taskdet;
    CardView cv_todayTask;
    String formattedDate,LoginedOperatorName;
    RecyclerView rcv_todayTask;
    CustomTextView ctv_vname,ctv_work_loc,ctv_work_date,ctv_opname,ctv_null;
    RelativeLayout rl_user_hfrag;
    String date;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String      mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public User_Home_Fragment() {
        // Required empty public constructor
    }

    public User_Home_Fragment(String opname) {

        this.LoginedOperatorName = opname;

    }

    public User_Home_Fragment(String opName, String formattedDate) {
        this.LoginedOperatorName = opName;
        this.date=formattedDate;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Home_Fragment newInstance(String param1, String param2) {
        User_Home_Fragment fragment = new User_Home_Fragment();
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

        View view = inflater.inflate(R.layout.fragment_user__home_, container, false);

        ctv_vname = view.findViewById(R.id.ctv_vname);
        ctv_work_loc = view.findViewById(R.id.ctv_work_loc);
        ctv_work_date = view.findViewById(R.id.ctv_work_date);

        cv_todayTask = view.findViewById(R.id.cv_todayTask);
        ctv_null = view.findViewById(R.id.ctv_nulls);
        ll_taskdet = view.findViewById(R.id.ll_taskdet);
        ctv_opname = view.findViewById(R.id.ctv_opname);
        ctv_opname.setText(LoginedOperatorName);

        rcv_todayTask = view.findViewById(R.id.rcv_todayTask);
        rl_user_hfrag = view.findViewById(R.id.rl_user_hfrag);

        AssignTaskToOperator operator = new AssignTaskToOperator();
        operator.execute();

        return view;
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

                    Snackbar.make(rl_user_hfrag, obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                        JSONArray json = obj.getJSONArray("Allassignedtasks");

                        List<GetAssignedTaskPoJo> list = new ArrayList<>();

                        for (int i =0 ; i<=json.length() ; i++)
                        {
                           JSONObject object = json.getJSONObject(i);

                            GetAssignedTaskPoJo poJo = new GetAssignedTaskPoJo();

                            poJo.setId(object.getInt("id"));
                            poJo.setOpname(object.getString("driver_name"));
                            poJo.setVehname(object.getString("vehicle_name"));
                            poJo.setVehnum(object.getString("vehicle_number"));
                            poJo.setVehmodel(object.getString("model_no"));
                            poJo.setVehcate( object.getString("category"));
                            poJo.setWork_loc( object.getString("work_location"));
                            poJo.setWork_date(object.getString("work_date"));
                            poJo.setSt_response(object.getString("started_response"));
                            poJo.setPhaseid(object.getInt("phaseid"));

                            Log.d("testval000",object.getString("driver_name"));

                            list.add(poJo);

                            // change condition for if and else

                                TodayTaskFetch fetch = new TodayTaskFetch(getContext(),list);
                                rcv_todayTask.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcv_todayTask.setAdapter(fetch);


                        }

                }

                else {
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

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = df.format(c);

            HashMap<String, String> params = new HashMap<>();
            params.put("driver_name",LoginedOperatorName);
            params.put("work_date",formattedDate);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getTasksassigned", params);
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
