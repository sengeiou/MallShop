package com.epro.mall.ui.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.epro.mall.R
import com.mike.baselib.utils.ext_isPureNumber_orDecimal
import kotlinx.android.synthetic.main.layout_modify_num.view.*

class ModifyNumberView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            tvPlus -> {
                var num = etNum.text.toString().toInt()
                num++
                etNum.setText(num.toString())
                onNumModifyListner?.onNumModify(num, MODIFY_TYPE_PLUS)
            }
            tvMinus -> {
                var num = etNum.text.toString().toInt()
                num--
                etNum.setText(num.toString())
                onNumModifyListner?.onNumModify(num, MODIFY_TYPE_MINUS)
            }
        }
    }

    companion object{
        const val MODIFY_TYPE_PLUS=1//增加
        const val MODIFY_TYPE_MINUS=2 //减少
        const val MODIFY_TYPE_INPUT=3 //输入
    }

    var svHint = ""

    var hint = ""
        set(value) {
            // etSearch.setHint(value)
        }

    init {
//        val t = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomSearchView)
//        svHint = t.getNonResourceString(R.styleable.CustomSearchView_custom_sv_hint)
//        t.recycle()
        initView()
    }

    interface OnNumModifyListner {
        fun onNumModify(num:Int,modifyType:Int)
    }

    var onNumModifyListner: OnNumModifyListner? = null
    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_modify_num, this, true)
        tvPlus.setOnClickListener(this)
        tvMinus.setOnClickListener(this)
    }

    inner class NumChangerWatcher() : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var num = 0
            if (p0.toString().ext_isPureNumber_orDecimal() && p0.toString().toInt() != 0) {
                num=p0.toString().toInt()
            } else {
                num=1
                etNum.setText(num.toString())
            }
            onNumModifyListner?.onNumModify(num, MODIFY_TYPE_INPUT)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

}