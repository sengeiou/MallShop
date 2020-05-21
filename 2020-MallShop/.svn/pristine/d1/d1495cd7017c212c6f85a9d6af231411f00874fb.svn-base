package com.epro.mall.ui.fragment

import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.mvp.model.bean.Product
import com.epro.mall.mvp.model.bean.Spec
import com.epro.mall.mvp.model.bean.SpecValue
import com.epro.mall.ui.adapter.SpecAdapter
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.bottompopup_select_spec.*

/**
 * 选择规格底部弹窗
 */
class SelectSpecBottomPopup : BaseBottomPopup(), View.OnClickListener {

    companion object {
        const val BUY_TYPE = "buy_type"
        const val ADD_TO_CART = "1" //加入购物车
        const val BUY_RIGHT_NOW = "2"// 立即购买
    }

    override fun onClick(p0: View?) {
        when (p0) {
            ivClose -> {
                dismiss()
            }
            btnSure -> {
                if (MallBusManager.checkNotLogin(activity!!)) {
                    return
                }
                if (!checkSpecAllSelected()) {
                    return
                }
                if (product == null) {
                    toast("product is null")
                }
                if (tvNum.text.toString().toInt() > product!!.productNumber) {
                    toast(getString(R.string.out_of_stock))
                    return
                }
                val buyType = arguments!!.getString(BUY_TYPE)
                onSureClickListner?.onSureClick(buyType!!, product!!, tvNum.text.toString().toInt())
            }
            tvPlus -> {
                var num = Integer.valueOf(tvNum.text.toString())
                num++
                if (product != null) {
                    if (num > product!!.productNumber) {
                        toast(getString(R.string.out_of_stock))
                        tvNum.text = "" + (num - 1)
                        return
                    }
                }
                tvNum.text = "" + num
            }

            tvMinus -> {
                var num = Integer.valueOf(tvNum.text.toString())
                if (num == 1) {
                    toast(getString(R.string.not_be_less_anymore))
                    return
                }
                num--
                tvNum.text = "" + num
            }
        }

    }

    override fun initView() {
        tvUnit.text = AppBusManager.getAmountUnit()
    }

    override fun initListener() {
        btnSure.setOnClickListener(this)
        ivClose.setOnClickListener(this)
        tvPlus.setOnClickListener(this)
        tvMinus.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.bottompopup_select_spec

    }

    /**
     * 处理有带活动的商品
     */
    private fun handlerGoodsActivity(goods: Goods) {
        val isHaveActivity=MallBusManager.isHaveActivity(goods)
        if(isHaveActivity){
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit() + " " + MallBusManager.getGoodsMinMaxPrice(goods)?.ext_formatAmount())
            llActivity.visibility=View.VISIBLE
            tvPrice.text=MallBusManager.getGoodsWithActivityMinMaxPrice(goods)?.ext_formatAmount()
        }else{
            llActivity.visibility=View.GONE
            tvPrice.text = MallBusManager.getGoodsMinMaxPrice(goods)?.ext_formatAmount()
        }
    }

    var adapter: SpecAdapter? = null
    var goods: Goods? = null
    var product: Product? = null
    override fun initData() {
        goods = AppBusManager.fromJson(arguments?.getString(ext_createJsonKey(Goods::class.java))!!, Goods::class.java)
        //价格
        handlerGoodsActivity(goods!!)
        //库存
        tvStock.text =getString(R.string.inventory)+" "  + MallBusManager.getTotalStock(goods!!)
        ivSpecImage.ext_loadConnersImageWithDomain(goods!!.goodsCompressPriUrl)

        rvSpecs.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        adapter = SpecAdapter(activity!!, goods)
        rvSpecs.adapter = adapter
        adapter?.onChildItemSelectListener = object : SpecAdapter.OnChildItemSelectListener {
            override fun onChildItemSelect(spec: Spec, specValue: SpecValue) {
            }
        }
        adapter?.onProductSelectListener = object : SpecAdapter.OnProductSelectListener {
            override fun onProductSelect(product: Product?) {
                this@SelectSpecBottomPopup.product = product
                if (product != null) {
                    tvPrice.text = product.onlineSalesPrice.ext_formatAmount()
                    tvStock.text = getString(R.string.inventory)+" " + product.productNumber.toString()
                    ivSpecImage.ext_loadConnersImageWithDomain(product.compressPicUrl)

                    //如果商品有加入活动
                    val activity=MallBusManager.getProductActivity(product)
                    if(activity==null){
                        llActivity.visibility=View.GONE
                    }else{
                        llActivity.visibility=View.VISIBLE
                        tvPrice.text=activity.discountPrice.ext_formatAmount()
                        tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+ product.onlineSalesPrice.ext_formatAmount())
                    }

                } else {
                    handlerGoodsActivity(goods!!)
                    tvStock.text =getString(R.string.inventory)+" "  + MallBusManager.getTotalStock(goods!!)
                    ivSpecImage.ext_loadConnersImageWithDomain(goods!!.goodsCompressPriUrl)
                }
            }
        }
        adapter!!.findProduct()

    }

    private fun checkSpecAllSelected(): Boolean {
        val firstFalse = ext_FirstFalse(adapter?.mData!!)
        if (firstFalse == -2) {
            toast("Spec data is null")
            return false
        }
        if (firstFalse >= 0) {
            toast(adapter?.mData!![firstFalse].name + getString(R.string.not_selected))
            return false
        }
        return true
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        val bundle = arguments
        bundle?.putString(ext_createJsonKey(Goods::class.java), AppBusManager.toJson(goods))
        onPopupDismissListener?.onPopupDismiss(bundle!!)
    }

    interface OnSureClickListner {
        fun onSureClick(buyTpe: String, product: Product, buyNum: Int)
    }

    var onSureClickListner: OnSureClickListner? = null
}