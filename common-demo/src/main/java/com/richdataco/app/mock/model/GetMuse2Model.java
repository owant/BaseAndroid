package com.richdataco.app.mock.model;

import com.alibaba.fastjson.JSON;
import com.richdataco.common.mockedata.MockService;
import com.richdataco.common.net.Response;

/**
 * Created by Dell on 2018/1/26.
 */

public class GetMuse2Model extends MockService {


    @Override
    public String getJsonData() {
        Response successResponse = getSuccessResponse();
        successResponse.setResult("{\n" +
                "  \"array\": [\n" +
                "    1,\n" +
                "    2,\n" +
                "    3\n" +
                "  ],\n" +
                "  \"boolean\": true,\n" +
                "  \"null\": null,\n" +
                "  \"number\": 123,\n" +
                "  \"object\": {\n" +
                "    \"a\": \"b\",\n" +
                "    \"c\": \"d\",\n" +
                "    \"e\": \"f\"\n" +
                "  },\n" +
                "  \"string\": \"Hello World\"\n" +
                "}");
        return JSON.toJSONString(successResponse);
    }
}
