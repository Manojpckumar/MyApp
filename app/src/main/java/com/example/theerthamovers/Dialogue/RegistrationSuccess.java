package com.example.theerthamovers.Dialogue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.R;

public class RegistrationSuccess extends DialogFragment {
    CustomButton btn_ok;
    TextView tv_text,tv_registered;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Alertstyle);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =null ;

        return view;
    }
}
