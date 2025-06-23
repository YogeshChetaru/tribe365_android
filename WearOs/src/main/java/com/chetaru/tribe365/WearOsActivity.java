package com.chetaru.tribe365_new;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class WearOsActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_os);


        // Enables Always-on
        setAmbientEnabled();

        Button button_respond = (Button)findViewById(R.id.button_respond);
        button_respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int notifyId = 1005;
                String channelId = getString(R.string.default_notification_channel_id);
                final String VOICE_RESPONSE_MY_APP = "voice_reply_my_app";
                String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
                RemoteInput ri = new RemoteInput.Builder(VOICE_RESPONSE_MY_APP)
                        .setLabel("Feedback")
                        .setChoices(replyChoices)
                        .build();

                Intent i = new Intent(Intent.ACTION_VIEW);
               /* i.setData(Uri.parse("geo:0,0?q=" +
                        Uri.encode("Google Headquarters")));*/
                i.putExtra("text", "How's things at work today?");
                PendingIntent pi = PendingIntent.getActivity(WearOsActivity.this, 0, i, 0);

                NotificationCompat.Action a =
                        new NotificationCompat.Action.Builder(R.drawable.open_on_phone_animation,
                                "Respond", pi)
                                .addRemoteInput(ri)
                                .build();

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(WearOsActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Feedback")
                                .setContentText("How's things at work today?")
                                .setContentIntent(pi)
                                .setLargeIcon(BitmapFactory.decodeResource(
                                        getResources(), R.drawable.ic_launcher_foreground));

                NotificationCompat.WearableExtender extender =
                        new NotificationCompat.WearableExtender();
                extender.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground, "do something",
                        pi).build());
                notificationBuilder.extend(extender);
                                //.extend(new NotificationCompat.WearableExtender().addAction(a));


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(channelId, "Your Notifications",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("How's things at work today?");
                    // notificationChannel.enableLights(true);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                //notificationManager.notify(notifyId, notificationBuilder.build());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel title",
                            NotificationManager.IMPORTANCE_MIN);
                    //code Added on 10 march
                    notificationBuilder.setChannelId(channelId);

                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0, notificationBuilder.build());
            }
        });
    }
}