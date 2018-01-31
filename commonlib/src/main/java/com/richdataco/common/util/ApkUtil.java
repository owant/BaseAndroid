package com.richdataco.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlaWang on 2017/5/3.
 * 对Apk进行操作
 * <item>安装apk {@link ApkUtil#installApk(Context, String)}</>
 * <item>卸载apk {@link ApkUtil#unInstallApk(Context, String)}</>
 * <item>检查手机安装了的apk集合 {@link ApkUtil#getSystemApkLists(Context)}</>
 * <item>判断是否安装了某个apk @{@link ApkUtil#existedApk(Context, String)}</item>
 */

public class ApkUtil {

    public static void installApk(Context context, String filePath) {
        File apkFile = new File(filePath);
        if (apkFile.exists()) {
            Intent installIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            installIntent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
            context.startActivity(installIntent);
        }
    }

    public static void unInstallApk(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent unInstallIntent = new Intent(Intent.ACTION_DELETE);
        unInstallIntent.setData(packageURI);
        context.startActivity(unInstallIntent);
    }

    public static List<String> getSystemApkLists(Context context) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        List<String> lists = new ArrayList<>();
        for (ResolveInfo item : resolveInfo) {
            String resolvePackageName = item.activityInfo.packageName;
            AndroidLog.i("exist apk", resolvePackageName);
            lists.add(resolvePackageName);
        }
        return lists;
    }

    public static boolean existedApk(Context context, String apkName) {
        if (!TextUtils.isEmpty(apkName)) {
            return getSystemApkLists(context).contains(apkName);
        }
        return false;
    }
}
