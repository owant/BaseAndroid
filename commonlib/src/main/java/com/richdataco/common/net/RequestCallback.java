package com.richdataco.common.net;

/**
 * Created by OlaWang on 2017/5/17.
 */

public interface RequestCallback {

    void onSuccess(String content);

    void onFail(String errorMessage);
}
