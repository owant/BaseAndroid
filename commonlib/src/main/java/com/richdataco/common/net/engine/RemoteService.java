package com.richdataco.common.net.engine;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.richdataco.common.mockedata.MockService;
import com.richdataco.common.net.HttpRequest;
import com.richdataco.common.net.RequestCallback;
import com.richdataco.common.net.Response;
import com.richdataco.common.net.URLData;
import com.richdataco.common.net.UrlConfigManager;

import java.util.Map;

/**
 * Created by OlaWang on 2017/5/18.
 */

public class RemoteService {

    private static RemoteService service = null;

    private RemoteService() {

    }

    public static synchronized RemoteService getInstance() {
        if (RemoteService.service == null) {
            RemoteService.service = new RemoteService();
        }
        return RemoteService.service;
    }

    /**
     * invoke the request
     *
     * @param context  activity
     * @param apiKey   url apikey
     * @param tag      cancel tag
     * @param params   input params
     * @param header   header maps
     * @param callback callback in ui thread
     */
    public void invoke(final Object tag,
                       final Context context,
                       final String apiKey,
                       final Map<String, String> params,
                       final Map<String, String> header,
                       final RequestCallback callback) {

        //通过apiKey查找需要的接口
        final URLData urlData = UrlConfigManager.findURL(context.getApplicationContext(), apiKey);

        //如果有虚拟的请求模型进行真实请求
        if (urlData.getMockClass() != null && !TextUtils.isEmpty(urlData.getMockClass())) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();

                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);

                if (callback != null) {
                    if (responseInJson.getIsError()) {//如果是错误的模型
                        callback.onFail(responseInJson.getErrorMessage());
                    } else {
                        callback.onSuccess(responseInJson.getResult());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } else {//进行真实请求
            HttpRequest httpRequest = new HttpRequest(urlData.getMethod(), urlData.getUrl(), callback);
            httpRequest.setTag(tag);
            httpRequest.setParams(params);
            httpRequest.setHeader(header);
            httpRequest.setUseBaseResponse(urlData.isBaseJSON());

            if (tag != null) {
                VolleyEngine.getInstance(context).getRequestQueue().cancelAll(tag);
            }
            VolleyEngine.getInstance(context).getRequestQueue().add(httpRequest.createRequest());
        }
    }

    public void cancelAll(Context context, Object tag) {
        VolleyEngine.getInstance(context.getApplicationContext())
                .getRequestQueue().cancelAll(tag);
    }

    public void cancelAll(Context context, RequestQueue.RequestFilter filter) {
        VolleyEngine.getInstance(context.getApplicationContext())
                .getRequestQueue().cancelAll(filter);
    }
}
