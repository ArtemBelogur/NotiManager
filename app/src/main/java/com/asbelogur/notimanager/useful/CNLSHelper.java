package com.asbelogur.notimanager.useful;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;

import com.asbelogur.notimanager.BuildConfig;

import java.util.List;

public class CNLSHelper {
    public static String getAppNameFromPackage(Context context, String packageName, boolean returnNull) {
        final PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(packageName, 0);
        } catch(final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        if(returnNull) {
            return ai == null ? null : pm.getApplicationLabel(ai).toString();
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : packageName);
    }

    public static Drawable getAppIconFromPackage(Context context, String packageName) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        Drawable drawable = null;
        try {
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            drawable = pm.getApplicationIcon(ai);
        } catch (Exception e) {
            if(BuildConfig.DEBUG) e.printStackTrace();
        }
        return drawable;
    }

    /*private String saveToInternalStorage(Bitmap bitmapImage, String name, Context context){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/notisaver/app_data/imageDir
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    */

    public static boolean isNotificationExist (String time, String text, String user, String name, DatabaseHelper dbHelper) {
        Cursor cursor = dbHelper.readNotifications();
        if (cursor.getCount() > 0)
            while (cursor.moveToNext())
                if (Math.abs(Long.parseLong(time) - Long.parseLong(cursor.getString(5))) < 1000 &&
                        text.equals(cursor.getString(4)) && user.equals(cursor.getString(3)) &&
                        name.equals(cursor.getString(2)))
                    return true;
        return false;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
