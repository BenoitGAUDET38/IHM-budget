package com.example.fineance.model.notifications;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.example.fineance.R;

public class ImageNotification extends Notification {
    public ImageNotification(Context applicationContext, Context currentContext, Resources resources, int priority, String title, String message) {
        super(applicationContext, currentContext, priority, title, message);
        this.notification.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.alarm)));
    }
}
