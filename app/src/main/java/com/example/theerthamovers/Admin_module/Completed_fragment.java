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

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.AllassignedtasksComple;
import com.example.theerthamovers.Adapter.GetAllCompletedWork;
import com.example.theerthamovers.Pojo.ExampleCompletedNew;
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
 * Use the {@link Completed_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed_fragment extends Fragment {

    RecyclerView rcv_completed;
    String formatdate;
    CustomTextView ctv_pendstatuss;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Completed_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Completed_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Completed_fragment newInstance(String param1, String param2) {
        Completed_fragment fragment = new Completed_fragment();
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
        View view= inflater.inflate(R.layout.fragment_completed_fragment, container, false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formatdate = df.format(c);

        rcv_completed = view.findViewById(R.id.rcv_completed);
        ctv_pendstatuss = view.findViewById(R.id.ctv_pendstatuss);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        api.GetAllCompletedd(formatdate).enqueue(new Callback<ExampleCompletedNew>() {
            @Override
            public void onResponse(Call<ExampleCompletedNew> call, Response<ExampleCompletedNew> response) {

                List<AllassignedtasksComple> list = response.body().getAllassignedtasksssses();

                if (response.body().getMessage().equals("No value found"))
                {
                    rcv_completed.setVisibility(View.GONE);
                    ctv_pendstatuss.setVisibility(View.VISIBLE);
                }
                else
                {
                    GetAllCompletedWork adapter = new GetAllCompletedWork(getContext(),list);
                    rcv_completed.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv_completed.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ExampleCompletedNew> call, Throwable t) {

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
