package com.example.fineance.model.notifications;

import static com.example.fineance.model.ApplicationStart.LOW_CHANNEL_ID;
import static com.example.fineance.model.ApplicationStart.DEFAULT_CHANNEL_ID;
import static com.example.fineance.model.ApplicationStart.HIGH_CHANNEL_ID;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fineance.R;

public abstract class Notification {
    protected static int notificationId = 0;
    protected Context currentContext;
    protected String channelId;
    protected NotificationCompat.Builder notification;

    public Notification(Context applicationContext, Context currentContext, int priority, String title, String message) {
        this.currentContext = currentContext;
        this.channelId = getChannelId(priority);
        this.notification = buildNotification(applicationContext, title, message, priority);
    }

    private NotificationCompat.Builder buildNotification(Context applicationContext, String title, String message, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle(title)
                .setContentText("id = " + notificationId + " - " + message)
                .setPriority(priority);

        switch (channelId) {
            case LOW_CHANNEL_ID:
                notification.setSmallIcon(R.drawable.ic_baseline_lightbulb_24); break;
            case DEFAULT_CHANNEL_ID:
                notification.setSmallIcon(R.drawable.ic_baseline_priority_high_24); break;
            case HIGH_CHANNEL_ID:
                notification.setSmallIcon(R.drawable.ic_baseline_warning_amber_24); break;
        }

        return notification;
    }

    public void sendNotificationOnChannel() {
        NotificationManagerCompat.from(currentContext).notify(notificationId, notification.build());
        notificationId++;
    }

    private String getChannelId(int priority) {
        switch (priority) {
            case NotificationCompat.PRIORITY_LOW:
                return LOW_CHANNEL_ID;
            case NotificationCompat.PRIORITY_HIGH:
                return HIGH_CHANNEL_ID;
            default:
                return DEFAULT_CHANNEL_ID;
        }
    }
}
