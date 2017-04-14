package com.marcoscg.ratedialog;

/**
 * Created by marco on 13/04/2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class MessageTextView extends TextView {

    private Context ctx;

    public MessageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctx = context;
        init();
    }

    public MessageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        init();
    }

    public MessageTextView(Context context) {
        super(context);
        ctx = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "regular.ttf");
        setTypeface(tf);
        setTextColor(getSecondaryTextColor(ctx));
    }

    private int getSecondaryTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
        TypedArray arr = context.obtainStyledAttributes(typedValue.data, new int[]{
                android.R.attr.textColorSecondary});
        int color = arr.getColor(0, -1);
        arr.recycle();
        return color;
    }

}