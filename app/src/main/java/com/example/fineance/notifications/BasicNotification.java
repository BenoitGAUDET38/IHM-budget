package com.example.fineance.notifications;

import android.content.Context;

public class BasicNotification extends Notification {
    public BasicNotification(Context context, int priority, String title, String message) {
        super(context, priority, title, message);
    }
}
