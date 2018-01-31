package com.richdataco.common.mockedata;

import com.alibaba.fastjson.JSON;
import com.richdataco.common.model.MusicModel;
import com.richdataco.common.net.Response;

/**
 * Created by OlaWang on 2017/5/18.
 */

public class MockMusicData extends MockService {

    @Override
    public String getJsonData() {

        MusicModel musicModel = new MusicModel();
        musicModel.musicName = "千与千寻";
        musicModel.singerName = "久石让";

        Response response = getSuccessResponse();
        response.setResult(JSON.toJSONString(musicModel));

        return JSON.toJSONString(response);
    }
}
