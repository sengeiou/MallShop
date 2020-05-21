package com.epro.mall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.epro.mall.R;
import com.epro.mall.mvp.model.bean.AreaPhoneBean;
import com.epro.mall.ui.adapter.AreaCodeListAdapter;
import com.epro.mall.ui.view.DLSideBar;
import com.epro.mall.utils.PinyinUtils;
import com.mike.baselib.activity.BaseTitleBarSimpleActivity;
import com.mike.baselib.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class AreaSelectActivity extends BaseTitleBarSimpleActivity implements View.OnClickListener{
    private Context mContext = this;
    private AreaCodeListAdapter mAdapter;
    private ArrayList<AreaPhoneBean> mBeans = new ArrayList<>();
    private ListView lvArea;
    private DLSideBar sbIndex;

    public static void launchForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, AreaSelectActivity.class), requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止输入法压缩布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void initView() {
        super.initView();
        getTitleView().setText(getString(R.string.area_select_title));
        lvArea = findViewById(R.id.lv_area);
        sbIndex = findViewById(R.id.sb_index);

        // 配置列表适配器
        lvArea.setVerticalScrollBarEnabled(false);
        lvArea.setFastScrollEnabled(false);
        mAdapter = new AreaCodeListAdapter(mContext, mBeans);
        lvArea.setAdapter(mAdapter);
        lvArea.setOnItemClickListener(mItemClickListener);
        // 配置右侧index
       sbIndex.setOnTouchingLetterChangedListener(mSBTouchListener);

    }

    @Override
    public void initData() {
        mBeans.clear();
        JSONArray array = null;
        try {
            array = new JSONArray(AppUtils.Companion.assetsFileToString(mContext,getResources().getString(R.string.area_select_json_name)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (null == array) array = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            AreaPhoneBean bean = new AreaPhoneBean();
            bean.name = array.optJSONObject(i).optString("zh");
            bean.fisrtSpell = PinyinUtils.getFirstSpell(bean.name.substring(0,1));
            bean.name_py = PinyinUtils.getPinYin(bean.name);
            bean.code = array.optJSONObject(i).optString("code");
            bean.locale = array.optJSONObject(i).optString("locale");
            bean.en_name = array.optJSONObject(i).optString("en");
            mBeans.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mBeans, new AreaPhoneBean.ComparatorPY());
        //增加常用数据
        //常用
        commonUseCountry();
        // 数据更新
        mAdapter.notifyDataSetChanged();
    }

    private void commonUseCountry() {
        AreaPhoneBean zh_hongkong = new AreaPhoneBean();
        zh_hongkong.name = mContext.getResources().getString(R.string.common_country_2);
        zh_hongkong.code = "852";
        zh_hongkong.fisrtSpell = PinyinUtils.getFirstSpell(zh_hongkong.name.substring(0,1));
        zh_hongkong.name_py = PinyinUtils.getPinYin(zh_hongkong.name);
        zh_hongkong.locale = "HK";
        zh_hongkong.en_name = "Hongkong";
        zh_hongkong.type = 1;
        mBeans.add(0, zh_hongkong);

        AreaPhoneBean zh_land = new AreaPhoneBean();
        zh_land.name = mContext.getResources().getString(R.string.common_country_1);
        zh_land.code = "86";
        zh_land.fisrtSpell = PinyinUtils.getFirstSpell(zh_land.name.substring(0,1));
        zh_land.name_py = PinyinUtils.getPinYin(zh_land.name);
        zh_land.locale = "CN";
        zh_land.en_name = "China";
        zh_land.type = 1;
        mBeans.add(0, zh_land);
    }

    /**
     * 右侧index选项监听
     */
    private DLSideBar.OnTouchingLetterChangedListener mSBTouchListener = new DLSideBar.OnTouchingLetterChangedListener() {
        @Override
        public void onTouchingLetterChanged(String str) {
            //触摸字母列时,将品牌列表更新到首字母出现的位置
            if (mBeans.size()>0){
                for (int i=0;i<mBeans.size();i++){
                    if (mBeans.get(i).fisrtSpell.compareToIgnoreCase(str) == 0) {
                        lvArea.setSelection(i);
                        break;
                    }
                }
            }
        }
    };


    /**
     * 选项点击事件
     */
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<AreaPhoneBean> bs = mAdapter.getList();
            AreaPhoneBean bean =bs.get(position);
            Intent data = new Intent();
            data.putExtra("area_code", bean.code);
            setResult(RESULT_OK, data);
            finish();
        }
    };

    /**
     * 比对筛选
     * @param filterStr
     */
    private void filterContacts(String filterStr){
        ArrayList<AreaPhoneBean> filters = new ArrayList<>();
        //遍历数组,筛选出包含关键字的item
        for (int i = 0; i < mBeans.size(); i++) {
            //过滤的条件
            if (isStrInString(mBeans.get(i).name_py,filterStr)
                    ||mBeans.get(i).name.contains(filterStr)
                    ||isStrInString(mBeans.get(i).fisrtSpell,filterStr)){
                //将筛选出来的item重新添加到数组中
                AreaPhoneBean filter = mBeans.get(i);
                filters.add(filter);
            }
        }

        //将列表更新为过滤的联系人
        mAdapter.updateListView(filters);
    }

    /**
     * 判断字符串是否包含
     * @param bigStr
     * @param smallStr
     * @return
     */
    public boolean isStrInString(String bigStr, String smallStr){
        return bigStr.toUpperCase().contains(smallStr.toUpperCase());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int layoutContentId() {
        return R.layout.activity_region_select;
    }

    @Override
    public void initListener() {

    }

}
