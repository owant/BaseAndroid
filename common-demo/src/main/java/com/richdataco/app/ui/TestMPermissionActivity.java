package com.richdataco.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.richdataco.app.R;
import com.richdataco.common.ui.base.BaseActivity;
import com.richdataco.common.util.MPermissionUtil;

/**
 * Created by OlaWang on 2017/5/12.
 */

public class TestMPermissionActivity extends BaseActivity {

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle bundle) {
        return R.layout.activity_mpermission_test;
    }

    @Override
    protected void onBaseBindView() {

    }

    @Override
    protected void onBaseLoadData() {

    }

    public void readAndWrite(View view) {

        if (MPermissionUtil.isMPermission()) {
            if (!MPermissionUtil.allPermissionsGranted(this, MPermissionUtil.PERMISSION_STORAGE)) {
                ActivityCompat.requestPermissions(this, MPermissionUtil.PERMISSION_STORAGE, 4);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4) {
            boolean state = MPermissionUtil.verifyPermissions(grantResults);
            if (!state) {
                Toast.makeText(this, "you deny the permission!", Toast.LENGTH_SHORT).show();
            } else {
                //TODO
            }
        }
    }

    public void openCamera(View view) {
        if (MPermissionUtil.isMPermission()) {
            if (!MPermissionUtil.allPermissionsGranted(this, MPermissionUtil.PERMISSION_CAMERA)) {
                ActivityCompat.requestPermissions(this, MPermissionUtil.PERMISSION_CAMERA, 5);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cameraIntent);
            }
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(cameraIntent);
        }
    }
}
