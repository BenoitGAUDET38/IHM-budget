package com.example.fineance.notifications.notificationsFactories;

import android.content.Context;
import android.content.res.Resources;

import com.example.fineance.R;
import com.example.fineance.notifications.BasicNotification;
import com.example.fineance.notifications.ImageNotification;

public abstract class AbstractNotificationFactory {
    public static int DEPENSE_IMG = R.drawable.depense;
    public static int LIMITE_IMG = R.drawable.limite;
    public static int ALARME_IMG = R.drawable.alarm;

    public abstract BasicNotification buildBasicNotification(Context context, String title, String message);

    public abstract ImageNotification buildImageNotification(Context context, Resources resources, int idImage, String title, String message);
}
