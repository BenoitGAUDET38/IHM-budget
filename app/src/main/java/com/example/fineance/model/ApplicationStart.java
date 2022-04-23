package com.example.fineance.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;

public class ApplicationStart extends Application {
    public static final String LOW_CHANNEL_ID = "low_priority";
    public static final String DEFAULT_CHANNEL_ID = "default_priority";
    public static final String HIGH_CHANNEL_ID = "high_priority";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        // for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelLow = new NotificationChannel(LOW_CHANNEL_ID, "low priority channel",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationChannel channelDefault = new NotificationChannel(DEFAULT_CHANNEL_ID, "default priority channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelHigh = new NotificationChannel(HIGH_CHANNEL_ID, "high priority channel",
                    NotificationManager.IMPORTANCE_HIGH);

            channelLow.setDescription("This channel has a LOW priority");
            channelDefault.setDescription("This channel has a DEFAULT priority");
            channelHigh.setDescription("This channel has a HIGH priority");

            // cannot be changed after
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channelLow);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channelDefault);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channelHigh);
        }
    }
}