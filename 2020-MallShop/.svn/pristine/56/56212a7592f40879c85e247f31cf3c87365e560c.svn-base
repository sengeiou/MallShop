package com.epro.mall.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.epro.mall.R
import com.epro.mall.ui.fragment.CartFragment
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.utils.StatusBarUtil

class CartActivity : BaseSimpleActivity() {
    companion object {
        const val TAG = "CartActivity"
        const val PRODUCT_IDS = "productIs"
        fun launch(context: Context) {
            context.startActivity(Intent(context, CartActivity::class.java))
        }

        fun launchWithProductIds(activity: Activity, productIds: ArrayList<String>, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, CartActivity::class.java)
                    .putExtra(PRODUCT_IDS, productIds), requestCode)
        }
        fun launchWithProductIds(fragment: Fragment, productIds:  ArrayList<String>, requestCode: Int) {
            fragment.startActivityForResult(Intent(fragment.context, CartActivity::class.java)
                    .putExtra(PRODUCT_IDS, productIds), requestCode)
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_cart
    }

    override fun initData() {
    }

    override fun initListener() {

    }

    override fun initView() {
        super.initView()
        val list = intent.getStringArrayListExtra(PRODUCT_IDS)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CartFragment.newInstance(list, true)).commitAllowingStateLoss()
    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

}