package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.epro.mall.R
import com.epro.mall.mvp.contract.MemberCentreContract
import com.epro.mall.mvp.model.bean.MemberCentreBean
import com.epro.mall.mvp.presenter.MemberCentrePresenter
import com.epro.mall.ui.adapter.DragTouchAdapter
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.activity_member_centre.*
import java.util.ArrayList
import java.util.Collections.swap
import kotlin.system.measureTimeMillis


class MemberCentreActivity : BaseTitleBarCustomActivity<MemberCentreContract.View, MemberCentrePresenter>(), MemberCentreContract.View {

    companion object {
        const val TAG = "MemberCentre"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, MemberCentreActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): MemberCentrePresenter {
        return MemberCentrePresenter()
    }

    override fun onMemberCentreSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_member_centre
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        pullData()
        super.onCreate(savedInstanceState)
    }

    var mMemberCentreAdpater: DragTouchAdapter? = null
    var mDataList = ArrayList<MemberCentreBean>()
    var mMoveDataList = ArrayList<MemberCentreBean>()
    override fun initView() {
        super.initView()
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mMemberCentreAdpater = DragTouchAdapter(this, recyclerView)
        recyclerView.adapter = mMemberCentreAdpater
        mMemberCentreAdpater?.notifyDataSetChanged(mDataList)
        pressDragView()
    }

    private fun pressDragView() {
        recyclerView.isLongPressDragEnabled = true // 拖拽排序，默认关闭。
        recyclerView.isItemViewSwipeEnabled = false // 侧滑删除，默认关闭。
        var mItemMoveListener: OnItemMoveListener = object : OnItemMoveListener {
            override fun onItemMove(srcHolder: RecyclerView.ViewHolder?, targetHolder: RecyclerView.ViewHolder?): Boolean { // 此方法在Item拖拽交换位置时被调用。
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder!!.itemViewType != targetHolder!!.itemViewType) return false
                // 第一个参数是要交换为之的Item，第二个是目标位置的Item。
                // 交换数据，并更新adapter。
                val fromPosition: Int = srcHolder!!.adapterPosition
                val toPosition: Int = targetHolder!!.adapterPosition
                Log.e("YB","移动前数据： "+mDataList)
                swap(mDataList, fromPosition, toPosition)
                Log.e("YB","移动后数据： "+mDataList)
                mMoveDataList = mDataList
                mMemberCentreAdpater!!.notifyItemMoved(fromPosition, toPosition)
                // 返回true，表示数据交换成功，ItemView可以交换位置。
                return true
            }

            override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) { // 此方法在Item在侧滑删除时被调用。
                // 从数据源移除该Item对应的数据，并刷新Adapter。
                val position: Int = srcHolder!!.adapterPosition
                mDataList.removeAt(position)
                mMemberCentreAdpater!!.notifyItemRemoved(position)
            }
        }
        recyclerView.setOnItemMoveListener(mItemMoveListener)
    }

    private fun pullData() {
        for (index in 0..3){
            mDataList.add(MemberCentreBean(index,"ok","会员"+index))
            logTools.t("YB").d("pullData 111 : "+ mDataList.size)
        }
    }

    override fun initListener() {
    }

}


