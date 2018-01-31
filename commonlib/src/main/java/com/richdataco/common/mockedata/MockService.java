package com.richdataco.common.mockedata;

import com.richdataco.common.net.Response;

/**
 * Created by OlaWang on 2017/5/18.
 */

public abstract class MockService {

    /**
     * 模拟返回的数据
     *
     * @return
     */
    public abstract String getJsonData();

    /**
     * 模拟成功的基本模型
     *
     * @return
     */
    public Response getSuccessResponse() {
        Response response = new Response();
        response.setIsError(false);
        response.setErrorType(0);
        response.setErrorMessage("");
        return response;
    }

    /**
     * 模拟失败的基本模型
     *
     * @param errorType
     * @param errorMessage
     * @return
     */
    public Response getFailResponse(int errorType, String errorMessage) {
        Response response = new Response();
        response.setIsError(true);
        response.setErrorType(errorType);
        response.setErrorMessage(errorMessage);
        return response;
    }
}
