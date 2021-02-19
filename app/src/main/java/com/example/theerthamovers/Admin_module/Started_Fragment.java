package com.example.theerthamovers.Admin_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theerthamovers.Adapter.GetAllCompletedWork;
import com.example.theerthamovers.Adapter.GetAllStartedWork;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.AllassignedtasksStarted;
import com.example.theerthamovers.Pojo.Example;
import com.example.theerthamovers.Pojo.ExampleStarted;
import com.example.theerthamovers.Pojo.ExampleStartedNew;
import com.example.theerthamovers.Pojo.StartedStatusInfo;
import com.example.theerthamovers.Pojo.Statusinfocompleted;
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
 * Use the {@link Started_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Started_Fragment extends Fragment {

    RecyclerView rcv_started;
    String formatdate;
    CustomTextView ctv_startstatus;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Started_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Started_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Started_Fragment newInstance(String param1, String param2) {
        Started_Fragment fragment = new Started_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_started_, container, false);

        rcv_started = view.findViewById(R.id.rcv_started);
        ctv_startstatus = view.findViewById(R.id.ctv_startstatus);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formatdate = df.format(c);

        api.GetAllStarted(formatdate).enqueue(new Callback<ExampleStartedNew>() {
            @Override
            public void onResponse(Call<ExampleStartedNew> call, Response<ExampleStartedNew> response) {

                List<AllassignedtasksStarted> list = response.body().getAllassignedtasksss();

                if (response.body().getMessage().equals("No value found"))
                {
                    rcv_started.setVisibility(View.GONE);
                    ctv_startstatus.setVisibility(View.VISIBLE);
                }
                else
                {
                    GetAllStartedWork adapter = new GetAllStartedWork(getContext(),list);
                    rcv_started.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv_started.setAdapter(adapter);
                }



            }

            @Override
            public void onFailure(Call<ExampleStartedNew> call, Throwable t) {

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
