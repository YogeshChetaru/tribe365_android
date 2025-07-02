package com.chetaru.tribe365_new.UI.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.UserInfo.ActLogin;
import com.chetaru.tribe365_new.utility.SessionParam;

public class Act_Splash extends BaseActivity {
    SessionParam sessionParam;
    String changeit_id = "";
    String que = "";
    String body = null;
    String path = "";
    String type = "";
    String feedbackId = "", supportId = "";
    String currentDate = "";
    //handel custom notification data
    String noti_title = null, noti_desc = null, noti_date = null, noti_link = null;
    //handel click value on notification
    String chatClick = null;
    String HappyIndexNoti = "";
    String readNotificationId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        sessionParam = new SessionParam(mContext);
        try {
            type = getIntent().getStringExtra("type");
            feedbackId = getIntent().getStringExtra("feedbackId");
            supportId = getIntent().getStringExtra("supportId");
            currentDate = getIntent().getStringExtra("currentDate");
            noti_title = getIntent().getStringExtra("title");
            noti_desc = getIntent().getStringExtra("desc");
            noti_link = getIntent().getStringExtra("link");
            noti_date = getIntent().getStringExtra("noti_date");
            HappyIndexNoti = getIntent().getStringExtra("noti");
            try {
                readNotificationId = getIntent().getStringExtra("readNotificationId");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NullPointerException npe) {
            //here we are initializing changeit id again so that we can compare it when app is in background
            npe.printStackTrace();
        }

        /*is used for the delay of 3 seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!sessionParam.token.isEmpty()) {
                    if (sessionParam.logintellsid.equals("y")) {

                        if (sessionParam.id.equals("")) {
                            Intent i = new Intent(mContext, ActLogin.class);
                            finishAllActivities();
                            return;
                        }/* else if(true){
                            startActivity(new Intent(mContext,Act_FreeVersionHome.class));
                            finishAllActivities();

                        }*/else if (body != null) {
                            if (body.equals("You have received a new message")) {
                                //startActivity(new Intent(mContext, Act_NotificationList.class));
                                startActivity(new Intent(mContext, Act_Home.class));
                            } else if (body.equals("How's things at work today?")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("popup", "popup"));
                            } else {
                                startActivity(new Intent(mContext, Act_Home.class));
                            }

                        }
                        if (type != null) {

                            if (type.equals("chat")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("feedbackId", feedbackId).putExtra("currentDate", currentDate));
                            } else if (type.equals("supportChat")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("supportId", supportId).putExtra("currentDate", currentDate));
                            } else if (type.equals("kudos")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("feedbackId", feedbackId).putExtra("currentDate", currentDate));
                            } else if (type.equals("custom_notification")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("noti_title", noti_title).putExtra("noti_desc", noti_desc)
                                        .putExtra("noti_date", noti_date).putExtra("noti_link", noti_link));
                            } else if (type.equals("happyIndexPush")) {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("noti_title", HappyIndexNoti));
                            } else {
                                startActivity(new Intent(mContext, Act_Home.class).putExtra("type", type)
                                        .putExtra("readNotificationId", readNotificationId)
                                        .putExtra("feedbackId", feedbackId).putExtra("currentDate", currentDate));
                            }

                        } else if (sessionParam.loginVersion ==1){
                            startActivity(new Intent(mContext, Act_FreeVersionHome.class));
                        }else if (sessionParam.loginVersion ==3){
                            startActivity(new Intent(mContext, BasicHomeActivity.class));
                        }else {
                            startActivity(new Intent(mContext,Act_Home.class)
                                    .putExtra("readNotificationId" , readNotificationId));

                        }
                        finishAllActivities();

                    } else {
                        Intent i = new Intent(mContext, ActLogin.class);
                        finish();
                        startActivity(i);
                    }

                } else {
                    Intent i = new Intent(mContext, ActLogin.class);
                    finish();
                    startActivity(i);
                    //overridePendingTransition(R.anim.move_up, R.anim.stay_anim);
                    overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_bottom);
                }

            }
        }, 3000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33) और ऊपर
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101
                );
            }
        } else {
            // Lower versions के लिए कोई runtime permission की आवश्यकता नहीं है
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted, Notifications को दिखाएं
            } /*else {
                // Permission Denied
            }*/
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
