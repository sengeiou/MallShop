package com.epro.mall.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.epro.mall.R
import com.epro.mall.ui.view.WebViewWrapper
import com.mike.baselib.fragment.BaseSimpleFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_loadGif
import kotlinx.android.synthetic.main.fragment_goods_webdetail.*
import java.net.URLDecoder


class GoodsWebDetailFragment : BaseSimpleFragment() {
    override fun layoutContentId(): Int {
        return R.layout.fragment_goods_webdetail
    }

    override fun lazyLoad() {
    }

    companion object {
        const val TAG = "GoodsWebDetailFragment"
        fun newInstance(str: String): GoodsWebDetailFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = GoodsWebDetailFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


    fun loadData(desc: String) {
        var de = URLDecoder.decode(desc, "UTF-8")
        de = "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1\">" + de
        logTools.d(de)
        webViewWrapper.webView.loadData(de, "text/html; charset=UTF-8", "UTF-8")
        var params = webViewWrapper.multipleStatusView.layoutParams
        params.height = DisplayManager.dip2px(500F)!!
        webViewWrapper.setOnWebViewHeightChangeListener(object : WebViewWrapper.OnWebViewHeightChangeListener {
            override fun onWebViewHeightChange(height: Int, progress: Int) {
                if (webViewWrapper != null) {
                    if (height > DisplayManager.dip2px(500F)!!) {
                         params.height = height
                        if (progress <= 100) {
                            webViewWrapper.showLoading()
                        }
                    }
                    webViewWrapper.multipleStatusView.layoutParams = params
                }
            }
        })
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun onResume() {
        super.onResume()
        webViewWrapper.onResume()
    }

    override fun onPause() {
        super.onPause()
        webViewWrapper.onPause()
    }

    override fun onDestroyView() {
        webViewWrapper.onDestroy()
        super.onDestroyView()
    }
}