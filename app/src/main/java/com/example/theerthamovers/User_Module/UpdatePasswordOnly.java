package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePasswordOnly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePasswordOnly extends Fragment {

    CustomEditText ced_userpwd,ced_userpwdconf;
    CustomButton cbn_useruppass;
    int id;
    LinearLayout ll_updatepassonly;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdatePasswordOnly() {
        // Required empty public constructor
    }

    public UpdatePasswordOnly(int id) {
        this.id =id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillModule_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePasswordOnly newInstance(String param1, String param2) {
        UpdatePasswordOnly fragment = new UpdatePasswordOnly();
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
        View view = inflater.inflate(R.layout.fragment_bill_module_fragment, container, false);

        ced_userpwd = view.findViewById(R.id.ced_userpwd);
        ced_userpwdconf = view.findViewById(R.id.ced_userpwdconf);
        cbn_useruppass = view.findViewById(R.id.cbn_useruppass);
        ll_updatepassonly = view.findViewById(R.id.ll_updatepassonly);

        cbn_useruppass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass,conpass;

                pass = ced_userpwd.getText().toString();
                conpass = ced_userpwdconf.getText().toString();

                if (Validate(pass,conpass))
                {
                    UpdateUsPass pass1 = new UpdateUsPass(id,pass);
                    pass1.execute();
                }

            }
        });

        return view;
    }

    private boolean Validate(String pass, String conpass) {

        if (pass.isEmpty() && !(pass.length()==5))
        {
            ced_userpwd.setError("Enter password with minimum five characters");
        }
       else if (conpass.isEmpty() && !(conpass.length()==5))
        {
            ced_userpwdconf.setError("Enter password with minimum five characters");
        }
        else if(!pass.equals(conpass))
        {
            Snackbar.make(ll_updatepassonly,"Password doesn't match",Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }

        return true;
    }


    class UpdateUsPass extends AsyncTask<Void, Void, String> {

        String pass;
        int id;


        public UpdateUsPass(int id, String pass) {
            this.id  = id;
            this.pass = pass;
        }

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

                    Snackbar.make(ll_updatepassonly,obj.getString("message"),Snackbar.LENGTH_SHORT).show();
                    SharedPrefMgr.getInstance(getContext()).userlogout();

                } else {
                    Snackbar.make(ll_updatepassonly,"Invalid call",Snackbar.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("id",String.valueOf(id));
            params.put("password",pass);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updateoperatorpwd", params);
        }
    }
}
