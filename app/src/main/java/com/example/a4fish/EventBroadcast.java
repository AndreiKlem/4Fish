package com.example.a4fish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class EventBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "4Fish")
                .setSmallIcon(R.drawable.ic_launcher_fish)
                .setContentTitle(intent.getStringExtra("event_title"))
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100, builder.build());
    }
}
