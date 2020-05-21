package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.mall.R
import com.mike.baselib.listener.LoginSuccessEvent
import com.epro.mall.mvp.contract.ScanCodeOrderListContract
import com.epro.mall.mvp.model.bean.ScanCodeOrderListOneBean
import com.epro.mall.mvp.presenter.ScanCodeOrderListPresenter
import com.epro.mall.ui.adapter.ScanCodeOrderAdapter
import com.epro.mall.ui.fragment.OrderListFragment
import com.epro.mall.ui.login.LoginActivity
import com.mike.baselib.view.EmptyView
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarListActivity
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppBusManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ScanCodeOrderListActivity : BaseTitleBarListActivity<ScanCodeOrderListOneBean, ScanCodeOrderListContract.View, ScanCodeOrderListPresenter, ScanCodeOrderAdapter>(), ScanCodeOrderListContract.View {

    override fun getListAdapter(list: ArrayList<ScanCodeOrderListOneBean>): ScanCodeOrderAdapter {
        return ScanCodeOrderAdapter(this, list)
    }

    override fun getListData() {
        if (AppBusManager.isLogin()) {
            mPresenter.ScanCodeOrderList(MallConst.SCAN_CODE_ORDER_LIST, shopId!!, page)
        }
    }

    companion object {
        const val TAG = "ScanCodeOrderList"
        var shopId: String? = null
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            shopId = str
            context.startActivity(Intent(context, ScanCodeOrderListActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): ScanCodeOrderListPresenter {
        return ScanCodeOrderListPresenter()
    }


    override fun initData() {

    }

    override fun initView() {
        super.initView()
        getLeftTitleView().visibility = View.GONE
        getTitleView().visibility = View.VISIBLE
        getTitleView().text = getString(R.string.scan_code_order_list)
        if (!AppBusManager.isLogin()) {
            showNotLoginView(true)
            getRefreshView().setEnableLoadMore(false)
            getRefreshView().setEnableRefresh(false)
        } else {
            getRefreshView().setEnableRefresh(true)
            getRefreshView().setEnableLoadMore(true)
        }
    }

    override fun initListener() {
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (700 == errorCode) {
            showNotLoginView(true)
        } else if (errorCode == ErrorStatus.SUCCESS) {
            if (listDataAdapter!!.mData.isEmpty()) {
                showNotLoginView(false)
            } else {
                getMultipleStatusView().showContent()
            }
        }
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        if (!AppBusManager.isLogin() && 700 == errorCode) {
            //do nothing , don't show toast
        } else {
            super.showError(errorMsg, errorCode, type)
        }
    }

    private fun showNotLoginView(visiable: Boolean = true) {
        logTools.t("YB").d(" showNotLoginView : ")
        val view = EmptyView.Builder(this)
                .setShowText1(getString(R.string.order_empty_title))
                .setShowText2(getString(R.string.login_check_order))
                .setClickLisener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        LoginActivity.launchWithForResult(this@ScanCodeOrderListActivity, OrderListFragment.ORDER_TO_LOGIN)
                    }
                })
                .create()
        getMultipleStatusView().showEmpty(view, ViewGroup.LayoutParams(-1, -1))
        val tvButton = getMultipleStatusView().findViewById<TextView>(R.id.tvBottom)
        tvButton.visibility = if (visiable) View.VISIBLE else View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccess(event: LoginSuccessEvent) {
        getRefreshView().setEnableLoadMore(true)
        getRefreshView().setEnableRefresh(true)
        page = 1
        getListData()
    }
}


