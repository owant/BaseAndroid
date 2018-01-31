package com.richdataco.app;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.richdataco.app.ui.TestBindViewToolsActivity;
import com.richdataco.app.ui.TestImageCropActivity;
import com.richdataco.app.ui.TestMPermissionActivity;
import com.richdataco.app.ui.TestRoundViewActivity;
import com.richdataco.app.ui.TestSortListViewActivity;
import com.richdataco.app.ui.TestNetActivity;
import com.richdataco.app.ui.TestViewStyleActivity;
import com.richdataco.common.ui.base.BaseFragment;

/**
 * Created by OlaWang on 2017/5/8.
 */

public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private Class[] testClass = new Class[]{
            TestRoundViewActivity.class,
            TestSortListViewActivity.class,
            TestBindViewToolsActivity.class,
            TestMPermissionActivity.class,
            TestNetActivity.class,
            TestImageCropActivity.class,
            TestViewStyleActivity.class
    };

    private ListView mListView;
    private BaseAdapter mBaseAdapter;


    @Override
    protected int onBaseLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onBaseBindView() {
        mListView = (ListView) getView().findViewById(R.id.main_lv_all_tests);
        mBaseAdapter = new ListAdapter();
        mListView.setAdapter(mBaseAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void onBaseLoadData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent nextIntent = new Intent(getActivity(), testClass[position]);
        startActivity(nextIntent);
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return testClass.length;
        }

        @Override
        public Object getItem(int position) {
            return testClass[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
                mHolder = new ViewHolder();
                mHolder.text = (TextView) convertView;
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.text.setText(testClass[position].getSimpleName());
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView text;
    }
}
