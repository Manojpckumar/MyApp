package com.example.theerthamovers.Admin_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theerthamovers.Adapter.GetAllCompletedAllTask;
import com.example.theerthamovers.Adapter.GetAllPendingWork;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.Example;
import com.example.theerthamovers.Pojo.Statusinfocompleted;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetAllCompletedTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetAllCompletedTask extends Fragment {

    RecyclerView rcv_completedall;
    CustomTextView ctv_pendstatus;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetAllCompletedTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetAllCompletedTask.
     */
    // TODO: Rename and change types and number of parameters
    public static GetAllCompletedTask newInstance(String param1, String param2) {
        GetAllCompletedTask fragment = new GetAllCompletedTask();
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
        View view = inflater.inflate(R.layout.fragment_get_all_completed_task, container, false);

        rcv_completedall = view.findViewById(R.id.rcv_completedall);
        ctv_pendstatus = view.findViewById(R.id.ctv_allcompleted);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api = retrofit.create(Api.class);

        api.GetAllCompleted().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                List<Statusinfocompleted> list = response.body().getStatusinfocompleted();

                if (list.isEmpty())
                {
                   ctv_pendstatus.setVisibility(View.VISIBLE);
                }
                else
                {
                    GetAllCompletedAllTask adapter = new GetAllCompletedAllTask(getContext(),list);
                    rcv_completedall.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv_completedall.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

        return view;
    }
}
