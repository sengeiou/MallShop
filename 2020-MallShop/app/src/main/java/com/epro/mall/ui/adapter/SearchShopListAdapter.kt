package com.epro.mall.ui.adapter


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.SearchShop
import com.mike.baselib.utils.ext_Highlight
import com.mike.baselib.utils.ext_loadConnersImageWithDomain
import com.mike.baselib.utils.ext_loadImageWithDomain
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * desc: 分类的 Adapter
 */
@Deprecated("废弃")
class SearchShopListAdapter(mContext: Context, list: ArrayList<SearchShop>, layoutId: Int = R.layout.item_search_shoplist) :
        CommonAdapter<SearchShop>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: SearchShop)
    }

    var keyword=""

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: SearchShop, position: Int) {
        holder.getView<TextView>(R.id.tvShopName).text=data.shopName
        val tvShopName=holder.getView<TextView>(R.id.tvShopName)
        //tvShopName.ext_Highlight(data.shopName,keyword)
        val ivLogo=holder.getView<ImageView>(R.id.ivShopLogo)
        holder.setText(R.id.tvSale,"月销"+data.saleRange+"笔")
        holder.setText(R.id.tvDistance,data.distance.toString()+"m")
        ivLogo.ext_loadConnersImageWithDomain(data.shopLogo)
        val rvImages=holder.getView<RecyclerView>(R.id.rvImages)
        rvImages.layoutManager= LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false)
        rvImages.adapter=ShopImageAdapter(mContext,ArrayList())
        val flTags: TagFlowLayout =holder.getView(R.id.flTags)

        val tags=ArrayList<String>()
        for(t in data.goodsTypeList){
            tags.add(t.goodsTypeName)
        }
        flTags.adapter = object : TagAdapter<String>(tags) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(mContext).inflate(R.layout.item_tag, parent, false) as TextView
                tv.text = s
                return tv
            }
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }

            }
        })
    }
}
