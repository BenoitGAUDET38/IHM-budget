package com.example.fineance.model.notifications.notificationsFactories;

import android.content.Context;
import android.content.res.Resources;

import androidx.core.app.NotificationCompat;

import com.example.fineance.model.notifications.BasicNotification;
import com.example.fineance.model.notifications.ImageNotification;

public class DefaultPriorityNotificationFactory extends AbstractNotificationFactory {
    @Override
    public BasicNotification buildBasicNotification(Context applicationContext, Context currentContext, String title, String message) {
        return new BasicNotification(applicationContext, currentContext, NotificationCompat.PRIORITY_DEFAULT, title, message);
    }

    @Override
    public ImageNotification buildImageNotification(Context applicationContext, Context currentContext, Resources resources, String title, String message) {
        return new ImageNotification(applicationContext, currentContext, resources, NotificationCompat.PRIORITY_DEFAULT, title, message);
    }

}
