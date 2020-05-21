package com.epro.mall.ui.adapter


import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GoodsCategory
import com.epro.mall.mvp.model.bean.ShopGoodsCategory
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.mike.baselib.utils.ext_loadConnersImageWithDomain
import com.mike.baselib.utils.ext_loadImage
import com.mike.baselib.utils.ext_loadImageWithDomain
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class CategoryGoodsChildAdapter(mContext: Context, categoryList: ArrayList<ShopGoodsCategory.Goods>, layoutId: Int=R.layout.item_category_goods_child) :
        CommonAdapter<ShopGoodsCategory.Goods>(mContext, categoryList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(category:ShopGoodsCategory.Goods)
    }
    var onItemClickListener:OnItemClickListener?=null


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ShopGoodsCategory.Goods, position: Int) {
        holder.setText(R.id.tvName,data.shoppingMallName.toString())
        holder.getView<ImageView>(R.id.ivGoodsImage).ext_loadConnersImageWithDomain(data.goodsPicUrl)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onItemClick(data)
                }
                GoodsDetailActivity.launchWithGoodsId(mContext,data.goodsID)
            }
        })
    }
}
