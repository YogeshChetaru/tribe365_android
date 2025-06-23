package com.chetaru.tribe365_new.Notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportListDetails;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Act_Splash;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_New_Question;
import com.chetaru.tribe365_new.UI.Know.DOT.Act_DOT_Details;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Diagnostics_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Tribeometer_list;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Personality_type_list;
import com.chetaru.tribe365_new.UI.Know.SOT.Act_SOT_Questionlist;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Question;
import com.chetaru.tribe365_new.UI.Offloading.Act_HistoryDetail;
import com.chetaru.tribe365_new.UI.Offloading.Act_Ref_HistoryDetail;
import com.chetaru.tribe365_new.UI.UserInfo.Act_TeamFeedback_Question;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String ACTION_REPLY_TAPPED =
            "com.chetaru.tribe365_new.ACTION_REPLY_TAPPED";
    private static final String TAG = "MyFirebaseMsgService";
    public static String REPLY_ACTION = "com.chetaru.tribe365_new.REPLY_ACTION";

    private static final int notificationId = 101;
    private static final int offloading_notificationId = 102;
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    public static String KEY_TEXT_REPLY_sceond = "key_text_reply_sceond";
    String changeit_id = "";
    String body = "";
    String title = "";
    String icon = "";
    String que = "";
    String notificationType = "";
    Context context;
    SessionParam sessionParam;

    NotificationManager notificationManager;
    String channelId = "";

    String currentDate = "";

    String feedbackId = "", supportId = "",teamId="";

    String noti_date = null, noti_link = null;
    SharedPreferences.Editor editor;
    String sound, readNotificationId = null;


    public static CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        int sourceInput = RemoteInput.getResultsSource(intent);
        RemoteInput.setResultsSource(intent, sourceInput);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.describeContents());
        }


        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size()>0) {
            remoteMessage.getData();
            Log.d(TAG, "Message  Body: " + remoteMessage.getData());
            changeit_id = remoteMessage.getData().get("android_channel_id");
            que = remoteMessage.getData().get("que");
            body = remoteMessage.getData().get("body");
            title = remoteMessage.getData().get("title");
            notificationType = remoteMessage.getData().get("notificationType");
            icon = remoteMessage.getData().get("icon");
            sound = remoteMessage.getData().get("sound");
            readNotificationId = remoteMessage.getData().get("notificationId");

            try {
                //handel notification on custom type param
                if (notificationType.equals("chat")) {
                    feedbackId = remoteMessage.getData().get("feedbackId");
                } else if (notificationType.equals("supportChat")) {
                    supportId = remoteMessage.getData().get("feedbackId");
                } else if (notificationType.equals("reflectionChat")) {
                    feedbackId = remoteMessage.getData().get("feedbackId");
                } else if (notificationType.equals("kudos")) {
                    currentDate = remoteMessage.getData().get("currentDate");
                } else if (notificationType.equals("custom_notification")) {
                    noti_date = remoteMessage.getData().get("date");
                    noti_link = remoteMessage.getData().get("link");
                } else if (notificationType.equals("teamFeedback")) {
                    feedbackId = remoteMessage.getData().get("userId");
                    noti_date = remoteMessage.getData().get("currentDate");
                    teamId = remoteMessage.getData().get("teamFeedbackId");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            body=remoteMessage.getNotification().getBody();
            title= remoteMessage.getNotification().getTitle();
        }


        try {
            sessionParam= new SessionParam(getApplicationContext());

            if (sessionParam.isSubscribeNoti) {

                if (notificationType.contains("happyIndexPush")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sendNotification(body, title);

                    }else {
                        sendKudosNotification(notificationType, title, body, icon, feedbackId, teamId, supportId, currentDate,
                                noti_date, noti_link);
                    }
                } else if(notificationType.contains("badDayOffload")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sendReplyNotification(body,title);
                    }else {
                        sendKudosNotification(notificationType, title, body, icon, feedbackId, teamId, supportId, currentDate,
                                noti_date, noti_link);
                    }
                } else {
                    sendKudosNotification(notificationType, title, body, icon, feedbackId, teamId, supportId, currentDate,
                            noti_date, noti_link);
                }
            }
        }catch (Exception e){
            if (sessionParam.isSubscribeNoti) {
                sendKudosNotification(notificationType, title, body, icon, feedbackId, teamId, supportId, currentDate,
                        noti_date, noti_link);
            }
            e.printStackTrace();
        }


    }


    // [START on_new_token]
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SessionParam sessionParam = new SessionParam(this);
        sessionParam.saveFCM_Token(this, s);
        Log.e("NEW_TOKEN", s);
    }



    private void sendNotification(String messageBody, String title) {
        Intent intent;
        sessionParam = new SessionParam(getApplicationContext());
        channelId = getString(R.string.default_notification_channel_id);


        intent = new Intent(this, Act_Splash.class)
                .putExtra("path", "noti")
                .putExtra("type", notificationType)
                .setAction(Long.toString(System.currentTimeMillis()));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
        );

        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
        int i;
//          Convert ASCII Emoji UNICODE.
//          For more Emojis, see http://apps.timwhitlock.info/emoji/tables/unicode.
        for (i = 0; i < replyChoices.length; i++) {
            if (replyChoices[i].contains("Yes")) replyChoices[i] =
                    replyChoices[i].replace("Yes", new String(
                            Character.toChars(0x1F60A)));
            //  replyChoices[i].replace("Yes","😊");
            if (replyChoices[i].contains("Maybe")) replyChoices[i] =
                    replyChoices[i].replace("Maybe", new String(
                            Character.toChars(0x1F610)));
            //replyChoices[i].replace("Maybe","😐");
            if (replyChoices[i].contains("No")) replyChoices[i] =
                    replyChoices[i].replace("No", new String(
                            Character.toChars(0x1F61F)));
            //replyChoices[i].replace("No","😟");

        }

        RemoteInput ri = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(body)
                .setChoices(replyChoices)
                //.setAllowDataType("body", false)
                .setAllowFreeFormInput(true)
                //.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_DISABLED)
                .setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_ENABLED)
                .build();



        NotificationCompat.Action a =
                new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red,
                        "", getReplyPendingIntent())
                        .addRemoteInput(ri)
                        .build();

        Uri defaultSoundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
         NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.ic_noti_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                        .setContentTitle(title)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setStyle(new NotificationCompat.InboxStyle())
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{200, 400, 600, 800})
                        .setOngoing(false)
                        .setGroup("GROUP")
                        .setGroupSummary(false)
                        .setContentIntent(notifyPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)


                        .addAction(a);



        NotificationCompat.WearableExtender extender =
                new NotificationCompat.WearableExtender();
        extender.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red, "send" ,
                getReplyPendingIntent())
                .addRemoteInput(ri).build());
        extender.extend(notificationBuilder);
        notificationBuilder.extend(extender);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Tribe_Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(messageBody);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{200, 400, 600, 800});
            notificationChannel.setLightColor(Color.RED);
            // notificationChannel.enableLights(true);
            if (defaultSoundUri != null) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();

                notificationChannel.setSound(defaultSoundUri, audioAttributes);
            }

            notificationManager.createNotificationChannel(notificationChannel);
        }

        sessionParam = new SessionParam(getApplicationContext());
        if (title.contains("Feedback")) {
            Intent myIntent = new Intent("Notification");
            myIntent.putExtra("action", "noti");
            this.sendBroadcast(myIntent);
        }
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());


    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */

    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendKudosNotification(String notificationType, String title, String body, String imageUrl
            , String feedbackId, String teamId, String supportId, String currentDate, String noti_date, String noti_link) {

        //notificationType, title, body,icon,feedback,currentDate,
        //                noti_date,noti_link
        Intent intentTask;
        sessionParam = new SessionParam(getApplicationContext());
        channelId = getString(R.string.default_notification_channel_id);

        Intent intent = null;
        //notificationType="customNotification";
        if (notificationType != null && !notificationType.equals("")) {
            if (notificationType.equals("chat")) {

                intent = new Intent(this, Act_HistoryDetail.class);
                // String feedbackId = getIntent().getStringExtra("feedbackId");
                intent.putExtra("changeItId", feedbackId);
                intent.putExtra("backHandel", "homeBack");
                intent.putExtra("readNotificationId", readNotificationId);


            }else if (notificationType.equals("badDayOffload")){
                if (sessionParam.loginVersion==3){
                    intent= new Intent(this,BasicHomeActivity.class);
                    intent.putExtra("notificationType",notificationType);
                }else {
                    intent = new Intent(this, Act_Home.class);
                    intent.putExtra("notificationType", notificationType);
                }
            }else if (notificationType.equals("supportChat")) {
                intent = new Intent(this, Act_SupportListDetails.class);
                //String feedbackId = getIntent().getStringExtra("supportId");
                intent.putExtra("changeItId", supportId);
                intent.putExtra("backHandel", true);
                intent.putExtra("readNotificationId", readNotificationId);

            }else if (notificationType.equals("reflectionChat")){
                intent =new Intent(this, Act_Ref_HistoryDetail.class);
                intent.putExtra("changeItId",feedbackId);
                intent.putExtra("backHandel",true);
                intent.putExtra("readNoticficationId",readNotificationId);
            }else if (notificationType.equals("teamFeedback")){
                intent=new Intent(this, Act_TeamFeedback_Question.class);
                intent.putExtra("userId",feedbackId);
                intent.putExtra("backHandel","HomeBack");
                intent.putExtra("teamId",teamId);
                intent.putExtra("date",noti_date);
                intent.putExtra("readNotificationId",notificationId);


            }else if (notificationType.equals("kudoAward")){
                if (sessionParam.loginVersion ==3 ){
                    intent = new Intent(this, BasicHomeActivity.class);
                    intent.putExtra("kudosClick", true);
                    intent.putExtra("readNotificationId", readNotificationId);
                }else {
                    intent = new Intent(this, Act_Home.class);
                    intent.putExtra("kudosClick", true);
                    intent.putExtra("readNotificationId", readNotificationId);
                }

            }else if (notificationType.equals("kudos")) {
                intent=new Intent(this, Act_Home.class);
                intent.putExtra("kudosClick",true);
                intent.putExtra("readNotificationId", readNotificationId);

                if (notificationType.equals("kudos")) {
                    Intent myIntent = new Intent("Notification");
                    myIntent.putExtra("action", "kudos");
                    this.sendBroadcast(myIntent);
                }

            } else if (notificationType.equals("custom_notification")) {
                intent = new Intent(this, Act_LinkNotification.class);
                intent.putExtra("noti_title", title);
                intent.putExtra("noti_desc", body);
                intent.putExtra("noti_link", noti_link);
                intent.putExtra("noti_date", noti_date);
                intent.putExtra("readNotificationId", readNotificationId);

            } else if (notificationType.equals("cultureStructure_reminder")) {
                intent = new Intent(this, Act_SOT_Questionlist.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");

            } else if (notificationType.equals("diagnostic_reminder")) {
                intent = new Intent(this, Act_Diagnostics_list.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");
            } else if (notificationType.equals("functionalLensReminder")) {
                intent = new Intent(this, Act_Home.class)
                        .putExtra("readNotificationId", readNotificationId);
            } else if (notificationType.equals("motivation_reminder")) {
                intent = new Intent(this, Act_SOT_Motivation_Question.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");
            } else if (notificationType.equals("teamRole_reminder")) {
                intent = new Intent(this, Cot_New_Question.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");
            } else if (notificationType.equals("dot_reminder")) {
                intent = new Intent(this, Act_DOT_Details.class)
                        .putExtra("backHandel", true)
                        .putExtra("path", "noti");
                editor = this.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                editor.putString("checklist", "checklist");
                editor.apply();
            } else if (notificationType.equals("tribeometer_reminder")) {
                intent = new Intent(this, Act_Tribeometer_list.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");
            } else if (notificationType.equals("personalityType_reminder")) {
                intent = new Intent(this, Act_Personality_type_list.class)
                        .putExtra("backHandel", true)
                        .putExtra("checklist", "checklist");
            } else {
                intent = new Intent(this, Act_Splash.class)
                        .putExtra("readNotificationId", readNotificationId);
            }
        } else {
            intent = new Intent(this, Act_Splash.class)
                    .putExtra("readNotificationId", readNotificationId);
        }


        int readId = 0;
        try {
            readId = Integer.parseInt(readNotificationId);
        } catch (Exception e) {
            e.printStackTrace();
            readId = 0;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(this, readId /*Requestcode*/, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE );
            }else {
                pendingIntent = PendingIntent.getActivity(this, readId /*Requestcode*/, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            }



        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder;
        notificationBuilder =
                new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setSmallIcon(R.drawable.ic_noti_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{200, 400, 600, 800})  //Vibration
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Tribe_Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(body);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{200, 400, 600, 800});
            notificationChannel.setLightColor(Color.RED);

            if (defaultSoundUri != null) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();

                notificationChannel.setSound(defaultSoundUri, audioAttributes);
            }
            notificationBuilder.setChannelId(channelId);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m , notificationBuilder.build());

    }




    @SuppressLint("LaunchActivityFromNotification")
    private PendingIntent getReplyPendingIntent() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
             intent = FcmBroadcastReceiver.getReplyMessageIntent(this, notificationId, 123);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                    PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT );
        } else {
            intent = Act_Home.getReplyMessageIntent(this, notificationId, 123);
            return PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_MUTABLE );
        }
    }
    private PendingIntent getReplyPendingIntentOff() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             intent = FcmBroadcastReceiver.getReplyOffloadingIntent(this, offloading_notificationId, 321);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            intent=Act_Home.getReplyMessageIntentOff(this,offloading_notificationId,123);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendReplyNotification(String body, String title) {

        String replyLabel = "Any bad things to highlight?";
        android.app.RemoteInput remoteInput =
                null;
        remoteInput = new android.app.RemoteInput.Builder(KEY_TEXT_REPLY_sceond)
                .setLabel(replyLabel)
                .build();
        Intent resultIntent= null;
        if (sessionParam.loginVersion ==3){
             resultIntent = new Intent(this, BasicHomeActivity.class);
            resultIntent.putExtra("reply", "sadReply");
        }else {
             resultIntent = new Intent(this, Act_Home.class);
            resultIntent.putExtra("reply", "sadReply");
        }


        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

         Icon icon = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            icon = Icon.createWithResource(getApplicationContext(),
                    android.R.drawable.ic_dialog_info);
        }


        Notification.Action replyAction =
                new Notification.Action.Builder(
                        icon,
                        "OFFLOAD", getReplyPendingIntentOff())
                        .addRemoteInput(remoteInput)
                        //.extend(new Notification.Action.WearableExtender().setHintDisplayActionInline())
                        .build();


        Notification newMessageNotification =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            newMessageNotification = new Notification.Builder(this, "questions")
                    .setColor(ContextCompat.getColor(this,
                            R.color.border_color))
                    .setSmallIcon(R.drawable.ic_noti_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(resultPendingIntent)
                    .setAllowSystemGeneratedContextualActions(true) //Determines whether the platform can generate contextual actions for a notification
                    .addAction(replyAction).build();
        }

        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(offloading_notificationId,
                newMessageNotification);
    }



}
