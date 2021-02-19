package com.example.theerthamovers.Admin_module;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.theerthamovers.Dialogue.NoInterNetDialogue;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Interface.OnFragmentInteractionListener;

import com.example.theerthamovers.Pojo.Adminpref;
import com.example.theerthamovers.Pojo.Userpref;
import com.example.theerthamovers.SharedPrefrence.SaveSharedPreference;
import com.example.theerthamovers.SharedPrefrence.SharedPrefMgr;
import com.example.theerthamovers.User_Module.User_Home_Fragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.theerthamovers.R;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Admin_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                               OnFragmentInteractionListener, Add_Vehicle.IAddVehicle ,
        Add_Task.IAddNewTask,Admin_Login.IAdminLogIn {

   // DrawerLayout drawer_layout;
    FrameLayout frame1;
    Fragment currenthold;
    DrawerLayout drawerLayout;
    ConstraintLayout con_adhome;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        con_adhome=findViewById(R.id.con_adhome);

        final File docsFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers");
        final File docsFolderdup = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Duplicate");
        final File docsFoldermain = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Main");
        final File docsFolderdupadmin = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Duplicate/Admin Bill");
        final File docsFolderdupclient = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Duplicate/Client Bill");
        final File docsFoldermainadmin = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Main/Admin Bill");
        final File docsFoldermainclient = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Theerthamovers/Main/Client Bill");

        if (!docsFolder.isFile()) {
            if (!(docsFolder.isDirectory()) &&!(docsFolderdup.isDirectory())&&!(docsFoldermain.isDirectory())
                    &&!(docsFolderdupadmin.isDirectory())&&!(docsFolderdupclient.isDirectory())
                    &&!(docsFoldermainadmin.isDirectory())&&!(docsFoldermainclient.isDirectory())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        Files.createDirectory(Paths.get(docsFolder.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFolderdup.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFoldermain.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFolderdupadmin.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFolderdupclient.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFoldermainadmin.getAbsolutePath()));
                        Files.createDirectory(Paths.get(docsFoldermainclient.getAbsolutePath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "unabble to", Toast.LENGTH_LONG).show();
                    }
                } else {
                    docsFolder.mkdir();
                    docsFolderdup.mkdir();
                    docsFoldermain.mkdir();
                    docsFolderdupadmin.mkdir();
                    docsFolderdupclient.mkdir();
                    docsFoldermainadmin.mkdir();
                    docsFoldermainadmin.mkdir();
                }
            }}

        Adminpref adminall = SharedPrefMgr.getInstance(this).adpref();

        String adminname = adminall.getUsername();
        Log.d("AdminUserName000",adminname);


        if (!SharedPrefMgr.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Admin_Login.class));
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        frame1 = findViewById(R.id.frame1);

        LoadFragment(new Admin_Home_Fragment(), true);

    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        } else {
            if (currenthold.getClass().equals(Admin_Profile_Dashboard.class)) {
                LoadFragment(new Admin_Home_Fragment(), false);
            }
            else if (currenthold.getClass().equals(Add_Vehicle.class)) {
                LoadFragment(new Admin_Home_Fragment(), false);
            }
            else if (currenthold.getClass().equals(Get_All_Drivers.class)) {
                LoadFragment(new Admin_Home_Fragment(), false);
            }
            else  if (currenthold.getClass().equals(Add_Task.class)) {
                LoadFragment(new Admin_Home_Fragment(), false);
            }
            else  if (currenthold.getClass().equals(Get_All_Vehicle.class)) {
                LoadFragment(new Admin_Home_Fragment(), false);
            }
            else {
                super.onBackPressed();
            }
        }
    }

    private void fn_permission() {

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 112); // your request code

    }



    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

//    private void checkConnection() {
//
//        boolean isConnected = ConnectivityReceiver.isConnected();
//        showSnack(isConnected);
//
//        if (!isConnected)
//        {
//            changeActivity();
//        }
//
//    }

    private void changeActivity() {

//            Intent intent = new Intent(this,NoInternet.class);
//            startActivity(intent);

        FragmentManager fm = getSupportFragmentManager();
        NoInterNetDialogue dialogue = new NoInterNetDialogue(this);
        dialogue.show(fm,"");

    }



    private void showSnack(boolean isConnected) {

        String message;
        if (isConnected) {
            message = "Connected";
        } else {
            message="No Internet";
            changeActivity();
            finish();
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();


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
    public void onDestroy() {


        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int menuid = menuItem.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (menuid) {

            case R.id.home:
                Adminpref adminall = SharedPrefMgr.getInstance(this).adpref();
                String adminname = adminall.getUsername();
                LoadFragment(new Admin_Home_Fragment(), false);
                break;

            case R.id.addvehicle:
                LoadFragment(new Add_Vehicle(), false);
                break;

            case R.id.assigntask:
                LoadFragment(new Add_Task(), false);
                break;

            case R.id.getalldriver:
                LoadFragment(new Get_All_Drivers(), false);
                break;

            case R.id.getaallvehicle:
                LoadFragment(new Get_All_Vehicle(), false);
                break;


            case R.id.profile:
                Adminpref adminalll = SharedPrefMgr.getInstance(this).adpref();
                String adminnamee = adminalll.getUsername();
                LoadFragment(new Admin_Profile_Dashboard(adminnamee), false);
                break;

            case R.id.signout:
                LogOut();
                break;
        }
        return false;
    }

    private void LogOut() {

        SharedPrefMgr.getInstance(getBaseContext()).logout();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void Add_New_Vehicyle() {
//        Toast.makeText(this, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
        SuccessDialogue dialogue = new SuccessDialogue(Admin_Home.this,"Vehicle Added Successfully");
        dialogue.show(fm,"");
    }

    @Override
    public void AdddTask() {
        FragmentManager fm = getSupportFragmentManager();
        SuccessDialogue dialogue = new SuccessDialogue(Admin_Home.this,"Task Assigned Successfully");
        dialogue.show(fm,"");
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void adminLoggedIn() {
        Snackbar.make(con_adhome,"Login Success",Snackbar.LENGTH_SHORT).show();
        startActivity(new Intent(Admin_Home.this, Admin_Home.class));
        finish();
    }
}
