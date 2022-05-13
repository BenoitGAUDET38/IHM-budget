package com.example.fineance.notifications;

import static com.example.fineance.notifications.ApplicationStart.LOW_CHANNEL_ID;
import static com.example.fineance.notifications.ApplicationStart.DEFAULT_CHANNEL_ID;
import static com.example.fineance.notifications.ApplicationStart.HIGH_CHANNEL_ID;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fineance.R;

public abstract class Notification {
    protected static int notificationId = 0;
    protected Context context;
    protected String channelId;
    protected NotificationCompat.Builder notification;

    public Notification(Context context, int priority, String title, String message) {
        this.context = context;
        this.channelId = getChannelId(priority);
        this.notification = buildNotification(context, title, message, priority);
    }

    private NotificationCompat.Builder buildNotification(Context context, String title, String message, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(message)
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
        NotificationManagerCompat.from(context).notify(notificationId, notification.build());
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
