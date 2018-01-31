package com.richdataco.common.net;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.intent.IntentMonitorRegistry;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.richdataco.common.net.engine.RemoteService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by OlaWang on 2017/5/18.
 */
@RunWith(JUnit4.class)
public class HttpRequestTest {

    @Test
    public void createRequest() throws Exception {
        Context targetContext = InstrumentationRegistry.getTargetContext();

        final long now = System.currentTimeMillis();
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                Log.i("success", content);
                Log.i("time", (System.currentTimeMillis() - now) + "");
            }

            @Override
            public void onFail(String errorMessage) {
                Log.i("fail", errorMessage);
            }
        };

        HttpRequest httpRequest = new HttpRequest(Request.Method.POST,
                "http://v5.pc.duomi.com/search-ajaxsearch-searchall",
                requestCallback);
//        //?kw=刘德华&pi=1&pz=12
        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");

        httpRequest.setParams(params);
        httpRequest.setShouldCache(true);
        httpRequest.setUseBaseResponse(false);


        Volley.newRequestQueue(targetContext).add(httpRequest.createRequest());


    }

    @Test
    public void testRemoteService() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        final long now = System.currentTimeMillis();
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                Log.i("success", content);
                Log.i("time", (System.currentTimeMillis() - now) + "");
            }

            @Override
            public void onFail(String errorMessage) {
                Log.i("fail", errorMessage);
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");

        RemoteService.getInstance().invoke("getMusic", targetContext, "getMusic", params, null, requestCallback);
    }

    @Test
    public void testRemoteService2() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        final long now = System.currentTimeMillis();
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                Log.i("mock success", content);
                Log.i("time", (System.currentTimeMillis() - now) + "");
            }

            @Override
            public void onFail(String errorMessage) {
                Log.i("fail", errorMessage);
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");

        RemoteService.getInstance().invoke("getMusic", targetContext, "getMusic2", params, null, requestCallback);
    }

}