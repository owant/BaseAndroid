package com.richdataco.common.net;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.richdataco.commonlib.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * loading all http rul address in Array
 */
public class UrlConfigManager {


    private static ArrayList<URLData> urlList;

    private static void fetchUrlDataFromXml(final Context context) {
        urlList = new ArrayList<URLData>();

        final XmlResourceParser xmlParser = context.getApplicationContext()
                .getResources().getXml(R.xml.url);

        int eventCode;
        try {
            eventCode = xmlParser.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT) {
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("Node".equals(xmlParser.getName())) {

                            final String key = xmlParser.getAttributeValue(null, "Key");
                            final URLData urlData = new URLData();
                            urlData.setKey(key);

                            String netType = xmlParser.getAttributeValue(null, "NetType").toLowerCase();
                            if ("post".equals(netType)) {
                                urlData.setMethod(1);
                            } else if ("get".equals(netType)) {
                                urlData.setMethod(0);
                            } else {
                                // TODO: 2017/5/18 这里还需要进行处理
                                urlData.setMethod(1);
                            }

                            urlData.setUrl(xmlParser.getAttributeValue(null, "Url"));

                            urlData.setMockClass(xmlParser.getAttributeValue(null, "MockClass"));

                            String shouldCache = xmlParser.getAttributeValue(null, "ShouldCache");
                            if ("true".equals(shouldCache)) {
                                urlData.setShouldCache(true);
                            } else {
                                urlData.setShouldCache(false);
                            }

                            String baseJson = xmlParser.getAttributeValue(null, "BaseJSON");
                            if ("true".equals(baseJson)) {
                                urlData.setBaseJSON(true);
                            } else {
                                urlData.setBaseJSON(false);
                            }

                            urlList.add(urlData);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventCode = xmlParser.next();
            }
        } catch (final XmlPullParserException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }
    }

    public static URLData findURL(final Context context, final String findKey) {
        // once loading data
        if (urlList == null || urlList.isEmpty())
            fetchUrlDataFromXml(context);

        for (URLData data : urlList) {
            if (findKey.equals(data.getKey())) {
                return data;
            }
        }
        return null;
    }


}