package com.example.theerthamovers.Admin_module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.AddNewVehicle;
import com.example.theerthamovers.Pojo.Veh;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.parser.Line;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Add_Vehicle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Vehicle extends Fragment {

    CustomButton btn_add_vehicle;
    CustomEditText ced_vname,ced_vnum,ced_vmodel,ced_vcate,ced_vpoint;
    LinearLayout ll_addvehicle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Add_Vehicle() {
        // Required empty public constructor
    }

   public interface IAddVehicle
    {
        void Add_New_Vehicyle();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Vehicle.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Vehicle newInstance(String param1, String param2) {
        Add_Vehicle fragment = new Add_Vehicle();
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
        View view = inflater.inflate(R.layout.fragment_add__vehicle, container, false);

        //bidning variable with view
        btn_add_vehicle = view.findViewById(R.id.btn_add_vehicle);
        ced_vname = view.findViewById(R.id.ced_vname);
        ced_vnum = view.findViewById(R.id.ced_vnum);
        ced_vmodel = view.findViewById(R.id.ced_vmodel);
        ced_vcate = view.findViewById(R.id.ced_vcate);
        ced_vpoint = view.findViewById(R.id.ced_vpoint);
        ll_addvehicle = view.findViewById(R.id.ll_addvehicle);

        btn_add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name,number,model,category,point;

                name=ced_vname.getText().toString();
                number=ced_vnum.getText().toString();
                model=ced_vmodel.getText().toString();
                category=ced_vcate.getText().toString();
                point=ced_vpoint.getText().toString();

                if (FormValidation(name,number,model,category,point))
                {
                    Add_Vehicle(name,number,model,category,point);
                }
                else
                {
                    Snackbar.make(ll_addvehicle,"Fill the form completely",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private boolean FormValidation(String name, String number, String model, String category,String point) {

        if (name.isEmpty() )
        {
            ced_vname.setError("Enter Vehicle Name");
        }
        else if(number.isEmpty())
        {
            ced_vnum.setError("Enter Vehicle Number");

        }
        else if(model.isEmpty())
        {
            ced_vmodel.setError("Enter Vehicle Model");
        }
        else if(category.isEmpty())
        {
            ced_vcate.setError("Enter Vehicle Category");
        }
        else if(point.isEmpty())
        {
            ced_vpoint.setError("Enter Vehicle Point Per Minitue");
        }
        else
        {
            return true;
        }

        return false;
    }


    public void Add_Vehicle(String name,String num,String model,String cate,String point)
    {
        Retrofit retrofit = RetrofitHelper.getClient();
        Api api =retrofit.create(Api.class);

        api.Addvehicle(name,num,model,cate,point).enqueue(new Callback<AddNewVehicle>() {
            @Override
            public void onResponse(Call<AddNewVehicle> call, Response<AddNewVehicle> response) {

                if (response.body().getMessage().equals("New Vehicle added successfully"))
                {
                    UpdateUi();
                    ((IAddVehicle) getActivity()).Add_New_Vehicyle();
                }
                else
                {
                    Snackbar.make(ll_addvehicle,"Vehicle already exist",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewVehicle> call, Throwable t) {

            }
        });
    }

    private void UpdateUi() {
        ced_vname.setText("");
        ced_vnum.setText("");
        ced_vmodel.setText("");
        ced_vcate.setText("");
        ced_vpoint.setText("");
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
