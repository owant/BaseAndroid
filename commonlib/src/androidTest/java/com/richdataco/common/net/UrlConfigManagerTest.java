package com.richdataco.common.net;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by OlaWang on 2017/5/18.
 */

@RunWith(JUnit4.class)
public class UrlConfigManagerTest {

    @Test
    public void urlConfig() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        URLData getWeatherInfo = UrlConfigManager.findURL(targetContext, "getWeatherInfo");
        Log.i("getWeatherInfo", getWeatherInfo.toString());
    }

}