package com.hongyan.life.activity.device;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class DeviceInfoActivity extends BaseActivity {

    String TAG = "DeviceInfoActivity";

    TextView mPhoneInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        initView();
        initData();
    }

    private void initData() {

        String ramInfo = getRAMInfo(this);
        String storageInfo = getStorageInfo(this, 0);
        String storageInfo1 = getStorageInfo(this, 1);
        mPhoneInfo.setText(getDeviceAndroidVersion()
                +"\nAndroid SDK版本:"+ Build.VERSION.SDK_INT + "\n"+ ramInfo + "\n内部存储信息:" + storageInfo + "\nSDCard  信息:" +
                storageInfo1 + "\n"  + getDeviceModel()+
                "\n芯片型号:"+android.os.Build.HARDWARE+  "\n主板名:" + android.os.Build.BOARD
                        + "\nFingerprint标识:" + android.os.Build.FINGERPRINT
        );
    }

    public void initView() {
        // TODO Auto-generated method stub
        mPhoneInfo = findViewById(R.id.activity_device_info_tv);
        findViewById(R.id.activity_device_back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 判断SD是否挂载
     */
    public static boolean isSDCardMount() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 使用反射方法 获取手机存储路径
     **/
    public static String getStoragePath(Context context, int type) {

        StorageManager sm = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getPathsMethod = sm.getClass().getMethod("getVolumePaths",
                    null);
            String[] path = (String[]) getPathsMethod.invoke(sm, null);

            switch (type) {
                case 0:
                    return path[type];
                case 1:
                    if (path.length > 1) {
                        return path[type];
                    } else {
                        return null;
                    }

                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 手机 RAM 信息 方法 一
     */
    public static String getTotalRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.totalMem;

        return Formatter.formatFileSize(context, size);
    }

    /**
     * 手机 RAM 信息 方法 二
     */
    public static String getTotalRAMOther(Context context) {
        String path = "/proc/meminfo";
        String firstLine = null;
        int totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {

            totalRam = (int) Math.ceil((new Float(Float.valueOf(firstLine)
                    / (1024 * 1024)).doubleValue()));

            long totalBytes = 0;

        }

        return Formatter.formatFileSize(context, totalRam);
    }

    /**
     * 获取 手机 可用 RAM
     */
    public static String getAvailableRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.availMem;

        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取 手机 RAM 信息
     */
    public static String getRAMInfo(Context context) {
        long totalSize = 0;
        long availableSize = 0;

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        totalSize = memoryInfo.totalMem;
        availableSize = memoryInfo.availMem;

        return "RAM信息：" + Formatter.formatFileSize(context, totalSize) + "  剩余大小：" +
                Formatter.formatFileSize(context, availableSize);
    }

    /**
     * 获取手机存储 ROM 信息
     * <p>
     * type： 用于区分内置存储于外置存储的方法
     * <p>
     * 内置SD卡 ：INTERNAL_STORAGE = 0;
     * <p>
     * 外置SD卡： EXTERNAL_STORAGE = 1;
     **/
    public static String getStorageInfo(Context context, int type) {

        String path = getStoragePath(context, type);
        /**
         * 无外置SD 卡判断
         * **/
        if (isSDCardMount() == false || TextUtils.isEmpty(path) || path == null) {
            return "无外置SD卡";
        }

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
        String stotageInfo;

        long blockCount = statFs.getBlockCountLong();
        long bloackSize = statFs.getBlockSizeLong();
        long totalSpace = bloackSize * blockCount;

        long availableBlocks = statFs.getAvailableBlocksLong();
        long availableSpace = availableBlocks * bloackSize;
        String typeText = "";
        switch (type) {
            case 0:
                typeText = "内置SD卡";
                break;
            case 1:
                typeText = "外置SD卡";
                break;
        }
        stotageInfo =  ""
                + Formatter.formatFileSize(context, availableSpace) + "/"
                + Formatter.formatFileSize(context, totalSpace);

        return stotageInfo;

    }

    /**
     * 获取手机Android 版本
     *
     * @return
     */
    public static String getDeviceAndroidVersion() {
        return "安卓版本：" + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return "手机型号：" + android.os.Build.MODEL;
    }


}
