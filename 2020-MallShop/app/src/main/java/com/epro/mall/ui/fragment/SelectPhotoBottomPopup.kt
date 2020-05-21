package com.epro.mall.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.SelectPhotoBean
import com.epro.mall.ui.adapter.SelectPhotoAdapter
import com.mike.baselib.fragment.BaseBottomPopup
import kotlinx.android.synthetic.main.bottompopup_selsect_photo.*

class SelectPhotoBottomPopup : BaseBottomPopup(){

    companion object{
        const val MODE = "MODE"
        fun newInstance(): SelectPhotoBottomPopup {
            val args = Bundle()
            val fragment = SelectPhotoBottomPopup()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.bottompopup_selsect_photo
    }

    override fun initView() {

    }

    override fun initData() {
        rvPhotoModes.layoutManager = LinearLayoutManager(activity)
        rvPhotoModes.adapter = SelectPhotoAdapter(activity!!, ArrayList())
        (rvPhotoModes.adapter as SelectPhotoAdapter).onItemClickListener = object:SelectPhotoAdapter.OnItemClickListener{
            override fun onClick(item: SelectPhotoBean) {
                if(item.icon!=2){
                    arguments?.putString(MODE,""+item.icon)
                }
                dismiss()
            }

        }
    }

    override fun initListener() {

    }
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onPopupDismissListener?.onPopupDismiss(arguments)
    }

}