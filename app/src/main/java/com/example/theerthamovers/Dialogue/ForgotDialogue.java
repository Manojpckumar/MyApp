package com.example.theerthamovers.Dialogue;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.R;

public class ForgotDialogue extends DialogFragment {

    Context context;
    CustomButton cbn_ok;

    CustomTextView ctv_alerttitle,ctv_alertmsg;
    String message,title;

    public ForgotDialogue(Context applicationContext,String msg,String title) {
        this.context =applicationContext;
        this.message=msg;
        this.title=title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Alertstyle);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =LayoutInflater.from(context).inflate(R.layout.forgot_password,container,false) ;

        cbn_ok=view.findViewById(R.id.cbn_ok);
        ctv_alertmsg=view.findViewById(R.id.ctv_alertmsg);
        ctv_alerttitle=view.findViewById(R.id.ctv_alerttitle);

        ctv_alerttitle.setText(title);
        ctv_alertmsg.setText(message);
        cbn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

}
