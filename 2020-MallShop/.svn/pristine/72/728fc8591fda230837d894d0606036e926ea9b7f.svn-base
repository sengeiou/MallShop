package com.mike.baselib.activity

import com.mike.baselib.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter


abstract class BaseTitleBarSimpleActivity: BaseTitleBarActivity<IBaseView, IPresenter<IBaseView>>(){
    override fun layoutCustomContentId(): Int {
        return R.layout.activity_base_titlebar_simple
    }

    override fun getPresenter(): BasePresenter<IBaseView> {
      return BasePresenter()
    }
}


