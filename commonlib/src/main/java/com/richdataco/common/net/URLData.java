package com.richdataco.common.net;

/**
 * /res/xml/url information data model
 */
public class URLData {

    /**
     * find key
     */
    private String key;

    /**
     * post or get
     */
    private int method;

    /**
     * url address
     */
    private String url;

    /**
     * location test-data
     */
    private String mockClass;

    private boolean mShouldCache = false;
    private boolean mBaseJSON = false;

    public URLData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMockClass() {
        return mockClass;
    }

    public void setMockClass(String mockClass) {
        this.mockClass = mockClass;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        mShouldCache = shouldCache;
    }

    public boolean isBaseJSON() {
        return mBaseJSON;
    }

    public void setBaseJSON(boolean baseJSON) {
        mBaseJSON = baseJSON;
    }

    @Override
    public String toString() {
        return "URLData{" +
                "key='" + key + '\'' +
                ", method=" + method +
                ", url='" + url + '\'' +
                ", mockClass='" + mockClass + '\'' +
                ", mShouldCache=" + mShouldCache +
                ", mBaseJSON=" + mBaseJSON +
                '}';
    }
}
