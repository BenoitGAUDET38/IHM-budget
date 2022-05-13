package com.example.fineance.notifications;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

public class ImageNotification extends Notification {
    public ImageNotification(Context context, Resources resources, int idImage, int priority, String title, String message) {
        super(context, priority, title, message);
        this.notification.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, idImage)));
    }
}
