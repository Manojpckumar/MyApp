package com.example.theerthamovers.User_Module;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.theerthamovers.Admin_module.Add_Task;
import com.example.theerthamovers.Admin_module.Add_Vehicle;
import com.example.theerthamovers.Admin_module.Admin_Home;
import com.example.theerthamovers.Admin_module.Admin_Login;
import com.example.theerthamovers.Admin_module.Get_All_Drivers;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;
import com.example.theerthamovers.SharedPrefrence.SharedUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.theerthamovers.R;

import java.io.File;

public class User_Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener,User_Phase_1.IUserPhase1,User_Phase_2.IUserPhase2 {

    DrawerLayout user_drawer_layout;
    FrameLayout frame1;
    Fragment currenthold;
    String name;
    CustomTextView ctv_nav_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home__page);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Userpref adminall = SharedPrefMgr.getInstance(this).uprefs();

        name = adminall.getUsername();
        Log.d("UserHomeUserName",name);

        user_drawer_layout = findViewById(R.id.user_drawer_layout);
        frame1 = findViewById(R.id.frame1);
       // ctv_nav_head = findViewById(R.id.ctv_nav_head);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, user_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        user_drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        frame1 = findViewById(R.id.frame1);

      //  ctv_nav_head.setText("Hi !"+name);

//        Fragment fragment = User_Home_Fragment.newInstance(name,"");
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.addToBackStack(null);
//        ft.replace(R.id.frame1,fragment);
//        ft.commit();

        LoadFragment(new User_Home_Fragment(name),true);

    }

    public void LoadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currenthold = fragment;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (currenthold.getClass().equals(User_Home_Fragment.class)) {

            new AlertDialog.Builder(User_Home_Page.this)
                    .setTitle("Are You Sure Want to Exit")
                    .setCancelable(false)
                    .setMessage("When you click ok you will exit from this app")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // moveTaskToBack(true);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.unnamed)
                    .show();
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int menuid = menuItem.getItemId();
        user_drawer_layout.closeDrawer(GravityCompat.START);

        switch (menuid) {
            case R.id.home:
                LoadFragment(new User_Home_Fragment(name), true);
                break;

            case R.id.getcompletedtask:
                LoadFragment(new Get_Completed_task(name), false);
                break;

            case R.id.profile:
                LoadFragment(new Profile_Fragment(name), true);
                break;

            case R.id.signout:
                SharedPrefMgr.getInstance(getBaseContext()).userlogout();
                break;

        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void PhaseOneCompleted() {

//        SuccessDialogue dialogue = new SuccessDialogue(User_Home_Page.this,"Phase One Values Submitted Successfully");
//        FragmentManager manager = getSupportFragmentManager();
//        dialogue.show(manager,"");
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void AddedToPhase2() {

//        SuccessDialogue dialogue = new SuccessDialogue(User_Home_Page.this,"Phase Two Values Submitted Successfully");
//        FragmentManager manager = getSupportFragmentManager();
//        dialogue.show(manager,"");
        getSupportFragmentManager().popBackStack();

     //   LoadFragment(new User_Home_Fragment(),false);

    }
}
