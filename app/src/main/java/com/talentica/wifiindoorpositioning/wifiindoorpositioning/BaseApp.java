package com.talentica.wifiindoorpositioning.wifiindoorpositioning;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by suyashg on 25/08/17.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
