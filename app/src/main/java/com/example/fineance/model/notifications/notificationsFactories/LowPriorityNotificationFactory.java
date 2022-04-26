package com.example.fineance.model.notifications.notificationsFactories;

import android.content.Context;
import android.content.res.Resources;

import androidx.core.app.NotificationCompat;

import com.example.fineance.R;
import com.example.fineance.model.notifications.BasicNotification;
import com.example.fineance.model.notifications.ImageNotification;

public class LowPriorityNotificationFactory extends AbstractNotificationFactory {
    @Override
    public BasicNotification buildBasicNotification(Context context, String title, String message) {
        return new BasicNotification(context, NotificationCompat.PRIORITY_LOW, title, message);
    }

    @Override
    public ImageNotification buildImageNotification(Context context, Resources resources, int idImage, String title, String message) {
        return new ImageNotification(context, resources, idImage, NotificationCompat.PRIORITY_LOW, title, message);
    }
}
