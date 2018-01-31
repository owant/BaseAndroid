package com.richdataco.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * app工具
 * <item>隐藏键盘 {@link AppUtils#hideKeyboard(Context, View)}</item>
 * <item>点击输入框之外的判断 {@link AppUtils#isShouldHideInput(View, MotionEvent)} </item>
 * <item>显示Toast {@link AppUtils#showToast(Context, String)}</item>*
 * <item>网络是否可以 {@link AppUtils#isNetworkAvailable(Context)}</item>
 * <item>打开设置 {@link AppUtils#openSettingsConn(Context)}</item>
 * <item>获得最近的SSID {@link AppUtils#getCurrentSSID(Context)}</item>
 * <item>判断APP是否进入后台 {@link AppUtils#isAppForeground(Context)}</item>
 * <item>获得APP版本号 {@link AppUtils#getAppVersion(Context)}</item>
 * <item>获得系统版本号 {@link AppUtils#getAndroidSystemVersion()}</item>
 * <item>复制到键盘 {@link AppUtils#coptyToClipBoard(Context, String)}</item>
 * <item>跳到首页 {@link AppUtils#intentToHome(Context)}</item>
 * <item>获得cpu的名称 {@link AppUtils#getCpuName()}</item>
 */
public class AppUtils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isShouldHideInput(View inputView, MotionEvent event) {
        boolean should = false;
        if (inputView != null && (inputView instanceof EditText)) {
            int[] leftTop = {0, 0};
            inputView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];

            int bottom = top + inputView.getHeight();
            int right = left + inputView.getWidth();

            if (event.getX() > left && event.getX() < right &&
                    event.getY() > top && event.getY() < bottom) {
                should = false;
            } else {
                should = true;
            }
        }
        return should;
    }

    public static void showToast(Context mContext, String toastInfo) {
        Toast mToast = Toast.makeText(mContext, toastInfo, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean netStatus = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null) {
            netStatus = networkInfo.isAvailable();
        }
        return netStatus;
    }

    public static void openSettingsConn(Context mContext, Integer requestCode) {
        Intent settings;
        // SDK>15，就是3.0以上的版本
        if (Build.VERSION.SDK_INT >= 15) {
            settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
        } else {
            settings = new Intent();
            ComponentName component = new ComponentName("com.android.settings",
                    "com.android.setttins.WirelessSettings");
            settings.setAction("android.intent.action.VIEW");

        }

        if (requestCode == null) {
            mContext.startActivity(settings);
        } else {
            ((Activity) mContext).startActivityForResult(settings, requestCode);
        }
    }

    public static void openSettingsConn(Context mContext) {
        openSettingsConn(mContext, null);
    }

    public static String getCurrentSSID(Context context) {
        String ssid = "";
        WifiManager wifiManager = (WifiManager) context
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            ssid = wifiManager.getConnectionInfo().getSSID();
            ssid = ssid.replaceAll("\"", "");
            if (TextUtils.equals("<unknown ssid>", ssid)) {
                ssid = "";
            }
        }
        return ssid;
    }

    public static boolean isAppForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.
                getRunningTasks(Integer.MAX_VALUE);

        ActivityManager.RunningTaskInfo foregroundTask = runningTasks.get(0);
        String packageName = foregroundTask.topActivity.getPackageName();
        String myPackageName = context.getPackageName();

        return packageName.equals(myPackageName);
    }

    public static String getAppVersion(Context context) {
        String myVersion = "v ";
        try {
            myVersion = myVersion + context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersion;
    }

    public static String getAndroidSystemVersion() {
        return "android " + Build.VERSION.SDK_INT;
    }



    public static void coptyToClipBoard(Context context, String content) {
        int targetVersion = Build.VERSION.SDK_INT;
        if (targetVersion >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", content);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.
                    getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
        }
    }

    /**
     * 判断是否有软控制键（手机底部几个按钮）
     *
     * @param activity
     * @return
     */
    public boolean isSoftKeyAvail(Activity activity) {
        final boolean[] isSoftkey = {false};
        final View activityRootView = (activity).getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int rootViewHeight = activityRootView.getRootView().getHeight();
                int viewHeight = activityRootView.getHeight();
                int heightDiff = rootViewHeight - viewHeight;
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    isSoftkey[0] = true;
                }
            }
        });
        return isSoftkey[0];
    }

    /**
     * 主动回到Home，后台运行
     *
     * @param context
     */
    public void intentToHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /**
     * 获取CPU名字
     *
     * @return
     */
    public String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}