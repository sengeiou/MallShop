package com.epro.mall.ui.adapter


import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.AdBanner
import com.epro.mall.mvp.model.bean.SearchShop
import com.epro.mall.mvp.model.bean.ShopLocation
import com.epro.mall.ui.activity.FindShopActivity
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.ui.activity.ShopDetailActivity
import com.epro.mall.utils.MallConst
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_allScientificNotation_to_formatDouble
import com.mike.baselib.utils.ext_loadConnersImageWithDomain
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoaderInterface
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import org.jetbrains.anko.backgroundResource


/**
 * desc: 分类的 Adapter
 */

class SearchShopListAdapter1(mContext: Context, list: ArrayList<SearchShop>, layoutId: Int = R.layout.item_homeshoplist2) :
        CommonAdapter<SearchShop>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: SearchShop)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: SearchShop, position: Int) {
        // holder.getView<TextView>(R.id.tvTag).visibility = View.GONE

        holder.getView<TextView>(R.id.tvShopName).text = data.shopName
        val ivShopLogo = holder.getView<ImageView>(R.id.ivShopLogo)
        ivShopLogo.ext_loadConnersImageWithDomain(data.shopLogo,5F,R.mipmap.bg_empty,ImageView.ScaleType.CENTER_CROP)
        val tvDistance = holder.getView<TextView>(R.id.tvDistance)
        if (data.distance>= 1000) {
            holder.setText(R.id.tvDistance, (data.distance.toDouble() / 1000).toString().ext_allScientificNotation_to_formatDouble(1) + "km")
        } else {
            holder.setText(R.id.tvDistance, "" + data.distance + "m")
        }
        tvDistance.setOnClickListener(View.OnClickListener {
            val shopLocationList = ArrayList<ShopLocation>()
            val shopLocation = ShopLocation("", data.latitude, data.longitude, data.shopId, data.shopName)
            shopLocationList.add(shopLocation)
            FindShopActivity.launchWithShopLocationList(mContext as Activity, shopLocationList)
        })
        holder.setText(R.id.tvSale, mContext.getString(R.string.month_sale_num) + data.saleRange.toString() + mContext.getString(R.string.pen))
        val list = ArrayList<String>()
        for (g in data.goodsTypeList!!) {
            list.add(g.goodsTypeName)
        }
        val flTags: TagFlowLayout = holder.getView(R.id.flTags)
        flTags.adapter = object : TagAdapter<String>(list) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(mContext).inflate(R.layout.item_tag, parent, false) as TextView
                tv.text = s
                return tv
            }
        }
        val rvImages = holder.getView<RecyclerView>(R.id.rvImages)
        if (data.apphomeRecommendVOList!!.isNotEmpty()) {
            rvImages.visibility = View.VISIBLE
            rvImages.layoutManager = GridLayoutManager(mContext, 3) as RecyclerView.LayoutManager?
            // rvImages.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL))
            rvImages.adapter = SearchShopRecommendGoodsAdapter(mContext, data.apphomeRecommendVOList!!)
        } else {
            rvImages.visibility = View.GONE
        }
        val llContent = holder.getView<LinearLayout>(R.id.llContent)
        val params = llContent.layoutParams as ViewGroup.MarginLayoutParams
        if (position == 0) {
            params.topMargin = DisplayManager.dip2px(12F)!!
            params.bottomMargin = DisplayManager.dip2px(6F)!!
        } else if (position == mData.size - 1) {
            params.topMargin = DisplayManager.dip2px(6F)!!
            params.bottomMargin = DisplayManager.dip2px(12F)!!
        } else {
            params.bottomMargin = DisplayManager.dip2px(6F)!!
            params.topMargin = DisplayManager.dip2px(6F)!!
        }
        llContent.layoutParams = params


        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }

    inner class GlideImageLoader : ImageLoaderInterface<LinearLayout> {
        override fun displayImage(context: Context?, path: Any?, containter: LinearLayout) {
            val imageView = containter.getChildAt(0) as ImageView
            val params = imageView.layoutParams as LinearLayout.LayoutParams
            val padding = DisplayManager.dip2px(12.toFloat())
            params.leftMargin = padding!!
            params.rightMargin = padding!!
            params.height = -1
            params.width = -1
            imageView?.layoutParams = params
            imageView?.backgroundResource = R.drawable.shape_bg_white_radius10
            //imageView?.setPadding(padding!!, 0, padding, 0)
            imageView?.ext_loadConnersImageWithDomain((path as AdBanner).goodsImage, 10F, R.mipmap.bg_long_empty)
            imageView?.setOnClickListener(View.OnClickListener {
                if ((path as AdBanner).redirect == MallConst.JUMP_TO_SHOP) {
                    ShopDetailActivity.launchWithShopId(mContext, path.shopId)
                } else {
                    GoodsDetailActivity.launchWithGoodsId(mContext, path.goodsId)
                }
            })
        }

        override fun createImageView(context: Context?): LinearLayout {
            val linearLayout = LinearLayout(context)
            linearLayout.gravity = Gravity.CENTER
            linearLayout.addView(ImageView(context))
            return linearLayout
        }
    }
}
