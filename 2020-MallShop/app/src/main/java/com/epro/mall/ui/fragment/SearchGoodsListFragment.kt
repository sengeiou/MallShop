package com.epro.mall.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.epro.mall.mvp.contract.SearchGoodsListContract
import com.epro.mall.mvp.model.bean.SearchGoods
import com.epro.mall.mvp.presenter.SearchGoodsListPresenter
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.ui.adapter.SearchGoodsListAdapter
import com.epro.mall.utils.MallConst
import com.mike.baselib.utils.DisplayManager

class SearchGoodsListFragment : BaseTitleBarListFragment<SearchGoods, SearchGoodsListContract.View
        , SearchGoodsListPresenter, SearchGoodsListAdapter>(), SearchGoodsListContract.View {

    companion object {
        const val TAG = "searchGoodsList"
        const val KEYWORD = "keyword"
        const val SHOP_ID="shopId"
        fun newInstance(keyword: String,shopId:String?): SearchGoodsListFragment {
            val args = Bundle()
            args.putString(KEYWORD, keyword)
            args.putString(SHOP_ID,shopId)
            val fragment = SearchGoodsListFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getListDataSuccess(list: List<SearchGoods>, type: String) {
        val keyword = arguments!!.getString(KEYWORD)
        listDataAdapter!!.keyword=keyword!!
        super.getListDataSuccess(list, type)
    }



    override fun getListAdapter(list: ArrayList<SearchGoods>): SearchGoodsListAdapter {
        return SearchGoodsListAdapter(activity!!, list)
    }

    override fun getListData() {
         val keyword=arguments!!.getString(KEYWORD)
         val shopId=arguments!!.getString(SHOP_ID)
         mPresenter.searchGoodsList(keyword!!,page,shopId,MallConst.SEARCH_GOODSLIST)
    }

    override fun initData() {
    }

    override fun getPresenter(): SearchGoodsListPresenter {
        return SearchGoodsListPresenter()
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility= View.GONE
        getRvListView().layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val padding= DisplayManager.dip2px(6F)!!
        getRvListView().setPadding(padding,0,padding,0)
        getRefreshView().setEnableLoadMore(true)
        listDataAdapter?.onItemClickListener = object : SearchGoodsListAdapter.OnItemClickListener {
            override fun onClick(item: SearchGoods) {
                GoodsDetailActivity.launchWithGoodsId(activity!!,item.goodsId)
            }
        }
    }

    override fun initListener() {
    }


}


