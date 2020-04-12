package com.marcoscg.ratedialog;

import android.app.Activity;
import android.content.Context;
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

    public static int DEFAULT_DAYS_UNTIL_PROMPT = 3;
    public static int DEFAULT_LAUNCHES_UNTIL_PROMPT = 7;
    private static String DEFAULT_DIALOG_KEY = "ratedialog";

    private Activity activity;
    private SharedPreferences prefs;

    private String dialogKey;
    private int daysUntilPrompt;
    private int launchesUntilPrompt;

    public RateDialog(Activity activity, String dialogKey) {
        this(activity, dialogKey, DEFAULT_DAYS_UNTIL_PROMPT, DEFAULT_LAUNCHES_UNTIL_PROMPT);
    }

    public RateDialog(Activity activity) {
        this(activity, DEFAULT_DIALOG_KEY);
    }

    public RateDialog(Activity activity, String dialogKey, int daysUntilPrompt, int launchesUntilPrompt) {
        this.activity = activity;
        this.dialogKey = dialogKey;
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;

        prefs = activity.getSharedPreferences(dialogKey, Context.MODE_PRIVATE);
    }

    public RateDialog(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        this(activity, DEFAULT_DIALOG_KEY, daysUntilPrompt, launchesUntilPrompt);
    }

    /**
     * Init rate dialog.
     *
     * @deprecated use {@link #init()} instead.
     */
    @Deprecated
    public static void with(Activity activity) {
        init(activity);
    }

    /**
     * Init rate dialog.
     *
     * @deprecated use {@link #init()} instead.
     */
    @Deprecated
    public static void with(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        init(activity, daysUntilPrompt, launchesUntilPrompt);
    }

    public static void init(Activity activity) {
        init(activity, DEFAULT_DAYS_UNTIL_PROMPT, DEFAULT_LAUNCHES_UNTIL_PROMPT);
    }

    public static void init(Activity activity, int daysUntilPrompt, int launchesUntilPrompt) {
        new RateDialog(activity, DEFAULT_DIALOG_KEY, daysUntilPrompt, launchesUntilPrompt).init();
    }

    /**
     * Show rate dialog.
     *
     * @deprecated use {@link #showDialog()} instead.
     */
    @Deprecated
    public static void show(Activity activity) {
        showDialog(activity);
    }

    public static void showDialog(Activity activity) {
        new RateDialog(activity, DEFAULT_DIALOG_KEY).showDialog();
    }

    /**
     * Public methods
     */
    public void init() {
        if (prefs == null || prefs.getBoolean(dialogKey + "dontshowagain", false)) { return; }

        long launch_count = prefs.getLong(dialogKey + "launch_count", 0) + 1;
        prefs.edit().putLong(dialogKey + "launch_count", launch_count).apply();

        long date_firstLaunch = prefs.getLong(dialogKey + "date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            prefs.edit().putLong(dialogKey + "date_firstlaunch", date_firstLaunch).apply();
        }

        if (launch_count >= launchesUntilPrompt) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (daysUntilPrompt * 24 * 60 * 60 * 1000)) {
                show(activity);
            }
        }
    }

    public void showDialog() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);

        title.setText(String.format(activity.getResources().getString(R.string.rate_dialog_title), activity.getResources().getString(R.string.app_name)));
        message.setText(activity.getString(R.string.rate_dialog_message, activity.getResources().getString(R.string.app_name)));

        AlertDialog dialog = new AlertDialog.Builder(activity)
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
                .show();

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
    private void neverShow() {
        if (prefs != null) {
            prefs.edit().putBoolean(dialogKey + "dontshowagain", true).apply();
        }
    }

    private static int dpToPx(int dp, Activity activity) {
        if (Build.VERSION.SDK_INT >= 21)
            dp = dp+2;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}