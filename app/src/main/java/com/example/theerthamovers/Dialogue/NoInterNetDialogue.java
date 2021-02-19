package com.example.theerthamovers.Dialogue;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.theerthamovers.Admin_module.Admin_Home;
import com.example.theerthamovers.CustomViews.CustomButton;

import com.example.theerthamovers.Network.NetworkCheck;
import com.example.theerthamovers.R;

public class NoInterNetDialogue extends DialogFragment  {

    CustomButton ceb_refresh;
    Context context;

    public NoInterNetDialogue(Context context) {

        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Alertstyle);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =LayoutInflater.from(context).inflate(R.layout.nointernet,container,false);

        ceb_refresh=view.findViewById(R.id.ceb_refresh);

        ceb_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NetworkCheck  check = new NetworkCheck(context);
                if (check.isNetworkAvailable())
                {
                    dismiss();
                }
            }
        });


        return view;
    }


}
