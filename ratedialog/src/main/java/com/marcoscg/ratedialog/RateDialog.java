package com.marcoscg.ratedialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RateDialog {

    private static String DEFAULT_DIALOG_KEY = "ratedialog";

    private int DAYS_UNTIL_PROMPT = 3;
    private int LAUNCHES_UNTIL_PROMPT = 7;

    private AlertDialog dialog;
    private Activity activity;
    private String dialogKey;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public RateDialog(Activity activity, String dialogKey) {
        init(activity, dialogKey, 0, 0);
    }

    public RateDialog(Activity activity) {
        init(activity, DEFAULT_DIALOG_KEY, 0, 0);
    }

    public RateDialog(Activity activity, String dialogKey, int daysUntilPrompt, int launchesUntilPrompt) {
        init(activity, dialogKey, daysUntilPrompt, launchesUntilPrompt);
    }

    public RateDialog(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        init(activity, DEFAULT_DIALOG_KEY, daysUntilPrompt, launchesUntilPrompt);
    }

    /**
     * Static methods
     */
    public static void with(Activity activity) {
        with(activity, 0, 0);
    }

    public static void with(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        new RateDialog(activity, DEFAULT_DIALOG_KEY, daysUntilPrompt, launchesUntilPrompt);
    }

    public static void show(Activity activity) {
        new RateDialog(activity, DEFAULT_DIALOG_KEY).showDialog();
    }

    /**
     * Public methods
     */
    public void showDialog() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);

        title.setText(String.format(activity.getResources().getString(R.string.rate_dialog_title), activity.getResources().getString(R.string.app_name)));
        message.setText(activity.getString(R.string.rate_dialog_message, activity.getResources().getString(R.string.app_name)));

        dialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(activity.getResources().getString(R.string.rate_dialog_action_rate), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                        }
                        neverShow();
                        Toast.makeText(activity, activity.getResources().getString(R.string.rate_dialog_thank_you), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton(activity.getResources().getString(R.string.rate_dialog_action_later), null)
                .setNegativeButton(activity.getResources().getString(R.string.rate_dialog_action_never), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        neverShow();
                    }
                })
                .create();
        dialog.show();

        Typeface tf = Typeface.createFromAsset(activity.getAssets(),
                "medium.ttf");

        Button positivebt = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button neutralbt = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button negativebt = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positivebt.setPadding(dpToPx(12, activity),0,dpToPx(12, activity),0);
        positivebt.setTypeface(tf);

        neutralbt.setPadding(dpToPx(12, activity),0,dpToPx(12, activity),0);
        neutralbt.setTypeface(tf);

        negativebt.setPadding(dpToPx(12, activity),0,dpToPx(12, activity),0);
        negativebt.setTypeface(tf);
    }

    /**
     * Private methods
     */
    private void init(Activity activity, String dialogKey, int daysUntilPrompt, int launchesUntilPrompt) {
        if (daysUntilPrompt > 0)
            DAYS_UNTIL_PROMPT = daysUntilPrompt;
        if (launchesUntilPrompt > 0)
            LAUNCHES_UNTIL_PROMPT = launchesUntilPrompt;

        this.activity = activity;
        this.dialogKey = dialogKey;
        prefs = activity.getSharedPreferences(dialogKey, 0);
        editor = prefs.edit();

        if (prefs.getBoolean(dialogKey + "dontshowagain", false)) { return; }

        long launch_count = prefs.getLong(dialogKey + "launch_count", 0) + 1;
        editor.putLong(dialogKey + "launch_count", launch_count);

        long date_firstLaunch = prefs.getLong(dialogKey + "date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong(dialogKey + "date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                show(activity);
            }
        }

        editor.apply();
    }

    private void neverShow() {
        if (editor != null) {
            editor.putBoolean(dialogKey + "dontshowagain", true);
            editor.commit();
        }
    }

    private static int dpToPx(int dp, Activity activity) {
        if (Build.VERSION.SDK_INT >= 21)
            dp = dp+2;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}