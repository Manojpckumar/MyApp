package com.example.theerthamovers.User_Module;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.CustomViews.MaterialButtonProcess;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileUpdate extends Fragment {

    int UID;
    LinearLayout ll_proupdate;

    CustomEditText ced_id,ced_username,ced_mob,ced_pass,ced_conpass,ced_em;
    CustomTextView tv_changepass;
    CustomButton btn_upprofile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileUpdate() {
        // Required empty public constructor
    }

    public UserProfileUpdate(int id) {
        this.UID=id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileUpdate newInstance(String param1, String param2) {
        UserProfileUpdate fragment = new UserProfileUpdate();
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
        View view = inflater.inflate(R.layout.fragment_user_profile_update, container, false);

        ced_id = view.findViewById(R.id.ced_id);
        ced_username = view.findViewById(R.id.ced_username);
        ced_mob = view.findViewById(R.id.ced_mob);
        ced_pass = view.findViewById(R.id.ced_pass);
        ced_conpass = view.findViewById(R.id.ced_conpass);
        ced_em = view.findViewById(R.id.ced_em);

        ll_proupdate = view.findViewById(R.id.ll_proupdate);
        btn_upprofile = view.findViewById(R.id.btn_upprofile);

        GetDetailsById id = new GetDetailsById(UID);
        id.execute();

        btn_upprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname,mob,email;

                uname = ced_username.getText().toString();
                mob = ced_mob.getText().toString();
                email = ced_em.getText().toString();

                if (Validate(uname,mob,email))
                {
                    UpdateProfile profile = new UpdateProfile(UID,uname,mob,email);
                    profile.execute();

                }

            }
        });


        return view;
    }

    private boolean Validate(String uname, String mob, String email) {

        if (uname.isEmpty())
        {
            ced_username.setError("Enter UserName");
        }
        else if(!Patterns.PHONE.matcher(mob).matches() && mob.isEmpty())
        {
            ced_mob.setError("Enter Valid mobile Number");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            ced_em.setError("Enter valid email address");
        }
        else
        {
            return true;
        }
        return false;
    }

    class GetDetailsById extends AsyncTask<Void, Void, String> {

        int id;

        public GetDetailsById(int uid) {
            this.id = uid;
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

                    Snackbar.make(ll_proupdate,obj.getString("message"),Snackbar.LENGTH_SHORT).show();

                    if (obj.getString("message").equals("true"))
                    {
                        JSONObject mod = obj.getJSONObject("operator");

                        ced_id.setText(String.valueOf(id));
                        ced_username.setText(mod.getString("username"));
                        ced_em.setText(mod.getString("email"));
                        ced_mob.setText(mod.getString("mobile"));
                    }

                } else {
                    Snackbar.make(ll_proupdate,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

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
            params.put("id", String.valueOf(id));

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getoperatorbyid", params);
        }
    }

    class UpdateProfile extends AsyncTask<Void, Void, String> {

        int id;
        String username,email,mobile;

        public UpdateProfile(int idd,String uname, String mob, String email) {
            this.id = idd;
            this.username =uname;
            this.mobile=mob;
            this.email=email;
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

                    Snackbar.make(ll_proupdate,obj.getString("message"),Snackbar.LENGTH_SHORT).show();


                } else {
                    Snackbar.make(ll_proupdate,"Invalid Attempt",Snackbar.LENGTH_SHORT).show();

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

            params.put("id",String.valueOf(id) );
            params.put("username",username );
            params.put("mobile",mobile );
            params.put("email", email);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updateoperatordet", params);
        }
    }
}
