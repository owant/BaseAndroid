package com.richdataco.app;

import com.richdataco.common.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by OlaWang on 2017/5/16.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            LeakCanary.install(this);
    }


}
