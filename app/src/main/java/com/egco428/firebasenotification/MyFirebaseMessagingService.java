package com.egco428.firebasenotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 6272user on 11/24/2016 AD.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        for (String k : remoteMessage.getData().keySet()) {
            String s = remoteMessage.getData().get(k);
            Log.i("Result", "onMessageReceived" + s);
        }
        if (remoteMessage.getNotification() != null) {
            String s = remoteMessage.getNotification().getBody();
            Log.i("Result", "onMessageReceived" + s);
        }

        Intent intent_call = new Intent(MyFirebaseMessagingService.this,CallActivity.class);
        Intent intent_setting = new Intent(MyFirebaseMessagingService.this,SettingActivity.class);
        Intent intent_warning  = new Intent(MyFirebaseMessagingService.this,WarningActivity.class);
        PendingIntent callIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, (int) System.currentTimeMillis(), intent_call, 0);
        PendingIntent settingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, (int) System.currentTimeMillis(), intent_setting, 0);
        PendingIntent warningIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, (int) System.currentTimeMillis(), intent_warning, 0);

        NotificationCompat.Action callAction = new NotificationCompat.Action.Builder(R.drawable.ic_call, "Call", callIntent).build();
        NotificationCompat.Action settingAction = new NotificationCompat.Action.Builder(R.drawable.ic_setting, "Setting", settingIntent).build();
        NotificationCompat.Action warningAction = new NotificationCompat.Action.Builder(R.drawable.ic_warning, "Warning", warningIntent).build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
        builder.setSmallIcon(R.drawable.ic_message);
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.addAction(callAction);
        builder.addAction(settingAction);
        builder.addAction(warningAction);
        builder.setWhen(System.currentTimeMillis() + 10);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

}


