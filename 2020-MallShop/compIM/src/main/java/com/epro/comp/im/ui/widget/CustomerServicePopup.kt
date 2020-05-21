package com.epro.comp.im.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.epro.comp.im.R
import razerdp.basepopup.BasePopupWindow

class CustomerServicePopup (context: Context): BasePopupWindow(context){

    override fun onCreateContentView(): View {
        val view  = LayoutInflater.from(context).inflate(R.layout.customer_service_list,null)
        return  view
    }

}