package com.richdataco.common.net;


/**
 * base Response in JSON
 * {
 * "isError":true,
 * "errorType":1,
 * "errorMessage":"network error!",
 * "result":""
 * }
 */
public class Response implements Cloneable{
    private boolean isError;
    private String errorMessage;
    private int errorType;
    private String result;
    public void setIsError(boolean isError) {
        this.isError = isError;
    }
    public boolean getIsError() {
        return isError;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
