package com.mike.baselib.fragment

import android.view.LayoutInflater
import com.mike.baselib.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_base_simple.*

abstract class BaseSimpleFragment: BaseLazyLoadFragment<IBaseView, IPresenter<IBaseView>>(){
    override fun getLayoutId(): Int {
       return R.layout.activity_base_simple
    }

    override fun initView() {
        flContent.removeAllViews()
        flContent.addView(LayoutInflater.from(activity).inflate(layoutContentId(),null))
    }
    /**
     *  加载内容布局
     */
    abstract fun layoutContentId(): Int

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
    }

    override fun getPresenter(): BasePresenter<IBaseView> {
        return BasePresenter()
    }
}


