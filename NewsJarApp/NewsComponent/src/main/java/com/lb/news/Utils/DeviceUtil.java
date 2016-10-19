package com.lb.news.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.lb.news.model.NewsContentResolver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by oli on 16-5-26.
 */
public class DeviceUtil {
    /**获取设备唯一id*/
    public static String getDeviceId(Context context,String imei){
        String deviceId = PreferenceUtils.readString(context,PreferenceUtils.KEY_DEVICEID);
        if(deviceId != null && !deviceId.equals("")) {
            return deviceId;
        }
        if(imei == null || imei.equals("")) {
            String mac = getMAC(context);
            if(mac == null || mac.equals("")) {
                Log.d("LewaLog","Generate deviceId by mac...");
                deviceId = getMD5(getUUID());
            }
            else {
                Log.d("LewaLog","Generate deviceId by uuid...");
                deviceId = getMD5(mac);
            }
        }
        else {
            Log.d("LewaLog","Generate deviceId by imei...");
            deviceId = getMD5(imei);
        }
        if(deviceId != null && !deviceId.equals("")) {
            PreferenceUtils.writeString(context,PreferenceUtils.KEY_DEVICEID,deviceId);
        }
        return deviceId;
    }

    private static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        Log.d("LewaLog","----->UUID"+uuid);
        return uniqueId;
    }

    //return imei
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager=(TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei=telephonyManager.getDeviceId();
        return imei;
    }

    //return MAC
    public static String getMAC(Context context){
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac;
    }

    //MD5
    public static String getMD5(String source){
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(source.getBytes(),0,source.length());
        byte p_md5Data[] = m.digest();
        String digst = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF){
                digst += "0";
            }
            digst += Integer.toHexString(b);
        }
        digst = digst.toUpperCase();
        return digst;
    }

    /**获取当前网络类型*/
    public static final String NETWORKTYPE_WIFI = "WIFI";
    public static final String NETWORKTYPE_MOBILE = "4G";
    public static final String NETWORKTYPE_INVALID = "UNKNOW";
    public static String getNetWorkType(Context context){
        String netWorkType = "";
        if(context == null) {
            return netWorkType;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                netWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                netWorkType = NETWORKTYPE_MOBILE;
            }
        } else {
            netWorkType = NETWORKTYPE_INVALID;
        }
        return netWorkType;
    }

//    public static String getNetworkDetailType(Context context){
//        TelephonyManager mTelephonyManager = (TelephonyManager)
//                context.getSystemService(Context.TELEPHONY_SERVICE);
//        int networkType = mTelephonyManager.getNetworkType();
//        switch (networkType) {
//            case TelephonyManager.NETWORK_TYPE_GPRS:
//            case TelephonyManager.NETWORK_TYPE_EDGE:
//            case TelephonyManager.NETWORK_TYPE_CDMA:
//            case TelephonyManager.NETWORK_TYPE_1xRTT:
//            case TelephonyManager.NETWORK_TYPE_IDEN:
//                return "2g";
//            case TelephonyManager.NETWORK_TYPE_UMTS:
//            case TelephonyManager.NETWORK_TYPE_EVDO_0:
//            case TelephonyManager.NETWORK_TYPE_EVDO_A:
//            case TelephonyManager.NETWORK_TYPE_HSDPA:
//            case TelephonyManager.NETWORK_TYPE_HSUPA:
//            case TelephonyManager.NETWORK_TYPE_HSPA:
//            case TelephonyManager.NETWORK_TYPE_EVDO_B:
//            case TelephonyManager.NETWORK_TYPE_EHRPD:
//            case TelephonyManager.NETWORK_TYPE_HSPAP:
//                //Log.d("Type", "3g");
//                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
//                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
//                //Some cases are added after  testing(real) in device with 3g enable data
//                //and speed also matters to decide 3g network type
//                //http://goo.gl/bhtVT
//                return "3g";
//            case TelephonyManager.NETWORK_TYPE_LTE:
//                //No specification for the 4g but from wiki
//                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
//                //https://goo.gl/9t7yrR
//                return "4g";
//            default:
//                return "Unknow";
//        }
//    }

    public static final String ENGLISH = "english";
    public static final String INDIA = "india";

    private static String mLanguage = null;
    public static String getLanguage(Context context){
        if(context == null) {
            return null;
        }
        mLanguage = context.getResources().getConfiguration().locale.getLanguage();
        if(("hi").equals(mLanguage)){
            return INDIA;
        }
        else if(("en").equals(mLanguage)) {
            return ENGLISH;
        }
        else {
            return ENGLISH;
        }
    }

//    public static String getToken(Context context){
//        return PreferenceUtils.readString(context,PreferenceUtils.KEY_TOKEN+mLanguage);
//    }
//
//    public static void saveToken(Context context,String value){
//        PreferenceUtils.writeString(context,PreferenceUtils.KEY_TOKEN+mLanguage,
//                value);
//    }

    public static String getTokenKey(Context context){
        if(context == null) {
            return null;
        }
        String key = null;
        String language = DeviceUtil.getLanguage(context);
        if(INDIA.equals(language)){
            key = NewsContentResolver.KEY_INDIA_TOKEN;
        }
        else if(ENGLISH.equals(language)) {
            key = NewsContentResolver.KEY_ENGLISH_TOKEN;
        }
        return key;
    }

    public static String getScreenResolution(Context context){
        if(context == null){
            return "";
        }
        WindowManager wm = (WindowManager)context.getSystemService(Activity.WINDOW_SERVICE);
        android.view.Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int W = displayMetrics.widthPixels;
        int H = displayMetrics.heightPixels;
        return H + "*" + W;
    }

}
