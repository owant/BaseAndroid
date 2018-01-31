package com.richdataco.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.richdataco.app.mock.model.FailModel;
import com.richdataco.common.net.Response;
import com.richdataco.common.net.URLData;
import com.richdataco.common.net.UrlConfigManager;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by OlaWang on 2017/5/17.
 */
@RunWith(AndroidJUnit4.class)
public class TestURLManager {

    @Test
    public void testUrlManager() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        URLData login = UrlConfigManager.findURL(appContext, "getMusic");
        //URLData{key='getMusic2', method=1, url='http://v5.pc.duomi.com/search-ajaxsearch-searchall', mockClass='', mShouldCache=true, mBaseJSON=true}
        Log.i("login", login.toString());
        assertEquals("URLData{key='getMusic', method=1, url='http://v5.pc.duomi.com/search-ajaxsearch-searchall'," +
                " mockClass='null', mShouldCache=true, mBaseJSON=false}", login.toString());
    }

    @Test
    public void getFailResponse() {
        FailModel f = new FailModel();
        Log.i("getJSON", f.getJsonData());
        Response response = JSON.parseObject(f.getJsonData(), Response.class);
        assertEquals(true, response.getIsError());
    }

    @Test
    public void parseJSON() {
        Response response = new Response();
        response.setResult("hallo");
        response.setErrorType(400);
        response.setIsError(true);
        response.setErrorMessage("网页不存在");

        String result = JSON.toJSONString(response);
        Log.i("parse", result);
        assertEquals("{\"errorMessage\":\"网页不存在\",\"errorType\":400,\"isError\":true,\"result\":\"hallo\"}", result);
    }

}
