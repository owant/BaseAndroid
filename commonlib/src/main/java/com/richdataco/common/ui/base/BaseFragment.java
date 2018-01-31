package com.richdataco.common.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by OlaWang on 2017/5/2.
 * flows{
 * {@link #onBaseLayoutId()}
 * {@link #onBaseBindView()}
 * {@link #onBaseLoadData()}
 * <p>
 * }
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tabView = inflater.inflate(onBaseLayoutId(), container, false);
        return tabView;
    }

    protected abstract int onBaseLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onBaseBindView();

        onBaseLoadData();
    }

    /**
     * bindView
     */
    protected abstract void onBaseBindView();

    /**
     * loading data from SQLite or Cloud
     */
    protected abstract void onBaseLoadData();

}
