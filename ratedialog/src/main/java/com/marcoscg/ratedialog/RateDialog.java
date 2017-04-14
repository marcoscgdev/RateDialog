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

    private static int DAYS_UNTIL_PROMPT = 3;
    private static int LAUNCHES_UNTIL_PROMPT = 7;

    private static AlertDialog dialog;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    public static RateDialog with(Activity activity) {

        prefs = activity.getSharedPreferences("ratedialog", 0);
        editor = prefs.edit();

        if (prefs.getBoolean("dontshowagain", false)) { return new RateDialog(); }

        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                show(activity);
            }
        }

        editor.commit();

        return new RateDialog();
    }

    public static RateDialog with(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {

        if (daysUntilPrompt > 0)
            DAYS_UNTIL_PROMPT = daysUntilPrompt;
        if (launchesUntilPrompt > 0)
            LAUNCHES_UNTIL_PROMPT = launchesUntilPrompt;

        prefs = activity.getSharedPreferences("ratedialog", 0);
        editor = prefs.edit();

        if (prefs.getBoolean("dontshowagain", false)) { return new RateDialog(); }

        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                show(activity);
            }
        }

        editor.commit();

        return new RateDialog();
    }

    public static RateDialog show(final Activity activity) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);

        title.setText(String.format(activity.getResources().getString(R.string.rate_dialog_title), activity.getResources().getString(R.string.app_name)));
        message.setText(String.format(activity.getResources().getString(R.string.rate_dialog_message), activity.getResources().getString(R.string.app_name)));

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

        return new RateDialog();
    }

    private static void neverShow() {
        if (editor != null) {
            editor.putBoolean("dontshowagain", true);
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