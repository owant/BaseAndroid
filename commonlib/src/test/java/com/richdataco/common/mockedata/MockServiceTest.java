package com.richdataco.common.mockedata;

import com.richdataco.common.net.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Dell on 2018/2/8.
 */
@RunWith(JUnit4.class)
public class MockServiceTest {

    @Test
    public void testMockServiceStatic() {
        MockService mockService = new MockService() {
            @Override
            public String getJsonData() {
                Response response1 = getFailResponse(200, "网络错误");
                Response response2 = getFailResponse(400, "服务器错误");

                System.out.printf("errorType=%d,errorMessage=%s\n",response1.getErrorType(),response1.getErrorMessage());
                System.out.printf("errorType=%d,errorMessage=%s\n",response2.getErrorType(),response2.getErrorMessage());

                return "";
            }
        };
        mockService.getJsonData();
    }

}