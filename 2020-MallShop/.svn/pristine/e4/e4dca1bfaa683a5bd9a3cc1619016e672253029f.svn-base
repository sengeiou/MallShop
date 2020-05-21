package com.epro.mall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.epro.mall.R;
import com.epro.mall.mvp.model.bean.LocationBean;
import com.epro.mall.ui.adapter.LocationListAdapter;
import com.mike.baselib.activity.BaseTitleBarSimpleActivity;
import com.mike.baselib.utils.AppBusManager;

import java.util.ArrayList;

public class LocationSelectActivity extends BaseTitleBarSimpleActivity implements View.OnClickListener {

    public static void launchForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, LocationSelectActivity.class), requestCode);
    }
    public static void launchForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), LocationSelectActivity.class), requestCode);
    }


    private Context mContext = this;
    private ListView lvArea;
    private LocationListAdapter mAdapter;
    private ArrayList<LocationBean> mArrayList = new ArrayList<>();

    @Override
    public int layoutContentId() {
        return R.layout.activity_location_select;
    }

    @Override
    public void initView() {
        super.initView();
        getTitleView().setText(getString(R.string.choose_area_address));
        lvArea = findViewById(R.id.lv_area);
        // 配置列表适配器
        lvArea.setVerticalScrollBarEnabled(false);
        lvArea.setFastScrollEnabled(false);
        mAdapter = new LocationListAdapter(mContext, mArrayList);
        lvArea.setAdapter(mAdapter);
        lvArea.setOnItemClickListener(mItemClickListener);
    }

    /**
     * 选项点击事件
     */
    public int selectPosition;
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectPosition = position;
            mAdapter.updateListView(selectPosition);
            ArrayList<LocationBean> bs = mAdapter.getList();
            LocationBean bean = bs.get(position);
            Intent data = new Intent();
            data.putExtra(AppBusManager.Companion.createJsonKey(LocationBean.class), AppBusManager.Companion.toJson(bean));
            setResult(RESULT_OK, data);
            finish();
        }
    };

    @Override
    public void initData() {
        LocationBean bean = new LocationBean(0, getString(R.string.all_hk), "");
        LocationBean bean1 = new LocationBean(1,getString(R.string.central_and_western_district),getString(R.string.hong_kong_island));
        LocationBean bean2 = new LocationBean(1,getString(R.string.wan_chai_district), getString(R.string.hong_kong_island));
        LocationBean bean3 = new LocationBean(1, getString(R.string.east_district), getString(R.string.hong_kong_island));
        LocationBean bean4 = new LocationBean(1, getString(R.string.south_area), getString(R.string.hong_kong_island));
        LocationBean bean5 = new LocationBean(2, getString(R.string.yau_tsim_mong_district),getString(R.string.kowloon_peninsula));
        LocationBean bean6 = new LocationBean(2, getString(R.string.sham_shui_po_district), getString(R.string.kowloon_peninsula));
        LocationBean bean7 = new LocationBean(2, getString(R.string.kowloon_city_district), getString(R.string.kowloon_peninsula));
        LocationBean bean8 = new LocationBean(2, getString(R.string.wong_tai_sin_district), getString(R.string.kowloon_peninsula));
        mArrayList.add(bean);
        mArrayList.add(bean1);
        mArrayList.add(bean2);
        mArrayList.add(bean3);
        mArrayList.add(bean4);
        mArrayList.add(bean5);
        mArrayList.add(bean6);
        mArrayList.add(bean7);
        mArrayList.add(bean8);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
