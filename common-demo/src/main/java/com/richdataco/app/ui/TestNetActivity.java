package com.richdataco.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.richdataco.app.R;
import com.richdataco.common.BaseApplication;
import com.richdataco.common.net.RequestCallback;
import com.richdataco.common.net.engine.RemoteService;
import com.richdataco.common.net.engine.VolleyEngine;
import com.richdataco.common.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OlaWang on 2017/5/18.
 */

public class TestNetActivity extends BaseActivity {

    TextView mTextView;
    Button mButton;


    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_volley_test;
    }

    @Override
    protected void onBaseBindView() {
        mTextView = (TextView) findViewById(R.id.volley_tv_info);
        mButton = (Button) findViewById(R.id.volley_btn_request);
    }

    @Override
    protected void onBaseLoadData() {

    }

    @Override
    protected void onDestroy() {
        VolleyEngine.getInstance(this).getRequestQueue().cancelAll("a");

        RemoteService.getInstance().cancelAll(this, "test");
        super.onDestroy();

    }

    public void sendRequest(View view) {
        //默认的请求
        StringRequest request = new StringRequest(Request.Method.POST, "http://v5.pc.duomi.com/search-ajaxsearch-searchall",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kw", "刘德华");
                params.put("pi", "1");
                params.put("pz", "2");
                return params;
            }
        };
        request.setTag("a");
        VolleyEngine.getInstance(this).getRequestQueue().add(request);
    }

    public void sendRemoteSuccessRequest(View view) {
        RequestCallback requestCallback = new RequestCallback() {

            @Override
            public void onSuccess(String content) {
                Toast.makeText(TestNetActivity.this, content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String errorMessage) {

            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");
        RemoteService.getInstance().invoke("test", this, "getSuccessMusic", params, null, requestCallback);
    }

    public void sendRemoteFailRequest(View view) {
        RequestCallback requestCallback = new RequestCallback() {

            @Override
            public void onSuccess(String content) {
                Toast.makeText(TestNetActivity.this, content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(TestNetActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");
        RemoteService.getInstance().invoke("test", this, "failMussic", params, null, requestCallback);
    }

    public void sendRealRequest(View view) {

        RequestCallback requestCallback = new RequestCallback() {

            @Override
            public void onSuccess(String content) {
                Toast.makeText(TestNetActivity.this, content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(TestNetActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("kw", "刘德华");
        params.put("pi", "1");
        params.put("pz", "2");
        RemoteService.getInstance().invoke("test", this, "getMusic", params, null, requestCallback);
    }
}
