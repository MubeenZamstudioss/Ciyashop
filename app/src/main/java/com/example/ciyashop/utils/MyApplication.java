package com.example.ciyashop.utils;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.applinks.AppLinkData;
import com.onesignal.OneSignal;
import com.pixplicity.easyprefs.library.Prefs;
import com.example.ciyashop.fcm.MyNotificationOpenedHandler;
import com.example.ciyashop.fcm.MyNotificationReceivedHandler;


/**
 * Created by Bhumi Shah on 12/23/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        new APIS();
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(Constant.MyPREFERENCES)
                .setUseDefaultSharedPreference(true)
                .build();


        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        // Process app link data
                    }
                });

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
                .init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
