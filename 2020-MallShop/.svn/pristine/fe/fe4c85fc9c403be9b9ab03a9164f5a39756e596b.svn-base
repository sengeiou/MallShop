package com.epro.mall.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.mall.R
import com.mike.baselib.listener.LoginSuccessEvent
import com.mike.baselib.listener.LogoutSuccessEvent
import com.epro.mall.mvp.contract.CartContract
import com.epro.mall.mvp.model.bean.CartGoods
import com.epro.mall.mvp.model.bean.ShopCart
import com.epro.mall.mvp.presenter.CartPresenter
import com.epro.mall.ui.activity.OrderInfoActivity
import com.epro.mall.ui.activity.ShopDetailActivity
import com.epro.mall.ui.adapter.ShopCartAdapter
import com.epro.mall.ui.login.LoginActivity
import com.mike.baselib.view.EmptyView
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.epro.mall.listener.CartChangeEvent
import com.mike.baselib.fragment.BaseRedTitleBarCustomFragment
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.*
import com.scwang.smartrefresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast

/**
 * 首页购物车
 */
class CartFragment : BaseRedTitleBarCustomFragment<CartContract.View, CartPresenter>(), CartContract.View, View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            cbAllSelect -> {
                for (cart in adapter?.mData!!) {
                    cart.judgeValue = cbAllSelect.isChecked
                    ext_setAllValue(cart.products, cbAllSelect.isChecked)
                }
                adapter?.notifyDataSetChanged()
                updateCartUI()
            }

            btnCheckout -> {
                if (sizeOfShop == 0) {
                    toast(getString(R.string.pls_choose_product))
                    return
                }
                if (sizeOfShop > 1) {
                    toast(getString(R.string.not_support_multi_store))
                    return
                }
                goToOrderInfo()
            }
        }
    }

    /**
     * 确认订单
     */
    private fun goToOrderInfo() {
        val selectedProducts = ArrayList<CartGoods>()
        for (p in selectedShopCart!!.products) {
            if (p.judge()) {
                selectedProducts.add(p)
            }
        }
        if (selectedProducts.isEmpty()) {
            toast(getString(R.string.pls_choose_product))
            return
        }
        val newShopCart = AppBusManager.fromJson(AppBusManager.toJson(selectedShopCart), ShopCart::class.java)
        newShopCart!!.products = selectedProducts
        OrderInfoActivity.launchWithShopCart(this@CartFragment, newShopCart, FOR_CHECOUT_RESULT)
    }

    /**
     * 结算个数,金额的更新
     */
    private fun updateCartUI() {
        var count = 0
        var amount = 0.toDouble()
        val shopIds = ArrayList<String>()
        var shopCount = 0
        for (cart in adapter?.mData!!) {
            if (cart.isValid != 0) {
                shopCount++
                for (cartGoods in cart.products) {
                    if (cartGoods.judge()) {
                        amount += cartGoods.salePrice.toDouble() * cartGoods.productCount
                        count += cartGoods.productCount
                        selectedShopCart = cart
                        if (!shopIds.contains(cart.shopId)) {
                            shopIds.add(cart.shopId)
                        }
                    }
                }
            }
        }
        tvSelectNum1.text = getString(R.string.selected) + "($count)"
        tvUnit.text = AppBusManager.getAmountUnit()
        tvTotal1.text = amount.ext_formatAmount()
        sizeOfShop = shopIds.size
        if (shopCount == 0) {
            rlBottom1.visibility = View.GONE
        } else {
            rlBottom1.visibility = View.VISIBLE
        }
//        if(activity is MainActivity){
//            val ac = activity as MainActivity
//            ac.showMsgCount(2, count)
//        }
        calculateActivityDiscount(amount)
    }

    /**
     * 计算活动优惠
     */
    private fun calculateActivityDiscount(amount: Double) {
        var isHaveActivity = false
        for (cart in adapter?.mData!!) {
            if (MallBusManager.isHaveActivity(cart)) {
                isHaveActivity = true
                break
            }
        }
        if (!isHaveActivity) {
            return
        }
        var discount = 0.toDouble() //总优惠
        var totalRealAmount = 0.toDouble()//总实际支付金额
        for (cart in adapter?.mData!!) {
            if (cart.isValid != 0) {
                for (goods in cart.products) {
                    if (goods.judge()) {
                        val a = MallBusManager.getProductActivity(goods)
                        if (a != null) {
                            totalRealAmount += a.discountPrice.toDouble() * goods.productCount
                        } else {
                            totalRealAmount += goods.salePrice.toDouble() * goods.productCount
                        }
                    }
                }
            }
        }
        tvTotal1.text = totalRealAmount.ext_formatAmount()
        discount = amount - totalRealAmount
        if (discount > 0.toDouble()) {
            llDiscount.visibility = View.VISIBLE
            tvDiscount.text = discount.ext_formatAmountWithUnit()
        } else {
            llDiscount.visibility = View.GONE
        }
    }

    private fun updateAllSelectCheckBox() {
        val delete = ArrayList<ShopCart>()
        for (cart in adapter?.mData!!) {
            if (cart.isValid == 0) {
                delete.add(cart)
            }
        }
        val all = ArrayList<ShopCart>()
        all.addAll(adapter!!.mData)
        all.removeAll(delete)
        cbAllSelect.isChecked = ext_isAllTrue(all)
    }

    override fun onGetCartGoodsListSuccess(shopCarts: ArrayList<ShopCart>) {
        val selectProductIds = arguments!!.getStringArrayList(PRODUCT_IDS)
        if (selectProductIds != null && selectProductIds.isNotEmpty()) {
            for (shopCart in shopCarts) {
                for (p in shopCart.products) {
                    if (selectProductIds.contains(p.productId)) {
                        p.judgeValue = true
                    }
                }
                if (ext_isAllTrue(shopCart.products)) {
                    shopCart.judgeValue = true
                }
            }

        }
        adapter?.setData(shopCarts)
        updateCartUI()
        updateAllSelectCheckBox()
    }


    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if ((type == MallConst.GET_CARTGOODS_LIST || type == MallConst.DELETE_CARTGOODS) && errorCode == ErrorStatus.SUCCESS) {
            if (adapter!!.mData.isEmpty()) {
                showEmptyView(false)
            } else {
                getMultipleStatusView().showContent()
            }
            finishRefresh()
        } else {
            super.dismissLoading(errorMsg, errorCode, type)
        }
    }

    override fun onDeleteCartGoodsSuccess() {
        toast(getString(R.string.delete_success))
        adapter!!.updateDeleteUI(deleteCartGoods!!, deleteChangePosition)
        updateCartUI()
        updateAllSelectCheckBox()
    }

    override fun onModifyCartGoodsSuccess() {
        //toast("修改成功")
        // adapter!!.notifyItemChanged(numChangePosition)
        updateCartUI()
        updateAllSelectCheckBox()
    }

    override fun getPresenter(): CartPresenter {
        return CartPresenter()
    }


    companion object {
        const val TAG = "CartFragment"
        const val HAVE_BACK = "have_back"
        const val FOR_CHECOUT_RESULT = 10
        const val PRODUCT_IDS = "productIs"
        const val CART_TO_LOGIN = 4
        fun newInstance(productIds: ArrayList<String>?, isHaveBack: Boolean = false): CartFragment {
            val args = Bundle()
            args.putStringArrayList(PRODUCT_IDS, productIds)
            args.putBoolean(HAVE_BACK, isHaveBack)
            val fragment = CartFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): CartFragment {
            return newInstance(ArrayList())
        }
    }


    var adapter: ShopCartAdapter? = null
    var numChangePosition = 0
    var deleteChangePosition = 0
    var deleteCartGoods: CartGoods? = null
    var selectedShopCart: ShopCart? = null
    var sizeOfShop = 0
    override fun initData() {
        rvCart.layoutManager = LinearLayoutManager(activity!!)
        adapter = ShopCartAdapter(activity!!, ArrayList())
        rvCart.adapter = adapter
        adapter?.onItemClickListener = object : ShopCartAdapter.OnItemClickListener {
            override fun onClick(data: ShopCart) {
                ShopDetailActivity.launchWithShopId(activity!!, data.shopId)
            }
        }
        adapter!!.onShopCartListEmptyListener = object : ShopCartAdapter.OnShopCartListEmptyListener {
            override fun onShopCartListEmpty() {
//                if (adapter?.mData!!.size == 0) {
//                    rlBottom1.visibility = View.GONE
//                }else{
//                    rlBottom1.visibility = View.VISIBLE
//                }
            }
        }
        adapter!!.onItemNumChangeListener = object : ShopCartAdapter.OnItemNumChangeListener {
            override fun onItemNumberChange(cartGoods: CartGoods, position: Int) {
                numChangePosition = position
                mPresenter.modifyCartGoods(cartGoods.productId, cartGoods.productCount, MallConst.MODIFY_CARTGOODS)
            }
        }

        adapter!!.onItemDeleteListener = object : ShopCartAdapter.OnItemDeleteListener {
            override fun onItemDelete(cartGoods: CartGoods, position: Int) {
                deleteChangePosition = position
                deleteCartGoods = cartGoods
                mPresenter.deleteCartGoods(cartGoods.productId, MallConst.DELETE_CARTGOODS)
            }
        }

        adapter!!.onItemConfirmListener = object : ShopCartAdapter.OnItemConfirmListener {
            override fun onItemConfirm(shopCart: ShopCart, position: Int) {
                selectedShopCart = shopCart
                goToOrderInfo()
            }
        }

        adapter!!.onItemSelectedListener = object : ShopCartAdapter.OnItemSelectedListener {
            override fun onItemSelected(shopCart: ShopCart, position: Int) {
                updateAllSelectCheckBox()
                updateCartUI()
            }
        }

        adapter!!.onShopSelectedListener = object : ShopCartAdapter.OnShopSelectedListener {
            override fun onShopSelected(shopCart: ShopCart, position: Int) {
                updateAllSelectCheckBox()
                updateCartUI()
            }
        }
    }

    override fun initListener() {
        rvCart.addOnScrollListener(scrollListener)
        cbAllSelect.setOnClickListener(this)
        btnCheckout.ext_doubleClick(this)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (getRefreshLayout().state != RefreshState.Loading) {
                val topRowVerticalPosition =
                        if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                setRefreshEnable(topRowVerticalPosition >= 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                FOR_CHECOUT_RESULT -> {
                    mPresenter.getCartGoodsList(MallConst.GET_CARTGOODS_LIST)
                }
            }
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_cart
    }

    override fun lazyLoad() {
        logTools.d(this::class.java.simpleName + "lazy do")
        if (AppBusManager.isLogin()) {
            mPresenter.getCartGoodsList(MallConst.GET_CARTGOODS_LIST)
        } else {
            showEmptyView()
            rlBottom1.visibility = View.GONE
            setRefreshEnable(false)
        }
    }

    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.cart)
        val isHaveBack = arguments!!.getBoolean(HAVE_BACK)
        getLeftBackView().visibility = if (isHaveBack) View.VISIBLE else View.GONE
        setRefreshEnable(true)
        if (!AppBusManager.isLogin()) {
            showEmptyView()
        } else {

        }
        rlBottom1.visibility = if (AppBusManager.isLogin()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        rvCart.removeOnScrollListener(scrollListener)
        super.onDestroyView()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartChangeEvent(event: CartChangeEvent) {
        if (AppBusManager.isLogin()) {
            mPresenter.getCartGoodsList(MallConst.GET_CARTGOODS_LIST)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccess(event: LoginSuccessEvent) {
        //  logTools.t("YB").d(" onLoginSuccess : ")
        //getTitleBar().visibility = View.GONE
        mPresenter.getCartGoodsList(MallConst.GET_CARTGOODS_LIST)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onLogoutSuccess(event: LogoutSuccessEvent) {
        logTools.d("do here logout")
        adapter!!.setData(ArrayList())
        showEmptyView()
        rlBottom1.visibility = View.GONE
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        if (errorCode == ErrorStatus.MULTI_DEVICE_ERROR || errorCode == ErrorStatus.TOKEN_EXPIERD) {
            adapter!!.setData(ArrayList())
            showEmptyView()
            rlBottom1.visibility = View.GONE
        }
    }

    private fun showEmptyView(visiable: Boolean = true) {
        // logTools.t("YB").d(" showEmptyView : ")
        val view = EmptyView.Builder(activity!!)
                .setImageResoue(R.mipmap.empty_cart)
                .setShowText1(getString(R.string.cart_empty_pls_stroll_around))
                .setShowText2(getString(R.string.login_check_cart))
                .setClickLisener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        LoginActivity.launchWithForResult(this@CartFragment, CART_TO_LOGIN)
                    }
                })
                .create()
        getMultipleStatusView().showEmpty(view, ViewGroup.LayoutParams(-1, -1))
        val tvButton = getMultipleStatusView().findViewById<TextView>(R.id.tvBottom)
        tvButton.visibility = if (visiable) View.VISIBLE else View.GONE
    }
}