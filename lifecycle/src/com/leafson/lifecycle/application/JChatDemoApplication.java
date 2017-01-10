package com.leafson.lifecycle.application;

import android.app.Application;
import android.util.Log;

public class JChatDemoApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("JpushDemoApplication", "init");
    }

}
