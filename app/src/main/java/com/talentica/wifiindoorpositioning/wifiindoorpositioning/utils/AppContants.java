package com.talentica.wifiindoorpositioning.wifiindoorpositioning.utils;

/**
 * Created by suyashg on 07/09/17.
 */

public class AppContants {

    //Reference points
    public static final int FETCH_INTERVAL = 3000;//3 secs
    public static final int READINGS_BATCH = 10;//10 values in every 3 secs

    public static final Float NaN = -110.0f;//RSSI value for no reception

    public static final String INTENT_FILTER = "ANDROID_WIFI_SCANNER";
    public static final String WIFI_DATA = "WIFI_DATA";
}
