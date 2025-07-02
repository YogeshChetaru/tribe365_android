package com.chetaru.tribe365_new.Notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.chetaru.tribe365_new.BuildConfig;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /** user for Remote View Notificaiton *****/
    private static final int NOTIF_ID = 1234;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;

    String changeit_id = "";
    String body = "";
    String title = "";
    String icon = "";
    String que = "";
    String notificationType = "";
    Context context;
    SessionParam sessionParam;
    BaseActivity baseActivity;
    NotificationCompat.Builder notificationBuilder;
    NotificationChannel Channel;
    NotificationManager notificationManager;
    String channelId = "";
    //handel param notification on string fields
    //for handel kudos date
    String currentDate = "";
    //for handel feeedback and support idsa
    String feedbackId = "", supportId = "",teamId="";
    //handel notification on custom view
    String noti_date = null, noti_link = null;
    SharedPreferences.Editor editor;
    String sound, readNotificationId = null;


//    public MyFirebaseMessagingService (Context context){
//        this.context = context;
//        sessionParam = new SessionParam(context);
//    }

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
    // [END receive_message]

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.describeContents());
        }

        // Check if message contains a notification payload.
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


        /* try {
            JSONObject json = new JSONObject(remoteMessage.getData().toString());
            //sendPushNotification(json);
           String body = remoteMessage.getData().get("body");
            String title = remoteMessage.getData().get("title");

            Log.d(TAG,"dataJson: "+json);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }*/

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            body = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();

            try {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().toString());
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody().toString());
                icon = remoteMessage.getNotification().getIcon();
                if (!icon.isEmpty()){
                    Log.d(TAG, "Message image Body: " + icon.toString());
                }
                //sendNotification(body,title);
            }catch (Exception e){
                e.printStackTrace();]
            }*/


        //sendNotification(body, title);
        // NotificationForBoth();



        try {
            sessionParam= new SessionParam(getApplicationContext());

            if (sessionParam.isSubscribeNoti) {

                if (notificationType.contains("happyIndexPush")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sendNotification(body, title);
                        //getChoiceNotification(title,body);
                        //sendReplyNotification(body,title);
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

         /* if (title.contains("Feedback")) {
                sendNotification(body, title);
            }else{
                sendKudosNotification(notificationType,title,icon);
            }*/

    }


    // [START on_new_token]
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SessionParam sessionParam = new SessionParam(this);
        sessionParam.saveFCM_Token(this, s);
        Log.e("NEW_TOKEN", s);
    }



    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }



    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String messageBody, String title) {
        Intent intent;
        sessionParam = new SessionParam(getApplicationContext());
        channelId = getString(R.string.default_notification_channel_id);


        intent = new Intent(this, Act_Splash.class)
                .putExtra("path", "noti")
                .putExtra("type", notificationType)
                .setAction(Long.toString(System.currentTimeMillis()));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /**if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
         }
         else { PendingIntent.FLAG_UPDATE_CURRENT
         }*/
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
        );
        // Build a PendingIntent for the reply action to trigger.
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(),
                        100,
                        FcmBroadcastReceiver.getReplyMessageIntent(this, notificationId, 123),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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

        /* Here we instantiate the Intent we want to use when the action in the smartwatch is pressed */
        //Intent resultIntent = new Intent(this, Act_Home.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
       // stackBuilder.addParentStack(Act_Home.class);
        // Adds the Intent that starts the Activity to the top of the stack
        //stackBuilder.addNextIntent(resultIntent);
       /* PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_CANCEL_CURRENT
        );*/

        NotificationCompat.Action a =
                new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red,
                        "", getReplyPendingIntent())
                        .addRemoteInput(ri)
                        //.setAllowGeneratedReplies(true)  // <--- true to enable smart replies
                        // Wear OS requires a hint to display the reply action inline.
                        /*.extend(new NotificationCompat.Action.WearableExtender()
                                .setHintDisplayActionInline(true))*/
                        .build();

        Uri defaultSoundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        // Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notification);


        //remoteView for show remote option on a view
        // RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.dialog_feedback_hows_your_day);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.ic_noti_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                        // .setLargeIcon( getBitmap(icon))
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

                        //.setLocalOnly(false)
                        .addAction(a);
                        //.setAllowSystemGeneratedContextualActions(true)
                       /* .extend(new NotificationCompat.WearableExtender()
                                .setContentAction(0))*/
                        //.extend(new NotificationCompat.WearableExtender().setBridgeTag("Tribe_Notifications"))
                        //.extend(new NotificationCompat.WearableExtender().addAction(a));



        NotificationCompat.WearableExtender extender =
                new NotificationCompat.WearableExtender();
        extender.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red, "send" ,
                getReplyPendingIntent())
                .addRemoteInput(ri).build());
        extender.extend(notificationBuilder);
        notificationBuilder.extend(extender);

        //Wear OS requires a hint to display the reply action inline.
        /*NotificationCompat.Action.WearableExtender actionExtender =
                new NotificationCompat.Action.WearableExtender()
                        .setHintLaunchesActivity(true)
                        .setHintDisplayActionInline(true);
        notificationBuilder.addAction(noti.extend(actionExtender).build());*/


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
            // notificationChannel.enableLights(true);
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
                //intent = new Intent(this, Act_Report.class);
                /*intent = new Intent(this, Act_Report.class);
                intent.putExtra("date", currentDate);
                intent.putExtra("backHandel","homeBack");
                intent.putExtra("readNotificationId", readNotificationId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
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

        // Create an Intent for the activity you want to start
        //Intent resultIntent = new Intent(this, ResultActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        /*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
                    // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);*/


        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       /* Uri sound = Uri.parse("android.resource://"
                + this.getPackageName() + "/"
                + R.raw.noti_tone);*/
        final Uri NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +  BuildConfig.APPLICATION_ID + "/" + R.raw.notification);

        // playNotificationSound();


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
            // notificationChannel.enableLights(true);
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

        //Since android Oreo notification channel is needed.
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sessionParam = new SessionParam(getApplicationContext());
           *//* if(title.contains("Feedback")){
                Intent myIntent = new Intent("Notification");
                myIntent.putExtra("action",notificationType);
                this.sendBroadcast(myIntent);
            }*//*
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel title",
                    NotificationManager.IMPORTANCE_MIN);
            //code Added on 10 march
            notificationBuilder.setChannelId(channelId);
            notificationManager.createNotificationChannel(channel);
        }*/
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        notificationManager.notify(m /* ID of notification */, notificationBuilder.build());

    }

    public void playNotificationSound() {
        try {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, notification);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            //image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void removeFirebaseOrigianlNotificaitons() {

        //check notificationManager is available
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null)
            return;

        //check api level for getActiveNotifications()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //if your Build version is less than android 6.0
            //we can remove all notifications instead.
            //notificationManager.cancelAll();
            return;
        }


        //check there are notifications
        StatusBarNotification[] activeNotifications =
                notificationManager.getActiveNotifications();
        if (activeNotifications == null)
            return;

        //remove all notification created by library(super.handleIntent(intent))
        for (StatusBarNotification tmp : activeNotifications) {
            Log.d("FCM StatusBarNoti",
                    "StatusBarNotification tag/id: " + tmp.getTag() + " / " + tmp.getId());
            String tag = tmp.getTag();
            int id = tmp.getId();

            //trace the library source code, follow the rule to remove it.
            if (tag != null && tag.contains("FCM-Notification"))
                notificationManager.cancel(tag, id);
        }
    }



    @SuppressLint("LaunchActivityFromNotification")
    private PendingIntent getReplyPendingIntent() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // start a
            // (i)  broadcast receiver which runs on the UI thread or
            // (ii) service for a background task to b executed , but for the purpose of this codelab, will be doing a broadcast receiver
            intent = FcmBroadcastReceiver.getReplyMessageIntent(this, notificationId, 123);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                    PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT );
        } else {
            // start your activity
            intent = Act_Home.getReplyMessageIntent(this, notificationId, 123);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_MUTABLE );
        }
    }
    private PendingIntent getReplyPendingIntentOff() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // start a
            // (i)  broadcast receiver which runs on the UI thread or
            // (ii) service for a background task to b executed , but for the purpose of this codelab, will be doing a broadcast receiver
            intent = FcmBroadcastReceiver.getReplyOffloadingIntent(this, offloading_notificationId, 321);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            // start your activity
           // intent = Act_Home.getReplyMessageIntent(this, offloading_notificationId, 123);
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
                   // .addAction(a)
                    //.extend(new NotificationCompat.WearableExtender().addAction(replyAction))
                    .addAction(replyAction).build();
        }

        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(offloading_notificationId,
                newMessageNotification);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void getChoiceNotification(String title, String body){
        sessionParam = new SessionParam(getApplicationContext());
        channelId = getString(R.string.default_notification_channel_id);

        Intent notifyIntent= new Intent(this,Act_Home.class);
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(Act_Home.class);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(notifyIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent mainPendingIntent= PendingIntent.getActivity(this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);

        RemoteInput ri = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(body)
                // Use machine learning to create responses based on previous messages.
                .setChoices(replyChoices)
                .build();

        // Pending intent =
        //      API <24 (M and below): activity so the lock-screen presents the auth challenge.
        //      API 24+ (N and above): this should be a Service or BroadcastReceiver.
        PendingIntent replyActionPendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(this, FcmBroadcastReceiver.class);
            intent.setAction(REPLY_ACTION);
            replyActionPendingIntent = PendingIntent.getService(this, 0, intent, 0);

        } else {
            replyActionPendingIntent = mainPendingIntent;
        }

        NotificationCompat.Action a =
                new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red,
                        null, replyActionPendingIntent)
                        .addRemoteInput(ri)
                        // Informs system we aren't bringing up our own custom UI for a reply
                        // action.
                        .setShowsUserInterface(false)
                        //Allow system to generate replies by context of conversations
                        .setAllowGeneratedReplies(false)  // <--- true to enable smart replies
                        .setSemanticAction(NotificationCompat.Action.SEMANTIC_ACTION_REPLY)
                        .build();

        Uri defaultSoundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_noti_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(getReplyPendingIntent())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .addAction(a)
                        .setCategory(Notification.CATEGORY_MESSAGE);
                        //.setAllowSystemGeneratedContextualActions(true)
                        /* .extend(new NotificationCompat.WearableExtender()
                                 .setContentAction(0))*/
                        //.extend(new NotificationCompat.WearableExtender().setBridgeTag("Tribe_Notifications"))
                        //.extend(new NotificationCompat.WearableExtender().addAction(a));



        NotificationCompat.WearableExtender extender =
                new NotificationCompat.WearableExtender();
        extender.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_send_arraow_red, null ,
                getReplyPendingIntent())
                .addRemoteInput(ri).build());
        notificationBuilder.extend(extender);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Tribe_Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(body);
            // notificationChannel.enableLights(true);
            if (defaultSoundUri != null) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();
                notificationChannel.setSound(defaultSoundUri, audioAttributes);
            }
          //  notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification= notificationBuilder.build();
        /*sessionParam = new SessionParam(getApplicationContext());
        if (title.contains("Feedback")) {
            Intent myIntent = new Intent("Notification");
            myIntent.putExtra("action", "noti");
            this.sendBroadcast(myIntent);
        }*/
        notificationManager.notify(notificationId /* ID of notification */, notification);

    }


    // call this method to setup notification for the first time
    private void setUpNotification(String title, String body){

         mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // we need to build a basic notification first, then update it
        Intent intentNotif = new Intent(this, Act_Home.class);
        intentNotif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);

        // notification's layout
          mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        // notification's icon
        mRemoteViews.setImageViewResource(R.id.notif_icon, R.drawable.ic_launcher);
        // notification's title
        mRemoteViews.setTextViewText(R.id.notif_title, title);
        // notification's content
        mRemoteViews.setTextViewText(R.id.notif_content, body);

        mBuilder = new NotificationCompat.Builder(this);

        CharSequence ticker = getResources().getString(R.string.label);
        int apiVersion = Build.VERSION.SDK_INT;

        if (apiVersion < Build.VERSION_CODES.HONEYCOMB) {
            mNotification = new Notification(R.drawable.ic_launcher, ticker, System.currentTimeMillis());
            mNotification.contentView = mRemoteViews;
            mNotification.contentIntent = pendIntent;

            mNotification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
            mNotification.defaults |= Notification.DEFAULT_LIGHTS;

            // starting service with notification in foreground mode
            startForeground(NOTIF_ID, mNotification);

        }else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            mBuilder.setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setContentIntent(pendIntent)
                    .setContent(mRemoteViews)
                    .setTicker(ticker);

            // starting service with notification in foreground mode
            startForeground(NOTIF_ID, mBuilder.build());
        }
    }
    // use this method to update the Notification's UI
    private void updateNotification(){

        int api = Build.VERSION.SDK_INT;
        // update the icon
        mRemoteViews.setImageViewResource(R.id.notif_icon, R.drawable.complete);
        // update the title
        mRemoteViews.setTextViewText(R.id.notif_title, "Feedback");
        // update the content
        mRemoteViews.setTextViewText(R.id.notif_content, "submit");

        // update the notification
        if (api < Build.VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(NOTIF_ID, mNotification);
        }else if (api >= Build.VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(NOTIF_ID, mBuilder.build());
        }
    }

}
