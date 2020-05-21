package com.epro.mall.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.ShopNewGoodsListContract
import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.epro.mall.mvp.presenter.ShopNewGoodsListPresenter
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.ui.adapter.ShopNewGoodsListAdapter
import com.epro.mall.utils.MallConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.mike.baselib.utils.DisplayManager
import org.jetbrains.anko.backgroundColor


/**
 * 店铺中商品列表页面
 */

class ShopNewGoodsListFragment : BaseTitleBarListFragment<GetShopNewGoodsListBean.NewGoods, ShopNewGoodsListContract.View
        , ShopNewGoodsListPresenter, ShopNewGoodsListAdapter>(), ShopNewGoodsListContract.View {

    companion object {
        const val TAG = "GoodsList"
        const val SHOP_ID = "shop_id"
        fun newInstance(shopId: String): ShopNewGoodsListFragment {
            val args = Bundle()
            args.putString(SHOP_ID, shopId)
            val fragment = ShopNewGoodsListFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): ShopNewGoodsListFragment {
            return newInstance("")
        }
    }


    override fun getListAdapter(list: ArrayList<GetShopNewGoodsListBean.NewGoods>): ShopNewGoodsListAdapter {
        return ShopNewGoodsListAdapter(activity!!, list)
    }

    override fun getListData() {

        val shopId=arguments!!.getString(SHOP_ID)
         mPresenter.getShopNewGoodsList(shopId!!,MallConst.GET_SHOP_GOODSLIST_NEW)
    }

    override fun initData() {
    }

    override fun getPresenter(): ShopNewGoodsListPresenter {
        return ShopNewGoodsListPresenter()
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility= View.GONE
        getRvListView().layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val padding=DisplayManager.dip2px(6F)!!
        getRvListView().setPadding(padding,0,padding,0)
        listDataAdapter?.onItemClickListener = object : ShopNewGoodsListAdapter.OnItemClickListener {
            override fun onClick(item: GetShopNewGoodsListBean.NewGoods) {
               GoodsDetailActivity.launchWithGoodsId(activity!!,item.goodsId)
            }
        }
        getRefreshView().setEnableLoadMore(false)
    }

    override fun initListener() {
    }


}


