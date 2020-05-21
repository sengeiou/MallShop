package com.mike.baselib.fragment

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.mike.baselib.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import org.jetbrains.anko.support.v4.toast
import razerdp.basepopup.BasePopupWindow


abstract class BaseBottomPopup : BaseDialogFragment<IBaseView, IPresenter<IBaseView>>() {

    override fun getPresenter(): IPresenter<IBaseView> {
        return BasePresenter()
    }

    //在DialogFragment中

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomDialog) //dialog全屏
    }
//    var rootView:View?=null
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = Dialog(activity!!, R.style.BottomDialog)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 设置Content前设定
//        rootView=LayoutInflater.from(activity!!).inflate(getLayoutId(), null)
//        dialog.setContentView(rootView!!)
//        dialog.setCanceledOnTouchOutside(true) // 外部点击取消
//        // 设置宽度为屏宽, 靠近屏幕底部。
//        val window = dialog.window
//        val lp = window!!.attributes
//       // window.setWindowAnimations(R.style.dialogWindowAnim)
//        lp.gravity = Gravity.BOTTOM // 紧贴底部
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT // 宽度持平
//        window.attributes = lp
//        return dialog
//    }


    private var isBackgroundTransparent=false
    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window!!.attributes
        params.gravity = Gravity.BOTTOM
        if(isBackgroundTransparent){
            params.dimAmount =0f//设置黑暗背景透明度
        }
        // params.alpha=1f//设置内容透明度
        window.setWindowAnimations(R.style.dialogWindowAnim)
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        // return inflater.inflate(getLayoutId(), null) //布局的高度会无效 wrap_content
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slideToUp(view)
    }

    private fun slideToUp(view: View) {
        val animation = AnimationUtils.loadAnimation(activity,
                R.anim.slide_in_bottom)
        animation.fillAfter = true
        view.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
//        view!!.isFocusableInTouchMode = true
//        view!!.requestFocus()
//        view!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
//                if (keyEvent.action === KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
//                    //dismissWithAnim()
//                    dismiss()
//                    return true
//                }
//                return false
//            }
//        })
//        view!!.setOnClickListener(View.OnClickListener {
//            dismiss()
//        })
    }

    override fun dismiss() {
        super.dismiss()
//        view?.postDelayed(Runnable {
//
//        },300)
//        val animation = AnimationUtils.loadAnimation(activity,
//                R.anim.slide_out_bottom)
//        animation.fillAfter = true
//        view!!.startAnimation(animation)
    }

    class Builder(bottomPopup: BaseBottomPopup) {
        private var bottomPopup: BaseBottomPopup? = null

        init {
            this.bottomPopup = bottomPopup
        }

        fun setBackgroundTransparent(isBackgroundTransparent: Boolean): Builder {
            bottomPopup?.isBackgroundTransparent = isBackgroundTransparent
            return this
        }

        fun putBundle(bundle: Bundle):Builder{
            bottomPopup?.arguments=bundle
            return this
        }
        fun putString(key:String,value:String?):Builder{
            var bundle=bottomPopup?.arguments
            if(bundle==null){
                bundle=Bundle()
                bottomPopup?.arguments=bundle
            }
            bundle.putString(key,value)
            return this
        }

        fun setOnPopupDismissListener(onPopupDismissListener: OnPopupDismissListener):Builder{
            bottomPopup?.onPopupDismissListener=onPopupDismissListener
            return this
        }

        fun create(): BaseBottomPopup {
            return bottomPopup!!
        }
    }

    interface OnPopupDismissListener {
        fun onPopupDismiss(bundle: Bundle?)
    }

    var onPopupDismissListener: OnPopupDismissListener? = null

}