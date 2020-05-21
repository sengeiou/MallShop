package com.epro.mall.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import com.epro.mall.R
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ext_loadImage
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader


/**
 * desc: 分类的 Adapter
 */

class GoodsDetailAdapter(mContext: Context, list: ArrayList<String>, layoutId: MultipleType<String>) :
        CommonAdapter<String>(mContext, list, layoutId) {

    var webViewHoder:ViewHolder?=null

    interface OnItemClickListener {
        fun onClick(item: String)
    }

    val imageList=ArrayList<String>()

    init {
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565342060345&di=308ee31fce991ab33cd8e3d16d9a4355&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fb1cce6f996734bdbb9b3fb9ef7705deabc980e35493b-ysf8BZ_fw658")
        imageList.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b5e4c905865494ee982209191df4e0e1/c2cec3fdfc03924590b2a9b58d94a4c27d1e2500.jpg")
        for(i in 0..1){
          list.add(""+i)
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==R.layout.item_goodsdetail_bottom){
             webViewHoder=super.onCreateViewHolder(parent, viewType)
            return webViewHoder!!
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    /**
     * 绑定数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        if(position==0){
            val banner= holder.getView<Banner>(R.id.banner)
            banner.setImages(imageList).setImageLoader(GlideImageLoader()).start()

        }else if (position==1){
           val wvGoodsDetail=holder.getView<WebView>(R.id.wvGoodsDetail)
          //  wvGoodsDetail.loadUrl("https://api.caseier.net/store-h5/details?id=212")
            wvGoodsDetail.settings.javaScriptEnabled=true
            wvGoodsDetail.loadUrl("https://item.m.jd.com/ware/view.action?wareId=7513407&clickUrl=undefined")
        }else{

        }

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }

     inner class GlideImageLoader :ImageLoader(){
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
             imageView?.ext_loadImage(path as String?)
        }

        override fun createImageView(context: Context?): ImageView {
            return super.createImageView(context)
        }
    }
}
