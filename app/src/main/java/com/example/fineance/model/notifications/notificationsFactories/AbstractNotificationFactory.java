package com.example.fineance.model.notifications.notificationsFactories;

import android.content.Context;
import android.content.res.Resources;

import com.example.fineance.model.notifications.BasicNotification;
import com.example.fineance.model.notifications.ImageNotification;

public abstract class AbstractNotificationFactory {
    public abstract BasicNotification buildBasicNotification(Context applicationContext, Context currentContext, String title, String message);
    public abstract ImageNotification buildImageNotification(Context applicationContext, Context currentContext, Resources resources, String title, String message);
}
