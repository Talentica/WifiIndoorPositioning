package com.talentica.wifiindoorpositioning.wifiindoorpositioning.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.LocDistance;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.LocationWithNearbyPlaces;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by suyashg on 11/09/17.
 */

public class Utils {

    public static String getDefaultAlgo(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefAlgo = prefs.getString("prefAlgo", "2");
        return prefAlgo;
    }


    public static boolean isLocationEnabled(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS enabled
            Log.d("Utils", "isLocationEnabled:" + true);
            return true;
        } else {
            //GPS disabled
            Log.d("Utils", "isLocationEnabled:" + false);
            return false;
        }
    }

    public static LocDistance getTheNearestPoint(LocationWithNearbyPlaces loc) {
        ArrayList<LocDistance> places = loc.getPlaces();
        if (places != null && places.size() > 0) {
            Collections.sort(places);
            return places.get(0);
        }
        return null;
    }

    public static String reduceDecimalPlaces(String location) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String[] split = location.split(" ");
        Double latValue = Double.valueOf(split[0]);
        Double lonValue = Double.valueOf(split[1]);
        String latFormat = formatter.format(latValue);
        String lonFormat = formatter.format(lonValue);
        return latFormat + ", " + lonFormat;
    }

    public static String getTheDistancefromOrigin(String location) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String[] split = location.split(" ");
        Double latValue = Double.valueOf(split[0]);
        Double lonValue = Double.valueOf(split[1]);
        double distance = Math.sqrt(latValue * latValue + lonValue * lonValue);
        String distanceValue = formatter.format(distance);
        return distanceValue;
    }
}
