package com.epro.mall.ui.fragment

import android.os.Bundle
import android.view.View
import com.epro.mall.mvp.contract.SearchShopListContract
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.mvp.model.bean.SearchShop
import com.epro.mall.mvp.presenter.SearchShopListPresenter
import com.epro.mall.ui.activity.ShopDetailActivity
import com.epro.mall.ui.adapter.SearchShopListAdapter
import com.epro.mall.ui.adapter.SearchShopListAdapter1
import com.epro.mall.utils.MallConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.ext_putJsonExtra

/**
 *  搜索结果中店铺列表
 */
class SearchShopListFragment : BaseTitleBarListFragment<SearchShop, SearchShopListContract.View
        , SearchShopListPresenter, SearchShopListAdapter1>(), SearchShopListContract.View, View.OnClickListener {
    override fun onClick(p0: View?) {
    }

    companion object {
        const val TAG = "ShopList"
        const val KEYWORD = "keyword"
        fun newInstance(keyword: String, location: Location): SearchShopListFragment {
            val args = Bundle()
            args.putString(KEYWORD, keyword)
            args.ext_putJsonExtra(location)
            val fragment = SearchShopListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getListAdapter(list: ArrayList<SearchShop>): SearchShopListAdapter1 {
        return SearchShopListAdapter1(activity!!, list)
    }

    override fun getListDataSuccess(list: List<SearchShop>, type: String) {
        val keyword = arguments!!.getString(KEYWORD)
        //listDataAdapter!!.keyword=keyword!!
        super.getListDataSuccess(list, type)
    }

    override fun getListData() {
        logTools.d("do here")
        val location = arguments!!.ext_getFromJsonWithClassKey(Location::class.java)
        val keyword = arguments!!.getString(KEYWORD)
        mPresenter.searchShopList(keyword!!, location!!.longitude, location!!.latitude, page, MallConst.SEARCH_SHOPLIST)
    }

    override fun initData() {
    }

    override fun getPresenter(): SearchShopListPresenter {
        return SearchShopListPresenter()
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        getRefreshView().setEnableLoadMore(true)
        listDataAdapter?.onItemClickListener = object : SearchShopListAdapter1.OnItemClickListener {
            override fun onClick(item: SearchShop) {
                ShopDetailActivity.launchWithShopId(activity!!, item.shopId)
            }
        }
    }

    override fun initListener() {
    }

}


