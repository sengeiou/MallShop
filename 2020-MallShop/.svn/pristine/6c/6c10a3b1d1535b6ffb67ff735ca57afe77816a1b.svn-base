package com.epro.mall.ui.fragment

import android.os.Bundle
import com.epro.mall.R
import com.mike.baselib.fragment.BaseSimpleFragment
import kotlinx.android.synthetic.main.fragment_common_web.*
import java.net.URLDecoder


class CommonWebFragment : BaseSimpleFragment() {
    override fun layoutContentId(): Int {
        return R.layout.fragment_common_web
    }

    override fun lazyLoad() {
    }

    companion object {
        const val TAG = "CommonWebFragment"
        fun newInstance(url: String): CommonWebFragment {
            val args = Bundle()
            args.putString(TAG, url)
            val fragment = CommonWebFragment()
            fragment.setArguments(args)
            return fragment
        }

    }

    fun loadData(desc: String) {
        val de = URLDecoder.decode(desc, "UTF-8")
        webViewWrapper.webView.loadData(de, "text/html; charset=UTF-8", "UTF-8")
    }

    fun loadUrl(url: String) {
        webViewWrapper.loadUrl(url)
    }

    override fun initData() {
        var url = ""
        if (arguments != null) {
            url = arguments!!.getString(TAG)!!
        }
        webViewWrapper.loadUrl(url)
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