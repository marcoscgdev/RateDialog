package com.marcoscg.ratedialog;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by marcos on 13/04/2017.
 */

public class ActionButton extends AppCompatButton {

    public ActionButton(Context context) {
        super(context);
        init();
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "medium.ttf");
        setTypeface(tf);
    }
}