package com.yashk2000.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {

    NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        createNotificationChannel();
        PendingIntent contentPendingIntent;
        contentPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_job_running)
                        .setContentTitle("Job Service")
                        .setContentText("Your Job is running!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(contentPendingIntent);
        mNotifyManager.notify(0, builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    public void createNotificationChannel() {

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
