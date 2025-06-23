package com.chetaru.tribe365_new.utility;

import android.app.Activity;
import android.content.Intent;

import com.chetaru.tribe365_new.R;

public class themeUtils {
    public final static int BLACK = 0;
    public final static int RED = 1;
    public static int cTheme;

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme) {
            default:
            case RED:
                activity.setTheme(R.style.AppTheme);
                break;
            case BLACK:
                activity.setTheme(R.style.ColorAppTheme);
                break;
        }
    }
}
