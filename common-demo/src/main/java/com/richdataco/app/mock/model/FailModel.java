package com.richdataco.app.mock.model;

import com.alibaba.fastjson.JSON;
import com.richdataco.common.mockedata.MockService;
import com.richdataco.common.net.Response;

/**
 * Created by Dell on 2018/1/26.
 */

public class FailModel extends MockService {
    @Override
    public String getJsonData() {
        Response response = getFailResponse(1001, "用户密码错误！");
        return JSON.toJSONString(response);
    }
}
