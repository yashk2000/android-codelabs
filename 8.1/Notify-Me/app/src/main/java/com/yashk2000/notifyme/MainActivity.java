package com.yashk2000.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifiyManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String ACTION_UPDATE_NOTIFICATION = "com.yashk2000.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_DELETE_NOTIFICATION = "com.yashk2000.android.notifyme.ACTION_DELETE_NOTIFICATION";
    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotifiacation();
            }
        });
        createNotificatioChannel();
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });
        setNotificationButtonState(true, false, false);
        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));
        registerReceiver(mReceiver,new IntentFilter(ACTION_DELETE_NOTIFICATION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void createNotificatioChannel() {
        mNotifiyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notificationn", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from mascot");
            mNotifiyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationMethod() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(getDeleteIntent());
        return notifyBuilder;
    }

    protected PendingIntent getDeleteIntent()
    {
        Intent intent = new Intent(ACTION_DELETE_NOTIFICATION);
        PendingIntent pi = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pi;
    }

    public void sendNotifiacation() {

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationMethod();
        mNotifiyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        setNotificationButtonState(false, true, true);

    }

    public void updateNotification() {

        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationMethod();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"));
        mNotifiyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void cancelNotification() {
        mNotifiyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    void setNotificationButtonState(boolean isNotifyEnabled, boolean isUpdateEnabled, boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.equals("com.yashk2000.android.notifyme.ACTION_UPDATE_NOTIFICATION"))
            updateNotification();
            else cancelNotification();
        }
    }
}
