package com.example.theerthamovers.User_Module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.R;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {

    CustomButton ced_signout;
    LinearLayout ll_showAll,ll_today,ll_profile;
    String OpName,formattedDate;
    Fragment currenthold;
    CustomTextView ctv_operator_name,ctv_first_letter;
    LinearLayout ll_userprofile,ll_changepa;
    int id;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    public Profile_Fragment(String name) {
        this.OpName= name;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Fragment newInstance(String param1, String param2) {
        Profile_Fragment fragment = new Profile_Fragment();
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
        View view =inflater.inflate(R.layout.fragment_profile_, container, false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        formattedDate = df.format(c);

        final Userpref adminall = SharedPrefMgr.getInstance(getContext()).uprefs();

        id = adminall.getId();
       Log.d("UserHomeUserName",String.valueOf(id));

        ced_signout=view.findViewById(R.id.ced_signout);
        ll_showAll=view.findViewById(R.id.ll_showAll);
        ll_today=view.findViewById(R.id.ll_today);
        ctv_operator_name=view.findViewById(R.id.ctv_operator_name);
        ctv_first_letter=view.findViewById(R.id.ctv_first_letter);
        ll_userprofile=view.findViewById(R.id.ll_userprofile);

        ll_profile=view.findViewById(R.id.ll_profile);
        ll_changepa=view.findViewById(R.id.ll_changepa);

        ctv_operator_name.setText(OpName);
        ctv_first_letter.setText(OpName.substring(0,1));

        ll_showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new Get_Completed_task(OpName), false);

            }
        });

        ll_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new User_Home_Fragment(OpName,formattedDate), false);

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new UserProfileUpdate(adminall.getId()), false);

            }
        });

        ll_changepa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadFragment(new UpdatePasswordOnly(adminall.getId()), false);
            }
        });

        return view;
    }

    public void LoadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currenthold = fragment;
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
