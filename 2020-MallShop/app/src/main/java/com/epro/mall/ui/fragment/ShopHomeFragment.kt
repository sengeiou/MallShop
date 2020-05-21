package com.epro.mall.ui.fragment;

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import com.epro.mall.R
import com.epro.mall.mvp.contract.ShopHomeContract
import com.epro.mall.mvp.model.bean.GetShopHomeViewBean
import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.mvp.presenter.ShopHomePresenter
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.ui.adapter.RecomGoodsAdapter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_loadConnersImageWithDomain
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_shop_home.*


class ShopHomeFragment : BaseTitleBarCustomFragment<ShopHomeContract.View, ShopHomePresenter>(), ShopHomeContract.View {
    override fun lazyLoad() {
    }

    override fun onGetShopGoodsListHotSuccess(goodsList: ArrayList<Goods>) {
    }

    override fun onGetShopGoodsListRecommendSuccess(goodsList: ArrayList<Goods>) {
    }

    fun updateData(hotsGoods: ArrayList<GetShopHomeViewBean.HotsGoods>, recomms: ArrayList<GetShopHomeViewBean.RecommendGoods>) {
        imageList.clear()
        for (h in hotsGoods) {
            imageList.add(h.shopGoodsImage + "*" + h.goodsId)
        }
        banner.setImages(imageList).setImageLoader(GlideImageLoader()).setDelayTime(3000).start()
        ( rvGoods.adapter as RecomGoodsAdapter).setData(recomms)
    }

    companion object {
        const val TAG = "ShopHome"
        const val SHOP_ID = "shop_id"
        fun newInstance(shopId: String): ShopHomeFragment {
            val args = Bundle()
            args.putString(SHOP_ID, shopId)
            val fragment = ShopHomeFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): ShopHomeFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): ShopHomePresenter {
        return ShopHomePresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_shop_home
    }

    val imageList = ArrayList<String>()
    override fun initData() {
        rvGoods.layoutManager = GridLayoutManager(activity, 2)
        rvGoods.adapter = RecomGoodsAdapter(activity!!, ArrayList())
        (rvGoods.adapter as RecomGoodsAdapter).onItemClickListener = object : RecomGoodsAdapter.OnItemClickListener {
            override fun onClick(item: GetShopHomeViewBean.RecommendGoods) {
                GoodsDetailActivity.launchWithGoodsId(activity!!, item.goodsId)
            }
        }
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        val params = banner.layoutParams
        params.height = ((DisplayManager.getScreenWidth()!! - 2 * DisplayManager.dip2px(12F)!!) * (186F / 351)).toInt()
        params.width = DisplayManager.getScreenWidth()!!
        banner.layoutParams = params
    }

    override fun initListener() {
    }

    inner class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            val padding = DisplayManager.dip2px(12.toFloat())
            imageView?.setPadding(padding!!, 0, padding, 0)
            //imageView?.ext_loadImage(R.mipmap.shop_home_page_banner)
            imageView?.ext_loadConnersImageWithDomain((path as String).split("*")[0],10F,R.mipmap.bg_long_empty)
            imageView!!.setOnClickListener(View.OnClickListener {
                GoodsDetailActivity.launchWithGoodsId(activity!!, (path as String).split("*")[1])
            })
        }

        override fun createImageView(context: Context?): ImageView {
            return super.createImageView(context)
        }
    }

}


