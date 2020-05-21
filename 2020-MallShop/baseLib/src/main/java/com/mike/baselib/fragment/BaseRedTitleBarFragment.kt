package com.mike.baselib.fragment

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mike.baselib.R
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_base_red_titlebar.*
import kotlinx.android.synthetic.main.layout_red_titlebar.*

abstract class BaseRedTitleBarFragment<V: IBaseView,P: IPresenter<V>>: BaseLazyLoadFragment<V, P>(){

    override fun getLayoutId(): Int {
       return R.layout.activity_base_red_titlebar
    }

    override fun initView() {
        flContent.removeAllViews()
        if (customTitleBar() == 0) {
            rlLeft.setOnClickListener(View.OnClickListener {
                activity?.finish()
            })
        } else {
            rlTitleBar.removeAllViews()
            rlTitleBar.addView(LayoutInflater.from(activity).inflate(customTitleBar(), rlTitleBar,false))
        }
        val customContentView=LayoutInflater.from(activity).inflate(layoutCustomContentId(),null)
        val contentView=customContentView.findViewById<FrameLayout>(R.id.flContent)
        if(contentView!=null){
            contentView.removeAllViews()
            contentView.addView(LayoutInflater.from(activity).inflate(layoutContentId(),null))
        }
        flContent.addView(customContentView)
    }

    open fun customTitleBar(): Int {
        return 0
    }

    abstract fun layoutCustomContentId(): Int

    abstract fun layoutContentId(): Int

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
    }

    /**
     * 中间标题
     */
    fun getTitleView(): TextView {
        return tvTitle
    }

    /**
     * 右边标题
     */
    fun getRightView(): TextView {
        return tvRight
    }

    fun setRightImageResource(res:Int){
        val rigntDrawable = resources?.getDrawable(res)
        rigntDrawable?.bounds = Rect(0, 0, rigntDrawable?.minimumWidth!!, rigntDrawable?.minimumHeight!!)
        tvRight.setCompoundDrawables(null, rigntDrawable, null, null)
    }

    /**
     * 左边标题
     */
    fun getLeftView(): RelativeLayout {
        return rlLeft
    }

    /**
     * 左边文字
     */
    fun getLeftTitleView(): TextView {
        return tvLeftTitle
    }

    /**
     * 左边返回键
     */

    fun getLeftBackView(): ImageView {
        return ivLeft
    }

    fun getTitleBar():RelativeLayout{
        return rlTitleBar
    }
}


