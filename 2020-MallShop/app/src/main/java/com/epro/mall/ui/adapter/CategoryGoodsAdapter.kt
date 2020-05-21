package com.epro.mall.ui.adapter


import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.ShopGoodsCategory
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class CategoryGoodsAdapter(mContext: Context, categoryList: ArrayList<ShopGoodsCategory>, layoutId: Int=R.layout.item_category_goods) :
        CommonAdapter<ShopGoodsCategory>(mContext, categoryList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(category:ShopGoodsCategory)
    }
    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ShopGoodsCategory, position: Int) {
        holder.setText(R.id.tvCategoryName,data.goodsTypeName)
        val rvGoods=holder.getView<RecyclerView>(R.id.rvGoods)
        rvGoods.layoutManager=GridLayoutManager(mContext,3)
        rvGoods.adapter=CategoryGoodsChildAdapter(mContext,data.goodsList)
    }
}
