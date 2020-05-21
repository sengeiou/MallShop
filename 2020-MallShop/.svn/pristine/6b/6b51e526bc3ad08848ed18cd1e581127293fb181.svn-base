package com.epro.mall.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.ShopCategoryContract
import com.epro.mall.mvp.model.bean.ShopGoodsCategory
import com.epro.mall.mvp.presenter.ShopCategoryPresenter
import com.epro.mall.ui.adapter.CategoryAdapter
import com.epro.mall.ui.adapter.CategoryGoodsAdapter
import com.epro.mall.utils.MallConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.fragment_shop_category.*


class ShopCategoryFragment : BaseTitleBarCustomFragment<ShopCategoryContract.View, ShopCategoryPresenter>(), ShopCategoryContract.View {

    val shopGoodsCategoryList=ArrayList<ShopGoodsCategory>()
    override fun onGetShopGoodsCategoryListSuccess(shopGoodsCategoryList: ArrayList<ShopGoodsCategory>) {
        this.shopGoodsCategoryList.clear()
        this.shopGoodsCategoryList.addAll(shopGoodsCategoryList)
        categoryGoodsAdapter!!.setData(shopGoodsCategoryList)
        categoryAdapter!!.setData(shopGoodsCategoryList)
    }

    companion object {
        const val TAG = "getShopGoodsCategoryList"
        const val SHOP_ID = "shop_id"
        fun newInstance(shopId: String): ShopCategoryFragment {
            val args = Bundle()
            args.putString(SHOP_ID, shopId)
            val fragment = ShopCategoryFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): ShopCategoryFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): ShopCategoryPresenter {
        return ShopCategoryPresenter()
    }


    override fun layoutContentId(): Int {
        return R.layout.fragment_shop_category
    }

    override fun initData() {

    }

    var categoryAdapter: CategoryAdapter? = null
    var categoryGoodsAdapter: CategoryGoodsAdapter? = null
    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        rvCategory.layoutManager = LinearLayoutManager(activity!!)
        categoryAdapter = CategoryAdapter(activity!!, ArrayList())
        rvCategory.adapter = categoryAdapter
        categoryAdapter!!.onItemClickListener = object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(category: ShopGoodsCategory, position: Int) {
                (rvGoods.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            }
        }
        rvGoods.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = rvGoods.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                val adapter = rvCategory.adapter as CategoryAdapter
                for (i in adapter.mData.indices) {
                    adapter.mData[i].judgeValue = i == position
                }
                adapter.notifyDataSetChanged()
            }
        })

        rvGoods.layoutManager = LinearLayoutManager(activity!!)
        categoryGoodsAdapter = CategoryGoodsAdapter(activity!!, ArrayList())
        rvGoods.adapter = categoryGoodsAdapter

        categoryGoodsAdapter!!.setData(shopGoodsCategoryList)
        categoryAdapter!!.setData(shopGoodsCategoryList)

    }

    override fun onDestroyView() {
        rvGoods.clearOnScrollListeners()
        super.onDestroyView()
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
        val shopId = arguments!!.getString(SHOP_ID)
        mPresenter.getShopGoodsCategoryList(shopId!!, MallConst.GET_SHOP_GOODSLIST_CATEGORY)
    }

}


