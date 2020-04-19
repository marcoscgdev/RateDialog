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
        RateDialog.init(this); // Shows the dialog each 3 days and each 7 launches (default config)
//        RateDialog.init(this, 2, RateDialog.DEFAULT_LAUNCHES_UNTIL_PROMPT);  // Shows the dialog with custom config.

        // Custom dialog instance
//        RateDialog rateDialog = new RateDialog(this, "custom_rate_dialog", 0 , 3);
//        rateDialog.init();
    }

    public void openRateDialog (View v) {
        RateDialog rateDialog = new RateDialog(this, "custom_rate_dialog_key");
        rateDialog.showDialog();
    }

}
