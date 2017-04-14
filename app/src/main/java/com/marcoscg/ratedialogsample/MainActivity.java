package com.marcoscg.ratedialogsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.marcoscg.ratedialog.RateDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RateDialog.with(this); // Shows the dialog each 3 days or each 7 launches (default config)
        //RateDialog.with(this, 2, 0);  // Shows the dialog with custom config. Use 0 to get the default value
    }

    public void openRateDialog (View v) {
        RateDialog.show(this); // Shows the dialog instantly
    }

}
