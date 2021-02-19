package com.example.theerthamovers.Admin_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theerthamovers.Adapter.GetAllPendingWork;
import com.example.theerthamovers.Adapter.GetAllStartedWork;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.AllAssignedTasksNew;
import com.example.theerthamovers.Pojo.ExamplePending;
import com.example.theerthamovers.Pojo.ExamplePendingNew;
import com.example.theerthamovers.Pojo.ExampleStarted;
import com.example.theerthamovers.Pojo.StartedStatusInfo;
import com.example.theerthamovers.Pojo.StatusinfoPending;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pending_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pending_Fragment extends Fragment {

    RecyclerView rcv_pending;
    CustomTextView ctv_pendstatus;
    String formatdate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pending_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pending_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Pending_Fragment newInstance(String param1, String param2) {
        Pending_Fragment fragment = new Pending_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_pending_, container, false);

        rcv_pending=view.findViewById(R.id.rcv_pending);
        ctv_pendstatus=view.findViewById(R.id.ctv_pendstatus);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formatdate = df.format(c);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        api.GetAllPending(formatdate).enqueue(new Callback<ExamplePendingNew>() {
            @Override
            public void onResponse(Call<ExamplePendingNew> call, Response<ExamplePendingNew> response) {

                List<AllAssignedTasksNew> list = response.body().getAllassignedtaskss();

                if (response.body().getMessage().equals("No value found"))
                {
                    rcv_pending.setVisibility(View.GONE);
                    ctv_pendstatus.setVisibility(View.VISIBLE);

                }
                else
                {
                    GetAllPendingWork adapter = new GetAllPendingWork(getContext(),list);
                    rcv_pending.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv_pending.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ExamplePendingNew> call, Throwable t) {

            }
        });

        return view;
    }

    private void LoadFragment(Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame2,fragment);
        ft.commit();

    }
}
