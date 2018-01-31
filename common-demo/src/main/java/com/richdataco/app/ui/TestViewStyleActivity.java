package com.richdataco.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.richdataco.app.R;
import com.richdataco.common.ui.base.BaseActivity;

/**
 * Created by Dell on 2018/1/29.
 */

public class TestViewStyleActivity extends BaseActivity {

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test_view_style;
    }

    @Override
    protected void onBaseBindView() {

    }

    @Override
    protected void onBaseLoadData() {

    }

    public void changeStyle(View view) {
        //setTextAppearance是不能改变背景的
        ((TextView) view).setTextAppearance(TestViewStyleActivity.this, R.style.TextStyle_WithoutBroad);
        ((TextView) view).setBackgroundDrawable(getResources().getDrawable(R.drawable.text_drawable_broad));
    }
}
