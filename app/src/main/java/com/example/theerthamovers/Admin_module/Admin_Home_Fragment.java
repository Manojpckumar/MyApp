package com.example.theerthamovers.Admin_module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.NoInterNetDialogue;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;

import com.example.theerthamovers.Pojo.Adminpref;
import com.example.theerthamovers.R;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Admin_Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Home_Fragment extends Fragment  {

    CustomButton btn_pending,btn_completed,btn_started;
    CustomTextView ctv_adminname;
   // String adminname;
    RelativeLayout rl_adminhomefrag;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Admin_Home_Fragment() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Home_Fragment newInstance(String param1, String param2) {
        Admin_Home_Fragment fragment = new Admin_Home_Fragment();
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

        View view = inflater.inflate(R.layout.fragment_admin__home_, container, false);

        btn_pending=view.findViewById(R.id.btn_pending);
        btn_started=view.findViewById(R.id.btn_started);
        btn_completed=view.findViewById(R.id.btn_completed);

        rl_adminhomefrag=view.findViewById(R.id.rl_adminhomefrag);

        ctv_adminname=view.findViewById(R.id.ctv_adminname);

        // shared value

        Adminpref adminall = SharedPrefMgr.getInstance(getContext()).adpref();
        String adminname = adminall.getUsername();
        ctv_adminname.setText("Hi..."+adminname);

        LoadFragment(new Pending_Fragment());

        btn_pending.setBackgroundResource(R.drawable.tab1_filled_back);
        btn_pending.setTextColor(getActivity().getResources().getColor(R.color.white));

        btn_started.setBackgroundResource(R.drawable.tab2_plain_back);
        btn_started.setTextColor(getActivity().getResources().getColor(R.color.black));

        btn_completed.setBackgroundResource(R.drawable.tab3_plain_back);
        btn_completed.setTextColor(getActivity().getResources().getColor(R.color.black));


        btn_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_pending.setBackgroundResource(R.drawable.tab1_filled_back);
                btn_pending.setTextColor(getActivity().getResources().getColor(R.color.white));

                btn_started.setBackgroundResource(R.drawable.tab2_plain_back);
                btn_started.setTextColor(getActivity().getResources().getColor(R.color.black));

                btn_completed.setBackgroundResource(R.drawable.tab3_plain_back);
                btn_completed.setTextColor(getActivity().getResources().getColor(R.color.black));

                LoadFragment(new Pending_Fragment());

            }
        });

        btn_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_started.setBackgroundResource(R.drawable.tab2_filled_back);
                btn_started.setTextColor(getActivity().getResources().getColor(R.color.white));

                btn_pending.setBackgroundResource(R.drawable.tab1_back_plain);
                btn_pending.setTextColor(getActivity().getResources().getColor(R.color.black));

                btn_completed.setBackgroundResource(R.drawable.tab3_plain_back);
                btn_completed.setTextColor(getActivity().getResources().getColor(R.color.black));

                LoadFragment(new Started_Fragment());

            }
        });

        btn_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_completed.setBackgroundResource(R.drawable.tab3_filled_back);
                btn_completed.setTextColor(getActivity().getResources().getColor(R.color.white));

                btn_started.setBackgroundResource(R.drawable.tab2_plain_back);
                btn_started.setTextColor(getActivity().getResources().getColor(R.color.black));

                btn_pending.setBackgroundResource(R.drawable.tab1_back_plain);
                btn_pending.setTextColor(getActivity().getResources().getColor(R.color.black));

                LoadFragment(new Completed_fragment());

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // register connection status listener
//        checkConnection();
//        MyApplication.getInstance().setConnectivityListener(this);
    }

//    private void checkConnection() {
//
//        boolean isConnected = ConnectivityReceiver.isConnected();
//        showSnack(isConnected);
//
//    }

    private void showSnack(boolean isConnected) {

        String message;
        if (isConnected) {
            message = "Connected";
        } else {
            message="No Internet";

            changeActivity();

            getActivity().finish();
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    private void changeActivity() {

//            Intent intent = new Intent(this,NoInternet.class);
//            startActivity(intent);

        FragmentManager fm = getFragmentManager();
        NoInterNetDialogue dialogue = new NoInterNetDialogue(getContext());
        dialogue.show(fm,"");

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

//    @Override
//    public void onNetworkConnectionChanged(boolean isConnected) {
//
//    }

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
