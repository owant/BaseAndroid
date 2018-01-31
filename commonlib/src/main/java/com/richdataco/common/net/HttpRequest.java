package com.richdataco.common.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by OlaWang on 2017/5/17.
 */

public class HttpRequest {

    private final static String TAG = "HttpRequest";

    private int method;
    private String url;
    private Map<String, String> params;
    private Map<String, String> header;

    private StringRequest stringRequest;
    private RequestCallback requestCallback;

    private Object tag;
    private boolean shouldCache;

    /**
     * if false not use the baseResponse,and true use the baseResponse
     * {@link com.richdataco.common.net.Response}
     */
    private boolean useBaseResponse = false;

    public HttpRequest(int method, String url, RequestCallback requestCallback) {
        this.method = method;
        this.url = url;
        this.requestCallback = requestCallback;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public boolean isShouldCache() {
        return shouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
    }

    public boolean isUseBaseResponse() {
        return useBaseResponse;
    }

    public void setUseBaseResponse(boolean useBaseResponse) {
        this.useBaseResponse = useBaseResponse;
    }

    public RequestCallback getRequestCallback() {
        return requestCallback;
    }

    public void setRequestCallback(RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
    }

    /**
     * create a StringRequest
     *
     * @return {@link StringRequest}
     */
    public StringRequest createRequest() {
        stringRequest = new StringRequest(method, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (requestCallback != null) {
                    if (useBaseResponse) {
                        com.richdataco.common.net.Response baseResponse = JSON.parseObject(response,
                                com.richdataco.common.net.Response.class);

                        if (!baseResponse.getIsError()) {

                            if (baseResponse.getErrorType() == -104) {
                                Log.e(TAG, "Request url=" + url + "\n" +
                                        "return messy format of JSON,Please connect the engineer solve this problem...");
                            }

                            requestCallback.onSuccess(baseResponse.getResult());

                        } else {
                            requestCallback.onFail(baseResponse.getErrorMessage());
                        }

                    } else {
                        requestCallback.onSuccess(response);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestCallback != null) {
                    requestCallback.onFail(error.toString());
                }
            }
        }) {

            //input the params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            //input the headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null) {
                    return header;
                }
                return super.getHeaders();
            }

            //solve the Chinese messy code
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = "";
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        stringRequest.setTag(tag);
        stringRequest.setShouldCache(shouldCache);

        return stringRequest;
    }


}
