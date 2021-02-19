package com.example.theerthamovers.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.theerthamovers.R;


public class CustomButton extends AppCompatButton {

    public CustomButton(Context context)
    {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs)
    {
        this(context, attrs, R.attr.borderlessButtonStyle);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            setTextSize(18);
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/GeometriaLight.ttf");
            setTypeface(tf);
        }
    }

}
