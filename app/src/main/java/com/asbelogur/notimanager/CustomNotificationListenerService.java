package com.asbelogur.notimanager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.asbelogur.notimanager.useful.CNLSHelper;

import java.text.SimpleDateFormat;
import java.util.Objects;


public class CustomNotificationListenerService extends NotificationListenerService {

    private static final String LOGTAG = "NotificationService";

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn){

        StatusBarNotification[] activeNotifications = this.getActiveNotifications();
        DatabaseHelper dbHelper = new DatabaseHelper(CustomNotificationListenerService.this);
        dbHelper.readNotifications();

        if(activeNotifications != null && activeNotifications.length > 0) {
            for (StatusBarNotification activeNotification : activeNotifications) {
                Bundle extras = sbn.getNotification().extras;
                Notification notification = sbn.getNotification();
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Icon largeIcon = notification.getLargeIcon();

                    if (largeIcon != null) {
                        largeIcon.loadDrawable(this);
                    }
                }

                 */

                String name = CNLSHelper.getAppNameFromPackage(this, sbn.getPackageName(), false);
                String user = extras.getString("android.title");
                String text = Objects.requireNonNull(extras.getCharSequence("android.text")).toString();


                String time = Long.toString(sbn.getPostTime());
                String chanelId = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    chanelId = notification.getChannelId();
                }

                assert chanelId != null;
                if (name != null && user != null && !chanelId.equals("VIRTUAL_KEYBOARD"))
                    dbHelper.addMessage(sbn.getPackageName(), name, user, text, time, chanelId, sbn.getGroupKey(), sbn.getId());

                String stringBuilder = "Catched notification: " + notification.toString();
                Log.i(LOGTAG, (String) stringBuilder);

                break;
            }
        }
    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        Log.i(LOGTAG, "Notification Removed");
    }

    /*
    public void saveLargeIconsToNotifications(StatusBarNotification statusBarNotification) {
        NotificationObject notificationObject = CommonMethods.getDataFromNotification(statusBarNotification, this.context);
        if (notificationObject.ifNotificationHasLargeIcon() && this.dbHelper.getAppIgnore(notificationObject.getPackageName()) != 1) {
            this.notificationObjects.put((Object)notificationObject.getPostTime(), (Object)notificationObject);
        }
    }

     */

}