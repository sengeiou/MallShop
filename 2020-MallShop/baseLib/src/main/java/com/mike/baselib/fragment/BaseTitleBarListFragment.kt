package com.mike.baselib.fragment


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.classic.common.MultipleStatusView
import com.mike.baselib.R
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.base.ListView
import com.mike.baselib.listener.RefreshEvent
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ToastUtil
import com.mike.baselib.utils.ext_loadGif
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_base_titlebar_list.*
import org.greenrobot.eventbus.EventBus


abstract class BaseTitleBarListFragment<D, V : ListView<D>, P : ListPresenter<D, V>, A : CommonAdapter<D>> :
        BaseTitleBarFragment<V, P>(), ListView<D> {

    override fun layoutCustomContentId(): Int {
        return R.layout.activity_base_titlebar_list;
    }

    protected var page: Int = 1// 第一页开始
    protected var dataList = ArrayList<D>()
    protected var listDataAdapter: A? = null

    override fun initView() {
        super.initView()

        rvList.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        listDataAdapter = getListAdapter(ArrayList())
        rvList.adapter = listDataAdapter
        AppBusManager.initRefreshUI(refreshLayout)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            val event = RefreshEvent()
            event.tag=this::class.java.simpleName
            EventBus.getDefault().post(event)
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            page += 1
            getListData()
        }

        multipleStatusView.setOnRetryClickListener(View.OnClickListener {
            lazyLoad()
        })
    }

    fun getMultipleStatusView(): MultipleStatusView {
        return multipleStatusView
    }

    fun getRvListView(): RecyclerView {
        return rvList
    }

    fun getRefreshView(): SmartRefreshLayout {
        return refreshLayout
    }

    /**
     * 获取列表数据
     */

    abstract fun getListData()

    override fun getListDataSuccess(list: List<D>, type: String) {
      // logTools.toJson(list)
        if (page == 1) {
            this.dataList.clear()
            this.dataList.addAll(list)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishRefresh()
            refreshLayout.setNoMoreData(false)
        } else {
            if (list.size == 0) {
                ToastUtil.showToast(getString(R.string.no_more_data))
                refreshLayout?.finishLoadMoreWithNoMoreData()
                return
            }
            this.dataList.addAll(list)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishLoadMore()
        }
    }

    override fun lazyLoad() {
        page = 1
        getListData()
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
        logTools.d(errorMsg)
        if (page == 1) {
            refreshLayout?.finishRefresh()
        } else {
            refreshLayout?.finishLoadMore()
        }
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showContent()
        }
    }

    override fun showLoading(type: String) {
        logTools.d(refreshLayout?.state)
        logTools.d(multipleStatusView.viewStatus)
        if (refreshLayout.state != RefreshState.Refreshing && refreshLayout.state != RefreshState.Loading
                && multipleStatusView.viewStatus != MultipleStatusView.STATUS_LOADING) {
            multipleStatusView?.showLoading()
            multipleStatusView.findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
            logTools.d("do here")
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (errorCode == ErrorStatus.SUCCESS) {
            if (this.dataList.size == 0) {
                multipleStatusView?.showEmpty()
            } else {
                multipleStatusView?.showContent()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        page = 1// 第一页开始
        dataList = ArrayList()
        listDataAdapter = null
    }

    abstract fun getListAdapter(list: ArrayList<D>): A


    override fun layoutContentId(): Int {
        return 0
    }

}


