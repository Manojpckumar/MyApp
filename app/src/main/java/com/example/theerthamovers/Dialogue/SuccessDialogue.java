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

public class SuccessDialogue extends DialogFragment {

    String msg;
    Context context;
    CustomTextView ced_msg;
    CustomButton cbn_ok;

    public SuccessDialogue(Context context, String Message) {
        this.msg=Message;
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Alertstyle);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =LayoutInflater.from(context).inflate(R.layout.success_dialogue,container,false) ;

        ced_msg=view.findViewById(R.id.ced_msg);
        cbn_ok=view.findViewById(R.id.cbn_ok);

        ced_msg.setText(msg);

        cbn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

}
