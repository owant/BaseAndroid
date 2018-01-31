package com.richdataco.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.richdataco.app.R;
import com.richdataco.common.ui.base.BaseActivity;
import com.richdataco.common.view.RoundImageView;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by OlaWang on 2017/5/8.
 */

public class TestImageCropActivity extends BaseActivity {

    RoundImageView mRoundImageView;

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test_image_crop;
    }

    @Override
    protected void onBaseBindView() {
        mRoundImageView = (RoundImageView) findViewById(R.id.crop_image_riv_user_photo);
    }

    @Override
    protected void onBaseLoadData() {

    }

    public void cropImage(View view) {
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {

            //clear the rui make iv refresh
            mRoundImageView.setImageURI(null);
            mRoundImageView.setImageURI(Crop.getOutput(result));
            //TODO 将Uri的文件上传到服务器
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
