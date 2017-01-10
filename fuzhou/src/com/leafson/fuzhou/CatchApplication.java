package com.leafson.fuzhou;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class CatchApplication extends Application {
    @Override
    public void onCreate() {
            super.onCreate();
            JPushInterface.init(this);
            JPushInterface.setDebugMode(true);
            CatchHandler.getInstance().init(getApplicationContext());
            
            
    }
}