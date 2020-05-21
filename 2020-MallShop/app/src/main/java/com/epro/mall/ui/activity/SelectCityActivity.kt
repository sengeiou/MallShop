package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.epro.mall.R
import com.epro.mall.mvp.contract.SelectCityContract
import com.epro.mall.mvp.model.bean.City
import com.epro.mall.mvp.model.cn.CNPinyin
import com.epro.mall.mvp.presenter.SelectCityPresenter
import com.epro.mall.ui.adapter.CityAdapter
import com.epro.mall.ui.adapter.stickyheader.StickyHeaderDecoration
import com.epro.mall.ui.view.CharIndexView
import com.epro.mall.utils.MallConst
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_putJsonExtra
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.activity_select_city.*


class SelectCityActivity : BaseTitleBarCustomActivity<SelectCityContract.View, SelectCityPresenter>(), SelectCityContract.View {
    override fun onGetCitiesSuccess() {

    }

    companion object {
        const val TAG = "getCities"

        fun launchForResult(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, SelectCityActivity::class.java), requestCode)
        }
    }

    override fun getPresenter(): SelectCityPresenter {
        return SelectCityPresenter()
    }

    override fun onGetCitiesSuccess(cnpCityList: ArrayList<CNPinyin<City>>) {
        val first = CNPinyin<City>(City("", "", "", ""))//热门城市占位
        first.firstChar = CNPinyin.DEF_CHAR
        cnpCityList.add(0, first)
        cityAdapter!!.setData(cnpCityList)
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_select_city
    }

    override fun initData() {
        mPresenter.getCities(MallConst.GET_LOCAL_CITY_LIST)
        //mPresenter.getCityList(MallConst.GET_CITY_LIST)
    }

    var cityAdapter: CityAdapter? = null
    override fun initView() {
        super.initView()
        rvCities.layoutManager = LinearLayoutManager(this)
        cityAdapter = CityAdapter(this, ArrayList(), object : MultipleType<CNPinyin<City>> {
            override fun getLayoutId(item: CNPinyin<City>, position: Int): Int {
                return if (position == 0) {
                    R.layout.item_city_first
                } else {
                    R.layout.item_city
                }
            }
        })
        rvCities.adapter = cityAdapter
        rvCities.addItemDecoration(StickyHeaderDecoration(cityAdapter))
        getTitleView().text = "选择城市"
        cityAdapter!!.onItemClickListener = object : CityAdapter.OnItemClickListener {
            override fun onItemClick(city: City, position: Int) {
                setResult(Activity.RESULT_OK, Intent().ext_putJsonExtra(city))
                finish()
            }
        }
    }

    override fun initListener() {
        charIndexView.setOnCharIndexChangedListener(object : CharIndexView.OnCharIndexChangedListener {
            override fun onCharIndexChanged(currentIndex: Char) {
                for (i in cityAdapter!!.mData.indices) {
                    if (cityAdapter!!.mData[i].firstChar == currentIndex) {
                        (rvCities.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                        return
                    }
                }
            }

            override fun onCharIndexSelected(currentIndex: String?) {
                if (currentIndex == null) {
                    tvIndex.visibility = View.INVISIBLE
                } else {
                    tvIndex.visibility = View.VISIBLE
                    tvIndex.text = currentIndex
                }
            }
        })
    }

}


