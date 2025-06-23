package com.chetaru.tribe365_new.UI.Setting;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationManagerCompat;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.UserInfo.Act_ProfileUser;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.judemanutd.autostarter.AutoStartPermissionHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Setting extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.switch_notification)
    SwitchCompat switch_notification;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.link_tv)
    TextView link_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.expand_image)
    ImageView expand_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.expand_note_ll)
    LinearLayout expand_note_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.open_setting_tv)
    TextView open_setting_tv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.auto_start_image)
    ImageView auto_start_image;

    boolean switchMode=false;
    boolean flag= false;
    SessionParam sessionParam;
     String channelId="questions";
     Utility utility;
     BaseRequest baseRequest;
     boolean deviceStatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_setting);
        ButterKnife.bind(this);
        sessionParam=new SessionParam(mContext);
        utility= new Utility();
        setSessionParam();
        try {
            deviceStatus= areNotificationsEnabled();
        }catch (Exception e){
            e.printStackTrace();
            deviceStatus=false;
        }

        expand_image.setBackground(mContext.getResources().getDrawable(R.drawable.ic_expand_more_24));
        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switch_notification.isChecked();
                   // openNotificationSettingsForApp(channelId);
                    sessionParam.isNotification(mContext,true);
                    updateNotificationStatus(true,deviceStatus);
                    sessionParam.persist(mContext);
                }else{
                    sessionParam.isNotification(mContext,false);
                    updateNotificationStatus(false,deviceStatus);
                    sessionParam.persist(mContext);
                }
            }
        });

        auto_start_image.setOnClickListener(view -> {
            Log.d("onCLick", "click");
            Boolean autoSta = AutoStartPermissionHelper.Companion.getInstance().getAutoStartPermission(mContext, true, false);
            sessionParam.setAutoStart(mContext, true);
        });

        Log.d("check status",deviceStatus+"");
        if (sessionParam.isSubscribeNoti){
            switch_notification.setChecked(true);
        }else {
            switch_notification.setChecked(false);
        }
        iv_top_back.setOnClickListener(v->{
            startActivity(new Intent(mContext, Act_ProfileUser.class));
        });
        tribe365.setOnClickListener(v->{
            callHomeAct(mContext);
        });
        link_tv.setOnClickListener(v ->{
            openWebPage("https://www.makeuseof.com/tag/android-notification-fixes");
        });
        expand_image.setOnClickListener(v -> {
            if (!flag){
                expand_note_ll.setVisibility(View.VISIBLE);
                expand_image.setBackground(mContext.getResources().getDrawable(R.drawable.ic_expand_less_24));
                flag = true;
            }else {
                expand_note_ll.setVisibility(View.GONE);
                expand_image.setBackground(mContext.getResources().getDrawable(R.drawable.ic_expand_more_24));
                flag = false;
            }
        });
        open_setting_tv.setOnClickListener(v ->{
            goToNotificationSettings(getString(R.string.default_notification_channel_id), mContext);
        });
    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                return false;
            }
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    return false;
                }
            }
            return true;
        } else {
            return NotificationManagerCompat.from(mContext).areNotificationsEnabled();
        }
    }

    public void setSessionParam(){
        try {
            Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openNotificationSettingsForApp(String channelId) {
        // Links to this app's notification settings.
       /* Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && channelId!=null){
            intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_CHANNEL_ID,channelId);
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        }
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);
        startActivity(intent);*/
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O && channelId != null) {
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
                //intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("app_package",getPackageName());
                intent.putExtra("app_uid",getApplicationInfo().uid);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {

                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", mContext.getApplicationInfo().uid);

            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));

            } else {
                return;
            }
            //intent.putExtra("app_package",getPackageName());
            //intent.putExtra("app_uid",getApplicationInfo().uid);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /***
     * **/
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            //Page not found
            utility.showToast(mContext,"page not found");
        }
    }
    /******************************* open Notification settings ****************/
    public void goToNotificationSettings(String channel, Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            if (channel != null) {
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
            } else {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (channel != null) {
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
            } else {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        context.startActivity(intent);
    }

    /************************** Notification Api Call **********/
    public void updateNotificationStatus(boolean status, boolean deviceValue){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    //JSONArray jsonArray = new JSONArray(object.toString());
                    JSONObject jsonObject= new JSONObject(object.toString());
                    String notiStatus= jsonObject.getString("notificationPush");
                    Log.d("NotificationStatus",notiStatus);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        int statusValue=0;
        if (status){
            statusValue=1;
        }else {
            statusValue=0;
        }
        //0: not enable , 1: enable

        int deviceStatus=0;
        if (deviceValue){
            deviceStatus= 1;
        }else{
            deviceStatus=0;
        }
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id, "appStatus", statusValue+"","deviceStatus", deviceStatus+"");
        baseRequest.callAPIPostWOLoader(1,object, ConstantAPI.api_updatePushNotificationStatus);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }

}