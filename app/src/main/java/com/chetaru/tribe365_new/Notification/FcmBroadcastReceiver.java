package com.chetaru.tribe365_new.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import static com.chetaru.tribe365_new.Notification.MyFirebaseMessagingService.ACTION_REPLY_TAPPED;
import static com.chetaru.tribe365_new.Notification.MyFirebaseMessagingService.REPLY_ACTION;

public class FcmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastRecevier";
    private static final int notificationId = 101;
    private static String KEY_TEXT_REPLY = "key_text_reply";
    private static String KEY_NOTIFICATION_ID = "key_noticiation_id";

    private static final int replyNotificationId=102;
    private static String KEY_OFFLOADING_REPLY="Key_noti_reply";
    public static String KEY_TEXT_REPLY_sceond = "key_text_reply_sceond";
    private static String KEY_OFF_NOTIFICATION_ID="key_notification_id";


    private static RemoteViews contentView;
    private static Notification notification;
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder mBuilder;
    Bitmap bitmap = null;
    NotificationCompat.Builder notificationBuilder;
    SessionParam sessionParam;
    Utility utility;
    String title = "";
    String body = "";
    String icon = "";
    Context mContext;
    private NotificationLostCallback mListener;
    private boolean check = false;

    public FcmBroadcastReceiver() {
    }

    public FcmBroadcastReceiver(NotificationLostCallback mListener) {
        super();
        this.mListener = mListener;
    }

    public static Intent getReplyMessageIntent(Context context, int notificationId, int KeyReply) {
        Intent intent = new Intent(context, FcmBroadcastReceiver.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_NOTIFICATION_ID, notificationId);
        intent.putExtra(KEY_TEXT_REPLY, KeyReply);
        return intent;
    }
    public static Intent getReplyOffloadingIntent(Context context, int notificationId, int KeyReply) {
        Intent intent = new Intent(context, FcmBroadcastReceiver.class);
        intent.setAction(ACTION_REPLY_TAPPED);
        intent.putExtra(KEY_OFF_NOTIFICATION_ID, notificationId);
        intent.putExtra(KEY_OFFLOADING_REPLY, KeyReply);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            this.mContext = context;
            sessionParam = new SessionParam(context);
            utility = new Utility();
            title = intent.getExtras().getString("title");
            body = intent.getExtras().getString("body");

            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            CharSequence message = null;
            //if (!check){
            if (REPLY_ACTION.equals(intent.getAction())) {
                message = MyFirebaseMessagingService.getReplyMessage(intent);
                String inputString = String.valueOf(message);
                //handel session date with time to open happy Index
                // if (!sessionParam.clickValue) {
                //  String kudosDate = utility.getTodayDate();
                //  if (!sessionParam.todayDate.equals(kudosDate)) {
                if (inputString.contains(new String(Character.toChars(0x1F60A)))) {
                    //happy index
                    api_addHappyIndex("3");
                    //utility.showToast(mContext,"user Happy");
                } else if (inputString.contains(new String(Character.toChars(0x1F610)))) {
                    //nutral index
                    api_addHappyIndex("2");
                    //utility.showToast(mContext,"user Neutral");
                } else if (inputString.contains(new String(Character.toChars(0x1F61F)))) {
                    //sad index
                    api_addHappyIndex("1");
                    //utility.showToast(mContext,"user Sad");
                } else {
                    api_addHappyIndex("3");
                }
            Log.d(TAG, "data: " + "title: " + message + " " + "Body: " + body + " " + "icon: " + "icon" + " ");
        }

            if (ACTION_REPLY_TAPPED.equals(intent.getAction())){

                Bundle remoteInputOffloading = android.app.RemoteInput.getResultsFromIntent(intent);

                if (remoteInputOffloading != null) {
                    String inputString="";
                    try {
                        inputString = remoteInputOffloading.getCharSequence(
                                KEY_TEXT_REPLY_sceond).toString();
                    }catch (Exception e){
                        e.printStackTrace();

                    }
                    sendOffloading(inputString);
                   // utility.showToast(mContext,inputString);

                }
            }
        }


    }

    public void api_addHappyIndex(String status) {
        BaseRequest baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {


                    String timeDate = utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext, timeDate, true);
                    String msg = null;
                    try {
                        JSONObject obj = new JSONObject(Json);
                        msg = obj.optString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (msg != null && !msg.equals("")) {
                        removeNotification();

                    } else {
                        replyNotification();
                    }
                    Log.d("BroadcastRecevier", "message status: " + status.toString());
                    //check=true;
                    try {
                        Intent myIntent = new Intent("Notification");
                        myIntent.putExtra("action", "Reply");
                        myIntent.putExtra("actionReply", "actionReply");
                        mContext.sendBroadcast(myIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                try {
                    utility.showToast(mContext, message);
                    replyErrorNotification("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
                replyErrorNotification(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id, "moodStatus", status);
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.api_addHappyIndex);
    }

    public void replyErrorNotification(String message) {
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification repliedNotification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            repliedNotification = new Notification.Builder(mContext, mContext.getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.drawable.ic_noti_icon)
                    .setContentText(message)
                    .build();
        }
        //for notify on notification bar
        notificationManager.notify(notificationId, repliedNotification);
        Handler h = new Handler();
        long delayInMilliseconds = 3000;
        h.postDelayed(new Runnable() {
            public void run() {
                notificationManager.cancel(notificationId);
            }
        }, delayInMilliseconds);
    }


    public void replyNotification() {
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification repliedNotification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            repliedNotification = new Notification.Builder(mContext, mContext.getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.drawable.ic_noti_icon)
                    .setContentText("Response received.")
                    .build();
        }
        //for notify on notification bar
        notificationManager.notify(notificationId, repliedNotification);

        try {
            Intent myIntent = new Intent("Notification");
            myIntent.putExtra("action", "noti");
            mContext.sendBroadcast(myIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //for remove Notification on top
      /* Handler h = new Handler() ;
       long delayInMilliseconds = 4000 ;
       h.postDelayed( new Runnable() {
           public void run () {
               notificationManager .cancel( notificationId ) ;
           }
       } , delayInMilliseconds) ;*/
    }

    public void removeNotification() {
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification repliedNotification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            repliedNotification = new Notification.Builder(mContext, mContext.getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.drawable.ic_noti_icon)
                    .setContentText("Response already received.")
                    .build();
        }
        //for notify on notification bar
        notificationManager.notify(notificationId, repliedNotification);


        //for remove Notification on top
        Handler h = new Handler();
        long delayInMilliseconds = 3000;
        h.postDelayed(new Runnable() {
            public void run() {
                notificationManager.cancel(notificationId);
            }
        }, delayInMilliseconds);
    }

    public void sendOffloading(String msgString){
        BaseRequest baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    JSONObject jsonObject=new JSONObject(Json);
                    String msg=jsonObject.getString("message");
                    utility.showToast(mContext,msg);
                    offloadingReply();
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

        JsonObject object=Functions.getClient().getJsonMapObject("message",msgString,
                "userId", sessionParam.id,
                "orgId",sessionParam.orgId,
                "image","");
        baseRequest.callAPIPost(1,object,ConstantAPI.postFeedback);

    }
    public void offloadingReply(){
        Notification repliedNotification =
                new Notification.Builder(mContext, mContext.getString(R.string.default_notification_channel_id))
                        .setSmallIcon(
                                R.drawable.app_icon)
                        .setContentText("Offloading submitted")
                        .build();
        notificationManager=(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(replyNotificationId, repliedNotification);
    }


}
