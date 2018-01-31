package com.richdataco.common.net;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

/**
 * Created by OlaWang on 2017/5/18.
 */
public class ResponseTest {

    @Test
    public void responseToJson() {

//        String source = "{ \n" +
//                "   \"isError\":false,\n" +
//                "   \"errorType\":0,\n" +
//                "   \"errorMessage\":\"\",\n" +
//                "   \"result\":{\n" +
//                "         \"cinemaId\":1,\n" +
//                "         \"cinemaName\":\"星美\"\n" +
//                "   }\n" +
//                "}";

        String source = "{ \n" +
                "   \"isError\":true,\n" +
                "   \"errorType\":1,\n" +
                "   \"errorMessage\":\"网络异常\",\n" +
                "   \"result\":\"\"\n" +
                "}";

        Response response = JSON.parseObject(source, Response.class);
        System.out.println(response.toString());
    }

}