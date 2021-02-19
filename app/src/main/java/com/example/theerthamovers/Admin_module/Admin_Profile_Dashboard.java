package com.example.theerthamovers.Admin_module;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.theerthamovers.Adapter.GetAllPendingAllTasks;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.R;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Profile_Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Admin_Profile_Dashboard extends Fragment {

    LinearLayout btn_SignOut,ll_completeTask,ll_pendingTask,ll_getallexbydate,ll_getallworkbydate;
    String username;
    CustomTextView ctv_adminprohead;
    Fragment currenthold;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Profile_Dashboard(String adminnamee) {
        this.username=adminnamee;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Profile_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Profile_Dashboard newInstance(String param1, String param2) {
        Admin_Profile_Dashboard fragment = new Admin_Profile_Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Admin_Profile_Dashboard() {

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

        View view = inflater.inflate(R.layout.fragment_admin__profile__dashboard, container, false);

        btn_SignOut=view.findViewById(R.id.btn_SignOut);
        ctv_adminprohead=view.findViewById(R.id.ctv_adminprohead);
        ll_completeTask=view.findViewById(R.id.ll_completeTask);
        ll_getallexbydate=view.findViewById(R.id.ll_getallexbydate);
        ll_getallworkbydate=view.findViewById(R.id.ll_getallworkbydate);
        ll_pendingTask=view.findViewById(R.id.ll_pendingTask);

        ctv_adminprohead.setText("Welcome..."+username);

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogOut();

            }
        });

        ll_completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new GetAllCompletedTask(),false);

            }
        });

        ll_pendingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new GetAllPendingTasks(),false);

            }
        });

        ll_getallexbydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new MonthlyIncomebyDate(),false);

            }
        });

        ll_getallworkbydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadFragment(new Monthly_Work_Report(),false);

            }
        });


        return view;

    }

    public void LoadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currenthold = fragment;
    }

    private void LogOut() {

        SaveSharedPreference.setLoggedIn(getContext(), false);
        Intent intent = new Intent(getContext(), Admin_Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
