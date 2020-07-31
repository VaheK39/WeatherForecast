package com.example.lesson48.customized_UI;

import android.app.Activity;
import android.view.WindowManager;

import com.example.lesson48.R;

public class ActivityUI {

    public static void setStatusBarColor(Activity activity, int color) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().setStatusBarColor(activity.getResources().getColor(color, null));
    }
}
