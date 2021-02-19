package com.example.theerthamovers.Admin_module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.theerthamovers.Adapter.GetAllOperatorsAdapter;
import com.example.theerthamovers.Adapter.GetAllVehicleAdapter;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.ExampleVehicles;
import com.example.theerthamovers.Pojo.GetAllOperator;
import com.example.theerthamovers.Pojo.GetAllVehicle;
import com.example.theerthamovers.Pojo.Getvehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Get_All_Vehicle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Get_All_Vehicle extends Fragment {

    RecyclerView rcv_getallveh;
    CustomTextView ctv_norec;
    RelativeLayout rl_getallveh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Get_All_Vehicle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Get_All_Vehicle.
     */
    // TODO: Rename and change types and number of parameters
    public static Get_All_Vehicle newInstance(String param1, String param2) {
        Get_All_Vehicle fragment = new Get_All_Vehicle();
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
        View view = inflater.inflate(R.layout.fragment_get__all__vehicle, container, false);

        rcv_getallveh= view.findViewById(R.id.rcv_getallveh);
        rl_getallveh=view.findViewById(R.id.rl_getallveh);

        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        api.GetAllVehicles().enqueue(new Callback<ExampleVehicles>() {
            @Override
            public void onResponse(Call<ExampleVehicles> call, Response<ExampleVehicles> response) {

                List<Getvehicle> list = response.body().getGetvehicles();

                if (list.isEmpty())
                {
                    Snackbar.make(rl_getallveh,"No records Found",Snackbar.LENGTH_SHORT).show();

                }
                else
                {
                    GetAllVehicleAdapter adapter = new GetAllVehicleAdapter(getContext(),list);
                    rcv_getallveh.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv_getallveh.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ExampleVehicles> call, Throwable t) {

            }
        });

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
