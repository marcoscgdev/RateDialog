package com.marcoscg.ratedialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RateDialog {

    public static int DEFAULT_DAYS_UNTIL_PROMPT = 3;
    public static int DEFAULT_LAUNCHES_UNTIL_PROMPT = 7;
    private static String DEFAULT_DIALOG_KEY = "default_rate_dialog_key";

    private Activity activity;
    private AlertDialog alertDialog;
    private SharedPreferences prefs;

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
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;

        prefs = activity.getSharedPreferences(dialogKey + "_rdp", Context.MODE_PRIVATE);

        setupDialog();
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
        if (prefs == null || prefs.getBoolean("do_not_show_again", false)) {
            return;
        }

        long launchCount = prefs.getLong("launch_count", 0) + 1;
        prefs.edit().putLong("launch_count", launchCount).apply();

        long dateFirstLaunch = prefs.getLong("date_first_launch", 0);
        if (dateFirstLaunch == 0) {
            dateFirstLaunch = System.currentTimeMillis();
            prefs.edit().putLong("date_first_launch", dateFirstLaunch).apply();
        }

        if (launchCount >= launchesUntilPrompt) {
            if (System.currentTimeMillis() >= dateFirstLaunch +
                    (daysUntilPrompt * 24 * 60 * 60 * 1000)) {
                showDialog();
            }
        }
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    /**
     * Private methods
     */
    private void neverShow() {
        if (prefs != null) {
            prefs.edit().putBoolean("do_not_show_again", true).apply();
        }
    }

    private void setupDialog() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);

        title.setText(String.format(activity.getResources().getString(R.string.rate_dialog_title), activity.getResources().getString(R.string.app_name)));
        message.setText(activity.getString(R.string.rate_dialog_message, activity.getResources().getString(R.string.app_name)));

        alertDialog = new AlertDialog.Builder(activity)
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

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                setButtonStyle(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE));
                setButtonStyle(alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL));
                setButtonStyle(alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE));
            }
        });
    }

    private void setButtonStyle(Button button) {
        if (Build.VERSION.SDK_INT >= 21) {
            button.setPadding(dpToPx(14),0, dpToPx(14),0);
        } else {
            button.setPadding(dpToPx(12),0, dpToPx(12),0);
        }

        button.setTypeface(Typeface.createFromAsset(activity.getAssets(), "medium.ttf"));
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}