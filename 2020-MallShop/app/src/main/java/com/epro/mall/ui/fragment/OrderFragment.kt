package com.epro.mall.ui.fragment

import android.os.Bundle
import android.support.v4.app.*
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.OrderContract
import com.epro.mall.mvp.presenter.OrderPresenter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.fragment_order.*
import java.util.*

/**
 * 首页订单页面 tab
 */
class OrderFragment : BaseTitleBarCustomFragment<OrderContract.View, OrderPresenter>(), OrderContract.View {

    companion object {
        const val TAG = "OrderFragment"
        const val ORDER_FRAGMENT_LOGIN = 4
        fun newInstance(str: String): OrderFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = OrderFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): OrderFragment {
            return newInstance("")
        }
    }

    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf(R.string.order_list_title_1, R.string.order_list_title_2,R.string.order_list_title_3,
            R.string.order_list_title_4, R.string.order_list_title_5)
    private val status = arrayOf("","1","8","6","9")  // (1未支付,8待收货,6已完成,9已取消,不传查所有)
    private var mAdapter: MyPagerAdapter? = null
    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        val fragmentA = activity as FragmentActivity
        mFragments.clear()
        for (i in status.indices) {
            mFragments.add(OrderListFragment.newInstance(status[i]).setViewPageFragment(true))
        }
        mAdapter = MyPagerAdapter(fragmentA.supportFragmentManager)
        vpOrders.adapter = mAdapter
        tlOrders.setViewPager(vpOrders)
    }

    override fun getPresenter(): OrderPresenter {
        return OrderPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_order
    }

    override fun lazyLoad() {
        logTools.d(this::class.java.simpleName + "lazy do")
    }

    override fun initData() {

    }

    override fun initListener() {
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return getString(mTitles[position])
        }

        override fun getItem(position: Int): Fragment {
            return mFragments.get(position)
        }
    }

}