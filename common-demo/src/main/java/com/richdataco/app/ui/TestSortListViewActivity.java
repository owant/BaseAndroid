package com.richdataco.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.richdataco.app.R;
import com.richdataco.common.ui.base.BaseActivity;
import com.richdataco.common.widget.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by OlaWang on 2017/5/9.
 */

public class TestSortListViewActivity extends BaseActivity {

    DragSortListView mDragSortListView;

    private ArrayAdapter<String> adapter;
    private String[] array;
    private ArrayList<String> list;

    private DragSortListView.DropListener onDrap = new DragSortListView.DropListener() {

        @Override
        public void drop(int from, int to) {
            String item = adapter.getItem(from);
            adapter.notifyDataSetChanged();
            adapter.remove(item);
            adapter.insert(item, to);

            Log.i("sort", "-------------------");
            for (String text : list) {
                Log.i("sort", text);
            }
        }
    };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    adapter.remove(adapter.getItem(which));
                }
            };

    private DragSortListView.DragScrollProfile ssProfile =
            new DragSortListView.DragScrollProfile() {
                @Override
                public float getSpeed(float w, long t) {
                    if (w > 0.8f) {
                        // Traverse all views in a millisecond
                        return ((float) adapter.getCount()) / 0.001f;
                    } else {
                        return 10.0f * w;
                    }
                }
            };

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sort_list_view;
    }

    @Override
    protected void onBaseBindView() {
        mDragSortListView = (DragSortListView) findViewById(R.id.test_sort_dslv_list);
        mDragSortListView.setDropListener(onDrap);
        mDragSortListView.setRemoveListener(onRemove);
        mDragSortListView.setDragScrollProfile(ssProfile);
    }

    @Override
    protected void onBaseLoadData() {
        array = getResources().getStringArray(R.array.SortData);
        list = new ArrayList<>(Arrays.asList(array));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_handle_right, R.id.text, list);
        mDragSortListView.setAdapter(adapter);
    }


}
