package com.example.theerthamovers.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTexView extends AppCompatTextView {

    public CustomTexView(Context context)
    {
        super(context);
        init();
    }

    public CustomTexView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomTexView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/GeometriaLight.ttf");
            setTypeface(tf);
        }
    }

}
