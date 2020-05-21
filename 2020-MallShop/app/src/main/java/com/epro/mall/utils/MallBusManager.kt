package com.epro.mall.utils

import android.content.Context
import android.text.TextUtils
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.*
import com.epro.mall.ui.login.LoginActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.baselib.utils.*
import java.util.*
import kotlin.collections.ArrayList

class MallBusManager {
    companion object {
        fun setUser(user: User?) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.USER_JSON, if (user == null) "" else Gson().toJson(user))
        }

        fun getUser(): User? {
            val user_json: String = SPUtils.get(AppContext.getInstance().context, SPConstant.USER_JSON, "") as String
            if (TextUtils.isEmpty(user_json)) {
                return null
            }
            return Gson().fromJson(user_json, User::class.java)
        }

        fun setSearchRecords(records: ArrayList<String>) {
            SPUtils.put(AppContext.getInstance().context, MallConst.KEY_SEARCH_RECORDS, AppBusManager.toJson(records)!!)
        }

        fun setOneSearchRecord(record: String) {
            val records = getSearchRecords()
            if (records.contains(record)) {
                records.remove(record)
            }
            records.add(0, record)
            setSearchRecords(records)
        }

        fun getSearchRecords(): ArrayList<String> {
            val json = SPUtils.get(AppContext.getInstance().context, MallConst.KEY_SEARCH_RECORDS, "") as String
            if (TextUtils.isEmpty(json)) {
                return ArrayList()
            }
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return AppBusManager.fromJson<ArrayList<String>>(json, type)!!
        }

        fun getDevCheckId(): Int {
            var id = 0
            val dev = AppBusManager.getDev()
            when (dev) {
                Constants.DV_TEST -> id = R.id.rbTest
                Constants.DV_PRE_RELEASE -> id = R.id.rbPreRelease
                Constants.DV_RELEASE -> id = R.id.rbRelease
            }
            return id
        }

        /**
         * 获取商品价格区间
         * @param goods
         * @return
         */
        fun getGoodsMinMaxPrice(goods: Goods): String? {
            if (goods.productList.isEmpty()) {
                ToastUtil.showToast("goods info list is null")
                return null
            }
            val maxPriceProduct = Collections.max<Product>(goods.productList) { product1, product2 -> if (product1.onlineSalesPrice.toDouble() - product2.onlineSalesPrice.toDouble() > 0) 1 else -1 }
            val minPriceProduct = Collections.min<Product>(goods.productList) { product1, product2 -> if (product1.onlineSalesPrice.toDouble() - product2.onlineSalesPrice.toDouble() > 0) 1 else -1 }
            if (maxPriceProduct == null) {
                ToastUtil.showToast("max Product is null")
                return null
            }
            if (minPriceProduct == null) {
                ToastUtil.showToast("min Product is null")
                return null
            }
            return if (maxPriceProduct.onlineSalesPrice == minPriceProduct.onlineSalesPrice)
                maxPriceProduct.onlineSalesPrice else minPriceProduct.onlineSalesPrice + "-" + maxPriceProduct.onlineSalesPrice
        }

        /**
         * 获取带活动的商品价格区间
         * @param goods
         * @return
         */
        fun getGoodsWithActivityMinMaxPrice(goods: Goods): String? {
            if (goods.productList.isEmpty()) {
                ToastUtil.showToast("goods info list is null")
                return null
            }
            val realPrices = ArrayList<String>()
            for (p in goods.productList) {
                val a = getProductActivity(p)
                if (a == null) {
                    realPrices.add(p.onlineSalesPrice)
                } else {
                    realPrices.add(a.discountPrice)
                }
            }
            val maxPrice = Collections.max<String>(realPrices) { price1, price2 -> if (price1.toDouble() - price2.toDouble() > 0) 1 else -1 }
            val minPrice = Collections.min<String>(realPrices) { price1, price2 -> if (price1.toDouble() - price2.toDouble() > 0) 1 else -1 }
            if (maxPrice == null) {
                ToastUtil.showToast("max Price is null")
                return null
            }
            if (minPrice == null) {
                ToastUtil.showToast("min Price is null")
                return null
            }
            return if (maxPrice == minPrice)
                maxPrice else minPrice + "-" + maxPrice
        }

        fun getRetailGoodsMinMaxPrice(goods: Goods): String? {
            if (goods.productList.isEmpty()) {
                ToastUtil.showToast("goods info list is null")
                return null
            }
            val maxPriceProduct = Collections.max<Product>(goods.productList) { product1, product2 -> if (product1.retailPrice.toDouble() - product2.retailPrice.toDouble() > 0) 1 else -1 }
            val minPriceProduct = Collections.min<Product>(goods.productList) { product1, product2 -> if (product1.retailPrice.toDouble() - product2.retailPrice.toDouble() > 0) 1 else -1 }
            if (maxPriceProduct == null) {
                ToastUtil.showToast("max Product is null")
                return null
            }
            if (minPriceProduct == null) {
                ToastUtil.showToast("min Product is null")
                return null
            }
            return if (maxPriceProduct.retailPrice == minPriceProduct.retailPrice)
                maxPriceProduct.retailPrice else minPriceProduct.retailPrice + "-" + maxPriceProduct.retailPrice
        }

        fun getGoodsMinMaxPriceWithUnit(goods: Goods): String? {
            return AppBusManager.getAmountUnit() + " " + getGoodsMinMaxPrice(goods)
        }

        fun getProductActivity(product: Product): GoodsActivity? {
            if (!TextUtils.isEmpty(product.onlineActivityInfo)) {
                val type = object : TypeToken<ArrayList<GoodsActivity>>() {}.type
                val activityList = AppBusManager.fromJson<ArrayList<GoodsActivity>>(product.onlineActivityInfo, type)
                if (activityList != null && activityList.isNotEmpty()) {
                    for (a in activityList) {
                        var now = System.currentTimeMillis()
                        if(isGetSystemTimeSuccess()){
                            now += getTimeDifferenceValue().toLong()
                        }
                        if (a.status == "1" && DateUtils.dateToStamp(a.startTime) <= now && now <= DateUtils.dateToStamp(a.endTime)) {
                            return a
                        }
                    }
                }
            }
            return null
        }

        /**
         * 线上商品活动
         */
        fun getProductActivity(cartGoods: CartGoods): GoodsActivity? {
            if (!TextUtils.isEmpty(cartGoods.onlineActivityInfo)) {
                val type = object : TypeToken<ArrayList<GoodsActivity>>() {}.type
                val activityList = AppBusManager.fromJson<ArrayList<GoodsActivity>>(cartGoods.onlineActivityInfo, type)
                if (activityList != null && activityList.isNotEmpty()) {
                    for (a in activityList) {
                        var now = System.currentTimeMillis()
                        if(isGetSystemTimeSuccess()){
                            now += getTimeDifferenceValue().toLong()
                        }
                        if (a.status == "1" && DateUtils.dateToStamp(a.startTime) <= now && now <= DateUtils.dateToStamp(a.endTime)) {
                            return a
                        }
                    }
                }
            }
            return null
        }

        /**
         * 扫码购商品,线下活动
         */
        fun getProductActivity(cartGoods: ScanBuyCartGoods): GoodsActivity? {
            if (!TextUtils.isEmpty(cartGoods.offlineActivityInfo)) {
                val type = object : TypeToken<ArrayList<GoodsActivity>>() {}.type
                val activityList = AppBusManager.fromJson<ArrayList<GoodsActivity>>(cartGoods.offlineActivityInfo, type)
                if (activityList != null && activityList.isNotEmpty()) {
                    for (a in activityList) {
                        var now = System.currentTimeMillis()
                        if(isGetSystemTimeSuccess()){
                            now += getTimeDifferenceValue().toLong()
                        }
                        if (a.status == "1" && DateUtils.dateToStamp(a.startTime) <= now && now <= DateUtils.dateToStamp(a.endTime)) {
                            return a
                        }
                    }
                }
            }
            return null
        }

        fun isHaveActivity(goods: Goods): Boolean {
            var isHave = false
            for (p in goods.productList) {
                if (getProductActivity(p) != null) {
                    isHave = true
                    break
                }
            }
            return isHave
        }

        fun isHaveActivity(shopCart: ShopCart): Boolean {
            var isHave = false
            for (c in shopCart.products) {
                if (getProductActivity(c) != null) {
                    isHave = true
                    break
                }
            }
            return isHave
        }

        fun isHaveActivity(goodsList: ArrayList<ScanBuyCartGoods>): Boolean {
            var isHave = false
            for (c in goodsList) {
                if (getProductActivity(c) != null) {
                    isHave = true
                    break
                }
            }
            return isHave
        }

        /**
         * 获取总库存
         */
        fun getTotalStock(goods: Goods): Int {
            var totalStock = 0
            if (goods.productList.isEmpty()) {
                return totalStock
            }
            for (p in goods.productList) {
                if (p.productNumber > 0) {
                    totalStock += p.productNumber
                }
            }
            return totalStock
        }

        fun getMiniPrice(price: String): String {
            if (TextUtils.isEmpty(price)) {
                return "0.00"
            }
            if (!price.contains("-")) {
                return price
            }
            val prices = price.split("-")
            return prices[0]
        }

        //是否区间价格
        fun isIntervalPrice(price: String): Boolean {
            if (TextUtils.isEmpty(price)) {
                return false
            }
            if (!price.contains("-")) {
                return false
            }
            val prices = price.split("-")
            return prices[0] != prices[1]
        }

        fun isProductHaveActivity(activityPrice: String?, price: String): Boolean {
            if (TextUtils.isEmpty(activityPrice)) {
                return false
            }
            if (activityPrice == price) {
                return false
            }
            return true
        }

        // 付款方式付款方式(1.现金 2.支付宝 3.paypal 4.微信 5.信用卡 6.银行卡)
        fun getPayModeText(mode: Int): String {
            return switchText(mode, R.array.filter_pay_type)
        }

        fun switchText(type: Int, res: Int): String {
            if (type < 0) {
                return ""
            }
            return AppContext.getInstance().context.resources.getStringArray(res)[type]
        }

        //        9000	订单支付成功
//        8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//        4000	订单支付失败
//        5000	重复请求
//        6001	用户中途取消
//        6002	网络连接出错
//        6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//        其它	其它支付错误
        fun getZfbResultText(resultStatus: Int): String {
            return when (resultStatus) {
                9000 -> AppContext.getInstance().getString(R.string.order_pay_success)
                8000 -> AppContext.getInstance().getString(R.string.order_processing)
                4000 -> AppContext.getInstance().getString(R.string.order_pay_failed)
                5000 -> AppContext.getInstance().getString(R.string.duplicate_request)
                6001 -> AppContext.getInstance().getString(R.string.user_canceled_halfway)
                6002 -> AppContext.getInstance().getString(R.string.network_connection_error)
                6004 -> AppContext.getInstance().getString(R.string.order_processing)
                else -> AppContext.getInstance().getString(R.string.other_pay_error)
            }

        }

        fun checkNotLogin(context: Context): Boolean {
            if (!AppBusManager.isLogin()) {
                LoginActivity.launch(context)
                return true
            }
            return false
        }

        fun getMineItemData(): ArrayList<ItemMine> {
            val list = ArrayList<ItemMine>()
            val mTexts1 = arrayOf(R.string.modify_password, R.string.about_us)
            val mIcons1 = arrayOf(R.mipmap.my_icon2, R.mipmap.my_icon4)
            val mBackgrounds1 = arrayOf(R.drawable.shape_bg_white_half_radius17_top, R.drawable.shape_bg_white_half_radius5)

            val mTexts = arrayOf(R.string.account_relative, R.string.modify_password, R.string.language_change, R.string.receive_address, R.string.about_us, R.string.my_message, R.string.set_dv)
            val mIcons = arrayOf(R.mipmap.my_icon1, R.mipmap.my_icon2, R.mipmap.icon_language, R.mipmap.my_icon3, R.mipmap.my_icon4, R.mipmap.my_icon4, R.mipmap.my_icon4)
            val mBackgrounds = arrayOf(R.drawable.shape_bg_white_half_radius17_top, R.color.white, R.drawable.shape_bg_white_half_radius5,
                    R.drawable.shape_bg_white_half_radius6_top, R.drawable.shape_bg_white_half_radius5, R.color.white, R.color.white)

            if (AppConfig.isPublish) {
                for (index in mTexts1.indices) {
                    list.add(ItemMine(mIcons1[index], AppContext.getInstance().getString(mTexts1[index]), mBackgrounds1[index]))
                }
            } else {
                for (index in mTexts.indices) {
                    list.add(ItemMine(mIcons[index], AppContext.getInstance().getString(mTexts[index]), mBackgrounds[index]))
                }
                val item = list.get(list.size - 1)
                item.content = item.content + "(" + AppBusManager.getDevName() + ")"
            }
            return list
        }

        fun setLocationInfo(info: GeolocationBean?) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.LOCATION_INFO, if (info == null) "" else Gson().toJson(info))
        }

        fun getLocationInfo(): GeolocationBean? {
            val info = SPUtils.get(AppContext.getInstance().context, SPConstant.LOCATION_INFO, "") as String
            if (TextUtils.isEmpty(info)) {
                return null
            }
            return Gson().fromJson(info, GeolocationBean::class.java)
        }

        fun setTimeDifferenceValue(value:String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.DIFFERENCE_VALUE, value)
        }

        fun getTimeDifferenceValue(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.DIFFERENCE_VALUE, "#") as String
        }

        fun isGetSystemTimeSuccess(): Boolean {
            return getTimeDifferenceValue().ext_isPureNumber_orDecimal()
        }

        /**
         * 过滤掉超过配送距离的地址
         */

        fun filterAddress(shopInfo: ShopInfo, addressList: ArrayList<Address>): ArrayList<Address> {
            var deliveryDistance = 0
            if (!TextUtils.isEmpty(shopInfo.distance)) {
                deliveryDistance = shopInfo.distance.toInt() * 1000
            }
            for (a in addressList) {
                val d = MallMapUtils.calculateLineDistance(shopInfo.latitude.toDouble(), shopInfo.longitude.toDouble()
                        , a.latitude.toDouble(), a.longitude.toDouble())
                a.distance = d.toInt()
                a.isEnabled = deliveryDistance >= d
            }
            return addressList
        }

        /**
         * 确认订单页面获取合适的地址
         */

        fun getRightAddress(shopInfo: ShopInfo, addresses: ArrayList<Address>): Address? {
            var default: Address? = null
            if (addresses.isEmpty()) {
                return default
            }
            val addressList = filterAddress(shopInfo, addresses)
            for (a in addressList) {
                if (a.isDefult == 1 && a.isEnabled) {
                    default = a
                    break
                }
            }
            if (default != null) {
                return default
            }
            for (a in addressList) {
                if (a.isEnabled) {
                    default = a
                    break
                }
            }
            return default
        }
    }

}