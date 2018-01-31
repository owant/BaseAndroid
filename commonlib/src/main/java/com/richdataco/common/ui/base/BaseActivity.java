package com.richdataco.common.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.richdataco.common.BaseApplication;

/**
 * Created by OlaWang on 2017/5/2.
 * flow{
 * <p>
 * {@link #onBaseIntent()}
 * {@link #onBasePreLayout()}
 * {@link #onBaseLayoutId(Bundle)}
 * {@link #onBaseBindView()}
 * {@link #onBaseLoadData()}
 * }
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBaseIntent();

        onBasePreLayout();

        setContentView(onBaseLayoutId(savedInstanceState));

        onBaseBindView();

        onBaseLoadData();
    }

    /**
     * get the intent information,and init the msg from intent
     */
    protected abstract void onBaseIntent();

    /**
     * before {@link #onBaseLayoutId(Bundle)}
     */
    protected abstract void onBasePreLayout();

    /**
     * init the layout id
     *
     * @return id
     */
    protected abstract int onBaseLayoutId(@Nullable Bundle savedInstanceState);

    /**
     * binding the view
     */
    protected abstract void onBaseBindView();

    /**
     * loading data from SQLite or Cloud
     */
    protected abstract void onBaseLoadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
