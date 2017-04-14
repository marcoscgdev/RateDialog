package com.marcoscg.ratedialog;

/**
 * Created by marcos on 13/04/2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class TitleTextView extends TextView {

    private Context ctx;

    public TitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctx = context;
        init();
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        init();
    }

    public TitleTextView(Context context) {
        super(context);
        ctx = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "medium.ttf");
        setTypeface(tf);
        setTextColor(getPrimaryTextColor(ctx));
    }

    private int getPrimaryTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray arr = context.obtainStyledAttributes(typedValue.data, new int[]{
                android.R.attr.textColorPrimary});
        int color = arr.getColor(0, -1);
        arr.recycle();
        return color;
    }

}