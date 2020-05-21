package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.PayResultContract
import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.epro.mall.mvp.model.bean.PayResult
import com.epro.mall.mvp.presenter.PayResultPresenter
import com.epro.mall.ui.MainActivity
import com.epro.mall.ui.adapter.ShopNewGoodsListAdapter
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_createJsonKey
import com.mike.baselib.utils.ext_setLeftImageResource
import kotlinx.android.synthetic.main.activity_pay_result.*

class PayResultActivity : BaseTitleBarCustomActivity<PayResultContract.View, PayResultPresenter>(), PayResultContract.View, View.OnClickListener {

    override fun onGetShopNewGoodsListSuccess(goodsList: ArrayList<GetShopNewGoodsListBean.NewGoods>) {
        (rvGoods.adapter as ShopNewGoodsListAdapter).setData(goodsList)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnCheck -> {
                val payResult = AppBusManager.fromJsonWithClassKey(intent, PayResult::class.java)
                val orderSn = payResult!!.orderSn
                OrderDetailActivity.launchWithStr(this, orderSn!!)
            }

            getRightView() -> {
                MainActivity.launchMain(this, 0)
                finish()
            }
        }
    }

    companion object {
        const val TAG = "PayResult"
        fun launchWithPayResult(context: Context, payResult: PayResult) {
            context.startActivity(Intent(context, PayResultActivity::class.java).putExtra(ext_createJsonKey(PayResult::class.java), AppBusManager.toJson(payResult)))
        }
    }

    override fun getPresenter(): PayResultPresenter {
        return PayResultPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_pay_result
    }

    override fun initData() {
        rvGoods.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        rvGoods.adapter = ShopNewGoodsListAdapter(this, ArrayList())
        (rvGoods.adapter as ShopNewGoodsListAdapter).onItemClickListener = object : ShopNewGoodsListAdapter.OnItemClickListener {
            override fun onClick(item: GetShopNewGoodsListBean.NewGoods) {
                GoodsDetailActivity.launchWithGoodsId(this@PayResultActivity, item.goodsId)
            }
        }
    }

    override fun initView() {
        super.initView()
        getLeftTitleView().text = ""
        getRightView().visibility = View.VISIBLE
        getRightView().ext_setLeftImageResource(R.mipmap.nav_home_default)
        val payResult = AppBusManager.fromJsonWithClassKey(intent, PayResult::class.java)
        when (payResult!!.payStatus) {
            MallConst.PAY_SUCCESS -> {
                ivResult.setImageResource(R.mipmap.icon_successful_payment)
                tvResult.text = getString(R.string.pay_success)
            }
            MallConst.PAY_FAILED -> {
                ivResult.setImageResource(R.mipmap.pay_failed)
                tvResult.text = getString(R.string.pay_failed)

            }
            MallConst.PAY_UNKOWN -> {
                ivResult.setImageResource(R.mipmap.pay_failed)
                tvResult.text = getString(R.string.pay_abnormal)
            }
        }
        mPresenter.getShopNewGoodsList(payResult.shopId, MallConst.GET_SHOP_GOODSLIST_NEW)
    }

    override fun initListener() {
        btnCheck.setOnClickListener(this)
        getRightView().setOnClickListener(this)
    }

}


