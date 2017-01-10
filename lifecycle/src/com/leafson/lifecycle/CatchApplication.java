package com.leafson.lifecycle;

import android.app.Application;

public class CatchApplication extends Application {
    @Override
    public void onCreate() {
            super.onCreate();
            CatchHandler.getInstance().init(getApplicationContext());
            
            
    }
}