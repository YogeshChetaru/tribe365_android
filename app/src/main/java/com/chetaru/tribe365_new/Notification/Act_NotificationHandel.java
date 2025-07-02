package com.chetaru.tribe365_new.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.RemoteInput;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

public class Act_NotificationHandel extends BaseActivity {

    private static final int notificationId = 101;
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    String channelId = "";
    NotificationManager notificationManager;

    SessionParam sessionParam;
    Utility utility;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__notification_handel);
        utility = new Utility();
        handleIntentAct();
    }

    private void handleIntentAct() {

        Intent intent = this.getIntent();

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {

            TextView myTextView = findViewById(R.id.replyMessage);
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();


            if (inputString.contains(new String(Character.toChars(0x1F60A)))) {
                //happy index
                // api_addHappyIndex("3");
                utility.showToast(mContext, "user Happy");
            } else if (inputString.contains(new String(Character.toChars(0x1F610)))) {
                //nutral index
                // api_addHappyIndex("2");
                utility.showToast(mContext, "user Neutral");
            } else if (inputString.contains(new String(Character.toChars(0x1F61F)))) {
                //sad index
                // api_addHappyIndex("1");
                utility.showToast(mContext, "user Sad");
            }
            myTextView.setText(inputString);

            Notification repliedNotification =
                    null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                repliedNotification = new Notification.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(
                                R.drawable.ic_noti_icon)
                        .setContentText("Reply received")
                        .build();
            }

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId,
                    repliedNotification);
        }
    }

    public void api_addHappyIndex(String status) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    String timeDate = utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext, timeDate, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                try {
                    utility.showToast(mContext, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id, "moodStatus", status);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_addHappyIndex);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_home_land;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}