package com.example.samplelauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

public class LauncherAppDetails {

    public static ArrayList<LauncherAppData> getAppDetailsList(Context context) {
        ArrayList<LauncherAppData> appDataArrayList = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(new Intent(Intent.ACTION_MAIN, null)
                .addCategory(Intent.CATEGORY_LAUNCHER), 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            if (resolveInfo.activityInfo.packageName != context.getPackageName()) {
                LauncherAppData launcherAppData = new LauncherAppData();
                launcherAppData.setAppName(resolveInfo.loadLabel(packageManager).toString());
                launcherAppData.setIcon(resolveInfo.activityInfo.loadIcon(packageManager));
                launcherAppData.setPackageName(resolveInfo.activityInfo.packageName);
                launcherAppData.setActivityName(resolveInfo.activityInfo.name);

                try {

                    PackageManager manager = context.getPackageManager();
                    PackageInfo packageInfo = manager.getPackageInfo(launcherAppData.getPackageName(), 0);
                    launcherAppData.setVersionCode(String.valueOf(packageInfo.versionCode));
                    launcherAppData.setVersionName(packageInfo.versionName);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                appDataArrayList.add(launcherAppData);
            }
        }

        return appDataArrayList;
    }
}
