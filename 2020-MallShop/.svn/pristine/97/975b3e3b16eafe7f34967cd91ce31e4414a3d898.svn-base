package com.mike.baselib.activity

import android.view.LayoutInflater
import com.mike.baselib.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import kotlinx.android.synthetic.main.activity_base_simple.*

abstract class BaseSimpleActivity: BaseActivity<IBaseView, IPresenter<IBaseView>>(){

    override fun layoutId(): Int {
        return R.layout.activity_base_simple
    }

    override fun initView() {
        flContent.removeAllViews()
        flContent.addView(LayoutInflater.from(this).inflate(layoutContentId(),null))
    }

    /**
     *  加载内容布局
     */
    abstract fun layoutContentId(): Int


    override fun getPresenter(): BasePresenter<IBaseView> {
       return BasePresenter()
    }
}


