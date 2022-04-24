package com.example.fineance.model.notifications;

import android.content.Context;

public class BasicNotification extends Notification {
    public BasicNotification(Context applicationContext, Context currentContext, int priority, String title, String message) {
        super(applicationContext, currentContext, priority, title, message);
    }
}
